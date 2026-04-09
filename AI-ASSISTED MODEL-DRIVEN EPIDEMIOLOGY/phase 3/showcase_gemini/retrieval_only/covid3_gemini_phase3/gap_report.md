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
- After fills gaps (re-detected): **8**
- Delta (before - after): **11**
- Delta missing parameters: **9**
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
| **Gap reduction** | 57.9% | 30% |
| **Reference agreement** | 37.5% | 30% |
| **Fill traceability** | 100.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **68.6/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 14 | 3 | 19 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 6 | 1 | 7 |

## 5. Gap filling results
- Filled via **RAG**: 26
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 1

### initialrt (missing_parameters)
- **Source:** rag
- **Value:** 3.8 dimensionless
- **Description:** Average initial reproduction number estimated across the 11 countries before interventions
- **From papers:** p2_covid3_llm_gemini_20260407_204521, p1_model_covid, p1_model_cholera

### basetransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** InitialRt / GenerationIntervalDays 1/day
- **Description:** Compartmental approximation of transmission intensity derived from initial Rt
- **From papers:** p1_model_dengue, p1_model_covid, p1_model_cholera

### lockdowneffect (missing_parameters)
- **Source:** rag
- **Value:** 0.19 dimensionless
- **Description:** Approximate remaining transmission multiplier under lockdown, corresponding to about 81 percent reduction in Rt reported in the paper
- **From papers:** p2_covid3_llm_gemini_20260407_204521, p1_model_covid, p2_covid3_llm_claude_20260407_213850

### combinedinterventionmultiplier (missing_parameters)
- **Source:** rag
- **Value:** PublicEventsBanEffect * SchoolClosureEffect * SelfIsolationEffect * SocialDistancingEffect * LockdownEffect dimensionless
- **Description:** Combined multiplicative effect of interventions on transmission
- **From papers:** p2_zika3_llm_openai_20260407_213203, p2_zika3_llm_gemini_20260407_211201, p2_zika3_llm_claude_20260407_215851

### effectivetransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** BaseTransmissionRate * CombinedInterventionMultiplier 1/day
- **Description:** Effective transmission rate after interventions in the compartmental approximation
- **From papers:** p1_model_covid, p1_model_cholera

### Susceptible->Infected (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Infected->Removed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Infected->Dead (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 6. Fill validation (vs gold standard)
- Parameters compared: **2**
- Exact match (<1% error): **2**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**
- **Accuracy (exact+close)**: **100.0%**
- Median relative error: **0.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| initialrt | 3.8 | 3.8 | 0.0% | exact |
| basetransmissionrate | InitialRt / GenerationIntervalDays | InitialRt / GenerationIntervalDays | — | no_comparison |
| lockdowneffect | 0.19 | 0.19 | 0.0% | exact |
| combinedinterventionmultiplier | PublicEventsBanEffect * SchoolClosureEffect * SelfIsolationEffect * SocialDistancingEffect * LockdownEffect | PublicEventsBanEffect * SchoolClosureEffect * SelfIsolationEffect * SocialDistancingEffect * LockdownEffect | — | no_comparison |
| effectivetransmissionrate | BaseTransmissionRate * CombinedInterventionMultiplier | BaseTransmissionRate * CombinedInterventionMultiplier | — | no_comparison |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **4** | Candidate: **4**
- Precision **0.75** | Recall **0.75** | F1 **0.75**
### Flows
- Gold count: **3** | Candidate: **1**
- Precision **0.0** | Recall **0.0** | F1 **0.0**
