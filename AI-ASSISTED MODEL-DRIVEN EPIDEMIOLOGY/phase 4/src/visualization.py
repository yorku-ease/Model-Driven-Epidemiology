"""
Task 10.1: Visualize uncertainty.

Create plots showing uncertainty bands (from Monte Carlo) and
parameter sensitivity (tornado/bar).
"""

import json
from pathlib import Path
from typing import Any, Dict, List, Optional

try:
    import numpy as np
    import matplotlib
    matplotlib.use("Agg")
    import matplotlib.pyplot as plt
    HAS_PLOT = True
except ImportError:
    HAS_PLOT = False


def plot_uncertainty_bands(
    monte_carlo_path: Path,
    output_path: Path,
    compartments_to_plot: Optional[List[str]] = None,
    max_compartments: int = 6,
) -> bool:
    """
    Plot trajectory with 5–50–95 percentile bands per compartment.
    """
    if not HAS_PLOT:
        return False
    with open(monte_carlo_path, "r", encoding="utf-8") as f:
        data = json.load(f)
    percentiles = data.get("percentiles", {})
    time_points = data.get("time_points", [])
    if not time_points or not percentiles:
        return False
    comp_names = list(percentiles.keys())[:max_compartments]
    if compartments_to_plot:
        comp_names = [c for c in compartments_to_plot if c in percentiles][:max_compartments]
    if not comp_names:
        return False

    n_plots = len(comp_names)
    fig, axes = plt.subplots((n_plots + 1) // 2, min(2, n_plots), squeeze=False, figsize=(10, 2.5 * ((n_plots + 1) // 2)))
    axes = axes.flatten()
    for idx, name in enumerate(comp_names):
        ax = axes[idx]
        p = percentiles[name]
        p05, p50, p95 = p["p05"], p["p50"], p["p95"]
        ax.fill_between(time_points, p05, p95, alpha=0.3)
        ax.plot(time_points, p50, label="median", lw=2)
        ax.set_title(name)
        ax.set_xlabel("Time (days)")
        ax.legend(loc="upper right", fontsize=8)
    for j in range(len(comp_names), len(axes)):
        axes[j].set_visible(False)
    plt.tight_layout()
    output_path.parent.mkdir(parents=True, exist_ok=True)
    plt.savefig(output_path, dpi=120)
    plt.close()
    return True


def plot_sensitivity_tornado(
    sensitivity_path: Path,
    output_path: Path,
    top_n: int = 12,
) -> bool:
    """
    Horizontal bar chart of parameter importance (combined_importance).
    """
    if not HAS_PLOT:
        return False
    with open(sensitivity_path, "r", encoding="utf-8") as f:
        data = json.load(f)
    sens_list = data.get("sensitivities", [])[:top_n]
    if not sens_list:
        return False
    params = [s["parameter"] for s in sens_list]
    importance = [s["combined_importance"] for s in sens_list]
    fig, ax = plt.subplots(figsize=(8, max(4, len(params) * 0.35)))
    y_pos = range(len(params))
    ax.barh(y_pos, importance, align="center", alpha=0.8)
    ax.set_yticks(y_pos)
    ax.set_yticklabels(params, fontsize=9)
    ax.set_xlabel("Combined sensitivity (peak + total cases)")
    ax.set_title("Parameter sensitivity (one-at-a-time)")
    plt.tight_layout()
    output_path.parent.mkdir(parents=True, exist_ok=True)
    plt.savefig(output_path, dpi=120)
    plt.close()
    return True
