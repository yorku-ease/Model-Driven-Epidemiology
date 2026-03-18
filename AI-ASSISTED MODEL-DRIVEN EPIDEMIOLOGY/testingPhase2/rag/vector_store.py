"""
vector_store.py
===============
Handles loading FAISS indices and performing similarity searches.

Workflow:
1. Load the FAISS index and chunks for a given document
2. Generate embedding for the query using BioLinkBERT
3. Search FAISS for similar chunks
4. Return the top-k most similar text chunks
"""

import os
import pickle
from pathlib import Path
from typing import List, Tuple, Optional

import numpy as np

# ============================================================================
# QUERY EMBEDDING MODEL
# ============================================================================

MODEL_NAME = "kamalkraj/BioSimCSE-BioLinkBERT-BASE"


def get_query_embedding(query: str) -> np.ndarray:
    """
    Generate embedding for a query string using BioLinkBERT.

    Args:
        query: The user's question/query

    Returns:
        numpy array of shape (1, 768), L2-normalized for cosine similarity
    """
    from sentence_transformers import SentenceTransformer

    print(f"[DEBUG] Loading embedding model: {MODEL_NAME}")
    model = SentenceTransformer(MODEL_NAME)

    print(f"[DEBUG] Generating embedding for query: '{query[:50]}...'")
    embedding = model.encode([query])

    # L2 normalize for cosine similarity
    norms = np.linalg.norm(embedding, axis=1, keepdims=True)
    embedding = embedding / norms

    print(f"[DEBUG] Query embedding shape: {embedding.shape}")
    return embedding.astype("float32")


# ============================================================================
# VECTOR STORE CLASS
# ============================================================================


class VectorStore:
    """
    A class to handle vector similarity search using FAISS.

    Usage:
        store = VectorStore("covid")
        results = store.search("What is the transmission rate?", top_k=5)

        for chunk, score in results:
            print(f"Score: {score:.4f}")
            print(f"Text: {chunk}")
    """

    def __init__(self, doc_name: str, base_path: str = None):
        """
        Initialize the VectorStore for a specific document.

        Args:
            doc_name: Name of the document (should match what was processed)
            base_path: Override base path if needed (optional)

        Raises:
            FileNotFoundError: If the vector database doesn't exist
        """
        self.doc_name = doc_name

        # Determine the vector_db directory
        if base_path is None:
            vector_db_dir = Path(__file__).parent / "vector_db" / doc_name
        else:
            vector_db_dir = Path(base_path) / "vector_db" / doc_name

        # DEBUG
        print(f"[DEBUG] VectorStore for document: {doc_name}")
        print(f"[DEBUG] Vector database path: {vector_db_dir}")

        # Check if the directory exists
        if not vector_db_dir.exists():
            raise FileNotFoundError(
                f"Vector database not found for '{doc_name}'. "
                f"Expected at: {vector_db_dir}. "
                f"Run text_processor.py first to create the index."
            )

        self.vector_db_dir = vector_db_dir

        # Load the FAISS index
        index_file = vector_db_dir / "index.faiss"
        if not index_file.exists():
            raise FileNotFoundError(
                f"FAISS index not found at: {index_file}. "
                f"Make sure text_processor.py ran successfully."
            )

        # Load FAISS index
        try:
            import faiss

            self.index = faiss.read_index(str(index_file))
            # DEBUG
            print(f"[DEBUG] Loaded FAISS index with {self.index.ntotal} vectors")
            print(f"[DEBUG] Index dimension: {self.index.d}")
        except ImportError:
            raise ImportError("faiss not installed. Run: pip install faiss-cpu")

        # Load the text chunks
        chunks_file = vector_db_dir / "chunks.pkl"
        if not chunks_file.exists():
            raise FileNotFoundError(f"Chunks file not found at: {chunks_file}")

        with open(chunks_file, "rb") as f:
            self.chunks = pickle.load(f)

        metadata_file = vector_db_dir / "chunk_metadata.pkl"
        if metadata_file.exists():
            with open(metadata_file, "rb") as f:
                self.chunk_metadata = pickle.load(f)
            print(f"[DEBUG] Loaded {len(self.chunk_metadata)} chunk metadata entries")
        else:
            self.chunk_metadata = None
            print("[DEBUG] No chunk metadata file found")

        # DEBUG
        print(f"[DEBUG] Loaded {len(self.chunks)} text chunks")

        # Load metadata (optional)
        metadata_file = vector_db_dir / "metadata.pkl"
        if metadata_file.exists():
            with open(metadata_file, "rb") as f:
                self.metadata = pickle.load(f)
            # DEBUG
            print(f"[DEBUG] Metadata: {self.metadata}")
        else:
            self.metadata = None

    def search(self, query: str, top_k: int = 5) -> List[Tuple[str, float]]:
        """
        Search for the most similar chunks to the query.

        Args:
            query: The user's question/query
            top_k: Number of results to return (default: 5)

        Returns:
            List of tuples (chunk_text, similarity_score), sorted by score descending

        DEBUG:
            - Print query: print(f"[DEBUG] Searching for: '{query}'")
            - Print scores: print(f"[DEBUG] Top {top_k} scores: {distances}")
            - Print chunk previews: for i, idx in enumerate(ids[0]):
        """
        # DEBUG
        print(f"[DEBUG] Searching for: '{query[:50]}...'")

        # [PLACEHOLDER] Generate query embedding
        query_embedding = get_query_embedding(query)

        if query_embedding is None:
            print("[ERROR] Query embedding generation failed")
            return []

        # Ensure we don't ask for more results than we have chunks
        actual_top_k = min(top_k, len(self.chunks))

        # DEBUG
        print(f"[DEBUG] Query embedding shape: {query_embedding.shape}")
        print(f"[DEBUG] Requesting top_{actual_top_k} results")

        # Search the FAISS index
        # Returns distances (similarity scores) and indices
        distances, indices = self.index.search(query_embedding, actual_top_k)

        # DEBUG
        print(f"[DEBUG] Raw distances: {distances[0]}")
        print(f"[DEBUG] Raw indices: {indices[0]}")

        # Build results list
        results = []
        for i, idx in enumerate(indices[0]):
            if idx >= 0:  # Valid index (FAISS returns -1 for invalid)
                chunk_text = self.chunks[idx]
                score = float(distances[0][i])
                results.append((chunk_text, score))

        # DEBUG: Show results
        print(f"[DEBUG] Found {len(results)} results")
        for i, (chunk, score) in enumerate(results):
            preview = chunk[:100].replace("\n", " ")
            print(f"[DEBUG] Result {i + 1}: score={score:.4f}, text='{preview}...'")

        return results

    def search_with_metadata(
        self, query: str, top_k: int = 5
    ) -> List[Tuple[str, float, dict]]:
        """
        Search for the most similar chunks to the query and return with metadata.

        Args:
            query: The user's question/query
            top_k: Number of results to return (default: 5)

        Returns:
            List of tuples (chunk_text, similarity_score, metadata_dict), sorted by score descending
        """
        print(f"[DEBUG] Searching for: '{query[:50]}...'")

        query_embedding = get_query_embedding(query)

        if query_embedding is None:
            print("[ERROR] Query embedding generation failed")
            return []

        actual_top_k = min(top_k, len(self.chunks))

        print(f"[DEBUG] Query embedding shape: {query_embedding.shape}")
        print(f"[DEBUG] Requesting top_{actual_top_k} results")

        distances, indices = self.index.search(query_embedding, actual_top_k)

        print(f"[DEBUG] Raw distances: {distances[0]}")
        print(f"[DEBUG] Raw indices: {indices[0]}")

        results = []
        for i, idx in enumerate(indices[0]):
            if idx >= 0:
                chunk_text = self.chunks[idx]
                score = float(distances[0][i])

                if self.chunk_metadata and idx < len(self.chunk_metadata):
                    metadata = self.chunk_metadata[idx]
                else:
                    metadata = {}

                results.append((chunk_text, score, metadata))

        print(f"[DEBUG] Found {len(results)} results")
        for i, (chunk, score, metadata) in enumerate(results):
            preview = chunk[:100].replace("\n", " ")
            section = metadata.get("section", "N/A")
            print(
                f"[DEBUG] Result {i + 1}: score={score:.4f}, section='{section}', text='{preview}...'"
            )

        return results

    def get_chunk(self, index: int) -> str:
        """
        Get a specific chunk by its index.

        Args:
            index: The chunk index

        Returns:
            The text chunk at that index
        """
        if 0 <= index < len(self.chunks):
            return self.chunks[index]
        else:
            raise IndexError(f"Chunk index {index} out of range")

    def get_all_chunks(self) -> List[str]:
        """
        Get all text chunks.

        Returns:
            List of all text chunks
        """
        return self.chunks

    def get_num_chunks(self) -> int:
        """
        Get the number of chunks in the store.

        Returns:
            Number of chunks
        """
        return len(self.chunks)


# ============================================================================
# STANDALONE TEST
# ============================================================================

if __name__ == "__main__":
    print("=" * 60)
    print("Testing vector_store.py")
    print("=" * 60)

    # Try to load an existing vector store
    # This will fail if no vector database exists yet
    print("\n[DEBUG] Attempting to load VectorStore...")

    try:
        # Try with a sample doc name - will fail if not processed
        store = VectorStore("test_doc")
        print(f"[DEBUG] Successfully loaded VectorStore")
        print(f"[DEBUG] Number of chunks: {store.get_num_chunks()}")

        # Try a test search
        results = store.search("test query", top_k=3)
        print(f"[DEBUG] Search returned {len(results)} results")

    except FileNotFoundError as e:
        print(f"[DEBUG] Expected error - vector database not created yet:")
        print(f"  {e}")
        print("\n[INFO] Run text_processor.py first to create a vector database")

    print("\n[DEBUG] Test complete!")
