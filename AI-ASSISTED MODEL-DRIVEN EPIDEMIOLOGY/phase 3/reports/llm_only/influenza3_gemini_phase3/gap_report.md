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
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 20.0% | 20% |
| **→ Composite** | **63.9/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 10 | 5 | 16 |
| **Extra in model** | Model items not in reference (noise/convention) | 2 | 13 | 5 | 20 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 19
- **Flagged** for manual review: 0

### vaccinatedineffective (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Ineffective

### communitytransmissionrate (missing_parameters)
- **Source:** inference
- **Value:** 1.8 
- **Reasoning:** Pand
- **Confidence:** LOW

### hospitaltransmissionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.3 
- **Reasoning:** Hospital transmission
- **Confidence:** LOW

### effectivevaccinationrate (missing_parameters)
- **Source:** inference
- **Value:** 0 
- **Reasoning:** During the early 'Introduction' phase of a novel influenza pandemic, a specific vaccine is typically not yet developed, licensed, or widely available, leading to a negligible effective vaccination rate.
- **Confidence:** LOW

### ineffectivevaccinationrate (missing_parameters)
- **Source:** inference
- **Value:** 0.5 
- **Reasoning:** Infl
- **Confidence:** LOW

### protectiondelayrate (missing_parameters)
- **Source:** inference
- **Value:** 0.1 1/day
- **Confidence:** LOW

### latentprogressionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.5 per day
- **Confidence:** LOW

### hospitalizationrate (missing_parameters)
- **Source:** inference
- **Value:** 0.01 per case
- **Confidence:** LOW

### communityrecoveryrate (missing_parameters)
- **Source:** inference
- **Value:** 0.2 per day
- **Confidence:** LOW

### hospitalrecoveryrate (missing_parameters)
- **Source:** inference
- **Value:** 0.1 per day
- **Confidence:** LOW

### hospitalmortalityrate (missing_parameters)
- **Source:** inference
- **Value:** 0.025 proportion
- **Confidence:** LOW

### Susceptible->VaccinatedEffectiveNotYetProtected (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible individuals receive a vaccine but have not yet developed full protective immunity.
- **Reasoning:** Vaccination is an intervention that moves individuals from a susceptible state to a vaccinated state

### Susceptible->VaccinatedIneffective (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible individuals receive a vaccine that does not confer effective immunity, transitioning them to a state where they have been vaccinated but remain susceptible to infection.
- **Reasoning:** The excerpt discusses plans for vaccine formulation and vaccine efficacy, directly implying that vaccination will occur and that some vaccines may not be effective, leading to this transition.

### VaccinatedEffectiveNotYetProtected->Latent (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Infection of vaccinated

### VaccinatedEffectiveNotYetProtected->ProtectedByVaccination (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have received an effective vaccine develop protective immunity over time.
- **Reasoning:** This transition represents the biological process of the immune system responding to the vaccine and generating protective antibodies, which occurs at an intrinsic rate after vaccination.

### VaccinatedIneffective->Latent (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Ineffectively vaccinated

### vaccinatedineffective (missing_compartments)
- **Source:** inference
- **Primary name:** Vaccinated Ineffective

### Susceptible->VaccinatedIneffective (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Susceptible individuals receive the

### VaccinatedIneffective->Latent (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** An individual

## 6. Fill validation (vs gold standard)
- Parameters compared: **10**
- Exact match (<1% error): **1**
- Close (<10% error): **1**
- Approximate (<50% error): **1**
- Poor (>50% error): **7**
- **Accuracy (exact+close)**: **20.0%**
- Median relative error: **85.01%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| communitytransmissionrate | 1.8 | 0.45 | 300.0% | poor |
| hospitaltransmissionrate | 0.3 | 0.2 | 50.0% | approximate |
| effectivevaccinationrate | 0.0 | 0.03 | 100.0% | poor |
| ineffectivevaccinationrate | 0.5 | 0.01 | 4900.0% | poor |
| protectiondelayrate | 0.1 | 0.1 | 0.0% | exact |
| latentprogressionrate | 0.5 | 0.526 | 4.94% | close |
| hospitalizationrate | 0.01 | 0.08 | 87.5% | poor |
| communityrecoveryrate | 0.2 | 0.667 | 70.01% | poor |
| hospitalrecoveryrate | 0.1 | 0.667 | 85.01% | poor |
| hospitalmortalityrate | 0.025 | 0.015 | 66.67% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **10**
- Precision **0.8** | Recall **1.0** | F1 **0.8889**
### Flows
- Gold count: **11** | Candidate: **16**
- Precision **0.6875** | Recall **1.0** | F1 **0.8148**
