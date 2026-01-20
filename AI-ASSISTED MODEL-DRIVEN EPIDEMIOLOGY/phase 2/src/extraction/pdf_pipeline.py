"""PDF ingestion and cleaning pipeline

Extracts text from PDF files, cleans it, detects sections, and extracts tables.
"""

import json
from pathlib import Path
from typing import Dict, List, Any, Optional
import re


class PDFPipeline:
    """Extract and clean text from PDF files"""
    
    def __init__(self):
        self.pdf_extractor = None
        self._init_pdf_extractor()
    
    def _init_pdf_extractor(self):
        """Initialize PDF extraction library"""
        # Try pdfplumber first (preferred)
        try:
            import pdfplumber
            self.pdf_extractor = 'pdfplumber'
            return
        except ImportError:
            pass
        
        # Fallback to PyPDF2
        try:
            import PyPDF2
            self.pdf_extractor = 'PyPDF2'
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
        
        if self.pdf_extractor == 'pdfplumber':
            return self._extract_with_pdfplumber(pdf_path)
        elif self.pdf_extractor == 'PyPDF2':
            return self._extract_with_pypdf2(pdf_path)
        else:
            raise RuntimeError("No PDF extraction library available")
    
    def _extract_with_pdfplumber(self, pdf_path: Path) -> Dict[str, Any]:
        """Extract text using pdfplumber"""
        import pdfplumber
        
        full_text = []
        pages_data = []
        
        with pdfplumber.open(pdf_path) as pdf:
            for page_num, page in enumerate(pdf.pages, start=1):
                text = page.extract_text()
                if text:
                    full_text.append(text)
                    pages_data.append({
                        "page_number": page_num,
                        "text": text,
                        "bbox": page.bbox if hasattr(page, 'bbox') else None
                    })
        
        return {
            "full_text": "\n".join(full_text),
            "pages": pages_data,
            "num_pages": len(pages_data),
            "extraction_method": "pdfplumber"
        }
    
    def _extract_with_pypdf2(self, pdf_path: Path) -> Dict[str, Any]:
        """Extract text using PyPDF2"""
        import PyPDF2
        
        full_text = []
        pages_data = []
        
        with open(pdf_path, 'rb') as file:
            pdf_reader = PyPDF2.PdfReader(file)
            for page_num, page in enumerate(pdf_reader.pages, start=1):
                text = page.extract_text()
                if text:
                    full_text.append(text)
                    pages_data.append({
                        "page_number": page_num,
                        "text": text
                    })
        
        return {
            "full_text": "\n".join(full_text),
            "pages": pages_data,
            "num_pages": len(pages_data),
            "extraction_method": "PyPDF2"
        }
    
    def clean_text(self, text: str) -> str:
        """
        Clean extracted text.
        
        - Remove headers/footers
        - Fix hyphenation
        - Remove page numbers
        - Normalize whitespace
        """
        # Fix hyphenation (words split across lines)
        text = re.sub(r'(\w+)-\s*\n\s*(\w+)', r'\1\2', text)
        
        # Remove standalone page numbers (likely headers/footers)
        text = re.sub(r'^\s*\d+\s*$', '', text, flags=re.MULTILINE)
        
        # Normalize whitespace
        text = re.sub(r'\s+', ' ', text)
        text = re.sub(r'\n\s*\n', '\n\n', text)
        
        return text.strip()
    
    def detect_sections(self, text: str) -> Dict[str, Any]:
        """
        Detect paper sections.
        
        Returns:
            Dictionary with section boundaries and text
        """
        sections = {
            "abstract": None,
            "introduction": None,
            "methods": None,
            "model": None,
            "parameters": None,
            "results": None,
            "discussion": None,
            "references": None
        }
        
        # Enhanced section patterns (more flexible)
        section_patterns = {
            "abstract": [
                r"(?i)^\s*(abstract|summary)\s*$",
                r"(?i)^\s*\d+\.\s*(abstract|summary)\s*$",
                r"(?i)^abstract\s*:",
            ],
            "introduction": [
                r"(?i)^\s*(introduction|background|background\s+and\s+motivation)\s*$",
                r"(?i)^\s*\d+\.\s*(introduction|background)\s*$",
                r"(?i)^introduction\s*:",
            ],
            "methods": [
                r"(?i)^\s*(methods?|methodology|materials\s+and\s+methods|methods\s+and\s+materials)\s*$",
                r"(?i)^\s*\d+\.\s*(methods?|methodology)\s*$",
                r"(?i)^methods?\s*:",
            ],
            "model": [
                r"(?i)^\s*(model|model\s+description|compartmental\s+model|mathematical\s+model|epidemiological\s+model)\s*$",
                r"(?i)^\s*\d+\.\d+\s*(model|compartmental\s+model)\s*$",
                r"(?i)^model\s*:",
                r"(?i)the\s+(seir|sir|sis)\s+model",
            ],
            "parameters": [
                r"(?i)^\s*(parameters?|parameter\s+estimation|parameter\s+values|parameter\s+description)\s*$",
                r"(?i)^\s*\d+\.\d+\s*parameters?\s*$",
                r"(?i)^parameters?\s*:",
                r"(?i)table\s+\d+.*parameters?",
            ],
            "results": [
                r"(?i)^\s*(results?|findings|numerical\s+results)\s*$",
                r"(?i)^\s*\d+\.\s*(results?|findings)\s*$",
                r"(?i)^results?\s*:",
            ],
            "discussion": [
                r"(?i)^\s*(discussion|conclusion|conclusions?|discussion\s+and\s+conclusion)\s*$",
                r"(?i)^\s*\d+\.\s*(discussion|conclusion)\s*$",
                r"(?i)^discussion\s*:",
            ],
            "references": [
                r"(?i)^\s*(references?|bibliography|works?\s+cited)\s*$",
                r"(?i)^\s*references?\s*:",
            ]
        }
        
        lines = text.split('\n')
        current_section = None
        section_start = 0
        
        # Also check for numbered sections (e.g., "2. Model Description")
        for i, line in enumerate(lines):
            line_stripped = line.strip()
            
            # Check all patterns for each section
            for section_name, patterns in section_patterns.items():
                matched = False
                for pattern in patterns:
                    if re.match(pattern, line_stripped) or re.search(pattern, line_stripped):
                        # Found a section header
                        if current_section:
                            sections[current_section] = {
                                "start_line": section_start,
                                "end_line": i,
                                "text": '\n'.join(lines[section_start:i])
                            }
                        current_section = section_name
                        section_start = i + 1
                        matched = True
                        break
                if matched:
                    break
        
        # Add final section
        if current_section:
            sections[current_section] = {
                "start_line": section_start,
                "end_line": len(lines),
                "text": '\n'.join(lines[section_start:])
            }
        
        # Also try to find sections by content keywords if headers not found
        if not any(sections.values()):
            # Look for model section by content
            full_text_lower = text.lower()
            if 'compartmental' in full_text_lower or 'seir' in full_text_lower or 'differential equation' in full_text_lower:
                # Estimate model section (rough approximation)
                model_start = text.lower().find('model')
                if model_start > 0:
                    sections["model"] = {
                        "start_line": text[:model_start].count('\n'),
                        "end_line": text[:model_start + 5000].count('\n'),
                        "text": text[model_start:model_start + 5000]
                    }
        
        return sections
    
    def extract_tables(self, pdf_path: str) -> List[Dict[str, Any]]:
        """
        Extract tables from PDF.
        
        Returns:
            List of tables with data and metadata
        """
        pdf_path = Path(pdf_path)
        tables = []
        
        if self.pdf_extractor == 'pdfplumber':
            import pdfplumber
            with pdfplumber.open(pdf_path) as pdf:
                for page_num, page in enumerate(pdf.pages, start=1):
                    page_tables = page.extract_tables()
                    for table_num, table in enumerate(page_tables, start=1):
                        if table:
                            tables.append({
                                "page_number": page_num,
                                "table_number": table_num,
                                "data": table,
                                "num_rows": len(table),
                                "num_cols": len(table[0]) if table else 0
                            })
        
        return tables
    
    def process_pdf(self, pdf_path: str, output_dir: Optional[str] = None) -> Dict[str, Any]:
        """
        Complete PDF processing pipeline.
        
        Args:
            pdf_path: Path to PDF file
            output_dir: Optional output directory for JSON files
        
        Returns:
            Dictionary with all extracted data
        """
        # Extract text
        raw_data = self.extract_text(pdf_path)
        full_text = raw_data["full_text"]
        
        # Clean text
        cleaned_text = self.clean_text(full_text)
        
        # Detect sections
        sections = self.detect_sections(cleaned_text)
        
        # Extract tables
        tables = self.extract_tables(pdf_path)
        
        # Combine results
        result = {
            "pdf_path": str(pdf_path),
            "full_text": cleaned_text,
            "raw_pages": raw_data["pages"],
            "num_pages": raw_data["num_pages"],
            "sections": sections,
            "tables": tables,
            "extraction_method": raw_data["extraction_method"]
        }
        
        # Save to JSON if output directory specified
        if output_dir:
            output_path = Path(output_dir)
            output_path.mkdir(parents=True, exist_ok=True)
            
            # Save paper_text.json
            with open(output_path / "paper_text.json", 'w') as f:
                json.dump({
                    "full_text": result["full_text"],
                    "raw_pages": result["raw_pages"],
                    "num_pages": result["num_pages"]
                }, f, indent=2)
            
            # Save paper_sections.json
            with open(output_path / "paper_sections.json", 'w') as f:
                json.dump({
                    "sections": result["sections"],
                    "tables": result["tables"]
                }, f, indent=2)
        
        return result
