# Phase 4: Uncertainty quantification

Phase 4 turns the selected (filled) models from Phase 3 into **uncertainty-aware** outputs: parameter distributions, Monte Carlo simulation, sensitivity analysis, and a final report. It follows the **general framework** idea: similar diseases use the same *types* of compartments and the same uncertainty treatment for parameter *types* (transmission, recovery, mortality, etc.), not disease-specific copy-paste.

## General framework

- **Typed compartments:** We care about *types* of compartments (e.g. vector-borne → animal/vector compartment; STD → sexual contact structure), not copying a specific disease model. The file `data/general_framework.json` defines model types (vector_borne, std, airborne, waterborne) and expected compartment types.
- **Typed parameter uncertainty:** Parameter distributions are assigned by **parameter type** (transmission, recovery, mortality, progression, contact) using the same distribution families and typical ranges for all diseases. The “uncertainty guide” is a general framework that similar diseases abide by.
- **General + disease-specific:** General-methodology sources (textbook-style knowledge, encoded here as a framework file) define how we treat parameter types; disease-specific values still come from the model’s own expressions or ranges (Phase 2/3).

## What Phase 4 does

- **Assign parameter distributions:** Read each `.compmodel`, classify parameters by type (transmission, recovery, mortality, etc.), and convert point estimates/ranges into distributions (mostly lognormal for rates, uniform when only a rough range is known).
- **Run Monte Carlo simulations:** Sample from the assigned distributions and run the Phase 1 generic simulator many times to obtain uncertainty bands (5–50–95% intervals) for each compartment over time.
- **Analyze sensitivity:** Perturb one parameter at a time around its baseline and measure how peak infections and total cases change, ranking the most influential parameters.
- **Visualize and report:** Generate uncertainty-band plots and tornado plots, and write a markdown report per disease that summarizes distributions, Monte Carlo results, and sensitivity findings.

## Directory layout

```
phase 4/
├── run_phase4.py           # Main entry
├── README.md
├── data/
│   └── general_framework.json   # Typed compartments + parameter uncertainty defaults
├── src/
│   ├── distributions.py    # 9.1
│   ├── monte_carlo.py      # 9.2 (uses Phase 1 simulator)
│   ├── sensitivity.py      # 9.3
│   ├── visualization.py    # 10.1
│   └── report.py           # 10.2
└── reports/                # Output per disease
    └── <disease>/
        ├── parameter_distributions.json
        ├── monte_carlo_results.json
        ├── sensitivity_results.json
        ├── uncertainty_bands.png
        ├── sensitivity_tornado.png
        └── PHASE4_REPORT.md
```

## Usage

**Run on all selected models (from Phase 3):**

```bash
cd "phase 4"
python run_phase4.py --selected-models "../phase 3/selected_models" --output reports
```

**Run on a single model:**

```bash
python run_phase4.py --model "../phase 3/selected_models/malaria/model_filled.compmodel" --output reports/single
```

**Options:**

- `--samples 1000` — Monte Carlo sample count (default 500).
- `--days 200` — Simulation length.

## Dependencies

- **Phase 1** `utils` (generic_simulator, xml_parser) for running the compartmental model.
- **Phase 3** `selected_models` (or any dir with `model_filled.compmodel` per disease).
- **Python:** numpy, matplotlib (for plots).

## Outputs

- **parameter_distributions.json** — Each parameter: type, family (lognormal/uniform), low, high, point estimate (from general framework + model expression).
- **monte_carlo_results.json** — Percentiles (5, 50, 95) per compartment over time; param sample summary.
- **sensitivity_results.json** — One-at-a-time sensitivity; ranked list of most influential parameters.
- **uncertainty_bands.png** — Trajectory with uncertainty bands.
- **sensitivity_tornado.png** — Parameter importance bar chart.
- **PHASE4_REPORT.md** — Human-readable summary.
