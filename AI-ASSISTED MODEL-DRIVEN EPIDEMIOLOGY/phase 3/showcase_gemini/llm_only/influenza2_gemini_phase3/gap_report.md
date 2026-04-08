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
- After fills gaps (re-detected): **6**
- Delta (before - after): **10**
- Delta missing parameters: **5**
- Delta missing compartments: **2**
- Delta missing flows: **3**

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
| **Gap reduction** | 62.5% | 30% |
| **Reference agreement** | 54.5% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **55.1/100** | — |

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
- Filled via **inference**: 22
- **Flagged** for manual review: 0

### clinicalprogressionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### subclinicalprogressionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### treatmentrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### protectionacquisitionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### protectionlossrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### TreatedClinical->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for TreatedClinical->Removed
- **Reasoning:** LLM unavailable.

## 6. Fill validation (vs gold standard)
- Parameters compared: **0**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| clinicalprogressionrate | None | 0.38 | — | no_fill |
| subclinicalprogressionrate | None | 0.38 | — | no_fill |
| treatmentrate | None | 1.0 | — | no_fill |
| protectionacquisitionrate | None | 0.02 | — | no_fill |
| protectionlossrate | None | 0.05 | — | no_fill |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **6**
- Precision **0.6667** | Recall **0.7143** | F1 **0.6897**
### Flows
- Gold count: **9** | Candidate: **6**
- Precision **0.5** | Recall **0.3333** | F1 **0.4**
