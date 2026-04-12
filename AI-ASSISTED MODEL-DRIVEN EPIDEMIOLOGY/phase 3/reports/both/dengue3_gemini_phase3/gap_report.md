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
- Extra compartments: susceptible humans, infectious humans (primary), recovered humans (primary), infectious humans (secondary), recovered humans (post-secondary), susceptible mosquitoes, exposed mosquitoes, infectious mosquitoes
- Extra parameters: β_hv, β_vh, γ_h, σ_v, μ_h, μ_v, case fatality rate, default vaccine coverage, default vaccination age, discount rate, dalys per symptomatic dengue case, dalys per severe dengue case, cost per hospitalised case (public payer, latin america)

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 34.6% | 30% |
| **Reference agreement** | 69.6% | 30% |
| **Fill traceability** | 59.3% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **43.1/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 9 | 8 | 9 | 26 |
| **Extra in model** | Model items not in reference (noise/convention) | 8 | 13 | 6 | 27 |

## 5. Gap filling results
- Filled via **RAG**: 8
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 35
- **Flagged** for manual review: 0

### primarysusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Susceptible
- **Reasoning:** The

### primaryexposed (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Exposed
- **Reasoning:** The

### primaryinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Infectious

### postprimaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** Post Primary Immune
- **Reasoning:** The

### vaccinatedsilentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Silent Infection

### secondarysusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Secondary Susceptible
- **Reasoning:** The

### postvaccinationsusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Susceptible

### secondaryinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** SecondaryInfectious

### postsecondaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** Postsecondary Immune
- **Reasoning:** The

### primarytransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### incubationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### recoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### routinevaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### naturalcrossprotectionwaningrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### vaccinecrossprotectionwaningrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### secondarytransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### postvaccinationtransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### PrimarySusceptible->PrimaryExposed (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Infection of a

### PrimarySusceptible->VaccinatedSilentInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Vaccination of

### PrimaryExposed->PrimaryInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression from the latent

### PrimaryInfectious->PostPrimaryImmune (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovery from a

### PostPrimaryImmune->SecondarySusceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have

### VaccinatedSilentInfection->PostVaccinationSusceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of transient immunity acquired from a silent dengue

### SecondarySusceptible->SecondaryInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have previously experienced

### PostVaccinationSusceptible->SecondaryInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals who are

### SecondaryInfectious->PostSecondaryImmune (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovery from a secondary dengue infection, leading to a state of post-secondary immunity.
- **Reasoning:** Individuals recover from a secondary dengue infection at a certain rate and develop an immune response, transitioning from an infectious state to an immune state, as supported by the excerpt's mention of 'long-lasting immunogenic memory' after infection.

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

### postvaccinationsusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Postvaccination Susceptible

### postsecondaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** Postsecondary Immune
- **Reasoning:** The

### PrimarySusceptible->PrimaryExposed (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** A previously

### PrimarySusceptible->VaccinatedSilentInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** A primary susceptible

### PrimaryExposed->PrimaryInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of

### PrimaryInfectious->PostPrimaryImmune (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovery from primary dengue infection, leading to a state of post-primary immunity.
- **Reasoning:** The paper states that natural infection establishes long-lasting immunogenic memory, indicating that individuals recover from primary infection and transition to an immune state.

### PostPrimaryImmune->SecondarySusceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of transient heter

### VaccinatedSilentInfection->PostVaccinationSusceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who

### SecondarySusceptible->SecondaryInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** A SecondarySusceptible individual acquires a secondary dengue infection through contact with an infected mosquito and becomes infectious.

### PostVaccinationSusceptible->SecondaryInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals who are

### SecondaryInfectious->PostSecondaryImmune (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals recovering from a secondary

## 6. Fill validation (vs gold standard)
- Parameters compared: **8**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **8**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.96%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| primarytransmissionrate | 0.0001 | 0.26 | 99.96% | poor |
| incubationrate | 0.0001 | 0.18 | 99.94% | poor |
| recoveryrate | 0.5 | 0.12 | 316.67% | poor |
| routinevaccinationrate | 0.0001 | 0.05 | 99.8% | poor |
| naturalcrossprotectionwaningrate | 0.0001 | 0.02 | 99.5% | poor |
| vaccinecrossprotectionwaningrate | 0.0001 | 0.03 | 99.67% | poor |
| secondarytransmissionrate | 0.0001 | 0.3 | 99.97% | poor |
| postvaccinationtransmissionrate | 0.0001 | 0.24 | 99.96% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **19**
- Precision **0.4737** | Recall **1.0** | F1 **0.6429**
### Flows
- Gold count: **9** | Candidate: **15**
- Precision **0.6** | Recall **1.0** | F1 **0.75**
