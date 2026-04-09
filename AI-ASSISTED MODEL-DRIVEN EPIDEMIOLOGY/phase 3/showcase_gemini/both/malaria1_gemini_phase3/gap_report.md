# Phase 3 Gap Analysis Report

**Disease / context:** malaria1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **7**
- Missing compartments: 0
- Missing parameters: 7
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 0
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 14
- Extra flows (not in gold standard): 2

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
- **μ1** — Present in gold standard but not in extracted model.
- **λv^v** — Present in gold standard but not in extracted model.
- **λv^s** — Present in gold standard but not in extracted model.
- **λh^v** — Present in gold standard but not in extracted model.
- **λh^s** — Present in gold standard but not in extracted model.
- **λm** — Present in gold standard but not in extracted model.
- **μ2** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: π, µ1, µ2, θ, ε, ηv, ηs, ξv, ξs, ρ, φ, β1, β2, β3

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 95.8% | 30% |
| **Fill traceability** | 28.6% | 20% |
| **Parameter accuracy** | 28.6% | 20% |
| **→ Composite** | **70.2/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 7 | 0 | 7 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 14 | 2 | 16 |

## 5. Gap filling results
- Filled via **RAG**: 2
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 5
- **Flagged** for manual review: 0

### μ1 (missing_parameters)
- **Source:** rag
- **Value:** 0.001384 1/time
- **Description:** Natural death rate of humans (μ1)
- **From papers:** p1_model_zika, p1_model_malaria, p1_model_measles

### λv^v (missing_parameters)
- **Source:** inference
- **Value:** 1e-05 per day
- **Confidence:** LOW

### λv^s (missing_parameters)
- **Source:** inference
- **Value:** 0.0001 per day
- **Confidence:** LOW

### λh^v (missing_parameters)
- **Source:** inference
- **Value:** 1e-05 1/day
- **Confidence:** LOW

### λh^s (missing_parameters)
- **Source:** inference
- **Value:** 1e-05 per day
- **Confidence:** LOW

### λm (missing_parameters)
- **Source:** inference
- **Value:** 0.0005 per day
- **Confidence:** LOW

### μ2 (missing_parameters)
- **Source:** rag
- **Value:** 0.1 1/time
- **Description:** Natural death rate of mosquitoes (μ2)
- **From papers:** p1_model_zika, p1_model_malaria, p1_model_measles

## 6. Fill validation (vs gold standard)
- Parameters compared: **7**
- Exact match (<1% error): **2**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **5**
- **Accuracy (exact+close)**: **28.6%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| μ1 | 0.001384 | 0.001384 | 0.0% | exact |
| λv^v | 1e-05 | 0.0 | 100.0% | poor |
| λv^s | 0.0001 | 0.0 | 100.0% | poor |
| λh^v | 1e-05 | 0.0 | 100.0% | poor |
| λh^s | 1e-05 | 0.0 | 100.0% | poor |
| λm | 0.0005 | 0.0 | 100.0% | poor |
| μ2 | 0.1 | 0.1 | 0.0% | exact |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **10**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **11** | Candidate: **13**
- Precision **0.8462** | Recall **1.0** | F1 **0.9167**
