# Phase 3 Gap Analysis Report

**Disease / context:** malaria2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **11**
- Missing compartments: 4
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 7
- Extra compartments (not in gold standard): 6
- Extra parameters (not in gold standard): 19
- Extra flows (not in gold standard): 13

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **11**
- After fills gaps (re-detected): **11**
- Delta (before - after): **0**
- Delta missing parameters: **0**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **treatedclinicaldisease** — Present in gold standard but not in extracted model. (severity: high)
- **untreatedclinicaldisease** — Present in gold standard but not in extracted model. (severity: high)
- **asymptomaticpatentinfection** — Present in gold standard but not in extracted model. (severity: high)
- **asymptomaticsubpatentinfection** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->TreatedClinicalDisease** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->UntreatedClinicalDisease** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->AsymptomaticPatentInfection** — Present in gold standard but not in extracted model (or flows list).
- **TreatedClinicalDisease->Prophylaxis** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedClinicalDisease->AsymptomaticPatentInfection** — Present in gold standard but not in extracted model (or flows list).
- **AsymptomaticPatentInfection->AsymptomaticSubPatentInfection** — Present in gold standard but not in extracted model (or flows list).
- **AsymptomaticSubPatentInfection->Susceptible** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: treated clinical disease, untreated clinical disease, asymptomatic patent infection, asymptomatic sub-patent infection, latent mosquitoes, infectious mosquitoes
- Extra parameters: φ, ft, rt, rp, rd, ru, λ, eir, hbi, llin_half_life, llin_adherence_decay, ddt_half_life, act_prophylaxis_duration, vaccine_efficacy, vaccine_half_life, llin_coverage, irs_coverage, msat_coverage, vaccine_coverage

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 0.0% | 30% |
| **Reference agreement** | 83.8% | 30% |
| **Fill traceability** | 63.6% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **57.9/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 4 | 0 | 7 | 11 |
| **Extra in model** | Model items not in reference (noise/convention) | 6 | 19 | 13 | 38 |

## 5. Gap filling results
- Filled via **RAG**: 14
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 8
- **Flagged** for manual review: 0

### treatedclinicaldisease (missing_compartments)
- **Source:** inference
- **Primary name:** treatedclinicaldisease
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### untreatedclinicaldisease (missing_compartments)
- **Source:** inference
- **Primary name:** untreatedclinicaldisease
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### asymptomaticpatentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** asymptomaticpatentinfection
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### asymptomaticsubpatentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** asymptomaticsubpatentinfection
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### Susceptible->TreatedClinicalDisease (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible->UntreatedClinicalDisease (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible->AsymptomaticPatentInfection (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### TreatedClinicalDisease->Prophylaxis (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 4 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedClinicalDisease->AsymptomaticPatentInfection (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 4 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### AsymptomaticPatentInfection->AsymptomaticSubPatentInfection (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 1 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### AsymptomaticSubPatentInfection->Susceptible (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **6** | Candidate: **9**
- Precision **0.7778** | Recall **1.0** | F1 **0.875**
### Flows
- Gold count: **8** | Candidate: **18**
- Precision **0.6667** | Recall **1.0** | F1 **0.8**
