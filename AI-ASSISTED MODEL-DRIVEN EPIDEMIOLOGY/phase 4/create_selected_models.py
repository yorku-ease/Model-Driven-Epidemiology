#!/usr/bin/env python3
"""
Create (or update) the phase 3/selected_models/ layout from a Phase 3 showcase directory.

For each disease/paper, copies model_filled.compmodel into:
  selected_models/<disease_stem>/model_filled.compmodel

This lets run_phase4.py --selected-models work with the new numbered-paper structure.

Usage:
  cd "phase 4"
  python3 create_selected_models.py --showcase-dir "../phase 3/showcase_gemini" --mode both
  python3 create_selected_models.py --showcase-dir "../phase 3/showcase_gemini" --mode both --output "../phase 3/selected_models"
"""

from __future__ import annotations

import argparse
import shutil
from pathlib import Path


def main():
    ap = argparse.ArgumentParser(description="Populate selected_models/ from a Phase 3 showcase directory.")
    ap.add_argument("--showcase-dir", required=True,
                    help="Phase 3 showcase directory (e.g. '../phase 3/showcase_gemini')")
    ap.add_argument("--mode", default="both", choices=["rag_only", "llm_only", "both"],
                    help="Fill mode to use (default: both)")
    ap.add_argument("--output", default="../phase 3/selected_models",
                    help="Target selected_models directory (default: ../phase 3/selected_models)")
    ap.add_argument("--dry-run", action="store_true",
                    help="Print what would be copied without doing it")
    args = ap.parse_args()

    showcase = Path(args.showcase_dir).resolve()
    mode_dir = showcase / args.mode
    out_root = Path(args.output).resolve()

    if not mode_dir.is_dir():
        print(f"Error: mode directory not found: {mode_dir}")
        return 1

    copied = 0
    for run_dir in sorted(mode_dir.iterdir()):
        if not run_dir.is_dir():
            continue
        src = run_dir / "model_filled.compmodel"
        if not src.exists():
            print(f"  SKIP {run_dir.name} — no model_filled.compmodel")
            continue

        # stem = disease paper name, e.g. covid1, influenza3
        name = run_dir.name  # e.g. covid1_gemini_phase3
        stem = name.split("_phase3")[0]        # covid1_gemini
        stem = "_".join(stem.split("_")[:-1])  # covid1

        dest_dir = out_root / stem
        dest = dest_dir / "model_filled.compmodel"

        if args.dry_run:
            print(f"  Would copy: {src} → {dest}")
        else:
            dest_dir.mkdir(parents=True, exist_ok=True)
            shutil.copy2(src, dest)
            # Also copy phase3 context JSON if available
            for extra in ["phase3_showcase_source.json", "phase3_validation.json"]:
                esrc = run_dir / extra
                if esrc.exists():
                    shutil.copy2(esrc, dest_dir / extra)
            print(f"  Copied: {stem}/model_filled.compmodel")
        copied += 1

    action = "Would copy" if args.dry_run else "Copied"
    print(f"\n{action} {copied} model(s) → {out_root}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
