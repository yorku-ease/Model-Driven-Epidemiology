# Phase 2.5: Disease feature models from gold standards, and Phase 2 alignment

Commands and paths: **INSTRUCTIONS.md**.

---

## Idea in one sentence

For **each benchmark disease**, Phase 2.5 defines a **disease-level feature model**—a concise description of **which structural options** compartmental modelling papers use for that disease when we trust curated **gold** `.compmodel` files. Every **Phase 2 extracted draft** can then be **mapped onto the same feature dimensions** so we can compare **automatic extraction** against that **gold-backed feature model** (“does the Phase 2 model agree with how we summarise the curated literature?”).

Software-product-line machinery (feature **tree**, **cross-tree rules**, SPL checks) is there to document and logically relate those Booleans—not to replace epidemiology—but the research story is essentially **gold feature model versus Phase 2 feature assignment**.

---

## What “feature model” means here

In feature-oriented software engineering, a **feature model** names independent or grouped **options** in a designed family of systems. Phase 2.5 applies that analogy to compartmental modelling **structure**:

- Features are Booleans aligned with **`EpiFeatureVector`** (routes, natural history compartments, vaccination, strata, vectors, recruitment, …).
- The **shared feature graph** (`feature_models/feature_tree.json`) is identical for every disease: it fixes the vocabulary and grouping.
- The **instance** for Disease *D* is the **truth assignment** inferred from gold models for *D*: which of those Booleans appear **supported** after rule-based inference (`src/inference_rules.py`) across the curated gold `.compmodel` files.

So the **disease-specific part** is the **Boolean tuple** (+ optional SPL view of consistency with declarative implications), not a different DAG per disease file.

---

## Where the gold standards come from

Benchmark diseases are anchored on **hand-curated gold compartment models** bundled with epidemiology benchmarks. In this repository they are consolidated under **`phase 2/data/diseases/<disease>/`** (PDFs plus one or more **`.compmodel`** files per folder). Earlier **Phase 1** assets (individual exemplars, metamodel descriptions under `phase 1/` may inform how Prompts are written and how quality checks run; they are part of the same methodological lineage—but **Phase 2.5’s numerical “gold truth” tuple is built from whichever gold compartment files you merge in that Phase 2 disease folder**.

If multiple gold papers sit in one folder, Phase 2.5 builds the disease feature model’s active set by taking the **logical OR** flag-by-flag across papers (union of structural motifs present in **any** trusted gold extraction for that disease).

---

## How Phase 2 fits in

Phase 2 produces **`model_draft.compmodel`** files for individual runs (PDF + LLM + timestamp folders under **`phase 2/reports/`**). Phase 2.5 treats each draft as another compartment model encoded in XML and runs **the same inference** to derive a Phase-2-local Boolean assignment.

**Comparison**, per draft and inferred disease slug:

| Gold-backed disease FM (explicit bundle) | Phase 2 draft assignment |
|------------------------------------------|-------------------------|
| `reports/disease_feature_models/<slug>.json` → field **`assignment`** (same tuple as `canonical_feature_vector`) | Inferred flags from **`model_draft.compmodel`** |

`canonical_by_disease/<slug>.json` remains the **full** record (per-model vectors, `matched_signals`, directory paths). The **`disease_feature_models`** copy is the thesis-facing **feature-model instance** for citing.

Discrepancies are surfaced as **missing**, **extra**, and an **alignment score** (`reports/draft_validations/*.json`). Each record includes **`gold_derived_disease_feature_model`** (relative path) and **`comparison_role`** so the JSON states that the comparison is Phase 2 versus that gold-derived FM.

This is deliberately **orthogonal** to whether the epidemic narrative in the PDF is correct; it evaluates **representation agreement** along the finite feature schema only.

---

## Artefacts tied to this story

| Output | Role in “FM + Phase 2” narrative |
|--------|----------------------------------|
| `reports/disease_feature_models/<slug>.json` | **Gold-derived disease FM bundle** (`assignment` Boolean tuple, list of gold filenames, pointer to extended `canonical_by_disease`). Cite this file when you mean “the disease feature model instantiated from standards.” |
| `reports/canonical_by_disease/<slug>.json` | Extended record: same tuple as **`assignment`** above, plus per-model breakdown and `matched_signals`. |
| `reports/canonical_profiles.json` | All diseases’ gold-backed assignments in one file. |
| `reports/fm_diagram/feature_tree.{dot,png,svg}` | Shared **feature vocabulary** diagram (documentation; not a biological pathway). |
| `reports/spl_configurator/<slug>_spl_report.json` | Cross-tree constraint checks on the gold tuple; each report includes **`gold_derived_disease_feature_model`** (relative path). |
| `reports/draft_validations/` | Phase 2 drafts vs gold FM (**`comparison_role`**, **`gold_derived_disease_feature_model`**, alignment fields). |
| `reports/phase25_run_summary.json` | Run provenance; **`steps`** include **`disease_feature_models`** (slugs written). |

---

## Optional extensions (manual or future tooling)

- **Appendix table (CSV/Markdown):** columns *feature × gold × Phase 2 draft* — derive from **`disease_feature_models/<slug>.json`** (`assignment`) and **`draft_validations`** **`draft_feature_vector`**.
- **Narrower gold corpus:** restricting the OR-merge to Phase 1 `papers/epimde/` exemplars only would require extra path configuration (not enforced by defaults).
