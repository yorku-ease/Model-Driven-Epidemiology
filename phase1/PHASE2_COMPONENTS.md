# Phase 2 Components and Programmatic Usage

## Which Analyzers Are Used in Phase 2?

### Core Phase 2 Components:

1. **`paper_promise_extractor.py`** - Extracts promises from papers
   - `PaperPromiseExtractor` class
   - Methods: `extract_from_pdf()`, `extract_from_text()`, `extract_with_llm()`, `extract_with_patterns()`

2. **`gap_analyzer.py`** - Analyzes gaps (enhanced for Phase 2)
   - `GapAnalyzer` class (works with `PaperPromises`)
   - `PaperPromises` class
   - `analyze_gaps_with_paper()` function (convenience wrapper)

### Supporting Components (Optional but Recommended):

3. **`model_analyzer.py`** - Analyzes model structure
   - `ModelAnalyzer` class
   - Used to generate reports that gap_analyzer can use (optional, but improves consistency)

## Programmatic Usage (From Another Script)

All analyzers can be used programmatically - you don't need to use command line!

### Example 1: Complete Phase 2 Workflow

```python
from pathlib import Path
from analysis.paper_promise_extractor import PaperPromiseExtractor
from analysis.gap_analyzer import GapAnalyzer, analyze_gaps_with_paper

# Option A: Use convenience function (easiest)
report = analyze_gaps_with_paper(
    model_path="Compartmental/CompartmentalModel/covid.compmodel",
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
    model_path="Compartmental/CompartmentalModel/covid.compmodel",
    model_name="COVID-19",
    paper_promises=promises
)
report = analyzer.generate_gap_report()

# Step 3: Export
analyzer.export_gap_report("reports/gap_reports/covid_with_paper.json")
```

### Example 2: Using Model Analyzer First (Recommended)

```python
from analysis.model_analyzer import ModelAnalyzer
from analysis.gap_analyzer import GapAnalyzer, analyze_gaps_with_paper

# Step 1: Analyze model structure (generates JSON report)
model_analyzer = ModelAnalyzer(
    "Compartmental/CompartmentalModel/covid.compmodel",
    "COVID-19"
)
model_analyzer.export_to_json("reports/model_analysis/covid_19_analysis.json")

# Step 2: Gap analysis (will use the model analysis report if available)
report = analyze_gaps_with_paper(
    model_path="Compartmental/CompartmentalModel/covid.compmodel",
    model_name="COVID-19",
    paper_path="papers/epimde/covid.pdf",
    use_llm=True
)
```

### Example 3: Batch Processing Multiple Models

```python
from pathlib import Path
from analysis.model_analyzer import ModelAnalyzer
from analysis.gap_analyzer import analyze_gaps_with_paper

models_dir = Path("Compartmental/CompartmentalModel")
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

### Example 4: Custom Phase 2 Script

```python
"""
Custom Phase 2 analysis script
"""
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent / 'analysis'))

from paper_promise_extractor import PaperPromiseExtractor
from gap_analyzer import GapAnalyzer, PaperPromises

def analyze_paper_and_model(paper_path, model_path, model_name):
    """Complete Phase 2 analysis"""
    
    # Extract promises
    extractor = PaperPromiseExtractor(use_llm=True, llm_api_key="sk-...")
    promises = extractor.extract_from_pdf(paper_path)
    
    # Save promises for reference
    extractor.save_promises(promises, f"reports/paper_promises/{model_name}_promises.json")
    
    # Analyze gaps
    analyzer = GapAnalyzer(model_path, model_name, paper_promises=promises)
    report = analyzer.generate_gap_report()
    
    # Export gap report
    analyzer.export_gap_report(f"reports/gap_reports/{model_name}_with_paper.json")
    
    return report

# Use it
if __name__ == '__main__':
    report = analyze_paper_and_model(
        "papers/epimde/covid.pdf",
        "Compartmental/CompartmentalModel/covid.compmodel",
        "COVID-19"
    )
    print(f"Found {report['totalGaps']} gaps")
```

## Key Classes and Functions

### PaperPromiseExtractor
```python
extractor = PaperPromiseExtractor(use_llm=True, llm_api_key="sk-...")
promises = extractor.extract_from_pdf("paper.pdf")
promises = extractor.extract_from_text("paper text...")
extractor.save_promises(promises, "output.json")
```

### GapAnalyzer
```python
# With paper promises (Phase 2)
analyzer = GapAnalyzer(
    model_path="model.compmodel",
    model_name="Model Name",
    paper_promises=promises  # PaperPromises object
)

# Without paper promises (Phase 1)
analyzer = GapAnalyzer(
    model_path="model.compmodel",
    model_name="Model Name",
    paper_promises=None  # Uses disease-specific rules
)

report = analyzer.generate_gap_report()
analyzer.export_gap_report("output.json")
```

### ModelAnalyzer
```python
analyzer = ModelAnalyzer("model.compmodel", "Model Name")
summary = analyzer.generate_summary()
analyzer.export_to_json("output.json")
df = analyzer.export_to_dataframe()
```

## Command Line vs Programmatic

**Command Line:**
- Good for: Quick one-off analysis, testing
- Example: `python3 gap_analyzer.py --model model.compmodel --name "Model" --paper-pdf paper.pdf`

**Programmatic:**
- Good for: Batch processing, integration with other scripts, automation
- Example: Import classes and use them in your own script

**Both work!** All analyzers support both command-line and programmatic usage.
