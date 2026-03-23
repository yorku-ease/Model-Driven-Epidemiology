# Phase 3 Gap Analysis Report

**Disease / context:** ebola

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **19**
- Missing compartments: 1
- Missing parameters: 14
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 4
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 14
- Extra flows (not in gold standard): 2

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **19**
- After fills gaps (re-detected): **3**
- Delta (before - after): **16**
- Delta missing parameters: **14**
- Delta missing compartments: **0**
- Delta missing flows: **2**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **environment pathogens** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Infectious->Recovered** — Present in gold standard but not in extracted model (or flows list).
- **Infectious->Deceased** — Present in gold standard but not in extracted model (or flows list).
- **Infectious->Environment pathogens** — Present in gold standard but not in extracted model (or flows list).
- **Deceased->Environment pathogens** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **λ** — Present in gold standard but not in extracted model.
- **μ** — Present in gold standard but not in extracted model.
- **β1** — Present in gold standard but not in extracted model.
- **β2** — Present in gold standard but not in extracted model.
- **β3** — Present in gold standard but not in extracted model.
- **δ1** — Present in gold standard but not in extracted model.
- **ω2** — Present in gold standard but not in extracted model.
- **ω3** — Present in gold standard but not in extracted model.
- **ω1** — Present in gold standard but not in extracted model.
- **ω1q** — Present in gold standard but not in extracted model.
- **δ2** — Present in gold standard but not in extracted model.
- **η** — Present in gold standard but not in extracted model.
- **ρ** — Present in gold standard but not in extracted model.
- **μb** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: ebola virus pathogens in the environment
- Extra parameters: recruitment rate of susceptible people, recruitment rate of infected people, effective contact rate of infected humans, contact rate of ebola virus pathogens in the environment, rate of contact with deceased humans, natural death rate of humans, death rate due to infection, rate of shedding of infected humans, rate at which infected people are quarantined, rate at which quarantined people recover, rate at which quarantined people die, proportion of shedding of deceased people, proportion rate of burial of deceased people, rate at which ebola virus decays in the environment

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 84.2% | 25% |
| **Reference agreement** | 76.0% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 15% |
| **Structural integrity** | 32.3%  (31→21 errors) | 15% |
| **→ Composite** | **59.9/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 2 | 1 | +1 |
| High | 3 | 0 | +3 |
| Medium | 26 | 20 | +6 |
| **Total** | **31** | **21** | **+10** |

**11 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible People -> ContactFlow | Annotated Susceptible People ContactFlow as direct S→I (no Exposed stage) |
| `zero_population_all` | all_compartments | Set Susceptible People population=1000 |
| `flow_chain_incomplete` | Ebola Virus Pathogens in the Environment | Added flow Ebola Virus Pathogens in the Environment → Recovered Disease |
| `flow_chain_incomplete` | Environmental Pathogens | Added flow Environmental Pathogens → Recovered Disease |
| `orphaned_parameters` | Recruitment rate of susceptible people | Wired Recruitment rate of susceptible people (//@parameters.0) to Quarantine People → Deceased Peopl |
| `orphaned_parameters` | Recruitment rate of infected people | Wired Recruitment rate of infected people (//@parameters.1) to Infectious People → Deceased People f |
| `orphaned_parameters` | Contact rate of Ebola virus pathogens in the environment | Wired Contact rate of Ebola virus pathogens in the environment (//@parameters.3) to Deceased People  |
| `orphaned_parameters` | Rate of contact with deceased humans | Wired Rate of contact with deceased humans (//@parameters.4) to Environmental Pathogens → Recovered  |
| `orphaned_parameters` | Natural death rate of humans | Wired Natural death rate of humans (//@parameters.5) to Infectious People → Recovered Disease flow |
| `orphaned_parameters` | Death rate due to infection | Wired Death rate due to infection (//@parameters.6) to Ebola Virus Pathogens in the Environment → Re |
| `self_referential_flow` | Susceptible People -> ContactFlow | Annotated Susceptible People ContactFlow as direct S→I (no Exposed stage) |

**21 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `self_referential_flow` | Susceptible People -> ContactFlow | **critical** |
| `orphaned_parameters` | Rate of shedding of infected humans | **medium** |
| `orphaned_parameters` | Rate at which infected people are quarantined | **medium** |
| `orphaned_parameters` | Rate at which quarantined people die | **medium** |
| `orphaned_parameters` | Proportion of shedding of deceased people | **medium** |
| `orphaned_parameters` | Proportion rate of burial of deceased people | **medium** |
| `orphaned_parameters` | Rate at which Ebola virus decays in the environment | **medium** |
| `orphaned_parameters` | λ | **medium** |
| `orphaned_parameters` | μ | **medium** |
| `orphaned_parameters` | β1 | **medium** |
| `orphaned_parameters` | β2 | **medium** |
| `orphaned_parameters` | β3 | **medium** |
| `orphaned_parameters` | δ1 | **medium** |
| `orphaned_parameters` | ω2 | **medium** |
| `orphaned_parameters` | ω3 | **medium** |
| `orphaned_parameters` | ω1 | **medium** |
| `orphaned_parameters` | ω1q | **medium** |
| `orphaned_parameters` | δ2 | **medium** |
| `orphaned_parameters` | η | **medium** |
| `orphaned_parameters` | ρ | **medium** |
| `orphaned_parameters` | μb | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 14 | 4 | 19 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 14 | 2 | 17 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 22
- **Flagged** for manual review: 0

### environment pathogens (missing_compartments)
- **Source:** inference
- **Primary name:** Environmental Pathogens

### Infectious->Environment pathogens (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Rate at which infected humans shed Ebola virus pathogens into the environment through urine and faeces.
- **Reasoning:** The excerpt explicitly states 'The disease can enter the environment through the urine and faeces of infected or deceased individuals' and mentions 'The rate at which shedding of infected humans', indicating a continuous release of pathogens

### Deceased->Environment pathogens (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The shedding of Ebola virus pathogens from deceased individuals into the environment.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **7**
- Precision **0.7143** | Recall **0.8333** | F1 **0.7692**
### Flows
- Gold count: **8** | Candidate: **8**
- Precision **0.75** | Recall **0.75** | F1 **0.75**
