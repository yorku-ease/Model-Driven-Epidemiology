# Phase 3 Gap Analysis Report

**Disease / context:** measles

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **3**
- Missing compartments: 0
- Missing parameters: 3
- Missing stratifications: 0
- Missing interventions: 0
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 7

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- None

## 4. Missing parameters
- **ξu** — Present in gold standard but not in extracted model.
- **ξm** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: adult vaccination group
- Extra parameters: x_u, x_a, b_c, b_a, j, h_a, h_u

## 5. Gap filling results
- Filled via **RAG**: 2
- Filled via **inference**: 1
- **Flagged** for manual review: 0

### ξu (missing_parameters)
- **Source:** rag
- **Value:** 0.01 to 10 cells mL^-1 person^-1 day^-1
- **Description:** Rate of water contamination by humans, i.e. rate of increase in V. cholerae concentration in the water reservoir
- **From papers:** p2_cholera_llm_gemini_20260211_201419, p2_cholera_llm_claude_20260212_211525, p2_cholera_llm_openai_20260218_174559

### ξm (missing_parameters)
- **Source:** rag
- **Value:** 0.01 to 10 cells mL^-1 person^-1 day^-1
- **Description:** Rate of water contamination by humans, i.e. rate of increase in V. cholerae concentration in the water reservoir
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_cholera_llm_openai_20260218_174559, p2_cholera_llm_gemini_20260211_201419

### φ (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

## 6. Fill validation (vs gold standard)
- Parameters compared: **2**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **1**
- Poor (>50% error): **1**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **20864.36%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| ξu | 0.01 | 0.0087 | 14.94% | approximate |
| ξm | 0.01 | 4.77e-05 | 20864.36% | poor |
| φ | None | 0.00128 | — | no_fill |
