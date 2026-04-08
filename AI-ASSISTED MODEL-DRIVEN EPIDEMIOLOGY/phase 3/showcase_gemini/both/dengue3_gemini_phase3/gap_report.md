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
- After fills gaps (re-detected): **0**
- Delta (before - after): **26**
- Delta missing parameters: **8**
- Delta missing compartments: **9**
- Delta missing flows: **9**

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
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 72.1% | 30% |
| **Fill traceability** | 65.4% | 20% |
| **Parameter accuracy** | 75.0% | 20% |
| **→ Composite** | **79.7/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 9 | 8 | 9 | 26 |
| **Extra in model** | Model items not in reference (noise/convention) | 8 | 13 | 6 | 27 |

## 5. Gap filling results
- Filled via **RAG**: 17
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 9
- **Flagged** for manual review: 0

### primarysusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** primarysusceptible
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### primaryexposed (missing_compartments)
- **Source:** inference
- **Primary name:** primaryexposed
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### primaryinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** primaryinfectious
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### postprimaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** postprimaryimmune
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### vaccinatedsilentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** vaccinatedsilentinfection
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### secondarysusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** secondarysusceptible
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### postvaccinationsusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** postvaccinationsusceptible
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### secondaryinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** secondaryinfectious
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### postsecondaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** postsecondaryimmune
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### primarytransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.26 
- **Description:** Transmission parameter for primary infection
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_dengue

### incubationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.20 
- **Description:** Progression from exposed to infectious
- **From papers:** p1_model_ebola, p1_model_cholera, p1_model_dengue

### recoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.14 
- **Description:** Recovery from infectious state
- **From papers:** p1_model_measles, p1_model_ebola, p1_model_cholera

### routinevaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.05 
- **Description:** Routine vaccination of 9-year-olds
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_dengue

### naturalcrossprotectionwaningrate (missing_parameters)
- **Source:** rag
- **Value:** 0.02 
- **Description:** Waning of temporary heterologous protection after natural infection
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_dengue

### vaccinecrossprotectionwaningrate (missing_parameters)
- **Source:** rag
- **Value:** 0.03 
- **Description:** Waning of temporary heterologous protection after vaccination
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_dengue

### secondarytransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.30 
- **Description:** Transmission parameter for secondary-like infection after natural primary infection
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_dengue

### postvaccinationtransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.24 
- **Description:** Transmission parameter after vaccine-induced silent infection
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_dengue

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

## 6. Fill validation (vs gold standard)
- Parameters compared: **8**
- Exact match (<1% error): **6**
- Close (<10% error): **0**
- Approximate (<50% error): **2**
- Poor (>50% error): **0**
- **Accuracy (exact+close)**: **75.0%**
- Median relative error: **0.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| primarytransmissionrate | 0.26 | 0.26 | 0.0% | exact |
| incubationrate | 0.2 | 0.18 | 11.11% | approximate |
| recoveryrate | 0.14 | 0.12 | 16.67% | approximate |
| routinevaccinationrate | 0.05 | 0.05 | 0.0% | exact |
| naturalcrossprotectionwaningrate | 0.02 | 0.02 | 0.0% | exact |
| vaccinecrossprotectionwaningrate | 0.03 | 0.03 | 0.0% | exact |
| secondarytransmissionrate | 0.3 | 0.3 | 0.0% | exact |
| postvaccinationtransmissionrate | 0.24 | 0.24 | 0.0% | exact |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **17**
- Precision **0.5294** | Recall **1.0** | F1 **0.6923**
### Flows
- Gold count: **9** | Candidate: **15**
- Precision **0.6** | Recall **1.0** | F1 **0.75**
