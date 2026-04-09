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
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **55.7/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 3 | 7 | 3 | 13 |
| **Extra in model** | Model items not in reference (noise/convention) | 3 | 10 | 3 | 16 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 13
- **Flagged** for manual review: 0

### susceptible female adults (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Female Adults

### exposed female adults (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Female Adults

### infectious female adults (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Female Adults
- **Reasoning:** The paper describes a general SEIR model for humans with an `I_H` compartment for all infectious individuals, but the expected compartment specifies a demographic (female adults) not explicitly modeled separately.

### 1/τ_h (missing_parameters)
- **Source:** inference
- **Value:** 0.1667 per day
- **Confidence:** LOW

### b_v (missing_parameters)
- **Source:** inference
- **Value:** 0.35 
- **Reasoning:** This
- **Confidence:** LOW

### 1/τ_v (missing_parameters)
- **Source:** inference
- **Value:** 0.07 per day
- **Confidence:** LOW

### r_h (missing_parameters)
- **Source:** inference
- **Value:** 0.01 per year
- **Confidence:** LOW

### f (missing_parameters)
- **Source:** inference
- **Value:** 5e-05 1/day
- **Confidence:** LOW

### δ_l (missing_parameters)
- **Source:** inference
- **Value:** 3.65e-05 
- **Confidence:** LOW

### φ (missing_parameters)
- **Source:** inference
- **Value:** 4.93e-05 
- **Confidence:** LOW

### Pupae (non-infectious)->Susceptible female adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The emergence of non-

### Susceptible female adults->Exposed female adults (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible female adults become exposed to Zika virus after being bitten by infectious Aedes mosquitoes.
- **Reasoning:** The transition from susceptible to exposed in humans for ZIKV is mediated by contact with infectious

### Exposed female adults->Infectious female adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed

## 6. Fill validation (vs gold standard)
- Parameters compared: **7**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **1**
- Poor (>50% error): **6**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| 1/τ_h | 0.1667 | 0.5 | 66.66% | poor |
| b_v | 0.35 | 0.001 | 34900.0% | poor |
| 1/τ_v | 0.07 | 0.125 | 44.0% | approximate |
| r_h | 0.01 | 5e-05 | 19900.0% | poor |
| f | 5e-05 | 80.0 | 100.0% | poor |
| δ_l | 3.65e-05 | 1.0 | 100.0% | poor |
| φ | 4.93e-05 | 0.68 | 99.99% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **13**
- Precision **0.7692** | Recall **1.0** | F1 **0.8696**
### Flows
- Gold count: **8** | Candidate: **11**
- Precision **0.7273** | Recall **1.0** | F1 **0.8421**
