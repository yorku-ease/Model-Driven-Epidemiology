# Phase 3 Gap Analysis Report

**Disease / context:** hiv2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **22**
- Missing compartments: 5
- Missing parameters: 10
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 7
- Extra compartments (not in gold standard): 6
- Extra parameters (not in gold standard): 11
- Extra flows (not in gold standard): 9

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **22**
- After fills gaps (re-detected): **12**
- Delta (before - after): **10**
- Delta missing parameters: **10**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **earlyinfection** — Present in gold standard but not in extracted model. (severity: high)
- **chronicinfection** — Present in gold standard but not in extracted model. (severity: high)
- **lateinfection** — Present in gold standard but not in extracted model. (severity: high)
- **onart** — Present in gold standard but not in extracted model. (severity: high)
- **removed** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->EarlyInfection** — Present in gold standard but not in extracted model (or flows list).
- **EarlyInfection->ChronicInfection** — Present in gold standard but not in extracted model (or flows list).
- **ChronicInfection->LateInfection** — Present in gold standard but not in extracted model (or flows list).
- **ChronicInfection->OnART** — Present in gold standard but not in extracted model (or flows list).
- **LateInfection->OnART** — Present in gold standard but not in extracted model (or flows list).
- **LateInfection->Removed** — Present in gold standard but not in extracted model (or flows list).
- **OnART->Removed** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **betaearly** — Present in gold standard but not in extracted model.
- **betachronic** — Present in gold standard but not in extracted model.
- **betalate** — Present in gold standard but not in extracted model.
- **betaart** — Present in gold standard but not in extracted model.
- **progression1** — Present in gold standard but not in extracted model.
- **progression2** — Present in gold standard but not in extracted model.
- **artstart1** — Present in gold standard but not in extracted model.
- **artstart2** — Present in gold standard but not in extracted model.
- **deathlate** — Present in gold standard but not in extracted model.
- **deathart** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: early infection, asymptomatic infection, pre-aids infection, aids, on antiretroviral therapy, dropped out of treatment
- Extra parameters: cd4 treatment eligibility threshold, average time from eligibility to treatment initiation, treatment access, retention on treatment after 3 years, art introduction time, baseline future scale-up art starts, low future scale-up art starts, medium future scale-up art starts, high future scale-up art starts, art transmission reduction, early infection transmissibility increase

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 45.5% | 30% |
| **Reference agreement** | 54.4% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **50.0/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 5 | 10 | 7 | 22 |
| **Extra in model** | Model items not in reference (noise/convention) | 6 | 11 | 9 | 26 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 34
- **Flagged** for manual review: 0

### earlyinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Early Infection
- **Reasoning:** The excerpt

### chronicinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Chronically Infected
- **Reasoning:** The

### lateinfection (missing_compartments)
- **Source:** inference
- **Primary name:** On ART
- **Reasoning:** The

### onart (missing_compartments)
- **Source:** inference
- **Primary name:** On ART
- **Reasoning:** The

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** On Antiretroviral Therapy

### Susceptible->EarlyInfection (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Transmission of HIV from an infectious individual to a susceptible individual, leading to early infection.
- **Reasoning:** HIV is a sexually transmitted infection, and its spread is mediated by contact between susceptible and infectious individuals.

### EarlyInfection->ChronicInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV infection from the acute seroconversion phase to the chronic, clinically latent stage.
- **Reasoning:** This transition represents the natural history of HIV infection where individuals move from the initial acute phase to a prolonged chronic phase, a fundamental process in HIV epidemiology models.

### ChronicInfection->LateInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV-infected individuals from the chronic asymptomatic stage to the late symptomatic (AIDS) stage due to immune system decline.
- **Reasoning:** This transition represents the natural progression of HIV disease over time as the immune system deteriorates, leading to the late symptomatic stage (AIDS) if not treated.

### ChronicInfection->OnART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with chronic HIV infection initiating antiretroviral therapy (ART).
- **Reasoning:** The excerpt discusses the expansion and provision of antiretroviral therapy (ART) to HIV-infected individuals, directly representing the transition from chronic infection to being on ART.

### LateInfection->OnART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in the late stage of HIV infection initiate antiretroviral therapy (ART).
- **Reasoning:** The excerpt discusses the provision and expansion of ART for HIV-infected individuals, indicating a transition from an infected state to a state of being on treatment.

### LateInfection->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality due to AIDS-related complications in individuals with late-stage HIV infection.
- **Reasoning:** The excerpt discusses the impact of antiretroviral therapy (ART) on reducing HIV spread and preventing progression, implying that without effective treatment, individuals in late infection stages would die and be removed from the population.

### OnART->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality or loss

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **11**
- Precision **0.4545** | Recall **0.8333** | F1 **0.5882**
### Flows
- Gold count: **7** | Candidate: **13**
- Precision **0.3846** | Recall **0.7143** | F1 **0.5**
