# Phase 3: RAG and Gap Filling

Phase 3 enhances extracted models (from Phase 2) with **RAG** (parameter lookup from a paper database) and **intelligent gap filling** (AI inference when values are missing). It implements the roadmap tasks **7.1–7.3** (paper database, RAG lookup) and **8.1–8.4** (gap detection with required vs optional logic, gap filling, evaluation, reporting).

## Goals

- **Task 7.1–7.3:** Build a searchable paper database from Phase 2 reports and support parameter lookup (RAG).
- **Task 8.1:** Detect gaps using **required vs optional** logic (only flag gaps when the paper promises something the model doesn’t have).
- **Task 8.2:** Fill gaps with a 3-tier approach: (1) RAG search, (2) intelligent inference (LLM + defaults), (3) flag for manual review.
- **Task 8.3:** Evaluate guess quality when ground-truth values are available.
- **Task 8.4:** Generate human-readable gap reports.

## Directory layout

```
phase 3/
├── run_phase3.py           # Main entry point
├── README.md
├── src/
│   ├── rag/                # Paper database and RAG
│   │   ├── paper_database.py   # Build index from Phase 2 reports
│   │   └── parameter_lookup.py # Query papers for parameter values
│   ├── gap_analysis/       # Gap detection and filling
│   │   ├── gap_detector.py     # Required vs optional gap detection
│   │   └── gap_filler_phase3.py # RAG → inference → flag
│   ├── inference/          # Intelligent inference
│   │   └── inference_engine.py # LLM + default library for missing params
│   ├── evaluation/         # Guess quality
│   │   └── guess_evaluator.py  # Compare guesses to actual values
│   └── reporting/
│       └── gap_report.py       # Markdown gap report
└── data/
    └── paper_database/     # Index and chunks (built by --build-db)
```

## Usage

### 1. Run from a Phase 2 report directory

Uses `model_draft.compmodel`, `paper_promises.json`, and `extracted_entities.json` from that report.

```bash
cd "phase 3"
python run_phase3.py --phase2-report "../phase 2/reports/cholera_llm_gemini_20260211_201419" --output reports/cholera_phase3
```

### 2. Build paper database and run

Build the RAG index from all Phase 2 reports (sibling of the given report dir), then run gap analysis:

```bash
python run_phase3.py --phase2-report "../phase 2/reports/cholera_llm_gemini_20260211_201419" \
    --paper-db data/paper_database --build-db --output reports/cholera_phase3
```

### 3. Skip RAG or inference

```bash
# No RAG (only inference + flag)
python run_phase3.py --phase2-report "../phase 2/reports/..." --no-rag --output reports/out

# No LLM inference (only RAG + flag)
python run_phase3.py --phase2-report "../phase 2/reports/..." --no-inference --output reports/out
```

## Outputs

- **`phase3_gaps.json`** — Detected gaps (missing compartments, parameters, stratifications, interventions) and required/optional logic.
- **`phase3_filled.json`** — Per-gap suggestions: source (rag / inference / flagged) and suggested value or action.
- **`gap_report.md`** — Human-readable summary and suggestions.

## Required vs optional logic (Task 8.1)

- **Vector-borne:** If the paper describes a vector-borne model (e.g. dengue, malaria) and the model has no vector/mosquito compartments → **gap**.
- **Stratification:** Only a gap if the paper explicitly promises stratification (e.g. “age-stratified”) and the model has none.
- **Simple model:** If the paper says “simple SEIR” (or similar), stratification is not required → no gap for “missing stratification”.
- **Compartments / parameters / interventions:** Gaps only for items that appear in **paper promises** and are missing in the extracted model.

## Dependencies

- Phase 2 outputs (report directory with `model_draft.compmodel`, `paper_promises.json`, `extracted_entities.json`, `paper_text.json`).
- For **LLM inference:** same API keys as Phase 2 (e.g. `phase 2/.api_key.txt` or `GEMINI_API_KEY` / `ANTHROPIC_API_KEY`). Optional env: `PHASE3_LLM_PROVIDER=gemini` (default) or `claude`.

No extra Python packages beyond Phase 2.
