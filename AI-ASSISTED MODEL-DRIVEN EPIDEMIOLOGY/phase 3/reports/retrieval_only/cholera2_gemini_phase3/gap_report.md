# Phase 3 Gap Analysis Report

**Disease / context:** cholera2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **45**
- Missing compartments: 5
- Missing parameters: 24
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 16
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 9
- Extra flows (not in gold standard): 0

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **45**
- After fills gaps (re-detected): **21**
- Delta (before - after): **24**
- Delta missing parameters: **24**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **vaccinated adults (two doses)** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated children under 5 (two doses)** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated adults (one dose)** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinated children under 5 (one dose)** — Present in gold standard but not in extracted model. (severity: high)
- **environmental vibrio cholerae reservoir** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible adults->Vaccinated adults (two doses)** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible adults->Vaccinated adults (one dose)** — Present in gold standard but not in extracted model (or flows list).
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
| **Gap reduction** | 53.3% | 30% |
| **Reference agreement** | 54.5% | 30% |
| **Fill traceability** | 36.4% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **39.6/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 5 | 24 | 16 | 45 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 9 | 0 | 9 |

## 5. Gap filling results
- Filled via **RAG**: 24
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 42

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

### environmental vibrio cholerae reservoir (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### βa (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### βc (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### βa_2dose (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### βc_2dose (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### βa_1dose (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### βc_1dose (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### σ*k (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_openai_20260407_213203, p2_cholera3_llm_openai_20260407_211654

### (1-σ)*k (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_openai_20260407_213203, p2_cholera3_llm_openai_20260407_211654

### γ (missing_parameters)
- **Source:** rag
- **Value:** 1/10 1/day
- **Description:** Rate at which successfully vaccinated individuals develop protection (mean 10 days)
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_hiv3_llm_claude_20260407_214604, p2_influenza3_llm_claude_20260407_214824

### ν2 (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_gemini_20260407_204240

### ν1 (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_gemini_20260407_204240

### ω2 (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### ω1 (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### ωr (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### ξs (missing_parameters)
- **Source:** rag
- **Value:** 10 days
- **Description:** Duration of inhibitory antimalarial blood concentration for short-acting drug
- **From papers:** p2_covid3_llm_openai_20260407_211832, p2_covid3_llm_gemini_20260407_204521, p2_dengue3_llm_claude_20260407_214050

### ξa (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_gemini_20260407_204240, p2_cholera3_llm_openai_20260407_211654, p2_dengue3_llm_claude_20260407_214050

### λa (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_gemini_20260407_204240

### λc (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_gemini_20260407_204240

### δ (missing_parameters)
- **Source:** rag
- **Value:** 0.029 /yr
- **Description:** Birth rate
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_hiv3_llm_gemini_20260407_205438, p2_cholera3_llm_gemini_20260407_204240

### μb (missing_parameters)
- **Source:** rag
- **Value:** 0.018 /yr
- **Description:** Background mortality rate
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_gemini_20260407_211201, p2_hiv3_llm_gemini_20260407_205438

### ve2a (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### ve2c (missing_parameters)
- **Source:** rag
- **Value:** 10 cells/ml day^-1 person^-1
- **Description:** Contribution of each infected person to the population of V. cholerae in the aquatic environment
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### ve1a (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### ve1c (missing_parameters)
- **Source:** rag
- **Value:** 10 cells/ml day^-1 person^-1
- **Description:** Contribution of each infected person to the population of V. cholerae in the aquatic environment
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### Susceptible adults->Vaccinated adults (two doses) (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible adults->Vaccinated adults (one dose) (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible children under 5->Vaccinated children under 5 (two doses) (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible children under 5->Vaccinated children under 5 (one dose) (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated adults (two doses)->Exposed / recently infected (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated adults (two doses)->Susceptible adults (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated children under 5 (two doses)->Exposed / recently infected (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated children under 5 (two doses)->Susceptible children under 5 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated adults (one dose)->Exposed / recently infected (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated adults (one dose)->Susceptible adults (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated children under 5 (one dose)->Exposed / recently infected (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated children under 5 (one dose)->Susceptible children under 5 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Exposed / recently infected->Symptomatic infectious (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Exposed / recently infected->Asymptomatic infectious (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Symptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Asymptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

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

### environmental vibrio cholerae reservoir (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### Susceptible adults->Vaccinated adults (two doses) (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible adults->Vaccinated adults (one dose) (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible children under 5->Vaccinated children under 5 (two doses) (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Susceptible children under 5->Vaccinated children under 5 (one dose) (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated adults (two doses)->Exposed / recently infected (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated adults (two doses)->Susceptible adults (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated children under 5 (two doses)->Exposed / recently infected (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated children under 5 (two doses)->Susceptible children under 5 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated adults (one dose)->Exposed / recently infected (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated adults (one dose)->Susceptible adults (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated children under 5 (one dose)->Exposed / recently infected (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Vaccinated children under 5 (one dose)->Susceptible children under 5 (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Exposed / recently infected->Symptomatic infectious (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Exposed / recently infected->Asymptomatic infectious (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Symptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### Asymptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

## 6. Fill validation (vs gold standard)
- Parameters compared: **24**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **4**
- Poor (>50% error): **20**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.98%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| βa | 0.5 | 0.35 | 42.86% | approximate |
| βc | 0.5 | 0.35 | 42.86% | approximate |
| βa_2dose | 0.5 | 1.0 | 50.0% | poor |
| βc_2dose | 0.5 | 1.0 | 50.0% | poor |
| βa_1dose | 0.5 | 1.0 | 50.0% | poor |
| βc_1dose | 0.5 | 1.0 | 50.0% | poor |
| σ*k | 0.5 | 0.14 | 257.14% | poor |
| (1-σ)*k | 0.5 | 0.21 | 138.1% | poor |
| γ | 1.0 | 0.5 | 100.0% | poor |
| ν2 | 0.0001 | 0.015 | 99.33% | poor |
| ν1 | 0.0001 | 0.002 | 95.0% | poor |
| ω2 | 0.5 | 0.00385 | 12887.01% | poor |
| ω1 | 0.5 | 0.01923 | 2500.1% | poor |
| ωr | 0.5 | 0.01 | 4900.0% | poor |
| ξs | 10.0 | 0.8 | 1150.0% | poor |
| ξa | 0.5 | 0.2 | 150.0% | poor |
| λa | 0.5 | 2500.0 | 99.98% | poor |
| λc | 0.5 | 2500.0 | 99.98% | poor |
| δ | 0.029 | 0.002 | 1350.0% | poor |
| μb | 0.018 | 0.6 | 97.0% | poor |
| ve2a | 0.5 | 0.76 | 34.21% | approximate |
| ve2c | 10.0 | 0.469 | 2032.2% | poor |
| ve1a | 0.5 | 0.76 | 34.21% | approximate |
| ve1c | 10.0 | 0.469 | 2032.2% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **11** | Candidate: **3**
- Precision **1.0** | Recall **0.5455** | F1 **0.7059**
### Flows
- Gold count: **21** | Candidate: **3**
- Precision **1.0** | Recall **0.2381** | F1 **0.3846**
