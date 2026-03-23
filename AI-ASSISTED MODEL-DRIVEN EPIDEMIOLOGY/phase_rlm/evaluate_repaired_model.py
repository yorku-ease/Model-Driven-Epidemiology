#!/usr/bin/env python3
"""
Run Phase 2 gold-standard evaluation on **entities parsed from a .compmodel** (e.g. RLM output).

Use this to compare baseline P/R/F1 for:
  - Phase 2 `evaluation_report.json` (from LLM extraction), vs
  - `evaluation_report_rlm_repaired.json` (from `model_repaired.compmodel` structure)

Same Evaluator + gold baseline rules as `phase 2/rerun_evaluation_only.py`.

Examples:
  cd phase_rlm
  python3 evaluate_repaired_model.py "../phase 2/reports/measles_llm_gemini_20260312_165901"
  python3 evaluate_repaired_model.py "../phase 2/reports/..." --compmodel ./custom.compmodel
"""

from __future__ import annotations

import argparse
import json
import re
import sys
from pathlib import Path
from typing import Any, Dict, Optional

PHASE_RLM = Path(__file__).resolve().parent
PHASE2 = PHASE_RLM.parent / "phase 2"
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

MIN_TRACEABILITY: Dict[str, Any] = {
    "coverage_metrics": {
        "total_items": 0,
        "items_with_evidence": 0,
        "coverage_percentage": 0.0,
        "paper_backed_items": 0,
        "faithfulness_percentage": 0.0,
    }
}


def find_baseline_for_report(
    report_dir: Path, baseline_dir: Path
) -> Optional[Path]:
    """Same rules as run_phase2 / rerun_evaluation_only."""
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
            "cholera",
            "dengue",
            "measles",
            "zika",
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


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Evaluate repaired .compmodel vs gold (Phase 2 semantic evaluator)."
    )
    parser.add_argument(
        "report_dir",
        type=Path,
        help="Phase 2 report folder (run name used for phase_rlm/output/<name>/)",
    )
    parser.add_argument(
        "--compmodel",
        type=Path,
        default=None,
        help="Path to repaired model (default: phase_rlm/output/<run>/model_repaired.compmodel)",
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
        help="Baseline .compmodel directory for auto-detect",
    )
    parser.add_argument(
        "--eval-threshold",
        type=float,
        default=0.70,
        help="Cosine similarity threshold (default 0.70, same as Phase 2 rerun)",
    )
    parser.add_argument(
        "-o",
        "--output",
        type=Path,
        default=None,
        help="Output JSON path (default: phase_rlm/output/<run>/evaluation_report_rlm_repaired.json)",
    )
    args = parser.parse_args()

    report_dir = args.report_dir.resolve()
    rlm_run_dir = PHASE_RLM / "output" / report_dir.name
    compmodel = args.compmodel or (rlm_run_dir / "model_repaired.compmodel")
    compmodel = compmodel.resolve()

    if not compmodel.is_file():
        print(f"Error: compmodel not found: {compmodel}", file=sys.stderr)
        return 1

    out_path = args.output
    if out_path is None:
        out_path = rlm_run_dir / "evaluation_report_rlm_repaired.json"
    else:
        out_path = out_path.resolve()

    gold_path = args.gold_standard
    if not gold_path:
        found = find_baseline_for_report(report_dir, args.baseline_models_dir)
        if found:
            gold_path = str(found)
            print(f"Auto-detected baseline: {found.name}")
        else:
            print(
                "Error: No baseline .compmodel matched. Use --gold-standard or check --baseline-models-dir.",
                file=sys.stderr,
            )
            return 1

    # Parse compmodel as entity dict (same schema as gold from .compmodel)
    parser_ev = Evaluator()
    extracted = parser_ev._convert_compmodel_to_gold_standard(str(compmodel))  # noqa: SLF001

    evaluator = Evaluator(
        gold_standard_path=gold_path,
        threshold=args.eval_threshold,
    )
    evaluator.load_model()

    evaluation = evaluator.evaluate(extracted, MIN_TRACEABILITY, EMPTY_GAPS)
    evaluation["evaluation_input_source"] = "rlm_repaired_compmodel"
    evaluation["evaluation_input_path"] = str(compmodel)
    evaluation["matching_note"] = (
        "Gold comparison uses structure parsed from the repaired compmodel; "
        "Phase 2 evaluation_report.json uses LLM extracted_entities.json."
    )

    out_path.parent.mkdir(parents=True, exist_ok=True)
    evaluator.save_evaluation(evaluation, str(out_path))
    print(f"Wrote {out_path}")

    gs = evaluation.get("gold_standard_comparison")
    if gs:
        print(
            f"  composite_score: {gs.get('composite_score')} "
            f"(threshold {args.eval_threshold})"
        )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
