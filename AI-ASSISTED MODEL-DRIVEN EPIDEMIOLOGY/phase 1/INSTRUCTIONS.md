# Phase 1: Instructions

## Quick Start

### Run All Analysis Tasks

```bash
cd "phase 1"
python3 run_phase1.py
```

This executes all 5 analysis tasks in sequence and generates comprehensive reports.

**Batch scope:** Tasks **1.1, 2.1, 2.2, and 2.3** each process **every** `*.compmodel` file in the **default model directory** (the first of `papers/epimde/`, then `phase 2/data/baseline_models/`, then legacy paths, that contains at least one `.compmodel` — see `utils/phase1_paths.py`). Put new reference models in `papers/epimde/` so they are included. For gap analysis, a matching PDF is searched next to the model file and under `papers/` (see `find_pdf_for_compmodel`).

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

# Static knowledge base (not recreated by run_phase1.py)
ls reports/protocols/ reports/taxonomies/ reports/patterns/ reports/manual_extraction/
```

**What each folder contains:** see **[`reports/REPORTS.md`](reports/REPORTS.md)** — catalog of outputs, static vs generated, and how Phase 3 consumes them.

---

## Analysis Tasks

### Task 1.1: Model Analysis

**Script:** `analysis/model_analyzer.py`

**Purpose:** Analyzes the structure of existing `.compmodel` files to extract all components.

**✅ Fully Generalizable:** Works with any `.compmodel` file regardless of disease type or model structure.

#### Input

**Automatic (no user input needed):**
- Searches **`papers/epimde/`** first (then Phase 2 `baseline_models/`, then optional legacy `Compartmental/CompartmentalModel`) for all `.compmodel` files
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

#### Validity (threats) and comparing methods

- **LLM / pattern quality is not guaranteed** — promises are only as good as the extractor. For a rigorous study, select a few papers and **manually** code “what the paper claims” (gold set), then compare **manual vs pattern-based vs LLM-based** overlap (e.g. compartments/parameters). The pipeline does not automate that triad; it supports pattern vs LLM modes.
- **Manual** reference labels are assumed to come from you (spreadsheet or sidecar JSON), not from a separate built-in UI.

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

### Task 2.2: Parameter uncertainty quantification

**Script:** `analysis/uncertainty_analyzer.py`

**Purpose:** Documents **all explicit parameters** in the `.compmodel` and creates a framework for **parameter-value** uncertainty (numeric values, literature ranges, confidence labels). This is **not** a full structural or stochastic uncertainty analysis unless you extend the code.

**✅ Fully Generalizable:** Works with any `.compmodel` file. Extracts all parameters regardless of disease type.

#### Input

**Automatic (no user input needed):**
- Extracts parameters from `.compmodel` files
- Works for: COVID-19, malaria, HIV, and any new epidemiological model

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

#### Confidence levels (why most are often **Unknown**)

The implementation assigns **High / Medium / Low** only for a **small set** of **disease-specific heuristics** (e.g. COVID-19, Malaria, HIV) based on **parameter name** substrings. **All other parameters** default to **Unknown**.

**Unknown** does *not* mean “the parameter is unknowable”; it means this tool has **not** linked the value to **papers, tables, or expert review**. Raising confidence requires **you** to add literature references (`papers` / ranges) or extend `get_parameter_uncertainty()` with curated rules.

- **High**: Intended meaning — value supported by multiple sources, narrow range (heuristic only when matched)
- **Medium**: Single source or wider range (heuristic)
- **Low**: Rough / context-dependent (heuristic)
- **Unknown**: Default when no heuristic matched — **expected for most parameters** until literature is attached

Each exported JSON includes a top-level **`methodologyNotes`** string explaining this.

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
analyzer.export_uncertainty_database("output.json")
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

Each method below is a standard tool in **global sensitivity analysis (GSA)**. Citations are for the *methodology*; they are independent of any particular disease result.

##### 1. Morris method (elementary effects) — default / screening

**What it does:** Morris (1991) proposes **one-at-a-time** “elementary effects” along randomized trajectories in the input space. For each parameter, the mean absolute elementary effect **μ\*** ranks **importance** (screening); the standard deviation of effects **σ** indicates **nonlinearity** and some **interaction** structure. This implementation applies that logic to parameters driving the compartmental simulator.

**Benefits:** Very **efficient** when there are **many** uncertain parameters; widely cited; good first pass to drop unimportant inputs before expensive calibration.

**Disadvantages:** Not a full **variance-based decomposition** (unlike Sobol’ indices); interactions are only partially reflected (mainly via σ); scaling of μ\* depends on output scaling.

**Reference:** Morris, M. D. (1991). Factorial sampling plans for preliminary computational experiments. *Technometrics*, 33(2), 161–174.

##### 2. Grid search

**What it does:** Evaluates the model on a **regular grid** (factorial or lattice) over one or a few parameters, or a reduced grid when the implementation enumerates combinations. Gives **complete coverage** of the chosen discrete design.

**Benefits:** **Transparent** and **deterministic**; easy to explain; optimal when you genuinely only care about a **small** number of discrete levels per parameter.

**Disadvantages:** **Curse of dimensionality** — cost grows exponentially with the number of parameters; rarely feasible for full models with dozens of rates.

**Reference:** Discussed in the GSA literature as brute-force exploration; see Saltelli et al. (2008), *Global Sensitivity Analysis: The Primer*, Wiley (Chapters on sampling designs).

##### 3. Random (Monte Carlo) sampling

**What it does:** Draws **independent random** points in the parameter space (uniform or as implemented), runs the simulator, and summarizes output variation. This is the simplest **Monte Carlo** exploration of input–output behavior.

**Benefits:** **Flexible**, trivially parallelizable, no grid structure; works when you only need rough **importance ordering** or variance of outputs.

**Disadvantages:** In high dimensions, **many** runs may be needed for stable estimates; **pure random** points can clump and leave holes compared to quasi-random designs.

**Reference:** Saltelli et al. (2008), *Global Sensitivity Analysis: The Primer*; standard Monte Carlo variance estimation.

##### 4. Sobol sequences (quasi-random)

**What it does:** In this codebase, **`sobol`** uses **scipy**’s **Sobol’ low-discrepancy** quasi-random sampler (`scipy.stats.qmc.Sobol`) to generate **space-filling** parameter vectors, then runs the simulator on those points—i.e. **quasi-Monte Carlo sampling** of the input space. That is **not** the same as computing full **Sobol’ variance-based sensitivity indices** (first-order / total-order), though those indices are *defined* using similar sequence ideas in the literature (see Saltelli et al., 2010).

**Benefits:** **Better coverage** than IID random for a given sample size; standard choice for quasi-random exploration.

**Disadvantages:** Still needs enough runs in high dimension; if you require **variance decomposition indices**, you must extend the analyzer; **scipy** is required (otherwise the script falls back to random sampling).

**References:** Sobol’, I. M. (1993). Sensitivity estimates for nonlinear mathematical models. *Mathematical Modelling and Computational Experiment*, 1(4), 407–414. For variance-based *indices*: Saltelli, A., et al. (2010). Variance based sensitivity analysis of model output: Design and estimator for the total sensitivity index. *Computer Physics Communications*, 181(2), 259–270.

##### Bibliography (methods)

- Morris, M. D. (1991). *Technometrics*, 33(2), 161–174.
- Sobol’, I. M. (1993). *Mathematical Modelling and Computational Experiment*, 1(4), 407–414.
- Saltelli, A., Ratto, M., Andres, T., Campolongo, F., Cariboni, J., Gatelli, D., Saisana, M., & Tarantola, S. (2008). *Global Sensitivity Analysis: The Primer*. Wiley.
- Saltelli, A., et al. (2010). *Computer Physics Communications*, 181(2), 259–270.

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

#### Methodology: which parameters are included and how they are “typed”

There is **no machine-learning** step. The pipeline is **deterministic**:

1. **Explicit parameters** — Every `<parameters>` entry with a numeric expression is considered (unless filtered by the analyzer).
2. **Flow-embedded values** — Flows may carry `rate`, `contactRate`, `rateParameter`, `contactRateParameter`, or stratum-specific rates; the code builds a **descriptive name** from source/target compartment names (e.g. `contactRate_Susceptible_to_Exposed`).
3. **Type classification** — `_classify_parameter_type()` uses **substring rules** on the parameter name: tokens like `beta`, `contact`, `transmission` → `transmission`; `gamma`, `recovery` → `recovery`; `sigma`, `incubation`, `rho` → `progression`; `mortality`, `death`, `mu` → `mortality`; else `other`.

That is **heuristic keyword mapping**, not “intelligence” in the ML sense. It helps group and filter parameters for Morris and reporting.

#### Auto-scaled variation ranges

- Very small parameters (< 0.001): ±200% variation
- Small parameters (< 0.01): ±100% variation
- Normal parameters: ±50% variation
- Ensures meaningful sensitivity for all parameter scales

#### Actual model dynamics (simulation)

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
- No disease-specific logic in the simulator core
- Parameter **discovery** is structural (XML); **type labels** use **heuristic** name rules (see “Methodology” above), not ML
- Adapts to many parameter scales via auto-scaled perturbation ranges
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

---

### Task 3.1: Paper Collection Management

**Script:** `analysis/paper_collection.py`

**Purpose:** Maintains a structured database of epidemiological papers with metadata.

**✅ Fully Generalizable:** Supports any disease type and unlimited number of papers. Extensible metadata structure.

#### Input

**Automatic workflow when you run `python3 analysis/paper_collection.py`:**

1. **Load** `data/papers/collection_index.json` if it exists (otherwise start empty).
2. If the index was **empty**, seed **3 curated example** rows (COVID-19 / Malaria / HIV with Tuite / Akowe / Espitia notes).
3. **`sync_epimde_reference_papers()`** — for every `*.compmodel` in **`papers/epimde/`**, if that **disease name** is not already in the index, **append** one row (placeholder title/authors; **pdfPath** set when a matching PDF exists next to the model or under `papers/`). So the collection stays aligned with your gold reference models (e.g. 10 diseases → up to 10 rows after sync).

Re-run the script after adding new `.compmodel` files to `papers/epimde/` to pick up new diseases.

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
    notes="Key observations",
    venue="Journal of X",
    venue_type="journal",
    bibtex="@article{key, ...}",  # paste from Google Scholar / PubMed / Zotero
)
```

#### What it tracks

For each paper:
- **Paper ID**: Unique identifier (e.g., `malaria_2025_1`)
- **Title, Authors, Year**: Basic metadata
- **Venue** (`venue`): Journal title, conference name, book title, etc.
- **Venue type** (`venueType`): `journal` | `conference` | `book` | `preprint` | `other` | `unknown`
- **BibTeX** (`bibtex`): Optional full BibTeX record (export from Scholar, PubMed, Crossref, reference manager)
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
    model_path="papers/epimde/covid.compmodel",
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
    model_path="papers/epimde/covid.compmodel",
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

models_dir = Path("papers/epimde")
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

Primary epidemiology benchmarks ship with Phase 1 under **`papers/epimde/`** (e.g. `covid.compmodel`, `malaria.compmodel`, `HIV.compmodel` and matching PDFs). Additional baselines used by Phase 2 live in **`../phase 2/data/baseline_models/`**. An optional Eclipse workspace under **`../Compartmental/CompartmentalModel/`** may contain extra tooling; this project’s scope is **epidemiology only**.

### Directory Structure

```
phase 1/
├── README.md                       # This file
├── metamodel_epidemiology.json     # Epidemiology metamodel (shared with Phase 2)
├── run_phase1.py                   # Main task runner
├── requirements.txt                # Python dependencies
│
├── analysis/                       # Core analysis scripts
│   ├── model_analyzer.py           # Task 1.1: Model structure
│   ├── gap_analyzer.py             # Task 2.1: Gap identification
│   ├── uncertainty_analyzer.py     # Task 2.2: Parameter uncertainty quantification
│   ├── sensitivity_analysis.py     # Task 2.3: Sensitivity analysis
│   ├── paper_collection.py         # Task 3.1: Paper management
│   ├── paper_promise_extractor.py  # Paper promise extraction
│   └── example_phase2_usage.py     # Phase 2 examples
│
├── utils/
│   ├── xml_parser.py               # XML parsing utilities
│   └── phase1_paths.py             # Canonical paths (epimde, Phase 2 baselines, legacy)
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
cd "phase 1"
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



---

## Appendix: Phase 1 results summary (reference)



## Executive Summary

Phase 1 analysis has been completed for three epidemiological models: COVID-19, Malaria, and HIV. This comprehensive analysis includes:

- **Model Structure Analysis**: Extraction and documentation of compartments, flows, parameters, and stratifications
- **Gap Analysis**: Comparison of models against paper promises (paper-driven analysis)
- **Parameter uncertainty quantification**: Parameter values, literature-range templates, confidence labels
- **Sensitivity Analysis**: Morris method sensitivity analysis to identify critical parameters
- **Paper Collection**: Management of research papers and model linkages

**Key Findings:**
- **3 primary models analyzed**: 
  - COVID-19: 15 compartments, age-stratified (3 age groups), population 14.8M
  - Malaria: 7 compartments (including vector), 17 explicit parameters, population 10.9M
  - HIV: 5 compartments, age-stratified, population 362K
- **21 total gaps identified** across all models:
  - 11 critical gaps (missing compartments/stratifications/parameters)
  - 10 medium gaps (missing interventions)
  - 0 low gaps
- **100% of gaps** are from paper promises (paper-driven analysis, not best-practice suggestions)
- **Sensitivity analysis completed** for all three models using Morris method
- **Parameter extraction successful** from both explicit parameters (Malaria) and flows (COVID-19, HIV)

---

## 1. Model Analysis Overview

### 1.1 Model Characteristics

| Model | Compartments | Parameters | Stratification | Vector Compartments | Temperature Dependent | Population |
|-------|--------------|------------|----------------|---------------------|----------------------|------------|
| **COVID-19** | 15 | 0 (rates in flows) | Yes (Age) | No | No | 14,800,000 |
| **Malaria** | 7 | 17 | No | Yes | Yes | 10,933,352 |
| **HIV** | 5 | 0 (rates in flows) | Yes (Age) | No | No | 362,796 |

### 1.2 Model Details

#### COVID-19 Model
- **Structure**: SEIR-like with age stratification (0-17, 18-64, 65+)
- **Key Features**:
  - 15 compartments including Susceptible, Exposed, Infectious, Recovered, ICU, Death compartments
  - Age-stratified transmission rates
  - Quarantine flow paths
  - No explicit parameters (rates embedded in flows)
- **Compartments**: Susceptible, Exposed, Quarantined Exposed, Infectious, Recovered, ICU, COVID Deaths, etc.

#### Malaria Model
- **Structure**: SEIR with vector-borne transmission
- **Key Features**:
  - 7 compartments (Human: Susceptible, Exposed, Infectious, Recovered; Mosquito: Susceptible, Exposed, Infectious)
  - 17 explicit parameters (αₕ, β₁, γₕ, μₕ, ρₕ, q, etc.)
  - Temperature-dependent transmission
  - No stratification (single population model)
- **Compartments**: Susceptible Humans, Exposed Humans, Infectious Humans, Recovered Humans, Susceptible Mosquitoes, Exposed Mosquitoes, Infectious Mosquitoes

#### HIV Model
- **Structure**: SIR-like with age/gender stratification
- **Key Features**:
  - 5 compartments: Susceptible, Infectious, Treated, Death compartments
  - Age-stratified (similar to COVID-19 structure)
  - No explicit parameters (rates in flows)
  - Multiple external sources and sinks
- **Compartments**: Susceptible, Infectious, Treated, HIV Deaths, Natural Deaths

---

## 2. Gap Analysis Results

### 2.1 Overall Gap Summary

| Model | Total Gaps | Critical | Medium | Low | Promised by Paper |
|-------|------------|---------|--------|-----|-------------------|
| **COVID-19** | 7 | 4 | 3 | 0 | 7 |
| **Malaria** | 8 | 5 | 3 | 0 | 8 |
| **HIV** | 6 | 2 | 4 | 0 | 6 |
| **Total** | **21** | **11** | **10** | **0** | **21** |

**Method**: Paper-driven gap analysis (pattern-based extraction from papers)

### 2.2 Gap Types

| Gap Type | COVID-19 | Malaria | HIV | Total |
|----------|----------|---------|-----|-------|
| **Structural** (Compartments) | 2 | 5 | 1 | 8 |
| **Parameters** | 2 | 0 | 1 | 3 |
| **Stratification** | 0 | 1 | 1 | 2 |
| **Interventions** | 3 | 2 | 3 | 8 |

### 2.3 COVID-19 Gap Analysis

**Paper Promises Extracted (Pattern-Based):**
- **Compartments**: Infectious, Exposed, Asymptomatic, Susceptible, Recovered, ICU, Severe
- **Stratifications**: Age
- **Parameters**: mortality_rate, contact_rate
- **Interventions**: quarantine, contact_tracing, vaccination
- **Extraction Note**: "Pattern-based extraction from paper text. Found 7 compartments, 1 stratifications, 2 parameters, 3 interventions."

**Actual Model Has:**
- **Compartments (15 total)**: Susceptible, Exposed, Quarantined Exposed, Infectious, Recovered, ICU, COVID Deaths, and others
- **Stratifications**: Age (0-17, 18-64, 65+) ✅ **MATCHES PAPER**
- **Parameters**: 0 explicit parameters (rates embedded in flows)
- **Interventions**: Quarantined Exposed compartment exists (may match quarantine)

**Critical Gaps (4 total):**

1. **Missing Promised Compartment: Asymptomatic**
   - **Severity**: High
   - **Description**: Paper promises Asymptomatic compartment but model does not include it
   - **Action**: Add compartment: Asymptomatic

2. **Missing Promised Compartment: Severe**
   - **Severity**: High
   - **Description**: Paper promises Severe compartment but model does not include it
   - **Action**: Add compartment: Severe

3. **Missing Promised Parameter: mortality_rate**
   - **Severity**: High
   - **Description**: Paper promises mortality_rate parameter but model does not include it
   - **Action**: Extract mortality_rate from flows or add as explicit parameter
   - **Note**: Model has mortality flows but not as explicit parameter

4. **Missing Promised Parameter: contact_rate**
   - **Severity**: High
   - **Description**: Paper promises contact_rate parameter but model does not include it
   - **Action**: Extract contact_rate from flows or add as explicit parameter
   - **Note**: Model has `contactRate` attributes in flows (e.g., 0.00001) but not as explicit parameter

**Medium Gaps (3 total):**

1. **Missing Promised Intervention: quarantine**
   - **Severity**: Medium
   - **Description**: Paper promises quarantine intervention but model does not include it
   - **Action**: Add intervention: quarantine
   - **Note**: Model has "Quarantined Exposed" compartment - may be conceptual mismatch

2. **Missing Promised Intervention: contact_tracing**
   - **Severity**: Medium
   - **Description**: Paper promises contact_tracing intervention but model does not include it
   - **Action**: Add intervention: contact_tracing

3. **Missing Promised Intervention: vaccination**
   - **Severity**: Medium
   - **Description**: Paper promises vaccination intervention but model does not include it
   - **Action**: Add intervention: vaccination

**Summary:**
- **Total Gaps**: 7 (4 critical, 3 medium, 0 low)
- **Promised by Paper**: 7/7 gaps are from paper promises (100%)
- **Age Stratification**: ✅ Present in model (matches paper)
- **Key Finding**: Model structure is close to paper but missing Asymptomatic and Severe compartments, and lacks explicit parameters

### 2.4 Malaria Gap Analysis

**Paper Promises Extracted (Pattern-Based):**
- **Compartments**: Infectious, Exposed, Vaccinated, Asymptomatic, Susceptible, Recovered, ICU, Severe
- **Stratifications**: Location
- **Parameters**: birth_rate, recovery_rate, contact_rate, mortality_rate
- **Interventions**: treatment, vaccination
- **Model Type**: SEIR
- **Extraction Note**: "Pattern-based extraction from paper text. Found 8 compartments, 1 stratifications, 4 parameters, 2 interventions."

**Actual Model Has:**
- **Compartments (7 total)**: Susceptible Humans, Exposed Humans, Infectious Humans, Recovered Humans, Susceptible Mosquitoes, Exposed Mosquitoes, Infectious Mosquitoes
- **Stratifications**: None (single population model)
- **Parameters**: 17 explicit parameters ✅ **HAS PARAMETERS** (αₕ, β₁, γₕ, μₕ, ρₕ, q, etc.)
- **Model Type**: SEIR with vector-borne transmission ✅
- **Special Features**: Vector compartments ✅, Temperature-dependent ✅

**Critical Gaps (5 total):**

1. **Missing Promised Compartment: Infectious**
   - **Severity**: High
   - **Description**: Paper promises Infectious compartment but model does not include it
   - **Action**: Add compartment: Infectious
   - **Note**: Model has "Infectious Humans" - may be naming/conceptual mismatch

2. **Missing Promised Compartment: Vaccinated**
   - **Severity**: High
   - **Description**: Paper promises Vaccinated compartment but model does not include it
   - **Action**: Add compartment: Vaccinated

3. **Missing Promised Compartment: Asymptomatic**
   - **Severity**: High
   - **Description**: Paper promises Asymptomatic compartment but model does not include it
   - **Action**: Add compartment: Asymptomatic

4. **Missing Promised Compartment: ICU**
   - **Severity**: High
   - **Description**: Paper promises ICU compartment but model does not include it
   - **Action**: Add compartment: ICU

5. **Missing Promised Stratification: location**
   - **Severity**: High
   - **Description**: Paper promises location stratification but model does not include it
   - **Action**: Add stratification dimension: location

**Medium Gaps (2 total):**

1. **Missing Promised Intervention: treatment**
   - **Severity**: Medium
   - **Description**: Paper promises treatment intervention but model does not include it
   - **Action**: Add intervention: treatment

2. **Missing Promised Intervention: vaccination**
   - **Severity**: Medium
   - **Description**: Paper promises vaccination intervention but model does not include it
   - **Action**: Add intervention: vaccination

**Summary:**
- **Total Gaps**: 8 (5 critical, 3 medium, 0 low)
- **Promised by Paper**: 8/8 gaps are from paper promises (100%)
- **Parameters**: ✅ Model has 17 explicit parameters (matches paper promise for parameters)
- **Vector Compartments**: ✅ Present (not in paper promises, but model feature)
- **Key Finding**: Model has correct structure (SEIR, vector-borne) but missing some compartments and location stratification that paper promises

### 2.5 HIV Gap Analysis

**Paper Promises Extracted (Pattern-Based):**
- **Compartments**: Exposed, Susceptible, Infectious
- **Stratifications**: Age
- **Parameters**: mortality_rate
- **Interventions**: treatment, vaccination, prep
- **Extraction Note**: "Pattern-based extraction from paper text. Found 3 compartments, 1 stratifications, 1 parameters, 3 interventions."

**Actual Model Has:**
- **Compartments (5 total)**: Susceptible, Infectious (Untreated Infected), Treated (with ART), Infectious (living with AIDS), HIV Deaths
- **Stratifications**: Has stratification (age/gender groups) ✅ **LIKELY MATCHES**
- **Parameters**: 0 explicit parameters (rates in flows)
- **Model Type**: SIR-like (not SEIR - no Exposed compartment)

**Critical Gaps (2 total):**

1. **Missing Promised Compartment: Exposed**
   - **Severity**: High
   - **Description**: Paper promises Exposed compartment but model does not include it
   - **Action**: Add compartment: Exposed
   - **Note**: Model is SIR (no latent period), not SEIR - this is a structural difference

2. **Missing Promised Stratification: age**
   - **Severity**: High
   - **Description**: Paper promises age stratification but model does not include it
   - **Action**: Add stratification dimension: age
   - **Note**: Model has stratification groups but may not match "age" naming - may be false positive

**Medium Gaps (3 total):**

1. **Missing Promised Intervention: treatment**
   - **Severity**: Medium
   - **Description**: Paper promises treatment intervention but model does not include it
   - **Action**: Add intervention: treatment
   - **Note**: Model has "Treated" compartment - likely conceptual match, may be false positive

2. **Missing Promised Intervention: vaccination**
   - **Severity**: Medium
   - **Description**: Paper promises vaccination intervention but model does not include it
   - **Action**: Add intervention: vaccination

3. **Missing Promised Intervention: prep**
   - **Severity**: Medium
   - **Description**: Paper promises prep (pre-exposure prophylaxis) intervention but model does not include it
   - **Action**: Add intervention: prep

**Missing Promised Parameter (1):**
- **mortality_rate**: Paper promises mortality_rate parameter but model does not include it (embedded in flows)

**Summary:**
- **Total Gaps**: 6 (2 critical, 4 medium, 0 low)
- **Promised by Paper**: 6/6 gaps are from paper promises (100%)
- **Model Type**: SIR (not SEIR) - structural difference from paper promise of Exposed compartment
- **Treated Compartment**: ✅ Present (may satisfy treatment intervention promise)
- **Key Finding**: Model has correct structure (Susceptible, Infectious, Treated) but missing Exposed compartment (SIR vs SEIR difference)

### 2.6 Gap Analysis Notes

**Important Considerations:**
- All gaps are based on **paper promises** (extracted from papers using pattern-based extraction)
- Some "gaps" may be **false positives** due to:
  - Naming mismatches (e.g., "Infectious Humans" vs "Infectious")
  - Conceptual differences (e.g., "Treated" compartment vs "treatment" intervention)
  - Pattern-based extraction limitations (may over-extract or miss nuances)
- For more accurate gap analysis, LLM-based extraction is recommended (requires OpenAI API key)
- Gap analysis is **faithful to papers**: only flags what papers promise, doesn't add literature-based components

---

## 3. Sensitivity Analysis Results

### 3.1 Method Used

All models were analyzed using the **Morris Method** (one-at-a-time sensitivity analysis):
- **Purpose**: Efficient screening to identify important parameters
- **Indices Calculated**:
  - **μ* (mu_star)**: Mean absolute elementary effect (importance) - higher = more important
  - **σ (sigma)**: Standard deviation (non-linearity/interactions) - higher = more non-linear

### 3.2 COVID-19 Sensitivity Analysis

**Method**: Morris Method  
**Trajectories**: 20  
**Variation Range**: ±50.0%  
**Parameters Analyzed**: 5 parameters (extracted from flows)

**Parameters Identified:**
1. `contactRate_Susceptible_to_Exposed` (current value: 6e-06) - Transmission rate
2. `rate_Infectious_stratum_0-17` (current value: 0.99) - Age-stratified rate
3. `rate_Infectious_stratum_18-64` (current value: 0.97) - Age-stratified rate
4. `rate_Admitted to hospital_stratum_0-17` (current value: 0.9) - Hospitalization rate
5. `rate_ICU_stratum_0-17` (current value: 0.85) - ICU admission rate

**Sensitivity Indices Results:**

| Parameter | μ (mean effect) | μ* (importance) | σ (non-linearity) | Interpretation |
|-----------|----------------|-----------------|-------------------|----------------|
| `contactRate_Susceptible_to_Exposed` | 15,910.55 | **15,910.55** | 0.069 | **Highly important**, low non-linearity |
| `rate_Infectious_stratum_0-17` | 0.0 | 0.0 | 0.0 | Not sensitive |
| `rate_Infectious_stratum_18-64` | 0.0 | 0.0 | 0.0 | Not sensitive |
| `rate_Admitted to hospital_stratum_0-17` | 0.0 | 0.0 | 0.0 | Not sensitive |
| `rate_ICU_stratum_0-17` | 0.0 | 0.0 | 0.0 | Not sensitive |

**Key Findings:**
- **Most Critical Parameter**: `contactRate_Susceptible_to_Exposed` has μ* = 15,910.55 (extremely high importance)
  - Small changes in this parameter cause large changes in model output
  - This is the transmission rate from Susceptible to Exposed
- **Other Parameters**: All other tested parameters showed zero sensitivity (may indicate they don't significantly affect outputs, or need different variation ranges)
- **Low Non-linearity**: σ = 0.069 for transmission rate indicates linear effects (no complex interactions)
- **Total Parameters Extracted**: 18 parameters identified in flows, but only 5 were analyzed in detail

**Recommendations:**
- Focus uncertainty quantification on `contactRate_Susceptible_to_Exposed` (most critical)
- Consider wider variation ranges or different methods for other parameters
- Parameter extraction successfully identified parameters from flow attributes (important for models without explicit parameters)

### 3.3 Malaria Sensitivity Analysis

**Method**: Morris Method  
**Trajectories**: 20  
**Variation Range**: ±50.0%  
**Parameters Analyzed**: 5 of 17 explicit parameters

**All Model Parameters (17 total):**
- **αₕ** (Recruitment rate): 15,129.0 people/time
- **q** (Progression Rh→Sh): 0.98765 1/time
- **β₁** (Transmission probability human): 0.9969 dimensionless
- **μₕ** (Natural mortality human): 0.001384 1/time
- **ρₕ** (Progression Eh→Ih): 0.001995 1/time
- **γₕ** (Recovery rate Ih→Rh): 0.120342 1/time
- **Nᵥ** (Mosquito population): 80,000.0
- **β₂** (Transmission probability mosquito): 0.9917 dimensionless
- **K** (Mosquito carrying capacity): 100,000.0
- Plus 8 more mosquito-related parameters

**Sensitivity Analysis Parameters (5 tested):**
1. **αₕ** (Recruitment rate): 15,129.0
2. **q** (Progression rate): 0.98765
3. **β₁** (Transmission probability): 0.9969
4. **μₕ** (Natural mortality): 0.001384
5. **ρₕ** (Progression rate): 0.001995

**Sensitivity Indices Results:**

| Parameter | μ (mean effect) | μ* (importance) | σ (non-linearity) | Interpretation |
|-----------|----------------|-----------------|-------------------|----------------|
| **αₕ** | 0.0 | 0.0 | 0.0 | Not sensitive in tested range |
| **q** | -6.51e-11 | 6.51e-11 | 3.31e-11 | Extremely low sensitivity |
| **β₁** | 0.0 | 0.0 | 0.0 | Not sensitive in tested range |
| **μₕ** | -1.10e-08 | 1.10e-08 | 4.39e-09 | Extremely low sensitivity |
| **ρₕ** | 0.0 | 0.0 | 0.0 | Not sensitive in tested range |

**Key Findings:**
- **All Tested Parameters Show Very Low Sensitivity**: All μ* values are near zero (e-11 to e-08)
  - Parameters may need different variation ranges
  - Or model outputs may be robust to these parameter variations
  - Or analysis may need different output metrics
- **Well-Documented Parameters**: All 17 parameters are explicit with descriptions ✅
- **Model Structure**: Vector-borne SEIR model with human and mosquito compartments

**Recommendations:**
- Test different variation ranges (current ±50% may not be appropriate for all parameters)
- Consider analyzing remaining 12 parameters (especially recovery rate γₕ and mosquito parameters)
- Test different output metrics (current analysis uses total cases, peak infections, time to peak)
- Model has good parameter structure (explicit parameters) - continue with literature review for parameter values

### 3.4 HIV Sensitivity Analysis

**Method**: Morris Method  
**Trajectories**: 20  
**Variation Range**: ±50.0%  
**Parameters Analyzed**: Parameters extracted from flows

**Model Characteristics:**
- Similar to COVID-19: parameters embedded in flows (no explicit parameters)
- Age-stratified transmission rates
- Treatment-related flows present

**Key Parameters Extracted from Flows:**
- Transmission rates (stratified by age/gender groups)
- Treatment rates
- Mortality rates
- Stratum-specific rates

**Analysis Status:**
- Sensitivity analysis completed using Morris method
- Parameters successfully extracted from flows (enables analysis despite no explicit parameters)
- Results identify which flow rates are most critical for model predictions

**Recommendations:**
- Similar to COVID-19: focus on transmission-related flow rates
- Treatment rates may also be important (model has Treated compartment)
- Consider extracting flows to explicit parameters for better documentation

---

## 4. Uncertainty Analysis Results

### 4.1 Parameter Uncertainty Tracking

All models have uncertainty analysis performed to track:
- **Parameter Values**: Current values in models
- **Confidence Levels**: High, medium, or low confidence in parameter values
- **Sources**: Where parameter values come from (paper, literature, estimated)
- **Literature Ranges**: Known ranges from literature (if available)

### 4.2 Key Findings

#### COVID-19 Uncertainty
- **Parameters**: Extracted from flows (contact rates, stratum-specific rates)
- **Confidence Levels**: Depends on flow rate sources (paper, literature, estimated)
- **Status**: Parameters embedded in flows make uncertainty tracking more challenging
- **Recommendation**: Extract rates to explicit parameters for better documentation

#### Malaria Uncertainty
- **Total Parameters**: 17 explicit parameters
- **All Parameters**: Currently marked as "Unknown" confidence
- **All Parameters**: Need literature search (no literature ranges yet documented)

**Example Malaria Parameters with Uncertainty Status:**

| Parameter | Value | Unit | Description | Confidence | Literature Range |
|-----------|-------|------|-------------|------------|------------------|
| **αₕ** | 15,129 | people/time | Recruitment rate of human populations | Unknown | Not searched |
| **q** | 0.98765 | 1/time | Progression rate of Rh to Sh | Unknown | Not searched |
| **β₁** | 0.9969 | dimensionless | Probability of transmission (human) | Unknown | Not searched |
| **μₕ** | 0.001384 | 1/time | Natural mortality rate of human population | Unknown | Not searched |
| **ρₕ** | 0.001995 | 1/time | Progression rate Eh to Ih | Unknown | Not searched |
| **γₕ** | 0.120342 | 1/time | Recovery rate Ih to Rh | Unknown | Not searched |
| *+ 11 more parameters* | | | | | |

**Key Finding**: All 17 Malaria parameters need literature review to:
- Establish confidence levels (High/Medium/Low)
- Document literature ranges (min, max, mean, std)
- Identify source papers
- Track parameter uncertainty

#### HIV Uncertainty
- **Parameters**: Extracted from flows (similar to COVID-19)
- **Confidence Levels**: Depends on flow rate sources
- **Status**: Parameters in flows make uncertainty tracking challenging

**Recommendations:**
1. **Priority**: Malaria parameters (17 explicit parameters) - all need literature review
2. **COVID-19/HIV**: Extract flow rates to explicit parameters for better uncertainty tracking
3. **Literature Search**: Identify ranges for all parameters to establish confidence levels
4. **Parameter Sources**: Track where each parameter value comes from (paper, literature, estimated)

---

## 5. Paper Collection

### 5.1 Papers Analyzed

| Disease | Paper | Year | Model |
|---------|-------|------|-------|
| **COVID-19** | Tuite et al. | 2020 | Age-stratified COVID-19 model |
| **Malaria** | Akowe et al. | 2025 | Malaria transmission with dual pathways |
| **HIV** | Espitia et al. | 2022 | HIV transmission with sexual behavior stratification |

### 5.2 Paper-Model Linkages

All three papers have:
- ✅ Corresponding `.compmodel` files extracted
- ✅ Paper PDFs available for gap analysis
- ✅ Metadata stored in paper collection database

**Location:**
- Models: `phase1/papers/epimde/` (covid.compmodel, malaria.compmodel, HIV.compmodel)
- Papers: `phase1/papers/epimde/` (covid.pdf, malaria.pdf, hiv.pdf)

---

## 6. Key Insights and Recommendations

### 6.1 Model Quality

**Strengths:**
- All models are structurally sound (valid `.compmodel` format)
- COVID-19 and HIV models use age stratification (important for disease dynamics)
- Malaria model has explicit parameters (best practice)
- All models have corresponding papers for validation

**Areas for Improvement:**
- **Parameter Definition**: COVID-19 and HIV models embed rates in flows rather than explicit parameters
  - **Recommendation**: Extract rates to explicit parameters for better documentation and uncertainty tracking
- **Gap Closure**: Some paper-promised components are missing (see Gap Analysis)
  - **Recommendation**: Review gaps and add missing components if they align with model objectives
- **Naming Consistency**: Some naming mismatches between papers and models
  - **Recommendation**: Standardize naming to match paper descriptions

### 6.2 Gap Analysis Quality

**Strengths:**
- Paper-driven analysis (faithful to papers)
- Identifies genuine gaps (missing promised components)
- Provides actionable recommendations (how to add missing components)

**Limitations:**
- Pattern-based extraction may have false positives/negatives
- Some gaps may be conceptual mismatches rather than actual gaps
- LLM-based extraction (recommended) requires API key setup

**Recommendations:**
- Use LLM-based extraction for more accurate promise extraction
- Manually verify extracted promises before gap analysis
- Review gaps in context (some may be acceptable design choices)

### 6.3 Sensitivity Analysis Quality

**Strengths:**
- Morris method is efficient and appropriate for screening
- Parameter extraction works for both explicit parameters and flow-based rates
- Identifies critical parameters for model refinement

**Recommendations:**
- For detailed analysis, consider Grid Search (if < 5 parameters) or Random Search (if > 5 parameters)
- For global sensitivity, consider Sobol sequences (requires scipy)
- Use sensitivity results to prioritize parameter refinement

---

## 7. Phase 1 Completeness

### 7.1 Completed Tasks

✅ **Task 1.1: Analyze Current Models**
- All three models analyzed (COVID-19, Malaria, HIV)
- Structure extracted: compartments, flows, parameters, stratifications
- Reports generated: `reports/model_analysis/`

✅ **Task 2.1: Gap Analysis**
- Paper-driven gap analysis completed for all three models
- Paper promises extracted (pattern-based)
- Gap reports generated: `reports/gap_reports/`

✅ **Task 2.2: Uncertainty Analysis**
- Parameter uncertainty tracked for all models
- Reports generated: `reports/uncertainty/`

✅ **Task 2.3: Sensitivity Analysis**
- Morris method sensitivity analysis completed
- Reports generated: `reports/sensitivity/`

✅ **Task 3.1: Paper Collection**
- Paper collection initialized with 3 papers
- Model-paper linkages established
- Reports generated: `reports/paper_collection/`

### 7.2 Phase 1 Status

**Phase 1 is COMPLETE and ready for Phase 2**

- ✅ All tasks completed
- ✅ All reports generated
- ✅ Generic and extensible (works with any models/papers)
- ✅ Paper-driven gap analysis implemented (Phase 2 ready)

**Next Steps for Phase 2:**
- Automated model extraction from papers (LLM-based)
- Enhanced paper promise extraction (LLM-based for accuracy)
- Model generation from paper text
- Validation using gap analysis

---

## 8. Complete Report Inventory

This section documents ALL reports generated during Phase 1 analysis and explains what each contains.

### 8.1 Model Analysis Reports

**Location:** `reports/model_analysis/`

#### Primary Models (Analyzed in Detail):
1. **`covid_19_analysis.json`**
   - **Model**: COVID-19 age-stratified model
   - **Content**: Full structure (15 compartments, flows, stratifications)
   - **Population**: 14,800,000
   - **Key Features**: Age stratification (0-17, 18-64, 65+), no explicit parameters (rates in flows)

2. **`malaria_analysis.json`**
   - **Model**: Malaria vector-borne transmission model
   - **Content**: Full structure (7 compartments, 17 parameters, vector compartments)
   - **Population**: 10,933,352
   - **Key Features**: Vector compartments (mosquitoes), temperature-dependent, explicit parameters

3. **`hiv_analysis.json`**
   - **Model**: HIV transmission model
   - **Content**: Full structure (5 compartments, flows, stratifications)
   - **Population**: 362,796
   - **Key Features**: Age/gender stratification, no explicit parameters (rates in flows)

#### HIV Sub-Population Models:
4. **`hiv_heterosexual_men_analysis.json`**
   - **Model**: HIV model for heterosexual men sub-population
   - **Content**: Model structure for specific risk group
   - **Note**: Derived from main HIV model with stratification

5. **`hiv_homosexual_men_analysis.json`**
   - **Model**: HIV model for homosexual men sub-population
   - **Content**: Model structure for specific risk group
   - **Note**: Derived from main HIV model with stratification

6. **`hiv_women_analysis.json`**
   - **Model**: HIV model for women sub-population
   - **Content**: Model structure for specific risk group
   - **Note**: Derived from main HIV model with stratification

#### Other Models (Analyzed but Not Primary Focus):
7. **`sample_analysis.json`**
   - **Model**: Sample/template model
   - **Content**: Example model structure for reference
   - **Usage**: Template for understanding model structure

#### Combined Analysis:
8. **`all_models_analysis.json`**
    - **Content**: Combined data from all models
    - **Format**: Array of all compartment/flow/parameter data
    - **Usage**: Cross-model comparison and analysis

9. **`all_models_analysis.csv`**
    - **Content**: Tabular format of combined analysis (epidemiology models only)
    - **Usage**: Spreadsheet analysis, data export
    - **Note**: Regenerate Excel with `analysis/model_analyzer.py` if needed (`all_models_analysis.xlsx` when pandas/openpyxl are installed)

10. **`summary_report.txt`**
    - **Content**: Text summary of all models
    - **Usage**: Quick reference for model characteristics

### 8.2 Gap Analysis Reports

**Location:** `reports/gap_reports/`

1. **`covid_19_gap_analysis.json`**
   - **Content**: Gap analysis comparing COVID-19 model to paper promises
   - **Gaps Found**: 7 total (4 critical, 3 medium)
   - **Paper Promises Extracted**: 7 compartments, 1 stratification, 2 parameters, 3 interventions
   - **Key Gaps**: Missing Asymptomatic compartment, Severe compartment, mortality_rate/contact_rate parameters

2. **`malaria_gap_analysis.json`**
   - **Content**: Gap analysis comparing Malaria model to paper promises
   - **Gaps Found**: 8 total (5 critical, 3 medium)
   - **Paper Promises Extracted**: 8 compartments, 1 stratification, 4 parameters, 2 interventions
   - **Key Gaps**: Missing Infectious/Vaccinated/Asymptomatic/ICU/Severe compartments, location stratification

3. **`hiv_gap_analysis.json`**
   - **Content**: Gap analysis comparing HIV model to paper promises
   - **Gaps Found**: 6 total (2 critical, 4 medium)
   - **Paper Promises Extracted**: 3 compartments, 1 stratification, 1 parameter, 3 interventions
   - **Key Gaps**: Missing Exposed compartment, age stratification, mortality_rate parameter

4. **`all_models_gap_summary.json`**
   - **Content**: Combined summary of all gap analyses
   - **Total Gaps**: 21 across all models (11 critical, 10 medium)
   - **Breakdown**: By model, by type (structural/parameter/stratification/intervention), by severity

### 8.3 Sensitivity Analysis Reports

**Location:** `reports/sensitivity/`

All reports use **Morris Method** (one-at-a-time sensitivity analysis):

1. **`covid_19_sensitivity_morris.json`**
   - **Method**: Morris (default)
   - **Parameters Analyzed**: 18 (extracted from flows)
   - **Key Findings**: `contactRate_Susceptible_to_Exposed` is most sensitive parameter
   - **Sensitivity Indices**: μ* (importance) and σ (non-linearity) for each parameter

2. **`malaria_sensitivity_morris.json`**
   - **Method**: Morris
   - **Parameters Analyzed**: 17 explicit parameters
   - **Key Findings**: Recovery (γₕ) and recruitment (αₕ) rates are most sensitive
   - **Sensitivity Indices**: μ* and σ for all 17 parameters

3. **`hiv_sensitivity_morris.json`**
   - **Method**: Morris
   - **Parameters Analyzed**: Parameters extracted from flows
   - **Key Findings**: Transmission and treatment rates are most sensitive
   - **Sensitivity Indices**: μ* and σ for extracted parameters

**Note**: All sensitivity analyses extract parameters from both explicit parameters AND flows (for models like COVID-19 and HIV that embed rates in flows).

### 8.4 Uncertainty Analysis Reports

**Location:** `reports/uncertainty/`

1. **`covid_19_uncertainty.json`**
   - **Content**: Parameter uncertainty tracking for COVID-19 model
   - **Parameters**: Extracted from flows (rates, contact rates)
   - **Confidence Levels**: Tracked for each parameter
   - **Sources**: Identified where parameter values come from

2. **`malaria_uncertainty.json`**
   - **Content**: Parameter uncertainty tracking for Malaria model
   - **Parameters**: 17 explicit parameters (αₕ, β₁, γₕ, μₕ, ρₕ, q, etc.)
   - **Confidence Levels**: Tracked for each parameter
   - **Literature Ranges**: Where available, literature ranges are noted
   - **Sources**: Model file, literature, estimated

3. **`hiv_uncertainty.json`**
   - **Content**: Parameter uncertainty tracking for HIV model
   - **Parameters**: Extracted from flows
   - **Confidence Levels**: Tracked for each parameter
   - **Sources**: Identified for each parameter

4. **`all_models_uncertainty.json`**
   - **Content**: Combined uncertainty database for all models
   - **Format**: Array of all parameters with uncertainty metadata
   - **Usage**: Cross-model parameter comparison, identifying parameters needing literature review

### 8.5 Paper Collection Report

**Location:** `reports/paper_collection/`

1. **`paper_collection.json`**
   - **Content**: Metadata for all papers analyzed
   - **Total Papers**: 3
   - **Papers Included**:
     - COVID-19: Tuite et al. (2020) - Age-stratified COVID-19 model
     - Malaria: Akowe et al. (2025) - Malaria transmission with dual pathways
     - HIV: Espitia et al. (2022) - HIV transmission with sexual behavior stratification
   - **Linkages**: Links papers to their corresponding `.compmodel` files
   - **Metadata**: Title, authors, year, disease, notes, added date

### 8.6 Framework and Protocol Reports (Static)

These are foundational reports that define the extraction and analysis framework:

#### **Extraction Protocol**
**Location:** `reports/protocols/extraction_protocol.json`
- **Content**: 8-step protocol for building models from research papers
- **Steps**: 
  1. Initial Paper Reading
  2. Locate Model Description
  3. Identify Compartments
  4. Map Flows
  5. Extract Parameters
  6. Handle Stratification
  7. Document Interventions
  8. Validate Model
- **Usage**: Guide for manual extraction, reference for automated extraction

#### **Required vs Optional Rules**
**Location:** `reports/protocols/required_optional.json`
- **Content**: Classification of model components as required vs optional
- **Required**: Essential components (compartments, basic flows)
- **Optional**: Enhancements (stratification, interventions, advanced features)
- **Usage**: Determines which gaps are critical vs acceptable

#### **Taxonomies**
**Location:** `reports/taxonomies/taxonomies.json`
- **Content**: Classification systems for model components
- **Includes**:
  - **Compartment Types**: Disease states (S, E, I, R), intervention states (V, Q, T)
  - **Flow Types**: ContactFlow, RateFlow, ExternalSource, ExternalSink
  - **Parameter Types**: Transmission (β), Recovery (γ), Mortality (μ)
  - **Stratification Types**: Age, Gender, Risk, Location, Vaccination Status
- **Note**: Marked as **extensible examples** - new types can be added
- **Usage**: Reference for identifying and classifying model components

#### **Pattern Library**
**Location:** `reports/patterns/pattern_library.json`
- **Content**: Common modeling patterns across diseases
- **Patterns Included**:
  - Standard SEIR
  - SEIR with Hospitalization
  - Vector-Borne (Malaria-like)
  - Age-Stratified SEIR
  - SIR with Treatment
  - And more...
- **Note**: Marked as **example structures** - new papers may contain unlisted patterns
- **Usage**: Pattern matching, validation, classification during extraction

#### **Manual Extraction Templates**
**Location:** `reports/manual_extraction/`

1. **`extraction_template.json`**
   - **Content**: Template structure for manual model extraction
   - **Usage**: Guide for manually extracting models from papers
   - **Fields**: Compartments, flows, parameters, stratifications, interventions

2. **`extraction_example.json`**
   - **Content**: Example of filled extraction template
   - **Usage**: Reference for how to fill extraction template
   - **Shows**: Proper structure and detail level

---

## 9. Technical Details

### 9.1 Analysis Tools

- **Model Analyzer**: Generic XML parser for `.compmodel` files
- **Gap Analyzer**: Paper-driven comparison using `PaperPromiseExtractor`
- **Sensitivity Analyzer**: Morris method with parameter extraction from flows
- **Uncertainty Analyzer**: Parameter tracking and confidence assessment
- **Paper Collection**: Metadata management system

### 9.2 Report Organization

**Directory Structure:**
```
reports/
├── model_analysis/       # Model structure reports (9 models + combined)
├── gap_reports/          # Gap analysis reports (3 models + summary)
├── sensitivity/          # Sensitivity analysis reports (3 models, Morris method)
├── uncertainty/          # Uncertainty tracking reports (3 models + combined)
├── paper_collection/     # Paper metadata (synced with epimde + manual entries)
├── protocols/            # Extraction protocol and rules (static)
├── taxonomies/           # Classification systems (static, extensible)
├── patterns/             # Common patterns library (static, extensible)
└── manual_extraction/    # Extraction templates and examples (static)
```

### 9.3 Output Formats

- **Primary Format**: JSON (structured, machine-readable)
- **Alternative Formats**: 
  - CSV/Excel for model analysis (spreadsheet analysis)
  - TXT summary reports (human-readable)
- **All Reports**: Include metadata (analysis date, model name, version)

### 9.4 Dependencies

- **Required**: Python 3.x, lxml/xml.etree
- **Recommended**: 
  - `pdfplumber` (for PDF text extraction in gap analysis)
  - `numpy` (for sensitivity analysis calculations)
  - `scipy` (for Sobol sequences in sensitivity analysis)
  - `openai` (optional, for LLM-based paper promise extraction)

