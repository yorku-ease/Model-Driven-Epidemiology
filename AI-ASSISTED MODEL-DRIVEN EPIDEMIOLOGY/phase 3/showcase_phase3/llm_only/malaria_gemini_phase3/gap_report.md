# Phase 3 Gap Analysis Report

**Disease / context:** malaria

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **12**
- Missing compartments: 2
- Missing parameters: 4
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 6
- Extra compartments (not in gold standard): 2
- Extra parameters (not in gold standard): 11
- Extra flows (not in gold standard): 7

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **12**
- After fills gaps (re-detected): **8**
- Delta (before - after): **4**
- Delta missing parameters: **4**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **vector-exposed humans (eh1)** — Present in gold standard but not in extracted model. (severity: high)
- **non-vector-exposed humans (eh2)** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible humans (SH)->Vector-exposed humans (EH1)** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible humans (SH)->Non-vector-exposed humans (EH2)** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated humans (VH)->Vector-exposed humans (EH1)** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated humans (VH)->Non-vector-exposed humans (EH2)** — Present in gold standard but not in extracted model (or flows list).
- **Vector-exposed humans (EH1)->Infectious humans (IH)** — Present in gold standard but not in extracted model (or flows list).
- **Non-vector-exposed humans (EH2)->Infectious humans (IH)** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **α1** — Present in gold standard but not in extracted model.
- **μ1** — Present in gold standard but not in extracted model.
- **α2** — Present in gold standard but not in extracted model.
- **μ2** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: vector exposed humans, non-vector exposed humans
- Extra parameters: π, µ1, µ2, ε, θ, φ, η_v, η_s, ξ_v, ξ_s, ρ

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 33.3% | 25% |
| **Reference agreement** | 97.8% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 15% |
| **Structural integrity** | 36.8%  (19→12 errors) | 15% |
| **→ Composite** | **53.3/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 6 | 0 | +6 |
| High | 2 | 1 | +1 |
| Medium | 11 | 11 | +0 |
| **Total** | **19** | **12** | **+7** |

**9 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible Humans -> ContactFlow | Redirected Susceptible Humans ContactFlow target from //@compartments.2 to //@compartments.3 (Non-Ve |
| `self_referential_flow` | Susceptible Humans -> ContactFlow | Redirected Susceptible Humans ContactFlow target from //@compartments.3 to //@compartments.2 (Vector |
| `self_referential_flow` | Vaccinated Humans -> ContactFlow | Redirected Vaccinated Humans ContactFlow target from //@compartments.2 to //@compartments.3 (Non-Vec |
| `self_referential_flow` | Vaccinated Humans -> ContactFlow | Redirected Vaccinated Humans ContactFlow target from //@compartments.3 to //@compartments.2 (Vector  |
| `self_referential_flow` | Susceptible Mosquitoes -> ContactFlow | Redirected Susceptible Mosquitoes ContactFlow target from //@compartments.8 to //@compartments.2 (Ve |
| `zero_population_all` | all_compartments | Set Susceptible Humans population=1000 |
| `missing_birth_sources` | Susceptible Mosquitoes | Set Susceptible Mosquitoes population=1000 (initial condition) |
| `flow_chain_incomplete` | Infectious Mosquitoes | Added flow Exposed Mosquitoes → Infectious Humans |
| `flow_chain_incomplete` | Infectious Mosquitoes | Added flow Exposed Mosquitoes → Infectious Humans |

**12 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `flow_chain_incomplete` | Infectious Mosquitoes | **high** |
| `orphaned_parameters` | π | **medium** |
| `orphaned_parameters` | k | **medium** |
| `orphaned_parameters` | µ1 | **medium** |
| `orphaned_parameters` | µ2 | **medium** |
| `orphaned_parameters` | Λ | **medium** |
| `orphaned_parameters` | δ1 | **medium** |
| `orphaned_parameters` | η_V | **medium** |
| `orphaned_parameters` | α1 | **medium** |
| `orphaned_parameters` | μ1 | **medium** |
| `orphaned_parameters` | α2 | **medium** |
| `orphaned_parameters` | μ2 | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 4 | 6 | 12 |
| **Extra in model** | Model items not in reference (noise/convention) | 2 | 11 | 7 | 20 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 20
- **Flagged** for manual review: 0

### vector-exposed humans (eh1) (missing_compartments)
- **Source:** inference
- **Primary name:** Vector-Exposed Humans

### non-vector-exposed humans (eh2) (missing_compartments)
- **Source:** inference
- **Primary name:** Non-Vector-Exposed Humans

### Susceptible humans (SH)->Vector-exposed humans (EH1) (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible humans become exposed to malaria infection through contact with infectious Anopheles mosquitoes.
- **Reasoning:** The paper explicitly states that malaria is a vectorborne disease with Anopheles mosquitoes playing the primary role in its transmission, indicating that human exposure occurs via vector contact.

### Susceptible humans (SH)->Non-vector-exposed humans (EH2) (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible humans

### Vaccinated humans (VH)->Vector-exposed humans (EH1) (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Vaccinated humans become

### Vaccinated humans (VH)->Non-vector-exposed humans (EH2) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Waning of vaccine-

### Vector-exposed humans (EH1)->Infectious humans (IH) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of vector

### Non-vector-exposed humans (EH2)->Infectious humans (IH) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **10**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **11** | Candidate: **12**
- Precision **0.9167** | Recall **1.0** | F1 **0.9565**
