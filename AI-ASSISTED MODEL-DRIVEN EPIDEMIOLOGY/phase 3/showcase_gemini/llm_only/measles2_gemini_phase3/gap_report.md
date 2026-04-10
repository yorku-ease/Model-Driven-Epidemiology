# Phase 3 Gap Analysis Report

**Disease / context:** measles2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **18**
- Missing compartments: 3
- Missing parameters: 9
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 6
- Extra compartments (not in gold standard): 2
- Extra parameters (not in gold standard): 15
- Extra flows (not in gold standard): 2

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **18**
- After fills gaps (re-detected): **2**
- Delta (before - after): **16**
- Delta missing parameters: **9**
- Delta missing compartments: **2**
- Delta missing flows: **5**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **maternalprotected** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinatedonedose** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinatedtwodose** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **MaternalProtected->Susceptible** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->VaccinatedOneDose** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedOneDose->VaccinatedTwoDose** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedOneDose->Exposed** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedTwoDose->Exposed** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedTwoDose->Susceptible** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **maternalimmunitylossrate** — Present in gold standard but not in extracted model.
- **mmr1vaccinationrate** — Present in gold standard but not in extracted model.
- **susceptibleinfectionrate** — Present in gold standard but not in extracted model.
- **mmr2vaccinationrate** — Present in gold standard but not in extracted model.
- **onedosebreakthroughrate** — Present in gold standard but not in extracted model.
- **twodosebreakthroughrate** — Present in gold standard but not in extracted model.
- **vaccinewaningrate** — Present in gold standard but not in extracted model.
- **incubationprogressionrate** — Present in gold standard but not in extracted model.
- **recoveryrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: single vaccinated, double vaccinated
- Extra parameters: infection rate, duration of maternal immunity, seasonality of transmission, importation parameters, report rate of imported cases, vaccine effectiveness, existing immunity in older generations, parameters of spatial spread, waning rate, mmr1 coverage increase, mmr2 coverage increase, mmr2 schedule age, mmr2 school-entry age, current mmr2 schedule age, mmr1 schedule age

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 88.9% | 30% |
| **Reference agreement** | 86.2% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 11.1% | 20% |
| **→ Composite** | **64.7/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 3 | 9 | 6 | 18 |
| **Extra in model** | Model items not in reference (noise/convention) | 2 | 15 | 2 | 19 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 20
- **Flagged** for manual review: 0

### maternalprotected (missing_compartments)
- **Source:** inference
- **Primary name:** Maternal Protected

### vaccinatedonedose (missing_compartments)
- **Source:** inference
- **Primary name:** VaccinatedOneDose
- **Reasoning:** The paper explicitly discusses the impact of 'first-dose coverage' and 'second MMR dose' schedules, necessitating a compartment to track individuals who have received only one vaccine dose.

### vaccinatedtwodose (missing_compartments)
- **Source:** inference
- **Primary name:** VaccinatedTwoDose
- **Reasoning:** The paper explicitly models the impact of an earlier second MMR dose and different vaccination schedules, necessitating a compartment to track individuals who have received two vaccine doses.

### maternalimmunitylossrate (missing_parameters)
- **Source:** inference
- **Value:** 1.75 1/year
- **Confidence:** LOW

### mmr1vaccinationrate (missing_parameters)
- **Source:** inference
- **Value:** 0.92 
- **Reasoning:** First dose MMR vaccination coverage in the UK has consistently been high, typically exceeding 90% for many years due to robust public health programs.
- **Confidence:** LOW

### susceptibleinfectionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.3 per day
- **Confidence:** LOW

### mmr2vaccinationrate (missing_parameters)
- **Source:** inference
- **Value:** 0.88 
- **Reasoning:** This
- **Confidence:** LOW

### onedosebreakthroughrate (missing_parameters)
- **Source:** inference
- **Value:** 0.07 
- **Reasoning:** One dose of MMR vaccine typically provides 90-97% protection against measles. A breakthrough rate of 7% reflects the
- **Confidence:** LOW

### twodosebreakthroughrate (missing_parameters)
- **Source:** inference
- **Value:** 0.01 
- **Reasoning:** The two-dose MMR vaccine is highly effective against measles, typically providing 97-99% protection. A breakthrough rate of 1% reflects the small proportion of vaccinated individuals who may still contract measles.
- **Confidence:** LOW

### vaccinewaningrate (missing_parameters)
- **Source:** inference
- **Value:** 0.005 per year
- **Confidence:** LOW

### incubationprogressionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.0833 days^-1
- **Reasoning:** The incubation progression rate is the inverse of the average incubation period. For measles, the average incubation period is typically
- **Confidence:** LOW

### recoveryrate (missing_parameters)
- **Source:** inference
- **Value:** 0.125 days^-1
- **Confidence:** LOW

### MaternalProtected->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of maternal antibodies, leading to loss of passive immunity against measles.
- **Reasoning:** Maternal antibodies provide temporary protection to infants, which naturally wanes over time, making them susceptible to measles before vaccination.

### Susceptible->VaccinatedOneDose (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in the susceptible compartment receive their first dose of the Measles-Mumps-Rubella (MMR) vaccine, transitioning them to the VaccinatedOneDose compartment.
- **Reasoning:** The paper explicitly discusses 'increasing first-dose coverage' and the impact of vaccination schedules, necessitating a flow for susceptible individuals to receive their initial vaccine dose.

### VaccinatedOneDose->VaccinatedTwoDose (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have received one dose of the MMR vaccine receive their second dose, transitioning to a fully vaccinated state.
- **Reasoning:** The paper discusses the impact of an 'earlier second MMR dose' on measles transmission, directly implying a rate-based transition from one-dose to two-dose vaccinated states within the model.

### VaccinatedOneDose->Exposed (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals

### VaccinatedTwoDose->Exposed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Vaccinated individuals with two

### VaccinatedTwoDose->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who received two doses of the MMR vaccine lose their vaccine-induced immunity over time and become susceptible to measles infection again.
- **Reasoning:** The paper excerpt explicitly states that 'waning of vaccine-induced immunity was included' in their model, indicating a transition from a vaccinated state back to susceptibility.

### maternalprotected (missing_compartments)
- **Source:** inference
- **Primary name:** Maternally Protected
- **Reasoning:** A comprehensive measles compartmental model, particularly one that is age-stratified and examines early childhood vaccination, should include a compartment for infants protected by maternally derived antibodies.

### MaternalProtected->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of maternal antibodies, leading to loss of passive immunity and becoming susceptible to measles.
- **Reasoning:** Infants born to immune mothers receive passive immunity which naturally wanes

## 6. Fill validation (vs gold standard)
- Parameters compared: **9**
- Exact match (<1% error): **1**
- Close (<10% error): **0**
- Approximate (<50% error): **3**
- Poor (>50% error): **5**
- **Accuracy (exact+close)**: **11.1%**
- Median relative error: **62.5%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| maternalimmunitylossrate | 1.75 | 0.167 | 947.9% | poor |
| mmr1vaccinationrate | 0.92 | 0.25 | 268.0% | poor |
| susceptibleinfectionrate | 0.3 | 0.8 | 62.5% | poor |
| mmr2vaccinationrate | 0.88 | 0.18 | 388.89% | poor |
| onedosebreakthroughrate | 0.07 | 0.08 | 12.5% | approximate |
| twodosebreakthroughrate | 0.01 | 0.01 | 0.0% | exact |
| vaccinewaningrate | 0.005 | 0.002 | 150.0% | poor |
| incubationprogressionrate | 0.0833 | 0.125 | 33.36% | approximate |
| recoveryrate | 0.125 | 0.143 | 12.59% | approximate |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **10**
- Precision **0.7** | Recall **1.0** | F1 **0.8235**
### Flows
- Gold count: **9** | Candidate: **11**
- Precision **0.8182** | Recall **1.0** | F1 **0.9**
