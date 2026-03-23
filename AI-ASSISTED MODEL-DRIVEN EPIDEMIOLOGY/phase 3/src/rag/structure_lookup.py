"""
RAG for **compartments** and **flows** (not only parameters).

Uses the built index: text chunks (paper mentions) + optional ``flow_index`` / compartment
names from ``parameter_index``-adjacent model structures.
"""

import re
from pathlib import Path
from typing import Any, Dict, List, Optional

from .paper_database import load_paper_database

GREEK_TO_LATIN = {
    "α": "alpha", "β": "beta", "γ": "gamma", "δ": "delta",
    "ε": "epsilon", "ζ": "zeta", "η": "eta", "θ": "theta",
    "λ": "lambda", "μ": "mu", "σ": "sigma", "τ": "tau",
}


def _norm(s: str) -> str:
    for g, l in GREEK_TO_LATIN.items():
        s = s.replace(g, l)
    return re.sub(r"[^a-z0-9]", "", s.lower())


def search_compartment_in_chunks(
    index: Dict[str, Any],
    compartment_name: str,
    disease_hint: Optional[str] = None,
    max_chunks: int = 8,
) -> List[Dict[str, Any]]:
    """Find text chunks mentioning a compartment name (for evidence snippets)."""
    if not compartment_name:
        return []
    terms = {_norm(compartment_name), compartment_name.lower().strip()}
    results: List[tuple] = []
    for entry in index.get("entries", []):
        if "chunks" not in entry:
            continue
        d = (entry.get("disease") or "").lower()
        boost = 2.0 if disease_hint and disease_hint.lower() in d else 0.5
        for i, chunk in enumerate(entry["chunks"]):
            cl = chunk.lower()
            hits = sum(1 for t in terms if t and t in cl)
            if hits > 0:
                results.append((hits * boost, {
                    "paper_id": entry.get("paper_id", ""),
                    "disease": entry.get("disease", ""),
                    "chunk_index": i,
                    "chunk": chunk[:2500],
                }))
    results.sort(key=lambda x: -x[0])
    return [r[1] for r in results[:max_chunks]]


def search_flow_index(
    index: Dict[str, Any],
    flow_signature: str,
    disease_hint: Optional[str] = None,
    max_results: int = 10,
) -> List[Dict[str, Any]]:
    """
    Search ``flow_index`` for a similar ``Source->Target`` flow string.
    """
    fi = index.get("flow_index") or []
    if not fi or not flow_signature or "->" not in flow_signature:
        return []
    src, _, tgt = flow_signature.partition("->")
    ns, nt = _norm(src), _norm(tgt)
    scored: List[tuple] = []
    for row in fi:
        sig = row.get("signature", "")
        if "->" not in sig:
            continue
        a, _, b = sig.partition("->")
        sa, sb = _norm(a), _norm(b)
        score = 0.0
        if ns and sa and (ns in sa or sa in ns):
            score += 1.0
        if nt and sb and (nt in sb or sb in nt):
            score += 1.0
        if score == 0:
            continue
        if disease_hint and disease_hint.lower() in (row.get("disease") or "").lower():
            score += 1.5
        scored.append((score, row))
    scored.sort(key=lambda x: -x[0])
    return [s[1] for s in scored[:max_results]]


def structure_lookup(
    index_path: Path,
    gap_type: str,
    expected: str,
    disease_hint: str = "",
) -> Dict[str, Any]:
    """
    Unified lookup for ``missing_compartments`` or ``missing_flows``.

    Returns evidence snippets and any matching flow_index rows.
    """
    path = Path(index_path)
    if path.is_file():
        path = path.parent
    index = load_paper_database(path)

    out: Dict[str, Any] = {
        "gap_type": gap_type,
        "expected": expected,
        "disease_hint": disease_hint,
        "chunk_evidence": [],
        "flow_matches": [],
    }

    if gap_type == "missing_compartments":
        out["chunk_evidence"] = search_compartment_in_chunks(
            index, expected, disease_hint=disease_hint or None
        )
    elif gap_type == "missing_flows":
        out["flow_matches"] = search_flow_index(
            index, expected, disease_hint=disease_hint or None
        )
        # Also search chunks for the arrow phrase
        if "->" in expected:
            a, _, b = expected.partition("->")
            q = f"{a.strip()} {b.strip()} flow infection"
            for entry in index.get("entries", []):
                if "chunks" not in entry:
                    continue
                if disease_hint and disease_hint.lower() not in (entry.get("disease") or "").lower():
                    continue
                for i, chunk in enumerate(entry["chunks"][:30]):
                    if a.strip().lower()[:12] in chunk.lower() and b.strip().lower()[:12] in chunk.lower():
                        out["chunk_evidence"].append({
                            "paper_id": entry.get("paper_id", ""),
                            "chunk_index": i,
                            "chunk": chunk[:2000],
                        })
                        break
                if len(out["chunk_evidence"]) >= 5:
                    break

    return out
