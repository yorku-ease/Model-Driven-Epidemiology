"""Render DOT to PNG/SVG using the system Graphviz ``dot`` command (when available)."""

from __future__ import annotations

import shutil
import subprocess
from pathlib import Path
from typing import Any, Dict, List, Tuple


def dot_executable() -> str | None:
    return shutil.which("dot")


def render_dot_to_images(
    dot_path: Path,
    out_dir: Path,
    basename: str = "feature_tree",
    dpi: int = 196,
) -> Tuple[Dict[str, Any], List[str]]:
    """Write ``basename``.png and ``basename``.svg under ``out_dir``."""

    exe = dot_executable()
    warnings: List[str] = []
    png = out_dir / f"{basename}.png"
    svg = out_dir / f"{basename}.svg"
    dp = Path(dot_path)
    payload: Dict[str, Any] = {
        "dot_path": str(dp.resolve()),
        "graphviz_dot_executable": exe,
        "png_path": None,
        "svg_path": None,
    }
    out_dir.mkdir(parents=True, exist_ok=True)
    if not exe:
        warnings.append(
            "Graphviz `dot` not found on PATH; skipped PNG/SVG. Install graphviz (e.g. apt install graphviz) and re-run."
        )
        return payload, warnings
    if not dp.is_file():
        warnings.append(f"Missing DOT file: {dp}")
        return payload, warnings
    try:
        subprocess.run(
            [exe, f"-Gdpi={dpi}", "-Tpng", "-o", str(png), str(dp)],
            check=True,
        )
        subprocess.run(
            [exe, "-Tsvg", "-o", str(svg), str(dp)],
            check=True,
        )
        payload["png_path"] = str(png.resolve())
        payload["svg_path"] = str(svg.resolve())
    except (OSError, subprocess.CalledProcessError) as exc:
        warnings.append(f"Rendering failed: {exc}")
    return payload, warnings
