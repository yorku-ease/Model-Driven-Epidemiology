#!/usr/bin/env python3
"""
Build markdown reports for Phase RLM fuzzy evaluations.

Inputs:
- phase_rlm/output/<run>/evaluation_report_fuzzy_temp.json
- phase 2/reports/<run>/evaluation_report_fuzzy_temp.json (for delta)
"""

from __future__ import annotations

import argparse
import json
from pathlib import Path
from typing import Any, Dict, Optional

PHASE_RLM = Path(__file__).resolve().parent
PHASE2 = PHASE_RLM.parent / "phase 2"
DEFAULT_RUNS = PHASE_RLM / "output"
DEFAULT_OUT = PHASE_RLM / "RESULTS_RLM_FUZZY.md"
EVAL_NAME = "evaluation_report_fuzzy_temp.json"

COMP_W = {"compartments": 0.425, "flows": 0.425, "parameters": 0.150}


def load_json(path: Path) -> Dict[str, Any]:
    if not path.is_file():
        return {}
    try:
        with open(path, encoding="utf-8") as f:
            return json.load(f)
    except (OSError, json.JSONDecodeError):
        return {}


def comp(gs: Optional[Dict[str, Any]]) -> Optional[float]:
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


def fmt(v: Optional[float]) -> str:
    return f"{v:.4f}" if v is not None else "—"


def trend(delta: Optional[float]) -> str:
    if delta is None:
        return "—"
    if abs(delta) < 1e-12:
        return "≈ same"
    if delta > 0:
        return f"↑ +{delta:.4f}"
    return f"↓ {delta:.4f}"


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Build Phase RLM fuzzy results and delta vs phase 2 fuzzy."
    )
    parser.add_argument(
        "--runs-dir",
        type=Path,
        default=DEFAULT_RUNS,
        help="Phase RLM run directory (default: phase_rlm/output)",
    )
    parser.add_argument(
        "-o",
        "--output",
        type=Path,
        default=DEFAULT_OUT,
        help="Output markdown path",
    )
    parser.add_argument(
        "--title",
        default="Phase RLM Fuzzy Results (vs Phase 2)",
        help="H1 title",
    )
    args = parser.parse_args()

    runs_dir = args.runs_dir.resolve()
    if not runs_dir.is_dir():
        print(f"Error: not a directory: {runs_dir}")
        return 1

    rows = []
    for run_dir in sorted(runs_dir.iterdir(), key=lambda p: p.name):
        if not run_dir.is_dir() or "_llm_" not in run_dir.name:
            continue
        rlm_eval = load_json(run_dir / EVAL_NAME)
        if not rlm_eval:
            continue
        p2_eval = load_json(PHASE2 / "reports" / run_dir.name / EVAL_NAME)

        rlm_gs = rlm_eval.get("gold_standard_comparison") or {}
        p2_gs = p2_eval.get("gold_standard_comparison") or {}
        rlm_comp = comp(rlm_gs)
        p2_comp = comp(p2_gs)
        delta = None if (rlm_comp is None or p2_comp is None) else (rlm_comp - p2_comp)
        rows.append((run_dir.name, rlm_comp, p2_comp, delta))

    out = args.output
    if not out.is_absolute():
        out = (PHASE_RLM / out).resolve()

    lines = [
        f"# {args.title}",
        "",
        f"**RLM runs dir:** `{runs_dir}`",
        f"**Phase 2 reference dir:** `{(PHASE2 / 'reports').resolve()}`",
        "",
        f"- Both sides use fuzzy file `{EVAL_NAME}`.",
        "- Delta is `RLM - Phase2` (positive means RLM improved).",
        "",
        "| Run | RLM composite | Phase2 composite | Delta |",
        "|-----|---------------|------------------|-------|",
    ]
    for name, rlm_comp, p2_comp, d in rows:
        lines.append(f"| `{name}` | {fmt(rlm_comp)} | {fmt(p2_comp)} | {trend(d)} |")

    if not rows:
        lines.extend(
            [
                "",
                "*No runs found with RLM fuzzy evaluations.*",
                "",
                "Run first:",
                "```bash",
                "cd phase_rlm",
                "python3 rerun_evaluation_fuzzy_temp.py --batch output",
                "```",
            ]
        )

    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text("\n".join(lines), encoding="utf-8")
    print(f"Wrote {out} ({len(rows)} runs)")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
