# Phase 3 Gap Analysis Report

**Disease / context:** malaria

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **20**
- Missing compartments: 3
- Missing parameters: 17
- Missing stratifications: 0
- Missing interventions: 0
- Extra compartments (not in gold standard): 6
- Extra parameters (not in gold standard): 0

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **exposed humans population** — Present in gold standard but not in extracted model. (severity: high)
- **infected humans population** — Present in gold standard but not in extracted model. (severity: high)
- **infected mosquitoes population** — Present in gold standard but not in extracted model. (severity: high)

## 4. Missing parameters
- **αₕ** — Present in gold standard but not in extracted model.
- **β₂a(t)** — Present in gold standard but not in extracted model.
- **β₁a(t)** — Present in gold standard but not in extracted model.
- **q** — Present in gold standard but not in extracted model.
- **β₁** — Present in gold standard but not in extracted model.
- **μₕ** — Present in gold standard but not in extracted model.
- **a(t)** — Present in gold standard but not in extracted model.
- **μᵥ(t)** — Present in gold standard but not in extracted model.
- **ρₕ** — Present in gold standard but not in extracted model.
- **γₕ** — Present in gold standard but not in extracted model.
- **μₕ+δ** — Present in gold standard but not in extracted model.
- **θᵥ(t,r)** — Present in gold standard but not in extracted model.
- **nᵥ** — Present in gold standard but not in extracted model.
- **β₂** — Present in gold standard but not in extracted model.
- **λₘ(t)** — Present in gold standard but not in extracted model.
- **k** — Present in gold standard but not in extracted model.
- **t** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: vaccinated humans, vector exposed humans, non-vector exposed humans, infectious humans, treated humans, infectious mosquitoes

## 5. Gap filling results
- Filled via **RAG**: 17
- Filled via **inference**: 0
- **Flagged** for manual review: 3

### αₕ (missing_parameters)
- **Source:** rag
- **Value:** 15129 people/time
- **Description:** Recruitment rate of human populations
- **From papers:** p2_hiv_llm_claude_20260218_171956, p2_baseline_malaria, p2_malaria_llm_claude_20260218_172126

### β₂a(t) (missing_parameters)
- **Source:** rag
- **Value:** β₂ * a(T) 1/time
- **Description:** Combined contact rate
- **From papers:** p2_baseline_malaria, p2_malaria_llm_claude_20260218_172126, p2_malaria_llm_gemini_20260218_173044

### β₁a(t) (missing_parameters)
- **Source:** rag
- **Value:** β₂ * a(T) 1/time
- **Description:** Combined contact rate
- **From papers:** p2_baseline_malaria, p2_malaria_llm_claude_20260218_172126, p2_malaria_llm_gemini_20260218_173044

### q (missing_parameters)
- **Source:** rag
- **Value:** 0.98765 1/time
- **Description:** Progression rate of Rh to Sh
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_baseline_malaria, p2_malaria_llm_claude_20260218_172126

### β₁ (missing_parameters)
- **Source:** rag
- **Value:** 0.9969 dimensionless
- **Description:** Probability of transmission (human)
- **From papers:** p2_baseline_malaria, p2_malaria_llm_claude_20260218_172126, p2_cholera_llm_claude_20260212_211525

### μₕ (missing_parameters)
- **Source:** rag
- **Value:** 0.001384 1/time
- **Description:** Natural mortality rate of human population
- **From papers:** p2_measles_llm_claude_20260212_211744, p2_hiv_llm_claude_20260218_171956, p2_baseline_malaria

### a(t) (missing_parameters)
- **Source:** rag
- **Value:** 0.000203 * T * (T - 11.7) * sqrt(42.3 - T) bites/person/time
- **Description:** Mosquito biting rate
- **From papers:** p1_model_malaria, p2_baseline_malaria, p2_malaria_llm_claude_20260218_172126

### μᵥ(t) (missing_parameters)
- **Source:** rag
- **Value:** 1/((-0.03*T*T) + (1.31*T) - 4.4) 1/time
- **Description:** Mortality rate of adult mosquitoes
- **From papers:** p2_dengue_llm_claude_20260212_211544, p1_model_malaria, p2_baseline_malaria

### ρₕ (missing_parameters)
- **Source:** rag
- **Value:** 0.001995 1/time
- **Description:** Progression rate Eh to Ih
- **From papers:** p2_malaria_llm_claude_20260218_172126, p2_baseline_malaria, p1_model_malaria

### γₕ (missing_parameters)
- **Source:** rag
- **Value:** 0.120342 1/time
- **Description:** Recovery rate Ih to Rh
- **From papers:** p2_malaria_llm_claude_20260218_172126, p2_baseline_malaria, p2_malaria_llm_gemini_20260218_173044

### μₕ+δ (missing_parameters)
- **Source:** rag
- **Value:** μₕ + 0.017325 1/time
- **Description:** Disease-induced death rate
- **From papers:** p1_model_malaria, p2_baseline_malaria, p2_malaria_llm_claude_20260218_172126

### θᵥ(t,r) (missing_parameters)
- **Source:** rag
- **Value:** (200 * exp(-0.00554 * T + 0.06737) * 4 * R * (50 - R) * 6.75e-5) / (2 + (1 / (0.00554 * T - 0.056737))) * (1 - Nᵥ / K) * Nᵥ mosquitoes/time
- **Description:** Mosquito recruitment rate
- **From papers:** p2_malaria_llm_gemini_20260218_173044, p2_baseline_malaria, p2_malaria_llm_claude_20260218_172126

### nᵥ (missing_parameters)
- **Source:** rag
- **Value:** 80000 mosquitoes
- **Description:** Approx. Mosquito population
- **From papers:** p2_baseline_malaria, p2_malaria_llm_claude_20260218_172126, p2_malaria_llm_gemini_20260218_173044

### β₂ (missing_parameters)
- **Source:** rag
- **Value:** 0.9969 dimensionless
- **Description:** Probability of transmission (human)
- **From papers:** p2_baseline_malaria, p2_malaria_llm_claude_20260218_172126, p2_cholera_llm_claude_20260212_211525

### λₘ(t) (missing_parameters)
- **Source:** rag
- **Value:** 0.000203 * T * (T - 11.7) * sqrt(42.3 - T) bites/person/time
- **Description:** Mosquito biting rate
- **From papers:** p2_malaria_llm_claude_20260218_172126, p2_baseline_malaria, p2_cholera_llm_gemini_20260211_201419

### k (missing_parameters)
- **Source:** rag
- **Value:** 100000 mosquitoes
- **Description:** Mosquito carrying capacity
- **From papers:** p2_cholera_llm_claude_20260212_211525, p2_cholera_llm_gemini_20260211_201419, p2_baseline_malaria

### t (missing_parameters)
- **Source:** rag
- **Value:** 1.0 
- **From papers:** p1_model_malaria, p2_baseline_malaria, p2_malaria_llm_claude_20260218_172126

### exposed humans population (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infected humans population (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infected mosquitoes population (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

## 6. Fill validation (vs gold standard)
- Parameters compared: **14**
- Exact match (<1% error): **14**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**
- **Accuracy (exact+close)**: **100.0%**
- Median relative error: **0.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| αₕ | 15129.0 | 15129.0 | 0.0% | exact |
| β₂a(t) | β₂ * a(T) | β₁ * a(T) | — | no_comparison |
| β₁a(t) | β₂ * a(T) | β₁ * a(T) | — | no_comparison |
| q | 0.98765 | 0.98765 | 0.0% | exact |
| β₁ | 0.9969 | 0.9917 | 0.52% | exact |
| μₕ | 0.001384 | 0.001384 | 0.0% | exact |
| a(t) | 0.000203 | 0.000203 | 0.0% | exact |
| μᵥ(t) | 1.0 | 1.0 | 0.0% | exact |
| ρₕ | 0.001995 | 0.001995 | 0.0% | exact |
| γₕ | 0.120342 | 0.120342 | 0.0% | exact |
| μₕ+δ | 0.017325 | 0.017325 | 0.0% | exact |
| θᵥ(t,r) | 200.0 | 200.0 | 0.0% | exact |
| nᵥ | 80000.0 | 80000.0 | 0.0% | exact |
| β₂ | 0.9969 | 0.9917 | 0.52% | exact |
| λₘ(t) | 0.000203 | 0.000203 | 0.0% | exact |
| k | 100000.0 | 100000.0 | 0.0% | exact |
| t | 1.0 |  | — | no_comparison |
