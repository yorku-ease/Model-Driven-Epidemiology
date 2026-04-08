# Phase 3 Gap Analysis Report

**Disease / context:** tuberculosis

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **4**
- Missing compartments: 0
- Missing parameters: 1
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 3
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 1
- Extra flows (not in gold standard): 0

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **4**
- After fills gaps (re-detected): **3**
- Delta (before - after): **1**
- Delta missing parameters: **1**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->Infected** — Present in gold standard but not in extracted model (or flows list).
- **Infected->Recovered** — Present in gold standard but not in extracted model (or flows list).
- **Recovered->Infected** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **π** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: ϵ

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 25.0% | 25% |
| **Reference agreement** | 70.8% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 15% |
| **Structural integrity** | 54.5%  (11→5 errors) | 15% |
| **→ Composite** | **47.1/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 2 | 0 | +2 |
| High | 1 | 0 | +1 |
| Medium | 8 | 5 | +3 |
| **Total** | **11** | **5** | **+6** |

**3 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `zero_population_all` | all_compartments | Set Susceptible population=1000 |
| `uniform_parameter_collapse` | all_flows | Reassigned parameters for 2 flow(s) using semantic matching |
| `flow_chain_incomplete` | Infectious | Added flow Infectious → Recovered |

**5 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `orphaned_parameters` | β | **medium** |
| `orphaned_parameters` | ϵ | **medium** |
| `orphaned_parameters` | u | **medium** |
| `orphaned_parameters` | μ | **medium** |
| `orphaned_parameters` | π | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 1 | 3 | 4 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 1 | 0 | 1 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 7
- **Flagged** for manual review: 0

### Susceptible->Infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Transmission of Mycobacterium tuberculosis from infectious individuals to susceptible individuals through close contact, typically via airborne respiratory droplets containing the bacteria.
- **Reasoning:** Tuberculosis is transmitted through contact between susceptible and infected/infectious individuals via airborne spread, making this a frequency- or density-dependent contact process requiring a ContactFlow.

### Infected->Recovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovery of infected (active TB) individuals through successful treatment or natural immune clearance, transitioning them to the recovered compartment
- **Reasoning:** In tuberculosis compartmental models, infected individuals recover primarily through antibiotic treatment (typically a 6-month regimen), which is modeled as a rate-dependent transition from the Infected to Recovered compartment.

### Recovered->Infected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Reinfection of recovered individuals with tuberculosis due to loss of immunity or re-exposure to Mycobacterium tuberculosis, reflecting that prior TB infection does not confer complete or lasting protective immunity
- **Reasoning:** Recovered individuals from tuberculosis can become reinfected because immunity after TB recovery is incomplete and wanes over time, making previously infected individuals susceptible to reinfection upon re-exposure to M. tuberculosis.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **4** | Candidate: **4**
- Precision **0.75** | Recall **0.75** | F1 **0.75**
### Flows
- Gold count: **6** | Candidate: **3**
- Precision **1.0** | Recall **0.5** | F1 **0.6667**
