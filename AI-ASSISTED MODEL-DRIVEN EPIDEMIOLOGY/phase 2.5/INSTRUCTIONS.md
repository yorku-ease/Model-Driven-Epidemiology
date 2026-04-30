# Phase 2.5: how to run

This file covers **commands, flags, and paths**. For the **conceptual motivation, definitions, and interpretation of results**, see **README.md** in this folder.

---

## Dependencies

- **Python:** 3.x. The Phase 2.5 scripts use the **standard library only** (no required `pip` packages for this phase).
- **Optional — diagram images:** Install **Graphviz** so the `dot` executable is on your `PATH`. Then `run_phase25.py` writes **`feature_tree.png`** and **`feature_tree.svg`** beside **`feature_tree.dot`**. Without Graphviz, only the `.dot` file is produced (plus a warning in the run summary).

  Examples: Debian/Ubuntu `sudo apt install graphviz`; Fedora `sudo dnf install graphviz`; macOS `brew install graphviz`; Windows Chocolatey `choco install graphviz`.

- **Full workspace stack** (PDF tools, LLMs elsewhere in the project): install from **`../requirements.txt`** relative to the parent `AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/` folder if needed.

---

## Working directory

Commands below assume the current directory is **`phase 2.5/`** (next to `phase 2/data/` and typically `phase 2/reports/`).

---

## Default pipeline (one command)

```bash
python3 run_phase25.py --data-root "../phase 2/data" --report-dir "./reports"
```

**What this does, in order:**

1. Builds **canonical profiles** for every benchmark disease (OR-merge of gold **`.compmodel`** files under each disease folder) and saves **`canonical_profiles.json`** and **`canonical_by_disease/<slug>.json`**.
2. Writes one **gold-derived disease feature model** file per disease: **`disease_feature_models/<slug>.json`** (Boolean **`assignment`**, gold source list—primary FM citation bundle).
3. Exports the **feature tree diagram** under **`fm_diagram/`** (DOT, and PNG/SVG when Graphviz `dot` is available).
4. Writes one **SPL report per disease** under **`spl_configurator/`** (constraint checks on the gold tuple; references the FM path).
5. Scans **`phase 2/reports/`** for **`model_draft.compmodel`**, compares each Phase 2 draft to the matching disease FM, writes **`draft_validations/*.json`**.
6. Writes **`phase25_run_summary.json`** (steps include which disease FM files were written, plus draft validation payloads when produced).

**Main output paths:**

| Path | Contents |
|------|----------|
| `reports/disease_feature_models/<slug>.json` | Gold-backed **disease FM** (`assignment`, gold file list); cite for “FM from standards” |
| `reports/canonical_profiles.json` | All diseases: merged gold tuples |
| `reports/canonical_by_disease/<slug>.json` | Per-disease detail (per-model breakdown, signals) |
| `reports/fm_diagram/feature_tree.dot` | Graphviz feature hierarchy |
| `reports/fm_diagram/feature_tree.png`, `.svg` | Rendered diagram (requires `dot`) |
| `reports/spl_configurator/<slug>_spl_report.json` | SPL/CNF check + propagation; FM path embedded |
| `reports/draft_validations/*.json` | Phase 2 draft vs gold FM |
| `reports/phase25_run_summary.json` | Full run summary |

---

## Common options

**Skip scanning Phase 2 drafts** (faster; no `draft_validations/` updates for batch scan):

```bash
python3 run_phase25.py --no-validate-phase2-drafts --data-root "../phase 2/data" --report-dir "./reports"
```

**Canonical profiles + disease FM files only** (no SPL, no diagram, no Phase 2 draft scan):

```bash
python3 run_phase25.py --profiles-only --data-root "../phase 2/data" --report-dir "./reports"
```
Still writes **`disease_feature_models/<slug>.json`** alongside **`canonical_profiles.json`**.

**Other useful flags**

| Flag | Effect |
|------|--------|
| `--no-spl` | Skip SPL reports |
| `--no-diagram` | Skip diagram export |
| `--no-diagram-images` | Write `.dot` only (no PNG/SVG) |
| `--with-witness` | Include extra SAT witness in SPL output (see README) |
| `--disease <slug>` | Restrict to one benchmark disease |
| `--phase2-reports <path>` | Root folder to scan for Phase 2 report subfolders |

---

## Validate a single Phase 2 draft

```bash
python3 run_phase25.py --data-root "../phase 2/data" --report-dir "./reports" \
  --validate-draft "../phase 2/reports/<run_folder>/model_draft.compmodel" \
  --disease <slug>
```

Use the disease slug that matches that run (e.g. `malaria` for folders like `malaria2_llm_...`).

---

## SPL-only helper (`run_configurator.py`)

For a **single disease**, re-export the diagram and write one SPL JSON (no full pipeline):

```bash
python3 run_configurator.py --disease malaria
```

For reproducibility and parity with thesis experiments, prefer **`run_phase25.py`** for the full artefact set.

---

## Adding a benchmark disease

Add at least one PDF and gold **`.compmodel`** under **`phase 2/data/diseases/<new_name>/`**, then run **`run_phase25.py`** again so canonical profiles and SPL reports include the new disease.

---

## Paths and layout

Defaults assume **`phase 2.5/`** sits beside **`phase 2/data/`** and **`phase 2/reports/`**. Adjust `--data-root`, `--report-dir`, and `--phase2-reports` if your tree differs.
