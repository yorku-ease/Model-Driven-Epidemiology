"""
Intelligent Inference for Missing Parameters

When RAG doesn't find a value, the AI makes an educated guess using:
1. Transfer learning from similar diseases
2. Biological constraints and typical ranges
3. LLM reasoning about plausibility
4. Conservative defaults with wide uncertainty

All inferred values are marked with LOW confidence for review.
"""

import importlib.util
import os
from pathlib import Path
from typing import Any, Dict, Optional

# Load LLMClient from Phase 2 using importlib (avoids package name conflict with Phase 3's src/)
_phase3_root = Path(__file__).resolve().parent.parent.parent
_phase2 = _phase3_root.parent / "phase 2"
_llm_client_path = _phase2 / "src" / "utils" / "llm_client.py"
_api_key_file = _phase2 / ".api_key.txt"

LLMClient = None
if _llm_client_path.exists():
    try:
        spec = importlib.util.spec_from_file_location("llm_client_phase2", _llm_client_path)
        mod = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(mod)
        LLMClient = mod.LLMClient
    except Exception:
        pass

PARAMETER_DEFAULTS: Dict[str, Dict[str, Any]] = {
    "incubation": {"value": 5.0, "unit": "days", "range": [2, 14], "note": "disease-dependent"},
    "recovery": {"value": 7.0, "unit": "days", "range": [3, 21], "note": "disease-dependent"},
    "transmission": {"value": 0.3, "unit": "per day", "range": [0.01, 1.0], "note": "contact-dependent"},
    "beta": {"value": 0.3, "unit": "per day", "range": [0.05, 0.8], "note": "transmission rate"},
    "gamma": {"value": 0.1, "unit": "per day", "range": [0.05, 0.5], "note": "recovery rate"},
    "sigma": {"value": 0.2, "unit": "per day", "range": [0.1, 0.5], "note": "incubation rate"},
    "mu": {"value": 0.0001, "unit": "per day", "range": [1e-5, 0.01], "note": "mortality"},
    "mortality": {"value": 0.001, "unit": "per day", "range": [0, 0.1], "note": "disease-dependent"},
}


def _guess_from_defaults(parameter_name: str, disease_hint: str = "") -> Optional[Dict[str, Any]]:
    """Conservative default from known parameter types."""
    name_lower = parameter_name.lower().replace("_", " ").strip()
    for key, spec in PARAMETER_DEFAULTS.items():
        if key in name_lower or name_lower in key:
            return {
                "value": spec["value"],
                "unit": spec.get("unit"),
                "range": spec.get("range"),
                "note": spec.get("note", ""),
                "source": "default_library",
                "confidence": "LOW",
                "warning": "Inferred from typical range; verify from literature.",
            }
    return None


def infer_parameter_llm(
    parameter_name: str,
    disease_hint: str,
    context: str = "",
    llm_client: Optional[Any] = None,
    provider: str = "gemini",
) -> Dict[str, Any]:
    """
    Use LLM to suggest a plausible value and reasoning.

    provider: LLM provider to use (gemini/openai/claude) — auto-detected from
              the Phase 2 report directory name so it matches the original run.
    """
    if llm_client is None and LLMClient is not None:
        llm_provider = os.getenv("PHASE3_LLM_PROVIDER", provider)
        llm_client = LLMClient(provider=llm_provider, api_key_file=str(_api_key_file))
    if llm_client is None or not getattr(llm_client, "available", False):
        return _infer_fallback(parameter_name, disease_hint)

    prompt = f"""You are an expert epidemiologist. A compartmental model is missing a parameter value.

Parameter name/symbol: {parameter_name}
Disease/context: {disease_hint}
{("Additional context: " + context[:800]) if context else ""}

Provide a plausible default value and brief reasoning. Use biological/epidemiological typical ranges.
Return ONLY valid JSON in this exact format (no markdown, no explanation outside JSON):
{{"value": <number>, "unit": "<unit string or null>", "reasoning": "<1-2 sentences>", "range_low": <number or null>, "range_high": <number or null>}}
"""
    try:
        import re as _re
        result = llm_client.extract_with_llm(prompt, max_tokens=500, temperature=0.2)
        if isinstance(result, dict):
            # Successful parse
            if result.get("value") is not None and "error" not in result:
                return {
                    "value": result.get("value"),
                    "unit": result.get("unit"),
                    "reasoning": result.get("reasoning", ""),
                    "range_low": result.get("range_low"),
                    "range_high": result.get("range_high"),
                    "source": "llm_inference",
                    "confidence": "LOW",
                    "warning": "AI-inferred; verify from literature.",
                }
            # Failed JSON parse — try to salvage from raw_response
            raw = result.get("raw_response", "")
            if raw:
                return _salvage_from_raw(raw)
    except Exception:
        pass
    return _infer_fallback(parameter_name, disease_hint)


def _salvage_from_raw(raw: str) -> Optional[Dict[str, Any]]:
    """Extract value from truncated/malformed LLM JSON response."""
    import re
    m = re.search(r'"value"\s*:\s*([0-9.eE+-]+)', raw)
    if not m:
        return None
    try:
        val = float(m.group(1))
    except ValueError:
        return None
    unit_m = re.search(r'"unit"\s*:\s*"([^"]*)"', raw)
    reason_m = re.search(r'"reasoning"\s*:\s*"([^"]*)"', raw)
    return {
        "value": val,
        "unit": unit_m.group(1) if unit_m else None,
        "reasoning": reason_m.group(1) if reason_m else "",
        "source": "llm_inference",
        "confidence": "LOW",
        "warning": "AI-inferred (partial response); verify from literature.",
    }


def _infer_fallback(parameter_name: str, disease_hint: str) -> Dict[str, Any]:
    """Fallback when LLM not available: use default library or generic."""
    out = _guess_from_defaults(parameter_name, disease_hint)
    if out:
        return out
    return {
        "value": None,
        "unit": None,
        "reasoning": "No default in library; manual lookup required.",
        "source": "flagged",
        "confidence": "LOW",
        "warning": "Could not infer; flag for manual review.",
    }
