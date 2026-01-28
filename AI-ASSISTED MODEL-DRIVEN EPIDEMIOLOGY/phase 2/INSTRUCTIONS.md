# Phase 2: Step-by-Step Instructions

Complete guide to running Phase 2 with detailed inputs and outputs for each step.

## Quick Start

1. **Set up API key** → `.api_key.txt`
2. **Install dependencies** → `pip install -r ../requirements.txt`
3. **Put paper in** → `data/papers/`
4. **Run** → `python3 run_phase2.py --paper data/papers/your_paper.pdf --output reports`
5. **Check results** → open the printed output folder and check `phase2_final_report.json`

---

## Detailed Setup

### Step 1: Set Up API Key

You can use either **OpenAI** or **Google Gemini** as your LLM provider.

**File:** `phase 2/.api_key.txt`

#### Option A: OpenAI (Default)

1. Get your OpenAI API key from [OpenAI Platform](https://platform.openai.com/api-keys)
2. Open `.api_key.txt` in the `phase 2` directory
3. Add your OpenAI API key on a new line (without quotes)
4. Save the file

Example:
```
sk-your-actual-api-key-here
```

**Alternative:** Set environment variable:
```bash
export OPENAI_API_KEY="sk-your-api-key-here"
```

#### Option B: Google Gemini

1. Get your Gemini API key from [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Open `.api_key.txt` in the `phase 2` directory
3. Add your Gemini API key on a new line (without quotes)
4. Save the file

Example:
```
AIza-your-actual-api-key-here
```

**Alternative:** Set environment variable:
```bash
export GEMINI_API_KEY="AIza-your-api-key-here"
```

**Note:** If your `.api_key.txt` file contains both keys, use the format:
```
openai:sk-...
gemini:AIza...
```

**What happens if you skip this:**
- Phase 2 will still run but with reduced accuracy
- Falls back to pattern-based extraction only
- No LLM-based entity extraction or gap suggestions

---

### Step 2: Install Dependencies

**Option A: Use Project Virtual Environment (Recommended)**
```bash
cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY"
source venv/bin/activate
pip install -r requirements.txt
cd "phase 2"
```

**Option B: Install Locally**
```bash
cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY"
pip install -r requirements.txt
```

**Required packages:**
- `pdfplumber` - PDF text extraction
- `openai` - LLM API client
- `lxml`, `xmltodict` - XML processing
- `pandas`, `numpy` - Data processing

---

### Step 3: Prepare Your Paper

Put your PDF paper in `data/papers/`:

```bash
cd "phase 2"
cp your_paper.pdf data/papers/
```

---

### Step 4: Run Phase 2

**Basic Command:**
```bash
python3 run_phase2.py --paper data/papers/your_paper.pdf --output reports
```

**Full Command (with all options):**
```bash
python3 run_phase2.py \
    --paper data/papers/your_paper.pdf \
    --output reports \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"
```

---

### Step 5: Check Results

Results are in a newly created folder under `reports/` with the format:

- `{disease}_{method}_{timestamp}`

The exact output folder path is printed in the console.

```bash
ls reports/
```

**Main Files to Check:**
- `model_draft.compmodel` - The extracted model (XML)
- `phase2_final_report.json` - Comprehensive report with all results

---

## Detailed Steps: Inputs, Processes, and Outputs

### Step 1: PDF Pipeline

**What You Provide:**
- PDF file (e.g., `data/papers/EbolaSensitivity.pdf`)

**What Happens:**
1. Extracts text from PDF using `pdfplumber` library
2. Cleans text:
   - Removes headers and footers
   - Fixes hyphenation (e.g., "trans-\nmission" → "transmission")
   - Normalizes whitespace
3. Detects sections:
   - Abstract, Introduction, Methods, Model, Results, Discussion
   - Uses pattern matching for section headers
4. Extracts tables:
   - Finds tables in PDF
   - Extracts table data

**What You Get:**
- `paper_text.json`:
  ```json
  {
    "full_text": "Complete cleaned text...",
    "pages": [
      {
        "page_number": 1,
        "text": "Text from page 1..."
      },
      ...
    ]
  }
  ```
- `paper_sections.json`:
  ```json
  {
    "sections": [
      {
        "title": "Abstract",
        "start_page": 1,
        "end_page": 1,
        "text": "Abstract text..."
      },
      ...
    ],
    "tables": [
      {
        "page": 3,
        "data": [["Header1", "Header2"], ["Value1", "Value2"]]
      }
    ]
  }
  ```

**Console Output:**
```
Step 1: Processing PDF...
  ✓ Extracted 10 pages
  ✓ Detected 6 sections
  ✓ Found 2 tables
```

**No LLM used** - Pure PDF processing

---

### Step 2: Paper Promises Extraction

**What You Provide:**
- (Automatic) Uses `paper_text.json` from Step 1

**What Happens:**

**Pattern-Based Extraction:**
- Searches for phrases like:
  - "we model", "our model includes"
  - "age-stratified", "gender-stratified"
  - "parameters include", "compartments are"

**LLM-Based Extraction (if API key available):**

**LLM Input:**
```
System: "You are a scientific paper analyzer. Return only valid JSON."

User: "Extract what this paper promises to model. Analyze the following paper text:

[Full paper text from Step 1]

Extract and return JSON with:
- compartments: List of compartments the paper promises to model
- parameters: List of parameters the paper promises to model  
- stratifications: List of stratifications (age, gender, etc.) the paper promises
- interventions: List of interventions the paper promises to model
- model_type: Type of model (SEIR, SIR, etc.)

Return only valid JSON."
```

**LLM Output:**
```json
{
  "compartments": ["Susceptible", "Exposed", "Infectious", "Recovered"],
  "parameters": ["β", "γ", "μ", "α"],
  "stratifications": ["age"],
  "interventions": [],
  "model_type": "SEIR"
}
```

**What You Get:**
- `paper_promises.json`:
  ```json
  {
    "compartments": [
      {
        "name": "Susceptible",
        "promised": true,
        "evidence": "We model a SEIR model with Susceptible (S)..."
      },
      ...
    ],
    "parameters": [...],
    "stratifications": [...],
    "interventions": [...],
    "model_type": "SEIR"
  }
  ```

**Console Output:**
```
Step 2: Extracting Paper Promises...
  ✓ Extracted promises: 4 compartments, 8 parameters, 1 stratification
```

**Fallback:** If LLM unavailable, uses pattern-based extraction

---

### Step 3: Entity Extraction

**What You Provide:**
- (Automatic) Uses `paper_text.json`, `paper_sections.json` from Step 1

**What Happens:**

**For Each Entity Type (Compartments, Flows, Parameters):**

1. **Pattern-Based Extraction:**
   - Uses regex patterns to find entities
   - For compartments: Looks for "Susceptible (S)", "Infectious (I)", etc.
   - For flows: Looks for "S → E", "dS/dt = ...", etc.
   - For parameters: Looks for parameter tables, "β = 0.5", etc.

2. **LLM-Based Extraction (if API key available):**

**For Compartments - LLM Input:**
```
System: "You are a scientific paper analyzer. Return only valid JSON."

User: "Extract all compartments mentioned in this epidemiological model paper.

Metamodel Schema (valid compartment types):
{
  "compartment_types": [
    "Susceptible", "Exposed", "Infectious", "Recovered",
    "Dead", "Deceased", "Vaccinated", ...
  ]
}

Example compartments from validated models:
- Susceptible (S) - Individuals who can be infected
- Exposed (E) - Individuals in incubation period
- Infectious (I) - Individuals who can transmit disease
- Recovered (R) - Individuals who have recovered

Paper text:
[Relevant sections from paper, especially Model section]

Extract compartments and return JSON array:
[
  {
    "name": "Compartment name",
    "description": "What this compartment represents",
    "text_span": "Exact text from paper mentioning this compartment"
  },
  ...
]"
```

**LLM Output:**
```json
[
  {
    "name": "Susceptible",
    "description": "Individuals who can be infected",
    "text_span": "Susceptible individuals (S) can become infected through contact with infectious individuals"
  },
  {
    "name": "Exposed",
    "description": "Individuals in latent period",
    "text_span": "Exposed individuals (E) are infected but not yet infectious"
  },
  ...
]
```

**For Flows - LLM Input:**
```
"Extract all flows between compartments.

Flow types from metamodel:
- RateFlow: Direct transitions (e.g., E → I at rate ω)
- ContactFlow: Contact-based transmission (e.g., S → E via contact with I)

Extracted compartments: [list from previous step]

Paper text:
[Relevant sections]

Extract flows and return JSON array:
[
  {
    "source": "Source compartment name",
    "target": "Target compartment name",
    "type": "RateFlow" or "ContactFlow",
    "description": "Description of the flow",
    "text_span": "Exact text from paper"
  },
  ...
]"
```

**LLM Output:**
```json
[
  {
    "source": "Susceptible",
    "target": "Exposed",
    "type": "ContactFlow",
    "description": "Infection through contact with infectious individuals",
    "text_span": "Susceptible individuals become exposed at rate βI"
  },
  {
    "source": "Exposed",
    "target": "Infectious",
    "type": "RateFlow",
    "description": "Progression from exposed to infectious",
    "text_span": "Exposed individuals progress to infectious at rate ω"
  },
  ...
]
```

**For Parameters - LLM Input:**
```
"Extract all parameters with their values, units, and descriptions.

Parameter types from metamodel:
- Transmission rates (β, β₁, β₂)
- Recovery rates (γ, γₕ)
- Death rates (μ, μₕ, α)
- Progression rates (ω, ρ, σ)
- Contact rates (c_I, c_D)

Paper text and tables:
[Relevant sections and extracted tables from Step 1]

Extract parameters and return JSON array:
[
  {
    "name": "Parameter name (e.g., β, γ)",
    "value": "Parameter value if given",
    "unit": "Unit (e.g., days^-1, weeks^-1)",
    "description": "What this parameter represents",
    "text_span": "Exact text from paper"
  },
  ...
]"
```

**LLM Output:**
```json
[
  {
    "name": "β",
    "value": "0.5",
    "unit": "days^-1",
    "description": "Transmission rate",
    "text_span": "The transmission rate β = 0.5 days^-1"
  },
  {
    "name": "γ",
    "value": "0.2",
    "unit": "days^-1",
    "description": "Recovery rate",
    "text_span": "The recovery rate γ = 0.2 days^-1"
  },
  ...
]
```

**What You Get:**
- `extracted_entities.json`:
  ```json
  {
    "compartments": [
      {
        "normalized_name": "Susceptible",
        "raw_text": "Susceptible (S)",
        "page_number": 3,
        "text_span": "Susceptible individuals (S)...",
        "extraction_method": "llm",
        "confidence": "high",
        "paper_backed": true,
        "description": "Individuals who can be infected"
      },
      ...
    ],
    "flows": [
      {
        "source": "Susceptible",
        "target": "Exposed",
        "flow_type": "ContactFlow",
        "raw_text": "S → E",
        "page_number": 3,
        "text_span": "Susceptible individuals become exposed...",
        "extraction_method": "llm",
        "confidence": "high",
        "paper_backed": true,
        "description": "Infection through contact"
      },
      ...
    ],
    "parameters": [
      {
        "normalized_name": "β",
        "value": "0.5",
        "unit": "days^-1",
        "description": "Transmission rate",
        "page_number": 4,
        "text_span": "β = 0.5 days^-1",
        "extraction_method": "llm",
        "confidence": "high",
        "paper_backed": true
      },
      ...
    ],
    "stratifications": [...],
    "interventions": [...],
    "extraction_summary": {
      "num_compartments": 5,
      "num_flows": 7,
      "num_parameters": 10,
      ...
    }
  }
  ```

**Console Output:**
```
Step 3: Extracting Entities with Evidence...
  ✓ Extracted entities:
    - Compartments: 5
    - Flows: 7
    - Parameters: 10
    - Stratifications: 0
    - Interventions: 0
```

**Fallback:** If LLM unavailable, uses pattern-based extraction (less accurate)

---

### Step 4: Model Synthesis

**What You Provide:**
- (Automatic) Uses `extracted_entities.json` from Step 3

**What Happens:**
1. Maps extracted entities to `.compmodel` XML structure
2. Creates compartments from extracted entities
3. Creates flows (RateFlow/ContactFlow) and links to parameters:
   - Uses semantic matching to link flows to parameters
   - Recovery flows → γ parameter
   - Disease death flows → α parameter
   - Transmission flows → β or contact rate parameters
4. Creates parameters from extracted entities
5. Validates XML structure

**No LLM used** - Pure rule-based XML generation

**What You Get:**
- `model_draft.compmodel` (XML file):
  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <compartmental:CompartmentalModel 
      xmlns:xmi="http://www.omg.org/XMI" 
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
      xmlns:compartmental="http://example.com/compartmentalmodel" 
      xmi:version="2.0">
    <parameters name="β" expression="0.5" type="CONSTANT" 
                description="Transmission rate" unit="days^-1"/>
    <parameters name="γ" expression="0.2" type="CONSTANT" 
                description="Recovery rate" unit="days^-1"/>
    <compartments PrimaryName="Susceptible" population="0">
      <outgoingFlows xsi:type="compartmental:ContactFlow" 
                     contactCompartment="//@compartments.2" 
                     contactRateParameter="//@parameters.0" 
                     target="//@compartments.1" 
                     description="Infection through contact"/>
    </compartments>
    ...
  </compartmental:CompartmentalModel>
  ```

**Console Output:**
```
Step 4: Synthesizing Model (.compmodel)...
  ✓ Model XML is valid
  ✓ Saved model to: reports/{disease}_{method}_{timestamp}/model_draft.compmodel
```

---

### Step 5: Traceability

**What You Provide:**
- (Automatic) Uses `extracted_entities.json` from Step 3, `model_draft.compmodel` from Step 4

**What Happens:**
1. Maps every model element to paper evidence
2. Links compartments, flows, parameters to text spans
3. Calculates coverage metrics:
   - Total items in model
   - Items with evidence
   - Coverage percentage
4. Calculates faithfulness metrics:
   - Paper-backed items
   - Faithfulness percentage

**No LLM used** - Pure mapping and calculation

**What You Get:**
- `traceability.json`:
  ```json
  {
    "traceability_map": {
      "compartments": {
        "Susceptible": {
          "evidence": [
            {
              "text_span": "Susceptible individuals (S)...",
              "page_number": 3,
              "confidence": "high"
            }
          ]
        },
        ...
      },
      "flows": {...},
      "parameters": {...}
    },
    "coverage_metrics": {
      "total_items": 22,
      "items_with_evidence": 20,
      "coverage_percentage": 90.9
    },
    "faithfulness_metrics": {
      "paper_backed_items": 18,
      "faithfulness_percentage": 81.8
    }
  }
  ```

**Console Output:**
```
Step 5: Creating Traceability Mapping...
  ✓ Traceability metrics:
    - Total items: 22
    - Items with evidence: 20
    - Coverage: 90.9%
    - Paper-backed: 18
    - Faithfulness: 81.8%
```

---

### Step 6: Gap Analysis

**What You Provide:**
- (Automatic) Uses `paper_promises.json` from Step 2, `extracted_entities.json` from Step 3

**What Happens:**
1. Compares paper promises vs extracted model
2. Finds missing compartments (promised but not extracted)
3. Finds missing parameters (promised but not extracted)
4. Finds missing stratifications and interventions
5. Categorizes by severity:
   - **Critical:** Essential for model function
   - **High:** Important but model can work without
   - **Medium:** Nice to have

**No LLM used** - Pure comparison

**What You Get:**
- `phase2_gap_report.json`:
  ```json
  {
    "missing_compartments": [
      {
        "name": "Vaccinated",
        "promised": true,
        "severity": "high",
        "evidence": "Paper mentions vaccination compartment"
      }
    ],
    "missing_parameters": [
      {
        "name": "vaccination_rate",
        "promised": true,
        "severity": "high",
        "evidence": "Paper mentions vaccination rate parameter"
      }
    ],
    "summary": {
      "total_gaps": 5,
      "critical_gaps": 0,
      "high_gaps": 3,
      "medium_gaps": 2
    }
  }
  ```

**Console Output:**
```
Step 6: Analyzing Gaps (Paper Promises vs Extracted Model)...
  ✓ Gap analysis complete:
    - Total gaps: 5
    - Critical: 0
    - High: 3
    - Medium: 2
```

---

### Step 7: Gap Filler

**What You Provide:**
- (Automatic) Uses `phase2_gap_report.json` from Step 6, `paper_text.json` from Step 1
- (Optional) `--prior-models-dir` for Phase 1 model analysis JSONs

**What Happens:**

For each gap, suggests how to fill it from three sources:

1. **Paper Text Re-examination:**
   - Searches for weak signals in paper
   - Looks for mentions that pattern extraction might have missed

2. **Prior Models:**
   - Searches Phase 1 model analysis JSONs
   - Finds how similar models handle the same gap
   - Provides examples from validated models

3. **Domain Knowledge (LLM):**

**LLM Input:**
```
System: "You are an epidemiological modeling expert. Return only valid JSON."

User: "Suggest how to fill this gap in an epidemiological model.

Gap: Missing 'age stratification' compartment dimension

Paper context:
[Relevant paper text sections about age effects]

Metamodel schema:
{
  "stratification_types": ["age", "gender", "location", ...]
}

Examples from prior models:
- COVID-19 model uses age stratification: 0-17, 18-64, 65+
- Malaria model uses age stratification: 0-5, 6-14, 15+

Suggest how to fill this gap and return JSON:
{
  "element": "Suggested element (compartment/parameter name)",
  "source": "domain_knowledge",
  "confidence": "high/medium/low",
  "rationale": "Why this suggestion makes epidemiological sense"
}"
```

**LLM Output:**
```json
{
  "element": "Age stratification with groups: 0-17, 18-64, 65+",
  "source": "domain_knowledge",
  "confidence": "high",
  "rationale": "Age is a common stratification in epidemiological models as transmission and severity vary significantly by age. The suggested age groups align with standard epidemiological practice."
}
```

**What You Get:**
- `gap_fill_suggestions.json`:
  ```json
  {
    "suggestions": [
      {
        "gap": {
          "type": "missing_stratification",
          "name": "age",
          "severity": "high"
        },
        "suggestions": [
          {
            "element": "Age stratification: 0-17, 18-64, 65+",
            "source": "domain_knowledge",
            "confidence": "high",
            "rationale": "Age is a common stratification...",
            "method": "llm"
          },
          {
            "element": "Age stratification from COVID-19 model",
            "source": "prior_model",
            "confidence": "medium",
            "rationale": "Similar model uses this stratification",
            "method": "prior_model_search"
          }
        ]
      },
      ...
    ],
    "summary": {
      "total_suggestions": 8,
      "from_paper": 2,
      "from_prior_models": 3,
      "from_domain_knowledge": 3
    }
  }
  ```

**Console Output:**
```
Step 7: Generating Gap Fill Suggestions...
  ✓ Generated suggestions: 8 total suggestions from 3 sources
```

**Fallback:** If LLM unavailable, only uses paper text and prior models

---

### Step 8: Quality Checks

**What You Provide:**
- (Automatic) Uses `model_draft.compmodel` from Step 4
- (Optional) `--phase1-dir` for Phase 1 analyzers

**What Happens:**
1. Runs Phase 1 model analyzer on extracted model:
   - Parses model structure
   - Counts compartments, flows, parameters
   - Analyzes model complexity
2. Runs Phase 1 uncertainty analyzer on parameters:
   - Documents parameter values
   - Identifies missing values
   - Notes parameter sources
3. Attempts sensitivity analysis:
   - Simulation-based analysis
   - May fail if the extracted model cannot be simulated (e.g., missing numeric values or unsupported constructs)

**No LLM used** - Uses Phase 1 analyzers

**What You Get:**
- `quality_checks.json`:
  ```json
  {
    "model_analysis": {
      "status": "completed",
      "num_compartments": 5,
      "num_flows": 7,
      "num_parameters": 10,
      "has_stratification": false,
      ...
    },
    "uncertainty_analysis": {
      "status": "completed",
      "parameters_with_values": 8,
      "parameters_without_values": 2,
      ...
    },
    "sensitivity_analysis": {
      "status": "not_runnable",
      "reason": "Model structure incompatible"
    }
  }
  ```

**Console Output:**
```
Step 8: Running Quality Checks (Phase 1 Analyzers)...
  ✓ Quality checks complete:
    - Model analysis: completed
    - Uncertainty analysis: completed
    - Sensitivity analysis: not_runnable
```

---

### Step 9: Evaluation

**What You Provide:**
- (Automatic) Uses all previous outputs
- (Optional) `--gold-standard` for comparison (JSON or .compmodel file)
- (Automatic) Baseline models in `data/baseline_models/` are auto-detected if they match the paper name

**What Happens:**
1. Calculates traceability coverage:
   - % of items with evidence
2. Calculates faithfulness:
   - % of items paper-backed
3. Analyzes gaps:
   - Total gaps, by severity
4. Optionally compares to baseline/gold standard:
   - **Auto-detection:** If a baseline `.compmodel` file exists in `data/baseline_models/` with a name matching the paper (e.g., `ebola_salem_smith.compmodel` for `EbolaSensitivity.pdf`), it's automatically used
   - Converts baseline `.compmodel` to gold standard format
   - Calculates precision/recall for compartments and parameters
   - Shows true positives, false positives, false negatives

**No LLM used** - Pure metric calculation

**What You Get:**
- `evaluation_report.json`:
  ```json
  {
    "traceability_coverage": 90.9,
    "faithfulness": 81.8,
    "gap_metrics": {
      "total_gaps": 5,
      "critical_gaps": 0,
      "high_gaps": 3,
      "medium_gaps": 2
    },
    "gold_standard_comparison": {
      "compartments": {
        "precision": 0.85,
        "recall": 0.90,
        "f1": 0.87,
        "tp": 4,
        "fp": 1,
        "fn": 0
      },
      "parameters": {
        "precision": 0.80,
        "recall": 0.88,
        "f1": 0.84,
        "tp": 8,
        "fp": 2,
        "fn": 1
      }
    }
  }
  ```

**Console Output:**
```
Step 9: Evaluating Extraction Quality...
  ✓ Evaluation complete:
    - Traceability coverage: 90.9%
    - Faithfulness: 81.8%
    - Total gaps: 5
```

---

### Final Step: Final Report Generation

**What You Provide:**
- (Automatic) Uses all output files from Steps 1-9

**What Happens:**
1. Combines all outputs into one comprehensive report
2. Generates executive summary
3. Includes all key metrics and results

**No LLM used** - Pure aggregation

**What You Get:**
- `phase2_final_report.json` ← **Main report to check**:
  ```json
  {
    "summary": {
      "extraction": {
        "compartments": 5,
        "flows": 7,
        "parameters": 10,
        ...
      },
      "quality_metrics": {
        "traceability_coverage": 90.9,
        "faithfulness": 81.8,
        "total_gaps": 5
      }
    },
    "paper_promises": {...},
    "extracted_entities": {...},
    "model_structure": {...},
    "traceability": {...},
    "gap_analysis": {...},
    "gap_suggestions": {...},
    "evaluation": {...},
    "quality_checks": {...}
  }
  ```

**Console Output:**
```
Generating Final Comprehensive Report...
  ✓ Final report saved: phase2_final_report.json
```

---

## Command Line Options

```bash
python3 run_phase2.py --help
```

**Required:**
- `--paper`: Path to PDF paper file
- `--output`: Base directory for results (folder name is auto-generated as `{disease}_{method}_{timestamp}`)

**Optional:**
- `--metamodel`: Path to epidemiology metamodel JSON (default: `../phase 1/metamodel_epidemiology.json`)
- `--api-key-file`: Path to API key file (default: `.api_key.txt`)
- `--llm-provider`: LLM provider to use - `openai` or `gemini` (default: `openai`)
- `--phase1-dir`: Path to Phase 1 directory (for quality checks)
- `--prior-models-dir`: Directory with Phase 1 model analysis JSONs (for gap filling)
- `--gold-standard`: Path to gold standard JSON or `.compmodel` file (for evaluation)
- `--baseline-models-dir`: Directory with baseline `.compmodel` files (default: `data/baseline_models`)
- `--no-llm`: Disable LLM, use pattern-based extraction only
- `--output-base-dir`: Base directory used when `--output` is not provided (default: `reports`)
- `--llm-compartments-chars`: Max characters of paper text sent to LLM for compartment extraction (default: `50000`)
- `--llm-flows-chars`: Max characters of paper text sent to LLM for flow extraction (default: `80000`)
- `--llm-parameters-chars`: Max characters of paper text sent to LLM for parameter extraction (default: `80000`)
- `--flow-fuzzy-threshold`: Fuzzy threshold for snapping flow endpoints to known compartments (default: `0.78`)

---

## Example Workflow

### Running with OpenAI (Default)

```bash
# 1. Activate virtual environment
cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY"
source venv/bin/activate

# 2. Set up API key (if not already done)
echo "sk-your-openai-key" > "phase 2/.api_key.txt"

# 3. Navigate to phase 2
cd "phase 2"

# 4. Run Phase 2 with OpenAI
python3 run_phase2.py \
    --paper data/papers/EbolaSensitivity.pdf \
    --output reports \
    --llm-provider openai \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"

# 5. Check results
# The run prints the exact output folder path under reports/
# Open the folder and check:
# - phase2_final_report.json
# - evaluation_report.json
# - model_draft.compmodel
```

### Running with Gemini

```bash
# 1. Activate virtual environment
cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY"
source venv/bin/activate

# 2. Set up API key (if not already done)
echo "AIza-your-gemini-key" > "phase 2/.api_key.txt"

# 3. Navigate to phase 2
cd "phase 2"

# 4. Run Phase 2 with Gemini
python3 run_phase2.py \
    --paper data/papers/EbolaSensitivity.pdf \
    --output reports \
    --llm-provider gemini \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"

# 5. Check results
# The run prints the exact output folder path under reports/
# Open the folder and check:
# - phase2_final_report.json
# - evaluation_report.json
# - model_draft.compmodel
```

### Basic Example (Your Own Paper)

```bash
# 1. Set up API key
echo "sk-your-api-key" > phase\ 2/.api_key.txt

# 2. Install dependencies (if needed)
cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY"
source venv/bin/activate
pip install -r requirements.txt

# 3. Put your paper in data/papers/
cd "phase 2"
cp your_paper.pdf data/papers/

# 4. Run Phase 2 (OpenAI - default)
python3 run_phase2.py \
    --paper data/papers/your_paper.pdf \
    --output reports

# Or with Gemini:
python3 run_phase2.py \
    --paper data/papers/your_paper.pdf \
    --output reports \
    --llm-provider gemini

# 5. Check main results
# The run prints the exact output folder path under reports/
# Open the folder and check:
# - phase2_final_report.json
# - evaluation_report.json
# - model_draft.compmodel
```

---

## Troubleshooting

### "No PDF extraction library found"
```bash
pip install pdfplumber
```

### "LLM not available"
- Check `.api_key.txt` has your API key
- Or set `OPENAI_API_KEY` or `GEMINI_API_KEY` environment variable
- Use `--llm-provider gemini` to use Gemini instead of OpenAI
- Phase 2 will still run but with reduced accuracy

### "Phase 1 analyzer not found"
- This is OK - quality checks will be skipped
- Make sure `--phase1-dir` points to Phase 1 directory if you want quality checks

### "Module not found" errors
- Make sure you're in the `phase 2` directory
- Check that `src/` folder exists with all subdirectories
- Install dependencies from parent directory: `pip install -r ../requirements.txt`

---

## What to Check After Running

1. **`phase2_final_report.json`** - Start here for overview
   - Summary section shows extraction counts and quality metrics
   - Check gap analysis for missing items
   - Review evaluation for quality scores

2. **`model_draft.compmodel`** - The extracted model
   - Open in text editor or XML viewer
   - Check compartments, flows, parameters
   - Verify parameter links (rateParameter/contactRateParameter)

3. **`phase2_gap_report.json`** - What's missing
   - Review missing items
   - Check severity levels

4. **`gap_fill_suggestions.json`** - How to fill gaps
   - Review suggestions for each gap
   - Check source and confidence

---

## Output Summary

After running Phase 2, you get:

- **1 main output:** `model_draft.compmodel` (the extracted model)
- **1 comprehensive report:** `phase2_final_report.json` (all results)
- **9 detailed files:** Individual JSON files for each step (for reference)

**Check `phase2_final_report.json` first** - it contains everything you need!
