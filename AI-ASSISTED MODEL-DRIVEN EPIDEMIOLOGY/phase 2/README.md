# Phase 2: Automated Model Extraction from Papers

## Overview

Phase 2 automatically extracts compartmental epidemiological models from scientific paper PDFs. Given a paper, it extracts the model structure, generates a `.compmodel` file, identifies gaps, suggests gap fills, and evaluates the quality.

**What it does:**
- Reads PDF papers
- Extracts compartments, flows, parameters, stratifications, interventions
- Generates `.compmodel` XML files
- Identifies what's missing (gaps)
- Suggests how to fill gaps
- Evaluates extraction quality

**Key Principle:** Faithfulness to the paper — only what is explicitly described or promised in the paper is extracted.

Paper type (vector-borne / climate) is **auto-detected** from text and promises, and prompts are tailored accordingly. Entity extraction uses a **single unified LLM call** for compartments, flows, and parameters when an API key is available, so the model sees the full context at once. Step 2 (paper promises) is **pattern-based only** to keep context focused. Evaluation compares extracted entities to baseline `.compmodel` files in `data/baseline_models/` when the paper name matches; precision, recall, and F1 are reported for compartments, parameters, and flows.

When using `--llm-provider gemini`, the pipeline uses **structured output** (JSON schema) for extraction so the model returns valid JSON. Temperature is set to **0** for deterministic output and a 2-minute timeout applies to avoid hanging.

## Project Structure

```
phase 2/
├── .api_key.txt              ← Your OpenAI or Gemini API key goes here
├── run_phase2.py             ← Main script to run the pipeline
├── build_results_md.py       ← Script to generate results summary from latest reports
├── README.md                  ← This file (comprehensive guide)
├── INSTRUCTIONS.md            ← Step-by-step how to run
├── RESULTS_REPORT.md         ← Evaluation results summary (run build_results_md.py to regenerate)
│
├── src/                       ← Source code (organized by function)
│   ├── extraction/            ← PDF processing & entity extraction
│   ├── synthesis/             ← Model generation & traceability
│   ├── analysis/              ← Gap analysis & gap filling
│   ├── evaluation/            ← Quality checks & final report
│   └── utils/                 ← LLM client utilities
│
├── data/
│   ├── papers/                ← Put your PDF papers here
│   └── baseline_models/       ← Baseline .compmodel files for evaluation (auto-detected)
│
└── reports/                   ← Output directory (auto-generated)
    └── {disease}_llm_{openai|gemini}_{timestamp}/
        ├── model_draft.compmodel      ← Main output: extracted model
        ├── phase2_final_report.json   ← Comprehensive report
        ├── evaluation_report.json     ← Metrics & gold-standard comparison (compartments, parameters, flows)
        └── [other detailed JSON files] ← paper_text.json, extracted_entities.json, etc.
```

## How It Works: The 9-Step Pipeline

Phase 2 runs 9 steps automatically. Each step has specific inputs, processes, and outputs:

### Step 1: PDF Pipeline

**Input:** PDF file (e.g., `data/papers/EbolaSensitivity.pdf`)

**Process:**
- Extracts text from PDF using `pdfplumber` library
- Cleans text (removes headers/footers, fixes hyphenation)
- Detects sections (Abstract, Methods, Model, Results, etc.)
- Extracts tables from PDF

**Output:**
- `paper_text.json` - Cleaned text with page numbers
  ```json
  {
    "full_text": "...",
    "pages": [
      {"page_number": 1, "text": "..."},
      ...
    ]
  }
  ```
- `paper_sections.json` - Detected sections and extracted tables
  ```json
  {
    "sections": [
      {"title": "Abstract", "start_page": 1, "end_page": 1},
      {"title": "Methods", "start_page": 2, "end_page": 5},
      ...
    ],
    "tables": [...]
  }
  ```

**No LLM used** - Pure PDF processing

---

## Running with Different LLM Providers and Models

Phase 2 supports both **OpenAI** and **Google Gemini**. You choose the provider with `--llm-provider` and can control the exact model with environment variables.

### OpenAI (ChatGPT)

- **Provider flag**: `--llm-provider openai`
- **API key**: `OPENAI_API_KEY` env var or `openai:sk-...` in `.api_key.txt`
- **Default model**: `gpt-4o` (set `OPENAI_MODEL=gpt-4o-mini` for cheaper/faster runs)

Override the OpenAI model:

```bash
export OPENAI_API_KEY="sk-..."         # or use .api_key.txt
export OPENAI_MODEL="gpt-4o-mini"      # optional: cheaper/faster (default is gpt-4o)
```

Run on a single paper:

```bash
python3 run_phase2.py \
  --paper data/papers/EbolaSensitivity.pdf \
  --output reports \
  --llm-provider openai \
  --phase1-dir "../phase 1" \
  --prior-models-dir "../phase 1/reports/model_analysis"
```

Run on **all papers** in `data/papers/`:

```bash
for paper in data/papers/*.pdf; do
  python3 run_phase2.py \
    --paper "$paper" \
    --output reports \
    --llm-provider openai \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"
done
```

### Google Gemini (Pro and Flash)

- **Provider flag**: `--llm-provider gemini`
- **API key**: `GEMINI_API_KEY` env var or `gemini:AIza...` in `.api_key.txt`
- **Default model**: `gemini-2.5-pro` (can be overridden)

You can switch between **Gemini 2.5 Pro** and **Gemini 2.5 Flash** using the `GEMINI_MODEL` environment variable.

#### Gemini 2.5 Pro – single paper

```bash
export GEMINI_API_KEY="AIza-..."          # or use .api_key.txt with gemini: prefix
export GEMINI_MODEL="gemini-2.5-pro"

python3 run_phase2.py \
  --paper data/papers/EbolaSensitivity.pdf \
  --output reports \
  --llm-provider gemini \
  --phase1-dir "../phase 1" \
  --prior-models-dir "../phase 1/reports/model_analysis"
```

#### Gemini 2.5 Pro – all papers

```bash
export GEMINI_API_KEY="AIza-..."
export GEMINI_MODEL="gemini-2.5-pro"

for paper in data/papers/*.pdf; do
  python3 run_phase2.py \
    --paper "$paper" \
    --output reports \
    --llm-provider gemini \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"
done
```

#### Gemini 2.5 Flash – single paper

```bash
export GEMINI_API_KEY="AIza-..."
export GEMINI_MODEL="gemini-2.5-flash"

python3 run_phase2.py \
  --paper data/papers/EbolaSensitivity.pdf \
  --output reports \
  --llm-provider gemini \
  --phase1-dir "../phase 1" \
  --prior-models-dir "../phase 1/reports/model_analysis"
```

#### Gemini 2.5 Flash – all papers

```bash
export GEMINI_API_KEY="AIza-..."
export GEMINI_MODEL="gemini-2.5-flash"

for paper in data/papers/*.pdf; do
  python3 run_phase2.py \
    --paper "$paper" \
    --output reports \
    --llm-provider gemini \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"
done
```

---

### Step 2: Paper Promises Extraction

**Input:** Cleaned paper text from Step 1

**Process:**
- **Pattern-based only:** Searches for phrases like "we model", "our model includes", "age-stratified", "parameters include", "compartments are"
- No LLM call in this step so that Step 3 receives focused context

**Output:** `paper_promises.json` — compartments, parameters, stratifications, and interventions the paper promises to model

---

### Step 3: Entity Extraction

**Input:** Paper text, pages, and tables from Step 1.

**Process:**
- **Unified single-pass LLM extraction (when API key is available):** One LLM call extracts compartments, flows, and parameters together from a context window built from model-relevant sections (equations, state variables, parameters, tables). The prompt asks for the **primary compartmental model** at the same level of abstraction as the paper’s model diagram or differential equations, with full descriptive names (e.g. "Susceptible", "Infectious") and no single-letter abbreviations. Output is JSON with `compartments`, `flows`, and `parameters`. If the response looks truncated (e.g. very few flows or parameters for a multi-compartment model), the pipeline retries with a higher token limit and keeps the more complete result.
- **Evidence:** Entities are recorded with description, page, and extraction method. Only what the paper clearly presents is extracted.
- **Fallback:** If the LLM is unavailable, pattern-based extraction is used (less accurate, especially for flows).

---

### Step 4: Model Synthesis

**Input:** Extracted entities from Step 3

**Process:**
- Maps entities to `.compmodel` XML structure
- Creates compartments from extracted entities
- Creates flows (RateFlow/ContactFlow) and links to parameters using semantic matching
  - Recovery flows → γ parameter
  - Disease death flows → α parameter
  - Transmission flows → β or contact rate parameters
- Creates parameters from extracted entities
- Validates XML structure

**No LLM used** - Pure rule-based XML generation

**Output:** `model_draft.compmodel` (XML file)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<compartmental:CompartmentalModel ...>
  <parameters name="β" expression="0.5" .../>
  <compartments PrimaryName="Susceptible" ...>
    <outgoingFlows xsi:type="compartmental:ContactFlow" 
                   contactRateParameter="//@parameters.0" .../>
  </compartments>
  ...
</compartmental:CompartmentalModel>
```

---

### Step 5: Traceability

**Input:** Extracted entities, model structure

**Process:**
- Maps every model element to paper evidence
- Links compartments, flows, parameters to text spans
- Calculates coverage metrics (% items with evidence)
- Calculates faithfulness metrics (% items paper-backed)

**No LLM used** - Pure mapping and calculation

**Output:** `traceability.json` with evidence mapping and metrics

---

### Step 6: Gap Analysis

**Input:** Paper promises (Step 2), extracted entities (Step 3)

**Process:**
- Compares promises vs extracted model
- Finds missing compartments (promised but not extracted)
- Finds missing parameters (promised but not extracted)
- Finds missing stratifications and interventions
- Categorizes by severity (critical/high/medium)

**No LLM used** - Pure comparison

**Output:** `phase2_gap_report.json` with missing items

---

### Step 7: Gap Filler

**Input:** Gap analysis, paper text, prior models (Phase 1)

**Process:**
- For each gap, suggests how to fill it from three sources:
  1. **Paper text re-examination** - Searches for weak signals
  2. **Prior models** - How similar Phase 1 models handle gaps
  3. **Domain knowledge (LLM)** - LLM-based suggestions

**LLM Usage (for domain knowledge suggestions):**

**LLM Input:**
```
System Message: "You are an epidemiological modeling expert. Return only valid JSON."

User Message:
"Suggest how to fill this gap in an epidemiological model.

Gap: [Missing compartment/parameter/stratification]

Paper context:
[Relevant paper text sections]

Metamodel schema:
[Valid types from metamodel_epidemiology.json]

Examples from prior models:
[How similar models handle this]

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
  "element": "Age stratification",
  "source": "domain_knowledge",
  "confidence": "high",
  "rationale": "Age is a common stratification in epidemiological models as transmission and severity vary by age"
}
```

**Output:** `gap_fill_suggestions.json` with suggestions for each gap

**Fallback:** If LLM unavailable, only uses paper text and prior models (no domain knowledge suggestions)

---

### Step 8: Quality Checks

**Input:** Generated model from Step 4

**Process:**
- Runs Phase 1 analyzers on extracted model:
  - Model analysis (structure, counts)
  - Uncertainty analysis (parameter uncertainty)
  - Sensitivity analysis (simulation-based; may fail if the extracted model cannot be simulated)

**No LLM used** - Uses Phase 1 analyzers

**Output:** `quality_checks.json` with Phase 1 analysis results

---

### Step 9: Evaluation

**Input:** All previous outputs, optionally baseline model (`.compmodel` file)

**Process:**
- Calculates quality metrics:
  - Traceability coverage (% items with evidence)
  - Faithfulness (% items paper-backed)
  - Gap metrics (total, by severity)
  - Precision/recall (if baseline/gold standard provided)
- **Auto-detects baseline models:** If a baseline `.compmodel` file exists in `data/baseline_models/` matching the paper name, it's automatically used for comparison
- Converts baseline `.compmodel` to gold standard format and computes precision/recall/F1 for **compartments**, **parameters**, and **flows**

**No LLM used** - Pure metric calculation

**Output:** `evaluation_report.json` with quality metrics and `gold_standard_comparison` (compartments, parameters, flows: precision, recall, F1)

---

### Final Step: Final Report Generation

**Input:** All outputs from Steps 1-9

**Process:**
- Combines everything into one comprehensive report
- Generates executive summary

**No LLM used** - Pure aggregation

**Output:** `phase2_final_report.json` ← **Main report to check**

---

## LLM Integration Details

### When LLM is Used

The LLM is used in **Step 3 (Entity Extraction)** when an API key is available. Step 2 uses pattern-based extraction only. Gap analysis (Step 6) and gap filler (Step 7) produce report files; gap-fill suggestions do not call the LLM in the default pipeline.

### LLM Configuration

- **Model:** `gpt-4o` (OpenAI default); `gemini-2.5-pro` (Gemini default). Override with `OPENAI_MODEL` or `GEMINI_MODEL`.
- **Temperature:** `0` for extraction (deterministic output).
- **Max output tokens:** 16000 (retry with 32000 if extraction looks truncated).
- **Timeout:** 2 minutes per call (Gemini); timeout errors are retried once.
- **Output format:** JSON (repaired automatically if malformed).

### LLM Input Structure

**System Message (always):**
```
"You are a scientific paper analyzer. Return only valid JSON."
```

**User Message Contains:**
- Paper text (relevant sections)
- Metamodel schema (for Step 3 and Step 7)
- Phase 1 example models (for Step 3 and Step 7)
- Specific extraction prompt
- JSON schema for expected output

### LLM Output Format

All LLM outputs are **JSON**:
- Compartments: `[{"name": "...", "description": "...", "text_span": "..."}]`
- Flows: `[{"source": "...", "target": "...", "type": "...", "description": "..."}]`
- Parameters: `[{"name": "...", "value": "...", "unit": "...", "description": "..."}]`
- Gap suggestions: `[{"element": "...", "source": "...", "confidence": "...", "rationale": "..."}]`

### LLM Fallback Behavior

If LLM is unavailable (no API key, network error, etc.):
- **Step 2:** Falls back to pattern-based extraction
- **Step 3:** Falls back to pattern-based extraction (less accurate, may miss complex flows)
- **Step 7:** Only uses paper text and prior models (no domain knowledge suggestions)

### Automatic Context Loading

Phase 2 automatically loads context to improve LLM extraction:

1. **Metamodel** from `../phase 1/metamodel_epidemiology.json`
   - Tells LLM valid model structures
   - Includes compartment types, flow types, parameter types
   - Epidemiology-only (excludes traffic/TRM to avoid confusion)

2. **Phase 1 examples** from `../phase 1/papers/epimde/*.compmodel`
   - Shows LLM proven patterns
   - Examples of compartment naming, parameter definitions, flow patterns

3. **Enhanced prompts** - Include metamodel and examples in LLM prompts

You'll see console messages like:
```
Using metamodel: ../phase 1/metamodel_epidemiology.json
Using example models for context: ../phase 1/papers/epimde
```

If these files aren't found, the pipeline still works but with reduced accuracy (falls back to pattern-based extraction only).

## Key Features

### Evidence-Based Extraction
Every extracted entity has:
- Text span from paper
- Page number
- Extraction method (pattern/LLM/table)
- Confidence level
- Paper-backed flag

### Gap Analysis
Identifies:
- Missing promised compartments
- Missing promised parameters
- Missing promised stratifications
- Missing promised interventions

### Gap Filling
Suggests fills from:
1. **Paper text re-examination** - Weak signals in paper
2. **Prior models** - How similar models handle gaps
3. **Domain knowledge** - LLM-based suggestions

### Quality Metrics
- **Traceability Coverage:** % of items with evidence
- **Faithfulness:** % of items paper-backed
- **Gap Metrics:** Total gaps by severity
- **Extraction Quality:** Precision/recall (if baseline/gold standard available)

### Baseline Model Evaluation

Phase 2 automatically uses baseline `.compmodel` files for evaluation if they exist:

1. **Auto-detection:** When you run Phase 2, it looks for baseline models in `data/baseline_models/` that match your paper name
   - Example: If processing `EbolaSensitivity.pdf`, it looks for `ebola*.compmodel` files
   - If found, automatically uses them for precision/recall calculation

2. **Manual specification:** You can also explicitly provide a baseline:
   ```bash
   python3 run_phase2.py --paper data/papers/your_paper.pdf \
       --output reports \
       --gold-standard data/baseline_models/your_baseline.compmodel
   ```

3. **What gets compared:**
   - **Compartments:** Extracted vs baseline compartments
   - **Parameters:** Extracted vs baseline parameters
   - **Metrics:** Precision, recall, F1 score, true positives, false positives, false negatives

4. **Output:** Results appear in `evaluation_report.json` under `gold_standard_comparison` (compartments, parameters, flows: precision, recall, F1, tp, fp, fn)

## Output Files

### Main Outputs (Check These First)

1. **`model_draft.compmodel`** - The extracted model (XML file)
2. **`phase2_final_report.json`** - Comprehensive report with all results

### Detailed Outputs (For Reference)

3. `paper_text.json` - Cleaned paper text
4. `paper_sections.json` - Detected sections and tables
5. `paper_promises.json` - What paper promises to model
6. `extracted_entities.json` - All extracted entities with evidence
7. `traceability.json` - Evidence mapping
8. `phase2_gap_report.json` - Missing items
9. `gap_fill_suggestions.json` - Gap fill suggestions
10. `quality_checks.json` - Phase 1 analyzer results
11. `evaluation_report.json` - Quality metrics and gold-standard comparison (compartments, parameters, flows)

### Generating the results summary

After running Phase 2 on multiple papers (e.g. all PDFs in `data/papers/`) with one or both providers (OpenAI and Gemini), aggregate the latest evaluation results into a markdown report:

```bash
python3 build_results_md.py
```

The script reads the most recent `evaluation_report.json` in each `reports/{disease}_llm_{openai|gemini}_{timestamp}/` folder, extracts precision/recall/F1 for compartments, parameters, and flows per disease and provider, and writes **`RESULTS_REPORT.md`** with per-disease tables, averages, and an F1 summary.

## Setup

### 1. Install Dependencies

All dependencies are unified in the parent directory:

```bash
cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY"
pip install -r requirements.txt
```

**Or use virtual environment:**
```bash
cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY"
source venv/bin/activate
pip install -r requirements.txt
```

**Key Dependencies:**
- `pdfplumber` - PDF text extraction
- `openai` - OpenAI API client (optional, if using OpenAI)
- `google-generativeai` - Google Gemini API client (optional, if using Gemini)
- `lxml` - XML processing
- `pandas`, `numpy` - Data processing

### 2. Set Up API Key

You can use either **OpenAI** or **Google Gemini** as your LLM provider.

#### Option A: OpenAI (Default)

**File:** `phase 2/.api_key.txt`

1. Open `.api_key.txt`
2. Add your OpenAI API key on a new line (without quotes)
   - Or use format: `openai:sk-your-api-key-here` (if file contains multiple keys)
3. Save

**Alternative:** Set environment variable:
```bash
export OPENAI_API_KEY="sk-your-api-key-here"
```

#### Option B: Google Gemini

**File:** `phase 2/.api_key.txt`

1. Get your Gemini API key from [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Open `.api_key.txt`
3. Add your Gemini API key on a new line (without quotes)
   - Or use format: `gemini:AIza...` (if file contains multiple keys)
4. Save

**Alternative:** Set environment variable:
```bash
export GEMINI_API_KEY="AIza-your-api-key-here"
```

**Note:** If your `.api_key.txt` file contains both keys, use the format:
```
openai:sk-...
gemini:AIza...
```

## Running Phase 2

### Quick Start

**1. Activate Virtual Environment:**
```bash
cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY"
source venv/bin/activate
cd "phase 2"
```

**2. Run with OpenAI (Default):**
```bash
python3 run_phase2.py \
    --paper data/papers/EbolaSensitivity.pdf \
    --output reports \
    --llm-provider openai \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"
```

**3. Run with Gemini:**
```bash
python3 run_phase2.py \
    --paper data/papers/EbolaSensitivity.pdf \
    --output reports \
    --llm-provider gemini \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"
```

**4. Check Results:**
- The run prints the created output folder path (under `reports/`)
- Main files to open:
  - `model_draft.compmodel`
  - `phase2_final_report.json`

### Basic Command (Minimal Options)

**With OpenAI:**
```bash
python3 run_phase2.py --paper data/papers/your_paper.pdf --output reports
```

**With Gemini:**
```bash
python3 run_phase2.py --paper data/papers/your_paper.pdf --output reports --llm-provider gemini
```

## Command Line Options

**Required:**
- `--paper`: Path to PDF paper file
- `--output`: Base directory for results (optional; folder name is always auto-generated as `{disease}_llm_{openai|gemini}_{timestamp}` when using LLM, e.g. `cholera_llm_openai_20260204_171722`)

**Optional:**
- `--metamodel`: Path to epidemiology metamodel JSON (default: `../phase 1/metamodel_epidemiology.json`)
- `--api-key-file`: Path to API key file (default: `.api_key.txt`)
- `--llm-provider`: LLM provider to use - `openai` or `gemini` (default: `openai`)
- `--phase1-dir`: Path to Phase 1 directory (for quality checks)
- `--prior-models-dir`: Directory with Phase 1 model analysis JSONs (for gap filling)
- `--gold-standard`: Path to gold standard JSON or .compmodel file (for evaluation)
- `--baseline-models-dir`: Directory with baseline .compmodel files (default: `data/baseline_models`)
- `--no-llm`: Disable LLM, use pattern-based extraction only
- `--output-base-dir`: Base directory used when `--output` is not provided (default: `reports`)
- `--llm-compartments-chars`: Max characters of paper text sent to LLM for compartment extraction (default: `50000`)
- `--llm-flows-chars`: Max characters of paper text sent to LLM for flow extraction (default: `80000`)
- `--llm-parameters-chars`: Max characters of paper text sent to LLM for parameter extraction (default: `80000`)
- `--flow-fuzzy-threshold`: Fuzzy threshold for snapping flow endpoints to known compartments (default: `0.78`)

## Limitations

1. **Parameter Values:** Some parameters may have placeholder values if not explicitly stated in paper
2. **Flow Extraction:** Flows can fail or be undercounted when the LLM returns **invalid JSON**—for example, unescaped double quotes or newlines inside the `text_span` field (e.g. long equation quotes). The pipeline now: (a) **repairs** such JSON (escapes inner quotes and newlines in string values), and (b) **salvages** flow objects one-by-one from broken arrays when full parse fails. If you see "LLM flow extraction error" or very few flows, the raw response was malformed; re-runs or different papers often succeed.
3. **Quality Checks:** Phase 1 analyzers may fail if an extracted model cannot be simulated (e.g., missing numeric values or unsupported constructs)

## Next Steps

1. Run Phase 2 on your papers (see `INSTRUCTIONS.md`)
2. Review `model_draft.compmodel` - The extracted model
3. Check `phase2_final_report.json` - Comprehensive results
4. Review gaps and suggestions
5. Compare with baseline models if available (auto-detected from `data/baseline_models/`); see `evaluation_report.json` for precision/recall/F1 on compartments, parameters, and flows
6. (Optional) Run `python3 build_results_md.py` to generate `RESULTS_REPORT.md` with per-disease and average P/R/F1 across the latest runs

## Support

- **`INSTRUCTIONS.md`** - Step-by-step guide with detailed inputs/outputs
- Check error messages in terminal output
- Review generated JSON files for details
