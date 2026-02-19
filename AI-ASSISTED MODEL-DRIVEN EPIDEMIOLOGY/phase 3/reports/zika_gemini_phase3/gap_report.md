# Phase 3 Gap Analysis Report

**Disease / context:** zika

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **15**
- Missing compartments: 6
- Missing parameters: 9
- Missing stratifications: 0
- Missing interventions: 0
- Extra compartments (not in gold standard): 6
- Extra parameters (not in gold standard): 13

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **eggs(non-infectious)** — Present in gold standard but not in extracted model. (severity: high)
- **larvae (non-infectious, logistic)** — Present in gold standard but not in extracted model. (severity: high)
- **pupae (non-infectious)** — Present in gold standard but not in extracted model. (severity: high)
- **susceptible female adults** — Present in gold standard but not in extracted model. (severity: high)
- **exposed female adults** — Present in gold standard but not in extracted model. (severity: high)
- **infectious female adults** — Present in gold standard but not in extracted model. (severity: high)

## 4. Missing parameters
- **1/τ_h** — Present in gold standard but not in extracted model.
- **1/τ1_v** — Present in gold standard but not in extracted model.
- **1/τ2_v** — Present in gold standard but not in extracted model.
- **τ3_v** — Present in gold standard but not in extracted model.
- **1/τ_v** — Present in gold standard but not in extracted model.
- **r_h** — Present in gold standard but not in extracted model.
- **f** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.
- **φ * τ3_v** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: vector eggs, vector larvae, vector pupae, susceptible mosquitoes, exposed mosquitoes, infectious mosquitoes
- Extra parameters: α_h, k_h, τ_1, μ_1, τ_2, μ_2, τ_3, μ_3, k_v, κ, q, α_v, μ_v

## 5. Gap filling results
- Filled via **RAG**: 8
- Filled via **inference**: 1
- **Flagged** for manual review: 6

### 1/τ_h (missing_parameters)
- **Source:** rag
- **Value:** 1 / (exp(μᵥ(T)) * (111/(T - 16))) 1/time
- **Description:** Progression rate Ev to Iv
- **From papers:** p2_dengue_llm_claude_20260212_211544, p2_hiv_llm_gemini_20260218_172812, p1_model_malaria

### 1/τ1_v (missing_parameters)
- **Source:** rag
- **Value:** 3 days
- **Description:** Egg development time
- **From papers:** p2_dengue_llm_claude_20260212_211544, p2_zika_llm_gemini_20260211_202118, p2_hiv_llm_gemini_20260218_172812

### 1/τ2_v (missing_parameters)
- **Source:** rag
- **Value:** 9 days
- **Description:** Larval development time
- **From papers:** p2_dengue_llm_claude_20260212_211544, p2_zika_llm_gemini_20260211_202118, p2_hiv_llm_gemini_20260218_172812

### τ3_v (missing_parameters)
- **Source:** rag
- **Value:** 1/3 1/day
- **Description:** Inverse of pupal development time (3 days).
- **From papers:** p2_dengue_llm_claude_20260212_211544, p2_zika_llm_gemini_20260211_202118, p2_hiv_llm_gemini_20260218_172812

### 1/τ_v (missing_parameters)
- **Source:** rag
- **Value:** 1 / (exp(μᵥ(T)) * (111/(T - 16))) 1/time
- **Description:** Progression rate Ev to Iv
- **From papers:** p2_dengue_llm_gemini_20260211_201457, p2_dengue_llm_claude_20260212_211544, p2_hiv_llm_gemini_20260218_172812

### r_h (missing_parameters)
- **Source:** rag
- **Value:** 0.001995 1/time
- **Description:** Progression rate Eh to Ih
- **From papers:** p2_cholera_llm_openai_20260218_174559, p2_cholera_llm_claude_20260212_211525, p2_dengue_llm_claude_20260212_211544

### f (missing_parameters)
- **Source:** rag
- **Value:** 1947.0 
- **From papers:** p2_zika_llm_claude_20260212_212158

### φ (missing_parameters)
- **Source:** inference
- **Value:** 0.1 per day
- **Reasoning:** The parameter φ often represents the rate of progression or transition between compartments. A typical value for such rates in epidemiological models is around 0.1 per day, assuming a moderate transition speed.
- **Confidence:** LOW

### φ * τ3_v (missing_parameters)
- **Source:** rag
- **Value:** 1/3 1/day
- **Description:** Inverse of pupal development time (3 days).
- **From papers:** p2_hiv_llm_gemini_20260218_172812, p1_model_malaria, p2_malaria_llm_claude_20260218_172126

### eggs(non-infectious) (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### larvae (non-infectious, logistic) (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### pupae (non-infectious) (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

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
- Parameters compared: **9**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **9**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **700.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| 1/τ_h | 1.0 | 0.5 | 100.0% | poor |
| 1/τ1_v | 3.0 | 0.3 | 900.0% | poor |
| 1/τ2_v | 9.0 | 0.1 | 8900.0% | poor |
| τ3_v | 1.0 | 0.3 | 233.33% | poor |
| 1/τ_v | 1.0 | 0.125 | 700.0% | poor |
| r_h | 0.001995 | 5e-05 | 3890.0% | poor |
| f | 1947.0 | 80.0 | 2333.75% | poor |
| φ | 0.1 | 0.68 | 85.29% | poor |
| φ * τ3_v | 1.0 | 3.0 | 66.67% | poor |
