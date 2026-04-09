# Phase 3 Gap Analysis Report

**Disease / context:** dengue2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **14**
- Missing compartments: 4
- Missing parameters: 5
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 5
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 13
- Extra flows (not in gold standard): 2

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **14**
- After fills gaps (re-detected): **0**
- Delta (before - after): **14**
- Delta missing parameters: **5**
- Delta missing compartments: **4**
- Delta missing flows: **5**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **exposed** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinatedinfected** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinatedrecovered** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->Exposed** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->Vaccinated** — Present in gold standard but not in extracted model (or flows list).
- **Exposed->Infectious** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated->VaccinatedInfected** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedInfected->VaccinatedRecovered** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **transmissionrate** — Present in gold standard but not in extracted model.
- **incubationrate** — Present in gold standard but not in extracted model.
- **recoveryrate** — Present in gold standard but not in extracted model.
- **screeningandvaccinationrate** — Present in gold standard but not in extracted model.
- **vaccinebreakthroughrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: per-exposure protection from vaccination for seronegative vaccinees, per-exposure protection from vaccination for seropositive vaccinees, average duration of protection for seronegative vaccinees, average duration of protection for seropositive vaccinees, probability of symptoms conditional on infection (primary), probability of symptoms conditional on infection (secondary), probability of symptoms conditional on infection (post-secondary), probability of hospitalization conditional on symptoms (primary), probability of hospitalization conditional on symptoms (secondary), probability of hospitalization conditional on symptoms (post-secondary), probability of death conditional on symptomatic disease, probability of mosquito to human transmission, mosquito emergence rate

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 91.7% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 20.0% | 20% |
| **→ Composite** | **61.5/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 4 | 5 | 5 | 14 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 13 | 2 | 15 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 14
- **Flagged** for manual review: 0

### exposed (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed

### vaccinated (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated
- **Reasoning:** The excerpt extensively discusses dengue vaccines, vaccination strategies, and vaccinees, indicating that a compartment for vaccinated individuals is crucial for modeling the described dynamics.

### vaccinatedinfected (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Infected
- **Reasoning:** The

### vaccinatedrecovered (missing_compartments)
- **Source:** inference
- **Primary name:** VaccinatedRecovered

### transmissionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.2 per day
- **Confidence:** LOW

### incubationrate (missing_parameters)
- **Source:** inference
- **Value:** 0.167 1/days
- **Confidence:** LOW

### recoveryrate (missing_parameters)
- **Source:** inference
- **Value:** 0.14 per day
- **Confidence:** LOW

### screeningandvaccinationrate (missing_parameters)
- **Source:** inference
- **Value:** 0.1 per year
- **Confidence:** LOW

### vaccinebreakthroughrate (missing_parameters)
- **Source:** inference
- **Value:** 0.4 
- **Reasoning:** Deng
- **Confidence:** LOW

### Susceptible->Exposed (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become exposed

### Susceptible->Vaccinated (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible individuals receive a vaccine and transition to the Vaccinated compartment.
- **Reasoning:** Vaccination is an intervention applied to susceptible individuals, typically occurring at a specific rate independent of disease transmission dynamics, as discussed in the context of a vaccination strategy.

### Exposed->Infectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have been infected with the dengue virus complete their intrinsic incubation period and become capable of transmitting the virus to mosquitoes.
- **Reasoning:** The transition from the exposed (latent) state to the infectious state

### Vaccinated->VaccinatedInfected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals become infected

### VaccinatedInfected->VaccinatedRecovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Vaccinated individuals recovering from dengue infection.
- **Reasoning:** Recovery from an infection is a natural biological process that occurs over time, typically modeled as a rate

## 6. Fill validation (vs gold standard)
- Parameters compared: **5**
- Exact match (<1% error): **1**
- Close (<10% error): **0**
- Approximate (<50% error): **2**
- Poor (>50% error): **2**
- **Accuracy (exact+close)**: **20.0%**
- Median relative error: **28.57%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| transmissionrate | 0.2 | 0.28 | 28.57% | approximate |
| incubationrate | 0.167 | 0.2 | 16.5% | approximate |
| recoveryrate | 0.14 | 0.14 | 0.0% | exact |
| screeningandvaccinationrate | 0.1 | 0.04 | 150.0% | poor |
| vaccinebreakthroughrate | 0.4 | 0.05 | 700.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **7**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **6** | Candidate: **7**
- Precision **0.7143** | Recall **1.0** | F1 **0.8333**
