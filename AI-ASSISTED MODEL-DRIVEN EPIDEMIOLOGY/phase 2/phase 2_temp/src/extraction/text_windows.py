"""Utilities for selecting the most relevant paper text for LLM prompts.

Goal: avoid sending full paper text to LLM; instead build small, task-specific
windows (promises, compartments, flows, parameters) from per-page text and tables.
"""

from __future__ import annotations

import re
from typing import Any, Dict, Iterable, List, Optional, Sequence, Tuple


def _compile_patterns(patterns: Sequence[str]) -> List[re.Pattern]:
    return [re.compile(p, flags=re.IGNORECASE) for p in patterns]


def select_page_indices(
    pages: Sequence[Dict[str, Any]],
    include_patterns: Sequence[str],
    *,
    exclude_patterns: Optional[Sequence[str]] = None,
    pad: int = 1,
    max_pages: int = 8,
    fallback_first_pages: int = 4,
) -> List[int]:
    """Select page indices matching patterns with optional padding."""
    if not pages:
        return []

    include = _compile_patterns(include_patterns) if include_patterns else []
    exclude = _compile_patterns(exclude_patterns) if exclude_patterns else []

    hits: List[int] = []
    for i, p in enumerate(pages):
        txt = (p.get("text") or "")
        if not txt:
            continue
        if exclude and any(rx.search(txt) for rx in exclude):
            continue
        if include and any(rx.search(txt) for rx in include):
            hits.append(i)

    if not hits:
        # fallback: first few pages usually contain model description / setup
        hits = list(range(min(fallback_first_pages, len(pages))))

    expanded = set()
    for i in hits:
        for j in range(max(0, i - pad), min(len(pages), i + pad + 1)):
            expanded.add(j)

    selected = sorted(expanded)
    if len(selected) > max_pages:
        selected = selected[:max_pages]
    return selected


def build_text_window(
    pages: Sequence[Dict[str, Any]],
    include_patterns: Sequence[str],
    *,
    title: str,
    max_chars: int = 20000,
    exclude_patterns: Optional[Sequence[str]] = None,
    pad: int = 1,
    max_pages: int = 8,
    fallback_first_pages: int = 4,
) -> str:
    """Build a tagged window text from selected pages."""
    idxs = select_page_indices(
        pages,
        include_patterns,
        exclude_patterns=exclude_patterns,
        pad=pad,
        max_pages=max_pages,
        fallback_first_pages=fallback_first_pages,
    )
    chunks: List[str] = [f"[WINDOW: {title}]"]
    total = 0

    for i in idxs:
        p = pages[i]
        page_no = p.get("page_number", i + 1)
        txt = (p.get("text") or "").strip()
        if not txt:
            continue
        block = f"\n[PAGE {page_no}]\n{txt}\n"
        if total + len(block) > max_chars:
            remaining = max(0, max_chars - total)
            if remaining > 200:
                chunks.append(block[:remaining] + "\n[... truncated ...]\n")
            break
        chunks.append(block)
        total += len(block)

    return "\n".join(chunks).strip()


def format_tables_for_prompt(
    tables: Sequence[Dict[str, Any]],
    *,
    title: str = "TABLES",
    max_tables: int = 3,
    max_rows: int = 12,
    max_chars: int = 12000,
) -> str:
    """Convert extracted tables into a compact text representation for prompts."""
    if not tables:
        return ""

    out: List[str] = [f"[{title}]"]
    total = 0
    used = 0

    for t in tables:
        if used >= max_tables:
            break
        data = t.get("data") or []
        if not data:
            continue

        page_no = t.get("page_number", t.get("page", ""))
        table_no = t.get("table_number", "")
        header = f"\n[TABLE {table_no} PAGE {page_no}]"
        rows = data[: max_rows]
        lines = []
        for row in rows:
            if isinstance(row, (list, tuple)):
                lines.append(" | ".join(str(c).strip() for c in row if c is not None))
            else:
                lines.append(str(row).strip())
        block = header + "\n" + "\n".join(lines) + "\n"

        if total + len(block) > max_chars:
            break
        out.append(block)
        total += len(block)
        used += 1

    return "\n".join(out).strip()

