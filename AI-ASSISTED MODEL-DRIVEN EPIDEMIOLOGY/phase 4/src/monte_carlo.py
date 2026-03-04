"""
Task 9.2: Monte Carlo simulation.

Run ensemble simulations with parameter uncertainty: sample from the
assigned distributions and simulate the model for each sample.
"""

import json
import sys
from pathlib import Path
from typing import Any, Dict, List, Optional

# Phase 1 simulator (same structure as .compmodel)
PHASE4_ROOT = Path(__file__).resolve().parent.parent
PHASE1_UTILS = PHASE4_ROOT.parent / "phase 1" / "utils"
if str(PHASE1_UTILS) not in sys.path:
    sys.path.insert(0, str(PHASE1_UTILS))

try:
    from xml_parser import CompModelParser
    from generic_simulator import GenericModelSimulator
    HAS_SIMULATOR = True
except Exception:
    HAS_SIMULATOR = False
    CompModelParser = None
    GenericModelSimulator = None

try:
    import numpy as np
    HAS_NUMPY = True
except ImportError:
    HAS_NUMPY = False


def sample_parameter(spec: Dict[str, Any], rng=None) -> float:
    """Draw one sample from a parameter distribution spec."""
    if rng is None and HAS_NUMPY:
        rng = np.random.default_rng()
    family = spec.get("family", "uniform")
    low = spec.get("low", 0.0)
    high = spec.get("high", 1.0)
    if low is None:
        low = 0.0
    if high is None:
        high = max(low, 1.0)
    if low > high:
        low, high = high, low

    if family == "uniform":
        if HAS_NUMPY and rng is not None:
            return float(rng.uniform(low, high))
        import random
        return random.uniform(low, high)
    if family == "lognormal" and HAS_NUMPY and rng is not None:
        # Use mid and spread of log-space
        mid = (low + high) / 2
        if mid <= 0:
            return float(rng.uniform(low, high))
        sigma = (np.log(high) - np.log(low)) / (2 * 1.96) if high > 0 else 0.5
        mu = np.log(mid)
        return float(rng.lognormal(mu, sigma))
    return (low + high) / 2


def run_monte_carlo(
    compmodel_path: Path,
    distributions_path: Path,
    n_samples: int = 1000,
    days: int = 200,
    dt: float = 0.1,
    seed: Optional[int] = None,
) -> Dict[str, Any]:
    """
    Run n_samples simulations with parameters drawn from distributions.
    Returns trajectories (time series) and summary percentiles.
    """
    if not HAS_SIMULATOR or not GenericModelSimulator:
        return {
            "error": "Phase 1 simulator not available (add phase 1/utils to path).",
            "trajectories": [],
            "percentiles": {},
        }
    if not HAS_NUMPY:
        return {"error": "numpy required for Monte Carlo.", "trajectories": [], "percentiles": {}}

    with open(distributions_path, "r", encoding="utf-8") as f:
        dist_data = json.load(f)
    param_specs = dist_data.get("parameter_distributions", {})

    rng = np.random.default_rng(seed)
    simulator = GenericModelSimulator(str(compmodel_path))
    comp_names = [c.get("primaryName", "?") for c in simulator.compartments]
    n_comps = len(comp_names)
    all_trajectories = []
    all_param_samples = []

    for _ in range(n_samples):
        params = {}
        for name, spec in param_specs.items():
            params[name] = sample_parameter(spec, rng)
        all_param_samples.append(params)
        try:
            result = simulator.simulate(params, days=days, dt=dt)
            traj = result.get("trajectory", result.get("populations", []))
            if traj is None:
                traj = [[0.0] * n_comps]
            all_trajectories.append(np.asarray(traj))
        except Exception:
            n_steps = int(days / dt) + 1
            all_trajectories.append(np.zeros((n_steps, n_comps)))

    if not all_trajectories:
        return {"trajectories": [], "percentiles": {}, "n_samples": 0}

    arr = np.array(all_trajectories)
    n_steps = arr.shape[1]
    percentiles = {}
    for i, name in enumerate(comp_names):
        if i >= arr.shape[2]:
            break
        series = arr[:, :, i]
        percentiles[name] = {
            "p05": np.percentile(series, 5, axis=0).tolist(),
            "p50": np.percentile(series, 50, axis=0).tolist(),
            "p95": np.percentile(series, 95, axis=0).tolist(),
        }
    time_points = np.linspace(0, days, n_steps).tolist()
    return {
        "n_samples": n_samples,
        "days": days,
        "dt": dt,
        "compartment_names": comp_names,
        "time_points": time_points,
        "percentiles": percentiles,
        "param_samples_summary": {
            name: {
                "mean": float(np.mean([s[name] for s in all_param_samples])),
                "std": float(np.std([s[name] for s in all_param_samples])),
            }
            for name in param_specs
        },
    }
