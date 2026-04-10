# Phase 3 Gap Analysis Report

**Disease / context:** zika1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **7**
- Missing compartments: 0
- Missing parameters: 7
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 0
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 10
- Extra flows (not in gold standard): 0

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **7**
- After fills gaps (re-detected): **0**
- Delta (before - after): **7**
- Delta missing parameters: **7**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- None

## 4. Missing parameters
- **1/τ_h** — Present in gold standard but not in extracted model.
- **b_v** — Present in gold standard but not in extracted model.
- **1/τ_v** — Present in gold standard but not in extracted model.
- **r_h** — Present in gold standard but not in extracted model.
- **f** — Present in gold standard but not in extracted model.
- **δ_l** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: 1/α_h, 1/α_v, 1/γ, e_v, μ_v, q, k_h, k_v, κ, r0

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 100.0% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **70.0/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 7 | 0 | 7 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 10 | 0 | 10 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 7
- **Flagged** for manual review: 0

### 1/τ_h (missing_parameters)
- **Source:** inference
- **Value:** 0.0133 per year
- **Confidence:** LOW

### b_v (missing_parameters)
- **Source:** inference
- **Value:** 0.35 
- **Confidence:** LOW

### 1/τ_v (missing_parameters)
- **Source:** inference
- **Value:** 0.05 1/day
- **Confidence:** LOW

### r_h (missing_parameters)
- **Source:** inference
- **Value:** 5.5e-05 1/
- **Confidence:** LOW

### f (missing_parameters)
- **Source:** inference
- **Value:** 4.5e-05 1
- **Confidence:** LOW

### δ_l (missing_parameters)
- **Source:** inference
- **Value:** 4e-05 1/
- **Confidence:** LOW

### φ (missing_parameters)
- **Source:** inference
- **Value:** 4.1e-05 per day
- **Confidence:** LOW

## 6. Fill validation (vs gold standard)
- Parameters compared: **7**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **1**
- Poor (>50% error): **6**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.99%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| 1/τ_h | 0.0133 | 0.5 | 97.34% | poor |
| b_v | 0.35 | 0.001 | 34900.0% | poor |
| 1/τ_v | 0.05 | 0.125 | 60.0% | poor |
| r_h | 5.5e-05 | 5e-05 | 10.0% | approximate |
| f | 4.5e-05 | 80.0 | 100.0% | poor |
| δ_l | 4e-05 | 1.0 | 100.0% | poor |
| φ | 4.1e-05 | 0.68 | 99.99% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **10**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **8** | Candidate: **8**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
