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
import re
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
    """Extract compartments, parameters, stratifications, and flows from .compmodel XML.

    Flows are encoded as ``"SourceName->TargetName"`` using compartment index resolution
    for ``target="compartments.N"`` references (document order of ``<compartments>``).
    """
    raw = compmodel_path.read_text(encoding="utf-8", errors="replace")
    # Inject missing namespace declarations so the parser won't choke
    if "xmlns:xsi" not in raw and "xsi:" in raw:
        raw = raw.replace(
            "xmlns:xmi=",
            'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xmi=',
        )
    root = ET.fromstring(raw)
    compartments_ordered: List[str] = []
    parameters, stratifications = [], []
    flows: List[str] = []

    for el in root:
        tag = _local_tag(el)
        if tag == "compartments":
            name = el.get("PrimaryName", "")
            if name:
                compartments_ordered.append(name)

    compartments = list(dict.fromkeys(compartments_ordered))

    for el in root.iter():
        tag = _local_tag(el)
        if tag == "parameters":
            name = el.get("name", "")
            if name and name.lower() not in ("none", "n/a", ""):
                parameters.append(name)
        elif tag == "groups":
            name = el.get("name", "")
            if name:
                stratifications.append(name)

    # Outgoing flows: under each <compartments> element, child tags containing "flow"
    for el in root:
        if _local_tag(el) != "compartments":
            continue
        src_name = el.get("PrimaryName", "")
        if not src_name:
            continue
        for child in el:
            ctag = _local_tag(child)
            if "flow" not in ctag.lower():
                continue
            tgt_ref = child.get("target", "") or ""
            # Handles ``compartments.N`` and ``//@compartments.N`` (Eclipse/XMI style)
            m = re.search(r"compartments\.(\d+)", tgt_ref.strip())
            if m:
                ti = int(m.group(1))
                if 0 <= ti < len(compartments_ordered):
                    flows.append(f"{src_name}->{compartments_ordered[ti]}")
            elif tgt_ref.startswith("compartments."):
                # Non-numeric reference — skip or keep raw
                flows.append(f"{src_name}->{tgt_ref}")

    return {
        "compartments": compartments,
        "parameters": list(dict.fromkeys(parameters)),
        "stratifications": list(dict.fromkeys(stratifications)),
        "flows": list(dict.fromkeys(flows)),
    }


# ─── Matching helpers ──────────────────────────────────────────────────────

SYNONYMS = [
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
    # Single-word direct check (fast path for plain compartment names like "Infectious")
    for group in SYNONYMS:
        if a_norm in group and b_norm in group:
            return True
    # Word-level check for multi-word names (e.g. "Infectious Humans" ↔ "Infected individuals")
    a_words = set(re.split(r"[^a-z]+", a.lower())) - {""}
    b_words = set(re.split(r"[^a-z]+", b.lower())) - {""}
    for group in SYNONYMS:
        if any(w in group for w in a_words) and any(w in group for w in b_words):
            return True
    return False


def _fuzzy_match_any(query: str, candidates: List[str]) -> bool:
    return any(_fuzzy_match(query, c) for c in candidates)


def _flows_from_entities(entities: Dict[str, Any]) -> List[str]:
    out: List[str] = []
    for f in entities.get("flows", []) or []:
        if not isinstance(f, dict):
            continue
        s = (f.get("source") or f.get("from") or "").strip()
        t = (f.get("target") or f.get("to") or "").strip()
        if s and t:
            out.append(f"{s}->{t}")
    return list(dict.fromkeys(out))


def _fuzzy_match_flow(gold_sig: str, candidates: List[str]) -> bool:
    """Match ``A->B`` against candidate flow strings using fuzzy compartment names."""
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


# ─── Three-layer gap analysis ──────────────────────────────────────────────

def detect_gaps_threelayer(
    entities: Dict[str, Any],
    model_structure: Dict[str, Any],
    gold_standard: Optional[Dict[str, Any]],
    paper_text: str = "",
) -> Dict[str, Any]:
    """
    Three-layer gap analysis matching the paper framework:

      Layer 1 — **Spec → Model** (specification gap):
        Items the LLM *recognised* from paper text (extracted_entities) that did
        NOT make it into the draft model.  These represent extraction/modelling
        failures: the information was found but wasn't wired in.

      Layer 2 — **Model → Gold** (validation gap):
        Items present in the reference (gold) model but absent from the draft.
        These are completeness gaps against the independent reference.

      Layer 3 — **Extra** (noise / hallucination):
        Items in the draft model that are NOT in the reference.  These may be
        over-specified flows/compartments or modelling conventions that differ.

    All three layers are computed for compartments, parameters, and flows.
    Returns a dict keyed by layer; each layer contains per-entity-type lists.
    """
    # ── Normalise entity lists ──────────────────────────────────────────────
    ent_comps = _normalize_list(entities.get("compartments", []))
    ent_params = _normalize_list(entities.get("parameters", []))
    ent_flows = _flows_from_entities(entities)

    mdl_comps = [c.lower() for c in model_structure.get("compartments", [])]
    mdl_params = [p.lower() for p in model_structure.get("parameters", [])]
    mdl_flows = [str(f) for f in model_structure.get("flows", []) if f]

    # Layer 1: spec → model (what was extracted but not in the draft model)
    spec_missing_comps = [
        e for e in ent_comps if not _fuzzy_match_any(e, mdl_comps)
    ]
    spec_missing_params = [
        e for e in ent_params if not _fuzzy_match_any(e, mdl_params)
    ]
    spec_missing_flows = [
        f for f in ent_flows if not _fuzzy_match_flow(f, mdl_flows)
    ]

    # Layer 2: gold → model (reference items missing from the draft model)
    gold_missing_comps: List[str] = []
    gold_missing_params: List[str] = []
    gold_missing_flows: List[str] = []
    if gold_standard:
        gold_comps = [c.lower() for c in gold_standard.get("compartments", [])]
        gold_params = [p.lower() for p in gold_standard.get("parameters", [])]
        gold_flows = [str(f) for f in gold_standard.get("flows", []) if f]
        all_comps = list(dict.fromkeys(ent_comps + mdl_comps))
        all_params = list(dict.fromkeys(ent_params + mdl_params))
        all_flows = list(dict.fromkeys(ent_flows + mdl_flows))
        gold_missing_comps = [gc for gc in gold_comps if not _fuzzy_match_any(gc, all_comps)]
        gold_missing_params = [gp for gp in gold_params if not _fuzzy_match_any(gp, all_params)]
        gold_missing_flows = [gf for gf in gold_flows if not _fuzzy_match_flow(gf, all_flows)]

    # Layer 3: extras (model items not in reference — noise / conventions)
    extra_comps: List[str] = []
    extra_params: List[str] = []
    extra_flows: List[str] = []
    if gold_standard:
        gold_comps_l = [c.lower() for c in gold_standard.get("compartments", [])]
        gold_params_l = [p.lower() for p in gold_standard.get("parameters", [])]
        gold_flows_l = [str(f) for f in gold_standard.get("flows", []) if f]
        extra_comps = [c for c in mdl_comps if not _fuzzy_match_any(c, gold_comps_l)]
        extra_params = [p for p in mdl_params if not _fuzzy_match_any(p, gold_params_l)]
        extra_flows = [f for f in mdl_flows if not _fuzzy_match_flow(f, gold_flows_l)]

    def _cnt(lst: List) -> int:
        return len(lst)

    return {
        "spec_vs_model": {
            "description": "Items recognised from paper text but absent from draft model (extraction/modelling failure).",
            "missing_compartments": spec_missing_comps,
            "missing_parameters": spec_missing_params,
            "missing_flows": spec_missing_flows,
            "summary": {
                "missing_compartments": _cnt(spec_missing_comps),
                "missing_parameters": _cnt(spec_missing_params),
                "missing_flows": _cnt(spec_missing_flows),
                "total": _cnt(spec_missing_comps) + _cnt(spec_missing_params) + _cnt(spec_missing_flows),
            },
        },
        "model_vs_gold": {
            "description": "Reference model items absent from extracted draft (completeness vs reference).",
            "missing_compartments": gold_missing_comps,
            "missing_parameters": gold_missing_params,
            "missing_flows": gold_missing_flows,
            "summary": {
                "missing_compartments": _cnt(gold_missing_comps),
                "missing_parameters": _cnt(gold_missing_params),
                "missing_flows": _cnt(gold_missing_flows),
                "total": _cnt(gold_missing_comps) + _cnt(gold_missing_params) + _cnt(gold_missing_flows),
            },
        },
        "extra_in_model": {
            "description": "Model items absent from reference (over-specification, noise, or differing convention).",
            "extra_compartments": extra_comps,
            "extra_parameters": extra_params,
            "extra_flows": extra_flows,
            "summary": {
                "extra_compartments": _cnt(extra_comps),
                "extra_parameters": _cnt(extra_params),
                "extra_flows": _cnt(extra_flows),
                "total": _cnt(extra_comps) + _cnt(extra_params) + _cnt(extra_flows),
            },
        },
        "has_gold": gold_standard is not None,
    }


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
        "missing_flows": [],
        "required_vs_optional": {},
        "comparison_mode": "gold_standard" if gold_standard else "promises",
        "summary": {},
    }

    # What the extraction produced
    extracted_compartments = _normalize_list(entities.get("compartments", []))
    extracted_parameters = _normalize_list(entities.get("parameters", []))
    extracted_stratifications = _normalize_list(entities.get("stratifications", []))
    extracted_interventions = _normalize_list(entities.get("interventions", []))
    extracted_flows = _flows_from_entities(entities)

    model_compartments = [c.lower() for c in model_structure.get("compartments", [])]
    model_parameters = [p.lower() for p in model_structure.get("parameters", [])]
    model_stratifications = [s.lower() for s in model_structure.get("stratifications", [])]
    model_flows = [str(x) for x in model_structure.get("flows", []) if x]

    all_comps = extracted_compartments + model_compartments
    all_params = extracted_parameters + model_parameters
    all_strats = extracted_stratifications + model_stratifications
    all_flows = extracted_flows + model_flows

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

        gold_flows = [str(x) for x in gold_standard.get("flows", []) if x]
        for gf in gold_flows:
            if not _fuzzy_match_flow(gf, all_flows):
                gaps["missing_flows"].append({
                    "expected": gf,
                    "severity": "high",
                    "reason": "Present in gold standard but not in extracted model (or flows list).",
                })

        # Also check for extra items (extracted but not in gold standard)
        extra_comps = [c for c in all_comps if not _fuzzy_match_any(c, gold_comps)]
        extra_params = [p for p in all_params if not _fuzzy_match_any(p, gold_params)]
        extra_flows = [f for f in all_flows if not _fuzzy_match_flow(f, gold_flows)]
        gaps["extra_compartments"] = list(dict.fromkeys(extra_comps))
        gaps["extra_parameters"] = list(dict.fromkeys(extra_params))
        gaps["extra_flows"] = list(dict.fromkeys(extra_flows))

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

        gaps["extra_compartments"] = []
        gaps["extra_parameters"] = []
        gaps["extra_flows"] = []

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
        + len(gaps["missing_flows"])
    )
    gaps["summary"] = {
        "total_gaps": total,
        "missing_compartments": len(gaps["missing_compartments"]),
        "missing_parameters": len(gaps["missing_parameters"]),
        "missing_stratifications": len(gaps["missing_stratifications"]),
        "missing_interventions": len(gaps["missing_interventions"]),
        "missing_flows": len(gaps["missing_flows"]),
        "extra_compartments": len(gaps.get("extra_compartments", [])),
        "extra_parameters": len(gaps.get("extra_parameters", [])),
        "extra_flows": len(gaps.get("extra_flows", [])),
    }
    return gaps
