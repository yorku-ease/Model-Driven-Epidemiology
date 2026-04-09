# Phase 3 Gap Analysis Report

**Disease / context:** tuberculosis2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **38**
- Missing compartments: 6
- Missing parameters: 17
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 15
- Extra compartments (not in gold standard): 6
- Extra parameters (not in gold standard): 16
- Extra flows (not in gold standard): 9

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **38**
- After fills gaps (re-detected): **20**
- Delta (before - after): **18**
- Delta missing parameters: **17**
- Delta missing compartments: **1**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **latentinfection** — Present in gold standard but not in extracted model. (severity: high)
- **infectioustuberculosis** — Present in gold standard but not in extracted model. (severity: high)
- **noninfectioustuberculosis** — Present in gold standard but not in extracted model. (severity: high)
- **ontreatment** — Present in gold standard but not in extracted model. (severity: high)
- **treatmentfailurestate** — Present in gold standard but not in extracted model. (severity: high)
- **selfcuredstate** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->LatentInfection** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->InfectiousTuberculosis** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->NonInfectiousTuberculosis** — Present in gold standard but not in extracted model (or flows list).
- **LatentInfection->InfectiousTuberculosis** — Present in gold standard but not in extracted model (or flows list).
- **LatentInfection->NonInfectiousTuberculosis** — Present in gold standard but not in extracted model (or flows list).
- **InfectiousTuberculosis->OnTreatment** — Present in gold standard but not in extracted model (or flows list).
- **InfectiousTuberculosis->SelfCuredState** — Present in gold standard but not in extracted model (or flows list).
- **NonInfectiousTuberculosis->OnTreatment** — Present in gold standard but not in extracted model (or flows list).
- **NonInfectiousTuberculosis->SelfCuredState** — Present in gold standard but not in extracted model (or flows list).
- **OnTreatment->LatentInfection** — Present in gold standard but not in extracted model (or flows list).
- **OnTreatment->TreatmentFailureState** — Present in gold standard but not in extracted model (or flows list).
- **TreatmentFailureState->InfectiousTuberculosis** — Present in gold standard but not in extracted model (or flows list).
- **TreatmentFailureState->NonInfectiousTuberculosis** — Present in gold standard but not in extracted model (or flows list).
- **SelfCuredState->InfectiousTuberculosis** — Present in gold standard but not in extracted model (or flows list).
- **SelfCuredState->NonInfectiousTuberculosis** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **primaryinfectiontolatentrate** — Present in gold standard but not in extracted model.
- **primaryinfectiontoinfectiousrate** — Present in gold standard but not in extracted model.
- **primaryinfectiontononinfectiousrate** — Present in gold standard but not in extracted model.
- **latentreactivationtoinfectiousrate** — Present in gold standard but not in extracted model.
- **latentreactivationtononinfectiousrate** — Present in gold standard but not in extracted model.
- **exogenousreinfectiontoinfectiousrate** — Present in gold standard but not in extracted model.
- **exogenousreinfectiontononinfectiousrate** — Present in gold standard but not in extracted model.
- **infectiouscasedetectionrate** — Present in gold standard but not in extracted model.
- **infectiousselfcurerate** — Present in gold standard but not in extracted model.
- **noninfectiouscasedetectionrate** — Present in gold standard but not in extracted model.
- **noninfectiousselfcurerate** — Present in gold standard but not in extracted model.
- **treatmentcurerate** — Present in gold standard but not in extracted model.
- **treatmentfailurerate** — Present in gold standard but not in extracted model.
- **failurerelapsetoinfectiousrate** — Present in gold standard but not in extracted model.
- **failurerelapsetononinfectiousrate** — Present in gold standard but not in extracted model.
- **selfcurerelapsetoinfectiousrate** — Present in gold standard but not in extracted model.
- **selfcurerelapsetononinfectiousrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: latent infection, infectious tuberculosis, non-infectious tuberculosis, treatment failure, cured but latently infected, self-cured
- Extra parameters: case detection rate, cure rate, case detection rate, old programme, fraction cured, old programme, relative case detection rate of non-infectious cases, annual risk of infection, incidence rate, all forms, incidence rate, infectious cases, prevalence rate, infectious cases, death rate, all forms, population growth, change in annual risk of infection, change in incidence rate, change in death rate, change in contact rate, hiv-1 infection in tuberculosis cases, 2020

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 47.4% | 30% |
| **Reference agreement** | 85.9% | 30% |
| **Fill traceability** | 75.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **75.0/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 6 | 17 | 15 | 38 |
| **Extra in model** | Model items not in reference (noise/convention) | 6 | 16 | 9 | 31 |

## 5. Gap filling results
- Filled via **RAG**: 47
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 11

### latentinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectioustuberculosis (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### noninfectioustuberculosis (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### treatmentfailurestate (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### selfcuredstate (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### Susceptible->LatentInfection (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible->InfectiousTuberculosis (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible->NonInfectiousTuberculosis (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### LatentInfection->InfectiousTuberculosis (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### LatentInfection->NonInfectiousTuberculosis (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### InfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### InfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### NonInfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### NonInfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### OnTreatment->LatentInfection (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### OnTreatment->TreatmentFailureState (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 2 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### TreatmentFailureState->InfectiousTuberculosis (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### TreatmentFailureState->NonInfectiousTuberculosis (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### SelfCuredState->InfectiousTuberculosis (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### SelfCuredState->NonInfectiousTuberculosis (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **8**
- Precision **0.875** | Recall **1.0** | F1 **0.9333**
### Flows
- Gold count: **15** | Candidate: **17**
- Precision **0.6471** | Recall **1.0** | F1 **0.7857**
