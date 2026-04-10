# Phase 3 Gap Analysis Report

**Disease / context:** measles1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **6**
- Missing compartments: 0
- Missing parameters: 2
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 4
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 6
- Extra flows (not in gold standard): 1

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **6**
- After fills gaps (re-detected): **0**
- Delta (before - after): **6**
- Delta missing parameters: **2**
- Delta missing compartments: **0**
- Delta missing flows: **4**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- **Exposed children->Exposed adults** — Present in gold standard but not in extracted model (or flows list).
- **Infectious children->Infectious adults** — Present in gold standard but not in extracted model (or flows list).
- **Immune children->Immune adults** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible adults->Vaccinated adults (catch-up/monitored)** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **ξu** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: xu, xa, bc, ba, j, hu

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 98.8% | 30% |
| **Fill traceability** | 58.3% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **71.3/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 2 | 4 | 6 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 6 | 1 | 7 |

## 5. Gap filling results
- Filled via **RAG**: 1
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 5
- **Flagged** for manual review: 0

### ξu (missing_parameters)
- **Source:** inference
- **Value:** 0.85 
- **Confidence:** LOW

### φ (missing_parameters)
- **Source:** rag
- **Value:** 0.40 dimensionless
- **Description:** Reduction in transmission from other interventions
- **From papers:** p2_dengue3_llm_claude_20260407_214050, p2_cholera3_llm_gemini_20260407_204240, p2_cholera3_llm_claude_20260407_213639

### Exposed children->Exposed adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed children age

### Infectious children->Infectious adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Infectious children age and transition into the infectious adult compartment.
- **Reasoning:** The model categorizes the population into two age groups, necessitating a flow for individuals to age from the child to the adult compartment while maintaining their infectious status.

### Immune children->Immune adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Immune children age and transition into the immune adult population.
- **Reasoning:** The model categorizes the population into age groups, so children naturally age into adults, maintaining their immune status.

### Susceptible adults->Vaccinated adults (catch-up/monitored) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible adults

## 6. Fill validation (vs gold standard)
- Parameters compared: **2**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **2**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **31150.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| ξu | 0.85 | 0.0087 | 9670.11% | poor |
| φ | 0.4 | 0.00128 | 31150.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **12** | Candidate: **12**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **20** | Candidate: **21**
- Precision **0.9524** | Recall **1.0** | F1 **0.9756**
