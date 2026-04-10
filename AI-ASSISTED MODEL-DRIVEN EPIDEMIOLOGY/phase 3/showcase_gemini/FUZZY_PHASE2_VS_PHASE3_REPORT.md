# Phase 2 Draft vs Phase 3 Filled: Recall Comparison

Both Phase 2 draft (`model_draft.compmodel`) and Phase 3 filled (`model_filled.compmodel`) are
evaluated against the **same baseline** with **identical matching** (substring + synonym groups).
Parameter threshold: **0.6**.

| Disease | Winner mode | Phase2 recall (C/P/F) | Phase3 recall (C/P/F) | ΔRecall (C/P/F) | Gap Δ |
|---------|------------|----------------------|----------------------|----------------|-------|
| cholera1 | **retrieval_only** | 0.750/1.000/0.667 | 0.750/1.000/0.667 | +0.000/+0.000/+0.000 | +0 |
| cholera2 | **both** | 0.545/0.120/0.238 | 1.000/1.000/0.714 | +0.455/+0.880/+0.476 | +39 |
| cholera3 | **retrieval_only** | 0.667/1.000/0.500 | 0.667/1.000/0.500 | +0.000/+0.000/+0.000 | +0 |
| covid1 | **both** | 0.875/1.000/0.909 | 1.000/1.000/1.000 | +0.125/+0.000/+0.091 | +2 |
| covid2 | **retrieval_only** | 1.000/0.923/1.000 | 1.000/1.000/1.000 | +0.000/+0.077/+0.000 | +1 |
| covid3 | **both** | 0.500/0.214/0.000 | 1.000/1.000/0.000 | +0.500/+0.786/+0.000 | +14 |
| dengue1 | **retrieval_only** | 1.000/1.000/0.857 | 1.000/1.000/0.857 | +0.000/+0.000/+0.000 | +2 |
| dengue2 | **llm_only** | 0.429/0.000/0.167 | 1.000/1.000/1.000 | +0.571/+1.000/+0.833 | +14 |
| dengue3 | **both** | 0.000/0.125/0.000 | 1.000/1.000/1.000 | +1.000/+0.875/+1.000 | +9 |
| ebola1 | **retrieval_only** | 0.833/0.500/0.375 | 0.833/0.929/0.500 | +0.000/+0.429/+0.125 | +7 |
| ebola2 | **retrieval_only** | 1.000/0.500/1.000 | 1.000/1.000/1.000 | +0.000/+0.500/+0.000 | +5 |
| ebola3 | **retrieval_only** | 1.000/0.900/0.375 | 1.000/1.000/0.625 | +0.000/+0.100/+0.250 | +3 |
| hiv1 | **both** | 0.778/1.000/0.300 | 1.000/1.000/1.000 | +0.222/+0.000/+0.700 | +8 |
| hiv2 | **llm_only** | 0.333/0.000/0.143 | 0.833/1.000/0.714 | +0.500/+1.000/+0.571 | +10 |
| hiv3 | **both** | 0.100/1.000/0.000 | 0.900/1.000/0.882 | +0.800/+0.000/+0.882 | +0 |
| influenza1 | **retrieval_only** | 1.000/1.000/0.800 | 1.000/1.000/0.800 | +0.000/+0.000/+0.000 | +0 |
| influenza2 | **both** | 0.714/0.600/0.556 | 1.000/1.000/0.889 | +0.286/+0.400/+0.333 | +15 |
| influenza3 | **llm_only** | 0.889/1.000/0.545 | 1.000/1.000/1.000 | +0.111/+0.000/+0.455 | +13 |
| malaria1 | **retrieval_only** | 1.000/0.737/1.000 | 1.000/1.000/1.000 | +0.000/+0.263/+0.000 | +7 |
| malaria2 | **retrieval_only** | 1.000/1.000/1.000 | 1.000/1.000/1.000 | +0.000/+0.000/+0.000 | +0 |
| malaria3 | **llm_only** | 0.778/0.062/0.375 | 1.000/1.000/1.000 | +0.222/+0.938/+0.625 | +18 |
| measles1 | **retrieval_only** | 1.000/0.947/0.800 | 1.000/1.000/1.000 | +0.000/+0.053/+0.200 | +5 |
| measles2 | **llm_only** | 0.571/0.556/0.333 | 1.000/1.000/1.000 | +0.429/+0.444/+0.667 | +16 |
| measles3 | **llm_only** | 0.833/0.143/0.857 | 1.000/1.000/1.000 | +0.167/+0.857/+0.143 | +9 |
| tuberculosis1 | **retrieval_only** | 1.000/0.889/0.500 | 1.000/1.000/0.500 | +0.000/+0.111/+0.000 | +1 |
| tuberculosis2 | **llm_only** | 0.857/0.529/0.333 | 1.000/1.000/1.000 | +0.143/+0.471/+0.667 | +17 |
| tuberculosis3 | **llm_only** | 0.571/0.889/0.000 | 0.857/1.000/0.625 | +0.286/+0.111/+0.625 | +18 |
| zika1 | **retrieval_only** | 1.000/0.737/1.000 | 1.000/0.895/1.000 | +0.000/+0.158/+0.000 | +5 |
| zika2 | **llm_only** | 0.600/1.000/0.286 | 1.000/1.000/1.000 | +0.400/+0.000/+0.714 | +3 |
| zika3 | **retrieval_only** | 1.000/1.000/1.000 | 1.000/1.000/1.000 | +0.000/+0.000/+0.000 | +0 |

## Aggregate (valid diseases only)

- Mean ΔRecall compartments: **+0.2072**
- Mean ΔRecall parameters:   **+0.3151**
- Mean ΔRecall flows:        **+0.3119**
- Diseases with any positive ΔRecall component: **24/30**

## Notes

- **C** = compartments, **P** = parameters, **F** = flows.
- Matching: compartment/flow names use substring + SYNONYMS (e.g. 'Recovered' matches 'Recovered humans');
  parameters use substring OR SequenceMatcher.
- Gap Δ = (gaps before Phase 3) − (gaps after Phase 3): positive means fewer gaps.
- ΔRecall > 0 means Phase 3 improved coverage vs the baseline for that entity type.