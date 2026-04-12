# Phase 3 Gap Analysis Report

**Disease / context:** measles2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **18**
- Missing compartments: 3
- Missing parameters: 9
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 6
- Extra compartments (not in gold standard): 2
- Extra parameters (not in gold standard): 15
- Extra flows (not in gold standard): 2

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **18**
- After fills gaps (re-detected): **9**
- Delta (before - after): **9**
- Delta missing parameters: **9**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **maternalprotected** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinatedonedose** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinatedtwodose** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **MaternalProtected->Susceptible** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->VaccinatedOneDose** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedOneDose->VaccinatedTwoDose** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedOneDose->Exposed** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedTwoDose->Exposed** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedTwoDose->Susceptible** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **maternalimmunitylossrate** — Present in gold standard but not in extracted model.
- **mmr1vaccinationrate** — Present in gold standard but not in extracted model.
- **susceptibleinfectionrate** — Present in gold standard but not in extracted model.
- **mmr2vaccinationrate** — Present in gold standard but not in extracted model.
- **onedosebreakthroughrate** — Present in gold standard but not in extracted model.
- **twodosebreakthroughrate** — Present in gold standard but not in extracted model.
- **vaccinewaningrate** — Present in gold standard but not in extracted model.
- **incubationprogressionrate** — Present in gold standard but not in extracted model.
- **recoveryrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: single vaccinated, double vaccinated
- Extra parameters: infection rate, duration of maternal immunity, seasonality of transmission, importation parameters, report rate of imported cases, vaccine effectiveness, existing immunity in older generations, parameters of spatial spread, waning rate, mmr1 coverage increase, mmr2 coverage increase, mmr2 schedule age, mmr2 school-entry age, current mmr2 schedule age, mmr1 schedule age

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 50.0% | 30% |
| **Reference agreement** | 52.2% | 30% |
| **Fill traceability** | 33.3% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **37.3/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 3 | 9 | 6 | 18 |
| **Extra in model** | Model items not in reference (noise/convention) | 2 | 15 | 2 | 19 |

## 5. Gap filling results
- Filled via **RAG**: 9
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 18

### maternalprotected (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### vaccinatedonedose (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### vaccinatedtwodose (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### maternalimmunitylossrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_openai_20260407_213203, p2_zika3_llm_gemini_20260407_211201

### mmr1vaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### susceptibleinfectionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### mmr2vaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### onedosebreakthroughrate (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639

### twodosebreakthroughrate (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639

### vaccinewaningrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### incubationprogressionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### recoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### MaternalProtected->Susceptible (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible->VaccinatedOneDose (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### VaccinatedOneDose->VaccinatedTwoDose (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### VaccinatedOneDose->Exposed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### VaccinatedTwoDose->Exposed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### VaccinatedTwoDose->Susceptible (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### maternalprotected (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### vaccinatedonedose (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### vaccinatedtwodose (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### MaternalProtected->Susceptible (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible->VaccinatedOneDose (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### VaccinatedOneDose->VaccinatedTwoDose (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### VaccinatedOneDose->Exposed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### VaccinatedTwoDose->Exposed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### VaccinatedTwoDose->Susceptible (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

## 6. Fill validation (vs gold standard)
- Parameters compared: **9**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **9**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.96%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| maternalimmunitylossrate | 0.0001 | 0.167 | 99.94% | poor |
| mmr1vaccinationrate | 0.0001 | 0.25 | 99.96% | poor |
| susceptibleinfectionrate | 0.0001 | 0.8 | 99.99% | poor |
| mmr2vaccinationrate | 0.0001 | 0.18 | 99.94% | poor |
| onedosebreakthroughrate | 10000.0 | 0.08 | 12499900.0% | poor |
| twodosebreakthroughrate | 10000.0 | 0.01 | 99999900.0% | poor |
| vaccinewaningrate | 0.0001 | 0.002 | 95.0% | poor |
| incubationprogressionrate | 0.0001 | 0.125 | 99.92% | poor |
| recoveryrate | 0.5 | 0.143 | 249.65% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **6**
- Precision **0.6667** | Recall **0.5714** | F1 **0.6154**
### Flows
- Gold count: **9** | Candidate: **5**
- Precision **0.6** | Recall **0.3333** | F1 **0.4286**
