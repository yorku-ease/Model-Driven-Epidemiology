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
| **Reference agreement** | 75.0% | 30% |
| **Fill traceability** | 28.8% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **41.7/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 6 | 17 | 15 | 38 |
| **Extra in model** | Model items not in reference (noise/convention) | 6 | 16 | 9 | 31 |

## 5. Gap filling results
- Filled via **RAG**: 17
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 42

### latentinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectioustuberculosis (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### noninfectioustuberculosis (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### ontreatment (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### treatmentfailurestate (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### selfcuredstate (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### primaryinfectiontolatentrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### primaryinfectiontoinfectiousrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### primaryinfectiontononinfectiousrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### latentreactivationtoinfectiousrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### latentreactivationtononinfectiousrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### exogenousreinfectiontoinfectiousrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### exogenousreinfectiontononinfectiousrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### infectiouscasedetectionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### infectiousselfcurerate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### noninfectiouscasedetectionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### noninfectiousselfcurerate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### treatmentcurerate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### treatmentfailurerate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### failurerelapsetoinfectiousrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### failurerelapsetononinfectiousrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### selfcurerelapsetoinfectiousrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### selfcurerelapsetononinfectiousrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### Susceptible->LatentInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible->InfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible->NonInfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### LatentInfection->InfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### LatentInfection->NonInfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### NonInfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### NonInfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### OnTreatment->LatentInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### OnTreatment->TreatmentFailureState (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### TreatmentFailureState->InfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### TreatmentFailureState->NonInfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### SelfCuredState->InfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### SelfCuredState->NonInfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### latentinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectioustuberculosis (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### noninfectioustuberculosis (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### ontreatment (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### treatmentfailurestate (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### selfcuredstate (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### Susceptible->LatentInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible->InfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible->NonInfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### LatentInfection->InfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### LatentInfection->NonInfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### NonInfectiousTuberculosis->OnTreatment (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### NonInfectiousTuberculosis->SelfCuredState (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### OnTreatment->LatentInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### OnTreatment->TreatmentFailureState (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### TreatmentFailureState->InfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### TreatmentFailureState->NonInfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### SelfCuredState->InfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### SelfCuredState->NonInfectiousTuberculosis (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

## 6. Fill validation (vs gold standard)
- Parameters compared: **17**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **17**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.67%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| primaryinfectiontolatentrate | 0.0001 | 0.18 | 99.94% | poor |
| primaryinfectiontoinfectiousrate | 0.0001 | 0.015 | 99.33% | poor |
| primaryinfectiontononinfectiousrate | 0.0001 | 0.01 | 99.0% | poor |
| latentreactivationtoinfectiousrate | 0.0001 | 0.0025 | 96.0% | poor |
| latentreactivationtononinfectiousrate | 0.0001 | 0.0015 | 93.33% | poor |
| exogenousreinfectiontoinfectiousrate | 0.0001 | 0.006 | 98.33% | poor |
| exogenousreinfectiontononinfectiousrate | 0.0001 | 0.004 | 97.5% | poor |
| infectiouscasedetectionrate | 0.0001 | 0.7 | 99.99% | poor |
| infectiousselfcurerate | 0.0001 | 0.03 | 99.67% | poor |
| noninfectiouscasedetectionrate | 0.0001 | 0.42 | 99.98% | poor |
| noninfectiousselfcurerate | 0.0001 | 0.04 | 99.75% | poor |
| treatmentcurerate | 0.0001 | 0.85 | 99.99% | poor |
| treatmentfailurerate | 0.0001 | 0.15 | 99.93% | poor |
| failurerelapsetoinfectiousrate | 0.0001 | 0.06 | 99.83% | poor |
| failurerelapsetononinfectiousrate | 0.0001 | 0.04 | 99.75% | poor |
| selfcurerelapsetoinfectiousrate | 0.0001 | 0.03 | 99.67% | poor |
| selfcurerelapsetononinfectiousrate | 0.0001 | 0.02 | 99.5% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **7**
- Precision **0.8571** | Recall **0.8571** | F1 **0.8571**
### Flows
- Gold count: **15** | Candidate: **14**
- Precision **0.5714** | Recall **0.7333** | F1 **0.6423**
