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
| **Reference agreement** | 83.8% | 30% |
| **Fill traceability** | 66.7% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **88.5/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 3 | 7 | 3 | 13 |
| **Extra in model** | Model items not in reference (noise/convention) | 3 | 10 | 3 | 16 |

## 5. Gap filling results
- Filled via **RAG**: 12
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 4
- **Flagged** for manual review: 0

### exposed female adults (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Female Adults

### Susceptible female adults->Exposed female adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Exposed female adults->Infectious female adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **14**
- Precision **0.7143** | Recall **1.0** | F1 **0.8333**
### Flows
- Gold count: **8** | Candidate: **11**
- Precision **0.7273** | Recall **1.0** | F1 **0.8421**
