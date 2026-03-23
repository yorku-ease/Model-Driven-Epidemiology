# Phase 3 — Overall Gap Analysis Report

## Database
- Entries: **54**
- Parameters indexed: **585**
- Text chunks: **894**
- Diseases: cholera, covid, dengue, ebola, hiv, influenza, malaria, measles, tuberculosis, zika

## Summary
- Reports analysed: **30** (10 diseases x 3 providers)
- Comparison mode: **30** gold-standard, **0** promise-based
- Reports with **zero gaps**: **3 / 30** (10%)
- Total gaps across all reports: **189**
- Filled: RAG: **136** | Inference: **6** | Flagged: **47**

## Gap counts by disease and provider

| Disease | Claude | Gemini | Openai |
|---------|--------|--------|--------|
| Cholera      | 1 (RAG:0 inf:0 flag:1) | 1 (RAG:0 inf:0 flag:1) | 1 (RAG:0 inf:0 flag:1) |
| COVID-19     | 1 (RAG:0 inf:0 flag:1) | 1 (RAG:0 inf:0 flag:1) | 1 (RAG:0 inf:0 flag:1) |
| Dengue       | 8 (RAG:7 inf:0 flag:1) | 7 (RAG:7 inf:0 flag:0) | 7 (RAG:7 inf:0 flag:0) |
| Ebola        | 3 (RAG:2 inf:0 flag:1) | 4 (RAG:2 inf:0 flag:2) | 6 (RAG:4 inf:0 flag:2) |
| Flu          | 0 | 0 | 0 |
| HIV          | 3 (RAG:0 inf:0 flag:3) | 3 (RAG:0 inf:0 flag:3) | 3 (RAG:0 inf:0 flag:3) |
| Malaria      | 5 (RAG:4 inf:0 flag:1) | 19 (RAG:12 inf:0 flag:7) | 20 (RAG:17 inf:0 flag:3) |
| Measles      | 3 (RAG:2 inf:1 flag:0) | 19 (RAG:18 inf:1 flag:0) | 19 (RAG:18 inf:1 flag:0) |
| Tuberculosis | 1 (RAG:1 inf:0 flag:0) | 1 (RAG:1 inf:0 flag:0) | 1 (RAG:1 inf:0 flag:0) |
| Zika         | 12 (RAG:8 inf:1 flag:3) | 15 (RAG:8 inf:1 flag:6) | 24 (RAG:17 inf:1 flag:6) |
| **Total** | **37** | **70** | **82** |

## Gap breakdown (all reports combined)

| Category | Count |
|----------|-------|
| Missing compartments | 44 |
| Missing parameters | 142 |
| Missing stratifications | 3 |
| Missing interventions | 0 |
| **Total** | **189** |

## Fill validation accuracy (vs gold standard)

- Parameters compared: **131**
- Exact (<1% error): **54** (41%)
- Close (<10% error): **0**
- Approximate (<50% error): **3**
- Poor (>50% error): **74**
- **Accuracy (exact+close)**: **41.2%**
- Median relative error: **100.0%**

| Disease | Provider | Compared | Exact | Close | Approx | Poor | Accuracy |
|---------|----------|----------|-------|-------|--------|------|----------|
| Dengue | Claude | 7 | 6 | 0 | 0 | 1 | 86.0% |
| Dengue | Gemini | 7 | 6 | 0 | 0 | 1 | 86.0% |
| Dengue | Openai | 7 | 6 | 0 | 0 | 1 | 86.0% |
| Ebola | Claude | 2 | 1 | 0 | 0 | 1 | 50.0% |
| Ebola | Gemini | 2 | 1 | 0 | 0 | 1 | 50.0% |
| Ebola | Openai | 2 | 1 | 0 | 0 | 1 | 50.0% |
| Malaria | Claude | 4 | 4 | 0 | 0 | 0 | 100.0% |
| Malaria | Gemini | 10 | 10 | 0 | 0 | 0 | 100.0% |
| Malaria | Openai | 14 | 14 | 0 | 0 | 0 | 100.0% |
| Measles | Claude | 3 | 0 | 0 | 1 | 2 | 0.0% |
| Measles | Gemini | 17 | 1 | 0 | 1 | 15 | 6.0% |
| Measles | Openai | 17 | 1 | 0 | 1 | 15 | 6.0% |
| Tuberculosis | Claude | 1 | 0 | 0 | 0 | 1 | 0.0% |
| Tuberculosis | Gemini | 1 | 0 | 0 | 0 | 1 | 0.0% |
| Tuberculosis | Openai | 1 | 0 | 0 | 0 | 1 | 0.0% |
| Zika | Claude | 9 | 0 | 0 | 0 | 9 | 0.0% |
| Zika | Gemini | 9 | 0 | 0 | 0 | 9 | 0.0% |
| Zika | Openai | 18 | 3 | 0 | 0 | 15 | 17.0% |

### By provider (validation)

| Provider | Gaps | RAG | Inference | Flagged | Compared | Exact | Accuracy |
|----------|------|-----|-----------|---------|----------|-------|----------|
| Claude | 37 | 24 | 2 | 11 | 26 | 11 | 42.3% |
| Gemini | 70 | 48 | 2 | 20 | 46 | 18 | 39.1% |
| Openai | 82 | 64 | 2 | 16 | 59 | 25 | 42.4% |

Accuracy = exact matches / compared (vs gold standard). RAG supplies most fills; inference is used when RAG finds nothing.

## Interpretation

**3/30** reports are gap-free. The remaining **27** reports have a total of **189** gaps.

- **Gap counts** depend on the Phase 2 extractor (different LLMs extract different parameters), so totals vary by provider.
- **Fills** come from RAG first (paper database lookup); when RAG finds nothing, LLM inference is tried; the rest are flagged for manual review.
- **Validation** compares only *filled parameter values* to the gold-standard baseline; compartments/stratifications are not valued, so they are not in the accuracy counts.
- For per-disease details, open the corresponding `reports/<disease>_<provider>_phase3/gap_report.md` and `phase3_validation.json`.
