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
| **Reference agreement** | 96.7% | 30% |
| **Fill traceability** | 66.7% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **84.8/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 7 | 3 | 12 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 8 | 3 | 12 |

## 5. Gap filling results
- Filled via **RAG**: 12
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 3
- **Flagged** for manual review: 0

### vaccinefailure (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccine Failure

### Susceptible->VaccineFailure (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### VaccineFailure->Latent (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **7**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **7** | Candidate: **8**
- Precision **0.875** | Recall **1.0** | F1 **0.9333**
