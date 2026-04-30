"""Gold-derived disease-level feature-model bundles — same Boolean assignment as canonical profile, clearer thesis-facing packaging."""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Dict, List


def build_disease_feature_model(canonical_profile_payload: Dict[str, Any]) -> Dict[str, Any]:
    """Wrap a ``build_canonical_profile_for_disease`` JSON dict as an explicit feature-model artefact."""

    slug = canonical_profile_payload["disease"]
    cfv = dict(canonical_profile_payload["canonical_feature_vector"])
    signals = cfv.pop("matched_signals", [])
    return {
        "artifact": "gold_derived_disease_feature_model",
        "schema_version": 1,
        "disease": slug,
        "description": (
            "Per-disease Boolean assignment over the shared EpiFeatureVector schema. "
            "Built only from hand-curated gold .compmodel files under the benchmark disease folder; "
            "Phase 2 model_draft.compmodel files are compared against this assignment (same inference rules)."
        ),
        "gold_standard": {
            "data_directory": canonical_profile_payload["disease_dir"],
            "gold_compmodel_files": list(canonical_profile_payload["gold_models_merged"]),
            "merge_rule": "logical_OR_per_boolean_field_across_listed_gold_files_then_folder_priors",
            "detail_record": f"canonical_by_disease/{slug}.json",
            "matched_signals_union": signals,
        },
        "assignment": cfv,
    }


def save_disease_feature_models(report_dir: Path, profiles: Dict[str, Dict[str, Any]]) -> List[str]:
    """Write ``reports/disease_feature_models/<slug>.json`` for each disease (relative to ``report_dir``)."""

    out_dir = report_dir / "disease_feature_models"
    out_dir.mkdir(parents=True, exist_ok=True)
    written: List[str] = []
    for slug, payload in profiles.items():
        doc = build_disease_feature_model(payload)
        path = out_dir / f"{slug}.json"
        path.write_text(json.dumps(doc, indent=2), encoding="utf-8")
        written.append(slug)
    return sorted(written)
