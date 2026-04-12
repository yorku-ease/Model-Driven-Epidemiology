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
- After fills gaps (re-detected): **26**
- Delta (before - after): **0**
- Delta missing parameters: **0**
- Delta missing compartments: **0**
- Delta missing flows: **0**

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
| **Gap reduction** | 0.0% | 30% |
| **Reference agreement** | 7.7% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **22.3/100** | — |

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
- Filled via **inference**: 0
- **Flagged** for manual review: 52

### untreatedstagei1 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### untreatedstagei2 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### untreatedstagei3 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### untreatedstagei4 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### artstagea1 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### artstagea2 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### artstagea3 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### artstagea4 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### removed (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### Susceptible->UntreatedStageI1 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI1->UntreatedStageI2 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI1->ARTStageA1 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI2->UntreatedStageI3 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI2->ARTStageA2 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI3->UntreatedStageI4 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI3->ARTStageA3 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI4->Removed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI4->ARTStageA4 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA1->ARTStageA2 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA1->UntreatedStageI1 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA2->ARTStageA3 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA2->UntreatedStageI2 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA3->ARTStageA4 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA3->UntreatedStageI3 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA4->Removed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA4->UntreatedStageI4 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### untreatedstagei1 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### untreatedstagei2 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### untreatedstagei3 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### untreatedstagei4 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### artstagea1 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### artstagea2 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### artstagea3 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### artstagea4 (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### removed (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### Susceptible->UntreatedStageI1 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI1->UntreatedStageI2 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI1->ARTStageA1 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI2->UntreatedStageI3 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI2->ARTStageA2 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI3->UntreatedStageI4 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI3->ARTStageA3 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI4->Removed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### UntreatedStageI4->ARTStageA4 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA1->ARTStageA2 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA1->UntreatedStageI1 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA2->ARTStageA3 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA2->UntreatedStageI2 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA3->ARTStageA4 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA3->UntreatedStageI3 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA4->Removed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ARTStageA4->UntreatedStageI4 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **3**
- Precision **0.3333** | Recall **0.1** | F1 **0.1538**
### Flows
- Gold count: **17** | Candidate: **3**
- Precision **0.0** | Recall **0.0** | F1 **0.0**
