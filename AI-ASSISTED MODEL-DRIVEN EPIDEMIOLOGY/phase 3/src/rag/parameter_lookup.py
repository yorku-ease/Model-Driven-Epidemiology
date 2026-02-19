"""
Parameter Lookup with RAG — two-tier search strategy:

  1) **Direct index lookup**: search the flat parameter_index for matching
     parameter names (with Greek/Latin normalization).

  2) **Text chunk search**: keyword-based search over paper text chunks,
     with regex value extraction from the chunk text.
"""

import re
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple

from .paper_database import load_paper_database

GREEK_TO_LATIN = {
    "α": "alpha", "β": "beta", "γ": "gamma", "δ": "delta",
    "ε": "epsilon", "ζ": "zeta", "η": "eta", "θ": "theta",
    "ι": "iota", "κ": "kappa", "λ": "lambda", "μ": "mu",
    "ν": "nu", "ξ": "xi", "π": "pi", "ρ": "rho",
    "σ": "sigma", "τ": "tau", "φ": "phi", "χ": "chi",
    "ψ": "psi", "ω": "omega",
}


def _normalize_name(s: str) -> str:
    for greek, latin in GREEK_TO_LATIN.items():
        s = s.replace(greek, latin)
    return re.sub(r"[^a-z0-9]", "", s.lower())


# ─── Tier 1: parameter index search ────────────────────────────────────────

def search_parameter_index(
    index: Dict[str, Any],
    parameter_name: str,
    disease_hint: Optional[str] = None,
    max_results: int = 10,
) -> List[Dict[str, Any]]:
    """
    Search the flat parameter_index for records matching the parameter name.
    Uses normalized name matching (handles Greek/Latin, underscores, etc.).
    """
    params = index.get("parameter_index", [])
    if not params or not parameter_name:
        return []

    norm_query = _normalize_name(parameter_name)
    if not norm_query:
        return []

    scored: List[Tuple[float, Dict[str, Any]]] = []
    for p in params:
        p_name = _normalize_name(p.get("name", ""))
        if not p_name:
            continue

        # Exact match is best
        if p_name == norm_query:
            score = 3.0
        elif norm_query in p_name or p_name in norm_query:
            score = 1.5
        else:
            continue

        # Boost same disease
        if disease_hint and disease_hint.lower() in (p.get("disease") or "").lower():
            score *= 2.0

        # Prefer entries that actually have values
        val = p.get("value", "")
        if val and str(val).strip() not in ("", "0", "0.0", "None"):
            score *= 1.5

        scored.append((score, p))

    scored.sort(key=lambda x: -x[0])
    return [s[1] for s in scored[:max_results]]


# ─── Tier 2: text chunk search ─────────────────────────────────────────────

def search_text_chunks(
    index: Dict[str, Any],
    parameter_name: str,
    disease_hint: Optional[str] = None,
    max_chunks: int = 10,
) -> List[Dict[str, Any]]:
    """
    Search paper text chunks for mentions of the parameter name.
    Returns ranked list of matching chunks.
    """
    if not parameter_name:
        return []

    norm = _normalize_name(parameter_name)
    # Build search patterns: both the original name and normalized version
    search_terms = list({parameter_name.lower(), norm})
    # Also try Greek if we have Latin
    for latin, greek in {v: k for k, v in GREEK_TO_LATIN.items()}.items():
        if latin in norm:
            search_terms.append(greek)

    results: List[Tuple[float, Dict[str, Any]]] = []
    for entry in index.get("entries", []):
        if "chunks" not in entry:
            continue
        disease = (entry.get("disease") or "").lower()
        disease_boost = 2.0 if disease_hint and disease == disease_hint.lower() else 0.5
        for i, chunk in enumerate(entry["chunks"]):
            cl = chunk.lower()
            hits = sum(1.0 for t in search_terms if t in cl)
            if hits > 0:
                score = hits * disease_boost
                results.append((score, {
                    "paper_id": entry["paper_id"],
                    "disease": entry.get("disease", ""),
                    "chunk_index": i,
                    "chunk": chunk[:2000],
                    "relevance": round(min(1.0, score / 2), 3),
                }))

    results.sort(key=lambda x: -x[0])
    return [r[1] for r in results[:max_chunks]]


def extract_value_from_chunk(chunk: str, parameter_name: str) -> Optional[Dict[str, Any]]:
    """
    Try to extract a numeric parameter value from a text chunk using regex.
    """
    name_esc = re.escape(parameter_name)
    patterns = [
        rf"\b{name_esc}\s*[=:≈]\s*([0-9]+\.?[0-9]*(?:[eE][+-]?\d+)?)\s*([a-zA-Z%/^-]+)?",
        rf"\b{name_esc}\s+(?:is\s+)?([0-9]+\.?[0-9]*(?:[eE][+-]?\d+)?)\s*([a-zA-Z%/^-]+)?",
        rf"([0-9]+\.?[0-9]*(?:[eE][+-]?\d+)?)\s*\(.*?{name_esc}.*?\)",
    ]
    for pat in patterns:
        m = re.search(pat, chunk, re.IGNORECASE)
        if m:
            value_str = m.group(1).strip()
            unit = m.group(2).strip() if m.lastindex >= 2 and m.group(2) else None
            try:
                value = float(value_str)
                return {
                    "value": value,
                    "unit": unit,
                    "source": "regex_from_chunk",
                    "text_snippet": chunk[max(0, m.start() - 60):m.end() + 60],
                }
            except ValueError:
                return {
                    "value": value_str,
                    "unit": unit,
                    "source": "regex_from_chunk",
                    "text_snippet": chunk[max(0, m.start() - 60):m.end() + 60],
                }
    return None


# ─── Combined lookup ───────────────────────────────────────────────────────

def parameter_lookup(
    index_path: Path,
    query: str,
    disease_hint: Optional[str] = None,
    parameter_name: Optional[str] = None,
) -> Dict[str, Any]:
    """
    Two-tier parameter lookup:
      1) Search parameter_index by parameter name (structured)
      2) Search text chunks by parameter name (unstructured + regex)
    """
    index = load_paper_database(index_path)
    pname = parameter_name or query

    # Tier 1: structured index
    idx_matches = search_parameter_index(index, pname, disease_hint=disease_hint)

    # Tier 2: text chunks
    chunk_snippets = search_text_chunks(index, pname, disease_hint=disease_hint, max_chunks=10)

    # Try regex extraction from chunks
    extracted_value = None
    if pname and chunk_snippets:
        for s in chunk_snippets:
            ev = extract_value_from_chunk(s["chunk"], pname)
            if ev:
                ev["paper_id"] = s["paper_id"]
                extracted_value = ev
                break

    # If no regex hit but index had a direct value, use that
    if not extracted_value and idx_matches:
        best = idx_matches[0]
        val = best.get("value", "")
        if val and str(val).strip() not in ("", "0", "0.0", "None"):
            extracted_value = {
                "value": val,
                "unit": best.get("unit", ""),
                "source": "parameter_index",
                "paper_id": best.get("paper_id", ""),
                "description": best.get("description", ""),
            }

    all_sources = list({m.get("paper_id", "") for m in idx_matches} | {s["paper_id"] for s in chunk_snippets})

    return {
        "query": query,
        "disease_hint": disease_hint,
        "found": bool(idx_matches or chunk_snippets),
        "index_matches": idx_matches[:5],
        "chunk_snippets": chunk_snippets[:5],
        "extracted_value": extracted_value,
        "sources": all_sources,
    }
