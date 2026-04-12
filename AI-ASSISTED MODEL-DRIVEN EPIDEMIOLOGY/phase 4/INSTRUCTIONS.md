# Phase 4: Instructions

## Usage

Run all commands from the `phase 4` directory (`cd "phase 4"`).

### Showcase directory (recommended)

Auto-selects the best Phase 3 fill mode per paper (`retrieval_only` / `llm_only` / `both`) using validation scores:

```bash
cd "phase 4"
python3 run_phase4.py \
    --showcase-dir "../phase 3/reports" \
    --mode auto \
    --output reports \
    --samples 1000 \
    --days 200
```

Force one fill mode for every paper:

```bash
python3 run_phase4.py \
    --showcase-dir "../phase 3/reports" \
    --mode both \
    --output reports
```

Quick test on a single filled model:

```bash
python3 run_phase4.py \
    --model "../phase 3/reports/both/covid1_gemini_phase3/model_filled.compmodel" \
    --output reports/single \
    --samples 100
```

### Build `selected_models/` from a showcase (copy only)

Use this if you want the flat `phase 3/selected_models/<paper>/model_filled.compmodel` tree for `--selected-models` or other tools. It does not run Monte Carlo.

```bash
cd "phase 4"
python3 run_phase4.py \
    --create-selected-models \
    --showcase-dir "../phase 3/reports" \
    --mode both \
    --selected-models-dir "../phase 3/selected_models"
```

### Selected-models layout (alternative)

Run UQ on a flat tree of `model_filled.compmodel` files (one subfolder per paper). Populate that tree with `--create-selected-models` above, or arrange folders by hand.

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
- `--showcase-dir` — Root of Phase 3 showcase output (contains `both/`, `llm_only/`, etc.).
- `--mode auto` | `both` | `retrieval_only` | `llm_only` — Which filled models to use from the showcase.
- `--create-selected-models` — Copy `model_filled.compmodel` from one showcase mode into `selected_models/` (use with `--showcase-dir`; optional `--selected-models-dir`, `--dry-run`).
- `--selected-models` — Root of that flat layout for full-batch Phase 4 (alternative to `--showcase-dir`).

## Dependencies

- Phase 1 `utils` (`generic_simulator`, `xml_parser`) on the Python path for simulation.
- Phase 3 output (or any directory containing `model_filled.compmodel` per disease).
- **Python:** `numpy`, `matplotlib` (for plots).
