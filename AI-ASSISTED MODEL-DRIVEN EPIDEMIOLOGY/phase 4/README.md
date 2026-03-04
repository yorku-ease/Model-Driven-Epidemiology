# Phase 4: Uncertainty quantification

Phase 4 turns the selected (filled) models from Phase 3 into **uncertainty-aware** outputs: parameter distributions, Monte Carlo simulation, sensitivity analysis, and a final report. It follows the **general framework** idea: similar diseases use the same *types* of compartments and the same uncertainty treatment for parameter *types* (transmission, recovery, mortality, etc.), not disease-specific copy-paste.

## General framework (what “stick to the comment” means)

- **Typed compartments:** We care about *types* of compartments (e.g. vector-borne → animal/vector compartment; STD → sexual contact structure), not copying a specific disease model. The file `data/general_framework.json` defines model types (vector_borne, std, airborne, waterborne) and expected compartment types.
- **Typed parameter uncertainty:** Parameter distributions are assigned by **parameter type** (transmission, recovery, mortality, progression, contact) using the same distribution families and typical ranges for all diseases. So the “uncertainty guide” is a general framework that all similar diseases abide by.
- **General + disease-specific:** The roadmap says we need general papers (e.g. textbook) and disease-specific papers. Here we implement the general part as a curated framework file; disease-specific values still come from the model (Phase 2/3) point estimates or ranges when available.

## Tasks

| Task | Description |
|------|-------------|
| **9.1** | Assign parameter distributions (point → family + bounds using general framework) |
| **9.2** | Monte Carlo simulation (ensemble with parameter uncertainty) |
| **9.3** | Sensitivity analysis (one-at-a-time; most influential parameters) |
| **10.1** | Visualize uncertainty (bands) and sensitivity (tornado) |
| **10.2** | Comprehensive report (distributions, MC summary, sensitivity, plots) |
| **10.3** | Run pipeline on all selected models (disease-agnostic) |

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
