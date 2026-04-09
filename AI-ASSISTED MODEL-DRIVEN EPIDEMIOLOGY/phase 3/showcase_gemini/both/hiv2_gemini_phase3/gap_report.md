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
| **Reference agreement** | 51.3% | 30% |
| **Fill traceability** | 58.3% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **60.7/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 5 | 10 | 7 | 22 |
| **Extra in model** | Model items not in reference (noise/convention) | 6 | 11 | 9 | 26 |

## 5. Gap filling results
- Filled via **RAG**: 24
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 10
- **Flagged** for manual review: 0

### earlyinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Acute Infection
- **Reasoning:** The excerpt

### chronicinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Chronic Infection
- **Reasoning:** The excerpt

### lateinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Treated Infected

### onart (missing_compartments)
- **Source:** inference
- **Primary name:** On ART

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** Treated

### Susceptible->EarlyInfection (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### EarlyInfection->ChronicInfection (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 1 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ChronicInfection->LateInfection (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 2 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ChronicInfection->OnART (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### LateInfection->OnART (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### LateInfection->Removed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### OnART->Removed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **13**
- Precision **0.3846** | Recall **0.8333** | F1 **0.5263**
### Flows
- Gold count: **7** | Candidate: **13**
- Precision **0.3846** | Recall **0.7143** | F1 **0.5**
