"""
Paper Database — load and query the comprehensive Phase 1 + Phase 2 index.

The index is produced by  phase 3/build_database.py  and stored at
  phase 3/data/paper_database/index.json

Schema (v2):
  version, num_entries, num_parameters, total_chunks, diseases,
  knowledge_base { pattern_library, taxonomies, required_optional, ... },
  entries [ { paper_id, source_phase, disease, chunks?, model_structure?,
              extracted_entities?, promises?, evaluation?, uncertainty?,
              sensitivity?, analysis?, ... } ],
  parameter_index [ { name, value, unit, description, disease, paper_id, ... } ]
"""

import json
from pathlib import Path
from typing import Any, Dict, List, Optional


def load_paper_database(index_path: Path) -> Dict[str, Any]:
    """Load paper database index from JSON (supports both dir and file)."""
    path = Path(index_path)
    if path.is_dir():
        path = path / "index.json"
    if not path.exists():
        return {"version": 0, "entries": [], "parameter_index": [], "knowledge_base": {}}
    with open(path, "r", encoding="utf-8") as f:
        return json.load(f)


def get_entries_for_disease(index: Dict[str, Any], disease: str) -> List[Dict[str, Any]]:
    """Return all entries matching a disease (case-insensitive substring)."""
    d = disease.lower()
    return [e for e in index.get("entries", []) if d in (e.get("disease") or "").lower()]


def get_parameter_records(index: Dict[str, Any], disease: Optional[str] = None) -> List[Dict[str, Any]]:
    """Return parameter records, optionally filtered by disease."""
    params = index.get("parameter_index", [])
    if disease:
        d = disease.lower()
        params = [p for p in params if d in (p.get("disease") or "").lower()]
    return params


def get_knowledge_base(index: Dict[str, Any]) -> Dict[str, Any]:
    """Return Phase 1 knowledge assets (patterns, taxonomies, protocols, ...)."""
    return index.get("knowledge_base", {})


def get_all_chunks(index: Dict[str, Any], disease: Optional[str] = None) -> List[Dict[str, str]]:
    """Yield (paper_id, disease, chunk) dicts from entries that have text chunks."""
    out: List[Dict[str, str]] = []
    for entry in index.get("entries", []):
        if disease and disease.lower() not in (entry.get("disease") or "").lower():
            continue
        for i, chunk in enumerate(entry.get("chunks", [])):
            out.append({
                "paper_id": entry.get("paper_id", ""),
                "disease": entry.get("disease", ""),
                "chunk_index": i,
                "chunk": chunk,
            })
    return out
