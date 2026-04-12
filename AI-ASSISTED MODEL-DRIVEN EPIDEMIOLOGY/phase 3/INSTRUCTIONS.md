# Phase 3: Instructions

## Usage

### Step 1: Build the paper database

Collects papers, models, extracted entities, and parameters from Phase 1 and Phase 2 into a single searchable index.

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
# Skip Rule-Based Retrieval (LLM inference + flag only)
python3 run_phase3.py --all --no-rag --output reports

# Skip LLM inference (Rule-Based Retrieval + flag only)
python3 run_phase3.py --all --no-inference --output reports

# Skip both (detection and reporting only)
python3 run_phase3.py --all --no-rag --no-inference --output reports
```

## LLM provider selection

Phase 3 detects which LLM provider Phase 2 used from the report directory name (e.g. `cholera_llm_gemini_...` → Gemini for inference).

API keys: `phase 2/.api_key.txt` (same as Phase 2).

Override from the command line:

```bash
python3 run_phase3.py --all --llm-provider gemini --output reports
python3 run_phase3.py --all --llm-provider openai --output reports
python3 run_phase3.py --all --llm-provider claude --output reports
```

Or environment variable: `PHASE3_LLM_PROVIDER=openai` (or `gemini`, `claude`).

## Recommended workflow

For a clean end-to-end run from scratch, use:

1. `python3 build_database.py`
2. `python3 run_phase3.py --showcase --llm-provider <gemini|openai|claude>`  
   (omit `--output` to write under `phase 3/reports/`; use another name if you want a separate tree)
3. `python3 build_phase3_results_md.py` — writes `RESULTS_PHASE3.md` (Phase 3 recall tables) plus `fuzzy_phase2_vs_phase3.json` and an appended **Phase 2 draft vs Phase 3 filled** section (same directory as step 2, default `--showcase reports`)

## Showcase: best Phase 2 (3 LLMs) × Rule-Based Retrieval / LLM / both

**Phase 2:** For each disease you have `disease_llm_gemini_*`, `disease_llm_openai_*`, `disease_llm_claude_*`. The script **compares all of them** and picks the **single best** Phase 2 report by composite evaluation score (traceability, faithfulness, gaps, gold F1).

**Phase 3:** you **choose** which API runs gap-filling inference (`--llm-provider` or `PHASE3_LLM_PROVIDER`). That choice is **independent** of which LLM won Phase 2 (e.g. best extraction might be OpenAI, while you run Phase 3 with Claude).

| Mode | Meaning |
|------|--------|
| `retrieval_only/` | Rule-Based Retrieval + flagged only (`--no-inference`) |
| `llm_only/` | LLM + flagged only (`--no-rag`) |
| `both/` | Rule-Based Retrieval + LLM + flagged |

Output folders: `<disease>_<phase3_llm>_phase3/`. Each contains **`phase3_showcase_source.json`** pointing at the **actual Phase 2 folder** used (so you know which extractor won).

```bash
cd "phase 3"
python3 build_database.py

# Default output base is reports/ (omit --output)
python3 run_phase3.py --showcase --llm-provider gemini

# Another Phase 3 inference provider without overwriting the same tree:
python3 run_phase3.py --showcase --llm-provider claude --output reports_claude
```

- **`--llm-provider`**: Phase 3 inference API. If omitted, set **`PHASE3_LLM_PROVIDER`**.
- For Gemini, default model is **Flash**: `--gemini-model gemini-2.5-flash` (default value).
- **`--output`** (default `reports`): base for `retrieval_only/`, `llm_only/`, `both/`, `SHOWCASE_REPORT.md`, `showcase_summary.json`.
- **`--diseases cholera,dengue`**: limit to listed disease slugs.

Use **`showcase_summary.json`** / **`phase3_showcase_source.json`** to see which Phase 2 report was used for each disease and which mode won.

## Dependencies

- Phase 2 report directories with extracted models
- `phase 2/.api_key.txt` for LLM inference when inference is enabled
- No extra Python packages beyond the main project `requirements.txt`

## Adding a new disease

1. Add `phase 2/data/diseases/<disease>/<stem>.pdf` and `<stem>.compmodel` with the same stem.
2. Run Phase 2 on that PDF (see [phase 2/INSTRUCTIONS.md](../phase%202/INSTRUCTIONS.md)).
3. `python3 build_database.py`
4. `python3 run_phase3.py --all --output reports`

## Regenerate `RESULTS_PHASE3.md`

```bash
cd "phase 3"
python3 build_phase3_results_md.py
# or: python3 build_phase3_results_md.py --showcase reports -o RESULTS_PHASE3.md
```
