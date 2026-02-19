# Phase 3 Gap Analysis Report

**Disease / context:** zika

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **12**
- Missing compartments: 3
- Missing parameters: 9
- Missing stratifications: 0
- Missing interventions: 0
- Extra compartments (not in gold standard): 3
- Extra parameters (not in gold standard): 11

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **susceptible female adults** — Present in gold standard but not in extracted model. (severity: high)
- **exposed female adults** — Present in gold standard but not in extracted model. (severity: high)
- **infectious female adults** — Present in gold standard but not in extracted model. (severity: high)

## 4. Missing parameters
- **1/τ_h** — Present in gold standard but not in extracted model.
- **τ3_v** — Present in gold standard but not in extracted model.
- **b_v** — Present in gold standard but not in extracted model.
- **1/τ_v** — Present in gold standard but not in extracted model.
- **r_h** — Present in gold standard but not in extracted model.
- **f** — Present in gold standard but not in extracted model.
- **δ_l** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.
- **φ * τ3_v** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: susceptible vectors, exposed vectors, infectious vectors
- Extra parameters: 1/α_h, 1/α_v, 1/γ, e_v, 1/τ3, μ_v, q, κ, k_h, k_v, r0

## 5. Gap filling results
- Filled via **RAG**: 8
- Filled via **inference**: 1
- **Flagged** for manual review: 3

### 1/τ_h (missing_parameters)
- **Source:** rag
- **Value:** 1 / (exp(μᵥ(T)) * (111/(T - 16))) 1/time
- **Description:** Progression rate Ev to Iv
- **From papers:** p2_dengue_llm_claude_20260212_211544, p2_hiv_llm_claude_20260218_171956, p2_hiv_llm_openai_20260218_174815

### τ3_v (missing_parameters)
- **Source:** rag
- **Value:** 1/3 1/day
- **Description:** Inverse of pupal development time (3 days).
- **From papers:** p2_dengue_llm_claude_20260212_211544, p2_malaria_llm_claude_20260218_172126, p2_hiv_llm_openai_20260218_174815

### b_v (missing_parameters)
- **Source:** rag
- **Value:** 0.3 to 0.5 1/day
- **Description:** Average biting rate per unit time of a female mosquito.
- **From papers:** p2_dengue_llm_openai_20260218_174642, p2_malaria_llm_claude_20260218_172126, p2_dengue_llm_claude_20260212_211544

### 1/τ_v (missing_parameters)
- **Source:** rag
- **Value:** 1 / (exp(μᵥ(T)) * (111/(T - 16))) 1/time
- **Description:** Progression rate Ev to Iv
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_dengue_llm_claude_20260212_211544, p2_malaria_llm_claude_20260218_172126

### r_h (missing_parameters)
- **Source:** rag
- **Value:** 0.001995 1/time
- **Description:** Progression rate Eh to Ih
- **From papers:** p2_hiv_llm_claude_20260218_171956, p2_dengue_llm_openai_20260218_174642, p2_cholera_llm_claude_20260212_211525

### f (missing_parameters)
- **Source:** rag
- **Value:** 1947.0 
- **From papers:** p2_zika_llm_claude_20260212_212158

### δ_l (missing_parameters)
- **Source:** rag
- **Value:** 80 eggs/mosquito/day
- **Description:** Number of eggs laid per female mosquito per unit time.
- **From papers:** p2_hiv_llm_claude_20260218_171956, p2_cholera_llm_gemini_20260211_201419, p2_cholera_llm_claude_20260212_211525

### φ (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### φ * τ3_v (missing_parameters)
- **Source:** rag
- **Value:** 1/3 1/day
- **Description:** Inverse of pupal development time (3 days).
- **From papers:** p2_malaria_llm_claude_20260218_172126, p1_model_malaria, p2_dengue_llm_claude_20260212_211544

### susceptible female adults (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### exposed female adults (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectious female adults (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

## 6. Fill validation (vs gold standard)
- Parameters compared: **8**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **8**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **2333.75%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| 1/τ_h | 1.0 | 0.5 | 100.0% | poor |
| τ3_v | 1.0 | 0.3 | 233.33% | poor |
| b_v | 0.3 | 0.001 | 29900.0% | poor |
| 1/τ_v | 1.0 | 0.125 | 700.0% | poor |
| r_h | 0.001995 | 5e-05 | 3890.0% | poor |
| f | 1947.0 | 80.0 | 2333.75% | poor |
| δ_l | 80.0 | 1.0 | 7900.0% | poor |
| φ | None | 0.68 | — | no_fill |
| φ * τ3_v | 1.0 | 3.0 | 66.67% | poor |
