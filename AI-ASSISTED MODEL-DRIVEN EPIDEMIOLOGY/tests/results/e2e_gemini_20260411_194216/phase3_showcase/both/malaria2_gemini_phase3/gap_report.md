# Phase 3 Gap Analysis Report

**Disease / context:** malaria2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **13**
- Missing compartments: 5
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 8
- Extra compartments (not in gold standard): 8
- Extra parameters (not in gold standard): 11
- Extra flows (not in gold standard): 11

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **13**
- After fills gaps (re-detected): **11**
- Delta (before - after): **2**
- Delta missing parameters: **0**
- Delta missing compartments: **1**
- Delta missing flows: **1**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **treatedclinicaldisease** — Present in gold standard but not in extracted model. (severity: high)
- **untreatedclinicaldisease** — Present in gold standard but not in extracted model. (severity: high)
- **prophylaxis** — Present in gold standard but not in extracted model. (severity: high)
- **asymptomaticpatentinfection** — Present in gold standard but not in extracted model. (severity: high)
- **asymptomaticsubpatentinfection** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->TreatedClinicalDisease** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->UntreatedClinicalDisease** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->AsymptomaticPatentInfection** — Present in gold standard but not in extracted model (or flows list).
- **TreatedClinicalDisease->Prophylaxis** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedClinicalDisease->AsymptomaticPatentInfection** — Present in gold standard but not in extracted model (or flows list).
- **Prophylaxis->Susceptible** — Present in gold standard but not in extracted model (or flows list).
- **AsymptomaticPatentInfection->AsymptomaticSubPatentInfection** — Present in gold standard but not in extracted model (or flows list).
- **AsymptomaticSubPatentInfection->Susceptible** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: exposed humans, untreated clinical disease humans, treated clinical disease humans, prophylactic protection humans, patent asymptomatic infection humans, sub-patent asymptomatic infection humans, exposed mosquitoes, infectious mosquitoes
- Extra parameters: w, ft, rt, rp, rd, ru, eir, ddt half-life, rts,s vaccine efficacy half-life, act prophylactic protection duration, llin insecticide efficacy half-life

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 15.4% | 30% |
| **Reference agreement** | 63.0% | 30% |
| **Fill traceability** | 83.3% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **60.2/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 5 | 0 | 8 | 13 |
| **Extra in model** | Model items not in reference (noise/convention) | 8 | 11 | 11 | 30 |

## 5. Gap filling results
- Filled via **RAG**: 16
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 8
- **Flagged** for manual review: 0

### treatedclinicaldisease (missing_compartments)
- **Source:** inference
- **Primary name:** Treated Clinical Disease

### untreatedclinicaldisease (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Clinical Disease

### prophylaxis (missing_compartments)
- **Source:** rag
- **Primary name:** prophylaxis
- **Evidence chunks:** 3 snippet(s) in database
- *Use paper snippets to confirm compartment label and add to model.*

### asymptomaticpatentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Asymptomatic Infected

### asymptomaticsubpatentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Asymptomatic Subpatent Infection

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

### Prophylaxis->Susceptible (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### AsymptomaticPatentInfection->AsymptomaticSubPatentInfection (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 1 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### AsymptomaticSubPatentInfection->Susceptible (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### treatedclinicaldisease (missing_compartments)
- **Source:** inference
- **Primary name:** Treated Clinical Disease

### untreatedclinicaldisease (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Clinical Disease

### asymptomaticpatentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Asymptomatic Patent Infection

### asymptomaticsubpatentinfection (missing_compartments)
- **Source:** inference
- **Primary name:** Asymptomatic Subpatent Infection

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
- Gold count: **6** | Candidate: **14**
- Precision **0.5** | Recall **1.0** | F1 **0.6667**
### Flows
- Gold count: **8** | Candidate: **19**
- Precision **0.4211** | Recall **1.0** | F1 **0.5926**
