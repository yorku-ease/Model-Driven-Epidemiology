"""SPL analysis JSON per disease + one-shot feature diagram assets."""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Dict, List, Optional

from .canonical_profile import _normalize_disease_key, build_all_profiles
from .csp.sat_engine import dpll
from .diagram_export import export_dot
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
    export_dot(Path(feature_tree_json), Path(dot_out))
    summary: Dict[str, Any] = {"diagram_dot": str(Path(dot_out).resolve())}
    if render_images:
        rp, warns = render_dot_to_images(Path(dot_out), Path(image_out_dir), basename=basename, dpi=dpi)
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
    with_witness: bool = False,
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
    if shared_feature_diagram_assets is not None:
        merged["shared_feature_diagram_assets"] = shared_feature_diagram_assets
    outp = report_dir / "spl_configurator" / f"{slug}_spl_report.json"
    outp.parent.mkdir(parents=True, exist_ok=True)
    outp.write_text(json.dumps(merged, indent=2), encoding="utf-8")
    merged["_spl_report_json"] = str(outp.resolve())
    return merged
