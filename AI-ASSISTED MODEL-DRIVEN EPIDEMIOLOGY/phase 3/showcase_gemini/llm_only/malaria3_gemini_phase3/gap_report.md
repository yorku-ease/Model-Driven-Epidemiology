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
- After fills gaps (re-detected): **30**
- Delta (before - after): **10**
- Delta missing parameters: **5**
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
| **Gap reduction** | 25.0% | 30% |
| **Reference agreement** | 93.3% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **55.5/100** | — |

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
- Filled via **inference**: 70
- **Flagged** for manual review: 0

### symptomaticinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** symptomaticinfectious
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### subpatentinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** subpatentinfectious
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### treatedinfectious (missing_compartments)
- **Source:** inference
- **Primary name:** treatedinfectious
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### susceptiblemosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** susceptiblemosquitoes
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### exposedmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** exposedmosquitoes
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### infectiousmosquitoes (missing_compartments)
- **Source:** inference
- **Primary name:** infectiousmosquitoes
- **Reasoning:** LLM unavailable; using expected label as placeholder.

### susceptibletosymptomaticinfectionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### susceptibletoasymptomaticinfectionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### treatmentrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### symptomatictoasymptomaticrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### superinfectiontosymptomaticrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### superinfectiontoasymptomaticrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### patenttosubpatentrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### subpatentclearancerate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### treatmentfailurerate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### protectionlossrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### mosquitolatentprogressionrate (missing_parameters)
- **Source:** inference
- **Value:** None 
- **Reasoning:** No default in library; manual lookup required.
- **Confidence:** LOW

### Susceptible->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for Susceptible->SymptomaticInfectious
- **Reasoning:** LLM unavailable.

### SymptomaticInfectious->TreatedInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for SymptomaticInfectious->TreatedInfectious
- **Reasoning:** LLM unavailable.

### SymptomaticInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for SymptomaticInfectious->AsymptomaticPatentInfectious
- **Reasoning:** LLM unavailable.

### SymptomaticInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for SymptomaticInfectious->SymptomaticInfectious
- **Reasoning:** LLM unavailable.

### AsymptomaticPatentInfectious->SubpatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for AsymptomaticPatentInfectious->SubpatentInfectious
- **Reasoning:** LLM unavailable.

### AsymptomaticPatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for AsymptomaticPatentInfectious->SymptomaticInfectious
- **Reasoning:** LLM unavailable.

### SubpatentInfectious->Susceptible (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for SubpatentInfectious->Susceptible
- **Reasoning:** LLM unavailable.

### SubpatentInfectious->SymptomaticInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for SubpatentInfectious->SymptomaticInfectious
- **Reasoning:** LLM unavailable.

### SubpatentInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for SubpatentInfectious->AsymptomaticPatentInfectious
- **Reasoning:** LLM unavailable.

### TreatedInfectious->ProtectedByProphylaxis (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for TreatedInfectious->ProtectedByProphylaxis
- **Reasoning:** LLM unavailable.

### TreatedInfectious->AsymptomaticPatentInfectious (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for TreatedInfectious->AsymptomaticPatentInfectious
- **Reasoning:** LLM unavailable.

### SusceptibleMosquitoes->ExposedMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for SusceptibleMosquitoes->ExposedMosquitoes
- **Reasoning:** LLM unavailable.

### ExposedMosquitoes->InfectiousMosquitoes (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Suggested transition for ExposedMosquitoes->InfectiousMosquitoes
- **Reasoning:** LLM unavailable.

## 6. Fill validation (vs gold standard)
- Parameters compared: **0**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| susceptibletosymptomaticinfectionrate | None | 0.040 | — | no_fill |
| susceptibletoasymptomaticinfectionrate | None | 0.030 | — | no_fill |
| treatmentrate | None | 0.35 | — | no_fill |
| symptomatictoasymptomaticrate | None | 0.20 | — | no_fill |
| superinfectiontosymptomaticrate | None | 0.025 | — | no_fill |
| superinfectiontoasymptomaticrate | None | 0.020 | — | no_fill |
| patenttosubpatentrate | None | 0.10 | — | no_fill |
| subpatentclearancerate | None | 0.05 | — | no_fill |
| treatmentfailurerate | None | 0.05 | — | no_fill |
| protectionlossrate | None | 0.08 | — | no_fill |
| mosquitolatentprogressionrate | None | 0.10 | — | no_fill |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **11**
- Precision **0.9091** | Recall **1.0** | F1 **0.9524**
### Flows
- Gold count: **16** | Candidate: **25**
- Precision **0.84** | Recall **1.0** | F1 **0.913**
