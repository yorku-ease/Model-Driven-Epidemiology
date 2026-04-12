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
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **69.6/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 2 | 4 | 6 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 6 | 1 | 7 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 6
- **Flagged** for manual review: 0

### ξu (missing_parameters)
- **Source:** inference
- **Value:** 0.85 
- **Confidence:** LOW

### φ (missing_parameters)
- **Source:** inference
- **Value:** 0.0005 per day
- **Confidence:** LOW

### Exposed children->Exposed adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed children age into

### Infectious children->Infectious adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Infectious children aging into infectious adults.
- **Reasoning:** This flow represents the natural aging process of an individual from the child age group to the adult age group while remaining in the infectious state.

### Immune children->Immune adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Immune children age into immune adults, retaining their immunity as they transition from the child age group to the adult age group.
- **Reasoning:** The paper categorizes the population into two age groups (0-15 yrs and above 15 yrs), necessitating a flow for individuals to age from the 'children' compartment to the 'adults' compartment while maintaining their immune status.

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
- Median relative error: **9670.11%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| ξu | 0.85 | 0.0087 | 9670.11% | poor |
| φ | 0.0005 | 0.00128 | 60.94% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **12** | Candidate: **12**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **20** | Candidate: **21**
- Precision **0.9524** | Recall **1.0** | F1 **0.9756**
