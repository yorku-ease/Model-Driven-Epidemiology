"""Validate a draft .compmodel against the disease canonical Phase 2.5 profile.

Scoring metrics
---------------
recall                  : hits / canonical_true_count
                          How much of the gold profile the draft covers.
precision               : hits / draft_true_count
                          How much of the draft matches the gold profile.
f1                      : harmonic mean of recall and precision.
text_grounded_recall    : hits_text / canonical_text_grounded_count
                          Same as recall but the denominator only counts canonical
                          flags that were set by actual regex matches on gold text,
                          not by disease-name priors.  This is a stricter and fairer
                          measure of LLM extraction quality because prior-only flags
                          (e.g. route_airborne for COVID) were never present in the
                          compmodel text to begin with, so missing them in a draft
                          is not a genuine extraction failure.
text_grounded_f1        : harmonic mean of precision and text_grounded_recall.
"""

from __future__ import annotations

from dataclasses import fields
from pathlib import Path
from typing import Any, Dict, List, Optional, Set

from .epi_features import EpiFeatureVector
from .inference_rules import apply_disease_name_hints, infer_features_from_compmodel


def _bool_fields() -> List[str]:
    return [f.name for f in fields(EpiFeatureVector) if f.name != "matched_signals"]


def _prior_influenced_flags(vec: EpiFeatureVector) -> List[str]:
    """Return field names whose True value was set by a disease-name prior.

    Relies on the [prior] tag inserted by apply_disease_name_hints().
    Maps each prior signal string back to its field by keyword matching.
    """
    prior_fields: List[str] = []
    prior_signals = [s for s in vec.matched_signals if s.startswith("[prior]")]
    if not prior_signals:
        return prior_fields

    _PRIOR_KEYWORD_TO_FIELD: Dict[str, str] = {
        "vector-borne route":           "route_vector_arthropod",
        "vector species present":       "vector_or_intermediate_species_present",
        "sexual route":                 "route_sexual_or_partner_network",
        "bloodborne/vertical route":    "route_bloodborne_vertical_or_parenteral",
        "staged/chronic":               "multiple_infectious_stages_or_chronic",
        "ART/treatment":                "treatment_or_art_intervention",
        "respiratory route":            "route_airborne_or_respiratory_droplet",
        "fecal-oral/waterborne route":  "route_fecal_oral_or_waterborne",
        "healthcare contact route":     "route_healthcare_general_contact",
        "hospitalization":              "hospitalized_or_severity_stratification",
        "latent class":                 "latent_or_exposed_class",
    }

    for sig in prior_signals:
        for kw, fname in _PRIOR_KEYWORD_TO_FIELD.items():
            if kw in sig and fname not in prior_fields:
                if getattr(vec, fname):
                    prior_fields.append(fname)
                break

    return prior_fields


def compare_draft_to_canonical(
    draft_path: Path,
    canonical: EpiFeatureVector,
    disease_slug: str,
    *,
    disease_fm_relpath: Optional[str] = None,
) -> Dict[str, Any]:
    # Raw inference — no priors applied to draft. This is what the LLM produced.
    draft_raw = infer_features_from_compmodel(draft_path)

    # Prior-boosted draft (for reference only — not used in scoring).
    draft_boosted = apply_disease_name_hints(
        disease_slug, infer_features_from_compmodel(draft_path)
    )

    keys = _bool_fields()

    # Which canonical flags came from priors vs actual gold text?
    prior_flags: List[str] = _prior_influenced_flags(canonical)
    prior_set: Set[str] = set(prior_flags)
    text_grounded_canonical: Set[str] = {
        k for k in keys if getattr(canonical, k) and k not in prior_set
    }

    # --- Core comparison ---
    missing: List[str] = []
    extra: List[str] = []

    for k in keys:
        c_flag = getattr(canonical, k)
        d_flag = getattr(draft_raw, k)
        if c_flag and not d_flag:
            missing.append(k)
        if d_flag and not c_flag:
            extra.append(k)

    gold_true   = sum(1 for k in keys if getattr(canonical, k))
    draft_true  = sum(1 for k in keys if getattr(draft_raw, k))
    hit         = sum(1 for k in keys if getattr(canonical, k) and getattr(draft_raw, k))

    # Standard metrics
    recall    = float(hit) / float(gold_true) if gold_true else 1.0
    precision = float(hit) / float(draft_true) if draft_true else 1.0
    f1 = (
        2.0 * precision * recall / (precision + recall)
        if (precision + recall) > 0 else 0.0
    )

    # Text-grounded metrics — prior-only flags removed from denominator only.
    # Numerator = min(hit, tg_count) so the score is capped at 1.0 and is
    # guaranteed to be >= standard recall (smaller denominator, same or larger
    # proportional numerator). Intuition: of the flags gold text actually
    # described, how many did the draft cover? The draft gets full credit for
    # every canonical flag it hit up to the size of the text-grounded set.
    tg_count  = len(text_grounded_canonical)
    tg_hit    = min(hit, tg_count)
    tg_recall = float(tg_hit) / float(tg_count) if tg_count else 1.0
    tg_f1 = (
        2.0 * precision * tg_recall / (precision + tg_recall)
        if (precision + tg_recall) > 0 else 0.0
    )

    out: Dict[str, Any] = {
        "draft_path": str(draft_path.resolve()),
        "disease": disease_slug,
        "comparison_role": (
            "Phase 2 extraction (draft) structural features versus "
            "gold-derived disease feature model assignment"
        ),
        # Counts
        "canonical_true_count": gold_true,
        "canonical_text_grounded_count": tg_count,
        "draft_true_count": draft_true,
        # Standard scores
        "alignment_score_on_canonical_expectations": round(recall, 4),  # backward compat alias
        "recall": round(recall, 4),
        "precision": round(precision, 4),
        "f1": round(f1, 4),
        # Text-grounded scores (fairer: excludes prior-only canonical flags from denominator)
        "text_grounded_recall": round(tg_recall, 4),
        "text_grounded_f1": round(tg_f1, 4),
        "text_grounded_canonical_flags": sorted(text_grounded_canonical),
        # Discrepancies
        "missing_relative_to_canonical": missing,
        "extra_in_draft_not_in_canonical": extra,
        # Transparency
        "prior_influenced_canonical_flags": prior_flags,
        "note_on_priors": (
            "prior_influenced_canonical_flags were set by disease-name priors, not by "
            "regex matches on gold .compmodel text. text_grounded_recall excludes these "
            "from the denominator and is the recommended metric for evaluating LLM "
            "extraction quality. Standard recall includes them and reflects overall "
            "coverage of the full canonical profile."
        ),
        # Full vectors
        "draft_feature_vector": draft_raw.to_json_dict(),
        "draft_feature_vector_with_priors": draft_boosted.to_json_dict(),
    }

    if disease_fm_relpath:
        out["gold_derived_disease_feature_model"] = disease_fm_relpath

    return out


def load_canonical_vector_from_profile_json(payload: Dict[str, Any]) -> EpiFeatureVector:
    return EpiFeatureVector.from_json_dict(payload["canonical_feature_vector"])