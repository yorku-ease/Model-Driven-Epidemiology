"""PDF text extraction pipeline

Extracts clean text from PDF files.
"""

import unicodedata
from pathlib import Path
from typing import Dict, Any


class PDFPipeline:
    """Extract and clean text from PDF files"""

    def __init__(self):
        self.pdf_extractor = None
        self._init_pdf_extractor()

    def _init_pdf_extractor(self):
        """Initialize PDF extraction library"""
        try:
            import pdfplumber

            self.pdf_extractor = "pdfplumber"
            return
        except ImportError:
            pass

        try:
            import PyPDF2

            self.pdf_extractor = "PyPDF2"
            return
        except ImportError:
            pass

        print("Warning: No PDF extraction library found. Install one of:")
        print("  pip install pdfplumber  # Recommended")
        print("  pip install PyPDF2     # Alternative")

    def extract_text(self, pdf_path: str) -> Dict[str, Any]:
        """
        Extract text from PDF file.

        Args:
            pdf_path: Path to PDF file

        Returns:
            Dictionary with text, pages, and metadata
        """
        pdf_path = Path(pdf_path)
        if not pdf_path.exists():
            raise FileNotFoundError(f"PDF file not found: {pdf_path}")

        if self.pdf_extractor == "pdfplumber":
            return self._extract_with_pdfplumber(pdf_path)
        elif self.pdf_extractor == "PyPDF2":
            return self._extract_with_pypdf2(pdf_path)
        else:
            raise RuntimeError("No PDF extraction library available")

    def _extract_with_pdfplumber(self, pdf_path: Path) -> Dict[str, Any]:
        import pdfplumber

        full_text = []
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

                if text:
                    full_text.append(text)

                words = page.extract_words(use_text_flow=True, keep_blank_chars=False)
                pages_data.append(
                    {
                        "page_number": page_num,
                        "text": text,
                        "words": words,
                        "bbox": page.bbox,
                    }
                )

        return {
            "full_text": "\n".join(full_text),
            "pages": pages_data,
            "num_pages": len(pages_data),
            "extraction_method": "pdfplumber",
        }

    def _extract_with_pypdf2(self, pdf_path: Path) -> Dict[str, Any]:
        """Extract text using PyPDF2"""
        import PyPDF2

        full_text = []
        pages_data = []

        with open(pdf_path, "rb") as file:
            pdf_reader = PyPDF2.PdfReader(file)
            for page_num, page in enumerate(pdf_reader.pages, start=1):
                text = page.extract_text()
                if text:
                    full_text.append(text)
                    pages_data.append({"page_number": page_num, "text": text})

        return {
            "full_text": "\n".join(full_text),
            "pages": pages_data,
            "num_pages": len(pages_data),
            "extraction_method": "PyPDF2",
        }

    def clean_text(self, text: str) -> str:
        """
        Clean extracted text.

        - Fix hyphenation
        - Remove page numbers
        - Normalize whitespace
        """
        import re

        text = re.sub(r"(\w+)-\s*\n\s*(\w+)", r"\1\2", text)
        text = re.sub(r"^\s*\d+\s*$", "", text, flags=re.MULTILINE)
        text = re.sub(r"[ \t]+", " ", text)
        text = re.sub(r"\n\s*\n", "\n\n", text)

        return text.strip()

    def extract_full_text(self, pdf_path: str) -> str:
        """
        Extract and clean full text from PDF.

        Args:
            pdf_path: Path to PDF file

        Returns:
            Cleaned text content
        """
        raw_data = self.extract_text(pdf_path)
        full_text = raw_data["full_text"]
        full_text = unicodedata.normalize("NFC", full_text)
        cleaned_text = self.clean_text(full_text)

        return cleaned_text
