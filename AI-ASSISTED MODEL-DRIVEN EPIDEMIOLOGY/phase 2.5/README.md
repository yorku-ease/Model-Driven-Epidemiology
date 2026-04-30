# Phase 2.5 — What it does

Phase 2.5 sits **between** the LLM-based model extraction pipeline (**Phase 2**) and downstream work (retrieval, analysis, thesis reporting). Its job is **quality control via feature-model comparison**: it checks whether compartmental epidemic models **auto-extracted by an LLM** (Phase 2 **`model_draft.compmodel`** files) **structurally agree** with **hand-curated “gold”** compartment models bundled for each benchmark disease.

Operational commands live in **[INSTRUCTIONS.md](INSTRUCTIONS.md)**.

---

## The core idea

Every compartmental model — SEIR-like families, strata, extras — can be described as a fixed set of **Boolean flags**:

- Latent/exposed class? Vaccination compartment? Hospitalization strata? Birth/recruitment? Vector or zoonotic hosts? Specific **transmission routes** (fecal–oral, respiratory, bloodborne, sexual network, healthcare contact)?

Phase 2.5 encodes those as **`EpiFeatureVector`**, with **17 Boolean dimensions** (plus optional free-text **`matched_signals`** for traceability):

| Theme | Examples of flags |
|--------|-------------------|
| **Transmission routes** | Vector/arthropod, sexual/partner, airborne/respiratory, fecal–oral, bloodborne/vertical, healthcare contact |
| **Natural history** | Latent/exposed, staged/chronic progression, hospitalized/severity stratification, treatment/ART, vaccination, recovered/immune endpoint |
| **Population & space** | Demographic stratification, spatial/patch-style naming |
| **Non-human hosts** | Vector or intermediate species, zoonotic/animal compartments |
| **Demography** | Explicit birth/recruitment/immigration naming |

Inference is **rule-based** (**`src/inference_rules.py`**): patterns over compartment names and flow text in the **`compmodel`** XML. For thin models, optional **folder-name priors** nudge plausible routes given the disease slug (e.g. **malaria**, **dengue**, **zika** ⇒ vector-route and vector/host flags strengthened from the banner name—not a claim that any single paper omitted those).

**Gold benchmarks:** curated **`.compmodel`** files live under **`phase 2/data/diseases/<disease>/`** (often several papers per disease). **Canonical profile** = logical **OR over gold files**: if *any* trusted gold model activates a flag, that flag counts as expected for benchmarking.

Phase 2.5 wraps that canonical tuple into an explicit **`gold_derived_disease_feature_model`** JSON under **`reports/disease_feature_models/<slug>.json`** so you can cite one artefact labelled “gold FM assignment.”

---

## The pipeline (step by step)

1. **Gold-backed profiles** — Per benchmark disease, read gold **`.compmodel`** XML under the disease folder, infer **`EpiFeatureVector`**, optionally apply folder-name priors, merge all gold files with **Boolean OR**, and write **`canonical_profiles.json`**, **`canonical_by_disease/<slug>.json`**, and **`disease_feature_models/<slug>.json`**.

2. **Shared vocabulary figure** — The same **`feature_models/feature_tree.json`** hierarchy drives a Graphviz **`feature_tree.dot`** (+ **PNG/SVG** if **`dot`** is installed). This picture is shared across diseases; assignments differ file-to-file.

3. **SPL consistency checks** — Declarative **cross-tree implications** (**`cross_tree_constraints.json`**) compile to CNF; each disease’s canonical assignment is evaluated in **`spl_configurator/<slug>_spl_report.json`** (“does this gold-derived tuple violate any enabled rule?” plus optional unit propagation).

4. **Phase 2 drafts** — Scan **`phase 2/reports/**/model_draft.compmodel`**, infer flags with **the same engine**, infer disease from the Phase 2 run folder name (**`malaria3_llm_…` → malaria**), and compare draft vs canonical. Each run writes **`draft_validations/<disease>_<phase2_run>_phase25_validation.json`** with missing flags, extras, **`alignment_score_on_canonical_expectations`**, and a pointer **`gold_derived_disease_feature_model`**.

Finally **`phase25_run_summary.json`** records the pipeline steps—optionally embedding all draft-validation records for one reproducible artefact bundle.

---

## What you get — results (benchmark run snapshot)

Figures below come from **one full** `run_phase25.py` invocation over **10** benchmark diseases and every Phase 2 draft reachable by the scanner in this checkout. Your numbers update when you rerun the pipeline after new extractions.

| Artefact | What it contains |
|---------|-------------------|
| **10 canonical disease profiles + 10 FM bundles** — `canonical_by_disease/` and **`disease_feature_models/`** | One merged gold assignment per disease. |
| **Feature diagram** — `reports/fm_diagram/feature_tree.{dot,png,svg}` | Colour-grouped catalogue of Booleans—the **shared** FM vocabulary diagram. |
| **10 SPL reports** — `spl_configurator/*_spl_report.json` | On default constraints in this codebase, **`all_constraints_satisfied`** is **true** for every canonical vector (violations absent). |
| **90 Phase 2 validations** — `draft_validations/*.json`, count `90` run summary | Typical layout: nine Phase 2 extraction runs × three LLM backends per disease (**`draft_validation_records`** keyed by slug in `phase25_run_summary`). |
| **`alignment_score_on_canonical_expectations`** distribution | On the archived run snapshot, scores ranged **approximately 0.2–1.0**; **11** drafts scored **1.0** against merged gold expectations. Drafts scoring below 1.0 mostly reflect **fewer compartments or weaker textual cues** on that run relative to multi-paper OR-merged gold, not inevitable “wrong model.” |

**In short**

Phase 2.5 is a structured **evaluation harness**. It replaces a vague question (“is this LLM epidemic model sensible?”) with a **measurable Boolean alignment score** across **17 epidemiological shape dimensions**, backed by curated gold merges and comparable Phase 2 artefacts. Missing vs extra lists say **what** differs structurally, not merely that an overall score dipped.
