# Phase 3 Gap Analysis Report

**Disease / context:** dengue

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **8**
- Missing compartments: 1
- Missing parameters: 7
- Missing stratifications: 0
- Missing interventions: 0
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 4

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **susceptible mosquitoes** — Present in gold standard but not in extracted model. (severity: high)

## 4. Missing parameters
- **μh** — Present in gold standard but not in extracted model.
- **μv** — Present in gold standard but not in extracted model.
- **ha** — Present in gold standard but not in extracted model.
- **γ1a** — Present in gold standard but not in extracted model.
- **γ2a** — Present in gold standard but not in extracted model.
- **va** — Present in gold standard but not in extracted model.
- **k** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: μ_h, μ_v, γ_1(a), γ_2(a)

## 5. Gap filling results
- Filled via **RAG**: 7
- Filled via **inference**: 0
- **Flagged** for manual review: 1

### μh (missing_parameters)
- **Source:** rag
- **Value:** 0.005 per unit time
- **Description:** The natural death rate of the human hosts
- **From papers:** p2_dengue_llm_claude_20260212_211544, p2_hiv_llm_claude_20260218_171956, p2_dengue_llm_openai_20260218_174642

### μv (missing_parameters)
- **Source:** rag
- **Value:** 0.65 per unit time
- **Description:** The natural death rate of the mosquitoes
- **From papers:** p2_dengue_llm_openai_20260218_174642, p2_zika_llm_claude_20260212_212158, p2_dengue_llm_claude_20260212_211544

### ha (missing_parameters)
- **Source:** rag
- **Value:** 18 per unit time
- **Description:** The age-specific transmission rate from infected mosquitoes to susceptible hosts
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_dengue_llm_openai_20260218_174642, p1_model_malaria

### γ1a (missing_parameters)
- **Source:** rag
- **Value:** 0.5 per unit time
- **Description:** The age-specific recovery rate of symptomatic infectious hosts
- **From papers:** p2_dengue_llm_claude_20260212_211544, p2_dengue_llm_openai_20260218_174642, p2_cholera_llm_openai_20260218_174559

### γ2a (missing_parameters)
- **Source:** rag
- **Value:** 0.5 per unit time
- **Description:** The age-specific recovery rate of asymptomatic infectious hosts
- **From papers:** p2_dengue_llm_claude_20260212_211544, p2_dengue_llm_openai_20260218_174642, p2_cholera_llm_openai_20260218_174559

### va (missing_parameters)
- **Source:** rag
- **Value:** 8 per unit time
- **Description:** The age-specific transmission rate from infectious hosts to susceptible mosquitoes
- **From papers:** p2_dengue_llm_openai_20260218_174642, p2_hiv_llm_openai_20260218_174815, p2_dengue_llm_claude_20260212_211544

### k (missing_parameters)
- **Source:** rag
- **Value:** 100000 mosquitoes
- **Description:** Mosquito carrying capacity
- **From papers:** p2_dengue_llm_claude_20260212_211544, p2_cholera_llm_claude_20260212_211525, p2_cholera_llm_gemini_20260211_201419

### susceptible mosquitoes (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

## 6. Fill validation (vs gold standard)
- Parameters compared: **7**
- Exact match (<1% error): **6**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **1**
- **Accuracy (exact+close)**: **85.7%**
- Median relative error: **0.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| μh | 0.005 | 0.005 | 0.0% | exact |
| μv | 0.65 | 0.65 | 0.0% | exact |
| ha | 18.0 | 18.0 | 0.0% | exact |
| γ1a | 0.5 | 0.5 | 0.0% | exact |
| γ2a | 0.5 | 0.5 | 0.0% | exact |
| va | 8.0 | 8.0 | 0.0% | exact |
| k | 100000.0 | 0.6 | 16666566.67% | poor |
