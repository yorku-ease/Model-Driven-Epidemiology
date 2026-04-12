# Phase 3 Gap Analysis Report

**Disease / context:** influenza3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **16**
- Missing compartments: 1
- Missing parameters: 10
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 5
- Extra compartments (not in gold standard): 2
- Extra parameters (not in gold standard): 13
- Extra flows (not in gold standard): 5

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **16**
- After fills gaps (re-detected): **3**
- Delta (before - after): **13**
- Delta missing parameters: **10**
- Delta missing compartments: **0**
- Delta missing flows: **3**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **vaccinatedineffective** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->VaccinatedEffectiveNotYetProtected** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->VaccinatedIneffective** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedEffectiveNotYetProtected->Latent** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedEffectiveNotYetProtected->ProtectedByVaccination** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedIneffective->Latent** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **communitytransmissionrate** — Present in gold standard but not in extracted model.
- **hospitaltransmissionrate** — Present in gold standard but not in extracted model.
- **effectivevaccinationrate** — Present in gold standard but not in extracted model.
- **ineffectivevaccinationrate** — Present in gold standard but not in extracted model.
- **protectiondelayrate** — Present in gold standard but not in extracted model.
- **latentprogressionrate** — Present in gold standard but not in extracted model.
- **hospitalizationrate** — Present in gold standard but not in extracted model.
- **communityrecoveryrate** — Present in gold standard but not in extracted model.
- **hospitalrecoveryrate** — Present in gold standard but not in extracted model.
- **hospitalmortalityrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: effectively vaccinated, ineffectively vaccinated
- Extra parameters: kappa, gamma_1, gamma_2, alpha_i, delta_i, q, beta_ij, r0, e_i, gamma, nu(t), vaccination_coverage, t*

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 81.2% | 30% |
| **Reference agreement** | 85.2% | 30% |
| **Fill traceability** | 76.3% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **65.2/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 10 | 5 | 16 |
| **Extra in model** | Model items not in reference (noise/convention) | 2 | 13 | 5 | 20 |

## 5. Gap filling results
- Filled via **RAG**: 10
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 9
- **Flagged** for manual review: 0

### vaccinatedineffective (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Ineffective

### communitytransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_openai_20260407_213203, p2_zika3_llm_gemini_20260407_211201

### hospitaltransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639

### effectivevaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### ineffectivevaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### protectiondelayrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### latentprogressionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### hospitalizationrate (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639

### communityrecoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_zika3_llm_claude_20260407_215851, p2_zika3_llm_openai_20260407_213203, p2_zika3_llm_gemini_20260407_211201

### hospitalrecoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### hospitalmortalityrate (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### Susceptible->VaccinatedEffectiveNotYetProtected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible individuals receive an effective vaccine but have not yet developed full protective immunity.
- **Reasoning:** Vaccination is an intervention that moves individuals from the susceptible compartment, and the 'NotYetProtected' state accounts for the biological delay in vaccine-induced immunity.

### Susceptible->VaccinatedIneffective (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible individuals receive a vaccine that does not confer effective immunity, transitioning them to a state of being vaccinated but still susceptible to infection.
- **Reasoning:** The excerpt discusses plans for a vaccine and 'vaccine efficacy,' implying that vaccination is a process and that some vaccinations may not be effective, leading to a transition to a 'VaccinatedIneffective' state.

### VaccinatedEffectiveNotYetProtected->Latent (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Infection of an

### VaccinatedEffectiveNotYetProtected->ProtectedByVaccination (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The development of protective immunity in individuals who have received an effective vaccine.
- **Reasoning:** This transition represents the biological process where an individual's immune system responds to an effective vaccine over time, leading to the establishment of protective immunity, which is

### VaccinatedIneffective->Latent (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Ineffectively vaccinated individuals become exposed to the influenza virus after contact with an infectious person.
- **Reasoning:** This flow represents the infection of individuals who received a vaccine that did not confer protection, occurring through contact with an infectious source.

### vaccinatedineffective (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Ineffective

### Susceptible->VaccinatedIneffective (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible individuals receive a vaccine that fails to confer immunity, transitioning them to a state of being vaccinated but still susceptible to infection.
- **Reasoning:** The excerpt explicitly mentions 'vaccine efficacy,' which implies that some vaccinations may not be effective, leading to a state where vaccinated individuals remain susceptible.

### VaccinatedIneffective->Latent (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Ineffectively vaccinated individuals

## 6. Fill validation (vs gold standard)
- Parameters compared: **10**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **10**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.99%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| communitytransmissionrate | 0.0001 | 0.45 | 99.98% | poor |
| hospitaltransmissionrate | 10000.0 | 0.2 | 4999900.0% | poor |
| effectivevaccinationrate | 0.0001 | 0.03 | 99.67% | poor |
| ineffectivevaccinationrate | 0.0001 | 0.01 | 99.0% | poor |
| protectiondelayrate | 0.0001 | 0.1 | 99.9% | poor |
| latentprogressionrate | 0.0001 | 0.526 | 99.98% | poor |
| hospitalizationrate | 10000.0 | 0.08 | 12499900.0% | poor |
| communityrecoveryrate | 0.0001 | 0.667 | 99.99% | poor |
| hospitalrecoveryrate | 10000.0 | 0.667 | 1499150.37% | poor |
| hospitalmortalityrate | 10000.0 | 0.015 | 66666566.67% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **10**
- Precision **0.8** | Recall **1.0** | F1 **0.8889**
### Flows
- Gold count: **11** | Candidate: **16**
- Precision **0.6875** | Recall **1.0** | F1 **0.8148**
