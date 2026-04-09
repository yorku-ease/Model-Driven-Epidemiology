# Phase 3 Gap Analysis Report

**Disease / context:** cholera3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **2**
- Missing compartments: 1
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 1
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 0
- Extra flows (not in gold standard): 3

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **2**
- After fills gaps (re-detected): **1**
- Delta (before - after): **1**
- Delta missing parameters: **0**
- Delta missing compartments: **1**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **aquatic toxigenic vibrio cholerae** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Infected->Aquatic toxigenic Vibrio cholerae** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: aquatic reservoir of vibrio cholerae

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 50.0% | 30% |
| **Reference agreement** | 28.6% | 30% |
| **Fill traceability** | 100.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **63.6/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 0 | 1 | 2 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 0 | 3 | 4 |

## 5. Gap filling results
- Filled via **RAG**: 2
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 1

### Infected->Aquatic toxigenic Vibrio cholerae (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **3** | Candidate: **4**
- Precision **0.5** | Recall **0.6667** | F1 **0.5714**
### Flows
- Gold count: **2** | Candidate: **4**
- Precision **0.0** | Recall **0.0** | F1 **0.0**
