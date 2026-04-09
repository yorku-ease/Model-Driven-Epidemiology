"""
Task 10.2: Create comprehensive report for the model.

Generate a final markdown report summarizing distributions, Monte Carlo
results, sensitivity, and links to plots.
"""

import json
from pathlib import Path
from typing import Any, Dict, List, Optional


def generate_report(
    disease: str,
    report_dir: Path,
    distributions_data: Dict[str, Any],
    monte_carlo_data: Optional[Dict[str, Any]] = None,
    sensitivity_data: Optional[Dict[str, Any]] = None,
    has_uncertainty_plot: bool = False,
    has_sensitivity_plot: bool = False,
    context: Optional[Dict[str, Any]] = None,
) -> str:
    """
    Write PHASE4_REPORT.md for the disease/paper.
    context may contain keys from phase3_showcase_source.json plus fill_mode and phase3_run_dir.
    """
    context = context or {}
    display = disease.replace("_", " ").title()
    fill_mode = context.get("fill_mode", "")
    if fill_mode == "rag_only":
        fill_mode = "retrieval_only"
    best_p2 = context.get("best_phase2_extractor", "")
    p2_score = context.get("phase2_score", "")
    p3_llm = context.get("phase3_llm_provider", "")
    p3_run = context.get("phase3_run_dir", "")

    lines = [
        f"# Phase 4 — Uncertainty quantification: {display}",
        "",
        "This report summarizes parameter distributions (general framework), Monte Carlo simulation, and sensitivity analysis.",
        "",
    ]

    # --- Provenance block ---
    if best_p2 or fill_mode or p2_score:
        lines += [
            "## Source model provenance",
            "",
            "| Field | Value |",
            "|-------|-------|",
        ]
        if best_p2:
            lines.append(f"| Best Phase 2 extractor | {best_p2} |")
        if p2_score:
            lines.append(f"| Phase 2 composite score | {p2_score:.2f} |")
        if p3_llm:
            lines.append(f"| Phase 3 LLM provider | {p3_llm} |")
        if fill_mode:
            lines.append(f"| Phase 3 fill mode | {fill_mode} |")
        if p3_run:
            lines.append(f"| Phase 3 run dir | `{Path(p3_run).name}` |")
        lines += ["", "---", ""]

    lines.append("## 1. Parameter distributions (Task 9.1)")
    lines.append("")
    lines.append("Distributions are assigned using the **general framework** (typed parameter uncertainty): same distribution families and typical ranges for similar parameter types across diseases.")
    lines.append("")
    dists = distributions_data.get("parameter_distributions", {})
    lines.append("| Parameter | Type | Family | Low | High | Point |")
    lines.append("|-----------|------|--------|-----|------|-------|")
    for name, spec in list(dists.items())[:25]:
        ptype = spec.get("parameter_type", "—")
        family = spec.get("family", "—")
        low = spec.get("low", "—")
        high = spec.get("high", "—")
        point = spec.get("point_estimate", "—")
        if low is not None and high is not None and point is None:
            point = (low + high) / 2
        lines.append(f"| {name} | {ptype} | {family} | {low} | {high} | {point} |")
    if len(dists) > 25:
        lines.append(f"| … | … | … | … | … | … (*{len(dists)} total*) |")
    lines.extend(["", "## 2. Monte Carlo simulation (Task 9.2)", ""])

    if monte_carlo_data and "error" not in monte_carlo_data:
        n = monte_carlo_data.get("n_samples", 0)
        days_mc = monte_carlo_data.get("days", "—")
        compartments = monte_carlo_data.get("compartment_names", [])
        lines.append(f"- **Samples:** {n}   **Days:** {days_mc}   **Compartments:** {', '.join(compartments) or '—'}")
        lines.append("")
        # Peak spread per compartment
        pct = monte_carlo_data.get("percentiles", {})
        if pct:
            lines += [
                "**Peak value spread (5th / 50th / 95th percentile across ensemble):**",
                "",
                "| Compartment | P5 peak | P50 peak | P95 peak |",
                "|-------------|---------|----------|----------|",
            ]
            for comp in compartments[:10]:
                traj_50 = pct.get("p50", {}).get(comp, [])
                traj_05 = pct.get("p05", {}).get(comp, [])
                traj_95 = pct.get("p95", {}).get(comp, [])
                pk50 = f"{max(traj_50):.2f}" if traj_50 else "—"
                pk05 = f"{max(traj_05):.2f}" if traj_05 else "—"
                pk95 = f"{max(traj_95):.2f}" if traj_95 else "—"
                lines.append(f"| {comp} | {pk05} | {pk50} | {pk95} |")
            lines.append("")
        if has_uncertainty_plot:
            lines.append("![Uncertainty bands](uncertainty_bands.png)")
            lines.append("")
    else:
        lines.append("- Monte Carlo not run or failed (check Phase 1 simulator and numpy).")
        lines.append("")

    lines.extend(["## 3. Sensitivity analysis (Task 9.3)", ""])
    if sensitivity_data and "error" not in sensitivity_data:
        most = sensitivity_data.get("most_influential", [])
        sensitivities = sensitivity_data.get("sensitivities", [])
        lines.append("**Parameter ranking by combined impact on peak infections + total cases:**")
        lines.append("")
        lines += [
            "| Rank | Parameter | Peak impact | Total cases impact | Combined |",
            "|------|-----------|------------|-------------------|---------|",
        ]
        for i, s in enumerate(sensitivities[:10], 1):
            pname = s.get("parameter", "—")
            peak = s.get("peak_sensitivity", 0)
            total = s.get("total_sensitivity", 0)
            combined = s.get("combined_importance", 0)
            lines.append(f"| {i} | {pname} | {peak:.4f} | {total:.4f} | {combined:.4f} |")
        lines.append("")
        if has_sensitivity_plot:
            lines.append("![Sensitivity tornado](sensitivity_tornado.png)")
            lines.append("")
    else:
        lines.append("Sensitivity analysis not run or failed.")
        lines.append("")

    lines.append("---")
    lines.append(f"*Generated by Phase 4 (uncertainty quantification). Model: `{disease}`. General framework: `phase 4/data/general_framework.json`.*")

    out_path = report_dir / "PHASE4_REPORT.md"
    report_dir.mkdir(parents=True, exist_ok=True)
    content = "\n".join(lines)
    out_path.write_text(content, encoding="utf-8")
    return content
