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
| **Reference agreement** | 61.2% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **49.4/100** | — |

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
- **Primary name:** Exposed Women

### infectiouswomensymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Women

### infectiouswomenasymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Asymptomatic Women

### infectiouswomengenitalpersistence (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Women Genital Persistence

### recoveredwomen (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered Women

### susceptiblemen (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Men

### exposedmen (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Humans

### infectiousmensymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Men Symptomatic Blood

### infectiousmenasymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** InfectiousMenAsymptomatic

### infectiousmensemenpersistence (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Men (Semen)

### recoveredmen (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered Men
- **Reasoning:** The

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes
- **Reasoning:** The paper explicitly states that Zika is primarily transmitted by Aedes mosquitoes and that the model includes mosquito bites as a main transmission pathway, necessitating a compartment for susceptible mosquitoes.

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Mosquitoes

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Mosquitoes

### SusceptibleWomen->ExposedWomen (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible women become exposed to the Zika virus through contact with infected individuals, such as infected mosquitoes or infected sexual partners.
- **Reasoning:** The paper states that ZIKV is transmitted by mosquito bites and sexual contact

### ExposedWomen->InfectiousWomenSymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed women progress to an infectious and symptomatic state, with the Zika virus present in their blood.
- **Reasoning:** This transition represents the progression of the disease within an exposed woman, from the latent period to the onset of symptoms and infectivity relevant for mosquito transmission.

### ExposedWomen->InfectiousWomenAsymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of

### InfectiousWomenSymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The establishment of persistent Zika virus infection in the genital tract of a symptomatic infectious woman, following acute viremia.
- **Reasoning:** This flow represents the physiological progression of Zika virus from the bloodstream to a persistent state in the genital tract, a known characteristic that enables sexual transmission.

### InfectiousWomenAsymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The establishment of Zika virus

### InfectiousWomenGenitalPersistence->RecoveredWomen (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Women with persistent

### SusceptibleMen->ExposedMen (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible men become exposed

### ExposedMen->InfectiousMenSymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression

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
- **Description:** The rate at

### InfectiousMenSemenPersistence->RecoveredMen (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Men who have been

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible mosquitoes become exposed to the Zika virus after biting an infected human host.
- **Reasoning:** The paper states ZIKV is primarily transmitted by Aedes mosquitoes, indicating infection occurs through contact (bites) with an infected host.

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed mosquitoes become infectious

### susceptiblewomen (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Women

### exposedwomen (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Women

### infectiouswomensymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** InfectiousSexual

### infectiouswomenasymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** InfectiousAsymptomaticWomen

### infectiouswomengenitalpersistence (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Women with Genital

### recoveredwomen (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered Women
- **Reasoning:** Given

### susceptiblemen (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Men

### exposedmen (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Men

### infectiousmensymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Men Symptomatic Blood

### infectiousmensemenpersistence (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Men Semen Persistence

### recoveredmen (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered Men

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes
- **Reasoning:** The paper states the model includes mosquito bites as a main transmission pathway, which necessitates a compartment for mosquitoes that can become infected.

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Mosquitoes

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Mosquitoes
- **Reasoning:** The paper explicitly states the model includes 'mosquito bites' as a main transmission pathway, necessitating a compartment for mosquitoes capable of transmitting the virus.

### SusceptibleWomen->ExposedWomen (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible women become

### ExposedWomen->InfectiousWomenSymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed women complete their

### ExposedWomen->InfectiousWomenAsymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed women complete their

### InfectiousWomenSymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The progression of a symptomatic woman with ZIKV in her blood to developing a persistent ZIKV infection in her genital tract.
- **Reasoning:** This flow represents the internal progression of a woman's infection from systemic (blood) to a persistent state in the genital tract, which is a rate-dependent process within an individual.

### InfectiousWomenAsymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The establishment of Zika

### InfectiousWomenGenitalPersistence->RecoveredWomen (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Women with persistent genital ZIKV infection eventually clear the virus and recover, becoming immune.
- **Reasoning:** Even with genital persistence, ZIKV infections eventually resolve, leading to recovery and potential immunity in women, which is typically modeled as a rate of transition.

### SusceptibleMen->ExposedMen (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible men

### ExposedMen->InfectiousMenSymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of exposed men

### ExposedMen->InfectiousMenAsymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of exposed men to an infectious, asymptomatic state where the virus is present in their blood.
- **Reasoning:** This flow represents the progression of exposed men through their latent period to become infectious in their blood, without symptoms.

### InfectiousMenSymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which Zika virus, present in the blood of a symptomatic infectious man, establishes persistence in his semen.
- **Reasoning:** The

### InfectiousMenAsymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of asymptomatic

### InfectiousMenSemenPersistence->RecoveredMen (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovery of infectious men

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible mosquitoes become exposed to the Zika virus after biting an infected human.
- **Reasoning:** The paper explicitly states ZIKV is primarily transmitted by Aedes mosquitoes and includes 'mosquito bites' as a main transmission pathway, indicating contact-dependent infection of mosquitoes.

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mosquitoes that have been infected with the Zika virus progress from the exposed state to the infectious state after an extrinsic incubation period.
- **Reasoning:** After acquiring the virus, mosquitoes undergo an extrinsic incubation period during which the virus replicates and disseminates, making them capable of transmitting the disease.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **15** | Candidate: **23**
- Precision **0.5652** | Recall **0.8667** | F1 **0.6842**
### Flows
- Gold count: **14** | Candidate: **23**
- Precision **0.4348** | Recall **0.7143** | F1 **0.5405**
