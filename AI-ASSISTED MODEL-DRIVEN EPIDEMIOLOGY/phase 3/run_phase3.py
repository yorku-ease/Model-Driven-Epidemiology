#!/usr/bin/env python3
"""
Phase 3: RAG and Gap Filling

Usage:
  # Step 1 — build the comprehensive database (run once, or when Phase 2 data changes)
  python build_database.py

  # Step 2a — run Phase 3 for a single Phase 2 report
  python run_phase3.py --phase2-report "../phase 2/reports/<disease>_llm_<provider>_<timestamp>" \\
      --output reports/<disease>_phase3

  # Step 2b — run Phase 3 for ALL latest Phase 2 reports at once
  python run_phase3.py --all --output reports

  # Specify LLM provider (overrides auto-detection from report dir name)
  python run_phase3.py --all --llm-provider gemini --output reports

  # Other options
  python run_phase3.py --all --no-rag --no-inference --output reports
"""

import argparse
import json
import sys
from pathlib import Path
from typing import Any, Dict, Optional

PHASE3_DIR = Path(__file__).resolve().parent
sys.path.insert(0, str(PHASE3_DIR))

from src.gap_analysis.gap_detector import (
    detect_gaps,
    load_extracted_entities,
    load_model_structure,
    load_paper_promises,
)
from src.gap_analysis.gap_filler_phase3 import fill_gaps
from src.gap_analysis.model_updater import apply_fills_to_model
from src.evaluation.guess_evaluator import evaluate_filled_gaps
from src.rag.paper_database import load_paper_database
from src.reporting.gap_report import generate_gap_report


DB_PATH = PHASE3_DIR / "data" / "paper_database"
PHASE1_DIR = PHASE3_DIR.parent / "phase 1"
PHASE2_DIR = PHASE3_DIR.parent / "phase 2"
BASELINES_DIR = PHASE2_DIR / "data" / "baseline_models"


def load_paper_text(report_dir: Path) -> str:
    path = report_dir / "paper_text.json"
    if not path.exists():
        return ""
    try:
        with open(path, "r", encoding="utf-8") as f:
            data = json.load(f)
        ft = data.get("full_text", "") or ""
        return ft if isinstance(ft, str) else " ".join(str(x) for x in ft)
    except Exception:
        return ""


def infer_disease(report_dir: Path) -> str:
    """Infer disease from report dir name (everything before '_llm_')."""
    name = report_dir.name.lower()
    if "_llm_" in name:
        return name.split("_llm_")[0]
    return name.split("_")[0] if "_" in name else name


def _latest_report_dirs() -> list:
    """Find the latest Phase 2 report dir for each (disease, provider)."""
    reports = PHASE2_DIR / "reports"
    if not reports.is_dir():
        return []
    grouped: dict = {}
    for d in sorted(reports.iterdir()):
        if not d.is_dir() or "_llm_" not in d.name:
            continue
        parts = d.name.split("_llm_")
        if len(parts) != 2:
            continue
        disease = parts[0]
        provider = parts[1].split("_")[0]
        key = f"{disease}_{provider}"
        grouped.setdefault(key, []).append(d)
    return [max(dirs, key=lambda p: p.name) for dirs in grouped.values()]


def _load_gold_with_values(compmodel_path: Path) -> Dict[str, Any]:
    """Load gold standard with full parameter details (name, expression, unit)."""
    import xml.etree.ElementTree as ET
    raw = compmodel_path.read_text(encoding="utf-8", errors="replace")
    if "xmlns:xsi" not in raw and "xsi:" in raw:
        raw = raw.replace(
            "xmlns:xmi=",
            'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xmi=',
        )
    root = ET.fromstring(raw)

    def ltag(el):
        return el.tag.split("}")[-1] if "}" in el.tag else el.tag

    parameters = []
    for el in root.iter():
        tag = ltag(el)
        if tag == "parameters":
            name = el.get("name", "")
            if name and name.lower() not in ("none", "n/a"):
                parameters.append({
                    "name": name,
                    "expression": el.get("expression", ""),
                    "value": el.get("expression", ""),
                    "unit": el.get("unit", ""),
                    "description": el.get("description", ""),
                })
    return {"parameters": parameters}


def _find_gold_standard(disease: str) -> Optional[Path]:
    """Auto-discover gold-standard .compmodel for a disease from baselines or Phase 1."""
    if BASELINES_DIR.is_dir():
        for cm in BASELINES_DIR.glob("*.compmodel"):
            if disease in cm.stem.lower():
                return cm
    # Phase 1 compmodels
    if PHASE1_DIR.is_dir():
        for cm in PHASE1_DIR.glob("papers/**/*.compmodel"):
            if disease in cm.stem.lower():
                return cm
    return None


def _infer_provider(report_dir: Path) -> str:
    """Infer LLM provider from report dir name (e.g. cholera_llm_gemini_... -> gemini)."""
    name = report_dir.name.lower()
    if "_llm_" in name:
        return name.split("_llm_")[1].split("_")[0]
    return "gemini"


def run_for_report(report_dir: Path, output_dir: Path, *, use_rag: bool, use_inference: bool,
                   llm_provider: Optional[str] = None):
    """Run Phase 3 pipeline for a single Phase 2 report directory."""
    disease = infer_disease(report_dir)
    provider = llm_provider or _infer_provider(report_dir)
    print(f"\n{'─'*60}")
    print(f"  Disease : {disease}")
    print(f"  Provider: {provider}")
    print(f"  Report  : {report_dir.name}")
    print(f"  Output  : {output_dir}")

    model_path = report_dir / "model_draft.compmodel"
    promises_path = report_dir / "paper_promises.json"
    entities_path = report_dir / "extracted_entities.json"

    for p, name in [(model_path, "model_draft.compmodel"),
                    (promises_path, "paper_promises.json"),
                    (entities_path, "extracted_entities.json")]:
        if not p.exists():
            print(f"  [WARN] Missing {name}")

    output_dir.mkdir(parents=True, exist_ok=True)

    promises = load_paper_promises(promises_path) if promises_path.exists() else {}
    entities = (load_extracted_entities(entities_path) if entities_path.exists()
                else {"compartments": [], "parameters": [], "stratifications": [], "interventions": []})
    model_structure = (load_model_structure(model_path) if model_path.exists()
                       else {"compartments": [], "parameters": [], "stratifications": []})
    paper_text = load_paper_text(report_dir)

    # Try to find gold standard baseline
    gold_standard = None
    gold_path = _find_gold_standard(disease)
    if gold_path:
        gold_standard = load_model_structure(gold_path)
        print(f"  Gold std: {gold_path.name} ({len(gold_standard['compartments'])}C / {len(gold_standard['parameters'])}P)")
    else:
        print(f"  Gold std: none (using promise-based fallback)")

    # 1) Gap detection
    gaps = detect_gaps(promises, entities, model_structure, paper_text=paper_text,
                       gold_standard=gold_standard)
    gaps_path = output_dir / "phase3_gaps.json"
    with open(gaps_path, "w", encoding="utf-8") as f:
        json.dump(gaps, f, indent=2)
    total = gaps["summary"].get("total_gaps", 0)
    mode = gaps.get("comparison_mode", "?")
    extra_c = gaps["summary"].get("extra_compartments", 0)
    extra_p = gaps["summary"].get("extra_parameters", 0)
    print(f"  Gaps    : {total} ({mode})" + (f"  Extra: {extra_c}C/{extra_p}P" if extra_c or extra_p else ""))

    # Load rich gold standard (with parameter values) for validation only
    gold_rich = _load_gold_with_values(gold_path) if gold_path else None

    # 2) Gap filling (RAG + LLM only — gold standard NOT used for filling)
    filled = fill_gaps(
        gaps, DB_PATH,
        disease_hint=disease,
        paper_text=paper_text,
        use_rag=use_rag,
        use_inference=use_inference,
        llm_provider=provider,
    )
    filled_path = output_dir / "phase3_filled.json"
    with open(filled_path, "w", encoding="utf-8") as f:
        json.dump(filled, f, indent=2)
    s = filled["summary"]
    print(f"  Filled  : RAG={s.get('rag_count',0)}  Inference={s.get('inference_count',0)}  Flagged={s.get('flagged_count',0)}")

    # 2b) Apply fills to draft model and write model_filled.compmodel
    if model_path.exists():
        filled_model_path = output_dir / "model_filled.compmodel"
        try:
            n_applied = apply_fills_to_model(Path(model_path), filled, filled_model_path)
            print(f"  Model   : {filled_model_path.name} ({n_applied} parameters updated/added)")
        except Exception as e:
            print(f"  Model   : skip ({e})")

    # 3) Validation against gold standard
    validation = evaluate_filled_gaps(filled, gold_standard=gold_rich)
    val_path = output_dir / "phase3_validation.json"
    with open(val_path, "w", encoding="utf-8") as f:
        json.dump(validation, f, indent=2)
    vs = validation["summary"]
    if vs.get("compared", 0) > 0:
        print(f"  Validate: {vs['compared']} compared — exact:{vs.get('exact',0)} close:{vs.get('close',0)} approx:{vs.get('approximate',0)} poor:{vs.get('poor',0)}")
    else:
        print(f"  Validate: {vs.get('total_filled', 0)} filled, {vs.get('not_compared', 0)} not comparable")

    # 4) Report
    report_md = output_dir / "gap_report.md"
    generate_gap_report(gaps, filled, report_md, disease_hint=disease, validation=validation)
    print(f"  Report  : {report_md}")


def main():
    ap = argparse.ArgumentParser(description="Phase 3: RAG and Gap Filling")
    ap.add_argument("--phase2-report", type=str, help="Path to a single Phase 2 report directory")
    ap.add_argument("--all", action="store_true", help="Run for ALL latest Phase 2 reports")
    ap.add_argument("--output", type=str, default="reports", help="Output directory")
    ap.add_argument("--llm-provider", type=str,
                    choices=["openai", "gemini", "claude"],
                    default=None,
                    help="LLM provider for inference (default: auto-detect from Phase 2 report name)")
    ap.add_argument("--no-rag", action="store_true", help="Skip RAG lookup")
    ap.add_argument("--no-inference", action="store_true", help="Skip LLM inference")
    args = ap.parse_args()

    if not args.phase2_report and not args.all:
        ap.error("Specify --phase2-report <dir> or --all")

    # Check database exists
    idx_file = DB_PATH / "index.json"
    if not idx_file.exists():
        print("Paper database not found. Run  python build_database.py  first.")
        sys.exit(1)
    db = load_paper_database(DB_PATH)
    print(f"Paper database: {db.get('num_entries',0)} entries, {db.get('num_parameters',0)} parameters")

    if args.all:
        dirs = _latest_report_dirs()
        if not dirs:
            print("No Phase 2 report directories found.")
            sys.exit(1)
        print(f"Running Phase 3 for {len(dirs)} reports...")
        out_base = Path(args.output)
        for rdir in sorted(dirs, key=lambda p: p.name):
            disease = infer_disease(rdir)
            provider = rdir.name.split("_llm_")[1].split("_")[0] if "_llm_" in rdir.name else "unknown"
            out = out_base / f"{disease}_{provider}_phase3"
            run_for_report(rdir, out, use_rag=not args.no_rag, use_inference=not args.no_inference,
                           llm_provider=args.llm_provider)

        # Overall report is created by select_best_model.py (PHASE3_OVERALL_REPORT.md in selected_models/)
        print(f"\n  Per-report outputs in: {out_base}/<disease>_<provider>_phase3/")
        print("  Run select_best_model.py to pick one model per disease and generate PHASE3_OVERALL_REPORT.md")
    else:
        rdir = Path(args.phase2_report)
        if not rdir.is_dir():
            print(f"Error: not a directory: {rdir}")
            sys.exit(1)
        run_for_report(rdir, Path(args.output), use_rag=not args.no_rag, use_inference=not args.no_inference,
                       llm_provider=args.llm_provider)

    print(f"\n{'='*60}")
    print("Phase 3 complete.")


if __name__ == "__main__":
    main()
