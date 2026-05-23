#!/usr/bin/env python3
"""
Full Pipeline Runner: Phase 2 (PDF → draft .compmodel) + Phase 3 (gap fill → filled .compmodel)

Usage:
  python run_full_pipeline.py --pdf <path> --llm-provider <provider> --output <dir>

Output:
  Writes final .compmodel to <output>/<name>.compmodel
  Prints final model path to stdout on success.

Environment:
  PYTHONPATH is automatically set to include all phase directories.
  API keys read from phase 2/.api_key.txt (or env vars).
"""

import argparse
import json
import os
import shutil
import subprocess
import sys
import tempfile
from pathlib import Path


ROOT = Path(__file__).resolve().parent
VENV_PYTHON = ROOT / ".venv" / "bin" / "python3"
PHASE2_DIR = ROOT / "phase 2"
PHASE3_DIR = ROOT / "phase 3"

# Use venv Python if available, fall back to sys.executable
PYTHON_BIN = str(VENV_PYTHON) if VENV_PYTHON.exists() else sys.executable

# Repair module (Phase 4 — model repair)
sys.path.insert(0, str(PHASE3_DIR))
try:
    from src.repair.model_repair import run_repair_pipeline
    REPAIR_AVAILABLE = True
except ImportError:
    REPAIR_AVAILABLE = False


def check_dependencies():
    """Verify that the required phase directories and scripts exist."""
    missing = []
    for name, path in [("Phase 2 runner", PHASE2_DIR / "run_phase2.py"),
                       ("Phase 3 runner", PHASE3_DIR / "run_phase3.py"),
                       ("Phase 3 DB builder", PHASE3_DIR / "build_database.py")]:
        if not path.exists():
            missing.append(f"{name} ({path})")
    if missing:
        print("ERROR: Missing required files:", file=sys.stderr)
        for m in missing:
            print(f"  {m}", file=sys.stderr)
        sys.exit(1)


def run_phase2(pdf_path: Path, llm_provider: str, phase2_output_dir: Path, system_prompt: str = None) -> Path:
    """Run Phase 2 and return the path to the report directory it created."""
    print(f"[Pipeline] Running Phase 2: {pdf_path.name} (provider: {llm_provider})")
    print(f"[Pipeline] Phase 2 output base: {phase2_output_dir}")

    cmd = [
        PYTHON_BIN, str(PHASE2_DIR / "run_phase2.py"),
        "--paper", str(pdf_path),
        "--llm-provider", llm_provider,
        "--output", str(phase2_output_dir),
    ]
    if system_prompt:
        cmd.extend(["--system-prompt", system_prompt])

    result = subprocess.run(
        cmd,
        cwd=str(PHASE2_DIR),
        capture_output=True,
        text=True,
    )

    # Print Phase 2 output
    if result.stdout:
        print(result.stdout)
    if result.stderr:
        print(result.stderr, file=sys.stderr)

    if result.returncode != 0:
        print(f"ERROR: Phase 2 failed with exit code {result.returncode}", file=sys.stderr)
        sys.exit(result.returncode)

    # Find the report directory Phase 2 created.
    # Phase 2 names it: <disease>_llm_<provider>_<timestamp>/ inside --output.
    # Find the newest subdirectory matching this pattern.
    candidates = sorted(
        [d for d in phase2_output_dir.iterdir()
         if d.is_dir() and f"_llm_{llm_provider}" in d.name],
        key=lambda p: p.name,
        reverse=True,
    )
    if not candidates:
        print("ERROR: Could not find Phase 2 report directory", file=sys.stderr)
        sys.exit(1)

    report_dir = candidates[0]
    model_draft = report_dir / "model_draft.compmodel"
    if not model_draft.exists():
        print(f"ERROR: Phase 2 did not produce {model_draft}", file=sys.stderr)
        sys.exit(1)

    print(f"[Pipeline] Phase 2 report: {report_dir}")
    return report_dir


def run_phase3(phase2_report_dir: Path, llm_provider: str, phase3_output_dir: Path):
    """Run Phase 3 gap filling on a Phase 2 report directory."""
    print(f"[Pipeline] Running Phase 3 gap filling...")
    print(f"[Pipeline] Phase 3 output: {phase3_output_dir}")

    result = subprocess.run(
        [
            PYTHON_BIN, str(PHASE3_DIR / "run_phase3.py"),
            "--phase2-report", str(phase2_report_dir),
            "--output", str(phase3_output_dir),
            "--llm-provider", llm_provider,
        ],
        cwd=str(PHASE3_DIR),
        capture_output=True,
        text=True,
    )

    if result.stdout:
        print(result.stdout)
    if result.stderr:
        print(result.stderr, file=sys.stderr)

    if result.returncode != 0:
        print(f"ERROR: Phase 3 failed with exit code {result.returncode}", file=sys.stderr)
        sys.exit(result.returncode)


def ensure_phase3_database():
    """Build the Phase 3 RAG database if it doesn't exist yet."""
    db_index = PHASE3_DIR / "data" / "paper_database" / "index.json"
    if not db_index.exists():
        print("[Pipeline] Building Phase 3 RAG database...")
        result = subprocess.run(
            [PYTHON_BIN, str(PHASE3_DIR / "build_database.py")],
            cwd=str(PHASE3_DIR),
            capture_output=True,
            text=True,
        )
        if result.stdout:
            print(result.stdout)
        if result.stderr:
            print(result.stderr, file=sys.stderr)
        if result.returncode != 0:
            print("WARNING: Phase 3 database build failed. Phase 3 may have limited functionality.",
                  file=sys.stderr)
    else:
        print(f"[Pipeline] Phase 3 database exists: {db_index}")


def extract_basename(pdf_path: Path, model_name: str = None) -> str:
    """Determine output model basename from PDF or user-provided name."""
    if model_name:
        return model_name.replace(".compmodel", "")
    return pdf_path.stem


def main():
    parser = argparse.ArgumentParser(
        description="Full pipeline: Phase 2 (PDF→model) + Phase 3 (gap fill)",
    )
    parser.add_argument("--pdf", required=True, help="Path to PDF file")
    parser.add_argument("--llm-provider", default="gemini",
                        choices=["openai", "gemini", "claude"],
                        help="LLM provider (default: gemini)")
    parser.add_argument("--output", default=None,
                        help="Output directory for final .compmodel (default: auto)")
    parser.add_argument("--model-name", default=None,
                        help="Output model basename (default: PDF stem)")
    parser.add_argument("--repair", action="store_true", default=False,
                        help="Run model validation repair (Phase 4) after gap filling")
    parser.add_argument("--json", action="store_true", default=False,
                        help="Output final model as JSON to stdout")
    parser.add_argument("--system-prompt", type=str, default=None,
                        help="Path to custom system prompt file (optional)")
    args = parser.parse_args()

    pdf_path = Path(args.pdf).resolve()
    if not pdf_path.exists():
        print(f"ERROR: PDF not found: {pdf_path}", file=sys.stderr)
        sys.exit(1)

    # Determine base output directory
    if args.output:
        final_output_dir = Path(args.output).resolve()
    else:
        final_output_dir = Path.cwd()
    final_output_dir.mkdir(parents=True, exist_ok=True)

    model_name = extract_basename(pdf_path, args.model_name)
    final_model_path = final_output_dir / f"{model_name}.compmodel"

    check_dependencies()

    # Use a temp workspace for intermediate files
    with tempfile.TemporaryDirectory(prefix="pipeline_") as tmp_dir:
        tmp_path = Path(tmp_dir)
        phase2_output = tmp_path / "phase2_output"
        phase3_output = tmp_path / "phase3_output"
        phase2_output.mkdir()
        phase3_output.mkdir()

        # Ensure Phase 3 database exists
        ensure_phase3_database()

        # Run Phase 2
        phase2_report_dir = run_phase2(pdf_path, args.llm_provider, phase2_output, args.system_prompt)

        # Run Phase 3
        run_phase3(phase2_report_dir, args.llm_provider, phase3_output)

        # Locate filled model
        filled_model = phase3_output / "model_filled.compmodel"
        if not filled_model.exists():
            # Fall back to Phase 2 draft if Phase 3 didn't produce output
            print("[Pipeline] Phase 3 filled model not found, using Phase 2 draft.", file=sys.stderr)
            filled_model = phase2_report_dir / "model_draft.compmodel"

        # Copy to final destination
        shutil.copy2(str(filled_model), str(final_model_path))
        print(f"[Pipeline] Final model: {final_model_path}")

        # Phase 4 — model repair (structural + LLM-based validation fix)
        if args.repair:
            if REPAIR_AVAILABLE:
                print(f"[Pipeline] Running model repair (Phase 4)...")
                try:
                    run_repair_pipeline(
                        final_model_path,
                        provider=args.llm_provider,
                        max_iterations=10,
                    )
                    print(f"[Pipeline] Repair complete: {final_model_path}")
                except Exception as e:
                    print(f"[Pipeline] Repair error (non-fatal): {e}", file=sys.stderr)
            else:
                print(f"[Pipeline] WARNING: Repair module not available (src/repair/model_repair.py missing)", file=sys.stderr)

    if args.json:
        with open(final_model_path, 'r', encoding='utf-8') as f:
            content = f.read()
        print(json.dumps({"model_xml": content}))
    else:
        # Print the output path for programmatic consumption (Java reads this)
        print(f"OUTPUT_PATH:{final_model_path}")

    return 0


if __name__ == "__main__":
    sys.exit(main())
