#!/usr/bin/env python3
"""
Phase 4: Uncertainty quantification.

Uses the general framework (data/general_framework.json) so that similar
diseases abide by the same uncertainty structure: typed parameter distributions,
Monte Carlo simulation, sensitivity analysis, visualization, and report.

Usage:
  # Run on all filled models from a Phase 3 showcase directory (recommended)
  python run_phase4.py --showcase-dir "../phase 3/showcase_gemini" --output reports

  # Only a specific fill mode (default: both)
  python run_phase4.py --showcase-dir "../phase 3/showcase_gemini" --mode rag_only --output reports

  # Auto mode: picks best fill strategy per paper based on recall + param gap score
  python run_phase4.py --showcase-dir "../phase 3/showcase_gemini" --mode auto --output reports

  # Legacy: run on Phase 3 selected_models layout (one subdir per disease)
  python run_phase4.py --selected-models "../phase 3/selected_models" --output reports

  # Run on a single model
  python run_phase4.py --model path/to/model_filled.compmodel --output reports/single

  # Fewer samples for quick test
  python run_phase4.py --showcase-dir "../phase 3/showcase_gemini" --output reports --samples 100
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
ALL_MODES = ["rag_only", "llm_only", "both"]


def _run_dir_stem(run_dir: Path) -> str:
    """Extract disease stem from a Phase 3 run directory name.

    e.g. 'covid1_gemini_phase3' → 'covid1'
    """
    name = run_dir.name                        # covid1_gemini_phase3
    stem = name.split("_phase3")[0]            # covid1_gemini
    return "_".join(stem.split("_")[:-1])      # covid1


def _score_mode(run_dir: Path) -> float:
    """
    Score a Phase 3 run directory for auto-mode selection.

    Score = comp_recall + flow_recall − 0.05 * param_gaps_after

    comp/flow recall come from structural_alignment.filled_vs_gold in
    phase3_validation.json; param gaps from len(missing_parameters) in
    phase3_gaps.json.

    Returns -inf if the directory has no usable data.
    """
    val_path = run_dir / "phase3_validation.json"
    gaps_path = run_dir / "phase3_gaps.json"

    if not val_path.exists():
        return float("-inf")

    try:
        val = json.loads(val_path.read_text(encoding="utf-8"))
        sa = val.get("structural_alignment", {})
        fvg = sa.get("filled_vs_gold", {})
        comp_recall = fvg.get("compartments", {}).get("recall") or 0.0
        flow_recall = fvg.get("flows", {}).get("recall") or 0.0
    except Exception:
        return float("-inf")

    param_gaps = 0
    if gaps_path.exists():
        try:
            gd = json.loads(gaps_path.read_text(encoding="utf-8"))
            param_gaps = len(gd.get("missing_parameters", gd.get("gaps", [])))
        except Exception:
            pass

    return comp_recall + flow_recall - 0.05 * param_gaps


def _pick_best_mode(showcase_dir: Path, disease_stem: str) -> tuple[str, Path, Path, float]:
    """
    For a given disease stem, evaluate all three fill modes and return
    (best_mode, model_path, run_dir, score).

    Raises ValueError if no mode has a valid model.
    """
    best_mode = None
    best_score = float("-inf")
    best_model: Path | None = None
    best_run: Path | None = None

    for mode in ALL_MODES:
        # Find the run_dir for this disease stem under this mode
        mode_dir = showcase_dir / mode
        if not mode_dir.is_dir():
            continue
        for run_dir in mode_dir.iterdir():
            if not run_dir.is_dir():
                continue
            if _run_dir_stem(run_dir) != disease_stem:
                continue
            model = run_dir / "model_filled.compmodel"
            if not model.exists():
                continue
            score = _score_mode(run_dir)
            if score > best_score:
                best_score = score
                best_mode = mode
                best_model = model
                best_run = run_dir
            break  # one run_dir per mode per disease

    if best_model is None:
        raise ValueError(f"No valid model found for disease '{disease_stem}' in any mode")
    return best_mode, best_model, best_run, best_score


def _iter_showcase_models(showcase_dir: Path, mode: str = "both"):
    """
    Yield (disease_stem, model_path, run_dir) from a Phase 3 showcase directory.

    When mode == 'auto', scores all three modes and picks the best per paper.
    Otherwise uses the specified mode directory directly.

    Layout: <showcase_dir>/<mode>/<disease>_<provider>_phase3/model_filled.compmodel
    e.g.   showcase_gemini/both/covid1_gemini_phase3/model_filled.compmodel
    """
    if mode == "auto":
        # Collect all disease stems from any available mode directory
        stems: set[str] = set()
        for m in ALL_MODES:
            mode_dir = showcase_dir / m
            if not mode_dir.is_dir():
                continue
            for run_dir in mode_dir.iterdir():
                if run_dir.is_dir() and (run_dir / "model_filled.compmodel").exists():
                    stems.add(_run_dir_stem(run_dir))
        for stem in sorted(stems):
            try:
                best_mode, model, run_dir, score = _pick_best_mode(showcase_dir, stem)
                print(f"  [auto] {stem}: chose '{best_mode}' (score={score:.3f})")
                yield stem, model, run_dir
            except ValueError as e:
                print(f"  [auto] SKIP {stem}: {e}")
        return

    mode_dir = showcase_dir / mode
    if not mode_dir.is_dir():
        print(f"  [WARN] Mode directory not found: {mode_dir}")
        return
    for run_dir in sorted(mode_dir.iterdir()):
        if not run_dir.is_dir():
            continue
        model = run_dir / "model_filled.compmodel"
        if not model.exists():
            continue
        yield _run_dir_stem(run_dir), model, run_dir


def run_for_disease(
    disease: str,
    model_path: Path,
    output_dir: Path,
    n_samples: int = 500,
    days: int = 200,
    context: dict | None = None,
) -> None:
    """Run full Phase 4 pipeline for one disease/paper."""
    output_dir.mkdir(parents=True, exist_ok=True)
    framework_path = FRAMEWORK_PATH if FRAMEWORK_PATH.exists() else None
    if not framework_path:
        print(f"  [WARN] General framework not found: {FRAMEWORK_PATH}")
        return
    context = context or {}

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
        context=context,
    )
    print(f"  Report: {output_dir / 'PHASE4_REPORT.md'}")


def main():
    ap = argparse.ArgumentParser(description="Phase 4: Uncertainty quantification (distributions, Monte Carlo, sensitivity, report)")
    ap.add_argument("--showcase-dir", type=str, default=None,
                    help="Phase 3 showcase directory (e.g. '../phase 3/showcase_gemini'); iterates all <mode>/<disease>_phase3/ runs")
    ap.add_argument("--mode", type=str, default="both",
                    choices=["rag_only", "llm_only", "both", "auto"],
                    help="Which Phase 3 fill mode to use from showcase. "
                         "'auto' picks the best mode per paper using "
                         "score = comp_recall + flow_recall − 0.05×param_gaps (default: both)")
    ap.add_argument("--selected-models", type=str, default=None,
                    help="Legacy: path to selected_models dir with one subdir per disease containing model_filled.compmodel")
    ap.add_argument("--model", type=str, default=None,
                    help="Single .compmodel file (run Phase 4 for this model only)")
    ap.add_argument("--output", type=str, default="reports",
                    help="Output directory for Phase 4 reports")
    ap.add_argument("--samples", type=int, default=500,
                    help="Monte Carlo samples (default 500; use 1000 for full run)")
    ap.add_argument("--days", type=int, default=200,
                    help="Simulation days")
    args = ap.parse_args()

    if not args.showcase_dir and not args.selected_models and not args.model:
        ap.error("Specify --showcase-dir, --selected-models, or --model")

    out_base = Path(args.output)

    # --- Single model mode ---
    if args.model:
        model_path = Path(args.model)
        if not model_path.exists():
            print(f"Error: model not found: {model_path}")
            sys.exit(1)
        disease = model_path.parent.name if model_path.parent.name else model_path.stem
        run_for_disease(disease, model_path, out_base, n_samples=args.samples, days=args.days)
        print("\nPhase 4 complete (single model).")
        return

    # --- Phase 3 showcase mode (recommended) ---
    if args.showcase_dir:
        showcase = Path(args.showcase_dir)
        if not showcase.is_dir():
            print(f"Error: showcase-dir not found: {showcase}")
            sys.exit(1)
        model_paths = list(_iter_showcase_models(showcase, mode=args.mode))
        if not model_paths:
            hint = "any mode" if args.mode == "auto" else showcase / args.mode
            print(f"No model_filled.compmodel found under {hint}/")
            sys.exit(1)
        mode_label = args.mode
        print(f"Running Phase 4 for {len(model_paths)} papers (mode={mode_label})...")
        for disease, model_path, run_dir in sorted(model_paths, key=lambda x: x[0]):
            # Infer actual mode from run_dir's parent when using auto
            actual_mode = run_dir.parent.name if args.mode == "auto" else args.mode
            print(f"\n{'─'*50}\n  {disease}  [mode={actual_mode}]")
            # Load Phase 3 source info for report context
            source_json = run_dir / "phase3_showcase_source.json"
            context: dict = {"phase3_run_dir": str(run_dir), "fill_mode": actual_mode}
            if source_json.exists():
                try:
                    context.update(json.loads(source_json.read_text(encoding="utf-8")))
                except Exception:
                    pass
            run_for_disease(disease, model_path, out_base / disease,
                            n_samples=args.samples, days=args.days, context=context)
        print(f"\n{'='*50}\nPhase 4 complete. Reports: {out_base}")
        return

    # --- Legacy selected-models mode ---
    selected = Path(args.selected_models)
    if not selected.is_dir():
        print(f"Error: selected-models dir not found: {selected}")
        sys.exit(1)
    model_paths_legacy = []
    for d in sorted(selected.iterdir()):
        if d.is_dir():
            m = d / "model_filled.compmodel"
            if m.exists():
                model_paths_legacy.append((d.name, m))
    if not model_paths_legacy:
        print("No model_filled.compmodel found in any subdir of selected_models.")
        sys.exit(1)
    print(f"Running Phase 4 for {len(model_paths_legacy)} diseases (selected-models mode)...")
    for disease, model_path in model_paths_legacy:
        print(f"\n{'─'*50}\n  {disease}")
        run_for_disease(disease, model_path, out_base / disease, n_samples=args.samples, days=args.days)
    print(f"\n{'='*50}\nPhase 4 complete. Reports: {out_base}")


if __name__ == "__main__":
    main()
