#!/usr/bin/env python3
"""SPL on one disease — thin wrapper around `src/phase25_spl`. For the full pipeline use `run_phase25.py`."""

from __future__ import annotations

import argparse
import json
import sys
from pathlib import Path

_ROOT = Path(__file__).resolve().parent
if str(_ROOT) not in sys.path:
    sys.path.insert(0, str(_ROOT))

from src.phase25_spl import export_feature_diagram_assets, spl_report_for_disease


def main() -> int:
    root = Path(__file__).resolve().parent


    ap = argparse.ArgumentParser(description="Phase 2.5 SPL helper (single disease)")




    

    ap.add_argument("--data-root", type=Path, default=root.parent / "phase 2" / "data")


    ap.add_argument("--report-dir", type=Path, default=root / "reports")


    ap.add_argument("--disease", type=str, required=True)


    ap.add_argument("--constraints", type=Path, default=root / "feature_models" / "cross_tree_constraints.json")


    ap.add_argument("--feature-tree", type=Path, default=root / "feature_models" / "feature_tree.json")


    ap.add_argument("--export-dot-to", type=Path, default=None)


    ap.add_argument("--no-diagram-images", action="store_true")


    ap.add_argument("--with-witness", action="store_true")




    

    args = ap.parse_args()


    data_root = args.data_root.expanduser().resolve()


    report_dir = args.report_dir.expanduser().resolve()


    fm_dot = args.export_dot_to or (report_dir / "fm_diagram" / "feature_tree.dot")


    fm_dir = fm_dot.parent




    

    diagram = export_feature_diagram_assets(


        args.feature_tree,


        fm_dot,


        fm_dir,


        render_images=not args.no_diagram_images,


    )




    

    merged = spl_report_for_disease(
        data_root=data_root,
        report_dir=report_dir,
        disease_slug=args.disease,
        constraints_json=args.constraints,
        feature_tree_json=args.feature_tree,
        with_witness=args.with_witness,
        diagram_render_images=not args.no_diagram_images,
        shared_feature_diagram_assets=diagram,
    )




    

    print(json.dumps(merged, indent=2))


    print(f"\nWritten: {merged.get('_spl_report_json')}", file=sys.stderr)



    return 0



if __name__ == "__main__":
    raise SystemExit(main())
