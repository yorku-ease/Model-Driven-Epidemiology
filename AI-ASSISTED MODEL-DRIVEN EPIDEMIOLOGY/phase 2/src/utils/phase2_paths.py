"""
Canonical paths for Phase 2.

Primary benchmark layout (multiple papers per disease)::

    phase 2/data/diseases/<disease>/covid1.pdf
    phase 2/data/diseases/<disease>/covid1.compmodel   # same stem as the PDF

Optional per-disease ``cases/`` subfolder still works::

    phase 2/data/diseases/<disease>/cases/covid1.pdf

Legacy (no ``diseases/`` wrapper): ``data/<disease>/`` or ``data/<disease>/cases/``.

Reserved top-level dirs under ``data/`` (not disease folders): ``baseline_models``,
``papers``, ``examples``, ``cases``.

Phase 1 corpus (``--paper-id``) still uses ``phase 1/data/papers/``; gold resolution
falls back to paired files when the resolved PDF lives under ``phase 2/data/``.
"""

from __future__ import annotations

import json
import re
from pathlib import Path
from typing import Any, Dict, List, Optional

# --- roots ---


def phase2_root() -> Path:
    """Directory containing ``run_phase2.py`` (the ``phase 2`` folder)."""
    return Path(__file__).resolve().parent.parent.parent


def phase1_root() -> Path:
    """Sibling ``phase 1`` folder."""
    return phase2_root().parent / "phase 1"


def project_root() -> Path:
    """``AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY`` folder."""
    return phase2_root().parent


def phase2_data_root() -> Path:
    """``phase 2/data`` — disease subfolders and reserved dirs."""
    return phase2_root() / "data"


# Not disease-name folders (legacy / tooling). ``cases`` reserved so only
# ``data/<disease>/cases/`` holds benchmark pairs, not ``data/cases/``.
DATA_RESERVED_TOP_DIRS = frozenset({"baseline_models", "papers", "examples", "cases"})

# PDF + gold pairs may live in an optional per-disease subfolder (legacy / optional).
DATA_CASE_SUBDIR = "cases"

# Canonical wrapper: ``data/diseases/<disease_name>/`` (keeps benchmarks separate from
# ``baseline_models/``, ``papers/``, etc.).
DATA_DISEASES_SUBDIR = "diseases"


def collection_index_path() -> Path:
    return phase1_root() / "data" / "papers" / "collection_index.json"


def paper_slot_dir(paper_id: str) -> Path:
    return phase1_root() / "data" / "papers" / paper_id


def epimde_dir() -> Path:
    return phase1_root() / "papers" / "epimde"


def default_baseline_models_dir() -> Path:
    return phase2_data_root() / "baseline_models"


# Map collection ``disease`` display names to baseline ``*.compmodel`` stem (lowercase).
DISEASE_TO_BASELINE_STEM: Dict[str, str] = {
    "COVID-19": "covid",
    "Malaria": "malaria",
    "HIV": "hiv",
    "Cholera": "cholera",
    "Dengue": "dengue",
    "Ebola": "ebola",
    "Influenza": "influenza",
    "Measles": "measles",
    "Tuberculosis": "tuberculosis",
    "Zika": "zika",
}


def _resolve_under_phase1(p: str | Path) -> Path:
    raw = Path(p)
    if raw.is_absolute():
        return raw
    return (phase1_root() / raw).resolve()


def load_collection_index() -> Dict[str, Any]:
    path = collection_index_path()
    if not path.is_file():
        return {"papers": []}
    with open(path, "r", encoding="utf-8") as f:
        return json.load(f)


def find_paper_record(paper_id: str) -> Optional[Dict[str, Any]]:
    data = load_collection_index()
    for rec in data.get("papers", []):
        if rec.get("id") == paper_id:
            return rec
    return None


def load_paper_metadata(paper_id: str) -> Optional[Dict[str, Any]]:
    meta_path = paper_slot_dir(paper_id) / "metadata.json"
    if not meta_path.is_file():
        return None
    with open(meta_path, "r", encoding="utf-8") as f:
        return json.load(f)


def iter_benchmark_disease_dirs(data: Optional[Path] = None) -> List[Path]:
    """
    Leaf folders that hold benchmark PDFs + ``.compmodel`` files.

    - ``data/diseases/<disease>/`` (canonical), and
    - ``data/<disease>/`` (legacy, excluding reserved top-level names).
    """
    root = data if data is not None else phase2_data_root()
    if not root.is_dir():
        return []
    out: List[Path] = []
    nested = root / DATA_DISEASES_SUBDIR
    if nested.is_dir():
        for sub in sorted(nested.iterdir()):
            if sub.is_dir():
                out.append(sub)
    for sub in sorted(root.iterdir()):
        if not sub.is_dir() or sub.name in DATA_RESERVED_TOP_DIRS:
            continue
        if sub.name == DATA_DISEASES_SUBDIR:
            continue
        out.append(sub)
    return out


def iter_benchmark_pdf_paths(data: Optional[Path] = None) -> List[Path]:
    """All ``*.pdf`` benchmark papers (canonical + legacy layouts)."""
    root = data if data is not None else phase2_data_root()
    pdfs: List[Path] = []
    for disease_dir in iter_benchmark_disease_dirs(root):
        case_dir = disease_dir / DATA_CASE_SUBDIR
        if case_dir.is_dir():
            pdfs.extend(sorted(case_dir.glob("*.pdf")))
        else:
            pdfs.extend(sorted(disease_dir.glob("*.pdf")))
    return pdfs


def _compmodel_in_dir(directory: Path, stem: str) -> Optional[Path]:
    """``directory/<stem>.compmodel`` if present (case-insensitive stem match on disk)."""
    if not directory.is_dir():
        return None
    exact = directory / f"{stem}.compmodel"
    if exact.is_file():
        return exact
    low = stem.lower()
    for p in directory.glob("*.compmodel"):
        if p.stem.lower() == low:
            return p
    return None


def is_paired_case_pdf(pdf_path: Path) -> bool:
    """
    True if ``pdf_path`` is a benchmark PDF under Phase 2 ``data/``:

    - ``data/diseases/<disease>/<stem>.pdf`` (canonical), or
    - ``data/diseases/<disease>/cases/<stem>.pdf``, or
    - ``data/<disease>/<stem>.pdf`` (legacy), or
    - ``data/<disease>/cases/<stem>.pdf``.

    Top-level ``<disease>`` must not be a reserved name.
    """
    pdf_path = pdf_path.resolve()
    data = phase2_data_root()
    try:
        rel = pdf_path.relative_to(data)
    except ValueError:
        return False
    parts = rel.parts
    n = len(parts)
    if n == 2:
        folder, fname = parts[0], parts[1]
        if folder in DATA_RESERVED_TOP_DIRS:
            return False
        return fname.lower().endswith(".pdf")
    if n == 3:
        a, b, fname = parts[0], parts[1], parts[2]
        if a == DATA_DISEASES_SUBDIR:
            return fname.lower().endswith(".pdf")
        if a in DATA_RESERVED_TOP_DIRS:
            return False
        if b == DATA_CASE_SUBDIR:
            return fname.lower().endswith(".pdf")
        return False
    if n == 4:
        a, b, c, fname = parts[0], parts[1], parts[2], parts[3]
        if a == DATA_DISEASES_SUBDIR and c == DATA_CASE_SUBDIR:
            return fname.lower().endswith(".pdf")
        return False
    return False


def paired_gold_for_pdf(pdf_path: Path) -> Optional[Path]:
    """``<same_dir>/<stem>.compmodel`` if that file exists (case-insensitive stem)."""
    parent = pdf_path.resolve().parent
    return _compmodel_in_dir(parent, pdf_path.stem)


def find_gold_compmodel_for_run_stem(run_stem: str) -> Optional[Path]:
    """
    Find ``*.compmodel`` for a report run stem (e.g. ``covid2``) under benchmark
    disease folders. Prefers ``cases/`` when present. Case-insensitive stem match.
    """
    run_stem = run_stem.lower()
    data = phase2_data_root()
    if not data.is_dir():
        return None
    found: List[Path] = []
    for disease_dir in iter_benchmark_disease_dirs(data):
        case_dir = disease_dir / DATA_CASE_SUBDIR
        c = _compmodel_in_dir(case_dir, run_stem) if case_dir.is_dir() else None
        r = _compmodel_in_dir(disease_dir, run_stem)
        if c:
            found.append(c)
        elif r:
            found.append(r)
    if not found:
        return None
    return sorted(found)[0]


def resolve_pdf_path_for_paper_id(paper_id: str) -> Path:
    """
    Resolve PDF for a corpus paper id.

    Order:
      1. ``metadata.json`` ``pdfPath`` (relative to phase 1 root or absolute)
      2. Record ``pdfPath`` from ``collection_index.json``
      3. Default ``phase 1/data/papers/<id>/<id>.pdf``
    """
    meta = load_paper_metadata(paper_id)
    rec = find_paper_record(paper_id)

    for src in (meta, rec):
        if not src:
            continue
        pdf_path = src.get("pdfPath")
        if pdf_path:
            p = _resolve_under_phase1(pdf_path)
            if p.is_file():
                return p

    default_pdf = paper_slot_dir(paper_id) / f"{paper_id}.pdf"
    if default_pdf.is_file():
        return default_pdf

    raise FileNotFoundError(
        f"No PDF found for paper id {paper_id!r}. "
        f"Set pdfPath in metadata.json or collection_index, or add {default_pdf}"
    )


def baseline_stem_for_disease(disease: str) -> str:
    """Return ``*.compmodel`` filename stem for a collection disease label."""
    if disease in DISEASE_TO_BASELINE_STEM:
        return DISEASE_TO_BASELINE_STEM[disease]
    cleaned = re.sub(r"[^a-zA-Z0-9]+", "_", disease.strip()).strip("_").lower()
    return cleaned or "model"


def resolve_gold_compmodel_path(
    paper_id: str,
    *,
    baseline_dir: Optional[Path] = None,
) -> Optional[Path]:
    """
    Resolve gold-standard ``.compmodel`` for ``--paper-id`` evaluation.

    Order:
      1. ``metadata.json`` / index ``goldCompmodelPath`` (relative to phase 1 root)
      2. Paired same-directory gold for the resolved PDF (Phase 2 ``data/`` layout)
      3. ``find_gold_compmodel_for_run_stem`` from PDF file stem
      4. Legacy ``baseline_models/{disease_stem}.compmodel`` from index disease
      5. ``phase 1/papers/epimde/{stem}.compmodel``
    """
    baseline_dir = baseline_dir or default_baseline_models_dir()
    meta = load_paper_metadata(paper_id)
    rec = find_paper_record(paper_id) or {}

    for src in (meta, rec):
        if not src:
            continue
        g = src.get("goldCompmodelPath")
        if g:
            p = _resolve_under_phase1(g)
            if p.is_file():
                return p

    try:
        pdf = resolve_pdf_path_for_paper_id(paper_id)
    except FileNotFoundError:
        pdf = None

    if pdf is not None:
        pg = paired_gold_for_pdf(pdf)
        if pg:
            return pg
        by_stem = find_gold_compmodel_for_run_stem(pdf.stem)
        if by_stem:
            return by_stem

    disease = (meta or rec).get("disease") or ""
    stem = baseline_stem_for_disease(str(disease))

    for folder in (baseline_dir, epimde_dir()):
        if folder.is_dir():
            cand = folder / f"{stem}.compmodel"
            if cand.is_file():
                return cand

    return None


def list_paper_ids() -> List[str]:
    data = load_collection_index()
    return [p["id"] for p in data.get("papers", []) if p.get("id")]


def resolve_gold_path_for_phase2_report(
    report_dir: Path, baseline_dir: Path
) -> Optional[Path]:
    """
    Gold ``.compmodel`` for a Phase 2 report folder named ``{run_stem}_llm_{provider}_{ts}``.

    Order:
      1. Benchmark gold under ``data/diseases/`` or legacy ``data/<disease>/`` (see ``find_gold_compmodel_for_run_stem``)
      2. Legacy fuzzy match under ``baseline_dir`` (flat ``*.compmodel``)
    """
    import re as _re

    name = report_dir.name
    if "_llm_" not in name:
        return None
    run_stem = name.split("_llm_")[0].lower()

    g = find_gold_compmodel_for_run_stem(run_stem)
    if g:
        return g

    if not baseline_dir.is_dir():
        return None

    paper_normalized = _re.sub(r"[_\-\s]+", " ", run_stem)
    paper_keywords = set(
        _re.split(
            r"[_\-\s]+|(?<=[a-z])(?=[A-Z0-9])|(?<=[0-9])(?=[A-Za-z])",
            paper_normalized,
        )
    )
    paper_keywords = {k.lower() for k in paper_keywords if k.strip()}

    for baseline_file in sorted(baseline_dir.glob("*.compmodel")):
        baseline_stem = baseline_file.stem.lower()
        baseline_normalized = _re.sub(r"[_\-\s]+", " ", baseline_stem)
        baseline_keywords = set(
            _re.split(
                r"[_\-\s]+|(?<=[a-z])(?=[A-Z0-9])|(?<=[0-9])(?=[A-Za-z])",
                baseline_normalized,
            )
        )
        baseline_keywords = {k.lower() for k in baseline_keywords if k.strip()}

        common_diseases = [
            "ebola",
            "covid",
            "malaria",
            "hiv",
            "flu",
            "tuberculosis",
            "tb",
        ]
        has_common_disease = any(
            disease in run_stem and disease in baseline_stem
            for disease in common_diseases
        )

        if (
            run_stem in baseline_stem
            or baseline_stem in run_stem
            or len(paper_keywords & baseline_keywords) > 0
            or has_common_disease
        ):
            return baseline_file
    return None
