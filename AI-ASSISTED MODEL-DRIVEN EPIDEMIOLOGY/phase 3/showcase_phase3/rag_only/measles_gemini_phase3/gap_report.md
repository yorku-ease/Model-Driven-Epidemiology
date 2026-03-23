# Phase 3 Gap Analysis Report

**Disease / context:** measles

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **8**
- Missing compartments: 1
- Missing parameters: 2
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 5
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 7
- Extra flows (not in gold standard): 5

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **8**
- After fills gaps (re-detected): **0**
- Delta (before - after): **8**
- Delta missing parameters: **2**
- Delta missing compartments: **1**
- Delta missing flows: **5**

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **vaccinated adults (catch-up/monitored)** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Un-monitored vaccinated children->Exposed children** — Present in gold standard but not in extracted model (or flows list).
- **Un-monitored vaccinated children->Un-monitored vaccinated adults** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible adults->Vaccinated adults (catch-up/monitored)** — Present in gold standard but not in extracted model (or flows list).
- **Un-monitored vaccinated adults->Exposed adults** — Present in gold standard but not in extracted model (or flows list).
- **Vaccinated adults (catch-up/monitored)->Immune adults** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **ξu** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: unmonitored vaccinated adults
- Extra parameters: x_u, x_a, b_c, b_a, j, h_a, h_u

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 25% |
| **Reference agreement** | 99.0% | 25% |
| **Fill traceability** | 87.5% | 20% |
| **Parameter accuracy** | 50.0% | 15% |
| **Structural integrity** | 45.8%  (24→13 errors) | 15% |
| **→ Composite** | **81.6/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 6 | 0 | +6 |
| High | 1 | 0 | +1 |
| Medium | 17 | 13 | +4 |
| **Total** | **24** | **13** | **+11** |

**7 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible Children -> ContactFlow | Redirected Susceptible Children ContactFlow target from //@compartments.3 to //@compartments.9 (Expo |
| `self_referential_flow` | Unmonitored Vaccinated Children -> ContactFlow | Redirected Unmonitored Vaccinated Children ContactFlow target from //@compartments.3 to //@compartme |
| `self_referential_flow` | Susceptible Adults -> ContactFlow | Redirected Susceptible Adults ContactFlow target from //@compartments.9 to //@compartments.3 (Expose |
| `self_referential_flow` | Unmonitored Vaccinated Adults -> ContactFlow | Redirected Unmonitored Vaccinated Adults ContactFlow target from //@compartments.9 to //@compartment |
| `zero_population_all` | all_compartments | Set Susceptible Children population=1000 |
| `uniform_parameter_collapse` | all_flows | Reassigned parameters for 12 flow(s) using semantic matching |
| `orphaned_parameters` | φ | Wired φ (//@parameters.17) to Monitored Vaccinated Children → Monitored Vaccinated Adults flow |

**13 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `orphaned_parameters` | x_U | **medium** |
| `orphaned_parameters` | x_M | **medium** |
| `orphaned_parameters` | x_A | **medium** |
| `orphaned_parameters` | m | **medium** |
| `orphaned_parameters` | g | **medium** |
| `orphaned_parameters` | j | **medium** |
| `orphaned_parameters` | e | **medium** |
| `orphaned_parameters` | h_A | **medium** |
| `orphaned_parameters` | h_U | **medium** |
| `orphaned_parameters` | h_M | **medium** |
| `orphaned_parameters` | d | **medium** |
| `orphaned_parameters` | q | **medium** |
| `orphaned_parameters` | ξu | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 1 | 2 | 5 | 8 |
| **Extra in model** | Model items not in reference (noise/convention) | 1 | 7 | 5 | 13 |

## 5. Gap filling results
- Filled via **RAG**: 7
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 1

### vaccinated adults (catch-up/monitored) (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### ξu (missing_parameters)
- **Source:** rag
- **Value:** 0.0087 1/week
- **Description:** Un-monitored child vaccination rate (xU)
- **From papers:** p2_cholera_llm_gemini_20260321_232556, p2_cholera_llm_openai_20260321_230736, p2_cholera_llm_claude_20260321_234449

### φ (missing_parameters)
- **Source:** rag
- **Value:** 0.0 
- **From papers:** p1_model_zika, p2_malaria_llm_claude_20260321_235246, p2_dengue_llm_claude_20260321_234654

### Un-monitored vaccinated children->Exposed children (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Un-monitored vaccinated children->Un-monitored vaccinated adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- **Text evidence:** 2 chunk(s)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible adults->Vaccinated adults (catch-up/monitored) (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- **Text evidence:** 2 chunk(s)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Un-monitored vaccinated adults->Exposed adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Vaccinated adults (catch-up/monitored)->Immune adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
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
- Gold count: **12** | Candidate: **13**
- Precision **1.0** | Recall **1.0** | F1 **1.0**
### Flows
- Gold count: **20** | Candidate: **25**
- Precision **0.96** | Recall **1.0** | F1 **0.9796**
