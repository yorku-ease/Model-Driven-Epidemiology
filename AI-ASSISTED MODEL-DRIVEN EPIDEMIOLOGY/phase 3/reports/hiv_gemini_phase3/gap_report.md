# Phase 3 Gap Analysis Report

**Disease / context:** hiv

**Comparison mode:** gold_standard

## 1. Gap summary
- Total gaps: **3**
- Missing compartments: 2
- Missing parameters: 0
- Missing stratifications: 1
- Missing interventions: 0
- Extra compartments (not in gold standard): 1
- Extra parameters (not in gold standard): 14

## 2. Required vs optional
- **stratification**: required_if_promised

## 3. Missing compartments
- **infectious** — Present in gold standard but not in extracted model. (severity: high)
- **hiv deaths** — Present in gold standard but not in extracted model. (severity: high)

## 4. Missing parameters
- None

## 4b. Extra items (extracted but not in gold standard)
- Extra compartments: people with aids
- Extra parameters: c, h, p, l, d, a, b_s, b_h, b_hw, b_hm, c_s, c_h, c_hw, c_hm

## 5. Gap filling results
- Filled via **RAG**: 0
- Filled via **inference**: 0
- **Flagged** for manual review: 3

### infectious (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### hiv deaths (missing_compartments)
- **Source:** flagged
- **Action:** manual_review — Could not fill compartments gap automatically.

### sexualbehavior (missing_stratifications)
- **Source:** flagged
- **Action:** manual_review — Could not fill stratifications gap automatically.
