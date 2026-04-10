# Phase 3 Gap Analysis Report

**Disease / context:** covid3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **19**
- Missing compartments: 2
- Missing parameters: 14
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 3
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 6
- Extra flows (not in gold standard): 1

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **19**
- After fills gaps (re-detected): **6**
- Delta (before - after): **13**
- Delta missing parameters: **12**
- Delta missing compartments: **1**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **susceptible** — Present in gold standard but not in extracted model. (severity: high)
- **removed** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->Infected** — Present in gold standard but not in extracted model (or flows list).
- **Infected->Removed** — Present in gold standard but not in extracted model (or flows list).
- **Infected->Dead** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **population11countries** — Present in gold standard but not in extracted model.
- **initialrt** — Present in gold standard but not in extracted model.
- **generationintervaldays** — Present in gold standard but not in extracted model.
- **basetransmissionrate** — Present in gold standard but not in extracted model.
- **publiceventsbaneffect** — Present in gold standard but not in extracted model.
- **schoolclosureeffect** — Present in gold standard but not in extracted model.
- **selfisolationeffect** — Present in gold standard but not in extracted model.
- **socialdistancingeffect** — Present in gold standard but not in extracted model.
- **lockdowneffect** — Present in gold standard but not in extracted model.
- **combinedinterventionmultiplier** — Present in gold standard but not in extracted model.
- **effectivetransmissionrate** — Present in gold standard but not in extracted model.
- **meanoutcomedelaydays** — Present in gold standard but not in extracted model.
- **removalrate** — Present in gold standard but not in extracted model.
- **deathrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: r_t, initial r_t, serial interval distribution, infection fatality ratio, infection-to-death distribution, lockdown effect

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 68.4% | 30% |
| **Reference agreement** | 42.9% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 7.7% | 20% |
| **→ Composite** | **44.9/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 14 | 3 | 19 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 6 | 1 | 7 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 25
- **Flagged** for manual review: 0

### susceptible (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** Deceased
- **Reasoning:** The model

### population11countries (missing_parameters)
- **Source:** inference
- **Value:** 750000000.0 
- **Confidence:** LOW

### initialrt (missing_parameters)
- **Source:** inference
- **Value:** 3.0 
- **Reasoning:** The text indicates that R_t during the uncontrolled epidemic phase for SARS-CoV-2 was typically around 2-4. A value of 3.0 is a reasonable mid-point for the initial R_t before interventions.
- **Confidence:** LOW

### generationintervaldays (missing_parameters)
- **Source:** inference
- **Value:** 5.5 days
- **Reasoning:** The generation interval for COVID-19 is typically estimated to be between 4 and 7 days, reflecting the average time between successive infections in a transmission chain.
- **Confidence:** LOW

### basetransmissionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.5 1/day
- **Reasoning:** Based on an estimated R0 for uncontrolled COVID-19 of around 3 and an average infectious period of 6 days (beta = R0/D).
- **Confidence:** LOW

### publiceventsbaneffect (missing_parameters)
- **Source:** inference
- **Value:** 0.7 
- **Confidence:** LOW

### schoolclosureeffect (missing_parameters)
- **Source:** inference
- **Value:** 0.15 
- **Confidence:** LOW

### selfisolationeffect (missing_parameters)
- **Source:** inference
- **Value:** 0.5 
- **Reasoning:** Self
- **Confidence:** LOW

### socialdistancingeffect (missing_parameters)
- **Source:** inference
- **Value:** 0.6 
- **Reasoning:** Social distancing
- **Confidence:** LOW

### lockdowneffect (missing_parameters)
- **Source:** inference
- **Value:** 0.3 
- **Confidence:** LOW

### combinedinterventionmultiplier (missing_parameters)
- **Source:** inference
- **Value:** 0.3 
- **Confidence:** LOW

### effectivetransmissionrate (missing_parameters)
- **Source:** inference
- **Value:** 3.0 
- **Reasoning:** The provided text indicates that the effective reproduction rate (R_t) for SARS-CoV-2 during an uncontrolled epidemic ranged from 2-4. A value of 3.0 represents a central estimate for this uncontrolled phase.
- **Confidence:** LOW

### meanoutcomedelaydays (missing_parameters)
- **Source:** inference
- **Value:** 24 days
- **Reasoning:** This parameter represents the mean delay from infection to death. For COVID-19, this typically ranges from 20 to 30 days, accounting for the incubation period and the time from symptom onset to death.
- **Confidence:** LOW

### removalrate (missing_parameters)
- **Source:** inference
- **Value:** 0.1 per day
- **Reasoning:** This value represents the inverse of a typical infectious period for COVID-19, commonly estimated to be around 10 days.
- **Confidence:** LOW

### deathrate (missing_parameters)
- **Source:** inference
- **Value:** 0.01 
- **Reasoning:** The Infection Fatality Rate (IFR) for COVID-19 typically ranges from 0.5% to 1.5% globally, varying by age and healthcare system, with 1% being a commonly cited average.
- **Confidence:** LOW

### Susceptible->Infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected through contact with infected individuals.
- **Reasoning:** COVID-19 is a directly transmissible disease, and the concept of R_t (reproduction number) discussed in the paper implies a contact-dependent infection process.

### Infected->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals move from the infected state to the removed state due to recovery or death, no longer contributing to transmission.
- **Reasoning:** The paper explicitly discusses 'linking the infection cycle to observed deaths' and 'estimating the deaths that would have occurred without interventions,' indicating that death is a key outcome of infection and a component of the 'Removed' state in a mechanistic model.

### Infected->Dead (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who are infected with

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** Deceased
- **Reasoning:** The

### initialrt (missing_parameters)
- **Source:** inference
- **Value:** 3.0 
- **Reasoning:** The provided text states that R_t for SARS-CoV-2 during an uncontrolled epidemic typically ranges from 2-4. This value represents the initial reproduction number before interventions.
- **Confidence:** LOW

### lockdowneffect (missing_parameters)
- **Source:** inference
- **Value:** 0.6 
- **Reasoning:** Lockdowns
- **Confidence:** LOW

### Susceptible->Infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected after effective contact with infectious individuals.
- **Reasoning:** Infectious diseases like COVID-19 spread through interactions between susceptible and infected individuals, making the transition dependent on contact rates.

### Infected->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** This flow represents infected individuals either recovering from the disease or dying due to it, thereby moving out of the infectious state.
- **Reasoning:** The excerpt discusses 'observed deaths' and the 'infection cycle,' implying that infected individuals eventually exit the infected state through recovery or death, which are processes typically modeled as a rate.

### Infected->Dead (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality of individuals infected

## 6. Fill validation (vs gold standard)
- Parameters compared: **13**
- Exact match (<1% error): **0**
- Close (<10% error): **1**
- Approximate (<50% error): **6**
- Poor (>50% error): **6**
- **Accuracy (exact+close)**: **7.7%**
- Median relative error: **40.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| population11countries | 750000000.0 | 741000000.0 | 1.21% | close |
| initialrt | 3.0 | 3.8 | 21.05% | approximate |
| generationintervaldays | 5.5 | 6.5 | 15.38% | approximate |
| basetransmissionrate | 0.5 | InitialRt / GenerationIntervalDays | — | no_comparison |
| publiceventsbaneffect | 0.7 | 1.0 | 30.0% | approximate |
| schoolclosureeffect | 0.15 | 1.0 | 85.0% | poor |
| selfisolationeffect | 0.5 | 1.0 | 50.0% | poor |
| socialdistancingeffect | 0.6 | 1.0 | 40.0% | approximate |
| lockdowneffect | 0.3 | 0.19 | 57.89% | poor |
| combinedinterventionmultiplier | 0.3 | PublicEventsBanEffect * SchoolClosureEffect * SelfIsolationEffect * SocialDistancingEffect * LockdownEffect | — | no_comparison |
| effectivetransmissionrate | 3.0 | BaseTransmissionRate * CombinedInterventionMultiplier | — | no_comparison |
| meanoutcomedelaydays | 24.0 | 18.0 | 33.33% | approximate |
| removalrate | 0.1 | 1.0 | 90.0% | poor |
| deathrate | 0.01 | 0.005 | 100.0% | poor |
| initialrt | 3.0 | 3.8 | 21.05% | approximate |
| lockdowneffect | 0.6 | 0.19 | 215.79% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **4** | Candidate: **4**
- Precision **1.0** | Recall **0.75** | F1 **0.8571**
### Flows
- Gold count: **3** | Candidate: **1**
- Precision **0.0** | Recall **0.0** | F1 **0.0**
