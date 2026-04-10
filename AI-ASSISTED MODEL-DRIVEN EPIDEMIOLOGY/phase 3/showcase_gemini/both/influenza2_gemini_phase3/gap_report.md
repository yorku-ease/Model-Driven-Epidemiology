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
- After fills gaps (re-detected): **1**
- Delta (before - after): **15**
- Delta missing parameters: **10**
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
| **Gap reduction** | 93.8% | 30% |
| **Reference agreement** | 97.1% | 30% |
| **Fill traceability** | 82.4% | 20% |
| **Parameter accuracy** | 10.0% | 20% |
| **→ Composite** | **75.7/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 10 | 4 | 16 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 19 | 0 | 19 |

## 5. Gap filling results
- Filled via **RAG**: 11
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 6
- **Flagged** for manual review: 0

### treatedclinical (missing_compartments)
- **Source:** inference
- **Primary name:** Treated

### protected (missing_compartments)
- **Source:** rag
- **Primary name:** protected
- **Evidence chunks:** 3 snippet(s) in database
- *Use paper snippets to confirm compartment label and add to model.*

### clinicaltransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### subclinicaltransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### clinicalprogressionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### subclinicalprogressionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### treatmentrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### clinicalrecoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### subclinicalrecoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### protectionacquisitionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### treatedrecoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### protectionlossrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### Susceptible->Protected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Transition of

### InfectiousClinical->TreatedClinical (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who are clinically infectious with influenza receive medical treatment, such as antivirals or supportive care.
- **Reasoning:** The paper excerpt explicitly mentions 'antiviral' measures as a strategy for mitigating influenza severity, indicating a transition to a treated state for clinically ill individuals.

### TreatedClinical->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have

### Protected->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals losing immunity

### TreatedClinical->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals receiving treatment

## 6. Fill validation (vs gold standard)
- Parameters compared: **10**
- Exact match (<1% error): **1**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **9**
- **Accuracy (exact+close)**: **10.0%**
- Median relative error: **99.97%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| clinicaltransmissionrate | 0.0001 | 0.45 | 99.98% | poor |
| subclinicaltransmissionrate | 0.0001 | 0.22 | 99.95% | poor |
| clinicalprogressionrate | 0.0001 | 0.38 | 99.97% | poor |
| subclinicalprogressionrate | 0.0001 | 0.38 | 99.97% | poor |
| treatmentrate | 0.0001 | 1.0 | 99.99% | poor |
| clinicalrecoveryrate | 0.0001 | 0.33 | 99.97% | poor |
| subclinicalrecoveryrate | 0.0001 | 0.33 | 99.97% | poor |
| protectionacquisitionrate | 0.0001 | 0.02 | 99.5% | poor |
| treatedrecoveryrate | 0.5 | 0.5 | 0.0% | exact |
| protectionlossrate | 0.0001 | 0.05 | 99.8% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **7** | Candidate: **6**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **9** | Candidate: **6**
- Precision **1.0** | Recall **0.8889** | F1 **0.9412**
