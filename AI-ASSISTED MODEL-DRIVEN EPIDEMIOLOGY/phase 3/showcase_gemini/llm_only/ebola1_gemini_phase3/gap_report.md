# Phase 3 Gap Analysis Report

**Disease / context:** ebola1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **12**
- Missing compartments: 1
- Missing parameters: 6
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 5
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 9
- Extra flows (not in gold standard): 1

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **12**
- After fills gaps (re-detected): **5**
- Delta (before - after): **7**
- Delta missing parameters: **6**
- Delta missing compartments: **0**
- Delta missing flows: **1**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **environment pathogens** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Infectious->Recovered** — Present in gold standard but not in extracted model (or flows list).
- **Infectious->Deceased** — Present in gold standard but not in extracted model (or flows list).
- **Infectious->Environment pathogens** — Present in gold standard but not in extracted model (or flows list).
- **Quarantine->Deceased** — Present in gold standard but not in extracted model (or flows list).
- **Deceased->Environment pathogens** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **μ** — Present in gold standard but not in extracted model.
- **ω2** — Present in gold standard but not in extracted model.
- **ω3** — Present in gold standard but not in extracted model.
- **ω1** — Present in gold standard but not in extracted model.
- **ω1q** — Present in gold standard but not in extracted model.
- **η** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: ebola virus pathogens in environment
- Extra parameters: α1, α2, ψ1, ψ2, ψ3, ε, w1, w2, c

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 58.3% | 30% |
| **Reference agreement** | 61.5% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **56.0/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 6 | 5 | 12 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 9 | 1 | 11 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 17
- **Flagged** for manual review: 0

### environment pathogens (missing_compartments)
- **Source:** inference
- **Primary name:** Environmental Pathogens

### Infectious->Deceased (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Rate of deaths of human individuals due

### Infectious->Environment pathogens (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Shedding of Ebola virus pathogens from infected humans into the environment through urine and faeces.
- **Reasoning:** The paper excerpt explicitly states that 'The disease can enter the environment through the urine and faeces of infected or deceased individuals', indicating a rate of pathogen shedding from infectious individuals to the environment.

### Quarantine->Deceased (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which individuals

### Deceased->Environment pathogens (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **7**
- Precision **0.5714** | Recall **0.6667** | F1 **0.6154**
### Flows
- Gold count: **8** | Candidate: **5**
- Precision **0.8** | Recall **0.5** | F1 **0.6154**
