# Dengue Model Evaluation - Insights Report

## Summary Statistics

| Metric | Average | Best | Worst |
|--------|---------|------|-------|
| **Composite Score** | 0.779 | 0.912 (gemini_fulltext_tables) | 0.235 (openai_fulltext_tables) |
| **Compartment F1** | 0.833 | 1.000 | 0.210 |
| **Flow F1** | 0.826 | 0.968 | 0.200 |
| **Parameter F1** | 0.495 | 0.667 | 0.308 |

**Quality Distribution:** 7 EXCELLENT, 12 GOOD, 4 MODERATE, 2 POOR

---

## Key Observations

### 1. Compartments - Good but Variable
- Average F1: 0.833 (lower than measles 0.949)
- Significant variance - some models achieve 1.0, others drop to 0.21
- **2 POOR performers** with compartment F1 < 0.5

### 2. Flows - Moderate
- Average F1: 0.826
- Similar to compartments - good for most, poor for outliers
- POOR models have very low flow F1 (~0.2)

### 3. Parameters - Weakest Link
- **Average F1: 0.495** (worse than measles 0.626)
- All models struggle with parameters
- Best param F1: 0.667 (openai_grobid_images)

---

## Model Rankings

### TOP PERFORMERS (Composite > 0.90)
| Model | Composite | Flags |
|-------|----------|-------|
| gemini_fulltext_tables | 0.912 | Best overall |
| gemini_grobid_tables_equations | 0.909 | - |
| gemini_grobid_images | 0.906 | - |
| gemini_grobid_equations | 0.901 | - |

### MODERATE (0.70 - 0.85)
| Model | Composite | Flags |
|-------|----------|-------|
| openai_baseline | 0.725 | Low comp F1 |
| openai_everything | 0.720 | Low comp F1 |
| openai_grobid_tables | 0.709 | Low comp/flow F1 |

### POOR (< 0.70) - NEEDS INVESTIGATION
| Model | Composite | Flags |
|-------|----------|-------|
| gemini_grobid_full | 0.691 | Low comp/flow |
| gemini_grobid_tables_images | 0.685 | Low comp/flow |
| openai_grobid_images_equations | 0.303 | **MAJOR** - 0.31 comp F1 |
| openai_fulltext_tables | 0.235 | **MAJOR** - 0.21 comp F1 |

---

## Provider Comparison

| Provider | Avg Composite | Avg Param F1 |
|----------|---------------|--------------|
| **Gemini** | 0.824 | 0.492 |
| **OpenAI** | 0.715 | 0.499 |

**Gemini significantly outperforms OpenAI** on composite score.

---

## Key Issues

### 1. Extreme Outliers
Two models perform very poorly:
- **openai_grobid_images_equations**: Only 31% compartment F1
- **openai_fulltext_tables**: Only 21% compartment F1

This suggests potential parsing/extraction issues for these specific configurations.

### 2. Parameter Extraction
All models struggle with parameters (avg F1: 0.495), similar pattern to measles.

### 3. Variance is High
- Composite ranges from 0.235 to 0.912
- Much more variable than measles (0.608 to 0.978)

---

## Comparison with Measles

| Metric | Measles | Dengue | Difference |
|--------|---------|--------|------------|
| Avg Composite | 0.903 | 0.779 | -0.124 |
| Avg Comp F1 | 0.949 | 0.833 | -0.116 |
| Avg Flow F1 | 0.954 | 0.826 | -0.128 |
| Avg Param F1 | 0.626 | 0.495 | -0.131 |

**Dengue models perform worse than measles across all metrics.**

---

## Recommendations

1. **Best model for dengue**: `gemini_fulltext_tables` (0.912)
2. **Avoid**: openai_grobid_images_equations, openai_fulltext_tables
3. **Parameter extraction** remains the biggest challenge
4. Consider investigating why some specific configs perform so poorly
