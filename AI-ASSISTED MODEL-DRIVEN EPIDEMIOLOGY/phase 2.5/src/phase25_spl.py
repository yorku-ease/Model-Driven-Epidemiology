"""SPL analysis JSON per disease + feature diagram assets (shared vocab + per-disease)."""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Dict, List, Optional

from .canonical_profile import _normalize_disease_key, build_all_profiles
from .csp.sat_engine import dpll
from .diagram_export import export_disease_dot, export_dot
from .dot_render import render_dot_to_images
from .epi_features import EpiFeatureVector
from .fm_compile import compile_constraints, load_constraint_file, ordered_boolean_field_names
from .fm_configurator import analyze_vector


def load_canonical_vector(data_root: Path, report_dir: Path, disease_slug: str) -> EpiFeatureVector:
    slug = _normalize_disease_key(disease_slug)
    jp = report_dir / "canonical_by_disease" / f"{slug}.json"
    if jp.is_file():
        px = json.loads(jp.read_text(encoding="utf-8"))
        return EpiFeatureVector.from_json_dict(px["canonical_feature_vector"])
    prof = build_all_profiles(data_root)
    pl = prof.get(slug)
    if not pl:
        raise KeyError(f"Unknown disease slug {slug}")
    return EpiFeatureVector.from_json_dict(pl["canonical_feature_vector"])


def _load_canonical_dict(report_dir: Path, disease_slug: str) -> Dict[str, Any]:
    """Return the raw canonical_feature_vector dict for a disease (needed for diagram export)."""
    slug = _normalize_disease_key(disease_slug)
    jp = report_dir / "canonical_by_disease" / f"{slug}.json"
    if jp.is_file():
        px = json.loads(jp.read_text(encoding="utf-8"))
        return px["canonical_feature_vector"]
    return {}


def witness_named_bool(model: Optional[List[bool]]) -> Optional[Dict[str, bool]]:
    if not model:
        return None
    nm = ordered_boolean_field_names()
    return {nm[i]: bool(model[i]) for i in range(len(nm))}


def export_feature_diagram_assets(
    feature_tree_json: Path,
    dot_out: Path,
    image_out_dir: Path,
    basename: str = "feature_tree",
    render_images: bool = True,
    dpi: int = 144,
) -> Dict[str, Any]:
    """Export the shared vocabulary diagram (all flags shown, no assignment)."""
    export_dot(Path(feature_tree_json), Path(dot_out))
    summary: Dict[str, Any] = {"diagram_dot": str(Path(dot_out).resolve())}
    if render_images:
        rp, warns = render_dot_to_images(Path(dot_out), Path(image_out_dir), basename=basename, dpi=dpi)
        summary["render"] = rp
        if warns:
            summary["render_warnings"] = warns
    return summary


def export_disease_diagram(
    feature_tree_json: Path,
    image_out_dir: Path,
    disease_slug: str,
    canonical_vector: Dict[str, Any],
    render_images: bool = True,
    dpi: int = 144,
) -> Dict[str, Any]:
    """Export a per-disease diagram highlighting only the true flags.

    The DOT file and rendered images go under
    ``image_out_dir/per_disease/<slug>/feature_profile.<ext>``.
    """
    slug = _normalize_disease_key(disease_slug)
    out_dir = Path(image_out_dir) / "per_disease" / slug
    out_dir.mkdir(parents=True, exist_ok=True)
    dot_path = out_dir / "feature_profile.dot"

    export_disease_dot(
        tree_path=Path(feature_tree_json),
        out_path=dot_path,
        canonical_vector=canonical_vector,
        disease_slug=slug,
    )

    summary: Dict[str, Any] = {"diagram_dot": str(dot_path.resolve()), "disease": slug}
    if render_images:
        rp, warns = render_dot_to_images(dot_path, out_dir, basename="feature_profile", dpi=dpi)
        summary["render"] = rp
        if warns:
            summary["render_warnings"] = warns
    return summary


def spl_report_for_disease(
    *,
    data_root: Path,
    report_dir: Path,
    disease_slug: str,
    constraints_json: Path,
    feature_tree_json: Optional[Path] = None,
    with_witness: bool = False,
    render_disease_diagram: bool = True,
    diagram_render_images: bool = True,
    shared_feature_diagram_assets: Optional[Dict[str, Any]] = None,
) -> Dict[str, Any]:
    fnames = ordered_boolean_field_names()
    pdata = load_constraint_file(Path(constraints_json))
    clauses, cids = compile_constraints(fnames, pdata)
    vec = load_canonical_vector(data_root, report_dir, disease_slug)
    analysis = analyze_vector(vec, clauses, cids)
    slug = _normalize_disease_key(disease_slug)

    merged: Dict[str, Any] = {
        "disease_slug": slug,
        "constraints_file": str(Path(constraints_json).resolve()),
        "gold_derived_disease_feature_model": f"disease_feature_models/{slug}.json",
        **analysis,
    }

    if with_witness:
        m = dpll(clauses, len(fnames), cids)
        merged["dpll_pure_cnf_sat_witness_named"] = witness_named_bool(m)

    # Per-disease feature diagram
    if render_disease_diagram and feature_tree_json is not None:
        canonical_dict = _load_canonical_dict(report_dir, slug)
        if canonical_dict:
            fm_diagram_dir = report_dir / "fm_diagram"
            disease_diagram = export_disease_diagram(
                feature_tree_json=Path(feature_tree_json),
                image_out_dir=fm_diagram_dir,
                disease_slug=slug,
                canonical_vector=canonical_dict,
                render_images=diagram_render_images,
            )
            merged["disease_feature_diagram"] = disease_diagram

    if shared_feature_diagram_assets is not None:
        merged["shared_feature_diagram_assets"] = shared_feature_diagram_assets

    outp = report_dir / "spl_configurator" / f"{slug}_spl_report.json"
    outp.parent.mkdir(parents=True, exist_ok=True)
    outp.write_text(json.dumps(merged, indent=2), encoding="utf-8")
    merged["_spl_report_json"] = str(outp.resolve())
    return merged