# Phase 3 Gap Analysis Report

**Disease / context:** ebola

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **6**
- Missing compartments: 2
- Missing parameters: 4
- Missing stratifications: 0
- Missing interventions: 0
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 2

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **exposed** — Present in gold standard but not in extracted model. (severity: high)
- **infectiousdeceased** — Present in gold standard but not in extracted model. (severity: high)

## 4. Missing parameters
- **c_i** — Present in gold standard but not in extracted model.
- **c_d** — Present in gold standard but not in extracted model.
- **μ** — Present in gold standard but not in extracted model.
- **λ** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: non-symptomatic infected
- Extra parameters: ci, cd

## 5. Gap filling results
- Filled via **RAG**: 4
- Filled via **inference**: 0
- **Flagged** for manual review: 2

### c_i (missing_parameters)
- **Source:** rag
- **Value:** variable people weeks^-1
- **Description:** Contact rate with infectious individuals (range 0–5)
- **From papers:** p2_ebola_llm_gemini_20260211_201558, p2_hiv_llm_claude_20260218_171956, p2_ebola_llm_claude_20260212_211623

### c_d (missing_parameters)
- **Source:** rag
- **Value:** variable people weeks^-1
- **Description:** Contact rate with infectious dead bodies (range 0–5)
- **From papers:** p2_cholera_llm_claude_20260212_211525, p2_ebola_llm_claude_20260212_211623, p2_ebola_llm_openai_20260218_174738

### μ (missing_parameters)
- **Source:** rag
- **Value:** 0.00038461 1/week
- **Description:** Background death rate (1/50 years = 1/(50×52) weeks)
- **From papers:** p2_tuberculosis_llm_gemini_20260211_202038, p1_model_malaria, p2_hiv_llm_claude_20260218_171956

### λ (missing_parameters)
- **Source:** rag
- **Value:** 0.00005 per day
- **Description:** Human population growth rate
- **From papers:** p2_hiv_llm_gemini_20260218_172812, p2_cholera_llm_claude_20260212_211525, p2_zika_llm_claude_20260212_212158

### exposed (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiousdeceased (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

## 6. Fill validation (vs gold standard)
- Parameters compared: **2**
- Exact match (<1% error): **1**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **1**
- **Accuracy (exact+close)**: **50.0%**
- Median relative error: **99.99%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| c_i | variable | 2.5 | — | no_fill |
| c_d | variable | 2.5 | — | no_fill |
| μ | 0.00038461 | 0.00038461 | 0.0% | exact |
| λ | 5e-05 | 0.38461 | 99.99% | poor |
