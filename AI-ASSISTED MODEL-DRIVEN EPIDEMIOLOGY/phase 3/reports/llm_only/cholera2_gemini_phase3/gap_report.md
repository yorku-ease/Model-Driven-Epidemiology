# Phase 3 Gap Analysis Report

**Disease / context:** cholera2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **45**
- Missing compartments: 5
- Missing parameters: 24
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 16
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 9
- Extra flows (not in gold standard): 0

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **45**
- After fills gaps (re-detected): **9**
- Delta (before - after): **36**
- Delta missing parameters: **24**
- Delta missing compartments: **4**
- Delta missing flows: **8**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **vaccinated adults (two doses)** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated children under 5 (two doses)** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated adults (one dose)** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated children under 5 (one dose)** — Present in gold standard but not in extracted model. (severity: high)
- **environmental vibrio cholerae reservoir** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible adults->Vaccinated adults (two doses)** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible adults->Vaccinated adults (one dose)** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible children under 5->Vaccinated children under 5 (two doses)** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible children under 5->Vaccinated children under 5 (one dose)** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (two doses)->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (two doses)->Susceptible adults** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated children under 5 (two doses)->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated children under 5 (two doses)->Susceptible children under 5** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (one dose)->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (one dose)->Susceptible adults** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated children under 5 (one dose)->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated children under 5 (one dose)->Susceptible children under 5** — Present in gold standard but not in extracted model (or flows list).
- **Exposed / recently infected->Symptomatic infectious** — Present in gold standard but not in extracted model (or flows list).
- **Exposed / recently infected->Asymptomatic infectious** — Present in gold standard but not in extracted model (or flows list).
- **Symptomatic infectious->Environmental Vibrio cholerae reservoir** — Present in gold standard but not in extracted model (or flows list).
- **Asymptomatic infectious->Environmental Vibrio cholerae reservoir** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **βa** — Present in gold standard but not in extracted model.
- **βc** — Present in gold standard but not in extracted model.
- **βa_2dose** — Present in gold standard but not in extracted model.
- **βc_2dose** — Present in gold standard but not in extracted model.
- **βa_1dose** — Present in gold standard but not in extracted model.
- **βc_1dose** — Present in gold standard but not in extracted model.
- **σ*k** — Present in gold standard but not in extracted model.
- **(1-σ)*k** — Present in gold standard but not in extracted model.
- **γ** — Present in gold standard but not in extracted model.
- **ν2** — Present in gold standard but not in extracted model.
- **ν1** — Present in gold standard but not in extracted model.
- **ω2** — Present in gold standard but not in extracted model.
- **ω1** — Present in gold standard but not in extracted model.
- **ωr** — Present in gold standard but not in extracted model.
- **ξs** — Present in gold standard but not in extracted model.
- **ξa** — Present in gold standard but not in extracted model.
- **λa** — Present in gold standard but not in extracted model.
- **λc** — Present in gold standard but not in extracted model.
- **δ** — Present in gold standard but not in extracted model.
- **μb** — Present in gold standard but not in extracted model.
- **ve2a** — Present in gold standard but not in extracted model.
- **ve2c** — Present in gold standard but not in extracted model.
- **ve1a** — Present in gold standard but not in extracted model.
- **ve1c** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: initial vaccine protection, vaccine protection duration, single-dose protection duration, baseline two-dose vaccine coverage, baseline one-dose vaccine coverage, no vaccine coverage, high-coverage two-dose vaccine coverage, high-coverage one-dose vaccine coverage, high-coverage no vaccine coverage

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 80.0% | 30% |
| **Reference agreement** | 82.4% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **58.7/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 5 | 24 | 16 | 45 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 9 | 0 | 9 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 54
- **Flagged** for manual review: 0

### vaccinated adults (two doses) (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Adults (Two Doses)

### vaccinated children under 5 (two doses) (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Children Under 5

### vaccinated adults (one dose) (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Individuals
- **Reasoning:** The

### vaccinated children under 5 (one dose) (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Children Under 5 (

### environmental vibrio cholerae reservoir (missing_compartments)
- **Source:** inference
- **Primary name:** Environmental Vibrio Cholerae
- **Reasoning:** The excerpt emphasizes inadequate water and sanitation infrastructure, which is critical for the persistence and transmission of *Vibrio cholerae* in the environment, forming a reservoir for human infection.

### βa (missing_parameters)
- **Source:** inference
- **Value:** 0.3 per day
- **Confidence:** LOW

### βc (missing_parameters)
- **Source:** inference
- **Value:** 0.5 per day
- **Confidence:** LOW

### βa_2dose (missing_parameters)
- **Source:** inference
- **Value:** 0.5 1/day
- **Confidence:** LOW

### βc_2dose (missing_parameters)
- **Source:** inference
- **Value:** 0.3 per day
- **Confidence:** LOW

### βa_1dose (missing_parameters)
- **Source:** inference
- **Value:** 0.2 1/day
- **Confidence:** LOW

### βc_1dose (missing_parameters)
- **Source:** inference
- **Value:** 0.2 per day
- **Confidence:** LOW

### σ*k (missing_parameters)
- **Source:** inference
- **Value:** 0.5 days^-1
- **Confidence:** LOW

### (1-σ)*k (missing_parameters)
- **Source:** inference
- **Value:** 0.07 per day
- **Confidence:** LOW

### γ (missing_parameters)
- **Source:** inference
- **Value:** 0.2 per day
- **Confidence:** LOW

### ν2 (missing_parameters)
- **Source:** inference
- **Value:** 0.000913 per day
- **Confidence:** LOW

### ν1 (missing_parameters)
- **Source:** inference
- **Value:** 1e-05 per day
- **Confidence:** LOW

### ω2 (missing_parameters)
- **Source:** inference
- **Value:** 0.00137 per
- **Confidence:** LOW

### ω1 (missing_parameters)
- **Source:** inference
- **Value:** 0.00091 per day
- **Confidence:** LOW

### ωr (missing_parameters)
- **Source:** inference
- **Value:** 0.00091 per day
- **Confidence:** LOW

### ξs (missing_parameters)
- **Source:** inference
- **Value:** 0.65 
- **Confidence:** LOW

### ξa (missing_parameters)
- **Source:** inference
- **Value:** 0.65 
- **Confidence:** LOW

### λa (missing_parameters)
- **Source:** inference
- **Value:** 0.05 1/day
- **Confidence:** LOW

### λc (missing_parameters)
- **Source:** inference
- **Value:** 0.1 per day
- **Confidence:** LOW

### δ (missing_parameters)
- **Source:** inference
- **Value:** 3.65e-05 per day
- **Reasoning:** In compartmental models, δ commonly represents the natural death rate of humans. A typical human lifespan of 70-80 years translates to a daily death rate of approximately 1/(75*365) per day.
- **Confidence:** LOW

### μb (missing_parameters)
- **Source:** inference
- **Value:** 6.85e-05 day^-
- **Confidence:** LOW

### ve2a (missing_parameters)
- **Source:** inference
- **Value:** 0.5 
- **Reasoning:** Oral
- **Confidence:** LOW

### ve2c (missing_parameters)
- **Source:** inference
- **Value:** 65 percent
- **Reasoning:** Oral cholera vaccines (OCVs) typically demonstrate moderate to good efficacy, often ranging from 50% to 85% in endemic settings, making 65% a plausible mid-range estimate for a default.
- **Confidence:** LOW

### ve1a (missing_parameters)
- **Source:** inference
- **Value:** 0.6 proportion
- **Confidence:** LOW

### ve1c (missing_parameters)
- **Source:** inference
- **Value:** 0.7 
- **Reasoning:** Oral cholera vaccines typically offer moderate to high protection, with efficacy often ranging from 60-85% for two doses in field settings. A value of 0.70 represents a plausible mid-range efficacy for a standard vaccine regimen.
- **Confidence:** LOW

### Susceptible adults->Vaccinated adults (two doses) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible adults receive two doses of the cholera vaccine and transition to the vaccinated compartment.
- **Reasoning:** The paper discusses the use of vaccine to eliminate cholera, indicating a process where susceptible individuals become vaccinated.

### Susceptible adults->Vaccinated adults (one dose) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible adults

### Susceptible children under 5->Vaccinated children under 5 (two doses) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible children under 5 years old receive two doses of the cholera vaccine and transition to the vaccinated compartment.
- **Reasoning:** Vaccination is an intervention applied to individuals, typically modeled as a rate at which susceptible individuals become vaccinated, independent of contact with infected individuals.

### Susceptible children under 5->Vaccinated children under 5 (one dose) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible children under 5 receiving their first dose of the cholera vaccine.
- **Reasoning:** This flow represents the administration of the first dose of the cholera vaccine to susceptible children, moving them into a vaccinated compartment at a certain rate.

### Vaccinated adults (two doses)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Vaccinated individuals, despite having received two doses, become exposed to and subsequently infected with cholera due to imperfect vaccine efficacy or waning immunity.
- **Reasoning:** Cholera vaccines do not provide 100% protection, and their efficacy can wane, allowing vaccinated individuals to still acquire the infection at a certain rate.

### Vaccinated adults (two doses)->Susceptible adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of

### Vaccinated children under 5 (two doses)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated children under

### Vaccinated children under 5 (two doses)->Susceptible children under 5 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Loss of vaccine-induced immunity over time in children under 5 who received two doses, rendering them susceptible to cholera again.
- **Reasoning:** Vaccine-induced immunity, even after two doses, can wane over time, causing individuals to revert from a protected state to a susceptible state at a specific rate.

### Vaccinated adults (one dose)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals can

### Vaccinated adults (one dose)->Susceptible adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of vaccine-induced immunity in adults who received one dose, causing them to revert to a susceptible state.
- **Reasoning:** Vaccine-induced immunity is not lifelong and can wane over time, making individuals susceptible again without requiring contact.

### Vaccinated children under 5 (one dose)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** The rate

### Vaccinated children under 5 (one dose)->Susceptible children under 5 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of vaccine-induced immunity in children under 5 who received one dose, causing them to revert to a susceptible state.
- **Reasoning:** This transition represents the loss of vaccine protection over time, which is a natural process occurring at a specific rate rather than through contact with other individuals.

### Exposed / recently infected->Symptomatic infectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** This flow represents the progression of an individual from the latent (exposed/incubating) phase to the symptomatic and infectious phase of cholera.
- **Reasoning:** The transition from an exposed but not yet symptomatic state to a symptomatic and infectious state is an intrinsic biological progression within an infected individual, occurring at a specific rate.

### Exposed / recently infected->Asymptomatic infectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which exposed individuals progress to become asymptomatically infectious.
- **Reasoning:** This transition represents the progression of infected individuals from the latent (exposed) phase to an infectious but asymptomatic state, which is typically modeled as a rate based on the incubation period.

### Symptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Symptomatic infectious individuals shed Vib

### Asymptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Shedding of Vib

### vaccinated adults (one dose) (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Adults
- **Reasoning:** The

### Susceptible adults->Vaccinated adults (one dose) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible adults receive a single dose of the cholera vaccine and transition to the vaccinated compartment.
- **Reasoning:** Vaccination is a direct intervention applied to susceptible individuals, typically modeled as a rate at which individuals receive the vaccine.

### Vaccinated adults (two doses)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals can

### Vaccinated children under 5 (two doses)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Vaccinated children

### Vaccinated adults (one dose)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals, despite having received one dose, can still become exposed to thelera pathogen and subsequently infected, due to incomplete vaccine efficacy or waning immunity.
- **Reasoning:** Cholera vaccines, particularly a single dose, do not offer 100% protection, allowing vaccinated individuals to still become infected upon exposure to the pathogen.

### Vaccinated adults (one dose)->Susceptible adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of vaccine

### Vaccinated children under 5 (one dose)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated children under

### Exposed / recently infected->Symptomatic infectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which individuals who have been infected with cholera complete their incubation period and develop symptomatic disease, becoming infectious.
- **Reasoning:** This transition represents the progression of individuals from a latent (exposed) state to a symptomatic infectious state, which is a time-dependent process within the host, not a contact-dependent transmission event.

### Exposed / recently infected->Asymptomatic infectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which individuals who have been infected with cholera progress from the latent (exposed) stage to an asymptomatic but infectious stage.
- **Reasoning:** This transition represents the natural history of the disease within an individual, where exposed individuals become infectious without developing symptoms, driven by an intrinsic rate rather than external contact.

## 6. Fill validation (vs gold standard)
- Parameters compared: **24**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **6**
- Poor (>50% error): **18**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **80.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| βa | 0.3 | 0.35 | 14.29% | approximate |
| βc | 0.5 | 0.35 | 42.86% | approximate |
| βa_2dose | 0.5 | 1.0 | 50.0% | poor |
| βc_2dose | 0.3 | 1.0 | 70.0% | poor |
| βa_1dose | 0.2 | 1.0 | 80.0% | poor |
| βc_1dose | 0.2 | 1.0 | 80.0% | poor |
| σ*k | 0.5 | 0.14 | 257.14% | poor |
| (1-σ)*k | 0.07 | 0.21 | 66.67% | poor |
| γ | 0.2 | 0.5 | 60.0% | poor |
| ν2 | 0.000913 | 0.015 | 93.91% | poor |
| ν1 | 1e-05 | 0.002 | 99.5% | poor |
| ω2 | 0.00137 | 0.00385 | 64.42% | poor |
| ω1 | 0.00091 | 0.01923 | 95.27% | poor |
| ωr | 0.00091 | 0.01 | 90.9% | poor |
| ξs | 0.65 | 0.8 | 18.75% | approximate |
| ξa | 0.65 | 0.2 | 225.0% | poor |
| λa | 0.05 | 2500.0 | 100.0% | poor |
| λc | 0.1 | 2500.0 | 100.0% | poor |
| δ | 3.65e-05 | 0.002 | 98.17% | poor |
| μb | 6.85e-05 | 0.6 | 99.99% | poor |
| ve2a | 0.5 | 0.76 | 34.21% | approximate |
| ve2c | 65.0 | 0.469 | 13759.28% | poor |
| ve1a | 0.6 | 0.76 | 21.05% | approximate |
| ve1c | 0.7 | 0.469 | 49.25% | approximate |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **11** | Candidate: **7**
- Precision **0.8571** | Recall **0.9091** | F1 **0.8824**
### Flows
- Gold count: **21** | Candidate: **8**
- Precision **1.0** | Recall **0.619** | F1 **0.7647**
