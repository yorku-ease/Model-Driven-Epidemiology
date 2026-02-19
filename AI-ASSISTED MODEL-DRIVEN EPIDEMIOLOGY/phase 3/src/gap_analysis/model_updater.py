"""
Apply Phase 3 filled parameter values to the draft .compmodel.

Produces model_filled.compmodel: the Phase 2 draft with parameter values
updated or added from RAG/inference. Used so downstream steps (and the
selector) have an improved model file per candidate.
"""

import re
import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Any, Dict, Optional

from .gap_detector import GREEK_TO_LATIN


def _local_tag(el: ET.Element) -> str:
    return el.tag.split("}")[-1] if "}" in el.tag else el.tag


def _normalize_for_match(s: str) -> str:
    """Normalize for parameter name matching (alphanumeric + normalized Greek)."""
    for greek, latin in GREEK_TO_LATIN.items():
        s = s.replace(greek, latin)
    return re.sub(r"[^a-z0-9]", "", s.lower())


def _extract_value(suggestion: Dict[str, Any]) -> Optional[str]:
    """Get a single numeric expression string from a fill suggestion."""
    val = suggestion.get("value")
    if val is None:
        return None
    if isinstance(val, (int, float)):
        return str(val)
    if isinstance(val, str):
        m = re.search(r"[-+]?[0-9]*\.?[0-9]+(?:[eE][-+]?\d+)?", val.strip())
        if m:
            return m.group()
    return None


def apply_fills_to_model(
    draft_path: Path,
    filled_result: Dict[str, Any],
    output_path: Path,
) -> int:
    """
    Write an updated .compmodel with filled parameter values applied.

    - For each filled_gap of type missing_parameters with source in (rag, inference)
      and a numeric value, update or add that parameter in the draft XML.
    - Returns the number of parameters updated or added.
    """
    raw = draft_path.read_text(encoding="utf-8", errors="replace")
    if "xmlns:xsi" not in raw and "xsi:" in raw:
        raw = raw.replace(
            "xmlns:xmi=",
            'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xmi=',
        )
    root = ET.fromstring(raw)

    # Collect existing parameter elements by normalized name (and keep first occurrence for update)
    param_elements: Dict[str, ET.Element] = {}
    param_tag = None
    insert_before = None
    for el in root:
        tag = _local_tag(el)
        if tag == "parameters":
            if param_tag is None:
                param_tag = el.tag
            name = el.get("name", "")
            if name:
                key = _normalize_for_match(name)
                if key and key not in param_elements:
                    param_elements[key] = el
        elif tag == "compartments" and insert_before is None:
            insert_before = el

    if param_tag is None:
        param_tag = "parameters"

    applied = 0
    for item in filled_result.get("filled_gaps", []):
        if item.get("gap_type") != "missing_parameters":
            continue
        if item.get("source") not in ("rag", "inference"):
            continue
        suggestion = item.get("suggestion") or {}
        expr = _extract_value(suggestion)
        if expr is None:
            continue
        expected = (item.get("gap") or {}).get("expected", "")
        if not expected or not str(expected).strip():
            continue
        key = _normalize_for_match(expected)
        if not key:
            continue

        unit = suggestion.get("unit") or ""
        if isinstance(unit, str) and unit.lower() in ("null", "none", ""):
            unit = ""
        desc = suggestion.get("description") or ""

        if key in param_elements:
            param_elements[key].set("expression", expr)
            if unit:
                param_elements[key].set("unit", unit)
            if desc:
                param_elements[key].set("description", desc)
            applied += 1
        else:
            # Add new parameter (use original expected name for display)
            new_el = ET.Element(param_tag)
            new_el.set("name", expected.strip())
            new_el.set("expression", expr)
            new_el.set("type", "CONSTANT")
            if unit:
                new_el.set("unit", unit)
            if desc:
                new_el.set("description", desc)
            param_elements[key] = new_el
            if insert_before is not None:
                root.insert(list(root).index(insert_before), new_el)
            else:
                root.append(new_el)
            applied += 1
    output_path.parent.mkdir(parents=True, exist_ok=True)
    tree = ET.ElementTree(root)
    try:
        ET.indent(tree, space="  ")
    except AttributeError:
        pass
    tree.write(
        output_path,
        encoding="unicode",
        default_namespace=None,
        method="xml",
        xml_declaration=True,
    )
    return applied
