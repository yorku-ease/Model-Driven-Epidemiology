# Phase 3 Gap Analysis Report

**Disease / context:** malaria

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **5**
- Missing compartments: 1
- Missing parameters: 4
- Missing stratifications: 0
- Missing interventions: 0
- Extra compartments (not in gold standard): 6
- Extra parameters (not in gold standard): 17

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **infected mosquitoes population** — Present in gold standard but not in extracted model. (severity: high)

## 4. Missing parameters
- **q** — Present in gold standard but not in extracted model.
- **μₕ** — Present in gold standard but not in extracted model.
- **μᵥ(t)** — Present in gold standard but not in extracted model.
- **k** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: vaccinated humans, vector exposed humans, non-vector exposed humans, infectious humans, treated humans, infectious mosquitoes
- Extra parameters: i, s, es, v, π, ε, ξv, ξs, σ, ω, φ, α1, α2, r0, r01, r02, r03

## 5. Gap filling results
- Filled via **RAG**: 4
- Filled via **inference**: 0
- **Flagged** for manual review: 1

### q (missing_parameters)
- **Source:** rag
- **Value:** 0.98765 1/time
- **Description:** Progression rate of Rh to Sh
- **From papers:** p2_zika_llm_gemini_20260211_202118, p2_baseline_malaria, p1_model_malaria

### μₕ (missing_parameters)
- **Source:** rag
- **Value:** 0.001384 1/time
- **Description:** Natural mortality rate of human population
- **From papers:** p2_baseline_malaria, p2_malaria_llm_claude_20260218_172126, p1_model_malaria

### μᵥ(t) (missing_parameters)
- **Source:** rag
- **Value:** 1/((-0.03*T*T) + (1.31*T) - 4.4) 1/time
- **Description:** Mortality rate of adult mosquitoes
- **From papers:** p1_model_malaria, p2_baseline_malaria, p2_dengue_llm_claude_20260212_211544

### k (missing_parameters)
- **Source:** rag
- **Value:** 100000 mosquitoes
- **Description:** Mosquito carrying capacity
- **From papers:** p2_cholera_llm_openai_20260218_174559, p2_malaria_llm_gemini_20260218_173044, p2_baseline_malaria

### infected mosquitoes population (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

## 6. Fill validation (vs gold standard)
- Parameters compared: **4**
- Exact match (<1% error): **4**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**
- **Accuracy (exact+close)**: **100.0%**
- Median relative error: **0.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| q | 0.98765 | 0.98765 | 0.0% | exact |
| μₕ | 0.001384 | 0.001384 | 0.0% | exact |
| μᵥ(t) | 1.0 | 1.0 | 0.0% | exact |
| k | 100000.0 | 100000.0 | 0.0% | exact |
