"""
Evaluate the quality of gap-filling results.

Compares RAG/inference-filled **parameter** values against the gold standard
baseline, and reports **structural** recall/precision-style alignment for
**compartments** and **flows** (same importance as parameters).
"""

import re
from pathlib import Path
from typing import Any, Callable, Dict, List, Optional, Tuple


GREEK_TO_LATIN = {
    "α": "alpha", "β": "beta", "γ": "gamma", "δ": "delta",
    "ε": "epsilon", "ζ": "zeta", "η": "eta", "θ": "theta",
    "ι": "iota", "κ": "kappa", "λ": "lambda", "μ": "mu",
    "ν": "nu", "ξ": "xi", "π": "pi", "ρ": "rho",
    "σ": "sigma", "τ": "tau", "φ": "phi", "χ": "chi",
    "ψ": "psi", "ω": "omega",
}


def _normalize(s: str) -> str:
    for greek, latin in GREEK_TO_LATIN.items():
        s = s.replace(greek, latin)
    return re.sub(r"[^a-z0-9]", "", s.lower())


def _parse_numeric(val: Any) -> Optional[float]:
    if val is None:
        return None
    if isinstance(val, (int, float)):
        return float(val)
    if isinstance(val, str):
        val = val.strip()
        m = re.search(r"[-+]?[0-9]*\.?[0-9]+(?:[eE][-+]?\d+)?", val)
        if m:
            try:
                return float(m.group())
            except ValueError:
                pass
    return None


def evaluate_single(
    filled_value: Any,
    gold_value: Any,
    filled_range: Optional[List[float]] = None,
) -> Dict[str, Any]:
    """Compare a single filled value against the gold standard value."""
    g = _parse_numeric(filled_value)
    a = _parse_numeric(gold_value)

    if g is None or a is None:
        return {
            "filled_value": filled_value,
            "gold_value": gold_value,
            "error_abs": None,
            "error_rel_pct": None,
            "within_range": None,
            "match_quality": "no_comparison" if a is None else "no_fill",
        }

    error_abs = abs(g - a)
    error_rel = (error_abs / abs(a) * 100) if a != 0 else (100.0 if g != 0 else 0.0)

    within_range = None
    if filled_range and len(filled_range) >= 2:
        lo, hi = min(filled_range[:2]), max(filled_range[:2])
        within_range = lo <= a <= hi

    if error_rel < 1:
        quality = "exact"
    elif error_rel < 10:
        quality = "close"
    elif error_rel < 50:
        quality = "approximate"
    else:
        quality = "poor"

    return {
        "filled_value": g,
        "gold_value": a,
        "error_abs": round(error_abs, 6),
        "error_rel_pct": round(error_rel, 2),
        "within_range": within_range,
        "match_quality": quality,
    }


def _build_gold_param_map(gold_standard: Dict[str, Any]) -> Dict[str, Dict[str, Any]]:
    """Build a lookup map from gold standard: normalized_name -> {name, value, unit, ...}."""
    param_map: Dict[str, Dict[str, Any]] = {}
    for p in gold_standard.get("parameters", []):
        if isinstance(p, str):
            param_map[_normalize(p)] = {"name": p, "value": None}
        elif isinstance(p, dict):
            name = p.get("name", "")
            param_map[_normalize(name)] = p
    return param_map


def evaluate_filled_gaps(
    filled_result: Dict[str, Any],
    gold_standard: Optional[Dict[str, Any]] = None,
) -> Dict[str, Any]:
    """
    Evaluate all filled gaps against the gold standard.

    gold_standard: dict with 'parameters' list from load_model_structure()
                   (each param has name, expression/value, unit, etc.)

    Returns: {evaluations: [...], summary: {...}}
    """
    if not gold_standard:
        return {"evaluations": [], "summary": {"count": 0, "note": "no gold standard"}}

    gold_params = _build_gold_param_map(gold_standard)
    evaluations: List[Dict[str, Any]] = []

    for item in filled_result.get("filled_gaps", []):
        gap = item.get("gap", {})
        gap_type = item.get("gap_type", "")
        source = item.get("source", "")
        suggestion = item.get("suggestion") or {}

        if gap_type != "missing_parameters":
            continue
        if source == "flagged":
            continue

        expected = gap.get("expected", "") or gap.get("promised", "")
        if not expected:
            continue

        norm_key = _normalize(expected)
        gold_entry = gold_params.get(norm_key)
        if not gold_entry:
            for gk, gv in gold_params.items():
                if norm_key in gk or gk in norm_key:
                    gold_entry = gv
                    break

        gold_value = None
        if gold_entry:
            gold_value = gold_entry.get("expression") or gold_entry.get("value")

        filled_value = suggestion.get("value")
        filled_range = suggestion.get("range") or None
        if filled_range is None and suggestion.get("range_low") is not None:
            filled_range = [suggestion.get("range_low"), suggestion.get("range_high")]

        ev = evaluate_single(filled_value, gold_value, filled_range)
        ev["parameter"] = expected
        ev["source"] = source
        evaluations.append(ev)

    # Summary
    compared = [e for e in evaluations if e.get("error_rel_pct") is not None]
    exact_count = sum(1 for e in compared if e["match_quality"] == "exact")
    close_count = sum(1 for e in compared if e["match_quality"] == "close")
    approx_count = sum(1 for e in compared if e["match_quality"] == "approximate")
    poor_count = sum(1 for e in compared if e["match_quality"] == "poor")

    summary = {
        "total_filled": len(evaluations),
        "compared": len(compared),
        "not_compared": len(evaluations) - len(compared),
        "exact": exact_count,
        "close": close_count,
        "approximate": approx_count,
        "poor": poor_count,
    }
    if compared:
        errors = sorted(e["error_rel_pct"] for e in compared)
        summary["median_rel_error_pct"] = round(
            errors[len(errors) // 2], 2
        )
        summary["mean_rel_error_pct"] = round(
            sum(errors) / len(errors), 2
        )
        summary["accuracy_pct"] = round(
            100 * (exact_count + close_count) / len(compared), 1
        )

    return {"evaluations": evaluations, "summary": summary}


def _fuzzy_match(a: str, b: str) -> bool:
    """Loose name match (same normalization as gap_detector)."""
    na = _normalize(a)
    nb = _normalize(b)
    if not na or not nb:
        return False
    if na == nb or na in nb or nb in na:
        return True
    return False


def _fuzzy_match_any(query: str, candidates: List[str]) -> bool:
    return any(_fuzzy_match(query, c) for c in candidates)


def _fuzzy_match_flow(gold_sig: str, candidates: List[str]) -> bool:
    if "->" not in gold_sig:
        return _fuzzy_match_any(gold_sig, candidates)
    ga, _, gb = gold_sig.partition("->")
    ga, gb = ga.strip(), gb.strip()
    for c in candidates:
        if "->" not in c:
            continue
        ca, _, cb = c.partition("->")
        if _fuzzy_match(ga, ca.strip()) and _fuzzy_match(gb, cb.strip()):
            return True
    return False


def evaluate_structural_alignment(
    gold_compmodel_path: Path,
    candidate_compmodel_path: Path,
) -> Dict[str, Any]:
    """
    Compare **compartments** and **flows** in a candidate model file to the
    gold-standard ``.compmodel`` (fuzzy matching on names / flow arrows).

    This does not depend on which gaps were filled — it scores the whole model.
    """
    from src.gap_analysis.gap_detector import load_model_structure

    if not gold_compmodel_path.exists() or not candidate_compmodel_path.exists():
        return {"note": "missing paths", "compartments": {}, "flows": {}}

    g = load_model_structure(gold_compmodel_path)
    m = load_model_structure(candidate_compmodel_path)

    g_comps = [str(x) for x in g.get("compartments", [])]
    m_comps = [str(x) for x in m.get("compartments", [])]
    g_flows = [str(x) for x in g.get("flows", [])]
    m_flows = [str(x) for x in m.get("flows", [])]

    def prf_block(gold: List[str], cand: List[str], is_flow: bool) -> Dict[str, Any]:
        match_fn = _fuzzy_match_flow if is_flow else lambda a, bs: _fuzzy_match_any(a, bs)
        tp_g = sum(1 for x in gold if match_fn(x, cand))
        tp_c = sum(1 for x in cand if match_fn(x, gold))
        prec = tp_c / len(cand) if cand else (1.0 if not gold else 0.0)
        rec = tp_g / len(gold) if gold else 1.0
        f1 = (2 * prec * rec / (prec + rec)) if (prec + rec) > 0 else 0.0
        return {
            "gold_count": len(gold),
            "candidate_count": len(cand),
            "matched_to_gold": tp_g,
            "matched_from_candidate": tp_c,
            "precision": round(prec, 4),
            "recall": round(rec, 4),
            "f1": round(f1, 4),
        }

    return {
        "compartments": prf_block(g_comps, m_comps, is_flow=False),
        "flows": prf_block(g_flows, m_flows, is_flow=True),
    }


def evaluate_phase3_full(
    filled_result: Dict[str, Any],
    gold_standard: Optional[Dict[str, Any]],
    gold_compmodel_path: Optional[Path],
    draft_compmodel_path: Optional[Path],
    filled_compmodel_path: Optional[Path],
) -> Dict[str, Any]:
    """
    Parameter fill quality + structural alignment (draft vs gold, filled vs gold).
    """
    param_eval = evaluate_filled_gaps(filled_result, gold_standard=gold_standard)

    structural: Dict[str, Any] = {}
    if gold_compmodel_path and draft_compmodel_path:
        structural["draft_vs_gold"] = evaluate_structural_alignment(
            gold_compmodel_path, draft_compmodel_path
        )
    if gold_compmodel_path and filled_compmodel_path and filled_compmodel_path.exists():
        structural["filled_vs_gold"] = evaluate_structural_alignment(
            gold_compmodel_path, filled_compmodel_path
        )

    merged_summary = dict(param_eval.get("summary") or {})
    best_struct = structural.get("filled_vs_gold") or structural.get("draft_vs_gold")
    if best_struct:
        merged_summary["compartments_f1"] = best_struct.get("compartments", {}).get("f1")
        merged_summary["flows_f1"] = best_struct.get("flows", {}).get("f1")

    out: Dict[str, Any] = {
        "parameter_fills": param_eval,
        "structural_alignment": structural,
        # Backward compatibility (older scripts expect top-level evaluations / summary)
        "evaluations": param_eval.get("evaluations", []),
        "summary": merged_summary,
    }
    return out


def compute_completeness_score(
    gaps_before: Dict[str, Any],
    gaps_after: Optional[Dict[str, Any]],
    filled_result: Dict[str, Any],
    validation_summary: Dict[str, Any],
    threelayer: Optional[Dict[str, Any]] = None,
    structural_before: Optional[Dict[str, Any]] = None,
    structural_after: Optional[Dict[str, Any]] = None,
) -> Dict[str, Any]:
    """
    0–100 completeness score for a Phase 3 run, mirroring the paper's composite:

      Component                   Weight  Source
      ─────────────────────────── ──────  ─────────────────────────────────────
      Gap reduction               25%     (gaps_before − gaps_after) / gaps_before
      Reference agreement         25%     mean recall (compartments + params + flows)
      Fill traceability           20%     fraction of fills with paper evidence
                                          (source ∈ {rag, spec_entity})
      Parameter accuracy          15%     fraction exact+close among compared fills
      Structural integrity        15%     1 − (structural_errors_after / max(errors_before,1))

    The structural integrity component is new (Phase RLM-inspired): it measures how
    many of the model's internal structural errors were resolved by the repair step.
    When structural checks are unavailable, its weight is redistributed to the others.
    """
    # ── Gap reduction ─────────────────────────────────────────────────────
    before_total = int((gaps_before.get("summary") or gaps_before).get("total_gaps", 0))
    after_total = int((gaps_after.get("summary") if gaps_after else gaps_before.get("summary") or gaps_before).get("total_gaps", before_total))
    gap_reduction = ((before_total - after_total) / before_total * 100) if before_total > 0 else 100.0
    gap_reduction = max(0.0, min(100.0, gap_reduction))

    # ── Reference agreement ───────────────────────────────────────────────
    c_recall = float(validation_summary.get("compartments_recall") or 0.0)
    p_recall = float(validation_summary.get("parameters_recall") or 0.0)
    f_recall = float(validation_summary.get("flows_recall") or 0.0)
    c_f1 = float(validation_summary.get("compartments_f1") or 0.0)
    f_f1 = float(validation_summary.get("flows_f1") or 0.0)
    ref_agreement = (c_recall + p_recall + f_recall) / 3 * 100 if any([c_recall, p_recall, f_recall]) else (c_f1 + f_f1) / 2 * 100

    # ── Fill traceability ─────────────────────────────────────────────────
    fills = filled_result.get("filled_gaps", [])
    total_fills = len(fills)
    traceable = sum(1 for f in fills if f.get("source") in ("rag", "spec_entity"))
    traceability = (traceable / total_fills * 100) if total_fills > 0 else 100.0

    # ── Parameter accuracy ────────────────────────────────────────────────
    compared = int(validation_summary.get("compared", 0))
    exact = int(validation_summary.get("exact", 0))
    close = int(validation_summary.get("close", 0))
    param_accuracy = ((exact + close) / compared * 100) if compared > 0 else 100.0

    # ── Structural integrity (Phase RLM-inspired) ─────────────────────────
    # Measures the % of structural errors resolved by the repair step.
    # 100 = no structural errors after repair; 0 = all errors remain.
    struct_available = (
        structural_before is not None and structural_before.get("available")
        and structural_after is not None and structural_after.get("available")
    )
    if struct_available:
        n_before_struct = int(structural_before.get("total_errors", 0))
        n_after_struct = int(structural_after.get("total_errors", 0))
        if n_before_struct == 0:
            struct_integrity = 100.0
        else:
            struct_integrity = (1.0 - n_after_struct / n_before_struct) * 100.0
        struct_integrity = max(0.0, min(100.0, struct_integrity))
        # Use 5-component weights
        score = (
            gap_reduction    * 0.25
            + ref_agreement  * 0.25
            + traceability   * 0.20
            + param_accuracy * 0.15
            + struct_integrity * 0.15
        )
        weights = {
            "gap_reduction": 0.25, "reference_agreement": 0.25,
            "traceability": 0.20, "parameter_accuracy": 0.15,
            "structural_integrity": 0.15,
        }
    else:
        # Structural check unavailable — redistribute its 15% to gap_reduction + ref_agreement
        struct_integrity = None
        score = (
            gap_reduction    * 0.30
            + ref_agreement  * 0.30
            + traceability   * 0.20
            + param_accuracy * 0.20
        )
        weights = {
            "gap_reduction": 0.30, "reference_agreement": 0.30,
            "traceability": 0.20, "parameter_accuracy": 0.20,
        }

    # Phase 2 draft score (before fills): reference agreement only, no gap reduction
    draft_ref = (c_f1 + f_f1) / 2 * 100
    phase2_score = max(0.0, min(100.0, draft_ref))

    components: Dict[str, Any] = {
        "gap_reduction_pct": round(gap_reduction, 1),
        "reference_agreement_pct": round(ref_agreement, 1),
        "fill_traceability_pct": round(traceability, 1),
        "parameter_accuracy_pct": round(param_accuracy, 1),
    }
    if struct_integrity is not None:
        components["structural_integrity_pct"] = round(struct_integrity, 1)
        components["structural_errors_before"] = int(structural_before.get("total_errors", 0)) if structural_before else 0
        components["structural_errors_after"] = int(structural_after.get("total_errors", 0)) if structural_after else 0

    return {
        "completeness_score": round(score, 1),
        "components": components,
        "weights": weights,
        "gap_counts": {"before": before_total, "after": after_total},
        "spec_layer": {
            "spec_vs_model_total": (threelayer or {}).get("spec_vs_model", {}).get("summary", {}).get("total", 0),
            "model_vs_gold_total": (threelayer or {}).get("model_vs_gold", {}).get("summary", {}).get("total", 0),
            "extra_in_model_total": (threelayer or {}).get("extra_in_model", {}).get("summary", {}).get("total", 0),
        } if threelayer else {},
    }
