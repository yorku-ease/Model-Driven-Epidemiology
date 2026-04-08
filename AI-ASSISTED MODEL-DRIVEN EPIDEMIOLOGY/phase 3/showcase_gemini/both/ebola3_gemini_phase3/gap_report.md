# Phase 3 Gap Analysis Report

**Disease / context:** ebola3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **6**
- Missing compartments: 0
- Missing parameters: 1
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 5
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 16
- Extra flows (not in gold standard): 2

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **6**
- After fills gaps (re-detected): **3**
- Delta (before - after): **3**
- Delta missing parameters: **1**
- Delta missing compartments: **0**
- Delta missing flows: **2**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- **InfectiousCommunity->Removed** — Present in gold standard but not in extracted model (or flows list).
- **InfectiousCommunity->FuneralInfectious** — Present in gold standard but not in extracted model (or flows list).
- **Hospitalized->FuneralInfectious** — Present in gold standard but not in extracted model (or flows list).
- **Hospitalized->Removed** — Present in gold standard but not in extracted model (or flows list).
- **FuneralInfectious->Removed** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **burialrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: dead but not yet buried
- Extra parameters: beta_i, beta_h, beta_f, alpha, 1/c_h, 1/c_d, 1/c_i, 1/c_f, h_1, d_1, d_2, r_0, r_0i, r_0h, r_0f, 1-z

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 50.0% | 30% |
| **Reference agreement** | 70.4% | 30% |
| **Fill traceability** | 100.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **76.1/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 1 | 5 | 6 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 16 | 2 | 19 |

## 5. Gap filling results
- Filled via **RAG**: 9
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 0

### InfectiousCommunity->Removed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Hospitalized->Removed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### FuneralInfectious->Removed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **6**
- Precision **0.6667** | Recall **0.8333** | F1 **0.7407**
### Flows
- Gold count: **8** | Candidate: **7**
- Precision **0.7143** | Recall **0.625** | F1 **0.6667**
