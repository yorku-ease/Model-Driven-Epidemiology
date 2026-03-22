"""
Example: Phase 2 Paper Promise Extraction and Gap Analysis

This example demonstrates how to use Phase 2 features:
1. Extract promises from a paper
2. Compare model to paper promises
3. Generate gap report

Run with:
    python3 analysis/example_phase2_usage.py
"""
import sys
from pathlib import Path

# Phase 1 root (folder that contains ``run_phase1.py``)
sys.path.insert(0, str(Path(__file__).parent.parent))

from analysis.gap_analyzer import analyze_gaps_with_paper
from analysis.paper_promise_extractor import PaperPromiseExtractor

def example_pattern_based():
    """Example using pattern-based extraction (no API key needed)"""
    print("=" * 80)
    print("EXAMPLE: Pattern-Based Extraction (No API Key Needed)")
    print("=" * 80)
    
    # Example paper text (you can replace with actual paper text)
    example_text = """
    This paper presents an age-stratified SEIR model for COVID-19 transmission.
    The model includes Susceptible (S), Exposed (E), Infectious (I), and Recovered (R) compartments.
    Age groups are stratified into: 0-17, 18-64, and 65+ years.
    Key parameters include transmission rate (beta) and recovery rate (gamma).
    We also model vaccination intervention.
    """
    
    # Extract promises
    extractor = PaperPromiseExtractor(use_llm=False)
    promises = extractor.extract_from_text(example_text)
    
    print("\nExtracted Promises:")
    print(f"  Compartments: {list(promises.compartments)}")
    print(f"  Stratifications: {list(promises.stratifications)}")
    print(f"  Parameters: {list(promises.parameters)}")
    print(f"  Interventions: {list(promises.interventions)}")
    print(f"  Model Type: {promises.model_type}")
    print(f"\n  Description: {promises.description}")


def example_with_paper_pdf():
    """Example with actual PDF (requires PDF file)"""
    print("\n" + "=" * 80)
    print("EXAMPLE: Extract from PDF and Analyze Gaps")
    print("=" * 80)
    
    # Paths: gold COVID model + PDF under phase 1/papers/epimde (aligned with Phase 2 baselines)
    phase1_root = Path(__file__).resolve().parent.parent
    model_path = phase1_root / 'papers' / 'epimde' / 'covid.compmodel'
    paper_path = phase1_root / 'papers' / 'epimde' / 'covid.pdf'
    
    if not model_path.exists():
        print(f"\n⚠ Model not found: {model_path}")
        print("Skipping this example.")
        return
    
    if not paper_path.exists():
        print(f"\n⚠ Paper not found: {paper_path}")
        print("Skipping this example.")
        print("To use this, provide a PDF path to a paper.")
        return
    
    print(f"\nModel: {model_path}")
    print(f"Paper: {paper_path}")
    
    # Analyze gaps (without LLM - uses pattern-based extraction)
    try:
        report = analyze_gaps_with_paper(
            model_path=str(model_path),
            model_name="COVID-19",
            paper_path=str(paper_path),
            use_llm=False  # Set to True if you have OpenAI API key
        )
        
        print("\n" + "=" * 80)
        print("GAP ANALYSIS RESULTS")
        print("=" * 80)
        print(f"\nTotal gaps: {report['totalGaps']}")
        print(f"Promised by paper: {report['summary']['promisedByPaper']}")
        
    except Exception as e:
        print(f"\n⚠ Error: {e}")
        print("This might be because:")
        print("  1. PDF extraction library not installed (pip install pdfplumber)")
        print("  2. PDF is corrupted or has no extractable text")


def example_with_llm():
    """Example with LLM (requires API key)"""
    print("\n" + "=" * 80)
    print("EXAMPLE: LLM-Based Extraction")
    print("=" * 80)
    print("\n⚠ This example requires OpenAI API key")
    print("Set OPENAI_API_KEY environment variable or provide --api-key")
    print("\nExample usage:")
    print("  export OPENAI_API_KEY='sk-...'")
    print("  python3 analysis/gap_analyzer.py \\")
    print("      papers/epimde/covid.compmodel \\")
    print("      'COVID-19' \\")
    print("      --paper-pdf papers/epimde/covid.pdf \\")
    print("      --use-llm")


def main():
    """Run all examples"""
    print("\n" + "=" * 80)
    print("PHASE 2: PAPER PROMISE EXTRACTION EXAMPLES")
    print("=" * 80)
    
    # Example 1: Pattern-based (works without any setup)
    example_pattern_based()
    
    # Example 2: With PDF (requires PDF file)
    example_with_paper_pdf()
    
    # Example 3: LLM-based (requires API key)
    example_with_llm()
    
    print("\n" + "=" * 80)
    print("EXAMPLES COMPLETE")
    print("=" * 80)
    print("\nNext steps:")
    print("  1. Install PDF extraction: pip install pdfplumber")
    print("  2. For better results, use LLM: pip install openai")
    print("  3. See PHASE2_PAPER_EXTRACTION.md for full documentation")


if __name__ == '__main__':
    main()
