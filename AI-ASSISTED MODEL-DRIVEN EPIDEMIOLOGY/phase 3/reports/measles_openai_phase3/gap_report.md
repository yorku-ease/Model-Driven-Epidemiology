# Phase 3 Gap Analysis Report

**Disease / context:** measles

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **19**
- Missing compartments: 0
- Missing parameters: 19
- Missing stratifications: 0
- Missing interventions: 0

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- None

## 4. Missing parameters
- **ξu** — Present in gold standard but not in extracted model.
- **ξm** — Present in gold standard but not in extracted model.
- **βc** — Present in gold standard but not in extracted model.
- **βa** — Present in gold standard but not in extracted model.
- **m** — Present in gold standard but not in extracted model.
- **γ** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.
- **ε** — Present in gold standard but not in extracted model.
- **ηa** — Present in gold standard but not in extracted model.
- **ηu** — Present in gold standard but not in extracted model.
- **ηm** — Present in gold standard but not in extracted model.
- **δ** — Present in gold standard but not in extracted model.
- **d** — Present in gold standard but not in extracted model.
- **ξa** — Present in gold standard but not in extracted model.
- **α** — Present in gold standard but not in extracted model.
- **q** — Present in gold standard but not in extracted model.
- **l** — Present in gold standard but not in extracted model.
- **ε * βc** — Present in gold standard but not in extracted model.
- **ε * βa** — Present in gold standard but not in extracted model.

## 5. Gap filling results
- Filled via **RAG**: 18
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

### βc (missing_parameters)
- **Source:** rag
- **Value:** 0.0 
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_cholera_llm_claude_20260212_211525, p2_ebola_llm_gemini_20260211_201558

### βa (missing_parameters)
- **Source:** rag
- **Value:** 0.2 dimensionless
- **Description:** 1 minus vaccine effectiveness; (1-e) is the effectiveness of vaccination
- **From papers:** p2_flu_llm_claude_20260212_211710, p2_malaria_llm_claude_20260218_172126, p1_model_malaria

### m (missing_parameters)
- **Source:** rag
- **Value:** 4.777e-6 per week
- **Description:** Monitored children vaccination rate
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_zika_llm_claude_20260212_212158, p2_zika_llm_gemini_20260211_202118

### γ (missing_parameters)
- **Source:** rag
- **Value:** 0.120342 1/time
- **Description:** Recovery rate Ih to Rh
- **From papers:** p2_flu_llm_claude_20260212_211710, p2_cholera_llm_gemini_20260211_201419, p2_ebola_llm_gemini_20260211_201558

### φ (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ε (missing_parameters)
- **Source:** rag
- **Value:** 2.0 
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_malaria_llm_gemini_20260218_173044, p2_tuberculosis_llm_claude_20260212_212137

### ηa (missing_parameters)
- **Source:** rag
- **Value:** 0.2 dimensionless
- **Description:** 1 minus vaccine effectiveness; (1-e) is the effectiveness of vaccination
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_flu_llm_claude_20260212_211710, p2_hiv_llm_openai_20260218_174815

### ηu (missing_parameters)
- **Source:** rag
- **Value:** 0.2 dimensionless
- **Description:** 1 minus vaccine effectiveness; (1-e) is the effectiveness of vaccination
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_malaria_llm_claude_20260218_172126, p2_malaria_llm_gemini_20260218_173044

### ηm (missing_parameters)
- **Source:** rag
- **Value:** 0.2 dimensionless
- **Description:** 1 minus vaccine effectiveness; (1-e) is the effectiveness of vaccination
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_flu_llm_claude_20260212_211710, p2_hiv_llm_openai_20260218_174815

### δ (missing_parameters)
- **Source:** rag
- **Value:** 9.3399e-5 per week
- **Description:** Disease-induced death rate
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_measles_llm_gemini_20260211_201815, p2_cholera_llm_claude_20260212_211525

### d (missing_parameters)
- **Source:** rag
- **Value:** 1.0 
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_cholera_llm_claude_20260212_211525, p2_hiv_llm_openai_20260218_174815

### ξa (missing_parameters)
- **Source:** rag
- **Value:** 0.01253 dimensionless
- **Description:** Fraction of total population in the measles transmission contact network
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_cholera_llm_gemini_20260211_201419, p2_cholera_llm_claude_20260212_211525

### α (missing_parameters)
- **Source:** rag
- **Value:** 0.0 
- **From papers:** p2_hiv_llm_claude_20260218_171956, p2_ebola_llm_gemini_20260211_201558, p1_model_malaria

### q (missing_parameters)
- **Source:** rag
- **Value:** 0.15 dimensionless
- **Description:** Portion of infections recorded
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_baseline_malaria, p2_zika_llm_claude_20260212_212158

### l (missing_parameters)
- **Source:** rag
- **Value:** 2017.0 
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_hiv_llm_openai_20260218_174815, p1_model_malaria

### ε * βc (missing_parameters)
- **Source:** rag
- **Value:** mu * N persons per week
- **Description:** Recruitment rate to children group (birth rate balancing death rate)
- **From papers:** p2_flu_llm_claude_20260212_211710, p2_malaria_llm_claude_20260218_172126, p1_model_malaria

### ε * βa (missing_parameters)
- **Source:** rag
- **Value:** mu * N persons per week
- **Description:** Recruitment rate to children group (birth rate balancing death rate)
- **From papers:** p2_flu_llm_claude_20260212_211710, p2_malaria_llm_claude_20260218_172126, p1_model_malaria

## 6. Fill validation (vs gold standard)
- Parameters compared: **16**
- Exact match (<1% error): **1**
- Close (<10% error): **0**
- Approximate (<50% error): **1**
- Poor (>50% error): **14**
- **Accuracy (exact+close)**: **6.2%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| ξu | 0.01 | 0.0087 | 14.94% | approximate |
| ξm | 0.01 | 4.77e-05 | 20864.36% | poor |
| βc | 0.0 | 0.00188 | 100.0% | poor |
| βa | 0.2 | 1e-05 | 1999900.0% | poor |
| m | 4.777e-06 | 0.000268 | 98.22% | poor |
| γ | 0.120342 | 0.5 | 75.93% | poor |
| φ | None | 0.00128 | — | no_fill |
| ε | 2.0 | 0.2 | 900.0% | poor |
| ηa | 0.2 | 0.0 | 100.0% | poor |
| ηu | 0.2 | 3e-06 | 6666566.67% | poor |
| ηm | 0.2 | 0.0167 | 1097.6% | poor |
| δ | 9.3399e-05 | 0.7 | 99.99% | poor |
| d | 1.0 | 9.34e-05 | 1070563.81% | poor |
| ξa | 0.01253 | 0.0 | 100.0% | poor |
| α | 0.0 | 0.01253 | 100.0% | poor |
| q | 0.15 | 0.15 | 0.0% | exact |
| l | 2017.0 | 0.02197 | 9180600.96% | poor |
| ε * βc | mu * N | ε * βC | — | no_comparison |
| ε * βa | mu * N | ε * βA | — | no_comparison |
