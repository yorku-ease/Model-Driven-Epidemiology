# Phase 3: Gap detection, Rule-Based Retrieval filling, and validation

Phase 3 takes Phase 2 report directories (extracted `.compmodel` drafts), compares them to **gold-standard baselines**, optionally **fills** missing numeric/structural gaps using a **paper database (Rule-Based Retrieval)** and **LLM inference**, and **validates** filled values against the baseline.

## Pipeline

```
Phase 2 report  ──►  Gap detection  ──►  Gap filling  ──►  Validation  ──►  Reports
(extracted model)    (vs gold or       (Retrieval + LLM)      (vs gold std)    (per run + selection)
                      promises)
```

1. **Gap detection** - Missing **compartments**, **parameters**, **stratifications**, and **flows** (`Source->Target` from XML / `extracted_entities`) vs baseline (preferred) or vs `paper_promises.json` (fallback).
2. **Gap filling (3-tier)** - **Parameters:** Rule-Based Retrieval → LLM inference → flag. **Compartments / flows:** Rule-Based Retrieval (`structure_lookup`: text evidence + `flow_index`) → LLM (`infer_compartment_llm` / `infer_flow_llm`) → flag. New compartment shells may be written to `model_filled.compmodel`; **flow wiring** is left to manual edit or **Phase RLM** (full-XML repair).
3. **Validation** - **Numeric:** each filled **parameter value** vs gold (exact / close / approximate / poor). **Structural:** precision / recall / F1 for **compartments** and **flows** between draft/filled model and gold `.compmodel` (both appear in `phase3_validation.json` and `gap_report.md`). For cross-phase comparisons in this project, we report **fuzzy recall** as the primary metric.
4. **Outputs** - `model_filled.compmodel`, `phase3_gaps.json`, `phase3_filled.json`, `phase3_validation.json`, `phase3_improvement.json`, and `gap_report.md` per run.
5. **Showcase comparison (optional)** - `run_phase3_showcase.py` compares `retrieval_only` (Rule-Based Retrieval only) / `llm_only` / `both` on the best Phase 2 report per disease from `../phase 2/reports/` (enhanced pipeline).

---

## Methodology

This section answers how Phase 3 is implemented end-to-end: what “gold” means, how the database is built, what Rule-Based Retrieval and inference do, and how text is chunked.

### 1. How the gold standard is built (and how it is discovered)

The **gold standard** for gap detection and for **validation** is **not** computed automatically from PDFs. It is a **reference compartmental model** in `.compmodel` (XML) form, treated as ground truth for **structure** (compartment, parameter, stratification **names**) and for **parameter expressions/values** when validating fills.

**Discovery order** (`run_phase3.py` → `_find_gold_standard` → `find_gold_compmodel_for_run_stem` in `phase 2/src/utils/phase2_paths.py`):

1. **`phase 2/data/diseases/<disease>/<stem>.compmodel`** - Exact stem match (case-insensitive) under the diseases benchmark tree. The report folder prefix (everything before `_llm_`) is used as the stem, e.g. `covid1_llm_openai_...` → looks for `covid1.compmodel` in any `diseases/*/` folder.
2. Else **`phase 2/data/<disease>/<stem>.compmodel`** - Legacy flat layout without the `diseases/` wrapper.
3. Else **`phase 2/data/baseline_models/*.compmodel`** - Legacy flat baseline directory (fallback).

**What is loaded:**

- For **gap detection**, the XML is parsed into lists of compartment, parameter, and stratification **names** (`load_model_structure`).
- For **validation of numeric fills**, the same file is parsed for full **parameter** records (name, expression/value, unit, description) (`_load_gold_with_values`).

**Important:** Gold files are **curated inputs** (manually authored or imported models placed under `baseline_models/` or Phase 1). Phase 3 does not derive them from the LLM extraction alone.

### 2. Promise-based comparisons - when they are used and why you may see “none”

Gap detection supports two modes (`gap_detector.detect_gaps`):

| Mode | When | What is compared |
|------|------|------------------|
| **`gold_standard`** | A baseline `.compmodel` was found for the disease | Extracted entities + draft model vs **gold lists** (fuzzy name matching). Gold-only items become “missing_*” gaps; extras vs gold are recorded where applicable. |
| **`promises`** | **No** baseline file was found | Same extracted/model union vs **`paper_promises.json`** (compartments, parameters, stratifications, interventions as promised by Phase 2). |

**Why aggregate reports often show “0 promise-based”:** If every disease in your study has a matching file under `phase 2/data/baseline_models/` (or Phase 1), **gold mode is always used** and promise mode is never selected for those runs. Promise mode is a **fallback** when no gold file exists or naming does not match.

**Note:** For promise mode to be meaningful, `paper_promises.json` must be **populated**; empty or sparse promises yield few or no gaps.

### 3. Database: “54 entries” - are these papers? How many per disease?

The **paper database** is the JSON index written by `build_database.py` to `data/paper_database/index.json`. The field **`num_entries`** (e.g. **54**) counts **indexed records**, not “54 PDF files” as a 1:1 mapping.

Each **entry** is one logical bundle, for example:

- **Phase 1:** A `.compmodel` under `phase 1/papers/**` (plus linked analysis/uncertainty JSON when present), and/or standalone PDFs not paired to a model.
- **Phase 2:** **One entry per (disease × provider)** using the **latest** Phase 2 report directory for that pair (includes `paper_text`-derived chunks, `extracted_entities.json`, `model_structure` from `model_draft.compmodel`, promises, evaluation snippets, etc.).
- **Phase 2 baselines:** One entry per **`phase 2/data/baseline_models/*.compmodel`**.

The index also stores **`knowledge_base`**: Phase 1 global assets (pattern library, taxonomies, protocols, paper collection metadata, etc.) - separate from the per-entry list.

**Typical scale (example from a built index):** Total **54** entries across **10** diseases → about **5–7 entries per disease** (mix of Phase 1 pieces, three Phase-2 provider snapshots, and baseline rows - exact counts depend on what exists on disk).

### 4. What is included for Rule-Based Retrieval

Rule-Based Retrieval in Phase 3 is **retrieval from the built index**, not a live vector DB in the default path. After `build_database.py`, the index contains:

- **`parameter_index`:** Flat list of parameter records (name, value/expression, unit, description, disease, source phase, `paper_id`) aggregated from:
  - Parsed **`model_structure.parameters`** on `.compmodel` files,
  - **`extracted_entities.parameters`** from Phase 2 reports,
  - **`uncertainty.parameters`** where present.
- **Text `chunks`:** For Phase 2 entries that have `paper_text.json`, the **`full_text`** field is split into overlapping character windows (see §7). Chunks are used for keyword search and regex value extraction.
- **Structured fields per entry:** e.g. `extracted_entities`, `promises`, `evaluation` subsets, `analysis`, `sensitivity` - as emitted by `build_database.py`.
- **`knowledge_base`:** Phase 1 JSON assets (patterns, taxonomies, extraction protocols, etc.) for possible downstream use.

**Gap filling does not peek at gold values:** The gold `.compmodel` is used for **which** gaps exist and for **post-hoc validation**, not as the source of suggested numbers during retrieval/inference (`gap_filler_phase3.py`).

### 5. How inference works

If Rule-Based Retrieval does not produce a usable value for a **missing parameter** gap, **Tier 2** runs (`src/inference/inference_engine.py`):

1. **LLM path:** A short prompt asks the configured provider (same family as Phase 2: Gemini / OpenAI / Claude via `phase 2`’s `LLMClient`) for a **numeric** `value`, optional `unit`, and brief `reasoning` in **strict JSON**. The **first ~800 characters** of the paper full text are passed as extra context.
2. If the client is unavailable or parsing fails, a **fallback** uses a small **keyword → default** library (e.g. incubation/recovery-style names → typical ranges) with **LOW** confidence.

Inferred values are labeled as **low confidence** and should be verified against literature.

### 6. What “flagged” means

**Flagged** is **Tier 3** of gap filling (`source: "flagged"` in `phase3_filled.json`):

- Rule-Based Retrieval did **not** return an `extracted_value`, **and**
- LLM inference did **not** return a usable value/reasoning,

**or** the gap is **not** a missing parameter (missing compartments / stratifications / interventions are not filled by the current retrieval+LLM path in the same way - they get a **manual_review** style suggestion).

So “flagged” means **no automatic fill**: human review or another pipeline is expected.

### 7. Text chunks: PDF pages vs finer granularity

Chunks are **not** “one chunk per PDF page.”

`build_database.py` uses **`_chunk_text`**: sliding **character** windows of default **2000** characters with **300** characters **overlap**, over the **concatenated `full_text`** string from `paper_text.json` (word-level bounding boxes are **not** loaded).

So resolution is **sub-page / multi-sentence** text segmentation, not page boundaries. Retrieval ranks chunks by **keyword hits** on parameter names (with Greek/Latin variants) plus a disease match boost (`parameter_lookup.search_text_chunks`).

### 8. Rule-Based Retrieval parameter lookup (mechanics)

For a missing parameter, `parameter_lookup` (`src/rag/parameter_lookup.py`):

1. **Structured:** Score matches in **`parameter_index`** by normalized name (Greek/Latin folding), prefer same disease and non-empty values.
2. **Unstructured:** Search **text chunks** for the parameter symbol/name; optionally **regex-extract** a numeric value from the chunk (`extract_value_from_chunk`).
3. If regex finds a value in a chunk, that becomes the retrieval **`extracted_value`**; else a strong **index** hit’s value may be used.

This is **lexical / regex retrieval** in the current implementation.

---

## Results (benchmark: 10 diseases × 3 papers, fuzzy matching)

Evaluated across **30 benchmark papers** (10 diseases × 3 papers each) with hand-authored gold `.compmodel` files.  
**Phase 2 baseline** = best recall across OpenAI / Gemini / Claude per paper, measured with the same fuzzy evaluator used for Phase 3.  
**Phase 3** = LLM provider Gemini, showcase run `showcase_gemini/`, evaluated against the same gold standards.

All metrics use **fuzzy recall** as the primary metric (synonym-aware name matching, e.g. "Infectious" ≡ "Infected").  
Full per-paper tables are in [`RESULTS_PHASE3_GEMINI.md`](RESULTS_PHASE3_GEMINI.md).

### Summary: Phase 2 vs Phase 3 (averaged over 30 papers)

| | Avg Comp Recall | Avg Comp F1 | Avg Flow Recall | Avg Flow F1 |
|--|----------------|-------------|----------------|-------------|
| **Phase 2 best** | 0.75 | 0.77 | 0.53 | 0.53 |
| Rule-Based Retrieval only (`retrieval_only`) | **0.79** (+0.03) | 0.79 | **0.62** (+0.09) | 0.60 |
| LLM only (`llm_only`) | **0.93** (+0.17) | 0.86 | **0.81** (+0.28) | 0.73 |
| **Both - Rule-Based Retrieval + LLM (`both`)** | **0.94** (+0.18) | **0.86** | **0.80** (+0.27) | **0.73** |

### Which mode to use

**`both` (Rule-Based Retrieval + LLM) is the best overall mode** and is the recommended default.

- It achieves the highest compartment recall (0.94, +0.18 over Phase 2) and equal-best F1 (0.86) alongside LLM-only.
- Rule-Based Retrieval alone is conservative: it never hurts recall (every Δ ≥ 0) but the gains are modest (+0.03 comp, +0.09 flow) because it only fills what is explicitly in the indexed papers.
- LLM alone closes large structural gaps but without the grounding of direct paper evidence.
- Combining both gives the best coverage: Rule-Based Retrieval provides paper-grounded evidence first; LLM fills the remaining gaps where retrieval found nothing.

**Recommendation:** Use `--mode both` (default) or `--mode auto` (auto-selects per paper based on a scoring function that balances recall improvement against remaining parameter gaps).

### Key observations

- **LLM is the primary driver of improvement** - the gap from `retrieval_only` (+0.03 comp / +0.09 flow) to `both` (+0.18 comp / +0.27 flow) shows that LLM inference is responsible for most of the structural gains.
- **Flow recall improves more than compartment recall** - flows are harder to extract from text alone (Phase 2: 0.53 flow recall vs 0.75 comp recall), so there is more room to improve.
- **Papers with weak Phase 2 drafts benefit most** - diseases such as Dengue P3 (0.00 → 1.00), HIV P2 (0.33 → 0.67), Cholera P2 (0.55 → 1.00) see the largest gains in `both` mode.
- **Papers already at ceiling stay there** - well-extracted papers (e.g. Malaria P1/P2, Zika P1/P3) remain at 1.00 / 1.00 without any regression.
- **No regressions** - every Δ in the final results is ≥ 0; the non-regression guard in `run_phase3.py` reverts to the Phase 2 draft if structural F1 drops.
- **Param gaps reflect genuine difficulty** - Tuberculosis P2/P3 (17–18 gaps), Malaria P3 (16 gaps), and Cholera P2 (24 gaps) are the hardest parameter-wise even after filling.

### Why results vary by disease

Different diseases have structurally very different models, and those differences explain most of the variation:

| Disease | Behaviour | Reason |
|---------|-----------|--------|
| **Tuberculosis** | High param gaps after fill (17–18); modest compartment gains | TB models have many fine-grained stages (latent, fast/slow progressors, treated, MDR-TB) and many rate parameters (treatment success, default, relapse). The paper text rarely states all of them numerically, so both retrieval and LLM inference leave many gaps. |
| **Malaria** | Good compartment recall, high param gaps (P3: 16 gaps) | Malaria models include both human and vector (mosquito) compartments. Compartment names are recoverable from text, but vector-related parameters (mosquito biting rate, vector-to-host ratio) are rarely stated clearly, leaving many param gaps. |
| **Cholera** | P2 has huge param gaps (24) and large gains in `both` | Cholera models include an environmental water compartment (`W`) and water-related parameters (bacteria shedding rate, water decay rate) that are disease-specific and hard to extract from general text. Phase 2 missed most of these; LLM inference fills some. |
| **Dengue** | P3 goes from 0.00 → 1.00 comp/flow in `both` | The Phase 2 draft for this paper was essentially empty (0.00 recall), meaning LLM extraction at Phase 2 failed completely. Phase 3 LLM inference essentially reconstructed the full model from the paper text, giving a +1.00 gain. |
| **HIV** | Very low Phase 2 recall (0.10–0.33), large Phase 3 gains | HIV models often have multiple exposure/infection stages (acute, chronic, AIDS) and treatment compartments (ART). The complex staging is hard for Phase 2 to extract in one shot; Phase 3 LLM fills in the missing compartments iteratively. |
| **Influenza** | Moderate gains; some param gaps remain | Influenza models are structurally standard (SEIR + exposed/infectious variants) so compartments are usually well extracted. Remaining gaps are age-stratified parameters that are paper-specific and hard to retrieve. |
| **COVID-19** | P3 flow recall stuck at 0.00 on some papers | Some COVID papers model complex interventions (quarantine, hospitalisation, ICU) with many directional flows. If Phase 2 missed the compartments entirely, Phase 3 cannot wire the flows either (you can't add a flow if the compartment doesn't exist yet). |
| **Ebola / Measles / Zika** | Generally high recall; marginal Phase 3 gains | These have well-studied, relatively simple compartmental structures (SEIR / SEIRD). Phase 2 already extracts most compartments and flows correctly, leaving little room for Phase 3 to add. Gains are mainly on flows. |

**General pattern:** Phase 3 helps most when Phase 2 extraction was incomplete (weak draft) and least when the model is already well-formed. Disease-specific complexity (vector dynamics, multi-stage infection, environmental compartments) is the main predictor of remaining param gaps after filling.

Regenerate results: `python3 build_phase3_results_md.py --showcase showcase_gemini -o RESULTS_PHASE3_GEMINI.md`

---

## Directory layout

```
phase 3/
├── run_phase3.py              # Main entry
├── build_database.py          # Index Phase 1 + 2 for Rule-Based Retrieval
├── run_phase3_showcase.py     # Best Phase 2 per disease + 3-mode comparison
├── src/                       # rag, gap_analysis, inference, evaluation, reporting
├── data/paper_database/       # Built index (index.json)
├── reports/                   # Per-disease/provider Phase 3 runs
└── showcase_gemini/           # Showcase outputs: per-mode runs + summary report
```

## Main artifacts

| File | Role |
|------|------|
| `phase3_gaps.json` | Detected gaps |
| `phase3_filled.json` | Fill source + suggested values |
| `phase3_validation.json` | Error % and quality label per fill |
| `model_filled.compmodel` | Draft with fills applied |
| `gap_report.md` | Human-readable per-disease summary |
| `showcase_gemini/SHOWCASE_REPORT.md` | Aggregated view across retrieval_only / llm_only / both |

## Reading aggregate results

After a showcase run, `showcase_gemini/SHOWCASE_REPORT.md` summarizes per-disease winners across `retrieval_only` / `llm_only` / `both`, plus key metrics and the selected Phase 2 source per disease. The full numeric comparison table is in [`RESULTS_PHASE3_GEMINI.md`](RESULTS_PHASE3_GEMINI.md).

## Adding diseases

Nothing is hardcoded: providers and diseases are inferred from directory names. New work:

1. Place PDF + gold model pair in `phase 2/data/diseases/<disease>/<stem>.pdf` + `<stem>.compmodel` (e.g. `diseases/covid/covid2.pdf` + `covid2.compmodel`).
2. Run Phase 2: `python run_phase2.py --paper data/diseases/<disease>/<stem>.pdf --llm-provider openai` → creates `reports/<stem>_llm_openai_<timestamp>/`.
3. Rebuild the database: `python build_database.py`.
4. Run Phase 3: `python run_phase3.py --all --output reports`.

## Documentation

| File | Purpose |
|------|---------|
| **[INSTRUCTIONS.md](INSTRUCTIONS.md)** | Commands, CLI flags, multi-provider workflow, API keys, dependencies. |
| **`run_phase3_showcase.py`** | **Showcase:** per disease, **best Phase 2** among **gemini / openai / claude** (by eval score), then × **retrieval_only / llm_only / both** with **your** Phase 3 **`--llm-provider`**. See INSTRUCTIONS.md. |

---

## Enhancements & integration with Phase RLM

**Why Phase RLM matters:** Phase 3 fills **values** and suggests **labels** and **flow narratives**; **Phase RLM** (`phase_rlm/`) is designed for **iterative structural repair** of `.compmodel` XML (flows, self-referential contacts, populations, etc.) using semantic search over the paper. A practical pipeline is:

1. Run Phase 3 (Rule-Based Retrieval + inference + structural validation) → inspect `phase3_validation.json` (parameter accuracy + compartments/flows quality metrics).
2. Run **Phase RLM** on the same Phase 2 report when XML structure is still invalid or flows are incomplete.
3. Re-run Phase 3 **evaluation** on the RLM output if you add a script, or compare `gap_report.md` before/after.

**Database (`build_database.py`):** Re-run after Phase 1/2 changes. The index now includes **`flow_index`** (signatures from all indexed `.compmodel` files), **`version`** 3, and Phase 1’s **`data/papers/*/metadata.json`** entries plus **`papers/epimde/`** as the primary model directory.

**Future improvements (ideas):** stronger flow retrieval/ranking; automatic XML flow insertion with validated compartment indices; joint scoring in Phase 4 reports.
