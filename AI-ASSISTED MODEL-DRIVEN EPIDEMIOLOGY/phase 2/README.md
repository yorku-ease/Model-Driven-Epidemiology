# Phase 2: Automated model extraction from papers

Phase 2 turns a scientific **PDF** into a structured compartmental **`.compmodel`** (XML), plus traceability, gap analysis, optional gap-fill suggestions, and evaluation against **baseline** models when `data/baseline_models/` contains a matching gold standard.

**Benchmark layout:** Papers live under `data/diseases/<disease>/<stem>.pdf` (e.g. `covid2.pdf`) paired with a hand-authored gold `<stem>.compmodel`. The report folder prefix equals the PDF stem (`covid2_llm_openai_<timestamp>/`). Gold is resolved automatically.

**Corpus mode:** If papers are registered in Phase 1 (`phase 1/data/papers/collection_index.json`), run with  
`python run_phase2.py --paper-id <id> ...`  
so PDF and gold paths resolve via `src/utils/phase2_paths.py` (see **`data/README.md`**).

**Existing reports (paper 1 of each disease):** Reports for the first paper of each disease are already in `reports/` with a `1` suffix (e.g. `covid1_llm_openai_<timestamp>/`). To process papers 2 and 3, see **Running Phase 2** below.

## Principles

- **Faithfulness:** Extract only what the paper explicitly describes or clearly promises.
- **Epidemiology scope:** Prompting and validation use **`../phase 1/metamodel_epidemiology.json`** (disease models only).
- **Providers:** OpenAI, Google Gemini, or Anthropic Claude (`--llm-provider`). Defaults and env overrides are documented in **INSTRUCTIONS.md** (not here).

## What the pipeline produces

For each run, outputs go under `reports/{disease}_llm_{provider}_{timestamp}/`. The files that matter most:

| Artifact | Role |
|----------|------|
| `model_draft.compmodel` | Extracted compartmental model |
| `phase2_final_report.json` | End-to-end summary |
| `evaluation_report.json` | Traceability, faithfulness, gaps; **gold_standard_comparison** (P/R/F1) if a baseline was found |
| `extracted_entities.json`, `traceability.json`, `paper_sections.json` | Evidence and intermediate structure |

Aggregated metrics: `python3 build_results_md.py` → Markdown summary from the chosen evaluation JSON in each latest run under `reports/`. The report is **recall-first**.

## Running Phase 2 on papers 2 and 3

Paper 1 of each disease has already been processed (reports named `<disease>1_llm_<provider>_<timestamp>/`). To run papers 2 and 3:

```bash
cd "phase 2"
# Activate venv if used: source "../venv/bin/activate"

# Single paper
python3 run_phase2.py \
    --paper data/diseases/covid/covid2.pdf \
    --llm-provider openai \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"
# Creates: reports/covid2_llm_openai_<timestamp>/   gold auto-resolved from data/diseases/covid/covid2.compmodel

# All papers ending in 2 or 3 (bash glob covers all disease folders)
for pdf in data/diseases/*/*[23].pdf; do
  python3 run_phase2.py \
      --paper "$pdf" \
      --llm-provider openai \
      --phase1-dir "../phase 1" \
      --prior-models-dir "../phase 1/reports/model_analysis"
done

# With Gemini
for pdf in data/diseases/*/*[23].pdf; do
  python3 run_phase2.py \
      --paper "$pdf" \
      --llm-provider gemini \
      --phase1-dir "../phase 1" \
      --prior-models-dir "../phase 1/reports/model_analysis"
done
```

**Note:** The Cholera folder on disk has an extra leading space (` cholera/`). The glob `data/diseases/*/*[23].pdf` still matches it correctly via shell expansion.

- **Recommended for paper numbers (fuzzy):** `-e evaluation_report_fuzzy_temp.json`.
- **Canonical report naming in this project:**  
  - `RESULTS_REPORT Enhanced Fuzzy.md` (from `reports/`, fuzzy evaluation)  
  - `RESULTS_REPORT Original Fuzzy.md` (from `old-reports/`, fuzzy evaluation)

## Pipeline shape (9 steps)

1. **PDF** → cleaned text, sections, tables (`paper_text.json`, `paper_sections.json`).
2. **Paper promises** (pattern-only) → `paper_promises.json`.
3. **Entity extraction** (LLM when a key is available, else patterns) → `extracted_entities.json`.
4. **Synthesis** → `model_draft.compmodel`.
5. **Traceability** → `traceability.json`.
6. **Gap analysis** → `phase2_gap_report.json` *(empty unless `--enable-gap-steps`)*.
7. **Gap-fill suggestions** → `gap_fill_suggestions.json` *(same)*.
8. **Quality checks** (Phase 1 analyzers when configured) → `quality_checks.json`.
9. **Evaluation** vs optional baseline → `evaluation_report.json`.

## Context loaded automatically

When paths exist, Phase 2 loads the epidemiology metamodel and example models from Phase 1 (`--phase1-dir`, `--prior-models-dir`) to steer extraction and gap filling.

| Phase 1 path | Phase 2 flag | Used for |
|--------------|--------------|----------|
| `../phase 1/metamodel_epidemiology.json` | `--metamodel` | Prompts & synthesis (default points here) |
| `../phase 1/papers/epimde/*.compmodel` | `--phase1-dir` | Optional **example** context for entity extraction (if folder exists) |
| `../phase 1/reports/model_analysis/*_analysis.json` | `--prior-models-dir` | **Only when** `--enable-gap-steps` runs Step 7 (prior-model suggestions) |

**Gap steps (6–7) are off by default** — see below. Quality checks (Step 8) still invoke Phase 1 analyzers via `--phase1-dir`.

---

## Methodology & limitations (FAQ)

### 1. How were the **patterns** chosen?

- **Paper promises (Step 2):** Hand-written **regex** lists in `src/extraction/paper_promise_extractor.py` (`extract_with_patterns`) — e.g. phrases like “compartments … include”, SEIR/SIR keywords, stratification and intervention keywords. There is **no** separate systematic literature review driving those regexes; they are **engineering heuristics** informed by common paper wording.
- **Entity extraction:** Additional **pattern** passes and keyword lists live in `src/extraction/entity_extractor.py` (plus LLM prompts when `--use-llm`). Optional **Phase 1** assets such as `phase 1/reports/patterns/pattern_library.json` may be loaded elsewhere for documentation/RAG context, but the **default** extraction patterns are **not** auto-mined from a corpus — they are **manual / iterative** code.

### 2. **Step 7 (gap filler)** — examples, LLM behavior, ablations

- **Default pipeline:** Steps **6–7 are skipped**; `phase2_gap_report.json` and `gap_fill_suggestions.json` are **empty stubs**. This avoids extra LLM calls and kept the main extraction path stable. To **actually run** gap analysis + gap fill:  
  `python run_phase2.py --paper YOUR.pdf --enable-gap-steps`  
  (Optionally set `--prior-models-dir` to Phase 1 `reports/model_analysis/`.)
- **Implementation (`src/analysis/gap_filler.py`):** For each gap, suggestions come from (1) **text search** in the paper, (2) **prior Phase 1 JSONs** (string match on compartment/parameter names — **not** injected as numbered few-shot *examples* inside the domain-knowledge LLM prompt), (3) **LLM** with a **format-only** prompt (JSON schema + rules) — **no** multi-example “here are 3 exemplar answers” block in the current code. So the INSTRUCTIONS-style narrative about “examples from prior models” in the **LLM** prompt is **misleading**; prior models are a **separate** suggestion channel.
- **Ablations:** The repository does **not** ship results for “prompt without examples” vs “with examples” for Step 7, because the production LLM path is **zero-shot** JSON. Any study of **last-example bias** would require a **controlled experiment** (same gaps, vary prompts) — not pre-computed here.

### 3. Do LLMs **remember** across papers? Improvement with more models?

- **No persistent memory:** Each API call is **stateless** unless the client sends **chat history**. Phase 2 uses **one-shot** (or chunked) calls per step; processing **paper A** does not change the model weights or hidden state for **paper B**.
- **No automatic “learning”** from analyzing more PDFs: quality gains come from **better prompts, baselines, or post-processing**, not from the LLM “remembering” prior runs. You can **reuse** outputs (e.g. Phase 1 JSON) as **context** in later steps — that is explicit **retrieval**, not model memory.

---

## Where to go next

| Document | Contents |
|----------|----------|
| **[INSTRUCTIONS.md](INSTRUCTIONS.md)** | Setup, API keys, commands, all CLI flags, troubleshooting, step-by-step I/O, optional **GROBID**, and **before/after pipeline comparison** (appendix). |
| **[RESULTS_REPORT.md](RESULTS_REPORT.md)** | Auto-generated tables, **recall-primary** (run `build_results_md.py`). |

---

*For runnable examples and provider-specific settings, open **INSTRUCTIONS.md**.*
