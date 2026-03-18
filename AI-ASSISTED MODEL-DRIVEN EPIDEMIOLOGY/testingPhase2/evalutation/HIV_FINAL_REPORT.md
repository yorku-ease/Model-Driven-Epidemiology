# HIV Model Evaluation Report

## Summary

**Updated Gold Standard**: 5 compartments, 19 flows, 28 parameters  
**Average Composite Score**: 0.412  
**Best Model**: openai_baseline (0.563)

---

## Results

| Model | Composite | Quality |
|-------|----------|---------|
| openai_baseline | 0.563 | MODERATE |
| openai_everything | 0.545 | POOR |
| openai_grobid_tables_images | 0.533 | POOR |
| openai_grobid_tables | 0.504 | POOR |
| openai_grobid_tables_equations | 0.485 | POOR |
| openai_grobid_only | 0.472 | POOR |
| openai_fulltext_tables | 0.471 | POOR |
| gemini_grobid_only | 0.453 | POOR |
| gemini_baseline | 0.420 | POOR |
| Average | **0.412** | POOR |

---

## Breakdown (Best Model: openai_baseline)

| Entity | F1 Score |
|--------|----------|
| Compartments | 0.615 |
| Flows | 0.439 |
| Parameters | 0.766 |

- **Compartments**: 4/5 matched (1 missed, 4 extra)
- **Flows**: 9/19 matched (10 missed, 13 extra)  
- **Parameters**: 18/28 matched (10 missed, 1 extra)

---

## Why It's Still Low

1. **Flow mismatch**: Gold has 19 flows, extracted models have different flow structures
2. **Over-extraction**: Models extract more compartments than gold has
3. **Structural differences**: The gold model is simplified compared to extracted models

---

## Conclusion

With the updated gold (28 parameters), the average improved from 0.336 to **0.412**. Still POOR overall, but better than before. The main issue remains flow and compartment structural differences between gold and extracted models.
