# Phase 2: Automated Model Extraction from Papers

## What is Phase 2?

Phase 2 automatically extracts compartmental epidemiological models from scientific papers. Given a paper PDF, it:

1. Extracts what the paper **promises** to model
2. Extracts detailed model entities (compartments, flows, parameters)
3. Generates a draft `.compmodel` file
4. Identifies gaps (what's missing)
5. Suggests how to fill gaps
6. Evaluates the extracted model quality

## Quick Start

### Step 1: Set Up API Key

1. Open the file `.api_key.txt` in this directory
2. Add your OpenAI API key on a new line (without quotes)
3. Example:
   ```
   sk-your-api-key-here
   ```

**Alternative:** Set environment variable:
```bash
export OPENAI_API_KEY="sk-your-api-key-here"
```

### Step 2: Install Dependencies

```bash
pip install -r requirements.txt
```

Or use the Phase 1 virtual environment:
```bash
cd "../phase 1"
source venv/bin/activate
pip install pdfplumber openai
```

### Step 3: Run Phase 2

```bash
# Basic usage
python run_phase2.py --paper data/papers/EbolaSensitivity.pdf --output reports/ebola

# With all options
python run_phase2.py \
    --paper data/papers/EbolaSensitivity.pdf \
    --output reports/ebola \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"
```

## Project Structure

```
phase 2/
├── .api_key.txt              # Your OpenAI API key (create this)
├── run_phase2.py             # Main pipeline script
├── requirements.txt          # Python dependencies
├── README.md                 # This file
│
├── src/                      # Source code
│   ├── extraction/           # PDF and entity extraction
│   │   ├── pdf_pipeline.py
│   │   ├── paper_promise_extractor.py
│   │   └── entity_extractor.py
│   ├── synthesis/            # Model generation
│   │   ├── model_synthesizer.py
│   │   └── traceability.py
│   ├── analysis/             # Gap analysis and filling
│   │   ├── gap_analyzer.py
│   │   └── gap_filler.py
│   ├── evaluation/           # Quality checks and metrics
│   │   ├── quality_checks.py
│   │   └── evaluator.py
│   └── utils/                # Utilities
│       └── llm_client.py
│
├── data/                     # Input data
│   ├── papers/               # PDF papers to process
│   └── baseline_models/      # Baseline models for comparison
│
├── reports/                   # Output directory
│   └── [paper_name]/         # Results for each paper
│       ├── paper_text.json
│       ├── paper_promises.json
│       ├── extracted_entities.json
│       ├── model_draft.compmodel
│       ├── traceability.json
│       ├── phase2_gap_report.json
│       ├── gap_fill_suggestions.json
│       ├── quality_checks.json
│       └── evaluation_report.json
│
└── docs/                      # Documentation
    ├── PHASE2_PLAN.md        # Detailed plan
    └── ...
```

## What Each Step Does

### Step 1: PDF Pipeline
- Extracts text from PDF
- Cleans text (removes headers, fixes hyphenation)
- Detects sections (Abstract, Methods, Model, etc.)
- Extracts tables

**Output:** `paper_text.json`, `paper_sections.json`

### Step 2: Paper Promises Extraction
- Identifies what the paper promises to model
- Extracts: compartments, parameters, stratifications, interventions, model type

**Output:** `paper_promises.json`

### Step 3: Entity Extraction
- Extracts compartments, flows, parameters with evidence
- Each entity has: text span, page number, confidence, extraction method

**Output:** `extracted_entities.json`

### Step 4: Model Synthesis
- Generates `.compmodel` XML file from extracted entities
- Creates compartments, flows, parameters
- Validates XML structure

**Output:** `model_draft.compmodel`

### Step 5: Traceability
- Maps every model element to paper evidence
- Calculates coverage and faithfulness metrics

**Output:** `traceability.json`

### Step 6: Gap Analysis
- Compares paper promises vs extracted model
- Identifies missing compartments, parameters, stratifications, interventions

**Output:** `phase2_gap_report.json`

### Step 7: Gap Filler
- Suggests how to fill gaps from:
  - Paper text re-examination
  - Prior models (Phase 1 analysis)
  - Domain knowledge (LLM)

**Output:** `gap_fill_suggestions.json`

### Step 8: Quality Checks
- Runs Phase 1 analyzers on extracted model
- Model analysis, uncertainty analysis, sensitivity analysis

**Output:** `quality_checks.json`

### Step 9: Evaluation
- Calculates metrics: traceability coverage, faithfulness, gap metrics
- Optionally compares to gold standard

**Output:** `evaluation_report.json`

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

## Example Workflow

```bash
# 1. Put your paper in data/papers/
cp your_paper.pdf data/papers/

# 2. Run Phase 2
python run_phase2.py \
    --paper data/papers/your_paper.pdf \
    --output reports/your_paper

# 3. Check results
ls reports/your_paper/
cat reports/your_paper/evaluation_report.json
```

## Output Files Explained

| File | Description |
|------|-------------|
| `paper_text.json` | Cleaned paper text with page numbers |
| `paper_sections.json` | Detected sections and extracted tables |
| `paper_promises.json` | What the paper promises to model |
| `extracted_entities.json` | All extracted entities with evidence |
| `model_draft.compmodel` | Generated model XML file |
| `traceability.json` | Evidence mapping and coverage metrics |
| `phase2_gap_report.json` | Missing items (gaps) |
| `gap_fill_suggestions.json` | Suggestions for filling gaps |
| `quality_checks.json` | Phase 1 analyzer results |
| `evaluation_report.json` | Quality metrics and scores |

## Troubleshooting

### "No PDF extraction library found"
```bash
pip install pdfplumber
```

### "LLM not available"
- Check `.api_key.txt` has your API key
- Or set `OPENAI_API_KEY` environment variable

### "Phase 1 analyzer not found"
- Make sure `--phase1-dir` points to Phase 1 directory
- Quality checks will be skipped if analyzers not found

### "No flows extracted"
- This is normal for some papers
- LLM-based extraction helps (enabled by default)
- Check `extracted_entities.json` for details

## Dependencies

See `requirements.txt` for full list. Main dependencies:
- `pdfplumber` - PDF text extraction
- `openai` - LLM API client
- `lxml` - XML processing
- `jsonschema` - JSON validation

## Next Steps

1. **Test with your papers:** Put PDFs in `data/papers/` and run
2. **Compare with baseline:** Compare `model_draft.compmodel` with baseline models
3. **Review gaps:** Check `phase2_gap_report.json` for missing items
4. **Use suggestions:** Review `gap_fill_suggestions.json` for gap fills

## Documentation

- `docs/PHASE2_PLAN.md` - Detailed implementation plan
- `docs/IMPLEMENTATION_STATUS.md` - Current status
- `docs/PHASE2_COMPLETE.md` - Completion summary

## Support

For issues or questions:
1. Check `docs/` for detailed documentation
2. Review error messages in terminal output
3. Check generated JSON files for details
