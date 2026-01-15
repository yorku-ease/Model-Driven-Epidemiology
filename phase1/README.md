# Phase 1: Model Analysis and Documentation Framework

**A comprehensive suite of Python tools for analyzing compartmental epidemiological models, extracting patterns from papers, and building systematic frameworks for model validation.**

---

## Table of Contents

1. [Overview](#overview)
2. [Quick Start](#quick-start)
3. [Analysis Tasks](#analysis-tasks)
   - [Task 1.1: Model Analysis](#task-11-model-analysis)
   - [Task 2.1: Gap Analysis (Paper-Driven)](#task-21-gap-analysis)
   - [Task 2.2: Uncertainty Quantification](#task-22-uncertainty-quantification)
   - [Task 2.3: Sensitivity Analysis](#task-23-sensitivity-analysis)
   - [Task 3.1: Paper Collection Management](#task-31-paper-collection-management)
4. [Static Outputs](#static-outputs)
5. [Sensitivity Analysis Methods](#sensitivity-analysis-methods)
6. [Programmatic Usage](#programmatic-usage)
7. [Component Stability](#component-stability)
8. [Technical Details](#technical-details)
9. [Dependencies](#dependencies)

---

## Overview

Phase 1 performs deep analysis of compartmental models (COVID-19, Malaria, HIV, Traffic networks) to:
1. **Extract** all model components (compartments, flows, parameters)
2. **Identify** gaps and missing elements
3. **Quantify** parameter uncertainty
4. **Analyze** parameter sensitivity
5. **Build** reusable taxonomies and patterns
6. **Compare** models to paper promises

### What Makes This Framework Unique

- **✅ Fully Generalizable**: Works with **any compartmental model** and **any disease/system**
- **✅ No Hardcoding**: No disease-specific logic - adapts automatically to any model
- **✅ Paper-Driven**: Compares models to paper promises (not arbitrary rules)
- **✅ Multi-Method**: Supports Morris, Grid, Random, and Sobol sensitivity analysis
- **✅ Auto-Adapting**: Automatically detects parameter scales, types, and naming conventions
- **✅ Extensible**: All components can be used programmatically or via command line
- **✅ Complete**: Covers structural analysis, gap detection, uncertainty, and sensitivity

### Generalizability Guarantee

**All analysis tasks work with new papers and models without modification:**

| Task | Generalizable? | How? |
|------|---------------|------|
| **Model Analysis** | ✅ Yes | Parses any `.compmodel` XML file |
| **Gap Analysis** | ✅ Yes | Extracts from any paper PDF (pattern or LLM-based) |
| **Uncertainty** | ✅ Yes | Tracks any parameters regardless of naming |
| **Sensitivity** | ✅ Yes | Simulates actual model structure, supports all flow types |
| **Paper Collection** | ✅ Yes | Supports any disease, unlimited papers |

**No changes needed for:**
- New diseases (e.g., Tuberculosis, Dengue, Ebola)
- New model types (e.g., SIS, SEIS, Multi-strain)
- New parameter names (English, Greek letters, custom)
- New paper formats (any PDF with text)
- New stratifications (age, location, vaccination, etc.)

**Works automatically because:**
- Uses XML parsing (structure-agnostic)
- Pattern matching (keyword-based, not disease-specific)
- Smart parameter classification (recognizes types by keywords)
- Auto-scaling (adapts to parameter magnitudes)
- Paper-driven gap analysis (compares to what paper promises)

---

## Quick Start

### Run All Analysis Tasks

```bash
cd phase1
python3 run_phase1.py
```

This executes all 5 analysis tasks in sequence and generates comprehensive reports.

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

### View Generated Reports

```bash
# Model analysis reports
ls reports/model_analysis/

# Gap analysis reports
ls reports/gap_reports/

# Uncertainty reports
ls reports/uncertainty/

# Sensitivity analysis reports
ls reports/sensitivity/

# Paper collection
ls reports/paper_collection/
```

---

## Analysis Tasks

### Task 1.1: Model Analysis

**Script:** `analysis/model_analyzer.py`

**Purpose:** Analyzes the structure of existing `.compmodel` files to extract all components.

**✅ Fully Generalizable:** Works with any `.compmodel` file regardless of disease type or model structure.

#### Input

**Automatic (no user input needed):**
- Searches `../Compartmental/CompartmentalModel/` directory for all `.compmodel` files
- Parses each XML file automatically

**What it extracts:**
- All compartments and their properties
- All flows between compartments
- All parameters (symbolic and numeric)
- Stratification groups and products
- External sources and sinks
- Initial populations

#### What it Analyzes

- **Compartments**: All population states (Susceptible, Infected, Recovered, etc.)
- **Flows**: Transitions between compartments (ContactFlow, RateFlow, etc.)
- **Parameters**: Symbolic parameters (transmission rates, recovery rates, etc.)
- **Groups**: Stratification groups (age groups, risk groups, etc.)
- **Products**: Cartesian products for multi-dimensional stratification
- **Initial Conditions**: Starting population in each compartment
- **External Sources/Sinks**: Births, deaths, immigration, emigration

#### Models Analyzed

**COVID-19** (`covid.compmodel`)
- 15 compartments with age stratification (0-17, 18-64, 65+)
- Focus on hospitalization and ICU dynamics
- No natural deaths (only COVID-induced deaths)

**Malaria** (`malaria.compmodel`)
- 7 human compartments + 3 mosquito compartments
- 17 symbolic parameters (first fully parametric model)
- Dual transmission: vector-borne + non-vector (blood transfusion, congenital)
- Vaccination dynamics with waning immunity
- Temperature-dependent transmission

**HIV** (`HIV.compmodel`)
- 5 compartments stratified by sexual behavior
- Complex transmission including bisexual contacts
- Natural and AIDS-induced deaths

**Traffic Networks** (`traffic.compmodel`, `trm_highway.compmodel`)
- Classical traffic flow (Coogan & Arcak 2015)
- Traffic Reaction Model (TRM) with flux decomposition
- Supply-demand constraints and junction rules

#### Output

```
reports/model_analysis/
├── [model_name]_analysis.json      # Individual model analysis
├── all_models_analysis.json        # Combined analysis of all models
└── summary_report.txt              # Quick summary statistics
```

**Each JSON file contains:**
```json
{
  "modelName": "Model Name",
  "summary": {
    "numCompartments": 15,
    "numParameters": 17,
    "numGroups": 1,
    "hasStratification": true,
    "hasVectorCompartments": false
  },
  "data": {
    "compartments": [...],
    "flows": [...],
    "parameters": [...],
    "groups": [...],
    "externalSources": [...],
    "externalSinks": [...]
  }
}
```

**Summary report shows:**
- Model name
- Number of compartments, parameters, groups
- Whether model has stratification
- Whether model has vector compartments
- External sources/sinks count

#### Why This Matters

These analyses serve as "ground truth" for validating automated extraction tools. They document exactly what's in each model with full detail.

#### Usage

```bash
# Command line
python3 analysis/model_analyzer.py

# Programmatic
from analysis.model_analyzer import ModelAnalyzer

analyzer = ModelAnalyzer("model.compmodel", "Model Name")
summary = analyzer.generate_summary()
analyzer.export_to_json("output.json")
```

---

### Task 2.1: Gap Analysis

**Script:** `analysis/gap_analyzer.py`

**Purpose:** Systematically identifies missing components in models by comparing to paper promises.

**✅ Fully Generalizable:** Works with any `.compmodel` file and any paper (PDF). No disease-specific rules.

#### Input

**Required:**
- `.compmodel` file path
- Model name
- Paper PDF path (optional but recommended)

**Optional:**
- `--use-llm`: Use LLM for extraction (requires API key)
- `--api-key`: OpenAI API key for LLM extraction

**Automatic extraction from paper:**
- Promised compartments
- Promised stratifications
- Promised parameters
- Promised interventions
- Model type (SEIR, SIR, Vector-Borne, etc.)

**Extraction methods:**
1. **Pattern-based** (default): Uses regex patterns to extract from paper, no API key needed
2. **LLM-based** (optional): Uses GPT-4 for better accuracy (requires OpenAI API key)

#### How It Works

The gap analyzer:
1. Extracts promises from the paper PDF (what the paper says it will model)
2. Analyzes the actual model structure
3. Compares them and identifies gaps
4. Only flags gaps for things the paper explicitly promises (paper-driven)

#### What it Identifies

- **Structural Gaps**: Missing compartments promised by paper
- **Parameter Gaps**: Missing parameters promised by paper
- **Stratification Gaps**: Missing stratification promised by paper
- **Intervention Gaps**: Missing interventions promised by paper

#### Gap Classification

- **High/Critical**: Paper promises it but model doesn't have it
- **Medium**: Important but model can function without
- **Low**: Optional enhancements

#### Output

```
reports/gap_reports/
├── [model_name]_gap_analysis.json  # Individual model gaps
└── all_models_gap_summary.json     # Summary across all models
```

**Each gap report contains:**
```json
{
  "modelName": "COVID-19",
  "analysisDate": "2026-01-14",
  "paperPromises": {
    "compartments": ["Asymptomatic", "Severe", "ICU", ...],
    "stratifications": ["age"],
    "parameters": ["mortality_rate", "contact_rate"],
    "interventions": ["vaccination", "quarantine"],
    "description": "What paper promises to model"
  },
  "totalGaps": 7,
  "gaps": {
    "structuralGaps": [
      {
        "gap": "Missing Promised Compartment: Asymptomatic",
        "description": "Paper promises Asymptomatic but model lacks it",
        "promisedByPaper": true,
        "severity": "high"
      }
    ],
    "parameterGaps": [...],
    "stratificationGaps": [...],
    "interventionGaps": [...]
  },
  "summary": {
    "criticalGaps": 4,
    "mediumGaps": 2,
    "lowGaps": 1,
    "promisedByPaper": 7
  }
}
```

**Summary report shows:**
- Total gaps across all models
- Gaps by model
- Gaps by severity (critical/medium/low)
- Gaps by type (structural/parameter/stratification/intervention)

#### Usage

```bash
# Phase 1: Minimal generic checks
python3 analysis/gap_analyzer.py

# With paper (pattern-based - default)
python3 analysis/gap_analyzer.py \
    --model covid.compmodel \
    --name "COVID-19" \
    --paper-pdf covid.pdf

# With paper and LLM (more accurate)
python3 analysis/gap_analyzer.py \
    --model covid.compmodel \
    --name "COVID-19" \
    --paper-pdf covid.pdf \
    --use-llm \
    --api-key "sk-..."

# Programmatic (recommended)
from analysis.gap_analyzer import analyze_gaps_with_paper

report = analyze_gaps_with_paper(
    model_path="model.compmodel",
    model_name="COVID-19",
    paper_path="paper.pdf",
    use_llm=True,
    llm_api_key="sk-..."
)
```

#### Why This Matters

Gap analysis helps identify what information is missing when extracting models from papers, and how critical those gaps are. The paper-driven approach ensures gaps reflect **faithfulness to the paper** rather than arbitrary literature standards.

---

### Task 2.2: Uncertainty Quantification

**Script:** `analysis/uncertainty_analyzer.py`

**Purpose:** Documents all parameters and creates a framework for quantifying uncertainty in parameter values.

**✅ Fully Generalizable:** Works with any `.compmodel` file. Extracts all parameters regardless of disease type.

#### Input

**Automatic (no user input needed):**
- Extracts parameters from `.compmodel` files
- Works for: COVID-19, Malaria, HIV, Traffic, and any new model

**What it extracts:**
- All explicit parameters (from `<parameters>` section)
- Parameter names (symbolic names like β, γ, α)
- Current values (if numeric)
- Parameter descriptions (if available)

#### What it Tracks

For each parameter:
- **Parameter Name**: Symbolic name (e.g., `beta`, `gamma`, `mu`, `β`, `α`)
- **Current Value**: Value used in the model
- **Confidence Level**: High, Medium, Low, Unknown
- **Literature Range**: Template for documenting ranges from multiple papers
- **Units**: Time units (per day, per year, etc.)
- **Source**: Where the value comes from (paper, estimate, calibration)
- **Notes**: Additional context about the parameter

#### Confidence Levels

- **High**: Value from multiple papers, narrow range, well-established
- **Medium**: Value from single paper or wider range
- **Low**: Rough estimate, wide range, context-dependent
- **Unknown**: No source documented, needs investigation (default)

#### Output

```
reports/uncertainty/
├── [model_name]_uncertainty.json   # Individual model parameters
└── all_models_uncertainty.json     # Combined database
```

**Each uncertainty report contains:**
```json
{
  "modelName": "Malaria",
  "analysisDate": "2026-01-14",
  "totalParameters": 10,
  "parameters": [
    {
      "parameter": "β₁",
      "name": "β₁ (transmission rate)",
      "current_value": "0.00001",
      "confidence": "Unknown",
      "literature_range": {
        "min": null,
        "max": null,
        "typical": null,
        "papers": []
      },
      "unit": "per day",
      "notes": "From model definition",
      "source": "model"
    }
  ],
  "confidenceSummary": {
    "high": 0,
    "medium": 0,
    "low": 0,
    "unknown": 10
  }
}
```

**Combined report aggregates:**
- All parameters across all models
- Confidence level distribution
- Models with most/least documented parameters

#### Example Output

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

#### Usage

```bash
# Command line
python3 analysis/uncertainty_analyzer.py

# Programmatic
from analysis.uncertainty_analyzer import UncertaintyAnalyzer

analyzer = UncertaintyAnalyzer("model.compmodel", "Model Name")
analyzer.export_uncertainty_db("output.json")
```

#### Why This Matters

Understanding parameter uncertainty is crucial for evaluating model predictions and identifying which parameters need better estimates from literature.

---

### Task 2.3: Sensitivity Analysis

**Script:** `analysis/sensitivity_analysis.py`

**Purpose:** Analyzes how sensitive model outputs are to changes in input parameters using **actual compartmental model dynamics**.

**✅ Fully Generalizable:** Works with any `.compmodel` file and any disease/model type. Automatically adapts to parameter scales.

**🎯 Key Feature:** Uses a generic model simulator that parses and simulates the actual model structure from `.compmodel` files - not a simplified approximation. Supports ContactFlow, RateFlow, ExternalSource, and ExternalSink flow types.

#### Input

**Required:**
- `.compmodel` file path (any compartmental model)
- Model name

**Optional:**
- `--method`: Sensitivity method (morris/random/grid/sobol)
- `--variation`: Variation range (default 0.5 = ±50%)

**What the script extracts automatically:**
- Parameters from explicit parameter definitions
- Parameters embedded in flows (rates, contact rates)
- Parameter types (transmission, recovery, progression, mortality)
- Current parameter values

#### Methods Supported

1. **Morris Method** (recommended for screening)
   - One-at-a-time parameter variation
   - Calculates μ* (importance) and σ (non-linearity)
   - Fast and efficient for many parameters

2. **Grid Search**
   - Systematic exploration of parameter space
   - Complete coverage
   - Best for 2-3 parameters

3. **Random Search**
   - Random sampling of parameter combinations
   - Flexible and fast
   - Good for exploration

4. **Sobol Sequences**
   - Quasi-random sampling
   - Better space coverage than random
   - Requires scipy

#### What it Calculates

**Sensitivity Indices:**
- **μ* (mu_star)**: Mean absolute effect - measures parameter importance
  - 0-10: Low importance
  - 10-50: Medium-high importance
  - 50-150: Very high importance
  - 150+: Extreme importance

- **σ (sigma)**: Standard deviation - measures non-linearity/interactions
  - 0-10: Linear effects
  - 10-50: Some non-linearity
  - 50-150: Strong non-linearity
  - 150+: Highly non-linear

**Model Outputs Analyzed:**
- Peak infections
- Time to peak
- Total cases
- Final recovered

#### Smart Features (Automatic)

**1. Intelligent Parameter Mapping**
- Recognizes transmission/contact parameters (beta, β, contact, transmission)
- Recognizes recovery parameters (gamma, γ, recovery)
- Recognizes progression parameters (sigma, σ, rho, ρ, incubation)
- Works with any parameter naming convention

**2. Auto-Scaled Variation Ranges**
- Very small parameters (< 0.001): ±200% variation
- Small parameters (< 0.01): ±100% variation
- Normal parameters: ±50% variation
- Ensures meaningful sensitivity for all parameter scales

**3. Actual Model Dynamics**
- Parses compartments and flows from `.compmodel` files
- Simulates using actual model structure (not simplified approximations)
- Supports ContactFlow, RateFlow, ExternalSource, ExternalSink
- Resolves parameter references and evaluates expressions
- Uses model's own parameter values and initial conditions

#### Output

```
reports/sensitivity/
├── [model_name]_sensitivity_morris.json
├── [model_name]_sensitivity_random.json
├── [model_name]_sensitivity_grid.json
└── [model_name]_sensitivity_sobol.json
```

**Each JSON report contains:**
```json
{
  "modelName": "Model Name",
  "method": "morris",
  "keyParameters": [
    {
      "parameter": "contactRate_X_to_Y",
      "type": "transmission",
      "currentValue": 0.00001,
      "importance": "high",
      "source": "flow"
    }
  ],
  "parametersAnalyzed": ["param1", "param2", ...],
  "sensitivityAnalysis": {
    "method": "Morris",
    "n_trajectories": 20,
    "sensitivity_indices": {
      "param1": {
        "mu": 45.81,
        "mu_star": 45.81,
        "sigma": 72.25
      }
    },
    "interpretation": {
      "mu_star": "Mean absolute effect (higher = more important)",
      "sigma": "Standard deviation (higher = more non-linear)"
    }
  }
}
```

#### Example Results

**COVID-19:**
- Contact rate (Susceptible→Exposed): μ*=15910 (extreme impact)
- Dominates disease transmission
- Age-stratified rates: Show minimal sensitivity (likely inactive in model dynamics)

**HIV:**
- Contact rate (Susceptible→Infectious): μ*=1.32 (medium impact)
- Treatment rate (Infectious→Treated): μ*=0.10 (low impact)
- Shows sensitivity to transmission and treatment parameters

**Malaria:**
- Vector-borne model with expression parameters
- Currently shows limited sensitivity due to complex expressions and initialization
- Requires temperature (T) and other environmental variables to be properly defined

#### Usage

```bash
# Morris method (default, recommended)
python3 analysis/sensitivity_analysis.py --method morris

# With custom variation range
python3 analysis/sensitivity_analysis.py --method morris --variation 0.3

# Other methods
python3 analysis/sensitivity_analysis.py --method random
python3 analysis/sensitivity_analysis.py --method grid
python3 analysis/sensitivity_analysis.py --method sobol

# Programmatic
from analysis.sensitivity_analysis import SensitivityAnalyzer

analyzer = SensitivityAnalyzer("model.compmodel", "Model Name")
report = analyzer.generate_sensitivity_report(method='morris', variation_range=0.5)
analyzer.export_sensitivity_report("output.json", method='morris')
```

#### Why This Matters

Identifies which parameters need the most accurate estimates - high-sensitivity parameters have large impact on predictions and should be prioritized in data collection and calibration efforts.

#### Generalizability

**✅ Works with any model:**
- No disease-specific logic
- No hardcoded parameter names
- Automatic parameter detection and classification
- Adapts to any parameter scale
- Handles stratified models, vector-borne models, multi-host models

**✅ Works with any parameter naming:**
- English names (transmission_rate, recovery_rate)
- Greek letters (β, γ, σ, ρ, α, μ)
- Custom names (contactRate_X_to_Y, rate_A_to_B)
- Flow-embedded rates (extracted automatically)

#### Implementation Details

**✅ Uses Actual Model Structure:**
- Parses compartments, flows, and parameters from `.compmodel` files
- Simulates using actual model dynamics (not simplified SEIR approximation)
- Supports ContactFlow, RateFlow, ExternalSource, and ExternalSink
- Handles parameter references and expression evaluation
- Works with stratified models and complex flow patterns

**What works well:**
- ✅ SEIR-like models (COVID-19, HIV) - Full support
- ✅ Multi-compartment models with various flow types
- ✅ Contact-based transmission (mass action dynamics)
- ✅ Rate-based transitions between compartments
- ✅ Parameter screening and ranking
- ✅ Works with any parameter names and scales

**What has limitations:**
- ⚠️ Vector-borne models with expression parameters (Malaria)
  - Models using expression parameters (e.g., `β₁a(T) = β₁ * a(T)`) require all sub-parameters defined
  - Temperature-dependent functions need environmental variables set
  - Complex initialization for multi-species models
- ⚠️ Traffic networks - needs supply-demand constraint support
- ⚠️ Models with undefined environmental variables (T, R, etc.)

**Why these limitations exist:**
- Expression parameters like `a(T) = 0.000203 * T * (T - 11.7) * sqrt(42.3 - T)` require temperature T to be defined
- Vector-borne models need careful initialization of both host and vector populations
- External sources/sinks with expression-based rates need all dependencies resolved

**Recommendations:**
- Works best with models that have:
  - Direct numeric parameter values or simple parameter references
  - Well-defined initial populations
  - Standard flow types (contact, rate, external)
- For models with complex expressions: ensure all dependent variables are defined
- For vector-borne models: verify mosquito/vector compartments are initialized

**Example results:**
- COVID-19: contactRate shows μ*=15910 (extreme sensitivity) ✅ Excellent
- HIV: contactRate shows μ*=1.3, treatment rate shows μ*=0.1 ✅ Good
- Malaria (vector-borne): Only 1/5 show sensitivity ⚠️ Limited
- Traffic: Not validated ⚠️ Not recommended

---

### Task 3.1: Paper Collection Management

**Script:** `analysis/paper_collection.py`

**Purpose:** Maintains a structured database of epidemiological papers with metadata.

**✅ Fully Generalizable:** Supports any disease type and unlimited number of papers. Extensible metadata structure.

#### Input

**Automatic (initializes with 3 hardcoded papers):**
- COVID-19: Tuite et al. 2020
- Malaria: Akowe et al. 2025
- HIV: Espitia et al. 2022

**Programmatic addition:**
```python
from analysis.paper_collection import PaperCollection

collection = PaperCollection()
collection.add_paper(
    title="Paper Title",
    authors="Author et al.",
    year=2025,
    disease="Disease Name",
    pdf_path="path/to/paper.pdf",
    notes="Key observations"
)
```

#### What it Tracks

For each paper:
- **Paper ID**: Unique identifier (e.g., `malaria_2025_1`)
- **Title, Authors, Year**: Basic metadata
- **Disease**: Which disease the paper models
- **PDF Path**: Location of paper file
- **Notes**: Key observations about the paper
- **Extracted Model**: Link to extracted model (if available)
- **Gaps**: Documented gaps in the model
- **Date Added**: When paper was added to collection

#### Output

```
reports/paper_collection/
└── paper_collection.json           # Collection index and summary

data/papers/
├── collection_index.json           # Full paper database
└── [paper_id]/
    └── metadata.json              # Individual paper metadata
```

**Collection report contains:**
```json
{
  "totalPapers": 3,
  "diseases": ["COVID-19", "Malaria", "HIV"],
  "papers": [
    {
      "id": "malaria_2025_1",
      "title": "Mathematical Model of Malaria Transmission Dynamics",
      "authors": "Akowe et al.",
      "year": 2025,
      "disease": "Malaria",
      "pdf_path": null,
      "notes": "Includes vector dynamics and vaccination",
      "extracted_model": "malaria.compmodel",
      "dateAdded": "2026-01-14"
    }
  ],
  "byDisease": {
    "COVID-19": 1,
    "Malaria": 1,
    "HIV": 1
  }
}
```

**Features:**
- Organized by disease type
- Searchable by title, authors, disease
- Links to extracted models
- Tracks gaps and notes
- Supports unlimited papers
- Extensible metadata structure

#### Usage

```bash
# Command line
python3 analysis/paper_collection.py

# Programmatic
from analysis.paper_collection import PaperCollection

collection = PaperCollection()
collection.add_paper(
    title="COVID-19 Model",
    authors="Smith et al.",
    year=2020,
    disease="COVID-19",
    pdf_path="papers/covid.pdf",
    notes="Age-stratified model"
)
collection.export_report("output.json")
```

#### Why This Matters

Provides organized access to papers for training AI extraction tools and validating results.

---

## Static Outputs

These outputs define standards and don't need regeneration.

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

### Required vs Optional Rules

**Location:** `reports/protocols/required_optional.json` (+ .md)

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

### Taxonomies

**Location:** `reports/taxonomies/taxonomies.json` (+ .md)

**Classifications:**
- **Compartment Types**: Disease States, Healthcare, Vectors, Demographics, Treatment
- **Flow Types**: ContactFlow, RateFlow, ExternalSource, ExternalSink
- **Parameter Types**: CONSTANT, VARIABLE, EXPRESSION
- **Stratification Types**: Age, Gender, Risk, Location, Vaccination Status

**Note:** These are examples based on analyzed models - not exhaustive lists.

### Pattern Library

**Location:** `reports/patterns/pattern_library.json` (+ .md)

**5 Patterns Identified:**
1. **Standard SEIR**: Basic susceptible-exposed-infectious-recovered
2. **SEIR with Hospitalization**: Adds hospital and ICU compartments
3. **Vector-Borne (Malaria)**: Human + mosquito compartments
4. **With Treatment**: Adds treatment and recovery pathways
5. **Stratified Model**: Population divided by age/risk/behavior

### Extraction Templates

**Location:** `reports/manual_extraction/extraction_template.json`

JSON template for manually extracting models from papers with fields for:
- Basic information (paper, disease, date)
- Compartments (name, population, type)
- Flows (source, target, type, rate)
- Parameters (name, value, unit, description)
- Stratification (groups, dimensions)
- Initial conditions
- Gaps identified

---

## Sensitivity Analysis Methods

### Method Comparison

| Method | Best For | Pros | Cons |
|--------|----------|------|------|
| **Morris** | Initial screening, many parameters | Fast, identifies important parameters, detects non-linearity | One-at-a-time, may miss interactions |
| **Grid Search** | Few parameters, systematic exploration | Complete coverage, reproducible | Exponential cost (curse of dimensionality) |
| **Random Search** | Exploration, many parameters | Simple, flexible | Less efficient than Sobol |
| **Sobol Sequences** | Global sensitivity, many parameters | Better space coverage than random | Requires scipy |

### Morris Method Details

**Recommended for initial screening**

Provides two metrics:
- **μ*** (mu_star): Mean absolute effect (importance)
- **σ** (sigma): Standard deviation (non-linearity/interactions)

**Interpretation:**
- High μ*: Parameter is important
- High σ: Parameter has non-linear effects or interactions

```python
morris_result = analyzer.morris_method(
    parameters={'beta': (0.000005, 0.000015), 'gamma': (0.05, 0.15)},
    n_trajectories=20
)
```

### Grid Search Details

Systematically explores all combinations of parameter values.

**Use when:**
- You have few parameters (2-3)
- You want complete coverage
- Computational cost is acceptable

```python
grid_result = analyzer.grid_search({
    'beta': (0.000005, 0.000015, 5),  # min, max, steps
    'gamma': (0.05, 0.15, 5)
})
```

### Random Search Details

Samples parameter combinations randomly.

**Use when:**
- You have many parameters
- You want quick exploration
- Computational budget is limited

```python
random_result = analyzer.random_search({
    'beta': (0.000005, 0.000015),
    'gamma': (0.05, 0.15)
}, n_samples=100)
```

### Sobol Sequences

Quasi-random sampling with better space coverage than pure random.

**Requirements:**
```bash
pip install scipy
```

```python
sobol_result = analyzer.sobol_method({
    'beta': (0.000005, 0.000015),
    'gamma': (0.05, 0.15)
}, n_samples=100)
```

### Recommendations

1. **Start with Morris method** for initial screening
2. **Use grid search** if you have 2-3 parameters and want complete coverage
3. **Use random/Sobol** for exploration with many parameters
4. **Use multi-parameter** to detect interactions

---

## Programmatic Usage

All analyzers can be used programmatically from another script.

### Example 1: Complete Gap Analysis Workflow

```python
from pathlib import Path
from analysis.paper_promise_extractor import PaperPromiseExtractor
from analysis.gap_analyzer import GapAnalyzer, analyze_gaps_with_paper

# Option A: Use convenience function (easiest)
report = analyze_gaps_with_paper(
    model_path="Compartmental/CompartmentalModel/covid.compmodel",
    model_name="COVID-19",
    paper_path="papers/epimde/covid.pdf",
    use_llm=True,
    llm_api_key="sk-...",
    output_path="reports/gap_reports/covid_with_paper.json"
)

# Option B: Step-by-step (more control)
# Step 1: Extract promises
extractor = PaperPromiseExtractor(use_llm=True, llm_api_key="sk-...")
promises = extractor.extract_from_pdf("papers/epimde/covid.pdf")

# Step 2: Analyze gaps
analyzer = GapAnalyzer(
    model_path="Compartmental/CompartmentalModel/covid.compmodel",
    model_name="COVID-19",
    paper_promises=promises
)
report = analyzer.generate_gap_report()

# Step 3: Export
analyzer.export_gap_report("reports/gap_reports/covid_with_paper.json")
```

### Example 2: Batch Processing Multiple Models

```python
from pathlib import Path
from analysis.model_analyzer import ModelAnalyzer
from analysis.gap_analyzer import analyze_gaps_with_paper

models_dir = Path("Compartmental/CompartmentalModel")
papers_dir = Path("papers/epimde")

# Find all models
for model_file in models_dir.glob("*.compmodel"):
    model_name = model_file.stem.replace('_', ' ').title()

    # Find corresponding paper
    paper_file = papers_dir / f"{model_file.stem}.pdf"

    if paper_file.exists():
        # Analyze model structure
        model_analyzer = ModelAnalyzer(str(model_file), model_name)
        model_analyzer.export_to_json(
            f"reports/model_analysis/{model_file.stem}_analysis.json"
        )

        # Analyze gaps with paper
        report = analyze_gaps_with_paper(
            model_path=str(model_file),
            model_name=model_name,
            paper_path=str(paper_file),
            use_llm=True,
            llm_api_key="sk-..."
        )
        print(f"✓ Analyzed {model_name}: {report['totalGaps']} gaps found")
```

### Example 3: Custom Sensitivity Analysis

```python
from analysis.sensitivity_analysis import SensitivityAnalyzer

analyzer = SensitivityAnalyzer("model.compmodel", "Model Name")

# Morris method (recommended for screening)
morris_result = analyzer.morris_method(
    parameters={'beta': (0.000005, 0.000015), 'gamma': (0.05, 0.15)},
    n_trajectories=20
)

# Grid search
grid_result = analyzer.grid_search(
    parameters={'beta': (0.000005, 0.000015, 5), 'gamma': (0.05, 0.15, 5)}
)

# Multi-parameter sensitivity
multi_result = analyzer.multi_parameter_sensitivity(
    parameters={'beta': (0.000005, 0.000015), 'gamma': (0.05, 0.15)},
    method='morris',
    n_samples=100
)
```

### Key Classes Summary

| Class | Purpose | Module |
|-------|---------|--------|
| `ModelAnalyzer` | Analyze model structure | `model_analyzer.py` |
| `GapAnalyzer` | Identify gaps | `gap_analyzer.py` |
| `PaperPromiseExtractor` | Extract paper promises | `paper_promise_extractor.py` |
| `UncertaintyAnalyzer` | Quantify uncertainty | `uncertainty_analyzer.py` |
| `SensitivityAnalyzer` | Parameter sensitivity | `sensitivity_analysis.py` |
| `PaperCollection` | Manage papers | `paper_collection.py` |

---

## Component Stability

### Status Summary

| Component | Status | Notes |
|-----------|--------|-------|
| **Model Analyzer** | ✅ Stable | Generic XML parsing |
| **Uncertainty Analyzer** | ✅ Stable | Generic structure |
| **Paper Collection** | ✅ Stable | Generic framework |
| **Gap Analysis** | ✅ Stable | Fully generic, paper-driven |
| **Sensitivity Analysis** | ✅ Stable | Uses actual model structure from `.compmodel` files |
| **Taxonomies** | ✅ Extensible | Marked as examples |
| **Patterns** | ✅ Extensible | Marked as examples |

### For New Diseases

**What works out of the box:**
- Model analysis (any .compmodel file)
- Uncertainty tracking (any parameters)
- Paper collection (any paper)
- Paper-driven gap analysis (fully generic)
- Sensitivity analysis (parses and simulates actual model structure)
  - Full support: Standard compartmental models (SEIR, SIR, etc.)
  - Full support: Multi-compartment models with ContactFlow, RateFlow
  - Limited support: Models with complex expression parameters (vector-borne with temperature dependencies)

**What has limitations:**
- Sensitivity analysis for models with:
  - Complex expression parameters requiring environmental variables (T, R, etc.)
  - Temperature-dependent transmission rates
  - Multi-species models with intricate initialization requirements
- Recommend: For complex expression-based models, ensure all dependent variables are defined

---

## Technical Details

### XML Parsing

Models are stored as XML files with `.compmodel` extension. The `utils/xml_parser.py` module provides `CompModelParser` class:

```python
from utils.xml_parser import CompModelParser

parser = CompModelParser('path/to/model.compmodel')
info = parser.get_model_info()
compartments = parser.get_compartments()
flows = parser.get_flows()
parameters = parser.get_parameters()
```

### Model File Locations

Models are in the parent `Compartmental/CompartmentalModel/` directory:
- `../Compartmental/CompartmentalModel/covid.compmodel`
- `../Compartmental/CompartmentalModel/malaria.compmodel`
- `../Compartmental/CompartmentalModel/HIV.compmodel`
- `../Compartmental/CompartmentalModel/traffic.compmodel`
- `../Compartmental/CompartmentalModel/trm_highway.compmodel`

### Directory Structure

```
phase1/
├── README.md                       # This file
├── run_phase1.py                   # Main task runner
├── requirements.txt                # Python dependencies
│
├── analysis/                       # Core analysis scripts
│   ├── model_analyzer.py           # Task 1.1: Model structure
│   ├── gap_analyzer.py             # Task 2.1: Gap identification
│   ├── uncertainty_analyzer.py     # Task 2.2: Uncertainty quantification
│   ├── sensitivity_analysis.py     # Task 2.3: Sensitivity analysis
│   ├── paper_collection.py         # Task 3.1: Paper management
│   ├── paper_promise_extractor.py  # Paper promise extraction
│   └── example_phase2_usage.py     # Phase 2 examples
│
├── utils/
│   └── xml_parser.py               # XML parsing utilities
│
├── data/
│   └── papers/                     # Paper metadata
│
├── papers/
│   └── epimde/                     # Model files and PDFs
│
└── reports/                        # Generated analysis outputs
    ├── model_analysis/             # Task 1.1 outputs
    ├── gap_reports/                # Task 2.1 outputs
    ├── uncertainty/                # Task 2.2 outputs
    ├── sensitivity/                # Task 2.3 outputs
    ├── paper_collection/           # Task 3.1 outputs
    ├── protocols/                  # Extraction protocol (static)
    ├── taxonomies/                 # Component taxonomies (static)
    ├── patterns/                   # Modeling patterns (static)
    └── manual_extraction/          # Extraction templates (static)
```

---

## Dependencies

### Required (Phase 1)

```bash
pip install lxml xmltodict
```

### Optional (Phase 2)

```bash
# For PDF extraction
pip install pdfplumber  # Recommended
# OR
pip install PyPDF2      # Alternative

# For LLM extraction (best results)
pip install openai
export OPENAI_API_KEY="your-api-key"
```

### Optional (Enhanced Features)

```bash
# For enhanced sensitivity analysis
pip install numpy scipy

# For data export to Excel/CSV
pip install pandas openpyxl
```

### Install All Dependencies

```bash
cd phase1
pip install -r requirements.txt
```

---

## Output Metrics

All analysis tasks calculate and track:
- **Model Structure**: Compartments, flows, parameters, groups
- **Gap Counts**: By type (structural, parameter, stratification, intervention) and severity
- **Uncertainty Levels**: High, Medium, Low, Unknown confidence
- **Sensitivity Indices**: μ* (importance), σ (non-linearity)
- **Model Outputs**: Peak infections, time to peak, total cases, final recovered

---

