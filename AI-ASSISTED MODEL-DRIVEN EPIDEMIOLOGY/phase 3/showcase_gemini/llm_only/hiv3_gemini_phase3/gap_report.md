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
| **Reference agreement** | 27.3% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **38.2/100** | — |

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
- **Primary name:** AIDS Stage

### untreatedstagei3 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### untreatedstagei4 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### artstagea1 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage

### artstagea2 (missing_compartments)
- **Source:** inference
- **Primary name:** Acute Infection

### artstagea3 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage
- **Reasoning:** The model

### artstagea4 (missing_compartments)
- **Source:** inference
- **Primary name:** Latent Stage

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS
- **Reasoning:** The

### Susceptible->UntreatedStageI1 (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Infection of a susceptible individual with HIV, leading to entry into the untreated Stage I of the disease.
- **Reasoning:** The paper explicitly mentions probabilities of infection per unit time step through partners, indicating transmission via contact.

### UntreatedStageI1->UntreatedStageI2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of

### UntreatedStageI1->ARTStageA1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Initiation of Antiretroviral Therapy (ART) for individuals in Untreated Stage I1.
- **Reasoning:** The transition from an untreated stage to an ART stage represents individuals starting treatment, which is typically modeled as a rate of treatment initiation rather than a contact-driven event.

### UntreatedStageI2->UntreatedStageI3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of

### UntreatedStageI2->ARTStageA2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in Untreated

### UntreatedStageI3->UntreatedStageI4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV

### UntreatedStageI3->ARTStageA3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Initiation of antiretro

### UntreatedStageI4->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in UntreatedStageI4 die and are removed

### UntreatedStageI4->ARTStageA4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in untreated HIV

### ARTStageA1->ARTStageA2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an

### ARTStageA1->UntreatedStageI1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Cessation of

### ARTStageA2->ARTStageA3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an individual from ART stage A2 to ART stage A3, indicating an improvement in treatment response or health status due to ongoing antiretroviral therapy.
- **Reasoning:** This transition describes an internal progression within an individual's treatment journey, driven by the efficacy of ART rather than direct contact with others.

### ARTStageA2->UntreatedStageI2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals on antiretroviral

### ARTStageA3->ARTStageA4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression from ART

### ARTStageA3->UntreatedStageI3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Cessation of

### ARTStageA4->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in ART stage A4 dying.
- **Reasoning:** The paper explicitly mentions a Weibull survival distribution, indicating that individuals can be removed from the population due to death.

### ARTStageA4->UntreatedStageI4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals discontinuing Antire

### untreatedstagei1 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### untreatedstagei2 (missing_compartments)
- **Source:** inference
- **Primary name:** HIV Stages
- **Reasoning:** The

### untreatedstagei3 (missing_compartments)
- **Source:** inference
- **Primary name:** HIV Stage I

### untreatedstagei4 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage
- **Reasoning:** The

### artstagea1 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage

### artstagea2 (missing_compartments)
- **Source:** inference
- **Primary name:** Late Stage HIV

### artstagea3 (missing_compartments)
- **Source:** inference
- **Primary name:** Disease Stages
- **Reasoning:** The

### artstagea4 (missing_compartments)
- **Source:** inference
- **Primary name:** ART Stages
- **Reasoning:** The

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS

### Susceptible->UntreatedStageI1 (missing_flows)
- **Source:** inference
- **Flow type:** ContactFlow
- **Description:** Infection of a susceptible individual by an infected partner, leading to the Untreated Stage I HIV infection.
- **Reasoning:** The excerpt describes probabilities of an index case infecting partners, which is characteristic of contact-based disease transmission.

### UntreatedStageI1->UntreatedStageI2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of HIV

### UntreatedStageI1->ARTStageA1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in Untreated Stage

### UntreatedStageI2->UntreatedStageI3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an untreated individual from HIV disease stage I2 to stage I

### UntreatedStageI2->ARTStageA2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in Untreated Stage

### UntreatedStageI3->UntreatedStageI4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression

### UntreatedStageI3->ARTStageA3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Initiation of

### UntreatedStageI4->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals in Untreated

### UntreatedStageI4->ARTStageA4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Initiation of antiretro

### ARTStageA1->ARTStageA2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an individual

### ARTStageA1->UntreatedStageI1 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals on Antiretro

### ARTStageA2->ARTStageA3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an individual from ART Stage A2 to ART Stage A3, likely representing a change in treatment status, adherence, or duration on antiretroviral therapy.
- **Reasoning:** Transitions between defined treatment stages (ART stages) are typically modeled as

### ARTStageA2->UntreatedStageI2 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Discontinuation of

### ARTStageA3->ARTStageA4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of an individual living with HIV from ART stage 3 to ART stage 4, representing advancement in their antiretroviral therapy regimen or clinical status.
- **Reasoning:** The transition between

### ARTStageA3->UntreatedStageI3 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Discontinuation or failure

### ARTStageA4->Removed (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Mortality of individuals

### ARTStageA4->UntreatedStageI4 (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals discontinue Antiretro

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **12**
- Precision **0.1667** | Recall **0.5** | F1 **0.25**
### Flows
- Gold count: **17** | Candidate: **5**
- Precision **0.4** | Recall **0.2353** | F1 **0.2963**
