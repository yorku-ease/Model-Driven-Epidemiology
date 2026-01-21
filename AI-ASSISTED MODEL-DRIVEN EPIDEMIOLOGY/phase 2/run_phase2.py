"""Main Phase 2 pipeline orchestrator"""

import argparse
import json
from pathlib import Path
from typing import Optional

from src.extraction.pdf_pipeline import PDFPipeline
from src.extraction.paper_promise_extractor import PaperPromiseExtractor
from src.extraction.entity_extractor import EntityExtractor
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
  # Process single paper
  python run_phase2.py --paper EbolaSensitivity.pdf --output reports/ebola
  
  # Process with custom API key file
  python run_phase2.py --paper EbolaSensitivity.pdf --output reports/ebola --api-key-file .api_key.txt
  
  # Process with metamodel for LLM prompts
  python run_phase2.py --paper EbolaSensitivity.pdf --output reports/ebola --metamodel ../phase 1/metamodel_epidemiology.json
        """
    )
    
    parser.add_argument('--paper', type=str, required=True,
                       help='Path to PDF paper file')
    parser.add_argument('--output', type=str, required=True,
                       help='Output directory for results')
    parser.add_argument('--metamodel', type=str,
                       default='../phase 1/metamodel_epidemiology.json',
                       help='Path to epidemiology metamodel JSON (for LLM prompts)')
    parser.add_argument('--api-key-file', type=str,
                       default='.api_key.txt',
                       help='Path to file containing OpenAI API key')
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
                       help='Path to gold standard JSON (for evaluation)')
    
    args = parser.parse_args()
    
    # Validate inputs
    paper_path = Path(args.paper)
    if not paper_path.exists():
        print(f"Error: Paper file not found: {paper_path}")
        return 1
    
    output_dir = Path(args.output)
    output_dir.mkdir(parents=True, exist_ok=True)
    
    print("=" * 80)
    print("PHASE 2: AUTOMATED MODEL EXTRACTION FROM PAPERS")
    print("=" * 80)
    print(f"Paper: {paper_path}")
    print(f"Output: {output_dir}")
    print(f"LLM: {'Enabled' if args.use_llm else 'Disabled (pattern-based only)'}")
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
    llm_client = LLMClient(api_key_file=args.api_key_file)
    
    metamodel_path = None
    if Path(args.metamodel).exists():
        metamodel_path = args.metamodel
        print(f"  Using metamodel: {metamodel_path}")
    
    promise_extractor = PaperPromiseExtractor(
        llm_client=llm_client,
        metamodel_path=metamodel_path
    )
    
    promises = promise_extractor.extract(
        pdf_data['full_text'],
        use_llm=args.use_llm
    )
    
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
    
    # Step 3: Entity Extraction
    print("Step 3: Extracting Entities with Evidence...")
    # Load example models path for entity extraction context
    example_models_path_entity = None
    if args.phase1_dir:
        phase1_models_path = Path(args.phase1_dir) / "papers" / "epimde"
        if phase1_models_path.exists():
            example_models_path_entity = str(phase1_models_path)
            print(f"  Using example models for context: {example_models_path_entity}")

    entity_extractor = EntityExtractor(
        llm_client=llm_client,
        metamodel_path=metamodel_path,
        example_models_path=example_models_path_entity
    )

    entities = entity_extractor.extract_all(pdf_data)
    
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
    evaluator = Evaluator(gold_standard_path=args.gold_standard)
    
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
        print(f"    - Gold standard comparison: Available")
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
