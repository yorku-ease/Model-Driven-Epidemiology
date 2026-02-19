"""
Task 8.4: Generate Human-Readable Gap Reports

- Per-disease gap report
- Overall summary report across all diseases and providers
"""

import json
from pathlib import Path
from typing import Any, Dict, List, Optional


def generate_gap_report(
    gaps: Dict[str, Any],
    filled: Dict[str, Any],
    report_path: Path,
    disease_hint: str = "",
    validation: Optional[Dict[str, Any]] = None,
) -> str:
    """Write a human-readable gap report to report_path (Markdown)."""
    lines: List[str] = []
    lines.append("# Phase 3 Gap Analysis Report")
    lines.append("")
    if disease_hint:
        lines.append(f"**Disease / context:** {disease_hint}")
        lines.append("")
    summary = gaps.get("summary", {})
    mode = gaps.get("comparison_mode", "promises")
    lines.append(f"**Comparison mode:** {mode}")
    lines.append("")

    # Section 1: Gap summary
    lines.append("## 1. Gap summary")
    lines.append(f"- Total gaps: **{summary.get('total_gaps', 0)}**")
    lines.append(f"- Missing compartments: {summary.get('missing_compartments', 0)}")
    lines.append(f"- Missing parameters: {summary.get('missing_parameters', 0)}")
    lines.append(f"- Missing stratifications: {summary.get('missing_stratifications', 0)}")
    lines.append(f"- Missing interventions: {summary.get('missing_interventions', 0)}")
    if summary.get("extra_compartments") or summary.get("extra_parameters"):
        lines.append(f"- Extra compartments (not in gold standard): {summary.get('extra_compartments', 0)}")
        lines.append(f"- Extra parameters (not in gold standard): {summary.get('extra_parameters', 0)}")
    lines.append("")

    # Section 2: Required vs optional
    lines.append("## 2. Required vs optional")
    for k, v in (gaps.get("required_vs_optional") or {}).items():
        lines.append(f"- **{k}**: {v}")
    lines.append("")

    # Section 3: Missing compartments
    lines.append("## 3. Missing compartments")
    for g in gaps.get("missing_compartments", []):
        label = g.get("expected", "") or g.get("promised", "")
        lines.append(f"- **{label}** — {g.get('reason', '')} (severity: {g.get('severity', '')})")
    if not gaps.get("missing_compartments"):
        lines.append("- None")
    lines.append("")

    # Section 4: Missing parameters
    lines.append("## 4. Missing parameters")
    for g in gaps.get("missing_parameters", []):
        label = g.get("expected", "") or g.get("promised", "")
        lines.append(f"- **{label}** — {g.get('reason', '')}")
    if not gaps.get("missing_parameters"):
        lines.append("- None")
    lines.append("")

    # Section 4b: Extra items
    if gaps.get("extra_compartments") or gaps.get("extra_parameters"):
        lines.append("## 4b. Extra items (extracted but not in gold standard)")
        if gaps.get("extra_compartments"):
            lines.append(f"- Extra compartments: {', '.join(gaps['extra_compartments'])}")
        if gaps.get("extra_parameters"):
            lines.append(f"- Extra parameters: {', '.join(gaps['extra_parameters'])}")
        lines.append("")

    # Section 5: Gap filling
    lines.append("## 5. Gap filling results")
    fill_summary = filled.get("summary", {})
    lines.append(f"- Filled via **RAG**: {fill_summary.get('rag_count', 0)}")
    lines.append(f"- Filled via **inference**: {fill_summary.get('inference_count', 0)}")
    lines.append(f"- **Flagged** for manual review: {fill_summary.get('flagged_count', 0)}")
    lines.append("")

    for item in filled.get("filled_gaps", []):
        gap = item.get("gap", {})
        gap_type = item.get("gap_type", "")
        source = item.get("source", "")
        sug = item.get("suggestion") or {}
        label = gap.get("expected", "") or gap.get("promised", "")
        lines.append(f"### {label} ({gap_type})")
        lines.append(f"- **Source:** {source}")
        if source == "rag" and sug.get("value") is not None:
            lines.append(f"- **Value:** {sug.get('value')} {sug.get('unit', '') or ''}")
            if sug.get("description"):
                lines.append(f"- **Description:** {sug['description']}")
            if sug.get("sources"):
                lines.append(f"- **From papers:** {', '.join(str(s) for s in sug['sources'][:3])}")
            if sug.get("note"):
                lines.append(f"- *{sug['note']}*")
        elif source == "inference":
            lines.append(f"- **Value:** {sug.get('value')} {sug.get('unit', '') or ''}")
            if sug.get("reasoning"):
                lines.append(f"- **Reasoning:** {sug['reasoning']}")
            if sug.get("confidence"):
                lines.append(f"- **Confidence:** {sug['confidence']}")
        else:
            lines.append(f"- **Action:** {sug.get('action', 'manual_review')} — {sug.get('reason', '')}")
        lines.append("")

    # Section 6: Validation
    if validation and validation.get("evaluations"):
        lines.append("## 6. Fill validation (vs gold standard)")
        vs = validation.get("summary", {})
        lines.append(f"- Parameters compared: **{vs.get('compared', 0)}**")
        lines.append(f"- Exact match (<1% error): **{vs.get('exact', 0)}**")
        lines.append(f"- Close (<10% error): **{vs.get('close', 0)}**")
        lines.append(f"- Approximate (<50% error): **{vs.get('approximate', 0)}**")
        lines.append(f"- Poor (>50% error): **{vs.get('poor', 0)}**")
        if vs.get("accuracy_pct") is not None:
            lines.append(f"- **Accuracy (exact+close)**: **{vs['accuracy_pct']}%**")
        if vs.get("median_rel_error_pct") is not None:
            lines.append(f"- Median relative error: **{vs['median_rel_error_pct']}%**")
        lines.append("")

        lines.append("| Parameter | Filled | Gold | Error % | Quality |")
        lines.append("|-----------|--------|------|---------|---------|")
        for ev in validation["evaluations"]:
            param = ev.get("parameter", "")
            fv = ev.get("filled_value", "—")
            gv = ev.get("gold_value", "—")
            err = ev.get("error_rel_pct")
            err_str = f"{err}%" if err is not None else "—"
            quality = ev.get("match_quality", "—")
            lines.append(f"| {param} | {fv} | {gv} | {err_str} | {quality} |")
        lines.append("")

    report_content = "\n".join(lines)
    Path(report_path).parent.mkdir(parents=True, exist_ok=True)
    with open(report_path, "w", encoding="utf-8") as f:
        f.write(report_content)
    return report_content


def _display_name(disease: str) -> str:
    """Auto-generate a display name from a disease slug."""
    special = {"hiv": "HIV", "covid": "COVID-19", "tb": "TB"}
    if disease.lower() in special:
        return special[disease.lower()]
    return disease.replace("_", " ").title()


def generate_overall_report(
    reports_dir: Path,
    output_path: Path,
    db_info: Optional[Dict[str, Any]] = None,
) -> str:
    """
    Scan all per-disease Phase 3 report dirs and produce a single overall summary.

    Expects reports_dir to contain subdirs like <disease>_<provider>_phase3/ each with
    phase3_gaps.json and phase3_filled.json.
    """
    reports_dir = Path(reports_dir)
    rows: List[Dict[str, Any]] = []

    for sub in sorted(reports_dir.iterdir()):
        if not sub.is_dir() or not sub.name.endswith("_phase3"):
            continue
        gaps_path = sub / "phase3_gaps.json"
        filled_path = sub / "phase3_filled.json"
        if not gaps_path.exists():
            continue

        parts = sub.name.replace("_phase3", "").rsplit("_", 1)
        disease = parts[0] if parts else sub.name
        provider = parts[1] if len(parts) > 1 else "?"

        with open(gaps_path, "r") as f:
            gaps = json.load(f)
        filled = {}
        if filled_path.exists():
            with open(filled_path, "r") as f:
                filled = json.load(f)

        gs = gaps.get("summary", {})
        fs = filled.get("summary", {})

        # Load validation if available
        val_path = sub / "phase3_validation.json"
        val = {}
        if val_path.exists():
            with open(val_path, "r") as f:
                val = json.load(f)
        val_s = val.get("summary", {})

        rows.append({
            "disease": disease,
            "provider": provider,
            "total_gaps": gs.get("total_gaps", 0),
            "mode": gaps.get("comparison_mode", "promises"),
            "rag": fs.get("rag_count", 0),
            "inference": fs.get("inference_count", 0),
            "flagged": fs.get("flagged_count", 0),
            "missing_comp": len(gaps.get("missing_compartments", [])),
            "missing_param": len(gaps.get("missing_parameters", [])),
            "missing_strat": len(gaps.get("missing_stratifications", [])),
            "missing_interv": len(gaps.get("missing_interventions", [])),
            "extra_comp": len(gaps.get("extra_compartments", [])),
            "extra_param": len(gaps.get("extra_parameters", [])),
            "val_compared": val_s.get("compared", 0),
            "val_exact": val_s.get("exact", 0),
            "val_close": val_s.get("close", 0),
            "val_approx": val_s.get("approximate", 0),
            "val_poor": val_s.get("poor", 0),
            "val_mean_err": val_s.get("mean_rel_error_pct"),
            "val_median_err": val_s.get("median_rel_error_pct"),
            "val_accuracy": val_s.get("accuracy_pct"),
        })

    if not rows:
        content = "# Phase 3 Overall Report\n\nNo per-disease reports found.\n"
        output_path.parent.mkdir(parents=True, exist_ok=True)
        output_path.write_text(content)
        return content

    diseases = sorted({r["disease"] for r in rows})
    providers = sorted({r["provider"] for r in rows})
    lookup = {(r["disease"], r["provider"]): r for r in rows}

    lines: List[str] = []
    lines.append("# Phase 3 — Overall Gap Analysis Report")
    lines.append("")

    # Database info
    if db_info:
        lines.append("## Database")
        lines.append(f"- Entries: **{db_info.get('num_entries', '?')}**")
        lines.append(f"- Parameters indexed: **{db_info.get('num_parameters', '?')}**")
        lines.append(f"- Text chunks: **{db_info.get('total_chunks', '?')}**")
        lines.append(f"- Diseases: {', '.join(db_info.get('diseases', []))}")
        lines.append("")

    # Summary stats
    total_gaps = sum(r["total_gaps"] for r in rows)
    total_rag = sum(r["rag"] for r in rows)
    total_infer = sum(r["inference"] for r in rows)
    total_flag = sum(r["flagged"] for r in rows)
    zero_gap_count = sum(1 for r in rows if r["total_gaps"] == 0)
    gold_count = sum(1 for r in rows if r.get("mode") == "gold_standard")
    promise_count = len(rows) - gold_count

    lines.append("## Summary")
    lines.append(f"- Reports analysed: **{len(rows)}** ({len(diseases)} diseases x {len(providers)} providers)")
    lines.append(f"- Comparison mode: **{gold_count}** gold-standard, **{promise_count}** promise-based")
    lines.append(f"- Reports with **zero gaps**: **{zero_gap_count} / {len(rows)}** ({100*zero_gap_count//len(rows) if rows else 0}%)")
    lines.append(f"- Total gaps across all reports: **{total_gaps}**")
    lines.append(f"- Filled: RAG: **{total_rag}** | Inference: **{total_infer}** | Flagged: **{total_flag}**")
    lines.append("")

    # Per-disease x provider table
    lines.append("## Gap counts by disease and provider")
    lines.append("")
    header = "| Disease |"
    sep = "|---------|"
    for prov in providers:
        header += f" {prov.capitalize()} |"
        sep += "--------|"
    lines.append(header)
    lines.append(sep)

    for d in diseases:
        disp = _display_name(d)
        row_str = f"| {disp:<12} |"
        for prov in providers:
            r = lookup.get((d, prov))
            if r is None:
                row_str += " — |"
            elif r["total_gaps"] == 0:
                row_str += " 0 |"
            else:
                detail = f"{r['total_gaps']} (RAG:{r['rag']} inf:{r['inference']} flag:{r['flagged']})"
                row_str += f" {detail} |"
        lines.append(row_str)

    # Totals row
    row_str = "| **Total** |"
    for prov in providers:
        prov_rows = [r for r in rows if r["provider"] == prov]
        t = sum(r["total_gaps"] for r in prov_rows)
        row_str += f" **{t}** |"
    lines.append(row_str)
    lines.append("")

    # Gap breakdown
    lines.append("## Gap breakdown (all reports combined)")
    lines.append("")
    mc = sum(r["missing_comp"] for r in rows)
    mp = sum(r["missing_param"] for r in rows)
    ms = sum(r["missing_strat"] for r in rows)
    mi = sum(r["missing_interv"] for r in rows)
    lines.append(f"| Category | Count |")
    lines.append(f"|----------|-------|")
    lines.append(f"| Missing compartments | {mc} |")
    lines.append(f"| Missing parameters | {mp} |")
    lines.append(f"| Missing stratifications | {ms} |")
    lines.append(f"| Missing interventions | {mi} |")
    lines.append(f"| **Total** | **{mc+mp+ms+mi}** |")
    lines.append("")

    # Validation accuracy
    val_rows = [r for r in rows if r["val_compared"] > 0]
    if val_rows:
        total_compared = sum(r["val_compared"] for r in val_rows)
        total_exact = sum(r["val_exact"] for r in val_rows)
        total_close = sum(r["val_close"] for r in val_rows)
        total_approx = sum(r["val_approx"] for r in val_rows)
        total_poor = sum(r["val_poor"] for r in val_rows)
        accuracy = round(100 * (total_exact + total_close) / max(1, total_compared), 1)
        med_vals = [r["val_median_err"] for r in val_rows if r.get("val_median_err") is not None]
        median_err = round(sorted(med_vals)[len(med_vals) // 2], 2) if med_vals else None

        lines.append("## Fill validation accuracy (vs gold standard)")
        lines.append("")
        lines.append(f"- Parameters compared: **{total_compared}**")
        lines.append(f"- Exact (<1% error): **{total_exact}** ({100*total_exact//max(1,total_compared)}%)")
        lines.append(f"- Close (<10% error): **{total_close}**")
        lines.append(f"- Approximate (<50% error): **{total_approx}**")
        lines.append(f"- Poor (>50% error): **{total_poor}**")
        lines.append(f"- **Accuracy (exact+close)**: **{accuracy}%**")
        if median_err is not None:
            lines.append(f"- Median relative error: **{median_err}%**")
        lines.append("")

        lines.append("| Disease | Provider | Compared | Exact | Close | Approx | Poor | Accuracy |")
        lines.append("|---------|----------|----------|-------|-------|--------|------|----------|")
        for r in sorted(val_rows, key=lambda x: (x["disease"], x["provider"])):
            disp = _display_name(r["disease"])
            acc = round(100 * (r["val_exact"] + r["val_close"]) / max(1, r["val_compared"]), 0)
            lines.append(f"| {disp} | {r['provider'].capitalize()} | {r['val_compared']} | {r['val_exact']} | {r['val_close']} | {r['val_approx']} | {r['val_poor']} | {acc}% |")
        lines.append("")

        # By-provider summary (aggregate validation and fills per provider)
        lines.append("### By provider (validation)")
        lines.append("")
        lines.append("| Provider | Gaps | RAG | Inference | Flagged | Compared | Exact | Accuracy |")
        lines.append("|----------|------|-----|-----------|---------|----------|-------|----------|")
        for prov in providers:
            prov_rows = [r for r in rows if r["provider"] == prov]
            tg = sum(r["total_gaps"] for r in prov_rows)
            rag = sum(r["rag"] for r in prov_rows)
            inf = sum(r["inference"] for r in prov_rows)
            fl = sum(r["flagged"] for r in prov_rows)
            comp = sum(r["val_compared"] for r in prov_rows)
            ex = sum(r["val_exact"] for r in prov_rows)
            acc_prov = round(100 * ex / comp, 1) if comp else "—"
            lines.append(f"| {prov.capitalize()} | {tg} | {rag} | {inf} | {fl} | {comp} | {ex} | {acc_prov}% |")
        lines.append("")
        lines.append("Accuracy = exact matches / compared (vs gold standard). RAG supplies most fills; inference is used when RAG finds nothing.")
        lines.append("")

    # Interpretation
    lines.append("## Interpretation")
    lines.append("")
    if zero_gap_count == len(rows):
        lines.append("All extracted models are **complete** relative to the gold standard / promises. "
                      "No compartments, parameters, stratifications, or interventions are missing.")
    elif zero_gap_count > len(rows) * 0.8:
        gap_reports = [r for r in rows if r["total_gaps"] > 0]
        lines.append(f"**{zero_gap_count}/{len(rows)}** reports have zero gaps — the extraction pipeline captures "
                      f"nearly everything.")
        lines.append("")
        lines.append("Reports with remaining gaps:")
        for r in gap_reports:
            disp = _display_name(r["disease"])
            lines.append(f"- **{disp}** ({r['provider']}): {r['total_gaps']} gaps "
                          f"(comp:{r['missing_comp']} param:{r['missing_param']} "
                          f"strat:{r['missing_strat']} interv:{r['missing_interv']})")
    else:
        lines.append(f"**{zero_gap_count}/{len(rows)}** reports are gap-free. "
                      f"The remaining **{len(rows)-zero_gap_count}** reports have a total of **{total_gaps}** gaps.")
    lines.append("")
    lines.append("- **Gap counts** depend on the Phase 2 extractor (different LLMs extract different parameters), so totals vary by provider.")
    lines.append("- **Fills** come from RAG first (paper database lookup); when RAG finds nothing, LLM inference is tried; the rest are flagged for manual review.")
    lines.append("- **Validation** compares only *filled parameter values* to the gold-standard baseline; compartments/stratifications are not valued, so they are not in the accuracy counts.")
    lines.append("- For per-disease details, open the corresponding `reports/<disease>_<provider>_phase3/gap_report.md` and `phase3_validation.json`.")
    lines.append("")

    content = "\n".join(lines)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text(content, encoding="utf-8")
    return content
