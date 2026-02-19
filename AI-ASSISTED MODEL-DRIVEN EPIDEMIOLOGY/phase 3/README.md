# Phase 3: Gap Detection, RAG Filling, and Validation

Phase 3 evaluates and enhances Phase 2's extracted models by comparing them against gold-standard baselines, filling missing parameters via RAG and LLM inference, and validating the accuracy of filled values.

## Pipeline overview

```
Phase 2 report  ──►  Gap Detection  ──►  Gap Filling  ──►  Validation  ──►  Reports
(extracted model)    (vs gold std)      (RAG + LLM)      (vs gold std)    (per-disease + overall)
```

### 1. Gap Detection
Compares the LLM-extracted model against the gold-standard baseline `.compmodel`. Identifies missing compartments, parameters, and stratifications. Falls back to promise-based comparison when no baseline exists.

### 2. Gap Filling (3-tier)
For each missing parameter:
1. **RAG** — searches the paper database (585 indexed parameters + 894 text chunks) by parameter name
2. **LLM Inference** — asks the same LLM provider used in Phase 2 (auto-detected from report dir name) to suggest a plausible value with reasoning
3. **Flag** — marks for manual review if both fail

### 3. Validation
Compares every RAG/LLM-filled value against the gold standard's actual value. Calculates per-parameter error and classifies as exact (<1%), close (<10%), approximate (<50%), or poor (>50%).

### 4. Model output
- **model_filled.compmodel** — the Phase 2 draft with filled parameter values applied (RAG/inference). So each Phase 3 run produces an improved model file per candidate.

### 5. Reporting
- Per-disease gap report with validation table
- Overall report aggregating all diseases and providers with accuracy metrics

## Directory layout

```
phase 3/
├── run_phase3.py              # Main entry point
├── build_database.py          # Builds paper database from Phase 1 + Phase 2
├── select_best_model.py       # Pick one best model per disease from multiple Phase 3 runs
├── README.md
├── src/
│   ├── rag/
│   │   ├── paper_database.py  # Load and query the database
│   │   └── parameter_lookup.py# Parameter search (index + text chunks)
│   ├── gap_analysis/
│   │   ├── gap_detector.py    # Gold-standard comparison + contextual checks
│   │   └── gap_filler_phase3.py # RAG → LLM → flag pipeline
│   ├── inference/
│   │   └── inference_engine.py# LLM inference with fallback defaults
│   ├── evaluation/
│   │   └── guess_evaluator.py # Compare fills vs gold standard values
│   └── reporting/
│       └── gap_report.py      # Per-disease + overall Markdown reports
├── data/
│   └── paper_database/        # Built by build_database.py
│       └── index.json
└── reports/                   # Output reports (one dir per disease/provider)
    ├── <disease>_<provider>_phase3/
    │   ├── phase3_gaps.json
    │   ├── phase3_filled.json
    │   ├── phase3_validation.json
    │   ├── model_filled.compmodel
    │   └── gap_report.md
    └── PHASE3_OVERALL_REPORT.md
```

## Usage

### Step 1: Build the paper database

Collects all papers, models, extracted entities, and parameters from Phase 1 and Phase 2 into a single searchable index.

```bash
cd "phase 3"
python3 build_database.py
```

### Step 2: Run Phase 3

**For all diseases and providers at once (recommended):**

```bash
python3 run_phase3.py --all --output reports
```

**For a single Phase 2 report:**

```bash
python3 run_phase3.py --phase2-report "../phase 2/reports/cholera_llm_gemini_20260211_201419" \
    --output reports/cholera_gemini_phase3
```

### Options

```bash
# Skip RAG (LLM inference + flag only)
python3 run_phase3.py --all --no-rag --output reports

# Skip LLM inference (RAG + flag only)
python3 run_phase3.py --all --no-inference --output reports

# Skip both (detection and reporting only)
python3 run_phase3.py --all --no-rag --no-inference --output reports
```

## LLM provider selection

Phase 3 automatically detects which LLM provider was used in Phase 2 from the report directory name (e.g. `cholera_llm_gemini_...` → uses Gemini for inference). This means each report's inference runs with the same provider that did the original extraction.

API keys are read from `phase 2/.api_key.txt` (same file Phase 2 uses).

Override from the command line (recommended, same as Phase 2):

```bash
python3 run_phase3.py --all --llm-provider gemini --output reports
python3 run_phase3.py --all --llm-provider openai --output reports
python3 run_phase3.py --all --llm-provider claude --output reports
```

Or set environment variable: `PHASE3_LLM_PROVIDER=openai` (or `gemini`, `claude`).

## Outputs

| File | Description |
|------|-------------|
| `phase3_gaps.json` | Detected gaps (missing compartments, parameters, stratifications) |
| `phase3_filled.json` | Per-gap fill attempts: source (rag/inference/flagged) and suggested value |
| `phase3_validation.json` | Accuracy of each fill vs gold standard (error %, quality rating) |
| `gap_report.md` | Human-readable per-disease report with validation table |
| `PHASE3_OVERALL_REPORT.md` | Aggregated summary across all diseases and providers |
| `model_filled.compmodel` | Phase 2 draft with RAG/inference parameter values applied |

## Workflow: three LLM runs + one best model per disease

To compare all three LLM providers and then pick the best filled model per disease (using Phase 2 evaluation and Phase 3 validation):

### 1. Run Phase 3 once per LLM provider

Use a **different output directory** for each provider so results are not overwritten. No hardcoded diseases or providers — all are discovered from your Phase 2 reports.

```bash
cd "phase 3"
python3 build_database.py

# Run with Gemini for filling (30 outputs: one per Phase 2 report)
python3 run_phase3.py --all --llm-provider gemini --output reports/gemini

# Run with OpenAI for filling
python3 run_phase3.py --all --llm-provider openai --output reports/openai

# Run with Claude for filling
python3 run_phase3.py --all --llm-provider claude --output reports/claude
```

You get **3 × (number of Phase 2 reports)** candidate dirs (e.g. 3×30 = 90 if you have 10 diseases × 3 extractors). Each candidate contains `model_filled.compmodel`, `phase3_gaps.json`, and `phase3_validation.json`.

### 2. Select the best model per disease

The selector uses **Phase 2 evaluation** (traceability, faithfulness, gaps, gold F1) as more important, then Phase 3 completeness and fill accuracy. It discovers all `*_phase3` dirs under the roots you pass and picks one candidate per disease.

```bash
python3 select_best_model.py \
  --phase2-reports "../phase 2/reports" \
  --phase3-roots reports/gemini reports/openai reports/claude \
  --output selected_models
```

Output:

- **selected_models/<disease>/model_filled.compmodel** — the chosen filled model for that disease
- **selected_models/<disease>/selection_report.json** — which candidate was chosen and its scores
- **selected_models/SELECTION_REPORT.md** — short summary for all diseases

No disease or provider names are hardcoded; they are inferred from directory names.

## Results report and how to read it

After running `run_phase3.py --all --output reports`, the main summary is:

- **`reports/PHASE3_OVERALL_REPORT.md`** — overall results

It contains:

1. **Database** — number of papers and parameters in the RAG index.
2. **Summary** — how many reports were analysed (diseases × providers), how many had zero gaps, total gaps, and fill counts (RAG / Inference / Flagged).
3. **Gap counts by disease and provider** — total gaps per (disease, provider) and how many were filled by RAG vs flagged.
4. **Gap breakdown** — counts of missing compartments, parameters, stratifications, interventions across all reports.
5. **Fill validation accuracy** — for each filled parameter, comparison to the gold-standard value:
   - **Exact**: &lt;1% error  
   - **Close**: &lt;10% error  
   - **Approximate**: &lt;50% error  
   - **Poor**: &gt;50% error  
   Accuracy (exact+close) and median relative error are reported; a table gives per-disease, per-provider accuracy.
6. **By provider** — aggregated gaps, RAG/inference/flagged counts, and accuracy per LLM provider (Gemini, OpenAI, Claude).
7. **Interpretation** — short explanation of what the numbers mean and where to find per-disease details.

Per-disease details (which parameters were filled, suggested values, and validation) are in:

- `reports/<disease>_<provider>_phase3/gap_report.md`
- `reports/<disease>_<provider>_phase3/phase3_validation.json`

## No hardcoded disease names

All disease names and providers are discovered dynamically from the data. Adding a new disease requires only:
1. Running Phase 2 for the new paper
2. Optionally adding a baseline `.compmodel` to `phase 2/data/baseline_models/`
3. Re-running `build_database.py` and `run_phase3.py --all`

## Dependencies

- Phase 2 outputs (report directories with extracted models)
- Phase 2 API key file (`phase 2/.api_key.txt`) for LLM inference
- No additional Python packages beyond Phase 2's requirements
