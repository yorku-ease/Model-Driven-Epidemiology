# Phase RLM output

All RLM artifacts for this run live **here** under `phase_rlm/output/`, not inside `phase 2/reports/`.

## Files

| File | Description |
|------|-------------|
| `model_repaired.compmodel` | Model after RLM structural repair |
| `initial_validation.json` | Structural validator on **Phase 2** `model_draft.compmodel` (before repair) |
| `final_validation.json` | Structural validator on **repaired** model |
| `comparison_report.json` | Before/after error counts and paths |
| `repair_report.json` | LLM repair loop log |
| `sections_vector_store/` | FAISS index (if built for this run) |
| `error_logs/` | Error memory for this run |

Original Phase 2 draft (unchanged): `../../../phase 2/reports/flu_llm_openai_20260321_231306/model_draft.compmodel`

## Did RLM improve things?

### 1) Structural validation (always available)

Compare `initial_validation.json` vs `final_validation.json` (`total_errors`, `errors_by_severity`).

### 2) Gold baseline P/R/F1 (same metric as Phase 2)

Phase 2 scores in `evaluation_report_fuzzy_temp.json` in the Phase 2 report folder. refer to **LLM extraction** (`extracted_entities.json`), not the XML alone.

**Fuzzy baseline (recommended, same as Phase 2 temp evaluator):** from `phase 2/`:

```bash
cd "../phase 2"
python3 rerun_evaluation_phase2_temp.py "/home/fatemeh/Documents/york/Code/Model/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/phase 2/reports/flu_llm_openai_20260321_231306" --rlm-evaluate
```

Writes **`evaluation_report_fuzzy_rlm.json`** in **this** folder (`phase_rlm/output/<run>/`). Compare to Phase 2’s **`evaluation_report_fuzzy_temp.json`** using:

```bash
python3 build_phase2_vs_rlm_fuzzy_md.py --reports-dir reports -o RESULTS_PHASE2_VS_RLM_FUZZY.md
```

**Semantic (optional):** from `phase_rlm/`:

```bash
python3 evaluate_repaired_model.py "/home/fatemeh/Documents/york/Code/Model/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/phase 2/reports/flu_llm_openai_20260321_231306"
```

Writes **`evaluation_report_rlm_repaired.json`** (embeddings). Different metric than fuzzy.
