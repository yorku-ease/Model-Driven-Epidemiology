# Phase 3 Gap Analysis Report

**Disease / context:** hiv3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **26**
- Missing compartments: 9
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 17
- Extra compartments (not in gold standard): 2
- Extra parameters (not in gold standard): 13
- Extra flows (not in gold standard): 3

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **26**
- After fills gaps (re-detected): **0**
- Delta (before - after): **26**
- Delta missing parameters: **0**
- Delta missing compartments: **9**
- Delta missing flows: **17**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **untreatedstagei1** — Present in gold standard but not in extracted model. (severity: high)
- **untreatedstagei2** — Present in gold standard but not in extracted model. (severity: high)
- **untreatedstagei3** — Present in gold standard but not in extracted model. (severity: high)
- **untreatedstagei4** — Present in gold standard but not in extracted model. (severity: high)
- **artstagea1** — Present in gold standard but not in extracted model. (severity: high)
- **artstagea2** — Present in gold standard but not in extracted model. (severity: high)
- **artstagea3** — Present in gold standard but not in extracted model. (severity: high)
- **artstagea4** — Present in gold standard but not in extracted model. (severity: high)
- **removed** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->UntreatedStageI1** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI1->UntreatedStageI2** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI1->ARTStageA1** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI2->UntreatedStageI3** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI2->ARTStageA2** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI3->UntreatedStageI4** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI3->ARTStageA3** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI4->Removed** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI4->ARTStageA4** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA1->ARTStageA2** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA1->UntreatedStageI1** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA2->ARTStageA3** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA2->UntreatedStageI2** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA3->ARTStageA4** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA3->UntreatedStageI3** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA4->Removed** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA4->UntreatedStageI4** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: infectious untreated, infectious treated
- Extra parameters: δ, μ, ρ, ν, ε, γ_u, γ_t, τ, ψ, θ, ω, α, φ

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 91.4% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **77.4/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 9 | 0 | 17 | 26 |
| **Extra in model** | Model items not in reference (noise/convention) | 2 | 13 | 3 | 18 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 26
- **Flagged** for manual review: 0

### untreatedstagei1 (missing_compartments)
- **Source:** inference
- **Primary name:** untreatedstagei1
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### untreatedstagei2 (missing_compartments)
- **Source:** inference
- **Primary name:** untreatedstagei2
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### untreatedstagei3 (missing_compartments)
- **Source:** inference
- **Primary name:** untreatedstagei3
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### untreatedstagei4 (missing_compartments)
- **Source:** inference
- **Primary name:** untreatedstagei4
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### artstagea1 (missing_compartments)
- **Source:** inference
- **Primary name:** artstagea1
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### artstagea2 (missing_compartments)
- **Source:** inference
- **Primary name:** artstagea2
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### artstagea3 (missing_compartments)
- **Source:** inference
- **Primary name:** artstagea3
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### artstagea4 (missing_compartments)
- **Source:** inference
- **Primary name:** artstagea4
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** removed
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### Susceptible->UntreatedStageI1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for Susceptible->UntreatedStageI1
- **Reasoning:** LLM unavailable.

### UntreatedStageI1->UntreatedStageI2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for UntreatedStageI1->UntreatedStageI2
- **Reasoning:** LLM unavailable.

### UntreatedStageI1->ARTStageA1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for UntreatedStageI1->ARTStageA1
- **Reasoning:** LLM unavailable.

### UntreatedStageI2->UntreatedStageI3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for UntreatedStageI2->UntreatedStageI3
- **Reasoning:** LLM unavailable.

### UntreatedStageI2->ARTStageA2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for UntreatedStageI2->ARTStageA2
- **Reasoning:** LLM unavailable.

### UntreatedStageI3->UntreatedStageI4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for UntreatedStageI3->UntreatedStageI4
- **Reasoning:** LLM unavailable.

### UntreatedStageI3->ARTStageA3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for UntreatedStageI3->ARTStageA3
- **Reasoning:** LLM unavailable.

### UntreatedStageI4->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for UntreatedStageI4->Removed
- **Reasoning:** LLM unavailable.

### UntreatedStageI4->ARTStageA4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for UntreatedStageI4->ARTStageA4
- **Reasoning:** LLM unavailable.

### ARTStageA1->ARTStageA2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for ARTStageA1->ARTStageA2
- **Reasoning:** LLM unavailable.

### ARTStageA1->UntreatedStageI1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for ARTStageA1->UntreatedStageI1
- **Reasoning:** LLM unavailable.

### ARTStageA2->ARTStageA3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for ARTStageA2->ARTStageA3
- **Reasoning:** LLM unavailable.

### ARTStageA2->UntreatedStageI2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for ARTStageA2->UntreatedStageI2
- **Reasoning:** LLM unavailable.

### ARTStageA3->ARTStageA4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for ARTStageA3->ARTStageA4
- **Reasoning:** LLM unavailable.

### ARTStageA3->UntreatedStageI3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for ARTStageA3->UntreatedStageI3
- **Reasoning:** LLM unavailable.

### ARTStageA4->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for ARTStageA4->Removed
- **Reasoning:** LLM unavailable.

### ARTStageA4->UntreatedStageI4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for ARTStageA4->UntreatedStageI4
- **Reasoning:** LLM unavailable.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **12**
- Precision **0.8333** | Recall **1.0** | F1 **0.9091**
### Flows
- Gold count: **17** | Candidate: **20**
- Precision **0.85** | Recall **1.0** | F1 **0.9189**
