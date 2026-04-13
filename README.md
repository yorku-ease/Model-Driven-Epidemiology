# Model-Driven Epidemiology

Research workspace for **model-driven epidemiology**: analyzing compartmental infectious-disease models, extracting structure from scientific papers with LLM assistance, and validating models against benchmarks.

## Main project (this scope)

**[`AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/`](AI-ASSISTED%20MODEL-DRIVEN%20EPIDEMIOLOGY/README.md)** — end-to-end pipeline:

| Area | Role |
|------|------|
| **Phase 1** | Reference model analysis, uncertainty/sensitivity tooling, epidemiology metamodel (`metamodel_epidemiology.json`) |
| **Phase 2** | PDF → structured extraction → `.compmodel` draft |
| **Phase 3** | Gap detection, rule-based retrieval + LLM fill, validation vs gold standard |
| **Phase 4** | Uncertainty quantification (Monte Carlo, sensitivity) on filled models |

Start with **`AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/README.md`** for the pipeline overview. Install dependencies with `pip install -r requirements.txt` from that folder. **How to run** each phase (commands, API keys, flags) lives only in each phase’s **`INSTRUCTIONS.md`**, not in README files.

## Optional: EpiMDE Eclipse workspace

**`Compartmental/`** holds an Eclipse/EMF **EpiMDE** workspace for editing compartmental models in the IDE. It is **not required** to run the AI-assisted Python pipeline above. This repository’s **documented scope** is **epidemiological** compartmental models only.

---

*For thesis/paper work, use the AI-assisted pipeline README as the canonical guide.*
