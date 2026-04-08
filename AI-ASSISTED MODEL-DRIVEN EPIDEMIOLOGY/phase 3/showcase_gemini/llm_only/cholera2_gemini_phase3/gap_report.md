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
- After fills gaps (re-detected): **24**
- Delta (before - after): **24**
- Delta missing parameters: **0**
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
| **Gap reduction** | 50.0% | 30% |
| **Reference agreement** | 98.6% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **64.6/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 6 | 24 | 18 | 48 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 9 | 1 | 10 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 72
- **Flagged** for manual review: 0

### βa (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### βc (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### βa_2dose (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### βc_2dose (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### βa_1dose (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### βc_1dose (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### σ*k (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### (1-σ)*k (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### γ (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ν2 (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ν1 (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ω2 (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ω1 (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ωr (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ξs (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ξa (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### λa (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### λc (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### δ (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### μb (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ve2a (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ve2c (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ve1a (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ve1c (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

## 6. Fill validation (vs gold standard)
- Parameters compared: **0**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| βa | None | 0.35 | — | no_fill |
| βc | None | 0.35 | — | no_fill |
| βa_2dose | None | βA * (1 - VE2A) | — | no_fill |
| βc_2dose | None | βC * (1 - VE2C) | — | no_fill |
| βa_1dose | None | βA * (1 - VE1A) | — | no_fill |
| βc_1dose | None | βC * (1 - VE1C) | — | no_fill |
| σ*k | None | 0.14 | — | no_fill |
| (1-σ)*k | None | 0.21 | — | no_fill |
| γ | None | 0.5 | — | no_fill |
| ν2 | None | 0.015 | — | no_fill |
| ν1 | None | 0.002 | — | no_fill |
| ω2 | None | 0.00385 | — | no_fill |
| ω1 | None | 0.01923 | — | no_fill |
| ωr | None | 0.01 | — | no_fill |
| ξs | None | 0.8 | — | no_fill |
| ξa | None | 0.2 | — | no_fill |
| λa | None | 2500 | — | no_fill |
| λc | None | 2500 | — | no_fill |
| δ | None | 0.002 | — | no_fill |
| μb | None | 0.6 | — | no_fill |
| ve2a | None | 0.76 | — | no_fill |
| ve2c | None | 0.469 * VE2A | — | no_fill |
| ve1a | None | 0.76 | — | no_fill |
| ve1c | None | 0.469 * VE1A | — | no_fill |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **11** | Candidate: **9**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **21** | Candidate: **18**
- Precision **0.9444** | Recall **1.0** | F1 **0.9714**
