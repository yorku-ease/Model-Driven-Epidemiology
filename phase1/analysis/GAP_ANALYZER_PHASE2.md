# Gap Analyzer - Phase 2 Ready

## Overview

The `gap_analyzer.py` has been refactored to be generic and ready for Phase 2, where paper promises will be automatically extracted and compared to models.

## Key Changes

### 1. **PaperPromises Class**
Represents what a paper promises to model. In Phase 2, this will be extracted from papers:
```python
promises = PaperPromises(
    compartments=['Asymptomatic', 'Severe'],
    stratifications=['age', 'risk_group'],
    parameters=['transmission_rate', 'recovery_rate'],
    interventions=['vaccination'],
    model_type='SEIR',
    description='Age-stratified SEIR model with vaccination'
)
```

### 2. **Generic Gap Analysis**
`analyze_gaps_generic(promises)` compares model structure to paper promises:
- Missing promised compartments → structural gap
- Missing promised stratifications → stratification gap
- Missing promised parameters → parameter gap
- Missing promised interventions → intervention gap

### 3. **Rule-Based Analysis**
`GapRule` class allows configurable gap detection:
- Can use keyword matching or custom conditions
- Can be loaded from JSON config files
- Supports required/optional rules

### 4. **Disease-Specific Rules (Phase 1 Compatibility)**
`_load_disease_specific_rules()` maintains Phase 1 functionality:
- Still works without paper promises
- Falls back to disease-specific rules
- Can be replaced with rule config files

## Usage

### Phase 1 (Disease-Specific Rules)
```python
analyzer = GapAnalyzer(model_path, model_name, paper_promises=None)
# Uses disease-specific rules
report = analyzer.generate_gap_report()
```

**Command line:**
```bash
python3 gap_analyzer.py  # Analyzes all models with Phase 1 rules
```

### Phase 2 (Paper-Driven)
```python
# Option 1: Extract promises yourself
from paper_promise_extractor import PaperPromiseExtractor
extractor = PaperPromiseExtractor(use_llm=True, llm_api_key="your-key")
promises = extractor.extract_from_pdf("paper.pdf")

analyzer = GapAnalyzer(model_path, model_name, paper_promises=promises)
report = analyzer.generate_gap_report()

# Option 2: Use convenience function (does extraction + analysis)
from gap_analyzer import analyze_gaps_with_paper
report = analyze_gaps_with_paper(
    model_path="model.compmodel",
    model_name="COVID-19",
    paper_path="paper.pdf",
    use_llm=True,
    llm_api_key="your-key"
)
```

**Command line:**
```bash
# Pattern-based extraction
python3 gap_analyzer.py --model model.compmodel --name "Model Name" --paper-pdf paper.pdf

# LLM-based extraction (more accurate)
python3 gap_analyzer.py --model model.compmodel --name "Model Name" \
                        --paper-pdf paper.pdf --use-llm --api-key sk-...
```

## Benefits

1. **Generic**: Works with any disease/model type
2. **Paper-Driven**: Gaps are based on what paper promises, not hardcoded rules
3. **Backward Compatible**: Phase 1 still works without changes
4. **Extensible**: Easy to add new rules or rule sources
5. **Accurate**: Only flags gaps that paper actually promises

## Phase 2 Integration

In Phase 2:
1. Extract paper promises (using LLM to read paper)
2. Pass promises to `GapAnalyzer`
3. Get gaps comparing model to paper promises
4. Optionally add general best-practice rules

This ensures gaps reflect **faithfulness to paper** rather than arbitrary literature standards.
