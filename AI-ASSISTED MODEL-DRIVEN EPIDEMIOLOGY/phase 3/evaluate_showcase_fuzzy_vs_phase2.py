#!/usr/bin/env python3
"""
Evaluate Phase 3 filled models and compare recall against Phase 2 drafts.

Both Phase 2 (`model_draft.compmodel`) and Phase 3 (`model_filled.compmodel`) are
parsed with **identical** code and matched against the same baseline using the
same algorithm — so the ΔRecall numbers are honest.

Matching strategy (same logic as gap_detector.py):
  - Compartments / flows: substring match (handles "Recovered" ↔ "Recovered humans")
    + SYNONYMS group (Exposed/Latent, Infectious/Infected, Recovered/Removed, …)
  - Parameters: SequenceMatcher with threshold (0.6 default) + substring fallback

Outputs (inside --showcase-dir):
  - fuzzy_phase2_vs_phase3.json
  - FUZZY_PHASE2_VS_PHASE3_REPORT.md
"""

from __future__ import annotations

import argparse
import json
import re
import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple
from difflib import SequenceMatcher


# ── Same synonym groups as gap_detector.py ─────────────────────────────────
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


def _load_json(path: Path) -> Dict[str, Any]:
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
    for cm in sorted(baseline_dir.glob("*.compmodel")):
        if disease.lower() in cm.stem.lower():
            return cm
    return None


# ── Per-disease evaluation ──────────────────────────────────────────────────

def evaluate_one(
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
    # Prefer model_repaired.compmodel (after structural repair) over model_filled.compmodel
    repaired_model = phase3_dir / "model_repaired.compmodel"
    filled_model = repaired_model if repaired_model.exists() else phase3_dir / "model_filled.compmodel"

    if not phase2_draft.exists():
        return {"disease": disease, "error": f"Missing model_draft.compmodel: {phase2_draft}"}
    if not filled_model.exists():
        return {"disease": disease, "error": f"Missing model_filled/repaired.compmodel: {filled_model}"}

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

    improvement = _load_json(phase3_dir / "phase3_improvement.json")

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


# ── Main ────────────────────────────────────────────────────────────────────

def main() -> None:
    ap = argparse.ArgumentParser(
        description="Compare Phase 2 draft vs Phase 3 filled recall — same algorithm, same baseline."
    )
    ap.add_argument("--showcase-dir", type=Path, required=True)
    ap.add_argument(
        "--baseline-models-dir",
        type=Path,
        default=Path(__file__).resolve().parent.parent / "phase 2" / "data" / "baseline_models",
    )
    ap.add_argument(
        "--param-threshold",
        type=float,
        default=0.6,
        help="SequenceMatcher threshold for parameter name matching (default 0.6)",
    )
    args = ap.parse_args()

    summary_path = args.showcase_dir / "showcase_summary.json"
    summary = _load_json(summary_path)
    if not isinstance(summary, list):
        raise SystemExit(f"Invalid or missing {summary_path}")

    rows: List[Dict[str, Any]] = []
    for item in summary:
        disease = item.get("disease")
        winner_mode = item.get("winner_mode")
        phase3_provider = item.get("phase3_llm_provider", "gemini")
        if not disease or not winner_mode:
            continue
        phase2_report = Path(item.get("best_phase2_report", ""))
        phase3_dir = args.showcase_dir / winner_mode / f"{disease}_{phase3_provider}_phase3"
        row = evaluate_one(
            disease=disease,
            phase2_report_dir=phase2_report,
            phase3_dir=phase3_dir,
            baseline_dir=args.baseline_models_dir,
            param_threshold=args.param_threshold,
        )
        row["winner_mode"] = winner_mode
        rows.append(row)

    out_json = args.showcase_dir / "fuzzy_phase2_vs_phase3.json"
    out_json.write_text(json.dumps(rows, indent=2, ensure_ascii=False), encoding="utf-8")

    # Markdown report
    lines: List[str] = [
        "# Phase 2 Draft vs Phase 3 Filled: Recall Comparison",
        "",
        "Both Phase 2 draft (`model_draft.compmodel`) and Phase 3 filled (`model_filled.compmodel`) are",
        "evaluated against the **same baseline** with **identical matching** (substring + synonym groups).",
        f"Parameter threshold: **{args.param_threshold}**.",
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

        def fmt_rec(m: Dict[str, Any], k: str) -> str:
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
        "## Aggregate (valid diseases only)",
        "",
        f"- Mean ΔRecall compartments: **{sum_dc/n:+.4f}**",
        f"- Mean ΔRecall parameters:   **{sum_dp/n:+.4f}**",
        f"- Mean ΔRecall flows:        **{sum_df/n:+.4f}**",
        f"- Diseases with any positive ΔRecall component: **{improved_total}/{n}**",
        "",
        "## Notes",
        "",
        "- **C** = compartments, **P** = parameters, **F** = flows.",
        "- Matching: compartment/flow names use substring + SYNONYMS (e.g. 'Recovered' matches 'Recovered humans');",
        "  parameters use substring OR SequenceMatcher.",
        "- Gap Δ = (gaps before Phase 3) − (gaps after Phase 3): positive means fewer gaps.",
        "- ΔRecall > 0 means Phase 3 improved coverage vs the baseline for that entity type.",
    ]

    out_md = args.showcase_dir / "FUZZY_PHASE2_VS_PHASE3_REPORT.md"
    out_md.write_text("\n".join(lines), encoding="utf-8")

    print(f"Wrote: {out_json}")
    print(f"Wrote: {out_md}")


if __name__ == "__main__":
    main()
