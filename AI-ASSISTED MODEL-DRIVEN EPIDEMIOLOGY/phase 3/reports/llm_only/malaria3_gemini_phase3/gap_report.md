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
- After fills gaps (re-detected): **22**
- Delta (before - after): **18**
- Delta missing parameters: **16**
- Delta missing compartments: **1**
- Delta missing flows: **1**

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
| **Gap reduction** | 45.0% | 30% |
| **Reference agreement** | 91.1% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 6.2% | 20% |
| **→ Composite** | **52.1/100** | — |

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
- **Primary name:** Asymptomatic Patent Infectious

### subpatentinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Subpatent Infectious

### treatedinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Treated Infectious

### protectedbyprophylaxis (missing_compartments)
- **Source:** inference
- **Primary name:** Drug Protected
- **Reasoning:** The excerpt

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Mosquitoes

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Mosquitoes
- **Reasoning:** The excerpt discusses transmission to mosquitoes and the lifespan of malaria vectors, indicating the importance of

### susceptibletosymptomaticinfectionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.005 per day
- **Confidence:** LOW

### susceptibletoasymptomaticinfectionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.005 per day
- **Confidence:** LOW

### treatmentrate (missing_parameters)
- **Source:** inference
- **Value:** 0.1 per day
- **Confidence:** LOW

### symptomatictoasymptomaticrate (missing_parameters)
- **Source:** inference
- **Value:** 0.1 per day
- **Confidence:** LOW

### superinfectiontosymptomaticrate (missing_parameters)
- **Source:** inference
- **Value:** 0.2 per superinfection
- **Confidence:** LOW

### superinfectiontoasymptomaticrate (missing_parameters)
- **Source:** inference
- **Value:** 0.05 1/day
- **Confidence:** LOW

### patenttosubpatentrate (missing_parameters)
- **Source:** inference
- **Value:** 0.1 per day
- **Confidence:** LOW

### subpatentclearancerate (missing_parameters)
- **Source:** inference
- **Value:** 1.5 per year
- **Confidence:** LOW

### treatmentrecoverytoprotectionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.05 per day
- **Confidence:** LOW

### treatmentfailurerate (missing_parameters)
- **Source:** inference
- **Value:** 0.1 
- **Reasoning:** Treatment failure can occur due to drug resistance, poor adherence, or host factors, even with effective antimalarials. A value of 0.1 (10%) represents a plausible failure rate in settings where drugs are generally effective but not perfect.
- **Confidence:** LOW

### protectionlossrate (missing_parameters)
- **Source:** inference
- **Value:** 0.033 per day
- **Confidence:** LOW

### humantomosquitotransmissionrate_d (missing_parameters)
- **Source:** inference
- **Value:** 0.15 
- **Reasoning:** This value represents the probability of a mosquito becoming infected after biting an infectious human. It falls within typical ranges used in malaria transmission models, which often vary from 0.05 to 0.5.
- **Confidence:** LOW

### humantomosquitotransmissionrate_a (missing_parameters)
- **Source:** inference
- **Value:** 0.15 
- **Reasoning:** This parameter represents the probability of a
- **Confidence:** LOW

### humantomosquitotransmissionrate_u (missing_parameters)
- **Source:** inference
- **Value:** 0.15 
- **Reasoning:** This value represents the probability of a mosquito becoming infected after biting an infectious human, a common baseline in malaria transmission models.
- **Confidence:** LOW

### humantomosquitotransmissionrate_t (missing_parameters)
- **Source:** inference
- **Value:** 0.15 
- **Confidence:** LOW

### mosquitolatentprogressionrate (missing_parameters)
- **Source:** inference
- **Value:** 0.09 per day
- **Confidence:** LOW

### Susceptible->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals

### Susceptible->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible

### SymptomaticInfectious->TreatedInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Symptomatic

### SymptomaticInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Resolution of clinical symptoms

### SymptomaticInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Symptomatic infectious individuals

### AsymptomaticPatentInfectious->SubpatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with patent, asymptomatic

### AsymptomaticPatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression

### AsymptomaticPatentInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals remain in the asymptomatic

### SubpatentInfectious->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with subpatent malaria

### SubpatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with a subpatent

### SubpatentInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of

### TreatedInfectious->ProtectedByProphylaxis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have

### TreatedInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have received

### ProtectedByProphylaxis->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals lose

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mosquitoes completing the extrinsic incubation period and becoming infectious.
- **Reasoning:** This flow represents the intrinsic biological process of parasite maturation within the mosquito, which occurs at a specific rate, making it a RateFlow.

### symptomaticinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Symptomatic Infectious

### asymptomaticpatentinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Asymptomatic Patent Infectious

### subpatentinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Asymptomatic Infectious

### treatedinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Treated Infectious

### protectedbyprophylaxis (missing_compartments)
- **Source:** inference
- **Primary name:** ProtectedByProphylaxis

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes
- **Reasoning:** The excerpt discusses preventing onward transmission to mosquitoes and the lifespan of malaria vectors, indicating the need for

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Mosquitoes

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Mosquitoes

### Susceptible->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible

### Susceptible->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected with malaria parasites through the bite of an infected mosquito, entering an asymptomatic but patent infectious state.
- **Reasoning:** The excerpt discusses 'asymptomatic parasite carriers' and 'preventing onward transmission to mosquitoes,' indicating that susceptible individuals can become infected and carry the parasite without symptoms, which is a fundamental step in malaria transmission.

### SymptomaticInfectious->TreatedInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Symptomatic infectious individuals

### SymptomaticInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals recover from acute

### SymptomaticInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals remain in the

### AsymptomaticPatentInfectious->SubpatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with patent, asymptomatic

### AsymptomaticPatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an asymptomatic malaria infection to a symptomatic, clinically apparent infection within an individual.
- **Reasoning:** The text mentions 'asymptomatic parasite carriers' and 'symptoms suggestive of malaria,' implying the existence of both states and the natural progression of the disease within individuals from an asymptomatic to a symptomatic phase.

### AsymptomaticPatentInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Treatment failure in asymptomatic patent

### SubpatentInfectious->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovery of individuals from a

### SubpatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who are

### SubpatentInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with subpatent malaria infections can progress to a state where their parasite density becomes patent (detectable) but they remain asymptomatic.
- **Reasoning:** This transition represents the natural progression of a malaria infection within an individual, where parasite load increases from undetectable to detectable levels without causing symptoms.

### TreatedInfectious->ProtectedByProphylaxis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have been

### TreatedInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Treated infectious

### ProtectedByProphylaxis->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who were temporarily protected

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible mosquitoes become

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The maturation of the Plas

## 6. Fill validation (vs gold standard)
- Parameters compared: **16**
- Exact match (<1% error): **1**
- Close (<10% error): **0**
- Approximate (<50% error): **2**
- Poor (>50% error): **13**
- **Accuracy (exact+close)**: **6.2%**
- Median relative error: **87.5%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| susceptibletosymptomaticinfectionrate | 0.005 | 0.04 | 87.5% | poor |
| susceptibletoasymptomaticinfectionrate | 0.005 | 0.03 | 83.33% | poor |
| treatmentrate | 0.1 | 0.35 | 71.43% | poor |
| symptomatictoasymptomaticrate | 0.1 | 0.2 | 50.0% | poor |
| superinfectiontosymptomaticrate | 0.2 | 0.025 | 700.0% | poor |
| superinfectiontoasymptomaticrate | 0.05 | 0.02 | 150.0% | poor |
| patenttosubpatentrate | 0.1 | 0.1 | 0.0% | exact |
| subpatentclearancerate | 1.5 | 0.05 | 2900.0% | poor |
| treatmentrecoverytoprotectionrate | 0.05 | 0.25 | 80.0% | poor |
| treatmentfailurerate | 0.1 | 0.05 | 100.0% | poor |
| protectionlossrate | 0.033 | 0.08 | 58.75% | poor |
| humantomosquitotransmissionrate_d | 0.15 | 0.2 | 25.0% | approximate |
| humantomosquitotransmissionrate_a | 0.15 | 0.067 | 123.88% | poor |
| humantomosquitotransmissionrate_u | 0.15 | 0.012 | 1150.0% | poor |
| humantomosquitotransmissionrate_t | 0.15 | 0.04 | 275.0% | poor |
| mosquitolatentprogressionrate | 0.09 | 0.1 | 10.0% | approximate |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **12**
- Precision **0.8333** | Recall **1.0** | F1 **0.9091**
### Flows
- Gold count: **16** | Candidate: **25**
- Precision **0.84** | Recall **1.0** | F1 **0.913**
