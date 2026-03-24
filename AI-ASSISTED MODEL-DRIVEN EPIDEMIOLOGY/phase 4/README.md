# Phase 4: Uncertainty quantification

Phase 4 turns **filled** models (typically from Phase 3 `selected_models/`) into **uncertainty-aware** outputs: typed parameter distributions, Monte Carlo simulation, sensitivity analysis, plots, and per-disease reports.

## Idea

- **Typed compartments & parameters:** `data/general_framework.json` describes model archetypes (e.g. vector-borne) and maps **parameter types** (transmission, recovery, mortality, …) to distribution families and plausible ranges.
- **General + disease-specific:** Framework supplies methodology; values still come from the model and earlier phases.

## What Phase 4 produces

- **Distributions** per parameter (e.g. lognormal for rates).
- **Monte Carlo** trajectories with percentile bands for compartment outputs.
- **Sensitivity** (one-at-a-time perturbations) with ranked parameters.
- **Plots** — uncertainty bands and tornado-style sensitivity charts.
- **`PHASE4_REPORT.md`** per disease under `reports/<disease>/`.

## Layout

```
phase 4/
├── run_phase4.py
├── data/general_framework.json
├── src/          # distributions, monte_carlo, sensitivity, visualization, report
└── reports/<disease>/   # JSON + PNG + PHASE4_REPORT.md
```

## Documentation

| File | Purpose |
|------|---------|
| **[INSTRUCTIONS.md](INSTRUCTIONS.md)** | Commands, options, dependencies. |
