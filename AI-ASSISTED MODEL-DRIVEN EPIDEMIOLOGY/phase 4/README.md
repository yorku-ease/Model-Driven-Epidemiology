# Phase 4: Uncertainty quantification

Phase 4 turns **filled** compartmental models (from Phase 3) into **uncertainty-aware** outputs: typed parameter distributions, Monte Carlo ensemble simulation, one-at-a-time sensitivity analysis, plots, and per-paper reports.

## What it does

| Step | Task | Output |
|------|------|--------|
| 9.1 | Assign parameter distributions via `general_framework.json` | `parameter_distributions.json` |
| 9.2 | Monte Carlo simulation (500–1000 samples, 200 days) | `monte_carlo_results.json`, `uncertainty_bands.png` |
| 9.3 | One-at-a-time sensitivity (±20% perturbation, rank by peak + total cases) | `sensitivity_results.json`, `sensitivity_tornado.png` |
| 10 | Markdown report with provenance, tables, and plot links | `PHASE4_REPORT.md` |

## Input: Phase 3 filled models

Phase 3 now produces filled models under:
```
phase 3/showcase_gemini/both/<disease>_gemini_phase3/model_filled.compmodel
```
e.g. `covid1_gemini_phase3/model_filled.compmodel`, `influenza3_gemini_phase3/model_filled.compmodel`, etc. (30 papers total: 10 diseases × 3 papers each).

## Running Phase 4

```bash
cd "phase 4"

# Recommended: auto-picks the best fill mode per paper
python3 run_phase4.py \
    --showcase-dir "../phase 3/showcase_gemini" \
    --mode auto \
    --output reports \
    --samples 1000 \
    --days 200

# Use a specific fill mode for all papers
python3 run_phase4.py \
    --showcase-dir "../phase 3/showcase_gemini" \
    --mode both \
    --output reports

# Quick test — single paper, 100 samples
python3 run_phase4.py \
    --model "../phase 3/showcase_gemini/both/covid1_gemini_phase3/model_filled.compmodel" \
    --output reports/single \
    --samples 100
```

### How `--mode auto` works

For each paper, Phase 4 evaluates all three fill modes (`rag_only`, `llm_only`, `both`) and picks the one with the highest score:

```
score = comp_recall + flow_recall − 0.05 × param_gaps_after
```

| Term | Source | Weight |
|------|--------|--------|
| `comp_recall` | `structural_alignment.filled_vs_gold.compartments.recall` in `phase3_validation.json` | 1.0 |
| `flow_recall` | `structural_alignment.filled_vs_gold.flows.recall` in `phase3_validation.json` | 1.0 |
| `param_gaps_after` | `len(missing_parameters)` in `phase3_gaps.json` | −0.05 per gap |

The small gap penalty (0.05) means a mode needs to reduce parameter gaps by >20 to outweigh a 1-point recall advantage. The winning mode is recorded in the report's **Source model provenance** table. When all modes tie (common when scores are identical), `both` is chosen first by sort order.

## Populating selected_models/ (optional legacy layout)

If you need the old `selected_models/<disease>/model_filled.compmodel` layout:

```bash
python3 create_selected_models.py \
    --showcase-dir "../phase 3/showcase_gemini" \
    --mode both \
    --output "../phase 3/selected_models"

# Then use legacy mode
python3 run_phase4.py --selected-models "../phase 3/selected_models" --output reports
```

## Layout

```
phase 4/
├── run_phase4.py               # Main entry point
├── create_selected_models.py   # Helper: build selected_models/ from showcase
├── data/
│   └── general_framework.json  # Typed parameter priors and distribution families
├── src/
│   ├── distributions.py        # Task 9.1 — assign distributions
│   ├── monte_carlo.py          # Task 9.2 — ensemble simulation
│   ├── sensitivity.py          # Task 9.3 — OAT sensitivity
│   ├── visualization.py        # Task 10.1 — plots
│   └── report.py               # Task 10.2 — PHASE4_REPORT.md
└── reports/
    └── <disease_stem>/         # e.g. covid1/, influenza3/
        ├── PHASE4_REPORT.md
        ├── parameter_distributions.json
        ├── monte_carlo_results.json
        ├── sensitivity_results.json
        ├── uncertainty_bands.png
        └── sensitivity_tornado.png
```

## Key design choices

- **General framework:** `data/general_framework.json` maps parameter type keywords (transmission, recovery, mortality, …) to distribution families (lognormal/uniform) and plausible CV ranges — the same methodology applies to all diseases.
- **Phase 1 simulator:** `monte_carlo.py` and `sensitivity.py` import `GenericModelSimulator` from `phase 1/utils/` to actually run the ODE model.
- **Report provenance:** `PHASE4_REPORT.md` now includes which Phase 2 extractor was best for that paper and which Phase 3 fill mode was used.

## Documentation

| File | Purpose |
|------|---------|
| **[INSTRUCTIONS.md](INSTRUCTIONS.md)** | Commands, CLI flags, dependencies. |
