#!/usr/bin/env python3
"""Run Phase 2 on every benchmark PDF (see ``phase2_paths.iter_benchmark_pdf_paths``)."""

import os
import subprocess
import sys
from pathlib import Path

PHASE2 = Path(__file__).resolve().parent
DATA = PHASE2 / "data"
BASELINE_DIR = DATA / "baseline_models"

sys.path.insert(0, str(PHASE2))
from src.utils.phase2_paths import iter_benchmark_pdf_paths  # noqa: E402


def iter_paired_pdfs():
    """Yield PDF paths under ``data/diseases/<disease>/`` (and legacy layouts)."""
    yield from iter_benchmark_pdf_paths()


def run_on_pdf(paper_path: Path, provider: str) -> bool:
    env = os.environ.copy()
    if provider == "openai":
        if not env.get("OPENAI_API_KEY"):
            print(f"  ⚠ OPENAI_API_KEY not set, skipping {paper_path}")
            return False
    else:
        if not env.get("GEMINI_API_KEY"):
            print(f"  ⚠ GEMINI_API_KEY not set, skipping {paper_path}")
            return False

    cmd = [
        sys.executable,
        str(PHASE2 / "run_phase2.py"),
        "--paper",
        str(paper_path),
        "--llm-provider",
        provider,
        "--baseline-models-dir",
        str(BASELINE_DIR),
        "--eval-threshold",
        "0.70",
    ]
    print(f"  Running {provider} on {paper_path.relative_to(PHASE2)}...")
    result = subprocess.run(cmd, cwd=PHASE2, env=env, capture_output=True, text=True)
    if result.returncode != 0:
        print(f"  ❌ Error: {result.stderr[:300]}")
        return False
    return True


def main() -> None:
    pdfs = list(iter_paired_pdfs())
    if not pdfs:
        print(
            f"No benchmark PDFs found under {DATA}/diseases/<disease>/ "
            f"(or legacy {DATA}/<disease>/). See data/README.md."
        )
        sys.exit(1)

    if not os.environ.get("OPENAI_API_KEY") or not os.environ.get("GEMINI_API_KEY"):
        print("Error: set both OPENAI_API_KEY and GEMINI_API_KEY")
        sys.exit(1)

    print("=" * 60)
    print(f"Phase 2 batch: {len(pdfs)} PDF(s) (data/diseases/... or legacy)")
    print("=" * 60)

    for pdf in pdfs:
        print(f"\n[{pdf}]")
        run_on_pdf(pdf, "openai")
        run_on_pdf(pdf, "gemini")

    out = PHASE2 / "reports" / "batch_results.json"
    out.parent.mkdir(parents=True, exist_ok=True)
    print(f"\nDone. (Optional: save JSON summary manually.)")


if __name__ == "__main__":
    main()
