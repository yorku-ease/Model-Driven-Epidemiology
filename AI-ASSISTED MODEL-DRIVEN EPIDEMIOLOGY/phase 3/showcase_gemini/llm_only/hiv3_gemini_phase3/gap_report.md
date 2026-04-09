# Phase 3 Gap Analysis Report

**Disease / context:** hiv3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **26**
- Missing compartments: 9
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 17
- Extra compartments (not in gold standard): 2
- Extra parameters (not in gold standard): 13
- Extra flows (not in gold standard): 3

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **26**
- After fills gaps (re-detected): **26**
- Delta (before - after): **0**
- Delta missing parameters: **0**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **untreatedstagei1** — Present in gold standard but not in extracted model. (severity: high)
- **untreatedstagei2** — Present in gold standard but not in extracted model. (severity: high)
- **untreatedstagei3** — Present in gold standard but not in extracted model. (severity: high)
- **untreatedstagei4** — Present in gold standard but not in extracted model. (severity: high)
- **artstagea1** — Present in gold standard but not in extracted model. (severity: high)
- **artstagea2** — Present in gold standard but not in extracted model. (severity: high)
- **artstagea3** — Present in gold standard but not in extracted model. (severity: high)
- **artstagea4** — Present in gold standard but not in extracted model. (severity: high)
- **removed** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Susceptible->UntreatedStageI1** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI1->UntreatedStageI2** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI1->ARTStageA1** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI2->UntreatedStageI3** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI2->ARTStageA2** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI3->UntreatedStageI4** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI3->ARTStageA3** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI4->Removed** — Present in gold standard but not in extracted model (or flows list).
- **UntreatedStageI4->ARTStageA4** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA1->ARTStageA2** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA1->UntreatedStageI1** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA2->ARTStageA3** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA2->UntreatedStageI2** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA3->ARTStageA4** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA3->UntreatedStageI3** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA4->Removed** — Present in gold standard but not in extracted model (or flows list).
- **ARTStageA4->UntreatedStageI4** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: infectious untreated, infectious treated
- Extra parameters: δ, μ, ρ, ν, ε, γ_u, γ_t, τ, ψ, θ, ω, α, φ

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 0.0% | 30% |
| **Reference agreement** | 40.6% | 30% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **32.2/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 9 | 0 | 17 | 26 |
| **Extra in model** | Model items not in reference (noise/convention) | 2 | 13 | 3 | 18 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 52
- **Flagged** for manual review: 0

### untreatedstagei1 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### untreatedstagei2 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I HIV

### untreatedstagei3 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I HIV

### untreatedstagei4 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### artstagea1 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage

### artstagea2 (missing_compartments)
- **Source:** inference
- **Primary name:** Late Stage
- **Reasoning:** The

### artstagea3 (missing_compartments)
- **Source:** inference
- **Primary name:** Fie

### artstagea4 (missing_compartments)
- **Source:** inference
- **Primary name:** Disease Stages
- **Reasoning:** The model

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Phase
- **Reasoning:** The model

### Susceptible->UntreatedStageI1 (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected with HIV and transition to the Untreated Stage I1 through contact with infectious partners.
- **Reasoning:** The excerpt describes 'probabilities, per unit time step, that the index case infects any one partner', which is characteristic of contact-based transmission leading to infection.

### UntreatedStageI1->UntreatedStageI2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV infection

### UntreatedStageI1->ARTStageA1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in Untreated Stage I1 initiate Antiretroviral Therapy (ART) and transition to ART Stage A1.
- **Reasoning:** The transition from an untreated disease stage to an ART-treated stage represents the initiation of medical intervention, which is typically modeled as a rate of treatment uptake within a population.

### UntreatedStageI2->UntreatedStageI3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of untreated HIV infection from stage I2 to stage I3.
- **Reasoning:** Disease progression between defined stages in an untreated population is typically modeled as a rate, reflecting the natural course of the infection.

### UntreatedStageI2->ARTStageA2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Initiation of antiretro

### UntreatedStageI3->UntreatedStageI4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV

### UntreatedStageI3->ARTStageA3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in untreated

### UntreatedStageI4->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in Unt

### UntreatedStageI4->ARTStageA4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Untreated HIV-

### ARTStageA1->ARTStageA2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an individual from an initial stage of antiretroviral therapy (ARTStageA1) to a subsequent stage (ARTStageA2), reflecting changes in treatment effectiveness or patient health status over time.
- **Reasoning:** The transition between ART stages represents an internal

### ARTStageA1->UntreatedStageI1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals on Antiretroviral Therapy (ART) in stage A1 discontinue treatment and revert to an untreated state in stage I1.
- **Reasoning:** This transition represents treatment discontinuation, which is an internal progression or regression within an individual's treatment status, typically modeled as a rate.

### ARTStageA2->ARTStageA3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of individuals from

### ARTStageA2->UntreatedStageI2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Cessation of

### ARTStageA3->ARTStageA4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression from ART

### ARTStageA3->UntreatedStageI3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals discontinuing or failing antiretroviral therapy (ART) and reverting to an untreated state while maintaining their current CD4 stage.
- **Reasoning:** This transition describes a change in treatment status (from ART to untreated) within the same disease progression stage, which is typically modeled as a rate rather than a contact-dependent event.

### ARTStageA4->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality

### ARTStageA4->UntreatedStageI4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals on antire

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **11**
- Precision **0.2727** | Recall **0.6** | F1 **0.375**
### Flows
- Gold count: **17** | Candidate: **7**
- Precision **0.5714** | Recall **0.3529** | F1 **0.4364**
