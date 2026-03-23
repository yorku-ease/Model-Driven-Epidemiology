#!/usr/bin/env python3
"""
Phase RLM - Agentic Repair Loop with Self-Correction

Main entry point for running the repair system on Phase 2 disease models.

Usage:
    python run_phase_rlm.py <phase2_report_dir>
    python run_phase_rlm.py --disease measles --provider gemini
    python run_phase_rlm.py <dir> --llm-provider gemini --llm-model gemini-2.5-flash

  --provider (-p)     : only filters which Phase 2 *report folder* to pick with --disease
  --llm-provider      : which API RLM uses for repair (like Phase 2 --llm-provider)

Example:
    python run_phase_rlm.py "../phase 2/reports/measles_llm_gemini_20260312_165901"

Evaluation context for the LLM defaults to evaluation_report_fuzzy_temp.json (config),
with fallback to evaluation_report.json. Override: --evaluation-json FILE.json
"""

import argparse
import json
import os
import sys
from pathlib import Path
from typing import Optional

PHASE_RLM_ROOT = Path(__file__).resolve().parent
sys.path.insert(0, str(PHASE_RLM_ROOT))

from src.repair_loop import run_repair
from src.section_classifier import SectionClassifier
from src.structural_validator import StructuralValidator
from src.utils.vector_store import VectorStore
from src.error_memory import ErrorMemory
from src.phase2_evaluation_context import load_evaluation_for_prompt


def rlm_output_dir(report_dir: Path, output_arg: Optional[Path]) -> Path:
    """Where RLM writes all artifacts (default: phase_rlm/output/<phase2_report_folder_name>/)."""
    if output_arg is not None:
        return output_arg.resolve()
    return (PHASE_RLM_ROOT / "output" / report_dir.name).resolve()


def write_rlm_readme(
    path: Path,
    report_dir: Path,
    eval_ref: Optional[str],
) -> None:
    """Small README so users know how to compare vs Phase 2."""
    eval_line = (
        f"`{Path(eval_ref).name}` in the Phase 2 report folder."
        if eval_ref
        else "the evaluation JSON you use for Phase 2 (e.g. `evaluation_report.json`)."
    )
    try:
        draft_rel = os.path.relpath(
            report_dir / "model_draft.compmodel", path.parent
        )
    except ValueError:
        draft_rel = str(report_dir / "model_draft.compmodel")
    text = f"""# Phase RLM output

All RLM artifacts for this run live **here** under `phase_rlm/output/`, not inside `phase 2/reports/`.

## Files

| File | Description |
|------|-------------|
| `model_repaired.compmodel` | Model after RLM structural repair |
| `initial_validation.json` | Structural validator on **Phase 2** `model_draft.compmodel` (before repair) |
| `final_validation.json` | Structural validator on **repaired** model |
| `comparison_report.json` | Before/after error counts and paths |
| `repair_report.json` | LLM repair loop log |
| `sections_vector_store/` | FAISS index (if built for this run) |
| `error_logs/` | Error memory for this run |

Original Phase 2 draft (unchanged): `{draft_rel}`

## Did RLM improve things?

### 1) Structural validation (always available)

Compare `initial_validation.json` vs `final_validation.json` (`total_errors`, `errors_by_severity`).

### 2) Gold baseline P/R/F1 (same metric as Phase 2)

Phase 2 scores in {eval_line} refer to **LLM extraction** (`extracted_entities.json`), not the XML alone.

**Fuzzy baseline (recommended, same as Phase 2 temp evaluator):** from `phase 2/`:

```bash
cd "../phase 2"
python3 rerun_evaluation_phase2_temp.py "{report_dir.as_posix()}" --rlm-evaluate
```

Writes **`evaluation_report_fuzzy_rlm.json`** in **this** folder (`phase_rlm/output/<run>/`). Compare to Phase 2’s **`evaluation_report_fuzzy_temp.json`** using:

```bash
python3 build_phase2_vs_rlm_fuzzy_md.py --reports-dir reports -o RESULTS_PHASE2_VS_RLM_FUZZY.md
```

**Semantic (optional):** from `phase_rlm/`:

```bash
python3 evaluate_repaired_model.py "{report_dir.as_posix()}"
```

Writes **`evaluation_report_rlm_repaired.json`** (embeddings). Different metric than fuzzy.
"""
    path.write_text(text, encoding="utf-8")


def load_config(config_path: Path = None) -> dict:
    """Load repair configuration."""
    if config_path is None:
        config_path = Path(__file__).parent / "configs" / "repair_config.json"

    with open(config_path) as f:
        return json.load(f)


def find_phase2_report(disease: str, provider: str = None) -> Path:
    """Find a Phase 2 report directory."""
    reports_dir = Path(__file__).parent.parent / "phase 2" / "reports"

    if not reports_dir.exists():
        reports_dir = Path("../phase 2/reports")

    pattern = f"{disease.lower()}_llm_*"

    if provider:
        pattern = f"{disease.lower()}_llm_{provider}_*"

    candidates = list(reports_dir.glob(pattern))

    if not candidates:
        raise FileNotFoundError(f"No Phase 2 report found for {disease}")

    candidates.sort(key=lambda x: x.stat().st_mtime, reverse=True)

    return candidates[0]


def validate_and_report(compmodel_path: Path) -> dict:
    """Run validator and return report."""
    validator = StructuralValidator()
    return validator.validate(compmodel_path)


def main():
    parser = argparse.ArgumentParser(
        description="Phase RLM: Agentic Repair Loop for Disease Models"
    )

    parser.add_argument(
        "report_dir",
        nargs="?",
        help="Path to Phase 2 report directory (contains model_draft.compmodel and paper_sections.json)",
    )

    parser.add_argument(
        "--disease",
        "-d",
        help="Disease name (e.g., measles, hiv, malaria)",
    )

    parser.add_argument(
        "--provider",
        "-p",
        choices=["openai", "gemini", "claude"],
        help=(
            "When using --disease: pick latest Phase 2 report folder matching "
            "{disease}_llm_{provider}_* (same idea as Phase 2 run naming). "
            "Does NOT choose which LLM runs RLM — use --llm-provider for that."
        ),
    )

    parser.add_argument(
        "--llm-provider",
        choices=["openai", "gemini"],
        default=None,
        help=(
            "Which API to use for RLM repair calls (overrides repair_config.json llm_provider). "
            "Default: value from configs/repair_config.json. Same backends as Phase 2 RLM utils."
        ),
    )

    parser.add_argument(
        "--llm-model",
        default=None,
        metavar="NAME",
        help=(
            "Model id for RLM (overrides repair_config.json llm_model), e.g. gemini-2.5-flash."
        ),
    )

    parser.add_argument(
        "--config",
        "-c",
        help="Path to custom repair config JSON",
    )
    parser.add_argument(
        "--api-key-file",
        type=str,
        default=str(PHASE_RLM_ROOT.parent / "phase 2" / ".api_key.txt"),
        help=(
            "API key file path (Phase 2 format). "
            "Default: ../phase 2/.api_key.txt. Supports lines like "
            "'openai:sk-...', 'gemini:AIza...', or a single raw key."
        ),
    )

    parser.add_argument(
        "--output",
        "-o",
        type=Path,
        help=(
            "Directory for RLM outputs (default: phase_rlm/output/<phase2_report_folder_name>/). "
            "Contains repaired model, validation JSON, vector store, error_logs, README."
        ),
    )

    parser.add_argument(
        "--validate-only",
        action="store_true",
        help="Only run validation, no repair",
    )

    parser.add_argument(
        "--evaluation-json",
        type=str,
        default=None,
        metavar="FILENAME",
        help=(
            "Phase 2 evaluation JSON inside the report dir (default: repair_config.json "
            "phase2_evaluation_json, else tries evaluation_report_fuzzy_temp.json then "
            "evaluation_report.json)"
        ),
    )

    args = parser.parse_args()

    config = load_config(Path(args.config) if args.config else None)
    config["api_key_file"] = args.api_key_file

    if args.llm_provider:
        config["llm_provider"] = args.llm_provider
    if args.llm_model:
        config["llm_model"] = args.llm_model

    if args.report_dir:
        report_dir = Path(args.report_dir)
    elif args.disease:
        report_dir = find_phase2_report(args.disease, args.provider)
    else:
        parser.print_help()
        sys.exit(1)

    if not report_dir.exists():
        print(f"Error: Report directory not found: {report_dir}")
        sys.exit(1)

    compmodel_path = report_dir / "model_draft.compmodel"
    paper_sections_path = report_dir / "paper_sections.json"

    if not compmodel_path.exists():
        print(f"Error: model_draft.compmodel not found in {report_dir}")
        sys.exit(1)

    if not paper_sections_path.exists():
        print(f"Warning: paper_sections.json not found, will use empty sections")
        paper_sections_path = None

    print(f"\n{'=' * 60}")
    print(f"Phase RLM - Agentic Repair Loop")
    print(f"{'=' * 60}")
    print(f"Report directory: {report_dir}")
    print(f"Model: {compmodel_path}")
    print(f"Sections: {paper_sections_path}")
    print(
        f"RLM LLM: provider={config.get('llm_provider', 'gemini')} "
        f"model={config.get('llm_model', 'gemini-2.5-flash')}"
    )
    print(f"API key file: {config.get('api_key_file')}")
    print(f"{'=' * 60}\n")

    print("[0/5] Pre-validation check...")
    initial_result = validate_and_report(compmodel_path)
    print(f"  Initial errors: {initial_result['total_errors']}")

    if args.validate_only:
        vo_dir = rlm_output_dir(report_dir, args.output)
        vo_dir.mkdir(parents=True, exist_ok=True)
        iv_path = vo_dir / "initial_validation.json"
        with open(iv_path, "w", encoding="utf-8") as f:
            json.dump(initial_result, f, indent=2)
        print("\n=== VALIDATION RESULTS ===")
        print(json.dumps(initial_result, indent=2))
        print(f"\n✓ Wrote structural validation snapshot: {iv_path}")
        print(f"  (RLM output folder: {vo_dir})")
        sys.exit(0)

    if initial_result["total_errors"] == 0:
        print("\n✓ Model has no structural errors. No repair needed.")
        sys.exit(0)

    if paper_sections_path is None:
        print("\nError: paper_sections.json required for repair")
        sys.exit(1)

    output_dir = rlm_output_dir(report_dir, args.output)
    output_dir.mkdir(parents=True, exist_ok=True)

    vector_store = None

    print("\n[0/5] Initializing semantic search index...")
    vector_store_path = output_dir / "sections_vector_store"

    if vector_store_path.exists():
        print(f"  Loading existing index from {vector_store_path}")
        vector_store = VectorStore()
        vector_store.load(vector_store_path)
    else:
        if paper_sections_path is None:
            print("\nError: paper_sections.json required for semantic search")
            sys.exit(1)
        print(f"  Building new index from {paper_sections_path}")
        vector_store = VectorStore()
        vector_store.build_from_paper_sections(paper_sections_path)
        vector_store.save(vector_store_path)
        print(f"  Index saved to {vector_store_path}")

    error_memory_dir = output_dir / "error_logs"
    disease_name = report_dir.name.split("_")[0] if report_dir.name else "unknown"
    error_memory = ErrorMemory(storage_dir=error_memory_dir, disease=disease_name)
    error_memory.load()

    print(f"\n  Error memory: {error_memory.entry_count} entries loaded")

    eval_pref = args.evaluation_json or config.get("phase2_evaluation_json")
    eval_summary, eval_path_used = load_evaluation_for_prompt(report_dir, eval_pref)
    if eval_path_used:
        print(f"  Phase 2 evaluation context from: {eval_path_used}")
    else:
        print("  Phase 2 evaluation: (no file found; LLM prompts will note this)")

    result = run_repair(
        compmodel_path=compmodel_path,
        paper_sections_path=paper_sections_path,
        config=config,
        vector_store=vector_store,
        error_memory=error_memory,
        evaluation_context=eval_summary,
    )

    initial_validation_path = output_dir / "initial_validation.json"
    with open(initial_validation_path, "w", encoding="utf-8") as f:
        json.dump(initial_result, f, indent=2)
    print(f"\n✓ Wrote pre-repair validation: {initial_validation_path}")

    repaired_model_path = output_dir / "model_repaired.compmodel"
    repaired_model_path.write_text(result["repaired_model_xml"], encoding="utf-8")
    print(f"✓ Repaired model saved to: {repaired_model_path}")

    report_path = output_dir / "repair_report.json"
    report_payload = dict(result["report"])
    report_payload["phase2_evaluation_json"] = eval_path_used
    report_payload["phase2_evaluation_json_preference"] = (
        args.evaluation_json or config.get("phase2_evaluation_json")
    )
    report_payload["rlm_output_dir"] = str(output_dir)
    report_payload["phase2_report_dir"] = str(report_dir.resolve())
    with open(report_path, "w", encoding="utf-8") as f:
        json.dump(report_payload, f, indent=2)
    print(f"✓ Repair report saved to: {report_path}")

    final_validation_path = output_dir / "final_validation.json"
    with open(final_validation_path, "w", encoding="utf-8") as f:
        json.dump(result["final_validation"], f, indent=2)
    print(f"✓ Final validation saved to: {final_validation_path}")

    delta = initial_result["total_errors"] - result["final_validation"]["total_errors"]
    comparison = {
        "phase2_report_dir": str(report_dir.resolve()),
        "rlm_output_dir": str(output_dir),
        "phase2_model_draft": str(compmodel_path.resolve()),
        "model_repaired": str(repaired_model_path.resolve()),
        "phase2_evaluation_context_file": eval_path_used,
        "structural_validation": {
            "before": {
                "source": "model_draft.compmodel",
                "total_errors": initial_result["total_errors"],
                "errors_by_severity": initial_result.get("errors_by_severity", {}),
                "valid": initial_result.get("valid", False),
            },
            "after": {
                "source": "model_repaired.compmodel",
                "total_errors": result["final_validation"]["total_errors"],
                "errors_by_severity": result["final_validation"].get(
                    "errors_by_severity", {}
                ),
                "valid": result["final_validation"].get("valid", False),
            },
            "delta_total_errors": delta,
        },
        "next_step_for_gold_prf": (
            "Run from phase_rlm: python3 evaluate_repaired_model.py "
            f"\"{report_dir.resolve()}\""
        ),
    }
    comparison_path = output_dir / "comparison_report.json"
    with open(comparison_path, "w", encoding="utf-8") as f:
        json.dump(comparison, f, indent=2)
    print(f"✓ Comparison report saved to: {comparison_path}")

    readme_path = output_dir / "README.md"
    write_rlm_readme(readme_path, report_dir.resolve(), eval_path_used)
    print(f"✓ README saved to: {readme_path}")
    print(f"\n  RLM output folder: {output_dir}")

    print(f"\n{'=' * 60}")
    print(f"SUMMARY")
    print(f"{'=' * 60}")
    print(f"Initial errors:  {result['report']['errors_found_initially']}")
    print(f"Errors repaired: {result['report']['errors_repaired']}")
    print(f"Errors remaining: {result['report']['errors_remaining']}")
    print(f"Pass rate:       {result['report']['validator_pass_rate']:.1%}")
    print(f"Model valid:     {result['report']['final_model_valid']}")
    if result["report"]["errors_remaining"] > 0:
        print(f"Error log:       {output_dir / 'error_logs'}")
    print(f"{'=' * 60}")


if __name__ == "__main__":
    main()
