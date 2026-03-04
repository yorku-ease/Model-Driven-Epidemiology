#!/usr/bin/env python3
"""
Phase 4: Uncertainty quantification.

Uses the general framework (data/general_framework.json) so that similar
diseases abide by the same uncertainty structure: typed parameter distributions,
Monte Carlo simulation, sensitivity analysis, visualization, and report.

Usage:
  # Run on all selected models (from Phase 3 select_best_model.py)
  python run_phase4.py --selected-models "../phase 3/selected_models" --output reports

  # Run on a single model
  python run_phase4.py --model path/to/model_filled.compmodel --output reports/single

  # Fewer samples for quick test
  python run_phase4.py --selected-models "../phase 3/selected_models" --output reports --samples 100
"""

import argparse
import json
import sys
from pathlib import Path

PHASE4_DIR = Path(__file__).resolve().parent
sys.path.insert(0, str(PHASE4_DIR))

from src.distributions import assign_distributions
from src.monte_carlo import run_monte_carlo
from src.sensitivity import run_sensitivity
from src.visualization import plot_uncertainty_bands, plot_sensitivity_tornado
from src.report import generate_report

FRAMEWORK_PATH = PHASE4_DIR / "data" / "general_framework.json"


def run_for_disease(
    disease: str,
    model_path: Path,
    output_dir: Path,
    n_samples: int = 500,
    days: int = 200,
) -> None:
    """Run full Phase 4 pipeline for one disease."""
    output_dir.mkdir(parents=True, exist_ok=True)
    framework_path = FRAMEWORK_PATH if FRAMEWORK_PATH.exists() else None
    if not framework_path:
        print(f"  [WARN] General framework not found: {FRAMEWORK_PATH}")
        return

    # 9.1 Assign parameter distributions
    dist_data = assign_distributions(model_path, framework_path, model_type_hint=None)
    dist_path = output_dir / "parameter_distributions.json"
    with open(dist_path, "w", encoding="utf-8") as f:
        json.dump(dist_data, f, indent=2)
    print(f"  Distributions: {len(dist_data.get('parameter_distributions', {}))} parameters")

    # 9.2 Monte Carlo
    mc_result = run_monte_carlo(model_path, dist_path, n_samples=n_samples, days=days, seed=42)
    mc_path = output_dir / "monte_carlo_results.json"
    with open(mc_path, "w", encoding="utf-8") as f:
        json.dump(mc_result, f, indent=2)
    if "error" in mc_result:
        print(f"  Monte Carlo: {mc_result['error']}")
    else:
        print(f"  Monte Carlo: {mc_result.get('n_samples', 0)} samples")

    # 9.3 Sensitivity
    sens_result = run_sensitivity(model_path, dist_path, days=days, perturb_fraction=0.2)
    sens_path = output_dir / "sensitivity_results.json"
    with open(sens_path, "w", encoding="utf-8") as f:
        json.dump(sens_result, f, indent=2)
    if "error" in sens_result:
        print(f"  Sensitivity: {sens_result['error']}")
    else:
        print(f"  Sensitivity: top 5 = {sens_result.get('most_influential', [])[:5]}")

    # 10.1 Visualize
    has_uncertainty = plot_uncertainty_bands(mc_path, output_dir / "uncertainty_bands.png") if mc_path.exists() else False
    has_tornado = plot_sensitivity_tornado(sens_path, output_dir / "sensitivity_tornado.png") if sens_path.exists() else False

    # 10.2 Report
    generate_report(
        disease,
        output_dir,
        dist_data,
        monte_carlo_data=mc_result if "error" not in mc_result else None,
        sensitivity_data=sens_result if "error" not in sens_result else None,
        has_uncertainty_plot=has_uncertainty,
        has_sensitivity_plot=has_tornado,
    )
    print(f"  Report: {output_dir / 'PHASE4_REPORT.md'}")


def main():
    ap = argparse.ArgumentParser(description="Phase 4: Uncertainty quantification (distributions, Monte Carlo, sensitivity, report)")
    ap.add_argument("--selected-models", type=str, default=None, help="Path to phase 3 selected_models dir (one subdir per disease with model_filled.compmodel)")
    ap.add_argument("--model", type=str, default=None, help="Single .compmodel file (run Phase 4 for this model only)")
    ap.add_argument("--output", type=str, default="reports", help="Output directory for Phase 4 reports")
    ap.add_argument("--samples", type=int, default=500, help="Monte Carlo samples (default 500; use 1000 for full run)")
    ap.add_argument("--days", type=int, default=200, help="Simulation days")
    args = ap.parse_args()

    if not args.selected_models and not args.model:
        ap.error("Specify --selected-models <dir> or --model <path>")

    out_base = Path(args.output)
    if args.model:
        model_path = Path(args.model)
        if not model_path.exists():
            print(f"Error: model not found: {model_path}")
            sys.exit(1)
        disease = model_path.parent.name if model_path.parent.name else model_path.stem
        run_for_disease(disease, model_path, out_base, n_samples=args.samples, days=args.days)
        print("\nPhase 4 complete (single model).")
        return

    selected = Path(args.selected_models)
    if not selected.is_dir():
        print(f"Error: selected-models dir not found: {selected}")
        sys.exit(1)
    diseases = [d.name for d in selected.iterdir() if d.is_dir()]
    model_paths = []
    for d in diseases:
        m = selected / d / "model_filled.compmodel"
        if m.exists():
            model_paths.append((d, m))
    if not model_paths:
        print("No model_filled.compmodel found in any subdir of selected_models.")
        sys.exit(1)
    print(f"Running Phase 4 for {len(model_paths)} diseases...")
    for disease, model_path in sorted(model_paths, key=lambda x: x[0]):
        print(f"\n{'─'*50}\n  {disease}")
        run_for_disease(disease, model_path, out_base / disease, n_samples=args.samples, days=args.days)
    print(f"\n{'='*50}\nPhase 4 complete. Reports: {out_base}")


if __name__ == "__main__":
    main()
