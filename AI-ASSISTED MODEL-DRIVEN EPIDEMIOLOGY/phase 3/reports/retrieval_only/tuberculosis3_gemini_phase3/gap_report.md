# Phase 3 Gap Analysis Report

**Disease / context:** tuberculosis3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **40**
- Missing compartments: 6
- Missing parameters: 18
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 16
- Extra compartments (not in gold standard): 6
- Extra parameters (not in gold standard): 19
- Extra flows (not in gold standard): 16

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **40**
- After fills gaps (re-detected): **22**
- Delta (before - after): **18**
- Delta missing parameters: **18**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **recenttbinfection** — Present in gold standard but not in extracted model. (severity: high)
- **remotetbinfection** — Present in gold standard but not in extracted model. (severity: high)
- **activetbsmearpositive** — Present in gold standard but not in extracted model. (severity: high)
- **activetbsmearnegative** — Present in gold standard but not in extracted model. (severity: high)
- **activetbextrapulmonary** — Present in gold standard but not in extracted model. (severity: high)
- **recentlytreatedrecovered** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Uninfected->RecentTBInfection** — Present in gold standard but not in extracted model (or flows list).
- **RecentTBInfection->RemoteTBInfection** — Present in gold standard but not in extracted model (or flows list).
- **RecentTBInfection->ActiveTBSmearPositive** — Present in gold standard but not in extracted model (or flows list).
- **RecentTBInfection->ActiveTBSmearNegative** — Present in gold standard but not in extracted model (or flows list).
- **RecentTBInfection->ActiveTBExtrapulmonary** — Present in gold standard but not in extracted model (or flows list).
- **RemoteTBInfection->RecentTBInfection** — Present in gold standard but not in extracted model (or flows list).
- **RemoteTBInfection->ActiveTBSmearPositive** — Present in gold standard but not in extracted model (or flows list).
- **RemoteTBInfection->ActiveTBSmearNegative** — Present in gold standard but not in extracted model (or flows list).
- **RemoteTBInfection->ActiveTBExtrapulmonary** — Present in gold standard but not in extracted model (or flows list).
- **ActiveTBSmearPositive->RecentlyTreatedRecovered** — Present in gold standard but not in extracted model (or flows list).
- **ActiveTBSmearNegative->RecentlyTreatedRecovered** — Present in gold standard but not in extracted model (or flows list).
- **ActiveTBExtrapulmonary->RecentlyTreatedRecovered** — Present in gold standard but not in extracted model (or flows list).
- **RecentlyTreatedRecovered->RemoteTBInfection** — Present in gold standard but not in extracted model (or flows list).
- **RecentlyTreatedRecovered->ActiveTBSmearPositive** — Present in gold standard but not in extracted model (or flows list).
- **RecentlyTreatedRecovered->ActiveTBSmearNegative** — Present in gold standard but not in extracted model (or flows list).
- **RecentlyTreatedRecovered->ActiveTBExtrapulmonary** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **infectionratefromsmearpositive** — Present in gold standard but not in extracted model.
- **infectionratefromsmearnegative** — Present in gold standard but not in extracted model.
- **recenttoremotestabilizationrate** — Present in gold standard but not in extracted model.
- **rapidprogressiontosmearpositiverate** — Present in gold standard but not in extracted model.
- **rapidprogressiontosmearnegativerate** — Present in gold standard but not in extracted model.
- **rapidprogressiontoextrapulmonaryrate** — Present in gold standard but not in extracted model.
- **reinfectionratefromsmearpositive** — Present in gold standard but not in extracted model.
- **reinfectionratefromsmearnegative** — Present in gold standard but not in extracted model.
- **remotereactivationtosmearpositiverate** — Present in gold standard but not in extracted model.
- **remotereactivationtosmearnegativerate** — Present in gold standard but not in extracted model.
- **remotereactivationtoextrapulmonaryrate** — Present in gold standard but not in extracted model.
- **treatmentorselfcureratesmearpositive** — Present in gold standard but not in extracted model.
- **treatmentorselfcureratesmearnegative** — Present in gold standard but not in extracted model.
- **treatmentorselfcurerateextrapulmonary** — Present in gold standard but not in extracted model.
- **recoveredtoremotestabilizationrate** — Present in gold standard but not in extracted model.
- **relapsetosmearpositiverate** — Present in gold standard but not in extracted model.
- **relapsetosmearnegativerate** — Present in gold standard but not in extracted model.
- **relapsetoextrapulmonaryrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: recently infected latent tb, remotely infected latent tb, active tb smear-positive pulmonary, active tb smear-negative pulmonary, active tb extrapulmonary, recently treated and recovered
- Extra parameters: beta, relative_infectiousness_smear_negative, relative_rate_early_progression, rapid_progression_rate, slow_progression_rate, proportion_extrapulmonary, proportion_smear_positive, mortality_rate_smear_positive, mortality_rate_smear_negative_extrapulmonary, natural_cure_rate_smear_positive, natural_cure_rate_smear_negative_extrapulmonary, informal_sector_size, treatment_success_notified_2008, treatment_success_informal, relapse_rate, population_growth_rate, diagnostic_rate_smear_positive_2008, diagnostic_rate_smear_negative_2008, diagnostic_rate_extrapulmonary_2008

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 45.0% | 30% |
| **Reference agreement** | 28.6% | 30% |
| **Fill traceability** | 29.0% | 20% |
| **Parameter accuracy** | 5.6% | 20% |
| **→ Composite** | **29.0/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 6 | 18 | 16 | 40 |
| **Extra in model** | Model items not in reference (noise/convention) | 6 | 19 | 16 | 41 |

## 5. Gap filling results
- Filled via **RAG**: 18
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 44

### recenttbinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### remotetbinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### activetbsmearpositive (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### activetbsmearnegative (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### activetbextrapulmonary (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### recentlytreatedrecovered (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectionratefromsmearpositive (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### infectionratefromsmearnegative (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### recenttoremotestabilizationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### rapidprogressiontosmearpositiverate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### rapidprogressiontosmearnegativerate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### rapidprogressiontoextrapulmonaryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### reinfectionratefromsmearpositive (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### reinfectionratefromsmearnegative (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### remotereactivationtosmearpositiverate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### remotereactivationtosmearnegativerate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### remotereactivationtoextrapulmonaryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### treatmentorselfcureratesmearpositive (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### treatmentorselfcureratesmearnegative (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### treatmentorselfcurerateextrapulmonary (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### recoveredtoremotestabilizationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### relapsetosmearpositiverate (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### relapsetosmearnegativerate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### relapsetoextrapulmonaryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### Uninfected->RecentTBInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentTBInfection->RemoteTBInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentTBInfection->ActiveTBSmearPositive (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentTBInfection->ActiveTBSmearNegative (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentTBInfection->ActiveTBExtrapulmonary (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RemoteTBInfection->RecentTBInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RemoteTBInfection->ActiveTBSmearPositive (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RemoteTBInfection->ActiveTBSmearNegative (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RemoteTBInfection->ActiveTBExtrapulmonary (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ActiveTBSmearPositive->RecentlyTreatedRecovered (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ActiveTBSmearNegative->RecentlyTreatedRecovered (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ActiveTBExtrapulmonary->RecentlyTreatedRecovered (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentlyTreatedRecovered->RemoteTBInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentlyTreatedRecovered->ActiveTBSmearPositive (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentlyTreatedRecovered->ActiveTBSmearNegative (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentlyTreatedRecovered->ActiveTBExtrapulmonary (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### recenttbinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### remotetbinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### activetbsmearpositive (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### activetbsmearnegative (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### activetbextrapulmonary (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### recentlytreatedrecovered (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### Uninfected->RecentTBInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentTBInfection->RemoteTBInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentTBInfection->ActiveTBSmearPositive (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentTBInfection->ActiveTBSmearNegative (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentTBInfection->ActiveTBExtrapulmonary (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RemoteTBInfection->RecentTBInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RemoteTBInfection->ActiveTBSmearPositive (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RemoteTBInfection->ActiveTBSmearNegative (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RemoteTBInfection->ActiveTBExtrapulmonary (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ActiveTBSmearPositive->RecentlyTreatedRecovered (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ActiveTBSmearNegative->RecentlyTreatedRecovered (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ActiveTBExtrapulmonary->RecentlyTreatedRecovered (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentlyTreatedRecovered->RemoteTBInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentlyTreatedRecovered->ActiveTBSmearPositive (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentlyTreatedRecovered->ActiveTBSmearNegative (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### RecentlyTreatedRecovered->ActiveTBExtrapulmonary (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

## 6. Fill validation (vs gold standard)
- Parameters compared: **18**
- Exact match (<1% error): **1**
- Close (<10% error): **0**
- Approximate (<50% error): **1**
- Poor (>50% error): **16**
- **Accuracy (exact+close)**: **5.6%**
- Median relative error: **99.85%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| infectionratefromsmearpositive | 0.0001 | 0.3 | 99.97% | poor |
| infectionratefromsmearnegative | 0.0001 | 0.066 | 99.85% | poor |
| recenttoremotestabilizationrate | 0.0001 | 0.5 | 99.98% | poor |
| rapidprogressiontosmearpositiverate | 0.0001 | 0.0455 | 99.78% | poor |
| rapidprogressiontosmearnegativerate | 0.0001 | 0.0245 | 99.59% | poor |
| rapidprogressiontoextrapulmonaryrate | 0.0001 | 0.0105 | 99.05% | poor |
| reinfectionratefromsmearpositive | 0.0001 | 0.18 | 99.94% | poor |
| reinfectionratefromsmearnegative | 0.0001 | 0.04 | 99.75% | poor |
| remotereactivationtosmearpositiverate | 0.0001 | 0.000325 | 69.23% | poor |
| remotereactivationtosmearnegativerate | 0.0001 | 0.000175 | 42.86% | approximate |
| remotereactivationtoextrapulmonaryrate | 0.0001 | 0.0001 | 0.0% | exact |
| treatmentorselfcureratesmearpositive | 0.0001 | 0.95 | 99.99% | poor |
| treatmentorselfcureratesmearnegative | 0.0001 | 0.43 | 99.98% | poor |
| treatmentorselfcurerateextrapulmonary | 0.0001 | 0.77 | 99.99% | poor |
| recoveredtoremotestabilizationrate | 0.0001 | 0.2 | 99.95% | poor |
| relapsetosmearpositiverate | 0.5 | 0.012 | 4066.67% | poor |
| relapsetosmearnegativerate | 0.0001 | 0.008 | 98.75% | poor |
| relapsetoextrapulmonaryrate | 0.0001 | 0.004 | 97.5% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **7**
- Precision **0.5714** | Recall **0.5714** | F1 **0.5714**
### Flows
- Gold count: **16** | Candidate: **16**
- Precision **0.0** | Recall **0.0** | F1 **0.0**
