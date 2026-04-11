# Phase 2: Step-by-Step Instructions

Complete guide to running Phase 2 with detailed inputs and outputs for each step.

## Quick Start

1. **Set up API key** → `phase 2/.api_key.txt` (OpenAI or Gemini; see Step 1 below)
2. **Install dependencies** → From parent dir: `pip install -r requirements.txt` (see Step 2)
3. **Put paper in** → `data/papers/`
4. **Run** → `python3 run_phase2.py --paper data/papers/your_paper.pdf --output reports` (add `--llm-provider gemini` for Gemini)
5. **Check results** → Open the printed output folder (e.g. `reports/cholera_llm_gemini_20260211_201419`) and check `phase2_final_report.json` and `evaluation_report.json`
6. **Results summary** → After multiple runs, `python3 build_results_md.py` creates `RESULTS_REPORT.md` with per-disease and average metrics (**recall-first**: R / P / F1) for compartments, parameters, and flows

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

#### Option C: Anthropic Claude

1. Get your Claude API key from Anthropic.
2. Open `.api_key.txt` in the `phase 2` directory
3. Add your Claude API key on a new line (without quotes), e.g.:
   ```text
   claude:sk-ant-your-claude-key
   ```

**Alternative:** Set environment variable:
```bash
export ANTHROPIC_API_KEY="sk-ant-your-claude-key"
```

**Note:** If your `.api_key.txt` file contains multiple keys, you can prefix them:
```text
openai:sk-...
gemini:AIza-...
claude:sk-ant-...
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

#### 4.1 Basic Command (default provider)

```bash
python3 run_phase2.py --paper data/papers/your_paper.pdf --output reports
```

#### 4.2 Run with OpenAI (ChatGPT)

Make sure you have an OpenAI key configured (see Step 1).

**Single paper:**

```bash
python3 run_phase2.py \
    --paper data/papers/your_paper.pdf \
    --output reports \
    --llm-provider openai \
    --phase1-dir "../phase 1"
```

**All papers in `data/papers/`:**

```bash
for paper in data/papers/*.pdf; do
  python3 run_phase2.py \
      --paper "$paper" \
      --output reports \
      --llm-provider openai \
      --phase1-dir "../phase 1"
done
```

#### 4.3 Run with Google Gemini – Pro vs Flash

Make sure you have a Gemini key configured (see Step 1).  
You select **Pro** vs **Flash** with the `GEMINI_MODEL` environment variable.

**Gemini 2.5 Pro – single paper:**

```bash
export GEMINI_MODEL="gemini-2.5-pro"

python3 run_phase2.py \
    --paper data/papers/your_paper.pdf \
    --output reports \
    --llm-provider gemini \
    --phase1-dir "../phase 1"
```

**Gemini 2.5 Pro – all papers:**

```bash
export GEMINI_MODEL="gemini-2.5-pro"

for paper in data/papers/*.pdf; do
  python3 run_phase2.py \
      --paper "$paper" \
      --output reports \
      --llm-provider gemini \
      --phase1-dir "../phase 1"
done
```

**Gemini 2.5 Flash – single paper:**

```bash
export GEMINI_MODEL="gemini-2.5-flash"

python3 run_phase2.py \
    --paper data/papers/your_paper.pdf \
    --output reports \
    --llm-provider gemini \
    --phase1-dir "../phase 1"
```

**Gemini 2.5 Flash – all papers:**

```bash
export GEMINI_MODEL="gemini-2.5-flash"

for paper in data/papers/*.pdf; do
  python3 run_phase2.py \
      --paper "$paper" \
      --output reports \
      --llm-provider gemini \
      --phase1-dir "../phase 1"
done
```

---

### Step 5: Check Results

Results are in a newly created folder under `reports/` with the format:

- `{disease}_llm_{openai|gemini}_{timestamp}` (e.g. `cholera_llm_openai_20260204_171722`, `dengue_llm_gemini_20260204_165333`)

The exact output folder path is printed in the console.

```bash
ls reports/
```

**Main Files to Check:**
- `model_draft.compmodel` - The extracted model (XML)
- `phase2_final_report.json` - Comprehensive report with all results
- `evaluation_report.json` - Quality metrics; if a baseline was used, see `gold_standard_comparison` for compartments, parameters, and flows (precision, recall, F1)

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
- **Pattern-based only:** Searches for phrases such as "we model", "our model includes", "age-stratified", "parameters include", "compartments are". No LLM is used so that entity extraction (Step 3) receives focused context.

**What You Get:**
- `paper_promises.json` — compartments, parameters, stratifications, and interventions the paper promises to model (and optional evidence strings).

**Console Output:**
```
Step 2: Extracting Paper Promises (pattern-only)...
  ✓ Extracted promises: 4 compartments, 8 parameters, 1 stratification
```

---

### Step 3: Entity Extraction

**What You Provide:**
- (Automatic) Uses `paper_text.json` and `paper_sections.json` from Step 1.

**What Happens:**
- **When an API key is available:** A single unified LLM call extracts compartments, flows, and parameters from a context window built from model-relevant sections (equations, state variables, parameters, tables). The prompt asks for the primary model at the same level of abstraction as the paper’s diagram or equations, with full descriptive names. Only what the paper clearly presents is extracted. If the response looks truncated, the pipeline retries with a higher token limit.
- **When no API key is available:** Pattern-based extraction is used (regex for compartments, flows, parameters); it is less accurate, especially for flows.

**Pattern-based extraction (fallback):**
- Compartments: e.g. "Susceptible (S)", "Infectious (I)"
- Flows: e.g. "S → E", "dS/dt = ..."
- Parameters: parameter tables, "β = 0.5", etc.

The LLM returns one JSON object with `compartments`, `flows`, and `parameters`. Each compartment has `name` and `description`; each flow has `source`, `target`, `type` (RateFlow/ContactFlow), and `description`; each parameter has `name`, `value`, `unit`, and `description`.

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
Step 3: Extracting Entities...
  ✓ Unified extraction: 5 compartments, 7 flows, 10 parameters
```

**Fallback:** If LLM unavailable, pattern-based extraction is used (less accurate, especially for flows).

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

### Step 6: Quality Checks

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
Step 6: Running Quality Checks (Phase 1 Analyzers)...
  ✓ Quality checks complete:
    - Model analysis: completed
    - Uncertainty analysis: completed
    - Sensitivity analysis: not_runnable
```

---

### Step 7: Evaluation

**What You Provide:**
- (Automatic) Uses all previous outputs
- (Optional) `--gold-standard` for comparison (JSON or .compmodel file)
- (Automatic) Baseline models in `data/baseline_models/` are auto-detected if they match the paper name

**What Happens:**
1. Calculates traceability coverage:
   - % of items with evidence
2. Calculates faithfulness:
   - % of items paper-backed
3. Optionally compares to baseline/gold standard:
   - **Auto-detection:** If a baseline `.compmodel` file exists in `data/baseline_models/` with a name matching the paper (e.g., `ebola_salem_smith.compmodel` for `EbolaSensitivity.pdf`), it's automatically used
   - Converts baseline `.compmodel` to gold standard format
   - Calculates precision/recall/F1 for **compartments**, **parameters**, and **flows**
   - Shows true positives, false positives, false negatives for each

**No LLM used** - Pure metric calculation

**What You Get:**
- `evaluation_report.json`:
  ```json
  {
    "traceability_coverage": { "coverage_percentage": 90.9 },
    "faithfulness": { "faithfulness_percentage": 81.8 },
    "gold_standard_comparison": {
      "compartments": {
        "precision": 0.85,
        "recall": 0.90,
        "f1": 0.87,
        "tp": 4,
        "fp": 1,
        "fn": 0
      },
      "parameters": { "precision": 0.80, "recall": 0.88, "f1": 0.84, "tp": 8, "fp": 2, "fn": 1 },
      "flows": { "precision": 0.75, "recall": 0.82, "f1": 0.78, "tp": 6, "fp": 2, "fn": 1 }
    }
  }
  ```

**Console Output:**
```
Step 7: Evaluating Extraction Quality...
  ✓ Evaluation complete:
    - Traceability coverage: 90.9%
    - Faithfulness: 81.8%
```

---

### Final Step: Final Report Generation

**What You Provide:**
- (Automatic) Uses all output files from Steps 1-7

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
        "faithfulness": 81.8
      }
    },
    "paper_promises": {...},
    "extracted_entities": {...},
    "model_structure": {...},
    "traceability": {...},
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
- `--gold-standard`: Path to gold standard JSON or `.compmodel` file (for evaluation; otherwise baseline in `data/baseline_models` is auto-detected)
- `--baseline-models-dir`: Directory with baseline `.compmodel` files (default: `data/baseline_models`)
- `--no-llm`: Disable LLM, use pattern-based extraction only
- `--output-base-dir`: Base directory used when `--output` is not provided (default: `reports`)
- `--llm-compartments-chars`: Max characters of paper text sent to LLM for compartment extraction (default: `50000`)
- `--llm-flows-chars`: Max characters of paper text sent to LLM for flow extraction (default: `80000`)
- `--llm-parameters-chars`: Max characters of paper text sent to LLM for parameter extraction (default: `80000`)
- `--flow-fuzzy-threshold`: Fuzzy threshold for snapping flow endpoints to known compartments (default: `0.78`)

Paper type (vector-borne / climate) is always **auto-detected** from the paper text and Step 2 promises; no option to set it manually.

**Generating a results summary:** After running on multiple papers (and optionally both providers), run `python3 build_results_md.py` in the `phase 2` directory to create `RESULTS_REPORT.md` with per-disease and average **recall** (primary), precision, and F1 for compartments, parameters, and flows (OpenAI, Gemini, Claude). Use `--reports-dir` / `-o` for separate old vs current reports.

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
    --phase1-dir "../phase 1"

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
    --phase1-dir "../phase 1"

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

## Benchmark: papers 2 and 3 (multi-disease glob)

Paper 1 of each disease is often processed first; to run **all** second and third papers across diseases (stems ending in `2` or `3`):

```bash
cd "phase 2"
# Optional: source ../venv/bin/activate

# Single paper example
python3 run_phase2.py \
    --paper data/diseases/covid/covid2.pdf \
    --llm-provider openai \
    --phase1-dir "../phase 1"

# All papers ending in 2 or 3
for pdf in data/diseases/*/*[23].pdf; do
  python3 run_phase2.py \
      --paper "$pdf" \
      --llm-provider openai \
      --phase1-dir "../phase 1"
done

# Same loop with Gemini: set --llm-provider gemini
```

**Note:** If a disease folder name has a leading space (e.g. Cholera), the glob `data/diseases/*/*[23].pdf` still matches via normal shell expansion.

## Data layout: migrating flat `papers/` + `baseline_models/`

Example for one disease (repeat per disease); run from `phase 2/data/`:

```bash
mkdir -p diseases/covid
mv papers/covid.pdf diseases/covid/covid1.pdf
mv ../baseline_models/covid.compmodel diseases/covid/covid1.compmodel
```

See also [data/README.md](data/README.md) for the directory layout.

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
   - Review evaluation for quality scores

2. **`evaluation_report.json`** - Quality and baseline comparison
   - Traceability coverage and faithfulness
   - If a baseline was used: `gold_standard_comparison` has compartments, parameters, and flows (precision, recall, F1)

3. **`model_draft.compmodel`** - The extracted model
   - Open in text editor or XML viewer
   - Check compartments, flows, parameters
   - Verify parameter links (rateParameter/contactRateParameter)

4. **`RESULTS_REPORT.md`** — After running `build_results_md.py`, use this for a quick scan of **recall** (and P/F1) across diseases and providers.

---

## Output Summary

After running Phase 2, you get:

- **1 main output:** `model_draft.compmodel` (the extracted model)
- **1 comprehensive report:** `phase2_final_report.json` (all results)
- **Evaluation:** `evaluation_report.json` (traceability, faithfulness; if baseline used: `gold_standard_comparison` with compartments, parameters, flows P/R/F1)
- **Other detailed files:** paper_text.json, paper_promises.json, extracted_entities.json, traceability.json, quality_checks.json

**Check `phase2_final_report.json` first** - it contains everything you need. Use `evaluation_report.json` for precision/recall/F1 when a baseline was auto-detected.

**Aggregating multiple runs:** Run `python3 build_results_md.py` to generate `RESULTS_REPORT.md` with per-disease and average metrics from the latest report in each `reports/{disease}_llm_{openai|gemini|claude}_{timestamp}/` folder (recall summary table included).


---

## Optional: GROBID (structured PDF parsing)

GROBID is used to parse scientific PDFs into structured sections (methods, results, references, etc.) so we can embed only the relevant parts for semantic search.

---

## Prerequisites

- Docker installed
- Python 3.x

### Install Docker (Arch Linux)

```bash
sudo pacman -S docker
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER
```

Log out and back in after adding yourself to the docker group.

---

## Running GROBID

Run GROBID as a background service:

```bash
docker run -d --rm -p 8070:8070 -e JAVA_OPTS="-XX:-UseContainerSupport" --name grobid lfoppiano/grobid:0.8.0
```

The first run will pull the Docker image (~2-3GB), subsequent runs start immediately.

Wait about 60 seconds for models to load, then verify it's alive:

```bash
curl http://localhost:8070/api/isalive
# Should return: true
```

You can also open `http://localhost:8070` in your browser to use the web UI.

### Stop GROBID

```bash
docker stop grobid
```

---

## Python Client

```bash
pip install grobid-client-python
```

---

## Usage in Code

```python
import requests

def parse_pdf_sections(pdf_path: str) -> dict:
    """Parse a PDF and return structured sections."""
    with open(pdf_path, 'rb') as f:
        response = requests.post(
            'http://localhost:8070/api/processFulltextDocument',
            files={'input': f},
            data={'consolidateHeader': '0'}
        )
    return response.text  # Returns TEI XML

def extract_methods_and_results(tei_xml: str) -> str:
    """Extract only Methods and Results sections from TEI XML."""
    from bs4 import BeautifulSoup
    soup = BeautifulSoup(tei_xml, 'xml')
    
    relevant_sections = []
    for div in soup.find_all('div'):
        head = div.find('head')
        if head and any(kw in head.text.lower() for kw in ['method', 'result', 'model', 'abstract']):
            relevant_sections.append(div.get_text())
    
    return '\n\n'.join(relevant_sections)
```

---

## Notes

- GROBID must be running before calling the API
- Only Methods + Results sections are needed for parameter extraction — discard references, acknowledgements, funding, author contributions
- The `-XX:-UseContainerSupport` flag is required on newer Linux kernels
- Do NOT commit the Docker image to the repo — everyone pulls it themselves


---

## Phase 2: Before vs After — What Improved Results

This appendix compares **initial pipeline runs** (early February 2026, “before”) with **later runs** (mid-February 2026, “after”) and summarizes what was changed and what was effective in raising extraction quality (precision, recall, F1) against baseline `.compmodel` gold standards.

---

## 1. Quantitative comparison (Gemini)

Scores below are **F1** for compartments (C), parameters (Par), and flows (Flow). “Before” = latest run per disease from **2026-02-04** (initial pipeline). “After” = latest run per disease from **2026-02-11** (improved pipeline). Same provider (Gemini) and same seven diseases.

| Disease       | Before (Feb 4) C / Par / Flow | After (Feb 11) C / Par / Flow | Change (F1) |
|---------------|--------------------------------|--------------------------------|-------------|
| Cholera       | 0.46 / 0.71 / 0.40            | 1.00 / 0.91 / 1.00             | +0.54 / +0.20 / +0.60 |
| Dengue        | 0.92 / 0.48 / 0.71            | 1.00 / 0.64 / 1.00             | +0.08 / +0.16 / +0.29 |
| Ebola         | 1.00 / 0.80 / 0.89            | 0.80 / 0.78 / 0.50             | −0.20 / −0.02 / −0.39 |
| Flu           | 0.67 / 0.40 / 0.91            | 0.80 / 0.46 / 0.75             | +0.13 / +0.06 / −0.16 |
| Measles       | 0.25 / 0.00 / 0.00             | 1.00 / 0.00 / 1.00             | +0.75 / 0.00 / +1.00 |
| Tuberculosis  | 0.67 / 0.57 / 0.50             | 1.00 / 0.47 / 0.67             | +0.33 / −0.10 / +0.17 |
| Zika          | 1.00 / 0.20 / 0.80             | 1.00 / 0.49 / 0.84             | 0.00 / +0.29 / +0.04 |
| **Average**   | **0.70 / 0.45 / 0.60**         | **0.94 / 0.54 / 0.82**         | **+0.24 / +0.09 / +0.22** |

**Summary:** On average, the improved pipeline increased F1 by about **+0.24** (compartments), **+0.09** (parameters), and **+0.22** (flows). Compartments and flows improved the most; parameters improved modestly. Some diseases (e.g. Measles) went from near-zero to strong scores; a few (e.g. Ebola) had small regressions on flows.

---

## 2. What we did (pipeline changes)

### 2.1 PDF extraction and cleaning

- **Before:** Extraction either used a single full-page pass or a different library (e.g. PyPDF2). Many papers showed **run-together text** (e.g. `FungEmergingThemesinEpidemiology` instead of `Fung Emerging Themes in Epidemiology`), which hurts entity and flow extraction.
- **After:**
  - **pdfplumber** as the main extractor (with PyPDF2 only as fallback).
  - **Two-column handling:** Each page is split into left and right halves, text is extracted per half with `extract_text(x_tolerance=2, y_tolerance=2)`, then concatenated. This preserves reading order and word boundaries in typical two-column journal layouts.
  - **Word-level data:** `extract_words(use_text_flow=True)` is used for headings and layout, and word-level data is stored in `raw_pages` for downstream use.
  - **clean_text():** Hyphenation across line breaks is fixed, standalone page numbers are removed, and whitespace is normalized.

**Effect:** Cleaner, correctly spaced text and better section boundaries, so the LLM and pattern-based steps see proper sentences and compartment/parameter/flow phrases instead of glued tokens.

### 2.2 Section detection and structure

- **Before:** Section detection could be weaker or more fragile when headings were lost in run-together text.
- **After:**
  - Heading detection uses **font size and boldness** (pdfplumber word metadata) plus regex-based fallbacks.
  - Sections can be built from layout (headings + text regions) or from a **text-based fallback** (e.g. “Abstract”, “Introduction”, “Methods”, “Results”).
  - Optional **coalesce_short_sections** merges very short sections to avoid tiny fragments.

**Effect:** More stable sectioning and better context windows for promises and entity extraction.

### 2.3 Entity extraction (LLM and fallbacks)

- **Unified extraction:** When the LLM is available, a **single unified LLM call** can extract compartments, flows, and parameters with full-paper (or sectioned) context, instead of separate calls with narrow context.
- **Paper-type awareness:** Vector-borne vs climate (and related) paper types are inferred from text and promises; **prompts are tailored** (e.g. mosquito compartments for dengue) so the model focuses on the right entity types.
- **Fallback:** If the unified call fails, the pipeline falls back to **separate** compartment / flow / parameter extraction; pattern-based and post-processing (e.g. parameter noise filtering) still run.

**Effect:** Better recall and consistency (compartments and flows in particular) and fewer spurious parameters when prompts and context are aligned with paper type.

### 2.4 Evaluation and baselines

- **Baseline auto-detection:** Baseline `.compmodel` files in `data/baseline_models/` are matched to the paper name (e.g. `cholera.compmodel` for Cholera.pdf) so **evaluation is consistent** across runs.
- **Fuzzy matching:** Gold-standard comparison uses a **similarity threshold** (e.g. 0.75) for compartments, parameters, and flows so near-matches (e.g. “Recovered” vs “Recovered humans”) count as correct.

**Effect:** More reliable and comparable P/R/F1 across runs and diseases.

---

## 2.5 Prompts and extraction logic (comprehensive)

What actually changed in the **prompts**, **context**, and **LLM settings** is described here so the comparison is reproducible and auditable.

### 2.5.1 System instruction (all providers)

The LLM client sends a **single shared system instruction** for every extraction call (so provider comparison is fair):

- *"You are a scientific paper analyzer. You must return ONLY valid JSON."*
- Rules: no explanations, no markdown code blocks, no extra text; begin with `{` or `[`, end with `}` or `]`; do **not** use `\`\`\`json`; invalid JSON causes errors; return pure JSON only.

For Gemini this is prepended to the user prompt; for OpenAI/Claude it is sent as a separate system message.

### 2.5.2 Paper-type tailoring (vector-borne / climate)

**Detection** (`paper_type.py`): The pipeline infers `vector_borne` and `climate` from (1) paper text regexes (e.g. mosquito, vector, dengue, zika, malaria, biting rate, susceptible mosquitoes; and climate, temperature, rainfall, seasonal, etc.) and (2) Step 2 promises (e.g. `model_type` or compartment names containing "mosquito", "vector", "egg").

**Injection into prompts:** The following **exact** blocks are appended to the task/context section of the compartment, flow, and parameter prompts when the flags are true.

- **Vector-borne (compartments):**  
  *"This paper appears to describe a VECTOR-BORNE model (e.g. mosquito, dengue, Zika, malaria). Look for BOTH human compartments (Susceptible humans, Exposed humans, Infectious humans, Recovered humans) AND vector/life-stage compartments (Susceptible mosquitoes, Infectious mosquitoes, Eggs, Larvae, Pupae, Susceptible female adults, Exposed female adults, Infectious female adults) if there is direct evidence in the text. Only include those for which you find a clear quote."*

- **Climate (compartments):**  
  *"Consider CLIMATE or ENVIRONMENTAL drivers if mentioned (e.g. temperature, rainfall, seasonality, humidity). Include compartments or parameters related to these only if explicitly evidenced."*

- **Vector-borne (flows):**  
  *"This paper appears to describe a VECTOR-BORNE model. Look for flows between HUMAN compartments (e.g. Susceptible humans -> Exposed humans) AND vector/life-stage flows (e.g. Eggs -> Larvae -> Pupae -> Susceptible female adults, and human-vector transmission flows). Only include flows for which you find clear evidence."*

- **Vector-borne (parameters):**  
  *"This paper appears to describe a VECTOR-BORNE model. Look for human parameters (transmission, recovery, mortality) AND vector/life-stage parameters (biting rate, egg/larval/pupal development rates, vector mortality, vector incubation). Only include parameters with clear evidence."*

- **Climate (parameters):**  
  *"Consider CLIMATE/ENVIRONMENTAL parameters if mentioned (e.g. temperature, rainfall, seasonality). Include only if explicitly defined in the text."*

So **what changed** in prompts: after adding paper-type detection, these blocks were added so the model explicitly looks for human+vector compartments/flows/parameters in vector-borne papers and for climate-related entities only when evidenced.

### 2.5.3 Separate extraction prompts (compartments, flows, parameters)

When **unified** extraction is not used (or fails), the pipeline uses **separate** prompts. Two variants exist: **detailed** (used for Gemini and Claude) and **concise** (used for OpenAI). Both now use the same “detailed” style for quality parity (`use_detailed_prompt = True`), so in practice all providers get the long instructions below.

**Compartments:**

- **Task:** Extract ALL compartment names; return only a valid JSON array; no markdown; begin with `[`, end with `]`.
- **Evidence rule:** Only include a compartment if there is a clear, direct quote; if no supporting quote, do **not** include it; prefer false negatives over inventing; reject if not enough evidence.
- **Chain-of-thought:** “First list the compartment names you find in the text; for each note the exact quote that defines it; then output the JSON array.”
- **Required JSON:** `{"name": "...", "description": "...", "text_span": "Exact quote..."}` for each compartment.
- **Systematic search:** Instructions list where to look (compartment definitions, state variables in equations like S(t)/dS/dt, population groups, model descriptions, tables, initial conditions S(0)/I(0), flow descriptions “from X to Y”).
- **Normalization:** Explicit mapping (e.g. Susceptible/S → Susceptible, Exposed/E/Latent → Exposed, Infectious/Infected/I → Infectious, Recovered/Removed/R → Recovered, Dead/Deceased/D → Dead or Infectious Deceased).
- **Optional injected context:**  
  - **Promised compartments (Step 2):** If Step 2 produced a list, the prompt says “PROMISED COMPARTMENTS (from a prior pass): X, Y, Z. Only include these if you find direct evidence in the paper text below. If you find no supporting quote for a promised compartment, do NOT include it.” So **what changed**: we use Step 2 promises as a hint but require evidence, reducing hallucination.  
  - **Metamodel:** Standard compartment types from the metamodel (e.g. from `metamodel_epidemiology.json`) are listed.  
  - **Phase 1 examples:** Up to 3 example models with compartment names (e.g. “Model A: Susceptible, Infectious, Recovered”) to anchor naming.

**Flows:**

- **Task:** Extract every flow (transition) between compartments; return only a valid JSON array.
- **Evidence rule:** Same as compartments: only include a flow with a clear quote; prefer omitting over inventing.
- **Chain-of-thought:** “First list each flow you find (source -> target) with the quote that describes it; then output the JSON array.”
- **Required JSON:** `{"source": "...", "target": "...", "description": "...", "text_span": "...", "flow_type": "RateFlow" or "ContactFlow"}`.  
  Flow types: RateFlow = progression/recovery/death/treatment; ContactFlow = transmission/infection.
- **Available compartments:** The prompt lists the **exact** compartment names from the compartment step so source/target must match them (reduces bogus flow endpoints).
- **Where to look:** Flow descriptions (“from X to Y”), differential equations (dX/dt = … + Y), transmission/progression/recovery/death/treatment wording, flow tables.

**Parameters:**

- **Task:** Extract every parameter mentioned; return only a valid JSON array.
- **Evidence rule:** Only include if there is clear evidence (quote or table row); prefer omitting over inventing.
- **Chain-of-thought:** “First list each parameter you find with its definition or table row; then output the JSON array.”
- **Required JSON:** `{"name": "...", "value": "...", "unit": "...", "description": "...", "text_span": "..."}`.
- **Where to look:** Greek letters with values, parameter definitions, parameter tables, rate/probability definitions, symbols in equations (e.g. dS/dt = -βSI).
- **Metamodel and Phase 1 examples:** Same idea as compartments (parameter types + example models with parameter names/values).

**What changed in separate prompts:** (1) Paper-type blocks above were added. (2) Evidence rule and chain-of-thought were made explicit and consistent across all three tasks. (3) Promised compartments were wired in with “only if evidence” to reduce hallucination. (4) Detailed prompts were standardized so all providers get the same thorough instructions.

### 2.5.4 Unified extraction (single LLM call)

When the LLM is available, the pipeline **first** tries **one** call that returns a single JSON object with `compartments`, `flows`, and `parameters`:

- **Role:** “You are an expert epidemiological modeler.”
- **Task:** From the paper text below, extract the **primary** compartmental model (diagram, flow chart, or system of ODEs). Return one JSON object with:
  - `compartments`: `[{ "name": "...", "description": "..." }]`
  - `flows`: `[{ "source": "...", "target": "...", "type": "RateFlow or ContactFlow", "description": "..." }]`
  - `parameters`: `[{ "name": "...", "value": "...", "unit": "...", "description": "..." }]`
- **Paper-type hint (if vector_borne):** “This paper describes a VECTOR-BORNE disease model. Look for both human AND vector/mosquito compartments and flows.”
- **Rules:**  
  - Compartments: same level of abstraction as the paper’s diagram/equations; use standard epidemiological names; use **full** names (not S, E, I, R or S_h, I_v, E1, I2); if multiple groups (humans/vectors, age groups), include compartments for each (e.g. Susceptible Humans, Infectious Mosquitoes).  
  - Flows: every transition arrow; source/target must **exactly** match compartment names from the list; ContactFlow = transmission/infection; RateFlow = recovery, death, vaccination, progression, etc.  
  - Parameters: symbols, values, units from tables/equations/text.  
  - Extract **only** what the paper explicitly presents; do not invent or over-split.

**Context for unified call:** The text sent is not the full paper but a **window** built by `build_text_window()` (see below), plus optional formatted tables. So **what changed**: we added a single high-level “extract the primary model” prompt with strict naming and evidence rules, and we feed it a focused window instead of raw full text.

### 2.5.5 Context building (text windows and limits)

**Character limits (separate extraction):**

- Compartments: `llm_compartments_chars` (default 50,000).
- Flows: `llm_flows_chars` (default 80,000).
- Parameters: `llm_parameters_chars` (default 80,000); when using a window, parameters use `min(llm_parameters_chars, 20_000)`.

**Text window (when using `build_text_window`):**

- **Goal:** Send only the most relevant pages/segments instead of the full PDF text.
- **Mechanism:** `select_page_indices()` selects pages whose text matches **include_patterns**, with optional **exclude_patterns**. Selected indices are then **padded** (e.g. ±1 page) and capped at **max_pages**. If no page matches, a **fallback** uses the first N pages (e.g. 4–6).
- **Patterns used for compartments:** e.g. `\bcompartment|\bstate\b|\bgroup\b|\bclass\b`, `\bS\s*\(t\)|\bE\s*\(t\)|\bI\s*\(t\)|\bR\s*\(t\)`, `d[a-z]\s*/\s*dt`, `\bequation|\bflow|\btransition|\bmodel\b`, etc.
- **Patterns for flows:** Similar (flow, transition, equation, model, differential).
- **Patterns for parameters:** Parameter, rate, value, table, equation.
- **Unified extraction:** Uses a single window with combined model-relevant patterns, `max_chars = max(compartments, flows, parameters)`, `pad=2`, `max_pages=14`, `fallback_first_pages=6`.

So **what changed**: we moved from “send truncated full text” to “send a pattern-based window of pages + padding” so the LLM sees the model-defining sections (and a bit of context) instead of arbitrary truncation.

### 2.5.6 LLM settings and structured output

- **Temperature:** For extraction, Gemini uses **0.2** when a response schema is set; unified extraction uses **temperature=0** for determinism. Others use the default (e.g. 0.3) unless overridden.
- **Max tokens:** Compartments 4000; flows 4000 (or 6000 in an experiment variant); parameters 8000; unified 16000 to avoid truncation.
- **Gemini structured output:** For separate extraction, Gemini uses **response_schema** (JSON Schema) for compartments, flows, and parameters so the API returns valid JSON matching the expected shape; this reduces parse failures and drift.

### 2.5.7 Post-processing (parameters and flows)

- **Parameters:** `_clean_and_filter_parameters()` drops entries that look like noise: e.g. DOI/arXiv identifiers, long numeric IDs, “etal”, “figure”, “table” as parameter names, and applies a blacklist of substrings. It keeps Greek letters, R0, N, and other epidemiology-relevant symbols. So **what changed**: after extraction we explicitly filter likely non-parameters to improve precision.
- **Flows:** Flows whose source or target does not match any extracted compartment are dropped; “inconsistent/unmatched” flows are removed so the synthesized model only has valid compartment references.

---

## 3. What was most effective

1. **PDF extractor and two-column handling** — Fixing run-together text and column order had the largest impact on **compartments and flows**. Without readable text, the LLM and patterns both underperform.
2. **Section detection and clean text** — Better sections and clean_text (hyphenation, whitespace) gave the LLM **clearer context** and improved recall, especially where model structure is described in “Methods” or “Model”.
3. **Unified LLM extraction and paper-type prompts** — One coherent call plus vector-borne/climate-aware prompts (§2.5.2, §2.5.4) improved **consistency and recall** for compartments and flows; parameter F1 improved more modestly and remains the hardest category.
4. **Prompt design (evidence rule, CoT, context windows)** — Explicit “only if evidence” and chain-of-thought in every prompt (§2.5.3), plus pattern-based text windows (§2.5.5) instead of blind truncation, reduced hallucinations and missed entities.
5. **Stable evaluation (baseline + fuzzy match)** — Same gold standards and matching rules make before/after and cross-disease comparisons meaningful.

---

## 4. Where results still vary

- **Parameters** still have the lowest F1 on average (around 0.54–0.56) and are sensitive to notation (e.g. β vs β * κ, subscripts). Further gains likely need notation-aware parsing or parameter-specific prompts.
- **Ebola** (and occasionally others) can show lower flow F1 in a given run due to model complexity or baseline definition; this is a known variance.
- **OpenAI** runs in the reported set had no valid API/key, so comparison here is only Gemini (before/after) and Claude (after); adding OpenAI back would require re-runs with a working key.

---

## 5. How to reproduce

- **Before (old pipeline):** Use report dirs from **2026-02-04** (e.g. `cholera_llm_gemini_20260204_165108`, etc.). Those were produced with the initial PDF and extraction setup.
- **After (current pipeline):** Run the current Phase 2 code (pdfplumber, two-column extraction, unified LLM, section detection, and evaluation as in `run_phase2.py`). Latest report dirs are under `reports/` with timestamps **20260211** (Gemini) and **20260212** (Claude).
- Regenerate summary tables with:  
  `python3 build_results_md.py`

---

*Generated from Phase 2 report directories and pipeline code. “Before” = 2026-02-04 Gemini runs; “After” = 2026-02-11 Gemini (and 2026-02-12 Claude) as in RESULTS_REPORT.md.*
