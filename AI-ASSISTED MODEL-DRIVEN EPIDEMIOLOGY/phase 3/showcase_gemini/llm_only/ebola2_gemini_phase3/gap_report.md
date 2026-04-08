# Phase 3 Gap Analysis Report

**Disease / context:** ebola2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **5**
- Missing compartments: 0
- Missing parameters: 5
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 0
- Extra compartments (not in gold standard): 2
- Extra parameters (not in gold standard): 8
- Extra flows (not in gold standard): 3

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **5**
- After fills gaps (re-detected): **2**
- Delta (before - after): **3**
- Delta missing parameters: **3**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- None

## 4. Missing parameters
- **hospitalizationrate** — Present in gold standard but not in extracted model.
- **recoveryrate** — Present in gold standard but not in extracted model.
- **deathratecommunity** — Present in gold standard but not in extracted model.
- **recoveryratehospital** — Present in gold standard but not in extracted model.
- **deathratehospital** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: cumulative cases, burials
- Extra parameters: θ, φ, γ, α, γh, δ, δh, κ

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 60.0% | 30% |
| **Reference agreement** | 65.1% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **57.5/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 5 | 0 | 5 |
| **Extra in model** | Model items not in reference (noise/convention) | 2 | 8 | 3 | 13 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 7
- **Flagged** for manual review: 0

### hospitalizationrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### deathratehospital (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

## 6. Fill validation (vs gold standard)
- Parameters compared: **0**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| hospitalizationrate | None | 0.2 | — | no_fill |
| deathratehospital | None | 0.1 | — | no_fill |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **8**
- Precision **0.625** | Recall **0.8333** | F1 **0.7143**
### Flows
- Gold count: **7** | Candidate: **10**
- Precision **0.5** | Recall **0.7143** | F1 **0.5882**
