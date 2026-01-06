"""
Main runner script for Phase 1 tasks

Executes all Phase 1 analysis tasks in order.
"""
import sys
from pathlib import Path

# Add analysis directory to path
sys.path.insert(0, str(Path(__file__).parent / 'analysis'))
sys.path.insert(0, str(Path(__file__).parent / 'utils'))

from analysis.model_analyzer import main as analyze_models
from analysis.protocol_builder import main as build_protocol
from analysis.required_optional import main as build_required_optional
from analysis.taxonomy_builder import main as build_taxonomies
from analysis.gap_analyzer import main as analyze_gaps
from analysis.uncertainty_analyzer import main as analyze_uncertainty
from analysis.sensitivity_analysis import main as run_sensitivity
from analysis.paper_collection import main as build_paper_collection
from analysis.manual_extraction import main as create_extraction_templates
from analysis.pattern_analyzer import main as analyze_patterns


def main():
    """Run all Phase 1 tasks"""
    print("=" * 80)
    print("PHASE 1: STRUCTURE FIRST - COMPLETE ANALYSIS")
    print("=" * 80)
    print()
    
    tasks = [
        # Task 1: Structure First
        ("Task 1.1: Analyze Current Models", analyze_models),
        ("Task 1.2: Build Extraction Protocol", build_protocol),
        ("Task 1.3: Define Required vs Optional", build_required_optional),
        ("Task 1.4: Build Taxonomies", build_taxonomies),
        # Task 2: Gaps and Uncertainty
        ("Task 2.1: Deep Dive into Malaria Model Gaps", analyze_gaps),
        ("Task 2.2: Quantify Uncertainty in Parameters", analyze_uncertainty),
        ("Task 2.3: Example Sensitivity Analysis", run_sensitivity),
        # Task 3: Paper Collection and Patterns
        ("Task 3.1: Build Paper Collection", build_paper_collection),
        ("Task 3.2: Manual Extraction Templates", create_extraction_templates),
        ("Task 3.3: Analyze Data Flow Patterns", analyze_patterns),
    ]
    
    for task_name, task_func in tasks:
        print(f"\n{'=' * 80}")
        print(f"Running: {task_name}")
        print('=' * 80)
        try:
            task_func()
            print(f"✓ {task_name} completed successfully")
        except Exception as e:
            print(f"✗ {task_name} failed with error: {e}")
            import traceback
            traceback.print_exc()
    
    print("\n" + "=" * 80)
    print("PHASE 1 ANALYSIS COMPLETE")
    print("=" * 80)
    print("\nGenerated reports are in the 'reports' directory:")
    print("  - reports/model_analysis/     (Task 1.1)")
    print("  - reports/protocols/          (Tasks 1.2, 1.3)")
    print("  - reports/taxonomies/         (Task 1.4)")
    print("  - reports/gap_reports/        (Task 2.1)")
    print("  - reports/uncertainty/         (Task 2.2)")
    print("  - reports/sensitivity/        (Task 2.3)")
    print("  - reports/paper_collection/   (Task 3.1)")
    print("  - reports/manual_extraction/  (Task 3.2)")
    print("  - reports/patterns/           (Task 3.3)")


if __name__ == '__main__':
    main()

