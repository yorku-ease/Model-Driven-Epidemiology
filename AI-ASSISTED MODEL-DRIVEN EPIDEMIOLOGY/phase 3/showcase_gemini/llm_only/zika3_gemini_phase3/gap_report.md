# Phase 3 Gap Analysis Report

**Disease / context:** zika3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **12**
- Missing compartments: 7
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 5
- Extra compartments (not in gold standard): 7
- Extra parameters (not in gold standard): 8
- Extra flows (not in gold standard): 8

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **12**
- After fills gaps (re-detected): **12**
- Delta (before - after): **0**
- Delta missing parameters: **0**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **susceptiblehumans** — Present in gold standard but not in extracted model. (severity: high)
- **exposedhumans** — Present in gold standard but not in extracted model. (severity: high)
- **infectioushumans** — Present in gold standard but not in extracted model. (severity: high)
- **recoveredhumans** — Present in gold standard but not in extracted model. (severity: high)
- **susceptiblemosquitoes** — Present in gold standard but not in extracted model. (severity: high)
- **exposedmosquitoes** — Present in gold standard but not in extracted model. (severity: high)
- **infectiousmosquitoes** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **SusceptibleHumans->ExposedHumans** — Present in gold standard but not in extracted model (or flows list).
- **ExposedHumans->InfectiousHumans** — Present in gold standard but not in extracted model (or flows list).
- **InfectiousHumans->RecoveredHumans** — Present in gold standard but not in extracted model (or flows list).
- **SusceptibleMosquitoes->ExposedMosquitoes** — Present in gold standard but not in extracted model (or flows list).
- **ExposedMosquitoes->InfectiousMosquitoes** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: susceptible humans, exposed humans, infectious humans, recovered humans, susceptible mosquitoes, exposed mosquitoes, infectious mosquitoes
- Extra parameters: β_h, β_v, 1/α_h, 1/α_v, 1/γ, 1/δ, ϕ, r_0

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 0.0% | 30% |
| **Reference agreement** | 88.5% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **46.5/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 7 | 0 | 5 | 12 |
| **Extra in model** | Model items not in reference (noise/convention) | 7 | 8 | 8 | 23 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 24
- **Flagged** for manual review: 0

### susceptiblehumans (missing_compartments)
- **Source:** inference
- **Primary name:** susceptiblehumans
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### exposedhumans (missing_compartments)
- **Source:** inference
- **Primary name:** exposedhumans
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### infectioushumans (missing_compartments)
- **Source:** inference
- **Primary name:** infectioushumans
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### recoveredhumans (missing_compartments)
- **Source:** inference
- **Primary name:** recoveredhumans
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** susceptiblemosquitoes
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** exposedmosquitoes
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** infectiousmosquitoes
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### SusceptibleHumans->ExposedHumans (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for SusceptibleHumans->ExposedHumans
- **Reasoning:** LLM unavailable.

### ExposedHumans->InfectiousHumans (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for ExposedHumans->InfectiousHumans
- **Reasoning:** LLM unavailable.

### InfectiousHumans->RecoveredHumans (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for InfectiousHumans->RecoveredHumans
- **Reasoning:** LLM unavailable.

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for SusceptibleMosquitoes->ExposedMosquitoes
- **Reasoning:** LLM unavailable.

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for ExposedMosquitoes->InfectiousMosquitoes
- **Reasoning:** LLM unavailable.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **7**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **5** | Candidate: **8**
- Precision **0.625** | Recall **1.0** | F1 **0.7692**
