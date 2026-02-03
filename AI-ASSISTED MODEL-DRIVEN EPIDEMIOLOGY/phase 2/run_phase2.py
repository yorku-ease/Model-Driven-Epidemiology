"""Main Phase 2 pipeline orchestrator"""

import argparse
import json
import os
import re
from datetime import datetime
from pathlib import Path
from typing import Optional

from src.extraction.pdf_pipeline import PDFPipeline
from src.extraction.paper_promise_extractor import PaperPromiseExtractor
from src.extraction.entity_extractor import EntityExtractor
from src.extraction.text_windows import build_text_window
from src.extraction.paper_type import detect_paper_type
from src.synthesis.model_synthesizer import ModelSynthesizer
from src.synthesis.traceability import TraceabilityMapper
from src.analysis.gap_analyzer import GapAnalyzer
from src.analysis.gap_filler import GapFiller
from src.evaluation.quality_checks import QualityChecker
from src.evaluation.evaluator import Evaluator
from src.evaluation.final_report_generator import FinalReportGenerator
from src.utils.llm_client import LLMClient


def main():
    """Run Phase 2 pipeline"""
    parser = argparse.ArgumentParser(
        description='Phase 2: Extract compartmental models from papers',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  # Process paper with auto-generated folder name (recommended)
  python run_phase2.py --paper EbolaSensitivity.pdf
  # Creates: reports/ebola_llm_openai_20240123_143022/
  
  # Process with Gemini instead of OpenAI
  python run_phase2.py --paper EbolaSensitivity.pdf --llm-provider gemini
  # Creates: reports/ebola_llm_gemini_20240123_143022/
  
  # Process without LLM (pattern-based only)
  python run_phase2.py --paper EbolaSensitivity.pdf --no-llm
  # Creates: reports/ebola_pattern_20240123_143022/
  
  # Process with custom output folder (override auto-generation)
  python run_phase2.py --paper EbolaSensitivity.pdf --output reports/my_custom_folder
  
  # Process with custom API key file
  python run_phase2.py --paper EbolaSensitivity.pdf --api-key-file .api_key.txt
  
  # Process with metamodel for LLM prompts
  python run_phase2.py --paper EbolaSensitivity.pdf --metamodel ../phase 1/metamodel_epidemiology.json
        """
    )
    
    parser.add_argument('--paper', type=str, required=True,
                       help='Path to PDF paper file')
    parser.add_argument('--output', type=str,
                       help='Base directory for output (folder name will be {disease}_{method}_{timestamp})')
    parser.add_argument('--output-base-dir', type=str, default='reports',
                       help='Base directory when --output not provided (default: reports)')
    parser.add_argument('--metamodel', type=str,
                       default='../phase 1/metamodel_epidemiology.json',
                       help='Path to epidemiology metamodel JSON (for LLM prompts)')
    parser.add_argument('--api-key-file', type=str,
                       default='.api_key.txt',
                       help='Path to file containing API key (OpenAI or Gemini)')
    parser.add_argument('--llm-provider', type=str,
                       choices=['openai', 'gemini'],
                       default='openai',
                       help='LLM provider to use: openai or gemini (default: openai)')
    parser.add_argument('--use-llm', action='store_true', default=True,
                       help='Use LLM for extraction (default: True)')
    parser.add_argument('--no-llm', dest='use_llm', action='store_false',
                       help='Disable LLM, use pattern-based only')
    parser.add_argument('--phase1-dir', type=str,
                       default='../phase 1',
                       help='Path to Phase 1 directory (for quality checks)')
    parser.add_argument('--prior-models-dir', type=str,
                       default='../phase 1/reports/model_analysis',
                       help='Directory with Phase 1 model analysis JSONs (for gap filling)')
    parser.add_argument('--gold-standard', type=str,
                       help='Path to gold standard JSON or .compmodel file (for evaluation)')
    parser.add_argument('--baseline-models-dir', type=str,
                       default='data/baseline_models',
                       help='Directory with baseline .compmodel files (auto-detected for evaluation)')

    # LLM context sizing (helps equation-heavy papers)
    parser.add_argument('--llm-compartments-chars', type=int, default=50000,
                       help='Max characters of paper text sent to LLM for compartment extraction (default: 50000)')
    parser.add_argument('--llm-flows-chars', type=int, default=80000,
                       help='Max characters of paper text sent to LLM for flow extraction (default: 80000)')
    parser.add_argument('--llm-parameters-chars', type=int, default=80000,
                       help='Max characters of paper text sent to LLM for parameter extraction (default: 80000)')
    parser.add_argument('--flow-fuzzy-threshold', type=float, default=0.78,
                       help='Fuzzy similarity threshold for snapping flow endpoints to known compartments (default: 0.78)')

    args = parser.parse_args()
    
    # Validate inputs
    paper_path = Path(args.paper)
    if not paper_path.exists():
        print(f"Error: Paper file not found: {paper_path}")
        return 1
    
    # Always generate folder name in format: {disease}_{method}_{timestamp}
    # Extract disease name from paper filename
    paper_stem = paper_path.stem.lower()
    
    # Common disease names to look for
    disease_keywords = {
        'ebola': 'ebola',
        'covid': 'covid',
        'sars-cov': 'covid',
        'coronavirus': 'covid',
        'malaria': 'malaria',
        'hiv': 'hiv',
        'aids': 'hiv',
        'tuberculosis': 'tuberculosis',
        'tb': 'tuberculosis',
        'flu': 'flu',
        'influenza': 'flu',
        'dengue': 'dengue',
        'cholera': 'cholera',
        'measles': 'measles',
        'mumps': 'mumps',
        'rubella': 'rubella',
        'zika': 'zika',
        'yellow fever': 'yellowfever',
        'yellowfever': 'yellowfever'
    }
    
    # Try to find disease name in paper filename
    disease_name = 'unknown'
    for keyword, disease in disease_keywords.items():
        if keyword in paper_stem:
            disease_name = disease
            break
    
    # If not found, try to extract from paper stem (take first meaningful word)
    if disease_name == 'unknown':
        # Remove common prefixes/suffixes
        cleaned = re.sub(r'^(paper|model|analysis|study|thesis|dissertation|report|document)[_\-\s]*', '', paper_stem)
        cleaned = re.sub(r'[_\-\s]+(paper|model|analysis|study|thesis|dissertation|report|document)$', '', cleaned)
        # Take first word or first 10 chars
        first_word = cleaned.split()[0] if cleaned.split() else paper_stem[:10]
        disease_name = re.sub(r'[^a-z0-9]', '', first_word.lower())[:15]  # Limit length
    
    # Determine method
    method = 'llm' if args.use_llm else 'pattern'
    if args.use_llm:
        method = f"{method}_{args.llm_provider}"  # e.g., "llm_gemini" or "llm_openai"
    
    # Generate timestamp
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    
    # Create folder name in format: {disease}_{method}_{timestamp}
    folder_name = f"{disease_name}_{method}_{timestamp}"
    
    # Determine base directory
    if args.output:
        # If output is provided, use it as the parent directory
        base_dir = Path(args.output)
    else:
        # Use default base directory
        base_dir = Path(args.output_base_dir)
    
    # Always create folder with the generated name
    output_dir = base_dir / folder_name
    
    output_dir.mkdir(parents=True, exist_ok=True)
    
    print("=" * 80)
    print("PHASE 2: AUTOMATED MODEL EXTRACTION FROM PAPERS")
    print("=" * 80)
    print(f"Paper: {paper_path}")
    print(f"Output: {output_dir}")
    # Create LLM client once so we can show status and reuse in Steps 2–3
    llm_client = LLMClient(api_key_file=args.api_key_file, provider=args.llm_provider)
    if args.use_llm:
        if llm_client.available:
            print(f"LLM: Enabled ({args.llm_provider})")
        else:
            print(f"LLM: Disabled — no API key for '{args.llm_provider}'. Put key in .api_key.txt as 'openai:sk-...' or 'gemini:AIza...' (or set OPENAI_API_KEY / GEMINI_API_KEY).")
    else:
        print(f"LLM: Disabled (pattern-based only)")
    print()
    
    # Step 1: PDF Pipeline
    print("Step 1: PDF Ingestion and Cleaning...")
    pdf_pipeline = PDFPipeline()
    pdf_data = pdf_pipeline.process_pdf(str(paper_path), str(output_dir))
    print(f"  ✓ Extracted {pdf_data['num_pages']} pages")
    print(f"  ✓ Detected {len([s for s in pdf_data['sections'].values() if s])} sections")
    print(f"  ✓ Found {len(pdf_data['tables'])} tables")
    print()
    
    # Step 2: Paper Promises Extraction
    print("Step 2: Extracting Paper Promises...")
    metamodel_path = None
    if Path(args.metamodel).exists():
        metamodel_path = args.metamodel
        print(f"  Using metamodel: {metamodel_path}")
    
    promise_extractor = PaperPromiseExtractor(
        llm_client=llm_client,
        metamodel_path=metamodel_path
    )

    # Build a smaller, model-focused text window for LLM (avoid dumping full paper)
    promise_text = pdf_data.get("full_text", "")
    try:
        pages_for_window = pdf_data.get("raw_pages") or pdf_data.get("pages") or []
        if pages_for_window:
            promise_text = build_text_window(
                pages_for_window,
                include_patterns=[
                    r"\bmodel\b",
                    r"\bcompartment",
                    r"\bseir\b|\bsir\b|\bsis\b",
                    r"\bparameter",
                    r"\bstratif",
                    r"\bvaccin|\btreat|\bintervention|\bcontrol",
                    r"\bequation|\bdifferential|\bd\/dt|d[a-z]\s*\/\s*dt",
                ],
                title="PROMISES WINDOW (abstract/intro/model/parameters hints)",
                max_chars=25000,
                pad=1,
                max_pages=8,
                fallback_first_pages=4,
            )
    except Exception:
        # Fall back to full_text if anything goes wrong
        promise_text = pdf_data.get("full_text", "")

    promises = promise_extractor.extract(promise_text, use_llm=args.use_llm)
    
    # Save promises
    promise_extractor.save_promises(
        promises,
        output_dir / "paper_promises.json"
    )
    
    print(f"  ✓ Extracted promises:")
    print(f"    - Compartments: {len(promises.get('compartments', []))}")
    print(f"    - Stratifications: {len(promises.get('stratifications', []))}")
    print(f"    - Parameters: {len(promises.get('parameters', []))}")
    print(f"    - Interventions: {len(promises.get('interventions', []))}")
    print(f"    - Model Type: {promises.get('model_type', 'Unknown')}")
    print(f"    - Method: {promises.get('extraction_method', 'unknown')}")
    print()

    # Paper type (vector-borne / climate) auto-detected for prompt tailoring
    paper_type = detect_paper_type(promise_text or pdf_data.get("full_text", ""), promises)
    print(f"  Paper type (auto): vector_borne={paper_type.get('vector_borne', False)}, climate={paper_type.get('climate', False)}")
    print()
    
    # Step 3: Entity Extraction
    print("Step 3: Extracting Entities with Evidence...")
    # Load example models path for entity extraction context
    example_models_path_entity = None
    if args.phase1_dir:
        phase1_models_path = Path(args.phase1_dir) / "papers" / "epimde"
        if phase1_models_path.exists():
            example_models_path_entity = str(phase1_models_path)
            print(f"  Using example models for context: {example_models_path_entity}")

    experiment = os.environ.get("PHASE2_EXPERIMENT", "").strip()
    entity_extractor = EntityExtractor(
        llm_client=llm_client,
        metamodel_path=metamodel_path,
        example_models_path=example_models_path_entity,
        llm_compartments_chars=args.llm_compartments_chars,
        llm_flows_chars=args.llm_flows_chars,
        llm_parameters_chars=args.llm_parameters_chars,
        flow_fuzzy_threshold=args.flow_fuzzy_threshold,
        paper_type=paper_type,
        experiment=experiment,
    )

    entities = entity_extractor.extract_all(pdf_data, paper_promises=promises)
    
    # Save entities
    entity_extractor.save_entities(
        entities,
        output_dir / "extracted_entities.json"
    )
    
    summary = entities.get('extraction_summary', {})
    print(f"  ✓ Extracted entities:")
    print(f"    - Compartments: {summary.get('num_compartments', 0)}")
    print(f"    - Flows: {summary.get('num_flows', 0)}")
    print(f"    - Parameters: {summary.get('num_parameters', 0)}")
    print(f"    - Stratifications: {summary.get('num_stratifications', 0)}")
    print(f"    - Interventions: {summary.get('num_interventions', 0)}")
    print()
    
    # Step 4: Model Synthesis
    print("Step 4: Synthesizing Model (.compmodel)...")
    model_synthesizer = ModelSynthesizer(metamodel_path=metamodel_path)
    
    model_xml = model_synthesizer.synthesize(entities, promises)
    
    # Validate XML
    if model_synthesizer.validate(model_xml):
        print("  ✓ Model XML is valid")
    else:
        print("  ⚠ Model XML validation failed (but saving anyway)")
    
    # Save model
    model_synthesizer.save_compmodel(
        model_xml,
        output_dir / "model_draft.compmodel"
    )
    print(f"  ✓ Saved model to: {output_dir / 'model_draft.compmodel'}")
    print()
    
    # Step 5: Traceability
    print("Step 5: Creating Traceability Mapping...")
    traceability_mapper = TraceabilityMapper()
    
    traceability = traceability_mapper.create_traceability(
        entities,
        str(output_dir / "model_draft.compmodel")
    )
    
    # Save traceability
    traceability_mapper.save_traceability(
        traceability,
        output_dir / "traceability.json"
    )
    
    metrics = traceability.get('coverage_metrics', {})
    print(f"  ✓ Traceability metrics:")
    print(f"    - Total items: {metrics.get('total_items', 0)}")
    print(f"    - Items with evidence: {metrics.get('items_with_evidence', 0)}")
    print(f"    - Coverage: {metrics.get('coverage_percentage', 0):.1f}%")
    print(f"    - Paper-backed: {metrics.get('paper_backed_items', 0)}")
    print(f"    - Faithfulness: {metrics.get('faithfulness_percentage', 0):.1f}%")
    print()
    
    # Step 6: Gap Analysis
    print("Step 6: Analyzing Gaps (Paper Promises vs Extracted Model)...")
    gap_analyzer = GapAnalyzer()
    
    paper_promises = gap_analyzer.load_paper_promises(output_dir / "paper_promises.json")
    extracted_entities = gap_analyzer.load_extracted_entities(output_dir / "extracted_entities.json")
    model_structure = gap_analyzer.load_model_structure(output_dir / "model_draft.compmodel")
    
    gaps = gap_analyzer.analyze_gaps(paper_promises, extracted_entities, model_structure)
    
    # Save gap report
    gap_analyzer.save_gap_report(gaps, output_dir / "phase2_gap_report.json")
    
    summary = gaps.get('summary', {})
    print(f"  ✓ Gap analysis complete:")
    print(f"    - Total gaps: {summary.get('total_gaps', 0)}")
    print(f"    - Critical: {summary.get('critical_gaps', 0)}")
    print(f"    - High: {summary.get('high_gaps', 0)}")
    print(f"    - Medium: {summary.get('medium_gaps', 0)}")
    print()
    
    # Step 7: Gap Filler
    print("Step 7: Generating Gap Fill Suggestions...")
    gap_filler = GapFiller(
        llm_client=llm_client,
        prior_models_dir=args.prior_models_dir
    )
    
    gap_suggestions = gap_filler.fill_gaps(
        gaps,
        pdf_data['full_text'],
        extracted_entities
    )
    
    # Save suggestions
    gap_filler.save_suggestions(gap_suggestions, output_dir / "gap_fill_suggestions.json")
    
    sugg_summary = gap_suggestions.get('summary', {})
    print(f"  ✓ Generated suggestions:")
    print(f"    - Total gaps: {sugg_summary.get('total_gaps', 0)}")
    print(f"    - Total suggestions: {sugg_summary.get('total_suggestions', 0)}")
    sugg_sources = sugg_summary.get('suggestions_by_source', {})
    if sugg_sources:
        print(f"    - By source: {sugg_sources}")
    print()
    
    # Step 8: Quality Checks
    print("Step 8: Running Quality Checks (Phase 1 Analyzers)...")
    quality_checker = QualityChecker(phase1_dir=args.phase1_dir)
    
    # Extract model name from paper path
    model_name = Path(args.paper).stem.replace('_', ' ').title()
    
    quality_results = quality_checker.check_model_quality(
        str(output_dir / "model_draft.compmodel"),
        model_name
    )
    
    # Save quality report
    quality_checker.save_quality_report(quality_results, output_dir / "quality_checks.json")
    
    status = quality_results.get('status', {})
    print(f"  ✓ Quality checks complete:")
    print(f"    - Model analysis: {status.get('model_analysis', 'unknown')}")
    print(f"    - Uncertainty analysis: {status.get('uncertainty_analysis', 'unknown')}")
    print(f"    - Sensitivity analysis: {status.get('sensitivity_analysis', 'unknown')}")
    print()
    
    # Step 9: Evaluation
    print("Step 9: Evaluating Extraction Quality...")
    
    # Auto-detect baseline model if not explicitly provided
    gold_standard_path = args.gold_standard
    if not gold_standard_path:
        # Try to find baseline model matching paper name
        baseline_dir = Path(args.baseline_models_dir)
        if baseline_dir.exists():
            paper_stem = Path(args.paper).stem.lower()
            # Extract keywords from paper name (split by common separators and camelCase)
            paper_normalized = re.sub(r'[_\-\s]+', ' ', paper_stem)
            # Split on camelCase boundaries (lowercase followed by uppercase) and numbers
            paper_keywords = set(re.split(r'[_\-\s]+|(?<=[a-z])(?=[A-Z0-9])|(?<=[0-9])(?=[A-Za-z])', paper_normalized))
            paper_keywords = {k.lower() for k in paper_keywords if k.strip()}
            
            # Look for matching baseline model
            for baseline_file in baseline_dir.glob("*.compmodel"):
                baseline_stem = baseline_file.stem.lower()
                baseline_normalized = re.sub(r'[_\-\s]+', ' ', baseline_stem)
                baseline_keywords = set(re.split(r'[_\-\s]+|(?<=[a-z])(?=[A-Z0-9])|(?<=[0-9])(?=[A-Za-z])', baseline_normalized))
                baseline_keywords = {k.lower() for k in baseline_keywords if k.strip()}
                
                # Check if paper name matches baseline name (fuzzy match)
                # Match if: (1) one contains the other, (2) they share common keywords, or (3) common disease names match
                common_diseases = ['ebola', 'covid', 'malaria', 'hiv', 'flu', 'tuberculosis', 'tb']
                has_common_disease = any(disease in paper_stem and disease in baseline_stem for disease in common_diseases)
                
                if (paper_stem in baseline_stem or baseline_stem in paper_stem or 
                    len(paper_keywords & baseline_keywords) > 0 or has_common_disease):
                    gold_standard_path = str(baseline_file)
                    print(f"  Auto-detected baseline model: {baseline_file.name}")
                    break
    
    evaluator = Evaluator(gold_standard_path=gold_standard_path)
    
    evaluation = evaluator.evaluate(
        extracted_entities,
        traceability,
        gaps
    )
    
    # Save evaluation
    evaluator.save_evaluation(evaluation, output_dir / "evaluation_report.json")
    
    print(f"  ✓ Evaluation complete:")
    trace_cov = evaluation.get('traceability_coverage', {})
    print(f"    - Traceability coverage: {trace_cov.get('coverage_percentage', 0):.1f}%")
    faithfulness = evaluation.get('faithfulness', {})
    print(f"    - Faithfulness: {faithfulness.get('faithfulness_percentage', 0):.1f}%")
    gap_analysis = evaluation.get('gap_analysis', {})
    print(f"    - Total gaps: {gap_analysis.get('total_gaps', 0)}")
    if evaluation.get('gold_standard_comparison'):
        gs_comp = evaluation['gold_standard_comparison']
        comp_metrics = gs_comp.get('compartments', {})
        param_metrics = gs_comp.get('parameters', {})
        flow_metrics = gs_comp.get('flows', {})
        print(f"    - Baseline comparison:")
        print(f"      * Compartments: Precision={comp_metrics.get('precision', 0):.2f}, Recall={comp_metrics.get('recall', 0):.2f}, F1={comp_metrics.get('f1', 0):.2f}")
        print(f"      * Parameters: Precision={param_metrics.get('precision', 0):.2f}, Recall={param_metrics.get('recall', 0):.2f}, F1={param_metrics.get('f1', 0):.2f}")
        if flow_metrics:
            print(f"      * Flows: Precision={flow_metrics.get('precision', 0):.2f}, Recall={flow_metrics.get('recall', 0):.2f}, F1={flow_metrics.get('f1', 0):.2f}")
    print()
    
    # Generate Final Comprehensive Report
    print("Generating Final Comprehensive Report...")
    report_generator = FinalReportGenerator()
    
    model_name = Path(args.paper).stem.replace('_', ' ').title()
    final_report = report_generator.generate_final_report(output_dir, model_name)
    
    # Save final report
    report_generator.save_final_report(final_report, output_dir / "phase2_final_report.json")
    
    print(f"  ✓ Final report saved: phase2_final_report.json")
    print()
    
    print("=" * 80)
    print("Phase 2 Complete! All Steps 1-9 Finished!")
    print("=" * 80)
    print(f"\nResults saved to: {output_dir}")
    print("\nMain Output:")
    print(f"  - model_draft.compmodel (Generated model)")
    print(f"  - phase2_final_report.json (Comprehensive report with all results)")
    print("\nDetailed Files (for reference):")
    print("  - paper_text.json, paper_promises.json, extracted_entities.json")
    print("  - traceability.json, phase2_gap_report.json, gap_fill_suggestions.json")
    print("  - quality_checks.json, evaluation_report.json")
    
    return 0


if __name__ == "__main__":
    exit(main())
