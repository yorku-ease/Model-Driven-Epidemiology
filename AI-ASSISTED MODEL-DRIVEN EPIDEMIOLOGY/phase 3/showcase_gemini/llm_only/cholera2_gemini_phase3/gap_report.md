# Phase 3 Gap Analysis Report

**Disease / context:** cholera2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **48**
- Missing compartments: 6
- Missing parameters: 24
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 18
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 9
- Extra flows (not in gold standard): 1

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **48**
- After fills gaps (re-detected): **0**
- Delta (before - after): **48**
- Delta missing parameters: **24**
- Delta missing compartments: **6**
- Delta missing flows: **18**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **vaccinated adults (two doses)** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated children under 5 (two doses)** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated adults (one dose)** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated children under 5 (one dose)** — Present in gold standard but not in extracted model. (severity: high)
- **exposed / recently infected** — Present in gold standard but not in extracted model. (severity: high)
- **environmental vibrio cholerae reservoir** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible adults->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible adults->Vaccinated adults (two doses)** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible adults->Vaccinated adults (one dose)** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible children under 5->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible children under 5->Vaccinated children under 5 (two doses)** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible children under 5->Vaccinated children under 5 (one dose)** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (two doses)->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (two doses)->Susceptible adults** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated children under 5 (two doses)->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated children under 5 (two doses)->Susceptible children under 5** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (one dose)->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (one dose)->Susceptible adults** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated children under 5 (one dose)->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated children under 5 (one dose)->Susceptible children under 5** — Present in gold standard but not in extracted model (or flows list).
- **Exposed / recently infected->Symptomatic infectious** — Present in gold standard but not in extracted model (or flows list).
- **Exposed / recently infected->Asymptomatic infectious** — Present in gold standard but not in extracted model (or flows list).
- **Symptomatic infectious->Environmental Vibrio cholerae reservoir** — Present in gold standard but not in extracted model (or flows list).
- **Asymptomatic infectious->Environmental Vibrio cholerae reservoir** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **βa** — Present in gold standard but not in extracted model.
- **βc** — Present in gold standard but not in extracted model.
- **βa_2dose** — Present in gold standard but not in extracted model.
- **βc_2dose** — Present in gold standard but not in extracted model.
- **βa_1dose** — Present in gold standard but not in extracted model.
- **βc_1dose** — Present in gold standard but not in extracted model.
- **σ*k** — Present in gold standard but not in extracted model.
- **(1-σ)*k** — Present in gold standard but not in extracted model.
- **γ** — Present in gold standard but not in extracted model.
- **ν2** — Present in gold standard but not in extracted model.
- **ν1** — Present in gold standard but not in extracted model.
- **ω2** — Present in gold standard but not in extracted model.
- **ω1** — Present in gold standard but not in extracted model.
- **ωr** — Present in gold standard but not in extracted model.
- **ξs** — Present in gold standard but not in extracted model.
- **ξa** — Present in gold standard but not in extracted model.
- **λa** — Present in gold standard but not in extracted model.
- **λc** — Present in gold standard but not in extracted model.
- **δ** — Present in gold standard but not in extracted model.
- **μb** — Present in gold standard but not in extracted model.
- **ve2a** — Present in gold standard but not in extracted model.
- **ve2c** — Present in gold standard but not in extracted model.
- **ve1a** — Present in gold standard but not in extracted model.
- **ve1c** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: initial vaccine protection, vaccine protection duration, single-dose protection duration, baseline two-dose vaccine coverage, baseline one-dose vaccine coverage, no vaccine coverage, high-coverage two-dose vaccine coverage, high-coverage one-dose vaccine coverage, high-coverage no vaccine coverage

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 92.7% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **77.8/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 6 | 24 | 18 | 48 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 9 | 1 | 10 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 55
- **Flagged** for manual review: 0

### vaccinated children under 5 (one dose) (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Children Under 5 (One Dose

### environmental vibrio cholerae reservoir (missing_compartments)
- **Source:** inference
- **Primary name:** Environmental Vibrio Cholerae Reservoir

### Susceptible children under 5->Vaccinated children under 5 (one dose) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible children under 5 years old receiving their first dose of the cholera vaccine.
- **Reasoning:** The paper excerpt discusses the use of cholera vaccine, implying a transition from susceptible to vaccinated states through a vaccination process, which is typically modeled as a rate.

### Vaccinated children under 5 (one dose)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated children under

### Vaccinated children under 5 (one dose)->Susceptible children under 5 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of vaccine-induced immunity in children under 5 who received one dose, leading to a return to susceptibility.
- **Reasoning:** This flow represents the loss of vaccine protection over time, which is a rate-dependent process and not driven by contact.

### Symptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Symptomatic infectious

### Asymptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Asymptomatic infectious individuals shed Vibrio cholerae bacteria into the environment (e.g., water sources) through their feces, contributing to the environmental reservoir of the pathogen.
- **Reasoning:** Asymptomatic individuals, despite showing no symptoms, can shed Vibrio cholerae into the environment, directly replenishing the environmental reservoir and sustaining transmission.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **11** | Candidate: **10**
- Precision **0.8** | Recall **1.0** | F1 **0.8889**
### Flows
- Gold count: **21** | Candidate: **15**
- Precision **0.9333** | Recall **1.0** | F1 **0.9655**
