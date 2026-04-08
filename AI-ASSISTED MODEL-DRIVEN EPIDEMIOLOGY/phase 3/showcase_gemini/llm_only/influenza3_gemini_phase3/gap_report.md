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
- After fills gaps (re-detected): **5**
- Delta (before - after): **11**
- Delta missing parameters: **5**
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
| **Gap reduction** | 68.8% | 30% |
| **Reference agreement** | 85.2% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **66.2/100** | — |

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
- Filled via **inference**: 21
- **Flagged** for manual review: 0

### effectivevaccinationrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### ineffectivevaccinationrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### protectiondelayrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### latentprogressionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### hospitalizationrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

## 6. Fill validation (vs gold standard)
- Parameters compared: **0**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| effectivevaccinationrate | None | 0.03 | — | no_fill |
| ineffectivevaccinationrate | None | 0.01 | — | no_fill |
| protectiondelayrate | None | 0.10 | — | no_fill |
| latentprogressionrate | None | 0.526 | — | no_fill |
| hospitalizationrate | None | 0.08 | — | no_fill |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **10**
- Precision **0.8** | Recall **1.0** | F1 **0.8889**
### Flows
- Gold count: **11** | Candidate: **16**
- Precision **0.6875** | Recall **1.0** | F1 **0.8148**
