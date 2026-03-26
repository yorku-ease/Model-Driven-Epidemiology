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
| **Reference agreement** | 85.6% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 0.0% | 15% |
| **Structural integrity** | 36.7%  (30→19 errors) | 15% |
| **→ Composite** | **51.9/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 3 | 0 | +3 |
| High | 3 | 1 | +2 |
| Medium | 24 | 18 | +6 |
| **Total** | **30** | **19** | **+11** |

**12 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible Humans -> ContactFlow | Redirected Susceptible Humans ContactFlow target from //@compartments.1 to //@compartments.8 (Expose |
| `self_referential_flow` | Susceptible Vectors -> ContactFlow | Redirected Susceptible Vectors ContactFlow target from //@compartments.8 to //@compartments.1 (Expos |
| `zero_population_all` | all_compartments | Set Susceptible Humans population=1000 |
| `missing_birth_sources` | Susceptible Humans | Set Susceptible Vectors population=1000 (initial condition) |
| `flow_chain_incomplete` | Infectious Vectors | Added flow Infectious Vectors → Recovered Humans |
| `flow_chain_incomplete` | Infectious Female Adults | Added flow Exposed Female Adults → Infectious Humans |
| `orphaned_parameters` | 1/α_H | Wired 1/α_H (//@parameters.1) to Eggs → Larvae flow |
| `orphaned_parameters` | 1/α_V | Wired 1/α_V (//@parameters.2) to Larvae → Pupae flow |
| `orphaned_parameters` | 1/τ_h | Wired 1/τ_h (//@parameters.19) to Pupae → Susceptible Female Adults flow |
| `orphaned_parameters` | b_v | Wired b_v (//@parameters.20) to Susceptible Female Adults → Exposed Female Adults flow |
| `orphaned_parameters` | 1/τ_v | Wired 1/τ_v (//@parameters.21) to Exposed Female Adults → Infectious Female Adults flow |
| `flow_chain_incomplete` | Infectious Female Adults | Added flow Exposed Female Adults → Infectious Humans |

**19 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `flow_chain_incomplete` | Infectious Female Adults | **high** |
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
- Filled via **inference**: 13
- **Flagged** for manual review: 0

### susceptible female adults (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Female Adults
- **Reasoning:** In Zika models that account for sexual transmission and microcephaly risk, the susceptible human population is often split by sex, with susceptible female adults tracked separately due to the critical importance of vertical transmission and congenital Zika syndrome in pregnant women.

### exposed female adults (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Female Adults
- **Reasoning:** In a Zika model that accounts for sexual transmission and vector-borne spread, the exposed class for female adults is a distinct compartment since Zika has differential impacts on women (particularly pregnant women) due to congenital Zika syndrome, requiring sex-stratified exposed compartments.

### infectious female adults (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Female Adults
- **Reasoning:** In a Zika SEIR host / SEI vector model with dynamic vector population, the vector compartments typically distinguish female adult mosquitoes (since only females bite and transmit), so the missing compartment is the infectious female adult mosquito compartment.

### 1/τ_h (missing_parameters)
- **Source:** inference
- **Value:** 0.2 per day
- **Reasoning:** 1/τ_h represents the rate of recovery (or inverse of the infectious period) for humans infected with Zika virus. The typical human infectious period for Zika is approximately 4-7 days, giving a recovery rate of roughly 1/5 = 0.2 per day.
- **Confidence:** LOW

### b_v (missing_parameters)
- **Source:** inference
- **Value:** 0.5 bites per mosquito per day
- **Reasoning:** The parameter b_v represents the biting rate of Aedes mosquitoes on humans. For Aedes aegypti, the typical biting rate ranges from 0.3 to 1.0 bites per mosquito per day, with 0.5 being a commonly used central estimate in Zika and dengue transmission models.
- **Confidence:** LOW

### 1/τ_v (missing_parameters)
- **Source:** inference
- **Value:** 0.1 per day
- **Reasoning:** 1/τ_v represents the rate at which exposed (latently infected) mosquitoes become infectious, i.e., the inverse of the extrinsic incubation period (EIP) for Zika virus in Aedes mosquitoes. The EIP for Zika in Aedes aegypti is typically around 7-14 days, giving a rate of approximately 0.07-0.14 per day, with 10 days (rate = 0.1/day) being a commonly used central estimate.
- **Confidence:** LOW

### r_h (missing_parameters)
- **Source:** inference
- **Value:** 0.1429 per day
- **Reasoning:** In Zika virus models, r_h typically represents the recovery rate of humans. The average infectious period for Zika is approximately 5-7 days, giving a recovery rate of about 1/7 ≈ 0.1429 per day.
- **Confidence:** LOW

### f (missing_parameters)
- **Source:** inference
- **Value:** 0.5 
- **Reasoning:** In Zika compartmental models, 'f' typically represents the fraction of symptomatic infections (or sometimes the fraction of bites on humans). Since approximately 80% of Zika infections are asymptomatic, if f represents the symptomatic fraction, a value around 0.2 would be appropriate. However, if f represents the fraction of effective bites or a transmission probability modifier, values around 0.5 are commonly used. Given the general SEIR/SEI framework context without further specification, 0.5 is a reasonable default representing a transmission-related fraction.
- **Confidence:** LOW

### δ_l (missing_parameters)
- **Source:** inference
- **Value:** 0.0014 per day
- **Reasoning:** In Zika compartmental models, δ_l typically represents the disease-induced death rate (lethality rate) for humans. Zika has a very low case fatality rate (estimated around 0.01-0.1%), so a plausible daily disease-induced death rate would be on the order of 0.001-0.002 per day, reflecting the rare but possible fatal outcomes particularly associated with neurological complications.
- **Confidence:** LOW

### φ (missing_parameters)
- **Source:** inference
- **Value:** 0.3 dimensionless (proportion)
- **Reasoning:** In Zika compartmental models, φ commonly represents the proportion of symptomatic infections among those infected. Approximately 20-30% of Zika virus infections are symptomatic, with the remainder being asymptomatic, so a value around 0.2-0.3 is typical in the literature.
- **Confidence:** LOW

### Pupae (non-infectious)->Susceptible female adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Emergence of susceptible adult female mosquitoes from the pupal stage, representing the maturation of non-infectious pupae into the susceptible adult vector population
- **Reasoning:** In a dynamic vector population model with explicit aquatic stages, pupae that are not vertically infected emerge as susceptible adult female mosquitoes at a maturation rate, entering the susceptible vector compartment (S_V).

### Susceptible female adults->Exposed female adults (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Transmission of Zika virus from infectious Aedes mosquitoes to susceptible female adults through mosquito bites, moving them into the exposed (latent) compartment
- **Reasoning:** In vector-borne disease models like ZIKV, susceptible humans become exposed through contact with infectious mosquito vectors via biting, which is a frequency-dependent contact process requiring interaction between two populations.

### Exposed female adults->Infectious female adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression from the exposed (latent) state to the infectious state in female adult humans, representing the end of the intrinsic incubation period of Zika virus
- **Reasoning:** The transition from Exposed to Infectious in the human SEIR model is governed by the intrinsic incubation period, which is a fixed per-capita rate (1/incubation period) independent of contact with other compartments, making it a RateFlow.

## 6. Fill validation (vs gold standard)
- Parameters compared: **7**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **1**
- Poor (>50% error): **6**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.38%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| 1/τ_h | 0.2 | 0.5 | 60.0% | poor |
| b_v | 0.5 | 0.001 | 49900.0% | poor |
| 1/τ_v | 0.1 | 0.125 | 20.0% | approximate |
| r_h | 0.1429 | 5e-05 | 285700.0% | poor |
| f | 0.5 | 80.0 | 99.38% | poor |
| δ_l | 0.0014 | 1.0 | 99.86% | poor |
| φ | 0.3 | 0.68 | 55.88% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **13**
- Precision **0.7692** | Recall **1.0** | F1 **0.8696**
### Flows
- Gold count: **8** | Candidate: **11**
- Precision **0.7273** | Recall **1.0** | F1 **0.8421**
