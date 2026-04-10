# Phase 3 Gap Analysis Report

**Disease / context:** hiv1

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **9**
- Missing compartments: 2
- Missing parameters: 0
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 7
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 7
- Extra flows (not in gold standard): 4

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **9**
- After fills gaps (re-detected): **1**
- Delta (before - after): **8**
- Delta missing parameters: **0**
- Delta missing compartments: **1**
- Delta missing flows: **7**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **treated with art** — Present in gold standard but not in extracted model. (severity: high)
- **recruitmentsource** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Untreated infected homosexual men->Treated with ART** — Present in gold standard but not in extracted model (or flows list).
- **Untreated infected homosexual men->People living with AIDS** — Present in gold standard but not in extracted model (or flows list).
- **Untreated infected women->Treated with ART** — Present in gold standard but not in extracted model (or flows list).
- **Untreated infected women->People living with AIDS** — Present in gold standard but not in extracted model (or flows list).
- **Untreated infected heterosexual men->Treated with ART** — Present in gold standard but not in extracted model (or flows list).
- **Untreated infected heterosexual men->People living with AIDS** — Present in gold standard but not in extracted model (or flows list).
- **Treated with ART->People living with AIDS** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: treated with antiretrovirals
- Extra parameters: b_s, b_h, b_hw, b_hm, c_s, c_hw, c_hm

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 88.9% | 30% |
| **Reference agreement** | 86.7% | 30% |
| **Fill traceability** | 50.0% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **82.7/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 2 | 0 | 7 | 9 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 7 | 4 | 12 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 10
- **Flagged** for manual review: 0

### treated with art (missing_compartments)
- **Source:** inference
- **Primary name:** Treated with ART
- **Reasoning:** The excerpt extensively discusses antiretroviral therapy (ART) as a crucial intervention for HIV, implying a need for a compartment representing individuals undergoing this treatment.

### recruitmentsource (missing_compartments)
- **Source:** inference
- **Primary name:** Recruitment Source
- **Reasoning:** The

### Untreated infected homosexual men->Treated with ART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** The rate at which untreated HIV-infected homosexual men initiate antiretroviral therapy (ART).
- **Reasoning:** The excerpt describes ART as a treatment that reduces viral replication, morbidity, and mortality for HIV-infected individuals, indicating a transition from an untreated to a treated state.

### Untreated infected homosexual men->People living with AIDS (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of untreated HIV infection to AIDS.
- **Reasoning:** In the absence of antiretroviral therapy, untreated HIV infection naturally progresses to AIDS, which is an advanced stage of the disease associated with increased morbidity and mortality.

### Untreated infected women->Treated with ART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Untreated HIV-infected women initiating antiretroviral therapy (ART).
- **Reasoning:** The excerpt describes ART as the 'best option' for viral suppression and a reduction in mortality, indicating a transition from an untreated to a treated state for infected individuals.

### Untreated infected women->People living with AIDS (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of

### Untreated infected heterosexual men->Treated with ART (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Untreated infected heterosexual men initiating antiretroviral therapy (ART).
- **Reasoning:** The excerpt highlights antiretroviral therapy (ART) as the best treatment option for HIV, indicating a transition for infected individuals to a treated state.

### Untreated infected heterosexual men->People living with AIDS (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Progression of untreated

### Treated with ART->People living with AIDS (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Individuals receiving antire

### recruitmentsource (missing_compartments)
- **Source:** inference
- **Primary name:** New Infections
- **Reasoning:** The excerpt

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **11**
- Precision **0.8182** | Recall **1.0** | F1 **0.9**
### Flows
- Gold count: **10** | Candidate: **14**
- Precision **0.7143** | Recall **1.0** | F1 **0.8333**
