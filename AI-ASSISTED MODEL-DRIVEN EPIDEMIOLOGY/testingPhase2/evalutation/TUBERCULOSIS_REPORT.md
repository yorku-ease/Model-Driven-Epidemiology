# Tuberculosis Model Evaluation Report

## Summary

**Average Composite Score**: 0.793  
**Best Model**: openai_baseline (0.918)  
**Quality**: GOOD

---

## Results

| Model | Composite | Quality |
|-------|----------|---------|
| openai_baseline | 0.918 | EXCELLENT |
| openai_grobid_full | 0.898 | EXCELLENT |
| gemini_grobid_tables | 0.893 | EXCELLENT |
| gemini_grobid_tables_equations | 0.889 | EXCELLENT |
| gemini_grobid_tables_images | 0.885 | EXCELLENT |
| gemini_grobid_images | 0.876 | EXCELLENT |
| gemini_grobid_full | 0.871 | EXCELLENT |
| gemini_grobid_only | 0.865 | EXCELLENT |
| gemini_grobid_images_equations | 0.859 | EXCELLENT |
| openai_grobid_images | 0.850 | EXCELLENT |
| gemini_everything | 0.838 | EXCELLENT |
| openai_everything | 0.832 | EXCELLENT |
| gemini_fulltext_tables | 0.815 | EXCELLENT |
| gemini_baseline | 0.814 | EXCELLENT |
| openai_grobid_tables | 0.805 | EXCELLENT |
| openai_grobid_tables_images | 0.800 | EXCELLENT |
| openai_grobid_tables_equations | 0.790 | GOOD |
| openai_fulltext_tables | 0.783 | GOOD |
| gemini_grobid_equations | 0.762 | GOOD |
| openai_grobid_equations | 0.729 | GOOD |
| gemini_grobid_only | 0.599 | MODERATE |
| openai_grobid_only | 0.573 | MODERATE |

---

## Analysis

- **All models except 2 are EXCELLENT or GOOD** (20/22)
- **Best performing extraction**: openai_baseline
- **Provider comparison**: OpenAI slightly better on average (0.806 vs 0.780)
- **Key strength**: Compartments and flows well matched
- **Weakness**: Parameter extraction varies

---

## Conclusion

**Tuberculosis models perform well.** Average 0.793 is among the best diseases tested.
