# Phase 3 Gap Analysis Report

**Disease / context:** hiv

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **9**
- Missing compartments: 2
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 7
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 7
- Extra flows (not in gold standard): 4

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **9**
- After fills gaps (re-detected): **1**
- Delta (before - after): **8**
- Delta missing parameters: **0**
- Delta missing compartments: **1**
- Delta missing flows: **7**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **treated with art** — Present in gold standard but not in extracted model. (severity: high)
- **recruitmentsource** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Untreated infected homosexual men->Treated with ART** — Present in gold standard but not in extracted model (or flows list).
- **Untreated infected homosexual men->People living with AIDS** — Present in gold standard but not in extracted model (or flows list).
- **Untreated infected women->Treated with ART** — Present in gold standard but not in extracted model (or flows list).
- **Untreated infected women->People living with AIDS** — Present in gold standard but not in extracted model (or flows list).
- **Untreated infected heterosexual men->Treated with ART** — Present in gold standard but not in extracted model (or flows list).
- **Untreated infected heterosexual men->People living with AIDS** — Present in gold standard but not in extracted model (or flows list).
- **Treated with ART->People living with AIDS** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: treated with antiretrovirals
- Extra parameters: b_s, b_h, b_hw, b_hm, c_s, c_hw, c_hm

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 88.9% | 25% |
| **Reference agreement** | 86.7% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 15% |
| **Structural integrity** | 28.0%  (25→18 errors) | 15% |
| **→ Composite** | **63.1/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 5 | 3 | +2 |
| High | 6 | 2 | +4 |
| Medium | 14 | 11 | +3 |
| Low | 0 | 2 | -2 |
| **Total** | **25** | **18** | **+7** |

**11 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible homosexual men -> ContactFlow | Annotated Susceptible homosexual men ContactFlow as direct S→I (no Exposed stage) |
| `self_referential_flow` | Susceptible women -> ContactFlow | Annotated Susceptible homosexual men ContactFlow as direct S→I (no Exposed stage) |
| `self_referential_flow` | Susceptible heterosexual men -> ContactFlow | Annotated Susceptible homosexual men ContactFlow as direct S→I (no Exposed stage) |
| `zero_population_all` | all_compartments | Set Susceptible homosexual men population=1000 |
| `uniform_parameter_collapse` | all_flows | Reassigned parameters for 7 flow(s) using semantic matching |
| `missing_birth_sources` | Susceptible homosexual men | Set Susceptible women population=1000 (initial condition) |
| `missing_birth_sources` | Susceptible women | Set Susceptible heterosexual men population=1000 (initial condition) |
| `missing_birth_sources` | Susceptible Population | Set Susceptible Population population=1000 (initial condition) |
| `self_referential_flow` | Susceptible homosexual men -> ContactFlow | Annotated Susceptible homosexual men ContactFlow as direct S→I (no Exposed stage) |
| `self_referential_flow` | Susceptible women -> ContactFlow | Annotated Susceptible homosexual men ContactFlow as direct S→I (no Exposed stage) |
| `self_referential_flow` | Susceptible heterosexual men -> ContactFlow | Annotated Susceptible homosexual men ContactFlow as direct S→I (no Exposed stage) |

**18 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `self_referential_flow` | Susceptible homosexual men -> ContactFlow | **critical** |
| `self_referential_flow` | Susceptible women -> ContactFlow | **critical** |
| `self_referential_flow` | Susceptible heterosexual men -> ContactFlow | **critical** |
| `flow_chain_incomplete` | People living with AIDS | **high** |
| `flow_chain_incomplete` | Recruitment Source | **high** |
| `orphaned_parameters` | C | **medium** |
| `orphaned_parameters` | h | **medium** |
| `orphaned_parameters` | c | **medium** |
| `orphaned_parameters` | p | **medium** |
| `orphaned_parameters` | l | **medium** |
| `orphaned_parameters` | d | **medium** |
| `orphaned_parameters` | b_h | **medium** |
| `orphaned_parameters` | c_s | **medium** |
| `orphaned_parameters` | c_h | **medium** |
| `orphaned_parameters` | c_hw | **medium** |
| `orphaned_parameters` | c_hm | **medium** |
| `composite_parameter_decomposition` | C | **low** |
| `composite_parameter_decomposition` | c | **low** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 0 | 7 | 9 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 7 | 4 | 12 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 15
- **Flagged** for manual review: 0

### treated with art (missing_compartments)
- **Source:** inference
- **Primary name:** Treated With ART
- **Reasoning:** The excerpt highlights antiretroviral therapy (ART) as a crucial intervention for HIV, reducing viral load, morbidity, and mortality, which necessitates a distinct compartment for individuals receiving this treatment.

### recruitmentsource (missing_compartments)
- **Source:** inference
- **Primary name:** Recruitment Source

### Untreated infected homosexual men->Treated with ART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Initiation of antiretroviral therapy (ART) among untreated infected homosexual men.
- **Reasoning:** The excerpt describes ART as the current best option for viral suppression and reduction of morbidity and mortality, indicating a transition from an untreated to a treated state for infected individuals.

### Untreated infected women->Treated with ART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which untreated HIV-infected women initiate antiretroviral therapy (ART).
- **Reasoning:** The paper excerpt explicitly mentions antiretroviral therapy (ART) as the best option for viral suppression and reduction of mortality for HIV-infected individuals, indicating a transition to a treated state.

### Untreated infected heterosexual men->Treated with ART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which untreated infected heterosexual men initiate antiretroviral therapy (ART).
- **Reasoning:** The excerpt emphasizes ART as the best available treatment for HIV, necessitating a flow to represent individuals transitioning from an untreated to a treated state within the model.

### Treated with ART->People living with AIDS (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression to the AIDS

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **11**
- Precision **0.8182** | Recall **1.0** | F1 **0.9**
### Flows
- Gold count: **10** | Candidate: **14**
- Precision **0.7143** | Recall **1.0** | F1 **0.8333**
