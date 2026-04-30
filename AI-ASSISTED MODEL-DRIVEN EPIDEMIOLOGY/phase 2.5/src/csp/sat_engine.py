"""Boolean CNF: unit propagation + DPLL (stdlib only).

Literal ``(idx, pos)``: if ``pos`` is True the literal is ``x_idx``; if ``pos`` is False the literal is ``¬x_idx``.
Each clause is a disjunction; the formula is the conjunction of all clauses.
"""

from __future__ import annotations

from typing import List, Optional, Sequence, Tuple

Lit = Tuple[int, bool]
Clause = List[Lit]


def literal_value(assign: Sequence[Optional[bool]], lit: Lit) -> Optional[bool]:
    i, positive = lit
    v = assign[i]
    if v is None:
        return None
    return v if positive else (not v)


def unit_propagate(
    clauses: Sequence[Clause],
    assign: List[Optional[bool]],
    clause_names: Optional[Sequence[str]] = None,
) -> Tuple[str, Optional[str], List[Tuple[str, int, bool]]]:
    nm = clause_names or [f"C{i}" for i in range(len(clauses))]
    trace: List[Tuple[str, int, bool]] = []
    while True:
        progressed = False
        for ci, clause in enumerate(clauses):
            cnm = nm[ci]
            unknown: List[Lit] = []
            satisfied = False
            for lit in clause:
                lv = literal_value(assign, lit)
                if lv is True:
                    satisfied = True
                    break

                if lv is None:
                    unknown.append(lit)

            if satisfied:
                continue
            if not unknown:
                return ("conflict", cnm, trace)
            if len(unknown) != 1:
                continue
            lit = unknown[0]
            idx, pos_lit = lit
            desired = pos_lit
            cur = assign[idx]
            if cur is None:
                assign[idx] = desired
                trace.append((cnm, idx, desired))
                progressed = True
            elif cur != desired:
                return ("conflict", cnm, trace)

        if not progressed:
            return ("ok", None, trace)


def dpll(
    clauses: Sequence[Clause],
    n_vars: int,
    clause_names: Optional[Sequence[str]] = None,
) -> Optional[List[bool]]:
    assign: List[Optional[bool]] = [None] * n_vars

    def rec(a: List[Optional[bool]]) -> Optional[List[bool]]:
        aa = list(a)
        st, _bad, _tr = unit_propagate(clauses, aa, clause_names)
        if st == "conflict":
            return None

        pick: Optional[int] = None
        for i, v in enumerate(aa):
            if v is None:
                pick = i
                break
        if pick is None:
            return [bool(x) for x in aa]
        for guess in (True, False):
            bb = aa[:]
            bb[pick] = guess
            got = rec(bb)
            if got is not None:
                return got
        return None

    return rec(assign)
