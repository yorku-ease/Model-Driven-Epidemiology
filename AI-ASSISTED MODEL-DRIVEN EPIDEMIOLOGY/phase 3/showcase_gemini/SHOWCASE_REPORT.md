# Phase 3 — Showcase (best Phase 2 LLM × RAG / LLM / both, Phase 3 inference: **gemini**)

For each disease: **best Phase 2** run among **gemini / openai / claude** (by evaluation score), then Phase 3 with **your chosen** `--llm-provider` (**gemini** here) for inference in three modes.

| Disease | Winning Phase 2 LLM | Best Phase 2 report | Phase2 score | Winner | retrieval_only | llm_only | both |
|---------|----------------------|---------------------|--------------|--------|----------|----------|------|
| Cholera1 | **claude** | `cholera1_llm_claude_20260321_234449` | 98.2 | **retrieval_only** | g=2 acc=0.0% cF1=0.8889 fF1=0.5455 | g=2 acc=0.0% cF1=0.6667 fF1=0.4 | g=2 acc=0.0% cF1=0.8889 fF1=0.5455 |
| Cholera2 | **openai** | `cholera2_llm_openai_20260407_211614` | 81.1 | **retrieval_only** | g=48 acc=91.7% cF1=1.0 fF1=0.9714 | g=48 acc=0.0% cF1=0.8889 fF1=0.9655 | g=48 acc=91.7% cF1=1.0 fF1=0.9412 |
| Cholera3 | **openai** | `cholera3_llm_openai_20260407_211654` | 97.8 | **retrieval_only** | g=2 acc=0.0% cF1=0.5714 fF1=None | g=2 acc=0.0% cF1=0.25 fF1=None | g=2 acc=0.0% cF1=0.2857 fF1=None |
| Covid1 | **claude** | `covid1_llm_claude_20260321_234526` | 80.0 | **retrieval_only** | g=2 acc=0.0% cF1=0.9677 fF1=0.9767 | g=2 acc=0.0% cF1=0.9677 fF1=0.9767 | g=2 acc=0.0% cF1=0.875 fF1=0.9302 |
| Covid2 | **gemini** | `covid2_llm_gemini_20260407_204358` | 92.6 | **retrieval_only** | g=1 acc=100.0% cF1=1.0 fF1=1.0 | g=1 acc=0.0% cF1=1.0 fF1=1.0 | g=1 acc=100.0% cF1=1.0 fF1=1.0 |
| Covid3 | **gemini** | `covid3_llm_gemini_20260407_204521` | 84.0 | **retrieval_only** | g=19 acc=100.0% cF1=0.75 fF1=None | g=19 acc=0.0% cF1=0.5 fF1=None | g=19 acc=100.0% cF1=0.4444 fF1=None |
| Dengue1 | **claude** | `dengue1_llm_claude_20260321_234654` | 93.9 | **retrieval_only** | g=4 acc=100.0% cF1=1.0 fF1=0.8182 | g=4 acc=0.0% cF1=1.0 fF1=0.8182 | g=4 acc=100.0% cF1=1.0 fF1=0.8182 |
| Dengue2 | **gemini** | `dengue2_llm_gemini_20260407_204629` | 82.2 | **retrieval_only** | g=14 acc=100.0% cF1=1.0 fF1=0.8333 | g=14 acc=20.0% cF1=1.0 fF1=0.8333 | g=14 acc=100.0% cF1=1.0 fF1=0.8333 |
| Dengue3 | **gemini** | `dengue3_llm_gemini_20260407_204755` | 83.8 | **retrieval_only** | g=26 acc=75.0% cF1=0.6923 fF1=0.75 | g=26 acc=0.0% cF1=0.6667 fF1=0.75 | g=26 acc=0.0% cF1=0.6667 fF1=0.75 |
| Ebola1 | **claude** | `ebola1_llm_claude_20260321_234954` | 81.4 | **retrieval_only** | g=12 acc=0.0% cF1=0.7692 fF1=0.7143 | g=12 acc=0.0% cF1=0.6154 fF1=0.6154 | g=12 acc=0.0% cF1=0.6154 fF1=0.6154 |
| Ebola2 | **openai** | `ebola2_llm_openai_20260407_212046` | 85.7 | **retrieval_only** | g=5 acc=80.0% cF1=0.7143 fF1=0.5882 | g=5 acc=0.0% cF1=0.7143 fF1=0.5882 | g=5 acc=80.0% cF1=0.7143 fF1=0.5882 |
| Ebola3 | **claude** | `ebola3_llm_claude_20260407_214336` | 86.9 | **retrieval_only** | g=6 acc=0.0% cF1=0.7407 fF1=0.6667 | g=6 acc=0.0% cF1=0.7407 fF1=0.6667 | g=6 acc=0.0% cF1=0.7407 fF1=0.6667 |
| Hiv1 | **gemini** | `hiv1_llm_gemini_20260321_233341` | 85.5 | **retrieval_only** | g=9 acc=0.0% cF1=0.9474 fF1=0.8333 | g=9 acc=0.0% cF1=0.9474 fF1=0.8333 | g=9 acc=0.0% cF1=0.9 fF1=0.8333 |
| Hiv2 | **openai** | `hiv2_llm_openai_20260407_212245` | 83.8 | **retrieval_only** | g=22 acc=0.0% cF1=0.7059 fF1=0.6364 | g=22 acc=0.0% cF1=0.5882 fF1=0.5 | g=22 acc=0.0% cF1=0.5263 fF1=0.5 |
| Hiv3 | **gemini** | `hiv3_llm_gemini_20260407_205438` | 82.2 | **retrieval_only** | g=26 acc=0.0% cF1=0.9091 fF1=0.9189 | g=26 acc=0.0% cF1=0.375 fF1=0.4364 | g=26 acc=0.0% cF1=0.5714 fF1=0.8235 |
| Influenza1 | **gemini** | `influenza1_llm_gemini_20260321_233517` | 88.6 | **retrieval_only** | g=1 acc=0.0% cF1=0.7895 fF1=0.8889 | g=1 acc=0.0% cF1=0.7895 fF1=0.8889 | g=1 acc=0.0% cF1=0.7895 fF1=0.8889 |
| Influenza2 | **claude** | `influenza2_llm_claude_20260407_214737` | 80.0 | **retrieval_only** | g=16 acc=0.0% cF1=0.6897 fF1=0.4 | g=16 acc=0.0% cF1=0.6897 fF1=0.4 | g=16 acc=0.0% cF1=0.6897 fF1=0.4 |
| Influenza3 | **claude** | `influenza3_llm_claude_20260407_214824` | 83.5 | **retrieval_only** | g=16 acc=70.0% cF1=0.8889 fF1=0.8148 | g=16 acc=0.0% cF1=0.8889 fF1=0.8148 | g=16 acc=0.0% cF1=0.8889 fF1=0.8148 |
| Malaria1 | **openai** | `malaria1_llm_openai_20260326_075326` | 89.5 | **both** | g=7 acc=0.0% cF1=1.0 fF1=0.9167 | g=7 acc=0.0% cF1=1.0 fF1=0.9167 | g=7 acc=28.6% cF1=1.0 fF1=0.9167 |
| Malaria2 | **claude** | `malaria2_llm_claude_20260407_214918` | 80.0 | **retrieval_only** | g=11 acc=0.0% cF1=0.875 fF1=0.8 | g=11 acc=0.0% cF1=0.875 fF1=0.8 | g=11 acc=0.0% cF1=0.875 fF1=0.8 |
| Malaria3 | **claude** | `malaria3_llm_claude_20260407_215058` | 80.0 | **retrieval_only** | g=40 acc=0.0% cF1=0.9524 fF1=0.913 | g=40 acc=0.0% cF1=0.9091 fF1=0.913 | g=40 acc=0.0% cF1=0.9091 fF1=0.913 |
| Measles1 | **openai** | `measles1_llm_openai_20260326_075510` | 89.1 | **retrieval_only** | g=6 acc=100.0% cF1=1.0 fF1=0.9756 | g=6 acc=0.0% cF1=1.0 fF1=0.9756 | g=6 acc=100.0% cF1=1.0 fF1=0.9756 |
| Measles2 | **openai** | `measles2_llm_openai_20260407_212742` | 81.7 | **retrieval_only** | g=18 acc=100.0% cF1=0.875 fF1=0.9 | g=18 acc=0.0% cF1=0.8235 fF1=0.9 | g=18 acc=0.0% cF1=0.75 fF1=0.8421 |
| Measles3 | **gemini** | `measles3_llm_gemini_20260407_210404` | 82.7 | **retrieval_only** | g=12 acc=0.0% cF1=1.0 fF1=0.9333 | g=12 acc=0.0% cF1=0.9333 fF1=0.9333 | g=12 acc=0.0% cF1=1.0 fF1=0.9333 |
| Tuberculosis1 | **claude** | `tuberculosis1_llm_claude_20260321_235852` | 89.4 | **retrieval_only** | g=4 acc=0.0% cF1=0.75 fF1=0.6667 | g=4 acc=0.0% cF1=0.75 fF1=0.6667 | g=4 acc=0.0% cF1=0.75 fF1=0.6667 |
| Tuberculosis2 | **openai** | `tuberculosis2_llm_openai_20260407_212903` | 82.4 | **retrieval_only** | g=38 acc=0.0% cF1=0.9333 fF1=0.7857 | g=38 acc=0.0% cF1=0.9333 fF1=0.7857 | g=38 acc=0.0% cF1=0.9333 fF1=0.7857 |
| Tuberculosis3 | **claude** | `tuberculosis3_llm_claude_20260407_215536` | 83.2 | **retrieval_only** | g=40 acc=0.0% cF1=0.8235 fF1=0.6667 | g=40 acc=0.0% cF1=0.6667 fF1=0.4762 | g=40 acc=0.0% cF1=0.6316 fF1=0.4762 |
| Zika1 | **claude** | `zika1_llm_claude_20260321_235943` | 89.5 | **retrieval_only** | g=13 acc=85.7% cF1=0.8696 fF1=0.8421 | g=13 acc=0.0% cF1=0.8696 fF1=0.8421 | g=13 acc=0.0% cF1=0.8333 fF1=0.8421 |
| Zika2 | **claude** | `zika2_llm_claude_20260407_215657` | 81.5 | **retrieval_only** | g=29 acc=0.0% cF1=0.8333 fF1=0.6829 | g=29 acc=0.0% cF1=0.6829 fF1=0.6154 | g=29 acc=0.0% cF1=0.7692 fF1=0.6829 |
| Zika3 | **claude** | `zika3_llm_claude_20260407_215851` | 84.4 | **retrieval_only** | g=12 acc=0.0% cF1=1.0 fF1=0.7692 | g=12 acc=0.0% cF1=1.0 fF1=0.7692 | g=12 acc=0.0% cF1=1.0 fF1=0.7692 |

## How to read

- **Winner** = lowest gap count, then highest parameter accuracy vs gold, then highest mean compartment/flow F1 (same ordering as `select_best_model.py`).
- Subfolders: `retrieval_only/`, `llm_only/`, `both/` each contain `<disease>_gemini_phase3/` with `phase3_gaps.json`, …, and `phase3_showcase_source.json` (which Phase 2 folder was used).
