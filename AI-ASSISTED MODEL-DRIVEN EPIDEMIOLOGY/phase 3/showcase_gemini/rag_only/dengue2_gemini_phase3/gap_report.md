# Phase 3 Gap Analysis Report

**Disease / context:** dengue2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **14**
- Missing compartments: 4
- Missing parameters: 5
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 5
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 13
- Extra flows (not in gold standard): 2

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **14**
- After fills gaps (re-detected): **0**
- Delta (before - after): **14**
- Delta missing parameters: **5**
- Delta missing compartments: **4**
- Delta missing flows: **5**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **exposed** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinatedinfected** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinatedrecovered** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->Exposed** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->Vaccinated** — Present in gold standard but not in extracted model (or flows list).
- **Exposed->Infectious** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated->VaccinatedInfected** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedInfected->VaccinatedRecovered** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **transmissionrate** — Present in gold standard but not in extracted model.
- **incubationrate** — Present in gold standard but not in extracted model.
- **recoveryrate** — Present in gold standard but not in extracted model.
- **screeningandvaccinationrate** — Present in gold standard but not in extracted model.
- **vaccinebreakthroughrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: per-exposure protection from vaccination for seronegative vaccinees, per-exposure protection from vaccination for seropositive vaccinees, average duration of protection for seronegative vaccinees, average duration of protection for seropositive vaccinees, probability of symptoms conditional on infection (primary), probability of symptoms conditional on infection (secondary), probability of symptoms conditional on infection (post-secondary), probability of hospitalization conditional on symptoms (primary), probability of hospitalization conditional on symptoms (secondary), probability of hospitalization conditional on symptoms (post-secondary), probability of death conditional on symptomatic disease, probability of mosquito to human transmission, mosquito emergence rate

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 91.7% | 30% |
| **Fill traceability** | 85.7% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **94.6/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 4 | 5 | 5 | 14 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 13 | 2 | 15 |

## 5. Gap filling results
- Filled via **RAG**: 12
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 2

### exposed (missing_compartments)
- **Source:** rag
- **Primary name:** exposed
- **Evidence chunks:** 3 snippet(s) in database
- *Use paper snippets to confirm compartment label and add to model.*

### vaccinated (missing_compartments)
- **Source:** rag
- **Primary name:** vaccinated
- **Evidence chunks:** 3 snippet(s) in database
- *Use paper snippets to confirm compartment label and add to model.*

### vaccinatedinfected (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### vaccinatedrecovered (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### transmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.28 
- **Description:** Approximate transmission parameter for simplified human-side dengue model
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_dengue

### incubationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.20 
- **Description:** Progression from exposed to infectious
- **From papers:** p1_model_ebola, p1_model_cholera, p1_model_dengue

### recoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.14 
- **Description:** Recovery from infectious state
- **From papers:** p1_model_measles, p1_model_ebola, p1_model_cholera

### screeningandvaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.04 
- **Description:** Routine screening at age 9 followed by vaccination if seropositive
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_dengue

### vaccinebreakthroughrate (missing_parameters)
- **Source:** rag
- **Value:** 0.05 
- **Description:** Residual post-vaccination infection risk representing imperfect protection
- **From papers:** p1_model_cholera, p1_model_dengue

### Susceptible->Exposed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible->Vaccinated (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Exposed->Infectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Vaccinated->VaccinatedInfected (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### VaccinatedInfected->VaccinatedRecovered (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 6. Fill validation (vs gold standard)
- Parameters compared: **5**
- Exact match (<1% error): **5**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**
- **Accuracy (exact+close)**: **100.0%**
- Median relative error: **0.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| transmissionrate | 0.28 | 0.28 | 0.0% | exact |
| incubationrate | 0.2 | 0.2 | 0.0% | exact |
| recoveryrate | 0.14 | 0.14 | 0.0% | exact |
| screeningandvaccinationrate | 0.04 | 0.04 | 0.0% | exact |
| vaccinebreakthroughrate | 0.05 | 0.05 | 0.0% | exact |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **7**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **6** | Candidate: **7**
- Precision **0.7143** | Recall **1.0** | F1 **0.8333**
