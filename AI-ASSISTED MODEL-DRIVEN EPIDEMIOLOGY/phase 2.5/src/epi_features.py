"""Epidemiological variability features for SPL-style configuration (not only vector yes/no)."""

from __future__ import annotations

from dataclasses import dataclass, field, fields
from typing import Any, Dict, FrozenSet, List, Optional


@dataclass
class EpiFeatureVector:
    """Boolean and categorical signals inferred from compartment/flow text (and optional priors)."""

    # --- Transmission ecology (orthogonal axes; disease may exhibit several) ---
    route_vector_arthropod: bool = False
    route_sexual_or_partner_network: bool = False
    route_airborne_or_respiratory_droplet: bool = False
    route_fecal_oral_or_waterborne: bool = False
    route_bloodborne_vertical_or_parenteral: bool = False
    route_healthcare_general_contact: bool = False

    # --- Clinical / natural history ---
    latent_or_exposed_class: bool = False  # E, Eh, latent TB, incubation etc.
    multiple_infectious_stages_or_chronic: bool = False  # HIV/AIDS stages, Ebola progression
    hospitalized_or_severity_stratification: bool = False
    treatment_or_art_intervention: bool = False
    vaccination_route_compartment: bool = False
    recovered_or_immune_endpoint: bool = False

    # --- Population structure ---
    stratification_demographic_roles: bool = False  # sex roles, MSM/hetero, adults/children keywords
    spatial_or_patch_like_naming: bool = False

    # --- Hosts beyond humans ---
    vector_or_intermediate_species_present: bool = False  # mosquito, snail, aquatic stage
    zoonotic_or_animal_compartment: bool = False

    # --- Demography in model ---
    recruitment_birth_or_immigration_named: bool = False

    # Optional: free-text hints for thesis / debugging
    matched_signals: List[str] = field(default_factory=list)

    def to_json_dict(self) -> Dict[str, Any]:
        d: Dict[str, Any] = {}
        for f in fields(self):
            if f.name == "matched_signals":
                d[f.name] = list(self.matched_signals)
            else:
                d[f.name] = getattr(self, f.name)
        return d

    @classmethod
    def from_json_dict(cls, d: Dict[str, Any]) -> "EpiFeatureVector":
        base = cls()
        kw: Dict[str, Any] = {}
        for f in fields(cls):
            if f.name == "matched_signals":
                kw[f.name] = list(d.get("matched_signals", base.matched_signals))
            else:
                kw[f.name] = d.get(f.name, getattr(base, f.name))
        return cls(**kw)


def merge_feature_vectors(vectors: List[EpiFeatureVector]) -> EpiFeatureVector:
    """Merge gold models for one disease: OR for every boolean (product-line union)."""
    if not vectors:
        return EpiFeatureVector()
    out = EpiFeatureVector()
    for v in vectors:
        for f in fields(EpiFeatureVector):
            if f.name == "matched_signals":
                continue
            if getattr(v, f.name):
                setattr(out, f.name, True)
    # Union matched signals (dedupe, cap length for readability)
    seen: FrozenSet[str] = frozenset()
    merged: List[str] = []
    for v in vectors:
        for s in v.matched_signals:
            if s not in seen:
                merged.append(s)
                seen = seen | {s}
    out.matched_signals = merged[:80]
    return out


def count_active_flags(v: EpiFeatureVector) -> int:
    n = 0
    for f in fields(EpiFeatureVector):
        if f.name == "matched_signals":
            continue
        if getattr(v, f.name):
            n += 1
    return n
