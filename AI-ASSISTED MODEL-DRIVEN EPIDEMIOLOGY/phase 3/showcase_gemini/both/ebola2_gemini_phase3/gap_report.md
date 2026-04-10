# Phase 3 Gap Analysis Report

**Disease / context:** ebola2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **5**
- Missing compartments: 0
- Missing parameters: 5
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 0
- Extra compartments (not in gold standard): 2
- Extra parameters (not in gold standard): 8
- Extra flows (not in gold standard): 3

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **5**
- After fills gaps (re-detected): **0**
- Delta (before - after): **5**
- Delta missing parameters: **5**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- None

## 4. Missing parameters
- **hospitalizationrate** — Present in gold standard but not in extracted model.
- **recoveryrate** — Present in gold standard but not in extracted model.
- **deathratecommunity** — Present in gold standard but not in extracted model.
- **recoveryratehospital** — Present in gold standard but not in extracted model.
- **deathratehospital** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: cumulative cases, burials
- Extra parameters: θ, φ, γ, α, γh, δ, δh, κ

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 84.0% | 30% |
| **Fill traceability** | 100.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **75.2/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 5 | 0 | 5 |
| **Extra in model** | Model items not in reference (noise/convention) | 2 | 8 | 3 | 13 |

## 5. Gap filling results
- Filled via **RAG**: 5
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 0

### hospitalizationrate (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639

### recoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### deathratecommunity (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_gemini_20260407_211201, p2_cholera3_llm_claude_20260407_213639

### recoveryratehospital (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### deathratehospital (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

## 6. Fill validation (vs gold standard)
- Parameters compared: **5**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **5**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **6666566.67%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| hospitalizationrate | 10000.0 | 0.2 | 4999900.0% | poor |
| recoveryrate | 0.5 | 0.1 | 400.0% | poor |
| deathratecommunity | 10000.0 | 0.15 | 6666566.67% | poor |
| recoveryratehospital | 10000.0 | 0.1 | 9999900.0% | poor |
| deathratehospital | 10000.0 | 0.1 | 9999900.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **8**
- Precision **0.75** | Recall **1.0** | F1 **0.8571**
### Flows
- Gold count: **7** | Candidate: **10**
- Precision **0.7** | Recall **1.0** | F1 **0.8235**
