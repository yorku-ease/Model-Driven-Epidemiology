#!/usr/bin/env python3
"""
Re-run Phase 2 Step 9 (evaluation) only from saved artifacts — no PDF, no LLM calls.

Reads from each report folder:
  - extracted_entities.json  (required)
  - traceability.json          (required)
  - phase2_gap_report.json     (optional; uses empty gap summary if missing)

Writes (default: overwrites evaluation in place):
  - evaluation_report.json  (default; use -o FILE for a separate copy, e.g. evaluation_report_rerun.json)

Gold baseline: use --gold-standard, or auto-detect from --baseline-models-dir using the
same rules as run_phase2.py (report folder name must look like {disease}_llm_{provider}_{ts}).

Examples:
  cd "phase 2"
  python3 rerun_evaluation_only.py reports/cholera_llm_openai_20260321_225824
  # Re-run Step 9 for every report under reports/ (updates evaluation_report.json each):
  python3 rerun_evaluation_only.py --batch reports
  python3 rerun_evaluation_only.py --batch old-reports
  python3 rerun_evaluation_only.py old-reports/cholera_llm_gemini_20260211_201419 --eval-threshold 0.75
  # Keep previous JSON as a sidecar instead of overwriting:
  python3 rerun_evaluation_only.py --batch reports -o evaluation_report_rerun.json

Then aggregate tables (use the same -e filename you wrote):
  python3 build_results_md.py -e evaluation_report.json -o RESULTS_REPORT_CURRENT.md
  python3 build_results_md.py -e evaluation_report_rerun.json -o RESULTS_REPORT_RERUN.md
"""

from __future__ import annotations

import argparse
import json
import re
import sys
from pathlib import Path
from typing import Any, Dict, List, Optional

PHASE2 = Path(__file__).resolve().parent
sys.path.insert(0, str(PHASE2))

from src.evaluation.evaluator import Evaluator  # noqa: E402


EMPTY_GAPS: Dict[str, Any] = {
    "missing_compartments": [],
    "missing_parameters": [],
    "missing_stratifications": [],
    "missing_interventions": [],
    "summary": {
        "total_gaps": 0,
        "critical_gaps": 0,
        "high_gaps": 0,
        "medium_gaps": 0,
    },
}


def find_baseline_for_report(
    report_dir: Path, baseline_dir: Path
) -> Optional[Path]:
    """Mirror run_phase2 auto-detection using folder name as paper stem."""
    if not baseline_dir.is_dir():
        return None
    name = report_dir.name
    if "_llm_" not in name:
        return None
    paper_stem = name.split("_llm_")[0].lower()

    paper_normalized = re.sub(r"[_\-\s]+", " ", paper_stem)
    paper_keywords = set(
        re.split(
            r"[_\-\s]+|(?<=[a-z])(?=[A-Z0-9])|(?<=[0-9])(?=[A-Za-z])",
            paper_normalized,
        )
    )
    paper_keywords = {k.lower() for k in paper_keywords if k.strip()}

    for baseline_file in sorted(baseline_dir.glob("*.compmodel")):
        baseline_stem = baseline_file.stem.lower()
        baseline_normalized = re.sub(r"[_\-\s]+", " ", baseline_stem)
        baseline_keywords = set(
            re.split(
                r"[_\-\s]+|(?<=[a-z])(?=[A-Z0-9])|(?<=[0-9])(?=[A-Za-z])",
                baseline_normalized,
            )
        )
        baseline_keywords = {k.lower() for k in baseline_keywords if k.strip()}

        common_diseases = [
            "ebola",
            "covid",
            "malaria",
            "hiv",
            "flu",
            "tuberculosis",
            "tb",
        ]
        has_common_disease = any(
            disease in paper_stem and disease in baseline_stem
            for disease in common_diseases
        )

        if (
            paper_stem in baseline_stem
            or baseline_stem in paper_stem
            or len(paper_keywords & baseline_keywords) > 0
            or has_common_disease
        ):
            return baseline_file
    return None


DEFAULT_OUTPUT_NAME = "evaluation_report.json"


def run_one(
    report_dir: Path,
    gold_standard_path: Optional[str],
    baseline_models_dir: Path,
    eval_threshold: float,
    output_filename: str,
) -> int:
    report_dir = report_dir.resolve()
    ent_path = report_dir / "extracted_entities.json"
    tr_path = report_dir / "traceability.json"
    if not ent_path.exists():
        print(f"  SKIP: missing {ent_path.name}")
        return 1
    if not tr_path.exists():
        print(f"  SKIP: missing {tr_path.name}")
        return 1

    with open(ent_path) as f:
        entities = json.load(f)
    with open(tr_path) as f:
        traceability = json.load(f)

    gap_path = report_dir / "phase2_gap_report.json"
    if gap_path.exists():
        with open(gap_path) as f:
            gaps = json.load(f)
    else:
        gaps = dict(EMPTY_GAPS)

    gold_path = gold_standard_path
    if not gold_path:
        found = find_baseline_for_report(report_dir, baseline_models_dir)
        if found:
            gold_path = str(found)
            print(f"  Auto-detected baseline: {found.name}")
        else:
            print("  WARNING: No baseline .compmodel matched — gold_standard_comparison will be null.")
            print(f"           Use --gold-standard or check --baseline-models-dir ({baseline_models_dir})")

    evaluator = Evaluator(gold_standard_path=gold_path, threshold=eval_threshold)
    evaluation = evaluator.evaluate(entities, traceability, gaps)
    out_path = report_dir / output_filename
    evaluator.save_evaluation(evaluation, str(out_path))
    print(f"  Wrote {out_path.name} (original evaluation_report.json unchanged)")

    gs = evaluation.get("gold_standard_comparison")
    if gs:
        c, p, fl = gs.get("compartments", {}), gs.get("parameters", {}), gs.get("flows", {})
        print(
            f"  P/R/F1  compartments: {c.get('precision', 0):.2f}/{c.get('recall', 0):.2f}/{c.get('f1', 0):.2f}"
        )
        print(
            f"          parameters:   {p.get('precision', 0):.2f}/{p.get('recall', 0):.2f}/{p.get('f1', 0):.2f}"
        )
        print(
            f"          flows:        {fl.get('precision', 0):.2f}/{fl.get('recall', 0):.2f}/{fl.get('f1', 0):.2f}"
        )
    return 0


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Re-run evaluation only (semantic baseline comparison), no LLM."
    )
    parser.add_argument(
        "report_dir",
        nargs="?",
        help="Single report directory (e.g. reports/cholera_llm_openai_20260321_225824)",
    )
    parser.add_argument(
        "--batch",
        type=Path,
        metavar="DIR",
        help="Run on every subfolder of DIR that contains extracted_entities.json",
    )
    parser.add_argument(
        "--gold-standard",
        type=str,
        default=None,
        help="Path to gold .compmodel or JSON (overrides auto-detect)",
    )
    parser.add_argument(
        "--baseline-models-dir",
        type=Path,
        default=PHASE2 / "data" / "baseline_models",
        help="Where to look for baseline .compmodel files (default: phase 2 data/baseline_models)",
    )
    parser.add_argument(
        "--eval-threshold",
        type=float,
        default=0.70,
        help="Cosine similarity threshold for matching (default: 0.70)",
    )
    parser.add_argument(
        "-o",
        "--output",
        type=str,
        default=DEFAULT_OUTPUT_NAME,
        metavar="FILENAME",
        help=(
            f"Output JSON filename inside each report folder (default: {DEFAULT_OUTPUT_NAME}, in place). "
            "Use e.g. -o evaluation_report_rerun.json to write a sidecar without replacing the main report."
        ),
    )
    args = parser.parse_args()

    if args.batch:
        root = args.batch.resolve()
        if not root.is_dir():
            print(f"Error: not a directory: {root}")
            return 1
        subdirs: List[Path] = sorted(
            d for d in root.iterdir() if d.is_dir() and (d / "extracted_entities.json").exists()
        )
        if not subdirs:
            print(f"No report folders with extracted_entities.json under {root}")
            return 1
        failed = 0
        for d in subdirs:
            print(f"=== {d.name} ===")
            failed += run_one(
                d,
                args.gold_standard,
                args.baseline_models_dir,
                args.eval_threshold,
                args.output,
            )
        return 1 if failed else 0

    if not args.report_dir:
        parser.error("Provide report_dir or --batch DIR")
    report_dir = Path(args.report_dir)
    if not report_dir.is_dir():
        print(f"Error: not a directory: {report_dir}")
        return 1
    return run_one(
        report_dir,
        args.gold_standard,
        args.baseline_models_dir,
        args.eval_threshold,
        args.output,
    )


if __name__ == "__main__":
    raise SystemExit(main())
