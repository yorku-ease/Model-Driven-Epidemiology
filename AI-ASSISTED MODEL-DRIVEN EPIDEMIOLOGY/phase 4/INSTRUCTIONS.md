# Phase 4: Instructions

## Usage

Run all commands from the `phase 4` directory (`cd "phase 4"`).

### Showcase directory (recommended)

Auto-selects the best Phase 3 fill mode per paper (`retrieval_only` / `llm_only` / `both`) using validation scores:

```bash
cd "phase 4"
python3 run_phase4.py \
    --showcase-dir "../phase 3/showcase_gemini" \
    --mode auto \
    --output reports \
    --samples 1000 \
    --days 200
```

Force one fill mode for every paper:

```bash
python3 run_phase4.py \
    --showcase-dir "../phase 3/showcase_gemini" \
    --mode both \
    --output reports
```

Quick test on a single filled model:

```bash
python3 run_phase4.py \
    --model "../phase 3/showcase_gemini/both/covid1_gemini_phase3/model_filled.compmodel" \
    --output reports/single \
    --samples 100
```

### Selected-models layout (alternative)

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

## Dependencies

- Phase 1 `utils` (`generic_simulator`, `xml_parser`) on the Python path for simulation.
- Phase 3 output (or any directory containing `model_filled.compmodel` per disease).
- **Python:** `numpy`, `matplotlib` (for plots).
