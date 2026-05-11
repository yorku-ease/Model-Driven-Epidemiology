#!/usr/bin/env python3
"""
Model Repair Runner — Post-Processing for .compmodel Validation Errors.

Usage:
  python run_repair.py --model <path> --llm-provider gemini

Applies structural fixes (no LLM) and then iteratively calls LLM to fix
remaining validation errors one at a time.
"""

import argparse
import sys
from pathlib import Path

PHASE3_DIR = Path(__file__).resolve().parent
sys.path.insert(0, str(PHASE3_DIR))

from src.repair.model_repair import run_repair_pipeline


def main():
    ap = argparse.ArgumentParser(description="Model repair: fix .compmodel validation errors")
    ap.add_argument("--model", required=True, help="Path to .compmodel file")
    ap.add_argument("--llm-provider", default="gemini",
                    choices=["gemini", "openai"], help="LLM provider")
    ap.add_argument("--max-iterations", type=int, default=10,
                    help="Max LLM repair iterations")
    args = ap.parse_args()
    
    model_path = Path(args.model).resolve()
    if not model_path.exists():
        print(f"ERROR: Model not found: {model_path}", file=sys.stderr)
        sys.exit(1)
    
    result = run_repair_pipeline(
        model_path,
        provider=args.llm_provider,
        max_iterations=args.max_iterations,
    )
    
    print(f"[Repair] Final model: {result}")
    print(f"OUTPUT_PATH:{result}")


if __name__ == "__main__":
    sys.exit(main())
