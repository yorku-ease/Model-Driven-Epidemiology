"""Validate a draft .compmodel against the disease canonical Phase 2.5 profile."""

from __future__ import annotations

from dataclasses import fields
from pathlib import Path
from typing import Any, Dict, List, Optional

from .epi_features import EpiFeatureVector
from .inference_rules import apply_disease_name_hints, infer_features_from_compmodel


def _bool_fields() -> List[str]:
    return [f.name for f in fields(EpiFeatureVector) if f.name != "matched_signals"]


def compare_draft_to_canonical(
    draft_path: Path,
    canonical: EpiFeatureVector,
    disease_slug: str,
    *,
    disease_fm_relpath: Optional[str] = None,
) -> Dict[str, Any]:
    raw = infer_features_from_compmodel(draft_path)
    draft = apply_disease_name_hints(disease_slug, raw)

    keys = _bool_fields()
    missing: List[str] = []
    novel: List[str] = []

    for k in keys:
        c_flag = getattr(canonical, k)
        d_flag = getattr(draft, k)
        if c_flag and not d_flag:
            missing.append(k)
        if d_flag and not c_flag:
            novel.append(k)

    required = sum(1 for k in keys if getattr(canonical, k))
    hit = sum(1 for k in keys if getattr(canonical, k) and getattr(draft, k))
    alignment = float(hit) / float(required) if required else 1.0

    out: Dict[str, Any] = {
        "draft_path": str(draft_path.resolve()),
        "disease": disease_slug,
        "comparison_role": (
            "Phase 2 extraction (draft) structural features versus gold-derived disease feature model assignment"
        ),
        "canonical_true_count": required,
        "draft_true_count": sum(1 for k in keys if getattr(draft, k)),
        "missing_relative_to_canonical": missing,
        "extra_in_draft_not_in_canonical": novel,
        "alignment_score_on_canonical_expectations": round(alignment, 4),
        "draft_feature_vector": draft.to_json_dict(),
    }
    if disease_fm_relpath:
        out["gold_derived_disease_feature_model"] = disease_fm_relpath
    return out


def load_canonical_vector_from_profile_json(payload: Dict[str, Any]) -> EpiFeatureVector:
    return EpiFeatureVector.from_json_dict(payload["canonical_feature_vector"])
