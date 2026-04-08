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
- After fills gaps (re-detected): **6**
- Delta (before - after): **12**
- Delta missing parameters: **3**
- Delta missing compartments: **3**
- Delta missing flows: **6**

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
| **Gap reduction** | 66.7% | 30% |
| **Reference agreement** | 88.8% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **66.6/100** | — |

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
- Filled via **inference**: 24
- **Flagged** for manual review: 0

### mmr1vaccinationrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### susceptibleinfectionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### mmr2vaccinationrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### onedosebreakthroughrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### twodosebreakthroughrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### vaccinewaningrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

## 6. Fill validation (vs gold standard)
- Parameters compared: **0**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| mmr1vaccinationrate | None | 0.25 | — | no_fill |
| susceptibleinfectionrate | None | 0.80 | — | no_fill |
| mmr2vaccinationrate | None | 0.18 | — | no_fill |
| onedosebreakthroughrate | None | 0.08 | — | no_fill |
| twodosebreakthroughrate | None | 0.01 | — | no_fill |
| vaccinewaningrate | None | 0.002 | — | no_fill |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **9**
- Precision **0.7778** | Recall **1.0** | F1 **0.875**
### Flows
- Gold count: **9** | Candidate: **11**
- Precision **0.8182** | Recall **1.0** | F1 **0.9**
