# Phase 2: Paper Promise Extraction

## Overview

Phase 2 adds the ability to extract what a paper **promises** to model, and then compare the extracted model to those promises. This ensures models are **faithful to the paper** rather than adding arbitrary literature-based components.

## Components Added

### 1. `PaperPromiseExtractor`
Extracts promises from papers (PDF or text) using:
- **LLM-based extraction** (recommended): Uses GPT-4/GPT-3.5 to understand paper context
- **Pattern-based extraction** (fallback): Uses regex patterns to find common elements

### 2. Enhanced `gap_analyzer.py`
**Unified gap analyzer supporting both Phase 1 and Phase 2:**
- Phase 1: Disease-specific rules (default mode)
- Phase 2: Paper-driven analysis (use `--paper-pdf` flag)
- Includes `analyze_gaps_with_paper()` convenience function
- Accepts `PaperPromises` objects for programmatic use

## Installation

### Basic (Pattern-Based Only)
```bash
# No additional dependencies needed
python3 analysis/gap_analysis_with_paper.py model.compmodel "Model Name" --paper-pdf paper.pdf
```

### With LLM Support (Recommended)
```bash
pip install openai
export OPENAI_API_KEY="your-api-key-here"
python3 analysis/gap_analysis_with_paper.py model.compmodel "Model Name" --paper-pdf paper.pdf --use-llm
```

### PDF Extraction (Required for PDF files)
```bash
# Option 1: pdfplumber (recommended, better quality)
pip install pdfplumber

# Option 2: PyPDF2 (fallback)
pip install PyPDF2
```

## Usage

### Example 1: Extract Promises from Paper
```python
from analysis.paper_promise_extractor import PaperPromiseExtractor

# Initialize extractor
extractor = PaperPromiseExtractor(use_llm=True, llm_api_key="your-key")

# Extract from PDF
promises = extractor.extract_from_pdf("paper.pdf")

# Save promises
extractor.save_promises(promises, "promises.json")

print(f"Compartments: {promises.compartments}")
print(f"Stratifications: {promises.stratifications}")
print(f"Parameters: {promises.parameters}")
```

### Example 2: Complete Gap Analysis with Paper
```python
from analysis.gap_analyzer import analyze_gaps_with_paper

# Analyze gaps comparing model to paper promises
report = analyze_gaps_with_paper(
    model_path="model.compmodel",
    model_name="COVID-19",
    paper_path="paper.pdf",
    use_llm=True,
    llm_api_key="your-key"
)

print(f"Total gaps: {report['totalGaps']}")
print(f"Promised by paper: {report['summary']['promisedByPaper']}")
```

### Example 3: Command Line Usage
```bash
# Phase 1: Analyze all models (default)
python3 analysis/gap_analyzer.py

# Phase 2: Pattern-based (no API key needed)
python3 analysis/gap_analyzer.py \
    --model Compartmental/CompartmentalModel/covid.compmodel \
    --name "COVID-19" \
    --paper-pdf papers/epimde/covid.pdf

# Phase 2: LLM-based (more accurate)
python3 analysis/gap_analyzer.py \
    --model Compartmental/CompartmentalModel/covid.compmodel \
    --name "COVID-19" \
    --paper-pdf papers/epimde/covid.pdf \
    --use-llm \
    --api-key "sk-..."
```

## How It Works

### Phase 1 (Before)
```
Model → Disease-Specific Rules → Gap Report
(Only flags what rules say should exist)
```

### Phase 2 (Now)
```
Paper → Extract Promises → Compare to Model → Gap Report
(Only flags what paper actually promises)
```

### Extraction Methods

#### LLM-Based (Recommended)
- Uses GPT-4/GPT-3.5 to understand paper context
- Extracts structured promises from free text
- Handles variations in terminology
- More accurate but requires API key

#### Pattern-Based (Fallback)
- Uses regex patterns to find common terms
- No API key needed
- Faster but less accurate
- Good for testing or when LLM unavailable

## Output

### Promise Extraction Output
```json
{
  "compartments": ["Susceptible", "Exposed", "Infectious", "Recovered"],
  "stratifications": ["age"],
  "parameters": ["transmission_rate", "recovery_rate"],
  "interventions": ["vaccination"],
  "model_type": "SEIR",
  "description": "Age-stratified SEIR model with vaccination"
}
```

### Gap Analysis Output
```json
{
  "modelName": "COVID-19",
  "paperPromises": { ... },
  "totalGaps": 2,
  "gaps": {
    "structuralGaps": [
      {
        "gap": "Missing Promised Compartment: Asymptomatic",
        "description": "Paper promises Asymptomatic compartment but model does not include it",
        "promisedByPaper": true,
        "severity": "high"
      }
    ],
    ...
  },
  "summary": {
    "promisedByPaper": 2,
    ...
  }
}
```

## Benefits

1. **Paper-Faithful**: Only flags gaps that paper actually promises
2. **No Hardcoding**: Works with any disease/model type
3. **LLM-Powered**: Understands context and variations
4. **Fallback Available**: Pattern-based extraction when LLM unavailable
5. **Integration Ready**: Works with existing gap analyzer

## Next Steps

1. **Test with existing papers**: Extract promises from COVID-19, Malaria, HIV papers
2. **Compare results**: See difference between Phase 1 (rules) and Phase 2 (promises)
3. **LLM fine-tuning**: Optionally fine-tune prompts for better extraction
4. **Batch processing**: Process multiple papers at once

## Troubleshooting

### LLM extraction fails
- Check API key is set correctly
- Ensure `openai` package is installed
- Falls back to pattern-based automatically

### PDF extraction fails
- Install `pdfplumber` or `PyPDF2`
- Some PDFs may have poor text extraction (scanned images)

### Pattern extraction misses items
- Pattern-based is less accurate
- Use LLM-based extraction for better results
- Manually review and adjust if needed
