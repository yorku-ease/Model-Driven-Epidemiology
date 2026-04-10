# Phase 3 Gap Analysis Report

**Disease / context:** dengue1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **3**
- Missing compartments: 0
- Missing parameters: 2
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 1
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 2
- Extra flows (not in gold standard): 3

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **3**
- After fills gaps (re-detected): **1**
- Delta (before - after): **2**
- Delta missing parameters: **2**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible mosquitoes->Infectious mosquitoes** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **γ1** — Present in gold standard but not in extracted model.
- **γ2** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: γ_1(a), γ_2(a)

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 66.7% | 30% |
| **Reference agreement** | 90.0% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **57.0/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 2 | 1 | 3 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 2 | 3 | 5 |

## 5. Gap filling results
- Filled via **RAG**: 2
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 2

### γ1 (missing_parameters)
- **Source:** rag
- **Value:** 1/1.5 1/day
- **Description:** Recovery rate for infectious class (range 1/4 to 1)
- **From papers:** p2_cholera3_llm_gemini_20260407_204240, p2_cholera3_llm_openai_20260407_211654, p2_influenza3_llm_claude_20260407_214824

### γ2 (missing_parameters)
- **Source:** rag
- **Value:** 1/1.5 1/day
- **Description:** Recovery rate for hospitalized class (range 1/4 to 1)
- **From papers:** p2_cholera3_llm_gemini_20260407_204240, p2_cholera3_llm_openai_20260407_211654, p2_influenza3_llm_claude_20260407_214824

### Susceptible mosquitoes->Infectious mosquitoes (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible mosquitoes->Infectious mosquitoes (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

## 6. Fill validation (vs gold standard)
- Parameters compared: **2**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **2**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| γ1 | 1.0 | 0.5 | 100.0% | poor |
| γ2 | 1.0 | 0.5 | 100.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **6**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **7** | Candidate: **12**
- Precision **0.75** | Recall **0.8571** | F1 **0.8**
