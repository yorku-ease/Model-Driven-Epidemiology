# Phase 3 Gap Analysis Report

**Disease / context:** zika

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **13**
- Missing compartments: 3
- Missing parameters: 7
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 3
- Extra compartments (not in gold standard): 3
- Extra parameters (not in gold standard): 10
- Extra flows (not in gold standard): 3

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **13**
- After fills gaps (re-detected): **0**
- Delta (before - after): **13**
- Delta missing parameters: **7**
- Delta missing compartments: **3**
- Delta missing flows: **3**

## 2. Required vs optional
- **stratification**: optional

## 3. Missing compartments
- **susceptible female adults** — Present in gold standard but not in extracted model. (severity: high)
- **exposed female adults** — Present in gold standard but not in extracted model. (severity: high)
- **infectious female adults** — Present in gold standard but not in extracted model. (severity: high)

## 3b. Missing flows (gold vs extraction/XML)
- **Pupae (non-infectious)->Susceptible female adults** — Present in gold standard but not in extracted model (or flows list).
- **Susceptible female adults->Exposed female adults** — Present in gold standard but not in extracted model (or flows list).
- **Exposed female adults->Infectious female adults** — Present in gold standard but not in extracted model (or flows list).

## 4. Missing parameters
- **1/τ_h** — Present in gold standard but not in extracted model.
- **b_v** — Present in gold standard but not in extracted model.
- **1/τ_v** — Present in gold standard but not in extracted model.
- **r_h** — Present in gold standard but not in extracted model.
- **f** — Present in gold standard but not in extracted model.
- **δ_l** — Present in gold standard but not in extracted model.
- **φ** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: susceptible vectors, exposed vectors, infectious vectors
- Extra parameters: 1/α_h, 1/α_v, 1/γ, e_v, μ_v, q, k_h, k_v, κ, r0

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 100.0% | 25% |
| **Reference agreement** | 85.6% | 25% |
| **Fill traceability** | 84.6% | 20% |
| **Parameter accuracy** | 71.4% | 15% |
| **Structural integrity** | 36.7%  (30→19 errors) | 15% |
| **→ Composite** | **79.5/100** | — |

## 1d. Structural integrity & repair

*Phase RLM-inspired: checks + targeted repairs on model_filled.compmodel →*
*model_repaired.compmodel. Independent of gold standard.*

| | Before repair | After repair | Resolved |
|---|---|---|---|
| Critical | 3 | 0 | +3 |
| High | 3 | 1 | +2 |
| Medium | 24 | 18 | +6 |
| **Total** | **30** | **19** | **+11** |

**12 repair(s) applied:**

| Error type | Element | Fix |
|-----------|---------|-----|
| `self_referential_flow` | Susceptible Humans -> ContactFlow | Redirected Susceptible Humans ContactFlow target from //@compartments.1 to //@compartments.8 (Expose |
| `self_referential_flow` | Susceptible Vectors -> ContactFlow | Redirected Susceptible Vectors ContactFlow target from //@compartments.8 to //@compartments.1 (Expos |
| `zero_population_all` | all_compartments | Set Susceptible Humans population=1000 |
| `missing_birth_sources` | Susceptible Humans | Set Susceptible Vectors population=1000 (initial condition) |
| `flow_chain_incomplete` | Infectious Vectors | Added flow Infectious Vectors → Recovered Humans |
| `flow_chain_incomplete` | infectious female adults | Added flow exposed female adults → Infectious Humans |
| `orphaned_parameters` | 1/α_H | Wired 1/α_H (//@parameters.1) to Eggs → Larvae flow |
| `orphaned_parameters` | 1/α_V | Wired 1/α_V (//@parameters.2) to Larvae → Pupae flow |
| `orphaned_parameters` | 1/τ_h | Wired 1/τ_h (//@parameters.19) to exposed female adults → infectious female adults flow |
| `orphaned_parameters` | f | Wired f (//@parameters.23) to Pupae → susceptible female adults flow |
| `orphaned_parameters` | φ | Wired φ (//@parameters.25) to susceptible female adults → exposed female adults flow |
| `flow_chain_incomplete` | infectious female adults | Added flow exposed female adults → Infectious Humans |

**19 structural error(s) remaining after repair:**

| Type | Element | Severity |
|------|---------|----------|
| `flow_chain_incomplete` | infectious female adults | **high** |
| `orphaned_parameters` | e_V | **medium** |
| `orphaned_parameters` | 1/τ1 | **medium** |
| `orphaned_parameters` | μ1 | **medium** |
| `orphaned_parameters` | 1/τ2 | **medium** |
| `orphaned_parameters` | μ2 | **medium** |
| `orphaned_parameters` | 1/τ3 | **medium** |
| `orphaned_parameters` | μ3 | **medium** |
| `orphaned_parameters` | μ_V | **medium** |
| `orphaned_parameters` | K_H | **medium** |
| `orphaned_parameters` | K_V | **medium** |
| `orphaned_parameters` | κ | **medium** |
| `orphaned_parameters` | λ | **medium** |
| `orphaned_parameters` | R0 | **medium** |
| `orphaned_parameters` | b_v | **medium** |
| `orphaned_parameters` | 1/τ_v | **medium** |
| `orphaned_parameters` | r_h | **medium** |
| `orphaned_parameters` | δ_l | **medium** |
| `parameter_layer_contamination` | R0 | **medium** |


## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 3 | 7 | 3 | 13 |
| **Extra in model** | Model items not in reference (noise/convention) | 3 | 10 | 3 | 16 |

## 5. Gap filling results
- Filled via **RAG**: 11
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 2

### susceptible female adults (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### exposed female adults (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### infectious female adults (missing_compartments)
- **Source:** rag
- **Primary name:** infectious female adults
- **Evidence chunks:** 3 snippet(s) in database
- *Use paper snippets to confirm compartment label and add to model.*

### 1/τ_h (missing_parameters)
- **Source:** rag
- **Value:** 0.5 1/day
- **Description:** Inverse incubation time in humans
- **From papers:** p1_model_zika, p2_ebola_llm_claude_20260321_234954, p2_hiv_llm_claude_20260321_235051

### b_v (missing_parameters)
- **Source:** rag
- **Value:** 0.001 1/day
- **Description:** Biting/exposure term for vectors becoming infected from infectious humans
- **From papers:** p1_model_zika, p2_dengue_llm_openai_20260321_230911, p2_dengue_llm_gemini_20260321_232851

### 1/τ_v (missing_parameters)
- **Source:** rag
- **Value:** 0.125 1/day
- **Description:** Inverse incubation time in vectors
- **From papers:** p1_model_zika, p2_malaria_llm_claude_20260321_235246, p2_hiv_llm_openai_20260321_231210

### r_h (missing_parameters)
- **Source:** rag
- **Value:** 0.00005 1/day
- **Description:** Human population growth rate (logistic)
- **From papers:** p1_model_zika, p2_malaria_llm_openai_20260321_231406, p2_dengue_llm_openai_20260321_230911

### f (missing_parameters)
- **Source:** rag
- **Value:** 1.0 
- **From papers:** p1_model_zika, p2_zika_llm_gemini_20260321_234220, p2_covid_llm_claude_20260321_234526

### δ_l (missing_parameters)
- **Source:** rag
- **Value:** 1 1/day
- **Description:** Larval density-dependent mortality coefficient (logistic term)
- **From papers:** p1_model_zika, p2_cholera_llm_gemini_20260321_232556, p2_dengue_llm_gemini_20260321_232851

### φ (missing_parameters)
- **Source:** rag
- **Value:** 0.0 
- **From papers:** p1_model_zika, p2_zika_llm_openai_20260321_232352, p2_dengue_llm_claude_20260321_234654

### Pupae (non-infectious)->Susceptible female adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Susceptible female adults->Exposed female adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### Exposed female adults->Infectious female adults (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 6. Fill validation (vs gold standard)
- Parameters compared: **7**
- Exact match (<1% error): **5**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **2**
- **Accuracy (exact+close)**: **71.4%**
- Median relative error: **0.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| 1/τ_h | 0.5 | 0.5 | 0.0% | exact |
| b_v | 0.001 | 0.001 | 0.0% | exact |
| 1/τ_v | 0.125 | 0.125 | 0.0% | exact |
| r_h | 5e-05 | 5e-05 | 0.0% | exact |
| f | 1.0 | 80.0 | 98.75% | poor |
| δ_l | 1.0 | 1.0 | 0.0% | exact |
| φ | 0.0 | 0.68 | 100.0% | poor |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **13**
- Precision **0.7692** | Recall **1.0** | F1 **0.8696**
### Flows
- Gold count: **8** | Candidate: **11**
- Precision **0.7273** | Recall **1.0** | F1 **0.8421**
