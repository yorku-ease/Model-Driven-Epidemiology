# Phase 2 Evaluation Results (E2E test run)

**Source runs:** `phase2/` (latest timestamp per disease × provider).

**Evaluation file:** `evaluation_report.json` in each run folder.

**Primary metric: recall** — share of gold-standard compartments, parameters, and flows that appear in the extracted model (minimize misses). Values are shown as **Recall / Precision / F1** per category. Precision is secondary (it penalizes hallucinated extras).

For pipeline **before/after** notes, see **INSTRUCTIONS.md** (appendix: *Phase 2: Before vs After — What Improved Results*).

---

## 1. Per-disease scores (**Recall** / Precision / F1)

_Primary metric: **Recall** — fraction of gold-standard compartments, parameters, and flows that were retrieved. Higher recall = fewer missed items. Precision penalizes hallucinated extras; use it secondarily._

### 1.1 OpenAI (GPT-4o-mini)

| Disease      | Compartments (R / P / F1)   | Parameters (R / P / F1)     | Flows (R / P / F1)        |
|-------------|-----------------------------|-----------------------------|----------------------------|
| Cholera (Paper 1) | - | - | - |
| Cholera (Paper 2) | - | - | - |
| Cholera (Paper 3) | - | - | - |
| COVID-19 (Paper 1) | - | - | - |
| COVID-19 (Paper 2) | - | - | - |
| COVID-19 (Paper 3) | - | - | - |
| Dengue (Paper 1) | - | - | - |
| Dengue (Paper 2) | - | - | - |
| Dengue (Paper 3) | - | - | - |
| Ebola (Paper 1) | - | - | - |
| Ebola (Paper 2) | - | - | - |
| Ebola (Paper 3) | - | - | - |
| HIV (Paper 1) | - | - | - |
| HIV (Paper 2) | - | - | - |
| HIV (Paper 3) | - | - | - |
| Influenza (Paper 1) | - | - | - |
| Influenza (Paper 2) | - | - | - |
| Influenza (Paper 3) | - | - | - |
| Malaria (Paper 1) | - | - | - |
| Malaria (Paper 2) | - | - | - |
| Malaria (Paper 3) | - | - | - |
| Measles (Paper 1) | - | - | - |
| Measles (Paper 2) | - | - | - |
| Measles (Paper 3) | - | - | - |
| Tuberculosis (Paper 1) | - | - | - |
| Tuberculosis (Paper 2) | - | - | - |
| Tuberculosis (Paper 3) | - | - | - |
| Zika (Paper 1) | - | - | - |
| Zika (Paper 2) | - | - | - |
| Zika (Paper 3) | - | - | - |

### 1.2 Gemini (2.5 Pro / Flash)

| Disease      | Compartments (R / P / F1)   | Parameters (R / P / F1)     | Flows (R / P / F1)        |
|-------------|-----------------------------|-----------------------------|----------------------------|
| Cholera (Paper 1) | - | - | - |
| Cholera (Paper 2) | - | - | - |
| Cholera (Paper 3) | - | - | - |
| COVID-19 (Paper 1) | - | - | - |
| COVID-19 (Paper 2) | - | - | - |
| COVID-19 (Paper 3) | - | - | - |
| Dengue (Paper 1) | - | - | - |
| Dengue (Paper 2) | - | - | - |
| Dengue (Paper 3) | **0.56** / 0.42 / 0.48      | **0.12** / 0.05 / 0.07      | **0.33** / 0.25 / 0.29     |
| Ebola (Paper 1) | - | - | - |
| Ebola (Paper 2) | - | - | - |
| Ebola (Paper 3) | - | - | - |
| HIV (Paper 1) | - | - | - |
| HIV (Paper 2) | - | - | - |
| HIV (Paper 3) | **0.10** / 0.20 / 0.13      | **0.05** / 0.04 / 0.04      | **0.00** / 0.00 / 0.00     |
| Influenza (Paper 1) | - | - | - |
| Influenza (Paper 2) | - | - | - |
| Influenza (Paper 3) | - | - | - |
| Malaria (Paper 1) | - | - | - |
| Malaria (Paper 2) | **0.83** / 0.50 / 0.62      | **0.00** / 0.00 / 0.00      | **0.38** / 0.27 / 0.32     |
| Malaria (Paper 3) | - | - | - |
| Measles (Paper 1) | - | - | - |
| Measles (Paper 2) | - | - | - |
| Measles (Paper 3) | - | - | - |
| Tuberculosis (Paper 1) | - | - | - |
| Tuberculosis (Paper 2) | - | - | - |
| Tuberculosis (Paper 3) | - | - | - |
| Zika (Paper 1) | - | - | - |
| Zika (Paper 2) | - | - | - |
| Zika (Paper 3) | - | - | - |

### 1.3 Claude (Opus 4)

| Disease      | Compartments (R / P / F1)   | Parameters (R / P / F1)     | Flows (R / P / F1)        |
|-------------|-----------------------------|-----------------------------|----------------------------|
| Cholera (Paper 1) | - | - | - |
| Cholera (Paper 2) | - | - | - |
| Cholera (Paper 3) | - | - | - |
| COVID-19 (Paper 1) | - | - | - |
| COVID-19 (Paper 2) | - | - | - |
| COVID-19 (Paper 3) | - | - | - |
| Dengue (Paper 1) | - | - | - |
| Dengue (Paper 2) | - | - | - |
| Dengue (Paper 3) | - | - | - |
| Ebola (Paper 1) | - | - | - |
| Ebola (Paper 2) | - | - | - |
| Ebola (Paper 3) | - | - | - |
| HIV (Paper 1) | - | - | - |
| HIV (Paper 2) | - | - | - |
| HIV (Paper 3) | - | - | - |
| Influenza (Paper 1) | - | - | - |
| Influenza (Paper 2) | - | - | - |
| Influenza (Paper 3) | - | - | - |
| Malaria (Paper 1) | - | - | - |
| Malaria (Paper 2) | - | - | - |
| Malaria (Paper 3) | - | - | - |
| Measles (Paper 1) | - | - | - |
| Measles (Paper 2) | - | - | - |
| Measles (Paper 3) | - | - | - |
| Tuberculosis (Paper 1) | - | - | - |
| Tuberculosis (Paper 2) | - | - | - |
| Tuberculosis (Paper 3) | - | - | - |
| Zika (Paper 1) | - | - | - |
| Zika (Paper 2) | - | - | - |
| Zika (Paper 3) | - | - | - |

---

## 2. Averages across 30 diseases

| Provider  | Metric     | Compartments (avg) | Parameters (avg) | Flows (avg) |
|-----------|------------|--------------------|------------------|-------------|
| **OpenAI**  | **Recall** | **0.00** | **0.00** | **0.00** |
|           | Precision | 0.00 | 0.00 | 0.00 |
|           | F1        | 0.00 | 0.00 | 0.00 |
| **Gemini**  | **Recall** | **0.50** | **0.06** | **0.24** |
|           | Precision | 0.37 | 0.03 | 0.17 |
|           | F1        | 0.41 | 0.04 | 0.20 |
| **Claude**  | **Recall** | **0.00** | **0.00** | **0.00** |
|           | Precision | 0.00 | 0.00 | 0.00 |
|           | F1        | 0.00 | 0.00 | 0.00 |

**Overall (mean recall — higher = fewer missed gold items):**
- Compartments recall: OpenAI **0.00**, Gemini **0.50**, Claude **0.00**
- Parameters recall: OpenAI **0.00**, Gemini **0.06**, Claude **0.00**
- Flows recall: OpenAI **0.00**, Gemini **0.24**, Claude **0.00**

---

## 3. Recall summary table (quick scan — Compartments / Parameters / Flows)

| Disease       | OpenAI R (C / Par / Flow) | Gemini R (C / Par / Flow) | Claude R (C / Par / Flow) |
|---------------|---------------------------|---------------------------|---------------------------|
| Cholera (Paper 1) | - / - / -                       | - / - / -                       | - / - / -                       |
| Cholera (Paper 2) | - / - / -                       | - / - / -                       | - / - / -                       |
| Cholera (Paper 3) | - / - / -                       | - / - / -                       | - / - / -                       |
| COVID-19 (Paper 1) | - / - / -                       | - / - / -                       | - / - / -                       |
| COVID-19 (Paper 2) | - / - / -                       | - / - / -                       | - / - / -                       |
| COVID-19 (Paper 3) | - / - / -                       | - / - / -                       | - / - / -                       |
| Dengue (Paper 1) | - / - / -                       | - / - / -                       | - / - / -                       |
| Dengue (Paper 2) | - / - / -                       | - / - / -                       | - / - / -                       |
| Dengue (Paper 3) | - / - / -                       | 0.56 / 0.12 / 0.33                    | - / - / -                       |
| Ebola (Paper 1) | - / - / -                       | - / - / -                       | - / - / -                       |
| Ebola (Paper 2) | - / - / -                       | - / - / -                       | - / - / -                       |
| Ebola (Paper 3) | - / - / -                       | - / - / -                       | - / - / -                       |
| HIV (Paper 1)  | - / - / -                       | - / - / -                       | - / - / -                       |
| HIV (Paper 2)  | - / - / -                       | - / - / -                       | - / - / -                       |
| HIV (Paper 3)  | - / - / -                       | 0.10 / 0.05 / 0.00                    | - / - / -                       |
| Influenza (Paper 1) | - / - / -                       | - / - / -                       | - / - / -                       |
| Influenza (Paper 2) | - / - / -                       | - / - / -                       | - / - / -                       |
| Influenza (Paper 3) | - / - / -                       | - / - / -                       | - / - / -                       |
| Malaria (Paper 1) | - / - / -                       | - / - / -                       | - / - / -                       |
| Malaria (Paper 2) | - / - / -                       | 0.83 / 0.00 / 0.38                    | - / - / -                       |
| Malaria (Paper 3) | - / - / -                       | - / - / -                       | - / - / -                       |
| Measles (Paper 1) | - / - / -                       | - / - / -                       | - / - / -                       |
| Measles (Paper 2) | - / - / -                       | - / - / -                       | - / - / -                       |
| Measles (Paper 3) | - / - / -                       | - / - / -                       | - / - / -                       |
| Tuberculosis (Paper 1) | - / - / -                       | - / - / -                       | - / - / -                       |
| Tuberculosis (Paper 2) | - / - / -                       | - / - / -                       | - / - / -                       |
| Tuberculosis (Paper 3) | - / - / -                       | - / - / -                       | - / - / -                       |
| Zika (Paper 1) | - / - / -                       | - / - / -                       | - / - / -                       |
| Zika (Paper 2) | - / - / -                       | - / - / -                       | - / - / -                       |
| Zika (Paper 3) | - / - / -                       | - / - / -                       | - / - / -                       |
| **Avg recall** | **0.00 / 0.00 / 0.00**     | **0.50 / 0.06 / 0.24**     | **0.00 / 0.00 / 0.00**     |