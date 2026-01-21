# Phase 2: Step-by-Step Instructions

## ✨ Recent Improvements

Phase 2 now includes **enhanced LLM integration** with automatic loading of:
- **Metamodel schema** - LLM understands valid compartment/parameter types
- **Phase 1 examples** - LLM learns from successful model patterns
- **LLM model refinement** - Analyzes draft models for improvements
- **Better XML formatting** - .compmodel files now properly formatted

These improvements are **automatic** - just run Phase 2 normally and you'll see:
```
Using metamodel: ../phase 1/metamodel_epidemiology.json
Using example models for context: ../phase 1/papers/epimde
```

See README.md for full details on the improvements.

---

## What You Need to Do

### Step 1: Set Up API Key

**File:** `.api_key.txt` (in the `phase 2` directory)

1. Open `.api_key.txt`
2. Add your OpenAI API key on a new line (without quotes)
3. Save the file

Example:
```
sk-your-actual-api-key-here
```

**Alternative:** Set environment variable:
```bash
export OPENAI_API_KEY="sk-your-api-key-here"
```

### Step 2: Install Dependencies

**Option A: Use Phase 1 Virtual Environment (Recommended)**
```bash
cd "../phase 1"
source venv/bin/activate
pip install pdfplumber openai
cd "../phase 2"
```

**Option B: Install Locally**
```bash
pip install -r requirements.txt
```

### Step 3: Prepare Your Paper

Put your PDF paper in `data/papers/`:

```bash
cp your_paper.pdf data/papers/
```

### Step 4: Run Phase 2

**Basic Command:**
```bash
python run_phase2.py --paper data/papers/your_paper.pdf --output reports/your_paper_name
```

**Full Command (with all options):**
```bash
python run_phase2.py \
    --paper data/papers/your_paper.pdf \
    --output reports/your_paper_name \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"
```

### Step 5: Check Results

Results are in `reports/your_paper_name/`:

```bash
ls reports/your_paper_name/
```

**Main Files to Check:**
- `model_draft.compmodel` - The extracted model (XML)
- `phase2_final_report.json` - Comprehensive report with all results

---

## Detailed Steps: What Happens and What You Get

### Step 1: PDF Pipeline

**What Happens:**
- Extracts text from PDF using pdfplumber
- Cleans text (removes headers/footers, fixes hyphenation)
- Detects sections (Abstract, Methods, Model, etc.)
- Extracts tables

**Input:** PDF file  
**Output:** 
- `paper_text.json` - Cleaned text with page numbers
- `paper_sections.json` - Detected sections and extracted tables

**Progress:** "✓ Extracted X pages, ✓ Detected X sections, ✓ Found X tables"

---

### Step 2: Paper Promises Extraction

**What Happens:**
- Uses patterns to find what paper promises to model
- Uses LLM (if available) to extract promises more accurately
- Identifies: compartments, parameters, stratifications, interventions, model type

**Input:** Cleaned paper text  
**Output:** `paper_promises.json`

**Contents:**
- List of promised compartments
- List of promised parameters
- List of promised stratifications
- List of promised interventions
- Model type (SEIR, SIR, etc.)

**Progress:** "✓ Extracted promises: X compartments, X parameters, etc."

---

### Step 3: Entity Extraction

**What Happens:**
- Extracts compartments with evidence (text span, page, confidence)
- Extracts flows with evidence (source, target, type, rate)
- Extracts parameters with evidence (value, unit, description)
- Extracts stratifications and interventions
- Uses both pattern matching and LLM
- **✨ NEW:** LLM prompts now include metamodel schema and Phase 1 examples for better extraction

**Input:** Paper text, pages, tables  
**Output:** `extracted_entities.json`

**Contents:**
- All compartments with evidence
- All flows with evidence
- All parameters with evidence
- Stratifications and interventions
- Extraction summary (counts)

**Progress:** "✓ Extracted entities: X compartments, X flows, X parameters"

---

### Step 4: Model Synthesis

**What Happens:**
- Maps extracted entities to `.compmodel` XML structure
- Creates compartments from extracted entities
- Creates flows (RateFlow/ContactFlow) and links to parameters
- Creates parameters from extracted entities
- **✨ NEW:** LLM analyzes draft model for improvements
- **✨ NEW:** XML output properly formatted with indentation (no longer all on one line!)
- Validates XML structure

**Input:** Extracted entities  
**Output:** `model_draft.compmodel` (XML file)

**Contents:**
- Compartments with initial populations
- Flows with parameter links (rateParameter/contactRateParameter)
- Parameters with values, units, descriptions
- Valid XML structure

**Progress:** "✓ Model XML is valid, ✓ Saved model to: ..."

---

### Step 5: Traceability

**What Happens:**
- Maps every model element to paper evidence
- Links compartments, flows, parameters to text spans
- Calculates coverage metrics
- Calculates faithfulness metrics

**Input:** Extracted entities, model structure  
**Output:** `traceability.json`

**Contents:**
- Evidence mapping for each entity
- Coverage metrics (total items, items with evidence, %)
- Faithfulness metrics (paper-backed items, %)

**Progress:** "✓ Traceability metrics: Coverage X%, Faithfulness X%"

---

### Step 6: Gap Analysis

**What Happens:**
- Compares paper promises vs extracted model
- Finds missing compartments (promised but not extracted)
- Finds missing parameters (promised but not extracted)
- Finds missing stratifications and interventions
- Categorizes by severity (critical/high/medium)

**Input:** Paper promises, extracted entities  
**Output:** `phase2_gap_report.json`

**Contents:**
- Missing compartments (if any)
- Missing parameters (if any)
- Missing stratifications (if any)
- Missing interventions (if any)
- Gap summary (total, by severity)

**Progress:** "✓ Gap analysis complete: X total gaps (X critical, X high, X medium)"

---

### Step 7: Gap Filler

**What Happens:**
- For each gap, searches paper text for weak signals
- Searches prior models (Phase 1 analysis) for similar gaps
- Uses LLM for domain knowledge suggestions
- **✨ NEW:** LLM prompts now include metamodel context and prior model examples
- Generates suggestions with source and confidence

**Input:** Gap analysis, paper text, prior models  
**Output:** `gap_fill_suggestions.json`

**Contents:**
- For each gap: suggested element, source (paper/prior/domain), confidence, rationale

**Progress:** "✓ Generated suggestions: X total suggestions from X sources"

---

### Step 8: Quality Checks

**What Happens:**
- Runs Phase 1 model analyzer on extracted model
- Runs Phase 1 uncertainty analyzer on parameters
- Attempts sensitivity analysis (may not be runnable)

**Input:** Generated model  
**Output:** `quality_checks.json`

**Contents:**
- Model analysis results (structure, counts)
- Uncertainty analysis results (parameter uncertainty)
- Sensitivity analysis status

**Progress:** "✓ Quality checks complete: Model analysis: completed/failed"

---

### Step 9: Evaluation

**What Happens:**
- Calculates traceability coverage
- Calculates faithfulness
- Analyzes gaps
- Optionally compares to gold standard (if provided)

**Input:** All previous outputs  
**Output:** `evaluation_report.json`

**Contents:**
- Traceability coverage metrics
- Faithfulness metrics
- Gap analysis summary
- Gold standard comparison (if available)

**Progress:** "✓ Evaluation complete: Coverage X%, Faithfulness X%"

---

### Final Step: Final Report Generation

**What Happens:**
- Combines all outputs into one comprehensive report
- Generates executive summary
- Includes all key metrics and results

**Input:** All output files  
**Output:** `phase2_final_report.json` ← **Main report**

**Contents:**
- Executive summary (extraction counts, quality metrics)
- Paper promises
- Extracted entities summary
- Model structure summary
- Traceability metrics
- Gap analysis summary
- Gap suggestions summary
- Evaluation results
- Quality check status

**Progress:** "✓ Final report saved: phase2_final_report.json"

---

## Command Line Options

```bash
python run_phase2.py --help
```

**Required:**
- `--paper`: Path to PDF paper file
- `--output`: Output directory for results

**Optional:**
- `--metamodel`: Path to epidemiology metamodel JSON (default: `../phase 1/metamodel_epidemiology.json`)
- `--api-key-file`: Path to API key file (default: `.api_key.txt`)
- `--phase1-dir`: Path to Phase 1 directory (for quality checks)
- `--prior-models-dir`: Directory with Phase 1 model analysis JSONs (for gap filling)
- `--gold-standard`: Path to gold standard JSON (for evaluation)
- `--no-llm`: Disable LLM, use pattern-based extraction only

---

## Example Workflow

```bash
# 1. Put your paper in data/papers/
cp your_paper.pdf data/papers/

# 2. Run Phase 2
python run_phase2.py \
    --paper data/papers/your_paper.pdf \
    --output reports/your_paper

# 3. Check main results
cat reports/your_paper/phase2_final_report.json | head -50
# or open in a text editor

# 4. Check the extracted model
cat reports/your_paper/model_draft.compmodel
```

---

## Troubleshooting

### "No PDF extraction library found"
```bash
pip install pdfplumber
```

### "LLM not available"
- Check `.api_key.txt` has your API key
- Or set `OPENAI_API_KEY` environment variable

### "Phase 1 analyzer not found"
- This is OK - quality checks will be skipped
- Make sure `--phase1-dir` points to Phase 1 directory if you want quality checks

### "Module not found" errors
- Make sure you're in the `phase 2` directory
- Check that `src/` folder exists with all subdirectories

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
