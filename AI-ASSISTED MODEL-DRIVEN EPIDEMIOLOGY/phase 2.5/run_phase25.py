#!/usr/bin/env python3
"""Phase 2.5: gold-derived disease feature models, Phase 2 draft vs FM comparison, SPL checks, diagrams (DOT+PNG/SVG).

With default flags this runs: canonical profiles, per-disease ``disease_feature_models/<slug>.json`` bundles,
feature-tree diagram exports (shared vocabulary + one per-disease diagram per disease),
SPL report per disease, optional batch validation of Phase 2 ``model_draft`` paths,
and merged ``phase25_run_summary.json``.
"""

from __future__ import annotations

import argparse
import json
import re
import sys
from pathlib import Path
from typing import Any, Dict, List, Optional

_ROOT = Path(__file__).resolve().parent
if str(_ROOT) not in sys.path:
    sys.path.insert(0, str(_ROOT))

from src.canonical_profile import (
    _normalize_disease_key,
    build_all_profiles,
    build_canonical_profile_for_disease,
    discover_disease_dirs,
    save_profiles,
)
from src.disease_feature_model import save_disease_feature_models

from src.validator import compare_draft_to_canonical, load_canonical_vector_from_profile_json

from src.phase25_spl import export_feature_diagram_assets, spl_report_for_disease


def infer_disease_slug_from_phase2_folder(dirname: str) -> Optional[str]:
    mm = re.match(r"([A-Za-z]+)\d+_", dirname)
    return mm.group(1).lower() if mm else None


def validate_all_phase2_drafts(
    *,
    profiles: Dict[str, Any],
    phase2_reports: Path,
    report_dir: Path,
) -> List[Dict[str, Any]]:
    """Every ``model_draft.compmodel`` under Phase 2 reports whose folder name matches ``<disease><digit>_``."""

    out: List[Dict[str, Any]] = []
    if not phase2_reports.is_dir():
        return out
    for draft in sorted(phase2_reports.glob("**/model_draft.compmodel")):
        parent = draft.parent.name
        slug = infer_disease_slug_from_phase2_folder(parent)
        if not slug or slug not in profiles:
            continue
        fm_rel = f"disease_feature_models/{slug}.json"
        canonical = load_canonical_vector_from_profile_json(profiles[slug])
        rec = compare_draft_to_canonical(draft.resolve(), canonical, slug, disease_fm_relpath=fm_rel)
        rec["phase2_run_folder"] = parent
        rec["draft_path"] = str(draft.resolve())
        out.append(rec)
        dest = report_dir / "draft_validations" / f"{slug}_{parent}_phase25_validation.json"
        dest.parent.mkdir(parents=True, exist_ok=True)
        dest.write_text(json.dumps(rec, indent=2), encoding="utf-8")
    return out


def main() -> int:
    ap = argparse.ArgumentParser(description="Phase 2.5 full pipeline (defaults: run everything)")
    ap.add_argument("--data-root", type=Path, default=_ROOT.parent / "phase 2" / "data")
    ap.add_argument("--report-dir", type=Path, default=_ROOT / "reports")
    ap.add_argument(
        "--phase2-reports",
        type=Path,
        default=_ROOT.parent / "phase 2" / "reports",
        help="Scan for model_draft.compmodel for batch validation (skip with --no-validate-phase2-drafts)",
    )
    ap.add_argument(
        "--feature-tree",
        type=Path,
        default=_ROOT / "feature_models" / "feature_tree.json",
    )
    ap.add_argument(
        "--constraints",
        type=Path,
        default=_ROOT / "feature_models" / "cross_tree_constraints.json",
    )
    ap.add_argument("--validate-draft", type=Path, default=None, help="Single draft .compmodel to score")
    ap.add_argument(
        "--disease",
        type=str,
        default=None,
        help="Normalize one disease only (narrow mode) or paired with single --validate-draft",
    )

    # narrow / skip flags
    ap.add_argument("--profiles-only", action="store_true", help="Only canonical_profiles.json (+ per-slug)")
    ap.add_argument("--no-spl", action="store_true", help="Skip SPL CNF JSON per disease")
    ap.add_argument("--no-diagram-images", action="store_true", help="Write DOT only; skip PNG/SVG")
    ap.add_argument("--no-diagram", action="store_true", help="Skip all feature tree diagrams")
    ap.add_argument("--no-validate-phase2-drafts", action="store_true")
    ap.add_argument("--with-witness", action="store_true", help="Add DPLL witness in SPL JSON")

    args = ap.parse_args()
    data_root = args.data_root.expanduser().resolve()
    report_dir = args.report_dir.expanduser().resolve()
    fm_dot = report_dir / "fm_diagram" / "feature_tree.dot"
    fm_diagram_dir = report_dir / "fm_diagram"

    if args.validate_draft:
        if not args.disease:
            print("ERROR: --validate-draft requires --disease <slug>", file=sys.stderr)
            return 2
        profiles = build_all_profiles(data_root)
        save_profiles(report_dir, profiles)
        save_disease_feature_models(report_dir, profiles)
        slug = _normalize_disease_key(args.disease)
        if slug not in profiles:
            print(f"No disease '{slug}'. Available: {list(profiles.keys())}", file=sys.stderr)
            return 2
        canonical = load_canonical_vector_from_profile_json(profiles[slug])
        fm_rel = f"disease_feature_models/{slug}.json"
        out = compare_draft_to_canonical(args.validate_draft.resolve(), canonical, slug, disease_fm_relpath=fm_rel)
        dest = report_dir / "draft_validations"
        dest.mkdir(parents=True, exist_ok=True)
        pn = args.validate_draft.parent.name or args.validate_draft.stem
        jp = dest / f"{slug}_{pn}_phase25_validation.json"
        jp.write_text(json.dumps(out, indent=2), encoding="utf-8")
        print(json.dumps(out, indent=2))
        print(f"\nWritten: {jp}", file=sys.stderr)
        return 0

    summary: Dict[str, Any] = {"phase": "2.5", "steps": []}

    if args.disease:
        slug = _normalize_disease_key(args.disease)
        tgt = None
        for dr in discover_disease_dirs(data_root):
            if _normalize_disease_key(dr.name) == slug:
                tgt = dr
                break
        if tgt is None:
            print(f"Disease folder not found: {slug}", file=sys.stderr)
            return 2
        profiles = {slug: build_canonical_profile_for_disease(tgt, slug)}
        save_profiles(report_dir, profiles)
        fm_written = save_disease_feature_models(report_dir, profiles)
        summary["steps"].append({"canonical_profiles": "single disease", "slug": slug})
        summary["steps"].append({"disease_feature_models": fm_written})
    else:
        profiles = build_all_profiles(data_root)
        save_profiles(report_dir, profiles)
        fm_written = save_disease_feature_models(report_dir, profiles)
        summary["steps"].append({"canonical_profiles": "all diseases", "count": len(profiles)})
        summary["steps"].append({"disease_feature_models": fm_written})

    if args.profiles_only:
        summary["steps"].append({"note": "stopped at --profiles-only"})
        (report_dir / "phase25_run_summary.json").write_text(json.dumps(summary, indent=2), encoding="utf-8")
        print(f"Phase 2.5: wrote profiles + disease_feature_models under {report_dir}", file=sys.stderr)
        return 0

    diagram_info: Dict[str, Any] = {}
    if not args.no_diagram:
        # Shared vocabulary diagram (all flags shown in neutral style)
        diagram_info = export_feature_diagram_assets(
            args.feature_tree,
            fm_dot,
            fm_diagram_dir,
            basename="feature_tree",
            render_images=not args.no_diagram_images,
        )
        summary["steps"].append({"feature_diagram_shared": diagram_info})

    if not args.no_spl:
        shared = diagram_info if diagram_info else None
        for slug in sorted(profiles.keys()):
            spl_report_for_disease(
                data_root=data_root,
                report_dir=report_dir,
                disease_slug=slug,
                constraints_json=args.constraints,
                feature_tree_json=args.feature_tree if not args.no_diagram else None,
                with_witness=args.with_witness,
                render_disease_diagram=not args.no_diagram,
                diagram_render_images=not args.no_diagram_images,
                shared_feature_diagram_assets=shared,
            )
        summary["steps"].append({"spl_reports_per_disease": list(profiles.keys())})

    validations: List[Dict[str, Any]] = []
    if not args.no_validate_phase2_drafts:
        validations = validate_all_phase2_drafts(
            profiles=profiles,
            phase2_reports=args.phase2_reports.resolve(),
            report_dir=report_dir,
        )
        summary["steps"].append({"phase2_draft_validations": len(validations)})
        summary["draft_validation_records"] = validations

    (report_dir / "phase25_run_summary.json").write_text(json.dumps(summary, indent=2), encoding="utf-8")

    print(json.dumps(summary, indent=2))
    print(f"\nPhase 2.5 complete. Summary: {report_dir / 'phase25_run_summary.json'}", file=sys.stderr)
    print(f"Disease feature models (gold-backed): {report_dir / 'disease_feature_models'}", file=sys.stderr)
    print(f"SPL folder: {report_dir / 'spl_configurator'}", file=sys.stderr)
    if not args.no_diagram:
        print(
            f"Shared diagram: {fm_diagram_dir / 'feature_tree.dot'} (+ PNG/SVG when Graphviz installed)",
            file=sys.stderr,
        )
        print(
            f"Per-disease diagrams: {fm_diagram_dir / 'per_disease'}/",
            file=sys.stderr,
        )
    else:
        print("Diagram outputs skipped (--no-diagram)", file=sys.stderr)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())