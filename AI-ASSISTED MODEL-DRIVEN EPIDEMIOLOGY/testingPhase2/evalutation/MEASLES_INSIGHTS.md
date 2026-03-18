# Measles Model Evaluation - Insights Report

## Summary Statistics

| Metric | Average | Best | Worst |
|--------|---------|------|-------|
| **Composite Score** | 0.903 | 0.978 (gemini_grobid_tables) | 0.608 (openai_grobid_images_equations) |
| **Compartment F1** | 0.949 | 1.000 (most models) | 0.500 |
| **Flow F1** | 0.954 | 0.987 (many models) | 0.805 |
| **Parameter F1** | 0.626 | 0.889 | 0.235 |

**Quality Distribution:** 17 EXCELLENT, 3 GOOD, 2 MODERATE

---

## Key Observations

### 1. Compartments - Excellent Overall
- Most models achieve perfect 1.0 F1 on compartments
- Issues only with worst-performing models (openai_grobid_images variants)
- Model handles case differences well ("children" vs "Children")

### 2. Flows - Very Strong
- Average F1: 0.954
- Minor issues with duplicate/missing flow entries
- Direction errors are rare

### 3. Parameters - Main Weakness
- **Average F1: 0.626** - this is the bottleneck
- Common issues:
  - **Symbol variations**: β → b, δ → d_p
  - **Missing Greek letters**: extracted as Latin equivalents
  - **Composite parameters missed**: e*βC, e*βA not extracted

---

## Model-by-Model Flags

### TOP PERFORMERS (Composite > 0.95)
| Model | Composite | Flags |
|-------|----------|-------|
| gemini_grobid_tables | 0.978 | Best overall |
| gemini_everything | 0.970 | - |
| gemini_grobid_equations | 0.970 | - |
| openai_grobid_tables | 0.959 | - |

### GOOD (0.85 - 0.95)
| Model | Composite | Flags |
|-------|----------|-------|
| openai_grobid_equations | 0.850 | Lower param recall |
| openai_grobid_images | 0.795 | Compartment issues, low param F1 |
| gemini_grobid_images | 0.791 | Flow/param issues |

### MODERATE (< 0.85)
| Model | Composite | Flags |
|-------|----------|-------|
| openai_grobid_only | 0.740 | Missing compartments, low param recall |
| openai_grobid_images_equations | 0.608 | **MAJOR** - only 50% comp F1 |

---

## Specific Issues to Flag

### 1. Symbol Translation Problem
Several models use different symbols than gold standard:
- `βC` → `b_C` (transmission rate)
- `βA` → `b_A` 
- `δ` → `d_p` (progression rate)
- `φ` → `j` (maturation rate)

### 2. Missing Composite Parameters
The following parameters were consistently missed across models:
- `βC` (Child transmission rate)
- `βA` (Adult transmission rate)  
- `δ` (Incubation progression rate)
- `α` (Contact network fraction)
- `q` (Case reporting fraction)
- `e * βC`, `e * βA` (Leak transmission rates)

### 3. Poor Performing Variants
- **openai_grobid_images_equations**: Only 50% compartment match - needs investigation
- **openai_grobid_only**: Low parameter extraction

---

## Provider Comparison

| Provider | Avg Composite | Avg Param F1 |
|----------|---------------|--------------|
| **Gemini** | 0.921 | 0.693 |
| **OpenAI** | 0.859 | 0.505 |

**Gemini outperforms OpenAI** on parameters significantly.

---

## Recommendations

1. **Best model for measles**: `gemini_grobid_tables` (0.978)
2. **Parameter extraction needs improvement** - this is the main gap
3. **Symbol mapping** could be added to improve parameter matching
4. **Avoid** openai_grobid_images variants for measles
