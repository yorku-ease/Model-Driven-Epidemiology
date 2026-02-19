# Phase 3 Gap Analysis Report

**Disease / context:** tuberculosis

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **1**
- Missing compartments: 0
- Missing parameters: 1
- Missing stratifications: 0
- Missing interventions: 0
- Extra compartments (not in gold standard): 0
- Extra parameters (not in gold standard): 1

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- None

## 4. Missing parameters
- **π** — Present in gold standard but not in extracted model.

## 4b. Extra items (extracted but not in gold standard)
- Extra parameters: ε

## 5. Gap filling results
- Filled via **RAG**: 1
- Filled via **inference**: 0
- **Flagged** for manual review: 0

### π (missing_parameters)
- **Source:** rag
- **Value:** Unif(0,1) dimensionless
- **Description:** Susceptibility; the proportion of the population that is initially susceptible.
- **From papers:** p2_flu_llm_claude_20260212_211710, p2_hiv_llm_claude_20260218_171956, p2_malaria_llm_claude_20260218_172126

## 6. Fill validation (vs gold standard)
- Parameters compared: **1**
- Exact match (<1% error): **0**
- Close (<10% error): **0**
- Approximate (<50% error): **0**
- Poor (>50% error): **1**
- **Accuracy (exact+close)**: **0.0%**
- Median relative error: **100.0%**

| Parameter | Filled | Gold | Error % | Quality |
|-----------|--------|------|---------|---------|
| π | 0.0 | 0.0059 | 100.0% | poor |
