# Phase 3 Gap Analysis Report

**Disease / context:** covid1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **2**
- Missing compartments: 1
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 1
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 36
- Extra flows (not in gold standard): 1

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **2**
- After fills gaps (re-detected): **2**
- Delta (before - after): **0**
- Delta missing parameters: **0**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **covid deaths** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **ICU->COVID Deaths** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: dead
- Extra parameters: latent period, presymptomatic infectious period, infectious period mild to moderate, infectious period severe, r0, quarantine duration, relative risk of transmission for isolated cases, hospital length of stay non-icu, hospital length of stay pre-icu, icu length of stay, hospital length of stay post-icu, probability of severe infection, <15 yr, no comorbidities, probability of severe infection, 15-49 yr, no comorbidities, probability of severe infection, 50-69 yr, no comorbidities, probability of severe infection, >=70 yr, no comorbidities, probability of severe infection, <15 yr, comorbidities, probability of severe infection, 15-49 yr, comorbidities, probability of severe infection, 50-69 yr, comorbidities, probability of severe infection, >=70 yr, comorbidities, probability severe case requires icu, probability of death in icu, <15 yr, no comorbidities, probability of death in icu, 15-49 yr, no comorbidities, probability of death in icu, 50-69 yr, no comorbidities, probability of death in icu, >=70 yr, no comorbidities, probability of death in icu, <15 yr, comorbidities, probability of death in icu, 15-49 yr, comorbidities, probability of death in icu, 50-69 yr, comorbidities, probability of death in icu, >=70 yr, comorbidities, base case testing and isolation rate, <15 yr, base case testing and isolation rate, 15-49 yr, base case testing and isolation rate, >=50 yr, base case quarantine rate, base case physical distancing reduction, physical distancing contact reduction (restrictive), physical distancing contact reduction (combination), icu threshold for dynamic intervention

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 0.0% | 30% |
| **Reference agreement** | 91.7% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **47.5/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 0 | 1 | 2 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 36 | 1 | 38 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 4

### covid deaths (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### ICU->COVID Deaths (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### covid deaths (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### ICU->COVID Deaths (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **8** | Candidate: **15**
- Precision **0.9333** | Recall **0.875** | F1 **0.9032**
### Flows
- Gold count: **11** | Candidate: **21**
- Precision **0.9524** | Recall **0.9091** | F1 **0.9302**
