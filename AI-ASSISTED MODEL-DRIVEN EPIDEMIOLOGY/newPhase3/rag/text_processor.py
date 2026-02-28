"""
text_processor.py
=================
Handles loading text documents, chunking them, and preparing them for embedding.

Workflow:
1. Load JSON from ../phase 2/data/{doc_name}.json
2. Extract "full_text" field
3. Split into chunks (by paragraphs with overlap)
4. Generate embeddings using BioLinkBERT
5. Save chunks and FAISS index to vector_db/{doc_name}/

Note: The actual embedding code is commented out - to be implemented later
when the embedding tool is finalized.
"""

import os
import pickle
import math
from pathlib import Path
from typing import List, Tuple

import numpy as np

# ============================================================================
# EMBEDDING MODEL
# ============================================================================

MODEL_NAME = "kamalkraj/BioSimCSE-BioLinkBERT-BASE"
EMBEDDING_DIM = 768


def get_embeddings(texts: List[str]) -> np.ndarray:
    """
    Generate embeddings for a list of text chunks using BioLinkBERT.

    Args:
        texts: List of text strings to embed

    Returns:
        numpy array of shape (len(texts), 768), L2-normalized for cosine similarity
    """
    from sentence_transformers import SentenceTransformer

    print(f"[DEBUG] Loading embedding model: {MODEL_NAME}")
    model = SentenceTransformer(MODEL_NAME)

    print(f"[DEBUG] Generating embeddings for {len(texts)} chunks...")
    embeddings = model.encode(texts, show_progress_bar=True)

    # L2 normalize for cosine similarity (essential for sentence-transformers)
    norms = np.linalg.norm(embeddings, axis=1, keepdims=True)
    embeddings = embeddings / norms

    print(f"[DEBUG] Embedding shape: {embeddings.shape}")
    return embeddings


# ============================================================================
# TEXT CHUNKING FUNCTIONS
# ============================================================================


def chunk_by_paragraphs(
    text: str, min_chunk_size: int = 200, max_chunk_size: int = 1000, overlap: int = 100
) -> List[str]:
    """
    Split text into chunks based on paragraphs.

    Strategy:
    - Split by double newlines (paragraph breaks)
    - Merge small paragraphs together
    - Allow overlap between chunks for context preservation

    Args:
        text: The full text to chunk
        min_chunk_size: Minimum characters for a chunk (default: 200)
        max_chunk_size: Maximum characters for a chunk (default: 1000)
        overlap: Number of characters to overlap between chunks (default: 100)

    Returns:
        List of text chunks

    DEBUG:
        - Check paragraph count: print(f"[DEBUG] Found {len(paragraphs)} paragraphs")
        - Check chunk count: print(f"[DEBUG] Created {len(chunks)} chunks")
    """
    # Split by paragraph (double newline or single newline groups)
    # Using \n\n first, then fallback to single newlines for remaining large blocks
    paragraphs = text.split("\n\n")

    # DEBUG
    # print(f"[DEBUG] Split into {len(paragraphs)} paragraphs")

    chunks = []
    current_chunk = []
    current_size = 0

    for para in paragraphs:
        para = para.strip()
        if not para:
            continue

        para_size = len(para)

        # If single paragraph is too large, split it by sentences
        if para_size > max_chunk_size:
            # First, save current chunk if non-empty
            if current_chunk:
                chunks.append(" ".join(current_chunk))
                # Keep overlap from previous chunk
                prev_chunk = chunks[-1]
                overlap_text = (
                    prev_chunk[-overlap:] if len(prev_chunk) > overlap else prev_chunk
                )
                current_chunk = [overlap_text]
                current_size = len(overlap_text)

            # Split large paragraph by sentences (crude approach)
            sentences = (
                para.replace(". ", ".|")
                .replace("? ", "?|")
                .replace("! ", "!|")
                .split("|")
            )

            for sent in sentences:
                sent = sent.strip()
                if not sent:
                    continue

                sent_size = len(sent)

                if current_size + sent_size > max_chunk_size and current_chunk:
                    chunks.append(" ".join(current_chunk))
                    # Add overlap
                    overlap_text = (
                        chunks[-1][-overlap:]
                        if len(chunks[-1]) > overlap
                        else chunks[-1]
                    )
                    current_chunk = [overlap_text]
                    current_size = len(overlap_text)

                current_chunk.append(sent)
                current_size += sent_size

        elif current_size + para_size > max_chunk_size:
            # Current chunk is full, save it
            if current_chunk:
                chunks.append(" ".join(current_chunk))
                # Keep overlap for next chunk
                overlap_text = (
                    chunks[-1][-overlap:] if len(chunks[-1]) > overlap else chunks[-1]
                )
                current_chunk = [overlap_text]
                current_size = len(overlap_text)

            current_chunk.append(para)
            current_size += para_size

        else:
            current_chunk.append(para)
            current_size += para_size

    # Don't forget the last chunk
    if current_chunk:
        chunks.append(" ".join(current_chunk))

    # DEBUG
    # print(f"[DEBUG] Created {len(chunks)} chunks")
    # for i, chunk in enumerate(chunks[:3]):  # Print first 3 chunks
    #     print(f"[DEBUG] Chunk {i}: {len(chunk)} chars - '{chunk[:100]}...'")

    return chunks


# ============================================================================
# GROBID-BASED SECTION EXTRACTION AND CHUNKING
# ============================================================================

SECTIONS_TO_KEEP = {"abstract", "methods", "results"}
SECTIONS_TO_REMOVE = {
    "references",
    "bibliography",
    "acknowledgements",
    "acknowledgments",
    "author contributions",
    "funding",
    "appendix",
    "supplementary",
    "introduction",
    "discussion",
    "conclusion",
    "keywords",
}


def extract_sections_with_grobid(
    pdf_path: str, grobid_url: str = "http://localhost:8070"
):
    """
    Extract structured sections from PDF using GROBID.

    Args:
        pdf_path: Path to PDF file
        grobid_url: URL of GROBID server

    Returns:
        List of section dicts with keys: heading, content, level, char_count
    """
    import xml.etree.ElementTree as ET
    from grobid_client.grobid_client import GrobidClient

    print(f"[DEBUG] Connecting to GROBID at {grobid_url}...")
    client = GrobidClient(config_path=None, grobid_server=grobid_url)

    print(f"[DEBUG] Processing PDF: {pdf_path}")

    result = client.process_pdf(
        "processFulltextDocument",
        pdf_path,
        generateIDs=True,
        consolidate_header=True,
        consolidate_citations=False,
        include_raw_citations=False,
        include_raw_affiliations=False,
        tei_coordinates=False,
        segment_sentences=False,
    )

    if result is None or len(result) < 3 or result[1] != 200:
        print(
            f"[ERROR] GROBID processing failed with status {result[1] if result else 'None'}"
        )
        return []

    tei_xml = result[2]
    sections = _parse_tei_sections(tei_xml)
    print(f"[DEBUG] Extracted {len(sections)} sections from PDF")
    return sections


def _parse_tei_sections(tei_xml: str):
    """Parse TEI XML output from GROBID."""
    import xml.etree.ElementTree as ET
    from typing import Any

    sections = []

    try:
        root = ET.fromstring(tei_xml)
    except ET.ParseError as e:
        print(f"[ERROR] Failed to parse TEI XML: {e}")
        return sections

    ns = {"tei": "http://www.tei-c.org/ns/1.0"}

    body = root.find(".//tei:body", ns)
    if body is None:
        print("[DEBUG] No body section found in TEI")
        return sections

    for div in body.findall("tei:div", ns):
        section = _parse_div(div, ns, level=1)
        if section:
            sections.append(section)

    return sections


def _parse_div(div, ns: dict, level: int = 1):
    """Parse a TEI div element into a section dict."""
    from typing import Any, Dict

    heading_elem = div.find("tei:head", ns)
    heading = (
        heading_elem.text.strip()
        if heading_elem is not None and heading_elem.text
        else ""
    )

    paragraphs = []
    for p in div.findall("tei:p", ns):
        if p.text:
            paragraphs.append(p.text.strip())

    content = "\n\n".join(paragraphs)

    if not heading and not content:
        return None

    return {
        "heading": heading,
        "content": content,
        "level": level,
        "char_count": len(content),
    }


def filter_sections_for_epidemiology(sections: list) -> list:
    """
    Filter sections for epidemiological model papers.

    Keeps: Abstract, Methods, Results
    Removes: References, Introduction, Discussion, Conclusion, etc.
    """
    filtered = []

    for section in sections:
        heading_lower = section["heading"].lower().strip()

        if heading_lower in SECTIONS_TO_REMOVE:
            print(f"[DEBUG] Removing section: '{section['heading']}'")
            continue

        if heading_lower in SECTIONS_TO_KEEP:
            filtered.append(section)
            print(
                f"[DEBUG] Keeping section: '{section['heading']}' ({section['char_count']} chars)"
            )
            continue

        for remove_term in SECTIONS_TO_REMOVE:
            if remove_term in heading_lower:
                print(
                    f"[DEBUG] Removing section (contains '{remove_term}'): '{section['heading']}'"
                )
                break
        else:
            filtered.append(section)
            print(
                f"[DEBUG] Keeping section: '{section['heading']}' ({section['char_count']} chars)"
            )

    return filtered


def chunk_sections(
    sections: list, max_chunk_size: int = 1000, overlap: int = 100
) -> List[dict]:
    """
    Chunk content from GROBID sections using the paragraph chunking function.

    Args:
        sections: List of section dicts with 'content' key
        max_chunk_size: Maximum characters per chunk
        overlap: Overlap between chunks

    Returns:
        List of dicts with keys: 'text', 'section', 'char_count'
    """
    all_chunks = []

    for section in sections:
        content = section.get("content", "")
        heading = section.get("heading", "Unknown")
        if not content or section.get("char_count", 0) == 0:
            continue

        section_chunks = chunk_by_paragraphs(
            content, min_chunk_size=200, max_chunk_size=max_chunk_size, overlap=overlap
        )

        for chunk in section_chunks:
            all_chunks.append(
                {"text": chunk, "section": heading, "char_count": len(chunk)}
            )

    return all_chunks


def chunk_sections_with_metadata(
    sections: list, max_chunk_size: int = 1000, overlap: int = 100
) -> Tuple[List[str], List[dict]]:
    """
    Chunk content from GROBID sections and return both text and metadata.

    Args:
        sections: List of section dicts with 'content' key
        max_chunk_size: Maximum characters per chunk
        overlap: Overlap between chunks

    Returns:
        Tuple of (list of text strings, list of metadata dicts)
    """
    chunks = chunk_sections(sections, max_chunk_size, overlap)

    texts = [c["text"] for c in chunks]
    metadata = [
        {"section": c["section"], "char_count": c["char_count"]} for c in chunks
    ]

    return texts, metadata


def process_document_with_grobid(
    doc_name: str,
    base_path: str = None,
    grobid_url: str = "http://localhost:8070",
    max_chunk_size: int = 1000,
    filter_sections: bool = False,
) -> bool:
    """
    Process a document using GROBID for section-based chunking.

    This function:
    1. Finds the PDF for the document
    2. Uses GROBID to extract ALL structured sections
    3. Optionally filters sections (keeps Abstract, Methods, Results)
    4. Chunks the content with section metadata
    5. Generates embeddings and creates FAISS index

    Args:
        doc_name: Name of the document (e.g., "cholera_llm_claude_20260212_211525")
        base_path: Override base path if needed
        grobid_url: URL of GROBID server
        max_chunk_size: Maximum chunk size
        filter_sections: If True, filter to keep only Abstract/Methods/Results

    Returns:
        True if processing succeeded, False otherwise
    """
    import json

    if base_path is None:
        project_root = Path(__file__).parent.parent
        pdfs_dir = project_root / "phase 2" / "data" / "papers"
        reports_dir = project_root / "phase 2" / "reports" / doc_name
    else:
        pdfs_dir = Path(base_path) / "papers"
        reports_dir = Path(base_path) / doc_name

    disease_name = doc_name.split("_")[0]
    pdf_file = pdfs_dir / f"{disease_name}.pdf"
    output_dir = Path(__file__).parent / "vector_db" / doc_name

    print(f"[DEBUG] PDF file: {pdf_file}")
    print(f"[DEBUG] Output directory: {output_dir}")

    if not pdf_file.exists():
        print(f"[ERROR] PDF not found: {pdf_file}")
        return False

    output_dir.mkdir(parents=True, exist_ok=True)

    print("[DEBUG] Extracting sections with GROBID...")
    sections = extract_sections_with_grobid(str(pdf_file), grobid_url)

    if not sections:
        print("[ERROR] No sections extracted from PDF")
        return False

    if filter_sections:
        print(f"\n[DEBUG] Filtering sections for epidemiology papers...")
        sections = filter_sections_for_epidemiology(sections)
        print(f"[DEBUG] Sections after filtering: {len(sections)}")

    print(
        f"\n[DEBUG] Chunking {len(sections)} sections (all sections, no filtering)..."
    )
    chunk_data = chunk_sections(sections, max_chunk_size=max_chunk_size)
    print(f"[DEBUG] Created {len(chunk_data)} chunks")

    texts = [c["text"] for c in chunk_data]
    chunk_metadata = [{"section": c["section"]} for c in chunk_data]

    chunks_file = output_dir / "chunks.pkl"
    with open(chunks_file, "wb") as f:
        pickle.dump(texts, f)
    print(f"[DEBUG] Saved chunks to {chunks_file}")

    metadata_file = output_dir / "chunk_metadata.pkl"
    with open(metadata_file, "wb") as f:
        pickle.dump(chunk_metadata, f)
    print(f"[DEBUG] Saved chunk metadata to {metadata_file}")

    print("[DEBUG] Generating embeddings...")
    embeddings = get_embeddings(texts)

    if embeddings is None:
        print("[WARNING] Embedding generation failed")
        return False

    try:
        import faiss

        embeddings = embeddings.astype("float32")
        dim = embeddings.shape[1]

        index = faiss.IndexFlatIP(dim)
        index.add(embeddings)

        index_file = output_dir / "index.faiss"
        faiss.write_index(index, str(index_file))
        print(f"[DEBUG] Saved FAISS index to {index_file}")

        metadata = {
            "doc_name": doc_name,
            "num_chunks": len(texts),
            "embedding_dim": dim,
            "method": "grobid_sectioning",
            "filter_sections": filter_sections,
        }
        metadata_file = output_dir / "metadata.pkl"
        with open(metadata_file, "wb") as f:
            pickle.dump(metadata, f)

        return True

    except Exception as e:
        print(f"[ERROR] Failed to create FAISS index: {e}")
        return False


# ============================================================================
# MAIN PROCESSING FUNCTION
# ============================================================================


def process_document(doc_name: str, base_path: str = None) -> bool:
    """
    Main function to process a document: load JSON, extract full_text, chunk, embed, and save.

    Args:
        doc_name: Name of the document (without extension)
                  Will look for ../phase 2/data/{doc_name}.json
        base_path: Override base path if needed (optional)

    Returns:
        True if processing succeeded, False otherwise
    """

    import json

    # Determine paths
    if base_path is None:
        project_root = Path(__file__).parent.parent
        data_dir = project_root / "phase 2" / "reports" / doc_name
    else:
        data_dir = Path(base_path)

    input_file = data_dir / "paper_text.json"
    output_dir = Path(__file__).parent / "vector_db" / doc_name

    # DEBUG: Show paths
    print(f"[DEBUG] Input file: {input_file}")
    print(f"[DEBUG] Output directory: {output_dir}")

    # Check if input file exists
    if not input_file.exists():
        print(f"[ERROR] Input file not found: {input_file}")
        print(f"[DEBUG] Files in reports dir: {list(data_dir.glob('*'))}")
        return False

    # Create output directory if it doesn't exist
    output_dir.mkdir(parents=True, exist_ok=True)

    # Load the JSON file
    print(f"[DEBUG] Loading JSON from {input_file}...")
    with open(input_file, "r", encoding="utf-8") as f:
        data = json.load(f)

    # Extract full_text from JSON
    if "full_text" not in data:
        print(f"[ERROR] 'full_text' key not found in JSON")
        print(f"[DEBUG] Available keys: {list(data.keys())}")
        return False

    text = data["full_text"]
    print(f"[DEBUG] Extracted full_text: {len(text)} characters")

    print(f"[DEBUG] Loaded {len(text)} characters")

    # Chunk the text
    print("[DEBUG] Chunking text by paragraphs...")
    chunks = chunk_by_paragraphs(text)
    print(f"[DEBUG] Created {len(chunks)} chunks")

    # Save chunks for later retrieval
    chunks_file = output_dir / "chunks.pkl"
    with open(chunks_file, "wb") as f:
        pickle.dump(chunks, f)
    print(f"[DEBUG] Saved chunks to {chunks_file}")

    # [PLACEHOLDER] Generate embeddings
    # TODO: Uncomment when embedding tool is ready
    print("[DEBUG] Generating embeddings (placeholder)...")
    embeddings = get_embeddings(chunks)

    if embeddings is None:
        print("[WARNING] Embedding generation returned None - skipping FAISS creation")
        print("[DEBUG] Only chunks.pkl was saved, no vector index created")
        return False

    # Create FAISS index
    # NOTE: FAISS requires numpy. Install with: pip install numpy faiss-cpu (or faiss-gpu)
    try:
        import numpy as np
        import faiss

        # DEBUG: Check embedding shape
        print(f"[DEBUG] Embedding shape: {embeddings.shape}")

        # Convert to float32 (FAISS requirement)
        embeddings = embeddings.astype("float32")

        # Get embedding dimension
        dim = embeddings.shape[1]
        print(f"[DEBUG] Embedding dimension: {dim}")

        # Create FAISS index
        # Using IndexFlatIP for inner product (cosine similarity with normalized vectors)
        index = faiss.IndexFlatIP(dim)

        # Add vectors to index
        index.add(embeddings)

        # DEBUG: Check index stats
        print(f"[DEBUG] Index total dimension: {index.d}")
        print(f"[DEBUG] Index total vectors: {index.ntotal}")

        # Save the FAISS index
        index_file = output_dir / "index.faiss"
        faiss.write_index(index, str(index_file))
        print(f"[DEBUG] Saved FAISS index to {index_file}")

        # Save metadata
        metadata = {
            "doc_name": doc_name,
            "num_chunks": len(chunks),
            "embedding_dim": dim,
        }
        metadata_file = output_dir / "metadata.pkl"
        with open(metadata_file, "wb") as f:
            pickle.dump(metadata, f)
        print(f"[DEBUG] Saved metadata to {metadata_file}")

        return True

    except ImportError as e:
        print(f"[ERROR] Missing dependency: {e}")
        print("[DEBUG] Install faiss with: pip install faiss-cpu")
        return False
    except Exception as e:
        print(f"[ERROR] Failed to create FAISS index: {e}")
        import traceback

        traceback.print_exc()
        return False


# ============================================================================
# STANDALONE TEST
# ============================================================================

if __name__ == "__main__":
    # Test with a sample document
    print("=" * 60)
    print("Testing text_processor.py")
    print("=" * 60)

    # First, let's see what .json files exist
    project_root = Path(__file__).parent.parent
    data_dir = project_root / "phase 2" / "data"
    print(f"\n[DEBUG] Checking for .json files in {data_dir}...")
    json_files = list(data_dir.glob("*.json"))

    if json_files:
        print(f"[DEBUG] Found {len(json_files)} .json files:")
        for f in json_files:
            print(f"  - {f.name}")
    else:
        print("[DEBUG] No .json files found")
        print(
            "[DEBUG] Create a test file in ../phase 2/data/test.json with 'full_text' field"
        )

    # Test chunking function with sample text
    sample_text = """
This is the first paragraph. It contains some introductory text.

This is the second paragraph. It has multiple sentences. This is another sentence in the second paragraph.

This is the third paragraph, which is much longer. It contains a lot of information about epidemiology and disease modeling. The text discusses various aspects of computational epidemiology, including agent-based models, differential equations, and machine learning approaches. This paragraph is intentionally long to test the chunking algorithm's ability to split large paragraphs.
"""

    print("\n[DEBUG] Testing chunk_by_paragraphs()...")
    test_chunks = chunk_by_paragraphs(sample_text)
    print(f"[DEBUG] Created {len(test_chunks)} chunks from sample text:")
    for i, chunk in enumerate(test_chunks):
        print(f"  Chunk {i}: {len(chunk)} chars - '{chunk[:50]}...'")

    print("\n[DEBUG] Test complete!")
