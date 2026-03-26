# Phase 3 Gap Analysis Report

**Disease / context:** cholera

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **2**
- Missing compartments: 1
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 1
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 0
- Extra flows (not in gold standard): 5

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **2**
- After fills gaps (re-detected): **2**
- Delta (before - after): **0**
- Delta missing parameters: **0**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **chollerae bacterial concentration** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Infectious->Chollerae bacterial concentration** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: bacteria in water reservoir

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 0.0% | 25% |
| **Reference agreement** | 53.3% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 15% |
| **Structural integrity** | 66.7%  (6→2 errors) | 15% |
| **→ Composite** | **38.3/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 2 | 1 | +1 |
| High | 2 | 1 | +1 |
| Medium | 2 | 0 | +2 |
| **Total** | **6** | **2** | **+4** |

**6 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible -> ContactFlow | Annotated Susceptible ContactFlow as direct S→I (no Exposed stage) |
| `zero_population_all` | all_compartments | Set Susceptible population=1000 |
| `flow_chain_incomplete` | Cholerae Bacterial Concentration | Added flow Cholerae Bacterial Concentration → Recovered |
| `orphaned_parameters` | 1/δ | Wired 1/δ (//@parameters.2) to Bacteria in Water Reservoir → Bacteria in Water Reservoir flow |
| `orphaned_parameters` | κ | Wired κ (//@parameters.4) to Cholerae Bacterial Concentration → Recovered flow |
| `self_referential_flow` | Susceptible -> ContactFlow | Annotated Susceptible ContactFlow as direct S→I (no Exposed stage) |

**2 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `self_referential_flow` | Susceptible -> ContactFlow | **critical** |
| `missing_death_sinks` | model | **high** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 0 | 1 | 2 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 0 | 5 | 6 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 4
- **Flagged** for manual review: 0

### chollerae bacterial concentration (missing_compartments)
- **Source:** inference
- **Primary name:** Cholerae Bacterial Concentration
- **Reasoning:** Cholera transmission models commonly include an environmental reservoir representing the concentration of Vibrio cholerae bacteria in water, which is the missing compartment here.

### Infectious->Chollerae bacterial concentration (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Shedding of Vibrio cholerae into the environmental bacterial concentration by infectious individuals, increasing the pathogen reservoir.
- **Reasoning:** In cholera models, infectious people contaminate the environment by excreting bacteria, so the flow is a continuous rate from Infectious to bacterial concentration.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **4** | Candidate: **5**
- Precision **0.6** | Recall **0.75** | F1 **0.6667**
### Flows
- Gold count: **3** | Candidate: **7**
- Precision **0.2857** | Recall **0.6667** | F1 **0.4**
