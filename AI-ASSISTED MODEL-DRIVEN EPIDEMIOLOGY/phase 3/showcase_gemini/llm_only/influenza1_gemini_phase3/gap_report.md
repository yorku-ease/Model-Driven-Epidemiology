# Phase 3 Gap Analysis Report

**Disease / context:** influenza1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **1**
- Missing compartments: 0
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 1
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 6
- Extra flows (not in gold standard): 0

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **1**
- After fills gaps (re-detected): **1**
- Delta (before - after): **0**
- Delta missing parameters: **0**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- **Infectious->Removed** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: π, λ0, ψ, p_icu, µ_icu|e, n

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 0.0% | 30% |
| **Reference agreement** | 83.9% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **45.2/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 0 | 1 | 1 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 6 | 0 | 6 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 2
- **Flagged** for manual review: 0

### Infectious->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Infectious individuals either recover from the disease, potentially gaining immunity, or die due to the disease, thus being removed from the pool of infectious individuals.
- **Reasoning:** The transition from Infectious to Removed (recovery or death) is an intrinsic process for an infected individual, occurring at a specific rate per individual, independent of contact with others.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **4** | Candidate: **6**
- Precision **0.8333** | Recall **0.75** | F1 **0.7895**
### Flows
- Gold count: **5** | Candidate: **4**
- Precision **1.0** | Recall **0.8** | F1 **0.8889**
