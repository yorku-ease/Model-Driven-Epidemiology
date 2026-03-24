# Phase 4: Instructions

## Usage

**All selected models from Phase 3:**

```bash
cd "phase 4"
python run_phase4.py --selected-models "../phase 3/selected_models" --output reports
```

**Single model:**

```bash
python run_phase4.py --model "../phase 3/selected_models/malaria/model_filled.compmodel" --output reports/single
```

## Options

- `--samples 1000` — Monte Carlo sample count (default 500).
- `--days 200` — Simulation horizon in days.

## Dependencies

- Phase 1 `utils` (`generic_simulator`, `xml_parser`) on the Python path for simulation.
- Phase 3 output (or any directory containing `model_filled.compmodel` per disease).
- **Python:** `numpy`, `matplotlib` (for plots).
