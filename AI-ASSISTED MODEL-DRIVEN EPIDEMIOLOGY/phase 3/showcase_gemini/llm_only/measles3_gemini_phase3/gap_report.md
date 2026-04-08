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
- After fills gaps (re-detected): **8**
- Delta (before - after): **4**
- Delta missing parameters: **2**
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
| **Gap reduction** | 33.3% | 30% |
| **Reference agreement** | 96.7% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **59.0/100** | — |

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
- Filled via **inference**: 20
- **Flagged** for manual review: 0

### vaccinefailure (missing_compartments)
- **Source:** inference
- **Primary name:** vaccinefailure
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### susceptibleinfectionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### effectivevaccinationrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### vaccinefailurerate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### vaccinefailureinfectionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### latentprogressionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### Susceptible->VaccineFailure (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for Susceptible->VaccineFailure
- **Reasoning:** LLM unavailable.

### VaccineFailure->Latent (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for VaccineFailure->Latent
- **Reasoning:** LLM unavailable.

## 6. Fill validation (vs gold standard)
- Parameters compared: **0**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| susceptibleinfectionrate | None | 0.52 | — | no_fill |
| effectivevaccinationrate | None | 0.18 | — | no_fill |
| vaccinefailurerate | None | 0.02 | — | no_fill |
| vaccinefailureinfectionrate | None | 0.40 | — | no_fill |
| latentprogressionrate | None | 52.14 | — | no_fill |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **7**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **7** | Candidate: **8**
- Precision **0.875** | Recall **1.0** | F1 **0.9333**
