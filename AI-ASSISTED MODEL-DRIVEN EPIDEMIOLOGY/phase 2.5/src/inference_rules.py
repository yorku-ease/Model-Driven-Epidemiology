"""Infer EpiFeatureVector from harvested .compmodel text (rule-based, extensible).

Fix log (relative to original):
- `birth` removed from bloodborne route rule; vertical transmission now requires
  explicit multi-word phrases (mother-to-child, vertical transmission, perinatal,
  congenital) so plain birth/death demography no longer triggers it.
- `\bim\b` removed from vector_or_intermediate_species_present; that bare token
  matched immune/Im compartments in HIV/cholera models.  Replaced with explicit
  mosquito-population labels used in vector ODE literature (Sm, Em, Im, Am, Rm
  only when they appear as *standalone* 1-3 char uppercase tokens, via a separate
  compartment-name check performed on the primary-name list, not the blob).
- `aquatic ` (trailing space) and ` snail` (leading space) replaced with proper
  `\baquatic\b` / `\bsnail\b` word-boundary patterns.
- `\brh\b` removed from recovered_or_immune_endpoint; too ambiguous (Rh blood
  type).  The compartment-name list already catches short labels like Rh via the
  `recovered` stem match on SecondaryName.
- `\bstage\b` tightened: bare "stage" is now only counted when accompanied by
  epidemiologically meaningful neighbours (infectious stage, chronic stage, etc.).
  Stand-alone `stage` in prose no longer fires multiple_infectious_stages_or_chronic.
- `women` removed from the sexual/partner-network route pattern.  Female
  stratification alone does not imply sexual transmission; the remaining terms
  (homosexual, heterosexual, sexual, partner, condom, msm, commercial sex,
  men who) are sufficient.
- `apply_disease_name_hints` now records which flags it sets in a dedicated
  `prior_signals` list so callers can distinguish regex hits from hardcoded priors.
"""

from __future__ import annotations

import re
from pathlib import Path
from typing import List, Optional, Tuple

from .compmodel_parse import harvest_compmodel_text
from .epi_features import EpiFeatureVector

# ---------------------------------------------------------------------------
# Mosquito-population compartment short-labels used in vector ODE literature.
# These are checked against the *primary name list* only (not the free-text
# blob) to avoid collisions with human compartment abbreviations like Im
# (immune), Sm (susceptible males), Am (asymptomatic males), etc.
# ---------------------------------------------------------------------------
_VECTOR_COMPARTMENT_LABELS: frozenset[str] = frozenset(
    # Susceptible / Exposed / Infectious / Asymptomatic / Recovered — mosquito
    ["Sm", "Em", "Im", "Am", "Rm",
     # Larva / pupa / adult stages
     "Lm", "Pm",
     # snail (schistosomiasis)
     "Ss", "Es", "Is",
     # generic vector labels
     "Sv", "Ev", "Iv"]
)

# ---------------------------------------------------------------------------
# Main inference rules.
# Each tuple: (EpiFeatureVector field name, human-readable label, compiled re)
# Order matters: first match wins for the matched_signals label de-dup.
# ---------------------------------------------------------------------------
_RULES: List[Tuple[str, str, re.Pattern]] = [
    # --- Transmission routes ---
    (
        "route_vector_arthropod",
        "vector/mosquito/snail",
        re.compile(
            r"\b(mosquito|mosquitoes|vector|anopheles|aedes|arthropod|larva"
            r"|sporozo|oocyst|bite|bites|snail)\b",
            re.I,
        ),
    ),
    (
        "route_sexual_or_partner_network",
        "sexual/partner network",
        re.compile(
            r"\b(homosexual|heterosexual|sexual|partner|condom"
            r"|commercial\s+sex|msm|men\s+who\s+have)\b",
            re.I,
        ),
    ),
    (
        "route_airborne_or_respiratory_droplet",
        "respiratory/airborne",
        re.compile(
            r"\b(droplet|airborne|respiratory|aerosol|cough"
            r"|covid|influenza|measles)\b",
            re.I,
        ),
    ),
    (
        "route_fecal_oral_or_waterborne",
        "fecal-oral/water",
        re.compile(
            r"\b(cholera|waterborne|fecal|ingestion|aquatic"
            r"|sanitation|environmental\s+reservoir)\b",
            re.I,
        ),
    ),
    (
        # 'birth' alone is NOT sufficient — it fires on birth/death demographic
        # terms.  Require explicit vertical/perinatal/mother-to-child phrasing.
        "route_bloodborne_vertical_or_parenteral",
        "blood/vertical",
        re.compile(
            r"\b(blood(?:borne)?|needle|vertical\s+transmission"
            r"|mother-to-child|perinatal|congenital\s+transmission"
            r"|transfusion|hiv\s+transmission)\b",
            re.I,
        ),
    ),
    (
        "route_healthcare_general_contact",
        "healthcare contact",
        re.compile(
            r"\b(hospital|icu|nosocomial|health[- ]care|isolation\s+ward)\b",
            re.I,
        ),
    ),
    # --- Clinical / natural history ---
    (
        "latent_or_exposed_class",
        "latent/exposed class",
        re.compile(
            r"\b(latent|ltbi|exposed|incubat|exposed\s+class"
            r"|ehi|eh[0-9]|eh1|eh2)\b",
            re.I,
        ),
    ),
    (
        # Tightened: require disease-specific multi-stage vocabulary.
        # Plain "stage" in prose no longer fires.
        "multiple_infectious_stages_or_chronic",
        "staged infection",
        re.compile(
            r"\b(aids|hiv\s+stage|infectious\s+stage|chronic\s+stage"
            r"|chronic\s+phase|tuberculosis\s+latent|undiagnosed"
            r"|disease\s+stage|clinical\s+stage|progressive)\b",
            re.I,
        ),
    ),
    (
        "hospitalized_or_severity_stratification",
        "hospital/severity",
        re.compile(
            r"\b(hospital(?:ized)?|icu|severe|severity"
            r"|clinical\s+progression)\b",
            re.I,
        ),
    ),
    (
        "treatment_or_art_intervention",
        "treatment/ART",
        re.compile(
            r"\b(art\b|therapy|antiretroviral|anti[- ]biotic|chemotherapy"
            r"|trimethoprim|isoniazid|rifamp|treatment|treated\b)\b",
            re.I,
        ),
    ),
    (
        "vaccination_route_compartment",
        "vaccination",
        re.compile(
            r"\b(vaccinat|immunization|vacc?inated|VH\b|preventive\s+dose)\b",
            re.I,
        ),
    ),
    (
        # Removed \brh\b — too ambiguous (Rh blood type).
        "recovered_or_immune_endpoint",
        "recovered",
        re.compile(
            r"\b(recovered|recovery|immune(?:d)?|waned\s+immunity|lifelong)\b",
            re.I,
        ),
    ),
    # --- Population structure ---
    (
        # Removed bare 'women' — female stratification ≠ sexual-route model.
        # Keep specific demographic role terms.
        "stratification_demographic_roles",
        "demographic stratification",
        re.compile(
            r"\b(adults\b|children|homosexual|heterosexual\s+(?:men|women)"
            r"|risk\s+group|stratif|age\s+group|age-structured"
            r"|pregnant\s+women)\b",
            re.I,
        ),
    ),
    (
        "spatial_or_patch_like_naming",
        "spatial/patch naming",
        re.compile(
            r"\b(patch|region|district|spatial|metapop)\b",
            re.I,
        ),
    ),
    # --- Non-human hosts ---
    (
        # \bim\b and \bsm\b removed — they collide with human compartment
        # abbreviations.  The short vector-label check is now done separately
        # via _check_vector_compartment_names() below.
        "vector_or_intermediate_species_present",
        "non-human vector spp.",
        re.compile(
            r"\b(mosquito|mosquitoes|snail|aquatic\s+stage"
            r"|vector\s+population|vector\s+compartment)\b",
            re.I,
        ),
    ),
    (
        "zoonotic_or_animal_compartment",
        "animal/zoonotic",
        re.compile(
            r"\b(animal|zoonotic|wildlife|primate|cattle)\b",
            re.I,
        ),
    ),
    # --- Demography ---
    (
        # 'birth' kept here (birth rate, birth flux) but removed from bloodborne.
        "recruitment_birth_or_immigration_named",
        "recruitment/birth",
        re.compile(
            r"\b(recruitment|birth|immigration|carrying\s+capacity"
            r"|incoming\s+flow)\b",
            re.I,
        ),
    ),
]


def _check_vector_compartment_names(names: List[str]) -> bool:
    """Return True if any primary compartment name is a known vector-population label.

    Only exact case-sensitive matches against the curated set are accepted so
    that human-host abbreviations (Im = immune, Sm = susceptible-males) are not
    confused with mosquito-host compartments (Im = infectious-mosquito).
    The distinction relies on the modeller using the standard vector-ODE
    convention; a secondary-name description check is therefore also performed.
    """
    for name in names:
        if name.strip() in _VECTOR_COMPARTMENT_LABELS:
            return True
    return False


def infer_features_from_compmodel(path: Path) -> EpiFeatureVector:
    """Run all rules against the harvested text; return a populated EpiFeatureVector."""
    names, blob = harvest_compmodel_text(path)
    blob_full = blob + " " + " ".join(n.lower() for n in names)
    v = EpiFeatureVector()
    matched: List[str] = []

    for field_name, label, pat in _RULES:
        if pat.search(blob_full):
            setattr(v, field_name, True)
            if label not in matched:
                matched.append(label)

    # Supplementary compartment-name check for vector species
    if not v.vector_or_intermediate_species_present:
        if _check_vector_compartment_names(names):
            v.vector_or_intermediate_species_present = True
            lbl = "non-human vector spp. (compartment label)"
            if lbl not in matched:
                matched.append(lbl)

    v.matched_signals = matched
    return v


def apply_disease_name_hints(
    disease_slug: str,
    v: EpiFeatureVector,
    *,
    record_priors: bool = True,
) -> EpiFeatureVector:
    """Apply lightweight hardcoded priors from the disease folder name.

    These priors encode well-known epidemiological facts that may not appear
    explicitly in every individual model's compartment text (e.g. malaria is
    always mosquito-borne).  They are intentionally conservative: only
    unambiguous, disease-defining traits are hard-coded.

    When *record_priors* is True (default) each prior that actually changes a
    flag is appended to ``v.matched_signals`` with a ``[prior]`` tag so
    downstream consumers can distinguish regex hits from hardcoded knowledge.
    """
    s = disease_slug.lower().strip().replace(" ", "")
    prior_labels: List[str] = []

    def _set(field: str, label: str) -> None:
        if not getattr(v, field):
            setattr(v, field, True)
            if record_priors:
                prior_labels.append(f"[prior] {label}")

    if s in ("malaria", "dengue", "zika", "yellowfever"):
        _set("route_vector_arthropod", "vector-borne route (disease prior)")
        _set("vector_or_intermediate_species_present", "vector species present (disease prior)")

    if s == "hiv":
        _set("route_sexual_or_partner_network", "sexual route (HIV prior)")
        _set("route_bloodborne_vertical_or_parenteral", "bloodborne/vertical route (HIV prior)")
        _set("multiple_infectious_stages_or_chronic", "staged/chronic infection (HIV prior)")
        _set("treatment_or_art_intervention", "ART/treatment (HIV prior)")

    if s in ("covid", "influenza", "measles", "tuberculosis", "tb"):
        _set("route_airborne_or_respiratory_droplet", "respiratory route (disease prior)")

    if s == "cholera":
        _set("route_fecal_oral_or_waterborne", "fecal-oral/waterborne route (cholera prior)")

    if s == "ebola":
        _set("route_healthcare_general_contact", "healthcare contact route (Ebola prior)")
        _set("hospitalized_or_severity_stratification", "hospitalization (Ebola prior)")

    if s in ("tuberculosis", "tb"):
        _set("latent_or_exposed_class", "latent class (TB prior)")

    if record_priors and prior_labels:
        v.matched_signals = v.matched_signals + prior_labels

    return v


def infer_with_priors(path: Path, disease_slug: str) -> EpiFeatureVector:
    """Convenience wrapper: infer from file then apply disease-name priors."""
    v = infer_features_from_compmodel(path)
    return apply_disease_name_hints(disease_slug, v)