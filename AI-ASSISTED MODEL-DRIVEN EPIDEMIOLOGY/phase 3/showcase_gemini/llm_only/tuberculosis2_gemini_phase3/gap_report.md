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
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 5.9% | 20% |
| **→ Composite** | **50.4/100** | — |

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
- **Reasoning:** The

### infectioustuberculosis (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible
- **Reasoning:** The model

### noninfectioustuberculosis (missing_compartments)
- **Source:** inference
- **Primary name:** Non-Infectious Tuberculosis
- **Reasoning:** The text explicitly describes 'non-infectious' cases of tuberculosis as a distinct category, which would require a separate compartment in a detailed epidemiological model to differentiate from infectious cases.

### ontreatment (missing_compartments)
- **Source:** inference
- **Primary name:** On Treatment
- **Reasoning:** The excerpt

### treatmentfailurestate (missing_compartments)
- **Source:** inference
- **Primary name:** Treatment Failure State
- **Reasoning:** The

### selfcuredstate (missing_compartments)
- **Source:** inference
- **Primary name:** Self-Cured State

### primaryinfectiontolatentrate (missing_parameters)
- **Source:** inference
- **Value:** 0.9 
- **Reasoning:** A
- **Confidence:** LOW

### primaryinfectiontoinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** 0.075 year^-1
- **Confidence:** LOW

### primaryinfectiontononinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** 0.9 per year
- **Confidence:** LOW

### latentreactivationtoinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** 0.001 per year
- **Confidence:** LOW

### latentreactivationtononinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** 0.0001 per year
- **Confidence:** LOW

### exogenousreinfectiontoinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** 1e-05 per year
- **Confidence:** LOW

### exogenousreinfectiontononinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** 0.8 per year
- **Confidence:** LOW

### infectiouscasedetectionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.85 per year
- **Confidence:** LOW

### infectiousselfcurerate (missing_parameters)
- **Source:** inference
- **Value:** 0.05 per year
- **Confidence:** LOW

### noninfectiouscasedetectionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.7 per year
- **Confidence:** LOW

### noninfectiousselfcurerate (missing_parameters)
- **Source:** inference
- **Value:** 0.05 per year
- **Confidence:** LOW

### treatmentcurerate (missing_parameters)
- **Source:** inference
- **Value:** 0.92 
- **Confidence:** LOW

### treatmentfailurerate (missing_parameters)
- **Source:** inference
- **Value:** 0.05 
- **Confidence:** LOW

### failurerelapsetoinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** 0.8 per year
- **Confidence:** LOW

### failurerelapsetononinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** 0.05 per year
- **Confidence:** LOW

### selfcurerelapsetoinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** 0.005 per year
- **Confidence:** LOW

### selfcurerelapsetononinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** 0.005 per year
- **Confidence:** LOW

### Susceptible->LatentInfection (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals acquire new infection and transition to the LatentInfection compartment.
- **Reasoning:** The text describes 'transmission of infection' and 'newly infected' individuals developing tuberculosis, implying that susceptible individuals become latently infected through

### Susceptible->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become newly infected with tuberculosis through contact with infectious individuals, subsequently developing progressive primary disease and becoming infectious themselves.
- **Reasoning:** The text refers to 'transmission of infection' and 'newly infected' individuals, indicating that the transition from Susceptible to InfectiousTuberculosis is driven by contact with existing infectious cases.

### Susceptible->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become newly

### LatentInfection->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression from latent tuberculosis infection to active infectious tuberculosis disease via endogenous reactivation or exogenous reinfection.
- **Reasoning:** The text describes this transition as endogenous reactivation or exogenous reinfection from a latent state, which is an internal progression typically modeled as a a rate.

### LatentInfection->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Endogenous reactivation of latent tuberculosis infection leading to the development of non-infectious active tuberculosis.
- **Reasoning:** The text states that tuberculosis arises from endogenous reactivation in those with remote (latent) infections, which

### InfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Infectious individuals

### InfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which

### NonInfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with non-

### NonInfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with non-

### OnTreatment->LatentInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals undergoing

### OnTreatment->TreatmentFailureState (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals undergoing tuberculosis treatment fail to be cured and transition to a state of treatment failure.
- **Reasoning:** Treatment failure is an intrinsic outcome for individuals receiving treatment, occurring at a specific rate rather than through contact with others.

### TreatmentFailureState->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals whose tuberculosis treatment has failed progress to an infectious state.
- **Reasoning:** Treatment failure implies the inability to cure the disease, leading to the persistence or re-emergence of active, infectious tuberculosis.

### TreatmentFailureState->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals whose

### SelfCuredState->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Reactivation of latent tuberculosis

### SelfCuredState->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who were

### latentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Latent Infection
- **Reasoning:** The

### infectioustuberculosis (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Tuberculosis

### noninfectioustuberculosis (missing_compartments)
- **Source:** inference
- **Primary name:** Non-Infectious Tuberculosis
- **Reasoning:** The excerpt explicitly describes 'non-infectious' cases of tuberculosis (pulmonary but sputum-smear negative, or extrapulmonary) as a distinct category from infectious cases, implying it should be a separate compartment in the model.

### ontreatment (missing_compartments)
- **Source:** inference
- **Primary name:** On Treatment

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
- **Description:** Susceptible individuals acquire

### Susceptible->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become newly

### Susceptible->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become

### LatentInfection->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Endogenous reactivation of latent tuberculosis infection into active, infectious tuberculosis.
- **Reasoning:** The text describes tuberculosis arising from 'endogenous reactivation' in people with 'remote (latent) infections,' which is an internal progression rate rather than a contact-driven event.

### LatentInfection->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Reactivation of latent tuberculosis infection into non-infectious active tuberculosis disease.
- **Reasoning:** The text describes tuberculosis arising from latent infections via endogenous reactivation, which is an internal process typically modeled as a rate rather than through contact.

### InfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Infectious individuals are diagnosed and initiate anti-

### InfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Natural resolution of infectious

### NonInfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with non-

### NonInfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Natural recovery from non-

### OnTreatment->LatentInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals successfully treated for

### OnTreatment->TreatmentFailureState (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals undergoing tuberculosis treatment fail

### TreatmentFailureState->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who fail tuberculosis treatment can progress to an infectious tuberculosis state.
- **Reasoning:** Treatment failure implies that the disease is not cured, leading to a return or persistence of the infectious state, which is a progression within the individual.

### TreatmentFailureState->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who

### SelfCuredState->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Endogenous reactivation of

### SelfCuredState->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in a self-

## 6. Fill validation (vs gold standard)
- Parameters compared: **17**
- Exact match (<1% error): **0**
- Close (<10% error): **1**
- Approximate (<50% error): **3**
- Poor (>50% error): **13**
- **Accuracy (exact+close)**: **5.9%**
- Median relative error: **75.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| primaryinfectiontolatentrate | 0.9 | 0.18 | 400.0% | poor |
| primaryinfectiontoinfectiousrate | 0.075 | 0.015 | 400.0% | poor |
| primaryinfectiontononinfectiousrate | 0.9 | 0.01 | 8900.0% | poor |
| latentreactivationtoinfectiousrate | 0.001 | 0.0025 | 60.0% | poor |
| latentreactivationtononinfectiousrate | 0.0001 | 0.0015 | 93.33% | poor |
| exogenousreinfectiontoinfectiousrate | 1e-05 | 0.006 | 99.83% | poor |
| exogenousreinfectiontononinfectiousrate | 0.8 | 0.004 | 19900.0% | poor |
| infectiouscasedetectionrate | 0.85 | 0.7 | 21.43% | approximate |
| infectiousselfcurerate | 0.05 | 0.03 | 66.67% | poor |
| noninfectiouscasedetectionrate | 0.7 | 0.42 | 66.67% | poor |
| noninfectiousselfcurerate | 0.05 | 0.04 | 25.0% | approximate |
| treatmentcurerate | 0.92 | 0.85 | 8.24% | close |
| treatmentfailurerate | 0.05 | 0.15 | 66.67% | poor |
| failurerelapsetoinfectiousrate | 0.8 | 0.06 | 1233.33% | poor |
| failurerelapsetononinfectiousrate | 0.05 | 0.04 | 25.0% | approximate |
| selfcurerelapsetoinfectiousrate | 0.005 | 0.03 | 83.33% | poor |
| selfcurerelapsetononinfectiousrate | 0.005 | 0.02 | 75.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **8**
- Precision **0.875** | Recall **1.0** | F1 **0.9333**
### Flows
- Gold count: **15** | Candidate: **17**
- Precision **0.6471** | Recall **1.0** | F1 **0.7857**
