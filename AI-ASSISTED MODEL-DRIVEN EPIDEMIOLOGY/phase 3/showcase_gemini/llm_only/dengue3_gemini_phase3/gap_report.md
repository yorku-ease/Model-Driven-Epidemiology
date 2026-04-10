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
| **Reference agreement** | 62.6% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 12.5% | 20% |
| **→ Composite** | **40.5/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 9 | 8 | 9 | 26 |
| **Extra in model** | Model items not in reference (noise/convention) | 8 | 13 | 6 | 27 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 44
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
- **Reasoning:** The excerpt

### postprimaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** Postprimary Immune
- **Reasoning:** The

### vaccinatedsilentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Asymptomatic Infected

### secondarysusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Secondary Susceptible

### postvaccinationsusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Postvaccination Susceptible

### secondaryinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Secondary Infectious
- **Reasoning:** The text

### postsecondaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** Postsecondary Immune
- **Reasoning:** The

### primarytransmissionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.2 per day
- **Confidence:** LOW

### incubationrate (missing_parameters)
- **Source:** inference
- **Value:** 0.167 1/day
- **Confidence:** LOW

### recoveryrate (missing_parameters)
- **Source:** inference
- **Value:** 0.14 per day
- **Confidence:** LOW

### routinevaccinationrate (missing_parameters)
- **Source:** inference
- **Value:** 80.0 % coverage
- **Confidence:** LOW

### naturalcrossprotectionwaningrate (missing_parameters)
- **Source:** inference
- **Value:** 0.5 per year
- **Confidence:** LOW

### vaccinecrossprotectionwaningrate (missing_parameters)
- **Source:** inference
- **Value:** 1.0 per year
- **Confidence:** LOW

### secondarytransmissionrate (missing_parameters)
- **Source:** inference
- **Value:** 3.0 
- **Reasoning:** The basic
- **Confidence:** LOW

### postvaccinationtransmissionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.5 
- **Confidence:** LOW

### PrimarySusceptible->PrimaryExposed (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** The transition of a susceptible individual to an exposed state after being bitten by an infected mosquito.
- **Reasoning:** Dengue is a vector

### PrimarySusceptible->VaccinatedSilentInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** A susceptible individual receives

### PrimaryExposed->PrimaryInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression from the latent

### PrimaryInfectious->PostPrimaryImmune (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals recovering from a

### PostPrimaryImmune->SecondarySusceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of transient, heterologous immunity acquired from a primary dengue infection, making an individual susceptible to a secondary infection.
- **Reasoning:** The paper states that natural infection induces 'transient, heterologous protection,' indicating that immunity from a primary infection is not permanent and can wane, leading to susceptibility to subsequent infections.

### VaccinatedSilentInfection->PostVaccinationSusceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The waning

### SecondarySusceptible->SecondaryInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** A previously infected or vaccinated

### PostVaccinationSusceptible->SecondaryInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals who

### SecondaryInfectious->PostSecondaryImmune (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals recovering from a secondary dengue infection (dengue3) transition to a post-secondary immune state, characterized by long-lasting immunogenic memory that influences the severity of any subsequent infections.
- **Reasoning:** The paper states that 'natural infection... establishes a long-lasting immunogenic memory, which determines disease severity of subsequent infections,' directly supporting the transition from an infectious state to an immune state after recovery.

### primarysusceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible
- **Reasoning:** The

### primaryexposed (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Exposed
- **Reasoning:** The model

### primaryinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Primary Infectious
- **Reasoning:** The

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
- **Primary name:** Secondary Infectious

### postsecondaryimmune (missing_compartments)
- **Source:** inference
- **Primary name:** Postsecondary Immune

### PrimarySusceptible->PrimaryExposed (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become exposed to the dengue virus through contact with infected mosquitoes.
- **Reasoning:** The excerpt discusses 'natural infection' and 'transmission settings' for dengue, a vector-borne disease, implying exposure occurs via contact with infected vectors.

### PrimarySusceptible->VaccinatedSilentInfection (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Primary susceptible individuals who

### PrimaryExposed->PrimaryInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have been exposed to dengue for the first time complete their incubation period and become infectious, capable of transmitting the virus.
- **Reasoning:** The provided text focuses on vaccine efficacy and population-level outcomes, offering no specific details regarding the incubation period or the transition from an exposed state to an infectious state for a primary dengue infection.

### PrimaryInfectious->PostPrimaryImmune (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals recovering from a primary dengue infection and acquiring post-primary immunity.
- **Reasoning:** The paper states that natural infection establishes long-lasting immunogenic memory, implying a transition from an infectious state to an immune state after primary infection.

### PostPrimaryImmune->SecondarySusceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of transient heterologous immunity acquired from a primary dengue infection, leading to susceptibility to a secondary infection.
- **Reasoning:** The paper states that natural infection induces 'transient, heterologous protection,' implying that this protection eventually wanes, making individuals susceptible to subsequent infections.

### VaccinatedSilentInfection->PostVaccinationSusceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who experienced

### SecondarySusceptible->SecondaryInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Secondary susceptible individuals become

### PostVaccinationSusceptible->SecondaryInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Infection of a

### SecondaryInfectious->PostSecondaryImmune (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals recovering from a secondary

## 6. Fill validation (vs gold standard)
- Parameters compared: **8**
- Exact match (<1% error): **0**
- Close (<10% error): **1**
- Approximate (<50% error): **2**
- Poor (>50% error): **5**
- **Accuracy (exact+close)**: **12.5%**
- Median relative error: **900.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| primarytransmissionrate | 0.2 | 0.26 | 23.08% | approximate |
| incubationrate | 0.167 | 0.18 | 7.22% | close |
| recoveryrate | 0.14 | 0.12 | 16.67% | approximate |
| routinevaccinationrate | 80.0 | 0.05 | 159900.0% | poor |
| naturalcrossprotectionwaningrate | 0.5 | 0.02 | 2400.0% | poor |
| vaccinecrossprotectionwaningrate | 1.0 | 0.03 | 3233.33% | poor |
| secondarytransmissionrate | 3.0 | 0.3 | 900.0% | poor |
| postvaccinationtransmissionrate | 0.5 | 0.24 | 108.33% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **17**
- Precision **0.4706** | Recall **0.8889** | F1 **0.6154**
### Flows
- Gold count: **9** | Candidate: **13**
- Precision **0.5385** | Recall **0.7778** | F1 **0.6364**
