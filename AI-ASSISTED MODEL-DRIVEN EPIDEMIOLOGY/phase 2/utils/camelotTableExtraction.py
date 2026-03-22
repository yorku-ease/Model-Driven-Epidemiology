"""Table extraction using Camelot

Extracts tables from PDF files using Camelot with both stream and lattice modes.
"""

import math
import re
from pathlib import Path
from typing import List, Dict, Any

import numpy as np
import pandas as pd


class CamelotTableExtractor:
    """Extract tables from PDF using Camelot"""

    def __init__(self):
        pass

    @staticmethod
    def _cell_to_str(v) -> str:
        """Coerce any Camelot/pandas cell to str (regex requires str, not float/NA)."""
        if v is None:
            return ""
        try:
            if pd.isna(v):
                return ""
        except (TypeError, ValueError):
            pass
        if isinstance(v, float) and math.isnan(v):
            return ""
        return str(v)

    def _is_valid_camelot_df(self, df) -> bool:
        """Validate extracted table dataframe."""
        if df is None or df.empty:
            return False

        df = df.replace("", None).dropna(how="all").dropna(axis=1, how="all")

        rows, cols = df.shape
        if rows < 2 or cols < 2:
            return False

        empty_ratio = df.isna().sum().sum() / (rows * cols)
        if empty_ratio > 0.4:
            return False

        math_tokens = re.compile(r"[=¼∂∑∫λμθβγΔ]")
        flat_str = [self._cell_to_str(v) for v in np.asarray(df).ravel()]
        math_cells = sum(1 for s in flat_str if math_tokens.search(s))
        if math_cells / (rows * cols) > 0.3:
            return False

        lens = [len(s) for s in flat_str]
        avg_len = float(np.mean(lens)) if lens else 0.0
        if avg_len > 80:
            return False

        return True

    def clean_text(self, text: str) -> str:
        """Clean extracted text."""
        text = re.sub(r"(\w+)-\s*\n\s*(\w+)", r"\1\2", text)
        text = re.sub(r"^\s*\d+\s*$", "", text, flags=re.MULTILINE)
        text = re.sub(r"[ \t]+", " ", text)
        text = re.sub(r"\n\s*\n", "\n\n", text)
        return text.strip()

    def extract_tables(
        self, pdf_path: str, apply_filter: bool = True
    ) -> List[Dict[str, Any]]:
        """
        Extract tables from PDF using Camelot (stream and lattice modes).

        Args:
            pdf_path: Path to PDF file
            apply_filter: If False, skip validation filter and return all tables

        Returns:
            List of extracted tables with metadata
        """
        import camelot

        tables = []

        stream_tables = camelot.read_pdf(pdf_path, pages="all", flavor="stream")

        for i, table in enumerate(stream_tables):
            df = table.df

            if apply_filter and not self._is_valid_camelot_df(df):
                continue

            tables.append(
                {
                    "source": "camelot-stream",
                    "table_index": i + 1,
                    "page": table.page,
                    "shape": df.shape,
                    "data": df.values.tolist(),
                }
            )

        try:
            lattice_tables = camelot.read_pdf(pdf_path, pages="all", flavor="lattice")

            for i, table in enumerate(lattice_tables):
                df = table.df

                if apply_filter and not self._is_valid_camelot_df(df):
                    continue

                tables.append(
                    {
                        "source": "camelot-lattice",
                        "table_index": i + 1,
                        "page": table.page,
                        "shape": df.shape,
                        "data": df.values.tolist(),
                    }
                )
        except Exception:
            pass

        return tables


def test_table_extraction(pdf_filename: str, out_path: str = "tables_output.json"):
    """Test table extraction and save results to JSON."""
    import json

    extractor = CamelotTableExtractor()
    tables = extractor.extract_tables(pdf_filename)

    tables_out = []
    for t in tables:
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

    output = {"pdf": pdf_filename, "num_tables": len(tables_out), "tables": tables_out}

    with open(out_path, "w", encoding="utf-8") as f:
        json.dump(output, f, indent=2, ensure_ascii=False)

    print(f"[OK] Tables extracted: {len(tables_out)}")
    print(f"[OK] Output written to: {out_path}")


if __name__ == "__main__":
    test_table_extraction("zika.pdf", "zika_tables.json")
