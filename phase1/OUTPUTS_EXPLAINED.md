# Phase 1 Outputs Explained

This document explains what each output file contains and what it means for your project.

## 📊 Output Structure

All outputs are in the `reports/` directory, organized by task:

```
reports/
├── model_analysis/      # Task 1.1: What's in your models?
├── protocols/           # Tasks 1.2, 1.3: How to extract models
├── taxonomies/          # Task 1.4: Classification system
├── gap_reports/         # Task 2.1: What's missing?
├── uncertainty/         # Task 2.2: Parameter confidence
├── sensitivity/         # Task 2.3: Parameter impact
├── paper_collection/    # Task 3.1: Paper database
├── manual_extraction/   # Task 3.2: Extraction templates
└── patterns/            # Task 3.3: Common patterns
```

---

## 1. Model Analysis (`reports/model_analysis/`)

**Purpose:** Complete breakdown of your existing models (COVID-19, Malaria, HIV)

### Files:

#### `all_models_analysis.json` / `.csv` / `.xlsx`
**What it contains:**
- Every compartment from all 3 models
- Every flow (how people move between compartments)
- Every parameter
- Population sizes
- Stratification information

**What it means:**
- **Ground truth data** for Phase 2 AI training
- Shows what a "complete" model looks like
- Documents all components that need to be extracted from papers

**Example entry:**
```json
{
  "Model": "COVID-19",
  "Type": "Compartment",
  "Name": "Susceptible",
  "Population": "14700000",
  "HasStratification": true,
  "NumFlows": 2
}
```

#### `covid_19_analysis.json`, `malaria_analysis.json`, `hiv_analysis.json`
**What it contains:**
- Detailed breakdown of each individual model
- All compartments with their flows
- All parameters with values
- Groups (stratification categories)

**What it means:**
- Reference for what each model contains
- Shows model-specific structures (e.g., Malaria has vectors, COVID has age groups)

#### `summary_report.txt`
**What it contains:**
- Quick statistics for each model:
  - Number of compartments
  - Number of parameters
  - Has stratification? (Yes/No)
  - Has vectors? (Yes/No)
  - Has temperature dependence? (Yes/No)

**What it means:**
- Quick overview of model complexity
- Helps identify model types at a glance

**Example:**
```
Model: COVID-19
  Compartments: 15
  Parameters: 0
  Has Stratification: True
  Has Vector Compartments: False
```

---

## 2. Protocols (`reports/protocols/`)

**Purpose:** Step-by-step instructions for extracting models from papers

### Files:

#### `extraction_protocol.json` / `.md`
**What it contains:**
- 8-step protocol for building models from papers:
  1. Initial Paper Reading
  2. Locate Model Description
  3. Extract Compartments
  4. Extract Flows
  5. Extract Parameters
  6. Extract Stratification
  7. Record Initial Conditions
  8. Document Missing Information

**What it means:**
- **Instructions for Phase 2 AI agents** - tells them exactly how to extract models
- Each step has specific actions and expected outputs
- Shows where to look in papers (diagrams, equations, text)

**Use case:** Phase 2 will use this to program AI agents that follow these steps

#### `required_optional.json` / `.md`
**What it contains:**
- **Required elements:** Things every model MUST have (compartments, flows, rates)
- **Optional elements:** Things that depend on context (stratification, vectors, demography)
- **Detection rules:** How to determine if something is required or optional

**What it means:**
- **Validation rules** for Phase 2 - AI can check if extracted model is complete
- Prevents false positives (e.g., don't flag "missing stratification" if paper says "simple SEIR")
- Context-aware gap detection

**Example:**
```json
{
  "alwaysRequired": ["compartments", "flows", "rates"],
  "conditional": {
    "stratification": "Required if paper mentions age/gender/risk groups",
    "vectors": "Required if disease is vector-borne"
  }
}
```

---

## 3. Taxonomies (`reports/taxonomies/`)

**Purpose:** Classification system for model components

### Files:

#### `taxonomies.json` / `.md`
**What it contains:**
- **Compartment Types:** Disease States (S, E, I, R), Healthcare (H, ICU), Vectors, etc.
- **Flow Types:** ContactFlow, RateFlow, ExternalSource, ExternalSink
- **Parameter Types:** CONSTANT, VARIABLE, EXPRESSION
- **Stratification Types:** Age, Gender, Risk, Location, Vaccination Status

**What it means:**
- **Classification system** for Phase 2 - AI can categorize extracted components
- Helps validate if extracted compartments make sense
- Enables pattern matching (e.g., "this looks like a standard SEIR")

**Use case:** Phase 2 AI can say "I found a Susceptible compartment (Disease State type)" instead of just "I found a compartment"

---

## 4. Gap Reports (`reports/gap_reports/`)

**Purpose:** Identifies what's missing from models compared to literature

### Files:

#### `malaria_gap_analysis.json` / `.md`
**What it contains:**
- **Structural gaps:** Missing compartments (e.g., Asymptomatic carriers)
- **Parameter gaps:** Missing parameters (e.g., asymptomatic proportion)
- **Stratification gaps:** Missing stratification (e.g., age groups)
- **Intervention gaps:** Missing interventions (e.g., treatment compartments)

**What it means:**
- Shows what your current model is missing
- Each gap has:
  - **Severity:** Critical, Medium, Low
  - **Why it matters:** Explanation
  - **How to add:** Instructions
  - **Literature sources:** Where it's found

**Example:**
```
Missing Asymptomatic Carriers (AH)
Severity: MEDIUM
Why: Asymptomatic carriers contribute to transmission
How to Add: Add compartment IH → AH with proportion p_asymp
```

**Use case:** Phase 2 AI can identify gaps when extracting models from papers

---

## 5. Uncertainty (`reports/uncertainty/`)

**Purpose:** Documents parameter confidence and ranges

### Files:

#### `all_models_uncertainty.json`
**What it contains:**
- Every parameter from all models
- Current value in your model
- Literature range (min, max, mean, std) - *template structure*
- Confidence level: High, Medium, Low, Unknown
- Notes about where parameter came from

**What it means:**
- **Parameter confidence database** - shows which parameters are well-known vs uncertain
- Template for Phase 2 to fill in literature ranges
- Helps identify parameters that need more research

**Example:**
```json
{
  "parameter": "β₁",
  "yourValue": "0.9969",
  "confidence": "Unknown",
  "notes": "Literature search needed",
  "rangeInLiterature": {
    "min": null,
    "max": null
  }
}
```

**Use case:** Phase 2 AI can flag uncertain parameters and suggest literature ranges

#### Individual model files (`covid_19_uncertainty.json`, etc.)
- Same structure but per-model
- Easier to review one model at a time

---

## 6. Sensitivity Analysis (`reports/sensitivity/`)

**Purpose:** Shows which parameters have biggest impact on model outcomes

### Files:

#### `covid_sensitivity_analysis.json` / `.md`
**What it contains:**
- Parameter sensitivity rankings
- How varying each parameter affects:
  - Peak infections
  - Time to peak
  - Total cases
- Sensitivity scores (higher = more important)

**What it means:**
- Identifies **key parameters** that matter most
- Helps prioritize which parameters need accurate values
- Shows which parameters to focus on when extracting from papers

**Example:**
```
Parameter: β (transmission rate)
Sensitivity Score: 0.85 (High)
Impact: Varying β by 10% changes peak infections by 8.5%
```

**Use case:** Phase 2 AI can prioritize extracting high-sensitivity parameters first

---

## 7. Paper Collection (`reports/paper_collection/`)

**Purpose:** Database of papers used for modeling

### Files:

#### `paper_collection.json`
**What it contains:**
- List of all papers in collection
- Metadata: title, authors, year, disease
- Paper IDs for organization
- Links to PDFs (if available)
- Notes about what each paper contains

**What it means:**
- **Paper database** for Phase 2
- Shows which papers correspond to which models
- Can be expanded as you add more papers

**Example:**
```json
{
  "id": "covid-19_2020_0",
  "title": "COVID-19 Model (Tuite et al. 2020)",
  "disease": "COVID-19",
  "notes": "Age-stratified COVID-19 model - source for covid.compmodel"
}
```

**Also see:** `data/papers/collection_index.json` - organized paper metadata

---

## 8. Manual Extraction (`reports/manual_extraction/`)

**Purpose:** Templates for manually extracting models from papers

### Files:

#### `extraction_template.json`
**What it contains:**
- JSON structure for extracting models
- Fields for: compartments, flows, parameters, stratification
- Format that Phase 2 AI should produce

**What it means:**
- **Target format** for Phase 2 AI output
- Shows exactly what structure extracted models should have
- Can be used for manual extraction as "ground truth" for training

#### `extraction_example.json`
**What it contains:**
- Example extraction based on COVID-19 model
- Shows what a complete extraction looks like
- Demonstrates the template structure

**Use case:** Phase 2 AI will produce output in this format

---

## 9. Patterns (`reports/patterns/`)

**Purpose:** Library of common model patterns

### Files:

#### `pattern_library.json` / `.md`
**What it contains:**
- **5 common patterns:**
  1. Standard SEIR
  2. SEIR with Hospitalization
  3. Vector-Borne (Malaria)
  4. With Treatment
  5. Stratified Model

**What it means:**
- **Pattern recognition** for Phase 2
- AI can identify "this paper describes a standard SEIR model"
- Helps validate if extracted model matches expected pattern
- Shows which patterns each of your models uses

**Example:**
```json
{
  "name": "Standard SEIR",
  "compartments": ["S", "E", "I", "R"],
  "flows": ["S → E", "E → I", "I → R"],
  "examples": ["COVID-19", "Malaria"]
}
```

**Use case:** Phase 2 AI can match extracted models to known patterns

---

## 📋 Summary: What Phase 2 Needs

Phase 2 (AI Components) will use these outputs as:

1. **Training Data:** `model_analysis/` - shows what complete models look like
2. **Instructions:** `protocols/` - tells AI how to extract models
3. **Validation Rules:** `required_optional/` - checks if extraction is complete
4. **Classification:** `taxonomies/` - categorizes extracted components
5. **Gap Detection:** `gap_reports/` - identifies missing components
6. **Parameter Confidence:** `uncertainty/` - flags uncertain parameters
7. **Priority:** `sensitivity/` - focuses on important parameters
8. **Paper Database:** `paper_collection/` - links papers to models
9. **Output Format:** `manual_extraction/` - target structure for AI output
10. **Pattern Matching:** `patterns/` - validates model structure

---

## 🎯 Key Takeaways

- **All outputs are in JSON (machine-readable) and Markdown (human-readable)**
- **JSON files** = for Phase 2 AI to read programmatically
- **Markdown files** = for you to review and understand
- **These outputs form the foundation** for building AI agents in Phase 2
- **Everything is structured** so Phase 2 can use it automatically

---

## 📁 Additional Files

### `data/papers/collection_index.json`
- Organized paper metadata
- Links to paper PDFs
- Structured for easy access

### `scripts/` directory
- Helper scripts for managing papers
- `add_papers.py` - Add new papers to collection
- `update_paper_links.py` - Update paper metadata
- `organize_papers.sh` - Organize papers by disease

