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
| **Reference agreement** | 77.4% | 30% |
| **Fill traceability** | 62.5% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **47.7/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 8 | 16 | 16 | 40 |
| **Extra in model** | Model items not in reference (noise/convention) | 7 | 16 | 11 | 34 |

## 5. Gap filling results
- Filled via **RAG**: 16
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 48
- **Flagged** for manual review: 0

### symptomaticinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Asymptomatic Infectious

### asymptomaticpatentinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Asymptomatic Infectious

### subpatentinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Subpatent Infectious

### treatedinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** Treated Infectious

### protectedbyprophylaxis (missing_compartments)
- **Source:** inference
- **Primary name:** Protected by Prophylaxis

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Mosquitoes

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Mosquitoes

### susceptibletosymptomaticinfectionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### susceptibletoasymptomaticinfectionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### treatmentrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### symptomatictoasymptomaticrate (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### superinfectiontosymptomaticrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### superinfectiontoasymptomaticrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### patenttosubpatentrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### subpatentclearancerate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### treatmentrecoverytoprotectionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### treatmentfailurerate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### protectionlossrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### humantomosquitotransmissionrate_d (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639

### humantomosquitotransmissionrate_a (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639

### humantomosquitotransmissionrate_u (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639

### humantomosquitotransmissionrate_t (missing_parameters)
- **Source:** rag
- **Value:** 10000 persons
- **Description:** Total human population size (constant)
- **From papers:** p2_cholera3_llm_claude_20260407_213639

### mosquitolatentprogressionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### Susceptible->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become

### Susceptible->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected with malaria parasites through mosquito bites and transition into an asymptomatic, patent infectious state, meaning they carry detectable parasites but show no symptoms.
- **Reasoning:** The text refers to 'asymptomatic parasite carriers' and 'parasitaemic individuals' who contribute to the 'infectious reservoir,' indicating that susceptible individuals can become infected and enter this state, which is typically driven by contact with infected mosquitoes.

### SymptomaticInfectious->TreatedInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Symptomatic

### SymptomaticInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who were

### SymptomaticInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals remain in the symptomatic

### AsymptomaticPatentInfectious->SubpatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with patent asymptomatic

### AsymptomaticPatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an asymptomatic malaria infection to a symptomatic malaria infection within an infected individual.
- **Reasoning:** The text distinguishes between 'asymptomatic parasite carriers' and individuals with 'symptoms suggestive of malaria,' implying a potential progression between these states within an infected host.

### AsymptomaticPatentInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals remain

### SubpatentInfectious->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with subpatent malaria

### SubpatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with a subpatent malaria infection can progress to develop symptomatic malaria.
- **Reasoning:** This transition represents the natural progression of malaria infection within an individual from an asymptomatic or subpatent stage to a symptomatic stage.

### SubpatentInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of

### TreatedInfectious->ProtectedByProphylaxis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have been

### TreatedInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who

### ProtectedByProphylaxis->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who were protected by

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible mosquitoes

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** This flow represents the

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
- **Primary name:** Protected by Prophylaxis

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Susceptible Mosquitoes

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Exposed Mosquitoes

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** Infectious Mosquitoes
- **Reasoning:** The text explicitly mentions preventing onward transmission to mosquitoes and the lifespan of malaria vectors, indicating that mosquitoes become infectious and are a critical part of the transmission cycle.

### Susceptible->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected

### Susceptible->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected with the malaria parasite (Plasmodium falciparum) through mosquito bites, entering an asymptomatic but patent infectious state where they carry parasites without showing clinical symptoms.
- **Reasoning:** The text explicitly mentions 'asymptomatic parasite carriers' who contribute to the 'infectious reservoir in humans,' indicating that susceptible individuals can become infected and remain asymptomatic while still being infectious.

### SymptomaticInfectious->TreatedInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Symptomatic infectious individuals

### SymptomaticInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals recover from symptomatic malaria

### SymptomaticInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals remain

### AsymptomaticPatentInfectious->SubpatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Asymptomatic individuals with

### AsymptomaticPatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an asymptomatic

### AsymptomaticPatentInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals remain

### SubpatentInfectious->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Recovery from subpatent malaria infection, either naturally or through treatment, leading to a return to the susceptible state.
- **Reasoning:** The excerpt describes antimalarial drugs providing a curative regimen, which would clear subpatent infections and allow individuals to return to the susceptible state.

### SubpatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression

### SubpatentInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals with a low,

### TreatedInfectious->ProtectedByProphylaxis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who have been treated

### TreatedInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals who received treatment

### ProtectedByProphylaxis->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in the ProtectedByProphylaxis compartment lose the protective effect of antimalarial drugs over time, returning to the Susceptible compartment.
- **Reasoning:** The protective effect of antimalarial prophylaxis is not permanent and wanes over time, leading individuals to eventually

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible mosquitoes become exposed

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The maturation of the Plasmodium parasite within the mosquito vector, rendering the mosquito capable of transmitting malaria.
- **Reasoning:** This flow represents the extrinsic incubation period (EIP) where exposed mosquitoes transition to an infectious state after the parasite develops, which is a time-dependent rate process.

## 6. Fill validation (vs gold standard)
- Parameters compared: **16**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **16**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.9%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| susceptibletosymptomaticinfectionrate | 0.0001 | 0.04 | 99.75% | poor |
| susceptibletoasymptomaticinfectionrate | 0.0001 | 0.03 | 99.67% | poor |
| treatmentrate | 0.0001 | 0.35 | 99.97% | poor |
| symptomatictoasymptomaticrate | 0.5 | 0.2 | 150.0% | poor |
| superinfectiontosymptomaticrate | 0.0001 | 0.025 | 99.6% | poor |
| superinfectiontoasymptomaticrate | 0.0001 | 0.02 | 99.5% | poor |
| patenttosubpatentrate | 0.0001 | 0.1 | 99.9% | poor |
| subpatentclearancerate | 0.0001 | 0.05 | 99.8% | poor |
| treatmentrecoverytoprotectionrate | 0.0001 | 0.25 | 99.96% | poor |
| treatmentfailurerate | 0.0001 | 0.05 | 99.8% | poor |
| protectionlossrate | 0.0001 | 0.08 | 99.87% | poor |
| humantomosquitotransmissionrate_d | 10000.0 | 0.2 | 4999900.0% | poor |
| humantomosquitotransmissionrate_a | 10000.0 | 0.067 | 14925273.13% | poor |
| humantomosquitotransmissionrate_u | 10000.0 | 0.012 | 83333233.33% | poor |
| humantomosquitotransmissionrate_t | 10000.0 | 0.04 | 24999900.0% | poor |
| mosquitolatentprogressionrate | 0.0001 | 0.1 | 99.9% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **10**
- Precision **0.9** | Recall **0.8889** | F1 **0.8944**
### Flows
- Gold count: **16** | Candidate: **18**
- Precision **0.7778** | Recall **0.5625** | F1 **0.6528**
