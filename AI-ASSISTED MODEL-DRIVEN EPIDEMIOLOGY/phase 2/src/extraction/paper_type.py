"""Detect paper type (vector-borne, climate-influenced) from text and promises.

Used to tailor prompts and context before entity extraction.
"""

import re
from typing import Any, Dict


# Keywords that indicate vector-borne models (host + vector, e.g. mosquito)
VECTOR_BORNE_KEYWORDS = [
    r"\bmosquito(es)?\b",
    r"\bvector\b",
    r"\bvector-?borne\b",
    r"\bdengue\b",
    r"\bzika\b",
    r"\bmalaria\b",
    r"\barthropod\b",
    r"\bbiting\s+rate\b",
    r"\bhost\s+and\s+vector\b",
    r"\bhuman\s+and\s+vector\b",
    r"\bsusceptible\s+mosquitoes?\b",
    r"\binfectious\s+mosquitoes?\b",
    r"\begg(s)?\s*\(.*\)\b",
    r"\blarva(e)?\b",
    r"\bpupae?\b",
]

# Keywords that indicate climate or environmental drivers
CLIMATE_KEYWORDS = [
    r"\bclimate\b",
    r"\btemperature\b",
    r"\brainfall\b",
    r"\bweather\b",
    r"\benvironmental\s+driver\b",
    r"\bseasonal\b",
    r"\bhumidity\b",
    r"\bel\s*nino\b",
    r"\bclimate\s+change\b",
]


def detect_paper_type(
    paper_text: str,
    promises: Dict[str, Any] | None = None,
) -> Dict[str, bool]:
    """
    Detect whether the paper describes a vector-borne and/or climate-influenced model.

    Args:
        paper_text: Concatenated paper text (e.g. abstract + intro + model section).
        promises: Optional Step 2 promises (model_type, compartments, etc.) for hints.

    Returns:
        {"vector_borne": bool, "climate": bool}
    """
    text = (paper_text or "").lower()
    vector_borne = False
    climate = False

    # Check paper text
    for pat in VECTOR_BORNE_KEYWORDS:
        if re.search(pat, text, re.IGNORECASE):
            vector_borne = True
            break
    for pat in CLIMATE_KEYWORDS:
        if re.search(pat, text, re.IGNORECASE):
            climate = True
            break

    # Hints from promises
    if promises:
        model_type = (promises.get("model_type") or "").lower()
        if any(
            x in model_type
            for x in ("vector", "vector-borne", "dengue", "zika", "malaria", "mosquito")
        ):
            vector_borne = True
        comps = promises.get("compartments") or []
        comp_str = " ".join(str(c) for c in comps).lower()
        if "mosquito" in comp_str or "vector" in comp_str or "egg" in comp_str:
            vector_borne = True

    return {"vector_borne": vector_borne, "climate": climate}
