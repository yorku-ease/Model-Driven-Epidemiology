# Phase 1: Model Analysis and Documentation

This directory contains a complete suite of Python tools for analyzing compartmental epidemiological models. Phase 1 focuses on understanding existing models, extracting patterns, building taxonomies, and creating frameworks for systematic model analysis.

## Overview

Phase 1 performs deep analysis of three existing compartmental models (COVID-19, Malaria, HIV) to:
1. Extract all model components (compartments, flows, parameters)
2. Identify gaps and missing elements
3. Quantify parameter uncertainty
4. Analyze parameter sensitivity
5. Build reusable taxonomies and patterns

---

## Quick Start

### Run All Analysis Tasks

```bash
cd phase1
python3 run_phase1.py
```

This executes all 5 analysis tasks in sequence and generates all reports.

### Run Individual Tasks

```bash
# Task 1.1: Analyze model structure
python3 analysis/model_analyzer.py

# Task 2.1: Identify gaps in models
python3 analysis/gap_analyzer.py

# Task 2.2: Quantify parameter uncertainty
python3 analysis/uncertainty_analyzer.py

# Task 2.3: Analyze parameter sensitivity
python3 analysis/sensitivity_analysis.py

# Task 3.1: Manage paper collection
python3 analysis/paper_collection.py
```

---

## What Phase 1 Does

### Task 1.1: Model Analysis

**Script:** `analysis/model_analyzer.py`

**Purpose:** Analyzes the structure of existing `.compmodel` files to extract all components.

**What it analyzes:**
- **Compartments**: All population states (Susceptible, Infected, etc.)
- **Flows**: Transitions between compartments (ContactFlow, RateFlow, etc.)
- **Parameters**: Symbolic parameters (transmission rates, recovery rates, etc.)
- **Groups**: Stratification groups (age groups, risk groups, etc.)
- **Products**: Cartesian products for multi-dimensional stratification
- **Initial Conditions**: Starting population in each compartment
- **External Sources/Sinks**: Births, deaths, immigration, emigration

**Models analyzed:**
- **COVID-19** (`Compartmental/CompartmentalModel/covid.compmodel`)
  - 15 compartments with age stratification (0-17, 18-64, 65+)
  - Focus on hospitalization and ICU dynamics
  - No natural deaths (only COVID-induced deaths)

- **Malaria** (`Compartmental/CompartmentalModel/malaria.compmodel`)
  - 7 compartments: Human (SH, VH, EH1, EH2, IH, TH, RH) + 3 Mosquito (SM, EM, IM)
  - 17 symbolic parameters (first fully parametric model)
  - Dual transmission: vector-borne + non-vector (blood transfusion, congenital)
  - Vaccination dynamics with waning immunity

- **HIV** (`Compartmental/CompartmentalModel/HIV.compmodel`)
  - 5 compartments stratified by sexual behavior (Homosexual Men, Women, Heterosexual Men)
  - Complex transmission including bisexual contacts
  - Natural and AIDS-induced deaths

**Outputs:**
- `reports/model_analysis/covid_19_analysis.json` - Complete COVID-19 breakdown
- `reports/model_analysis/malaria_analysis.json` - Complete Malaria breakdown
- `reports/model_analysis/hiv_analysis.json` - Complete HIV breakdown
- `reports/model_analysis/all_models_analysis.json` - Combined analysis
- `reports/model_analysis/summary_report.txt` - Quick summary statistics

**Why this matters:**
These analyses serve as "ground truth" for validating automated extraction tools. They document exactly what's in each model with full detail.

---

### Task 2.1: Gap Analysis

**Script:** `analysis/gap_analyzer.py`

**Purpose:** Systematically identifies missing components in models by comparing them to literature expectations and comprehensive disease modeling practices.

**What it identifies:**
- **Structural Gaps**: Missing compartments (e.g., asymptomatic states, chronic stages)
- **Parameter Gaps**: Missing parameters (e.g., seasonality, age-specific rates)
- **Stratification Gaps**: Missing population stratification (e.g., age groups, location)
- **Intervention Gaps**: Missing interventions (e.g., vaccination, treatment protocols)

**Gap Classification:**
- **Critical**: Essential for model validity (e.g., missing key transmission parameter)
- **Medium**: Important but model can function without (e.g., demographic stratification)
- **Low**: Optional enhancements (e.g., seasonal variation, spatial dynamics)

**Outputs:**
- `reports/gap_reports/covid_19_gap_analysis.json` - COVID-19 gaps
- `reports/gap_reports/malaria_gap_analysis.json` - Malaria gaps
- `reports/gap_reports/hiv_gap_analysis.json` - HIV gaps

**Example gaps found:**
- **Malaria**: Missing mosquito lifecycle stages, no spatial dynamics, missing seasonality
- **COVID-19**: No asymptomatic compartment, simplified hospital progression
- **HIV**: No drug resistance modeling, simplified treatment dynamics

**Why this matters:**
Gap analysis helps identify what information is missing when extracting models from papers, and how critical those gaps are.

---

### Task 2.2: Uncertainty Quantification

**Script:** `analysis/uncertainty_analyzer.py`

**Purpose:** Documents all parameters and creates a framework for quantifying uncertainty in parameter values.

**What it tracks:**
- **Parameter Name**: Symbolic name (e.g., `beta`, `gamma`, `mu`)
- **Current Value**: Value used in the model
- **Confidence Level**: High, Medium, Low, Unknown
- **Literature Range**: Template for documenting ranges from multiple papers
- **Units**: Time units (per day, per year, etc.)
- **Source**: Where the value comes from (paper, estimate, calibration)

**Confidence Levels:**
- **High**: Value from multiple papers, narrow range, well-established
- **Medium**: Value from single paper or wider range
- **Low**: Rough estimate, wide range, context-dependent
- **Unknown**: No source documented, needs investigation

**Outputs:**
- `reports/uncertainty/covid_19_uncertainty.json` - COVID-19 parameters
- `reports/uncertainty/malaria_uncertainty.json` - Malaria parameters (10 tracked)
- `reports/uncertainty/hiv_uncertainty.json` - HIV parameters
- `reports/uncertainty/all_models_uncertainty.json` - Combined database

**Example (Malaria model):**
```json
{
  "parameter": "pi",
  "name": "pi (human birth rate)",
  "current_value": "0.012",
  "confidence": "Unknown",
  "literature_range": {
    "min": null,
    "max": null,
    "typical": null,
    "papers": []
  },
  "unit": "per day",
  "notes": "From model definition"
}
```

**Why this matters:**
Understanding parameter uncertainty is crucial for evaluating model predictions and identifying which parameters need better estimates from literature.

---

### Task 2.3: Sensitivity Analysis

**Script:** `analysis/sensitivity_analysis.py`

**Purpose:** Analyzes how sensitive model outputs are to changes in input parameters.

**What it calculates:**
- **Sensitivity Score**: How much output changes when parameter changes
- **Parameter Ranking**: Which parameters have the biggest impact
- **Impact Categories**:
  - Peak infections
  - Time to peak
  - Total cases
  - Disease prevalence

**Method:**
- Varies each parameter by ±10%, ±20%, ±50%
- Simulates model with varied parameters
- Measures change in key outputs
- Ranks parameters by sensitivity score

**Outputs:**
- `reports/sensitivity/covid_19_sensitivity_analysis.json` - COVID-19 sensitivity
- `reports/sensitivity/malaria_sensitivity_analysis.json` - Malaria sensitivity
- `reports/sensitivity/hiv_sensitivity_analysis.json` - HIV sensitivity

**Note:** Currently generates framework. Full sensitivity requires numeric parameters (only Malaria has them - 17 parameters).

**Why this matters:**
Identifies which parameters need the most accurate estimates - high-sensitivity parameters have large impact on predictions.

---

### Task 3.1: Paper Collection Management

**Script:** `analysis/paper_collection.py`

**Purpose:** Maintains a structured database of epidemiological papers with metadata.

**What it tracks:**
- **Paper ID**: Unique identifier (e.g., `malaria_2025_1`)
- **Title, Authors, Year**: Basic metadata
- **Disease**: Which disease the paper models
- **PDF Path**: Location of paper file
- **Notes**: Key observations about the paper
- **Extracted Model**: Link to extracted model (if available)
- **Gaps**: Documented gaps in the model

**Current Collection:**
- 3 papers: COVID-19, Malaria, HIV
- Framework supports unlimited papers
- Organized by disease categories

**Outputs:**
- `reports/paper_collection/paper_collection.json` - Collection index
- `data/papers/collection_index.json` - Paper database
- `data/papers/{paper_id}/metadata.json` - Individual paper metadata

**Why this matters:**
Provides organized access to papers for training AI extraction tools and validating results.

---

## Static Outputs (Already Generated)

These outputs define standards and don't need regeneration:

### Extraction Protocol
**Location:** `reports/protocols/extraction_protocol.json` (+ .md)

**Content:** 8-step protocol for extracting models from papers
1. Identify model type
2. Extract basic structure
3. Extract compartments
4. Classify flows
5. Extract parameters
6. Identify stratification
7. Extract initial conditions
8. Document gaps

**Why this matters:** Provides systematic rules for manual and automated extraction.

---

### Required vs Optional Rules
**Location:** `reports/protocols/required_optional.json` (+ .md)

**Content:** Clear definitions of what's always required vs conditionally required

**Always Required:**
- At least 2 compartments
- At least 1 flow
- Rates or parameters for all flows
- Total population

**Conditionally Required:**
- Parameters (if model uses symbolic notation)
- Stratification (if paper mentions age/risk groups)
- Vector compartments (for vector-borne diseases)
- External sources/sinks (if births/deaths modeled)

**Why this matters:** Helps validate extracted models - can detect incomplete extractions.

---

### Taxonomies
**Location:** `reports/taxonomies/taxonomies.json` (+ .md)

**Content:** Classification system for model components

**Classifications:**
- **Compartment Types**: Disease States, Healthcare, Vectors, Demographics, Treatment
- **Flow Types**: ContactFlow, RateFlow, ExternalSource, ExternalSink
- **Parameter Types**: CONSTANT, VARIABLE, EXPRESSION
- **Stratification Types**: Age, Gender, Risk, Location, Vaccination Status

**Note:** These are examples based on COVID/Malaria/HIV - not exhaustive lists.

**Why this matters:** Helps categorize components during extraction and analysis.

---

### Pattern Library
**Location:** `reports/patterns/pattern_library.json` (+ .md)

**Content:** Common modeling patterns across diseases

**5 Patterns Identified:**
1. **Standard SEIR**: Basic susceptible-exposed-infectious-recovered
2. **SEIR with Hospitalization**: Adds hospital and ICU compartments
3. **Vector-Borne (Malaria)**: Human + mosquito compartments
4. **With Treatment**: Adds treatment and recovery pathways
5. **Stratified Model**: Population divided by age/risk/behavior

**Why this matters:** Recognizing patterns helps identify model structure faster.

---

### Extraction Templates
**Location:** `reports/manual_extraction/extraction_template.json`

**Content:** JSON template for manually extracting models from papers

**Includes fields for:**
- Basic information (paper, disease, date)
- Compartments (name, population, type)
- Flows (source, target, type, rate)
- Parameters (name, value, unit, description)
- Stratification (groups, dimensions)
- Initial conditions
- Gaps identified

**Example:** `extraction_example.json` shows completed template for COVID-19

**Why this matters:** Provides standard format for manual extraction, usable as training data for AI.

---


## Technical Details

### XML Parsing
Models are stored as XML files with `.compmodel` extension. The `utils/xml_parser.py` module provides `CompModelParser` class for parsing:

```python
from utils.xml_parser import CompModelParser

parser = CompModelParser('path/to/model.compmodel')
info = parser.get_model_info()
```

### Model File Locations
Models are in the parent `Compartmental/CompartmentalModel/` directory:
- `../Compartmental/CompartmentalModel/covid.compmodel`
- `../Compartmental/CompartmentalModel/malaria.compmodel`
- `../Compartmental/CompartmentalModel/HIV.compmodel`


---
