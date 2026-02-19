# AI-Assisted Model-Driven Epidemiology

A comprehensive framework for analyzing, extracting, and validating compartmental epidemiological models from scientific papers using AI assistance. The framework progresses through three phases: structural analysis of reference models, automated LLM-driven extraction from papers, and RAG-based gap filling with gold-standard validation.

## Project Overview

| Phase | Purpose | Key Technique |
|-------|---------|---------------|
| **Phase 1** | Analyze existing `.compmodel` files | Structural analysis, sensitivity analysis |
| **Phase 2** | Extract models from PDF papers | LLM-driven entity extraction + synthesis |
| **Phase 3** | Fill gaps and validate results | RAG lookup + LLM inference vs gold standard |

## End-to-End Workflow

```
 Phase 1                    Phase 2                     Phase 3
┌──────────┐   examples   ┌──────────────┐   models   ┌──────────────────┐
│ Baseline │ ──────────►  │ PDF → LLM →  │ ────────►  │ Gap Detection    │
│ .compmodel│             │ .compmodel   │            │ RAG + LLM Fill   │
│ analysis │              │ extraction   │            │ Gold-Std Validate│
└──────────┘              └──────────────┘            └──────────────────┘
     │                          │                            │
     ▼                          ▼                            ▼
  reports/                   reports/                     reports/
  model_analysis.json        model_draft.compmodel        phase3_gaps.json
  gap_report.json            phase2_final_report.json     phase3_filled.json
  sensitivity.json           traceability.json            PHASE3_OVERALL_REPORT.md
```

## Project Structure

```
AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/
├── requirements.txt              ← Unified dependencies
├── README.md                     ← This file
│
├── phase 1/                      ← Model Analysis
│   ├── run_phase1.py             ← Main script
│   ├── analysis/                 ← Analyzers (model, gap, uncertainty, sensitivity)
│   ├── papers/                   ← Baseline .compmodel files and PDFs
│   │   └── epimde/               ← Gold-standard models
│   └── reports/                  ← Analysis outputs
│
├── phase 2/                      ← Automated Extraction
│   ├── run_phase2.py             ← Main script
│   ├── .api_key.txt              ← LLM API key (Gemini/OpenAI/Claude)
│   ├── src/
│   │   ├── extraction/           ← PDF processing + entity extraction
│   │   ├── synthesis/            ← .compmodel generation
│   │   ├── analysis/             ← Gap analysis + filling
│   │   ├── evaluation/           ← Quality checks
│   │   └── utils/                ← LLM client (shared with Phase 3)
│   ├── data/
│   │   ├── papers/               ← Input PDFs
│   │   └── baseline_models/      ← Gold-standard .compmodel for validation
│   └── reports/                  ← One directory per disease/provider/timestamp
│
└── phase 3/                      ← RAG Gap Filling + Validation
    ├── run_phase3.py             ← Main script
    ├── build_database.py         ← Build paper database from Phase 1 + 2
    ├── src/
    │   ├── rag/                  ← Paper database + parameter lookup
    │   ├── gap_analysis/         ← Gold-standard comparison + 3-tier filler
    │   ├── inference/            ← LLM inference engine
    │   ├── evaluation/           ← Fill accuracy evaluator
    │   └── reporting/            ← Per-disease + overall Markdown reports
    ├── data/
    │   └── paper_database/       ← Searchable index (built by build_database.py)
    └── reports/                  ← Phase 3 outputs + PHASE3_OVERALL_REPORT.md
```

## Setup

### 1. Install Dependencies

```bash
cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY"
pip install -r requirements.txt
```

**Key packages:** `pdfplumber` (PDF extraction), `openai` (LLM API), `google-generativeai` (Gemini), `lxml`/`xmltodict` (XML), `pandas`/`numpy` (data), `matplotlib`/`seaborn` (plotting), `scipy` (sensitivity analysis).

### 2. Set Up API Key

Create `phase 2/.api_key.txt` with your LLM API key. Phase 3 reads the same file automatically.

```bash
echo "your-api-key-here" > "phase 2/.api_key.txt"
```

Supported providers: **Gemini** (default), **OpenAI**, **Claude**.

## Phase 1: Model Analysis

Analyzes existing `.compmodel` files to understand model structure, identify gaps, quantify uncertainty, and run sensitivity analysis.

### What it does

1. **Model Analysis** — parses `.compmodel` XML, extracts compartments, flows, parameters
2. **Gap Analysis** — identifies missing components based on expected patterns
3. **Uncertainty Analysis** — documents parameter values, sources, literature ranges
4. **Sensitivity Analysis** — tests output sensitivity to parameter changes (Morris, Grid, Random, Sobol)

### Running Phase 1

```bash
cd "phase 1"
python run_phase1.py --model papers/epimde/malaria.compmodel --output reports/malaria
```

### Outputs

- `model_analysis.json` — model structure summary
- `gap_report.json` — missing components
- `uncertainty_analysis.json` — parameter uncertainty
- `sensitivity_analysis.json` — sensitivity results

---

## Phase 2: Automated Model Extraction

Extracts compartmental models from scientific paper PDFs using a 9-step LLM-assisted pipeline.

### The 9-Step Pipeline

| Step | Name | Uses LLM? | Output |
|------|------|-----------|--------|
| 1 | PDF text extraction | No | `paper_text.json`, `paper_sections.json` |
| 2 | Paper promises | Yes (optional) | `paper_promises.json` |
| 3 | Entity extraction | Yes | `extracted_entities.json` |
| 4 | Model synthesis | No | `model_draft.compmodel` |
| 5 | Traceability | No | `traceability.json` |
| 6 | Gap analysis | No | `phase2_gap_report.json` |
| 7 | Gap filling | Yes | `gap_fill_suggestions.json` |
| 8 | Quality checks | No | `quality_checks.json` |
| 9 | Evaluation | No | `evaluation_report.json` |

The LLM receives paper text, metamodel schemas, and Phase 1 example models as context.

### Running Phase 2

```bash
cd "phase 2"
python run_phase2.py --paper data/papers/your_paper.pdf --output reports/your_paper
```

### LLM integration

- **Steps 2 & 3:** Extract what the paper promises and identify model entities (compartments, flows, parameters) with evidence
- **Step 7:** Suggest gap fills from domain knowledge
- **Fallback:** If LLM unavailable, falls back to pattern-based extraction (less accurate)
- **Temperature:** 0.3 (low for consistency)

---

## Phase 3: Gap Detection, RAG Filling, and Validation

Evaluates Phase 2's extracted models against gold-standard baselines, fills missing parameters using RAG and LLM inference, and measures accuracy.

### Pipeline

```
Phase 2 report  →  Gap Detection  →  Gap Filling  →  Validation  →  Reports
(extracted model)  (vs gold std)    (RAG + LLM)    (vs gold std)   (per-disease + overall)
```

### Gap Filling: 3-tier approach

1. **RAG** — searches indexed paper database (585+ parameters, 894+ text chunks) for matching values
2. **LLM Inference** — uses the same LLM provider from Phase 2 (auto-detected from report directory name) to suggest plausible values with reasoning
3. **Flag** — marks for manual review when both tiers fail

### Validation

Every filled value is compared against the gold-standard `.compmodel` value and classified:

| Match | Error |
|-------|-------|
| Exact | <1% |
| Close | <10% |
| Approximate | <50% |
| Poor | >50% |

### Running Phase 3

```bash
cd "phase 3"

# Build paper database (once, or when Phase 2 data changes)
python3 build_database.py

# Run for all diseases at once
python3 run_phase3.py --all --output reports
```

### Outputs

- Per-disease: `phase3_gaps.json`, `phase3_filled.json`, `phase3_validation.json`, `gap_report.md`, **`model_filled.compmodel`** (draft with filled parameters applied)
- Overall: `PHASE3_OVERALL_REPORT.md` with aggregated accuracy metrics

### Best model per disease (optional)

Run Phase 3 with each LLM provider into separate output dirs (`reports/gemini`, `reports/openai`, `reports/claude`), then run the selector to pick one filled model per disease using Phase 2 evaluation and Phase 3 validation (no hardcoded diseases or providers):

```bash
cd "phase 3"
python3 select_best_model.py --phase2-reports "../phase 2/reports" \
  --phase3-roots reports/gemini reports/openai reports/claude --output selected_models
```

Result: `selected_models/<disease>/model_filled.compmodel` plus `selection_report.json` and `SELECTION_REPORT.md`. See `phase 3/README.md` for the full workflow.

---

## Quick Start (Full Pipeline)

```bash
cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY"
pip install -r requirements.txt
echo "your-api-key" > "phase 2/.api_key.txt"

# Phase 1: Analyze baseline models
cd "phase 1"
python run_phase1.py --model papers/epimde/cholera.compmodel --output reports/cholera

# Phase 2: Extract from a new paper
cd "../phase 2"
python run_phase2.py --paper data/papers/cholera_paper.pdf --output reports/cholera_llm_gemini

# Phase 3: Build database, detect gaps, fill, validate
cd "../phase 3"
python3 build_database.py
python3 run_phase3.py --all --output reports
```

## Results

### Phase 3 overall report

After running Phase 3 with `--all`, the main results are in:

- **`phase 3/reports/PHASE3_OVERALL_REPORT.md`**

This report includes:

| Section | What it shows |
|--------|----------------|
| **Database** | Size of the RAG index (papers, parameters, chunks). |
| **Summary** | Number of reports (diseases × providers), gap-free reports, total gaps, and how many gaps were filled by **RAG**, **LLM inference**, or **flagged**. |
| **Gap counts by disease and provider** | For each (disease, provider), total gaps and fill breakdown (RAG vs flagged). |
| **Fill validation accuracy** | For each filled parameter, comparison to the gold-standard value: exact (&lt;1% error), close (&lt;10%), approximate (&lt;50%), poor (&gt;50%). Overall accuracy (exact+close) and a per-disease, per-provider table. |
| **By provider** | Aggregated gaps, RAG/inference/flagged counts, and accuracy for Gemini, OpenAI, and Claude. |
| **Interpretation** | Short guide to reading the numbers and where to find per-disease details. |

Per-disease reports (which parameters were filled, suggested values, validation table) are in:

- `phase 3/reports/<disease>_<provider>_phase3/gap_report.md`
- `phase 3/reports/<disease>_<provider>_phase3/phase3_validation.json`

See **`phase 3/README.md`** for a full description of the report and how to interpret it.

### Phase 1 and Phase 2 outputs

- **Phase 1:** `phase 1/reports/<name>/` — model_analysis.json, gap_report.json, uncertainty_analysis.json, sensitivity_analysis.json.
- **Phase 2:** `phase 2/reports/<disease>_llm_<provider>_<timestamp>/` — model_draft.compmodel, phase2_final_report.json, traceability.json, etc.

---

## Adding a New Disease

No code changes required:

1. Place the PDF in `phase 2/data/papers/`
2. Optionally add a gold-standard `.compmodel` to `phase 2/data/baseline_models/`
3. Run Phase 2 for the new paper
4. Re-run `build_database.py` and `run_phase3.py --all`

## Key Principles

- **Faithfulness** — only extract what papers explicitly describe
- **Evidence-Based** — every entity has a text span and page number
- **Traceability** — every model element links back to paper evidence
- **Dynamic** — no hardcoded disease names; fully data-driven
- **Honest Evaluation** — gold standard used only for validation, not filling
- **Multi-Provider** — works with Gemini, OpenAI, or Claude; Phase 3 auto-matches the Phase 2 provider

## Documentation

- `phase 1/README.md` — Phase 1 details
- `phase 2/README.md` — Phase 2 details
- `phase 2/INSTRUCTIONS.md` — step-by-step Phase 2 guide
- `phase 3/README.md` — Phase 3 details (gap detection, RAG, LLM inference, validation)
