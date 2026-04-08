# Phase 3 Gap Analysis Report

**Disease / context:** measles

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **6**
- Missing compartments: 0
- Missing parameters: 2
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 4
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 6
- Extra flows (not in gold standard): 1

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **6**
- After fills gaps (re-detected): **0**
- Delta (before - after): **6**
- Delta missing parameters: **2**
- Delta missing compartments: **0**
- Delta missing flows: **4**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- None

## 3b. Missing flows (gold vs extraction/XML)
- **Exposed children->Exposed adults** — Present in gold standard but not in extracted model (or flows list).
- **Infectious children->Infectious adults** — Present in gold standard but not in extracted model (or flows list).
- **Immune children->Immune adults** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible adults->Vaccinated adults (catch-up/monitored)** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **ξu** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: xu, xa, bc, ba, j, hu

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 25% |
| **Reference agreement** | 98.8% | 25% |
| **Fill traceability** | 0.0% | 20% |
| **Parameter accuracy** | 0.0% | 15% |
| **Structural integrity** | 54.2%  (24→11 errors) | 15% |
| **→ Composite** | **57.8/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 6 | 0 | +6 |
| High | 1 | 0 | +1 |
| Medium | 17 | 11 | +6 |
| **Total** | **24** | **11** | **+13** |

**8 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible Children -> ContactFlow | Redirected Susceptible Children ContactFlow target from //@compartments.3 to //@compartments.9 (Expo |
| `self_referential_flow` | Un-monitored Vaccinated Children -> ContactFlow | Redirected Un-monitored Vaccinated Children ContactFlow target from //@compartments.3 to //@compartm |
| `self_referential_flow` | Susceptible Adults -> ContactFlow | Redirected Susceptible Adults ContactFlow target from //@compartments.9 to //@compartments.3 (Expose |
| `self_referential_flow` | Un-monitored Vaccinated Adults -> ContactFlow | Redirected Un-monitored Vaccinated Adults ContactFlow target from //@compartments.9 to //@compartmen |
| `zero_population_all` | all_compartments | Set Susceptible Children population=1000 |
| `uniform_parameter_collapse` | all_flows | Reassigned parameters for 10 flow(s) using semantic matching |
| `orphaned_parameters` | ξu | Wired ξu (//@parameters.16) to Immune Children → Immune Adults flow |
| `orphaned_parameters` | φ | Wired φ (//@parameters.17) to Susceptible Adults → Vaccinated Adults flow |

**11 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `orphaned_parameters` | xU | **medium** |
| `orphaned_parameters` | xM | **medium** |
| `orphaned_parameters` | xA | **medium** |
| `orphaned_parameters` | m | **medium** |
| `orphaned_parameters` | g | **medium** |
| `orphaned_parameters` | j | **medium** |
| `orphaned_parameters` | hA | **medium** |
| `orphaned_parameters` | hU | **medium** |
| `orphaned_parameters` | hM | **medium** |
| `orphaned_parameters` | d | **medium** |
| `orphaned_parameters` | q | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 2 | 4 | 6 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 6 | 1 | 7 |

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 6
- **Flagged** for manual review: 0

### ξu (missing_parameters)
- **Source:** inference
- **Value:** 0.05 per year
- **Reasoning:** For measles age-structured models, a small per-capita rate on the order of a few percent per year is plausible for a monitored/unvaccinated-related transition parameter, reflecting gradual movement over time rather than rapid change. A default of 0.05/year lies within a typical epidemiological range for slow behavioral or programmatic transitions.
- **Confidence:** LOW

### φ (missing_parameters)
- **Source:** inference
- **Value:** 0.8 dimensionless
- **Reasoning:** For measles vaccination models, a monitored vaccination parameter is typically high because measles vaccines are highly effective and programs aim for strong uptake/coverage. A plausible default is around 0.8, representing substantial but not perfect vaccination success/acceptance in the population.
- **Confidence:** LOW

### Exposed children->Exposed adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Ageing progression of measles-exposed children into the exposed adult class as they move from the 0–15 year age group to the above-15 year age group while still in the latent (exposed) state.
- **Reasoning:** The model stratifies the population by age, so individuals in the exposed child compartment must transition to the exposed adult compartment when they age into the next group.

### Infectious children->Infectious adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Aging progression of infected individuals from the child age class into the adult age class while remaining infectious.
- **Reasoning:** The model splits the population into children (0–15 years) and adults (>15 years), so infectious children naturally transition into infectious adults as they age.

### Immune children->Immune adults (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Aging/maturation of immune children into the immune adult class as children move from the 0–15 age group to the >15 age group while remaining immune.
- **Reasoning:** This is an age-structured model, so immune children naturally transition to immune adults through aging rather than through disease contact.

### Susceptible adults->Vaccinated adults (catch-up/monitored) (missing_flows)
- **Source:** inference
- **Flow type:** RateFlow
- **Description:** Catch-up/monitored vaccination of susceptible adults into the vaccinated adult class, representing adults who receive measles immunization through a supervised or catch-up program.
- **Reasoning:** This is a vaccination process moving individuals from susceptibility to immunity, so it is a rate-based transition rather than a contact-driven infection flow.

## 6. Fill validation (vs gold standard)
- Parameters compared: **2**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **2**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **62400.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| ξu | 0.05 | 0.0087 | 474.71% | poor |
| φ | 0.8 | 0.00128 | 62400.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **12** | Candidate: **12**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **20** | Candidate: **21**
- Precision **0.9524** | Recall **1.0** | F1 **0.9756**
