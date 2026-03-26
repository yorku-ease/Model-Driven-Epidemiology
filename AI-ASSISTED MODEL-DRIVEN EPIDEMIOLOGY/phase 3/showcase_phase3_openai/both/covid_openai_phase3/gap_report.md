# Phase 3 Gap Analysis Report

**Disease / context:** covid

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **2**
- Missing compartments: 1
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 1
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 36
- Extra flows (not in gold standard): 1

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **2**
- After fills gaps (re-detected): **0**
- Delta (before - after): **2**
- Delta missing parameters: **0**
- Delta missing compartments: **1**
- Delta missing flows: **1**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **covid deaths** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **ICU->COVID Deaths** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: dead
- Extra parameters: latent period, presymptomatic infectious period, infectious period mild to moderate, infectious period severe, r0, quarantine duration, relative risk of transmission for isolated cases, hospital length of stay non-icu, hospital length of stay pre-icu, icu length of stay, hospital length of stay post-icu, probability of severe infection, <15 yr, no comorbidities, probability of severe infection, 15-49 yr, no comorbidities, probability of severe infection, 50-69 yr, no comorbidities, probability of severe infection, >=70 yr, no comorbidities, probability of severe infection, <15 yr, comorbidities, probability of severe infection, 15-49 yr, comorbidities, probability of severe infection, 50-69 yr, comorbidities, probability of severe infection, >=70 yr, comorbidities, probability severe case requires icu, probability of death in icu, <15 yr, no comorbidities, probability of death in icu, 15-49 yr, no comorbidities, probability of death in icu, 50-69 yr, no comorbidities, probability of death in icu, >=70 yr, no comorbidities, probability of death in icu, <15 yr, comorbidities, probability of death in icu, 15-49 yr, comorbidities, probability of death in icu, 50-69 yr, comorbidities, probability of death in icu, >=70 yr, comorbidities, base case testing and isolation rate, <15 yr, base case testing and isolation rate, 15-49 yr, base case testing and isolation rate, >=50 yr, base case quarantine rate, base case physical distancing reduction, physical distancing contact reduction (restrictive), physical distancing contact reduction (combination), icu threshold for dynamic intervention

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 25% |
| **Reference agreement** | 97.2% | 25% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 100.0% | 15% |
| **Structural integrity** | 51.3%  (39→19 errors) | 15% |
| **→ Composite** | **82.0/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 3 | 0 | +3 |
| High | 3 | 0 | +3 |
| Medium | 33 | 19 | +14 |
| **Total** | **39** | **19** | **+20** |

**19 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible -> ContactFlow | Redirected Susceptible ContactFlow target from //@compartments.1 to //@compartments.2 (Exposed Quara |
| `self_referential_flow` | Susceptible -> ContactFlow | Redirected Susceptible ContactFlow target from //@compartments.2 to //@compartments.1 (Exposed) |
| `zero_population_all` | all_compartments | Set Susceptible population=1000 |
| `flow_chain_incomplete` | Dead | Added flow Dead → Recovered |
| `flow_chain_incomplete` | covid deaths | Added flow covid deaths → Recovered |
| `orphaned_parameters` | Presymptomatic infectious period | Wired Presymptomatic infectious period (//@parameters.1) to Infectious Mild to Moderate → Infectious |
| `orphaned_parameters` | Infectious period severe | Wired Infectious period severe (//@parameters.3) to Infectious Severe → Infectious Severe Isolated f |
| `orphaned_parameters` | R0 | Wired R0 (//@parameters.4) to Admitted to Hospital → Recovered flow |
| `orphaned_parameters` | Quarantine duration | Wired Quarantine duration (//@parameters.5) to Infectious Mild to Moderate → Recovered flow |
| `orphaned_parameters` | Hospital length of stay non-ICU | Wired Hospital length of stay non-ICU (//@parameters.7) to Infectious Severe → Admitted to Hospital  |
| `orphaned_parameters` | Hospital length of stay pre-ICU | Wired Hospital length of stay pre-ICU (//@parameters.8) to Infectious Severe → Admitted to Hospital  |
| `orphaned_parameters` | ICU length of stay | Wired ICU length of stay (//@parameters.9) to Infectious Severe Isolated → Admitted to Hospital flow |
| `orphaned_parameters` | Hospital length of stay post-ICU | Wired Hospital length of stay post-ICU (//@parameters.10) to ICU → Admitted to Hospital Post-ICU flo |
| `orphaned_parameters` | Probability of severe infection, <15 yr, no comorbidities | Wired Probability of severe infection, <15 yr, no comorbidities (//@parameters.11) to Admitted to Ho |
| `orphaned_parameters` | Probability of severe infection, 15-49 yr, no comorbidities | Wired Probability of severe infection, 15-49 yr, no comorbidities (//@parameters.12) to Admitted to  |
| `orphaned_parameters` | Probability of severe infection, 50-69 yr, no comorbidities | Wired Probability of severe infection, 50-69 yr, no comorbidities (//@parameters.13) to Infectious S |
| `orphaned_parameters` | Probability of severe infection, >=70 yr, no comorbidities | Wired Probability of severe infection, >=70 yr, no comorbidities (//@parameters.14) to Infectious Mi |
| `orphaned_parameters` | Probability of severe infection, <15 yr, comorbidities | Wired Probability of severe infection, <15 yr, comorbidities (//@parameters.15) to Admitted to Hospi |
| `orphaned_parameters` | Probability severe case requires ICU | Wired Probability severe case requires ICU (//@parameters.19) to ICU → Dead flow |

**19 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `orphaned_parameters` | Probability of severe infection, 15-49 yr, comorbidities | **medium** |
| `orphaned_parameters` | Probability of severe infection, 50-69 yr, comorbidities | **medium** |
| `orphaned_parameters` | Probability of severe infection, >=70 yr, comorbidities | **medium** |
| `orphaned_parameters` | Probability of death in ICU, <15 yr, no comorbidities | **medium** |
| `orphaned_parameters` | Probability of death in ICU, 15-49 yr, no comorbidities | **medium** |
| `orphaned_parameters` | Probability of death in ICU, 50-69 yr, no comorbidities | **medium** |
| `orphaned_parameters` | Probability of death in ICU, >=70 yr, no comorbidities | **medium** |
| `orphaned_parameters` | Probability of death in ICU, <15 yr, comorbidities | **medium** |
| `orphaned_parameters` | Probability of death in ICU, 15-49 yr, comorbidities | **medium** |
| `orphaned_parameters` | Probability of death in ICU, 50-69 yr, comorbidities | **medium** |
| `orphaned_parameters` | Probability of death in ICU, >=70 yr, comorbidities | **medium** |
| `orphaned_parameters` | Base case testing and isolation rate, <15 yr | **medium** |
| `orphaned_parameters` | Base case testing and isolation rate, 15-49 yr | **medium** |
| `orphaned_parameters` | Base case testing and isolation rate, >=50 yr | **medium** |
| `orphaned_parameters` | Base case quarantine rate | **medium** |
| `orphaned_parameters` | Base case physical distancing reduction | **medium** |
| `orphaned_parameters` | Physical distancing contact reduction (restrictive) | **medium** |
| `orphaned_parameters` | Physical distancing contact reduction (combination) | **medium** |
| `orphaned_parameters` | ICU threshold for dynamic intervention | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 0 | 1 | 2 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 36 | 1 | 38 |

## 5. Gap filling results
- Filled via **RAG**: 1
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 1
- **Flagged** for manual review: 0

### covid deaths (missing_compartments)
- **Source:** inference
- **Primary name:** covid deaths
- **Reasoning:** Inference failed; using expected label.

### ICU->COVID Deaths (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 2 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **8** | Candidate: **16**
- Precision **0.9375** | Recall **1.0** | F1 **0.9677**
### Flows
- Gold count: **11** | Candidate: **22**
- Precision **0.9545** | Recall **1.0** | F1 **0.9767**
