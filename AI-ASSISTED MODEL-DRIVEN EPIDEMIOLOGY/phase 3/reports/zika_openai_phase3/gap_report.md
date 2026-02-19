# Phase 3 Gap Analysis Report

**Disease / context:** zika

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **24**
- Missing compartments: 6
- Missing parameters: 18
- Missing stratifications: 0
- Missing interventions: 0
- Extra compartments (not in gold standard): 3
- Extra parameters (not in gold standard): 0

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
- **b** — Present in gold standard but not in extracted model.
- **1/τ_h** — Present in gold standard but not in extracted model.
- **γ_h** — Present in gold standard but not in extracted model.
- **1/τ1_v** — Present in gold standard but not in extracted model.
- **1/τ2_v** — Present in gold standard but not in extracted model.
- **τ3_v** — Present in gold standard but not in extracted model.
- **b_v** — Present in gold standard but not in extracted model.
- **1/τ_v** — Present in gold standard but not in extracted model.
- **r_h** — Present in gold standard but not in extracted model.
- **μ_h** — Present in gold standard but not in extracted model.
- **f** — Present in gold standard but not in extracted model.
- **μ1_v** — Present in gold standard but not in extracted model.
- **μ2_v** — Present in gold standard but not in extracted model.
- **δ_l** — Present in gold standard but not in extracted model.
- **μ3_v** — Present in gold standard but not in extracted model.
- **μ_a_v** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.
- **φ * τ3_v** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: susceptible mosquitoes, exposed mosquitoes, infectious mosquitoes

## 5. Gap filling results
- Filled via **RAG**: 17
- Filled via **inference**: 1
- **Flagged** for manual review: 6

### b (missing_parameters)
- **Source:** rag
- **Value:** 0.0 
- **From papers:** p2_zika_llm_gemini_20260211_202118, p2_dengue_llm_claude_20260212_211544, p2_zika_llm_claude_20260212_212158

### 1/τ_h (missing_parameters)
- **Source:** rag
- **Value:** 1 / (exp(μᵥ(T)) * (111/(T - 16))) 1/time
- **Description:** Progression rate Ev to Iv
- **From papers:** p2_dengue_llm_claude_20260212_211544, p2_hiv_llm_gemini_20260218_172812, p1_model_malaria

### γ_h (missing_parameters)
- **Source:** rag
- **Value:** 100000 individuals
- **Description:** Number of alternative hosts besides humans in the environment
- **From papers:** p2_cholera_llm_openai_20260218_174559, p2_zika_llm_gemini_20260211_202118, p1_model_malaria

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

### b_v (missing_parameters)
- **Source:** rag
- **Value:** 0.3 to 0.5 1/day
- **Description:** Average biting rate per unit time of a female mosquito.
- **From papers:** p2_zika_llm_gemini_20260211_202118, p2_dengue_llm_claude_20260212_211544, p2_malaria_llm_claude_20260218_172126

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

### μ_h (missing_parameters)
- **Source:** rag
- **Value:** 0.005 per unit time
- **Description:** The natural death rate of the human hosts
- **From papers:** p2_zika_llm_gemini_20260211_202118, p2_dengue_llm_claude_20260212_211544, p2_zika_llm_claude_20260212_212158

### f (missing_parameters)
- **Source:** rag
- **Value:** 1947.0 
- **From papers:** p2_zika_llm_claude_20260212_212158

### μ1_v (missing_parameters)
- **Source:** rag
- **Value:** 0.05 per day
- **Description:** Egg mortality rate
- **From papers:** p1_model_malaria, p2_zika_llm_gemini_20260211_202118, p2_zika_llm_claude_20260212_212158

### μ2_v (missing_parameters)
- **Source:** rag
- **Value:** 0.025 per day
- **Description:** Larval mortality rate
- **From papers:** p1_model_malaria, p2_zika_llm_gemini_20260211_202118, p2_zika_llm_claude_20260212_212158

### δ_l (missing_parameters)
- **Source:** rag
- **Value:** 80 eggs/mosquito/day
- **Description:** Number of eggs laid per female mosquito per unit time.
- **From papers:** p2_cholera_llm_openai_20260218_174559, p2_zika_llm_gemini_20260211_202118, p2_hiv_llm_gemini_20260218_172812

### μ3_v (missing_parameters)
- **Source:** rag
- **Value:** 0.0025 per day
- **Description:** Pupal mortality rate
- **From papers:** p1_model_malaria, p2_zika_llm_gemini_20260211_202118, p2_zika_llm_claude_20260212_212158

### μ_a_v (missing_parameters)
- **Source:** rag
- **Value:** 100000 individuals
- **Description:** Number of alternative hosts besides humans in the environment
- **From papers:** p2_zika_llm_gemini_20260211_202118, p2_hiv_llm_gemini_20260218_172812, p1_model_malaria

### φ (missing_parameters)
- **Source:** inference
- **Value:** 0.1 per day
- **Reasoning:** The parameter φ often represents the rate of progression or transition between compartments, such as from exposed to infectious. A typical range for such transitions in infectious disease models is between 0.05 and 0.2 per day.
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
- Parameters compared: **18**
- Exact match (<1% error): **3**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **15**
- **Accuracy (exact+close)**: **16.7%**
- Median relative error: **900.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| b | 0.0 | 0.071 | 100.0% | poor |
| 1/τ_h | 1.0 | 0.5 | 100.0% | poor |
| γ_h | 100000.0 | 0.2 | 49999900.0% | poor |
| 1/τ1_v | 3.0 | 0.3 | 900.0% | poor |
| 1/τ2_v | 9.0 | 0.1 | 8900.0% | poor |
| τ3_v | 1.0 | 0.3 | 233.33% | poor |
| b_v | 0.3 | 0.001 | 29900.0% | poor |
| 1/τ_v | 1.0 | 0.125 | 700.0% | poor |
| r_h | 0.001995 | 5e-05 | 3890.0% | poor |
| μ_h | 0.005 | 1.94e-05 | 25673.2% | poor |
| f | 1947.0 | 80.0 | 2333.75% | poor |
| μ1_v | 0.05 | 0.05 | 0.0% | exact |
| μ2_v | 0.025 | 0.025 | 0.0% | exact |
| δ_l | 80.0 | 1.0 | 7900.0% | poor |
| μ3_v | 0.0025 | 0.0025 | 0.0% | exact |
| μ_a_v | 100000.0 | 0.05 | 199999900.0% | poor |
| φ | 0.1 | 0.68 | 85.29% | poor |
| φ * τ3_v | 1.0 | 3.0 | 66.67% | poor |
