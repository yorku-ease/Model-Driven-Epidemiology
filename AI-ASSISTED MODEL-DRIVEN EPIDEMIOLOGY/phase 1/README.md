# Phase 1: Model analysis and documentation

Python tooling to analyze compartmental **epidemiological** `.compmodel` files, compare models to paper promises, quantify **parameter** uncertainty, run sensitivity analyses, and maintain a structured paper collection.

## Role in the pipeline

- **Reference models** live in **`papers/epimde/`**; Phase 2 baselines mirror many of them under `phase 2/data/baseline_models/`. Batch scripts resolve paths via `utils/phase1_paths.py`.
- **`metamodel_epidemiology.json`** is the shared epidemiology-only metamodel for LLM prompting (with Phase 2).

## What Phase 1 does

| Area | Scripts | Typical outputs under `reports/` |
|------|---------|----------------------------------|
| Structure | `analysis/model_analyzer.py` | `model_analysis/` |
| Gaps vs paper | `analysis/gap_analyzer.py` | `gap_reports/` |
| **Parameter** uncertainty | `analysis/uncertainty_analyzer.py` | `uncertainty/` |
| Sensitivity | `analysis/sensitivity_analysis.py` | `sensitivity/` |
| Paper index | `analysis/paper_collection.py` | `paper_collection/` |

`run_phase1.py` runs the full task chain in one go.

Optional knowledge-base files live under `reports/protocols/`, `reports/taxonomies/`, `reports/patterns/`, `reports/manual_extraction/` — they are **not** produced by `run_phase1.py`; restore from Git (e.g. `origin/compartmental`) or backup if deleted. Phase 3’s `build_database.py` loads them when present. **See [`reports/REPORTS.md`](reports/REPORTS.md)** for a full catalog of every subfolder and how outputs relate to tasks and Phase 3.

## Principles

- **Generalizable:** XML-driven analysis; no disease-specific hardcoding in core parsers.
- **Paper-driven gap analysis** when PDFs and promises are available.
- **Multi-method sensitivity:** Morris, grid, random, Sobol (depending on model and options).

## Validity, limitations, and extensions (reviewer-oriented)

### Threat to validity: promise extraction (gap analysis)

**Paper “promises”** (what the PDF is claimed to describe) may come from **pattern-based** regex/heuristics or an **LLM**. Quality is bounded by the method: LLM output depends on model, prompt, and PDF quality; pattern-based output misses nuanced wording.

**What you can do:** For a small **gold set** of papers, code promises **manually** (or from a structured abstract) and compare agreement with pattern-based vs LLM-based extraction (e.g. overlap on compartments/parameters). Report **disagreement rates** — that is a proper validity check, not assumed by the pipeline.

### Comparing manual vs pattern-based vs LLM-based

| Mode | Role in this repo |
|------|---------------------|
| **Manual** | Human-supplied promise lists (outside the default batch) or qualitative comparison for validation. |
| **Pattern-based** | Default in `gap_analyzer` / `paper_promise_extractor` when no LLM — fast, no API. |
| **LLM-based** | Optional (`--use-llm` where supported); better flexibility, same validity caveats as any LLM extraction. |

A **structured comparison study** (same PDFs, three methods, manual reference) is recommended for publications; the code supports switching modes but does not auto-run that evaluation.

### Parameter uncertainty quantification (not generic “uncertainty”)

Task 2.2 is specifically **parameter uncertainty**: documenting **numeric parameter values** in the model, template fields for literature ranges, and a **confidence** label. It does **not** replace full probabilistic uncertainty quantification (e.g. full priors on every rate) unless you extend the analyzer.

### Why confidence is often “Unknown”

Non-unknown **High / Medium / Low** confidence is only assigned where **hard-coded heuristics** match **disease name** (a small set) and **parameter name** substrings (`uncertainty_analyzer.py`). For **other diseases** or **parameters**, the default is **Unknown** because the tool does **not** query papers or databases automatically — filling confidence properly requires **literature linkage** (DOI, tables) or **expert judgment**, then editing the JSON or extending the code.

### Sensitivity analysis: “parameter mapping” methodology

There is **no separate ML model** for “intelligent” mapping. The sensitivity script **(a)** reads explicit `<parameters>`, **(b)** walks flows and builds names like `contactRate_<source>_to_<target>` for embedded rates, **(c)** **classifies** type (transmission, recovery, …) with **keyword rules** on the parameter name (`beta`, `contact`, `gamma`, …). That is **rule-based / heuristic**, not learned intelligence. See **INSTRUCTIONS.md** (Task 2.3) for the exact behavior.

### Paper collection: venue and BibTeX

Each entry supports **`venue`** (e.g. journal name), **`venueType`** (`journal` \| `conference` \| `book` \| `preprint` \| `other` \| `unknown`), and optional **`bibtex`** (paste from Google Scholar, PubMed, Zotero, etc.). See `analysis/paper_collection.py` and **INSTRUCTIONS.md**.

### Taxonomies and patterns (`reports/taxonomies/`, `reports/patterns/`)

These JSON files are **curated knowledge-base** artifacts. **Automatically** deriving a complete, literature-grounded taxonomy or pattern library would require a **dedicated systematic review** — suitable as **its own paper** or appendix. The pipeline **consumes** these files where present; it does **not** replace a full literature review for taxonomy construction.

---

## Methodology: reference models, papers, and how components are identified

Phase 1 does **not** perform a standalone literature search to discover model structures from scratch. Instead it **analyzes compartmental models already encoded as `.compmodel` XML** and, when you attach a PDF, compares those structures to **what the paper says it will model** (“paper promises”). That distinction matters for reporting and for any future **systematic search** you may add on top.

### What was analyzed (current corpus)

| Layer | What | Notes |
|-------|------|--------|
| **Primary reference models** | Curated gold `.compmodel` files under **`papers/epimde/`** | Batch tools analyze **every** `*.compmodel` in this directory (non-recursive). The `paper_collection` initializer still lists three canonical sources (Tuite et al. 2020; Akowe et al. 2025; Espitia et al. 2022); additional diseases may be present as the corpus grows. |
| **Paired source PDFs** | PDFs in `papers/epimde/` with the same stem as the model (e.g. `dengue.compmodel` + `dengue.pdf`) | Used for gap analysis when found; see `utils/phase1_paths.find_pdf_for_compmodel`. |
| **Extended baselines (optional)** | `phase 2/data/baseline_models/*.compmodel` | Used when `papers/epimde/` is empty and Phase 2 baselines resolve as the default directory (`utils/phase1_paths.py`). |
| **Additional papers for testing** | PDFs under **`papers/new papers/`** | Optional inputs for ad-hoc gap runs; not all are paired to an `epimde` model. |

**Counts change over time.** For a **snapshot of `reports/`** and what each folder contains, see **[`reports/REPORTS.md`](reports/REPORTS.md)**.

### How model components are found

1. **Structure from XML (primary):** `analysis/model_analyzer.py` parses each `.compmodel` file and extracts **compartments**, **flows** (e.g. contact, rate, sources/sinks), and **parameters** (explicit definitions and values embedded in expressions). This is the authoritative list of “model components” for Phase 1.
2. **Promises from papers (optional):** When you pass `--paper-pdf`, `analysis/gap_analyzer.py` extracts what the PDF claims the model includes (pattern-based and/or LLM-assisted), then **compares** that to the XML-derived structure to list **gaps**. Components are therefore **not** mined by crawling abstracts; they are either **read from the machine-readable model** or **contrasted with stated paper intent**.
3. **Systematic search (for reports / extensions):** If you need a **reproducible literature strategy** (e.g. PRISMA-style), treat Phase 1 as the **analysis engine** and define separately: research question, databases, inclusion/exclusion (e.g. compartmental ODE models in infectious disease), screening steps, and how selected papers become `.compmodel` + PDF pairs. The current project uses a **purposefully sampled** set of high-quality reference models rather than an exhaustive scrape.

---

## Sensitivity analysis methods (summary + citations)

Phase 1’s `analysis/sensitivity_analysis.py` implements four approaches. Each is standard in **global sensitivity analysis (GSA)**; short characterization and primary references:

| Method | Idea | Strengths | Limitations | Key reference |
|--------|------|-------------|-------------|----------------|
| **Morris (elementary effects)** | One-at-a-time moves along random trajectories in discrete levels; mean absolute effect **μ\*** screens important factors, **σ** hints at nonlinearity/interaction. | Cheap **screening** with many parameters; well understood. | Not a full variance decomposition; interactions only indirectly. | Morris (1991), *Technometrics*. |
| **Grid** | Regular grid over selected parameters (factorial / lattice). | Transparent, **exhaustive** over the grid; good for **very few** factors. | **Curse of dimensionality**; impractical for many parameters. | Design-of-experiments texts; GSA context in Saltelli et al. (2008). |
| **Random (Monte Carlo)** | Independent random draws over parameter ranges; output variance explored empirically. | Simple, **flexible**, easy to parallelize; no grid structure required. | May need **many** runs for stable estimates in high dimensions. | Saltelli et al. (2008); Monte Carlo sampling in GSA. |
| **Sobol sequences** | **Quasi-random** Sobol’ points (`scipy.stats.qmc.Sobol`) for parameter draws; **not** full Saltelli-style variance *indices* unless extended. | Better coverage than IID random for a given *n*. | Needs **scipy**; many runs in high dimension; Saltelli (2010) indices need extra computation. | Sobol’ (1993); Saltelli et al. (2010). |

**References (for methods, not disease-specific results)**

- Morris, M. D. (1991). Factorial sampling plans for preliminary computational experiments. *Technometrics*, 33(2), 161–174.
- Sobol’, I. M. (1993). Sensitivity estimates for nonlinear mathematical models. *Mathematical Modelling and Computational Experiment*, 1(4), 407–414.
- Saltelli, A., Ratto, M., Andres, T., Campolongo, F., Cariboni, J., Gatelli, D., Saisana, M., & Tarantola, S. (2008). *Global Sensitivity Analysis: The Primer*. Wiley.
- Saltelli, A., et al. (2010). Variance based sensitivity analysis of model output: Design and estimator for the total sensitivity index. *Computer Physics Communications*, 181(2), 259–270.

*For CLI defaults, file layouts, and longer worked examples, see **[INSTRUCTIONS.md](INSTRUCTIONS.md)** (Task 2.3).*

## Documentation

| File | Purpose |
|------|---------|
| **[INSTRUCTIONS.md](INSTRUCTIONS.md)** | Setup, commands, per-task CLI, programmatic notes, troubleshooting, **appendix** with the former Phase 1 results summary. |
| **[reports/REPORTS.md](reports/REPORTS.md)** | **Catalog of `reports/`** — generated vs static files, filenames, Phase 3 usage, regeneration. |

---

*Run all shell commands from the `phase 1` directory (`cd "phase 1"`).*
