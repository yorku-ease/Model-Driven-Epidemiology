"""Compile cross_tree_constraints.json into CNF clauses over ordered Epi booleans."""

from __future__ import annotations

import json
from dataclasses import fields
from pathlib import Path
from typing import Dict, List, Sequence, Tuple

from .epi_features import EpiFeatureVector
from .csp.sat_engine import Clause


def ordered_boolean_field_names() -> List[str]:
    return [f.name for f in fields(EpiFeatureVector) if f.name != "matched_signals"]


def compile_constraints(names: Sequence[str], data: dict) -> Tuple[List[Clause], List[str]]:
    idx = {n: i for i, n in enumerate(names)}
    clauses: List[Clause] = []
    cids: List[str] = []

    for imp in data.get("implies", []):
        if imp.get("enabled", True) is False:
            continue
        a, b = imp["if"], imp["then"]
        if a not in idx or b not in idx:
            raise KeyError(f"{imp.get('id')}: bad feature name in implies")
        clauses.append([(idx[a], False), (idx[b], True)])
        cids.append(imp.get("id", f"impl_{len(cids)}"))

    for mx in data.get("mutex", []):
        if mx.get("enabled", True) is False:
            continue
        fts = [x for x in mx.get("features", []) if x in idx]
        if len(fts) < 2:
            continue
        clauses.append([(idx[x], False) for x in fts])
        cids.append(mx.get("id", f"mutex_{len(cids)}"))

    return clauses, cids


def load_constraint_file(path: Path) -> dict:
    return json.loads(path.read_text(encoding="utf-8"))
