# Phase 3 Gap Analysis Report

**Disease / context:** ebola3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **6**
- Missing compartments: 0
- Missing parameters: 1
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 5
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 16
- Extra flows (not in gold standard): 2

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **6**
- After fills gaps (re-detected): **3**
- Delta (before - after): **3**
- Delta missing parameters: **1**
- Delta missing compartments: **0**
- Delta missing flows: **2**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- **InfectiousCommunity->Removed** — Present in gold standard but not in extracted model (or flows list).
- **InfectiousCommunity->FuneralInfectious** — Present in gold standard but not in extracted model (or flows list).
- **Hospitalized->FuneralInfectious** — Present in gold standard but not in extracted model (or flows list).
- **Hospitalized->Removed** — Present in gold standard but not in extracted model (or flows list).
- **FuneralInfectious->Removed** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **burialrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: dead but not yet buried
- Extra parameters: beta_i, beta_h, beta_f, alpha, 1/c_h, 1/c_d, 1/c_i, 1/c_f, h_1, d_1, d_2, r_0, r_0i, r_0h, r_0f, 1-z

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 50.0% | 30% |
| **Reference agreement** | 78.8% | 30% |
| **Fill traceability** | 55.6% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **49.7/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 1 | 5 | 6 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 16 | 2 | 19 |

## 5. Gap filling results
- Filled via **RAG**: 1
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 8
- **Flagged** for manual review: 0

### burialrate (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### InfectiousCommunity->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in the

### InfectiousCommunity->FuneralInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** An infectious individual in the community dies and their remains become a source of infection during traditional burial ceremonies.
- **Reasoning:** The model explicitly subdivides infectious phases to account for transmission 'after death during traditional burial,' implying a transition from a living infectious state to a deceased infectious state relevant to funerals.

### Hospitalized->FuneralInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who die while hospitalized

### Hospitalized->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Hospitalized individuals either recover from Ebola or die due to the infection, transitioning them out of the infectious and hospitalized state.
- **Reasoning:** This flow is necessary to account for the natural progression of disease for hospitalized patients, leading to either recovery or death, which are the ultimate outcomes for individuals in a disease model's 'Removed' compartment.

### FuneralInfectious->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Deceased individuals

### InfectiousCommunity->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in the infectious

### Hospitalized->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Hospitalized individuals either recover

### FuneralInfectious->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who

## 6. Fill validation (vs gold standard)
- Parameters compared: **1**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **1**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **85.71%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| burialrate | 0.5 | 3.5 | 85.71% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **6**
- Precision **0.8333** | Recall **1.0** | F1 **0.9091**
### Flows
- Gold count: **8** | Candidate: **7**
- Precision **0.7143** | Recall **0.625** | F1 **0.6667**
