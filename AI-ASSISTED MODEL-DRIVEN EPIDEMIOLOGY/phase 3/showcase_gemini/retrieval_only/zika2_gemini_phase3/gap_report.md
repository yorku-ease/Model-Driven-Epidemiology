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
- After fills gaps (re-detected): **29**
- Delta (before - after): **0**
- Delta missing parameters: **0**
- Delta missing compartments: **0**
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
| **Gap reduction** | 0.0% | 30% |
| **Reference agreement** | 42.9% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **32.9/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 15 | 0 | 14 | 29 |
| **Extra in model** | Model items not in reference (noise/convention) | 15 | 12 | 17 | 44 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 58

### susceptiblewomen (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### exposedwomen (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiouswomensymptomaticblood (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiouswomenasymptomaticblood (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiouswomengenitalpersistence (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### recoveredwomen (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### susceptiblemen (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### exposedmen (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiousmensymptomaticblood (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiousmenasymptomaticblood (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiousmensemenpersistence (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### recoveredmen (missing_compartments)
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

### SusceptibleWomen->ExposedWomen (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ExposedWomen->InfectiousWomenSymptomaticBlood (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ExposedWomen->InfectiousWomenAsymptomaticBlood (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousWomenSymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousWomenAsymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousWomenGenitalPersistence->RecoveredWomen (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### SusceptibleMen->ExposedMen (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ExposedMen->InfectiousMenSymptomaticBlood (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ExposedMen->InfectiousMenAsymptomaticBlood (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousMenSymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousMenAsymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousMenSemenPersistence->RecoveredMen (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### susceptiblewomen (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### exposedwomen (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiouswomensymptomaticblood (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiouswomenasymptomaticblood (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiouswomengenitalpersistence (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### recoveredwomen (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### susceptiblemen (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### exposedmen (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiousmensymptomaticblood (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiousmenasymptomaticblood (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectiousmensemenpersistence (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### recoveredmen (missing_compartments)
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

### SusceptibleWomen->ExposedWomen (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ExposedWomen->InfectiousWomenSymptomaticBlood (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ExposedWomen->InfectiousWomenAsymptomaticBlood (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousWomenSymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousWomenAsymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousWomenGenitalPersistence->RecoveredWomen (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### SusceptibleMen->ExposedMen (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ExposedMen->InfectiousMenSymptomaticBlood (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ExposedMen->InfectiousMenAsymptomaticBlood (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousMenSymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousMenAsymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### InfectiousMenSemenPersistence->RecoveredMen (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **15** | Candidate: **15**
- Precision **0.6** | Recall **0.6** | F1 **0.6**
### Flows
- Gold count: **14** | Candidate: **17**
- Precision **0.2353** | Recall **0.2857** | F1 **0.2581**
