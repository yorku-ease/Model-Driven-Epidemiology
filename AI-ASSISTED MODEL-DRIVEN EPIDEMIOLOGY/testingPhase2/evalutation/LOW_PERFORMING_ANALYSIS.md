# Analysis Report: Low-Performing Disease Models

## Executive Summary

This report analyzes why three disease models (Zika, Ebola, Influenza) show unexpectedly low evaluation scores. After investigation, we found these are **not system errors** but reflect genuine differences between gold standards and extracted models.

---

## 1. Zika Analysis

### Evaluation Scores
- **Average Composite**: 0.474
- **Best Model**: openai_grobid_images (0.585)
- **Compartment F1**: 0.4-0.5 range
- **Flow F1**: 0.5-0.6 range

### Root Cause: Vector Compartment Mismatch

| Gold Standard | Extracted Model | Similarity Score |
|---------------|-----------------|------------------|
| Eggs | Egg Vector | 0.71 (below threshold) |
| Larvae | Larva Vector | 0.69 (below threshold) |
| Pupae | Pupa Vector | 0.70 (below threshold) |
| Susceptible female adults | Susceptible Vector | 0.68 (below threshold) |

**Problem**: The gold standard uses generic terms ("Eggs", "Larvae") while extracted models include "Vector" suffix ("Egg Vector"). At threshold 0.72, these semantic matches fall just below the cutoff.

### Additional Issues
- Gold has 10 compartments, extracted has 10 compartments
- 4 compartment false positives: "Egg Vector", "Larva Vector", "Pupa Vector", "Susceptible Vector"
- 4 compartment false negatives: "Eggs", "Larvae", "Pupae", "Susceptible female adults"
- Flow matching also affected by vector terminology differences

### Recommendation
- **Option 1**: Update gold standard to match extracted terminology (add "Vector" suffix)
- **Option 2**: Lower threshold to 0.65 for these specific compartments
- **Option 3**: Accept this as a genuine model difference - extracted model is more explicit

---

## 2. Ebola Analysis

### Evaluation Scores
- **Average Composite**: 0.417
- **Best Model**: gemini_grobid_tables_equations (0.760)
- **Issue**: Very high variance (0.093 to 0.760)

### Root Cause: Structural Differences

The Ebola gold standard has fundamentally different structure from extracted models:

**Gold Standard (6 compartments)**:
- Susceptible
- Exposed
- Infectious
- Recovered
- Dead
- Hospitalized

**Extracted Models (vary widely)**:
- Some have 3-4 compartments
- Some have 10+ compartments
- Different population groupings

### Specific Example Issues

Looking at worst performer `Ebola_gemini_grobid_only` (0.093):
- Gold: 16 flows, Extracted: 25+ flows
- Very low TP match rate due to structural differences
- Different transmission models (SEIR vs more complex)

### Provider Performance Gap
- **Gemini**: Average 0.506
- **OpenAI**: Average 0.328
- **Gap**: +0.178 (significant)

This suggests some extraction methods work better for Ebola's complex structure.

### Recommendation
- Review Ebola gold standard for completeness
- Consider that extracted models may be more comprehensive
- High variance suggests extraction method matters significantly for Ebola

---

## 3. Influenza Analysis

### Evaluation Scores
- **Average Composite**: 0.569
- **Best Model**: openai_grobid_full (0.845)
- **Issue**: Very high variance (0.174 to 0.845)

### Root Cause: Small Gold Model

| Metric | Gold | Extracted Range |
|--------|------|-----------------|
| Compartments | 6 | 4-12 |
| Flows | 6 | 8-35 |
| Parameters | 3 | 5-20 |

The gold standard is extremely minimal:
- Only 6 compartments (Susceptible, Exposed, Infectious, Recovered, etc.)
- Only 6 flows
- Only 3 parameters

### Specific Issue: Over-Extraction

Extracted models have significantly MORE elements than gold:
- `influenza_openai_baseline`: 12 compartments vs 6 gold
- `influenza_openai_grobid_full`: 35 flows vs 6 gold

This causes low precision scores (many false positives).

### Best Performer Analysis

`influenza_openai_grobid_full` (0.845) likely works well because:
- Full grobid extraction captures more complete model
- Closer match to a more detailed gold standard version

### Recommendation
- The gold standard may be too minimal
- Consider if extracted models are actually more complete/representative
- The high variance (0.174-0.845) suggests extraction method is critical

---

## 4. Common Themes

### Issue 1: Terminology Mismatches
- Zika: "Eggs" vs "Egg Vector"
- Affects semantic matching at threshold 0.72

### Issue 2: Gold Standard Completeness
- HIV: Simplified gold (5 compartments) vs detailed extraction (8+)
- Influenza: Minimal gold (6 compartments) vs detailed extractions
- Ebola: Different structural approaches

### Issue 3: Over-Extraction
- Several models extract MORE compartments/flows than gold
- This causes low precision (false positives)

### Issue 4: High Variance
- Ebola: 0.093 to 0.760
- Influenza: 0.174 to 0.845
- Zika: 0.155 to 0.585

This suggests extraction method significantly impacts quality.

---

## 5. Recommendations

### Short-term
1. **Update Zika gold standard**: Add "Vector" suffix to mosquito compartments
2. **Review Ebola gold**: Ensure it represents the full model complexity
3. **Verify Influenza gold**: Consider if it's too minimal

### Long-term
1. **Hybrid matching**: Use lower threshold (0.65) for specific terminology patterns
2. **Gold standard improvement**: Make gold standards comprehensive
3. **Extraction tuning**: Identify which extraction methods work best per disease

---

## 6. Conclusion

The low evaluation scores for Zika, Ebola, and Influenza are **legitimate findings**, not system errors. They reflect:

1. **Terminology differences** (Zika vector naming)
2. **Structural differences** (Ebola model complexity)
3. **Completeness gaps** (Influenza minimal gold)
4. **Over-extraction** (several models extracting more than gold)

The evaluation system is working correctly - it's identifying genuine discrepancies between gold standards and extracted models.

---

*Report generated: 2026-03-01*
*Evaluation threshold: 0.72*
