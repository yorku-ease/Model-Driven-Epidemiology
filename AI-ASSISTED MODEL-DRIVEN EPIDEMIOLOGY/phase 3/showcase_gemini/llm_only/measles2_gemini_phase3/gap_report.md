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
- After fills gaps (re-detected): **9**
- Delta (before - after): **9**
- Delta missing parameters: **9**
- Delta missing compartments: **0**
- Delta missing flows: **0**

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
| **Gap reduction** | 50.0% | 30% |
| **Reference agreement** | 86.2% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **60.9/100** | — |

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
- Filled via **inference**: 27
- **Flagged** for manual review: 0

### maternalprotected (missing_compartments)
- **Source:** inference
- **Primary name:** Maternal Protected
- **Reasoning:** An age-stratified measles model, especially one evaluating vaccination impact, typically includes a compartment for infants protected by maternal antibodies,

### vaccinatedonedose (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated One Dose

### vaccinatedtwodose (missing_compartments)
- **Source:** inference
- **Primary name:** VaccinatedTwoDose
- **Reasoning:** The paper explicitly models the impact of an earlier second MMR dose and different vaccination schedules, requiring a compartment to track individuals who have received two vaccine doses.

### MaternalProtected->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of maternal antibodies, leading to loss of passive immunity and return to susceptibility.
- **Reasoning:** Maternal antibodies provide temporary protection against measles to newborns, which naturally wanes over time, making them susceptible to infection.

### Susceptible->VaccinatedOneDose (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in the susceptible

### VaccinatedOneDose->VaccinatedTwoDose (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have received one dose of the MMR vaccine receive their second dose, transitioning to a two-dose vaccinated state.
- **Reasoning:** The paper discusses the impact of an 'earlier second dose for the Measles-Mumps-Rubella (MMR) vaccine', directly implying a transition from one-dose to two-dose vaccination status, which is a rate-based process.

### VaccinatedOneDose->Exposed (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals with

### VaccinatedTwoDose->Exposed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning

### VaccinatedTwoDose->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of vaccine-induced immunity, where individuals who received two doses of the MMR vaccine lose their protection over time and revert to a susceptible state.
- **Reasoning:** The paper explicitly mentions 'waning of vaccine-induced immunity was included' in their model, indicating a transition from a vaccinated state back to susceptibility due to loss of protection over time.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **10**
- Precision **0.7** | Recall **1.0** | F1 **0.8235**
### Flows
- Gold count: **9** | Candidate: **11**
- Precision **0.8182** | Recall **1.0** | F1 **0.9**
