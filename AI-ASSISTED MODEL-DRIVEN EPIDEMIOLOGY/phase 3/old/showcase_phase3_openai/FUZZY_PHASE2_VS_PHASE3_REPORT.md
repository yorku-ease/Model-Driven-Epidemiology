# Phase 2 Draft vs Phase 3 Filled: Recall Comparison

Both Phase 2 draft (`model_draft.compmodel`) and Phase 3 filled (`model_filled.compmodel`) are
evaluated against the **same baseline** with **identical matching** (substring + synonym groups).
Parameter threshold: **0.6**.

| Disease | Winner mode | Phase2 recall (C/P/F) | Phase3 recall (C/P/F) | ΔRecall (C/P/F) | Gap Δ |
|---------|------------|----------------------|----------------------|----------------|-------|
| cholera | **retrieval_only** | 0.750/1.000/0.667 | 1.000/1.000/1.000 | +0.250/+0.000/+0.333 | +2 |
| covid | **retrieval_only** | 0.875/1.000/0.909 | 1.000/1.000/1.000 | +0.125/+0.000/+0.091 | +2 |
| dengue | **retrieval_only** | 1.000/1.000/0.857 | 1.000/1.000/1.000 | +0.000/+0.000/+0.143 | +4 |
| ebola | **retrieval_only** | 0.833/0.500/0.375 | 1.000/0.571/0.625 | +0.167/+0.071/+0.250 | +4 |
| flu | **retrieval_only** | 1.000/1.000/0.800 | 1.000/1.000/1.000 | +0.000/+0.000/+0.200 | +0 |
| hiv | **retrieval_only** | 0.778/1.000/0.300 | 1.000/1.000/1.000 | +0.222/+0.000/+0.700 | +9 |
| malaria | **both** | 1.000/0.737/1.000 | 1.000/1.000/1.000 | +0.000/+0.263/+0.000 | +7 |
| measles | **retrieval_only** | 1.000/0.947/0.800 | 1.000/1.000/1.000 | +0.000/+0.053/+0.200 | +6 |
| tuberculosis | **retrieval_only** | 1.000/0.889/0.500 | 1.000/1.000/0.667 | +0.000/+0.111/+0.167 | +1 |
| zika | **retrieval_only** | 1.000/0.737/1.000 | 1.000/1.000/1.000 | +0.000/+0.263/+0.000 | +13 |

## Aggregate (valid diseases only)

- Mean ΔRecall compartments: **+0.0764**
- Mean ΔRecall parameters:   **+0.0761**
- Mean ΔRecall flows:        **+0.2084**
- Diseases with any positive ΔRecall component: **10/10**

## Notes

- **C** = compartments, **P** = parameters, **F** = flows.
- Matching: compartment/flow names use substring + SYNONYMS (e.g. 'Recovered' matches 'Recovered humans');
  parameters use substring OR SequenceMatcher.
- Gap Δ = (gaps before Phase 3) − (gaps after Phase 3): positive means fewer gaps.
- ΔRecall > 0 means Phase 3 improved coverage vs the baseline for that entity type.