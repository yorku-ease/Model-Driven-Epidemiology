#!/usr/bin/env python3
"""
Phase RLM - Agentic Repair Loop with Self-Correction

Main entry point for running the repair system on Phase 2 disease models.

Usage:
    python run_phase_rlm.py <phase2_report_dir>
    python run_phase_rlm.py --disease measles --provider gemini

Example:
    python run_phase_rlm.py "../phase 2/reports/measles_llm_gemini_20260312_165901"
"""

import argparse
import json
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent))

from src.repair_loop import run_repair
from src.section_classifier import SectionClassifier
from src.structural_validator import StructuralValidator


def load_config(config_path: Path = None) -> dict:
    """Load repair configuration."""
    if config_path is None:
        config_path = Path(__file__).parent / "configs" / "repair_config.json"

    with open(config_path) as f:
        return json.load(f)


def find_phase2_report(disease: str, provider: str = None) -> Path:
    """Find a Phase 2 report directory."""
    reports_dir = Path(__file__).parent.parent / "phase 2" / "reports"

    if not reports_dir.exists():
        reports_dir = Path("../phase 2/reports")

    pattern = f"{disease.lower()}_llm_*"

    if provider:
        pattern = f"{disease.lower()}_llm_{provider}_*"

    candidates = list(reports_dir.glob(pattern))

    if not candidates:
        raise FileNotFoundError(f"No Phase 2 report found for {disease}")

    candidates.sort(key=lambda x: x.stat().st_mtime, reverse=True)

    return candidates[0]


def validate_and_report(compmodel_path: Path) -> dict:
    """Run validator and return report."""
    validator = StructuralValidator()
    return validator.validate(compmodel_path)


def main():
    parser = argparse.ArgumentParser(
        description="Phase RLM: Agentic Repair Loop for Disease Models"
    )

    parser.add_argument(
        "report_dir",
        nargs="?",
        help="Path to Phase 2 report directory (contains model_draft.compmodel and paper_sections.json)",
    )

    parser.add_argument(
        "--disease",
        "-d",
        help="Disease name (e.g., measles, hiv, malaria)",
    )

    parser.add_argument(
        "--provider",
        "-p",
        choices=["openai", "gemini", "claude"],
        help="LLM provider used in Phase 2",
    )

    parser.add_argument(
        "--config",
        "-c",
        help="Path to custom repair config JSON",
    )

    parser.add_argument(
        "--output",
        "-o",
        help="Output directory for repaired model",
    )

    parser.add_argument(
        "--validate-only",
        action="store_true",
        help="Only run validation, no repair",
    )

    args = parser.parse_args()

    config = load_config(Path(args.config) if args.config else None)

    if args.report_dir:
        report_dir = Path(args.report_dir)
    elif args.disease:
        report_dir = find_phase2_report(args.disease, args.provider)
    else:
        parser.print_help()
        sys.exit(1)

    if not report_dir.exists():
        print(f"Error: Report directory not found: {report_dir}")
        sys.exit(1)

    compmodel_path = report_dir / "model_draft.compmodel"
    paper_sections_path = report_dir / "paper_sections.json"

    if not compmodel_path.exists():
        print(f"Error: model_draft.compmodel not found in {report_dir}")
        sys.exit(1)

    if not paper_sections_path.exists():
        print(f"Warning: paper_sections.json not found, will use empty sections")
        paper_sections_path = None

    print(f"\n{'=' * 60}")
    print(f"Phase RLM - Agentic Repair Loop")
    print(f"{'=' * 60}")
    print(f"Report directory: {report_dir}")
    print(f"Model: {compmodel_path}")
    print(f"Sections: {paper_sections_path}")
    print(f"Provider: {config.get('llm_provider', 'gemini')}")
    print(f"{'=' * 60}\n")

    print("[0/5] Pre-validation check...")
    initial_result = validate_and_report(compmodel_path)
    print(f"  Initial errors: {initial_result['total_errors']}")

    if args.validate_only:
        print("\n=== VALIDATION RESULTS ===")
        print(json.dumps(initial_result, indent=2))
        sys.exit(0)

    if initial_result["total_errors"] == 0:
        print("\n✓ Model has no structural errors. No repair needed.")
        sys.exit(0)

    if paper_sections_path is None:
        print("\nError: paper_sections.json required for repair")
        sys.exit(1)

    result = run_repair(
        compmodel_path=compmodel_path,
        paper_sections_path=paper_sections_path,
        config=config,
    )

    output_dir = Path(args.output) if args.output else report_dir

    output_dir.mkdir(parents=True, exist_ok=True)

    repaired_model_path = output_dir / "model_repaired.compmodel"
    repaired_model_path.write_text(result["repaired_model_xml"], encoding="utf-8")
    print(f"\n✓ Repaired model saved to: {repaired_model_path}")

    report_path = output_dir / "repair_report.json"
    with open(report_path, "w") as f:
        json.dump(result["report"], f, indent=2)
    print(f"✓ Repair report saved to: {report_path}")

    final_validation_path = output_dir / "final_validation.json"
    with open(final_validation_path, "w") as f:
        json.dump(result["final_validation"], f, indent=2)
    print(f"✓ Final validation saved to: {final_validation_path}")

    print(f"\n{'=' * 60}")
    print(f"SUMMARY")
    print(f"{'=' * 60}")
    print(f"Initial errors:  {result['report']['errors_found_initially']}")
    print(f"Errors repaired: {result['report']['errors_repaired']}")
    print(f"Errors remaining: {result['report']['errors_remaining']}")
    print(f"Pass rate:       {result['report']['validator_pass_rate']:.1%}")
    print(f"Model valid:     {result['report']['final_model_valid']}")
    print(f"{'=' * 60}")


if __name__ == "__main__":
    main()
