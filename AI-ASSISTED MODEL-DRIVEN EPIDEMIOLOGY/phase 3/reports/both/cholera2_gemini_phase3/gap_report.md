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
- After fills gaps (re-detected): **6**
- Delta (before - after): **39**
- Delta missing parameters: **24**
- Delta missing compartments: **5**
- Delta missing flows: **10**

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
| **Gap reduction** | 86.7% | 30% |
| **Reference agreement** | 87.1% | 30% |
| **Fill traceability** | 72.2% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **66.6/100** | — |

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
- Filled via **inference**: 30
- **Flagged** for manual review: 0

### vaccinated adults (two doses) (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated
- **Reasoning:** The excerpt

### vaccinated children under 5 (two doses) (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Children Under 5

### vaccinated adults (one dose) (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Individuals
- **Reasoning:** The

### vaccinated children under 5 (one dose) (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Children Under 5 (One Dose)

### environmental vibrio cholerae reservoir (missing_compartments)
- **Source:** inference
- **Primary name:** Environmental Vibrio Reservoir

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
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which susceptible adults receive two doses of the cholera vaccine and transition to the vaccinated compartment.
- **Reasoning:** The paper discusses the 'use of vaccine' as a strategy, indicating a direct transition from susceptible to vaccinated states, which is best represented by a rate flow.

### Susceptible adults->Vaccinated adults (one dose) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Vaccination of susceptible adults with one dose of the cholera vaccine.
- **Reasoning:** The paper excerpt discusses the 'use of vaccine' and 'cholera vaccination' as a strategy, indicating that vaccination is a modeled intervention for susceptible individuals.

### Susceptible children under 5->Vaccinated children under 5 (two doses) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Vaccination of susceptible children under 5 with two doses of cholera vaccine.
- **Reasoning:** Vaccination is an intervention applied to individuals at a specific rate, independent of contact with infected individuals.

### Susceptible children under 5->Vaccinated children under 5 (one dose) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Vaccination of susceptible children under 5 with one dose of cholera vaccine.
- **Reasoning:** This flow represents the direct intervention of vaccinating susceptible children, moving them into a vaccinated compartment, which is a key strategy mentioned in the paper excerpt for cholera elimination.

### Vaccinated adults (two doses)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated adults (two

### Vaccinated adults (two doses)->Susceptible adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of vaccine-

### Vaccinated children under 5 (two doses)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Vaccinated children under 5 (two doses) becoming exposed/infected with cholera due to vaccine failure or incomplete protection.
- **Reasoning:** Vaccination reduces, but does not eliminate, the risk of infection, meaning a proportion of vaccinated individuals can still become exposed or infected at a certain rate.

### Vaccinated children under 5 (two doses)->Susceptible children under 5 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning immunity from two-dose cholera vaccine in children under 5 years, leading to a return to the susceptible state.
- **Reasoning:** Vaccine-induced immunity is not lifelong and can wane over time, making previously vaccinated individuals susceptible again.

### Vaccinated adults (one dose)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals

### Vaccinated adults (one dose)->Susceptible adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of vaccine-induced immunity in adults who received one dose, causing them to revert to a susceptible state.
- **Reasoning:** Vaccine efficacy is not permanent and can wane over time, particularly for a single dose, leading to a loss of protection against cholera.

### Vaccinated children under 5 (one dose)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated

### Vaccinated children under 5 (one dose)->Susceptible children under 5 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of vaccine-induced immunity in children under 5 who received one dose of cholera vaccine, leading to a return to susceptibility.
- **Reasoning:** Vaccine-induced immunity is not lifelong and can wane over time, causing individuals to revert from a vaccinated state to a susceptible state.

### Exposed / recently infected->Symptomatic infectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The progression of an infected individual from the asymptomatic incubation period to developing symptoms and becoming infectious.
- **Reasoning:** This transition represents the natural progression of the disease within an infected individual, moving from an asymptomatic exposed state to a symptomatic and infectious state after the incubation period, which is typically modeled as a rate.

### Exposed / recently infected->Asymptomatic infectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of individuals from the exposed state to an asymptomatic infectious state after the incubation period.
- **Reasoning:** This transition represents the biological progression of an infected individual from being exposed to becoming infectious without developing symptoms, which is a common feature in cholera transmission dynamics.

### Symptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Symptomatic infectious individuals

### Asymptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Shedding of Vib

### environmental vibrio cholerae reservoir (missing_compartments)
- **Source:** inference
- **Primary name:** Environmental Vibrio Cholerae Reservoir

### Vaccinated adults (two doses)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Vaccinated adults, despite

### Vaccinated children under 5 (two doses)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Vaccinated children under

### Vaccinated adults (one dose)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated individuals acquiring cholera infection despite having received one dose of vaccine, due to imperfect vaccine efficacy or waning immunity.
- **Reasoning:** This flow represents the infection of vaccinated individuals upon exposure to the cholera pathogen, which is a contact-dependent process influenced by vaccine efficacy and exposure risk.

### Vaccinated children under 5 (one dose)->Exposed / recently infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated children under 5 years old who have received one dose of vaccine become exposed to cholera due to imperfect vaccine efficacy or waning immunity upon contact with the pathogen.
- **Reasoning:** The transition from a vaccinated state to an exposed state is driven by contact with the pathogen, accounting for the vaccine's imperfect protection against infection.

### Exposed / recently infected->Symptomatic infectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The progression of individuals from a latent, asymptomatic infection state to developing symptoms and becoming infectious.
- **Reasoning:** This transition represents the incubation period and the onset of clinical symptoms and infectiousness in individuals previously exposed to the pathogen.

### Exposed / recently infected->Asymptomatic infectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The progression of an infected individual from the latent (exposed) stage to the asymptomatic infectious stage.
- **Reasoning:** This transition represents the intrinsic biological progression of the disease within an individual, not a transmission event due to contact.

### Symptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Symptomatic infectious individuals

### Asymptomatic infectious->Environmental Vibrio cholerae reservoir (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Asymptomatic infectious individuals shed Vibrio cholerae bacteria into the environment, contaminating water sources and contributing to the environmental reservoir of the pathogen.
- **Reasoning:** Asymptomatic individuals, despite not showing symptoms, can still shed the cholera bacterium into the environment, directly contributing to the environmental reservoir.

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
- Gold count: **11** | Candidate: **6**
- Precision **0.8333** | Recall **1.0** | F1 **0.9091**
### Flows
- Gold count: **21** | Candidate: **6**
- Precision **1.0** | Recall **0.7143** | F1 **0.8333**
