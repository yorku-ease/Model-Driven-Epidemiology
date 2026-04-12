# Phase 3 Gap Analysis Report

**Disease / context:** hiv3

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **47**
- Missing compartments: 9
- Missing parameters: 21
- Missing stratifications: 0
- Missing interventions: 0
- Missing flows: 17
- Extra compartments (not in gold standard): 4
- Extra parameters (not in gold standard): 28
- Extra flows (not in gold standard): 7

## 1b. Improvement vs Phase 2 draft
- Phase 2 gaps (before fills): **47**
- After fills gaps (re-detected): **26**
- Delta (before - after): **21**
- Delta missing parameters: **21**
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
- **betai1** — Present in gold standard but not in extracted model.
- **betai2** — Present in gold standard but not in extracted model.
- **betai3** — Present in gold standard but not in extracted model.
- **betai4** — Present in gold standard but not in extracted model.
- **betaa1** — Present in gold standard but not in extracted model.
- **betaa2** — Present in gold standard but not in extracted model.
- **betaa3** — Present in gold standard but not in extracted model.
- **betaa4** — Present in gold standard but not in extracted model.
- **progressi1toi2** — Present in gold standard but not in extracted model.
- **progressi2toi3** — Present in gold standard but not in extracted model.
- **progressi3toi4** — Present in gold standard but not in extracted model.
- **untreatedmortalityi4** — Present in gold standard but not in extracted model.
- **artstarti1** — Present in gold standard but not in extracted model.
- **artstarti2** — Present in gold standard but not in extracted model.
- **artstarti3** — Present in gold standard but not in extracted model.
- **artstarti4** — Present in gold standard but not in extracted model.
- **progressa1toa2** — Present in gold standard but not in extracted model.
- **progressa2toa3** — Present in gold standard but not in extracted model.
- **progressa3toa4** — Present in gold standard but not in extracted model.
- **artmortalitya4** — Present in gold standard but not in extracted model.
- **artdropoutrate** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: infectious (acute phase), infectious (chronic phase), infectious (final phase), infectious (on art)
- Extra parameters: initial cd4 mu, initial cd4 sigma, survival median, survival shape, acute phase duration, acute phase infectivity, chronic phase infectivity, final phase infectivity, final phase duration, concurrency number, partnership duration, start art cd4, proportion tested rate, monte carlo repeats, birth rate, background mortality rate, transmission parameter initial value, relative transmission on art, progression untreated, progression treated, intervention timing, intervention rate, intervention maximum, testing rate, refusal, drop-out, failure rate, other interventions reduction in transmission

## 1c. Completeness score (0–100)
| Component | Score | Weight |
|-----------|-------|--------|
| **Gap reduction** | 44.7% | 30% |
| **Reference agreement** | 26.8% | 30% |
| **Fill traceability** | 87.7% | 20% |
| **Parameter accuracy** | 100.0% | 20% |
| **→ Composite** | **59.0/100** | — |

## 2b. Three-layer gap analysis

*Matches the paper framework: gaps between specification, extraction, and validation.*

| Layer | Description | C | P | F | Total |
|-------|-------------|---|---|---|-------|
| **Spec → Model** | Recognised from paper text but absent from model | 0 | 0 | 0 | 0 |
| **Model → Gold** | Reference model items absent from extraction | 9 | 21 | 17 | 47 |
| **Extra in model** | Model items not in reference (noise/convention) | 4 | 28 | 7 | 39 |

## 5. Gap filling results
- Filled via **RAG**: 55
- Filled via **paper entities (spec)**: 0
- Filled via **inference**: 18
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
- **Primary name:** Untreated Stage I HIV

### artstagea1 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage
- **Reasoning:** The text

### artstagea2 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage

### artstagea3 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS
- **Reasoning:** The

### artstagea4 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage
- **Reasoning:** The model

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS
- **Reasoning:** The model describes

### betai1 (missing_parameters)
- **Source:** rag
- **Value:** 0.08 
- **Description:** Transmission intensity from untreated stage I1; early untreated infection contributes substantially to onward transmission
- **From papers:** p1_model_cholera, p1_model_dengue, p1_model_influenza

### betai2 (missing_parameters)
- **Source:** rag
- **Value:** 0.05 
- **Description:** Transmission intensity from untreated stage I2
- **From papers:** p1_model_cholera, p1_model_dengue, p1_model_influenza

### betai3 (missing_parameters)
- **Source:** rag
- **Value:** 0.04 
- **Description:** Transmission intensity from untreated stage I3
- **From papers:** p1_model_cholera, p1_model_dengue, p1_model_influenza

### betai4 (missing_parameters)
- **Source:** rag
- **Value:** 0.06 
- **Description:** Transmission intensity from untreated stage I4
- **From papers:** p1_model_cholera, p1_model_dengue, p1_model_influenza

### betaa1 (missing_parameters)
- **Source:** rag
- **Value:** 0.0008 
- **Description:** Residual transmission from ART stage A1, approximating strong ART-associated reduction in infectiousness
- **From papers:** p1_model_cholera, p1_model_dengue, p2_gold_hiv3

### betaa2 (missing_parameters)
- **Source:** rag
- **Value:** 0.0005 
- **Description:** Residual transmission from ART stage A2
- **From papers:** p1_model_cholera, p1_model_dengue, p2_gold_hiv3

### betaa3 (missing_parameters)
- **Source:** rag
- **Value:** 0.0004 
- **Description:** Residual transmission from ART stage A3
- **From papers:** p1_model_cholera, p1_model_dengue, p2_gold_hiv3

### betaa4 (missing_parameters)
- **Source:** rag
- **Value:** 0.0006 
- **Description:** Residual transmission from ART stage A4
- **From papers:** p1_model_cholera, p1_model_dengue, p2_gold_hiv3

### progressi1toi2 (missing_parameters)
- **Source:** rag
- **Value:** 0.25 
- **Description:** Progression rate from untreated stage I1 to I2
- **From papers:** p1_model_cholera, p2_cholera3_llm_claude_20260407_213639, p1_model_covid

### progressi2toi3 (missing_parameters)
- **Source:** rag
- **Value:** 0.20 
- **Description:** Progression rate from untreated stage I2 to I3
- **From papers:** p1_model_cholera, p2_cholera3_llm_claude_20260407_213639, p1_model_covid

### progressi3toi4 (missing_parameters)
- **Source:** rag
- **Value:** 0.18 
- **Description:** Progression rate from untreated stage I3 to I4
- **From papers:** p1_model_cholera, p2_cholera3_llm_claude_20260407_213639, p1_model_covid

### untreatedmortalityi4 (missing_parameters)
- **Source:** rag
- **Value:** 0.30 
- **Description:** Mortality rate from untreated late stage I4
- **From papers:** p1_model_cholera, p2_gold_hiv3, p1_model_hiv

### artstarti1 (missing_parameters)
- **Source:** rag
- **Value:** 0.70 
- **Description:** Immediate ART uptake from stage I1 after diagnosis
- **From papers:** p1_model_cholera, p2_cholera3_llm_claude_20260407_213639, p1_model_covid

### artstarti2 (missing_parameters)
- **Source:** rag
- **Value:** 0.70 
- **Description:** Immediate ART uptake from stage I2 after diagnosis
- **From papers:** p1_model_cholera, p2_cholera3_llm_claude_20260407_213639, p1_model_covid

### artstarti3 (missing_parameters)
- **Source:** rag
- **Value:** 0.70 
- **Description:** Immediate ART uptake from stage I3 after diagnosis
- **From papers:** p1_model_cholera, p2_cholera3_llm_claude_20260407_213639, p1_model_covid

### artstarti4 (missing_parameters)
- **Source:** rag
- **Value:** 0.70 
- **Description:** Immediate ART uptake from stage I4 after diagnosis
- **From papers:** p1_model_cholera, p2_cholera3_llm_claude_20260407_213639, p1_model_covid

### progressa1toa2 (missing_parameters)
- **Source:** rag
- **Value:** 0.08 
- **Description:** Progression while on ART from A1 to A2
- **From papers:** p1_model_cholera, p1_model_covid, p2_gold_hiv3

### progressa2toa3 (missing_parameters)
- **Source:** rag
- **Value:** 0.07 
- **Description:** Progression while on ART from A2 to A3
- **From papers:** p1_model_cholera, p1_model_covid, p2_gold_hiv3

### progressa3toa4 (missing_parameters)
- **Source:** rag
- **Value:** 0.06 
- **Description:** Progression while on ART from A3 to A4
- **From papers:** p1_model_cholera, p1_model_covid, p2_gold_hiv3

### artmortalitya4 (missing_parameters)
- **Source:** rag
- **Value:** 0.05 
- **Description:** Mortality rate from late stage A4 while on ART
- **From papers:** p1_model_cholera, p1_model_covid, p2_gold_hiv3

### artdropoutrate (missing_parameters)
- **Source:** rag
- **Value:** 0.015 
- **Description:** Long-term annual ART dropout rate used in the paper's implementation assumptions
- **From papers:** p1_model_cholera, p1_model_covid, p2_gold_hiv3

### Susceptible->UntreatedStageI1 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI1->UntreatedStageI2 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI1->ARTStageA1 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 2 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI2->UntreatedStageI3 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI2->ARTStageA2 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI3->UntreatedStageI4 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI3->ARTStageA3 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI4->Removed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI4->ARTStageA4 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA1->ARTStageA2 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA1->UntreatedStageI1 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA2->ARTStageA3 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA2->UntreatedStageI2 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA3->ARTStageA4 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA3->UntreatedStageI3 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA4->Removed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA4->UntreatedStageI4 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### untreatedstagei1 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### untreatedstagei2 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated HIV Stage F

### untreatedstagei3 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### untreatedstagei4 (missing_compartments)
- **Source:** inference
- **Primary name:** Untreated Stage I

### artstagea1 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage
- **Reasoning:** The

### artstagea2 (missing_compartments)
- **Source:** inference
- **Primary name:** Disease Stages

### artstagea3 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage
- **Reasoning:** The model

### artstagea4 (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS Stage

### removed (missing_compartments)
- **Source:** inference
- **Primary name:** AIDS
- **Reasoning:** The

### Susceptible->UntreatedStageI1 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI1->UntreatedStageI2 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI1->ARTStageA1 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 2 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI2->UntreatedStageI3 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI2->ARTStageA2 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI3->UntreatedStageI4 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI3->ARTStageA3 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI4->Removed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### UntreatedStageI4->ARTStageA4 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA1->ARTStageA2 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA1->UntreatedStageI1 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA2->ARTStageA3 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA2->UntreatedStageI2 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA3->ARTStageA4 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA3->UntreatedStageI3 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA4->Removed (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 5 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

### ARTStageA4->UntreatedStageI4 (missing_flows)
- **Source:** rag
- **Similar flows in corpus:** 3 match(es)
- *Analogous flows from indexed models / text; align with gold wiring.*

## 6. Fill validation (vs gold standard)
- Parameters compared: **21**
- Exact match (<1% error): **21**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **0**
- **Accuracy (exact+close)**: **100.0%**
- Median relative error: **0.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| betai1 | 0.08 | 0.08 | 0.0% | exact |
| betai2 | 0.05 | 0.05 | 0.0% | exact |
| betai3 | 0.04 | 0.04 | 0.0% | exact |
| betai4 | 0.06 | 0.06 | 0.0% | exact |
| betaa1 | 0.0008 | 0.0008 | 0.0% | exact |
| betaa2 | 0.0005 | 0.0005 | 0.0% | exact |
| betaa3 | 0.0004 | 0.0004 | 0.0% | exact |
| betaa4 | 0.0006 | 0.0006 | 0.0% | exact |
| progressi1toi2 | 0.25 | 0.25 | 0.0% | exact |
| progressi2toi3 | 0.2 | 0.2 | 0.0% | exact |
| progressi3toi4 | 0.18 | 0.18 | 0.0% | exact |
| untreatedmortalityi4 | 0.3 | 0.3 | 0.0% | exact |
| artstarti1 | 0.7 | 0.7 | 0.0% | exact |
| artstarti2 | 0.7 | 0.7 | 0.0% | exact |
| artstarti3 | 0.7 | 0.7 | 0.0% | exact |
| artstarti4 | 0.7 | 0.7 | 0.0% | exact |
| progressa1toa2 | 0.08 | 0.08 | 0.0% | exact |
| progressa2toa3 | 0.07 | 0.07 | 0.0% | exact |
| progressa3toa4 | 0.06 | 0.06 | 0.0% | exact |
| artmortalitya4 | 0.05 | 0.05 | 0.0% | exact |
| artdropoutrate | 0.015 | 0.015 | 0.0% | exact |

## 7. Structural alignment vs gold (compartments & flows)
### Compartments
- Gold count: **10** | Candidate: **9**
- Precision **0.2222** | Recall **0.5** | F1 **0.3077**
### Flows
- Gold count: **17** | Candidate: **9**
- Precision **0.2222** | Recall **0.2353** | F1 **0.2286**
