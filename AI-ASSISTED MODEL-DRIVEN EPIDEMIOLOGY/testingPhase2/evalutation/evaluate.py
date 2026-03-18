"""
evaluate.py
===========
Entry point for evaluating LLM-extracted compartmental models.

Loads gold standard from .compmodel XML, loads extracted model from JSON,
runs semantic matching evaluation, returns a detailed JSON report.

Report structure:
  - meta:            run info (disease, case, provider, threshold, timestamp)
  - scores:          per-entity F1/precision/recall + weighted composite
  - counts:          gold/extracted/tp/fp/fn per entity type
  - matches:         every matched pair with similarity score
  - false_positives: extracted items with no gold match (hallucinated)
  - false_negatives: gold items with no extracted match (missed)
  - direction_errors: flows matched only when reversed
  - value_errors:    parameters whose numeric values diverge > 5%
  - llm_oversight:   plain-language summary for human review
"""

import json
from datetime import datetime, timezone
from pathlib import Path
from typing import Dict, Any, Optional, Union

from evaluator import Evaluator, report_to_dict
from utils.xmlParsestojson import parse_compartmental_xml


# ---------------------------------------------------------------------------
# Loaders
# ---------------------------------------------------------------------------

def load_gold(source: str) -> Dict[str, Any]:
    """Load gold standard from .compmodel XML or canonical .json file."""
    path = str(source)
    if path.endswith(".json"):
        with open(path, "r", encoding="utf-8") as f:
            return json.load(f)
    else:
        with open(path, "r", encoding="utf-8") as f:
            xml_content = f.read()
        return parse_compartmental_xml(xml_content)


def load_extracted(source: str) -> Dict[str, Any]:
    """Load LLM-extracted model from JSON file."""
    with open(str(source), "r", encoding="utf-8") as f:
        return json.load(f)


# ---------------------------------------------------------------------------
# Report enrichment helpers
# ---------------------------------------------------------------------------

def _counts(gold_data: Dict, extracted_data: Dict, base: Dict) -> Dict:
    """Add raw totals alongside TP/FP/FN for each entity type."""
    return {
        "compartments": {
            "gold_total":      len(gold_data.get("compartments", [])),
            "extracted_total": len(extracted_data.get("compartments", [])),
            "tp": base["compartments"]["tp"],
            "fp": base["compartments"]["fp"],
            "fn": base["compartments"]["fn"],
        },
        "flows": {
            "gold_total":      len(gold_data.get("flows", [])),
            "extracted_total": len(extracted_data.get("flows", [])),
            "tp": base["flows"]["tp"],
            "fp": base["flows"]["fp"],
            "fn": base["flows"]["fn"],
        },
        "parameters": {
            "gold_total":      len(gold_data.get("parameters", [])),
            "extracted_total": len(extracted_data.get("parameters", [])),
            "tp": base["parameters"]["tp"],
            "fp": base["parameters"]["fp"],
            "fn": base["parameters"]["fn"],
        },
    }


def _false_positives(base: Dict, extracted_data: Dict) -> Dict:
    """Extracted items that found no gold match — hallucinated entities."""
    matched_comps  = {m["extracted"] for m in base["compartment_matches"]}
    matched_flows  = {m["extracted"] for m in base["flow_matches"]}
    matched_params = {m["extracted"] for m in base["parameter_matches"]}

    fp_comps = [
        c["name"]
        for c in extracted_data.get("compartments", [])
        if c.get("name") not in matched_comps
    ]

    fp_flows = []
    for f in extracted_data.get("flows", []):
        fs = f"{f.get('source', '')} -> {f.get('target', '')}"
        if fs not in matched_flows:
            fp_flows.append(fs)

    fp_params = []
    for p in extracted_data.get("parameters", []):
        sym  = p.get("symbol", "")
        desc = p.get("description", "")
        label = f"{sym} {desc}".strip() if desc else sym
        if label not in matched_params:
            fp_params.append(label)

    return {"compartments": fp_comps, "flows": fp_flows, "parameters": fp_params}


def _false_negatives(base: Dict, gold_data: Dict) -> Dict:
    """Gold items that found no extracted match — missed by the LLM."""
    matched_comps  = {m["gold"] for m in base["compartment_matches"]}
    matched_flows  = {m["gold"] for m in base["flow_matches"]}
    matched_params = {m["gold"] for m in base["parameter_matches"]}

    fn_comps = [
        c["name"]
        for c in gold_data.get("compartments", [])
        if c.get("name") not in matched_comps
    ]

    fn_flows = []
    for f in gold_data.get("flows", []):
        fs = f"{f.get('source', '')} -> {f.get('target', '')}"
        if fs not in matched_flows:
            fn_flows.append(fs)

    fn_params = []
    for p in gold_data.get("parameters", []):
        sym  = p.get("symbol", "")
        desc = p.get("description", "")
        label = f"{sym} {desc}".strip() if desc else sym
        if label not in matched_params:
            fn_params.append(label)

    return {"compartments": fn_comps, "flows": fn_flows, "parameters": fn_params}


def _scores_block(base: Dict) -> Dict:
    """Clean scores dict with weights and rounded values."""
    weights = {"compartments": 0.425, "flows": 0.425, "parameters": 0.150}
    composite = sum(weights[k] * base[k]["f1"] for k in weights)
    result = {}
    for k in ("compartments", "flows", "parameters"):
        result[k] = {
            "precision": round(base[k]["precision"], 4),
            "recall":    round(base[k]["recall"],    4),
            "f1":        round(base[k]["f1"],        4),
            "weight":    weights[k],
        }
    result["composite"] = round(composite, 4)
    return result


def _llm_oversight(
    scores: Dict,
    fp: Dict,
    fn: Dict,
    direction_errors: list,
    value_errors: list,
) -> Dict:
    """Plain-language summary block for human review."""
    composite = scores["composite"]

    if composite >= 0.90:
        quality = "EXCELLENT"
    elif composite >= 0.75:
        quality = "GOOD"
    elif composite >= 0.55:
        quality = "MODERATE"
    else:
        quality = "POOR"

    def verdict(f1: float) -> str:
        if f1 >= 0.90: return "excellent"
        if f1 >= 0.75: return "good"
        if f1 >= 0.55: return "moderate"
        return "poor"

    observations = []
    total_fn = sum(len(fn[k]) for k in fn)
    total_fp = sum(len(fp[k]) for k in fp)

    if total_fn > 0:
        observations.append(
            f"Missed {len(fn['compartments'])} compartment(s), "
            f"{len(fn['flows'])} flow(s), "
            f"{len(fn['parameters'])} parameter(s) from gold standard."
        )
    if total_fp > 0:
        observations.append(
            f"Hallucinated {len(fp['compartments'])} compartment(s), "
            f"{len(fp['flows'])} flow(s), "
            f"{len(fp['parameters'])} parameter(s) not in gold standard."
        )
    if direction_errors:
        observations.append(
            f"{len(direction_errors)} flow(s) had source/target direction reversed vs gold."
        )
    if value_errors:
        observations.append(
            f"{len(value_errors)} parameter(s) had numeric values diverging "
            f">5% from gold after unit normalisation."
        )
    if not observations:
        observations.append("No significant issues detected.")

    # Surface top missed/hallucinated items for quick review (max 5 each)
    review_items = {}
    for label, items in [
        ("missed_compartments",       fn["compartments"]),
        ("missed_flows",              fn["flows"]),
        ("missed_parameters",         fn["parameters"]),
        ("hallucinated_compartments", fp["compartments"]),
        ("hallucinated_flows",        fp["flows"]),
        ("hallucinated_parameters",   fp["parameters"]),
    ]:
        if items:
            review_items[label] = items[:5]
            if len(items) > 5:
                review_items[f"{label}_total"] = len(items)

    return {
        "overall_quality": quality,
        "composite_score": composite,
        "entity_verdicts": {
            "compartments": verdict(scores["compartments"]["f1"]),
            "flows":        verdict(scores["flows"]["f1"]),
            "parameters":   verdict(scores["parameters"]["f1"]),
        },
        "observations":    observations,
        "review_items":    review_items,
        "flags": {
            "has_direction_errors": len(direction_errors) > 0,
            "has_value_errors":     len(value_errors) > 0,
            "has_hallucinations":   total_fp > 0,
            "has_missed_items":     total_fn > 0,
        },
    }


# ---------------------------------------------------------------------------
# Main evaluate function
# ---------------------------------------------------------------------------

def evaluate(
    gold_source: Union[str, Dict[str, Any]],
    extracted_source: Union[str, Dict[str, Any]],
    threshold: float = 0.72,
    model_name: str = "sentence-transformers/all-MiniLM-L6-v2",
    meta: Optional[Dict[str, Any]] = None,
) -> Dict[str, Any]:
    """
    Evaluate extracted model against gold standard.

    Args:
        gold_source:      Path to .compmodel XML / canonical JSON, or pre-parsed dict
        extracted_source: Path to extracted JSON file, or pre-parsed dict
        threshold:        Cosine similarity threshold (default 0.72)
        model_name:       Sentence-transformer model name
        meta:             Optional dict with run metadata (disease, case_id, provider...)

    Returns:
        Detailed evaluation report as a dictionary
    """
    if isinstance(gold_source, (str, Path)):
        gold_data = load_gold(str(gold_source))
    else:
        gold_data = gold_source

    if isinstance(extracted_source, (str, Path)):
        extracted_data = load_extracted(str(extracted_source))
    else:
        extracted_data = extracted_source

    evaluator  = Evaluator(threshold=threshold, model_name=model_name)
    raw_report = report_to_dict(evaluator.evaluate(gold_data, extracted_data))

    scores     = _scores_block(raw_report)
    counts     = _counts(gold_data, extracted_data, raw_report)
    fp         = _false_positives(raw_report, extracted_data)
    fn         = _false_negatives(raw_report, gold_data)
    dir_errors = raw_report.get("direction_errors", [])
    val_errors = raw_report.get("value_errors", [])
    oversight  = _llm_oversight(scores, fp, fn, dir_errors, val_errors)

    return {
        "meta": {
            **(meta or {}),
            "threshold":    threshold,
            "model_name":   model_name,
            "evaluated_at": datetime.now(timezone.utc).isoformat(),
        },
        "scores":           scores,
        "counts":           counts,
        "matches": {
            "compartments": raw_report.get("compartment_matches", []),
            "flows":        raw_report.get("flow_matches",        []),
            "parameters":   raw_report.get("parameter_matches",   []),
        },
        "false_positives":  fp,
        "false_negatives":  fn,
        "direction_errors": dir_errors,
        "value_errors":     val_errors,
        "llm_oversight":    oversight,
    }


# ---------------------------------------------------------------------------
# File-based convenience wrapper
# ---------------------------------------------------------------------------

def evaluate_from_files(
    gold_xml_path: str,
    extracted_json_path: str,
    output_path: Optional[str] = None,
    threshold: float = 0.72,
    meta: Optional[Dict[str, Any]] = None,
) -> Dict[str, Any]:
    """
    Evaluate extracted JSON against gold XML, optionally write report to disk.

    Args:
        gold_xml_path:       Path to gold standard .compmodel or .json file
        extracted_json_path: Path to LLM-extracted JSON file
        output_path:         Optional path to write JSON report
        threshold:           Cosine similarity threshold
        meta:                Optional metadata dict (disease, case_id, provider...)

    Returns:
        Detailed evaluation report as a dictionary
    """
    result = evaluate(gold_xml_path, extracted_json_path, threshold, meta=meta)

    if output_path:
        Path(output_path).parent.mkdir(parents=True, exist_ok=True)
        with open(output_path, "w", encoding="utf-8") as f:
            json.dump(result, f, indent=2, ensure_ascii=False)

    return result


# ---------------------------------------------------------------------------
# CLI entry point
# ---------------------------------------------------------------------------

if __name__ == "__main__":
    import sys

    if len(sys.argv) < 3:
        print(
            "Usage: python evaluate.py <gold.compmodel> <extracted.json> "
            "[output.json] [threshold] [disease] [case_id] [provider]"
        )
        sys.exit(1)

    gold_path      = sys.argv[1]
    extracted_path = sys.argv[2]
    output_path    = sys.argv[3] if len(sys.argv) > 3 else None
    threshold      = float(sys.argv[4]) if len(sys.argv) > 4 else 0.72

    cli_meta = {}
    if len(sys.argv) > 5: cli_meta["disease"]  = sys.argv[5]
    if len(sys.argv) > 6: cli_meta["case_id"]  = sys.argv[6]
    if len(sys.argv) > 7: cli_meta["provider"] = sys.argv[7]

    result = evaluate_from_files(
        gold_path, extracted_path, output_path, threshold, meta=cli_meta
    )
    print(json.dumps(result, indent=2, ensure_ascii=False))
