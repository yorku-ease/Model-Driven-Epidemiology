# Phase 3 Gap Analysis Report

**Disease / context:** influenza2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **16**
- Missing compartments: 2
- Missing parameters: 10
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 4
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 19
- Extra flows (not in gold standard): 0

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **16**
- After fills gaps (re-detected): **3**
- Delta (before - after): **13**
- Delta missing parameters: **10**
- Delta missing compartments: **1**
- Delta missing flows: **2**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **treatedclinical** — Present in gold standard but not in extracted model. (severity: high)
- **protected** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->Protected** — Present in gold standard but not in extracted model (or flows list).
- **InfectiousClinical->TreatedClinical** — Present in gold standard but not in extracted model (or flows list).
- **TreatedClinical->Removed** — Present in gold standard but not in extracted model (or flows list).
- **Protected->Susceptible** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **clinicaltransmissionrate** — Present in gold standard but not in extracted model.
- **subclinicaltransmissionrate** — Present in gold standard but not in extracted model.
- **clinicalprogressionrate** — Present in gold standard but not in extracted model.
- **subclinicalprogressionrate** — Present in gold standard but not in extracted model.
- **treatmentrate** — Present in gold standard but not in extracted model.
- **clinicalrecoveryrate** — Present in gold standard but not in extracted model.
- **subclinicalrecoveryrate** — Present in gold standard but not in extracted model.
- **protectionacquisitionrate** — Present in gold standard but not in extracted model.
- **treatedrecoveryrate** — Present in gold standard but not in extracted model.
- **protectionlossrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: r0, generation time, clinical fraction, household transmission fraction, community transmission fraction, school and workplace transmission fraction, school contact rate multiplier, vaccine efficacy (susceptibility reduction), vaccine efficacy (infectiousness reduction), vaccine efficacy (clinical case reduction), pre-pandemic vaccine efficacy (susceptibility reduction), vaccination rate, vaccine protection delay, us population size, gb population size, household quarantine compliance, household quarantine external contact reduction, household quarantine internal contact increase, quarantine duration

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 81.2% | 30% |
| **Reference agreement** | 97.1% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **63.5/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 10 | 4 | 16 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 19 | 0 | 19 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 19
- **Flagged** for manual review: 0

### treatedclinical (missing_compartments)
- **Source:** inference
- **Primary name:** Treated Clinical
- **Reasoning:** The

### protected (missing_compartments)
- **Source:** inference
- **Primary name:** Protected

### clinicaltransmissionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.15 
- **Reasoning:** This
- **Confidence:** LOW

### subclinicaltransmissionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.75 
- **Confidence:** LOW

### clinicalprogressionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.5 day^-1
- **Reasoning:** The clinical progression rate is the inverse of the incubation period. For influenza, the average incubation period is typically around 2 days.
- **Confidence:** LOW

### subclinicalprogressionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.2 1/day
- **Confidence:** LOW

### treatmentrate (missing_parameters)
- **Source:** inference
- **Value:** 0.1 per day
- **Confidence:** LOW

### clinicalrecoveryrate (missing_parameters)
- **Source:** inference
- **Value:** 0.2 per day
- **Reasoning:** The typical duration of clinical symptoms for influenza is approximately 5-7 days. The clinical recovery rate is the inverse of this duration.
- **Confidence:** LOW

### subclinicalrecoveryrate (missing_parameters)
- **Source:** inference
- **Value:** 0.167 per day
- **Confidence:** LOW

### protectionacquisitionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.6 
- **Reasoning:** This
- **Confidence:** LOW

### treatedrecoveryrate (missing_parameters)
- **Source:** inference
- **Value:** 0.167 per day
- **Confidence:** LOW

### protectionlossrate (missing_parameters)
- **Source:** inference
- **Value:** 0.003 per day
- **Confidence:** LOW

### Susceptible->Protected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The transition of susceptible individuals to a protected state, likely through vaccination or other prophylactic interventions.
- **Reasoning:** The paper mentions 'vaccine' as a prevention strategy, which typically confers protection at a given rate, independent of contact with

### InfectiousClinical->TreatedClinical (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which

### TreatedClinical->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who were clinically treated

### Protected->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Loss of

### treatedclinical (missing_compartments)
- **Source:** inference
- **Primary name:** Treated Clinical

### InfectiousClinical->TreatedClinical (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at

### TreatedClinical->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals receiving treatment for clinical

## 6. Fill validation (vs gold standard)
- Parameters compared: **10**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **4**
- Poor (>50% error): **6**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **66.67%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| clinicaltransmissionrate | 0.15 | 0.45 | 66.67% | poor |
| subclinicaltransmissionrate | 0.75 | 0.22 | 240.91% | poor |
| clinicalprogressionrate | 0.5 | 0.38 | 31.58% | approximate |
| subclinicalprogressionrate | 0.2 | 0.38 | 47.37% | approximate |
| treatmentrate | 0.1 | 1.0 | 90.0% | poor |
| clinicalrecoveryrate | 0.2 | 0.33 | 39.39% | approximate |
| subclinicalrecoveryrate | 0.167 | 0.33 | 49.39% | approximate |
| protectionacquisitionrate | 0.6 | 0.02 | 2900.0% | poor |
| treatedrecoveryrate | 0.167 | 0.5 | 66.6% | poor |
| protectionlossrate | 0.003 | 0.05 | 94.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **6**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **9** | Candidate: **6**
- Precision **1.0** | Recall **0.8889** | F1 **0.9412**
