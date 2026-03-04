"""
Task 9.3: Sensitivity analysis.

Identify the most influential parameters on model outputs using
one-at-a-time perturbations (general framework: same approach for all model types).
"""

import json
import sys
from pathlib import Path
from typing import Any, Dict, List, Optional

PHASE4_ROOT = Path(__file__).resolve().parent.parent
PHASE1_UTILS = PHASE4_ROOT.parent / "phase 1" / "utils"
if str(PHASE1_UTILS) not in sys.path:
    sys.path.insert(0, str(PHASE1_UTILS))

try:
    from generic_simulator import GenericModelSimulator
    HAS_SIMULATOR = True
except Exception:
    HAS_SIMULATOR = False
    GenericModelSimulator = None


def run_sensitivity(
    compmodel_path: Path,
    distributions_path: Path,
    days: int = 200,
    dt: float = 0.1,
    perturb_fraction: float = 0.2,
) -> Dict[str, Any]:
    """
    One-at-a-time sensitivity: for each parameter, run model at low and high
    (point ± perturb_fraction), record change in peak infections and total cases.
    Rank parameters by normalized sensitivity index.
    """
    if not HAS_SIMULATOR or not GenericModelSimulator:
        return {"error": "Phase 1 simulator not available.", "sensitivities": []}

    with open(distributions_path, "r", encoding="utf-8") as f:
        dist_data = json.load(f)
    param_specs = dist_data.get("parameter_distributions", {})

    simulator = GenericModelSimulator(str(compmodel_path))
    baseline_params = {}
    for name, spec in param_specs.items():
        point = spec.get("point_estimate")
        if point is not None:
            baseline_params[name] = point
        else:
            low, high = spec.get("low"), spec.get("high")
            if low is not None and high is not None:
                baseline_params[name] = (low + high) / 2
            else:
                baseline_params[name] = 1.0

    baseline_result = simulator.simulate(baseline_params, days=days, dt=dt)
    baseline_peak = baseline_result.get("peakInfections", 0) or 0.1
    baseline_cases = baseline_result.get("totalCases", 0) or 0.1

    sensitivities = []
    for name, spec in param_specs.items():
        point = baseline_params.get(name, (spec.get("low", 0) + spec.get("high", 1)) / 2)
        if point == 0:
            delta = 0.1
        else:
            delta = abs(point) * perturb_fraction
        low_val = max(spec.get("low", point - delta), 1e-10) if point > 0 else point - delta
        high_val = point + delta

        result_low = simulator.simulate({**baseline_params, name: low_val}, days=days, dt=dt)
        result_high = simulator.simulate({**baseline_params, name: high_val}, days=days, dt=dt)
        peak_low = result_low.get("peakInfections", 0)
        peak_high = result_high.get("peakInfections", 0)
        cases_low = result_low.get("totalCases", 0)
        cases_high = result_high.get("totalCases", 0)

        sens_peak = (peak_high - peak_low) / baseline_peak if baseline_peak else 0
        sens_cases = (cases_high - cases_low) / baseline_cases if baseline_cases else 0
        combined = abs(sens_peak) + abs(sens_cases)
        sensitivities.append({
            "parameter": name,
            "parameter_type": spec.get("parameter_type", "other"),
            "point_estimate": point,
            "low": low_val,
            "high": high_val,
            "peak_infections_sensitivity": sens_peak,
            "total_cases_sensitivity": sens_cases,
            "combined_importance": combined,
        })

    sensitivities.sort(key=lambda x: -abs(x["combined_importance"]))
    return {
        "baseline_peak_infections": baseline_peak,
        "baseline_total_cases": baseline_cases,
        "perturb_fraction": perturb_fraction,
        "sensitivities": sensitivities,
        "most_influential": [s["parameter"] for s in sensitivities[:5]],
    }
