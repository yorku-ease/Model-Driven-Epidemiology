# Repository cleanup (maintenance)

## What was removed (safe to omit from version control)

| Item | Why |
|------|-----|
| **`venv/`** | Recreate locally: `python -m venv venv && source venv/bin/activate && pip install -r requirements.txt` |
| **`testingPhase2/`** | Stand-alone experiments (embeddings, GROBID, evaluation harness). Not imported by Phases 1–3 or `phase_rlm`. |
| **`phase 2/reports_pruned/`** | Intermediate inputs for the optional “pruned PDF” Phase 2 variant. Regenerate with `create_pruned_folders.py` if needed. |
| **`phase 2/reports_pruned_output/`** | Outputs from `run_pruned_pipeline.py`. Regenerate after restoring `reports_pruned/`. |
| **`phase 2/data/old_baseline_models/`** | Duplicate of `data/baseline_models/` (same file set). |
| **`__pycache__/`** | Python bytecode caches (regenerated on run). |

## What was kept

- **`phase 2/reports/`** — current Phase 2 runs  
- **`phase 2/old-reports/`** — historical runs  
- **`phase 3/reports/`**, **`phase 3/selected_models/`** — Phase 3 and selection artifacts  
- **`phase 1/reports/`**, **`phase 4/`** — analysis / UQ pipeline (unchanged)  

## Optional pruned pipeline

If you need pruned-folder recall experiments again:

1. `python create_pruned_folders.py` → rebuilds `reports_pruned/`  
2. `python run_pruned_pipeline.py --pruned-dir reports_pruned --output reports_pruned_output`  
3. `python summarize_pruned_recall.py`  

See `phase 2/run_pruned_pipeline.py` docstrings for options.
