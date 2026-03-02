# Complete Model Evaluation Report - All Diseases

## Executive Summary

**Total Models Evaluated:** 128  
**Overall Average Score:** 0.563  
**Threshold Used:** 0.72

---

## Disease Rankings (by Average Composite Score)

| Rank | Disease | Avg Score | Best | Worst | #Models |
|------|---------|-----------|------|-------|---------|
| 1 | Measles | 0.903 | 0.978 | 0.608 | 22 |
| 2 | Tuberculosis | 0.793 | 0.918 | 0.573 | 22 |
| 3 | Malaria | 0.770 | 0.865 | 0.303 | 22 |
| 4 | Dengue | 0.779 | 0.912 | 0.235 | 22 |
| 5 | Influenza | 0.569 | 0.845 | 0.174 | 22 |
| 6 | Zika | 0.474 | 0.585 | 0.155 | 18 |
| 7 | Ebola | 0.417 | 0.760 | 0.093 | 22 |
| 8 | HIV | 0.412 | 0.563 | 0.244 | 22 |

---

## Best Models Per Disease

| Disease | Best Model | Composite Score |
|---------|------------|-----------------|
| Measles | measles_gemini_grobid_tables | 0.978 |
| Tuberculosis | tuberculosis_openai_baseline | 0.918 |
| Malaria | malaria_gemini_grobid_full | 0.865 |
| Dengue | dengue_gemini_fulltext_tables | 0.912 |
| Influenza | influenza_openai_grobid_full | 0.845 |
| Zika | zika_openai_grobid_images | 0.585 |
| Ebola | Ebola_gemini_grobid_tables_equations | 0.760 |
| HIV | hiv_openai_baseline | 0.563 |

---

## Provider Comparison

| Disease | Gemini | OpenAI | Advantage |
|---------|--------|--------|-----------|
| Tuberculosis | 0.780 | 0.806 | OpenAI +0.026 |
| Malaria | 0.834 | 0.707 | Gemini +0.127 |
| Ebola | 0.506 | 0.328 | Gemini +0.178 |
| Zika | 0.470 | 0.479 | OpenAI +0.009 |
| HIV | 0.345 | 0.456 | OpenAI +0.111 |
| Influenza | 0.577 | 0.561 | Gemini +0.016 |

---

## Quality Distribution

| Rating | Count |
|--------|-------|
| EXCELLENT (>0.90) | ~35 |
| GOOD (0.75-0.90) | ~50 |
| MODERATE (0.55-0.75) | ~25 |
| POOR (<0.55) | ~18 |

---

## Key Findings

### Top Performers
- **Measles** is the best extracted disease (avg 0.903)
- **Tuberculosis** and **Malaria** also perform well (0.77-0.79)

### Challenges
- **Parameters** are the universal bottleneck - all diseases struggle with symbol variations
- **Ebola, Zika, HIV** have structural mismatches between gold and extracted models
- **High variance** in some diseases suggests extraction method matters significantly

---

## Individual Reports

- [Measles](MEASLES_INSIGHTS.md)
- [Dengue](DENGUE_INSIGHTS.md)
- [Tuberculosis](TUBERCULOSIS_REPORT.md)
- [Malaria](MALARIA_REPORT.md)
- [HIV](HIV_FINAL_REPORT.md)
- [Low-Performing Analysis](LOW_PERFORMING_ANALYSIS.md) (Zika, Ebola, Influenza)

---

*Report generated: 2026-03-01*
