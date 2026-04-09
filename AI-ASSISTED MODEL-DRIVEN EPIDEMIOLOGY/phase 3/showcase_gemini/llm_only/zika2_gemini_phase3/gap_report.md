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
- After fills gaps (re-detected): **28**
- Delta (before - after): **1**
- Delta missing parameters: **0**
- Delta missing compartments: **1**
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
| **Gap reduction** | 3.4% | 30% |
| **Reference agreement** | 64.9% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **40.5/100** | — |

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
- Filled via **inference**: 57
- **Flagged** for manual review: 0

### susceptiblewomen (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Women

### exposedwomen (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Humans

### infectiouswomensymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** InfectiousWomenSexuallyTransmissible

### infectiouswomenasymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** InfectiousWomenAsymptomaticSexual

### infectiouswomengenitalpersistence (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Women Genital Persistence

### recoveredwomen (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered Women

### susceptiblemen (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Men
- **Reasoning:** The

### exposedmen (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Men

### infectiousmensymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Men Symptomatic Blood

### infectiousmensemenpersistence (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Men With Semen

### recoveredmen (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered Men

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes
- **Reasoning:** The model explicitly includes mosquito bites as a main transmission pathway, requiring a compartment for mosquitoes that can become infected.

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Mosquitoes

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Mosquitoes

### SusceptibleWomen->ExposedWomen (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible women become exposed to ZIKV through contact with infected mosquitoes or infected sexual partners.
- **Reasoning:** The paper explicitly states that ZIKV is transmitted by mosquito bites and sexual contact, which are both contact-dependent transmission routes leading to exposure.

### ExposedWomen->InfectiousWomenSymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed women

### ExposedWomen->InfectiousWomenAsymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed women complete

### InfectiousWomenSymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The establishment and persistence of

### InfectiousWomenAsymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Asymptomatic infectious women

### InfectiousWomenGenitalPersistence->RecoveredWomen (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Women with persistent genital ZIKV infection clear the virus and recover, gaining immunity.
- **Reasoning:** This transition represents

### SusceptibleMen->ExposedMen (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Transmission of the Zika virus from an infected mosquito to a susceptible man through a mosquito bite, leading to the man becoming exposed.
- **Reasoning:** The paper states that Zika is primarily transmitted by Aedes mosquitoes and lists mosquito bites

### ExposedMen->InfectiousMenSymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of exposed men

### ExposedMen->InfectiousMenAsymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of exposed

### InfectiousMenSymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which

### InfectiousMenAsymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate

### InfectiousMenSemenPersistence->RecoveredMen (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovery of infectious men with

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible mosquitoes become exposed to the Zika virus after biting an infected human.
- **Reasoning:** The paper states ZIKV is primarily transmitted by Aedes mosquitoes, indicating a contact-dependent transmission from infected humans to susceptible mosquitoes.

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mosquitoes transition from being exposed to the virus to becoming infectious after the extrinsic incubation period.
- **Reasoning:** After a mosquito acquires the virus, it undergoes an extrinsic incubation period before it can transmit the virus to a human, which is a time-dependent process represented by a rate.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **15** | Candidate: **26**
- Precision **0.5385** | Recall **0.9333** | F1 **0.6829**
### Flows
- Gold count: **14** | Candidate: **25**
- Precision **0.48** | Recall **0.8571** | F1 **0.6154**
