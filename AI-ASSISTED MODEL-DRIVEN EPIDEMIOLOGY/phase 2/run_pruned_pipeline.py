"""Run Phase 2 pipeline on pre-processed paper_sections.json files.

Accepts pre-processed sections from pruned folders and runs the full pipeline
for both OpenAI and Gemini LLMs.
"""

import argparse
import json
import os
import re
import unicodedata
from datetime import datetime
from pathlib import Path
from typing import Dict, Any, Optional, List

from src.extraction.paper_promise_extractor import PaperPromiseExtractor
from src.extraction.entity_extractor import EntityExtractor
from src.extraction.paper_type import detect_paper_type
from src.synthesis.model_synthesizer import ModelSynthesizer
from src.synthesis.traceability import TraceabilityMapper
from src.evaluation.quality_checks import QualityChecker
from src.utils.llm_client import LLMClient
from recall_comparison import RecallComparator, find_baseline_model


def load_pruned_paper(pruned_folder: Path) -> Dict[str, Any]:
    """Load pre-processed paper_sections.json and build pipeline data."""
    sections_file = pruned_folder / "paper_sections.json"
    if not sections_file.exists():
        raise FileNotFoundError(f"paper_sections.json not found in {pruned_folder}")

    with open(sections_file, "r", encoding="utf-8") as f:
        data = json.load(f)

    sections = data.get("sections", {})
    tables = data.get("tables", [])

    full_text_parts = []
    for section_key, section in sections.items():
        heading = section.get("heading", "")
        text = section.get("text", "")
        if heading:
            full_text_parts.append(f"{heading}\n\n{text}")
        else:
            full_text_parts.append(text)
    full_text = "\n\n".join(full_text_parts)
    full_text = unicodedata.normalize("NFC", full_text)

    pages_data = []
    all_text = full_text
    page_size = 3000
    for i in range(0, len(all_text), page_size):
        pages_data.append(
            {
                "page_number": len(pages_data) + 1,
                "text": all_text[i : i + page_size],
                "words": [],
                "bbox": None,
            }
        )

    return {
        "full_text": full_text,
        "sections": sections,
        "tables": tables,
        "pages": pages_data,
        "num_pages": len(pages_data),
        "extraction_method": "pre-processed (pruned)",
        "source_folder": str(pruned_folder),
    }


def run_pipeline_for_llm(
    pdf_data: Dict[str, Any],
    disease_name: str,
    llm_provider: str,
    output_base_dir: Path,
    api_key_file: str,
    metamodel_path: str,
    args: argparse.Namespace,
) -> Path:
    """Run the full pipeline for a single LLM provider."""

    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    folder_name = f"{disease_name}_pruned_{llm_provider}_{timestamp}"
    output_dir = output_base_dir / folder_name
    output_dir.mkdir(parents=True, exist_ok=True)

    print("=" * 80)
    print(f"RUNNING PIPELINE: {disease_name} / {llm_provider}")
    print("=" * 80)

    print(f"Output: {output_dir}")
    print()

    llm_client = LLMClient(api_key_file=api_key_file, provider=llm_provider)
    if llm_client.available:
        print(f"LLM: Enabled ({llm_provider})")
    else:
        print(f"LLM: Not available ({llm_provider})")

    paper_text_file = output_dir / "paper_text.json"
    with open(paper_text_file, "w", encoding="utf-8") as f:
        json.dump(
            {
                "full_text": pdf_data["full_text"],
                "num_pages": pdf_data["num_pages"],
                "source": pdf_data.get("source_folder", ""),
                "extraction_method": pdf_data["extraction_method"],
            },
            f,
            indent=2,
            ensure_ascii=False,
        )

    sections_file = output_dir / "paper_sections.json"
    with open(sections_file, "w", encoding="utf-8") as f:
        json.dump(
            {"sections": pdf_data["sections"], "tables": pdf_data["tables"]},
            f,
            indent=2,
            ensure_ascii=False,
        )

    print(f"Saved paper_text.json and paper_sections.json")

    print("\nStep 2: Extracting Paper Promises (pattern-only)...")
    promise_extractor = PaperPromiseExtractor(
        llm_client=llm_client, metamodel_path=metamodel_path
    )

    promise_text = pdf_data.get("full_text", "")
    promises = promise_extractor.extract(promise_text, use_llm=False)
    promise_extractor.save_promises(promises, output_dir / "paper_promises.json")

    print(f"  Compartments: {len(promises.get('compartments', []))}")
    print(f"  Parameters: {len(promises.get('parameters', []))}")
    print(f"  Model Type: {promises.get('model_type', 'Unknown')}")

    paper_type = detect_paper_type(promise_text, promises)
    print(
        f"  Paper type: vector_borne={paper_type.get('vector_borne', False)}, "
        f"climate={paper_type.get('climate', False)}"
    )

    print("\nStep 3: Extracting Entities...")
    example_models_path = None
    if args.phase1_dir:
        phase1_models_path = Path(args.phase1_dir) / "papers" / "epimde"
        if phase1_models_path.exists():
            example_models_path = str(phase1_models_path)

    experiment = os.environ.get("PHASE2_EXPERIMENT", "").strip()
    entity_extractor = EntityExtractor(
        llm_client=llm_client,
        metamodel_path=metamodel_path,
        example_models_path=example_models_path,
        llm_compartments_chars=args.llm_compartments_chars,
        llm_flows_chars=args.llm_flows_chars,
        llm_parameters_chars=args.llm_parameters_chars,
        flow_fuzzy_threshold=args.flow_fuzzy_threshold,
        paper_type=paper_type,
        experiment=experiment,
    )

    entities = entity_extractor.extract_all(pdf_data, paper_promises=promises)
    entity_extractor.save_entities(entities, output_dir / "extracted_entities.json")

    summary = entities.get("extraction_summary", {})
    print(f"  Compartments: {summary.get('num_compartments', 0)}")
    print(f"  Flows: {summary.get('num_flows', 0)}")
    print(f"  Parameters: {summary.get('num_parameters', 0)}")
    print(f"  Stratifications: {summary.get('num_stratifications', 0)}")
    print(f"  Interventions: {summary.get('num_interventions', 0)}")

    print("\nStep 4: Synthesizing Model (.compmodel)...")
    model_synthesizer = ModelSynthesizer(metamodel_path=metamodel_path)
    model_xml = model_synthesizer.synthesize(entities, promises)

    if model_synthesizer.validate(model_xml):
        print("  Model XML is valid")
    else:
        print("  Model XML validation failed (but saving anyway)")

    model_synthesizer.save_compmodel(model_xml, output_dir / "model_draft.compmodel")
    print(f"  Saved model to: model_draft.compmodel")

    print("\nStep 5: Creating Traceability Mapping...")
    traceability_mapper = TraceabilityMapper()
    traceability = traceability_mapper.create_traceability(
        entities, str(output_dir / "model_draft.compmodel")
    )
    traceability_mapper.save_traceability(
        traceability, output_dir / "traceability.json"
    )

    metrics = traceability.get("coverage_metrics", {})
    print(f"  Total items: {metrics.get('total_items', 0)}")
    print(f"  Items with evidence: {metrics.get('items_with_evidence', 0)}")
    print(f"  Coverage: {metrics.get('coverage_percentage', 0):.1f}%")

    print("\nSteps 6-7: Skipping gap analysis & gap filler...")
    gaps = {
        "missing_compartments": [],
        "missing_parameters": [],
        "missing_stratifications": [],
        "missing_interventions": [],
        "summary": {
            "total_gaps": 0,
            "critical_gaps": 0,
            "high_gaps": 0,
            "medium_gaps": 0,
        },
    }
    gap_suggestions = {"gaps": [], "summary": {"total_gaps": 0, "total_suggestions": 0}}
    with open(output_dir / "phase2_gap_report.json", "w") as f:
        json.dump(gaps, f, indent=2)
    with open(output_dir / "gap_fill_suggestions.json", "w") as f:
        json.dump(gap_suggestions, f, indent=2)
    print("  Saved empty gap reports")

    print("\nStep 8: Running Quality Checks...")
    quality_checker = QualityChecker(phase1_dir=args.phase1_dir)
    quality_results = quality_checker.check_model_quality(
        str(output_dir / "model_draft.compmodel"), disease_name.title()
    )
    quality_checker.save_quality_report(
        quality_results, output_dir / "quality_checks.json"
    )

    status = quality_results.get("status", {})
    print(f"  Model analysis: {status.get('model_analysis', 'unknown')}")
    print(f"  Uncertainty analysis: {status.get('uncertainty_analysis', 'unknown')}")
    print(f"  Sensitivity analysis: {status.get('sensitivity_analysis', 'unknown')}")

    print("\nStep 9: Evaluating Extraction Quality (Recall Only)...")
    comparator = RecallComparator(threshold=args.eval_threshold)

    baseline_path = find_baseline_model(args.baseline_models_dir, disease_name)
    if not baseline_path:
        print("  No baseline model found, skipping evaluation")
        recall_result = {}
    else:
        recall_result = comparator.compare(
            str(output_dir / "model_draft.compmodel"), baseline_path
        )

        with open(output_dir / "recall_evaluation.json", "w") as f:
            json.dump(recall_result, f, indent=2)

        print(
            f"  Compartments Recall: {recall_result['compartments']['recall']:.4f} "
            f"(TP={recall_result['compartments']['tp']}, FN={recall_result['compartments']['fn']})"
        )
        print(
            f"  Flows Recall: {recall_result['flows']['recall']:.4f} "
            f"(TP={recall_result['flows']['tp']}, FN={recall_result['flows']['fn']})"
        )
        print(
            f"  Parameters Recall: {recall_result['parameters']['recall']:.4f} "
            f"(TP={recall_result['parameters']['tp']}, FN={recall_result['parameters']['fn']})"
        )

    print("\n  Skipping Final Report Generator (Recall-only mode)")

    print("\n" + "=" * 80)
    print(f"PIPELINE COMPLETE: {disease_name} / {llm_provider}")
    print(f"Output: {output_dir}")
    print("=" * 80)
    print()

    return output_dir


def main():
    parser = argparse.ArgumentParser(
        description="Run Phase 2 pipeline on pre-processed paper_sections.json",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  # Run on all pruned folders with both LLMs
  python run_pruned_pipeline.py --pruned-dir reports_pruned

  # Run with specific LLM
  python run_pruned_pipeline.py --pruned-dir reports_pruned --llm-providers openai

  # Run specific diseases
  python run_pruned_pipeline.py --pruned-dir reports_pruned --diseases ebola malaria

  # Custom output directory
  python run_pruned_pipeline.py --pruned-dir reports_pruned --output reports_pruned_output
        """,
    )

    parser.add_argument(
        "--pruned-dir",
        type=str,
        default="reports_pruned",
        help="Directory containing pruned paper folders",
    )
    parser.add_argument(
        "--output-dir",
        type=str,
        default="reports_pruned_output",
        help="Base directory for output",
    )
    parser.add_argument(
        "--llm-providers",
        type=str,
        nargs="+",
        choices=["openai", "gemini", "claude"],
        default=["openai", "gemini"],
        help="LLM providers to use (default: openai gemini)",
    )
    parser.add_argument(
        "--diseases",
        type=str,
        nargs="+",
        help="Specific diseases to process (default: all found)",
    )
    parser.add_argument(
        "--api-key-file",
        type=str,
        default=".api_key.txt",
        help="Path to file containing API key",
    )
    parser.add_argument(
        "--metamodel",
        type=str,
        default="../phase 1/metamodel_epidemiology.json",
        help="Path to epidemiology metamodel JSON",
    )
    parser.add_argument(
        "--phase1-dir",
        type=str,
        default="../phase 1",
        help="Path to Phase 1 directory",
    )
    parser.add_argument(
        "--baseline-models-dir",
        type=str,
        default="data/baseline_models",
        help="Directory with baseline .compmodel files",
    )
    parser.add_argument(
        "--llm-compartments-chars",
        type=int,
        default=50000,
        help="Max characters for compartment extraction",
    )
    parser.add_argument(
        "--llm-flows-chars",
        type=int,
        default=80000,
        help="Max characters for flow extraction",
    )
    parser.add_argument(
        "--llm-parameters-chars",
        type=int,
        default=80000,
        help="Max characters for parameter extraction",
    )
    parser.add_argument(
        "--flow-fuzzy-threshold",
        type=float,
        default=0.78,
        help="Fuzzy similarity threshold for flows",
    )
    parser.add_argument(
        "--eval-threshold",
        type=float,
        default=0.70,
        help="Cosine similarity threshold for evaluation",
    )

    args = parser.parse_args()

    pruned_dir = Path(args.pruned_dir)
    if not pruned_dir.exists():
        print(f"Error: Pruned directory not found: {pruned_dir}")
        return 1

    if not args.diseases:
        disease_folders = []
        for entry in sorted(pruned_dir.iterdir()):
            if entry.is_dir() and entry.name.startswith(
                (
                    "dengue",
                    "ebola",
                    "flu",
                    "hiv",
                    "malaria",
                    "measles",
                    "tuberculosis",
                    "zika",
                )
            ):
                disease_folders.append(entry)
    else:
        disease_folders = []
        for disease in args.diseases:
            for entry in sorted(pruned_dir.iterdir()):
                if entry.is_dir() and entry.name.startswith(disease.lower()):
                    disease_folders.append(entry)
                    break

    if not disease_folders:
        print(f"Error: No pruned folders found in {pruned_dir}")
        return 1

    output_base_dir = Path(args.output_dir)
    output_base_dir.mkdir(parents=True, exist_ok=True)

    print("=" * 80)
    print("PRUNED PAPER PIPELINE")
    print("=" * 80)
    print(f"Source: {pruned_dir}")
    print(f"Output: {output_base_dir}")
    print(f"LLMs: {', '.join(args.llm_providers)}")
    print(f"Diseases: {[f.name for f in disease_folders]}")
    print("=" * 80)
    print()

    results = []
    for folder in disease_folders:
        disease_name = folder.name.split("_")[0]

        print(f"\n{'#' * 80}")
        print(f"# DISEASE: {disease_name.upper()}")
        print(f"# FOLDER: {folder.name}")
        print(f"{'#' * 80}")

        try:
            pdf_data = load_pruned_paper(folder)
            print(
                f"Loaded {len(pdf_data['sections'])} sections, {len(pdf_data['tables'])} tables"
            )
        except Exception as e:
            print(f"Error loading {folder}: {e}")
            continue

        for llm_provider in args.llm_providers:
            try:
                output_path = run_pipeline_for_llm(
                    pdf_data=pdf_data,
                    disease_name=disease_name,
                    llm_provider=llm_provider,
                    output_base_dir=output_base_dir,
                    api_key_file=args.api_key_file,
                    metamodel_path=args.metamodel,
                    args=args,
                )
                results.append(
                    {
                        "disease": disease_name,
                        "llm": llm_provider,
                        "output": str(output_path),
                        "status": "success",
                    }
                )
            except Exception as e:
                print(f"Error running {llm_provider}: {e}")
                import traceback

                traceback.print_exc()
                results.append(
                    {
                        "disease": disease_name,
                        "llm": llm_provider,
                        "status": "failed",
                        "error": str(e),
                    }
                )

    print("\n" + "=" * 80)
    print("SUMMARY")
    print("=" * 80)
    success_count = sum(1 for r in results if r["status"] == "success")
    failed_count = len(results) - success_count
    print(f"Total runs: {len(results)}")
    print(f"Success: {success_count}")
    print(f"Failed: {failed_count}")
    print()
    for r in results:
        status_icon = "OK" if r["status"] == "success" else "FAIL"
        print(f"  [{status_icon}] {r['disease']} / {r['llm']}")
        if r["status"] == "success":
            print(f"      -> {r['output']}")
        else:
            print(f"      -> {r.get('error', 'Unknown error')}")

    summary_file = output_base_dir / "run_summary.json"
    with open(summary_file, "w") as f:
        json.dump(
            {
                "pruned_dir": str(pruned_dir),
                "output_dir": str(output_base_dir),
                "llm_providers": args.llm_providers,
                "results": results,
            },
            f,
            indent=2,
        )
    print(f"\nSummary saved to: {summary_file}")

    return 0


if __name__ == "__main__":
    exit(main())
