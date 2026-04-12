"""
Build the legacy phase 3/selected_models/ tree from a Phase 3 showcase directory.

Used by run_phase4.py (--create-selected-models and showcase iteration helpers).
"""

from __future__ import annotations

import shutil
from pathlib import Path

ALL_MODES = ["retrieval_only", "llm_only", "both"]
MODE_ALIASES = {"rag_only": "retrieval_only"}


def normalize_mode(mode: str) -> str:
    return MODE_ALIASES.get(mode, mode)


def run_dir_stem(run_dir: Path) -> str:
    """Extract disease+paper stem from a Phase 3 run directory name.

    e.g. 'covid1_gemini_phase3' → 'covid1'
    """
    name = run_dir.name
    stem = name.split("_phase3")[0]
    return "_".join(stem.split("_")[:-1])


def resolve_showcase_mode_dir(showcase: Path, mode: str) -> Path | None:
    """Directory for one fill mode under a showcase root, or None if missing.

    Accepts legacy ``rag_only`` as an alias for ``retrieval_only`` when present.
    """
    mode = normalize_mode(mode)
    mode_dir = showcase / mode
    if mode_dir.is_dir():
        return mode_dir
    if mode == "retrieval_only":
        legacy = showcase / "rag_only"
        if legacy.is_dir():
            return legacy
    return None


def populate_selected_models_from_showcase(
    showcase_dir: Path,
    mode: str,
    output_root: Path,
    *,
    dry_run: bool = False,
) -> tuple[int, int]:
    """Copy ``model_filled.compmodel`` from one showcase mode into ``selected_models`` layout.

    Destination: ``output_root/<stem>/model_filled.compmodel`` for each run under the mode.

    Returns ``(exit_code, n_copied)`` where ``exit_code`` is 0 on success, 1 on error.
    ``mode`` must be ``retrieval_only``, ``llm_only``, or ``both`` (not ``auto``).
    """
    mode = normalize_mode(mode)
    if mode == "auto":
        return 1, 0

    mode_dir = resolve_showcase_mode_dir(showcase_dir, mode)
    if not mode_dir:
        print(f"Error: mode directory not found under {showcase_dir} (tried '{mode}', legacy 'rag_only')")
        return 1, 0

    output_root = output_root.resolve()
    copied = 0
    for run_dir in sorted(mode_dir.iterdir()):
        if not run_dir.is_dir():
            continue
        src = run_dir / "model_filled.compmodel"
        if not src.exists():
            print(f"  SKIP {run_dir.name} — no model_filled.compmodel")
            continue

        stem = run_dir_stem(run_dir)
        dest_dir = output_root / stem
        dest = dest_dir / "model_filled.compmodel"

        if dry_run:
            print(f"  Would copy: {src} → {dest}")
        else:
            dest_dir.mkdir(parents=True, exist_ok=True)
            shutil.copy2(src, dest)
            for extra in ["phase3_showcase_source.json", "phase3_validation.json"]:
                esrc = run_dir / extra
                if esrc.exists():
                    shutil.copy2(esrc, dest_dir / extra)
            print(f"  Copied: {stem}/model_filled.compmodel")
        copied += 1

    action = "Would copy" if dry_run else "Copied"
    print(f"\n{action} {copied} model(s) → {output_root}")
    return 0, copied
