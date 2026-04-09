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
- After fills gaps (re-detected): **21**
- Delta (before - after): **17**
- Delta missing parameters: **17**
- Delta missing compartments: **0**
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
| **Gap reduction** | 44.7% | 30% |
| **Reference agreement** | 85.9% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **59.2/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 6 | 17 | 15 | 38 |
| **Extra in model** | Model items not in reference (noise/convention) | 6 | 16 | 9 | 31 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 59
- **Flagged** for manual review: 0

### latentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Latent Infection
- **Reasoning:** The text explicitly states that tuberculosis can arise from 'remote (latent) infections,' indicating a distinct compartment for individuals who are infected but not actively diseased.

### infectioustuberculosis (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Tuberculosis

### noninfectioustuberculosis (missing_compartments)
- **Source:** inference
- **Primary name:** Non-Infectious Tuberculosis
- **Reasoning:** The text explicitly distinguishes between infectious and non-infectious cases of tuberculosis, indicating that non-infectious cases represent a distinct epidemiological state that should be modeled.

### ontreatment (missing_compartments)
- **Source:** inference
- **Primary name:** On Treatment
- **Reasoning:** The text

### treatmentfailurestate (missing_compartments)
- **Source:** inference
- **Primary name:** Treatment Failure
- **Reasoning:** The

### selfcuredstate (missing_compartments)
- **Source:** inference
- **Primary name:** Self-Cured State

### Susceptible->LatentInfection (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Acquisition of new

### Susceptible->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Transmission of tuberculosis infection

### Susceptible->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible

### LatentInfection->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression

### LatentInfection->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Endogenous

### InfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Infectious

### InfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Spontaneous resolution of

### NonInfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with non-infect

### NonInfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Spontaneous recovery from

### OnTreatment->LatentInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals successfully complete treatment for

### OnTreatment->TreatmentFailureState (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals undergoing tuberculosis treatment may fail to be cured and transition to a state of treatment failure.
- **Reasoning:** Even with high cure rates, a proportion of treated individuals will not be cured and will experience treatment failure, a common outcome in tuberculosis epidemiology.

### TreatmentFailureState->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals whose tuberculosis treatment has

### TreatmentFailureState->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals whose tuberculosis treatment has

### SelfCuredState->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Reactivation of latent

### SelfCuredState->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Reactivation of latent tuberculosis

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **8**
- Precision **0.875** | Recall **1.0** | F1 **0.9333**
### Flows
- Gold count: **15** | Candidate: **17**
- Precision **0.6471** | Recall **1.0** | F1 **0.7857**
