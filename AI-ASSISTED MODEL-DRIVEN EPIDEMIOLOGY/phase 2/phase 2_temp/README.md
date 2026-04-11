# Phase 2 (temp): Automated Model Extraction - Alternate Pipeline

`phase 2_temp` is an alternative / experimental version of the Phase 2 extraction pipeline.
It shares the same 7-step structure but lacks the `--paper-id` corpus mode and some later enhancements.

## Key difference from the main Phase 2

| Feature | `phase 2/` (main) | `phase 2_temp/` |
|---------|------------------|-----------------|
| `--paper-id` corpus mode | ✓ | ✗ |
| `--paper` direct path | ✓ | ✓ |
| Benchmark PDF stem naming | ✓ (via `phase2_paths`) | ✓ (auto-detected from path) |
| Gold auto-detection | `data/diseases/` → `data/baseline_models/` | same order |
| Latest enhancements | ✓ | may lag behind |

## Benchmark data layout

Both pipelines share the same data under `../data/diseases/`:

```
phase 2/
  data/
    diseases/
      covid/
        covid1.pdf   covid1.compmodel
        covid2.pdf   covid2.compmodel
        covid3.pdf   covid3.compmodel
      influenza/
        influenza1.pdf   influenza1.compmodel
        ...
```

When the paper path is inside `data/diseases/`, the report folder prefix equals the **PDF stem**
(e.g. `data/diseases/covid/covid2.pdf` → `reports/covid2_llm_openai_<timestamp>/`).

## How to run

**Commands, examples, and flags** for this folder are in **[INSTRUCTIONS.md](INSTRUCTIONS.md)**. The main Phase 2 guide is **[../INSTRUCTIONS.md](../INSTRUCTIONS.md)** for the canonical pipeline.

## Gold standard auto-detection (evaluation step)

1. `data/diseases/<disease>/<stem>.compmodel` - exact stem match (case-insensitive).
2. `data/baseline_models/*.compmodel` - legacy fallback (fuzzy name match).

## Outputs

Reports go under `reports/{stem}_llm_{provider}_{timestamp}/`.
Main files: `model_draft.compmodel`, `phase2_final_report.json`, `evaluation_report.json`.
