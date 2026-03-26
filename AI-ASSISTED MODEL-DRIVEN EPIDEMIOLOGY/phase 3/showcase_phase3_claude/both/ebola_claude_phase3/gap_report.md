# Phase 3 Gap Analysis Report

**Disease / context:** ebola

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **12**
- Missing compartments: 1
- Missing parameters: 6
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 5
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 9
- Extra flows (not in gold standard): 1

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **12**
- After fills gaps (re-detected): **3**
- Delta (before - after): **9**
- Delta missing parameters: **6**
- Delta missing compartments: **1**
- Delta missing flows: **2**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **environment pathogens** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Infectious->Recovered** — Present in gold standard but not in extracted model (or flows list).
- **Infectious->Deceased** — Present in gold standard but not in extracted model (or flows list).
- **Infectious->Environment pathogens** — Present in gold standard but not in extracted model (or flows list).
- **Quarantine->Deceased** — Present in gold standard but not in extracted model (or flows list).
- **Deceased->Environment pathogens** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **μ** — Present in gold standard but not in extracted model.
- **ω2** — Present in gold standard but not in extracted model.
- **ω3** — Present in gold standard but not in extracted model.
- **ω1** — Present in gold standard but not in extracted model.
- **ω1q** — Present in gold standard but not in extracted model.
- **η** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: ebola virus pathogens in environment
- Extra parameters: α1, α2, ψ1, ψ2, ψ3, ε, w1, w2, c

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 75.0% | 25% |
| **Reference agreement** | 74.2% | 25% |
| **Fill traceability** | 100.0% | 20% |
| **Parameter accuracy** | 100.0% | 15% |
| **Structural integrity** | 40.0%  (25→15 errors) | 15% |
| **→ Composite** | **78.3/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 2 | 1 | +1 |
| High | 4 | 0 | +4 |
| Medium | 17 | 12 | +5 |
| Low | 2 | 2 | +0 |
| **Total** | **25** | **15** | **+10** |

**11 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible -> ContactFlow | Annotated Susceptible ContactFlow as direct S→I (no Exposed stage) |
| `zero_population_all` | all_compartments | Set Susceptible population=1000 |
| `flow_chain_incomplete` | Ebola Virus Pathogens in Environment | Added flow Ebola Virus Pathogens in Environment → Recovered |
| `flow_chain_incomplete` | Dead | Added flow Dead → Recovered |
| `flow_chain_incomplete` | Environment Pathogens | Added flow Environment Pathogens → Recovered |
| `orphaned_parameters` | α1 | Wired α1 (//@parameters.0) to Ebola Virus Pathogens in Environment → Recovered flow |
| `orphaned_parameters` | α2 | Wired α2 (//@parameters.1) to Dead → Recovered flow |
| `orphaned_parameters` | ψ2 | Wired ψ2 (//@parameters.3) to Environment Pathogens → Recovered flow |
| `orphaned_parameters` | ω2 | Wired ω2 (//@parameters.16) to Infectious → Recovered flow |
| `orphaned_parameters` | ω3 | Wired ω3 (//@parameters.17) to Infectious → Environment Pathogens flow |
| `self_referential_flow` | Susceptible -> ContactFlow | Annotated Susceptible ContactFlow as direct S→I (no Exposed stage) |

**15 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `self_referential_flow` | Susceptible -> ContactFlow | **critical** |
| `orphaned_parameters` | ψ3 | **medium** |
| `orphaned_parameters` | ε | **medium** |
| `orphaned_parameters` | ρ1 | **medium** |
| `orphaned_parameters` | w2 | **medium** |
| `orphaned_parameters` | h | **medium** |
| `orphaned_parameters` | c | **medium** |
| `orphaned_parameters` | b | **medium** |
| `orphaned_parameters` | d | **medium** |
| `orphaned_parameters` | μ | **medium** |
| `orphaned_parameters` | ω1 | **medium** |
| `orphaned_parameters` | ω1q | **medium** |
| `orphaned_parameters` | η | **medium** |
| `composite_parameter_decomposition` | c | **low** |
| `composite_parameter_decomposition` | b | **low** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 6 | 5 | 12 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 9 | 1 | 11 |

## 5. Gap filling results
- Filled via **RAG**: 9
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 6
- **Flagged** for manual review: 0

### Infectious->Deceased (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Quarantine->Deceased (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- **Text evidence:** 2 chunk(s)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Deceased->Environment pathogens (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 2 match(es)
- **Text evidence:** 2 chunk(s)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **7**
- Precision **0.7143** | Recall **0.8333** | F1 **0.7692**
### Flows
- Gold count: **8** | Candidate: **6**
- Precision **0.8333** | Recall **0.625** | F1 **0.7143**
