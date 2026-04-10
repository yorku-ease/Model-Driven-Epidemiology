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
- After fills gaps (re-detected): **5**
- Delta (before - after): **14**
- Delta missing parameters: **12**
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
| **Gap reduction** | 73.7% | 30% |
| **Reference agreement** | 50.0% | 30% |
| **Fill traceability** | 85.4% | 20% |
| **Parameter accuracy** | 15.4% | 20% |
| **→ Composite** | **57.3/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 14 | 3 | 19 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 6 | 1 | 7 |

## 5. Gap filling results
- Filled via **RAG**: 17
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 7
- **Flagged** for manual review: 0

### susceptible (missing_compartments)
- **Source:** rag
- **Primary name:** susceptible
- **Evidence chunks:** 3 snippet(s) in database
- *Use paper snippets to confirm compartment label and add to model.*

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered
- **Reasoning:** The model

### population11countries (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### initialrt (missing_parameters)
- **Source:** rag
- **Value:** 3.8 dimensionless
- **Description:** Initial reproduction number before interventions, averaged across all countries (95% credible interval: 2.4–5.6).
- **From papers:** p2_covid3_llm_gemini_20260407_204521, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### generationintervaldays (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### basetransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### publiceventsbaneffect (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### schoolclosureeffect (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### selfisolationeffect (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### socialdistancingeffect (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### lockdowneffect (missing_parameters)
- **Source:** rag
- **Value:** 81 percent reduction in Rt
- **Description:** Estimated relative reduction in Rt due to lockdown intervention (95% CI: 75–87%)
- **From papers:** p2_covid3_llm_claude_20260407_213850, p2_covid3_llm_gemini_20260407_204521, p2_cholera3_llm_claude_20260407_213639

### combinedinterventionmultiplier (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_openai_20260407_213203, p2_zika3_llm_gemini_20260407_211201

### effectivetransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### meanoutcomedelaydays (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### removalrate (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### deathrate (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### Susceptible->Infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** The process by which a susceptible individual contracts SARS-CoV-2 infection from an infected individual.
- **Reasoning:** COVID-19 is a directly transmissible disease, and new infections arise from interactions between susceptible and infected individuals, which is characteristic of a ContactFlow.

### Infected->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals transition from the Infected compartment to the Removed compartment due to recovery or death.
- **Reasoning:** The model links the infection cycle to observed deaths, indicating that individuals exit the infected state through processes like recovery or death, which constitute the 'Removed' compartment.

### Infected->Dead (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality of infected individuals due to the disease.
- **Reasoning:** The paper explicitly discusses 'observed deaths' and 'deaths attributable to COVID-19', indicating that infected individuals can transition to a

### initialrt (missing_parameters)
- **Source:** rag
- **Value:** 3.8 dimensionless
- **Description:** Initial reproduction number before interventions, averaged across all countries (95% credible interval: 2.4–5.6).
- **From papers:** p2_covid3_llm_gemini_20260407_204521, p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### lockdowneffect (missing_parameters)
- **Source:** rag
- **Value:** 81 percent reduction in Rt
- **Description:** Estimated relative reduction in Rt due to lockdown intervention (95% CI: 75–87%)
- **From papers:** p2_covid3_llm_claude_20260407_213850, p2_covid3_llm_gemini_20260407_204521, p2_cholera3_llm_claude_20260407_213639

### Susceptible->Infected (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected

### Infected->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who are infected either recover from the disease or die due to it, thus being removed from the pool of infectious individuals.
- **Reasoning:** Recovery or death from an infection are intrinsic processes occurring at a certain rate per individual, not dependent on contact with others.

### Infected->Dead (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality

## 6. Fill validation (vs gold standard)
- Parameters compared: **13**
- Exact match (<1% error): **2**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **11**
- **Accuracy (exact+close)**: **15.4%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| population11countries | 0.0001 | 741000000.0 | 100.0% | poor |
| initialrt | 3.8 | 3.8 | 0.0% | exact |
| generationintervaldays | 0.0001 | 6.5 | 100.0% | poor |
| basetransmissionrate | 0.0001-0.001 | InitialRt / GenerationIntervalDays | — | no_comparison |
| publiceventsbaneffect | 0.0001 | 1.0 | 99.99% | poor |
| schoolclosureeffect | 10000.0 | 1.0 | 999900.0% | poor |
| selfisolationeffect | 0.0001 | 1.0 | 99.99% | poor |
| socialdistancingeffect | 0.0001 | 1.0 | 99.99% | poor |
| lockdowneffect | 81.0 | 0.19 | 42531.58% | poor |
| combinedinterventionmultiplier | 0.0001-0.001 | PublicEventsBanEffect * SchoolClosureEffect * SelfIsolationEffect * SocialDistancingEffect * LockdownEffect | — | no_comparison |
| effectivetransmissionrate | 0.0001-0.001 | BaseTransmissionRate * CombinedInterventionMultiplier | — | no_comparison |
| meanoutcomedelaydays | 0.0001 | 18.0 | 100.0% | poor |
| removalrate | 0.5 | 1.0 | 50.0% | poor |
| deathrate | 10000.0 | 0.005 | 199999900.0% | poor |
| initialrt | 3.8 | 3.8 | 0.0% | exact |
| lockdowneffect | 81.0 | 0.19 | 42531.58% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **4** | Candidate: **4**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **3** | Candidate: **1**
- Precision **0.0** | Recall **0.0** | F1 **0.0**
