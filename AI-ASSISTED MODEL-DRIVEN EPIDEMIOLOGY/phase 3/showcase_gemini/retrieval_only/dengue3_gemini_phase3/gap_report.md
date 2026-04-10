# Phase 3 Gap Analysis Report

**Disease / context:** dengue3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **26**
- Missing compartments: 9
- Missing parameters: 8
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 9
- Extra compartments (not in gold standard): 8
- Extra parameters (not in gold standard): 13
- Extra flows (not in gold standard): 6

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **26**
- After fills gaps (re-detected): **18**
- Delta (before - after): **8**
- Delta missing parameters: **8**
- Delta missing compartments: **0**
- Delta missing flows: **0**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **primarysusceptible** — Present in gold standard but not in extracted model. (severity: high)
- **primaryexposed** — Present in gold standard but not in extracted model. (severity: high)
- **primaryinfectious** — Present in gold standard but not in extracted model. (severity: high)
- **postprimaryimmune** — Present in gold standard but not in extracted model. (severity: high)
- **vaccinatedsilentinfection** — Present in gold standard but not in extracted model. (severity: high)
- **secondarysusceptible** — Present in gold standard but not in extracted model. (severity: high)
- **postvaccinationsusceptible** — Present in gold standard but not in extracted model. (severity: high)
- **secondaryinfectious** — Present in gold standard but not in extracted model. (severity: high)
- **postsecondaryimmune** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **PrimarySusceptible->PrimaryExposed** — Present in gold standard but not in extracted model (or flows list).
- **PrimarySusceptible->VaccinatedSilentInfection** — Present in gold standard but not in extracted model (or flows list).
- **PrimaryExposed->PrimaryInfectious** — Present in gold standard but not in extracted model (or flows list).
- **PrimaryInfectious->PostPrimaryImmune** — Present in gold standard but not in extracted model (or flows list).
- **PostPrimaryImmune->SecondarySusceptible** — Present in gold standard but not in extracted model (or flows list).
- **VaccinatedSilentInfection->PostVaccinationSusceptible** — Present in gold standard but not in extracted model (or flows list).
- **SecondarySusceptible->SecondaryInfectious** — Present in gold standard but not in extracted model (or flows list).
- **PostVaccinationSusceptible->SecondaryInfectious** — Present in gold standard but not in extracted model (or flows list).
- **SecondaryInfectious->PostSecondaryImmune** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **primarytransmissionrate** — Present in gold standard but not in extracted model.
- **incubationrate** — Present in gold standard but not in extracted model.
- **recoveryrate** — Present in gold standard but not in extracted model.
- **routinevaccinationrate** — Present in gold standard but not in extracted model.
- **naturalcrossprotectionwaningrate** — Present in gold standard but not in extracted model.
- **vaccinecrossprotectionwaningrate** — Present in gold standard but not in extracted model.
- **secondarytransmissionrate** — Present in gold standard but not in extracted model.
- **postvaccinationtransmissionrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: susceptible humans, infectious humans (primary), recovered humans (primary), infectious humans (secondary), recovered humans (post-secondary), susceptible mosquitoes, exposed mosquitoes, infectious mosquitoes
- Extra parameters: β_hv, β_vh, γ_h, σ_v, μ_h, μ_v, case fatality rate, default vaccine coverage, default vaccination age, discount rate, dalys per symptomatic dengue case, dalys per severe dengue case, cost per hospitalised case (public payer, latin america)

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 30.8% | 30% |
| **Reference agreement** | 0.0% | 30% |
| **Fill traceability** | 18.2% | 20% |
| **Parameter accuracy** | 0.0% | 20% |
| **→ Composite** | **12.9/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 9 | 8 | 9 | 26 |
| **Extra in model** | Model items not in reference (noise/convention) | 8 | 13 | 6 | 27 |

## 5. Gap filling results
- Filled via **RAG**: 8
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 36

### primarysusceptible (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### primaryexposed (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### primaryinfectious (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### postprimaryimmune (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### vaccinatedsilentinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### secondarysusceptible (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### postvaccinationsusceptible (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### secondaryinfectious (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### postsecondaryimmune (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### primarytransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### incubationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### recoveryrate (missing_parameters)
- **Source:** rag
- **Value:** 0.5-1 day^-1
- **Description:** Rate of exposure to contaminated water (contact rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### routinevaccinationrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### naturalcrossprotectionwaningrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### vaccinecrossprotectionwaningrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### secondarytransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### postvaccinationtransmissionrate (missing_parameters)
- **Source:** rag
- **Value:** 0.0001-0.001 day^-1
- **Description:** Human birth and death rate (susceptible renewal rate)
- **From papers:** p2_cholera3_llm_claude_20260407_213639, p2_cholera3_llm_gemini_20260407_204240

### PrimarySusceptible->PrimaryExposed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### PrimarySusceptible->VaccinatedSilentInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### PrimaryExposed->PrimaryInfectious (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### PrimaryInfectious->PostPrimaryImmune (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### PostPrimaryImmune->SecondarySusceptible (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### VaccinatedSilentInfection->PostVaccinationSusceptible (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### SecondarySusceptible->SecondaryInfectious (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### PostVaccinationSusceptible->SecondaryInfectious (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### SecondaryInfectious->PostSecondaryImmune (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### primarysusceptible (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### primaryexposed (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### primaryinfectious (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### postprimaryimmune (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### vaccinatedsilentinfection (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### secondarysusceptible (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### postvaccinationsusceptible (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### secondaryinfectious (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### postsecondaryimmune (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### PrimarySusceptible->PrimaryExposed (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### PrimarySusceptible->VaccinatedSilentInfection (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### PrimaryExposed->PrimaryInfectious (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### PrimaryInfectious->PostPrimaryImmune (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### PostPrimaryImmune->SecondarySusceptible (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### VaccinatedSilentInfection->PostVaccinationSusceptible (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### SecondarySusceptible->SecondaryInfectious (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### PostVaccinationSusceptible->SecondaryInfectious (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

### SecondaryInfectious->PostSecondaryImmune (missing_flows)
- **Source:** flagged
- **Action:** manual_review — Could not fill flows gap automatically.

## 6. Fill validation (vs gold standard)
- Parameters compared: **8**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **8**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **99.96%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| primarytransmissionrate | 0.0001 | 0.26 | 99.96% | poor |
| incubationrate | 0.0001 | 0.18 | 99.94% | poor |
| recoveryrate | 0.5 | 0.12 | 316.67% | poor |
| routinevaccinationrate | 0.0001 | 0.05 | 99.8% | poor |
| naturalcrossprotectionwaningrate | 0.0001 | 0.02 | 99.5% | poor |
| vaccinecrossprotectionwaningrate | 0.0001 | 0.03 | 99.67% | poor |
| secondarytransmissionrate | 0.0001 | 0.3 | 99.97% | poor |
| postvaccinationtransmissionrate | 0.0001 | 0.24 | 99.96% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **9** | Candidate: **8**
- Precision **0.0** | Recall **0.0** | F1 **0.0**
### Flows
- Gold count: **9** | Candidate: **6**
- Precision **0.0** | Recall **0.0** | F1 **0.0**
