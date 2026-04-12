# Phase 3 — Showcase (best Phase 2 LLM × Retrieval / LLM / both, Phase 3 inference: **gemini**)

For each disease: **best Phase 2** run among **gemini / openai / claude** (by evaluation score), then Phase 3 with **your chosen** `--llm-provider` (**gemini** here) for inference in three modes.

| Disease | Winning Phase 2 LLM | Best Phase 2 report | Phase2 score | Winner | retrieval_only | llm_only | both |
|---------|----------------------|---------------------|--------------|--------|----------|----------|------|
| Zika3 | **claude** | `zika3_llm_claude_20260407_215851` | 84.4 | **retrieval_only** | g=12 acc=0.0% cF1=1.0 fF1=0.7692 | g=12 acc=0.0% cF1=1.0 fF1=0.7692 | g=12 acc=0.0% cF1=1.0 fF1=0.7692 |

## How to read

- **Winner** = lowest gap count, then highest parameter accuracy vs gold, then highest mean compartment/flow F1.
- Subfolders: `retrieval_only/`, `llm_only/`, `both/` each contain `<disease>_gemini_phase3/` with `phase3_gaps.json`, …, and `phase3_showcase_source.json` (which Phase 2 folder was used).
