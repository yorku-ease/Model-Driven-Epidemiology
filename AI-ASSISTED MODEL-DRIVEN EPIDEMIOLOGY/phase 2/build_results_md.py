#!/usr/bin/env python3
"""Build RESULTS_NEW_RUN.md from latest evaluation_report.json per disease/provider."""
import json
from pathlib import Path

REPORTS_DIR = Path(__file__).resolve().parent / "reports"
OUT_MD = Path(__file__).resolve().parent / "RESULTS_REPORT.md"

DISEASES = [
    "cholera",
    "dengue",
    "ebola",
    "flu",
    "measles",
    "tuberculosis",
    "zika",
]
DISEASE_DISPLAY = {
    "cholera": "Cholera",
    "dengue": "Dengue",
    "ebola": "Ebola",
    "flu": "Flu",
    "measles": "Measles",
    "tuberculosis": "Tuberculosis",
    "zika": "Zika",
}


def get_latest_per_disease():
    """For each (disease, provider) return path to latest evaluation_report.json dir."""
    if not REPORTS_DIR.is_dir():
        return {}
    out = {}
    for subdir in REPORTS_DIR.iterdir():
        if not subdir.is_dir():
            continue
        name = subdir.name
        if not name.endswith(".json") and "_llm_" in name:
            parts = name.split("_")
            if len(parts) >= 4:
                # e.g. cholera_llm_openai_20260204_171722
                provider = parts[2]  # openai | gemini
                if provider not in ("openai", "gemini"):
                    continue
                ts = "_".join(parts[3:])  # 20260204_171722
                disease = parts[0]
                if disease not in DISEASES:
                    continue
                key = (disease, provider)
                if key not in out or ts > out[key][0]:
                    out[key] = (ts, subdir)
    return {k: v[1] for k, v in out.items()}


def load_scores(report_dir):
    path = report_dir / "evaluation_report.json"
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


def fmt(p, r, f1):
    return f"{p:.2f} / {r:.2f} / **{f1:.2f}**"


def main():
    latest = get_latest_per_disease()
    # Collect per-disease scores
    openai_rows = []
    gemini_rows = []
    for disease in DISEASES:
        display = DISEASE_DISPLAY[disease]
        for provider, rows in [("openai", openai_rows), ("gemini", gemini_rows)]:
            key = (disease, provider)
            dirpath = latest.get(key)
            if not dirpath:
                rows.append((display, None))
                continue
            scores = load_scores(dirpath)
            if not scores:
                rows.append((display, None))
                continue
            c = scores["compartments"]
            p = scores["parameters"]
            fl = scores["flows"]
            row = (
                display,
                (
                    fmt(c.get("precision", 0), c.get("recall", 0), c.get("f1", 0)),
                    fmt(p.get("precision", 0), p.get("recall", 0), p.get("f1", 0)),
                    fmt(fl.get("precision", 0), fl.get("recall", 0), fl.get("f1", 0)),
                ),
            )
            rows.append(row)

    # Averages (only over diseases we have data for)
    def avg(rows, kind, metric):
        vals = []
        for _, s in rows:
            if s is None:
                continue
            idx = {"precision": 0, "recall": 1, "f1": 2}[metric]
            # parse from formatted string like "0.86 / 1.00 / **0.92**"
            part = s[kind].split("/")[idx].strip().strip("*")
            vals.append(float(part))
        return round(sum(vals) / len(vals), 2) if vals else 0

    openai_avg = {}
    gemini_avg = {}
    for kind in (0, 1, 2):  # compartments, parameters, flows
        for metric in ("precision", "recall", "f1"):
            openai_avg[(kind, metric)] = avg(
                openai_rows, kind, metric
            )
            gemini_avg[(kind, metric)] = avg(gemini_rows, kind, metric)

    # F1-only for summary
    openai_f1 = []
    gemini_f1 = []
    for i, disease in enumerate(DISEASES):
        display = DISEASE_DISPLAY[disease]
        o_row = openai_rows[i]
        g_row = gemini_rows[i]
        def f1_vals(trip):
            if not trip:
                return ("-", "-", "-")
            return tuple(trip[i].split("/")[2].strip().replace("*", "") for i in range(3))
        o_f1 = f1_vals(o_row[1])
        g_f1 = f1_vals(g_row[1])
        openai_f1.append((display, o_f1))
        gemini_f1.append((display, g_f1))

    # Build markdown
    lines = [
        "## 1. Per-disease scores (Precision / Recall / F1)",
        "",
        "### 1.1 OpenAI (GPT-4o-mini)",
        "",
        "| Disease      | Compartments (P / R / F1)   | Parameters (P / R / F1)     | Flows (P / R / F1)        |",
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
        "| Disease      | Compartments (P / R / F1)   | Parameters (P / R / F1)     | Flows (P / R / F1)        |",
        "|-------------|-----------------------------|-----------------------------|----------------------------|",
    ])
    for (display, s) in gemini_rows:
        if s is None:
            lines.append(f"| {display:<12} | - | - | - |")
        else:
            lines.append(f"| {display:<12} | {s[0]:<27} | {s[1]:<27} | {s[2]:<26} |")
    lines.extend([
        "",
        "---",
        "",
        "## 2. Averages across 7 diseases",
        "",
        "| Provider | Metric      | Compartments P / R / F1 (avg)     | Parameters P / R / F1 (avg)     | Flows P / R / F1 (avg)        |",
        "|----------|-------------|-----------------------------------|----------------------------------|--------------------------------|",
        f"| **OpenAI** | Precision  | **{openai_avg[(0,'precision')]:.2f}**                          | **{openai_avg[(1,'precision')]:.2f}**                         | **{openai_avg[(2,'precision')]:.2f}**                       |",
        f"|          | Recall      | **{openai_avg[(0,'recall')]:.2f}**                          | **{openai_avg[(1,'recall')]:.2f}**                         | **{openai_avg[(2,'recall')]:.2f}**                       |",
        f"|          | **F1**      | **{openai_avg[(0,'f1')]:.2f}**                          | **{openai_avg[(1,'f1')]:.2f}**                         | **{openai_avg[(2,'f1')]:.2f}**                       |",
        f"| **Gemini** | Precision  | **{gemini_avg[(0,'precision')]:.2f}**                          | **{gemini_avg[(1,'precision')]:.2f}**                         | **{gemini_avg[(2,'precision')]:.2f}**                       |",
        f"|          | Recall      | **{gemini_avg[(0,'recall')]:.2f}**                          | **{gemini_avg[(1,'recall')]:.2f}**                         | **{gemini_avg[(2,'recall')]:.2f}**                       |",
        f"|          | **F1**      | **{gemini_avg[(0,'f1')]:.2f}**                          | **{gemini_avg[(1,'f1')]:.2f}**                         | **{gemini_avg[(2,'f1')]:.2f}**                       |",
        "",
        "**Overall (both providers, mean of OpenAI and Gemini F1):**",
        f"- Compartments F1: **{(openai_avg[(0,'f1')] + gemini_avg[(0,'f1')])/2:.2f}**",
        f"- Parameters F1: **{(openai_avg[(1,'f1')] + gemini_avg[(1,'f1')])/2:.2f}**",
        f"- Flows F1: **{(openai_avg[(2,'f1')] + gemini_avg[(2,'f1')])/2:.2f}**",
        "",
        "---",
        "",
        "## 3. F1 summary table (for quick scan)",
        "",
        "| Disease       | OpenAI C / Par / Flow (F1) | Gemini C / Par / Flow (F1) |",
        "|---------------|----------------------------|----------------------------|",
    ])
    for i in range(len(DISEASES)):
        display = DISEASE_DISPLAY[DISEASES[i]]
        o = openai_f1[i][1]
        g = gemini_f1[i][1]
        lines.append(f"| {display:<14} | {o[0]} / {o[1]} / {o[2]:<25} | {g[0]} / {g[1]} / {g[2]:<25} |")
    o_c = openai_avg[(0, "f1")]
    o_p = openai_avg[(1, "f1")]
    o_f = openai_avg[(2, "f1")]
    g_c = gemini_avg[(0, "f1")]
    g_p = gemini_avg[(1, "f1")]
    g_f = gemini_avg[(2, "f1")]
    lines.append(f"| **Average**   | **{o_c:.2f} / {o_p:.2f} / {o_f:.2f}**     | **{g_c:.2f} / {g_p:.2f} / {g_f:.2f}**     |")

    title = "# Phase 2 Evaluation Results\n\n"
    intro = "This report summarizes precision (P), recall (R), and F1 for **compartments**, **parameters**, and **flows** against baseline `.compmodel` gold standards. Each row is the latest run per disease and provider.\n\n---\n\n"
    OUT_MD.write_text(title + intro + "\n".join(lines))
    print("Wrote", OUT_MD)


if __name__ == "__main__":
    main()
