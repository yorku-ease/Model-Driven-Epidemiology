#!/usr/bin/env python3
"""Build RESULTS_REPORT.md from latest per-disease evaluation JSON per disease/provider.

Examples:
  python3 build_results_md.py
  python3 build_results_md.py --reports-dir reports -o RESULTS_REPORT_CURRENT.md
  python3 build_results_md.py --evaluation-json evaluation_report_rerun.json \\
      -o RESULTS_REPORT_RERUN.md
  python3 build_results_md.py --reports-dir old-reports -o RESULTS_REPORT_OLD.md \\
      --title "Phase 2 Evaluation Results (archived runs)"
"""
import argparse
import json
from pathlib import Path

DEFAULT_REPORTS_DIR = Path(__file__).resolve().parent / "reports"
DEFAULT_OUT_MD = Path(__file__).resolve().parent / "RESULTS_REPORT.md"
DEFAULT_EVAL_JSON = "evaluation_report.json"

DISEASES = [
    "cholera",
    "covid",
    "dengue",
    "ebola",
    "flu",
    "hiv",
    "malaria",
    "measles",
    "tuberculosis",
    "zika",
]
DISEASE_DISPLAY = {
    "cholera": "Cholera",
    "covid": "COVID-19",
    "dengue": "Dengue",
    "ebola": "Ebola",
    "flu": "Flu",
    "hiv": "HIV",
    "malaria": "Malaria",
    "measles": "Measles",
    "tuberculosis": "Tuberculosis",
    "zika": "Zika",
}


def get_latest_per_disease(reports_dir: Path, eval_filename: str):
    """For each (disease, provider) return path to latest run dir that contains eval_filename."""
    if not reports_dir.is_dir():
        return {}
    out = {}
    for subdir in reports_dir.iterdir():
        if not subdir.is_dir():
            continue
        if not (subdir / eval_filename).exists():
            continue
        name = subdir.name
        if not name.endswith(".json") and "_llm_" in name:
            parts = name.split("_")
            if len(parts) >= 4:
                # e.g. cholera_llm_openai_20260204_171722 or cholera_llm_claude_20260212_211328
                provider = parts[2]  # openai | gemini | claude
                if provider not in ("openai", "gemini", "claude"):
                    continue
                ts = "_".join(parts[3:])  # 20260204_171722
                disease = parts[0]
                if disease not in DISEASES:
                    continue
                key = (disease, provider)
                if key not in out or ts > out[key][0]:
                    out[key] = (ts, subdir)
    return {k: v[1] for k, v in out.items()}


def load_scores(report_dir: Path, eval_filename: str):
    path = report_dir / eval_filename
    if not path.exists():
        return None
    with open(path) as f:
        data = json.load(f)
    gc = data.get("gold_standard_comparison") or {}
    return {
        "compartments": gc.get("compartments") or {},
        "parameters": gc.get("parameters") or {},
        "flows": gc.get("flows") or {},
    }


def fmt_recall_first(p, r, f1):
    """Recall first and bold — primary metric (how much of the gold standard was found)."""
    return f"**{r:.2f}** / {p:.2f} / {f1:.2f}"


def metric_from_cell(cell_str: str, index: int) -> str:
    """Parse P/R/F1 from '**r** / p / f1' style cell (index 0=R, 1=P, 2=F1)."""
    if not cell_str or cell_str == "-":
        return "-"
    parts = [x.strip().replace("*", "") for x in cell_str.split("/")]
    if len(parts) <= index:
        return "-"
    return parts[index]


def main():
    parser = argparse.ArgumentParser(
        description="Aggregate Phase 2 evaluation (recall-primary) into a markdown report."
    )
    parser.add_argument(
        "--reports-dir",
        type=Path,
        default=DEFAULT_REPORTS_DIR,
        help=f"Directory with run folders (default: {DEFAULT_REPORTS_DIR.name}/)",
    )
    parser.add_argument(
        "-o",
        "--output",
        type=Path,
        default=DEFAULT_OUT_MD,
        help=f"Output markdown path (default: {DEFAULT_OUT_MD.name})",
    )
    parser.add_argument(
        "--title",
        default="Phase 2 Evaluation Results",
        help="H1 title for the report",
    )
    parser.add_argument(
        "-e",
        "--evaluation-json",
        type=str,
        default=DEFAULT_EVAL_JSON,
        metavar="FILENAME",
        help=(
            f"Evaluation JSON filename inside each report folder (default: {DEFAULT_EVAL_JSON}). "
            "Use evaluation_report_rerun.json after rerun_evaluation_only.py."
        ),
    )
    args = parser.parse_args()

    reports_dir = args.reports_dir.resolve()
    out_md = args.output
    if not out_md.is_absolute():
        out_md = (Path(__file__).resolve().parent / out_md).resolve()

    eval_filename = args.evaluation_json.strip() or DEFAULT_EVAL_JSON
    latest = get_latest_per_disease(reports_dir, eval_filename)
    # Collect per-disease scores
    openai_rows = []
    gemini_rows = []
    claude_rows = []
    for disease in DISEASES:
        display = DISEASE_DISPLAY[disease]
        for provider, rows in [("openai", openai_rows), ("gemini", gemini_rows), ("claude", claude_rows)]:
            key = (disease, provider)
            dirpath = latest.get(key)
            if not dirpath:
                rows.append((display, None))
                continue
            scores = load_scores(dirpath, eval_filename)
            if not scores:
                rows.append((display, None))
                continue
            c = scores["compartments"]
            p = scores["parameters"]
            fl = scores["flows"]
            row = (
                display,
                (
                    fmt_recall_first(c.get("precision", 0), c.get("recall", 0), c.get("f1", 0)),
                    fmt_recall_first(p.get("precision", 0), p.get("recall", 0), p.get("f1", 0)),
                    fmt_recall_first(fl.get("precision", 0), fl.get("recall", 0), fl.get("f1", 0)),
                ),
            )
            rows.append(row)

    # Averages (only over diseases we have data for)
    # Cell format: "**recall** / precision / f1" → index 0=R, 1=P, 2=F1
    def avg(rows, kind, metric):
        vals = []
        idx = {"recall": 0, "precision": 1, "f1": 2}[metric]
        for _, s in rows:
            if s is None:
                continue
            part = s[kind].split("/")[idx].strip().replace("*", "")
            vals.append(float(part))
        return round(sum(vals) / len(vals), 2) if vals else 0

    openai_avg = {}
    gemini_avg = {}
    claude_avg = {}
    for kind in (0, 1, 2):  # compartments, parameters, flows
        for metric in ("precision", "recall", "f1"):
            openai_avg[(kind, metric)] = avg(openai_rows, kind, metric)
            gemini_avg[(kind, metric)] = avg(gemini_rows, kind, metric)
            claude_avg[(kind, metric)] = avg(claude_rows, kind, metric)

    # Recall-only for quick summary (R / P / F1 order in cells → index 0 = recall)
    def recall_triple(trip):
        if not trip:
            return ("-", "-", "-")
        return tuple(metric_from_cell(trip[i], 0) for i in range(3))

    openai_recall = []
    gemini_recall = []
    claude_recall = []
    for i, disease in enumerate(DISEASES):
        display = DISEASE_DISPLAY[disease]
        o_row = openai_rows[i]
        g_row = gemini_rows[i]
        c_row = claude_rows[i]
        openai_recall.append((display, recall_triple(o_row[1])))
        gemini_recall.append((display, recall_triple(g_row[1])))
        claude_recall.append((display, recall_triple(c_row[1])))

    # Build markdown — **Recall first** (coverage of gold items; not missing structure)
    lines = [
        "## 1. Per-disease scores (**Recall** / Precision / F1)",
        "",
        "_Primary metric: **Recall** — fraction of gold-standard compartments, parameters, and flows that were retrieved. "
        "Higher recall = fewer missed items. Precision penalizes hallucinated extras; use it secondarily._",
        "",
        "### 1.1 OpenAI (GPT-4o-mini)",
        "",
        "| Disease      | Compartments (R / P / F1)   | Parameters (R / P / F1)     | Flows (R / P / F1)        |",
        "|-------------|-----------------------------|-----------------------------|----------------------------|",
    ]
    for (display, s) in openai_rows:
        if s is None:
            lines.append(f"| {display:<12} | - | - | - |")
        else:
            lines.append(f"| {display:<12} | {s[0]:<27} | {s[1]:<27} | {s[2]:<26} |")
    lines.extend([
        "",
        "### 1.2 Gemini (2.5 Pro / Flash)",
        "",
        "| Disease      | Compartments (R / P / F1)   | Parameters (R / P / F1)     | Flows (R / P / F1)        |",
        "|-------------|-----------------------------|-----------------------------|----------------------------|",
    ])
    for (display, s) in gemini_rows:
        if s is None:
            lines.append(f"| {display:<12} | - | - | - |")
        else:
            lines.append(f"| {display:<12} | {s[0]:<27} | {s[1]:<27} | {s[2]:<26} |")
    lines.extend([
        "",
        "### 1.3 Claude (Opus 4)",
        "",
        "| Disease      | Compartments (R / P / F1)   | Parameters (R / P / F1)     | Flows (R / P / F1)        |",
        "|-------------|-----------------------------|-----------------------------|----------------------------|",
    ])
    for (display, s) in claude_rows:
        if s is None:
            lines.append(f"| {display:<12} | - | - | - |")
        else:
            lines.append(f"| {display:<12} | {s[0]:<27} | {s[1]:<27} | {s[2]:<26} |")
    lines.extend([
        "",
        "---",
        "",
        f"## 2. Averages across {len(DISEASES)} diseases",
        "",
        "| Provider  | Metric     | Compartments (avg) | Parameters (avg) | Flows (avg) |",
        "|-----------|------------|--------------------|------------------|-------------|",
        f"| **OpenAI**  | **Recall** | **{openai_avg[(0,'recall')]:.2f}** | **{openai_avg[(1,'recall')]:.2f}** | **{openai_avg[(2,'recall')]:.2f}** |",
        f"|           | Precision | {openai_avg[(0,'precision')]:.2f} | {openai_avg[(1,'precision')]:.2f} | {openai_avg[(2,'precision')]:.2f} |",
        f"|           | F1        | {openai_avg[(0,'f1')]:.2f} | {openai_avg[(1,'f1')]:.2f} | {openai_avg[(2,'f1')]:.2f} |",
        f"| **Gemini**  | **Recall** | **{gemini_avg[(0,'recall')]:.2f}** | **{gemini_avg[(1,'recall')]:.2f}** | **{gemini_avg[(2,'recall')]:.2f}** |",
        f"|           | Precision | {gemini_avg[(0,'precision')]:.2f} | {gemini_avg[(1,'precision')]:.2f} | {gemini_avg[(2,'precision')]:.2f} |",
        f"|           | F1        | {gemini_avg[(0,'f1')]:.2f} | {gemini_avg[(1,'f1')]:.2f} | {gemini_avg[(2,'f1')]:.2f} |",
        f"| **Claude**  | **Recall** | **{claude_avg[(0,'recall')]:.2f}** | **{claude_avg[(1,'recall')]:.2f}** | **{claude_avg[(2,'recall')]:.2f}** |",
        f"|           | Precision | {claude_avg[(0,'precision')]:.2f} | {claude_avg[(1,'precision')]:.2f} | {claude_avg[(2,'precision')]:.2f} |",
        f"|           | F1        | {claude_avg[(0,'f1')]:.2f} | {claude_avg[(1,'f1')]:.2f} | {claude_avg[(2,'f1')]:.2f} |",
        "",
        "**Overall (mean recall — higher = fewer missed gold items):**",
        f"- Compartments recall: OpenAI **{openai_avg[(0,'recall')]:.2f}**, Gemini **{gemini_avg[(0,'recall')]:.2f}**, Claude **{claude_avg[(0,'recall')]:.2f}**",
        f"- Parameters recall: OpenAI **{openai_avg[(1,'recall')]:.2f}**, Gemini **{gemini_avg[(1,'recall')]:.2f}**, Claude **{claude_avg[(1,'recall')]:.2f}**",
        f"- Flows recall: OpenAI **{openai_avg[(2,'recall')]:.2f}**, Gemini **{gemini_avg[(2,'recall')]:.2f}**, Claude **{claude_avg[(2,'recall')]:.2f}**",
        "",
        "---",
        "",
        "## 3. Recall summary table (quick scan — Compartments / Parameters / Flows)",
        "",
        "| Disease       | OpenAI R (C / Par / Flow) | Gemini R (C / Par / Flow) | Claude R (C / Par / Flow) |",
        "|---------------|---------------------------|---------------------------|---------------------------|",
    ])
    claude_n = sum(1 for _, s in claude_rows if s is not None)
    for i in range(len(DISEASES)):
        display = DISEASE_DISPLAY[DISEASES[i]]
        o = openai_recall[i][1]
        g = gemini_recall[i][1]
        c = claude_recall[i][1]
        lines.append(f"| {display:<14} | {o[0]} / {o[1]} / {o[2]:<23} | {g[0]} / {g[1]} / {g[2]:<23} | {c[0]} / {c[1]} / {c[2]:<23} |")
    o_c = openai_avg[(0, "recall")]
    o_p = openai_avg[(1, "recall")]
    o_f = openai_avg[(2, "recall")]
    g_c = gemini_avg[(0, "recall")]
    g_p = gemini_avg[(1, "recall")]
    g_f = gemini_avg[(2, "recall")]
    c_c = claude_avg[(0, "recall")]
    c_p = claude_avg[(1, "recall")]
    c_f = claude_avg[(2, "recall")]
    claude_avg_suffix = f" ({claude_n})" if claude_n < len(DISEASES) and claude_n else ""
    lines.append(f"| **Avg recall** | **{o_c:.2f} / {o_p:.2f} / {o_f:.2f}**     | **{g_c:.2f} / {g_p:.2f} / {g_f:.2f}**     | **{c_c:.2f} / {c_p:.2f} / {c_f:.2f}**{claude_avg_suffix}     |")

    title = f"# {args.title}\n\n"
    intro = (
        f"**Source runs:** `{reports_dir.name}/` (latest timestamp per disease × provider).\n\n"
        f"**Evaluation file:** `{eval_filename}` in each run folder.\n\n"
        "**Primary metric: recall** — share of gold-standard compartments, parameters, and flows that appear in the "
        "extracted model (minimize misses). Values are shown as **Recall / Precision / F1** per category. "
        "Precision is secondary (it penalizes hallucinated extras).\n\n"
        "For pipeline **before/after** notes, see **INSTRUCTIONS.md** "
        "(appendix: *Phase 2: Before vs After — What Improved Results*).\n\n---\n\n"
    )
    out_md.parent.mkdir(parents=True, exist_ok=True)
    out_md.write_text(title + intro + "\n".join(lines))
    print("Wrote", out_md, f"(from `{eval_filename}` in each latest run)")


if __name__ == "__main__":
    main()
