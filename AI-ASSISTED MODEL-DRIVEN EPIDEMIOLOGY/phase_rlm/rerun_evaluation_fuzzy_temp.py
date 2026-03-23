#!/usr/bin/env python3
"""
Re-run fuzzy (phase 2_temp) evaluation for Phase RLM repaired models.

- Input repaired model: phase_rlm/output/<run>/model_repaired.compmodel
- Baseline models: phase 2/data/baseline_models/*.compmodel
- Phase 2 reference eval (for delta): phase 2/reports/<run>/evaluation_report_fuzzy_temp.json

Output per RLM run folder:
- evaluation_report_fuzzy_temp.json
- phase2_vs_rlm_fuzzy_delta.json
"""

from __future__ import annotations

import argparse
import json
import re
import sys
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple

PHASE_RLM = Path(__file__).resolve().parent
PHASE2 = PHASE_RLM.parent / "phase 2"
RLM_OUTPUT = PHASE_RLM / "output"

TEMP_EVAL_ROOT = PHASE2 / "phase 2_temp" / "src"
if not TEMP_EVAL_ROOT.is_dir():
    print(f"Error: missing {TEMP_EVAL_ROOT}", file=sys.stderr)
    raise SystemExit(1)
sys.path.insert(0, str(TEMP_EVAL_ROOT))
from evaluation.evaluator import Evaluator as FuzzyEvaluator  # noqa: E402

sys.path.append(str(PHASE2))
from src.evaluation.evaluator import Evaluator as Phase2Evaluator  # noqa: E402


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

DEFAULT_EVAL_NAME = "evaluation_report_fuzzy_temp.json"
DELTA_NAME = "phase2_vs_rlm_fuzzy_delta.json"
PHASE2_EVAL_NAME = "evaluation_report_fuzzy_temp.json"
COMP_W = {"compartments": 0.425, "flows": 0.425, "parameters": 0.150}


def find_baseline_for_run(run_name: str, baseline_dir: Path) -> Optional[Path]:
    if not baseline_dir.is_dir():
        return None
    if "_llm_" not in run_name:
        return None
    paper_stem = run_name.split("_llm_")[0].lower()
    paper_normalized = re.sub(r"[_\-\s]+", " ", paper_stem)
    paper_keywords = set(
        re.split(
            r"[_\-\s]+|(?<=[a-z])(?=[A-Z0-9])|(?<=[0-9])(?=[A-Za-z])",
            paper_normalized,
        )
    )
    paper_keywords = {k.lower() for k in paper_keywords if k.strip()}

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

        has_common_disease = any(
            d in paper_stem and d in baseline_stem for d in common_diseases
        )
        if (
            paper_stem in baseline_stem
            or baseline_stem in paper_stem
            or len(paper_keywords & baseline_keywords) > 0
            or has_common_disease
        ):
            return baseline_file
    return None


def compmodel_to_entities(compmodel_path: Path) -> Dict[str, Any]:
    ev = Phase2Evaluator()
    return ev._convert_compmodel_to_gold_standard(str(compmodel_path))  # noqa: SLF001


def load_phase2_inputs(run_name: str) -> Tuple[Dict[str, Any], Dict[str, Any]]:
    report_dir = PHASE2 / "reports" / run_name
    traceability_path = report_dir / "traceability.json"
    gap_path = report_dir / "phase2_gap_report.json"

    if traceability_path.is_file():
        with open(traceability_path, encoding="utf-8") as f:
            traceability = json.load(f)
    else:
        traceability = {"coverage_metrics": {}}

    if gap_path.is_file():
        with open(gap_path, encoding="utf-8") as f:
            gaps = json.load(f)
    else:
        gaps = dict(EMPTY_GAPS)

    return traceability, gaps


def composite_from_gold(gs: Optional[Dict[str, Any]]) -> Optional[float]:
    if not gs:
        return None
    if gs.get("composite_score") is not None:
        try:
            return float(gs["composite_score"])
        except (TypeError, ValueError):
            return None
    c = gs.get("compartments") or {}
    p = gs.get("parameters") or {}
    f = gs.get("flows") or {}
    try:
        return (
            COMP_W["compartments"] * float(c.get("f1") or 0)
            + COMP_W["flows"] * float(f.get("f1") or 0)
            + COMP_W["parameters"] * float(p.get("f1") or 0)
        )
    except (TypeError, ValueError):
        return None


def build_delta(run_name: str, rlm_eval: Dict[str, Any]) -> Dict[str, Any]:
    phase2_eval_path = PHASE2 / "reports" / run_name / PHASE2_EVAL_NAME
    phase2_data: Dict[str, Any] = {}
    if phase2_eval_path.is_file():
        try:
            with open(phase2_eval_path, encoding="utf-8") as f:
                phase2_data = json.load(f)
        except (OSError, json.JSONDecodeError):
            phase2_data = {}

    p2_gs = phase2_data.get("gold_standard_comparison") if phase2_data else None
    rlm_gs = rlm_eval.get("gold_standard_comparison")

    p2_comp = composite_from_gold(p2_gs)
    rlm_comp = composite_from_gold(rlm_gs)

    delta = None
    trend = "unknown"
    if p2_comp is not None and rlm_comp is not None:
        delta = rlm_comp - p2_comp
        if abs(delta) < 1e-12:
            trend = "same"
        elif delta > 0:
            trend = "improved"
        else:
            trend = "decreased"

    return {
        "run_name": run_name,
        "phase2_eval_path": str(phase2_eval_path),
        "rlm_eval_path": str((RLM_OUTPUT / run_name / DEFAULT_EVAL_NAME).resolve()),
        "phase2_composite_score": p2_comp,
        "rlm_composite_score": rlm_comp,
        "delta_rlm_minus_phase2": delta,
        "trend": trend,
        "note": "Comparison uses fuzzy evaluation_report_fuzzy_temp.json from phase 2 reports.",
    }


def eval_one(run_dir: Path, gold_standard: Optional[str], baseline_dir: Path) -> int:
    run_name = run_dir.name
    repaired = run_dir / "model_repaired.compmodel"
    if not repaired.is_file():
        print(f"  SKIP: missing {repaired}")
        return 1

    traceability, gaps = load_phase2_inputs(run_name)
    entities = compmodel_to_entities(repaired)

    gold = gold_standard
    if not gold:
        baseline = find_baseline_for_run(run_name, baseline_dir)
        if baseline:
            gold = str(baseline)
            print(f"  Auto-detected baseline: {baseline.name}")
        else:
            print("  WARNING: no matching baseline, gold_standard_comparison may be null")

    evaluator = FuzzyEvaluator(gold_standard_path=gold)
    evaluation = evaluator.evaluate(entities, traceability, gaps)
    evaluation["evaluation_input_source"] = "phase_rlm_model_repaired_compmodel"
    evaluation["evaluation_input_path"] = str(repaired.resolve())

    out_eval = run_dir / DEFAULT_EVAL_NAME
    evaluator.save_evaluation(evaluation, str(out_eval))
    print(f"  Wrote {out_eval}")

    delta_payload = build_delta(run_name, evaluation)
    out_delta = run_dir / DELTA_NAME
    with open(out_delta, "w", encoding="utf-8") as f:
        json.dump(delta_payload, f, indent=2)
    print(
        f"  Phase2→RLM trend: {delta_payload['trend']} "
        f"(delta={delta_payload['delta_rlm_minus_phase2']})"
    )
    return 0


def main() -> int:
    parser = argparse.ArgumentParser(
        description=(
            "Evaluate phase_rlm/output models with phase2_temp fuzzy evaluator and "
            "compare to phase 2 reports/evaluation_report_fuzzy_temp.json."
        )
    )
    parser.add_argument("run_dir", nargs="?", help="Single run under phase_rlm/output/")
    parser.add_argument(
        "--batch",
        type=Path,
        default=RLM_OUTPUT,
        help="Run on all subfolders under this directory (default: phase_rlm/output)",
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
        help="Baseline .compmodel directory (default: phase 2/data/baseline_models)",
    )
    args = parser.parse_args()

    if args.run_dir:
        one = Path(args.run_dir).resolve()
        if not one.is_dir():
            print(f"Error: not a directory: {one}", file=sys.stderr)
            return 1
        return eval_one(one, args.gold_standard, args.baseline_models_dir)

    root = args.batch.resolve()
    if not root.is_dir():
        print(f"Error: not a directory: {root}", file=sys.stderr)
        return 1
    runs = sorted(
        d for d in root.iterdir() if d.is_dir() and (d / "model_repaired.compmodel").exists()
    )
    if not runs:
        print(f"No RLM runs found in {root}")
        return 1
    failed = 0
    for d in runs:
        print(f"=== {d.name} ===")
        failed += eval_one(d, args.gold_standard, args.baseline_models_dir)
    return 1 if failed else 0


if __name__ == "__main__":
    raise SystemExit(main())
