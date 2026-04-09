# Phase 3 Gap Analysis Report

**Disease / context:** dengue1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **4**
- Missing compartments: 1
- Missing parameters: 2
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 1
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 2
- Extra flows (not in gold standard): 4

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **4**
- After fills gaps (re-detected): **0**
- Delta (before - after): **4**
- Delta missing parameters: **2**
- Delta missing compartments: **1**
- Delta missing flows: **1**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **susceptible mosquitoes** — Present in gold standard but not in extracted model. (severity: high)

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
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 90.9% | 30% |
| **Fill traceability** | 75.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **92.3/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 2 | 1 | 4 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 2 | 4 | 6 |

## 5. Gap filling results
- Filled via **RAG**: 3
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 1

### susceptible mosquitoes (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### γ1 (missing_parameters)
- **Source:** rag
- **Value:** 0.5 1/time
- **Description:** Recovery rate of symptomatic infectious hosts (paper: γ1a)
- **From papers:** p1_model_malaria, p1_model_dengue, p2_influenza3_llm_claude_20260407_214824

### γ2 (missing_parameters)
- **Source:** rag
- **Value:** 0.5 1/time
- **Description:** Recovery rate of asymptomatic infectious hosts (paper: γ2a)
- **From papers:** p1_model_malaria, p1_model_dengue, p2_influenza3_llm_claude_20260407_214824

### Susceptible mosquitoes->Infectious mosquitoes (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 6. Fill validation (vs gold standard)
- Parameters compared: **2**
- Exact match (<1% error): **2**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**
- **Accuracy (exact+close)**: **100.0%**
- Median relative error: **0.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| γ1 | 0.5 | 0.5 | 0.0% | exact |
| γ2 | 0.5 | 0.5 | 0.0% | exact |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **7**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **7** | Candidate: **13**
- Precision **0.6923** | Recall **1.0** | F1 **0.8182**
