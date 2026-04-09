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
| **Reference agreement** | 54.5% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **60.7/100** | — |

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

### InfectiousClinical->TreatedClinical (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who are

### TreatedClinical->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals receiving treatment recover

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **6**
- Precision **0.6667** | Recall **0.7143** | F1 **0.6897**
### Flows
- Gold count: **9** | Candidate: **6**
- Precision **0.5** | Recall **0.3333** | F1 **0.4**
