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
- **Reasoning:** The model’s missing human compartment corresponds to the susceptible adult female population in the Zika host structure, which is the key at-risk group emphasized in the paper context.

### exposed female adults (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Female Adults
- **Reasoning:** The missing compartment refers to the human exposed class in the Zika SEIR host model, specifically the exposed female adult subgroup.

### infectious female adults (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Female Adults
- **Reasoning:** The missing compartment in the Zika compartmental model corresponds to the infectious adult female vector population, which transmits ZIKV to humans.

### 1/τ_h (missing_parameters)
- **Source:** inference
- **Value:** 0.2 day^-1
- **Reasoning:** For Zika, the human infectious period is typically about 5 days, so the recovery/removal rate 1/τ_h is plausibly around 0.2 per day. Reported symptomatic and viremic periods commonly fall in the ~3–7 day range, corresponding to roughly 0.14–0.33 day^-1.
- **Confidence:** LOW

### b_v (missing_parameters)
- **Source:** inference
- **Value:** 0.5 per day
- **Reasoning:** For Zika vector-borne transmission models, a mosquito biting/contact rate on the order of 0.3–1.0 bites per mosquito per day is commonly used; 0.5/day is a reasonable mid-range default for Aedes mosquitoes. This reflects frequent daytime biting behavior while staying within typical arboviral model assumptions.
- **Confidence:** LOW

### 1/τ_v (missing_parameters)
- **Source:** inference
- **Value:** 10 days
- **Reasoning:** For Zika, the intrinsic incubation period in humans is typically about 3–14 days, with a central value around 5–10 days. If 1/τ_v is being used as a latent/incubation timescale in the vector-related SEI/SEIR framework, a default of about 10 days is a reasonable conservative midpoint within common epidemiologic ranges.
- **Confidence:** LOW

### r_h (missing_parameters)
- **Source:** inference
- **Value:** 0.1 1/day
- **Reasoning:** For Zika SEIR-type human models, the human recovery rate is typically on the order of 1/7 to 1/14 per day, reflecting about 1–2 weeks of infectiousness/viremia. A default of 0.1/day corresponds to an average infectious period of about 10 days, which is biologically plausible.
- **Confidence:** LOW

### f (missing_parameters)
- **Source:** inference
- **Value:** 0.5 dimensionless
- **Reasoning:** For Zika compartmental models, a parameter f is commonly used as the fraction of exposed humans who become symptomatic or progress to the clinically apparent infectious class. A default of 0.5 is a plausible midpoint within typical epidemiologic ranges, since a substantial fraction of Zika infections are asymptomatic but many models assume roughly half are symptomatic for baseline analyses.
- **Confidence:** LOW

### δ_l (missing_parameters)
- **Source:** inference
- **Value:** 0.1 1/day
- **Reasoning:** A plausible default for δ_l is a low-to-moderate per-capita transition/removal rate on the order of weeks, consistent with typical Zika-related compartmental models where latent or progression processes occur over several days to a couple of weeks. Using 0.1/day corresponds to an average timescale of about 10 days, which is biologically reasonable for ZIKV dynamics.
- **Confidence:** LOW

### φ (missing_parameters)
- **Source:** inference
- **Value:** 0.5 1/day
- **Reasoning:** For Zika compartmental models, φ is commonly used as the progression rate from exposed to infectious, so a plausible default is the inverse of the intrinsic incubation period. Zika incubation is typically about 3–14 days, giving a central daily rate near 0.5/day.
- **Confidence:** LOW

### Pupae (non-infectious)->Susceptible female adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Maturation of non-infectious pupae into susceptible female adult mosquitoes entering the vector population
- **Reasoning:** This is a demographic development transition in the mosquito life cycle, not an infection-driven contact process.

### Susceptible female adults->Exposed female adults (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Female susceptible adults become exposed after being bitten by infectious Zika-infected mosquitoes, initiating infection in the adult female human compartment.
- **Reasoning:** Zika is primarily mosquito-borne, so the transition from susceptible to exposed in adult females is driven by infectious vector contact rather than an internal progression rate.

### Exposed female adults->Infectious female adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of exposed female adults to infectious female adults after the intrinsic incubation period of Zika infection.
- **Reasoning:** In the SEIR human model, exposed individuals become infectious at the incubation rate, so exposed female adults transition to infectious female adults.

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
| 1/τ_v | 10.0 | 0.125 | 7900.0% | poor |
| r_h | 0.1 | 5e-05 | 199900.0% | poor |
| f | 0.5 | 80.0 | 99.38% | poor |
| δ_l | 0.1 | 1.0 | 90.0% | poor |
| φ | 0.5 | 0.68 | 26.47% | approximate |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **13**
- Precision **0.7692** | Recall **1.0** | F1 **0.8696**
### Flows
- Gold count: **8** | Candidate: **11**
- Precision **0.7273** | Recall **1.0** | F1 **0.8421**
