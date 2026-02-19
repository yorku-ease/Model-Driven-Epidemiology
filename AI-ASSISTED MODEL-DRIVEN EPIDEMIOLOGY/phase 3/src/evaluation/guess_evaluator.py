"""
Evaluate the quality of gap-filling results.

Compares RAG/inference-filled parameter values against the gold standard
baseline to measure accuracy.
"""

import re
from typing import Any, Dict, List, Optional, Tuple


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
