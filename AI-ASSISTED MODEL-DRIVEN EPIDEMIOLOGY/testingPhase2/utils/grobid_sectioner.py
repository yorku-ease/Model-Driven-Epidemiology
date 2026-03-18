#!/usr/bin/env python3
"""
grobid_sectioner.py
===================
Extracts structured sections from PDF using GROBID.

Returns a dict with:
{
    "sections": [...],  # List of section dicts with keys: heading, content, level, char_count
    "metadata": {...}  # Dict with keys: total_sections, total_chars, sections_summary
}

sections_summary is a list of dicts: [{"name": "Abstract", "char_count": 500}, ...]

Usage:
    from utils.grobid_sectioner import extract_sections_from_pdf

    result = extract_sections_from_pdf("/path/to/paper.pdf")
    # Returns: {"sections": [...], "metadata": {...}}
"""

from pathlib import Path
from typing import List, Dict, Any, Optional
import xml.etree.ElementTree as ET

from grobid_client.grobid_client import GrobidClient

DEFAULT_GROBID_URL = "http://localhost:8070"


def parse_tei_sections(tei_xml: str) -> List[Dict[str, Any]]:
    """
    Parse TEI XML output from GROBID and extract all sections.

    Args:
        tei_xml: TEI XML string from GROBID

    Returns:
        List of dicts with keys: 'heading', 'content', 'level', 'char_count'
    """
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
        section = parse_div(div, ns, level=1)
        if section:
            sections.append(section)

    return sections


def parse_div(div: ET.Element, ns: Dict, level: int = 1) -> Optional[Dict[str, Any]]:
    """Parse a TEI div element into a section dict."""
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


def extract_sections_from_pdf(
    pdf_path: str,
    grobid_url: str = DEFAULT_GROBID_URL,
) -> Dict[str, Any]:
    """
    Extract structured sections from a PDF using GROBID.

    Args:
        pdf_path: Path to the PDF file
        grobid_url: URL of GROBID server (default: http://localhost:8070)

    Returns:
        Dict with keys:
            - sections: List of section dicts (heading, content, level, char_count)
            - metadata: Dict with total_sections, total_chars, sections_summary

        sections_summary is a list of [{"name": str, "char_count": int}, ...]

    Raises:
        FileNotFoundError: If PDF file does not exist
        RuntimeError: If GROBID processing fails
    """
    print(f"[DEBUG] Connecting to GROBID at {grobid_url}...")
    client = GrobidClient(config_path=None, grobid_server=grobid_url)

    print(f"[DEBUG] Processing PDF: {pdf_path}")
    pdf_file = Path(pdf_path)

    if not pdf_file.exists():
        raise FileNotFoundError(f"PDF not found: {pdf_path}")

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

    if result is None or len(result) < 3:
        raise RuntimeError("GROBID returned no result")

    status = result[1]
    tei_xml = result[2]

    print(f"[DEBUG] GROBID status: {status}")

    if status != 200:
        raise RuntimeError(f"GROBID processing failed with status {status}")

    print(f"[DEBUG] Received TEI XML: {len(tei_xml)} characters")
    sections = parse_tei_sections(tei_xml)
    print(f"[DEBUG] Extracted {len(sections)} sections")

    total_chars = sum(s.get("char_count", 0) for s in sections)

    sections_summary = [
        {"name": s["heading"], "char_count": s["char_count"]} for s in sections
    ]

    metadata = {
        "total_sections": len(sections),
        "total_chars": total_chars,
        "sections_summary": sections_summary,
    }

    return {
        "sections": sections,
        "metadata": metadata,
    }


def print_result(result: Dict[str, Any], max_content_chars: int = 500):
    """Pretty print the result for debugging."""
    sections = result.get("sections", [])
    metadata = result.get("metadata", {})

    print("\n" + "=" * 80)
    print("METADATA")
    print("=" * 80)
    print(f"Total sections: {metadata.get('total_sections', 0)}")
    print(f"Total characters: {metadata.get('total_chars', 0)}")
    print("\nSections summary:")
    for s in metadata.get("sections_summary", []):
        print(f"  - {s['name']}: {s['char_count']} chars")

    print("\n" + "=" * 80)
    print("SECTIONS")
    print("=" * 80)

    for i, section in enumerate(sections):
        print(f"\n--- Section {i + 1} ---")
        print(f"Heading: {section['heading']}")
        print(f"Level: {section['level']}")
        print(f"Characters: {section['char_count']}")
        content_preview = section["content"][:max_content_chars]
        if len(section["content"]) > max_content_chars:
            content_preview += "..."
        print(f"Content preview:\n{content_preview}")
        print("-" * 40)


if __name__ == "__main__":
    import argparse
    import json

    parser = argparse.ArgumentParser(
        description="Extract sections from PDF using GROBID"
    )
    parser.add_argument("pdf_path", help="Path to PDF file")
    parser.add_argument(
        "--grobid-url", default=DEFAULT_GROBID_URL, help="GROBID server URL"
    )
    parser.add_argument("--output", help="Output JSON file (optional)")

    args = parser.parse_args()

    result = extract_sections_from_pdf(args.pdf_path, args.grobid_url)

    if args.output:
        output_path = Path(args.output)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        with open(output_path, "w", encoding="utf-8") as f:
            json.dump(result, f, indent=2, ensure_ascii=False)
        print(f"[DEBUG] Saved result to {output_path}")

    print_result(result)
