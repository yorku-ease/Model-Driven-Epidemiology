# Phase 3 Gap Analysis Report

**Disease / context:** covid2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **1**
- Missing compartments: 0
- Missing parameters: 1
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 0

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **1**
- After fills gaps (re-detected): **0**
- Delta (before - after): **1**
- Delta missing parameters: **1**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- None

## 4. Missing parameters
- **initialseed** — Present in gold standard but not in extracted model.

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 100.0% | 30% |
| **Fill traceability** | 100.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **80.0/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 1 | 0 | 1 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 0 | 0 | 0 |

## 5. Gap filling results
- Filled via **RAG**: 1
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 0

### initialseed (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

## 6. Fill validation (vs gold standard)
- Parameters compared: **1**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **1**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| initialseed | 0.0001 | 5.0 | 100.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **4** | Candidate: **4**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **3** | Candidate: **3**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
