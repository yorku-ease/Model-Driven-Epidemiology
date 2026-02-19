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
from src.inference.inference_engine import infer_parameter_llm


def fill_gaps(
    gaps: Dict[str, Any],
    paper_database_path: Path,
    disease_hint: str = "",
    paper_text: str = "",
    use_rag: bool = True,
    use_inference: bool = True,
    llm_client: Optional[Any] = None,
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

    for gap_type in ["missing_parameters", "missing_compartments",
                     "missing_stratifications", "missing_interventions"]:
        for gap in gaps.get(gap_type, []):
            expected = gap.get("expected", "") or gap.get("promised", "")
            result: Dict[str, Any] = {
                "gap": gap,
                "gap_type": gap_type,
                "source": "flagged",
                "suggestion": None,
            }

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

                # Tier 2: LLM inference
                if use_inference:
                    inferred = infer_parameter_llm(
                        expected, disease_hint,
                        context=paper_text[:800],
                        llm_client=llm_client,
                    )
                    if inferred.get("value") is not None or inferred.get("reasoning"):
                        result["source"] = "inference"
                        result["suggestion"] = inferred
                        filled.append(result)
                        continue

            # Tier 3: Flag for manual review
            result["suggestion"] = {
                "action": "manual_review",
                "reason": f"Could not fill {gap_type.replace('missing_', '')} gap automatically.",
            }
            filled.append(result)

    summary = {
        "rag_count": sum(1 for f in filled if f["source"] == "rag"),
        "inference_count": sum(1 for f in filled if f["source"] == "inference"),
        "flagged_count": sum(1 for f in filled if f["source"] == "flagged"),
    }
    return {"filled_gaps": filled, "summary": summary}
