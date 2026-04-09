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
- After fills gaps (re-detected): **25**
- Delta (before - after): **1**
- Delta missing parameters: **0**
- Delta missing compartments: **1**
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
| **Gap reduction** | 3.8% | 30% |
| **Reference agreement** | 69.7% | 30% |
| **Fill traceability** | 68.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **55.7/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 9 | 0 | 17 | 26 |
| **Extra in model** | Model items not in reference (noise/convention) | 2 | 13 | 3 | 18 |

## 5. Gap filling results
- Filled via **RAG**: 34
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 17
- **Flagged** for manual review: 0

### untreatedstagei1 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### untreatedstagei2 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage II

### untreatedstagei3 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### untreatedstagei4 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### artstagea1 (missing_compartments)
- **Source:** inference
- **Primary name:** On Antiretroviral Therapy

### artstagea2 (missing_compartments)
- **Source:** inference
- **Primary name:** ART Stage
- **Reasoning:** The

### artstagea3 (missing_compartments)
- **Source:** inference
- **Primary name:** CD4 Stages

### artstagea4 (missing_compartments)
- **Source:** inference
- **Primary name:** Disease Stage
- **Reasoning:** The excerpt

### Susceptible->UntreatedStageI1 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI1->UntreatedStageI2 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI1->ARTStageA1 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 2 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI2->UntreatedStageI3 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI2->ARTStageA2 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI3->UntreatedStageI4 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI3->ARTStageA3 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI4->Removed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI4->ARTStageA4 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA1->ARTStageA2 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA1->UntreatedStageI1 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA2->ARTStageA3 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA2->UntreatedStageI2 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA3->ARTStageA4 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA3->UntreatedStageI3 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA4->Removed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA4->UntreatedStageI4 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **10**
- Precision **0.4** | Recall **1.0** | F1 **0.5714**
### Flows
- Gold count: **17** | Candidate: **10**
- Precision **0.7** | Recall **1.0** | F1 **0.8235**
