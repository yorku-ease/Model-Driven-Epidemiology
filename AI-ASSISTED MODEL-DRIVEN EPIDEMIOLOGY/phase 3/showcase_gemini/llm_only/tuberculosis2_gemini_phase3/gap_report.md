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
- After fills gaps (re-detected): **37**
- Delta (before - after): **1**
- Delta missing parameters: **0**
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
| **Gap reduction** | 2.6% | 30% |
| **Reference agreement** | 85.9% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **46.6/100** | — |

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
- Filled via **inference**: 75
- **Flagged** for manual review: 0

### latentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** latentinfection
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### infectioustuberculosis (missing_compartments)
- **Source:** inference
- **Primary name:** infectioustuberculosis
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### noninfectioustuberculosis (missing_compartments)
- **Source:** inference
- **Primary name:** noninfectioustuberculosis
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### treatmentfailurestate (missing_compartments)
- **Source:** inference
- **Primary name:** treatmentfailurestate
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### selfcuredstate (missing_compartments)
- **Source:** inference
- **Primary name:** selfcuredstate
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### primaryinfectiontolatentrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### primaryinfectiontoinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### primaryinfectiontononinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### latentreactivationtoinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### latentreactivationtononinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### exogenousreinfectiontoinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### exogenousreinfectiontononinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### infectiouscasedetectionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### infectiousselfcurerate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### noninfectiouscasedetectionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### noninfectiousselfcurerate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### treatmentcurerate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### treatmentfailurerate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### failurerelapsetoinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### failurerelapsetononinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### selfcurerelapsetoinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### selfcurerelapsetononinfectiousrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### Susceptible->LatentInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for Susceptible->LatentInfection
- **Reasoning:** LLM unavailable.

### Susceptible->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for Susceptible->InfectiousTuberculosis
- **Reasoning:** LLM unavailable.

### Susceptible->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for Susceptible->NonInfectiousTuberculosis
- **Reasoning:** LLM unavailable.

### LatentInfection->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for LatentInfection->InfectiousTuberculosis
- **Reasoning:** LLM unavailable.

### LatentInfection->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for LatentInfection->NonInfectiousTuberculosis
- **Reasoning:** LLM unavailable.

### InfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for InfectiousTuberculosis->OnTreatment
- **Reasoning:** LLM unavailable.

### InfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for InfectiousTuberculosis->SelfCuredState
- **Reasoning:** LLM unavailable.

### NonInfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for NonInfectiousTuberculosis->OnTreatment
- **Reasoning:** LLM unavailable.

### NonInfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for NonInfectiousTuberculosis->SelfCuredState
- **Reasoning:** LLM unavailable.

### OnTreatment->LatentInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for OnTreatment->LatentInfection
- **Reasoning:** LLM unavailable.

### OnTreatment->TreatmentFailureState (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for OnTreatment->TreatmentFailureState
- **Reasoning:** LLM unavailable.

### TreatmentFailureState->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for TreatmentFailureState->InfectiousTuberculosis
- **Reasoning:** LLM unavailable.

### TreatmentFailureState->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for TreatmentFailureState->NonInfectiousTuberculosis
- **Reasoning:** LLM unavailable.

### SelfCuredState->InfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for SelfCuredState->InfectiousTuberculosis
- **Reasoning:** LLM unavailable.

### SelfCuredState->NonInfectiousTuberculosis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for SelfCuredState->NonInfectiousTuberculosis
- **Reasoning:** LLM unavailable.

## 6. Fill validation (vs gold standard)
- Parameters compared: **0**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| primaryinfectiontolatentrate | None | 0.18 | — | no_fill |
| primaryinfectiontoinfectiousrate | None | 0.015 | — | no_fill |
| primaryinfectiontononinfectiousrate | None | 0.010 | — | no_fill |
| latentreactivationtoinfectiousrate | None | 0.0025 | — | no_fill |
| latentreactivationtononinfectiousrate | None | 0.0015 | — | no_fill |
| exogenousreinfectiontoinfectiousrate | None | 0.006 | — | no_fill |
| exogenousreinfectiontononinfectiousrate | None | 0.004 | — | no_fill |
| infectiouscasedetectionrate | None | 0.70 | — | no_fill |
| infectiousselfcurerate | None | 0.03 | — | no_fill |
| noninfectiouscasedetectionrate | None | 0.42 | — | no_fill |
| noninfectiousselfcurerate | None | 0.04 | — | no_fill |
| treatmentcurerate | None | 0.85 | — | no_fill |
| treatmentfailurerate | None | 0.15 | — | no_fill |
| failurerelapsetoinfectiousrate | None | 0.06 | — | no_fill |
| failurerelapsetononinfectiousrate | None | 0.04 | — | no_fill |
| selfcurerelapsetoinfectiousrate | None | 0.03 | — | no_fill |
| selfcurerelapsetononinfectiousrate | None | 0.02 | — | no_fill |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **8**
- Precision **0.875** | Recall **1.0** | F1 **0.9333**
### Flows
- Gold count: **15** | Candidate: **17**
- Precision **0.6471** | Recall **1.0** | F1 **0.7857**
