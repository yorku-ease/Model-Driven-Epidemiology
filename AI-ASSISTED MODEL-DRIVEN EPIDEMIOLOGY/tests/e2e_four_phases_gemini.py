#!/usr/bin/env python3
r"""
End-to-end test: Phase 2 → Phase 3 → Phase 4 (Gemini), random benchmark PDFs.

Phase 1 is **not** run (no new artifacts for this pipeline).

After Phase 2 / Phase 3, runs the same markdown builders as a normal project run:

- ``phase 2/build_results_md.py`` → ``tests/results/<run>/build/RESULTS_REPORT.md``
- ``phase 3/build_phase3_results_md.py`` → ``tests/results/<run>/build/RESULTS_PHASE3.md``

Phase 3 outputs use a minimal showcase layout under ``phase3_showcase/both/<stem>_gemini_phase3/``
so ``build_phase3_results_md.py`` can read metrics the usual way. A small ``showcase_summary.json``
is written for the fuzzy P2 vs P3 section.

All artifacts live under ``tests/results/e2e_gemini_<timestamp>/`` only.

Usage::

  cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY"
  python3 tests/e2e_four_phases_gemini.py --seed 42
"""

from __future__ import annotations

import argparse
import importlib.util
import json
import os
import random
import shutil
import subprocess
import sys
from datetime import datetime
from pathlib import Path
from typing import Any, Dict, List


def _project_root() -> Path:
    return Path(__file__).resolve().parent.parent


def _tests_results_dir(root: Path) -> Path:
    return root / "tests" / "results"


def _load_phase2_paths(phase2_dir: Path):
    spec = importlib.util.spec_from_file_location(
        "phase2_paths",
        phase2_dir / "src" / "utils" / "phase2_paths.py",
    )
    assert spec and spec.loader
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    return mod


def _gemini_available(phase2_dir: Path) -> bool:
    if os.environ.get("GEMINI_API_KEY", "").strip():
        return True
    key_file = phase2_dir / ".api_key.txt"
    if not key_file.is_file():
        return False
    text = key_file.read_text(encoding="utf-8", errors="replace")
    for line in text.splitlines():
        line = line.strip()
        if not line or line.startswith("#"):
            continue
        if line.lower().startswith("gemini:") or line.startswith("AIza"):
            return True
    return False


def _run(
    cmd: List[str],
    *,
    cwd: Path,
    env: dict | None = None,
) -> None:
    print(f"\n>>> {' '.join(cmd)}\n", flush=True)
    r = subprocess.run(cmd, cwd=str(cwd), env=env)
    if r.returncode != 0:
        raise RuntimeError(f"Command failed (exit {r.returncode}): {cmd}")


def phase2_run(
    phase2_dir: Path,
    pdf: Path,
    out_parent: Path,
    env: dict,
) -> Path:
    stem = pdf.stem.lower()
    out_parent.mkdir(parents=True, exist_ok=True)
    before = set(out_parent.glob("*")) if out_parent.is_dir() else set()
    _run(
        [
            sys.executable,
            "run_phase2.py",
            "--paper",
            str(pdf.resolve()),
            "--output",
            str(out_parent),
            "--llm-provider",
            "gemini",
            "--phase1-dir",
            str(_project_root() / "phase 1"),
        ],
        cwd=phase2_dir,
        env=env,
    )
    if not out_parent.is_dir():
        raise FileNotFoundError(f"Expected output parent: {out_parent}")
    after = set(out_parent.glob("*"))
    new_dirs = [
        p
        for p in (after - before)
        if p.is_dir() and "_llm_" in p.name and "gemini" in p.name.lower()
    ]
    if not new_dirs:
        candidates = sorted(
            out_parent.glob(f"{stem}_llm_gemini_*"),
            key=lambda p: p.name,
        )
        if not candidates:
            raise RuntimeError(
                f"No new Phase 2 report under {out_parent} for stem {stem!r}"
            )
        return candidates[-1]
    return max(new_dirs, key=lambda p: p.stat().st_mtime)


def phase3_build_db(phase3_dir: Path, env: dict) -> None:
    _run([sys.executable, "build_database.py"], cwd=phase3_dir, env=env)


def phase3_run(
    phase3_dir: Path,
    phase2_report: Path,
    out_dir: Path,
    env: dict,
) -> None:
    _run(
        [
            sys.executable,
            "run_phase3.py",
            "--phase2-report",
            str(phase2_report.resolve()),
            "--output",
            str(out_dir),
            "--llm-provider",
            "gemini",
        ],
        cwd=phase3_dir,
        env=env,
    )


def phase4_run(
    phase4_dir: Path,
    model_filled: Path,
    out_dir: Path,
    samples: int,
    days: int,
    env: dict,
) -> None:
    _run(
        [
            sys.executable,
            "run_phase4.py",
            "--model",
            str(model_filled.resolve()),
            "--output",
            str(out_dir),
            "--samples",
            str(samples),
            "--days",
            str(days),
        ],
        cwd=phase4_dir,
        env=env,
    )


def write_showcase_summary(
    showcase_root: Path,
    rows: List[Dict[str, Any]],
) -> None:
    """Minimal summary so build_phase3_results_md fuzzy section can resolve paths."""
    summary = []
    for row in rows:
        stem = row["stem"]
        summary.append(
            {
                "disease": stem,
                "disease_display": stem,
                "best_phase2_report": row["phase2_report_abs"],
                "best_phase2_extractor": "gemini",
                "phase2_score": 0.0,
                "phase3_llm_provider": "gemini",
                "phase3_gemini_model": os.environ.get("GEMINI_MODEL", "gemini-2.5-flash"),
                "modes": {},
                "winner_mode": "both",
            }
        )
    showcase_root.mkdir(parents=True, exist_ok=True)
    path = showcase_root / "showcase_summary.json"
    path.write_text(json.dumps(summary, indent=2, ensure_ascii=False), encoding="utf-8")
    print(f"Wrote {path}")


def run_build_results_md(phase2_dir: Path, reports_dir: Path, out_md: Path, env: dict) -> None:
    _run(
        [
            sys.executable,
            "build_results_md.py",
            "--reports-dir",
            str(reports_dir.resolve()),
            "-o",
            str(out_md.resolve()),
            "--title",
            "Phase 2 Evaluation Results (E2E test run)",
        ],
        cwd=phase2_dir,
        env=env,
    )


def run_build_phase3_results_md(
    phase3_dir: Path,
    showcase_dir: Path,
    out_md: Path,
    env: dict,
) -> None:
    _run(
        [
            sys.executable,
            "build_phase3_results_md.py",
            "--showcase",
            str(showcase_dir.resolve()),
            "-o",
            str(out_md.resolve()),
            "--mode",
            "both",
        ],
        cwd=phase3_dir,
        env=env,
    )


def finalize_build_folder(run_root: Path, paper_rows: List[Dict[str, Any]]) -> None:
    b = run_root / "build"
    b.mkdir(parents=True, exist_ok=True)
    lines = [
        "# E2E build folder",
        "",
        "| Generated (same scripts as a full run) |",
        "|----------------------------------------|",
        "| [RESULTS_REPORT.md](RESULTS_REPORT.md) | Phase 2 — `build_results_md.py` |",
        "| [RESULTS_PHASE3.md](RESULTS_PHASE3.md) | Phase 3 — `build_phase3_results_md.py` |",
        "",
        "Per-paper copies:",
        "",
    ]
    for row in paper_rows:
        stem = row["stem"]
        p3 = Path(row["phase3_output_abs"])
        p4 = Path(row["phase4_output_abs"])
        gap = p3 / "gap_report.md"
        rep = p4 / "PHASE4_REPORT.md"
        if gap.is_file():
            shutil.copy2(gap, b / f"GAP_REPORT_{stem}.md")
            lines.append(f"- `GAP_REPORT_{stem}.md` ← `phase3_showcase/both/{stem}_gemini_phase3/gap_report.md`")
        if rep.is_file():
            shutil.copy2(rep, b / f"PHASE4_{stem}_REPORT.md")
            lines.append(f"- `PHASE4_{stem}_REPORT.md` ← `phase4/{stem}/PHASE4_REPORT.md`")
    lines.append("")
    (b / "INDEX.md").write_text("\n".join(lines), encoding="utf-8")


def main() -> int:
    root = _project_root()
    phase2_dir = root / "phase 2"
    phase3_dir = root / "phase 3"
    phase4_dir = root / "phase 4"

    ap = argparse.ArgumentParser(
        description="E2E: Phase 2–4 (Gemini), MD builders, outputs under tests/results/",
    )
    ap.add_argument(
        "--seed",
        type=int,
        default=None,
        help="Seed for random.sample (which PDFs). Omit for random choice each run.",
    )
    ap.add_argument("--papers", type=int, default=3, help="Number of PDFs (default: 3)")
    ap.add_argument("--phase4-samples", type=int, default=1000, help="Monte Carlo samples (default: 1000)")
    ap.add_argument("--phase4-days", type=int, default=200, help="Simulation days (default: 200)")
    args = ap.parse_args()

    if args.seed is not None:
        random.seed(args.seed)

    os.environ.setdefault("GEMINI_MODEL", "gemini-2.5-flash")
    os.environ.setdefault("PHASE3_LLM_PROVIDER", "gemini")

    if not _gemini_available(phase2_dir):
        print(
            "Error: Gemini API key not found. Set GEMINI_API_KEY or add a Gemini key to phase 2/.api_key.txt",
            file=sys.stderr,
        )
        return 2

    mod = _load_phase2_paths(phase2_dir)
    pdfs = mod.iter_benchmark_pdf_paths()
    if len(pdfs) < args.papers:
        print(
            f"Error: need at least {args.papers} benchmark PDFs; found {len(pdfs)}.",
            file=sys.stderr,
        )
        return 2

    chosen = random.sample(pdfs, k=args.papers)
    run_id = datetime.now().strftime("%Y%m%d_%H%M%S")
    results_base = _tests_results_dir(root)
    results_base.mkdir(parents=True, exist_ok=True)
    run_root = results_base / f"e2e_gemini_{run_id}"
    run_root.mkdir(parents=True, exist_ok=True)

    phase2_out = run_root / "phase2"
    phase3_showcase = run_root / "phase3_showcase"
    both_root = phase3_showcase / "both"
    phase4_root = run_root / "phase4"
    build_dir = run_root / "build"
    for d in (phase2_out, both_root, phase4_root, build_dir):
        d.mkdir(parents=True, exist_ok=True)

    env = os.environ.copy()

    print("=" * 72)
    print("E2E: Phase 2 → Phase 3 → markdown builders → Phase 4 (Gemini)")
    print(f"  Output: {run_root.relative_to(root)}")
    print(f"  Seed: {args.seed!r}")
    for p in chosen:
        print(f"    - {p.relative_to(phase2_dir)}")
    print("=" * 72)

    phase2_reports: List[tuple[Path, Path, str]] = []

    for i, pdf in enumerate(chosen, start=1):
        stem = pdf.stem
        print(f"\n{'#' * 72}\n# Phase 2  {i}/{len(chosen)}: {stem}\n{'#' * 72}")
        report_dir = phase2_run(phase2_dir, pdf, phase2_out, env)
        phase2_reports.append((pdf, report_dir, stem))

    print("\n[Phase 3] build_database.py …")
    phase3_build_db(phase3_dir, env)

    paper_rows: List[Dict[str, Any]] = []

    for i, (pdf, report_dir, stem) in enumerate(phase2_reports, start=1):
        print(f"\n{'#' * 72}\n# Phase 3  {i}/{len(phase2_reports)}: {stem}\n{'#' * 72}")
        p3_out = both_root / f"{stem}_gemini_phase3"
        phase3_run(phase3_dir, report_dir, p3_out, env)
        filled = p3_out / "model_filled.compmodel"
        if not filled.is_file():
            raise FileNotFoundError(f"Phase 3 did not produce {filled}")
        paper_rows.append(
            {
                "stem": stem,
                "pdf": str(pdf.relative_to(phase2_dir)),
                "phase2_report_abs": str(report_dir.resolve()),
                "phase2_report": str(report_dir.relative_to(root)),
                "phase3_output_abs": str(p3_out.resolve()),
                "phase3_output": str(p3_out.relative_to(root)),
            }
        )

    write_showcase_summary(phase3_showcase, paper_rows)

    print("\n[Build] phase 2/build_results_md.py …")
    run_build_results_md(
        phase2_dir,
        phase2_out,
        build_dir / "RESULTS_REPORT.md",
        env,
    )

    print("\n[Build] phase 3/build_phase3_results_md.py …")
    run_build_phase3_results_md(
        phase3_dir,
        phase3_showcase,
        build_dir / "RESULTS_PHASE3.md",
        env,
    )

    for i, row in enumerate(paper_rows, start=1):
        stem = row["stem"]
        print(f"\n{'#' * 72}\n# Phase 4  {i}/{len(paper_rows)}: {stem}\n{'#' * 72}")
        p3 = Path(row["phase3_output_abs"])
        filled = p3 / "model_filled.compmodel"
        p4_out = phase4_root / stem
        phase4_run(phase4_dir, filled, p4_out, args.phase4_samples, args.phase4_days, env)
        row["phase4_output_abs"] = str(p4_out.resolve())
        row["phase4_output"] = str(p4_out.relative_to(root))

    finalize_build_folder(run_root, paper_rows)

    readme = run_root / "README.md"
    readme.write_text(
        "\n".join(
            [
                f"# E2E run `{run_id}`",
                "",
                "| Path | Description |",
                "|------|-------------|",
                "| `phase2/` | Phase 2 report folders |",
                "| `phase3_showcase/both/` | Phase 3 runs (showcase layout for `build_phase3_results_md.py`) |",
                "| `phase4/` | Phase 4 outputs per paper |",
                "| `build/` | **RESULTS_REPORT.md**, **RESULTS_PHASE3.md**, **INDEX.md**, copies |",
                "",
                f"Seed: `{args.seed!r}` · GEMINI_MODEL: `{os.environ.get('GEMINI_MODEL')}`",
                "",
            ]
        ),
        encoding="utf-8",
    )

    summary_path = run_root / "e2e_summary.json"
    summary_path.write_text(
        json.dumps(
            {
                "run_id": run_id,
                "seed": args.seed,
                "gemini_model": os.environ.get("GEMINI_MODEL"),
                "papers": paper_rows,
            },
            indent=2,
        ),
        encoding="utf-8",
    )
    print(f"\n{'=' * 72}\nDone.\n  {build_dir / 'RESULTS_REPORT.md'}\n  {build_dir / 'RESULTS_PHASE3.md'}\n  {build_dir / 'INDEX.md'}\n{'=' * 72}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
