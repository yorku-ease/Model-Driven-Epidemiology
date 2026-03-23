# Phase RLM: Instructions

## API keys

`src/utils/llm.py` reads **`GEMINI_API_KEY`** and **`OPENAI_API_KEY`** from the environment. Export them or align with your Phase 2 key management.

`run_phase_rlm.py` also supports Phase 2-style key files via `--api-key-file` (default: `../phase 2/.api_key.txt`), including:

- `openai:sk-...`
- `gemini:AIza...`
- or a single raw key line

## Configuration

`configs/repair_config.json` (excerpt):

```json
{
  "phase2_evaluation_json": "evaluation_report_fuzzy_temp.json",
  "llm_provider": "gemini",
  "max_iterations": 5,
  "max_context_iterations": 3,
  "initial_search_k": 10,
  "additional_search_k": 5,
  "min_chunk_score": 0.25,
  "error_memory": {
    "semantic_threshold": 0.82,
    "max_results": 3,
    "min_entries_for_semantic": 10
  }
}
```

| Parameter | Default | Description |
|-----------|---------|-------------|
| `max_iterations` | 5 | Outer passes over errors |
| `max_context_iterations` | 3 | Inner LLM calls per error |
| `initial_search_k` | 10 | First chunk retrieval |
| `additional_search_k` | 5 | Extra chunks on NEED_MORE_CONTEXT |
| `min_chunk_score` | 0.25 | Minimum similarity for chunks |
| `semantic_threshold` | 0.82 | Memory semantic match |
| `phase2_evaluation_json` | `evaluation_report_fuzzy_temp.json` | Which Phase 2 evaluation file in the report folder to load for LLM context (falls back to `evaluation_report.json` if missing) |

## Phase 2 evaluation JSON (optional context for the LLM)

RLM reads **one** evaluation file from the same report directory and injects a short **baseline P/R/F1 summary** into repair prompts (soft guidance only; structural repair still follows the validator).

- **Default preference:** `evaluation_report_fuzzy_temp.json` (from `rerun_evaluation_phase2_temp.py`), then **`evaluation_report.json`** if the first is absent.
- **Override:** `--evaluation-json evaluation_report.json` or set `phase2_evaluation_json` in `repair_config.json`.
- **Recorded in** `repair_report.json` as `phase2_evaluation_json` (path used) and `phase2_evaluation_json_preference`.

## Which LLM RLM uses

- **Default (like before):** `configs/repair_config.json` → `llm_provider` and `llm_model` (no CLI needed).
- **Override (same idea as Phase 2 `--llm-provider`):**
  - `--llm-provider openai` or `--llm-provider gemini`
  - `--llm-model <id>` optional (e.g. `gemini-2.5-flash`)

**Note:** `-p` / `--provider` only filters **which Phase 2 report folder** to use with `--disease` (matches `*_llm_<provider>_*` in the folder name). It does **not** set the repair LLM.

## Usage

From `phase_rlm/`:

```bash
# Latest Phase 2 report for a disease + provider (folder pick only)
python3 run_phase_rlm.py --disease measles --provider gemini

# Explicit report directory — RLM uses repair_config.json for the API unless you pass:
python3 run_phase_rlm.py "../phase 2/reports/measles_llm_gemini_20260312_165901"
python3 run_phase_rlm.py "../phase 2/reports/measles_llm_gemini_20260312_165901" \
  --llm-provider openai --llm-model gpt-5.4-mini

# Validation only (no repair)
python3 run_phase_rlm.py --disease measles --provider gemini --validate-only

# Use semantic Phase 2 evaluation file instead of fuzzy-temp default
python3 run_phase_rlm.py "../phase 2/reports/measles_llm_gemini_20260312_165901" \
  --evaluation-json evaluation_report.json
```

## Fuzzy evaluation in `phase_rlm` (no edits to phase 2 scripts)

Run fuzzy evaluation on repaired models under `phase_rlm/output/`:

```bash
cd phase_rlm
python3 rerun_evaluation_fuzzy_temp.py --batch output
```

This writes, per run folder:
- `evaluation_report_fuzzy_temp.json` (RLM repaired model vs Phase 2 baseline model)
- `phase2_vs_rlm_fuzzy_delta.json` (increase/decrease vs `phase 2/reports/<run>/evaluation_report_fuzzy_temp.json`)

Build an aggregate markdown (like Phase 2 `build_results_md.py`):

```bash
python3 build_results_md_rlm.py --runs-dir output -o RESULTS_RLM_FUZZY.md
```

### Batch (example)

```bash
for disease in measles flu dengue malaria hiv ebola; do
    python3 run_phase_rlm.py --disease "$disease" --provider gemini
done
```

## Outputs (default folder: `phase_rlm/output/<phase2_report_folder_name>/`)

Override with `--output /path/to/dir`. **Phase 2 `reports/` is not modified** by RLM (only read for `model_draft.compmodel`, `paper_sections.json`, evaluation JSON, etc.).

| Location | Description |
|----------|-------------|
| `output/<run>/model_repaired.compmodel` | Repaired XML |
| `output/<run>/initial_validation.json` | Validator on Phase 2 `model_draft.compmodel` (before) |
| `output/<run>/final_validation.json` | Validator after repair |
| `output/<run>/comparison_report.json` | Before/after structural comparison |
| `output/<run>/repair_report.json` | LLM repair log |
| `output/<run>/README.md` | How to compare vs Phase 2 + optional gold eval |
| `output/<run>/error_logs/` | Error memory |
| `output/<run>/sections_vector_store/` | FAISS index (rebuilt if missing) |

### Gold P/R/F1 vs Phase 2 (optional)

After repair, compare baseline scores using the **same** Phase 2 evaluator on the repaired structure:

```bash
cd phase_rlm
python3 evaluate_repaired_model.py "../phase 2/reports/YOUR_RUN_FOLDER"
```

Writes `output/<run>/evaluation_report_rlm_repaired.json`. Compare `composite_score` to `evaluation_report.json` in the Phase 2 report folder (extraction-based vs compmodel-structure-based; see JSON `matching_note`).

## Prerequisites

- `model_draft.compmodel` and **`paper_sections.json`** in the Phase 2 report (repair needs sections for RAG).
- Initial structural errors (validator finds issues); if zero errors, the tool exits without repair.

## Troubleshooting

**Repairs fail or repeat:**

```bash
rm -rf output/*/error_logs/
rm -rf output/*/sections_vector_store/
```

Ensure `paper_sections.json` exists in the Phase 2 report for that run.

**LLM not improving fixes:** Inspect `repair_engine.py` / `llm_prompts.py`; consider raising `max_context_iterations` in config.

**Rate limits:** Pause and resume with remaining diseases.

## Development notes (v1 → v2)

- No hardcoded bypass for `self_referential_flow`
- Full-model prompt and full-model response
- LLM-generated search queries
- FAISS-backed error memory
- Skip retries on `CANNOT_FIX`
