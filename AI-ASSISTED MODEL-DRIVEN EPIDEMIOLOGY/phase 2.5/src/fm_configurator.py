"""Product-line-style configurator: constraint check + unit propagation (no Phase 3).

Changes vs original:
- Removed duplicate `ordered_names()` function.  Now imports
  `ordered_boolean_field_names` from fm_compile and re-exports it as
  `ordered_names` for backward compatibility.
- `vector_to_true_only_partial` comment clarified.
"""

from __future__ import annotations

from typing import Any, Dict, List, Optional, Sequence

from .epi_features import EpiFeatureVector
from .csp.sat_engine import Clause, unit_propagate

# Single source of truth for field ordering — defined in fm_compile.
from .fm_compile import ordered_boolean_field_names

# Backward-compatible alias used throughout this module and by callers.
ordered_names = ordered_boolean_field_names


def vector_to_full_assignment(vec: EpiFeatureVector) -> List[bool]:
    return [bool(getattr(vec, n)) for n in ordered_names()]


def vector_to_true_only_partial(vec: EpiFeatureVector) -> List[Optional[bool]]:
    """Positive features fixed True; others left as None (unknown).

    Unit propagation will attempt to derive additional values from the
    cross-tree CNF constraints starting from this partial assignment.
    """
    row: List[Optional[bool]] = []
    for n in ordered_names():
        row.append(True if getattr(vec, n) else None)
    return row


def violated_clause_ids(
    assign_full: Sequence[bool],
    clauses: Sequence[Clause],
    clause_ids: Sequence[str],
) -> List[str]:
    bad: List[str] = []
    for k, clause in enumerate(clauses):
        satisfied = False
        for lit in clause:
            i, pos = lit
            val = assign_full[i]
            if pos and val:
                satisfied = True
                break
            if (not pos) and (not val):
                satisfied = True
                break
        if not satisfied:
            bad.append(clause_ids[k])
    return bad


def propagate_from_partial(
    partial: List[Optional[bool]],
    clauses: Sequence[Clause],
    clause_ids: Sequence[str],
) -> Dict[str, Any]:
    working = list(partial)
    status, first_bad, trace = unit_propagate(clauses, working, clause_ids)
    mapping = ordered_names()

    labelled = {
        mapping[i]: (
            "true" if working[i] is True else ("false" if working[i] is False else "unknown")
        )
        for i in range(len(mapping))
    }

    out_trace = [
        {"clause_id": cid, "variable": mapping[j], "value": vt}
        for cid, j, vt in trace
    ]
    return {
        "solver_status_after_unit_propagation": status,
        "first_conflict_clause": first_bad,
        "assignment": labelled,
        "unit_propagation_events": out_trace,
    }


def analyze_vector(
    vec: EpiFeatureVector,
    clauses: Sequence[Clause],
    clause_ids: Sequence[str],
) -> Dict[str, Any]:
    names = ordered_names()
    full = vector_to_full_assignment(vec)
    bad_ids = violated_clause_ids(full, clauses, clause_ids)
    propagation = propagate_from_partial(vector_to_true_only_partial(vec), clauses, clause_ids)

    return {
        "purpose": (
            "Check whether disease canonical feature tuple (gold OR-merge) satisfies "
            "cross-tree CNF constraints; second sub-report forwards unit propagation "
            "starting from positives-only partial assignment."
        ),
        "full_assignment_named": {names[i]: full[i] for i in range(len(names))},
        "all_constraints_satisfied": len(bad_ids) == 0,
        "violated_constraint_ids_when_checking_named_full_vector": bad_ids,
        "positives_only_unit_propagation": propagation,
    }