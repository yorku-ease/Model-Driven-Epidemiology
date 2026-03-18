#!/usr/bin/env python3
"""Runner script to parse benchmark .compmodel files to JSON gold standard format."""

import os
import json
from pathlib import Path

from utils.xmlParsestojson import parse_compartmental_xml

BENCHMARK_DIR = Path(__file__).parent / "benchMarkModel"
OUTPUT_DIR = Path(__file__).parent / "parsed_models" / "benchmark_gold_standard"


def main():
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    compmodel_files = sorted(BENCHMARK_DIR.glob("*.compmodel"))
    print(f"Found {len(compmodel_files)} .compmodel files")

    for compmodel_file in compmodel_files:
        print(f"Parsing: {compmodel_file.name}")

        xml_content = compmodel_file.read_text(encoding="utf-8")
        parsed = parse_compartmental_xml(xml_content)

        base_name = compmodel_file.stem
        output_name = f"{base_name}_gold.json"
        output_path = OUTPUT_DIR / output_name

        output_path.write_text(json.dumps(parsed, indent=2), encoding="utf-8")
        print(f"  -> {output_path.name}")

    print(f"\nDone! Parsed {len(compmodel_files)} files to {OUTPUT_DIR}")


if __name__ == "__main__":
    main()
