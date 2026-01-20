# Phase 2: Automated Model Extraction from Papers

## Overview

Phase 2 automatically extracts compartmental epidemiological models from scientific papers. Given a paper PDF, it extracts the model structure, generates a `.compmodel` file, identifies gaps, suggests gap fills, and evaluates the quality.

**What it does:**
- Reads PDF papers
- Extracts compartments, flows, parameters, stratifications, interventions
- Generates `.compmodel` XML files
- Identifies what's missing (gaps)
- Suggests how to fill gaps
- Evaluates extraction quality

**Key Principle:** Faithfulness to the paper - only extracts what papers explicitly describe or promise.

## Project Structure

```
phase 2/
├── .api_key.txt              ← Your OpenAI API key goes here
├── run_phase2.py             ← Main script to run
├── requirements.txt          ← Python dependencies
├── README.md                 ← This file (explains everything)
├── INSTRUCTIONS.md           ← Step-by-step how to run
│
├── src/                      ← Source code (organized by function)
│   ├── extraction/           ← PDF processing & entity extraction
│   ├── synthesis/            ← Model generation & traceability
│   ├── analysis/             ← Gap analysis & gap filling
│   ├── evaluation/           ← Quality checks & final report
│   └── utils/                ← LLM client utilities
│
├── data/
│   ├── papers/               ← Put your PDF papers here
│   └── baseline_models/      ← Baseline models for comparison
│
└── reports/                  ← Output directory (auto-generated)
    └── [paper_name]/
        ├── model_draft.compmodel      ← Main output: extracted model
        ├── phase2_final_report.json   ← Comprehensive report
        └── [10 detailed JSON files]   ← Detailed results for reference
```

## How It Works: The 9-Step Pipeline

Phase 2 runs 9 steps automatically:

### Step 1: PDF Pipeline
**Input:** PDF file  
**Process:** Extracts text, cleans it, detects sections, extracts tables  
**Output:** `paper_text.json`, `paper_sections.json`

### Step 2: Paper Promises Extraction
**Input:** Cleaned paper text  
**Process:** Identifies what the paper promises to model (compartments, parameters, etc.)  
**Output:** `paper_promises.json`

### Step 3: Entity Extraction
**Input:** Paper text, pages, tables  
**Process:** Extracts compartments, flows, parameters, stratifications, interventions with evidence  
**Output:** `extracted_entities.json`

### Step 4: Model Synthesis
**Input:** Extracted entities  
**Process:** Generates `.compmodel` XML file from entities  
**Output:** `model_draft.compmodel`

### Step 5: Traceability
**Input:** Extracted entities, model structure  
**Process:** Maps every model element to paper evidence  
**Output:** `traceability.json`

### Step 6: Gap Analysis
**Input:** Paper promises, extracted entities  
**Process:** Compares promises vs extracted model, finds missing items  
**Output:** `phase2_gap_report.json`

### Step 7: Gap Filler
**Input:** Gap analysis, paper text, prior models  
**Process:** Suggests how to fill gaps from paper/prior models/domain knowledge  
**Output:** `gap_fill_suggestions.json`

### Step 8: Quality Checks
**Input:** Generated model  
**Process:** Runs Phase 1 analyzers (model analysis, uncertainty, sensitivity)  
**Output:** `quality_checks.json`

### Step 9: Evaluation
**Input:** All previous outputs  
**Process:** Calculates quality metrics (coverage, faithfulness, gaps)  
**Output:** `evaluation_report.json`

### Final Step: Final Report Generation
**Input:** All outputs  
**Process:** Combines everything into one comprehensive report  
**Output:** `phase2_final_report.json` ← **Main report to check**

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
- **Extraction Quality:** Precision/recall (if gold standard available)

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
11. `evaluation_report.json` - Quality metrics

## Dependencies

See `requirements.txt`. Main dependencies:
- `pdfplumber` - PDF text extraction
- `openai` - LLM API client
- `lxml` - XML processing

## Configuration

### API Key Setup
1. Open `.api_key.txt`
2. Add your OpenAI API key on a new line
3. Save

**Alternative:** Set environment variable:
```bash
export OPENAI_API_KEY="sk-your-api-key-here"
```

## Limitations

1. **Parameter Values:** Some parameters may have placeholder values if not explicitly stated in paper
2. **Flow Extraction:** May miss some flows depending on paper format
3. **Quality Checks:** Phase 1 analyzers may not work if incompatible

## Next Steps

1. Run Phase 2 on your papers (see `INSTRUCTIONS.md`)
2. Review `model_draft.compmodel` - The extracted model
3. Check `phase2_final_report.json` - Comprehensive results
4. Review gaps and suggestions
5. Compare with baseline models if available

## Support

- **`INSTRUCTIONS.md`** - Step-by-step guide with inputs/outputs
- Check error messages in terminal output
- Review generated JSON files for details
