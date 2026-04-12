# Phase 3 Gap Analysis Report

**Disease / context:** tuberculosis1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **4**
- Missing compartments: 0
- Missing parameters: 1
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 3
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 1
- Extra flows (not in gold standard): 0

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **4**
- After fills gaps (re-detected): **3**
- Delta (before - after): **1**
- Delta missing parameters: **1**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->Infected** — Present in gold standard but not in extracted model (or flows list).
- **Infected->Recovered** — Present in gold standard but not in extracted model (or flows list).
- **Recovered->Infected** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **π** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: ϵ

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 25.0% | 30% |
| **Reference agreement** | 83.3% | 30% |
| **Fill traceability** | 57.1% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **43.9/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 1 | 3 | 4 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 1 | 0 | 1 |

## 5. Gap filling results
- Filled via **RAG**: 1
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 6
- **Flagged** for manual review: 0

### π (missing_parameters)
- **Source:** rag
- **Value:** 200 USD (2014)
- **Description:** Cost from a public payer perspective for a hospitalised dengue case in a middle-income Latin American-like setting.
- **From papers:** p2_dengue3_llm_openai_20260407_212001, p2_cholera3_llm_gemini_20260407_204240, p2_dengue3_llm_gemini_20260407_204755

### Susceptible->Infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals acquire Mycobacterium tuberculosis infection through contact with infectious individuals.
- **Reasoning:** The excerpt emphasizes 'tuberculosis transmission' and 'how disease may spread in a population,' indicating that new infections arise from interactions between susceptible and infected individuals.

### Infected->Recovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which individuals actively infected with tuberculosis recover from the disease, either spontaneously or through successful treatment, becoming non-infectious.
- **Reasoning:** Recovery from active infection is a fundamental process in tuberculosis dynamics, influenced by treatment and the natural course of the disease, which is essential for reducing disease prevalence.

### Recovered->Infected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovered individuals

### Susceptible->Infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected with Mycobacterium tuberculosis after coming into contact with infectious individuals.
- **Reasoning:** The excerpt discusses 'dynamics of tuberculosis transmission' and 'how disease may spread in a population,' indicating that new infections arise from contact between susceptible and infected individuals.

### Infected->Recovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** This flow represents individuals who were actively infected with tuberculosis and have successfully recovered, either naturally or, more commonly, through effective treatment, thereby ceasing to be infectious.
- **Reasoning:** Recovery from active tuberculosis, often facilitated by treatment, is a fundamental process that removes individuals from the infectious compartment and is essential for modeling disease resolution and population-level dynamics.

### Recovered->Infected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** A proportion of

## 6. Fill validation (vs gold standard)
- Parameters compared: **1**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **1**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **3389730.51%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| π | 200.0 | 0.0059 | 3389730.51% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **4** | Candidate: **4**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **6** | Candidate: **3**
- Precision **1.0** | Recall **0.5** | F1 **0.6667**
