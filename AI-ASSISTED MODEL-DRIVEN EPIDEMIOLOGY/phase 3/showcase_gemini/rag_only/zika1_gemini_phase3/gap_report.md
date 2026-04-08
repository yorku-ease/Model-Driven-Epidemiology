# Phase 3 Gap Analysis Report

**Disease / context:** zika1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **13**
- Missing compartments: 3
- Missing parameters: 7
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 3
- Extra compartments (not in gold standard): 3
- Extra parameters (not in gold standard): 10
- Extra flows (not in gold standard): 3

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **13**
- After fills gaps (re-detected): **0**
- Delta (before - after): **13**
- Delta missing parameters: **7**
- Delta missing compartments: **3**
- Delta missing flows: **3**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **susceptible female adults** — Present in gold standard but not in extracted model. (severity: high)
- **exposed female adults** — Present in gold standard but not in extracted model. (severity: high)
- **infectious female adults** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Pupae (non-infectious)->Susceptible female adults** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible female adults->Exposed female adults** — Present in gold standard but not in extracted model (or flows list).
- **Exposed female adults->Infectious female adults** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **1/τ_h** — Present in gold standard but not in extracted model.
- **b_v** — Present in gold standard but not in extracted model.
- **1/τ_v** — Present in gold standard but not in extracted model.
- **r_h** — Present in gold standard but not in extracted model.
- **f** — Present in gold standard but not in extracted model.
- **δ_l** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: susceptible vectors, exposed vectors, infectious vectors
- Extra parameters: 1/α_h, 1/α_v, 1/γ, e_v, μ_v, q, k_h, k_v, κ, r0

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 85.6% | 30% |
| **Fill traceability** | 76.9% | 20% |
| **Parameter accuracy** | 85.7% | 20% |
| **→ Composite** | **88.2/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 3 | 7 | 3 | 13 |
| **Extra in model** | Model items not in reference (noise/convention) | 3 | 10 | 3 | 16 |

## 5. Gap filling results
- Filled via **RAG**: 10
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 3

### susceptible female adults (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### exposed female adults (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectious female adults (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### 1/τ_h (missing_parameters)
- **Source:** rag
- **Value:** 0.5 1/day
- **Description:** Inverse incubation time in humans
- **From papers:** p2_zika3_llm_openai_20260407_213203, p2_cholera3_llm_claude_20260407_213639, p1_model_dengue

### b_v (missing_parameters)
- **Source:** rag
- **Value:** 0.001 1/day
- **Description:** Biting/exposure term for vectors becoming infected from infectious humans
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p1_model_dengue, p2_cholera3_llm_gemini_20260407_204240

### 1/τ_v (missing_parameters)
- **Source:** rag
- **Value:** 0.125 1/day
- **Description:** Inverse incubation time in vectors
- **From papers:** p2_zika3_llm_openai_20260407_213203, p2_cholera3_llm_claude_20260407_213639, p1_model_dengue

### r_h (missing_parameters)
- **Source:** rag
- **Value:** 0.00005 1/day
- **Description:** Human population growth rate (logistic)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p1_model_dengue

### f (missing_parameters)
- **Source:** rag
- **Value:** 80 1/day
- **Description:** Eggs laid per female mosquito per unit time
- **From papers:** p1_model_covid, p2_cholera3_llm_claude_20260407_213639, p1_model_zika

### δ_l (missing_parameters)
- **Source:** rag
- **Value:** 1 1/day
- **Description:** Larval density-dependent mortality coefficient (logistic term)
- **From papers:** p1_model_cholera, p1_model_hiv, p1_model_zika

### φ (missing_parameters)
- **Source:** rag
- **Value:** 0.00128 1/week
- **Description:** Maturation rate child→adult (j)
- **From papers:** p1_model_measles, p2_cholera3_llm_claude_20260407_213639, p2_dengue3_llm_claude_20260407_214050

### Pupae (non-infectious)->Susceptible female adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible female adults->Exposed female adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Exposed female adults->Infectious female adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

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
| 1/τ_h | 0.5 | 0.5 | 0.0% | exact |
| b_v | 0.001 | 0.001 | 0.0% | exact |
| 1/τ_v | 0.125 | 0.125 | 0.0% | exact |
| r_h | 5e-05 | 5e-05 | 0.0% | exact |
| f | 80.0 | 80.0 | 0.0% | exact |
| δ_l | 1.0 | 1.0 | 0.0% | exact |
| φ | 0.00128 | 0.68 | 99.81% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **13**
- Precision **0.7692** | Recall **1.0** | F1 **0.8696**
### Flows
- Gold count: **8** | Candidate: **11**
- Precision **0.7273** | Recall **1.0** | F1 **0.8421**
