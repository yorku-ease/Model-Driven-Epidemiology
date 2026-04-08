# Phase 3 Gap Analysis Report

**Disease / context:** malaria

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **7**
- Missing compartments: 0
- Missing parameters: 7
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 0
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 14
- Extra flows (not in gold standard): 2

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **7**
- After fills gaps (re-detected): **0**
- Delta (before - after): **7**
- Delta missing parameters: **7**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- None

## 4. Missing parameters
- **μ1** — Present in gold standard but not in extracted model.
- **λv^v** — Present in gold standard but not in extracted model.
- **λv^s** — Present in gold standard but not in extracted model.
- **λh^v** — Present in gold standard but not in extracted model.
- **λh^s** — Present in gold standard but not in extracted model.
- **λm** — Present in gold standard but not in extracted model.
- **μ2** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: π, µ1, µ2, θ, ε, ηv, ηs, ξv, ξs, ρ, φ, β1, β2, β3

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 25% |
| **Reference agreement** | 95.8% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 0.0% | 15% |
| **Structural integrity** | 26.5%  (34→25 errors) | 15% |
| **→ Composite** | **52.9/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 6 | 0 | +6 |
| High | 2 | 1 | +1 |
| Medium | 26 | 24 | +2 |
| **Total** | **34** | **25** | **+9** |

**10 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible Humans -> ContactFlow | Redirected Susceptible Humans ContactFlow target from //@compartments.2 to //@compartments.3 (Non-Ve |
| `self_referential_flow` | Susceptible Humans -> ContactFlow | Redirected Susceptible Humans ContactFlow target from //@compartments.3 to //@compartments.2 (Vector |
| `self_referential_flow` | Vaccinated Humans -> ContactFlow | Redirected Vaccinated Humans ContactFlow target from //@compartments.2 to //@compartments.3 (Non-Vec |
| `self_referential_flow` | Vaccinated Humans -> ContactFlow | Redirected Vaccinated Humans ContactFlow target from //@compartments.3 to //@compartments.2 (Vector- |
| `self_referential_flow` | Susceptible Mosquitoes -> ContactFlow | Redirected Susceptible Mosquitoes ContactFlow target from //@compartments.8 to //@compartments.2 (Ve |
| `zero_population_all` | all_compartments | Set Susceptible Humans population=1000 |
| `missing_birth_sources` | Susceptible Mosquitoes | Set Susceptible Mosquitoes population=1000 (initial condition) |
| `flow_chain_incomplete` | Infectious Mosquitoes | Added flow Exposed Mosquitoes → Infectious Humans |
| `orphaned_parameters` | θ | Wired θ (//@parameters.4) to Vaccinated Humans → Susceptible Humans flow |
| `flow_chain_incomplete` | Infectious Mosquitoes | Added flow Exposed Mosquitoes → Infectious Humans |

**25 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `flow_chain_incomplete` | Infectious Mosquitoes | **high** |
| `orphaned_parameters` | π | **medium** |
| `orphaned_parameters` | µ1 | **medium** |
| `orphaned_parameters` | µ2 | **medium** |
| `orphaned_parameters` | ε | **medium** |
| `orphaned_parameters` | ηS | **medium** |
| `orphaned_parameters` | ξV | **medium** |
| `orphaned_parameters` | ξS | **medium** |
| `orphaned_parameters` | τ2 | **medium** |
| `orphaned_parameters` | δ1 | **medium** |
| `orphaned_parameters` | σ | **medium** |
| `orphaned_parameters` | ρ | **medium** |
| `orphaned_parameters` | φ | **medium** |
| `orphaned_parameters` | β1 | **medium** |
| `orphaned_parameters` | β2 | **medium** |
| `orphaned_parameters` | α1 | **medium** |
| `orphaned_parameters` | α2 | **medium** |
| `orphaned_parameters` | β3 | **medium** |
| `orphaned_parameters` | μ1 | **medium** |
| `orphaned_parameters` | λv^v | **medium** |
| `orphaned_parameters` | λv^s | **medium** |
| `orphaned_parameters` | λh^v | **medium** |
| `orphaned_parameters` | λh^s | **medium** |
| `orphaned_parameters` | λm | **medium** |
| `orphaned_parameters` | μ2 | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 7 | 0 | 7 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 14 | 2 | 16 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 7
- **Flagged** for manual review: 0

### μ1 (missing_parameters)
- **Source:** inference
- **Value:** 0.0001 per day
- **Reasoning:** A plausible default for a small non-vector-related loss/removal or background transition rate in malaria models is on the order of 10^-4 per day, reflecting rare events such as transfusion- or congenital-associated contributions relative to dominant mosquito transmission. This corresponds to an annual scale of roughly 0.04, which is within a typical low-rate epidemiological range for minor pathways or background processes.
- **Confidence:** LOW

### λv^v (missing_parameters)
- **Source:** inference
- **Value:** 0.0001 per person per day
- **Reasoning:** A small default non-vector transmission rate is appropriate because malaria is overwhelmingly mosquito-borne, while transfusion/congenital/iatrogenic transmission is rare and context-specific. A value on the order of 10^-4 per person-day is plausible for models that include a low background non-vector force of infection.
- **Confidence:** LOW

### λv^s (missing_parameters)
- **Source:** inference
- **Value:** 0.0001 per person per day
- **Reasoning:** A small default is appropriate because non-vector malaria transmission (e.g., transfusion, congenital, transplant-related) is rare compared with mosquito-borne transmission and typically contributes only a minor background force of infection. A plausible order-of-magnitude range is about 1e-5 to 1e-3 per person per day, depending on local blood safety, maternal parasitemia, and healthcare context.
- **Confidence:** LOW

### λh^v (missing_parameters)
- **Source:** inference
- **Value:** 0.0001 per person per day
- **Reasoning:** A plausible default for a non-vector human-to-human transmission rate in malaria models is very low, since transfusion, congenital, and transplant transmission are rare compared with mosquito-borne spread. A small value on the order of 10^-5 to 10^-4 per person-day is consistent with these uncommon routes while still allowing occasional persistence in special settings.
- **Confidence:** LOW

### λh^s (missing_parameters)
- **Source:** inference
- **Value:** 0.0001 per capita per day
- **Reasoning:** A small non-vector transmission rate is plausible because transfusion, congenital, and transplant-associated malaria are rare compared with mosquito-borne transmission, but not negligible in endemic settings. A default on the order of 10^-4 per capita per day is consistent with a low-frequency background force of infection used for these pathways.
- **Confidence:** LOW

### λm (missing_parameters)
- **Source:** inference
- **Value:** 0.0001 per person per day
- **Reasoning:** A small default is appropriate because non-vector malaria transmission is rare relative to mosquito-borne transmission, but it is nonzero due to transfusion, congenital, and transplant-associated cases. A value around 10^-4 per person-day is a plausible low-end placeholder for models that include a background non-vector force of infection.
- **Confidence:** LOW

### μ2 (missing_parameters)
- **Source:** inference
- **Value:** 0.01 1/day
- **Reasoning:** A plausible default for a malaria non-vector-related transition or loss rate is on the order of 10^-2 per day, reflecting a relatively slow process compared with mosquito transmission but still biologically meaningful in endemic settings. This falls within typical epidemiological rate ranges used for human malaria compartment models when specific data are unavailable.
- **Confidence:** LOW

## 6. Fill validation (vs gold standard)
- Parameters compared: **7**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **7**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| μ1 | 0.0001 | 0.001384 | 92.77% | poor |
| λv^v | 0.0001 | 0.0 | 100.0% | poor |
| λv^s | 0.0001 | 0.0 | 100.0% | poor |
| λh^v | 0.0001 | 0.0 | 100.0% | poor |
| λh^s | 0.0001 | 0.0 | 100.0% | poor |
| λm | 0.0001 | 0.0 | 100.0% | poor |
| μ2 | 0.01 | 0.1 | 90.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **10**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **11** | Candidate: **13**
- Precision **0.8462** | Recall **1.0** | F1 **0.9167**
