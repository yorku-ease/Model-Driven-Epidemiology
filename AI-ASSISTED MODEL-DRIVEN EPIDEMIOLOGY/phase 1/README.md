# Phase 1: Model analysis and documentation

Python tooling to analyze compartmental epidemiological `.compmodel` files, compare models to paper promises, quantify parameter uncertainty, run sensitivity analyses, and maintain a structured paper collection.

## Role in the pipeline

- Reference models live in `papers/epimde/`; Phase 2 baselines mirror many of them under `phase 2/data/baseline_models/`. Batch scripts resolve paths via `utils/phase1_paths.py`.
- `metamodel_epidemiology.json` is the shared epidemiology-only metamodel used for LLM prompting in Phase 2.

## What Phase 1 does

| Area | Scripts | Typical outputs under `reports/` |
|------|---------|----------------------------------|
| Structure | `analysis/model_analyzer.py` | `model_analysis/` |
| Gaps vs paper | `analysis/gap_analyzer.py` | `gap_reports/` |
| Parameter uncertainty | `analysis/uncertainty_analyzer.py` | `uncertainty/` |
| Sensitivity | `analysis/sensitivity_analysis.py` | `sensitivity/` |
| Paper index | `analysis/paper_collection.py` | `paper_collection/` |

`run_phase1.py` runs the full task chain in one go.

Optional knowledge-base files live under `reports/protocols/`, `reports/taxonomies/`, `reports/patterns/`, `reports/manual_extraction/`. Phase 3's `build_database.py` loads them when present. See [`reports/REPORTS.md`](reports/REPORTS.md) for a full catalog of every subfolder and how outputs relate to tasks and Phase 3.

## Principles

- Generalizable: XML-driven analysis; no disease-specific hardcoding in core parsers.
- Paper-driven gap analysis when PDFs and promises are available.
- Multi-method sensitivity: Morris, grid, random, Sobol (depending on model and options).

## Limitations and methodology notes

### Promise extraction (gap analysis)

Paper "promises" - the set of model entities the PDF is claimed to describe - may come from pattern-based regex heuristics or an LLM. Quality is bounded by the extraction method: LLM output depends on model, prompt, and PDF quality; pattern-based output can miss nuanced wording.

For a validity check, code promises manually for a small gold set of papers (or derive them from a structured abstract) and compare agreement with pattern-based and LLM-based extraction (e.g. overlap on compartments/parameters). The code supports switching modes but does not automate this comparison.

### Comparing manual vs. pattern-based vs. LLM-based extraction

| Mode | Role in this repo |
|------|------------------|
| Manual | Human-supplied promise lists, or qualitative comparison for validation |
| Pattern-based | Default in `gap_analyzer` / `paper_promise_extractor` when no LLM - fast, no API required |
| LLM-based | Optional (`--use-llm` where supported); more flexible, same validity caveats as any LLM extraction |

A structured comparison study (same PDFs, three methods, manual reference) is recommended for publications; the code supports all three modes but does not auto-run that evaluation.

### Parameter uncertainty quantification

Task 2.2 is specifically parameter uncertainty: documenting numeric parameter values in the model, template fields for literature ranges, and a confidence label. It does not replace full probabilistic uncertainty quantification (e.g. full priors on every rate) unless you extend the analyzer.

### Why confidence is often "Unknown"

High / Medium / Low confidence labels are only assigned where hard-coded heuristics match a specific disease name and parameter name substring (`uncertainty_analyzer.py`). For other diseases or parameters, the default is Unknown because the tool does not query papers or databases automatically. Filling confidence properly requires literature linkage (DOI, tables) or expert judgment, then editing the JSON or extending the code.

### Sensitivity analysis: parameter mapping methodology

There is no ML model for "intelligent" parameter mapping. The sensitivity script (a) reads explicit `<parameters>`, (b) walks flows and builds names like `contactRate_<source>_to_<target>` for embedded rates, (c) classifies type (transmission, recovery, etc.) using keyword rules on the parameter name (`beta`, `contact`, `gamma`, ...). This is rule-based classification, not learned. See INSTRUCTIONS.md (Task 2.3) for the exact behavior.

### Paper collection: venue and BibTeX

Each entry supports `venue` (e.g. journal name), `venueType` (`journal` | `conference` | `book` | `preprint` | `other` | `unknown`), and optional `bibtex` (paste from Google Scholar, PubMed, Zotero, etc.). See `analysis/paper_collection.py` and INSTRUCTIONS.md.

### Taxonomies and patterns

The JSON files in `reports/taxonomies/` and `reports/patterns/` are curated knowledge-base artifacts. Automatically deriving a complete, literature-grounded taxonomy or pattern library would require a dedicated systematic review. The pipeline consumes these files where present; it does not replace a full literature review for taxonomy construction.

---

## Methodology: reference models, papers, and how components are identified

Phase 1 does not perform a standalone literature search to discover model structures from scratch. Instead it analyzes compartmental models already encoded as `.compmodel` XML and, when you attach a PDF, compares those structures to what the paper says it will model (the "paper promises"). That distinction matters for reporting and for any future systematic search you may add on top.

### What was analyzed (current corpus)

| Layer | What | Notes |
|-------|------|-------|
| Primary reference models | Curated gold `.compmodel` files under `papers/epimde/` | Batch tools analyze every `*.compmodel` in this directory (non-recursive). The `paper_collection` initializer lists three canonical sources (Tuite et al. 2020; Akowe et al. 2025; Espitia et al. 2022); additional diseases may be present as the corpus grows. |
| Paired source PDFs | PDFs in `papers/epimde/` with the same stem as the model (e.g. `dengue.compmodel` + `dengue.pdf`) | Used for gap analysis when found; see `utils/phase1_paths.find_pdf_for_compmodel`. |
| Extended baselines (optional) | `phase 2/data/baseline_models/*.compmodel` | Used when `papers/epimde/` is empty and Phase 2 baselines resolve as the default directory (`utils/phase1_paths.py`). |
| Additional papers for testing | PDFs under `papers/new papers/` | Optional inputs for ad-hoc gap runs; not all are paired to an `epimde` model. |

Counts change over time. For a snapshot of `reports/` and what each folder contains, see [`reports/REPORTS.md`](reports/REPORTS.md).

### How model components are found

1. **Structure from XML (primary):** `analysis/model_analyzer.py` parses each `.compmodel` file and extracts compartments, flows (e.g. contact, rate, sources/sinks), and parameters (explicit definitions and values embedded in expressions). This is the authoritative list of model components for Phase 1.
2. **Promises from papers (optional):** When you pass `--paper-pdf`, `analysis/gap_analyzer.py` extracts what the PDF claims the model includes (pattern-based and/or LLM-assisted), then compares that to the XML-derived structure to list gaps. Components are therefore not mined by crawling abstracts; they are either read from the machine-readable model or contrasted with stated paper intent.
3. **Systematic search (for extensions):** If you need a reproducible literature strategy (e.g. PRISMA-style), treat Phase 1 as the analysis engine and define separately: research question, databases, inclusion/exclusion criteria, screening steps, and how selected papers become `.compmodel` + PDF pairs. The current project uses a purposefully sampled set of high-quality reference models rather than an exhaustive search.

---

## Sensitivity analysis methods

Phase 1's `analysis/sensitivity_analysis.py` implements four approaches from global sensitivity analysis (GSA). GSA studies how variation in model inputs (parameters) propagates into variation in model outputs (e.g. total infections, epidemic peak).

| Method | Idea | Strengths | Limitations | Key reference |
|--------|------|-----------|-------------|---------------|
| Morris (elementary effects) | One-at-a-time moves along random trajectories in discrete levels; mean absolute effect µ* screens important factors, σ hints at interactions | Cheap screening with many parameters; well understood | Not a full variance decomposition; interactions only indirectly captured | Morris (1991), *Technometrics* |
| Grid | Regular grid over selected parameters (factorial / lattice) | Transparent, exhaustive over the grid; good for very few factors | Curse of dimensionality; impractical for many parameters | Saltelli et al. (2008) |
| Random (Monte Carlo) | Independent random draws over parameter ranges; output variance explored empirically | Simple, flexible, easy to parallelize; no grid structure required | May need many runs for stable estimates in high dimensions | Saltelli et al. (2008) |
| Sobol sequences | Quasi-random Sobol' points (`scipy.stats.qmc.Sobol`) for parameter draws; not full Saltelli-style variance indices unless extended | Better coverage than IID random for a given n | Needs scipy; many runs in high dimension; full Saltelli (2010) indices need extra computation | Sobol' (1993); Saltelli et al. (2010) |

**References**

- Morris, M. D. (1991). Factorial sampling plans for preliminary computational experiments. *Technometrics*, 33(2), 161-174.
- Sobol', I. M. (1993). Sensitivity estimates for nonlinear mathematical models. *Mathematical Modelling and Computational Experiment*, 1(4), 407-414.
- Saltelli, A., Ratto, M., Andres, T., Campolongo, F., Cariboni, J., Gatelli, D., Saisana, M., & Tarantola, S. (2008). *Global Sensitivity Analysis: The Primer*. Wiley.
- Saltelli, A., et al. (2010). Variance based sensitivity analysis of model output: Design and estimator for the total sensitivity index. *Computer Physics Communications*, 181(2), 259-270.

For CLI defaults, file layouts, and worked examples, see [INSTRUCTIONS.md](INSTRUCTIONS.md) (Task 2.3).

## Documentation

| File | Purpose |
|------|---------|
| [INSTRUCTIONS.md](INSTRUCTIONS.md) | Setup, commands, per-task CLI, programmatic notes, troubleshooting, appendix with the former Phase 1 results summary |
| [reports/REPORTS.md](reports/REPORTS.md) | Catalog of `reports/` - generated vs static files, filenames, Phase 3 usage, regeneration |

---

*All command-line examples and working-directory notes are in [INSTRUCTIONS.md](INSTRUCTIONS.md).*
