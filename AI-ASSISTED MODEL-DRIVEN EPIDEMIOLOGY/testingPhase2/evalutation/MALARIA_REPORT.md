# Malaria Model Evaluation Report

## Summary

**Average Composite Score**: 0.770  
**Best Model**: gemini_grobid_full (0.865)  
**Quality**: GOOD

---

## Results

| Model | Composite | Quality |
|-------|----------|---------|
| gemini_grobid_full | 0.865 | EXCELLENT |
| gemini_grobid_tables | 0.857 | EXCELLENT |
| gemini_grobid_tables_images | 0.852 | EXCELLENT |
| gemini_grobid_tables_equations | 0.849 | EXCELLENT |
| gemini_grobid_images | 0.845 | EXCELLENT |
| gemini_grobid_only | 0.840 | EXCELLENT |
| gemini_grobid_equations | 0.838 | EXCELLENT |
| gemini_baseline | 0.831 | EXCELLENT |
| gemini_everything | 0.829 | EXCELLENT |
| gemini_fulltext_tables | 0.828 | EXCELLENT |
| gemini_grobid_images_equations | 0.826 | EXCELLENT |
| openai_baseline | 0.819 | EXCELLENT |
| openai_grobid_tables | 0.816 | EXCELLENT |
| openai_grobid_tables_images | 0.812 | EXCELLENT |
| openai_grobid_tables_equations | 0.808 | EXCELLENT |
| openai_grobid_images | 0.795 | GOOD |
| openai_grobid_full | 0.787 | GOOD |
| openai_grobid_only | 0.756 | GOOD |
| openai_everything | 0.749 | GOOD |
| openai_grobid_equations | 0.748 | GOOD |
| openai_fulltext_tables | 0.713 | GOOD |
| openai_grobid_images_equations | 0.303 | POOR |

---

## Analysis

- **21/22 models are GOOD or EXCELLENT**
- **Best performing extraction**: gemini_grobid_full
- **Provider comparison**: Gemini significantly better (0.834 vs 0.707)
- **Key strength**: Strong compartment matching across models
- **Weakness**: openai_grobid_images_equations is an extreme outlier (0.303)

---

## Conclusion

**Malaria models perform well.** Average 0.770 is solid. Gemini models consistently outperform OpenAI for this disease.
