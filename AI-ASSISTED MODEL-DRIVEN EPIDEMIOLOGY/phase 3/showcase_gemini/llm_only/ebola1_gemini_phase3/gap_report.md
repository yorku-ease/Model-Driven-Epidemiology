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
- After fills gaps (re-detected): **9**
- Delta (before - after): **3**
- Delta missing parameters: **0**
- Delta missing compartments: **1**
- Delta missing flows: **2**

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
| **Gap reduction** | 25.0% | 30% |
| **Reference agreement** | 74.2% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **49.8/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 6 | 5 | 12 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 9 | 1 | 11 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 21
- **Flagged** for manual review: 0

### μ (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ω2 (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ω3 (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ω1 (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ω1q (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### η (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### Infectious->Deceased (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for Infectious->Deceased
- **Reasoning:** LLM unavailable.

### Quarantine->Deceased (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for Quarantine->Deceased
- **Reasoning:** LLM unavailable.

### Deceased->Environment pathogens (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for Deceased->Environment pathogens
- **Reasoning:** LLM unavailable.

## 6. Fill validation (vs gold standard)
- Parameters compared: **0**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| μ | None | 0.00038461 | — | no_fill |
| ω2 | None | 0.0 | — | no_fill |
| ω3 | None | 0.0 | — | no_fill |
| ω1 | None | 0.0 | — | no_fill |
| ω1q | None | 0.0 | — | no_fill |
| η | None | 0.0 | — | no_fill |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **7**
- Precision **0.7143** | Recall **0.8333** | F1 **0.7692**
### Flows
- Gold count: **8** | Candidate: **6**
- Precision **0.8333** | Recall **0.625** | F1 **0.7143**
