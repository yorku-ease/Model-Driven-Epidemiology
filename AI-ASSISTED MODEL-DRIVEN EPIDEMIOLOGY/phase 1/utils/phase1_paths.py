"""
Canonical paths for Phase 1 and alignment with Phase 2 gold-standard models.

Prefer, in order:
  1. ``phase 1/papers/epimde/*.compmodel`` — reference models shipped with Phase 1
  2. ``phase 2/data/diseases/<disease>/*.compmodel`` — per-paper benchmark gold models
  3. ``phase 2/data/baseline_models/*.compmodel`` — legacy flat baselines
Legacy Eclipse workspace ``../../Compartmental/CompartmentalModel`` is optional and
no longer included in default batch discovery.
"""

from __future__ import annotations

from pathlib import Path
from typing import List, Optional, Tuple


def phase1_root() -> Path:
    """Directory containing ``run_phase1.py`` (the ``phase 1`` folder)."""
    return Path(__file__).resolve().parent.parent


def project_root() -> Path:
    """``AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY`` folder."""
    return phase1_root().parent


def epimde_models_dir() -> Path:
    """Primary gold models for Phase 1 analysis."""
    return phase1_root() / "papers" / "epimde"


def phase2_data_root() -> Path:
    """Phase 2 data root."""
    return project_root() / "phase 2" / "data"


def phase2_diseases_models_dir() -> Path:
    """Phase 2 benchmark folder with per-disease subfolders."""
    return phase2_data_root() / "diseases"


def phase2_baseline_models_dir() -> Path:
    """Phase 2 legacy flat baselines dir; paired gold lives under ``phase 2/data/<disease>/``."""
    return phase2_data_root() / "baseline_models"


def legacy_compartmental_models_dir() -> Path:
    """Optional external Eclipse project path; may be absent in minimal checkouts."""
    return project_root().parent / "Compartmental" / "CompartmentalModel"


def default_model_search_dirs() -> List[Path]:
    """
    Ordered list of directories to search for ``*.compmodel`` files.
    Existence is not required; callers typically filter.
    """
    return [
        epimde_models_dir(),
        phase2_diseases_models_dir(),
        phase2_baseline_models_dir(),
    ]


def resolve_default_model_dir() -> Path:
    """
    Pick the best default directory for batch analysis:
    first path that exists and contains at least one ``.compmodel``,
    otherwise ``epimde_models_dir()`` (for a clear error message).
    """
    for d in default_model_search_dirs():
        if d.is_dir() and find_compmodel_files(d):
            return d
    return epimde_models_dir()


def resolve_fallback_compmodel_dir() -> Path:
    """
    Single directory used when code expects one legacy ``base_path``
    (e.g. uncertainty/sensitivity batch modes).
    Preference: epimde, then Phase 2 per-paper ``data/diseases``, then baselines.
    """
    return resolve_default_model_dir()


def _stem_to_display_name(stem: str) -> str:
    """Turn ``covid.compmodel`` stem into a short display name for reports."""
    s = stem.lower()
    if s == "covid":
        return "COVID-19"
    if s == "hiv":
        return "HIV"
    return " ".join(
        word.capitalize() for word in stem.replace("_", " ").replace("-", " ").split()
    )


def _find_phase2_benchmark_compmodels(directory: Path) -> List[Path]:
    """
    Find Phase 2 benchmark ``.compmodel`` files under:
      - ``data/diseases/<disease>/*.compmodel``
      - ``data/diseases/<disease>/cases/*.compmodel``
    """
    files: List[Path] = []
    if not directory.is_dir():
        return files
    for disease_dir in sorted(directory.iterdir()):
        if not disease_dir.is_dir():
            continue
        files.extend(sorted(disease_dir.glob("*.compmodel")))
        case_dir = disease_dir / "cases"
        if case_dir.is_dir():
            files.extend(sorted(case_dir.glob("*.compmodel")))
    return files


def find_compmodel_files(directory: Path) -> List[Tuple[str, Path]]:
    """
    All ``*.compmodel`` files in ``directory`` (non-recursive).

    Returns:
        List of ``(display_name, file_path)`` — same naming rules as Task 1.1.
    """
    models: List[Tuple[str, Path]] = []
    if not directory.is_dir():
        return models

    if directory.resolve() == phase2_diseases_models_dir().resolve():
        compmodel_files = _find_phase2_benchmark_compmodels(directory)
    else:
        compmodel_files = sorted(directory.glob("*.compmodel"))

    for file_path in compmodel_files:
        display = _stem_to_display_name(file_path.stem)
        models.append((display, file_path))
    return models


def find_pdf_for_compmodel(model_path: Path, papers_base_dir: Path) -> Optional[Path]:
    """
    Best-effort PDF for gap analysis: same directory as the model, then
    recursive search under ``papers_base_dir`` (e.g. ``phase 1/papers``).
    """
    if not model_path.exists():
        return None
    stem = model_path.stem
    candidates = [
        model_path.parent / f"{stem}.pdf",
        model_path.parent / f"{stem.lower()}.pdf",
    ]
    if stem.upper() == "HIV":
        candidates.extend(
            [
                model_path.parent / "hiv.pdf",
                model_path.parent / "HIV.pdf",
            ]
        )
    for c in candidates:
        if c.is_file():
            return c
    if papers_base_dir.is_dir():
        for name in (stem, stem.lower(), stem.replace("_", "-")):
            for pdf_file in papers_base_dir.rglob(f"{name}.pdf"):
                if pdf_file.is_file():
                    return pdf_file
    return None
