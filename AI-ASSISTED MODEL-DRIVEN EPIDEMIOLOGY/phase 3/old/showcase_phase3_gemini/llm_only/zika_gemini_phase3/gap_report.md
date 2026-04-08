# Phase 3 Gap Analysis Report

**Disease / context:** zika

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
- After fills gaps (re-detected): **3**
- Delta (before - after): **10**
- Delta missing parameters: **7**
- Delta missing compartments: **2**
- Delta missing flows: **1**

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
| **Gap reduction** | 76.9% | 25% |
| **Reference agreement** | 72.8% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 15% |
| **Structural integrity** | 34.4%  (32→21 errors) | 15% |
| **→ Composite** | **57.6/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 3 | 0 | +3 |
| High | 5 | 1 | +4 |
| Medium | 24 | 20 | +4 |
| **Total** | **32** | **21** | **+11** |

**12 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible Humans -> ContactFlow | Redirected Susceptible Humans ContactFlow target from //@compartments.1 to //@compartments.8 (Expose |
| `self_referential_flow` | Susceptible Vectors -> ContactFlow | Redirected Susceptible Vectors ContactFlow target from //@compartments.8 to //@compartments.1 (Expos |
| `zero_population_all` | all_compartments | Set Susceptible Humans population=1000 |
| `missing_birth_sources` | Susceptible Humans | Set Susceptible Vectors population=1000 (initial condition) |
| `flow_chain_incomplete` | Infectious Vectors | Added flow Infectious Vectors → Recovered Humans |
| `flow_chain_incomplete` | Pregnant Women | Added flow Pregnant Women → Recovered Humans |
| `flow_chain_incomplete` | Infectious Female Adults | Added flow Infectious Female Adults → Recovered Humans |
| `flow_chain_incomplete` | Pregnant Exposed | Added flow Pregnant Women → Recovered Humans |
| `orphaned_parameters` | 1/α_H | Wired 1/α_H (//@parameters.1) to Eggs → Larvae flow |
| `orphaned_parameters` | 1/α_V | Wired 1/α_V (//@parameters.2) to Larvae → Pupae flow |
| `orphaned_parameters` | 1/τ_h | Wired 1/τ_h (//@parameters.19) to Pupae → Susceptible Female Adults flow |
| `flow_chain_incomplete` | Pregnant Exposed | Added flow Pregnant Women → Recovered Humans |

**21 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `flow_chain_incomplete` | Pregnant Exposed | **high** |
| `orphaned_parameters` | e_V | **medium** |
| `orphaned_parameters` | 1/τ1 | **medium** |
| `orphaned_parameters` | μ1 | **medium** |
| `orphaned_parameters` | 1/τ2 | **medium** |
| `orphaned_parameters` | μ2 | **medium** |
| `orphaned_parameters` | 1/τ3 | **medium** |
| `orphaned_parameters` | μ3 | **medium** |
| `orphaned_parameters` | μ_V | **medium** |
| `orphaned_parameters` | K_H | **medium** |
| `orphaned_parameters` | K_V | **medium** |
| `orphaned_parameters` | κ | **medium** |
| `orphaned_parameters` | λ | **medium** |
| `orphaned_parameters` | R0 | **medium** |
| `orphaned_parameters` | b_v | **medium** |
| `orphaned_parameters` | 1/τ_v | **medium** |
| `orphaned_parameters` | r_h | **medium** |
| `orphaned_parameters` | f | **medium** |
| `orphaned_parameters` | δ_l | **medium** |
| `orphaned_parameters` | φ | **medium** |
| `parameter_layer_contamination` | R0 | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 3 | 7 | 3 | 13 |
| **Extra in model** | Model items not in reference (noise/convention) | 3 | 10 | 3 | 16 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 16
- **Flagged** for manual review: 0

### exposed female adults (missing_compartments)
- **Source:** inference
- **Primary name:** Pregnant Exposed
- **Reasoning:** The model

### Susceptible female adults->Exposed female adults (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible female adults transition to the exposed compartment after being bitten by infectious Aedes mosquitoes.
- **Reasoning:** The paper explicitly states that ZIKV is

### Exposed female adults->Infectious female adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed female adults complete their intrinsic incubation period and become infectious, capable of transmitting the Zika virus.
- **Reasoning:** This transition represents the progression of infected individuals from the latent (exposed) state to the infectious state, which is a time-dependent biological process typically modeled as a rate.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **14**
- Precision **0.6429** | Recall **0.9** | F1 **0.75**
### Flows
- Gold count: **8** | Candidate: **9**
- Precision **0.6667** | Recall **0.75** | F1 **0.7059**
