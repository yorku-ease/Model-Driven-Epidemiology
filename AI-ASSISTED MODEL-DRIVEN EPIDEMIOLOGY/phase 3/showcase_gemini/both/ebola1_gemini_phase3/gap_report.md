# Phase 3 Gap Analysis Report

**Disease / context:** ebola1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **12**
- Missing compartments: 1
- Missing parameters: 6
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 5
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 9
- Extra flows (not in gold standard): 1

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **12**
- After fills gaps (re-detected): **5**
- Delta (before - after): **7**
- Delta missing parameters: **6**
- Delta missing compartments: **0**
- Delta missing flows: **1**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **environment pathogens** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Infectious->Recovered** — Present in gold standard but not in extracted model (or flows list).
- **Infectious->Deceased** — Present in gold standard but not in extracted model (or flows list).
- **Infectious->Environment pathogens** — Present in gold standard but not in extracted model (or flows list).
- **Quarantine->Deceased** — Present in gold standard but not in extracted model (or flows list).
- **Deceased->Environment pathogens** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **μ** — Present in gold standard but not in extracted model.
- **ω2** — Present in gold standard but not in extracted model.
- **ω3** — Present in gold standard but not in extracted model.
- **ω1** — Present in gold standard but not in extracted model.
- **ω1q** — Present in gold standard but not in extracted model.
- **η** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: ebola virus pathogens in environment
- Extra parameters: α1, α2, ψ1, ψ2, ψ3, ε, w1, w2, c

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 58.3% | 30% |
| **Reference agreement** | 69.2% | 30% |
| **Fill traceability** | 67.6% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **51.8/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 6 | 5 | 12 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 9 | 1 | 11 |

## 5. Gap filling results
- Filled via **RAG**: 6
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 11
- **Flagged** for manual review: 0

### environment pathogens (missing_compartments)
- **Source:** inference
- **Primary name:** Environment
- **Reasoning:** The

### μ (missing_parameters)
- **Source:** rag
- **Value:** 10.5 
- **From papers:** p2_dengue3_llm_openai_20260407_212001, p2_zika3_llm_claude_20260407_215851, p2_hiv3_llm_claude_20260407_214604

### ω2 (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### ω3 (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### ω1 (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### ω1q (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_openai_20260407_211654, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### η (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_covid3_llm_openai_20260407_211832, p2_covid3_llm_gemini_20260407_204521, p2_dengue3_llm_claude_20260407_214050

### Infectious->Recovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Rate at which infected individuals recover from Ebola Virus Disease and gain permanent immunity.
- **Reasoning:** The paper explicitly states that permanent disease-induced immunity exists, indicating a recovery process from the infectious state.

### Infectious->Deceased (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which infected individuals die due to the Ebola Virus Disease.
- **Reasoning:** The text explicitly states 'Rate of deaths of human individuals due to infection', indicating a direct transition from the infectious state to deceased due to the disease itself.

### Infectious->Environment pathogens (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Shedding of Ebola virus into the environment by infected humans through urine and faeces.
- **Reasoning:** The excerpt explicitly states

### Quarantine->Deceased (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which

### Deceased->Environment pathogens (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Deceased individuals shed Ebola virus pathogens into the environment through bodily fluids or waste.
- **Reasoning:** The paper states that 'the disease can enter the environment through the urine and faeces of ... deceased individuals,' indicating a shedding rate from deceased to the environment.

### environment pathogens (missing_compartments)
- **Source:** inference
- **Primary name:** Environmental Pathogens

### Infectious->Deceased (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Rate of deaths of human individuals due to infection
- **Reasoning:** The paper excerpt explicitly states 'Rate of deaths of human individuals due to infection', which directly describes the transition from the Infectious compartment to Deceased.

### Infectious->Environment pathogens (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Shedding of Ebola

### Quarantine->Deceased (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which individuals in quarantine succumb to the Ebola Virus Disease.
- **Reasoning:** Individuals in quarantine, if infected, can still die from the disease, representing a direct transition from the quarantined state to deceased at a certain rate.

### Deceased->Environment pathogens (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Shedding of Ebola virus pathogens from deceased individuals into the environment.
- **Reasoning:** The excerpt explicitly states that 'The disease

## 6. Fill validation (vs gold standard)
- Parameters compared: **6**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **6**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| μ | 10.5 | 0.00038461 | 2729938.22% | poor |
| ω2 | 0.5 | 0.0 | 100.0% | poor |
| ω3 | 0.5 | 0.0 | 100.0% | poor |
| ω1 | 0.5 | 0.0 | 100.0% | poor |
| ω1q | 0.5 | 0.0 | 100.0% | poor |
| η | 0.5 | 0.0 | 100.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **7**
- Precision **0.7143** | Recall **0.8333** | F1 **0.7692**
### Flows
- Gold count: **8** | Candidate: **5**
- Precision **0.8** | Recall **0.5** | F1 **0.6154**
