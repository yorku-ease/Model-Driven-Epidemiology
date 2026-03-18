"""
Error Memory for Intelligent Repair

Stores and retrieves past repair attempts (successes and failures) for smarter LLM repair.
- Exact match on error signature for same errors across runs
- Semantic search for similar errors (same type, different element, same underlying cause)
- Append-only for consistency between JSON and FAISS
"""

import json
import uuid
from dataclasses import dataclass
from datetime import datetime
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple

import faiss
import numpy as np
from sentence_transformers import SentenceTransformer


class ConsistencyError(Exception):
    """Raised when JSON and FAISS index are out of sync."""

    pass


@dataclass
class MemoryEntry:
    """Single entry in error memory."""

    id: str
    disease: str
    error_type: str
    element: str
    outcome: str  # "success" or "failure"
    failure_reason: Optional[str]
    llm_explanation: Optional[str]
    search_query_used: Optional[str]
    fix_summary: Optional[str]
    paper_evidence_chunk: Optional[str]
    timestamp: str

    def to_dict(self) -> Dict[str, Any]:
        return {
            "id": self.id,
            "disease": self.disease,
            "error_type": self.error_type,
            "element": self.element,
            "outcome": self.outcome,
            "failure_reason": self.failure_reason,
            "llm_explanation": self.llm_explanation,
            "search_query_used": self.search_query_used,
            "fix_summary": self.fix_summary,
            "paper_evidence_chunk": self.paper_evidence_chunk,
            "timestamp": self.timestamp,
        }

    @classmethod
    def from_dict(cls, d: Dict[str, Any]) -> "MemoryEntry":
        return cls(
            id=d["id"],
            disease=d.get("disease", "unknown"),
            error_type=d["error_type"],
            element=d["element"],
            outcome=d["outcome"],
            failure_reason=d.get("failure_reason"),
            llm_explanation=d.get("llm_explanation"),
            search_query_used=d.get("search_query_used"),
            fix_summary=d.get("fix_summary"),
            paper_evidence_chunk=d.get("paper_evidence_chunk"),
            timestamp=d["timestamp"],
        )

    def to_embed_string(self) -> str:
        """Convert to string for embedding."""
        parts = [
            f"error_type: {self.error_type}",
            f"element: {self.element}",
            f"outcome: {self.outcome}",
        ]
        if self.failure_reason:
            parts.append(f"failure_reason: {self.failure_reason}")
        if self.llm_explanation:
            parts.append(f"explanation: {self.llm_explanation}")
        if self.search_query_used:
            parts.append(f"search_query_used: {self.search_query_used}")
        if self.fix_summary:
            parts.append(f"fix_summary: {self.fix_summary}")
        return " ".join(parts)


@dataclass
class MemorySearchResult:
    """Result of a memory search."""

    entries: List[MemoryEntry]
    scores: List[float]
    match_type: str  # "exact", "semantic", or "none"


class ErrorMemory:
    """Manages error log memory with JSON + FAISS storage."""

    DEFAULT_MODEL = "all-MiniLM-L6-v2"
    DIMENSION = 384

    def __init__(
        self,
        storage_dir: Path,
        disease: str = "unknown",
        model_name: str = DEFAULT_MODEL,
    ):
        self.storage_dir = Path(storage_dir)
        self.disease = disease
        self.entries: List[MemoryEntry] = []
        self.exact_index: Dict[str, int] = {}  # "type|element" -> entry index
        self.faiss_index: Optional[faiss.Index] = None
        self.embedding_model = SentenceTransformer(model_name)
        self._loaded = False

    def _get_json_path(self) -> Path:
        return self.storage_dir / "error_log.json"

    def _get_faiss_path(self) -> Path:
        return self.storage_dir / "error_log_vector_store"

    def _ensure_storage_dir(self) -> None:
        self.storage_dir.mkdir(parents=True, exist_ok=True)
        self._get_faiss_path().mkdir(parents=True, exist_ok=True)

    def load(self) -> bool:
        """Load memory from disk. Returns True if loaded, False if no memory exists."""
        json_path = self._get_json_path()
        faiss_path = self._get_faiss_path()

        if not json_path.exists():
            self._loaded = True
            return False

        try:
            with open(json_path, "r", encoding="utf-8") as f:
                data = json.load(f)

            self.entries = [MemoryEntry.from_dict(e) for e in data.get("entries", [])]

            self.exact_index = {}
            for i, entry in enumerate(self.entries):
                key = f"{entry.error_type}|{entry.element}"
                self.exact_index[key] = i

            faiss_index_path = faiss_path / "faiss_index.bin"
            if faiss_index_path.exists():
                self.faiss_index = faiss.read_index(str(faiss_index_path))

            if self.faiss_index:
                self._verify_consistency()

            self._loaded = True
            print(f"[ErrorMemory] Loaded {len(self.entries)} entries")
            return True

        except Exception as e:
            print(f"[ErrorMemory] Warning: Failed to load memory: {e}")
            self.entries = []
            self.exact_index = {}
            self.faiss_index = None
            self._loaded = True
            return False

    def _verify_consistency(self) -> None:
        """Verify JSON entries and FAISS index are in sync."""
        if self.faiss_index is None:
            return

        faiss_count = self.faiss_index.ntotal
        json_count = len(self.entries)

        if faiss_count != json_count:
            raise ConsistencyError(
                f"JSON has {json_count} entries but FAISS has {faiss_count} vectors"
            )

    def save(self) -> None:
        """Save memory to disk. JSON first, then FAISS."""
        self._ensure_storage_dir()

        json_path = self._get_json_path()
        with open(json_path, "w", encoding="utf-8") as f:
            json.dump(
                {
                    "version": 2,
                    "disease": self.disease,
                    "entries": [e.to_dict() for e in self.entries],
                },
                f,
                indent=2,
                ensure_ascii=False,
            )

        if self.faiss_index:
            faiss_path = self._get_faiss_path()
            faiss.write_index(self.faiss_index, str(faiss_path / "faiss_index.bin"))

        print(f"[ErrorMemory] Saved {len(self.entries)} entries")

    def search(
        self,
        error_type: str,
        element: str,
        semantic_threshold: float = 0.82,
        max_results: int = 3,
        min_entries_for_semantic: int = 10,
    ) -> MemorySearchResult:
        """Search memory: exact match first, semantic fallback.

        Args:
            error_type: Type of error
            element: Element causing the error
            semantic_threshold: Minimum cosine similarity for semantic match
            max_results: Maximum number of semantic results
            min_entries_for_semantic: Minimum entries before using semantic search

        Returns:
            MemorySearchResult with entries, scores, and match type
        """
        exact_key = f"{error_type}|{element}"

        if exact_key in self.exact_index:
            idx = self.exact_index[exact_key]
            entry = self.entries[idx]
            return MemorySearchResult(
                entries=[entry],
                scores=[1.0],
                match_type="exact",
            )

        if not self.faiss_index or len(self.entries) < min_entries_for_semantic:
            return MemorySearchResult(entries=[], scores=[], match_type="none")

        query_entry = MemoryEntry(
            id="",
            disease=self.disease,
            error_type=error_type,
            element=element,
            outcome="",
            failure_reason=None,
            llm_explanation=None,
            search_query_used=None,
            fix_summary=None,
            paper_evidence_chunk=None,
            timestamp="",
        )

        query_emb = self.embedding_model.encode([query_entry.to_embed_string()])
        self._normalize(query_emb)

        search_k = min(max_results + 5, self.faiss_index.ntotal)
        scores, idxs = self.faiss_index.search(query_emb.astype(np.float32), search_k)

        results = []
        result_scores = []
        for score, idx in zip(scores[0], idxs[0]):
            if idx < 0:
                continue
            if score < semantic_threshold:
                continue
            results.append(self.entries[idx])
            result_scores.append(float(score))

            if len(results) >= max_results:
                break

        if results:
            return MemorySearchResult(
                entries=results,
                scores=result_scores,
                match_type="semantic",
            )

        return MemorySearchResult(entries=[], scores=[], match_type="none")

    def add_entry(
        self,
        error_type: str,
        element: str,
        outcome: str,
        failure_reason: Optional[str] = None,
        llm_explanation: Optional[str] = None,
        search_query_used: Optional[str] = None,
        fix_summary: Optional[str] = None,
        paper_evidence_chunk: Optional[str] = None,
    ) -> MemoryEntry:
        """Add a new entry to memory (append-only)."""
        entry = MemoryEntry(
            id=str(uuid.uuid4()),
            disease=self.disease,
            error_type=error_type,
            element=element,
            outcome=outcome,
            failure_reason=failure_reason,
            llm_explanation=llm_explanation,
            search_query_used=search_query_used,
            fix_summary=fix_summary,
            paper_evidence_chunk=paper_evidence_chunk,
            timestamp=datetime.now().isoformat(),
        )

        idx = len(self.entries)
        self.entries.append(entry)
        self.exact_index[f"{error_type}|{element}"] = idx

        emb = self.embedding_model.encode([entry.to_embed_string()])
        self._normalize(emb)

        if self.faiss_index is None:
            self.faiss_index = faiss.IndexFlatIP(self.DIMENSION)

        self.faiss_index.add(emb.astype(np.float32))

        self.save()

        return entry

    def add_success(
        self,
        error_type: str,
        element: str,
        search_query_used: Optional[str],
        fix_summary: str,
        paper_evidence_chunk: Optional[str] = None,
    ) -> MemoryEntry:
        """Add a successful repair entry."""
        return self.add_entry(
            error_type=error_type,
            element=element,
            outcome="success",
            search_query_used=search_query_used,
            fix_summary=fix_summary,
            paper_evidence_chunk=paper_evidence_chunk,
        )

    def add_failure(
        self,
        error_type: str,
        element: str,
        failure_reason: str,
        llm_explanation: str,
    ) -> MemoryEntry:
        """Add a failed repair entry."""
        return self.add_entry(
            error_type=error_type,
            element=element,
            outcome="failure",
            failure_reason=failure_reason,
            llm_explanation=llm_explanation,
        )

    def _normalize(self, embeddings: np.ndarray) -> None:
        """Normalize vectors for cosine similarity."""
        norms = np.linalg.norm(embeddings, axis=1, keepdims=True)
        norms = np.where(norms == 0, 1, norms)
        embeddings[:] = embeddings / norms

    def format_for_prompt(
        self, result: MemorySearchResult, max_entries: int = 3
    ) -> str:
        """Format memory search result for LLM prompt."""
        if not result.entries:
            return "No previous repair attempts for this error type."

        lines = []
        for i, (entry, score) in enumerate(
            zip(result.entries[:max_entries], result.scores[:max_entries]), 1
        ):
            lines.append(
                f"--- Past Attempt {i} ({entry.outcome}, similarity: {score:.2f}) ---"
            )
            lines.append(f"Element: {entry.element}")
            lines.append(f"Outcome: {entry.outcome}")

            if entry.outcome == "success":
                if entry.search_query_used:
                    lines.append(f"Successful search query: {entry.search_query_used}")
                if entry.fix_summary:
                    lines.append(f"Fix applied: {entry.fix_summary}")
                if entry.paper_evidence_chunk:
                    chunk = (
                        entry.paper_evidence_chunk[:300] + "..."
                        if len(entry.paper_evidence_chunk) > 300
                        else entry.paper_evidence_chunk
                    )
                    lines.append(f"Evidence: {chunk}")
            else:
                if entry.failure_reason:
                    lines.append(f"Failure reason: {entry.failure_reason}")
                if entry.llm_explanation:
                    explanation = (
                        entry.llm_explanation[:300] + "..."
                        if len(entry.llm_explanation) > 300
                        else entry.llm_explanation
                    )
                    lines.append(f"LLM explanation: {explanation}")

            lines.append("")

        return "\n".join(lines)

    @property
    def has_memory(self) -> bool:
        return len(self.entries) > 0

    @property
    def entry_count(self) -> int:
        return len(self.entries)
