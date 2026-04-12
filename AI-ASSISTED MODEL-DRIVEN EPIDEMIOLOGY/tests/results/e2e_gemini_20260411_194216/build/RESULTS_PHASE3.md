# Phase 3 Recall Summary — phase3_showcase

**Primary metric: Recall** — fraction of gold-standard compartments/flows that appear in the Phase 3 filled model (`filled_vs_gold`). Shown as **Recall** / Precision / F1.

**Best P2** = which Phase 2 LLM (openai/gemini/claude) produced the best-scoring report used as input for Phase 3.

**Param gaps after** = number of gold parameters still missing after Phase 3 fill.

**Δ vs draft** = change in compartment Recall from Phase 2 draft to Phase 3 filled model.

---

### Rule-Based Retrieval + LLM (`both`)

| Disease | Best P2 | P2 Comp R | P3 Comp R | Δ Comp | P2 Flow R | P3 Flow R | Δ Flow | Param gaps↓ |
|---------|---------|-----------|-----------|--------|-----------|-----------|--------|-------------|
| Dengue (P3) | gemini | 0.00 | **0.89** | +0.89 | 0.00 | **0.78** | +0.78 | 8 |
| HIV (P3) | gemini | 0.10 | **0.50** | +0.40 | 0.00 | **0.24** | +0.24 | 21 |
| Malaria (P2) | gemini | 0.50 | **1.00** | +0.50 | 0.00 | **1.00** | +1.00 | 0 |
| **Average** | — | 0.20 | **0.80** | +0.60 | 0.00 | **0.67** | — | — |

---

## Quick comparison: Phase 2 baseline vs Phase 3 modes (averages)

| | Avg Comp Recall | Avg Comp F1 | Avg Flow Recall | Avg Flow F1 |
|--|----------------|-------------|----------------|-------------|
| **Phase 2 best** | 0.20 | 0.19 | 0.00 | 0.00 |
| **Both (Rule-Based Retrieval+LLM)** | **0.80** (+0.60) | 0.50 | **0.67** () | 0.44 |


---

## Phase 2 draft vs Phase 3 filled (same fuzzy algorithm vs gold)

Both Phase 2 draft (`model_draft.compmodel`) and Phase 3 filled (`model_filled.compmodel`) are
evaluated against the **same baseline** with **identical matching** (substring + synonym groups).
Parameter threshold: **0.6**.

| Disease | Winner mode | Phase2 recall (C/P/F) | Phase3 recall (C/P/F) | ΔRecall (C/P/F) | Gap Δ |
|---------|------------|----------------------|----------------------|----------------|-------|
| hiv3 | **both** | 0.100/0.381/0.000 | 0.500/1.000/0.235 | +0.400/+0.619/+0.235 | +21 |
| dengue3 | **both** | 0.000/0.250/0.000 | 0.889/1.000/0.778 | +0.889/+0.750/+0.778 | +9 |
| malaria2 | **both** | 0.500/1.000/0.000 | 1.000/1.000/1.000 | +0.500/+0.000/+1.000 | +2 |

### Aggregate (valid diseases only)

- Mean ΔRecall compartments: **+0.5963**
- Mean ΔRecall parameters:   **+0.4563**
- Mean ΔRecall flows:        **+0.6710**
- Diseases with any positive ΔRecall component: **3/3**

**Notes:** C = compartments, P = parameters, F = flows. Gap Δ = gaps before − gaps after Phase 3.