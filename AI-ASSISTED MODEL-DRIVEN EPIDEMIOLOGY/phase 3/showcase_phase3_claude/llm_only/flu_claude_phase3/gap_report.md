# Phase 3 Gap Analysis Report

**Disease / context:** flu

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **1**
- Missing compartments: 0
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 1
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 6
- Extra flows (not in gold standard): 0

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **1**
- After fills gaps (re-detected): **1**
- Delta (before - after): **0**
- Delta missing parameters: **0**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- **Infectious->Removed** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: π, λ0, ψ, p_icu, µ_icu|e, n

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 0.0% | 25% |
| **Reference agreement** | 83.9% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 15% |
| **Structural integrity** | 47.4%  (19→10 errors) | 15% |
| **→ Composite** | **43.1/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 2 | 0 | +2 |
| High | 3 | 2 | +1 |
| Medium | 14 | 8 | +6 |
| **Total** | **19** | **10** | **+9** |

**10 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible -> ContactFlow | Redirected Susceptible ContactFlow target from //@compartments.1 to //@compartments.2 (Exposed 2) |
| `zero_population_all` | all_compartments | Set Susceptible population=1000 |
| `flow_chain_incomplete` | Infectious 2 | Added flow Infectious 1 → Recovered |
| `parameter_layer_contamination` | π | Cleared contaminated expression for π |
| `parameter_layer_contamination` | λ0 | Cleared contaminated expression for λ0 |
| `parameter_layer_contamination` | ψ | Cleared contaminated expression for ψ |
| `parameter_layer_contamination` | β | Cleared contaminated expression for β |
| `parameter_layer_contamination` | p_ICU | Cleared contaminated expression for p_ICU |
| `parameter_layer_contamination` | η | Cleared contaminated expression for η |
| `flow_chain_incomplete` | Infectious 2 | Added flow Infectious 1 → Recovered |

**10 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `missing_death_sinks` | model | **high** |
| `flow_chain_incomplete` | Infectious 2 | **high** |
| `orphaned_parameters` | π | **medium** |
| `orphaned_parameters` | λ0 | **medium** |
| `orphaned_parameters` | ψ | **medium** |
| `orphaned_parameters` | p_ICU | **medium** |
| `orphaned_parameters` | η | **medium** |
| `orphaned_parameters` | µ_ICU|E | **medium** |
| `orphaned_parameters` | σ^2_ICU|E | **medium** |
| `orphaned_parameters` | N | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 0 | 1 | 1 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 6 | 0 | 6 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 2
- **Flagged** for manual review: 0

### Infectious->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovery or removal of infectious individuals from the infectious compartment, representing individuals who recover from flu (gaining immunity) or are otherwise removed (e.g., death or isolation), transitioning them to the Removed compartment.
- **Reasoning:** In a standard SIR-type compartmental model for influenza, infectious individuals leave the infectious compartment at a rate proportional to the number of infectious individuals (typically governed by the recovery rate γ), which is a simple rate-dependent process rather than a contact-dependent one.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **4** | Candidate: **6**
- Precision **0.8333** | Recall **0.75** | F1 **0.7895**
### Flows
- Gold count: **5** | Candidate: **4**
- Precision **1.0** | Recall **0.8** | F1 **0.8889**
