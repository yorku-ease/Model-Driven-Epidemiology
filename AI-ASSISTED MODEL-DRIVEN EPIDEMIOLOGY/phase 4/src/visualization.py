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
    import matplotlib.cm as cm
    HAS_PLOT = True
except ImportError:
    HAS_PLOT = False

# Colour palette for uncertainty band subplots (one colour per compartment)
_BAND_COLOURS = [
    "#2196F3",  # blue
    "#E91E63",  # pink/red
    "#4CAF50",  # green
    "#FF9800",  # orange
    "#9C27B0",  # purple
    "#00BCD4",  # cyan
    "#F44336",  # red
    "#8BC34A",  # light green
    "#FF5722",  # deep orange
    "#3F51B5",  # indigo
    "#009688",  # teal
    "#FFC107",  # amber
]


def plot_uncertainty_bands(
    monte_carlo_path: Path,
    output_path: Path,
    compartments_to_plot: Optional[List[str]] = None,
) -> bool:
    """
    Plot trajectory with 5–50–95 percentile uncertainty bands for ALL compartments.
    Each compartment gets its own subplot arranged in a 2-column grid.
    """
    if not HAS_PLOT:
        return False

    with open(monte_carlo_path, "r", encoding="utf-8") as f:
        data = json.load(f)

    percentiles = data.get("percentiles", {})
    time_points = data.get("time_points", [])
    if not time_points or not percentiles:
        return False

    comp_names = list(percentiles.keys())
    if compartments_to_plot:
        comp_names = [c for c in compartments_to_plot if c in percentiles]
    if not comp_names:
        return False

    n = len(comp_names)
    ncols = 2
    nrows = (n + 1) // ncols

    fig, axes = plt.subplots(nrows, ncols, figsize=(13, 3.2 * nrows), squeeze=False)
    fig.patch.set_facecolor("#F8F9FA")

    for idx, name in enumerate(comp_names):
        row, col = divmod(idx, ncols)
        ax = axes[row][col]
        ax.set_facecolor("#FFFFFF")

        colour = _BAND_COLOURS[idx % len(_BAND_COLOURS)]
        p = percentiles[name]
        p05 = p.get("p05", [])
        p50 = p.get("p50", [])
        p95 = p.get("p95", [])

        if p50:
            ax.fill_between(time_points, p05, p95,
                            alpha=0.25, color=colour, label="5th–95th pct")
            ax.plot(time_points, p50, color=colour, lw=2, label="Median")
            ax.plot(time_points, p05, color=colour, lw=0.8, alpha=0.6, linestyle="--")
            ax.plot(time_points, p95, color=colour, lw=0.8, alpha=0.6, linestyle="--")

        # Shorten long compartment names for the title
        title = name if len(name) <= 30 else name[:28] + "…"
        ax.set_title(title, fontsize=9, fontweight="bold", pad=4)
        ax.set_xlabel("Time (days)", fontsize=8)
        ax.tick_params(labelsize=8)
        ax.spines["top"].set_visible(False)
        ax.spines["right"].set_visible(False)
        ax.legend(fontsize=7, loc="upper right")

    # Hide any unused subplot cells
    for j in range(n, nrows * ncols):
        row, col = divmod(j, ncols)
        axes[row][col].set_visible(False)

    fig.suptitle("Monte Carlo uncertainty bands (1 000 samples, 5th / median / 95th percentile)",
                 fontsize=11, fontweight="bold", y=1.01)
    plt.tight_layout()
    output_path.parent.mkdir(parents=True, exist_ok=True)
    plt.savefig(output_path, dpi=130, bbox_inches="tight")
    plt.close()
    return True


def plot_sensitivity_tornado(
    sensitivity_path: Path,
    output_path: Path,
    top_n: int = 15,
    min_importance: float = 1e-6,
) -> bool:
    """
    Horizontal bar chart of parameter importance (combined_importance).
    Only shows parameters with combined_importance > min_importance.
    Bars are colour-coded by magnitude (dark = most influential).
    Returns False (and skips saving) if no parameter clears the threshold.
    """
    if not HAS_PLOT:
        return False

    with open(sensitivity_path, "r", encoding="utf-8") as f:
        data = json.load(f)

    sens_list = [
        s for s in data.get("sensitivities", [])
        if abs(s.get("combined_importance", 0)) > min_importance
    ][:top_n]

    if not sens_list:
        # All parameters have zero impact — create an informative placeholder instead
        fig, ax = plt.subplots(figsize=(8, 2.5))
        fig.patch.set_facecolor("#F8F9FA")
        ax.set_facecolor("#FFFFFF")
        ax.text(0.5, 0.5,
                "All parameters have negligible sensitivity\n"
                "(model output is insensitive to ±20% parameter perturbation\n"
                "— likely due to missing transmission rates in filled model)",
                ha="center", va="center", fontsize=10, color="#666666",
                transform=ax.transAxes, wrap=True)
        ax.axis("off")
        plt.tight_layout()
        output_path.parent.mkdir(parents=True, exist_ok=True)
        plt.savefig(output_path, dpi=130, bbox_inches="tight")
        plt.close()
        return True

    params = [s["parameter"] for s in sens_list]
    importance = [abs(s["combined_importance"]) for s in sens_list]

    # Shorten long parameter names
    def _short(name: str, n: int = 40) -> str:
        return name if len(name) <= n else name[:n - 1] + "…"

    params_display = [_short(p) for p in params]

    # Colour gradient: most important = deep blue, least = light blue
    max_imp = max(importance) if importance else 1.0
    norm_vals = [v / max_imp for v in importance]
    cmap = cm.get_cmap("Blues")
    colours = [cmap(0.35 + 0.6 * v) for v in norm_vals]

    fig_height = max(3.5, len(params) * 0.45 + 1.2)
    fig, ax = plt.subplots(figsize=(9, fig_height))
    fig.patch.set_facecolor("#F8F9FA")
    ax.set_facecolor("#FFFFFF")

    y_pos = range(len(params))
    bars = ax.barh(y_pos, importance, align="center", color=colours,
                   edgecolor="white", linewidth=0.5, height=0.65)

    # Value labels on bars
    for bar, val in zip(bars, importance):
        ax.text(bar.get_width() + max_imp * 0.01, bar.get_y() + bar.get_height() / 2,
                f"{val:.4f}", va="center", ha="left", fontsize=8, color="#333333")

    ax.set_yticks(y_pos)
    ax.set_yticklabels(params_display, fontsize=9)
    ax.invert_yaxis()  # most influential at top
    ax.set_xlabel("Combined sensitivity  (|Δ peak infections| + |Δ total cases|)  /  baseline",
                  fontsize=9)
    ax.set_title("Parameter sensitivity — one-at-a-time perturbation (±20%)",
                 fontsize=10, fontweight="bold", pad=8)
    ax.spines["top"].set_visible(False)
    ax.spines["right"].set_visible(False)
    ax.tick_params(axis="x", labelsize=8)

    plt.tight_layout()
    output_path.parent.mkdir(parents=True, exist_ok=True)
    plt.savefig(output_path, dpi=130, bbox_inches="tight")
    plt.close()
    return True
