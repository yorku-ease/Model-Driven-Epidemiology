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
- After fills gaps (re-detected): **24**
- Delta (before - after): **16**
- Delta missing parameters: **16**
- Delta missing compartments: **0**
- Delta missing flows: **0**

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
| **Gap reduction** | 40.0% | 30% |
| **Reference agreement** | 91.1% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **59.3/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 8 | 16 | 16 | 40 |
| **Extra in model** | Model items not in reference (noise/convention) | 7 | 16 | 11 | 34 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 64
- **Flagged** for manual review: 0

### symptomaticinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Symptomatic Infectious

### asymptomaticpatentinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Asymptomatic Infectious

### subpatentinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Subpatent Infectious
- **Reasoning:** The

### treatedinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Treated Infectious

### protectedbyprophylaxis (missing_compartments)
- **Source:** inference
- **Primary name:** On Prophylaxis

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Mosquitoes

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Mosquitoes
- **Reasoning:** The text discusses preventing onward transmission to mosquitoes and the lifespan of malaria vectors, indicating that mosquitoes become infectious and are a critical part of the transmission cycle.

### Susceptible->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become symptom

### Susceptible->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected with malaria parasites and transition to an asymptomatic, patent infectious state, capable of transmitting the disease.
- **Reasoning:** The excerpt explicitly mentions 'asymptomatic parasite carriers' and the 'infectious reservoir in humans,' indicating that susceptible individuals can become infected and carry the parasite without symptoms, thus contributing to transmission.

### SymptomaticInfectious->TreatedInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Symptomatic infectious

### SymptomaticInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who were symptomatic

### SymptomaticInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals remain in the Sympt

### AsymptomaticPatentInfectious->SubpatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with detectable, asymptomatic

### AsymptomaticPatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression from asymptomatic to

### AsymptomaticPatentInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals remain in the

### SubpatentInfectious->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with sub

### SubpatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with subpatent malaria infections can progress to develop symptomatic malaria.
- **Reasoning:** The progression from a subpatent to a symptomatic infection is an intrinsic biological process within an infected individual, driven by parasite multiplication, not by contact with other individuals.

### SubpatentInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with a low, undetectable parasite load (subpatent) progress to having a detectable parasite load (patent) while remaining asymptomatic.
- **Reasoning:** This transition represents the natural progression of a malaria infection where parasite density increases from subpatent to patent levels within an asymptomatic individual, independent of external contact.

### TreatedInfectious->ProtectedByProphylaxis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have been treated

### TreatedInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have received ant

### ProtectedByProphylaxis->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible mosquitoes

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mosquitoes transition from being infected but not yet infectious to being capable of transmitting malaria after completing the extrinsic incubation period.
- **Reasoning:** This flow represents the biological maturation of the parasite within the mosquito, a time-dependent process known as the extrinsic incubation period, making it a rate-driven transition.

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **12**
- Precision **0.8333** | Recall **1.0** | F1 **0.9091**
### Flows
- Gold count: **16** | Candidate: **25**
- Precision **0.84** | Recall **1.0** | F1 **0.913**
