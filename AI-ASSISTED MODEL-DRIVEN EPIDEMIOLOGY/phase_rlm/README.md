# Phase RLM: Agentic repair loop

An LLM-assisted repair pass for **Phase 2** `.compmodel` drafts: validate structure, retrieve evidence from the paper via **semantic search (FAISS)**, repair iteratively, and learn from **error memory**. Uses the same Phase 2 report folder (`model_draft.compmodel`, `paper_sections.json`). Optionally loads **`evaluation_report_fuzzy_temp.json`** (default) or **`evaluation_report.json`** for a short baseline summary in prompts—see **`INSTRUCTIONS.md`** (`phase2_evaluation_json`, `--evaluation-json`).

## Overview

1. **Semantic search** — Chunked paper text for evidence.
2. **Error memory** — Past successes/failures to avoid repeating dead ends.
3. **Iterative LLM calls** — `NEED_MORE_CONTEXT` expands retrieval before another repair attempt.
4. **Decision tags** — `CAN_FIX` / `NEED_MORE_CONTEXT` / `CANNOT_FIX`.

## Architecture

```
run_phase_rlm.py → RepairLoop → IterativeRepairEngine
                      (ErrorMemory + VectorStore + LLMClient) → StructuralValidator
```

## Error types (examples)

| Type | Severity | Meaning |
|------|----------|---------|
| `self_referential_flow` | Critical | Invalid contact/flow wiring |
| `zero_population_all` | Critical | All populations zero |
| `missing_birth_sources` | High | Susceptible without recruitment |
| `orphaned_parameters` | Medium | Unused parameters |
| `isolated_compartment` | Medium | No flows in/out |

## Design principles

- **Full model XML** to the LLM (no truncation).
- **Full corrected XML back** (no patch merging).
- **LLM-authored search queries** per error context.
- **No retry** after `CANNOT_FIX`.

## Repair tag protocol

- `<CAN_FIX>…full model XML…</CAN_FIX>`
- `<NEED_MORE_CONTEXT>…</NEED_MORE_CONTEXT>`
- `<CANNOT_FIX>…</CANNOT_FIX>`

## Scope: fixes vs limits

**Often fixes:** self-referential flows, missing death/recruitment flows, orphaned parameters, some structural completions.

**Cannot fix:** missing numeric values from the paper, wrong disease model family (needs re-extraction), contradictions in the PDF.

## Typical error reduction (illustrative)

| Stage | Critical | High | Medium | Total |
|-------|----------|------|--------|-------|
| Phase 2 draft | ~6 | ~1 | ~12 | ~19 |
| After RLM | ~1 | ~1 | ~3 | ~5 |

## Key paths

```
phase_rlm/
├── run_phase_rlm.py
├── evaluate_repaired_model.py   # optional: gold P/R/F1 on repaired compmodel vs baseline
├── rerun_evaluation_fuzzy_temp.py  # fuzzy evaluation on RLM outputs (phase2_temp logic)
├── build_results_md_rlm.py      # aggregate markdown for RLM fuzzy results
├── output/                      # created per run (not inside phase 2/reports/)
│   └── <same_name_as_phase2_report_folder>/
├── configs/repair_config.json
├── src/  (repair_loop, repair_engine, structural_validator, error_memory, utils/vector_store, utils/llm_client)
```

## Outputs (under **this** repo folder)

By default, everything goes to **`phase_rlm/output/<run>/`** where `<run>` matches the Phase 2 report folder name (e.g. `measles_llm_gemini_20260312_165901`). **Nothing** is written under `phase 2/reports/` except what Phase 2 already created.

| File | Role |
|------|------|
| `model_repaired.compmodel` | Repaired XML |
| `initial_validation.json` | Structural validator on Phase 2 `model_draft.compmodel` |
| `final_validation.json` | Structural validator on repaired model |
| `comparison_report.json` | Before/after error counts + paths |
| `repair_report.json` | LLM repair log |
| `sections_vector_store/` | FAISS index for this run |
| `error_logs/` | Error memory for this run |
| `README.md` | How to compare vs Phase 2 + fuzzy/semantic eval commands |

Optional: run **`python3 evaluate_repaired_model.py <phase2_report_dir>`** to write **`evaluation_report_rlm_repaired.json`** in **`phase_rlm/output/<run>/`** (same gold metric as Phase 2, input = structure from repaired compmodel).

## Documentation

| File | Purpose |
|------|---------|
| **[INSTRUCTIONS.md](INSTRUCTIONS.md)** | Configuration, CLI, outputs, batch runs, troubleshooting, API keys (`GEMINI_API_KEY` / `OPENAI_API_KEY`). |

---

*Phase RLM: Agentic Repair Loop for Epidemiological Models — Model-Driven Epidemiology Project.*
