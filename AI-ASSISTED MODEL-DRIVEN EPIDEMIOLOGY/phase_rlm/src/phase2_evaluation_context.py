"""
Load Phase 2 evaluation JSON from a report folder and format a short string for RLM prompts.

Supports semantic (evaluation_report.json) and fuzzy-temp (evaluation_report_fuzzy_temp.json) shapes.
"""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple


DEFAULT_EVAL_FILENAMES = (
    "evaluation_report_fuzzy_temp.json",
    "evaluation_report.json",
)


def format_evaluation_summary(eval_data: Dict[str, Any]) -> str:
    """Turn Phase 2 evaluation JSON into a compact block for the repair LLM."""
    gc = eval_data.get("gold_standard_comparison")
    if gc is None:
        return "No gold_standard_comparison in evaluation file (baseline may be missing)."
    if not gc:
        return "gold_standard_comparison is empty."

    lines: List[str] = []

    if "composite_score" in gc:
        lines.append(f"Baseline composite score: {gc['composite_score']}")
    mm = gc.get("matching_method")
    if mm:
        lines.append(f"Matching method: {mm}")
    if "threshold" in gc:
        lines.append(f"Semantic threshold: {gc['threshold']}")
    if "similarity_threshold" in gc:
        lines.append(f"String/fuzzy threshold: {gc['similarity_threshold']}")

    for key in ("compartments", "flows", "parameters"):
        block = gc.get(key)
        if not isinstance(block, dict):
            continue
        f1 = block.get("f1")
        p = block.get("precision")
        r = block.get("recall")
        if f1 is not None or p is not None or r is not None:
            lines.append(
                f"  {key}: P={p} R={r} F1={f1}"
            )

    return "\n".join(lines) if lines else str(gc)[:2000]


def resolve_evaluation_path(
    report_dir: Path,
    preferred: Optional[str],
) -> Tuple[Optional[Path], List[str]]:
    """
    Pick first existing file: preferred (if set), then DEFAULT_EVAL_FILENAMES order.

    Returns (path_or_none, list of filenames tried).
    """
    tried: List[str] = []
    candidates: List[str] = []
    if preferred:
        candidates.append(preferred)
    for name in DEFAULT_EVAL_FILENAMES:
        if name not in candidates:
            candidates.append(name)

    for name in candidates:
        tried.append(name)
        p = report_dir / name
        if p.is_file():
            return p, tried
    return None, tried


def load_evaluation_for_prompt(
    report_dir: Path,
    preferred_filename: Optional[str],
) -> Tuple[str, Optional[str]]:
    """
    Load evaluation JSON and return (summary_text, path_used_as_string_or_none).
    """
    path, tried = resolve_evaluation_path(report_dir, preferred_filename)
    if path is None:
        return (
            "No Phase 2 evaluation file found (tried: "
            + ", ".join(tried)
            + "). Run rerun_evaluation_only.py or rerun_evaluation_phase2_temp.py first.",
            None,
        )
    try:
        with open(path, encoding="utf-8") as f:
            data = json.load(f)
    except (OSError, json.JSONDecodeError) as e:
        return (f"Failed to read evaluation file {path}: {e}", str(path))

    return format_evaluation_summary(data), str(path)
