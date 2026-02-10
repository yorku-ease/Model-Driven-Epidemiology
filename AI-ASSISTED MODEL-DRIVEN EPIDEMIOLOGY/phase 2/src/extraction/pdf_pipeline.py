"""PDF ingestion and cleaning pipeline

Extracts text from PDF files, cleans it, detects sections, and extracts tables.
"""

import json
from pathlib import Path
from typing import Dict, List, Any, Optional
import re
import unicodedata


class PDFPipeline:
    """Extract and clean text from PDF files"""
    #SECTION_ALIASES = {
    #    "abstract": ["abstract"],
    #    "introduction": ["introduction", "background"],
    #    "methods": ["methods", "methodology", "materials"],
    #    "model": ["model", "model description"],
    #    "results": ["results", "numerical results"]
    #}

    HEADING_REGEXES = [
        r'^\s*\d+(\.\d+)*\s+[A-Z][A-Za-z ,\-]{3,}$',  # 1 Introduction / 2.1 Model formulation
        r'^[A-Z][A-Z \-]{4,}$',                       # ALL CAPS headings
        r'^[A-Z][A-Za-z ]{3,}$',                      # Capitalized short line
    ]


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
    #Check if the provided text is bold
    def _is_bold_font(self, fontname: str) -> bool:
        f = fontname.lower()
        return any(k in f for k in ["bold", "semibold", "black", "heavy"])

    def _looks_like_numbered_heading(self, text: str) -> bool:
        t = text.strip()

        # Examples: "1 Introduction", "2. Model formulation", "3 Results"
        return bool(re.match(r'^\d+(\.\d+)?\s+[A-Z][A-Za-z ]{3,}$', t))

    #Looks for heading based on font size and boldness
    def _extract_headings(self, page, min_font_size=11):
        words = page.extract_words(
            use_text_flow=True,
            keep_blank_chars=False
        )

        lines = {}
        for w in words:
            y = round(w["top"], 1)
            lines.setdefault(y, []).append(w)

        headings = []

        for y, line_words in lines.items():
            text = " ".join(w["text"] for w in line_words)
            sizes = [self._get_font_size(w) for w in line_words]
            max_size = max(sizes) if sizes else 0.0
            bold = any(
                self._is_bold_font(w.get("fontname", ""))
                for w in line_words
            )

            is_heading = (
                len(text) >= 4 and
                (
                    (max_size >= min_font_size and max_size > 0) or
                    (bold and max_size >= min_font_size - 1)
                )
            )

            # Skip pure page numbers
            if text.strip().isdigit():
                continue
            
            if (
                is_heading or
                self._looks_like_numbered_heading(text)
            ):
                headings.append({
                    "text": text.strip(),
                    "top": y,
                    "font_size": max_size,
                    "bold": bold
                })

        return headings
    #Line by line heading detector
    def _is_heading_line(self, text: str) -> bool:
        t = text.strip()
        if len(t) < 4 or len(t) > 120:
            return False

        for pat in self.HEADING_REGEXES:
            if re.match(pat, t):
                return True

        return False

    def _get_font_size(self, w, default=0.0):
        try:
            return float(w.get("size", default))
        except Exception:
            return default


    def _is_valid_table(self, table):
        if not table or len(table) < 2:
            return False

        row_lengths = [len(row) for row in table if row]
        if not row_lengths:
            return False

        return len(set(row_lengths)) == 1
    
    
    def coalesce_short_sections(self, sections: dict, min_paragraphs: int = 2):
        """
        Merge sections with too little content into the previous section.
        Order-preserving and lossless.
        """

        def paragraph_count(text: str) -> int:
            if not text:
                return 0
            return len([p for p in text.split("\n\n") if p.strip()])

        merged = []
        items = list(sections.items())

        for sec_id, sec in items:
            if isinstance(sec, dict):
                heading = sec.get("heading", "")
                text = sec.get("text", "")
            else:
                heading = ""
                text = sec

            if not merged:
                merged.append({
                    "heading": heading,
                    "text": text
                })
                continue

            if paragraph_count(text) < min_paragraphs:
                # merge into previous
                prev = merged[-1]
                if heading:
                    prev["text"] += f"\n\n[{heading}]\n"
                if text:
                    prev["text"] += text
            else:
                merged.append({
                    "heading": heading,
                    "text": text
                })

        # reassign ids
        return {
            f"section_{i+1}": sec
            for i, sec in enumerate(merged)
        }



    def _fallback_sections_from_text(self, pages):
        """
        Regex/content-based fallback section detection.
        Always returns something.
        """
        full_text = "\n".join(p["text"] or "" for p in pages)
        text_lower = full_text.lower()

        sections = {}

        def extract_between(start_keys, end_keys=None):
            for k in start_keys:
                idx = text_lower.find(k)
                if idx != -1:
                    start = idx
                    end = None
                    if end_keys:
                        for ek in end_keys:
                            eidx = text_lower.find(ek, start + 50)
                            if eidx != -1:
                                end = eidx
                                break
                    return full_text[start:end].strip()
            return None

        # Abstract
        sections["abstract"] = extract_between(
            ["abstract"],
            ["introduction"]
        )

        # Introduction
        sections["introduction"] = extract_between(
            ["introduction"],
            ["methods", "model", "materials"]
        )

        # Methods / Model
        sections["methods"] = extract_between(
            ["methods", "methodology", "model"],
            ["results"]
        )

        # Results
        sections["results"] = extract_between(
            ["results", "numerical results"],
            None
        )

        if not sections:
            return {
                "content": "\n\n".join(
                    f"[PAGE {p['page_number']}]\n{p['text']}"
                    for p in pages if p.get("text")
                )
            }

        # Remove empty sections
        return {k: v for k, v in sections.items() if v}
    

    #def _classify_heading(self, text: str):
    #    t = re.sub(r'[^a-z0-9 ]', '', text.lower())
    #    for section, aliases in self.SECTION_ALIASES.items():
    #        if any(a in t for a in aliases):
    #            return section
    #    return None
    def _extract_headings_from_text(self, text: str):
        headings = []
        lines = text.splitlines()

        for i, line in enumerate(lines):
            if self._is_heading_line(line):
                headings.append({
                    "text": line.strip(),
                    "line_index": i
                })

        return headings

    def detect_sections_from_text(self, full_text: str):
        lines = full_text.splitlines()
        headings = []

        for i, line in enumerate(lines):
            if self._is_heading_line(line):
                headings.append((i, line.strip()))

        # Fallback: no headings → single section
        if not headings:
            return {
                "section_1": full_text.strip()
            }

        sections = {}

        for idx, (start_i, title) in enumerate(headings):
            end_i = headings[idx + 1][0] if idx + 1 < len(headings) else len(lines)
            section_text = "\n".join(lines[start_i + 1:end_i]).strip()

            sections[f"section_{idx + 1}"] = {
                "heading": title,
                "text": section_text
            }

        return sections




    def _extract_with_pdfplumber(self, pdf_path: Path) -> Dict[str, Any]:
        import pdfplumber

        full_text = []
        pages_data = []

        with pdfplumber.open(pdf_path) as pdf:
            for page_num, page in enumerate(pdf.pages, start=1):
                width = page.width
                height = page.height

                left = page.crop((0, 0, width / 2, height))
                right = page.crop((width / 2, 0, width, height))

                left_text = left.extract_text(x_tolerance=2, y_tolerance=2) or ""
                right_text = right.extract_text(x_tolerance=2, y_tolerance=2) or ""

                text = left_text + "\n" + right_text

                headings = self._extract_headings(page)

                if text:
                    full_text.append(text)

                words = page.extract_words(
                    use_text_flow=True,
                    keep_blank_chars=False
                )
                pages_data.append({
                    "page_number": page_num,
                    "text": text,
                    "words": words,
                    "headings": headings,
                    "bbox": page.bbox
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
        
        # Normalize whitespace but preserve newlines
        text = re.sub(r'[ \t]+', ' ', text)
        text = re.sub(r'\n\s*\n', '\n\n', text)
        
        return text.strip()
    
    #OLD SECTION DETECTION LOGIC
    #def detect_sections(self, text: str) -> Dict[str, Any]:
    #    """
    #    Detect paper sections.
    #    
    #    Returns:
    #        Dictionary with section boundaries and text
    #    """
    #    sections = {
    #        "abstract": None,
    #        "introduction": None,
    #        "methods": None,
    #        "model": None,
    #        "parameters": None,
    #        "results": None,
    #        "discussion": None,
    #        "references": None
    #    }
    #    
    #    # Enhanced section patterns (more flexible)
    #    section_patterns = {
    #        "abstract": [
    #            r"(?i)^\s*(abstract|summary)\s*$",
    #            r"(?i)^\s*\d+\.\s*(abstract|summary)\s*$",
    #            r"(?i)^abstract\s*:",
    #        ],
    #        "introduction": [
    #            r"(?i)^\s*(introduction|background|background\s+and\s+motivation)\s*$",
    #            r"(?i)^\s*\d+\.\s*(introduction|background)\s*$",
    #            r"(?i)^introduction\s*:",
    #        ],
    #        "methods": [
    #            r"(?i)^\s*(methods?|methodology|materials\s+and\s+methods|methods\s+and\s+materials)\s*$",
    #            r"(?i)^\s*\d+\.\s*(methods?|methodology)\s*$",
    #            r"(?i)^methods?\s*:",
    #        ],
    #        "model": [
    #            r"(?i)^\s*(model|model\s+description|compartmental\s+model|mathematical\s+model|epidemiological\s+model)\s*$",
    #            r"(?i)^\s*\d+\.\d+\s*(model|compartmental\s+model)\s*$",
    #            r"(?i)^model\s*:",
    #            r"(?i)the\s+(seir|sir|sis)\s+model",
    #        ],
    #        "parameters": [
    #            r"(?i)^\s*(parameters?|parameter\s+estimation|parameter\s+values|parameter\s+description)\s*$",
    #            r"(?i)^\s*\d+\.\d+\s*parameters?\s*$",
    #            r"(?i)^parameters?\s*:",
    #            r"(?i)table\s+\d+.*parameters?",
    #        ],
    #        "results": [
    #            r"(?i)^\s*(results?|findings|numerical\s+results)\s*$",
    #            r"(?i)^\s*\d+\.\s*(results?|findings)\s*$",
    #            r"(?i)^results?\s*:",
    #        ],
    #        "discussion": [
    #            r"(?i)^\s*(discussion|conclusion|conclusions?|discussion\s+and\s+conclusion)\s*$",
    #            r"(?i)^\s*\d+\.\s*(discussion|conclusion)\s*$",
    #            r"(?i)^discussion\s*:",
    #        ],
    #        "references": [
    #            r"(?i)^\s*(references?|bibliography|works?\s+cited)\s*$",
    #            r"(?i)^\s*references?\s*:",
    #        ]
    #    }
    #    
    #    lines = text.split('\n')
    #    current_section = None
    #    section_start = 0
    #    
    #    # Also check for numbered sections (e.g., "2. Model Description")
    #    for i, line in enumerate(lines):
    #        line_stripped = line.strip()
    #        
    #        # Check all patterns for each section
    #        for section_name, patterns in section_patterns.items():
    #            matched = False
    #            for pattern in patterns:
    #                if re.match(pattern, line_stripped) or re.search(pattern, line_stripped):
    #                    # Found a section header
    #                    if current_section:
    #                        sections[current_section] = {
    #                            "start_line": section_start,
    #                            "end_line": i,
    #                            "text": '\n'.join(lines[section_start:i])
    #                        }
    #                    current_section = section_name
    #                    section_start = i + 1
    #                    matched = True
    #                    break
    #            if matched:
    #                break
    #    
    #    # Add final section
    #    if current_section:
    #        sections[current_section] = {
    #            "start_line": section_start,
    #            "end_line": len(lines),
    #            "text": '\n'.join(lines[section_start:])
    #        }
    #    
    #    # Also try to find sections by content keywords if headers not found
    #    if not any(sections.values()):
    #        # Look for model section by content
    #        full_text_lower = text.lower()
    #        if 'compartmental' in full_text_lower or 'seir' in full_text_lower or 'differential equation' in full_text_lower:
    #            # Estimate model section (rough approximation)
    #            model_start = text.lower().find('model')
    #            if model_start > 0:
    #                sections["model"] = {
    #                    "start_line": text[:model_start].count('\n'),
    #                    "end_line": text[:model_start + 5000].count('\n'),
    #                    "text": text[model_start:model_start + 5000]
    #                }
    #    
    #    return sections
    
    def _is_valid_camelot_df(self, df) -> bool:
        if df is None or df.empty:
            return False

        df = df.replace("", None).dropna(how="all").dropna(axis=1, how="all")

        rows, cols = df.shape
        if rows < 2 or cols < 2:
            return False

        # % empty cells
        empty_ratio = df.isna().sum().sum() / (rows * cols)
        if empty_ratio > 0.4:
            return False

        # reject equation-like tables
        math_tokens = re.compile(r"[=¼∂∑∫λμθβγΔ]")
        math_cells = sum(
            1 for v in df.astype(str).values.flatten()
            if math_tokens.search(v)
        )
        if math_cells / (rows * cols) > 0.3:
            return False

        # reject long paragraph blobs
        avg_len = df.astype(str).map(len).values.mean()
        if avg_len > 80:
            return False

        return True



    def extract_tables_camelot(self, pdf_path: str) -> List[Dict[str, Any]]:
        import camelot

        tables = []

        # 1. STREAM mode first (works almost everywhere)
        stream_tables = camelot.read_pdf(
            pdf_path,
            pages="all",
            flavor="stream"
        )

        for i, table in enumerate(stream_tables):
            df = table.df

            if self._is_valid_camelot_df(df):
                tables.append({
                    "source": "camelot-stream",
                    "table_index": i + 1,
                    "page": table.page,
                    "shape": df.shape,
                    "data": df.values.tolist()
                })

        # 2. LATTICE mode (optional, only if Ghostscript works)
        try:
            lattice_tables = camelot.read_pdf(
                pdf_path,
                pages="all",
                flavor="lattice"
            )

            for i, table in enumerate(lattice_tables):
                df = table.df

                if self._is_valid_camelot_df(df):
                    tables.append({
                        "source": "camelot-lattice",
                        "table_index": i + 1,
                        "page": table.page,
                        "shape": df.shape,
                        "data": df.values.tolist()
                    })
        except Exception:
            pass

        return tables


    def extract_tables(self, pdf_path: str) -> List[Dict[str, Any]]:
        #"""
        #Extract tables from PDF.
        #
        #Returns:
        #    List of tables with data and metadata
        #"""
        #pdf_path = Path(pdf_path)
        #tables = []
        #
        #if self.pdf_extractor == 'pdfplumber':
        #    import pdfplumber
        #    with pdfplumber.open(pdf_path) as pdf:
        #        for page_num, page in enumerate(pdf.pages, start=1):
        #            page_tables = page.extract_tables(
        #                table_settings={
        #                    "vertical_strategy": "lines",
        #                    "horizontal_strategy": "lines",
        #                    "snap_tolerance": 3,
        #                    "join_tolerance": 3
        #                }
        #            )
#
        #            for table_num, table in enumerate(page_tables, start=1):
        #                if self._is_valid_table(table):
        #                    tables.append({
        #                        "page_number": page_num,
        #                        "table_number": table_num,
        #                        "data": table,
        #                        "num_rows": len(table),
        #                        "num_cols": len(table[0])
        #                    })
        #
        #return tables

        try:
            return self.extract_tables_camelot(pdf_path)
        except Exception:
            return []
    
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

        # Unicode normalization (SAFE)
        full_text = unicodedata.normalize("NFC", full_text)
        
        # Clean text
        cleaned_text = self.clean_text(full_text)
        
        # Detect sections
        sections = self.detect_sections_from_text(cleaned_text)
        sections = self.coalesce_short_sections(sections)
        
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
                }, f, indent=2, ensure_ascii=False)
            
            # Save paper_sections.json
            with open(output_path / "paper_sections.json", 'w') as f:
                json.dump({
                    "sections": result["sections"],
                    "tables": result["tables"]
                }, f, indent=2, ensure_ascii=False)
        
        return result
    
    


def test_pdf_pipeline(pdf_filename: str, out_path: str = "pipeline_output.json"):
    """
    Runs the PDF pipeline and writes results to a JSON file
    suitable for LLM ingestion.
    """

    pipeline = PDFPipeline()
    result = pipeline.process_pdf(pdf_filename)

    # ---- Normalize sections ----
    sections_out = []
    for sec_id, sec in result["sections"].items():
        if isinstance(sec, dict):
            sections_out.append({
                "id": sec_id,
                "heading": sec.get("heading", ""),
                "text": sec.get("text", "")
            })
        else:
            sections_out.append({
                "id": sec_id,
                "heading": "",
                "text": sec
            })
    # ---- Normalize tables ----
    tables_out = []
    for t in result["tables"]:
        rows, cols = t["shape"]
        tables_out.append({
            "page": t["page"],
            "source": t["source"],
            "table_index": t["table_index"],
            "rows": rows,
            "cols": cols,
            "data": t["data"]
        })

    output = {
        "pdf": result["pdf_path"],
        "num_pages": result["num_pages"],
        "extraction_method": result["extraction_method"],
        "sections": sections_out,
        "tables": tables_out
    }

    with open(out_path, "w", encoding="utf-8") as f:
        json.dump(output, f, indent=2, ensure_ascii=False)

    print(f"[OK] Pipeline output written to: {out_path}")


    print("\nTEST COMPLETE")
    print("=" * 80)



if __name__ == "__main__":
    # Replace with your PDF filename (same folder)
    test_pdf_pipeline("zika.pdf", "zika.json")
