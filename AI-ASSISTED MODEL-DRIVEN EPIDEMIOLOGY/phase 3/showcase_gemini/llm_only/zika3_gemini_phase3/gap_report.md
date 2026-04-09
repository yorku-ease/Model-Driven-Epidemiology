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
- **Primary name:** Susceptible Humans
- **Reasoning:** The

### exposedhumans (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Humans
- **Reasoning:** Z

### infectioushumans (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Humans

### recoveredhumans (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered
- **Reasoning:** The

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Mosquitoes

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Mosquitoes
- **Reasoning:** The excerpt states ZIKV transmission is predominantly vector-borne, which necessitates a compartment for infectious vectors, specifically mosquitoes, to complete the transmission cycle.

### SusceptibleHumans->ExposedHumans (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible humans become exposed to ZIKV through contact with infected vectors or humans.
- **Reasoning:** The paper states that ZIKV transmission is predominantly vector-borne and can also occur via sexual contact and blood transfusions, indicating contact-based transmission.

### ExposedHumans->InfectiousHumans (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have been infected

### InfectiousHumans->RecoveredHumans (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovery of an infectious human individual from ZIKV infection, leading to immunity.
- **Reasoning:** Recovery from ZIKV infection is an intrinsic process where an individual clears the virus and develops immunity, transitioning out of the

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible mosquitoes become exposed to the zika3 virus after biting an infectious host.
- **Reasoning:** The paper states that ZIKV transmission is predominantly vector-borne, indicating that mosquitoes acquire the infection through contact with an infectious source.

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The progression of mosquitoes from

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **7**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **5** | Candidate: **8**
- Precision **0.625** | Recall **1.0** | F1 **0.7692**
