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
| **Gap reduction** | 100.0% | 25% |
| **Reference agreement** | 95.8% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 0.0% | 15% |
| **Structural integrity** | 26.5%  (34→25 errors) | 15% |
| **→ Composite** | **52.9/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 6 | 0 | +6 |
| High | 2 | 1 | +1 |
| Medium | 26 | 24 | +2 |
| **Total** | **34** | **25** | **+9** |

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

**25 structural error(s) remaining after repair:**

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
| `orphaned_parameters` | λv^v | **medium** |
| `orphaned_parameters` | λv^s | **medium** |
| `orphaned_parameters` | λh^v | **medium** |
| `orphaned_parameters` | λh^s | **medium** |
| `orphaned_parameters` | λm | **medium** |
| `orphaned_parameters` | μ2 | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 7 | 0 | 7 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 14 | 2 | 16 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 7
- **Flagged** for manual review: 0

### μ1 (missing_parameters)
- **Source:** inference
- **Value:** 1e-05 per day
- **Confidence:** LOW

### λv^v (missing_parameters)
- **Source:** inference
- **Value:** 0.0001 per person
- **Confidence:** LOW

### λv^s (missing_parameters)
- **Source:** inference
- **Value:** 0.0001 per day
- **Confidence:** LOW

### λh^v (missing_parameters)
- **Source:** inference
- **Value:** 1e-05 per day
- **Confidence:** LOW

### λh^s (missing_parameters)
- **Source:** inference
- **Value:** 1e-05 per person per day
- **Confidence:** LOW

### λm (missing_parameters)
- **Source:** inference
- **Value:** 0.0001 per day
- **Confidence:** LOW

### μ2 (missing_parameters)
- **Source:** inference
- **Value:** 1e-05 per day
- **Confidence:** LOW

## 6. Fill validation (vs gold standard)
- Parameters compared: **7**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **7**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| μ1 | 1e-05 | 0.001384 | 99.28% | poor |
| λv^v | 0.0001 | 0.0 | 100.0% | poor |
| λv^s | 0.0001 | 0.0 | 100.0% | poor |
| λh^v | 1e-05 | 0.0 | 100.0% | poor |
| λh^s | 1e-05 | 0.0 | 100.0% | poor |
| λm | 0.0001 | 0.0 | 100.0% | poor |
| μ2 | 1e-05 | 0.1 | 99.99% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **10**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **11** | Candidate: **13**
- Precision **0.8462** | Recall **1.0** | F1 **0.9167**
