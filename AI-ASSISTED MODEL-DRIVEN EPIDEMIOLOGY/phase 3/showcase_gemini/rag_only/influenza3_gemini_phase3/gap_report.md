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
- After fills gaps (re-detected): **0**
- Delta (before - after): **16**
- Delta missing parameters: **10**
- Delta missing compartments: **1**
- Delta missing flows: **5**

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
| **Gap reduction** | 100.0% | 30% |
| **Reference agreement** | 85.2% | 30% |
| **Fill traceability** | 93.8% | 20% |
| **Parameter accuracy** | 70.0% | 20% |
| **→ Composite** | **88.3/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 10 | 5 | 16 |
| **Extra in model** | Model items not in reference (noise/convention) | 2 | 13 | 5 | 20 |

## 5. Gap filling results
- Filled via **RAG**: 15
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 1

### vaccinatedineffective (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### communitytransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.45 
- **Description:** Representative age-specific transmission term from infectious community cases; in the paper lambda_i depends on the age-structured contact matrix and transmission probability q
- **From papers:** p1_model_covid, p1_model_influenza, p1_model_cholera

### hospitaltransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.20 
- **Description:** Representative contribution of hospitalized infectious individuals to the force of infection; the paper includes I_j + H_j in the force of infection
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_influenza

### effectivevaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.03 
- **Description:** e_i * nu(t), effective vaccination rate for this representative age group
- **From papers:** p1_model_measles, p1_model_cholera, p1_model_influenza

### ineffectivevaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.01 
- **Description:** (1-e_i) * nu(t), ineffective vaccination rate for this representative age group
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_influenza

### protectiondelayrate (missing_parameters)
- **Source:** rag
- **Value:** 0.10 
- **Description:** gamma, rate of becoming protected after vaccination; mean 10 days in the paper
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_influenza

### latentprogressionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.526 
- **Description:** kappa, progression rate from latent to infectious; based on mean latent period 1.9 days in the paper
- **From papers:** p1_model_measles, p1_model_covid, p1_model_cholera

### hospitalizationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.2 
- **From papers:** p1_model_ebola, p1_model_cholera, p1_model_influenza

### communityrecoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.20 
- **Description:** Aggregate recovery/removal rate for non-hospitalized infectious survivors in weekly units
- **From papers:** p1_model_ebola, p1_model_influenza, p1_model_cholera

### hospitalrecoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.25 
- **Description:** Aggregate recovery/removal rate from H to R in weekly units
- **From papers:** p1_model_ebola, p1_model_cholera, p1_model_influenza

### hospitalmortalityrate (missing_parameters)
- **Source:** rag
- **Value:** 0.015 
- **Description:** d_i, age-specific mortality rate among hospitalized individuals
- **From papers:** p1_model_covid, p1_model_cholera, p1_model_dengue

### Susceptible->VaccinatedEffectiveNotYetProtected (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible->VaccinatedIneffective (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### VaccinatedEffectiveNotYetProtected->Latent (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### VaccinatedEffectiveNotYetProtected->ProtectedByVaccination (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### VaccinatedIneffective->Latent (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 6. Fill validation (vs gold standard)
- Parameters compared: **10**
- Exact match (<1% error): **7**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **3**
- **Accuracy (exact+close)**: **70.0%**
- Median relative error: **0.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| communitytransmissionrate | 0.45 | 0.45 | 0.0% | exact |
| hospitaltransmissionrate | 0.2 | 0.2 | 0.0% | exact |
| effectivevaccinationrate | 0.03 | 0.03 | 0.0% | exact |
| ineffectivevaccinationrate | 0.01 | 0.01 | 0.0% | exact |
| protectiondelayrate | 0.1 | 0.1 | 0.0% | exact |
| latentprogressionrate | 0.526 | 0.526 | 0.0% | exact |
| hospitalizationrate | 0.2 | 0.08 | 150.0% | poor |
| communityrecoveryrate | 0.2 | 0.667 | 70.01% | poor |
| hospitalrecoveryrate | 0.25 | 0.667 | 62.52% | poor |
| hospitalmortalityrate | 0.015 | 0.015 | 0.0% | exact |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **10**
- Precision **0.8** | Recall **1.0** | F1 **0.8889**
### Flows
- Gold count: **11** | Candidate: **16**
- Precision **0.6875** | Recall **1.0** | F1 **0.8148**
