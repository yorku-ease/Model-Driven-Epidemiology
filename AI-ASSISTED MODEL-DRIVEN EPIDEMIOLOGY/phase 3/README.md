# Phase 3: Gap detection, RAG filling, and validation

Phase 3 takes Phase 2 report directories (extracted `.compmodel` drafts), compares them to **gold-standard baselines**, optionally **fills** missing numeric/structural gaps using a **paper database (RAG)** and **LLM inference**, and **validates** filled values against the baseline.

## Pipeline

```
Phase 2 report  ──►  Gap detection  ──►  Gap filling  ──►  Validation  ──►  Reports
(extracted model)    (vs gold or       (RAG + LLM)      (vs gold std)    (per run + selection)
                      promises)
```

1. **Gap detection** — Missing **compartments**, **parameters**, **stratifications**, and **flows** (`Source->Target` from XML / `extracted_entities`) vs baseline (preferred) or vs `paper_promises.json` (fallback).
2. **Gap filling (3-tier)** — **Parameters:** RAG → LLM inference → flag. **Compartments / flows:** RAG (`structure_lookup`: text evidence + `flow_index`) → LLM (`infer_compartment_llm` / `infer_flow_llm`) → flag. New compartment shells may be written to `model_filled.compmodel`; **flow wiring** is left to manual edit or **Phase RLM** (full-XML repair).
3. **Validation** — **Numeric:** each filled **parameter value** vs gold (exact / close / approximate / poor). **Structural:** precision / recall / F1 for **compartments** and **flows** between draft/filled model and gold `.compmodel` (both appear in `phase3_validation.json` and `gap_report.md`). For cross-phase comparisons in this project, we report **fuzzy recall** as the primary metric.
4. **Outputs** — `model_filled.compmodel`, `phase3_gaps.json`, `phase3_filled.json`, `phase3_validation.json`, `phase3_improvement.json`, and `gap_report.md` per run.
5. **Showcase comparison (optional)** — `run_phase3_showcase.py` compares `rag_only` / `llm_only` / `both` on the best Phase 2 report per disease from `../phase 2/reports/` (enhanced pipeline).

---

## Methodology

This section answers how Phase 3 is implemented end-to-end: what “gold” means, how the database is built, what RAG and inference do, and how text is chunked.

### 1. How the gold standard is built (and how it is discovered)

The **gold standard** for gap detection and for **validation** is **not** computed automatically from PDFs. It is a **reference compartmental model** in `.compmodel` (XML) form, treated as ground truth for **structure** (compartment, parameter, stratification **names**) and for **parameter expressions/values** when validating fills.

**Discovery order** (`run_phase3.py` → `_find_gold_standard`):

1. **`phase 2/data/baseline_models/*.compmodel`** — First file whose stem matches the inferred **disease** name (substring match, case-insensitive).
2. Else **`phase 1/papers/**/*.compmodel`** — Same substring match on the filename stem.

**What is loaded:**

- For **gap detection**, the XML is parsed into lists of compartment, parameter, and stratification **names** (`load_model_structure`).
- For **validation of numeric fills**, the same file is parsed for full **parameter** records (name, expression/value, unit, description) (`_load_gold_with_values`).

**Important:** Gold files are **curated inputs** (manually authored or imported models placed under `baseline_models/` or Phase 1). Phase 3 does not derive them from the LLM extraction alone.

### 2. Promise-based comparisons — when they are used and why you may see “none”

Gap detection supports two modes (`gap_detector.detect_gaps`):

| Mode | When | What is compared |
|------|------|------------------|
| **`gold_standard`** | A baseline `.compmodel` was found for the disease | Extracted entities + draft model vs **gold lists** (fuzzy name matching). Gold-only items become “missing_*” gaps; extras vs gold are recorded where applicable. |
| **`promises`** | **No** baseline file was found | Same extracted/model union vs **`paper_promises.json`** (compartments, parameters, stratifications, interventions as promised by Phase 2). |

**Why aggregate reports often show “0 promise-based”:** If every disease in your study has a matching file under `phase 2/data/baseline_models/` (or Phase 1), **gold mode is always used** and promise mode is never selected for those runs. Promise mode is a **fallback** when no gold file exists or naming does not match.

**Note:** For promise mode to be meaningful, `paper_promises.json` must be **populated**; empty or sparse promises yield few or no gaps.

### 3. Database: “54 entries” — are these papers? How many per disease?

The **paper database** is the JSON index written by `build_database.py` to `data/paper_database/index.json`. The field **`num_entries`** (e.g. **54**) counts **indexed records**, not “54 PDF files” as a 1:1 mapping.

Each **entry** is one logical bundle, for example:

- **Phase 1:** A `.compmodel` under `phase 1/papers/**` (plus linked analysis/uncertainty JSON when present), and/or standalone PDFs not paired to a model.
- **Phase 2:** **One entry per (disease × provider)** using the **latest** Phase 2 report directory for that pair (includes `paper_text`-derived chunks, `extracted_entities.json`, `model_structure` from `model_draft.compmodel`, promises, evaluation snippets, etc.).
- **Phase 2 baselines:** One entry per **`phase 2/data/baseline_models/*.compmodel`**.

The index also stores **`knowledge_base`**: Phase 1 global assets (pattern library, taxonomies, protocols, paper collection metadata, etc.) — separate from the per-entry list.

**Typical scale (example from a built index):** Total **54** entries across **10** diseases → about **5–7 entries per disease** (mix of Phase 1 pieces, three Phase-2 provider snapshots, and baseline rows — exact counts depend on what exists on disk).

### 4. What is included for RAG

RAG in Phase 3 is **retrieval from the built index**, not a live vector DB in the default path. After `build_database.py`, the index contains:

- **`parameter_index`:** Flat list of parameter records (name, value/expression, unit, description, disease, source phase, `paper_id`) aggregated from:
  - Parsed **`model_structure.parameters`** on `.compmodel` files,
  - **`extracted_entities.parameters`** from Phase 2 reports,
  - **`uncertainty.parameters`** where present.
- **Text `chunks`:** For Phase 2 entries that have `paper_text.json`, the **`full_text`** field is split into overlapping character windows (see §7). Chunks are used for keyword search and regex value extraction.
- **Structured fields per entry:** e.g. `extracted_entities`, `promises`, `evaluation` subsets, `analysis`, `sensitivity` — as emitted by `build_database.py`.
- **`knowledge_base`:** Phase 1 JSON assets (patterns, taxonomies, extraction protocols, etc.) for possible downstream use.

**Gap filling does not peek at gold values:** The gold `.compmodel` is used for **which** gaps exist and for **post-hoc validation**, not as the source of suggested numbers during RAG/inference (`gap_filler_phase3.py`).

### 5. How inference works

If RAG does not produce a usable value for a **missing parameter** gap, **Tier 2** runs (`src/inference/inference_engine.py`):

1. **LLM path:** A short prompt asks the configured provider (same family as Phase 2: Gemini / OpenAI / Claude via `phase 2`’s `LLMClient`) for a **numeric** `value`, optional `unit`, and brief `reasoning` in **strict JSON**. The **first ~800 characters** of the paper full text are passed as extra context.
2. If the client is unavailable or parsing fails, a **fallback** uses a small **keyword → default** library (e.g. incubation/recovery-style names → typical ranges) with **LOW** confidence.

Inferred values are labeled as **low confidence** and should be verified against literature.

### 6. What “flagged” means

**Flagged** is **Tier 3** of gap filling (`source: "flagged"` in `phase3_filled.json`):

- RAG did **not** return an `extracted_value`, **and**
- LLM inference did **not** return a usable value/reasoning,

**or** the gap is **not** a missing parameter (missing compartments / stratifications / interventions are not filled by the current RAG+LLM path in the same way — they get a **manual_review** style suggestion).

So “flagged” means **no automatic fill**: human review or another pipeline is expected.

### 7. Text chunks: PDF pages vs finer granularity

Chunks are **not** “one chunk per PDF page.”

`build_database.py` uses **`_chunk_text`**: sliding **character** windows of default **2000** characters with **300** characters **overlap**, over the **concatenated `full_text`** string from `paper_text.json` (word-level bounding boxes are **not** loaded).

So resolution is **sub-page / multi-sentence** text segmentation, not page boundaries. Retrieval ranks chunks by **keyword hits** on parameter names (with Greek/Latin variants) plus a disease match boost (`parameter_lookup.search_text_chunks`).

### 8. RAG parameter lookup (mechanics)

For a missing parameter, `parameter_lookup` (`src/rag/parameter_lookup.py`):

1. **Structured:** Score matches in **`parameter_index`** by normalized name (Greek/Latin folding), prefer same disease and non-empty values.
2. **Unstructured:** Search **text chunks** for the parameter symbol/name; optionally **regex-extract** a numeric value from the chunk (`extract_value_from_chunk`).
3. If regex finds a value in a chunk, that becomes the RAG **`extracted_value`**; else a strong **index** hit’s value may be used.

This is **lexical / regex RAG** in the current implementation.

---

## Directory layout

```
phase 3/
├── run_phase3.py              # Main entry
├── build_database.py          # Index Phase 1 + 2 for RAG
├── run_phase3_showcase.py     # Best Phase 2 per disease + 3-mode comparison
├── src/                       # rag, gap_analysis, inference, evaluation, reporting
├── data/paper_database/       # Built index (index.json)
├── reports/                   # Per-disease/provider Phase 3 runs
└── showcase_phase3/           # Showcase outputs: per-mode runs + summary report
```

## Main artifacts

| File | Role |
|------|------|
| `phase3_gaps.json` | Detected gaps |
| `phase3_filled.json` | Fill source + suggested values |
| `phase3_validation.json` | Error % and quality label per fill |
| `model_filled.compmodel` | Draft with fills applied |
| `gap_report.md` | Human-readable per-disease summary |
| `showcase_phase3/SHOWCASE_REPORT.md` | Aggregated view across rag_only / llm_only / both |

## Reading aggregate results

After a showcase run, `SHOWCASE_REPORT.md` summarizes per-disease winners across `rag_only` / `llm_only` / `both`, plus key metrics and the selected Phase 2 source per disease.

## Adding diseases

Nothing is hardcoded: providers and diseases are inferred from directory names. New work: run Phase 2 → add or name a baseline in `phase 2/data/baseline_models/` so gold discovery succeeds → `build_database.py` → Phase 3.

## Documentation

| File | Purpose |
|------|---------|
| **[INSTRUCTIONS.md](INSTRUCTIONS.md)** | Commands, CLI flags, multi-provider workflow, API keys, dependencies. |
| **`run_phase3_showcase.py`** | **Showcase:** per disease, **best Phase 2** among **gemini / openai / claude** (by eval score), then × **rag_only / llm_only / both** with **your** Phase 3 **`--llm-provider`**. See INSTRUCTIONS.md. |

---

## Enhancements & integration with Phase RLM

**Why Phase RLM matters:** Phase 3 fills **values** and suggests **labels** and **flow narratives**; **Phase RLM** (`phase_rlm/`) is designed for **iterative structural repair** of `.compmodel` XML (flows, self-referential contacts, populations, etc.) using semantic search over the paper. A practical pipeline is:

1. Run Phase 3 (RAG + inference + structural validation) → inspect `phase3_validation.json` (parameter accuracy + compartments/flows quality metrics).
2. Run **Phase RLM** on the same Phase 2 report when XML structure is still invalid or flows are incomplete.
3. Re-run Phase 3 **evaluation** on the RLM output if you add a script, or compare `gap_report.md` before/after.

**Database (`build_database.py`):** Re-run after Phase 1/2 changes. The index now includes **`flow_index`** (signatures from all indexed `.compmodel` files), **`version`** 3, and Phase 1’s **`data/papers/*/metadata.json`** entries plus **`papers/epimde/`** as the primary model directory.

**Future improvements (ideas):** stronger flow retrieval/ranking; automatic XML flow insertion with validated compartment indices; joint scoring in Phase 4 reports.
