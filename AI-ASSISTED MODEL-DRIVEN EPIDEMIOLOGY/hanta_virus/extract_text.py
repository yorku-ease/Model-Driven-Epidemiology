"""Extract text from all hanta virus PDFs using GROBID (reusing phase 2 code)"""
import sys
import json
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent.parent / "phase 2"))

from utils.grobid_sectioner import extract_sections_from_pdf

HERE = Path(__file__).parent
READINGS = HERE / "readings"
OUTPUT = HERE / "outputs"
GROBID_URL = "http://localhost:8070"

def sections_to_text(sections):
    parts = []
    for s in sections:
        heading = s.get("heading", "")
        content = s.get("content", "")
        if heading:
            parts.append(f"{heading}\n\n{content}")
        else:
            parts.append(content)
    return "\n\n".join(parts)

def main():
    OUTPUT.mkdir(parents=True, exist_ok=True)
    pdfs = sorted(READINGS.glob("*.pdf"))
    if not pdfs:
        print("No PDFs found in readings/")
        return

    for pdf in pdfs:
        print(f"[{pdf.name}] Extracting with GROBID...")
        result = extract_sections_from_pdf(str(pdf), GROBID_URL)
        sections = result.get("sections", [])
        full_text = sections_to_text(sections)
        metadata = result.get("metadata", {})
        print(f"  -> {len(sections)} sections, {metadata.get('total_chars', 0)} chars")

        out_path = OUTPUT / f"{pdf.stem}.txt"
        out_path.write_text(full_text, encoding="utf-8")
        print(f"  -> Saved to {out_path}")

if __name__ == "__main__":
    main()
