# Phase 3 Gap Analysis Report

**Disease / context:** zika2

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **29**
- Missing compartments: 15
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 14
- Extra compartments (not in gold standard): 15
- Extra parameters (not in gold standard): 12
- Extra flows (not in gold standard): 17

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **29**
- After fills gaps (re-detected): **27**
- Delta (before - after): **2**
- Delta missing parameters: **0**
- Delta missing compartments: **2**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **susceptiblewomen** — Present in gold standard but not in extracted model. (severity: high)
- **exposedwomen** — Present in gold standard but not in extracted model. (severity: high)
- **infectiouswomensymptomaticblood** — Present in gold standard but not in extracted model. (severity: high)
- **infectiouswomenasymptomaticblood** — Present in gold standard but not in extracted model. (severity: high)
- **infectiouswomengenitalpersistence** — Present in gold standard but not in extracted model. (severity: high)
- **recoveredwomen** — Present in gold standard but not in extracted model. (severity: high)
- **susceptiblemen** — Present in gold standard but not in extracted model. (severity: high)
- **exposedmen** — Present in gold standard but not in extracted model. (severity: high)
- **infectiousmensymptomaticblood** — Present in gold standard but not in extracted model. (severity: high)
- **infectiousmenasymptomaticblood** — Present in gold standard but not in extracted model. (severity: high)
- **infectiousmensemenpersistence** — Present in gold standard but not in extracted model. (severity: high)
- **recoveredmen** — Present in gold standard but not in extracted model. (severity: high)
- **susceptiblemosquitoes** — Present in gold standard but not in extracted model. (severity: high)
- **exposedmosquitoes** — Present in gold standard but not in extracted model. (severity: high)
- **infectiousmosquitoes** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **SusceptibleWomen->ExposedWomen** — Present in gold standard but not in extracted model (or flows list).
- **ExposedWomen->InfectiousWomenSymptomaticBlood** — Present in gold standard but not in extracted model (or flows list).
- **ExposedWomen->InfectiousWomenAsymptomaticBlood** — Present in gold standard but not in extracted model (or flows list).
- **InfectiousWomenSymptomaticBlood->InfectiousWomenGenitalPersistence** — Present in gold standard but not in extracted model (or flows list).
- **InfectiousWomenAsymptomaticBlood->InfectiousWomenGenitalPersistence** — Present in gold standard but not in extracted model (or flows list).
- **InfectiousWomenGenitalPersistence->RecoveredWomen** — Present in gold standard but not in extracted model (or flows list).
- **SusceptibleMen->ExposedMen** — Present in gold standard but not in extracted model (or flows list).
- **ExposedMen->InfectiousMenSymptomaticBlood** — Present in gold standard but not in extracted model (or flows list).
- **ExposedMen->InfectiousMenAsymptomaticBlood** — Present in gold standard but not in extracted model (or flows list).
- **InfectiousMenSymptomaticBlood->InfectiousMenSemenPersistence** — Present in gold standard but not in extracted model (or flows list).
- **InfectiousMenAsymptomaticBlood->InfectiousMenSemenPersistence** — Present in gold standard but not in extracted model (or flows list).
- **InfectiousMenSemenPersistence->RecoveredMen** — Present in gold standard but not in extracted model (or flows list).
- **SusceptibleMosquitoes->ExposedMosquitoes** — Present in gold standard but not in extracted model (or flows list).
- **ExposedMosquitoes->InfectiousMosquitoes** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: susceptible women, exposed women, symptomatic infectious women, asymptomatic infectious women, infectious women genital only, recovered women, susceptible men, exposed men, symptomatic infectious men, asymptomatic infectious men, infectious men genital only, recovered men, susceptible mosquitoes, exposed mosquitoes, infectious mosquitoes
- Extra parameters: β_vh, β_hv, β_wm, β_mw, ϕ, 1/λ_h, 1/λ_v, 1/γ_1, 1/γ_2, 1/γ_3, 1/ν, ρ

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 6.9% | 30% |
| **Reference agreement** | 72.6% | 30% |
| **Fill traceability** | 51.9% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **54.2/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 15 | 0 | 14 | 29 |
| **Extra in model** | Model items not in reference (noise/convention) | 15 | 12 | 17 | 44 |

## 5. Gap filling results
- Filled via **RAG**: 28
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 28
- **Flagged** for manual review: 0

### susceptiblewomen (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Women

### exposedwomen (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Women

### infectiouswomensymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Women Symptomatic Blood

### infectiouswomenasymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Asymptomatic Women

### infectiouswomengenitalpersistence (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Women Genital Persistence

### recoveredwomen (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered
- **Reasoning:** A compartmental

### susceptiblemen (missing_compartments)
- **Source:** inference
- **Primary name:** SusceptibleMen
- **Reasoning:** The model explicitly includes sexual contact as a main transmission pathway,

### exposedmen (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Men
- **Reasoning:** The

### infectiousmensemenpersistence (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Men Semen Persistence
- **Reasoning:** The model explicitly includes sexual transmission of ZIKV, and the persistence

### recoveredmen (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered Men
- **Reasoning:** The gold label explicitly indicates 'recoveredmen' as the missing compartment, suggesting the model differentiates by sex and includes a recovered state.

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Mosquitoes

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Mosquitoes

### SusceptibleWomen->ExposedWomen (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ExposedWomen->InfectiousWomenSymptomaticBlood (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ExposedWomen->InfectiousWomenAsymptomaticBlood (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### InfectiousWomenSymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### InfectiousWomenAsymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### InfectiousWomenGenitalPersistence->RecoveredWomen (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### SusceptibleMen->ExposedMen (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ExposedMen->InfectiousMenSymptomaticBlood (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ExposedMen->InfectiousMenAsymptomaticBlood (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### InfectiousMenSymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### InfectiousMenAsymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### InfectiousMenSemenPersistence->RecoveredMen (missing_flows)
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
- Gold count: **15** | Candidate: **24**
- Precision **0.625** | Recall **1.0** | F1 **0.7692**
### Flows
- Gold count: **14** | Candidate: **27**
- Precision **0.5185** | Recall **1.0** | F1 **0.6829**
