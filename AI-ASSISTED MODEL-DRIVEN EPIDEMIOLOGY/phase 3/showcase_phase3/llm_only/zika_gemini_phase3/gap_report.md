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
| **Gap reduction** | 100.0% | 25% |
| **Reference agreement** | 83.8% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 15% |
| **Structural integrity** | 41.9%  (31→18 errors) | 15% |
| **→ Composite** | **67.2/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 3 | 0 | +3 |
| High | 4 | 0 | +4 |
| Medium | 24 | 18 | +6 |
| **Total** | **31** | **18** | **+13** |

**12 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible Humans -> ContactFlow | Redirected Susceptible Humans ContactFlow target from //@compartments.1 to //@compartments.8 (Expose |
| `self_referential_flow` | Susceptible Vectors -> ContactFlow | Redirected Susceptible Vectors ContactFlow target from //@compartments.8 to //@compartments.1 (Expos |
| `zero_population_all` | all_compartments | Set Susceptible Humans population=1000 |
| `missing_birth_sources` | Susceptible Humans | Set Susceptible Vectors population=1000 (initial condition) |
| `flow_chain_incomplete` | Infectious Vectors | Added flow Infectious Vectors → Recovered Humans |
| `flow_chain_incomplete` | Exposed Pregnant Females | Added flow Exposed Pregnant Females → Infectious Humans |
| `flow_chain_incomplete` | Infectious Female Adults | Added flow Infectious Female Adults → Recovered Humans |
| `orphaned_parameters` | 1/α_H | Wired 1/α_H (//@parameters.1) to Eggs → Larvae flow |
| `orphaned_parameters` | 1/α_V | Wired 1/α_V (//@parameters.2) to Larvae → Pupae flow |
| `orphaned_parameters` | e_V | Wired e_V (//@parameters.4) to Pupae → Susceptible Female Adults flow |
| `orphaned_parameters` | 1/τ_h | Wired 1/τ_h (//@parameters.19) to Susceptible Female Adults → Exposed Female Adults flow |
| `orphaned_parameters` | b_v | Wired b_v (//@parameters.20) to Exposed Female Adults → Infectious Female Adults flow |

**18 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
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
- **Primary name:** Exposed Female Adults

### Susceptible female adults->Exposed female adults (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible female adults become exposed to ZIKV after being bitten by an infectious Aedes mosquito, entering the latent period before becoming infectious.
- **Reasoning:** The transition from susceptible to exposed in humans for ZIKV is driven by contact with infectious vectors, making it a contact-dependent infection process.

### Exposed female adults->Infectious female adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The transition of female adults from the latent (exposed) stage to the infectious stage after completing the intrinsic incubation period.
- **Reasoning:** In an SEIR model, individuals in the Exposed compartment become Infectious after a period of latency, which is typically modeled as a rate-based transition.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **14**
- Precision **0.7143** | Recall **1.0** | F1 **0.8333**
### Flows
- Gold count: **8** | Candidate: **11**
- Precision **0.7273** | Recall **1.0** | F1 **0.8421**
