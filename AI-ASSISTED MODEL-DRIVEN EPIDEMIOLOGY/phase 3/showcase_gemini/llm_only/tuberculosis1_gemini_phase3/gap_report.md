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
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **42.5/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 1 | 3 | 4 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 1 | 0 | 1 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 7
- **Flagged** for manual review: 0

### π (missing_parameters)
- **Source:** inference
- **Value:** 0.02 per capita per year
- **Confidence:** LOW

### Susceptible->Infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected with Mycobacterium tuberculosis through contact with infectious individuals.
- **Reasoning:** The excerpt discusses 'dynamics of tuberculosis transmission' and 'how disease may spread in a population,' indicating person-to-person transmission via contact.

### Infected->Recovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which individuals actively infected with tuberculosis recover from the disease, either spontaneously or due to successful treatment, and are no longer infectious.
- **Reasoning:** Recovery is a fundamental process in the natural history of tuberculosis, whether spontaneous or through treatment, and is essential for reducing disease prevalence.

### Recovered->Infected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Loss of immunity or

### Susceptible->Infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals acquire Mycobacterium tuberculosis infection through contact with infectious individuals.
- **Reasoning:** The excerpt discusses 'tuberculosis transmission' and 'how disease may spread in a population,' which implies person-to-person contact as the mechanism for new infections.

### Infected->Recovered (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The process by which individuals with active tuberculosis infection successfully clear the infection and transition to a recovered state, often due to effective treatment.
- **Reasoning:** Recovery from active tuberculosis infection is a common and critical outcome, typically achieved through medical treatment, and must be included to accurately model disease dynamics and the impact of interventions.

### Recovered->Infected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have recovered

## 6. Fill validation (vs gold standard)
- Parameters compared: **1**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **1**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **238.98%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| π | 0.02 | 0.0059 | 238.98% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **4** | Candidate: **4**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **6** | Candidate: **3**
- Precision **1.0** | Recall **0.5** | F1 **0.6667**
