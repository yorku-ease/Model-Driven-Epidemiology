# AI-Assisted Model-Driven Epidemiology

A comprehensive framework for analyzing, extracting, and synthesizing compartmental epidemiological models from scientific papers using AI assistance.

## Project Overview

This project consists of three phases:

- **Phase 1**: Analysis of existing `.compmodel` files - structural analysis, gap identification, uncertainty quantification, and sensitivity analysis
- **Phase 2**: Automated extraction of models from PDF papers - uses LLM to extract model components, generates `.compmodel` files, identifies gaps, and suggests improvements
- **Phase 3**: RAG and gap filling - paper database for parameter lookup, gap detection with required vs optional logic, intelligent inference for missing parameters, and human-readable gap reports (see `phase 3/README.md`)

## Project Structure

```
AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/
├── requirements.txt          ← Unified dependencies (install here)
├── README.md                 ← This file (project overview)
│
├── phase 1/                  ← Model Analysis Phase
│   ├── analysis/             ← Analyzers (model, gap, uncertainty, sensitivity)
│   ├── papers/               ← Example .compmodel files and PDFs
│   ├── metamodel_*.json      ← Model schema definitions
│   ├── run_phase1.py         ← Main Phase 1 script
│   └── reports/              ← Phase 1 analysis outputs
│
└── phase 2/                  ← Automated Extraction Phase
    ├── .api_key.txt          ← OpenAI API key (create this)
    ├── src/                  ← Source code
    │   ├── extraction/       ← PDF processing & entity extraction
    │   ├── synthesis/        ← Model generation
    │   ├── analysis/          ← Gap analysis & filling
    │   ├── evaluation/       ← Quality checks
    │   └── utils/             ← LLM client
    ├── data/                 ← Input papers and baseline models
    ├── run_phase2.py         ← Main Phase 2 script
    └── reports/              ← Phase 2 extraction outputs
```

## Setup

### 1. Install Dependencies

All dependencies are unified in the root `requirements.txt`:

```bash
cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY"
pip install -r requirements.txt
```

**Key Dependencies:**
- `pdfplumber` - PDF text extraction (Phase 2)
- `openai` - LLM API client (Phase 2)
- `lxml`, `xmltodict` - XML processing (Phase 1 & 2)
- `pandas`, `numpy` - Data processing
- `matplotlib`, `seaborn` - Visualization (Phase 1)
- `scipy` - Sensitivity analysis (Phase 1)

### 2. Set Up API Key (Phase 2 Only)

For Phase 2 LLM features, create `phase 2/.api_key.txt`:

```bash
cd "phase 2"
echo "sk-your-openai-api-key-here" > .api_key.txt
```

**Alternative:** Set environment variable:
```bash
export OPENAI_API_KEY="sk-your-api-key-here"
```

## Phase 1: Model Analysis

**Purpose:** Analyze existing `.compmodel` files to understand structure, identify gaps, quantify uncertainty, and perform sensitivity analysis.

### What Phase 1 Does

1. **Model Analysis** - Parses `.compmodel` XML, extracts structure (compartments, flows, parameters)
2. **Gap Analysis** - Identifies missing components based on expected patterns
3. **Uncertainty Analysis** - Documents parameter values, sources, literature ranges
4. **Sensitivity Analysis** - Tests how outputs change with parameter variations (Morris, Grid, Random, Sobol methods)

### Inputs

- `.compmodel` XML files (compartmental epidemiological models)
- Optional: Paper PDFs for context

### Outputs

- `model_analysis.json` - Model structure summary
- `gap_report.json` - Missing components
- `uncertainty_analysis.json` - Parameter uncertainty documentation
- `sensitivity_analysis.json` - Sensitivity results

### Running Phase 1

```bash
cd "phase 1"
python run_phase1.py --model path/to/model.compmodel --output reports/model_name
```

## Phase 2: Automated Model Extraction

**Purpose:** Automatically extract compartmental models from scientific paper PDFs using LLM assistance.

### What Phase 2 Does

Phase 2 runs a 9-step pipeline that:
1. Extracts text from PDF papers
2. Identifies what the paper promises to model
3. Extracts model entities (compartments, flows, parameters) with evidence
4. Generates `.compmodel` XML files
5. Maps elements to paper evidence (traceability)
6. Identifies gaps (promised but missing)
7. Suggests how to fill gaps
8. Runs quality checks
9. Evaluates extraction quality

### The 9-Step Pipeline: Detailed Inputs and Outputs

#### Step 1: PDF Pipeline
**Input:** PDF file  
**Process:**
- Extracts text using `pdfplumber`
- Cleans text (removes headers/footers, fixes hyphenation)
- Detects sections (Abstract, Methods, Model, Results, etc.)
- Extracts tables

**Output:**
- `paper_text.json` - Cleaned text with page numbers
- `paper_sections.json` - Detected sections and extracted tables

---

#### Step 2: Paper Promises Extraction
**Input:** Cleaned paper text from Step 1  
**Process:**
- **Pattern-based:** Searches for phrases like "we model", "our model includes", "age-stratified"
- **LLM-based (if available):** Sends paper text to LLM with prompt asking what the paper promises to model

**LLM Input:**
```
System: "You are a scientific paper analyzer. Extract what the paper promises to model."
User: "[Paper text] + Extract: compartments, parameters, stratifications, interventions, model type"
```

**LLM Output:** JSON with:
- `compartments`: List of promised compartments
- `parameters`: List of promised parameters
- `stratifications`: List of promised stratifications
- `interventions`: List of promised interventions
- `model_type`: SEIR, SIR, etc.

**Output:** `paper_promises.json`

---

#### Step 3: Entity Extraction
**Input:** Paper text, pages, tables from Step 1  
**Process:**
- Extracts compartments, flows, parameters, stratifications, interventions
- Uses **both** pattern matching and LLM
- Records evidence (text span, page number, confidence)

**LLM Usage:**

**For Compartments:**
- **LLM Input:** Paper text + metamodel schema + Phase 1 example models + prompt: "Extract all compartments mentioned in this paper"
- **LLM Output:** JSON array of compartments with names, descriptions, text spans
- **What LLM sees:** 
  - Metamodel schema (valid compartment types)
  - Example compartments from Phase 1 models (e.g., "Susceptible", "Infectious", "Recovered")
  - Paper text sections

**For Flows:**
- **LLM Input:** Paper text + compartments list + prompt: "Extract all flows between compartments"
- **LLM Output:** JSON array of flows with source, target, type (RateFlow/ContactFlow), description
- **What LLM sees:**
  - Extracted compartments
  - Paper text with flow descriptions
  - Example flows from Phase 1 models

**For Parameters:**
- **LLM Input:** Paper text + parameter tables + prompt: "Extract all parameters with values, units, descriptions"
- **LLM Output:** JSON array of parameters with name, value, unit, description, text span
- **What LLM sees:**
  - Extracted tables (if any)
  - Paper text with parameter definitions
  - Example parameters from Phase 1 models

**Output:** `extracted_entities.json` with:
- All entities with evidence (text span, page, confidence, extraction method)
- Extraction summary (counts)

---

#### Step 4: Model Synthesis
**Input:** Extracted entities from Step 3  
**Process:**
- Maps entities to `.compmodel` XML structure
- Creates compartments, flows, parameters
- Links flows to parameters using semantic matching (e.g., recovery flows → γ parameter)
- **No LLM used here** - pure rule-based XML generation

**Output:** `model_draft.compmodel` (XML file)

---

#### Step 5: Traceability
**Input:** Extracted entities, model structure  
**Process:**
- Maps every model element to paper evidence
- Links compartments, flows, parameters to text spans
- Calculates coverage and faithfulness metrics
- **No LLM used here** - pure mapping

**Output:** `traceability.json` with evidence mapping and metrics

---

#### Step 6: Gap Analysis
**Input:** Paper promises (Step 2), extracted entities (Step 3)  
**Process:**
- Compares promises vs extracted model
- Finds missing compartments, parameters, stratifications, interventions
- Categorizes by severity (critical/high/medium)
- **No LLM used here** - pure comparison

**Output:** `phase2_gap_report.json` with missing items

---

#### Step 7: Gap Filler
**Input:** Gap analysis, paper text, prior models (Phase 1)  
**Process:**
- For each gap, suggests how to fill it from three sources:
  1. **Paper text re-examination** - Searches for weak signals
  2. **Prior models** - How similar Phase 1 models handle gaps
  3. **Domain knowledge (LLM)** - LLM-based suggestions

**LLM Usage:**
- **LLM Input:** Gap description + paper text + metamodel schema + prior model examples + prompt: "Suggest how to fill this gap based on epidemiological knowledge"
- **LLM Output:** JSON with suggested element, source, confidence, rationale
- **What LLM sees:**
  - The specific gap (e.g., "Missing age stratification")
  - Relevant paper text sections
  - Metamodel schema (valid types)
  - Examples from Phase 1 models showing similar features

**Output:** `gap_fill_suggestions.json` with suggestions for each gap

---

#### Step 8: Quality Checks
**Input:** Generated model from Step 4  
**Process:**
- Runs Phase 1 analyzers on extracted model
- Model analysis, uncertainty analysis, sensitivity analysis
- **No LLM used here** - uses Phase 1 analyzers

**Output:** `quality_checks.json` with Phase 1 analysis results

---

#### Step 9: Evaluation
**Input:** All previous outputs  
**Process:**
- Calculates quality metrics:
  - Traceability coverage (% items with evidence)
  - Faithfulness (% items paper-backed)
  - Gap metrics (total, by severity)
  - Precision/recall (if gold standard provided)
- **No LLM used here** - pure metric calculation

**Output:** `evaluation_report.json` with quality metrics

---

#### Final Step: Final Report Generation
**Input:** All outputs from Steps 1-9  
**Process:**
- Combines everything into one comprehensive report
- **No LLM used here** - pure aggregation

**Output:** `phase2_final_report.json` ← **Main report to check**

---

## LLM Integration Details

### When LLM is Used

LLM is used in **3 steps** of Phase 2:

1. **Step 2: Paper Promises Extraction** (optional, falls back to patterns)
2. **Step 3: Entity Extraction** (always used if API key available)
3. **Step 7: Gap Filling** (for domain knowledge suggestions)

### LLM Input Structure

**System Message:**
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

### LLM Configuration

- **Model:** `gpt-4o-mini` (default, can be changed)
- **Temperature:** `0.3` (low for consistency)
- **Max Tokens:** `2000` (sufficient for most extractions)

### LLM Fallback Behavior

If LLM is unavailable (no API key, network error, etc.):
- **Step 2:** Falls back to pattern-based extraction
- **Step 3:** Falls back to pattern-based extraction (less accurate)
- **Step 7:** Only uses paper text and prior models (no domain knowledge suggestions)

## Phase 3: RAG and Gap Filling

**Purpose:** Enhance Phase 2 outputs with a paper database (RAG) for parameter lookup and intelligent gap filling (AI inference for missing parameters). Implements required vs optional gap logic and human-readable gap reports. See **`phase 3/README.md`** for details.

```bash
cd "phase 3"
python run_phase3.py --phase2-report "../phase 2/reports/<report_dir>" --build-db --output reports/phase3_out
```

---

## Running the Project

### Phase 1: Analyze Existing Models

```bash
cd "phase 1"
python run_phase1.py --model papers/epimde/malaria.compmodel --output reports/malaria
```

### Phase 2: Extract from Papers

```bash
cd "phase 2"
python run_phase2.py --paper data/papers/your_paper.pdf --output reports/your_paper
```

### Phase 3: RAG and Gap Filling

```bash
cd "phase 3"
python run_phase3.py --phase2-report "../phase 2/reports/<report_dir>" --paper-db data/paper_database --build-db --output reports/phase3_out
```

**With all options:**
```bash
python run_phase2.py \
    --paper data/papers/your_paper.pdf \
    --output reports/your_paper \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"
```

## Key Outputs

### Phase 1 Outputs
- Model structure analysis
- Gap reports
- Uncertainty documentation
- Sensitivity analysis results

### Phase 2 Outputs
- **`model_draft.compmodel`** - Extracted model (main output)
- **`phase2_final_report.json`** - Comprehensive report (check this first)
- Detailed JSON files for each step (for reference)

## Understanding the Workflow

1. **Phase 1** analyzes existing models to understand patterns and create examples
2. **Phase 2** uses those examples + LLM to extract new models from papers
3. **Phase 2** identifies gaps and suggests improvements
4. **Phase 2** evaluates quality and generates comprehensive reports

## Key Principles

- **Faithfulness:** Only extract what papers explicitly describe
- **Evidence-Based:** Every entity has text span and page number
- **Traceability:** Every model element links back to paper evidence
- **Gap-Driven:** Identify what's missing and suggest how to fill it

## Documentation

- **`phase 1/README.md`** - Phase 1 details
- **`phase 2/README.md`** - Phase 2 details
- **`phase 2/INSTRUCTIONS.md`** - Step-by-step Phase 2 guide

## Support

- Check error messages in terminal output
- Review generated JSON files for details
- See individual phase READMEs for specific issues
