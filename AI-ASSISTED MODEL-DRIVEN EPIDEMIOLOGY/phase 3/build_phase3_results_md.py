#!/usr/bin/env python3
"""
Build a Phase 3 recall summary table (Compartments / Flows / Parameters).

Reads phase3_validation.json (filled_vs_gold structural alignment) and
phase3_gaps.json (parameter gaps) from each showcase run directory.

Usage:
  cd "phase 3"
  python3 build_phase3_results_md.py --showcase showcase_gemini -o RESULTS_PHASE3_GEMINI.md
  python3 build_phase3_results_md.py --showcase showcase_claude -o RESULTS_PHASE3_CLAUDE.md

  # Compare specific modes (default: all three)
  python3 build_phase3_results_md.py --showcase showcase_gemini --mode both -o RESULTS_PHASE3_BOTH.md
"""

from __future__ import annotations

import argparse
import json
import math
from pathlib import Path

PHASE3_DIR = Path(__file__).resolve().parent
PHASE2_DIR = PHASE3_DIR.parent / "phase 2"
MODES = ["rag_only", "llm_only", "both"]

DISEASE_DISPLAY = {
    "cholera1": "Cholera (P1)", "cholera2": "Cholera (P2)", "cholera3": "Cholera (P3)",
    "covid1": "COVID-19 (P1)", "covid2": "COVID-19 (P2)", "covid3": "COVID-19 (P3)",
    "dengue1": "Dengue (P1)", "dengue2": "Dengue (P2)", "dengue3": "Dengue (P3)",
    "ebola1": "Ebola (P1)", "ebola2": "Ebola (P2)", "ebola3": "Ebola (P3)",
    "hiv1": "HIV (P1)", "hiv2": "HIV (P2)", "hiv3": "HIV (P3)",
    "influenza1": "Influenza (P1)", "influenza2": "Influenza (P2)", "influenza3": "Influenza (P3)",
    "malaria1": "Malaria (P1)", "malaria2": "Malaria (P2)", "malaria3": "Malaria (P3)",
    "measles1": "Measles (P1)", "measles2": "Measles (P2)", "measles3": "Measles (P3)",
    "tuberculosis1": "TB (P1)", "tuberculosis2": "TB (P2)", "tuberculosis3": "TB (P3)",
    "zika1": "Zika (P1)", "zika2": "Zika (P2)", "zika3": "Zika (P3)",
}

DISEASE_ORDER = [
    "cholera1","cholera2","cholera3",
    "covid1","covid2","covid3",
    "dengue1","dengue2","dengue3",
    "ebola1","ebola2","ebola3",
    "hiv1","hiv2","hiv3",
    "influenza1","influenza2","influenza3",
    "malaria1","malaria2","malaria3",
    "measles1","measles2","measles3",
    "tuberculosis1","tuberculosis2","tuberculosis3",
    "zika1","zika2","zika3",
]


def _fmt(v) -> str:
    if v is None or (isinstance(v, float) and math.isnan(v)):
        return " — "
    return f"{v:.2f}"


def load_run_metrics(run_dir: Path) -> dict | None:
    """Load recall/precision/F1 for compartments & flows, plus parameter gap info."""
    val_path = run_dir / "phase3_validation.json"
    gap_path = run_dir / "phase3_gaps.json"
    if not val_path.exists():
        return None

    val = json.loads(val_path.read_text(encoding="utf-8"))
    sa = val.get("structural_alignment", {})

    def _metrics(section: dict, key: str) -> dict:
        m = section.get(key, {})
        return {
            "recall": m.get("recall"),
            "precision": m.get("precision"),
            "f1": m.get("f1"),
        }

    draft = sa.get("draft_vs_gold", {})
    filled = sa.get("filled_vs_gold", {})

    result = {
        "draft": {
            "compartments": _metrics(draft, "compartments"),
            "flows": _metrics(draft, "flows"),
        },
        "filled": {
            "compartments": _metrics(filled, "compartments"),
            "flows": _metrics(filled, "flows"),
        },
        "params_before": None,
        "params_after": None,
        "param_accuracy_pct": val.get("summary", {}).get("accuracy_pct"),
    }

    # Parameter gap counts (before = after Phase 3 fill, so missing_parameters = remaining gaps)
    if gap_path.exists():
        gaps = json.loads(gap_path.read_text(encoding="utf-8"))
        summary = gaps.get("summary", {})
        result["params_after"] = summary.get("missing_parameters", 0)
        result["extra_params"] = summary.get("extra_parameters", 0)

    return result


def best_p2_recall(disease: str, metric: str) -> float | None:
    """Best recall across all providers for a disease from Phase 2 fuzzy eval."""
    reports = PHASE2_DIR / "reports"
    if not reports.is_dir():
        return None
    best = None
    for d in reports.iterdir():
        if not d.name.startswith(f"{disease}_llm_"):
            continue
        f = d / "evaluation_report_fuzzy_temp.json"
        if not f.exists():
            continue
        try:
            v = json.loads(f.read_text(encoding="utf-8"))
            r = v.get("gold_standard_comparison", {}).get(metric, {}).get("recall")
            if r is not None and (best is None or r > best):
                best = r
        except Exception:
            pass
    return best


def load_showcase(showcase_dir: Path) -> dict:
    """
    Returns:
      { disease: { mode: metrics_dict, "best_p2": str, "phase2_score": float } }
    """
    summary_path = showcase_dir / "showcase_summary.json"
    summary = []
    if summary_path.exists():
        summary = json.loads(summary_path.read_text(encoding="utf-8"))

    best_p2 = {row["disease"]: row.get("best_phase2_extractor", "?") for row in summary}
    p2_scores = {row["disease"]: row.get("phase2_score", None) for row in summary}

    data: dict = {}
    for mode in MODES:
        mode_dir = showcase_dir / mode
        if not mode_dir.is_dir():
            continue
        for run_dir in sorted(mode_dir.iterdir()):
            if not run_dir.is_dir():
                continue
            # folder name: <disease>_<provider>_phase3
            parts = run_dir.name.split("_phase3")[0].rsplit("_", 1)
            disease = parts[0] if parts else run_dir.name
            metrics = load_run_metrics(run_dir)
            if metrics is None:
                continue
            if disease not in data:
                data[disease] = {"best_p2": best_p2.get(disease, "?"), "phase2_score": p2_scores.get(disease)}
            data[disease][mode] = metrics

    return data


def _table_header(mode_label: str) -> list[str]:
    lines = [
        f"### {mode_label}",
        "",
        "| Disease | Best P2 | P2 Comp R | P3 Comp R | Δ Comp | P2 Flow R | P3 Flow R | Δ Flow | Param gaps↓ |",
        "|---------|---------|-----------|-----------|--------|-----------|-----------|--------|-------------|",
    ]
    return lines


def _row(disease: str, best_p2_extractor: str, m: dict) -> str:
    display = DISEASE_DISPLAY.get(disease, disease)
    fc = m["filled"]["compartments"]
    ff = m["filled"]["flows"]

    p2cr = best_p2_recall(disease, "compartments")
    p2fr = best_p2_recall(disease, "flows")
    p3cr = fc.get("recall")
    p3fr = ff.get("recall")

    def sgn(v): return (("+" if v >= 0 else "") + f"{v:.2f}") if v is not None else "—"
    dc = (p3cr - p2cr) if (p3cr is not None and p2cr is not None) else None
    df = (p3fr - p2fr) if (p3fr is not None and p2fr is not None) else None

    param_after = m.get("params_after")
    param_str = str(param_after) if param_after is not None else "—"

    return (
        f"| {display} | {best_p2_extractor} "
        f"| {_fmt(p2cr)} | **{_fmt(p3cr)}** | {sgn(dc)} "
        f"| {_fmt(p2fr)} | **{_fmt(p3fr)}** | {sgn(df)} "
        f"| {param_str} |"
    )


def build_md(data: dict, showcase_dir: Path, modes: list[str]) -> str:
    lines: list[str] = []
    showcase_name = showcase_dir.name

    lines += [
        f"# Phase 3 Recall Summary — {showcase_name}",
        "",
        "**Primary metric: Recall** — fraction of gold-standard compartments/flows that appear in the "
        "Phase 3 filled model (`filled_vs_gold`). Shown as **Recall** / Precision / F1.",
        "",
        "**Best P2** = which Phase 2 LLM (openai/gemini/claude) produced the best-scoring report "
        "used as input for Phase 3.",
        "",
        "**Param gaps after** = number of gold parameters still missing after Phase 3 fill.",
        "",
        "**Δ vs draft** = change in compartment Recall from Phase 2 draft to Phase 3 filled model.",
        "",
        "---",
        "",
    ]

    for mode in modes:
        if not any(mode in data[d] for d in data):
            continue
        mode_label = {
            "rag_only": "RAG only (no LLM inference)",
            "llm_only": "LLM only (no RAG)",
            "both": "RAG + LLM (both)",
        }.get(mode, mode)

        lines += _table_header(mode_label)

        all_fc_r, all_ff_r, all_fc_f1, all_ff_f1 = [], [], [], []
        for disease in DISEASE_ORDER:
            if disease not in data:
                continue
            entry = data[disease]
            m = entry.get(mode)
            if m is None:
                continue
            lines.append(_row(disease, entry["best_p2"], m))
            if m["filled"]["compartments"]["recall"] is not None:
                all_fc_r.append(m["filled"]["compartments"]["recall"])
                all_fc_f1.append(m["filled"]["compartments"]["f1"])
            if m["filled"]["flows"]["recall"] is not None:
                all_ff_r.append(m["filled"]["flows"]["recall"])
                all_ff_f1.append(m["filled"]["flows"]["f1"])

        # Averages row
        def avg(lst): return sum(lst) / len(lst) if lst else None
        all_p2c = [best_p2_recall(d, "compartments") for d in DISEASE_ORDER if d in data and data[d].get(mode)]
        all_p2f = [best_p2_recall(d, "flows") for d in DISEASE_ORDER if d in data and data[d].get(mode)]
        all_p2c = [v for v in all_p2c if v is not None]
        all_p2f = [v for v in all_p2f if v is not None]
        a_p2cr = avg(all_p2c); a_p2fr = avg(all_p2f)
        a_cr = avg(all_fc_r); a_ff = avg(all_ff_r)
        dc_avg = (a_cr - a_p2cr) if (a_cr and a_p2cr) else None
        df_avg = (a_ff - a_p2fr) if (a_ff and a_p2fr) else None
        sgn = lambda v: (("+" if v >= 0 else "") + f"{v:.2f}") if v is not None else "—"
        lines.append(
            f"| **Average** | — "
            f"| {_fmt(a_p2cr)} | **{_fmt(a_cr)}** | {sgn(dc_avg)} "
            f"| {_fmt(a_p2fr)} | **{_fmt(a_ff)}** | {sgn(df_avg)} | — |"
        )
        lines.append("")

    # --- Quick comparison across modes ---
    # Global Phase 2 baseline averages
    p2c_all = [best_p2_recall(d, "compartments") for d in DISEASE_ORDER if d in data]
    p2f_all = [best_p2_recall(d, "flows") for d in DISEASE_ORDER if d in data]
    p2c_all = [v for v in p2c_all if v is not None]
    p2f_all = [v for v in p2f_all if v is not None]
    def avg(lst): return sum(lst)/len(lst) if lst else None
    p2ca, p2fa = avg(p2c_all), avg(p2f_all)

    lines += [
        "---",
        "",
        "## Quick comparison: Phase 2 baseline vs Phase 3 modes (averages)",
        "",
        "| | Avg Comp Recall | Avg Comp F1 | Avg Flow Recall | Avg Flow F1 |",
        "|--|----------------|-------------|----------------|-------------|",
        f"| **Phase 2 best** | {_fmt(p2ca)} | — | {_fmt(p2fa)} | — |",
    ]
    for mode in modes:
        all_cr, all_cf1, all_fr, all_ff1 = [], [], [], []
        for disease in DISEASE_ORDER:
            if disease not in data:
                continue
            m = data[disease].get(mode)
            if m is None:
                continue
            if m["filled"]["compartments"]["recall"] is not None:
                all_cr.append(m["filled"]["compartments"]["recall"])
                all_cf1.append(m["filled"]["compartments"]["f1"])
            if m["filled"]["flows"]["recall"] is not None:
                all_fr.append(m["filled"]["flows"]["recall"])
                all_ff1.append(m["filled"]["flows"]["f1"])
        label = {"rag_only": "RAG only", "llm_only": "LLM only", "both": "**Both (RAG+LLM)**"}.get(mode, mode)
        dcr = avg(all_cr) - p2ca if (avg(all_cr) and p2ca) else None
        dfr = avg(all_fr) - p2fa if (avg(all_fr) and p2fa) else None
        sgn = lambda v: (("+" if v >= 0 else "") + f"{v:.2f}") if v is not None else ""
        lines.append(
            f"| {label} | **{_fmt(avg(all_cr))}** ({sgn(dcr)}) | {_fmt(avg(all_cf1))} "
            f"| **{_fmt(avg(all_fr))}** ({sgn(dfr)}) | {_fmt(avg(all_ff1))} |"
        )
    lines.append("")

    return "\n".join(lines)


def main():
    parser = argparse.ArgumentParser(description="Build Phase 3 recall summary markdown.")
    parser.add_argument("--showcase", type=str, default="showcase_gemini",
                        help="Showcase directory name (default: showcase_gemini)")
    parser.add_argument("--mode", type=str, choices=MODES + ["all"], default="all",
                        help="Which fill mode(s) to include (default: all)")
    parser.add_argument("-o", "--output", type=str, default=None,
                        help="Output .md filename (default: RESULTS_PHASE3_<showcase>.md)")
    args = parser.parse_args()

    showcase_dir = PHASE3_DIR / args.showcase
    if not showcase_dir.is_dir():
        print(f"Error: showcase directory not found: {showcase_dir}")
        return 1

    modes = MODES if args.mode == "all" else [args.mode]
    out_path = PHASE3_DIR / (args.output or f"RESULTS_PHASE3_{args.showcase.upper()}.md")

    print(f"Reading from: {showcase_dir}")
    data = load_showcase(showcase_dir)
    print(f"Found {len(data)} diseases across modes: {modes}")

    md = build_md(data, showcase_dir, modes)
    out_path.write_text(md, encoding="utf-8")
    print(f"Wrote {out_path}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
