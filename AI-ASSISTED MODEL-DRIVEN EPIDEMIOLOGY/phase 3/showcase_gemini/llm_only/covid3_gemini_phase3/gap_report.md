# Phase 3 Gap Analysis Report

**Disease / context:** covid3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **19**
- Missing compartments: 2
- Missing parameters: 14
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 3
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 6
- Extra flows (not in gold standard): 1

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **19**
- After fills gaps (re-detected): **6**
- Delta (before - after): **13**
- Delta missing parameters: **12**
- Delta missing compartments: **1**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **susceptible** — Present in gold standard but not in extracted model. (severity: high)
- **removed** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->Infected** — Present in gold standard but not in extracted model (or flows list).
- **Infected->Removed** — Present in gold standard but not in extracted model (or flows list).
- **Infected->Dead** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **population11countries** — Present in gold standard but not in extracted model.
- **initialrt** — Present in gold standard but not in extracted model.
- **generationintervaldays** — Present in gold standard but not in extracted model.
- **basetransmissionrate** — Present in gold standard but not in extracted model.
- **publiceventsbaneffect** — Present in gold standard but not in extracted model.
- **schoolclosureeffect** — Present in gold standard but not in extracted model.
- **selfisolationeffect** — Present in gold standard but not in extracted model.
- **socialdistancingeffect** — Present in gold standard but not in extracted model.
- **lockdowneffect** — Present in gold standard but not in extracted model.
- **combinedinterventionmultiplier** — Present in gold standard but not in extracted model.
- **effectivetransmissionrate** — Present in gold standard but not in extracted model.
- **meanoutcomedelaydays** — Present in gold standard but not in extracted model.
- **removalrate** — Present in gold standard but not in extracted model.
- **deathrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: r_t, initial r_t, serial interval distribution, infection fatality ratio, infection-to-death distribution, lockdown effect

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 68.4% | 30% |
| **Reference agreement** | 25.0% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **28.0/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 14 | 3 | 19 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 6 | 1 | 7 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 25
- **Flagged** for manual review: 0

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** Deceased
- **Reasoning:** The model

### initialrt (missing_parameters)
- **Source:** inference
- **Value:** 3.0 
- **Reasoning:** The text explicitly states that initial R_t for SARS-CoV-2 in uncontrolled epidemic settings in China was typically around 2-4.
- **Confidence:** LOW

### lockdowneffect (missing_parameters)
- **Source:** inference
- **Value:** 0.25 
- **Confidence:** LOW

### Susceptible->Infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected after effective contact with infectious individuals.
- **Reasoning:** The paper discusses the 'infection cycle' and 'R_t' (reproduction number), which are characteristic of contact-based transmission where the rate of new infections depends on interactions between susceptible and infected individuals.

### Infected->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which infected individuals either recover from the disease or die due to the disease, thereby being removed from the infectious population.
- **Reasoning:** The paper discusses linking the 'infection cycle to observed deaths' and 'estimating the deaths that would have occurred without interventions', which implies a transition out of the infected state into a removed state (including death).

### Infected->Dead (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality of infected individuals due to the disease.
- **Reasoning:** The paper describes a mechanistic model linking the infection cycle to observed deaths, implying a direct transition from the infected state to death at a specific rate.

## 6. Fill validation (vs gold standard)
- Parameters compared: **2**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **2**
- Poor (>50% error): **0**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **31.58%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| initialrt | 3.0 | 3.8 | 21.05% | approximate |
| lockdowneffect | 0.25 | 0.19 | 31.58% | approximate |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **4** | Candidate: **4**
- Precision **0.5** | Recall **0.5** | F1 **0.5**
### Flows
- Gold count: **3** | Candidate: **1**
- Precision **0.0** | Recall **0.0** | F1 **0.0**
