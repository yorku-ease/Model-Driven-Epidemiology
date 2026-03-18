"""
Vector Store for Semantic RAG

- Builds FAISS index from paper_sections.json
- Semantic search using sentence-transformers
- Smart chunking at ~500 chars with sentence boundaries
"""

import json
import re
from dataclasses import dataclass, asdict, field
from pathlib import Path
from typing import List, Optional

import faiss
import numpy as np
from sentence_transformers import SentenceTransformer

from ..section_classifier import SectionLabel


@dataclass
class Chunk:
    chunk_id: str
    source_type: str
    source_id: str
    title: str
    content: str
    score: float = 0.0

    def to_section_label(self) -> SectionLabel:
        """Convert to SectionLabel for compatibility with existing code."""
        return SectionLabel(
            title=self.title,
            content=self.content,
            label=self.source_type,
            score=self.score,
            section_type=self.source_type,
        )

    def to_dict(self) -> dict:
        return asdict(self)

    @classmethod
    def from_dict(cls, d: dict) -> "Chunk":
        return cls(**d)


class VectorStore:
    """FAISS-backed semantic search for paper sections with smart chunking."""

    DEFAULT_MODEL = "all-MiniLM-L6-v2"
    DIMENSION = 384
    CHUNK_SIZE = 500
    CHUNK_OVERLAP = 50

    def __init__(self, model_name: str = DEFAULT_MODEL, chunk_size: int = CHUNK_SIZE):
        self.model = SentenceTransformer(model_name)
        self.model_name = model_name
        self.chunk_size = chunk_size
        self.index: Optional[faiss.Index] = None
        self.chunks: List[Chunk] = []
        self._id_to_chunk: dict = {}
        self._built = False

    def _split_into_sentences(self, text: str) -> List[str]:
        """Split text into sentences."""
        sentence_endings = r"(?<=[.!?])\s+"
        sentences = re.split(sentence_endings, text)
        return [s.strip() for s in sentences if s.strip()]

    def _chunk_text(self, text: str, chunk_id_prefix: str) -> List[Chunk]:
        """Split text into ~500 char chunks at sentence boundaries."""
        sentences = self._split_into_sentences(text)

        if not sentences:
            return []

        chunks = []
        current_chunk = ""
        chunk_idx = 0

        for sentence in sentences:
            if len(current_chunk) + len(sentence) + 1 <= self.chunk_size:
                current_chunk += (" " if current_chunk else "") + sentence
            else:
                if current_chunk:
                    chunks.append(
                        Chunk(
                            chunk_id=f"{chunk_id_prefix}_{chunk_idx}",
                            source_type="chunk",
                            source_id=chunk_id_prefix,
                            title="",
                            content=current_chunk.strip(),
                        )
                    )
                    chunk_idx += 1

                if len(sentence) <= self.chunk_size:
                    current_chunk = sentence
                else:
                    words = sentence.split()
                    current_chunk = ""
                    for word in words:
                        if len(current_chunk) + len(word) + 1 <= self.chunk_size:
                            current_chunk += (" " if current_chunk else "") + word
                        else:
                            if current_chunk:
                                chunks.append(
                                    Chunk(
                                        chunk_id=f"{chunk_id_prefix}_{chunk_idx}",
                                        source_type="chunk",
                                        source_id=chunk_id_prefix,
                                        title="",
                                        content=current_chunk.strip(),
                                    )
                                )
                                chunk_idx += 1
                            current_chunk = word

        if current_chunk.strip():
            chunks.append(
                Chunk(
                    chunk_id=f"{chunk_id_prefix}_{chunk_idx}",
                    source_type="chunk",
                    source_id=chunk_id_prefix,
                    title="",
                    content=current_chunk.strip(),
                )
            )

        return chunks

    def build_from_paper_sections(self, paper_sections_path: Path) -> int:
        """Build FAISS index from paper_sections.json.

        Args:
            paper_sections_path: Path to paper_sections.json

        Returns:
            Number of chunks created
        """
        print(f"[VectorStore] Loading paper sections from {paper_sections_path}")
        with open(paper_sections_path, "r", encoding="utf-8") as f:
            paper_data = json.load(f)

        self.chunks = []

        sections = paper_data.get("sections", {})
        if isinstance(sections, dict):
            section_chunks = self._create_section_chunks(sections)
            self.chunks.extend(section_chunks)
        elif isinstance(sections, list):
            section_dict = {f"section_{i}": s for i, s in enumerate(sections, 1)}
            section_chunks = self._create_section_chunks(section_dict)
            self.chunks.extend(section_chunks)

        tables = paper_data.get("tables", [])
        if tables:
            table_chunks = self._create_table_chunks(tables)
            self.chunks.extend(table_chunks)

        print(f"[VectorStore] Created {len(self.chunks)} chunks")
        print(
            f"  - Sections: {sum(1 for c in self.chunks if c.source_type == 'section')}"
        )
        print(f"  - Tables: {sum(1 for c in self.chunks if c.source_type == 'table')}")

        self._embed_and_build_index()

        return len(self.chunks)

    def _create_section_chunks(self, sections: dict) -> List[Chunk]:
        """Split sections into ~500 char chunks at sentence boundaries."""
        chunks = []

        for section_id, section_data in sections.items():
            if isinstance(section_data, dict):
                heading = section_data.get("heading", section_data.get("title", ""))
                text = section_data.get("text", section_data.get("content", ""))
            else:
                continue

            if not text or len(text.strip()) < 50:
                continue

            text_chunks = self._chunk_text(text, f"section_{section_id}")

            for chunk in text_chunks:
                chunk.title = heading
                chunk.source_type = "section"
                chunk.source_id = section_id
                chunks.append(chunk)

        return chunks

    def _create_table_chunks(self, tables: list) -> List[Chunk]:
        """Tables kept as single chunks (they're already compact)."""
        chunks = []

        for i, table in enumerate(tables, 1):
            if not isinstance(table, dict):
                continue

            table_index = table.get("table_index", i)
            page = table.get("page", "N/A")
            data = table.get("data", [])

            if not data:
                continue

            table_text = self._format_table_as_text(data)

            chunk = Chunk(
                chunk_id=f"chunk_table_{table_index}",
                source_type="table",
                source_id=f"table_{table_index}",
                title=f"Table {table_index} (page {page})",
                content=table_text,
            )
            chunks.append(chunk)

        return chunks

    def _format_table_as_text(self, data: list) -> str:
        """Format table data as readable text."""
        lines = []

        for row in data:
            if not row:
                continue
            cells = [str(cell).strip() if cell else "" for cell in row]
            lines.append(" | ".join(cells))

        return "\n".join(lines)

    def _embed_and_build_index(self) -> None:
        """Embed all chunks and build FAISS index."""
        if not self.chunks:
            print("[VectorStore] No chunks to index")
            return

        print(f"[VectorStore] Embedding {len(self.chunks)} chunks...")
        texts = [f"{c.title}\n\n{c.content}" for c in self.chunks]
        embeddings = self.model.encode(texts, show_progress_bar=True)

        dim = embeddings.shape[1]
        self.index = faiss.IndexFlatIP(dim)
        faiss.normalize_L2(embeddings)
        self.index.add(embeddings)

        self._id_to_chunk = {c.chunk_id: c for c in self.chunks}
        self._built = True

        print(
            f"[VectorStore] FAISS index built: {self.index.ntotal} vectors, dim={dim}"
        )

    def semantic_search(
        self,
        query: str,
        top_k: int = 10,
        include_types: Optional[List[str]] = None,
        exclude_types: Optional[List[str]] = None,
        min_score: float = 0.0,
    ) -> List[Chunk]:
        """Search by semantic similarity.

        Args:
            query: Search query text
            top_k: Number of results to return
            include_types: Filter to include only these types
            exclude_types: Filter to exclude these types
            min_score: Minimum similarity score threshold

        Returns:
            List of Chunks sorted by relevance score (descending)
        """
        if not self._built or self.index is None:
            raise RuntimeError(
                "VectorStore not built. Call build_from_paper_sections() first."
            )

        query_embedding = self.model.encode([query])
        faiss.normalize_L2(query_embedding)

        search_k = min(top_k * 3, self.index.ntotal)
        scores, indices = self.index.search(
            query_embedding.astype(np.float32), search_k
        )

        results = []
        for idx, score in zip(indices[0], scores[0]):
            if idx < 0:
                continue

            chunk = self.chunks[idx]

            if score < min_score:
                continue

            if include_types and chunk.source_type not in include_types:
                continue
            if exclude_types and chunk.source_type in exclude_types:
                continue

            chunk.score = float(score)
            results.append(chunk)

            if len(results) >= top_k:
                break

        return results

    def save(self, path: Path) -> None:
        """Save index + chunks to disk.

        Saves:
        - faiss_index.bin: The FAISS index
        - chunks.json: List of Chunk metadata
        - config.json: Model info
        """
        path = Path(path)
        path.mkdir(parents=True, exist_ok=True)

        if self.index is not None:
            index_path = path / "faiss_index.bin"
            faiss.write_index(self.index, str(index_path))

        chunks_path = path / "chunks.json"
        with open(chunks_path, "w", encoding="utf-8") as f:
            json.dump(
                [c.to_dict() for c in self.chunks], f, indent=2, ensure_ascii=False
            )

        config_path = path / "config.json"
        with open(config_path, "w") as f:
            json.dump(
                {
                    "model": self.model_name,
                    "dimension": self.DIMENSION,
                    "num_chunks": len(self.chunks),
                },
                f,
            )

        print(f"[VectorStore] Saved to {path}")

    def load(self, path: Path) -> None:
        """Load index + chunks from disk."""
        path = Path(path)

        index_path = path / "faiss_index.bin"
        if index_path.exists():
            self.index = faiss.read_index(str(index_path))

        chunks_path = path / "chunks.json"
        with open(chunks_path, "r", encoding="utf-8") as f:
            chunks_data = json.load(f)
            self.chunks = [Chunk.from_dict(c) for c in chunks_data]

        self._id_to_chunk = {c.chunk_id: c for c in self.chunks}
        self._built = True

        print(
            f"[VectorStore] Loaded from {path}: {len(self.chunks)} chunks, {self.index.ntotal if self.index else 0} vectors"
        )

    @property
    def is_built(self) -> bool:
        return self._built


def create_vector_store(
    paper_sections_path: Path, save_path: Optional[Path] = None
) -> VectorStore:
    """Convenience function to create and optionally save a vector store.

    Args:
        paper_sections_path: Path to paper_sections.json
        save_path: Optional path to save the index

    Returns:
        Built VectorStore instance
    """
    vector_store = VectorStore()
    vector_store.build_from_paper_sections(paper_sections_path)

    if save_path:
        vector_store.save(save_path)

    return vector_store
