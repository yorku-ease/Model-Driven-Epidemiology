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
| **Fill traceability** | 75.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **72.5/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 4 | 5 | 5 | 14 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 13 | 2 | 15 |

## 5. Gap filling results
- Filled via **RAG**: 7
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 7
- **Flagged** for manual review: 0

### exposed (missing_compartments)
- **Source:** rag
- **Primary name:** exposed
- **Evidence chunks:** 3 snippet(s) in database
- *Use paper snippets to confirm compartment label and add to model.*

### vaccinated (missing_compartments)
- **Source:** rag
- **Primary name:** vaccinated
- **Evidence chunks:** 3 snippet(s) in database
- *Use paper snippets to confirm compartment label and add to model.*

### vaccinatedinfected (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Infected
- **Reasoning:** The text explicitly discusses 'vaccinees' who experience 'their first natural DENV infection,' indicating a state where vaccinated individuals can still become infected.

### vaccinatedrecovered (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Recovered

### transmissionrate (missing_parameters)
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

### screeningandvaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### vaccinebreakthroughrate (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639

### Susceptible->Exposed (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become exposed to the dengue virus, typically through the bite of an infected mosquito, transitioning into the exposed compartment.
- **Reasoning:** The text refers to 'first natural DENV infection' and 'prior DENV exposure,' indicating a transmission event that is contact-dependent (via vector) for a susceptible individual to become exposed.

### Susceptible->Vaccinated (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible individuals receive a dengue vaccine, transitioning them into a vaccinated state.
- **Reasoning:** Vaccination is an external public health intervention applied to susceptible individuals, not a result of direct contact with infected individuals or an intrinsic biological process related to the disease itself.

### Exposed->Infectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have been exposed to the dengue virus complete their intrinsic incubation period and become infectious.
- **Reasoning:** The progression from an exposed state to an infectious state is an intrinsic biological process within an individual, typically governed by an incubation period, making it a rate-dependent transition rather than one driven by contact.

### Vaccinated->VaccinatedInfected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals become infected with the

### VaccinatedInfected->VaccinatedRecovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovery of vaccinated individuals from dengue infection.
- **Reasoning:** Infected individuals, including those who are vaccinated, typically recover from dengue infection over time, which is a standard process in infectious disease models.

## 6. Fill validation (vs gold standard)
- Parameters compared: **5**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **5**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.96%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| transmissionrate | 0.0001 | 0.28 | 99.96% | poor |
| incubationrate | 0.0001 | 0.2 | 99.95% | poor |
| recoveryrate | 0.5 | 0.14 | 257.14% | poor |
| screeningandvaccinationrate | 0.0001 | 0.04 | 99.75% | poor |
| vaccinebreakthroughrate | 10000.0 | 0.05 | 19999900.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **7**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **6** | Candidate: **7**
- Precision **0.7143** | Recall **1.0** | F1 **0.8333**
