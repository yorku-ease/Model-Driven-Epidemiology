"""Build one merged (canonical) feature profile per disease from all gold .compmodel files.

Changes vs original:
- matched_signals truncation (was silently capped at 80) now records a warning
  in the profile JSON when truncation occurs.
- Per-model vectors now include a `prior_signals` sub-list so readers can see
  which signals came from apply_disease_name_hints() vs actual regex matches.
"""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Dict, List, Optional

from .epi_features import EpiFeatureVector, merge_feature_vectors
from .inference_rules import apply_disease_name_hints, infer_features_from_compmodel

_SIGNAL_CAP = 80  # kept for readability; now flagged when hit


def _normalize_disease_key(raw: str) -> str:
    return raw.strip().lower().replace(" ", "_")


def discover_disease_dirs(data_root: Path) -> List[Path]:
    diseases = data_root / "diseases"
    if not diseases.is_dir():
        return []
    return sorted(p for p in diseases.iterdir() if p.is_dir())


def gold_compmodels_in_disease(disease_dir: Path) -> List[Path]:
    return sorted(disease_dir.glob("*.compmodel"))


def build_canonical_profile_for_disease(
    disease_dir: Path,
    disease_slug: Optional[str] = None,
) -> Dict[str, Any]:
    """Merge all gold models in folder into one canonical EpiFeatureVector."""
    slug = disease_slug or _normalize_disease_key(disease_dir.name)
    paths = gold_compmodels_in_disease(disease_dir)
    per_model: Dict[str, Any] = {}
    vectors: List[EpiFeatureVector] = []

    for cm in paths:
        stem = cm.stem
        raw = infer_features_from_compmodel(cm)
        # Apply disease-name priors per model (record_priors=True so [prior] tags appear).
        v = apply_disease_name_hints(slug, raw, record_priors=True)
        vectors.append(v)

        # Split signals into regex-hit vs prior for per-model record
        regex_sigs = [s for s in v.matched_signals if not s.startswith("[prior]")]
        prior_sigs = [s for s in v.matched_signals if s.startswith("[prior]")]
        model_dict = v.to_json_dict()
        model_dict["prior_signals"] = prior_sigs
        model_dict["matched_signals"] = regex_sigs  # keep matched_signals clean
        per_model[stem] = model_dict

    merged = merge_feature_vectors(vectors)
    merged = apply_disease_name_hints(slug, merged, record_priors=True)

    # Cap signals with truncation warning
    all_sigs = merged.matched_signals
    truncated = False
    if len(all_sigs) > _SIGNAL_CAP:
        all_sigs = all_sigs[:_SIGNAL_CAP]
        truncated = True
    merged.matched_signals = all_sigs

    profile: Dict[str, Any] = {
        "phase": "2.5",
        "schema_version": 1,
        "disease": slug,
        "disease_dir": str(disease_dir.resolve()),
        "canonical_feature_vector": merged.to_json_dict(),
        "gold_models_merged": [p.name for p in paths],
        "per_model_feature_vectors": per_model,
        "notes": (
            "Canonical profile = logical OR across all listed gold .compmodel files "
            "for this disease, then disease-folder priors merged (OR). "
            "Same assignment is written under reports/disease_feature_models/<slug>.json "
            "as the gold-derived disease feature model. "
            "Signals prefixed with [prior] were set by apply_disease_name_hints(), "
            "not by regex matches on the compmodel text."
        ),
    }
    if truncated:
        profile["matched_signals_truncated"] = True
        profile["matched_signals_truncation_cap"] = _SIGNAL_CAP

    return profile


def build_all_profiles(data_root: Path) -> Dict[str, Dict[str, Any]]:
    out: Dict[str, Dict[str, Any]] = {}
    for ddir in discover_disease_dirs(data_root):
        slug = _normalize_disease_key(ddir.name)
        out[slug] = build_canonical_profile_for_disease(ddir, slug)
    return out


def save_profiles(report_dir: Path, profiles: Dict[str, Dict[str, Any]]) -> None:
    report_dir.mkdir(parents=True, exist_ok=True)
    canon = report_dir / "canonical_by_disease"
    canon.mkdir(exist_ok=True)
    merged_path = report_dir / "canonical_profiles.json"
    for slug, payload in profiles.items():
        fp = canon / f"{slug}.json"
        fp.write_text(json.dumps(payload, indent=2), encoding="utf-8")
    merged_path.write_text(json.dumps(profiles, indent=2), encoding="utf-8")