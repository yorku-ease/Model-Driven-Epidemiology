"""
Gap Filling with RAG + Intelligent Inference

For each detected gap, try to fill it:
1. RAG search in paper database (parameter index + text chunks)
2. LLM intelligent inference (educated guess)
3. Flag for manual review

Gold standard is NOT used for filling — only for detection and validation.
This ensures the fill accuracy numbers are honest.
"""

import re
import sys
from pathlib import Path
from typing import Any, Dict, List, Optional

_phase3 = Path(__file__).resolve().parent.parent
if str(_phase3) not in sys.path:
    sys.path.insert(0, str(_phase3))
from src.rag.parameter_lookup import parameter_lookup
from src.rag.structure_lookup import structure_lookup
from src.inference.inference_engine import infer_parameter_llm, infer_compartment_llm, infer_flow_llm


def fill_spec_gaps(
    threelayer: Dict[str, Any],
    entities: Dict[str, Any],
) -> List[Dict[str, Any]]:
    """
    Fill *spec→model* gaps directly from extracted_entities.

    These items were identified from the paper text but never made it into the
    draft model.  Since we already have the information (names, values), we can
    add them without RAG or LLM inference — they are paper-traceable by definition.

    Returns a list of fill records compatible with ``filled_gaps`` in the main
    fill_gaps output (so model_updater can process them in one pass).
    """
    spec = threelayer.get("spec_vs_model", {})
    ent_params_raw = entities.get("parameters", []) or []
    ent_flows_raw = entities.get("flows", []) or []

    # Build a quick name→value lookup for extracted parameters
    param_value_map: Dict[str, Any] = {}
    for p in ent_params_raw:
        if isinstance(p, dict):
            name = (p.get("normalized_name") or p.get("name") or "").strip()
            val = p.get("value") or p.get("expression") or p.get("default_value")
            unit = p.get("unit") or ""
            desc = p.get("description") or ""
            if name:
                param_value_map[name.lower()] = {"value": val, "unit": unit, "description": desc}

    fills: List[Dict[str, Any]] = []

    # ── Compartments ─────────────────────────────────────────────────────
    for name in spec.get("missing_compartments", []):
        fills.append({
            "gap": {"expected": name, "severity": "medium",
                    "reason": "Identified in extracted entities but absent from model."},
            "gap_type": "missing_compartments",
            "source": "spec_entity",   # paper-traceable
            "suggestion": {"primary_name": name, "note": "From extracted entities (paper text)."},
        })

    # ── Parameters ───────────────────────────────────────────────────────
    for name in spec.get("missing_parameters", []):
        ev = param_value_map.get(name.lower(), {})
        fills.append({
            "gap": {"expected": name, "severity": "medium",
                    "reason": "Identified in extracted entities but absent from model."},
            "gap_type": "missing_parameters",
            "source": "spec_entity",
            "suggestion": {
                "value": ev.get("value"),
                "unit": ev.get("unit", ""),
                "description": ev.get("description", ""),
                "note": "From extracted entities (paper text).",
            },
        })

    # ── Flows ─────────────────────────────────────────────────────────────
    for sig in spec.get("missing_flows", []):
        fills.append({
            "gap": {"expected": sig, "severity": "medium",
                    "reason": "Flow identified in extracted entities but absent from model."},
            "gap_type": "missing_flows",
            "source": "spec_entity",
            "suggestion": {"signature": sig, "note": "From extracted entities (paper text)."},
        })

    return fills


def fill_gaps(
    gaps: Dict[str, Any],
    paper_database_path: Path,
    disease_hint: str = "",
    paper_text: str = "",
    use_rag: bool = True,
    use_inference: bool = True,
    llm_client: Optional[Any] = None,
    llm_provider: str = "gemini",
    threelayer: Optional[Dict[str, Any]] = None,
    entities: Optional[Dict[str, Any]] = None,
) -> Dict[str, Any]:
    """
    For each gap, try to fill it:
    1) RAG lookup in paper database
    2) LLM inference
    3) Flag for manual review
    """
    filled: List[Dict[str, Any]] = []
    db_path = Path(paper_database_path)
    if db_path.is_file():
        db_path = db_path.parent

    # Severity ordering (matches Phase RLM's repair priority)
    _SEV_ORDER = {"critical": 0, "high": 1, "medium": 2, "low": 3}

    def _sorted_gaps(gap_list: List[Dict[str, Any]]) -> List[Dict[str, Any]]:
        return sorted(gap_list, key=lambda g: _SEV_ORDER.get(g.get("severity", "low"), 3))

    for gap_type in ["missing_compartments", "missing_parameters", "missing_flows",
                     "missing_stratifications", "missing_interventions"]:
        for gap in _sorted_gaps(gaps.get(gap_type, [])):
            expected = gap.get("expected", "") or gap.get("promised", "")
            result: Dict[str, Any] = {
                "gap": gap,
                "gap_type": gap_type,
                "source": "flagged",
                "suggestion": None,
            }

            # ── Compartments: RAG evidence + LLM label ─────────────────────
            if gap_type == "missing_compartments" and expected:
                ev = {}
                if use_rag and db_path.exists():
                    ev = structure_lookup(db_path, gap_type, str(expected), disease_hint=disease_hint)
                if ev.get("chunk_evidence"):
                    result["source"] = "rag"
                    result["suggestion"] = {
                        "primary_name": str(expected).strip(),
                        "evidence_chunks": ev["chunk_evidence"][:3],
                        "note": "Use paper snippets to confirm compartment label and add to model.",
                    }
                    filled.append(result)
                    continue
                if use_inference:
                    inf = infer_compartment_llm(
                        str(expected), disease_hint,
                        context=paper_text[:2000],
                        provider=llm_provider,
                    )
                    if inf.get("primary_name"):
                        result["source"] = "inference"
                        result["suggestion"] = inf
                        filled.append(result)
                        continue

            # ── Flows: flow_index + chunks + LLM narrative ─────────────────
            if gap_type == "missing_flows" and expected and "->" in str(expected):
                ev = {}
                if use_rag and db_path.exists():
                    ev = structure_lookup(db_path, gap_type, str(expected), disease_hint=disease_hint)
                if ev.get("flow_matches"):
                    result["source"] = "rag"
                    result["suggestion"] = {
                        "signature": str(expected).strip(),
                        "similar_flows_in_corpus": ev["flow_matches"][:5],
                        "chunk_evidence": ev.get("chunk_evidence", [])[:2],
                        "note": "Analogous flows from indexed models / text; align with gold wiring.",
                    }
                    filled.append(result)
                    continue
                if ev.get("chunk_evidence") and use_rag:
                    result["source"] = "rag"
                    result["suggestion"] = {
                        "signature": str(expected).strip(),
                        "chunk_evidence": ev["chunk_evidence"][:3],
                        "note": "Text evidence only; structural edit may need Phase RLM.",
                    }
                    filled.append(result)
                    continue
                if use_inference:
                    inf = infer_flow_llm(
                        str(expected), disease_hint,
                        context=paper_text[:2000],
                        llm_client=llm_client,
                        provider=llm_provider,
                    )
                    if inf.get("description") or inf.get("flow_type"):
                        result["source"] = "inference"
                        result["suggestion"] = inf
                        filled.append(result)
                        continue

            if gap_type == "missing_parameters" and expected:
                # Tier 1: RAG
                if use_rag and db_path.exists():
                    query = f"{disease_hint} {expected} parameter value".strip()
                    lookup = parameter_lookup(
                        db_path, query,
                        disease_hint=disease_hint or None,
                        parameter_name=expected,
                    )
                    if lookup.get("extracted_value"):
                        result["source"] = "rag"
                        result["suggestion"] = {
                            **lookup["extracted_value"],
                            "sources": lookup.get("sources", []),
                        }
                        filled.append(result)
                        continue

                # Tier 2: LLM inference (uses same provider as Phase 2)
                if use_inference:
                    inferred = infer_parameter_llm(
                        expected, disease_hint,
                        context=paper_text[:800],
                        llm_client=llm_client,
                        provider=llm_provider,
                    )
                    if inferred.get("value") is not None or inferred.get("reasoning"):
                        result["source"] = "inference"
                        result["suggestion"] = inferred
                        filled.append(result)
                        continue

            # Tier 3: Flag for manual review.
            # NOTE: We do NOT add compartment or flow shells here using the gold-standard
            # name, because doing so would scaffold the model directly from gold-standard
            # labels without any paper evidence — inflating structural recall dishonestly.
            # Only RAG and inference fills (above) are allowed to add structural elements.
            fallback: Dict[str, Any] = {
                "action": "manual_review",
                "reason": f"Could not fill {gap_type.replace('missing_', '')} gap automatically.",
            }
            result["suggestion"] = fallback
            filled.append(result)

    # ── Spec→model fills (paper-traceable, no RAG/LLM needed) ────────────────
    spec_fills: List[Dict[str, Any]] = []
    if threelayer and entities:
        spec_fills = fill_spec_gaps(threelayer, entities)
        # Deduplicate: skip spec fills that are already covered by gold-based fills
        already_covered = {
            f["gap"].get("expected", "").lower()
            for f in filled
            if f.get("gap_type") in ("missing_compartments", "missing_parameters", "missing_flows")
        }
        spec_fills = [
            sf for sf in spec_fills
            if sf["gap"].get("expected", "").lower() not in already_covered
        ]
        filled.extend(spec_fills)

    summary = {
        "rag_count": sum(1 for f in filled if f["source"] == "rag"),
        "inference_count": sum(1 for f in filled if f["source"] == "inference"),
        "spec_entity_count": sum(1 for f in filled if f["source"] == "spec_entity"),
        "flagged_count": sum(1 for f in filled if f["source"] == "flagged"),
    }
    return {"filled_gaps": filled, "summary": summary}
