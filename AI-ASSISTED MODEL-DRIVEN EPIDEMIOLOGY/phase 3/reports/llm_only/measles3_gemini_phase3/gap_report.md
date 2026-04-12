# Phase 3 Gap Analysis Report

**Disease / context:** measles3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **12**
- Missing compartments: 2
- Missing parameters: 7
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 3
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 8
- Extra flows (not in gold standard): 3

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **12**
- After fills gaps (re-detected): **3**
- Delta (before - after): **9**
- Delta missing parameters: **7**
- Delta missing compartments: **1**
- Delta missing flows: **1**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **maternalimmunity** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinefailure** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **MaternalImmunity->Susceptible** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->VaccineFailure** — Present in gold standard but not in extracted model (or flows list).
- **VaccineFailure->Latent** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **maternalimmunitylossrate** — Present in gold standard but not in extracted model.
- **susceptibleinfectionrate** — Present in gold standard but not in extracted model.
- **effectivevaccinationrate** — Present in gold standard but not in extracted model.
- **vaccinefailurerate** — Present in gold standard but not in extracted model.
- **vaccinefailureinfectionrate** — Present in gold standard but not in extracted model.
- **latentprogressionrate** — Present in gold standard but not in extracted model.
- **recoveryrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: vaccine failure
- Extra parameters: force of infection, rate of progression from latency, rate of recovery from infection, rate of loss of passive immunity, vaccine efficacy, birth rate, background death rate, contact rates

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 75.0% | 30% |
| **Reference agreement** | 93.3% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **60.5/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 7 | 3 | 12 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 8 | 3 | 12 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 15
- **Flagged** for manual review: 0

### maternalimmunity (missing_compartments)
- **Source:** inference
- **Primary name:** MaternalImmunity
- **Reasoning:** The text highlights the importance of seroepidemiological studies in profiling 'passive immunity', which for measles, primarily refers to the temporary immunity passed from mother to infant.

### vaccinefailure (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccine Failure

### maternalimmunitylossrate (missing_parameters)
- **Source:** inference
- **Value:** 1.6 per year
- **Confidence:** LOW

### susceptibleinfectionrate (missing_parameters)
- **Source:** inference
- **Value:** 1095.0 per year
- **Confidence:** LOW

### effectivevaccinationrate (missing_parameters)
- **Source:** inference
- **Value:** 0.0025 per day
- **Confidence:** LOW

### vaccinefailurerate (missing_parameters)
- **Source:** inference
- **Value:** 0.03 
- **Confidence:** LOW

### vaccinefailureinfectionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.03 
- **Confidence:** LOW

### latentprogressionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.111 days^-1
- **Confidence:** LOW

### recoveryrate (missing_parameters)
- **Source:** inference
- **Value:** 0.125 per day
- **Confidence:** LOW

### MaternalImmunity->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The waning of passive maternal antibodies in infants, leading to a loss of temporary immunity and a return to the susceptible state.
- **Reasoning:** Maternal immunity is a temporary form of passive immunity that naturally wanes over time at a specific rate, making the individual susceptible to infection.

### Susceptible->VaccineFailure (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who are

### VaccineFailure->Latent (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals

### vaccinefailure (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccine-Failed

### Susceptible->VaccineFailure (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible individuals who receive

### VaccineFailure->Latent (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Individuals who have experienced vaccine failure (i.e., are susceptible despite vaccination or due to waning vaccine immunity) become

## 6. Fill validation (vs gold standard)
- Parameters compared: **7**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **1**
- Poor (>50% error): **6**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **98.61%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| maternalimmunitylossrate | 1.6 | 4.0 | 60.0% | poor |
| susceptibleinfectionrate | 1095.0 | 0.52 | 210476.92% | poor |
| effectivevaccinationrate | 0.0025 | 0.18 | 98.61% | poor |
| vaccinefailurerate | 0.03 | 0.02 | 50.0% | approximate |
| vaccinefailureinfectionrate | 0.03 | 0.4 | 92.5% | poor |
| latentprogressionrate | 0.111 | 52.14 | 99.79% | poor |
| recoveryrate | 0.125 | 52.14 | 99.76% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **8**
- Precision **0.875** | Recall **1.0** | F1 **0.9333**
### Flows
- Gold count: **7** | Candidate: **8**
- Precision **0.875** | Recall **1.0** | F1 **0.9333**
