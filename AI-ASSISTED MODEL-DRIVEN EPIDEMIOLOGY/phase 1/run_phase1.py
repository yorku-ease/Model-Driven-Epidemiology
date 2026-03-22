"""
Main runner script for Phase 1 tasks

Executes all Phase 1 analysis tasks in order.
"""
import sys
from pathlib import Path

# Phase 1 root on path so ``from analysis.*`` and ``from utils.*`` resolve from any CWD
_PHASE1_ROOT = Path(__file__).resolve().parent
if str(_PHASE1_ROOT) not in sys.path:
    sys.path.insert(0, str(_PHASE1_ROOT))

from analysis.model_analyzer import main as analyze_models
from analysis.gap_analyzer import main as analyze_gaps
from analysis.uncertainty_analyzer import main as analyze_uncertainty
from analysis.sensitivity_analysis import main as run_sensitivity
from analysis.paper_collection import main as build_paper_collection


def main():
    """Run all Phase 1 tasks"""
    print("=" * 80)
    print("PHASE 1: STRUCTURE FIRST - COMPLETE ANALYSIS")
    print("=" * 80)
    print()
    
    tasks = [
        # Task 1: Structure First
        ("Task 1.1: Analyze Current Models", analyze_models),
        # Task 2: Gaps and Uncertainty
        ("Task 2.1: Deep Dive into Malaria Model Gaps", analyze_gaps),
        ("Task 2.2: Quantify Uncertainty in Parameters", analyze_uncertainty),
        ("Task 2.3: Example Sensitivity Analysis", run_sensitivity),
        # Task 3: Paper Collection
        ("Task 3.1: Build Paper Collection", build_paper_collection),
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
    print("  - reports/gap_reports/        (Task 2.1)")
    print("  - reports/uncertainty/        (Task 2.2)")
    print("  - reports/sensitivity/        (Task 2.3)")
    print("  - reports/paper_collection/   (Task 3.1)")
    print("\nOptional static folders (NOT created by this script):")
    print("  reports/protocols/, reports/taxonomies/, reports/patterns/,")
    print("  reports/manual_extraction/ — restore from backup or git if you use them.")
    print("Catalog of all report outputs: reports/REPORTS.md")


if __name__ == '__main__':
    main()

