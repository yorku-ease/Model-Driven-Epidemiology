# Phase 3 Gap Analysis Report

**Disease / context:** ebola

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **4**
- Missing compartments: 2
- Missing parameters: 2
- Missing stratifications: 0
- Missing interventions: 0
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 0

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **exposed** — Present in gold standard but not in extracted model. (severity: high)
- **infectiousdeceased** — Present in gold standard but not in extracted model. (severity: high)

## 4. Missing parameters
- **μ** — Present in gold standard but not in extracted model.
- **λ** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: non-symptomatic infected

## 5. Gap filling results
- Filled via **RAG**: 2
- Filled via **inference**: 0
- **Flagged** for manual review: 2

### μ (missing_parameters)
- **Source:** rag
- **Value:** 0.00038461 1/week
- **Description:** Background death rate (1/50 years = 1/(50×52) weeks)
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_hiv_llm_claude_20260218_171956, p2_ebola_llm_claude_20260212_211623

### λ (missing_parameters)
- **Source:** rag
- **Value:** 0.00005 per day
- **Description:** Human population growth rate
- **From papers:** p2_ebola_llm_gemini_20260211_201558, p2_dengue_llm_claude_20260212_211544, p2_hiv_llm_gemini_20260218_172812

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
| μ | 0.00038461 | 0.00038461 | 0.0% | exact |
| λ | 5e-05 | 0.38461 | 99.99% | poor |
