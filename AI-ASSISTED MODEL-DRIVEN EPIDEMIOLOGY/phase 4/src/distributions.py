"""
Task 9.1: Assign parameter distributions.

Uses the general framework (typed parameter uncertainty defaults) so that
similar diseases abide by the same uncertainty structure. Converts point
estimates to distribution specs (family, bounds or mean/cv) for Monte Carlo.
"""

import json
import re
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple


def _local_tag(el) -> str:
    return el.tag.split("}")[-1] if "}" in el.tag else el.tag


def load_parameters_from_compmodel(compmodel_path: Path) -> List[Dict[str, Any]]:
    """Extract parameter name and expression from .compmodel XML."""
    raw = compmodel_path.read_text(encoding="utf-8", errors="replace")
    if "xmlns:xsi" not in raw and "xsi:" in raw:
        raw = raw.replace(
            "xmlns:xmi=",
            'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xmi=',
        )
    import xml.etree.ElementTree as ET
    root = ET.fromstring(raw)
    params = []
    for el in root.iter():
        if _local_tag(el) == "parameters":
            name = el.get("name", "")
            expr = el.get("expression", "")
            if name:
                params.append({
                    "name": name,
                    "expression": expr,
                    "description": (el.get("description") or "").lower(),
                    "unit": el.get("unit", ""),
                })
    return params


def _parse_point_or_range(expression: str) -> Tuple[Optional[float], Optional[float]]:
    """Parse expression to (point_value) or (low, high). Returns (None, None) if unparseable."""
    if not expression or not str(expression).strip():
        return None, None
    s = str(expression).strip()
    # Range: "0.2 to 0.4", "10^-5 to 1", "2.9 to 14"
    m = re.search(r"([0-9.eE+-]+)\s*(?:to|-)\s*([0-9.eE+-]+)", s, re.IGNORECASE)
    if m:
        try:
            low = float(m.group(1))
            high = float(m.group(2))
            if low > high:
                low, high = high, low
            return low, high
        except ValueError:
            pass
    # Single number
    m = re.search(r"[-+]?[0-9]*\.?[0-9]+(?:[eE][-+]?\d+)?", s)
    if m:
        try:
            return float(m.group()), None
        except ValueError:
            pass
    return None, None


def _classify_parameter_type(name: str, description: str, keywords: Dict[str, List[str]]) -> str:
    """Classify parameter into transmission, recovery, mortality, progression, contact, other."""
    combined = f"{name} {description}".lower()
    for ptype, kws in keywords.items():
        if ptype == "other":
            continue
        for kw in kws:
            if kw.lower() in combined:
                return ptype
    return "other"


def assign_distributions(
    compmodel_path: Path,
    framework_path: Path,
    model_type_hint: Optional[str] = None,
) -> Dict[str, Any]:
    """
    Assign a distribution spec to each parameter using the general framework.

    General framework provides distribution family and typical cv/range by
    *parameter type* (transmission, recovery, etc.), not by disease. Point
    estimates or ranges from the model expression are used when available.
    """
    params = load_parameters_from_compmodel(compmodel_path)
    framework = json.loads(framework_path.read_text(encoding="utf-8"))
    defaults = framework.get("parameter_uncertainty_defaults", {})
    keywords = framework.get("parameter_type_keywords", {})

    distributions = {}
    for p in params:
        name = p["name"]
        expr = p["expression"]
        desc = p.get("description", "")
        ptype = _classify_parameter_type(name, desc, keywords)
        spec_default = defaults.get(ptype, defaults.get("other", {}))
        family = spec_default.get("family", "uniform")
        cv = spec_default.get("cv")
        rel_range = spec_default.get("relative_range", 0.5)

        point, high = _parse_point_or_range(expr)
        low_val, high_val = None, None
        if point is not None and high is not None:
            low_val, high_val = point, high
        elif point is not None:
            if family == "lognormal" and cv and point > 0:
                import math
                sigma = (math.log(1 + cv ** 2)) ** 0.5
                mu = math.log(point) - 0.5 * sigma ** 2
                low_val = math.exp(mu - 1.96 * sigma)
                high_val = math.exp(mu + 1.96 * sigma)
            else:
                r = rel_range or 0.5
                low_val = point * (1 - r)
                high_val = point * (1 + r)
                if low_val < 0 and "rate" in ptype:
                    low_val = point * 0.1
        else:
            if ptype == "transmission":
                low_val, high_val = 0.01, 1.0
            elif ptype == "recovery":
                low_val, high_val = 0.05, 0.5
            elif ptype == "mortality":
                low_val, high_val = 1e-5, 0.01
            else:
                low_val, high_val = 0.0, 1.0

        if low_val is not None and high_val is not None and low_val > high_val:
            low_val, high_val = high_val, low_val
        if low_val is not None and low_val < 0 and family == "lognormal":
            family = "uniform"
        if ptype in ("transmission", "recovery", "contact", "progression", "mortality") and low_val is not None and low_val < 0:
            low_val = 1e-10
        if high_val is not None and high_val < 0 and ptype in ("transmission", "recovery", "contact", "progression", "mortality"):
            high_val = max(0.1, low_val or 0.1)
        distributions[name] = {
            "parameter_type": ptype,
            "family": family,
            "low": low_val,
            "high": high_val,
            "point_estimate": point if high is None else (point + high) / 2 if point is not None else None,
            "source_expression": expr,
        }
    return {
        "model_path": str(compmodel_path),
        "model_type_hint": model_type_hint,
        "parameter_distributions": distributions,
        "framework_source": str(framework_path),
    }
