# Phase 3 Gap Analysis Report

**Disease / context:** dengue

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **4**
- Missing compartments: 1
- Missing parameters: 2
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 1
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 2
- Extra flows (not in gold standard): 4

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **4**
- After fills gaps (re-detected): **0**
- Delta (before - after): **4**
- Delta missing parameters: **2**
- Delta missing compartments: **1**
- Delta missing flows: **1**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **susceptible mosquitoes** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible mosquitoes->Infectious mosquitoes** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **γ1** — Present in gold standard but not in extracted model.
- **γ2** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: γ_1(a), γ_2(a)

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 25% |
| **Reference agreement** | 90.9% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 0.0% | 15% |
| **Structural integrity** | 41.7%  (12→7 errors) | 15% |
| **→ Composite** | **54.0/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 4 | 1 | +3 |
| High | 1 | 0 | +1 |
| Medium | 7 | 6 | +1 |
| **Total** | **12** | **7** | **+5** |

**7 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible Humans -> ContactFlow | Annotated Susceptible Humans ContactFlow as direct S→I (no Exposed stage) |
| `self_referential_flow` | Asymptomatic Infectious Humans -> ContactFlow | Redirected Asymptomatic Infectious Humans ContactFlow target from //@compartments.2 to //@compartmen |
| `self_referential_flow` | Infectious Mosquitoes -> ContactFlow | Redirected Infectious Mosquitoes ContactFlow target from //@compartments.5 to //@compartments.1 (Exp |
| `zero_population_all` | all_compartments | Set Susceptible Humans population=1000 |
| `missing_birth_sources` | Susceptible Mosquitoes | Set Susceptible Mosquitoes population=1000 (initial condition) |
| `orphaned_parameters` | β_v(a) | Wired β_v(a) (//@parameters.6) to Susceptible Mosquitoes → Infectious Mosquitoes flow |
| `self_referential_flow` | Susceptible Humans -> ContactFlow | Annotated Susceptible Humans ContactFlow as direct S→I (no Exposed stage) |

**7 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `self_referential_flow` | Susceptible Humans -> ContactFlow | **critical** |
| `orphaned_parameters` | μ_v | **medium** |
| `orphaned_parameters` | β_h(a) | **medium** |
| `orphaned_parameters` | ρ | **medium** |
| `orphaned_parameters` | σ | **medium** |
| `orphaned_parameters` | γ1 | **medium** |
| `orphaned_parameters` | γ2 | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 2 | 1 | 4 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 2 | 4 | 6 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 4
- **Flagged** for manual review: 0

### susceptible mosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes

### γ1 (missing_parameters)
- **Source:** inference
- **Value:** 0.143 per day
- **Confidence:** LOW

### γ2 (missing_parameters)
- **Source:** inference
- **Value:** 0.143 per day
- **Confidence:** LOW

### Susceptible mosquitoes->Infectious mosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The transition of a susceptible

## 6. Fill validation (vs gold standard)
- Parameters compared: **2**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **2**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **71.4%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| γ1 | 0.143 | 0.5 | 71.4% | poor |
| γ2 | 0.143 | 0.5 | 71.4% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **7**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **7** | Candidate: **13**
- Precision **0.6923** | Recall **1.0** | F1 **0.8182**
