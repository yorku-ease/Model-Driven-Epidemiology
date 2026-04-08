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
- After fills gaps (re-detected): **15**
- Delta (before - after): **25**
- Delta missing parameters: **18**
- Delta missing compartments: **3**
- Delta missing flows: **4**

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
| **Gap reduction** | 62.5% | 30% |
| **Reference agreement** | 74.5% | 30% |
| **Fill traceability** | 80.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **77.1/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 6 | 18 | 16 | 40 |
| **Extra in model** | Model items not in reference (noise/convention) | 6 | 19 | 16 | 41 |

## 5. Gap filling results
- Filled via **RAG**: 46
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 9
- **Flagged** for manual review: 0

### activetbsmearpositive (missing_compartments)
- **Source:** inference
- **Primary name:** activetbsmearpositive
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### activetbsmearnegative (missing_compartments)
- **Source:** inference
- **Primary name:** activetbsmearnegative
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### activetbextrapulmonary (missing_compartments)
- **Source:** inference
- **Primary name:** activetbextrapulmonary
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### RecentTBInfection->ActiveTBSmearPositive (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### RecentTBInfection->ActiveTBSmearNegative (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### RecentTBInfection->ActiveTBExtrapulmonary (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### RemoteTBInfection->ActiveTBSmearPositive (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### RemoteTBInfection->ActiveTBSmearNegative (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### RemoteTBInfection->ActiveTBExtrapulmonary (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ActiveTBSmearPositive->RecentlyTreatedRecovered (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ActiveTBSmearNegative->RecentlyTreatedRecovered (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ActiveTBExtrapulmonary->RecentlyTreatedRecovered (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### RecentlyTreatedRecovered->ActiveTBSmearPositive (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### RecentlyTreatedRecovered->ActiveTBSmearNegative (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### RecentlyTreatedRecovered->ActiveTBExtrapulmonary (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **10**
- Precision **0.7** | Recall **1.0** | F1 **0.8235**
### Flows
- Gold count: **16** | Candidate: **32**
- Precision **0.5** | Recall **1.0** | F1 **0.6667**
