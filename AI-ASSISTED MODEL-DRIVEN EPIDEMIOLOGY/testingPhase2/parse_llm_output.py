#!/usr/bin/env python3
"""Runner script to parse LLM output files to JSON format.

Usage:
    python parse_llm_output.py <disease_name>

Example:
    python parse_llm_output.py measles
    python parse_llm_output.py zika
"""

import json
import sys
from pathlib import Path

from utils.xmlParsestojson import parse_compartmental_xml


def main():
    if len(sys.argv) != 2:
        print(f"Usage: python {sys.argv[0]} <disease_name>")
        print(f"Example: python {sys.argv[0]} measles")
        sys.exit(1)

    disease = sys.argv[1]

    LLM_OUTPUT_DIR = Path(__file__).parent / "llm_output" / disease
    OUTPUT_DIR = Path(__file__).parent / "parsed_models" / disease

    if not LLM_OUTPUT_DIR.exists():
        print(f"Error: Directory not found: {LLM_OUTPUT_DIR}")
        sys.exit(1)

    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    txt_files = sorted(LLM_OUTPUT_DIR.glob("*.txt"))
    print(f"Found {len(txt_files)} .txt files for '{disease}'")

    failed = []
    manually_parsed = []

    for txt_file in txt_files:
        print(f"Parsing: {txt_file.name}")

        try:
            content = txt_file.read_text(encoding="utf-8")

            if content.startswith("Error:"):
                print(f"  -> FAILED: LLM API Error - {content[:100]}")
                failed.append((txt_file.name, "LLM_API_ERROR"))
                continue

            parsed = parse_compartmental_xml(content)

            base_name = txt_file.stem
            output_name = f"{base_name}_parsed.json"
            output_path = OUTPUT_DIR / output_name

            output_path.write_text(json.dumps(parsed, indent=2), encoding="utf-8")
            print(f"  -> {output_path.name}")
        except Exception as e:
            print(f"  -> FAILED: {e}")
            failed.append((txt_file.name, str(e)[:50]))

    print(
        f"\nDone! Parsed {len(txt_files) - len(failed)}/{len(txt_files)} files to {OUTPUT_DIR}"
    )

    if failed:
        print("\nFailed files:")
        for name, err in failed:
            print(f"  - {name}: {err}")

    return failed


if __name__ == "__main__":
    main()
