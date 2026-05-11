"""
Apply Phase 3 fills to the draft .compmodel.

What this module writes:
  1. **Parameters** — update `expression` for matched parameters; add new ones.
  2. **Compartments** — add missing compartment shells (from RAG, inference, OR
     gap-detection in gold-standard mode) with the gold-aligned PrimaryName.
  3. **Flows** — inject outgoingFlows elements for missing flows whose source AND
     target compartments already exist in the (possibly updated) model.

Renaming existing compartments is intentionally NOT done: changing a PrimaryName
could silently break human-readable model semantics outside of this pipeline.
"""

import re
import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple

from .gap_detector import GREEK_TO_LATIN


def _local_tag(el: ET.Element) -> str:
    return el.tag.split("}")[-1] if "}" in el.tag else el.tag


def _normalize_for_match(s: str) -> str:
    for greek, latin in GREEK_TO_LATIN.items():
        s = s.replace(greek, latin)
    return re.sub(r"[^a-z0-9]", "", s.lower())


def _fuzzy_name_match(a: str, b: str) -> bool:
    """Substring-based fuzzy match (same logic as gap_detector._fuzzy_match)."""
    an = _normalize_for_match(a)
    bn = _normalize_for_match(b)
    if not an or not bn:
        return False
    return an in bn or bn in an


def _extract_value(suggestion: Dict[str, Any]) -> Optional[str]:
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


def _first_flow_tag(root: ET.Element) -> Optional[str]:
    """Return the tag name of the first flow child found in any compartment."""
    for el in root:
        if _local_tag(el) != "compartments":
            continue
        for child in el:
            if "flow" in _local_tag(child).lower():
                return child.tag
    return None


def _compartments_ordered(root: ET.Element) -> List[Tuple[int, ET.Element, str]]:
    """Return list of (global_index, element, PrimaryName) for compartments in root order."""
    result = []
    idx = 0
    for el in root:
        if _local_tag(el) == "compartments":
            result.append((idx, el, el.get("PrimaryName", "")))
            idx += 1
    return result


def apply_fills_to_model(
    draft_path: Path,
    filled_result: Dict[str, Any],
    output_path: Path,
) -> int:
    """
    Write an updated .compmodel with fills applied.

    Returns the total count of updates (params updated + compartments added + flows injected).
    """
    raw = draft_path.read_text(encoding="utf-8", errors="replace")
    if "xmlns:xsi" not in raw and "xsi:" in raw:
        raw = raw.replace(
            "xmlns:xmi=",
            'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xmi=',
        )
    root = ET.fromstring(raw)

    # ── Collect existing parameter elements ────────────────────────────────
    param_elements: Dict[str, ET.Element] = {}
    param_tag: Optional[str] = None
    insert_before: Optional[ET.Element] = None
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

    # ── Pass 1: Parameters and Compartments ───────────────────────────────
    for item in filled_result.get("filled_gaps", []):
        gap_type = item.get("gap_type", "")
        source = item.get("source", "")
        suggestion = item.get("suggestion") or {}
        gap = item.get("gap") or {}

        # ── Compartments ──────────────────────────────────────────────────
        if gap_type == "missing_compartments":
            # Only accept fills backed by paper evidence (RAG, inference, spec_entity).
            # Flagged fills (no evidence found) must NOT add compartment shells using
            # gold-standard names — that would scaffold the model dishonestly.
            if source not in ("rag", "inference", "spec_entity"):
                continue
            name = (suggestion.get("primary_name") or "").strip()
            if not name:
                name = (gap.get("expected") or "").strip()
            if not name:
                continue
            key = _normalize_for_match(name)
            existing = {
                _normalize_for_match(el.get("PrimaryName", ""))
                for el in root
                if _local_tag(el) == "compartments"
            }
            # Also check fuzzy: skip if a very close name already exists
            existing_names = [
                el.get("PrimaryName", "")
                for el in root
                if _local_tag(el) == "compartments"
            ]
            if key in existing or any(_fuzzy_name_match(name, en) for en in existing_names):
                continue
            comp_tag = next(
                (el.tag for el in root if _local_tag(el) == "compartments"),
                "compartments",
            )
            new_c = ET.Element(comp_tag)
            new_c.set("PrimaryName", name)
            root.append(new_c)
            applied += 1
            continue

        # ── Parameters ────────────────────────────────────────────────────
        if gap_type != "missing_parameters":
            continue
        if source not in ("rag", "inference", "spec_entity"):
            continue
        expr = _extract_value(suggestion)
        if expr is None:
            continue
        expected = gap.get("expected", "")
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
            new_el = ET.Element(param_tag)
            new_el.set("name", str(expected).strip())
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

    # ── Pass 2: Flow injection ─────────────────────────────────────────────
    # Build up-to-date compartment order (after any additions in Pass 1)
    comp_order = _compartments_ordered(root)
    flow_tag = _first_flow_tag(root) or "outgoingFlows"

    for item in filled_result.get("filled_gaps", []):
        if item.get("gap_type") != "missing_flows":
            continue
        gap = item.get("gap") or {}
        sig = (gap.get("expected") or "").strip()
        if "->" not in sig:
            continue
        src_name, _, tgt_name = sig.partition("->")
        src_name = src_name.strip()
        tgt_name = tgt_name.strip()

        # Fuzzy-find source and target in current compartment list
        src_entry: Optional[Tuple[int, ET.Element, str]] = None
        tgt_idx: Optional[int] = None
        for order_idx, (comp_idx, el, name) in enumerate(comp_order):
            if _fuzzy_name_match(src_name, name):
                src_entry = (order_idx, el, name)
            if _fuzzy_name_match(tgt_name, name):
                tgt_idx = order_idx

        if src_entry is None or tgt_idx is None:
            continue

        _, src_el, _ = src_entry

        # Check if this target already has a flow going from src
        already_wired = False
        for child in src_el:
            if "flow" not in _local_tag(child).lower():
                continue
            tgt_ref = (child.get("target") or "").strip()
            m = re.search(r"compartments\.(\d+)", tgt_ref)
            if m and int(m.group(1)) == tgt_idx:
                already_wired = True
                break
        if already_wired:
            continue

        new_flow = ET.SubElement(src_el, flow_tag)
        new_flow.set("target", f"//@compartments.{tgt_idx}")
        applied += 1

    # ── Write output ───────────────────────────────────────────────────────
    output_path.parent.mkdir(parents=True, exist_ok=True)
    try:
        ET.indent(root, space="  ")
    except AttributeError:
        pass
    xml_str = ET.tostring(root, encoding="unicode", xml_declaration=True)

    # Fix namespace prefixes: ElementTree's C accelerator auto-generates
    # ns0, ns1 etc. Map them to expected EMF prefixes by reading the
    # xmlns declarations from the serialized XML.
    _NS_EXPECTED = {
        'http://example.com/compartmentalmodel': 'compartmental',
        'http://www.omg.org/XMI': 'xmi',
        'http://www.w3.org/2001/XMLSchema-instance': 'xsi',
    }
    _prefix_map = {}
    for _m in re.finditer(r'xmlns:(ns\d+)="([^"]+)"', xml_str):
        _auto_prefix, _uri = _m.groups()
        if _uri in _NS_EXPECTED:
            _prefix_map[_auto_prefix] = _NS_EXPECTED[_uri]
    for _auto, _target in sorted(_prefix_map.items(), key=lambda x: -len(x[0])):
        xml_str = xml_str.replace(f'xmlns:{_auto}=', f'xmlns:{_target}=')
        xml_str = re.sub(f'(?<=<){_auto}:', f'{_target}:', xml_str)
        xml_str = re.sub(f'(?<=</){_auto}:', f'{_target}:', xml_str)
        xml_str = re.sub(f'(?<=[\'" ]){_auto}:', f'{_target}:', xml_str)

    with open(output_path, "w", encoding="utf-8") as f:
        f.write(xml_str)
    return applied
