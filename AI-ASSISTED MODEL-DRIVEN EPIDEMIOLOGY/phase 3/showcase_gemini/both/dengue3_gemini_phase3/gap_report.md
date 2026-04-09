# Phase 3 Gap Analysis Report

**Disease / context:** dengue3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **26**
- Missing compartments: 9
- Missing parameters: 8
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 9
- Extra compartments (not in gold standard): 8
- Extra parameters (not in gold standard): 13
- Extra flows (not in gold standard): 6

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **26**
- After fills gaps (re-detected): **18**
- Delta (before - after): **8**
- Delta missing parameters: **8**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **primarysusceptible** — Present in gold standard but not in extracted model. (severity: high)
- **primaryexposed** — Present in gold standard but not in extracted model. (severity: high)
- **primaryinfectious** — Present in gold standard but not in extracted model. (severity: high)
- **postprimaryimmune** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinatedsilentinfection** — Present in gold standard but not in extracted model. (severity: high)
- **secondarysusceptible** — Present in gold standard but not in extracted model. (severity: high)
- **postvaccinationsusceptible** — Present in gold standard but not in extracted model. (severity: high)
- **secondaryinfectious** — Present in gold standard but not in extracted model. (severity: high)
- **postsecondaryimmune** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **PrimarySusceptible->PrimaryExposed** — Present in gold standard but not in extracted model (or flows list).
- **PrimarySusceptible->VaccinatedSilentInfection** — Present in gold standard but not in extracted model (or flows list).
- **PrimaryExposed->PrimaryInfectious** — Present in gold standard but not in extracted model (or flows list).
- **PrimaryInfectious->PostPrimaryImmune** — Present in gold standard but not in extracted model (or flows list).
- **PostPrimaryImmune->SecondarySusceptible** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedSilentInfection->PostVaccinationSusceptible** — Present in gold standard but not in extracted model (or flows list).
- **SecondarySusceptible->SecondaryInfectious** — Present in gold standard but not in extracted model (or flows list).
- **PostVaccinationSusceptible->SecondaryInfectious** — Present in gold standard but not in extracted model (or flows list).
- **SecondaryInfectious->PostSecondaryImmune** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **primarytransmissionrate** — Present in gold standard but not in extracted model.
- **incubationrate** — Present in gold standard but not in extracted model.
- **recoveryrate** — Present in gold standard but not in extracted model.
- **routinevaccinationrate** — Present in gold standard but not in extracted model.
- **naturalcrossprotectionwaningrate** — Present in gold standard but not in extracted model.
- **vaccinecrossprotectionwaningrate** — Present in gold standard but not in extracted model.
- **secondarytransmissionrate** — Present in gold standard but not in extracted model.
- **postvaccinationtransmissionrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: susceptible humans, infectious humans (primary), recovered humans (primary), infectious humans (secondary), recovered humans (post-secondary), susceptible mosquitoes, exposed mosquitoes, infectious mosquitoes
- Extra parameters: β_hv, β_vh, γ_h, σ_v, μ_h, μ_v, case fatality rate, default vaccine coverage, default vaccination age, discount rate, dalys per symptomatic dengue case, dalys per severe dengue case, cost per hospitalised case (public payer, latin america)

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 30.8% | 30% |
| **Reference agreement** | 70.8% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **60.5/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 9 | 8 | 9 | 26 |
| **Extra in model** | Model items not in reference (noise/convention) | 8 | 13 | 6 | 27 |

## 5. Gap filling results
- Filled via **RAG**: 26
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 18
- **Flagged** for manual review: 0

### primarysusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Susceptible

### primaryexposed (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Exposed
- **Reasoning:** The text

### primaryinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Infectious

### postprimaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** Postprimary Immune

### vaccinatedsilentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Asymptomatic Infected

### secondarysusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** SecondarySusceptible

### postvaccinationsusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Postvaccination Susceptible

### secondaryinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** SecondaryInfectious

### postsecondaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** Postsecondary Immune
- **Reasoning:** The

### PrimarySusceptible->PrimaryExposed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### PrimarySusceptible->VaccinatedSilentInfection (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### PrimaryExposed->PrimaryInfectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### PrimaryInfectious->PostPrimaryImmune (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### PostPrimaryImmune->SecondarySusceptible (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### VaccinatedSilentInfection->PostVaccinationSusceptible (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### SecondarySusceptible->SecondaryInfectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### PostVaccinationSusceptible->SecondaryInfectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### SecondaryInfectious->PostSecondaryImmune (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **18**
- Precision **0.5** | Recall **1.0** | F1 **0.6667**
### Flows
- Gold count: **9** | Candidate: **15**
- Precision **0.6** | Recall **1.0** | F1 **0.75**
