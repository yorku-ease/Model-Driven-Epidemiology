# Phase 2: Automated model extraction from papers

Phase 2 turns a scientific PDF into a structured compartmental `.compmodel` (XML), plus traceability and evaluation against baseline models when a matching gold standard exists. **Gap detection and gap filling** for the benchmark are implemented in **Phase 3** (Rule-Based Retrieval, LLM completion, validation against gold).

**Benchmark layout:** Papers live under `data/diseases/<disease>/<stem>.pdf` (e.g. `covid2.pdf`) paired with a hand-authored gold `<stem>.compmodel`. The report folder prefix equals the PDF stem (`covid2_llm_openai_<timestamp>/`). Gold is resolved automatically.

**Corpus mode:** If papers are registered in Phase 1 (`phase 1/data/papers/collection_index.json`), use `--paper-id` so PDF and gold paths resolve via `src/utils/phase2_paths.py`. See [INSTRUCTIONS.md](INSTRUCTIONS.md) for the exact command and [data/README.md](data/README.md) for layout.

**Existing reports (paper 1 of each disease):** Reports for the first paper of each disease are already in `reports/` with a `1` suffix (e.g. `covid1_llm_openai_<timestamp>/`). To process papers 2 and 3, see Running Phase 2 below.

## Results summary (benchmark: 10 diseases × 3 papers, fuzzy matching)

Evaluated across 30 benchmark papers using fuzzy string matching (`evaluation_report.json` from `run_phase2.py`).
Gold standard = hand-authored `.compmodel` files under `data/diseases/<disease>/`.

| Provider | Avg Compartment Recall | Avg Flow Recall |
|----------|----------------------|-----------------|
| OpenAI (GPT-4o) | 0.75 | 0.52 |
| Gemini (2.5 Pro) | 0.81 | 0.59 |
| Claude (3.5 Sonnet) | 0.81 | 0.58 |
| Best per disease | 0.84 | 0.66 |

Full per-disease tables: see [RESULTS.md](RESULTS.md). Regenerate with `build_results_md.py` as documented in [INSTRUCTIONS.md](INSTRUCTIONS.md).
Phase 3 (Rule-Based Retrieval + gap filling) improves these to 0.94 compartment recall / 0.80 flow recall - see `phase 3/README.md`.

---

## Principles

- Faithfulness: extract only what the paper explicitly describes or clearly promises.
- Epidemiology scope: prompting and validation use `../phase 1/metamodel_epidemiology.json` (disease models only).
- Providers: OpenAI, Google Gemini, or Anthropic Claude (`--llm-provider`). Defaults and env overrides are documented in INSTRUCTIONS.md.

## What the pipeline produces

For each run, outputs go under `reports/{disease}_llm_{provider}_{timestamp}/`. The most important files:

| Artifact | Role |
|----------|------|
| `model_draft.compmodel` | Extracted compartmental model |
| `phase2_final_report.json` | End-to-end summary |
| `evaluation_report.json` | Traceability, faithfulness; `gold_standard_comparison` (P/R/F1) if a baseline was found |
| `extracted_entities.json`, `traceability.json`, `paper_sections.json` | Evidence and intermediate structure |

Aggregated Markdown tables from evaluation JSON are produced with `build_results_md.py`; see [INSTRUCTIONS.md](INSTRUCTIONS.md).

**How to run** (single PDF, shell loops over papers, benchmark PDFs under `data/diseases/`, corpus `--paper-id`, providers): [INSTRUCTIONS.md](INSTRUCTIONS.md).

## Pipeline shape (7 steps)

1. **PDF** - cleaned text, sections, tables (`paper_text.json`, `paper_sections.json`).
2. **Paper promises** (pattern-only) - `paper_promises.json`.
3. **Entity extraction** (LLM when a key is available, else patterns) - `extracted_entities.json`.
4. **Synthesis** - `model_draft.compmodel`.
5. **Traceability** - `traceability.json`.
6. **Quality checks** (Phase 1 analyzers when configured) - `quality_checks.json`.
7. **Evaluation** vs optional baseline - `evaluation_report.json`.

## Context loaded automatically

When paths exist, Phase 2 loads the epidemiology metamodel and example models from Phase 1 (`--phase1-dir`) to steer extraction and quality checks.

| Phase 1 path | Phase 2 flag | Used for |
|--------------|--------------|----------|
| `../phase 1/metamodel_epidemiology.json` | `--metamodel` | Prompts and synthesis (default points here) |
| `../phase 1/papers/epimde/*.compmodel` | `--phase1-dir` | Optional example context for entity extraction (if folder exists) |

Quality checks invoke Phase 1 analyzers via `--phase1-dir`.

---

## Methodology and implementation notes

### 1. How the extraction patterns were chosen

**Paper promises (Step 2):** The promise extractor uses hand-written regex lists in `src/extraction/paper_promise_extractor.py` - e.g. phrases like "compartments include", SEIR/SIR keywords, stratification and intervention keywords. There is no separate systematic literature review driving those regexes; they are engineering heuristics informed by common paper wording.

**Entity extraction:** Additional pattern passes and keyword lists are in `src/extraction/entity_extractor.py` (plus LLM prompts when `--use-llm`). Optional Phase 1 assets such as `phase 1/reports/patterns/pattern_library.json` may be loaded elsewhere for documentation context, but the default extraction patterns are not auto-mined from a corpus - they are manual, iterative code.

### 2. LLM memory and learning across papers

Each API call is stateless unless the client explicitly sends chat history. Phase 2 uses one-shot (or chunked) calls per step; processing paper A does not change the model weights or state for paper B. There is no automatic "learning" from analyzing more PDFs. Quality improvements come from better prompts, better baselines, or better post-processing - not from the LLM retaining prior runs. Reusing Phase 1 JSON outputs as context in later steps is explicit retrieval, not model memory.

---

## Evaluation outputs (markdown)

| File | Role |
|------|------|
| [RESULTS.md](RESULTS.md) | **Primary benchmark table** — fuzzy matching vs gold (`evaluation_report.json`), recall-first, 3 papers × 10 diseases. |
| `RESULTS_REPORT.md` | Generated by `build_results_md.py` (default reads `evaluation_report.json`; use `-e` if you use another filename). |

**Evaluator:** `src/evaluation/evaluator.py` compares extracted entities to the gold `.compmodel` using **fuzzy string matching** (no embedding models).

## Documentation

| File | Role |
|------|------|
| [INSTRUCTIONS.md](INSTRUCTIONS.md) | **How to run:** setup, API keys, commands, flags, troubleshooting, optional GROBID |
| [build_results_md.py](build_results_md.py) | Aggregate per-report `evaluation_report.json` into a markdown summary (see INSTRUCTIONS) |
