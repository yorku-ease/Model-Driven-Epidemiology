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
- Extra compartments (not in gold standard): 12
- Extra parameters (not in gold standard): 21
- Extra flows (not in gold standard): 12

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **26**
- After fills gaps (re-detected): **17**
- Delta (before - after): **9**
- Delta missing parameters: **8**
- Delta missing compartments: **1**
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
- Extra compartments: susceptible humans (naive), vaccinated humans (naive), infected humans (primary), recovered humans (post-primary), vaccinated humans (post-primary), infected humans (secondary), recovered humans (post-secondary), infected humans (postsecondary), recovered humans (post-postsecondary), susceptible mosquitoes, exposed mosquitoes, infectious mosquitoes
- Extra parameters: vaccination_age, vaccination_coverage, vaccination_doses, vaccination_dose_interval, human_mean_lifespan, case_fatality_rate_symptomatic_dengue, symptomatic_dengue_dalys, severe_dengue_dalys, cost_symptomatic_public_latinamerica, cost_hospitalised_public_latinamerica, cost_symptomatic_societal_latinamerica, cost_hospitalised_societal_latinamerica, cost_fatal_societal_latinamerica, cost_symptomatic_public_southeastasia, cost_hospitalised_public_southeastasia, cost_symptomatic_societal_southeastasia, cost_hospitalised_societal_southeastasia, cost_fatal_societal_southeastasia, discount_rate_health_costs, time_horizon_evaluation, threshold_cost_per_daly_averted_basecase

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 34.6% | 30% |
| **Reference agreement** | 50.8% | 30% |
| **Fill traceability** | 80.2% | 20% |
| **Parameter accuracy** | 75.0% | 20% |
| **→ Composite** | **56.7/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 9 | 8 | 9 | 26 |
| **Extra in model** | Model items not in reference (noise/convention) | 12 | 21 | 12 | 45 |

## 5. Gap filling results
- Filled via **RAG**: 26
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 17
- **Flagged** for manual review: 0

### primarysusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Susceptible
- **Reasoning:** The

### primaryexposed (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Exposed

### primaryinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Infectious
- **Reasoning:** The excerpt

### postprimaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** Postprimary Immune

### vaccinatedsilentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Asymptomatic Infected

### secondarysusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Secondary Susceptible
- **Reasoning:** The

### postvaccinationsusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Postvaccination Susceptible

### secondaryinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** SecondaryInfectious

### postsecondaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** Postsecondary Immune

### primarytransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.26 
- **Description:** Transmission parameter for primary infection
- **From papers:** p2_gold_dengue3, p1_model_cholera, p1_model_dengue

### incubationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.20 
- **Description:** Progression from exposed to infectious
- **From papers:** p2_gold_dengue3, p1_model_cholera, p2_gold_ebola2

### recoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.14 
- **Description:** Recovery from infectious state
- **From papers:** p2_gold_measles2, p2_gold_dengue3, p2_gold_ebola2

### routinevaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.05 
- **Description:** Routine vaccination of 9-year-olds
- **From papers:** p2_gold_dengue3, p1_model_cholera, p1_model_dengue

### naturalcrossprotectionwaningrate (missing_parameters)
- **Source:** rag
- **Value:** 0.02 
- **Description:** Waning of temporary heterologous protection after natural infection
- **From papers:** p2_gold_dengue3, p1_model_cholera, p1_model_dengue

### vaccinecrossprotectionwaningrate (missing_parameters)
- **Source:** rag
- **Value:** 0.03 
- **Description:** Waning of temporary heterologous protection after vaccination
- **From papers:** p2_gold_dengue3, p1_model_cholera, p1_model_dengue

### secondarytransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.30 
- **Description:** Transmission parameter for secondary-like infection after natural primary infection
- **From papers:** p2_gold_dengue3, p1_model_cholera, p1_model_dengue

### postvaccinationtransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.24 
- **Description:** Transmission parameter after vaccine-induced silent infection
- **From papers:** p2_gold_dengue3, p1_model_cholera, p1_model_dengue

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

### primarysusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Susceptible

### primaryexposed (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Exposed
- **Reasoning:** The excerpt

### primaryinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Infectious
- **Reasoning:** The

### postprimaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** Postprimary Immune

### vaccinatedsilentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Infected
- **Reasoning:** The

### secondarysusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Secondary Susceptible
- **Reasoning:** The text explicitly mentions 'subsequent infections' and 'disease severity of subsequent infections' determined by 'immunogenic memory,' indicating that individuals can

### postvaccinationsusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Post-Vaccination Susceptible

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
- Gold count: **9** | Candidate: **22**
- Precision **0.3636** | Recall **0.8889** | F1 **0.5161**
### Flows
- Gold count: **9** | Candidate: **19**
- Precision **0.3684** | Recall **0.7778** | F1 **0.5**
