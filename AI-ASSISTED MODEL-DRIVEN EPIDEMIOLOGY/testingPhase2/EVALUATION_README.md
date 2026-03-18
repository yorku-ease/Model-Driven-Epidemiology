# EpiMDE Model Evaluation System — Technical README

## Overview

This document describes the semantic matching system used to evaluate how faithfully an LLM-extracted epidemiological model matches a gold-standard benchmark. The core idea: instead of comparing XML syntax or doing exact string matching, we convert both models into flat lists of meaningful strings and compare them using sentence embeddings — vectors that capture *meaning*, not just spelling.

---

## Why Not String Matching?

Rapidfuzz testing revealed two fundamental failures:

| Problem | Example | Rapidfuzz WRatio |
|---------|---------|-----------------|
| Short symbol vs full name | `"S"` vs `"Susceptible"` | 60 — fails |
| Spelled-out vs Greek | `"gamma"` vs `"γ"` | 0 — fails completely |
| Synonym | `"Infected individuals"` vs `"Infectious"` | 67 — fails |
| Rephrased long name | `"Concentration of bacteria in water"` vs `"Bacterial concentration in water reservoir"` | 62 — fails |

These are not edge cases — they are the normal output of an LLM extracting from a scientific paper. String matching cannot solve a semantic problem.

---

## System Architecture

```
Gold .compmodel XML
        │
        ▼
   xml_parser.py  ──────────────────────────────┐
        │                                        │
        ▼                                        ▼
  canonical JSON                          canonical JSON   ◄── LLM JSON output
        │                                        │
        └──────────────┬─────────────────────────┘
                       ▼
              evaluator.py
                       │
          ┌────────────┼────────────┐
          ▼            ▼            ▼
    Compartments    Flows       Parameters
    matching        matching    matching
          │            │            │
          └────────────┼────────────┘
                       ▼
              TP / FP / FN → F1 per entity type
                       │
                       ▼
              Composite score (0–1)
```

---

## Step 1 — Canonical JSON Format

Both the gold XML and the LLM output are normalized into the same flat JSON structure before any comparison happens. This eliminates all XML syntax differences, namespace issues, and index references.

```json
{
  "compartments": [
    { "name": "Susceptible" },
    { "name": "Infectious" },
    { "name": "Recovered" },
    { "name": "Bacterial concentration in water reservoir" }
  ],
  "flows": [
    {
      "source": "Susceptible",
      "target": "Infectious",
      "type": "ContactFlow",
      "contact_compartment": "Bacterial concentration in water reservoir",
      "rate_parameter": "λ",
      "description": "Exposure to contaminated water"
    },
    {
      "source": "Infectious",
      "target": "Recovered",
      "type": "RateFlow",
      "rate_parameter": "γ",
      "rate_value": 0.5,
      "rate_unit": "per week"
    }
  ],
  "parameters": [
    { "symbol": "λ", "type": "EXPRESSION", "description": "Force of infection", "unit": "1/time" },
    { "symbol": "γ", "type": "CONSTANT",   "value": 0.5, "unit": "per week" },
    { "symbol": "α", "type": "CONSTANT",   "description": "Water contamination rate" },
    { "symbol": "μ_B", "type": "CONSTANT", "description": "Bacteria decay rate" },
    { "symbol": "K",  "type": "CONSTANT",  "description": "IC50 concentration" }
  ]
}
```

**Key design decisions:**
- Compartment names are always full words — the LLM is instructed to write `"Susceptible"` not `"S"`. The expansion happens at extraction time, not at evaluation time.
- Flows reference compartments by name, never by index. XML's `//@compartments.2` is resolved to its actual name during parsing.
- `__external__` and `__dead__` are sentinel values for BirthSource and DeathSink flows.
- Parameters carry both symbol and description — both are used during matching.

---

## Step 2 — What Gets Embedded

Only the meaningful text fields are passed to the embedding model. JSON keys, curly braces, indices, and type strings never touch the embedder.

```python
# Compartments — embed the name string directly
gold_compartment_strings = [
    "Susceptible",
    "Infectious",
    "Recovered",
    "Bacterial concentration in water reservoir"
]

# Flows — embed as "Source->Target" ordered string
# Direction matters: "A->B" ≠ "B->A"
gold_flow_strings = [
    "Susceptible->Infectious",
    "Infectious->Recovered",
    "Infectious->Bacterial concentration in water reservoir"
]

# Parameters — embed symbol + description concatenated
# This handles "γ" matching "gamma" via biomedical context
gold_param_strings = [
    "λ force of infection",
    "γ recovery rate",
    "α water contamination rate",
    "μ_B bacteria decay rate",
    "K IC50 concentration half saturation"
]
```

Concatenating symbol and description for parameters is important — `"γ"` alone gives the model very little to work with, but `"γ recovery rate"` allows the embedder to match it against `"gamma recovery"` or `"rate of recovery from infection"`.

---

## Step 3 — The Embedding Model

**Model:** `sentence-transformers/all-MiniLM-L6-v2`

| Property | Value |
|----------|-------|
| Embedding dimension | 384 |
| Max input tokens | 256 |
| Size on disk | ~90 MB |
| CPU inference speed | ~5ms per batch after warmup |
| Trained on | 1B+ sentence pairs including scientific text |

Why this model over BioSimCSE or other biomedical models: BioSimCSE is trained on clinical text (symptoms, drugs, procedures) and handles epidemiological notation poorly — it failed on `"γ"` → `"recovery rate"` in testing. `all-MiniLM-L6-v2` is trained on diverse text including mathematical and scientific corpora and handles the symbol-to-description matching much better.

Alternative if you want higher accuracy at the cost of speed: `sentence-transformers/all-mpnet-base-v2` (768-dim, ~3× slower, noticeably better on long rephrased strings).

**Loading once at startup:**
```python
from sentence_transformers import SentenceTransformer, util

model = SentenceTransformer("sentence-transformers/all-MiniLM-L6-v2")
# Load time: ~2 seconds. After this, all encoding is fast.
```

---

## Step 4 — Matrix Similarity (Not Per-Query Search)

This is the key efficiency insight. You do not run one search query per compartment. You encode everything at once and compute a full similarity matrix in a single operation.

```python
import numpy as np
from sentence_transformers import util

# Encode everything in one call
gold_embs = model.encode(gold_compartment_strings)   # shape: (4, 384)
llm_embs  = model.encode(llm_compartment_strings)    # shape: (5, 384)

# One matrix multiply gives all pairwise scores at once
similarity_matrix = util.cos_sim(gold_embs, llm_embs)
# Result shape: (4, 5)
# similarity_matrix[i][j] = similarity between gold[i] and llm[j]
```

Example similarity matrix for compartments:

```
                    Susceptible    Infected     Recovered    B
                    humans         individuals
Susceptible         0.97           0.61         0.42         0.31
Infectious          0.58           0.89         0.51         0.28
Recovered           0.44           0.53         0.98         0.22
Bacterial reserv.   0.29           0.31         0.25         0.91
```

**Timing for a full model evaluation (10 diseases × 11 cases × 2 LLMs = 220 runs):**

| Step | Time |
|------|------|
| Model load (once) | ~2 seconds |
| Encode all strings per run | ~5ms |
| Matrix similarity | <2ms |
| Hungarian matching | <1ms |
| **Total per evaluation** | **~10ms** |
| **Total for 220 evaluations** | **~4 seconds** |

---

## Step 5 — Hungarian Algorithm for Optimal Matching

Cosine similarity gives scores but not a matching. The naive approach — each LLM item claims its highest-scoring gold item — causes double-matching (two LLM compartments both claiming "Susceptible"). The Hungarian algorithm finds the globally optimal one-to-one assignment.

```python
from scipy.optimize import linear_sum_assignment

# Convert similarity to cost (Hungarian minimizes cost)
cost_matrix = 1 - similarity_matrix.numpy()

# Find optimal assignment
gold_indices, llm_indices = linear_sum_assignment(cost_matrix)

# Apply threshold — matches below threshold become FP/FN
THRESHOLD = 0.72  # tune this on a held-out pair first

matches = []
for g_idx, l_idx in zip(gold_indices, llm_indices):
    score = float(similarity_matrix[g_idx][l_idx])
    if score >= THRESHOLD:
        matches.append((gold_strings[g_idx], llm_strings[l_idx], score))
    # else: both become unmatched (FN for gold, FP for llm)
```

Why 0.72 as starting threshold: in testing, true matches (same concept, different wording) scored 0.75–0.99. False matches (different concepts) scored 0.25–0.65. There is a natural gap. You should verify this on your own gold/extracted pairs before running the full experiment.

---

## Step 6 — TP / FP / FN and Scoring

After matching:
- **TP** = number of matched pairs above threshold
- **FP** = LLM items that found no match above threshold (hallucinated compartments/flows)
- **FN** = gold items that found no match above threshold (missed by LLM)

```python
def f1(tp, fp, fn):
    precision = tp / (tp + fp) if (tp + fp) > 0 else 0.0
    recall    = tp / (tp + fn) if (tp + fn) > 0 else 0.0
    if precision + recall == 0:
        return 0.0
    return 2 * precision * recall / (precision + recall)

composite_score = (
    0.425 * f1(comp_tp, comp_fp, comp_fn) +
    0.425 * f1(flow_tp, flow_fp, flow_fn) +
    0.150 * f1(param_tp, param_fp, param_fn)
)
```

Weights reflect priority: compartments and flows matter equally and are the primary signal. Parameters are lower weight because many papers do not report all values numerically, and the LLM cannot invent what is not there.

---

## Flow Direction Enforcement

Flows are matched as ordered strings: `"Susceptible->Infectious"` ≠ `"Infectious->Susceptible"`. Because the full string is embedded, the embedder naturally treats direction as part of the meaning — reversing source and target produces a meaningfully different sentence and scores lower. No special direction-checking code is needed.

However, the evaluator also logs **direction errors** separately: cases where the reversed flow would have matched but the original does not. These are recorded in the output as a diagnostic, not counted as TP.

---

## Additional Improvements

### 1 — Pre-embed the Gold Standard

Since gold benchmarks do not change between runs, embed them once and save to disk:

```python
import numpy as np
gold_embs = model.encode(gold_strings)
np.save("gold_embeddings.npy", gold_embs)
```

Load at evaluation time instead of re-encoding. Saves ~5ms per run, and more importantly removes any risk of embedding drift if the model version changes between runs.

### 2 — Synonym Pre-normalisation (First-Pass Filter)

Before embedding, run the extracted strings through `PARAM_SYNONYMS` and `STATE_SYNONYMS` from `synonyms.py`. If a direct lookup hit is found, set its similarity to 1.0 and skip the embedding step for that item. This handles the Greek letter cases (`"gamma"` → `"γ"`) with 100% reliability without relying on the embedding model to get it right.

```python
def resolve_before_embedding(symbol, gold_symbols, synonyms):
    canonical = synonyms.resolve_param(symbol)
    for i, gold_sym in enumerate(gold_symbols):
        if synonyms.resolve_param(gold_sym) == canonical:
            return i, 1.0  # force-match, score=1.0
    return None, None  # fall through to embedding
```

### 3 — Flow Type as Bonus Signal

After matching flows by string similarity, check if the flow types agree (`ContactFlow` vs `RateFlow`). A type mismatch on an otherwise good match is logged as a warning but does not reduce the score — the LLM sometimes uses RateFlow where ContactFlow is more correct, and this is a metamodel detail, not a factual error about the model structure.

### 4 — Parameter Value Check (Low Priority)

After symbol matching, if both gold and extracted have a numeric value, check with unit normalization:

```python
# Normalize to per-day
UNIT_TO_DAY = {"per week": 1/7, "per year": 1/365, "per day": 1.0}

gold_per_day = gold_value * UNIT_TO_DAY.get(gold_unit, 1.0)
llm_per_day  = llm_value  * UNIT_TO_DAY.get(llm_unit,  1.0)

value_ok = abs(gold_per_day - llm_per_day) / gold_per_day < 0.05
```

Record as `value_match: true/false` in the output. Does not affect TP/FP/FN — purely diagnostic.

### 5 — Threshold Calibration Run

Before running all 220 evaluations, run the evaluator on 2–3 manually verified (gold, extracted) pairs and sweep the threshold from 0.60 to 0.85. Plot TP+FN (recall) and FP against threshold. Pick the threshold where the two curves cross — this is the point of equal precision and recall. Wrong threshold = wrong TP/FP/FN numbers for the entire experiment.

---

## Dependencies

```
sentence-transformers    # embedding model
scipy                    # Hungarian algorithm (linear_sum_assignment)
numpy                    # matrix operations
```

No rapidfuzz. No regex. No hardcoded string rules beyond the synonym table.

```bash
pip install sentence-transformers scipy numpy
```

The synonym table (`synonyms.py`) is still used as a first-pass filter for Greek symbols, but the embedding model handles everything else. If the synonym table has no entry for a symbol, the system degrades gracefully to pure semantic matching rather than failing.
