# Phase 3 Gap Analysis Report

**Disease / context:** zika1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **7**
- Missing compartments: 0
- Missing parameters: 7
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 0
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 10
- Extra flows (not in gold standard): 0

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **7**
- After fills gaps (re-detected): **2**
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
- **1/τ_h** — Present in gold standard but not in extracted model.
- **b_v** — Present in gold standard but not in extracted model.
- **1/τ_v** — Present in gold standard but not in extracted model.
- **r_h** — Present in gold standard but not in extracted model.
- **f** — Present in gold standard but not in extracted model.
- **δ_l** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: 1/α_h, 1/α_v, 1/γ, e_v, μ_v, q, k_h, k_v, κ, r0

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 71.4% | 30% |
| **Reference agreement** | 100.0% | 30% |
| **Fill traceability** | 77.8% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **67.0/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 7 | 0 | 7 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 10 | 0 | 10 |

## 5. Gap filling results
- Filled via **RAG**: 7
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 2

### 1/τ_h (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_openai_20260407_213203, p2_cholera3_llm_openai_20260407_211654

### b_v (missing_parameters)
- **Source:** flagged
- **Action:** manual_review — Could not fill parameters gap automatically.

### 1/τ_v (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_gemini_20260407_204240, p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_openai_20260407_213203

### r_h (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_dengue3_llm_openai_20260407_212001, p2_dengue3_llm_claude_20260407_214050, p2_cholera3_llm_gemini_20260407_204240

### f (missing_parameters)
- **Source:** rag
- **Value:** country-specific dimensionless
- **Description:** Population-averaged infection fatality ratio, adjusted for age structure and contact patterns of each country
- **From papers:** p2_covid3_llm_claude_20260407_213850, p2_covid3_llm_gemini_20260407_204521, p2_cholera3_llm_claude_20260407_213639

### δ_l (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### φ (missing_parameters)
- **Source:** rag
- **Value:** 0.40 dimensionless
- **Description:** Reduction in transmission from other interventions
- **From papers:** p2_dengue3_llm_claude_20260407_214050, p2_cholera3_llm_gemini_20260407_204240, p2_cholera3_llm_claude_20260407_213639

### b_v (missing_parameters)
- **Source:** flagged
- **Action:** manual_review — Could not fill parameters gap automatically.

### f (missing_parameters)
- **Source:** rag
- **Value:** country-specific dimensionless
- **Description:** Population-averaged infection fatality ratio, adjusted for age structure and contact patterns of each country
- **From papers:** p2_covid3_llm_claude_20260407_213850, p2_covid3_llm_gemini_20260407_204521, p2_cholera3_llm_claude_20260407_213639

## 6. Fill validation (vs gold standard)
- Parameters compared: **5**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **1**
- Poor (>50% error): **4**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **300.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| 1/τ_h | 10000.0 | 0.5 | 1999900.0% | poor |
| 1/τ_v | 0.5 | 0.125 | 300.0% | poor |
| r_h | 10000.0 | 5e-05 | 19999999900.0% | poor |
| f | country-specific | 80 | — | no_fill |
| δ_l | 0.5 | 1.0 | 50.0% | poor |
| φ | 0.4 | 0.68 | 41.18% | approximate |
| f | country-specific | 80 | — | no_fill |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **10**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **8** | Candidate: **8**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
