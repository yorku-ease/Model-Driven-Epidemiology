"""
Automatic Gap Detection

Two modes of comparison:

1) **Gold-standard mode** (preferred): Compare extracted entities against
   a baseline .compmodel file. This gives real, meaningful gaps — things
   that exist in the gold standard but the extraction missed.

2) **Promise mode** (fallback): Compare against paper_promises.json.
   Only useful if promises are actually populated.

Both modes also apply contextual checks (vector-borne, stratification).
"""

import json
import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Any, Dict, List, Optional


# ─── Vector-borne detection (no hardcoded disease names) ───────────────────

VECTOR_BORNE_STRONG = [
    "vector-borne", "mosquito population", "susceptible mosquitoes",
    "infectious mosquitoes", "biting rate", "mosquito compartment",
    "vector compartment", "mosquito dynamics",
]
VECTOR_BORNE_WEAK = [
    "mosquito", "arthropod",
]

STRATIFICATION_KEYWORDS = [
    "age-stratified", "age stratified", "age groups", "stratified by age",
    "stratified by gender", "risk groups",
]

SIMPLE_MODEL_PHRASES = [
    "simple seir", "basic seir", "simple sir", "basic sir",
    "minimal model", "simple model",
]


def _strip_references(text: str) -> str:
    """Remove references section from end of paper text."""
    for marker in ["references\n", "bibliography\n", "literature cited\n"]:
        idx = text.rfind(marker)
        if idx != -1 and idx > len(text) * 0.6:
            return text[:idx]
    return text


def _paper_is_vector_borne(promises: Dict[str, Any], paper_text: str) -> bool:
    model_type = (promises.get("model_type") or "").lower()
    if any(v in model_type for v in ["vector-borne", "vector borne"]):
        return True
    text = _strip_references((paper_text or "").lower())
    for kw in VECTOR_BORNE_STRONG:
        if kw in text:
            return True
    return sum(1 for kw in VECTOR_BORNE_WEAK if kw in text) >= 2


def _model_has_vector_compartments(model_structure: Dict[str, Any]) -> bool:
    comps = [c.lower() for c in model_structure.get("compartments", [])]
    return any("mosquito" in c or "vector" in c or "larva" in c for c in comps)


def _paper_promises_stratification(promises: Dict[str, Any], paper_text: str) -> bool:
    strat = promises.get("stratifications") or []
    if isinstance(strat, list) and strat:
        return True
    text = (paper_text or "").lower()
    return any(kw in text for kw in STRATIFICATION_KEYWORDS)


def _paper_expects_simple_model(promises: Dict[str, Any], paper_text: str) -> bool:
    text = (paper_text or "").lower()
    return any(phrase in text for phrase in SIMPLE_MODEL_PHRASES)


# ─── Loaders ───────────────────────────────────────────────────────────────

def load_paper_promises(path: Path) -> Dict[str, Any]:
    with open(path, "r", encoding="utf-8") as f:
        return json.load(f)


def load_extracted_entities(path: Path) -> Dict[str, Any]:
    with open(path, "r", encoding="utf-8") as f:
        return json.load(f)


def _local_tag(el: ET.Element) -> str:
    return el.tag.split("}")[-1] if "}" in el.tag else el.tag


def load_model_structure(compmodel_path: Path) -> Dict[str, Any]:
    """Extract compartments, parameters, stratifications from .compmodel XML."""
    raw = compmodel_path.read_text(encoding="utf-8", errors="replace")
    # Inject missing namespace declarations so the parser won't choke
    if "xmlns:xsi" not in raw and "xsi:" in raw:
        raw = raw.replace(
            "xmlns:xmi=",
            'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xmi=',
        )
    root = ET.fromstring(raw)
    compartments, parameters, stratifications = [], [], []
    for el in root.iter():
        tag = _local_tag(el)
        if tag == "compartments":
            name = el.get("PrimaryName", "")
            if name:
                compartments.append(name)
        elif tag == "parameters":
            name = el.get("name", "")
            if name and name.lower() not in ("none", "n/a", ""):
                parameters.append(name)
        elif tag == "groups":
            name = el.get("name", "")
            if name:
                stratifications.append(name)
    return {
        "compartments": list(dict.fromkeys(compartments)),
        "parameters": list(dict.fromkeys(parameters)),
        "stratifications": list(dict.fromkeys(stratifications)),
    }


# ─── Matching helpers ──────────────────────────────────────────────────────

SYNONYMS = [
    ("exposed", "latent", "incubating"),
    ("infectious", "infected", "symptomatic"),
    ("recovered", "removed", "immune"),
    ("dead", "deceased", "death"),
    ("susceptible",),
]

GREEK_TO_LATIN = {
    "α": "alpha", "β": "beta", "γ": "gamma", "δ": "delta",
    "ε": "epsilon", "ζ": "zeta", "η": "eta", "θ": "theta",
    "ι": "iota", "κ": "kappa", "λ": "lambda", "μ": "mu",
    "ν": "nu", "ξ": "xi", "π": "pi", "ρ": "rho",
    "σ": "sigma", "τ": "tau", "φ": "phi", "χ": "chi",
    "ψ": "psi", "ω": "omega",
}
LATIN_TO_GREEK = {v: k for k, v in GREEK_TO_LATIN.items()}


def _normalize_list(items: List[Any]) -> List[str]:
    out = []
    for x in items:
        if isinstance(x, str) and x.strip():
            out.append(x.strip().lower())
        elif isinstance(x, dict):
            v = x.get("normalized_name") or x.get("name") or x.get("dimension") or x.get("type") or ""
            if v.strip():
                out.append(v.strip().lower())
    return list(dict.fromkeys(out))


def _normalize_param_name(s: str) -> str:
    """Normalize Greek letters to Latin and strip non-alphanumeric."""
    for greek, latin in GREEK_TO_LATIN.items():
        s = s.replace(greek, latin)
    return s.lower().strip()


def _fuzzy_match(a: str, b: str) -> bool:
    a_norm = _normalize_param_name(a)
    b_norm = _normalize_param_name(b)
    if a_norm in b_norm or b_norm in a_norm:
        return True
    for group in SYNONYMS:
        if a_norm in group and b_norm in group:
            return True
    return False


def _fuzzy_match_any(query: str, candidates: List[str]) -> bool:
    return any(_fuzzy_match(query, c) for c in candidates)


# ─── Core gap detection ───────────────────────────────────────────────────

def detect_gaps(
    promises: Dict[str, Any],
    entities: Dict[str, Any],
    model_structure: Dict[str, Any],
    paper_text: str = "",
    gold_standard: Optional[Dict[str, Any]] = None,
) -> Dict[str, Any]:
    """
    Detect gaps using gold standard (if available) or promises (fallback).

    gold_standard: if provided, a dict with compartments/parameters/stratifications
                   lists from the baseline .compmodel (the ground truth).
    """
    gaps: Dict[str, Any] = {
        "missing_compartments": [],
        "missing_parameters": [],
        "missing_stratifications": [],
        "missing_interventions": [],
        "required_vs_optional": {},
        "comparison_mode": "gold_standard" if gold_standard else "promises",
        "summary": {},
    }

    # What the extraction produced
    extracted_compartments = _normalize_list(entities.get("compartments", []))
    extracted_parameters = _normalize_list(entities.get("parameters", []))
    extracted_stratifications = _normalize_list(entities.get("stratifications", []))
    extracted_interventions = _normalize_list(entities.get("interventions", []))

    model_compartments = [c.lower() for c in model_structure.get("compartments", [])]
    model_parameters = [p.lower() for p in model_structure.get("parameters", [])]
    model_stratifications = [s.lower() for s in model_structure.get("stratifications", [])]

    all_comps = extracted_compartments + model_compartments
    all_params = extracted_parameters + model_parameters
    all_strats = extracted_stratifications + model_stratifications

    # ── Gold-standard comparison (the meaningful one) ──────────────────────
    if gold_standard:
        gold_comps = [c.lower() for c in gold_standard.get("compartments", [])]
        gold_params = [p.lower() for p in gold_standard.get("parameters", [])]
        gold_strats = [s.lower() for s in gold_standard.get("stratifications", [])]

        for gc in gold_comps:
            if not _fuzzy_match_any(gc, all_comps):
                gaps["missing_compartments"].append({
                    "expected": gc,
                    "severity": "high",
                    "reason": "Present in gold standard but not in extracted model.",
                })

        for gp in gold_params:
            if not _fuzzy_match_any(gp, all_params):
                gaps["missing_parameters"].append({
                    "expected": gp,
                    "severity": "high",
                    "reason": "Present in gold standard but not in extracted model.",
                })

        for gs in gold_strats:
            if not _fuzzy_match_any(gs, all_strats):
                gaps["missing_stratifications"].append({
                    "expected": gs,
                    "severity": "high",
                    "reason": "Present in gold standard but not in extracted model.",
                })

        # Also check for extra items (extracted but not in gold standard)
        extra_comps = [c for c in all_comps if not _fuzzy_match_any(c, gold_comps)]
        extra_params = [p for p in all_params if not _fuzzy_match_any(p, gold_params)]
        gaps["extra_compartments"] = list(dict.fromkeys(extra_comps))
        gaps["extra_parameters"] = list(dict.fromkeys(extra_params))

    # ── Promise-based comparison (fallback) ────────────────────────────────
    else:
        promised_compartments = _normalize_list(promises.get("compartments", []))
        promised_parameters = _normalize_list(promises.get("parameters", []))
        promised_stratifications = _normalize_list(promises.get("stratifications", []))
        promised_interventions = _normalize_list(promises.get("interventions", []))

        for prom in promised_compartments:
            if not _fuzzy_match_any(prom, all_comps):
                gaps["missing_compartments"].append({
                    "expected": prom,
                    "severity": "high",
                    "reason": "Promised in paper but not in extracted model.",
                })

        for prom in promised_parameters:
            if not _fuzzy_match_any(prom, all_params):
                gaps["missing_parameters"].append({
                    "expected": prom,
                    "severity": "high",
                    "reason": "Promised in paper but not in extracted model.",
                })

        for prom in promised_interventions:
            if not _fuzzy_match_any(prom, extracted_interventions):
                gaps["missing_interventions"].append({
                    "expected": prom,
                    "severity": "medium",
                    "reason": "Promised in paper but not in extracted model.",
                })

    # ── Contextual checks (both modes) ────────────────────────────────────
    if _paper_is_vector_borne(promises, paper_text) and not _model_has_vector_compartments(model_structure):
        gaps["missing_compartments"].append({
            "expected": "vector/mosquito compartments",
            "severity": "critical",
            "reason": "Paper describes vector-borne disease but model has no vector compartments.",
        })
        gaps["required_vs_optional"]["vector_compartments"] = "required"

    expect_strat = _paper_promises_stratification(promises, paper_text)
    simple = _paper_expects_simple_model(promises, paper_text)
    if expect_strat and not simple:
        gaps["required_vs_optional"]["stratification"] = "required_if_promised"
    else:
        gaps["required_vs_optional"]["stratification"] = "optional"

    total = (
        len(gaps["missing_compartments"]) + len(gaps["missing_parameters"])
        + len(gaps["missing_stratifications"]) + len(gaps["missing_interventions"])
    )
    gaps["summary"] = {
        "total_gaps": total,
        "missing_compartments": len(gaps["missing_compartments"]),
        "missing_parameters": len(gaps["missing_parameters"]),
        "missing_stratifications": len(gaps["missing_stratifications"]),
        "missing_interventions": len(gaps["missing_interventions"]),
        "extra_compartments": len(gaps.get("extra_compartments", [])),
        "extra_parameters": len(gaps.get("extra_parameters", [])),
    }
    return gaps
