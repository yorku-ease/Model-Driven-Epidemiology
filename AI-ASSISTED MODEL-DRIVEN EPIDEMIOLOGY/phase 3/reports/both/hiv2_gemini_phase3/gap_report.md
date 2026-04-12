# Phase 3 Gap Analysis Report

**Disease / context:** hiv2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **22**
- Missing compartments: 5
- Missing parameters: 10
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 7
- Extra compartments (not in gold standard): 6
- Extra parameters (not in gold standard): 11
- Extra flows (not in gold standard): 9

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **22**
- After fills gaps (re-detected): **12**
- Delta (before - after): **10**
- Delta missing parameters: **10**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **earlyinfection** — Present in gold standard but not in extracted model. (severity: high)
- **chronicinfection** — Present in gold standard but not in extracted model. (severity: high)
- **lateinfection** — Present in gold standard but not in extracted model. (severity: high)
- **onart** — Present in gold standard but not in extracted model. (severity: high)
- **removed** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->EarlyInfection** — Present in gold standard but not in extracted model (or flows list).
- **EarlyInfection->ChronicInfection** — Present in gold standard but not in extracted model (or flows list).
- **ChronicInfection->LateInfection** — Present in gold standard but not in extracted model (or flows list).
- **ChronicInfection->OnART** — Present in gold standard but not in extracted model (or flows list).
- **LateInfection->OnART** — Present in gold standard but not in extracted model (or flows list).
- **LateInfection->Removed** — Present in gold standard but not in extracted model (or flows list).
- **OnART->Removed** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **betaearly** — Present in gold standard but not in extracted model.
- **betachronic** — Present in gold standard but not in extracted model.
- **betalate** — Present in gold standard but not in extracted model.
- **betaart** — Present in gold standard but not in extracted model.
- **progression1** — Present in gold standard but not in extracted model.
- **progression2** — Present in gold standard but not in extracted model.
- **artstart1** — Present in gold standard but not in extracted model.
- **artstart2** — Present in gold standard but not in extracted model.
- **deathlate** — Present in gold standard but not in extracted model.
- **deathart** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: early infection, asymptomatic infection, pre-aids infection, aids, on antiretroviral therapy, dropped out of treatment
- Extra parameters: cd4 treatment eligibility threshold, average time from eligibility to treatment initiation, treatment access, retention on treatment after 3 years, art introduction time, baseline future scale-up art starts, low future scale-up art starts, medium future scale-up art starts, high future scale-up art starts, art transmission reduction, early infection transmissibility increase

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 45.5% | 30% |
| **Reference agreement** | 40.2% | 30% |
| **Fill traceability** | 64.7% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **38.6/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 5 | 10 | 7 | 22 |
| **Extra in model** | Model items not in reference (noise/convention) | 6 | 11 | 9 | 26 |

## 5. Gap filling results
- Filled via **RAG**: 10
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 24
- **Flagged** for manual review: 0

### earlyinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Early Infection
- **Reasoning:** The

### chronicinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Chronic Infection
- **Reasoning:** The

### lateinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Late Stage Infection

### onart (missing_compartments)
- **Source:** inference
- **Primary name:** On ART
- **Reasoning:** The

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** Treated Infected
- **Reasoning:** The

### betaearly (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### betachronic (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639

### betalate (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### betaart (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### progression1 (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### progression2 (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### artstart1 (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### artstart2 (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### deathlate (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### deathart (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### Susceptible->EarlyInfection (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** A susceptible individual acquires HIV infection through contact with an infected individual, transitioning into the early infection stage.
- **Reasoning:** HIV is a communicable disease primarily transmitted through contact, and the excerpt discusses 'reducing the spread' and 'transmission of infection,' indicating a contact-driven process.

### EarlyInfection->ChronicInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression from early/acute HIV infection to chronic HIV infection.
- **Reasoning:** HIV infection naturally progresses from an early, acute phase to a chronic, often asymptomatic phase over time within an infected individual.

### ChronicInfection->LateInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV-infected individuals from the chronic asymptomatic or early symptomatic phase to the late symptomatic phase, often characterized by severe immunosuppression and opportunistic infections (AIDS).
- **Reasoning:** This transition represents the natural progression of HIV disease within an infected individual over time, driven by viral replication and immune system decline, rather than external contact.

### ChronicInfection->OnART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Initiation of antiretroviral therapy for individuals with chronic HIV infection.
- **Reasoning:** The excerpt discusses expanding the provision of antiretroviral therapy (ART) and immediate treatment for HIV-infected individuals, directly describing the transition from chronic infection to being on ART.

### LateInfection->OnART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in the late stage of HIV infection initiating antiretroviral therapy.
- **Reasoning:** The paper discusses the provision of ART to HIV-infected individuals, including strategies for immediate treatment and treatment according to guidelines, directly supporting a flow from an infected state to an 'OnART' state.

### LateInfection->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality due to

### OnART->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality (HIV-

### earlyinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Early Infection
- **Reasoning:** The excerpt

### chronicinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Chronic Infection
- **Reasoning:** The excerpt

### lateinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Chronic Infection

### onart (missing_compartments)
- **Source:** inference
- **Primary name:** On ART
- **Reasoning:** The excerpt extensively discusses the impact of antiretroviral therapy (ART) on HIV transmission and incidence, necessitating a compartment to represent individuals receiving treatment.

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible
- **Reasoning:** The excerpt discusses 'reducing the spread of HIV' and 'new infections could be averted,' which necessitates a compartment for individuals who are not yet infected but are at risk.

### Susceptible->EarlyInfection (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** The rate at which susceptible individuals acquire HIV infection through contact with infected individuals.
- **Reasoning:** The excerpt discusses the 'spread' and 'transmission' of HIV, indicating that new infections arise from interactions between susceptible and infected individuals.

### EarlyInfection->ChronicInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV infection from the early, often acute or asymptomatic, stage to the chronic stage within an infected individual.
- **Reasoning:** This flow represents the natural, time-dependent progression of HIV infection from an early stage to a chronic stage within an infected individual, a fundamental aspect of HIV natural history.

### ChronicInfection->LateInfection (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV

### ChronicInfection->OnART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with

### LateInfection->OnART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in a late stage of HIV infection initiate antiretroviral therapy (ART).
- **Reasoning:** The excerpt discusses the provision of ART as a treatment intervention for HIV-infected individuals, indicating a transition from an infected state to a treated state.

### LateInfection->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in the late stage

### OnART->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality or

## 6. Fill validation (vs gold standard)
- Parameters compared: **10**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **3**
- Poor (>50% error): **7**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.98%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| betaearly | 0.5 | 0.8 | 37.5% | approximate |
| betachronic | 10000.0 | 0.3 | 3333233.33% | poor |
| betalate | 0.5 | 0.6 | 16.67% | approximate |
| betaart | 0.5 | 0.05 | 900.0% | poor |
| progression1 | 0.0001 | 0.5 | 99.98% | poor |
| progression2 | 0.0001 | 0.2 | 99.95% | poor |
| artstart1 | 0.5 | 0.3 | 66.67% | poor |
| artstart2 | 0.5 | 0.4 | 25.0% | approximate |
| deathlate | 10000.0 | 0.2 | 4999900.0% | poor |
| deathart | 10000.0 | 0.05 | 19999900.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **11**
- Precision **0.3636** | Recall **0.6667** | F1 **0.4706**
### Flows
- Gold count: **7** | Candidate: **11**
- Precision **0.2727** | Recall **0.4286** | F1 **0.3333**
