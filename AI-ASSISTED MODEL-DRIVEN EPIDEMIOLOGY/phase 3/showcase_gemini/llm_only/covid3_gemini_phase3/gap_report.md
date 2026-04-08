# Phase 3 Gap Analysis Report

**Disease / context:** covid3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **19**
- Missing compartments: 2
- Missing parameters: 14
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 3
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 6
- Extra flows (not in gold standard): 1

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **19**
- After fills gaps (re-detected): **14**
- Delta (before - after): **5**
- Delta missing parameters: **3**
- Delta missing compartments: **2**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **susceptible** — Present in gold standard but not in extracted model. (severity: high)
- **removed** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->Infected** — Present in gold standard but not in extracted model (or flows list).
- **Infected->Removed** — Present in gold standard but not in extracted model (or flows list).
- **Infected->Dead** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **population11countries** — Present in gold standard but not in extracted model.
- **initialrt** — Present in gold standard but not in extracted model.
- **generationintervaldays** — Present in gold standard but not in extracted model.
- **basetransmissionrate** — Present in gold standard but not in extracted model.
- **publiceventsbaneffect** — Present in gold standard but not in extracted model.
- **schoolclosureeffect** — Present in gold standard but not in extracted model.
- **selfisolationeffect** — Present in gold standard but not in extracted model.
- **socialdistancingeffect** — Present in gold standard but not in extracted model.
- **lockdowneffect** — Present in gold standard but not in extracted model.
- **combinedinterventionmultiplier** — Present in gold standard but not in extracted model.
- **effectivetransmissionrate** — Present in gold standard but not in extracted model.
- **meanoutcomedelaydays** — Present in gold standard but not in extracted model.
- **removalrate** — Present in gold standard but not in extracted model.
- **deathrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: r_t, initial r_t, serial interval distribution, infection fatality ratio, infection-to-death distribution, lockdown effect

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 26.3% | 30% |
| **Reference agreement** | 37.5% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **39.1/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 14 | 3 | 19 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 6 | 1 | 7 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 33
- **Flagged** for manual review: 0

### population11countries (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### initialrt (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### generationintervaldays (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### publiceventsbaneffect (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### schoolclosureeffect (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### selfisolationeffect (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### socialdistancingeffect (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### lockdowneffect (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### meanoutcomedelaydays (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### removalrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### deathrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### Susceptible->Infected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for Susceptible->Infected
- **Reasoning:** LLM unavailable.

### Infected->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for Infected->Removed
- **Reasoning:** LLM unavailable.

### Infected->Dead (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for Infected->Dead
- **Reasoning:** LLM unavailable.

## 6. Fill validation (vs gold standard)
- Parameters compared: **0**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| population11countries | None | 741000000 | — | no_fill |
| initialrt | None | 3.8 | — | no_fill |
| generationintervaldays | None | 6.5 | — | no_fill |
| publiceventsbaneffect | None | 1.0 | — | no_fill |
| schoolclosureeffect | None | 1.0 | — | no_fill |
| selfisolationeffect | None | 1.0 | — | no_fill |
| socialdistancingeffect | None | 1.0 | — | no_fill |
| lockdowneffect | None | 0.19 | — | no_fill |
| meanoutcomedelaydays | None | 18 | — | no_fill |
| removalrate | None | 1 / MeanOutcomeDelayDays | — | no_fill |
| deathrate | None | 0.005 | — | no_fill |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **4** | Candidate: **4**
- Precision **0.75** | Recall **0.75** | F1 **0.75**
### Flows
- Gold count: **3** | Candidate: **1**
- Precision **0.0** | Recall **0.0** | F1 **0.0**
