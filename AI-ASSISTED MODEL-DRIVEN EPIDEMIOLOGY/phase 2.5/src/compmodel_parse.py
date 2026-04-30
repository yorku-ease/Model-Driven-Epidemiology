"""Lightweight .compmodel XML text harvest (no EMF runtime)."""

from __future__ import annotations

import re
import xml.etree.ElementTree as ET
from pathlib import Path
from typing import List, Tuple


_NS_RE = re.compile(r"\{[^}]+\}")


def _local(tag: str) -> str:
    return _NS_RE.sub("", tag)


def harvest_compmodel_text(path: Path) -> Tuple[List[str], str]:
    """Return (compartment_primary_names, concatenated_blob_for_matching)."""
    tree = ET.parse(path)
    root = tree.getroot()
    names: List[str] = []
    chunks: List[str] = []

    for el in root.iter():
        tag = _local(el.tag)
        if tag == "compartments":
            pn = el.get("PrimaryName") or ""
            sn = el.get("SecondaryName") or ""
            if pn.strip():
                names.append(pn.strip())
            if sn.strip():
                chunks.append(sn.strip())
            chunks.append(pn)
        if tag in ("outgoingFlows", "incomingFlows"):
            for attr in ("description", "xsi:type"):
                v = el.get(attr) or ""
                if v:
                    chunks.append(v)
        if tag == "externalSinks":
            v = el.get("name") or ""
            if v:
                chunks.append(v)

    blob = " \n ".join(names + chunks).lower()
    return names, blob
