# Phase 3 — Showcase (best Phase 2 LLM × RAG / LLM / both, Phase 3 inference: **gemini**)

For each disease: **best Phase 2** run among **gemini / openai / claude** (by evaluation score), then Phase 3 with **your chosen** `--llm-provider` (**gemini** here) for inference in three modes.

| Disease | Winning Phase 2 LLM | Best Phase 2 report | Phase2 score | Winner | rag_only | llm_only | both |
|---------|----------------------|---------------------|--------------|--------|----------|----------|------|
| Cholera | **claude** | `cholera_llm_claude_20260321_234449` | 98.2 | **rag_only** | g=2 acc=0.0% cF1=0.8889 fF1=0.5455 | g=2 acc=0.0% cF1=0.6667 fF1=0.4 | g=2 acc=0.0% cF1=0.6667 fF1=0.4 |
| Covid | **claude** | `covid_llm_claude_20260321_234526` | 80.0 | **rag_only** | g=2 acc=0.0% cF1=0.9677 fF1=0.9767 | g=2 acc=0.0% cF1=0.875 fF1=0.9302 | g=2 acc=0.0% cF1=0.875 fF1=0.9302 |
| Dengue | **claude** | `dengue_llm_claude_20260321_234654` | 93.9 | **rag_only** | g=4 acc=100.0% cF1=1.0 fF1=0.8182 | g=4 acc=0.0% cF1=1.0 fF1=0.8182 | g=4 acc=100.0% cF1=1.0 fF1=0.8182 |
| Ebola | **claude** | `ebola_llm_claude_20260321_234954` | 81.4 | **rag_only** | g=12 acc=0.0% cF1=0.7692 fF1=0.7143 | g=12 acc=0.0% cF1=0.6154 fF1=0.6154 | g=12 acc=0.0% cF1=0.6154 fF1=0.6154 |
| Flu | **gemini** | `flu_llm_gemini_20260321_233517` | 88.6 | **rag_only** | g=1 acc=0.0% cF1=0.7895 fF1=0.8889 | g=1 acc=0.0% cF1=0.7895 fF1=0.8889 | g=1 acc=0.0% cF1=0.7895 fF1=0.8889 |
| Hiv | **gemini** | `hiv_llm_gemini_20260321_233341` | 85.5 | **rag_only** | g=9 acc=0.0% cF1=0.9474 fF1=0.8333 | g=9 acc=0.0% cF1=0.9 fF1=0.8333 | g=9 acc=0.0% cF1=0.8 fF1=0.8333 |
| Malaria | **openai** | `malaria_llm_openai_20260326_075326` | 89.5 | **both** | g=7 acc=0.0% cF1=1.0 fF1=0.9167 | g=7 acc=0.0% cF1=1.0 fF1=0.9167 | g=7 acc=28.6% cF1=1.0 fF1=0.9167 |
| Measles | **openai** | `measles_llm_openai_20260326_075510` | 89.1 | **rag_only** | g=6 acc=50.0% cF1=1.0 fF1=0.9756 | g=6 acc=0.0% cF1=1.0 fF1=0.9756 | g=6 acc=50.0% cF1=1.0 fF1=0.9756 |
| Tuberculosis | **claude** | `tuberculosis_llm_claude_20260321_235852` | 89.4 | **rag_only** | g=4 acc=0.0% cF1=0.75 fF1=0.6667 | g=4 acc=0.0% cF1=0.75 fF1=0.6667 | g=4 acc=0.0% cF1=0.75 fF1=0.6667 |
| Zika | **claude** | `zika_llm_claude_20260321_235943` | 89.5 | **rag_only** | g=13 acc=71.4% cF1=0.8696 fF1=0.8421 | g=13 acc=0.0% cF1=0.75 fF1=0.7059 | g=13 acc=0.0% cF1=0.8333 fF1=0.8421 |

## How to read

- **Winner** = lowest gap count, then highest parameter accuracy vs gold, then highest mean compartment/flow F1 (same ordering as `select_best_model.py`).
- Subfolders: `rag_only/`, `llm_only/`, `both/` each contain `<disease>_gemini_phase3/` with `phase3_gaps.json`, …, and `phase3_showcase_source.json` (which Phase 2 folder was used).
