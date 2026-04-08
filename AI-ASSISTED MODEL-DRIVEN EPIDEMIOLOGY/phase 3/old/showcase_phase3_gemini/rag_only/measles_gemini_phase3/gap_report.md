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
| **Fill traceability** | 100.0% | 20% |
| **Parameter accuracy** | 50.0% | 15% |
| **Structural integrity** | 50.0%  (24→12 errors) | 15% |
| **→ Composite** | **84.7/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 6 | 0 | +6 |
| High | 1 | 0 | +1 |
| Medium | 17 | 12 | +5 |
| **Total** | **24** | **12** | **+12** |

**7 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible Children -> ContactFlow | Redirected Susceptible Children ContactFlow target from //@compartments.3 to //@compartments.9 (Expo |
| `self_referential_flow` | Un-monitored Vaccinated Children -> ContactFlow | Redirected Un-monitored Vaccinated Children ContactFlow target from //@compartments.3 to //@compartm |
| `self_referential_flow` | Susceptible Adults -> ContactFlow | Redirected Susceptible Adults ContactFlow target from //@compartments.9 to //@compartments.3 (Expose |
| `self_referential_flow` | Un-monitored Vaccinated Adults -> ContactFlow | Redirected Un-monitored Vaccinated Adults ContactFlow target from //@compartments.9 to //@compartmen |
| `zero_population_all` | all_compartments | Set Susceptible Children population=1000 |
| `uniform_parameter_collapse` | all_flows | Reassigned parameters for 10 flow(s) using semantic matching |
| `orphaned_parameters` | φ | Wired φ (//@parameters.17) to Immune Children → Immune Adults flow |

**12 structural error(s) remaining after repair:**

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
| `orphaned_parameters` | ξu | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 0 | 2 | 4 | 6 |
| **Extra in model** | Model items not in reference (noise/convention) | 0 | 6 | 1 | 7 |

## 5. Gap filling results
- Filled via **RAG**: 6
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 0

### ξu (missing_parameters)
- **Source:** rag
- **Value:** 0.0087 1/week
- **Description:** Un-monitored child vaccination rate (xU)
- **From papers:** p2_malaria_llm_gemini_20260321_233636, p2_baseline_measles, p2_cholera_llm_claude_20260321_234449

### φ (missing_parameters)
- **Source:** rag
- **Value:** 0.0 
- **From papers:** p2_dengue_llm_claude_20260321_234654, p2_baseline_zika, p2_malaria_llm_claude_20260321_235246

### Exposed children->Exposed adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Infectious children->Infectious adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Immune children->Immune adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 4 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible adults->Vaccinated adults (catch-up/monitored) (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- **Text evidence:** 2 chunk(s)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 6. Fill validation (vs gold standard)
- Parameters compared: **2**
- Exact match (<1% error): **1**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **1**
- **Accuracy (exact+close)**: **50.0%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| ξu | 0.0087 | 0.0087 | 0.0% | exact |
| φ | 0.0 | 0.00128 | 100.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **12** | Candidate: **12**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **20** | Candidate: **21**
- Precision **0.9524** | Recall **1.0** | F1 **0.9756**
