# Phase 3 Gap Analysis Report

**Disease / context:** measles

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **8**
- Missing compartments: 1
- Missing parameters: 2
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 5
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 7
- Extra flows (not in gold standard): 5

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **8**
- After fills gaps (re-detected): **3**
- Delta (before - after): **5**
- Delta missing parameters: **2**
- Delta missing compartments: **0**
- Delta missing flows: **3**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **vaccinated adults (catch-up/monitored)** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Un-monitored vaccinated children->Exposed children** — Present in gold standard but not in extracted model (or flows list).
- **Un-monitored vaccinated children->Un-monitored vaccinated adults** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible adults->Vaccinated adults (catch-up/monitored)** — Present in gold standard but not in extracted model (or flows list).
- **Un-monitored vaccinated adults->Exposed adults** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (catch-up/monitored)->Immune adults** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **ξu** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: unmonitored vaccinated adults
- Extra parameters: x_u, x_a, b_c, b_a, j, h_a, h_u

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 62.5% | 25% |
| **Reference agreement** | 94.2% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 15% |
| **Structural integrity** | 45.8%  (24→13 errors) | 15% |
| **→ Composite** | **61.0/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 6 | 0 | +6 |
| High | 1 | 0 | +1 |
| Medium | 17 | 13 | +4 |
| **Total** | **24** | **13** | **+11** |

**7 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible Children -> ContactFlow | Redirected Susceptible Children ContactFlow target from //@compartments.3 to //@compartments.9 (Expo |
| `self_referential_flow` | Unmonitored Vaccinated Children -> ContactFlow | Redirected Unmonitored Vaccinated Children ContactFlow target from //@compartments.3 to //@compartme |
| `self_referential_flow` | Susceptible Adults -> ContactFlow | Redirected Susceptible Adults ContactFlow target from //@compartments.9 to //@compartments.3 (Expose |
| `self_referential_flow` | Unmonitored Vaccinated Adults -> ContactFlow | Redirected Unmonitored Vaccinated Adults ContactFlow target from //@compartments.9 to //@compartment |
| `zero_population_all` | all_compartments | Set Susceptible Children population=1000 |
| `uniform_parameter_collapse` | all_flows | Reassigned parameters for 12 flow(s) using semantic matching |
| `orphaned_parameters` | ξu | Wired ξu (//@parameters.16) to Monitored Vaccinated Children → Monitored Vaccinated Adults flow |

**13 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `orphaned_parameters` | x_U | **medium** |
| `orphaned_parameters` | x_M | **medium** |
| `orphaned_parameters` | x_A | **medium** |
| `orphaned_parameters` | m | **medium** |
| `orphaned_parameters` | g | **medium** |
| `orphaned_parameters` | j | **medium** |
| `orphaned_parameters` | e | **medium** |
| `orphaned_parameters` | h_A | **medium** |
| `orphaned_parameters` | h_U | **medium** |
| `orphaned_parameters` | h_M | **medium** |
| `orphaned_parameters` | d | **medium** |
| `orphaned_parameters` | q | **medium** |
| `orphaned_parameters` | φ | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 2 | 5 | 8 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 7 | 5 | 13 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 11
- **Flagged** for manual review: 0

### vaccinated adults (catch-up/monitored) (missing_compartments)
- **Source:** inference
- **Primary name:** Monitored Vaccinated Children

### Susceptible adults->Vaccinated adults (catch-up/monitored) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible adults receive the measles vaccine, either as a catch-up dose or through a monitored vaccination program, transitioning them to the vaccinated adult compartment.
- **Reasoning:** Vaccination is an external intervention applied to susceptible individuals at a certain rate, not a result of contact-dependent transmission.

### Vaccinated adults (catch-up/monitored)->Immune adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The transition of vaccinated adults, who received their doses through catch-up or monitored programs, developing protective immunity against measles.
- **Reasoning:** Successful vaccination directly confers immunity to individuals, and this process is typically modeled as a rate rather than through contact.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **12** | Candidate: **12**
- Precision **1.0** | Recall **0.9167** | F1 **0.9565**
### Flows
- Gold count: **20** | Candidate: **23**
- Precision **0.9565** | Recall **0.9** | F1 **0.9274**
