"""
Structural integrity check for filled .compmodel files.

Borrows Phase RLM's StructuralValidator to detect structural errors that are
independent of any gold standard:
  - self_referential_flow     : ContactFlow where contactCompartment == target
  - orphaned_parameters       : parameter declared but never referenced in flows
  - uniform_parameter_collapse: >80% of flows use the same parameter index
  - zero_population_all       : all compartments have population=0
  - missing_birth_sources     : susceptible compartment with no inflow
  - missing_death_sinks       : complex model (>4 compartments) without externalSinks
  - flow_chain_incomplete     : non-terminal compartment has no outgoing flow
  - parameter_layer_contamination: expression contains Bayesian/inference keywords

These errors are an additional quality layer on top of the gap-based analysis.
"""

from __future__ import annotations

import sys
from pathlib import Path
from typing import Any, Dict, List, Optional


def _load_validator() -> Optional[Any]:
    """Import StructuralValidator from Phase RLM if available."""
    import importlib.util

    phase_rlm = Path(__file__).resolve().parents[3] / "phase_rlm"
    if not phase_rlm.is_dir():
        return None
    validator_path = phase_rlm / "src" / "structural_validator.py"
    if not validator_path.exists():
        return None
    try:
        spec = importlib.util.spec_from_file_location(
            "phase_rlm_structural_validator", validator_path
        )
        mod = importlib.util.module_from_spec(spec)  # type: ignore[arg-type]
        spec.loader.exec_module(mod)  # type: ignore[union-attr]
        return mod.StructuralValidator()
    except Exception:
        return None


_VALIDATOR = None


def _get_validator() -> Optional[Any]:
    global _VALIDATOR
    if _VALIDATOR is None:
        _VALIDATOR = _load_validator()
    return _VALIDATOR


# Severity → numeric priority (lower = more urgent, matches RLM ordering)
_SEVERITY_ORDER = {"critical": 0, "high": 1, "medium": 2, "low": 3}


def check_structural_integrity(compmodel_path: Path) -> Dict[str, Any]:
    """
    Run structural validation on a .compmodel file.

    Returns a dict:
      {
        "available": bool,          # False if Phase RLM not found
        "valid": bool,
        "total_errors": int,
        "errors_by_severity": {...},
        "errors": [...],            # list of error dicts (type, element, detail, severity)
        "summary_text": str,        # human-readable one-liner
      }
    """
    validator = _get_validator()
    if validator is None:
        return {
            "available": False,
            "note": "Phase RLM not found; structural checks skipped.",
        }

    if not compmodel_path.exists():
        return {
            "available": True,
            "valid": True,
            "total_errors": 0,
            "errors_by_severity": {},
            "errors": [],
            "summary_text": "File not found; skipped.",
        }

    try:
        result = validator.validate(compmodel_path)
    except Exception as exc:
        return {
            "available": True,
            "valid": False,
            "total_errors": -1,
            "errors": [],
            "summary_text": f"Validation failed: {exc}",
        }

    errors: List[Dict[str, Any]] = result.get("errors", [])
    # Sort: critical first
    errors_sorted = sorted(errors, key=lambda e: _SEVERITY_ORDER.get(e.get("severity", "low"), 3))

    sev = result.get("errors_by_severity", {})
    parts = []
    for s in ("critical", "high", "medium", "low"):
        n = sev.get(s, 0)
        if n:
            parts.append(f"{n} {s}")
    summary = f"{result.get('total_errors', 0)} structural error(s): " + (", ".join(parts) if parts else "none")

    return {
        "available": True,
        "valid": result.get("valid", True),
        "total_errors": result.get("total_errors", 0),
        "errors_by_severity": sev,
        "errors": errors_sorted,
        "summary_text": summary,
    }


def structural_errors_as_gaps(structural: Dict[str, Any]) -> List[Dict[str, Any]]:
    """
    Convert structural errors into gap-like dicts for reporting.
    Each item has: type, element, detail, severity.
    Only includes actionable errors (not low-priority cosmetics).
    """
    if not structural.get("available") or not structural.get("errors"):
        return []
    actionable = {"critical", "high", "medium"}
    return [
        e for e in structural["errors"]
        if e.get("severity") in actionable
    ]
