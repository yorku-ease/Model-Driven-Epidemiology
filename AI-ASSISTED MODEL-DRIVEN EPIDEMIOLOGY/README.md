# AI-Assisted Model-Driven Epidemiology

This project implements a four-phase pipeline for the automated extraction, completion, and validation of compartmental epidemiological models from scientific papers using large language models (LLMs). A compartmental model divides a population into groups (e.g., Susceptible, Infectious, Recovered) and uses differential equations to describe how individuals move between those groups over time. Given a PDF paper, the system produces a structured `.compmodel` file that encodes the model's compartments, transitions, and parameters, validated against a hand-authored gold-standard model.

## Pipeline overview

| Phase | Purpose | Key technique |
|-------|---------|---------------|
| Phase 1 | Analyse existing gold-standard `.compmodel` files | Structural parsing, sensitivity analysis |
| Phase 2 | Extract a compartmental model from a scientific PDF | LLM-driven entity extraction |
| Phase 3 | Identify and fill gaps in the extracted model | Rule-Based Retrieval + LLM inference |
| Phase 4 | Quantify uncertainty in the completed model | Monte Carlo simulation, sensitivity analysis |

## Workflow

```
Phase 1                   Phase 2                    Phase 3
Gold .compmodel  ──►  PDF → LLM → draft  ──►  Gap detection + filling
analysis                 .compmodel             validated vs gold standard
                              |                        |
                              v                        v
                       model_draft.compmodel     model_filled.compmodel
                       evaluation_report.json    phase3_validation.json
                                                        |
                                                        v
                                                    Phase 4
                                              Monte Carlo + sensitivity
                                              uncertainty_bands.png
                                              sensitivity_tornado.png
```

## Repository structure

```
AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/
├── requirements.txt              - Python dependencies
├── phase 1/                      - Baseline model analysis
├── phase 2/                      - LLM extraction from PDFs
├── phase 3/                      - Gap filling and validation
└── phase 4/                      - Uncertainty quantification
```

## How to run

Setup, API keys, and all command-line examples are documented in each phase’s **`INSTRUCTIONS.md`** (not in README files). Supported LLM providers: Gemini (recommended), OpenAI, Claude. Place the shared API key in **`phase 2/.api_key.txt`**.

| Phase | Instructions file |
|-------|-------------------|
| Phase 1 | [phase 1/INSTRUCTIONS.md](phase%201/INSTRUCTIONS.md) |
| Phase 2 | [phase 2/INSTRUCTIONS.md](phase%202/INSTRUCTIONS.md) |
| Phase 3 | [phase 3/INSTRUCTIONS.md](phase%203/INSTRUCTIONS.md) |
| Phase 4 | [phase 4/INSTRUCTIONS.md](phase%204/INSTRUCTIONS.md) |

From the project root: `pip install -r requirements.txt`, then follow phases in order using the guides above.

## Results summary

Phase 3 was evaluated across 30 benchmark papers (10 diseases × 3 papers each) using fuzzy string matching against hand-authored gold-standard models. The `both` mode, which combines Rule-Based Retrieval with LLM inference, achieves the highest recall.

| | Avg. Compartment Recall | Avg. Flow Recall |
|--|------------------------|-----------------|
| Phase 2 baseline (best provider per paper) | 0.75 | 0.53 |
| Phase 3 - `both` mode | **0.94** (+0.18) | **0.80** (+0.27) |

Full results: `phase 3/RESULTS_PHASE3_GEMINI.md`. Phase 4 uncertainty results: `phase 4/README.md`.

## Adding a new disease

No code changes are required: add PDF + hand-authored gold `.compmodel` under `phase 2/data/diseases/<disease>/` with matching stems, then run Phase 2 and rebuild Phase 3’s index. **Exact commands** are in [phase 2/INSTRUCTIONS.md](phase%202/INSTRUCTIONS.md) and [phase 3/INSTRUCTIONS.md](phase%203/INSTRUCTIONS.md).

## Design principles

- Extraction is evidence-based: every extracted model element is linked to a specific text span and page number in the source paper.
- Gold-standard models are used exclusively for validation, never as a source of fill values during gap filling.
- The pipeline is provider-agnostic: Gemini, OpenAI, and Claude can all be used interchangeably.

## Documentation

Each phase has a **`README.md`** (what it does, methodology, results) and an **`INSTRUCTIONS.md`** (how to run: setup, commands, CLI flags, troubleshooting). API keys go in `phase 2/.api_key.txt` (see `.gitignore`).
