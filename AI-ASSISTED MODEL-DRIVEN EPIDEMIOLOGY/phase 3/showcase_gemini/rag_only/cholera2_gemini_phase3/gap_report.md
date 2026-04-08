# Phase 3 Gap Analysis Report

**Disease / context:** cholera2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **48**
- Missing compartments: 6
- Missing parameters: 24
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 18
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 9
- Extra flows (not in gold standard): 1

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **48**
- After fills gaps (re-detected): **0**
- Delta (before - after): **48**
- Delta missing parameters: **24**
- Delta missing compartments: **6**
- Delta missing flows: **18**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **vaccinated adults (two doses)** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated children under 5 (two doses)** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated adults (one dose)** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated children under 5 (one dose)** — Present in gold standard but not in extracted model. (severity: high)
- **exposed / recently infected** — Present in gold standard but not in extracted model. (severity: high)
- **environmental vibrio cholerae reservoir** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible adults->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible adults->Vaccinated adults (two doses)** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible adults->Vaccinated adults (one dose)** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible children under 5->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible children under 5->Vaccinated children under 5 (two doses)** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible children under 5->Vaccinated children under 5 (one dose)** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (two doses)->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (two doses)->Susceptible adults** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated children under 5 (two doses)->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated children under 5 (two doses)->Susceptible children under 5** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (one dose)->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (one dose)->Susceptible adults** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated children under 5 (one dose)->Exposed / recently infected** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated children under 5 (one dose)->Susceptible children under 5** — Present in gold standard but not in extracted model (or flows list).
- **Exposed / recently infected->Symptomatic infectious** — Present in gold standard but not in extracted model (or flows list).
- **Exposed / recently infected->Asymptomatic infectious** — Present in gold standard but not in extracted model (or flows list).
- **Symptomatic infectious->Environmental Vibrio cholerae reservoir** — Present in gold standard but not in extracted model (or flows list).
- **Asymptomatic infectious->Environmental Vibrio cholerae reservoir** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **βa** — Present in gold standard but not in extracted model.
- **βc** — Present in gold standard but not in extracted model.
- **βa_2dose** — Present in gold standard but not in extracted model.
- **βc_2dose** — Present in gold standard but not in extracted model.
- **βa_1dose** — Present in gold standard but not in extracted model.
- **βc_1dose** — Present in gold standard but not in extracted model.
- **σ*k** — Present in gold standard but not in extracted model.
- **(1-σ)*k** — Present in gold standard but not in extracted model.
- **γ** — Present in gold standard but not in extracted model.
- **ν2** — Present in gold standard but not in extracted model.
- **ν1** — Present in gold standard but not in extracted model.
- **ω2** — Present in gold standard but not in extracted model.
- **ω1** — Present in gold standard but not in extracted model.
- **ωr** — Present in gold standard but not in extracted model.
- **ξs** — Present in gold standard but not in extracted model.
- **ξa** — Present in gold standard but not in extracted model.
- **λa** — Present in gold standard but not in extracted model.
- **λc** — Present in gold standard but not in extracted model.
- **δ** — Present in gold standard but not in extracted model.
- **μb** — Present in gold standard but not in extracted model.
- **ve2a** — Present in gold standard but not in extracted model.
- **ve2c** — Present in gold standard but not in extracted model.
- **ve1a** — Present in gold standard but not in extracted model.
- **ve1c** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: initial vaccine protection, vaccine protection duration, single-dose protection duration, baseline two-dose vaccine coverage, baseline one-dose vaccine coverage, no vaccine coverage, high-coverage two-dose vaccine coverage, high-coverage one-dose vaccine coverage, high-coverage no vaccine coverage

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 98.6% | 30% |
| **Fill traceability** | 87.5% | 20% |
| **Parameter accuracy** | 91.7% | 20% |
| **→ Composite** | **95.4/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 6 | 24 | 18 | 48 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 9 | 1 | 10 |

## 5. Gap filling results
- Filled via **RAG**: 42
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 6

### vaccinated adults (two doses) (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### vaccinated children under 5 (two doses) (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### vaccinated adults (one dose) (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### vaccinated children under 5 (one dose) (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### exposed / recently infected (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### environmental vibrio cholerae reservoir (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### βa (missing_parameters)
- **Source:** rag
- **Value:** 0.35 1/week
- **Description:** Adult environmental transmission/contact rate
- **From papers:** p1_model_measles, p1_model_cholera, p1_model_dengue

### βc (missing_parameters)
- **Source:** rag
- **Value:** 0.35 1/week
- **Description:** Child environmental transmission/contact rate
- **From papers:** p1_model_measles, p1_model_cholera

### βa_2dose (missing_parameters)
- **Source:** rag
- **Value:** βA * (1 - VE2A) 1/week
- **Description:** Breakthrough transmission rate for adults after two doses
- **From papers:** p1_model_cholera, p1_model_hiv, p1_model_dengue

### βc_2dose (missing_parameters)
- **Source:** rag
- **Value:** βC * (1 - VE2C) 1/week
- **Description:** Breakthrough transmission rate for children under 5 after two doses
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_dengue

### βa_1dose (missing_parameters)
- **Source:** rag
- **Value:** βA * (1 - VE1A) 1/week
- **Description:** Breakthrough transmission rate for adults after one dose
- **From papers:** p1_model_cholera, p1_model_hiv, p1_model_dengue

### βc_1dose (missing_parameters)
- **Source:** rag
- **Value:** βC * (1 - VE1C) 1/week
- **Description:** Breakthrough transmission rate for children under 5 after one dose
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_dengue

### σ*k (missing_parameters)
- **Source:** rag
- **Value:** 0.14 1/week
- **Description:** Progression from exposure to symptomatic infection
- **From papers:** p1_model_dengue, p2_zika3_llm_gemini_20260407_211201, p1_model_cholera

### (1-σ)*k (missing_parameters)
- **Source:** rag
- **Value:** 0.21 1/week
- **Description:** Progression from exposure to asymptomatic infection
- **From papers:** p1_model_dengue, p2_zika3_llm_gemini_20260407_211201, p1_model_cholera

### γ (missing_parameters)
- **Source:** rag
- **Value:** 0.11834  days
- **Description:** Probability an exposed host becomes symptomatic
- **From papers:** p1_model_measles, p1_model_tuberculosis, p2_zika3_llm_gemini_20260407_211201

### ν2 (missing_parameters)
- **Source:** rag
- **Value:** 0.015 1/week
- **Description:** Campaign vaccination rate into two-dose classes; scenario-dependent rollout parameter
- **From papers:** p1_model_covid, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### ν1 (missing_parameters)
- **Source:** rag
- **Value:** 0.002 1/week
- **Description:** Campaign vaccination rate into one-dose classes; scenario-dependent rollout parameter
- **From papers:** p1_model_covid, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### ω2 (missing_parameters)
- **Source:** rag
- **Value:** 0.00385 1/week
- **Description:** Waning rate of two-dose protection; approximately 5-year horizon
- **From papers:** p1_model_measles, p1_model_ebola, p1_model_cholera

### ω1 (missing_parameters)
- **Source:** rag
- **Value:** 0.01923 1/week
- **Description:** Waning rate of one-dose protection; protection assumed lost after about 1 year
- **From papers:** p1_model_measles, p1_model_ebola, p1_model_cholera

### ωr (missing_parameters)
- **Source:** rag
- **Value:** 0.01 1/week
- **Description:** Waning rate of natural immunity
- **From papers:** p1_model_measles, p1_model_covid, p1_model_cholera

### ξs (missing_parameters)
- **Source:** rag
- **Value:** 0.8 1/week
- **Description:** Shedding rate from symptomatic infectious individuals into environmental reservoir
- **From papers:** p2_covid3_llm_gemini_20260407_204521, p2_covid3_llm_claude_20260407_213850, p2_cholera3_llm_claude_20260407_213639

### ξa (missing_parameters)
- **Source:** rag
- **Value:** 0.2 1/week
- **Description:** Shedding rate from asymptomatic infectious individuals into environmental reservoir
- **From papers:** p1_model_measles, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### λa (missing_parameters)
- **Source:** rag
- **Value:** 2500 persons/week
- **Description:** Recruitment rate into adult susceptible class
- **From papers:** p1_model_measles, p1_model_hiv, p2_cholera3_llm_claude_20260407_213639

### λc (missing_parameters)
- **Source:** rag
- **Value:** 2500 persons/week
- **Description:** Recruitment rate into adult susceptible class
- **From papers:** p1_model_measles, p1_model_covid, p1_model_hiv

### δ (missing_parameters)
- **Source:** rag
- **Value:** 0.04545 days
- **Description:** human infection rate
- **From papers:** p1_model_measles, p1_model_tuberculosis, p1_model_hiv

### μb (missing_parameters)
- **Source:** rag
- **Value:** 0.6 1/week
- **Description:** Environmental bacterial decay rate
- **From papers:** p1_model_measles, p1_model_ebola, p1_model_dengue

### ve2a (missing_parameters)
- **Source:** rag
- **Value:** 0.76 dimensionless
- **Description:** Initial effectiveness of two-dose OCV in adults
- **From papers:** p1_model_measles, p1_model_cholera, p2_cholera3_llm_claude_20260407_213639

### ve2c (missing_parameters)
- **Source:** rag
- **Value:** 0.469 * VE2A dimensionless
- **Description:** Two-dose vaccine effectiveness in children under 5, scaled as 46.9% of adult protection
- **From papers:** p1_model_measles, p1_model_covid, p2_cholera3_llm_claude_20260407_213639

### ve1a (missing_parameters)
- **Source:** rag
- **Value:** 0.76 dimensionless
- **Description:** One-dose adult protection during the first year, assumed equal initially to two-dose protection
- **From papers:** p1_model_measles, p1_model_cholera, p2_cholera3_llm_claude_20260407_213639

### ve1c (missing_parameters)
- **Source:** rag
- **Value:** 0.469 * VE1A dimensionless
- **Description:** One-dose protection in children under 5 during the first year
- **From papers:** p1_model_measles, p1_model_covid, p2_cholera3_llm_claude_20260407_213639

### Susceptible adults->Exposed / recently infected (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible adults->Vaccinated adults (two doses) (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible adults->Vaccinated adults (one dose) (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible children under 5->Exposed / recently infected (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible children under 5->Vaccinated children under 5 (two doses) (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible children under 5->Vaccinated children under 5 (one dose) (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Vaccinated adults (two doses)->Exposed / recently infected (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Vaccinated adults (two doses)->Susceptible adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Vaccinated children under 5 (two doses)->Exposed / recently infected (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Vaccinated children under 5 (two doses)->Susceptible children under 5 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Vaccinated adults (one dose)->Exposed / recently infected (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Vaccinated adults (one dose)->Susceptible adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Vaccinated children under 5 (one dose)->Exposed / recently infected (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Vaccinated children under 5 (one dose)->Susceptible children under 5 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Exposed / recently infected->Symptomatic infectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Exposed / recently infected->Asymptomatic infectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Symptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Asymptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 6. Fill validation (vs gold standard)
- Parameters compared: **24**
- Exact match (<1% error): **22**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **2**
- **Accuracy (exact+close)**: **91.7%**
- Median relative error: **0.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| βa | 0.35 | 0.35 | 0.0% | exact |
| βc | 0.35 | 0.35 | 0.0% | exact |
| βa_2dose | 1.0 | 1.0 | 0.0% | exact |
| βc_2dose | 1.0 | 1.0 | 0.0% | exact |
| βa_1dose | 1.0 | 1.0 | 0.0% | exact |
| βc_1dose | 1.0 | 1.0 | 0.0% | exact |
| σ*k | 0.14 | 0.14 | 0.0% | exact |
| (1-σ)*k | 0.21 | 0.21 | 0.0% | exact |
| γ | 0.11834 | 0.5 | 76.33% | poor |
| ν2 | 0.015 | 0.015 | 0.0% | exact |
| ν1 | 0.002 | 0.002 | 0.0% | exact |
| ω2 | 0.00385 | 0.00385 | 0.0% | exact |
| ω1 | 0.01923 | 0.01923 | 0.0% | exact |
| ωr | 0.01 | 0.01 | 0.0% | exact |
| ξs | 0.8 | 0.8 | 0.0% | exact |
| ξa | 0.2 | 0.2 | 0.0% | exact |
| λa | 2500.0 | 2500.0 | 0.0% | exact |
| λc | 2500.0 | 2500.0 | 0.0% | exact |
| δ | 0.04545 | 0.002 | 2172.5% | poor |
| μb | 0.6 | 0.6 | 0.0% | exact |
| ve2a | 0.76 | 0.76 | 0.0% | exact |
| ve2c | 0.469 | 0.469 | 0.0% | exact |
| ve1a | 0.76 | 0.76 | 0.0% | exact |
| ve1c | 0.469 | 0.469 | 0.0% | exact |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **11** | Candidate: **9**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **21** | Candidate: **18**
- Precision **0.9444** | Recall **1.0** | F1 **0.9714**
