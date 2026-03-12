"""PDF ingestion pipeline using GROBID and Camelot

Extracts structured sections from PDF using GROBID and tables using Camelot.
"""

import json
import unicodedata
from pathlib import Path
from typing import Dict, List, Any, Optional

from utils.grobid_sectioner import extract_sections_from_pdf
from utils.camelotTableExtraction import CamelotTableExtractor


DEFAULT_GROBID_URL = "http://localhost:8070"


class PDFPipeline:
    """Extract and clean text from PDF files using GROBID + Camelot"""

    def __init__(self, grobid_url: str = DEFAULT_GROBID_URL, check_grobid: bool = True):
        self.grobid_url = grobid_url
        self.check_grobid = check_grobid
        self.table_extractor = CamelotTableExtractor()
        if check_grobid:
            self._check_grobid_available()
        else:
            print(
                f"[PDFPipeline] WARNING: Skipping GROBID availability check. "
                f"Ensure GROBID is running at {self.grobid_url}"
            )

    def _check_grobid_available(self):
        """Check if GROBID server is available"""
        import requests

        try:
            response = requests.get(f"{self.grobid_url}/api/isalive", timeout=5)
            if response.status_code != 200:
                raise RuntimeError(f"GROBID returned status {response.status_code}")
        except Exception as e:
            raise RuntimeError(
                f"GROBID server not available at {self.grobid_url}. "
                "Please start GROBID with: docker run -d --name grobid -p 8070:8070 lfoppiano/grobid:0.8.1"
            ) from e

    def _convert_sections_to_dict(
        self, sections: List[Dict[str, Any]]
    ) -> Dict[str, Any]:
        """Convert grobid section list to dict format expected by pipeline"""
        result = {}
        for i, section in enumerate(sections):
            key = f"section_{i + 1}"
            heading = section.get("heading", "")
            content = section.get("content", "")
            level = section.get("level", 1)
            result[key] = {"heading": heading, "text": content, "level": level}
        return result

    def _build_full_text(self, sections: List[Dict[str, Any]]) -> str:
        """Build full text from grobid sections"""
        texts = []
        for section in sections:
            heading = section.get("heading", "")
            content = section.get("content", "")
            if heading:
                texts.append(f"{heading}\n\n{content}")
            else:
                texts.append(content)
        return "\n\n".join(texts)

    def _extract_pages_with_pdfplumber(self, pdf_path: Path) -> List[Dict[str, Any]]:
        """Extract page-level data using pdfplumber (needed for entity extraction)"""
        import pdfplumber

        pages_data = []
        with pdfplumber.open(pdf_path) as pdf:
            for page_num, page in enumerate(pdf.pages, start=1):
                try:
                    width = page.width
                    height = page.height
                    left = page.crop((0, 0, width / 2, height))
                    right = page.crop((width / 2, 0, width, height))
                    left_text = left.extract_text(x_tolerance=2, y_tolerance=2) or ""
                    right_text = right.extract_text(x_tolerance=2, y_tolerance=2) or ""
                    text = left_text + "\n" + right_text
                except Exception:
                    text = page.extract_text() or ""

                words = page.extract_words(use_text_flow=True, keep_blank_chars=False)
                pages_data.append(
                    {
                        "page_number": page_num,
                        "text": text,
                        "words": words,
                        "bbox": page.bbox,
                    }
                )

        return pages_data

    def process_pdf(
        self, pdf_path: str, output_dir: Optional[str] = None
    ) -> Dict[str, Any]:
        """
        Complete PDF processing pipeline using GROBID + Camelot.

        Args:
            pdf_path: Path to PDF file
            output_dir: Optional output directory for JSON files

        Returns:
            Dictionary with extracted data:
            - full_text: Combined text from all sections
            - sections: Dict of section objects (from GROBID)
            - tables: List of tables (from Camelot)
            - num_pages: Number of pages (if available)
            - extraction_method: "grobid+camelot"
        """
        pdf_path = Path(pdf_path)
        if not pdf_path.exists():
            raise FileNotFoundError(f"PDF file not found: {pdf_path}")

        print(f"[PDFPipeline] Processing {pdf_path} with GROBID...")

        grobid_result = extract_sections_from_pdf(
            str(pdf_path), self.grobid_url, check_server=self.check_grobid
        )
        sections_list = grobid_result.get("sections", [])

        sections_dict = self._convert_sections_to_dict(sections_list)
        full_text = self._build_full_text(sections_list)
        full_text = unicodedata.normalize("NFC", full_text)

        print(
            f"[PDFPipeline] Extracted {len(sections_dict)} sections, {len(sections_list)} total chars"
        )

        print(f"[PDFPipeline] Extracting tables with Camelot...")
        tables = self.table_extractor.extract_tables(str(pdf_path))
        print(f"[PDFPipeline] Found {len(tables)} tables")

        print(f"[PDFPipeline] Extracting page data with pdfplumber...")
        pages_data = self._extract_pages_with_pdfplumber(pdf_path)
        print(f"[PDFPipeline] Extracted {len(pages_data)} pages")

        result = {
            "pdf_path": str(pdf_path),
            "full_text": full_text,
            "sections": sections_dict,
            "sections_list": sections_list,
            "tables": tables,
            "pages": pages_data,
            "num_pages": len(pages_data),
            "extraction_method": "grobid+camelot",
        }

        if output_dir:
            output_path = Path(output_dir)
            output_path.mkdir(parents=True, exist_ok=True)

            with open(output_path / "paper_text.json", "w") as f:
                json.dump(
                    {
                        "full_text": result["full_text"],
                        "num_pages": result["num_pages"],
                    },
                    f,
                    indent=2,
                    ensure_ascii=False,
                )

            with open(output_path / "paper_sections.json", "w") as f:
                json.dump(
                    {"sections": result["sections"], "tables": result["tables"]},
                    f,
                    indent=2,
                    ensure_ascii=False,
                )

        return result


def test_pdf_pipeline(pdf_filename: str, out_path: str = "pipeline_output.json"):
    """Runs the PDF pipeline and writes results to a JSON file."""
    pipeline = PDFPipeline()
    result = pipeline.process_pdf(pdf_filename)

    sections_out = []
    for sec_id, sec in result["sections"].items():
        sections_out.append(
            {
                "id": sec_id,
                "heading": sec.get("heading", ""),
                "text": sec.get("text", ""),
            }
        )

    tables_out = []
    for t in result["tables"]:
        rows, cols = t["shape"]
        tables_out.append(
            {
                "page": t["page"],
                "source": t["source"],
                "table_index": t["table_index"],
                "rows": rows,
                "cols": cols,
                "data": t["data"],
            }
        )

    output = {
        "pdf": result["pdf_path"],
        "num_pages": result["num_pages"],
        "extraction_method": result["extraction_method"],
        "sections": sections_out,
        "tables": tables_out,
    }

    with open(out_path, "w", encoding="utf-8") as f:
        json.dump(output, f, indent=2, ensure_ascii=False)

    print(f"[OK] Pipeline output written to: {out_path}")


if __name__ == "__main__":
    test_pdf_pipeline("zika.pdf", "zika.json")
