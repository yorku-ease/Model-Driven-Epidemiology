# Model-Driven Epidemiology

Research workspace for **model-driven epidemiology**: compartmental infectious-disease models, extraction from scientific papers with LLM assistance, and validation against benchmarks. It has two connected parts: the **EpiMDE** Eclipse workspace for the **`.compmodel`** metamodel, and the **phased Python pipeline** that operates on those models.

---

## 1. EpiMDE compartmental workspace (`Compartmental/`)

**[`Compartmental/`](Compartmental/)** is the Eclipse/EMF **EpiMDE** project: metamodel (`compartmental.ecore`), tree and Sirius editors, equation generation, and example **`.compmodel`** files. This is where the **compartmental model format** and IDE-side tooling live.

Full documentation: **[`Compartmental/README.md`](Compartmental/README.md)**.

---

## 2. AI-assisted pipeline (Phases 1–2.5–3–4)

**[`AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/`](AI-ASSISTED%20MODEL-DRIVEN%20EPIDEMIOLOGY/README.md)** — end-to-end automation on top of the same **`.compmodel`** ecosystem:

| Phase | Role |
|-------|------|
| **Phase 1** | Reference model analysis, uncertainty/sensitivity tooling, epidemiology metamodel (`metamodel_epidemiology.json`) |
| **Phase 2** | PDF → structured extraction → `.compmodel` draft |
| **Phase 2.5** | Disease-level **canonical feature profiles** (merge gold models per disease folder); optional draft alignment |
| **Phase 3** | Gap detection, rule-based retrieval + LLM fill, validation vs gold standard |
| **Phase 4** | Uncertainty quantification (Monte Carlo, sensitivity) on filled models |

Overview: **`AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/README.md`**. Install dependencies with `pip install -r requirements.txt` from that folder. **How to run** each phase (commands, API keys, flags) is documented in each phase’s **`INSTRUCTIONS.md`**, not in the top-level README files.

---

*For runnable commands and environment setup, follow each phase’s **`INSTRUCTIONS.md`** after the overview README.*
