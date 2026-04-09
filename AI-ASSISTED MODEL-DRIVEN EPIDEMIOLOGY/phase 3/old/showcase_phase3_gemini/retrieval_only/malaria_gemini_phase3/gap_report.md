# Phase 3 Gap Analysis Report

**Disease / context:** malaria

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
- After fills gaps (re-detected): **5**
- Delta (before - after): **2**
- Delta missing parameters: **2**
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
| **Gap reduction** | 28.6% | 25% |
| **Reference agreement** | 95.8% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 15% |
| **Structural integrity** | 31.0%  (29→20 errors) | 15% |
| **→ Composite** | **50.8/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 6 | 0 | +6 |
| High | 2 | 1 | +1 |
| Medium | 21 | 19 | +2 |
| **Total** | **29** | **20** | **+9** |

**10 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible Humans -> ContactFlow | Redirected Susceptible Humans ContactFlow target from //@compartments.2 to //@compartments.3 (Non-Ve |
| `self_referential_flow` | Susceptible Humans -> ContactFlow | Redirected Susceptible Humans ContactFlow target from //@compartments.3 to //@compartments.2 (Vector |
| `self_referential_flow` | Vaccinated Humans -> ContactFlow | Redirected Vaccinated Humans ContactFlow target from //@compartments.2 to //@compartments.3 (Non-Vec |
| `self_referential_flow` | Vaccinated Humans -> ContactFlow | Redirected Vaccinated Humans ContactFlow target from //@compartments.3 to //@compartments.2 (Vector- |
| `self_referential_flow` | Susceptible Mosquitoes -> ContactFlow | Redirected Susceptible Mosquitoes ContactFlow target from //@compartments.8 to //@compartments.2 (Ve |
| `zero_population_all` | all_compartments | Set Susceptible Humans population=1000 |
| `missing_birth_sources` | Susceptible Mosquitoes | Set Susceptible Mosquitoes population=1000 (initial condition) |
| `flow_chain_incomplete` | Infectious Mosquitoes | Added flow Exposed Mosquitoes → Infectious Humans |
| `orphaned_parameters` | θ | Wired θ (//@parameters.4) to Vaccinated Humans → Susceptible Humans flow |
| `flow_chain_incomplete` | Infectious Mosquitoes | Added flow Exposed Mosquitoes → Infectious Humans |

**20 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `flow_chain_incomplete` | Infectious Mosquitoes | **high** |
| `orphaned_parameters` | π | **medium** |
| `orphaned_parameters` | µ1 | **medium** |
| `orphaned_parameters` | µ2 | **medium** |
| `orphaned_parameters` | ε | **medium** |
| `orphaned_parameters` | ηS | **medium** |
| `orphaned_parameters` | ξV | **medium** |
| `orphaned_parameters` | ξS | **medium** |
| `orphaned_parameters` | τ2 | **medium** |
| `orphaned_parameters` | δ1 | **medium** |
| `orphaned_parameters` | σ | **medium** |
| `orphaned_parameters` | ρ | **medium** |
| `orphaned_parameters` | φ | **medium** |
| `orphaned_parameters` | β1 | **medium** |
| `orphaned_parameters` | β2 | **medium** |
| `orphaned_parameters` | α1 | **medium** |
| `orphaned_parameters` | α2 | **medium** |
| `orphaned_parameters` | β3 | **medium** |
| `orphaned_parameters` | μ1 | **medium** |
| `orphaned_parameters` | μ2 | **medium** |


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
- Filled via **inference**: 0
- **Flagged** for manual review: 10

### λv^v (missing_parameters)
- **Source:** flagged
- **Action:** manual_review — Could not fill parameters gap automatically.

### λv^s (missing_parameters)
- **Source:** flagged
- **Action:** manual_review — Could not fill parameters gap automatically.

### λh^v (missing_parameters)
- **Source:** flagged
- **Action:** manual_review — Could not fill parameters gap automatically.

### λh^s (missing_parameters)
- **Source:** flagged
- **Action:** manual_review — Could not fill parameters gap automatically.

### λm (missing_parameters)
- **Source:** flagged
- **Action:** manual_review — Could not fill parameters gap automatically.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **10**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **11** | Candidate: **13**
- Precision **0.8462** | Recall **1.0** | F1 **0.9167**
