# Phase 3 Gap Analysis Report

**Disease / context:** malaria1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **7**
- Missing compartments: 0
- Missing parameters: 7
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 0
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 14
- Extra flows (not in gold standard): 2

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **7**
- After fills gaps (re-detected): **0**
- Delta (before - after): **7**
- Delta missing parameters: **7**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- None

## 4. Missing parameters
- **μ1** — Present in gold standard but not in extracted model.
- **λv^v** — Present in gold standard but not in extracted model.
- **λv^s** — Present in gold standard but not in extracted model.
- **λh^v** — Present in gold standard but not in extracted model.
- **λh^s** — Present in gold standard but not in extracted model.
- **λm** — Present in gold standard but not in extracted model.
- **μ2** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: π, µ1, µ2, θ, ε, ηv, ηs, ξv, ξs, ρ, φ, β1, β2, β3

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 95.8% | 30% |
| **Fill traceability** | 100.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **78.8/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 7 | 0 | 7 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 14 | 2 | 16 |

## 5. Gap filling results
- Filled via **RAG**: 7
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 0

### μ1 (missing_parameters)
- **Source:** rag
- **Value:** 0.018 /yr
- **Description:** Background mortality rate
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_gemini_20260407_211201, p2_hiv3_llm_gemini_20260407_205438

### λv^v (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_gemini_20260407_204240

### λv^s (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_gemini_20260407_204240

### λh^v (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_gemini_20260407_204240, p2_cholera3_llm_claude_20260407_213639

### λh^s (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_gemini_20260407_204240, p2_cholera3_llm_claude_20260407_213639

### λm (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_gemini_20260407_204240

### μ2 (missing_parameters)
- **Source:** rag
- **Value:** 0.018 /yr
- **Description:** Background mortality rate
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_gemini_20260407_211201, p2_hiv3_llm_gemini_20260407_205438

## 6. Fill validation (vs gold standard)
- Parameters compared: **7**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **7**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| μ1 | 0.018 | 0.001384 | 1200.58% | poor |
| λv^v | 0.5 | 0.0 | 100.0% | poor |
| λv^s | 0.5 | 0.0 | 100.0% | poor |
| λh^v | 10000.0 | 0.0 | 100.0% | poor |
| λh^s | 10000.0 | 0.0 | 100.0% | poor |
| λm | 0.5 | 0.0 | 100.0% | poor |
| μ2 | 0.018 | 0.1 | 82.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **10**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **11** | Candidate: **13**
- Precision **0.8462** | Recall **1.0** | F1 **0.9167**
