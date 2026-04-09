# Phase 3 Gap Analysis Report

**Disease / context:** malaria3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **40**
- Missing compartments: 8
- Missing parameters: 16
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 16
- Extra compartments (not in gold standard): 7
- Extra parameters (not in gold standard): 16
- Extra flows (not in gold standard): 11

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **40**
- After fills gaps (re-detected): **19**
- Delta (before - after): **21**
- Delta missing parameters: **16**
- Delta missing compartments: **2**
- Delta missing flows: **3**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **symptomaticinfectious** — Present in gold standard but not in extracted model. (severity: high)
- **asymptomaticpatentinfectious** — Present in gold standard but not in extracted model. (severity: high)
- **subpatentinfectious** — Present in gold standard but not in extracted model. (severity: high)
- **treatedinfectious** — Present in gold standard but not in extracted model. (severity: high)
- **protectedbyprophylaxis** — Present in gold standard but not in extracted model. (severity: high)
- **susceptiblemosquitoes** — Present in gold standard but not in extracted model. (severity: high)
- **exposedmosquitoes** — Present in gold standard but not in extracted model. (severity: high)
- **infectiousmosquitoes** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->SymptomaticInfectious** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible->AsymptomaticPatentInfectious** — Present in gold standard but not in extracted model (or flows list).
- **SymptomaticInfectious->TreatedInfectious** — Present in gold standard but not in extracted model (or flows list).
- **SymptomaticInfectious->AsymptomaticPatentInfectious** — Present in gold standard but not in extracted model (or flows list).
- **SymptomaticInfectious->SymptomaticInfectious** — Present in gold standard but not in extracted model (or flows list).
- **AsymptomaticPatentInfectious->SubpatentInfectious** — Present in gold standard but not in extracted model (or flows list).
- **AsymptomaticPatentInfectious->SymptomaticInfectious** — Present in gold standard but not in extracted model (or flows list).
- **AsymptomaticPatentInfectious->AsymptomaticPatentInfectious** — Present in gold standard but not in extracted model (or flows list).
- **SubpatentInfectious->Susceptible** — Present in gold standard but not in extracted model (or flows list).
- **SubpatentInfectious->SymptomaticInfectious** — Present in gold standard but not in extracted model (or flows list).
- **SubpatentInfectious->AsymptomaticPatentInfectious** — Present in gold standard but not in extracted model (or flows list).
- **TreatedInfectious->ProtectedByProphylaxis** — Present in gold standard but not in extracted model (or flows list).
- **TreatedInfectious->AsymptomaticPatentInfectious** — Present in gold standard but not in extracted model (or flows list).
- **ProtectedByProphylaxis->Susceptible** — Present in gold standard but not in extracted model (or flows list).
- **SusceptibleMosquitoes->ExposedMosquitoes** — Present in gold standard but not in extracted model (or flows list).
- **ExposedMosquitoes->InfectiousMosquitoes** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **susceptibletosymptomaticinfectionrate** — Present in gold standard but not in extracted model.
- **susceptibletoasymptomaticinfectionrate** — Present in gold standard but not in extracted model.
- **treatmentrate** — Present in gold standard but not in extracted model.
- **symptomatictoasymptomaticrate** — Present in gold standard but not in extracted model.
- **superinfectiontosymptomaticrate** — Present in gold standard but not in extracted model.
- **superinfectiontoasymptomaticrate** — Present in gold standard but not in extracted model.
- **patenttosubpatentrate** — Present in gold standard but not in extracted model.
- **subpatentclearancerate** — Present in gold standard but not in extracted model.
- **treatmentrecoverytoprotectionrate** — Present in gold standard but not in extracted model.
- **treatmentfailurerate** — Present in gold standard but not in extracted model.
- **protectionlossrate** — Present in gold standard but not in extracted model.
- **humantomosquitotransmissionrate_d** — Present in gold standard but not in extracted model.
- **humantomosquitotransmissionrate_a** — Present in gold standard but not in extracted model.
- **humantomosquitotransmissionrate_u** — Present in gold standard but not in extracted model.
- **humantomosquitotransmissionrate_t** — Present in gold standard but not in extracted model.
- **mosquitolatentprogressionrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: symptomatic infectious humans, asymptomatic infectious humans, subpatent infectious humans, treated infectious humans, prophylactically protected humans, exposed mosquitoes, infectious mosquitoes
- Extra parameters: drug efficacy (act), duration of gametocytaemia after non-artemisinin treatment, duration of gametocytaemia after act treatment, duration of gametocytaemia after act-pq treatment, duration of prophylaxis (short-acting drug), duration of prophylaxis (long-acting drug), reduction in infectiousness after non-artemisinin treatment, reduction in infectiousness after act/act-pq treatment, pregnancy prevalence in population aged 15-45, correlation in participation between mda rounds, mda coverage, act coverage for symptomatic treatment, non-act treatment efficacy, act treatment efficacy, relative infectivity of asymptomatic vs symptomatic, relative infectivity of subpatent vs symptomatic

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 52.5% | 30% |
| **Reference agreement** | 93.3% | 30% |
| **Fill traceability** | 68.4% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **77.4/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 8 | 16 | 16 | 40 |
| **Extra in model** | Model items not in reference (noise/convention) | 7 | 16 | 11 | 34 |

## 5. Gap filling results
- Filled via **RAG**: 45
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 14

### symptomaticinfectious (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### subpatentinfectious (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### treatedinfectious (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### susceptiblemosquitoes (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### exposedmosquitoes (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiousmosquitoes (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### Susceptible->SymptomaticInfectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### SymptomaticInfectious->TreatedInfectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### SymptomaticInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### SymptomaticInfectious->SymptomaticInfectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### AsymptomaticPatentInfectious->SubpatentInfectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### AsymptomaticPatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### SubpatentInfectious->Susceptible (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### SubpatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### SubpatentInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### TreatedInfectious->ProtectedByProphylaxis (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### TreatedInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **11**
- Precision **0.9091** | Recall **1.0** | F1 **0.9524**
### Flows
- Gold count: **16** | Candidate: **25**
- Precision **0.84** | Recall **1.0** | F1 **0.913**
