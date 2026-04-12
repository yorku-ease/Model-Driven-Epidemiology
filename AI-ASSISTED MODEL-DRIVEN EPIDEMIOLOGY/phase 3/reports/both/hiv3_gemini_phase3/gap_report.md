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
| **Reference agreement** | 57.5% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **47.3/100** | — |

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
- **Primary name:** AIDS Phase
- **Reasoning:** The model

### untreatedstagei3 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I HIV

### untreatedstagei4 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I HIV

### artstagea1 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage
- **Reasoning:** The model

### artstagea2 (missing_compartments)
- **Source:** inference
- **Primary name:** ART Stage
- **Reasoning:** The

### artstagea3 (missing_compartments)
- **Source:** inference
- **Primary name:** Disease Stages

### artstagea4 (missing_compartments)
- **Source:** inference
- **Primary name:** Acute HIV Infection
- **Reasoning:** The

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS
- **Reasoning:** The model

### Susceptible->UntreatedStageI1 (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals become infected with HIV and transition into the first untreated stage (UntreatedStageI1) through contact with an infectious partner.
- **Reasoning:** The excerpt explicitly mentions 'probabilities...that the index case infects any one partner' and details 'partners and concurrency', indicating transmission occurs via contact.

### UntreatedStageI1->UntreatedStageI2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV

### UntreatedStageI1->ARTStageA1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in an untreated stage of HIV infection initiate Antiretroviral Therapy (ART) and transition to a treated stage.
- **Reasoning:** The transition from an untreated stage to an ART-treated stage represents the initiation of medical

### UntreatedStageI2->UntreatedStageI3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression

### UntreatedStageI2->ARTStageA2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals initiating antiretroviral therapy (ART

### UntreatedStageI3->UntreatedStageI4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an untreated HIV-infected individual from disease stage I3 to stage I4.
- **Reasoning:** The transition between consecutive untreated disease stages (UntreatedStageI3 to UntreatedStageI4) represents

### UntreatedStageI3->ARTStageA3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in Untreated

### UntreatedStageI4->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in

### UntreatedStageI4->ARTStageA4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in Unt

### ARTStageA1->ARTStageA2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression from one stage

### ARTStageA1->UntreatedStageI1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals on antiretroviral

### ARTStageA2->ARTStageA3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression

### ARTStageA2->UntreatedStageI2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals on Antire

### ARTStageA3->ARTStageA4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an

### ARTStageA3->UntreatedStageI3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals discontinuing antiretroviral therapy (ART) and transitioning to an untreated disease stage.
- **Reasoning:** This transition represents the rate at which individuals cease ART and revert to an untreated state, which is typically modeled as a rate rather than a contact-driven event.

### ARTStageA4->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality of individuals

### ARTStageA4->UntreatedStageI4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals on ART in stage A4 cease treatment or experience treatment failure, transitioning to an untreated stage I4.
- **Reasoning:** This transition represents an internal change in an individual's treatment status and disease progression, independent of contact with others.

### untreatedstagei1 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### untreatedstagei2 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage

### untreatedstagei3 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### untreatedstagei4 (missing_compartments)
- **Source:** inference
- **Primary name:** Latent Infection
- **Reasoning:** The

### artstagea1 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage

### artstagea2 (missing_compartments)
- **Source:** inference
- **Primary name:** Latent Stage
- **Reasoning:** The

### artstagea3 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage

### artstagea4 (missing_compartments)
- **Source:** inference
- **Primary name:** Disease Stages
- **Reasoning:** The model

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS
- **Reasoning:** The

### Susceptible->UntreatedStageI1 (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Susceptible individuals acquire HIV infection and transition to the UntreatedStageI1 through sexual contact with an infectious partner.
- **Reasoning:** The excerpt details 'Partners and concurrency' and 'probabilities... that the index case infects any one partner', which are hallmarks of contact-based transmission for HIV.

### UntreatedStageI1->UntreatedStageI2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV infection from an earlier untreated stage (UntreatedStageI1) to a later untreated stage (UntreatedStageI2) within an individual.

### UntreatedStageI1->ARTStageA1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Initiation of antiretroviral therapy (ART) for individuals in an untreated stage of HIV infection.

### UntreatedStageI2->UntreatedStageI3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV disease from untreated stage I2 to untreated stage I3.
- **Reasoning:** This flow represents the natural progression of HIV disease within an untreated individual, moving from one clinical stage to the next based on disease pathophysiology.

### UntreatedStageI2->ARTStageA2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in

### UntreatedStageI3->UntreatedStageI4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV infection from untreated clinical stage I3 to untreated clinical stage I4.
- **Reasoning:** The model describes varying infectivity with time and a survival distribution, which inherently implies progression through different stages of HIV infection within an untreated individual.

### UntreatedStageI3->ARTStageA3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Initiation of antire

### UntreatedStageI4->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in Untreated

### UntreatedStageI4->ARTStageA4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in Untreated Stage I with CD4 category 4 initiate Antiretroviral Therapy (ART) and transition to ART Stage A with CD4 category 4.
- **Reasoning:** The transition from an untreated stage to an ART-treated stage represents the initiation of medical treatment, which is a rate-based process rather than a contact-based interaction.

### ARTStageA1->ARTStageA2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an individual

### ARTStageA1->UntreatedStageI1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Cessation of antire

### ARTStageA2->ARTStageA3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of individuals from

### ARTStageA2->UntreatedStageI2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Cessation of antire

### ARTStageA3->ARTStageA4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression from ART stage

### ARTStageA3->UntreatedStageI3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Cessation of antire

### ARTStageA4->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals on ART in stage A4 are removed from the model population, likely due to death

### ARTStageA4->UntreatedStageI4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals on

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **11**
- Precision **0.2727** | Recall **0.9** | F1 **0.4186**
### Flows
- Gold count: **17** | Candidate: **8**
- Precision **0.625** | Recall **0.8824** | F1 **0.7317**
