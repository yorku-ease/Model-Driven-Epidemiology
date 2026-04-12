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
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **40.0/100** | — |

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
- **Reasoning:** The

### chronicinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Chronic Infection
- **Reasoning:** The

### lateinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Late Infection

### onart (missing_compartments)
- **Source:** inference
- **Primary name:** On ART
- **Reasoning:** The excerpt

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** Treated Infected

### betaearly (missing_parameters)
- **Source:** inference
- **Value:** 0.0005 per susceptible
- **Confidence:** LOW

### betachronic (missing_parameters)
- **Source:** inference
- **Value:** 5e-05 per susceptible-infected
- **Confidence:** LOW

### betalate (missing_parameters)
- **Source:** inference
- **Value:** 0.005 
- **Confidence:** LOW

### betaart (missing_parameters)
- **Source:** inference
- **Value:** 0.008 per susceptible-
- **Confidence:** LOW

### progression1 (missing_parameters)
- **Source:** inference
- **Value:** 0.1 per year
- **Confidence:** LOW

### progression2 (missing_parameters)
- **Source:** inference
- **Value:** 0.05 per year
- **Confidence:** LOW

### artstart1 (missing_parameters)
- **Source:** inference
- **Value:** 350.0 cells/µL
- **Confidence:** LOW

### artstart2 (missing_parameters)
- **Source:** inference
- **Value:** 350.0 cells/mm³
- **Confidence:** LOW

### deathlate (missing_parameters)
- **Source:** inference
- **Value:** 0.05 per year
- **Confidence:** LOW

### deathart (missing_parameters)
- **Source:** inference
- **Value:** 0.03 per year
- **Confidence:** LOW

### Susceptible->EarlyInfection (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** New HIV infections occurring when susceptible individuals come into contact with infected individuals.
- **Reasoning:** HIV is a communicable disease primarily transmitted through contact, and the text discusses 'reducing the spread' and 'transmission of infection', indicating contact-driven incidence.

### EarlyInfection->ChronicInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Natural progression of HIV infection from the acute/early phase to the chronic phase within an infected individual.
- **Reasoning:** This transition represents the natural, time-dependent progression of HIV disease within an individual from the initial acute infection stage to the long-term chronic stage.

### ChronicInfection->LateInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV infection from the chronic phase to the late-stage phase, characterized by severe immunosuppression and increased susceptibility to opportunistic infections.
- **Reasoning:** This transition represents the natural biological progression of HIV disease within an infected individual over time, independent of contact, as the immune system deteriorates.

### ChronicInfection->OnART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Initiation of antire

### LateInfection->OnART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Initiation of antiretroviral therapy (ART) for individuals in the late stage of HIV infection.
- **Reasoning:** The paper excerpt discusses the provision of ART, including 'immediate treatment for all HIV-infected individuals' and 'providing treatment in line with current therapeutic guidelines', directly supporting a transition to ART for infected individuals.

### LateInfection->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality due

### OnART->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals on antiretroviral therapy (ART) who are removed from the modeled population due to death, loss to follow-up, or migration.
- **Reasoning:** The transition from 'OnART' to 'Removed' typically represents events like death or loss to follow-up, which are generally modeled as a constant rate per unit of time, characteristic of a RateFlow.

### earlyinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Early Infection
- **Reasoning:** The

### chronicinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Chronic Infection

### lateinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Late Infection

### onart (missing_compartments)
- **Source:** inference
- **Primary name:** On Antiretroviral Therapy

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** Treated Infected
- **Reasoning:** The

### Susceptible->EarlyInfection (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** A susceptible individual acquires HIV infection from an infected individual through contact, transitioning into an early infection stage.
- **Reasoning:** The excerpt discusses 'reducing the spread of HIV' and 'reduce transmission of infection,' indicating that new infections arise from contact between susceptible and infected individuals.

### EarlyInfection->ChronicInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Natural progression of HIV infection from the acute/early phase to the chronic asymptomatic phase within an infected individual.
- **Reasoning:** This transition represents the natural, time-dependent progression of HIV infection within an individual from the acute phase to the chronic phase, which is a characteristic epidemiological feature of HIV.

### ChronicInfection->LateInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV disease within an infected individual from the chronic asymptomatic phase to the late-stage symptomatic phase (e.g., AIDS).
- **Reasoning:** This transition represents the natural, time-dependent progression of HIV infection within an individual, typically characterized by a decline in CD4 cell count and the onset of opportunistic infections, moving from a chronic state to a more advanced, late-stage disease.

### ChronicInfection->OnART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Initiation of

### LateInfection->OnART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in the late stage of HIV infection initiate antiretroviral therapy (ART).
- **Reasoning:** This transition represents the rate at which individuals with late-stage HIV infection are diagnosed and subsequently start ART, a medical intervention discussed in the excerpt.

### LateInfection->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality due to

### OnART->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality (HIV-

## 6. Fill validation (vs gold standard)
- Parameters compared: **10**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **1**
- Poor (>50% error): **9**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.17%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| betaearly | 0.0005 | 0.8 | 99.94% | poor |
| betachronic | 5e-05 | 0.3 | 99.98% | poor |
| betalate | 0.005 | 0.6 | 99.17% | poor |
| betaart | 0.008 | 0.05 | 84.0% | poor |
| progression1 | 0.1 | 0.5 | 80.0% | poor |
| progression2 | 0.05 | 0.2 | 75.0% | poor |
| artstart1 | 350.0 | 0.3 | 116566.67% | poor |
| artstart2 | 350.0 | 0.4 | 87400.0% | poor |
| deathlate | 0.05 | 0.2 | 75.0% | poor |
| deathart | 0.03 | 0.05 | 40.0% | approximate |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **11**
- Precision **0.4545** | Recall **0.8333** | F1 **0.5882**
### Flows
- Gold count: **7** | Candidate: **13**
- Precision **0.3846** | Recall **0.7143** | F1 **0.5**
