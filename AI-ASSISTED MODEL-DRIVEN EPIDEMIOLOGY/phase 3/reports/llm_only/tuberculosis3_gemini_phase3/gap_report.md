# Phase 3 Gap Analysis Report

**Disease / context:** tuberculosis3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **40**
- Missing compartments: 6
- Missing parameters: 18
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 16
- Extra compartments (not in gold standard): 6
- Extra parameters (not in gold standard): 19
- Extra flows (not in gold standard): 16

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **40**
- After fills gaps (re-detected): **22**
- Delta (before - after): **18**
- Delta missing parameters: **18**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **recenttbinfection** — Present in gold standard but not in extracted model. (severity: high)
- **remotetbinfection** — Present in gold standard but not in extracted model. (severity: high)
- **activetbsmearpositive** — Present in gold standard but not in extracted model. (severity: high)
- **activetbsmearnegative** — Present in gold standard but not in extracted model. (severity: high)
- **activetbextrapulmonary** — Present in gold standard but not in extracted model. (severity: high)
- **recentlytreatedrecovered** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Uninfected->RecentTBInfection** — Present in gold standard but not in extracted model (or flows list).
- **RecentTBInfection->RemoteTBInfection** — Present in gold standard but not in extracted model (or flows list).
- **RecentTBInfection->ActiveTBSmearPositive** — Present in gold standard but not in extracted model (or flows list).
- **RecentTBInfection->ActiveTBSmearNegative** — Present in gold standard but not in extracted model (or flows list).
- **RecentTBInfection->ActiveTBExtrapulmonary** — Present in gold standard but not in extracted model (or flows list).
- **RemoteTBInfection->RecentTBInfection** — Present in gold standard but not in extracted model (or flows list).
- **RemoteTBInfection->ActiveTBSmearPositive** — Present in gold standard but not in extracted model (or flows list).
- **RemoteTBInfection->ActiveTBSmearNegative** — Present in gold standard but not in extracted model (or flows list).
- **RemoteTBInfection->ActiveTBExtrapulmonary** — Present in gold standard but not in extracted model (or flows list).
- **ActiveTBSmearPositive->RecentlyTreatedRecovered** — Present in gold standard but not in extracted model (or flows list).
- **ActiveTBSmearNegative->RecentlyTreatedRecovered** — Present in gold standard but not in extracted model (or flows list).
- **ActiveTBExtrapulmonary->RecentlyTreatedRecovered** — Present in gold standard but not in extracted model (or flows list).
- **RecentlyTreatedRecovered->RemoteTBInfection** — Present in gold standard but not in extracted model (or flows list).
- **RecentlyTreatedRecovered->ActiveTBSmearPositive** — Present in gold standard but not in extracted model (or flows list).
- **RecentlyTreatedRecovered->ActiveTBSmearNegative** — Present in gold standard but not in extracted model (or flows list).
- **RecentlyTreatedRecovered->ActiveTBExtrapulmonary** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **infectionratefromsmearpositive** — Present in gold standard but not in extracted model.
- **infectionratefromsmearnegative** — Present in gold standard but not in extracted model.
- **recenttoremotestabilizationrate** — Present in gold standard but not in extracted model.
- **rapidprogressiontosmearpositiverate** — Present in gold standard but not in extracted model.
- **rapidprogressiontosmearnegativerate** — Present in gold standard but not in extracted model.
- **rapidprogressiontoextrapulmonaryrate** — Present in gold standard but not in extracted model.
- **reinfectionratefromsmearpositive** — Present in gold standard but not in extracted model.
- **reinfectionratefromsmearnegative** — Present in gold standard but not in extracted model.
- **remotereactivationtosmearpositiverate** — Present in gold standard but not in extracted model.
- **remotereactivationtosmearnegativerate** — Present in gold standard but not in extracted model.
- **remotereactivationtoextrapulmonaryrate** — Present in gold standard but not in extracted model.
- **treatmentorselfcureratesmearpositive** — Present in gold standard but not in extracted model.
- **treatmentorselfcureratesmearnegative** — Present in gold standard but not in extracted model.
- **treatmentorselfcurerateextrapulmonary** — Present in gold standard but not in extracted model.
- **recoveredtoremotestabilizationrate** — Present in gold standard but not in extracted model.
- **relapsetosmearpositiverate** — Present in gold standard but not in extracted model.
- **relapsetosmearnegativerate** — Present in gold standard but not in extracted model.
- **relapsetoextrapulmonaryrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: recently infected latent tb, remotely infected latent tb, active tb smear-positive pulmonary, active tb smear-negative pulmonary, active tb extrapulmonary, recently treated and recovered
- Extra parameters: beta, relative_infectiousness_smear_negative, relative_rate_early_progression, rapid_progression_rate, slow_progression_rate, proportion_extrapulmonary, proportion_smear_positive, mortality_rate_smear_positive, mortality_rate_smear_negative_extrapulmonary, natural_cure_rate_smear_positive, natural_cure_rate_smear_negative_extrapulmonary, informal_sector_size, treatment_success_notified_2008, treatment_success_informal, relapse_rate, population_growth_rate, diagnostic_rate_smear_positive_2008, diagnostic_rate_smear_negative_2008, diagnostic_rate_extrapulmonary_2008

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 45.0% | 30% |
| **Reference agreement** | 58.5% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 16.7% | 20% |
| **→ Composite** | **44.4/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 6 | 18 | 16 | 40 |
| **Extra in model** | Model items not in reference (noise/convention) | 6 | 19 | 16 | 41 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 62
- **Flagged** for manual review: 0

### recenttbinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Latent TB
- **Reasoning:** The

### remotetbinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Remote TB Infection

### activetbsmearpositive (missing_compartments)
- **Source:** inference
- **Primary name:** Active TB Smear Positive

### activetbsmearnegative (missing_compartments)
- **Source:** inference
- **Primary name:** Active TB Smear-Negative Undiagnosed

### activetbextrapulmonary (missing_compartments)
- **Source:** inference
- **Primary name:** Active TB Extrapulmonary

### recentlytreatedrecovered (missing_compartments)
- **Source:** inference
- **Primary name:** Recently Treated Recovered

### infectionratefromsmearpositive (missing_parameters)
- **Source:** inference
- **Value:** 1e-06 
- **Confidence:** LOW

### infectionratefromsmearnegative (missing_parameters)
- **Source:** inference
- **Value:** 0.15 1/year
- **Confidence:** LOW

### recenttoremotestabilizationrate (missing_parameters)
- **Source:** inference
- **Value:** 0.5 1/year
- **Confidence:** LOW

### rapidprogressiontosmearpositiverate (missing_parameters)
- **Source:** inference
- **Value:** 0.05 per year
- **Confidence:** LOW

### rapidprogressiontosmearnegativerate (missing_parameters)
- **Source:** inference
- **Value:** 6.0 per year
- **Confidence:** LOW

### rapidprogressiontoextrapulmonaryrate (missing_parameters)
- **Source:** inference
- **Value:** 0.05 per year
- **Confidence:** LOW

### reinfectionratefromsmearpositive (missing_parameters)
- **Source:** inference
- **Value:** 0.005 per year
- **Confidence:** LOW

### reinfectionratefromsmearnegative (missing_parameters)
- **Source:** inference
- **Value:** 0.005 per year
- **Confidence:** LOW

### remotereactivationtosmearpositiverate (missing_parameters)
- **Source:** inference
- **Value:** 0.0007 year^-1
- **Confidence:** LOW

### remotereactivationtosmearnegativerate (missing_parameters)
- **Source:** inference
- **Value:** 0.0005 per year
- **Confidence:** LOW

### remotereactivationtoextrapulmonaryrate (missing_parameters)
- **Source:** inference
- **Value:** 0.0003 per person-
- **Confidence:** LOW

### treatmentorselfcureratesmearpositive (missing_parameters)
- **Source:** inference
- **Value:** 1.5 per year
- **Confidence:** LOW

### treatmentorselfcureratesmearnegative (missing_parameters)
- **Source:** inference
- **Value:** 0.5 per year
- **Confidence:** LOW

### treatmentorselfcurerateextrapulmonary (missing_parameters)
- **Source:** inference
- **Value:** 1.5 per year
- **Confidence:** LOW

### recoveredtoremotestabilizationrate (missing_parameters)
- **Source:** inference
- **Value:** 0.2 per year
- **Confidence:** LOW

### relapsetosmearpositiverate (missing_parameters)
- **Source:** inference
- **Value:** 0.05 per year
- **Confidence:** LOW

### relapsetosmearnegativerate (missing_parameters)
- **Source:** inference
- **Value:** 0.0005 per day
- **Confidence:** LOW

### relapsetoextrapulmonaryrate (missing_parameters)
- **Source:** inference
- **Value:** 0.01 per year
- **Confidence:** LOW

### Uninfected->RecentTBInfection (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** The acquisition of new

### RecentTBInfection->RemoteTBInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The natural progression of a recently acquired latent tuberculosis infection to a more established, remote latent state over time.
- **Reasoning:** Compartmental models of TB epidemics, using ordinary differential equations, inherently include the aging of latent infections from a recent to a remote state as part of the

### RecentTBInfection->ActiveTBSmearPositive (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression from recent

### RecentTBInfection->ActiveTBSmearNegative (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression from

### RecentTBInfection->ActiveTBExtrapulmonary (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of individuals with recent Mycobacterium tuberculosis infection to active extrapulmonary tuberculosis disease.
- **Reasoning:** This transition represents the biological progression of an infected individual to active disease, which occurs at an intrinsic rate rather than through contact transmission.

### RemoteTBInfection->RecentTBInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** This flow represents the reactivation

### RemoteTBInfection->ActiveTBSmearPositive (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The reactivation of a remote

### RemoteTBInfection->ActiveTBSmearNegative (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Reactivation of a remote

### RemoteTBInfection->ActiveTBExtrapulmonary (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an existing

### ActiveTBSmearPositive->RecentlyTreatedRecovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with active, smear

### ActiveTBSmearNegative->RecentlyTreatedRecovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with smear-

### ActiveTBExtrapulmonary->RecentlyTreatedRecovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with active

### RecentlyTreatedRecovered->RemoteTBInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have recently recovered

### RecentlyTreatedRecovered->ActiveTBSmearPositive (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who

### RecentlyTreatedRecovered->ActiveTBSmearNegative (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have recently recovered

### RecentlyTreatedRecovered->ActiveTBExtrapulmonary (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which individuals who have recently recovered from tuberculosis after treatment experience a relapse or reactivation of their infection, leading to the development of active extrapulmonary tuberculosis.
- **Reasoning:** This flow represents a biological progression where individuals previously treated and recovered from TB develop a new episode of active extrapulmonary TB due to relapse or

### recenttbinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Recent Latent TB

### remotetbinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Latent Infection
- **Reasoning:** The

### activetbsmearpositive (missing_compartments)
- **Source:** inference
- **Primary name:** Active TB Smear Positive

### activetbsmearnegative (missing_compartments)
- **Source:** inference
- **Primary name:** Active TB Smear-Negative

### activetbextrapulmonary (missing_compartments)
- **Source:** inference
- **Primary name:** Active TB Extrapulmonary

### recentlytreatedrecovered (missing_compartments)
- **Source:** inference
- **Primary name:** Recently Treated Recovered

### Uninfected->RecentTBInfection (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Individuals who are uninfected

### RecentTBInfection->RemoteTBInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The natural progression of a recently acquired tuberculosis infection to a long-term, established latent tuberculosis infection.
- **Reasoning:** This transition represents the maturation of an infection within an individual over time, independent of external contact, and is therefore best modeled as a rate flow.

### RecentTBInfection->ActiveTBSmearPositive (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression

### RecentTBInfection->ActiveTBSmearNegative (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with recent TB infection progress to active, smear-negative tuberculosis disease.

### RecentTBInfection->ActiveTBExtrapulmonary (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with a recent

### RemoteTBInfection->RecentTBInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** This transition is biologically impl

### RemoteTBInfection->ActiveTBSmearPositive (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression

### RemoteTBInfection->ActiveTBSmearNegative (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of individuals with remote (latent) tuberculosis infection to active, smear-negative tuberculosis disease.
- **Reasoning:** The transition from a latent infection state to active disease is an internal progression within an individual, typically modeled as a rate, rather than a contact-dependent transmission event between individuals.

### RemoteTBInfection->ActiveTBExtrapulmonary (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression

### ActiveTBSmearPositive->RecentlyTreatedRecovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with active, smear-positive tuberculosis successfully complete treatment and recover, transitioning to a recently treated and recovered state.
- **Reasoning:** This transition represents the successful outcome of treatment for active TB cases, which is a rate-dependent process of recovery rather than a contact-dependent transmission event.

### ActiveTBSmearNegative->RecentlyTreatedRecovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Successful completion of anti-

### ActiveTBExtrapulmonary->RecentlyTreatedRecovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with active extrapulmonary tuberculosis successfully complete treatment and recover, transitioning into a state of recent treatment and recovery.
- **Reasoning:** This transition represents the successful outcome of treatment for extrapulmonary TB, moving individuals from an active disease state to a recovered state, which is typically modeled as a rate dependent on treatment efficacy and duration.

### RecentlyTreatedRecovered->RemoteTBInfection (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Individuals who have recently

### RecentlyTreatedRecovered->ActiveTBSmearPositive (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Relapse

### RecentlyTreatedRecovered->ActiveTBSmearNegative (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have recently recovered from tuberculosis treatment experience a relapse of the disease, returning to an active smear-negative TB state.
- **Reasoning:** Relapse of tuberculosis after treatment

### RecentlyTreatedRecovered->ActiveTBExtrapulmonary (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have recently recovered from tuberculosis treatment can experience a relapse or reactivation of their infection, leading to a new episode of active extrapulmonary tuberculosis.
- **Reasoning:** This flow represents the reactivation or relapse of TB disease in individuals previously treated and recovered, leading to a new active extrapulmonary infection, which is a rate-dependent internal progression within the host.

## 6. Fill validation (vs gold standard)
- Parameters compared: **18**
- Exact match (<1% error): **2**
- Close (<10% error): **1**
- Approximate (<50% error): **1**
- Poor (>50% error): **14**
- **Accuracy (exact+close)**: **16.7%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| infectionratefromsmearpositive | 1e-06 | 0.3 | 100.0% | poor |
| infectionratefromsmearnegative | 0.15 | 0.066 | 127.27% | poor |
| recenttoremotestabilizationrate | 0.5 | 0.5 | 0.0% | exact |
| rapidprogressiontosmearpositiverate | 0.05 | 0.0455 | 9.89% | close |
| rapidprogressiontosmearnegativerate | 6.0 | 0.0245 | 24389.8% | poor |
| rapidprogressiontoextrapulmonaryrate | 0.05 | 0.0105 | 376.19% | poor |
| reinfectionratefromsmearpositive | 0.005 | 0.18 | 97.22% | poor |
| reinfectionratefromsmearnegative | 0.005 | 0.04 | 87.5% | poor |
| remotereactivationtosmearpositiverate | 0.0007 | 0.000325 | 115.38% | poor |
| remotereactivationtosmearnegativerate | 0.0005 | 0.000175 | 185.71% | poor |
| remotereactivationtoextrapulmonaryrate | 0.0003 | 0.0001 | 200.0% | poor |
| treatmentorselfcureratesmearpositive | 1.5 | 0.95 | 57.89% | poor |
| treatmentorselfcureratesmearnegative | 0.5 | 0.43 | 16.28% | approximate |
| treatmentorselfcurerateextrapulmonary | 1.5 | 0.77 | 94.81% | poor |
| recoveredtoremotestabilizationrate | 0.2 | 0.2 | 0.0% | exact |
| relapsetosmearpositiverate | 0.05 | 0.012 | 316.67% | poor |
| relapsetosmearnegativerate | 0.0005 | 0.008 | 93.75% | poor |
| relapsetoextrapulmonaryrate | 0.01 | 0.004 | 150.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **12**
- Precision **0.5833** | Recall **0.8571** | F1 **0.6942**
### Flows
- Gold count: **16** | Candidate: **26**
- Precision **0.3846** | Recall **0.625** | F1 **0.4762**
