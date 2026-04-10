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
| **Reference agreement** | 21.6% | 30% |
| **Fill traceability** | 29.4% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **26.0/100** | — |

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
- Filled via **inference**: 0
- **Flagged** for manual review: 24

### earlyinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### chronicinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### lateinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### onart (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### removed (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

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
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### EarlyInfection->ChronicInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ChronicInfection->LateInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ChronicInfection->OnART (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### LateInfection->OnART (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### LateInfection->Removed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### OnART->Removed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### earlyinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### chronicinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### lateinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### onart (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### removed (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### Susceptible->EarlyInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### EarlyInfection->ChronicInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ChronicInfection->LateInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ChronicInfection->OnART (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### LateInfection->OnART (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### LateInfection->Removed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### OnART->Removed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

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
- Gold count: **6** | Candidate: **7**
- Precision **0.2857** | Recall **0.3333** | F1 **0.3077**
### Flows
- Gold count: **7** | Candidate: **9**
- Precision **0.1111** | Recall **0.1429** | F1 **0.125**
