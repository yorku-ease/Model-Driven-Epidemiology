# Phase 3 Gap Analysis Report

**Disease / context:** measles3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **12**
- Missing compartments: 2
- Missing parameters: 7
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 3
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 8
- Extra flows (not in gold standard): 3

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **12**
- After fills gaps (re-detected): **5**
- Delta (before - after): **7**
- Delta missing parameters: **7**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **maternalimmunity** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinefailure** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **MaternalImmunity->Susceptible** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->VaccineFailure** — Present in gold standard but not in extracted model (or flows list).
- **VaccineFailure->Latent** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **maternalimmunitylossrate** — Present in gold standard but not in extracted model.
- **susceptibleinfectionrate** — Present in gold standard but not in extracted model.
- **effectivevaccinationrate** — Present in gold standard but not in extracted model.
- **vaccinefailurerate** — Present in gold standard but not in extracted model.
- **vaccinefailureinfectionrate** — Present in gold standard but not in extracted model.
- **latentprogressionrate** — Present in gold standard but not in extracted model.
- **recoveryrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: vaccine failure
- Extra parameters: force of infection, rate of progression from latency, rate of recovery from infection, rate of loss of passive immunity, vaccine efficacy, birth rate, background death rate, contact rates

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 58.3% | 30% |
| **Reference agreement** | 88.3% | 30% |
| **Fill traceability** | 41.2% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **52.2/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 7 | 3 | 12 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 8 | 3 | 12 |

## 5. Gap filling results
- Filled via **RAG**: 7
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 10

### maternalimmunity (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### vaccinefailure (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### maternalimmunitylossrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_openai_20260407_213203, p2_zika3_llm_gemini_20260407_211201

### susceptibleinfectionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### effectivevaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### vaccinefailurerate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### vaccinefailureinfectionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### latentprogressionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### recoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### MaternalImmunity->Susceptible (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible->VaccineFailure (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### VaccineFailure->Latent (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### maternalimmunity (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### vaccinefailure (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### MaternalImmunity->Susceptible (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible->VaccineFailure (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### VaccineFailure->Latent (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

## 6. Fill validation (vs gold standard)
- Parameters compared: **7**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **7**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.98%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| maternalimmunitylossrate | 0.0001 | 4.0 | 100.0% | poor |
| susceptibleinfectionrate | 0.0001 | 0.52 | 99.98% | poor |
| effectivevaccinationrate | 0.0001 | 0.18 | 99.94% | poor |
| vaccinefailurerate | 0.0001 | 0.02 | 99.5% | poor |
| vaccinefailureinfectionrate | 0.0001 | 0.4 | 99.98% | poor |
| latentprogressionrate | 0.0001 | 52.14 | 100.0% | poor |
| recoveryrate | 0.5 | 52.14 | 99.04% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **6**
- Precision **1.0** | Recall **0.8333** | F1 **0.9091**
### Flows
- Gold count: **7** | Candidate: **7**
- Precision **0.8571** | Recall **0.8571** | F1 **0.8571**
