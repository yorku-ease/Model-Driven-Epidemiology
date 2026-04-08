# Phase 2 (temp): Automated Model Extraction — Alternate Pipeline

`phase 2_temp` is an alternative / experimental version of the Phase 2 extraction pipeline.
It shares the same 9-step structure but lacks the `--paper-id` corpus mode and some later enhancements.

## Key difference from the main Phase 2

| Feature | `phase 2/` (main) | `phase 2_temp/` |
|---------|------------------|-----------------|
| `--paper-id` corpus mode | ✓ | ✗ |
| `--paper` direct path | ✓ | ✓ |
| Benchmark PDF stem naming | ✓ (via `phase2_paths`) | ✓ (auto-detected from path) |
| Gold auto-detection | `data/diseases/` → `data/baseline_models/` | same order |
| Phase 1 gap steps | `--enable-gap-steps` flag | enabled by default |
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

## Running

```bash
cd "phase 2_temp"

# Activate venv if used
# source "../../venv/bin/activate"

# Single benchmark paper (stem-based naming, gold auto-detected)
python3 run_phase2.py \
    --paper "../data/diseases/covid/covid2.pdf" \
    --llm-provider openai \
    --phase1-dir "../../phase 1" \
    --prior-models-dir "../../phase 1/reports/model_analysis"
# Creates: reports/covid2_llm_openai_<timestamp>/

# All papers ending in 2 or 3 across all diseases (papers 1 already done in main Phase 2)
for pdf in ../data/diseases/*/*[23].pdf; do
  python3 run_phase2.py \
      --paper "$pdf" \
      --llm-provider openai \
      --phase1-dir "../../phase 1" \
      --prior-models-dir "../../phase 1/reports/model_analysis"
done

# With Gemini instead
for pdf in ../data/diseases/*/*[23].pdf; do
  python3 run_phase2.py \
      --paper "$pdf" \
      --llm-provider gemini \
      --phase1-dir "../../phase 1" \
      --prior-models-dir "../../phase 1/reports/model_analysis"
done
```

## Gold standard auto-detection (Step 9)

1. `data/diseases/<disease>/<stem>.compmodel` — exact stem match (case-insensitive).
2. `data/baseline_models/*.compmodel` — legacy fallback (fuzzy name match).

## Outputs

Reports go under `reports/{stem}_llm_{provider}_{timestamp}/`.
Main files: `model_draft.compmodel`, `phase2_final_report.json`, `evaluation_report.json`.
