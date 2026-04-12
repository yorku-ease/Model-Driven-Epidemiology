#!/usr/bin/env python3
"""
Build Phase 3 recall tables and Phase 2 draft vs Phase 3 filled comparison.

1. Reads ``phase3_validation.json`` / ``phase3_gaps.json`` under each mode run
   (default showcase directory: ``reports/``).
2. If ``showcase_summary.json`` exists, recomputes fuzzy P2 draft vs P3 filled vs gold,
   writes ``fuzzy_phase2_vs_phase3.json``, and appends that section to the markdown.

Usage:
  cd "phase 3"
  python3 build_phase3_results_md.py
  python3 build_phase3_results_md.py --showcase my_run -o RESULTS_PHASE3_CLAUDE.md
  python3 build_phase3_results_md.py --mode both --skip-fuzzy
"""

from __future__ import annotations

import argparse
import json
import math
import re
import xml.etree.ElementTree as ET
from difflib import SequenceMatcher
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple

PHASE3_DIR = Path(__file__).resolve().parent
PHASE2_DIR = PHASE3_DIR.parent / "phase 2"
MODES = ["retrieval_only", "llm_only", "both"]
LEGACY_MODE_ALIASES = {"rag_only": "retrieval_only"}

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


SYNONYMS: List[Tuple[str, ...]] = [
    ("exposed", "latent", "incubating"),
    ("infectious", "infected", "symptomatic", "infective"),
    ("recovered", "removed", "immune"),
    ("dead", "deceased", "death"),
    ("susceptible",),
    ("vector", "mosquito"),
]

GREEK_TO_LATIN = {
    "α": "alpha", "β": "beta", "γ": "gamma", "δ": "delta",
    "ε": "epsilon", "ζ": "zeta", "η": "eta", "θ": "theta",
    "ι": "iota", "κ": "kappa", "λ": "lambda", "μ": "mu",
    "ν": "nu", "ξ": "xi", "π": "pi", "ρ": "rho",
    "σ": "sigma", "τ": "tau", "φ": "phi", "χ": "chi",
    "ψ": "psi", "ω": "omega",
}


def _norm(s: str) -> str:
    """Lowercase, normalize Greek → Latin, strip non-alphanum."""
    for g, l in GREEK_TO_LATIN.items():
        s = s.replace(g, l)
    return re.sub(r"[^a-z0-9]", "", s.lower())


def _fuzzy_match(a: str, b: str) -> bool:
    """Structural name match: substring or synonym group."""
    an = _norm(a)
    bn = _norm(b)
    if not an or not bn:
        return False
    # Substring (handles 'recovered' ↔ 'recovered humans')
    if an in bn or bn in an:
        return True
    # Synonym groups
    a_words = set(re.split(r"[^a-z]+", a.lower()))
    b_words = set(re.split(r"[^a-z]+", b.lower()))
    for grp in SYNONYMS:
        if any(w in grp for w in a_words) and any(w in grp for w in b_words):
            return True
    return False


def _fuzzy_match_flow(sig_a: str, sig_b: str) -> bool:
    """Match A->B against C->D by fuzzy-matching each side."""
    if "->" not in sig_a or "->" not in sig_b:
        return _fuzzy_match(sig_a, sig_b)
    aa, _, ab = sig_a.partition("->")
    ba, _, bb = sig_b.partition("->")
    return _fuzzy_match(aa.strip(), ba.strip()) and _fuzzy_match(ab.strip(), bb.strip())


def _seq_match(a: str, b: str, threshold: float) -> float:
    return SequenceMatcher(None, _norm(a), _norm(b)).ratio()


def _load_json_file(path: Path) -> Dict[str, Any]:
    try:
        with open(path, "r", encoding="utf-8") as f:
            return json.load(f)
    except Exception:
        return {}


# ── XML parsing (identical for Phase 2 draft and Phase 3 filled) ───────────

def _parse_compmodel(path: Path) -> Dict[str, List[str]]:
    """Extract compartments, parameters, and flows from a .compmodel file."""
    raw = path.read_text(encoding="utf-8", errors="replace")
    if "xmlns:xsi" not in raw and "xsi:" in raw:
        raw = raw.replace(
            "xmlns:xmi=",
            'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xmi=',
        )
    try:
        root = ET.fromstring(raw)
    except ET.ParseError:
        return {"compartments": [], "parameters": [], "flows": []}

    def ltag(el: ET.Element) -> str:
        return el.tag.split("}")[-1] if "}" in el.tag else el.tag

    comp_order: List[str] = []
    for el in root:
        if ltag(el) == "compartments":
            p = (el.get("PrimaryName") or "").strip()
            if p:
                comp_order.append(p)

    parameters: List[str] = []
    for el in root.iter():
        if ltag(el) == "parameters":
            n = (el.get("name") or "").strip()
            if n and n.lower() not in ("none", "n/a"):
                parameters.append(n)

    flows: List[str] = []
    for el in root:
        if ltag(el) != "compartments":
            continue
        src = (el.get("PrimaryName") or "").strip()
        if not src:
            continue
        for child in el:
            if "flow" not in ltag(child).lower():
                continue
            tgt_ref = (child.get("target") or "").strip()
            m = re.search(r"compartments\.(\d+)", tgt_ref)
            if m:
                idx = int(m.group(1))
                if 0 <= idx < len(comp_order):
                    flows.append(f"{src}->{comp_order[idx]}")

    return {
        "compartments": list(dict.fromkeys(comp_order)),
        "parameters": list(dict.fromkeys(parameters)),
        "flows": list(dict.fromkeys(flows)),
    }


# ── Metrics ─────────────────────────────────────────────────────────────────

def _struct_metrics(extracted: List[str], gold: List[str], is_flow: bool = False) -> Dict[str, Any]:
    """Recall/precision using substring+synonym matching (same as gap_detector)."""
    match_fn = _fuzzy_match_flow if is_flow else _fuzzy_match
    tp_gold = sum(1 for g in gold if any(match_fn(g, e) for e in extracted))
    tp_cand = sum(1 for e in extracted if any(match_fn(e, g) for g in gold))
    prec = tp_cand / len(extracted) if extracted else (1.0 if not gold else 0.0)
    rec = tp_gold / len(gold) if gold else 1.0
    f1 = (2 * prec * rec / (prec + rec)) if (prec + rec) > 0 else 0.0
    unmatched_gold = [g for g in gold if not any(match_fn(g, e) for e in extracted)]
    unmatched_ext = [e for e in extracted if not any(match_fn(e, g) for g in gold)]
    return {
        "precision": round(prec, 4),
        "recall": round(rec, 4),
        "f1": round(f1, 4),
        "gold_count": len(gold),
        "extracted_count": len(extracted),
        "tp_gold": tp_gold,
        "unmatched_gold": unmatched_gold,
        "unmatched_extracted": unmatched_ext,
    }


def _param_metrics(extracted: List[str], gold: List[str], threshold: float) -> Dict[str, Any]:
    """Recall/precision for parameters: substring OR SequenceMatcher≥threshold."""
    def match(a: str, b: str) -> bool:
        an, bn = _norm(a), _norm(b)
        if an and bn and (an in bn or bn in an):
            return True
        return SequenceMatcher(None, an, bn).ratio() >= threshold

    tp_gold = sum(1 for g in gold if any(match(g, e) for e in extracted))
    tp_cand = sum(1 for e in extracted if any(match(e, g) for g in gold))
    prec = tp_cand / len(extracted) if extracted else (1.0 if not gold else 0.0)
    rec = tp_gold / len(gold) if gold else 1.0
    f1 = (2 * prec * rec / (prec + rec)) if (prec + rec) > 0 else 0.0
    unmatched_gold = [g for g in gold if not any(match(g, e) for e in extracted)]
    unmatched_ext = [e for e in extracted if not any(match(e, g) for g in gold)]
    return {
        "precision": round(prec, 4),
        "recall": round(rec, 4),
        "f1": round(f1, 4),
        "gold_count": len(gold),
        "extracted_count": len(extracted),
        "tp_gold": tp_gold,
        "unmatched_gold": unmatched_gold,
        "unmatched_extracted": unmatched_ext,
    }


def _find_baseline(disease: str, baseline_dir: Path) -> Optional[Path]:
    if not baseline_dir.is_dir():
        return None
    d = disease.lower()
    # 1) Exact stem match anywhere in tree (handles data/diseases/covid/covid1.compmodel)
    for cm in sorted(baseline_dir.glob(f"**/{d}.compmodel")):
        return cm
    # 2) Flat directory: stem contains disease name (legacy baseline_models/)
    for cm in sorted(baseline_dir.glob("*.compmodel")):
        if d in cm.stem.lower():
            return cm
    # 3) Subdirectory: stem contains disease name (data/diseases/<disease>/<stem>.compmodel)
    for cm in sorted(baseline_dir.glob("**/*.compmodel")):
        if d in cm.stem.lower():
            return cm
    return None


# ── Per-disease evaluation ──────────────────────────────────────────────────

def _evaluate_p2_draft_vs_p3_filled(
    disease: str,
    phase2_report_dir: Path,
    phase3_dir: Path,
    baseline_dir: Path,
    param_threshold: float,
) -> Dict[str, Any]:
    baseline = _find_baseline(disease, baseline_dir)
    if not baseline:
        return {"disease": disease, "error": f"No baseline for: {disease}"}

    phase2_draft = phase2_report_dir / "model_draft.compmodel"
    filled_model = phase3_dir / "model_filled.compmodel"

    if not phase2_draft.exists():
        return {"disease": disease, "error": f"Missing model_draft.compmodel: {phase2_draft}"}
    if not filled_model.exists():
        return {"disease": disease, "error": f"Missing model_filled.compmodel: {filled_model}"}

    gold = _parse_compmodel(baseline)
    p2 = _parse_compmodel(phase2_draft)
    p3 = _parse_compmodel(filled_model)
    phase3_model_used = filled_model.name  # track which file was evaluated

    # Compute with identical algorithm
    p2_metrics = {
        "compartments": _struct_metrics(p2["compartments"], gold["compartments"], is_flow=False),
        "parameters": _param_metrics(p2["parameters"], gold["parameters"], param_threshold),
        "flows": _struct_metrics(p2["flows"], gold["flows"], is_flow=True),
    }
    p3_metrics = {
        "compartments": _struct_metrics(p3["compartments"], gold["compartments"], is_flow=False),
        "parameters": _param_metrics(p3["parameters"], gold["parameters"], param_threshold),
        "flows": _struct_metrics(p3["flows"], gold["flows"], is_flow=True),
    }

    def rec(blob: Dict[str, Any], key: str) -> float:
        return float((blob.get(key) or {}).get("recall", 0.0))

    delta = {
        "compartments_recall_delta": round(rec(p3_metrics, "compartments") - rec(p2_metrics, "compartments"), 4),
        "parameters_recall_delta": round(rec(p3_metrics, "parameters") - rec(p2_metrics, "parameters"), 4),
        "flows_recall_delta": round(rec(p3_metrics, "flows") - rec(p2_metrics, "flows"), 4),
    }

    improvement = _load_json_file(phase3_dir / "phase3_improvement.json")

    return {
        "disease": disease,
        "phase2_report_dir": str(phase2_report_dir),
        "phase3_dir": str(phase3_dir),
        "baseline": str(baseline),
        "phase2_metrics": p2_metrics,
        "phase3_metrics": p3_metrics,
        "phase2_to_phase3_delta": delta,
        "phase3_improvement": improvement.get("summary", {}),
        "note": "Both Phase2 draft and Phase3 filled evaluated with identical substring+synonym matching vs same baseline.",
    }


def run_fuzzy_p2_vs_p3_evaluation(
    showcase_dir: Path,
    baseline_models_dir: Path,
    param_threshold: float,
) -> list[dict[str, Any]]:
    """Run draft vs filled comparison for each disease (winner mode from showcase_summary.json)."""
    summary_path = showcase_dir / "showcase_summary.json"
    summary = _load_json_file(summary_path)
    if not isinstance(summary, list):
        return []
    rows: list[dict[str, Any]] = []
    for item in summary:
        disease = item.get("disease")
        winner_mode = item.get("winner_mode")
        phase3_provider = item.get("phase3_llm_provider", "gemini")
        if not disease or not winner_mode:
            continue
        phase2_report = Path(item.get("best_phase2_report", ""))
        phase3_dir = showcase_dir / winner_mode / f"{disease}_{phase3_provider}_phase3"
        row = _evaluate_p2_draft_vs_p3_filled(
            disease=disease,
            phase2_report_dir=phase2_report,
            phase3_dir=phase3_dir,
            baseline_dir=baseline_models_dir,
            param_threshold=param_threshold,
        )
        row["winner_mode"] = winner_mode
        rows.append(row)
    return rows


def format_fuzzy_p2_vs_p3_markdown(rows: list[dict[str, Any]], param_threshold: float) -> str:
    lines: list[str] = [
        "## Phase 2 draft vs Phase 3 filled (same fuzzy algorithm vs gold)",
        "",
        "Both Phase 2 draft (`model_draft.compmodel`) and Phase 3 filled (`model_filled.compmodel`) are",
        "evaluated against the **same baseline** with **identical matching** (substring + synonym groups).",
        f"Parameter threshold: **{param_threshold}**.",
        "",
        "| Disease | Winner mode | Phase2 recall (C/P/F) | Phase3 recall (C/P/F) | ΔRecall (C/P/F) | Gap Δ |",
        "|---------|------------|----------------------|----------------------|----------------|-------|",
    ]
    sum_dc = sum_dp = sum_df = 0.0
    improved_total = 0
    valid_rows = [r for r in rows if "error" not in r]
    for r in rows:
        if "error" in r:
            lines.append(f"| {r.get('disease','?')} | {r.get('winner_mode','?')} | ERROR | ERROR | ERROR | — |")
            continue
        d = r["phase2_to_phase3_delta"]
        p2m = r["phase2_metrics"]
        p3m = r["phase3_metrics"]
        imp = r.get("phase3_improvement", {})
        gap_delta = imp.get("delta_total_gaps", 0)

        def fmt_rec(m: dict[str, Any], k: str) -> str:
            v = m.get(k, {}).get("recall", 0.0)
            return f"{v:.3f}"

        p2str = f"{fmt_rec(p2m,'compartments')}/{fmt_rec(p2m,'parameters')}/{fmt_rec(p2m,'flows')}"
        p3str = f"{fmt_rec(p3m,'compartments')}/{fmt_rec(p3m,'parameters')}/{fmt_rec(p3m,'flows')}"
        dc = d["compartments_recall_delta"]
        dp = d["parameters_recall_delta"]
        df = d["flows_recall_delta"]
        sum_dc += dc
        sum_dp += dp
        sum_df += df
        if dc > 0 or dp > 0 or df > 0:
            improved_total += 1
        dstr = f"{dc:+.3f}/{dp:+.3f}/{df:+.3f}"
        lines.append(
            f"| {r['disease']} | **{r['winner_mode']}** | {p2str} | {p3str} | {dstr} | {gap_delta:+d} |"
        )
    n = max(1, len(valid_rows))
    lines += [
        "",
        "### Aggregate (valid diseases only)",
        "",
        f"- Mean ΔRecall compartments: **{sum_dc/n:+.4f}**",
        f"- Mean ΔRecall parameters:   **{sum_dp/n:+.4f}**",
        f"- Mean ΔRecall flows:        **{sum_df/n:+.4f}**",
        f"- Diseases with any positive ΔRecall component: **{improved_total}/{n}**",
        "",
        "**Notes:** C = compartments, P = parameters, F = flows. Gap Δ = gaps before − gaps after Phase 3.",
    ]
    return "\n".join(lines)


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


def load_fuzzy_p2_recalls(showcase_dir: Path) -> dict:
    """
    Load Phase 2 recall values from fuzzy_phase2_vs_phase3.json (same algorithm
    as Phase 3 evaluation — substring + synonym matching vs same gold standard).
    Returns {disease: {"compartments": recall, "flows": recall}}.
    """
    fuzzy_path = showcase_dir / "fuzzy_phase2_vs_phase3.json"
    if not fuzzy_path.exists():
        return {}
    try:
        rows = json.loads(fuzzy_path.read_text(encoding="utf-8"))
    except Exception:
        return {}
    result: dict = {}
    for row in rows:
        disease = row.get("disease", "")
        if not disease or "error" in row:
            continue
        p2m = row.get("phase2_metrics", {})
        result[disease] = {
            "compartments": (p2m.get("compartments") or {}).get("recall"),
            "flows": (p2m.get("flows") or {}).get("recall"),
            "compartments_f1": (p2m.get("compartments") or {}).get("f1"),
            "flows_f1": (p2m.get("flows") or {}).get("f1"),
        }
    return result


def best_p2_recall(disease: str, metric: str, fuzzy_p2: dict | None = None) -> float | None:
    """Phase 2 recall for a disease.

    Prefers fuzzy_phase2_vs_phase3.json (identical methodology to Phase 3 eval).
    Falls back to Phase 2 report folders: ``evaluation_report.json`` (fuzzy vs gold).
    """
    if fuzzy_p2 and disease in fuzzy_p2:
        return fuzzy_p2[disease].get(metric)
    reports = PHASE2_DIR / "reports"
    if not reports.is_dir():
        return None
    best = None
    for d in reports.iterdir():
        if not d.name.startswith(f"{disease}_llm_"):
            continue
        f = d / "evaluation_report.json"
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
      { disease: { mode: metrics_dict, "best_p2": str, "phase2_score": float,
                   "fuzzy_p2_comp_recall": float, "fuzzy_p2_flow_recall": float } }
    """
    summary_path = showcase_dir / "showcase_summary.json"
    summary = []
    if summary_path.exists():
        summary = json.loads(summary_path.read_text(encoding="utf-8"))

    best_p2 = {row["disease"]: row.get("best_phase2_extractor", "?") for row in summary}
    p2_scores = {row["disease"]: row.get("phase2_score", None) for row in summary}

    # Load P2 recalls from fuzzy comparison (identical methodology to Phase 3 eval)
    fuzzy_p2 = load_fuzzy_p2_recalls(showcase_dir)

    data: dict = {}
    for mode in MODES + list(LEGACY_MODE_ALIASES.keys()):
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
            canonical_mode = LEGACY_MODE_ALIASES.get(mode, mode)
            if disease not in data:
                data[disease] = {
                    "best_p2": best_p2.get(disease, "?"),
                    "phase2_score": p2_scores.get(disease),
                    "fuzzy_p2": fuzzy_p2.get(disease, {}),
                }
            # Prefer canonical folder name if both exist
            if canonical_mode not in data[disease]:
                data[disease][canonical_mode] = metrics

    return data


def _table_header(mode_label: str) -> list[str]:
    lines = [
        f"### {mode_label}",
        "",
        "| Disease | Best P2 | P2 Comp R | P3 Comp R | Δ Comp | P2 Flow R | P3 Flow R | Δ Flow | Param gaps↓ |",
        "|---------|---------|-----------|-----------|--------|-----------|-----------|--------|-------------|",
    ]
    return lines


def _row(disease: str, best_p2_extractor: str, m: dict, disease_fuzzy_p2: dict | None = None) -> str:
    """disease_fuzzy_p2 is already the per-disease dict: {compartments: recall, flows: recall}."""
    display = DISEASE_DISPLAY.get(disease, disease)
    fc = m["filled"]["compartments"]
    ff = m["filled"]["flows"]

    # Use fuzzy comparison P2 values when available (identical methodology to P3 eval)
    if disease_fuzzy_p2 and disease_fuzzy_p2.get("compartments") is not None:
        p2cr = disease_fuzzy_p2["compartments"]
        p2fr = disease_fuzzy_p2.get("flows")
    else:
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
            "retrieval_only": "Rule-Based Retrieval only (`retrieval_only`, no LLM inference)",
            "llm_only": "LLM only (no Rule-Based Retrieval)",
            "both": "Rule-Based Retrieval + LLM (`both`)",
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
            lines.append(_row(disease, entry["best_p2"], m, entry.get("fuzzy_p2")))
            if m["filled"]["compartments"]["recall"] is not None:
                all_fc_r.append(m["filled"]["compartments"]["recall"])
                all_fc_f1.append(m["filled"]["compartments"]["f1"])
            if m["filled"]["flows"]["recall"] is not None:
                all_ff_r.append(m["filled"]["flows"]["recall"])
                all_ff_f1.append(m["filled"]["flows"]["f1"])

        # Averages row
        def avg(lst): return sum(lst) / len(lst) if lst else None
        def _p2c(d):
            fp = data[d].get("fuzzy_p2", {})
            return fp.get("compartments") if fp.get("compartments") is not None else best_p2_recall(d, "compartments")
        def _p2f(d):
            fp = data[d].get("fuzzy_p2", {})
            return fp.get("flows") if fp.get("flows") is not None else best_p2_recall(d, "flows")
        all_p2c = [_p2c(d) for d in DISEASE_ORDER if d in data and data[d].get(mode)]
        all_p2f = [_p2f(d) for d in DISEASE_ORDER if d in data and data[d].get(mode)]
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
    # Global Phase 2 baseline averages (using fuzzy comparison where available)
    def _gp2c(d):
        fp = data[d].get("fuzzy_p2", {})
        return fp.get("compartments") if fp.get("compartments") is not None else best_p2_recall(d, "compartments")
    def _gp2f(d):
        fp = data[d].get("fuzzy_p2", {})
        return fp.get("flows") if fp.get("flows") is not None else best_p2_recall(d, "flows")
    p2c_all = [_gp2c(d) for d in DISEASE_ORDER if d in data]
    p2f_all = [_gp2f(d) for d in DISEASE_ORDER if d in data]
    p2c_all = [v for v in p2c_all if v is not None]
    p2f_all = [v for v in p2f_all if v is not None]
    def avg(lst): return sum(lst)/len(lst) if lst else None
    p2ca, p2fa = avg(p2c_all), avg(p2f_all)

    # Phase 2 average F1 (from fuzzy comparison)
    p2cf1_all = [data[d].get("fuzzy_p2", {}).get("compartments_f1")
                 for d in DISEASE_ORDER if d in data]
    p2ff1_all = [data[d].get("fuzzy_p2", {}).get("flows_f1")
                 for d in DISEASE_ORDER if d in data]
    p2cf1_all = [v for v in p2cf1_all if v is not None]
    p2ff1_all = [v for v in p2ff1_all if v is not None]
    p2cf1a, p2ff1a = avg(p2cf1_all), avg(p2ff1_all)

    lines += [
        "---",
        "",
        "## Quick comparison: Phase 2 baseline vs Phase 3 modes (averages)",
        "",
        "| | Avg Comp Recall | Avg Comp F1 | Avg Flow Recall | Avg Flow F1 |",
        "|--|----------------|-------------|----------------|-------------|",
        f"| **Phase 2 best** | {_fmt(p2ca)} | {_fmt(p2cf1a)} | {_fmt(p2fa)} | {_fmt(p2ff1a)} |",
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
        label = {
            "retrieval_only": "Rule-Based Retrieval only (`retrieval_only`)",
            "llm_only": "LLM only",
            "both": "**Both (Rule-Based Retrieval+LLM)**",
        }.get(mode, mode)
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
    parser = argparse.ArgumentParser(
        description="Build Phase 3 recall summary + optional Phase 2 draft vs Phase 3 filled comparison.",
    )
    parser.add_argument("--showcase", type=str, default="reports",
                        help="Showcase directory under phase 3 (default: reports)")
    parser.add_argument("--mode", type=str, choices=MODES + ["rag_only", "all"], default="all",
                        help="Which fill mode(s) to include (default: all)")
    parser.add_argument("-o", "--output", type=str, default=None,
                        help="Output .md filename (default: RESULTS_PHASE3.md)")
    parser.add_argument(
        "--skip-fuzzy",
        action="store_true",
        help="Skip writing fuzzy_phase2_vs_phase3.json and the P2 vs P3 section (uses existing fuzzy JSON if present)",
    )
    parser.add_argument(
        "--baseline-models-dir",
        type=Path,
        default=PHASE2_DIR / "data" / "diseases",
        help="Gold .compmodel directory for fuzzy P2 vs P3 comparison (default: phase 2/data/diseases)",
    )
    parser.add_argument(
        "--param-threshold",
        type=float,
        default=0.6,
        help="SequenceMatcher threshold for parameter names in fuzzy comparison (default 0.6)",
    )
    args = parser.parse_args()

    raw_show = Path(args.showcase)
    showcase_dir = raw_show.resolve() if raw_show.is_absolute() else (PHASE3_DIR / raw_show).resolve()
    if not showcase_dir.is_dir():
        print(f"Error: showcase directory not found: {showcase_dir}")
        return 1

    if args.mode == "all":
        modes = MODES
    else:
        modes = [LEGACY_MODE_ALIASES.get(args.mode, args.mode)]
    if args.output:
        raw_out = Path(args.output)
        out_path = raw_out.resolve() if raw_out.is_absolute() else (PHASE3_DIR / raw_out).resolve()
    else:
        out_path = PHASE3_DIR / "RESULTS_PHASE3.md"

    summary_path = showcase_dir / "showcase_summary.json"
    fuzzy_md = ""
    if summary_path.exists() and not args.skip_fuzzy:
        rows = run_fuzzy_p2_vs_p3_evaluation(
            showcase_dir, args.baseline_models_dir, args.param_threshold
        )
        out_fuzzy = showcase_dir / "fuzzy_phase2_vs_phase3.json"
        out_fuzzy.write_text(json.dumps(rows, indent=2, ensure_ascii=False), encoding="utf-8")
        print(f"Wrote {out_fuzzy}")
        fuzzy_md = format_fuzzy_p2_vs_p3_markdown(rows, args.param_threshold)
    elif not args.skip_fuzzy and not (showcase_dir / "showcase_summary.json").exists():
        print("Note: no showcase_summary.json — skipping fuzzy P2 vs P3 comparison.")

    print(f"Reading from: {showcase_dir}")
    data = load_showcase(showcase_dir)
    print(f"Found {len(data)} diseases across modes: {modes}")

    md = build_md(data, showcase_dir, modes)
    if fuzzy_md:
        md = md + "\n\n---\n\n" + fuzzy_md
    out_path.write_text(md, encoding="utf-8")
    print(f"Wrote {out_path}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
