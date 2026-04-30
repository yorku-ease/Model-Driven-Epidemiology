"""Infer EpiFeatureVector from harvested .compmodel text (rule-based, extensible)."""

from __future__ import annotations

import re
from pathlib import Path
from typing import List, Optional

from .compmodel_parse import harvest_compmodel_text
from .epi_features import EpiFeatureVector

# Order matters for signal labels (first match wins per category in matched_signals)

_RULES: List[tuple[str, str, re.Pattern]] = [
    ("route_vector_arthropod", "vector/mosquito/snail", re.compile(
        r"\b(mosquito|mosquitoes|vector|anopheles|aedes|arthropod|larva|sporozo|oocyst|bite|bites)\b", re.I
    )),
    ("route_sexual_or_partner_network", "sexual/partner network", re.compile(
        r"\b(homosexual|heterosexual|sexual|partner|condom|commercial sex|msm|women|men who)\b", re.I
    )),
    ("route_airborne_or_respiratory_droplet", "respiratory/airborne", re.compile(
        r"\b(droplet|airborne|respiratory|aerosol|cough|covid|influenza|measles)\b", re.I
    )),
    ("route_fecal_oral_or_waterborne", "fecal-oral/water", re.compile(
        r"\b(cholera|waterborne|fecal|ingestion|aquatic|sanitation|environmental reservoir)\b", re.I
    )),
    ("route_bloodborne_vertical_or_parenteral", "blood/vertical", re.compile(
        r"\b(blood|needle|vertical|mother-to-child|birth|transfusion|hiv transmission)\b", re.I
    )),
    ("route_healthcare_general_contact", "healthcare contact", re.compile(
        r"\b(hospital|icu|nosocomial|health[- ]care|isolation ward)\b", re.I
    )),
    ("latent_or_exposed_class", "latent/exposed class", re.compile(
        r"\b(latent|ltbi|exposed|incubat|exposed class|ehi| eh[0-9]|eh1|eh2)\b", re.I
    )),
    ("multiple_infectious_stages_or_chronic", "staged infection", re.compile(
        r"\b(aids|acute|chronic phase|tuberculosis latent|undiagnosed|stage)\b", re.I
    )),
    ("hospitalized_or_severity_stratification", "hospital/severity", re.compile(
        r"\b(hospital|icu|severe|severity|clinical progression)\b", re.I
    )),
    ("treatment_or_art_intervention", "treatment/ART", re.compile(
        r"\b(art\b|therapy|antiretroviral|anti[- ]biotic|chemotherapy|trimethoprim|isoniazid|rifamp|treatment|treated\b)\b", re.I
    )),
    ("vaccination_route_compartment", "vaccination", re.compile(
        r"\b(vaccinat|immunization|vaccc?inated| VH\b|preventive dose)\b", re.I
    )),
    ("recovered_or_immune_endpoint", "recovered", re.compile(
        r"\b(recovered|recovery|immune|rh\b| waned immunity|lifelong)\b", re.I
    )),
    ("stratification_demographic_roles", "demographic stratification", re.compile(
        r"\b(adults\b|children|homosexual|women|heterosexual men|risk group|stratif)\b", re.I
    )),
    ("spatial_or_patch_like_naming", "spatial/patch naming", re.compile(
        r"\b(patch|region|district|spatial|metapop)\b", re.I
    )),
    ("vector_or_intermediate_species_present", "non-human vector spp.", re.compile(
        r"\b(mosquito|mosquitoes|\bsm\b|\be[sm]\b|\bim\b| snail|aquatic )\b", re.I
    )),
    ("zoonotic_or_animal_compartment", "animal/zoonotic", re.compile(
        r"\b(animal|zoonotic|wildlife|primate|cattle)\b", re.I
    )),
    ("recruitment_birth_or_immigration_named", "recruitment/birth", re.compile(
        r"\b(recruitment|birth|immigration|carrying capacity|incoming flow)\b", re.I
    )),
]


def infer_features_from_compmodel(path: Path) -> EpiFeatureVector:
    names, blob = harvest_compmodel_text(path)
    blob_full = blob + " " + " ".join(n.lower() for n in names)
    v = EpiFeatureVector()
    matched: List[str] = []

    for field_name, label, pat in _RULES:
        if pat.search(blob_full):
            setattr(v, field_name, True)
            if label not in matched:
                matched.append(label)

    v.matched_signals = matched
    return v


def apply_disease_name_hints(disease_slug: str, v: EpiFeatureVector) -> EpiFeatureVector:
    """Lightweight priors from folder name (not from paper text)."""
    s = disease_slug.lower().strip().replace(" ", "")
    if s in ("malaria", "dengue", "zika", "yellowfever"):
        v.route_vector_arthropod = True
        v.vector_or_intermediate_species_present = True
    if s in ("hiv",):
        v.route_sexual_or_partner_network = True
        v.route_bloodborne_vertical_or_parenteral = True
        v.multiple_infectious_stages_or_chronic = True
        v.treatment_or_art_intervention = True
    if s in ("covid", "influenza", "measles", "tuberculosis", "tb"):
        v.route_airborne_or_respiratory_droplet = True
    if s in ("cholera",):
        v.route_fecal_oral_or_waterborne = True
    if s in ("ebola",):
        v.route_healthcare_general_contact = True
        v.hospitalized_or_severity_stratification = True
    if s in ("tuberculosis", "tb"):
        v.latent_or_exposed_class = True
    return v


def infer_with_priors(path: Path, disease_slug: str) -> EpiFeatureVector:
    v = infer_features_from_compmodel(path)
    merged = apply_disease_name_hints(disease_slug, v)
    return merged
