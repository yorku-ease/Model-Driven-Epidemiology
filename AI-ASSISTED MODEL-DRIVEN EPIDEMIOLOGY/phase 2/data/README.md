# Phase 2 `data/` layout

## Canonical layout: `diseases/<disease>/`

Put **all papers for one disease** under **`data/diseases/<disease_name>/`** so benchmark PDFs and gold models stay separate from tooling folders at the `data/` root (`baseline_models/`, `papers/`, `examples/`). The top-level name **`cases/`** is reserved at **`data/`** (not inside a disease folder).

The **file name (stem) must match** between PDF and gold model:

```text
data/
  baseline_models/     # optional: legacy flat *.compmodel (fallback)
  papers/              # optional: legacy flat *.pdf (fallback)
  examples/            # example JSON for tooling
  diseases/
    covid/
      covid1.pdf
      covid1.compmodel
      covid2.pdf
      covid2.compmodel
    ebola/
      ebola1.pdf
      ebola1.compmodel
      ...
```

Optional: use **`data/diseases/<disease>/cases/`** for PDF + gold pairs if you prefer a subfolder inside each disease directory.

Legacy (still supported): **`data/<disease>/`** without the `diseases/` wrapper, with or without a per-disease `cases/` folder.

Run Phase 2 on one paper:

```bash
python run_phase2.py --paper data/diseases/covid/covid2.pdf --llm-provider openai
```

This picks gold **`data/diseases/covid/covid2.compmodel`** automatically (same stem as the PDF) and writes **`reports/covid2_llm_openai_<timestamp>/`** (report prefix = PDF stem).

## Gold models

You still **author** each `.compmodel` by hand from the paper’s equations; the pipeline does not generate gold from the PDF. Pairing is **only** by matching filenames (stem match; case-insensitive fallback on disk).

## Migrating from flat `data/papers/*.pdf` + `baseline_models/*.compmodel`

Example for one disease (repeat for each):

```bash
cd "$(dirname "$0")"   # phase 2/data — adjust path
mkdir -p diseases/covid
mv papers/covid.pdf diseases/covid/covid1.pdf
mv ../baseline_models/covid.compmodel diseases/covid/covid1.compmodel
```

Add `covid2.pdf` / `covid2.compmodel` in `diseases/covid/` when you have a second paper, and so on.

## Phase 3

Report folders look like `covid2_llm_openai_...`. Gold is resolved via **`find_gold_compmodel_for_run_stem`** (searches under `data/diseases/*/`, then legacy `data/<disease>/`).
