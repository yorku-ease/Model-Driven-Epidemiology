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
- After fills gaps (re-detected): **26**
- Delta (before - after): **3**
- Delta missing parameters: **0**
- Delta missing compartments: **3**
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
| **Gap reduction** | 10.3% | 30% |
| **Reference agreement** | 75.8% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **55.8/100** | — |

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
- Filled via **inference**: 56
- **Flagged** for manual review: 0

### susceptiblewomen (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Women

### exposedwomen (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Women

### infectiouswomensymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Women Symptomatic

### infectiouswomenasymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** InfectiousMenAsymptomaticBlood

### infectiouswomengenitalpersistence (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Women Genital Persistence

### recoveredwomen (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered Women
- **Reasoning:** The

### susceptiblemen (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Men
- **Reasoning:** To

### exposedmen (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Men
- **Reasoning:** Given

### infectiousmensymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** InfectiousMenSymptomatic

### infectiousmenasymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** InfectiousMen

### infectiousmensemenpersistence (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Men (Semen)

### recoveredmen (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered Women

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes
- **Reasoning:** The paper states the model includes 'mosquito bites' as a main transmission pathway, which requires compartments for the mosquito population, including susceptible individuals to be bitten by infected humans and become infected themselves.

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Mosquitoes

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Mosquitoes

### SusceptibleWomen->ExposedWomen (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible women become exposed to the Zika virus through contact with infected individuals (sexual contact) or infected vectors (mosquito bites).
- **Reasoning:** The paper explicitly states that ZIKV spreads through mosquito

### ExposedWomen->InfectiousWomenSymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of infected women from the exposed (latent) state to the infectious and symptomatic state, where the virus is present in the blood.
- **Reasoning:** This flow represents the completion of the intrinsic incubation period,

### ExposedWomen->InfectiousWomenAsymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed women progress

### InfectiousWomenSymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Transition of ZIKV from systemic infection (blood) to persistent infection in the genital tract of symptomatic women.
- **Reasoning:** Zika virus is known to persist in the genital tract, enabling sexual transmission, and this transition represents the physiological process of the virus localizing from the blood to the genital tract within an infected woman.

### InfectiousWomenAsymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The establishment of Zika virus persistence in the genital tract of an asymptomatic infectious woman from a blood infection.
- **Reasoning:** This transition represents an internal biological progression within an infected individual, where the virus moves from the bloodstream to establish a persistent infection in the genital tract, rather than a transmission event due to contact.

### InfectiousWomenGenitalPersistence->RecoveredWomen (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Infectious women

### SusceptibleMen->ExposedMen (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible men become

### ExposedMen->InfectiousMenSymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The progression of exposed men to an infectious and symptomatic state where the Zika virus is present in their blood.
- **Reasoning:** This transition represents the intrinsic progression from the latent (exposed) period to the infectious and symptomatic stage within an individual, which is typically modeled as a rate.

### ExposedMen->InfectiousMenAsymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed men progress

### InfectiousMenSymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate

### InfectiousMenAsymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of Zika virus

### InfectiousMenSemenPersistence->RecoveredMen (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovery of infectious men with persistent Zika virus in semen to a recovered state.
- **Reasoning:** Men who are infectious with Zika virus, even with semen persistence, will eventually recover from the infection and transition to a recovered compartment.

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible mosquitoes become exposed to the Zika virus after biting an infected host.
- **Reasoning:** The paper explicitly states that ZIKV is primarily transmitted by Aedes mosquitoes and lists 'mosquito bites' as a main transmission pathway, indicating a contact-dependent infection process for mosquitoes.

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed mosquitoes become infectious

### susceptiblewomen (missing_compartments)
- **Source:** inference
- **Primary name:** SusceptibleWomen

### exposedwomen (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Women
- **Reasoning:** Given

### infectiouswomensymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Women Symptomatic Blood

### infectiouswomenasymptomaticblood (missing_compartments)
- **Source:** inference
- **Primary name:** InfectiousWomenAsymptomatic

### infectiouswomengenitalpersistence (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Women Genital Persistence

### recoveredwomen (missing_compartments)
- **Source:** inference
- **Primary name:** Recovered Women
- **Reasoning:** The

### susceptiblemen (missing_compartments)
- **Source:** inference
- **Primary name:** SusceptibleWomen
- **Reasoning:** Given

### exposedmen (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Men
- **Reasoning:** The

### infectiousmensemenpersistence (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Men (Semen Persistence)

### recoveredmen (missing_compartments)
- **Source:** inference
- **Primary name:** RecoveredMen
- **Reasoning:** Given

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes
- **Reasoning:** The paper explicitly states the model includes 'mosquito bites' as a main transmission pathway, necessitating a susceptible mosquito compartment for the vector population.

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
- **Reasoning:** The paper explicitly states that ZIKV transmission occurs via 'mosquito bites and sexual contact', both of which are contact-based pathways leading to exposure.

### ExposedWomen->InfectiousWomenSymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The progression of exposed women to an infectious and symptomatic state, where the virus is present in their blood, making

### ExposedWomen->InfectiousWomenAsymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of exposed women to an infectious, asymptomatic, and viremic state.
- **Reasoning:** This flow represents the progression of the infection within an individual woman from the exposed (latent) state to the infectious (viremic) state, marking the end of the incubation period.

### InfectiousWomenSymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The persistence of

### InfectiousWomenAsymptomaticBlood->InfectiousWomenGenitalPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Asymptomatic infected women

### InfectiousWomenGenitalPersistence->RecoveredWomen (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Women with persistent genital Zika virus infection recover and clear the virus.
- **Reasoning:** This flow represents the natural process of recovery from persistent genital ZIKV infection,

### SusceptibleMen->ExposedMen (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible men become exposed

### ExposedMen->InfectiousMenSymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed men become infectious

### ExposedMen->InfectiousMenAsymptomaticBlood (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of exposed men to an infectious state where the virus is present in their blood asymptomatically.
- **Reasoning:** This flow represents the incubation period within men, after which they become infectious via their blood but remain asymptomatic.

### InfectiousMenSymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The establishment of ZIKV persistence in the semen of a symptomatic infectious man.
- **Reasoning:** This flow represents the biological process of ZIKV moving from the bloodstream to persist in the semen within an individual, which is a rate-dependent internal progression.

### InfectiousMenAsymptomaticBlood->InfectiousMenSemenPersistence (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of Z

### InfectiousMenSemenPersistence->RecoveredMen (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Infect

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible mosquitoes become exposed to ZIKV after biting an infected human.
- **Reasoning:** The paper states that ZIKV is primarily transmitted by Aedes mosquitoes and that mosquito bites are a main transmission pathway, implying contact between infected humans and susceptible mosquitoes.

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Exposed mosquitoes transition to an infectious state after completing the extrinsic incubation period, becoming capable of transmitting the virus.
- **Reasoning:** In vector-borne disease models, exposed mosquitoes undergo an extrinsic incubation period before becoming infectious, representing the time the virus takes to replicate within the mosquito to a transmissible level.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **15** | Candidate: **21**
- Precision **0.7143** | Recall **1.0** | F1 **0.8333**
### Flows
- Gold count: **14** | Candidate: **27**
- Precision **0.5185** | Recall **1.0** | F1 **0.6829**
