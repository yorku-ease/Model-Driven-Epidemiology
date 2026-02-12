# Phase 2 Evaluation Results

This report summarizes precision (P), recall (R), and F1 for **compartments**, **parameters**, and **flows** against baseline `.compmodel` gold standards. Each row is the latest run per disease and provider.

---

## 1. Per-disease scores (Precision / Recall / F1)

### 1.1 OpenAI (GPT-4o-mini)

| Disease      | Compartments (P / R / F1)   | Parameters (P / R / F1)     | Flows (P / R / F1)        |
|-------------|-----------------------------|-----------------------------|----------------------------|
| Cholera      | - | - | - |
| Dengue       | - | - | - |
| Ebola        | - | - | - |
| Flu          | - | - | - |
| Measles      | - | - | - |
| Tuberculosis | - | - | - |
| Zika         | - | - | - |

### 1.2 Gemini (2.5 Pro / Flash)

| Disease      | Compartments (P / R / F1)   | Parameters (P / R / F1)     | Flows (P / R / F1)        |
|-------------|-----------------------------|-----------------------------|----------------------------|
| Cholera      | 1.00 / 1.00 / **1.00**      | 1.00 / 0.83 / **0.91**      | 1.00 / 1.00 / **1.00**     |
| Dengue       | 1.00 / 1.00 / **1.00**      | 0.78 / 0.54 / **0.64**      | 1.00 / 1.00 / **1.00**     |
| Ebola        | 0.80 / 0.80 / **0.80**      | 1.00 / 0.64 / **0.78**      | 0.50 / 0.50 / **0.50**     |
| Flu          | 0.67 / 1.00 / **0.80**      | 0.30 / 1.00 / **0.46**      | 1.00 / 0.60 / **0.75**     |
| Measles      | 1.00 / 1.00 / **1.00**      | 0.00 / 0.00 / **0.00**      | 1.00 / 1.00 / **1.00**     |
| Tuberculosis | 1.00 / 1.00 / **1.00**      | 0.50 / 0.44 / **0.47**      | 1.00 / 0.50 / **0.67**     |
| Zika         | 1.00 / 1.00 / **1.00**      | 0.47 / 0.50 / **0.49**      | 0.73 / 1.00 / **0.84**     |

---

## 2. Averages across 7 diseases

| Provider | Metric      | Compartments P / R / F1 (avg)     | Parameters P / R / F1 (avg)     | Flows P / R / F1 (avg)        |
|----------|-------------|-----------------------------------|----------------------------------|--------------------------------|
| **OpenAI** | Precision  | **0.00**                          | **0.00**                         | **0.00**                       |
|          | Recall      | **0.00**                          | **0.00**                         | **0.00**                       |
|          | **F1**      | **0.00**                          | **0.00**                         | **0.00**                       |
| **Gemini** | Precision  | **0.92**                          | **0.58**                         | **0.89**                       |
|          | Recall      | **0.97**                          | **0.56**                         | **0.80**                       |
|          | **F1**      | **0.94**                          | **0.54**                         | **0.82**                       |

**Overall (both providers, mean of OpenAI and Gemini F1):**
- Compartments F1: **0.47**
- Parameters F1: **0.27**
- Flows F1: **0.41**

---

## 3. F1 summary table (for quick scan)

| Disease       | OpenAI C / Par / Flow (F1) | Gemini C / Par / Flow (F1) |
|---------------|----------------------------|----------------------------|
| Cholera        | - / - / -                         | 1.00 / 0.91 / 1.00                      |
| Dengue         | - / - / -                         | 1.00 / 0.64 / 1.00                      |
| Ebola          | - / - / -                         | 0.80 / 0.78 / 0.50                      |
| Flu            | - / - / -                         | 0.80 / 0.46 / 0.75                      |
| Measles        | - / - / -                         | 1.00 / 0.00 / 1.00                      |
| Tuberculosis   | - / - / -                         | 1.00 / 0.47 / 0.67                      |
| Zika           | - / - / -                         | 1.00 / 0.49 / 0.84                      |
| **Average**   | **0.00 / 0.00 / 0.00**     | **0.94 / 0.54 / 0.82**     |