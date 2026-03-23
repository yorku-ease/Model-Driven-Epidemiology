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
    detect_gaps_threelayer,
    load_extracted_entities,
    load_model_structure,
    load_paper_promises,
)
from src.gap_analysis.gap_filler_phase3 import fill_gaps
from src.gap_analysis.model_updater import apply_fills_to_model
from src.evaluation.guess_evaluator import compute_completeness_score, evaluate_phase3_full
from src.evaluation.structural_check import check_structural_integrity, structural_errors_as_gaps
from src.gap_analysis.structural_repairer import repair_structural_errors
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
    extra_f = gaps["summary"].get("extra_flows", 0)
    extra_bits = []
    if extra_c or extra_p or extra_f:
        extra_bits.append(f"Extra vs gold: {extra_c}C/{extra_p}P/{extra_f}F")
    print(f"  Gaps    : {total} ({mode})" + (f"  [{', '.join(extra_bits)}]" if extra_bits else ""))

    # Load rich gold standard (with parameter values) for validation only
    gold_rich = _load_gold_with_values(gold_path) if gold_path else None

    # 1b) Three-layer gap analysis (spec→model, model→gold, extras)
    threelayer = detect_gaps_threelayer(
        entities, model_structure, gold_standard, paper_text=paper_text
    )
    threelayer_path = output_dir / "phase3_threelayer_gaps.json"
    with open(threelayer_path, "w", encoding="utf-8") as f:
        json.dump(threelayer, f, indent=2)
    spec_total = threelayer["spec_vs_model"]["summary"]["total"]
    gold_total = threelayer["model_vs_gold"]["summary"]["total"]
    extra_total = threelayer["extra_in_model"]["summary"]["total"]
    print(f"  3-Layer : spec→model={spec_total}  model→gold={gold_total}  extra={extra_total}")

    # 2) Gap filling — iterative loop (max 2 rounds, like Phase RLM's repair loop)
    #    Round 1: full fill (RAG + spec-entity + inference)
    #    Round 2: second pass on remaining gaps (RAG + inference only — spec fills are one-shot)
    MAX_FILL_ITERATIONS = 2
    filled_model_path = output_dir / "model_filled.compmodel"
    gaps_after: Optional[Dict[str, Any]] = None

    # Accumulated state across iterations
    all_fill_records: list = []
    fill_summary_accum: Dict[str, Any] = {}
    current_source_path = model_path  # source compmodel for apply_fills_to_model
    filled: Optional[Dict[str, Any]] = None

    for fill_iter in range(1, MAX_FILL_ITERATIONS + 1):
        gaps_this_iter = gaps if fill_iter == 1 else gaps_after
        if not gaps_this_iter or gaps_this_iter["summary"].get("total_gaps", 0) == 0:
            if fill_iter > 1:
                print(f"  [Iter {fill_iter}] No remaining gaps — done early.")
            break

        print(f"  [Iter {fill_iter}/{MAX_FILL_ITERATIONS}] Filling "
              f"{gaps_this_iter['summary'].get('total_gaps', 0)} gap(s)...")

        filled_iter = fill_gaps(
            gaps_this_iter, DB_PATH,
            disease_hint=disease,
            paper_text=paper_text,
            use_rag=use_rag,
            use_inference=use_inference,
            llm_provider=provider,
            # spec-entity fills only on first round (entities are used up after iter 1)
            threelayer=threelayer if fill_iter == 1 else None,
            entities=entities if fill_iter == 1 else None,
        )

        # Accumulate fill records from this iteration
        all_fill_records.extend(filled_iter.get("filled", []))
        iter_s = filled_iter.get("summary", {})
        for key in ("rag_count", "spec_entity_count", "inference_count", "flagged_count", "total_filled"):
            fill_summary_accum[key] = fill_summary_accum.get(key, 0) + iter_s.get(key, 0)

        # Apply fills; iter 2+ starts from the previous iteration's output
        if current_source_path.exists():
            try:
                n_applied = apply_fills_to_model(current_source_path, filled_iter, filled_model_path)
                print(f"  [Iter {fill_iter}] Applied {n_applied} update(s) → {filled_model_path.name}")
            except Exception as e:
                print(f"  [Iter {fill_iter}] Apply error: {e}")
                break
        else:
            print(f"  [Iter {fill_iter}] Source model not found, skipping apply.")
            break

        # After applying, the next iteration reads from the filled output
        current_source_path = filled_model_path

        # Re-detect gaps from the freshly filled model
        try:
            if filled_model_path.exists():
                filled_structure = load_model_structure(filled_model_path)
                gaps_after = detect_gaps(
                    promises, entities, filled_structure,
                    paper_text=paper_text,
                    gold_standard=gold_standard,
                )
                remaining = gaps_after["summary"].get("total_gaps", 0)
                print(f"  [Iter {fill_iter}] Gaps remaining after this round: {remaining}")
        except Exception as e:
            print(f"  [Iter {fill_iter}] Re-detection error: {e}")
            break

        filled = filled_iter  # keep reference to last iteration's fill object

    # Build a merged filled result for reporting (all iterations combined)
    if filled is not None:
        filled = dict(filled)  # shallow copy
        filled["filled"] = all_fill_records
        filled["summary"] = dict(filled.get("summary", {}))
        filled["summary"].update(fill_summary_accum)
        filled["summary"]["fill_iterations"] = MAX_FILL_ITERATIONS
    elif gaps["summary"].get("total_gaps", 0) == 0:
        # No gaps at all from the start
        filled = {"filled": [], "summary": {"total_filled": 0}}

    filled_path = output_dir / "phase3_filled.json"
    if filled:
        with open(filled_path, "w", encoding="utf-8") as f:
            json.dump(filled, f, indent=2)
        s = filled["summary"]
        print(f"  Filled  : RAG={s.get('rag_count',0)}  Spec={s.get('spec_entity_count',0)}  "
              f"Inference={s.get('inference_count',0)}  Flagged={s.get('flagged_count',0)}"
              f"  (across {MAX_FILL_ITERATIONS} iteration(s))")

    # 2d) Structural integrity check + repair (Phase RLM-inspired)
    #     Step 1 — Detect structural errors independent of gold standard.
    #     Step 2 — Apply targeted repairs (parameter wiring, self-referential flows,
    #              parameter collapse, zero populations, broken flow chains).
    #     Step 3 — Re-validate to count remaining errors after repair.
    structural: Dict[str, Any] = {}
    structural_after: Dict[str, Any] = {}
    repair_report: Dict[str, Any] = {}
    repaired_model_path = output_dir / "model_repaired.compmodel"

    if filled_model_path.exists():
        structural = check_structural_integrity(filled_model_path)
        if structural.get("available"):
            n_before = structural.get("total_errors", 0)
            print(f"  Struct  : {structural.get('summary_text', '')} [before repair]")
            with open(output_dir / "phase3_structural_before.json", "w", encoding="utf-8") as f:
                json.dump(structural, f, indent=2)

            # Run structural repair
            try:
                repair_report = repair_structural_errors(
                    filled_model_path,
                    structural,
                    repaired_model_path,
                    llm_provider=provider,
                    disease_hint=disease,
                    paper_text=paper_text,
                    paper_db_path=DB_PATH,
                )
                n_fixed = repair_report.get("repairs_applied", 0)
                print(f"  Repair  : {n_fixed} fix(es) applied → {repaired_model_path.name}")

                # Re-validate after repair
                if repaired_model_path.exists():
                    structural_after = check_structural_integrity(repaired_model_path)
                    n_after = structural_after.get("total_errors", 0)
                    delta = n_before - n_after
                    print(f"  Struct  : {structural_after.get('summary_text','')} [after repair]  Δ={delta:+d}")
                    with open(output_dir / "phase3_structural_after.json", "w", encoding="utf-8") as f:
                        json.dump(structural_after, f, indent=2)

                with open(output_dir / "phase3_repair_log.json", "w", encoding="utf-8") as f:
                    json.dump(repair_report, f, indent=2)

            except Exception as e:
                print(f"  Repair  : error — {e}")
        else:
            print(f"  Struct  : {structural.get('note', 'Phase RLM not available')}")

    improvement: Optional[Dict[str, Any]] = None
    if gaps_after:
        before_summary = gaps.get("summary", {}) or {}
        after_summary = gaps_after.get("summary", {}) or {}
        improvement = {
            "comparison": "phase2_draft_vs_phase3_filled",
            "before": before_summary,
            "after": after_summary,
            "summary": {
                "before_total_gaps": before_summary.get("total_gaps", 0),
                "after_total_gaps": after_summary.get("total_gaps", 0),
                "delta_total_gaps": before_summary.get("total_gaps", 0)
                - after_summary.get("total_gaps", 0),
                "delta_missing_parameters": before_summary.get("missing_parameters", 0)
                - after_summary.get("missing_parameters", 0),
                "delta_missing_compartments": before_summary.get("missing_compartments", 0)
                - after_summary.get("missing_compartments", 0),
                "delta_missing_flows": before_summary.get("missing_flows", 0)
                - after_summary.get("missing_flows", 0),
            },
        }
        with open(output_dir / "phase3_improvement.json", "w", encoding="utf-8") as f:
            json.dump(improvement, f, indent=2)

    # 3) Validation: parameter fills vs gold + structural alignment (compartments & flows)
    validation = evaluate_phase3_full(
        filled,
        gold_standard=gold_rich,
        gold_compmodel_path=gold_path,
        draft_compmodel_path=model_path if model_path.exists() else None,
        filled_compmodel_path=filled_model_path if filled_model_path.exists() else None,
    )
    val_path = output_dir / "phase3_validation.json"
    with open(val_path, "w", encoding="utf-8") as f:
        json.dump(validation, f, indent=2)
    vs = validation["summary"]
    if vs.get("compared", 0) > 0:
        print(f"  Validate: {vs['compared']} compared — exact:{vs.get('exact',0)} close:{vs.get('close',0)} approx:{vs.get('approximate',0)} poor:{vs.get('poor',0)}")
    else:
        print(f"  Validate: {vs.get('total_filled', 0)} filled, {vs.get('not_compared', 0)} not comparable")
    if vs.get("compartments_f1") is not None or vs.get("flows_f1") is not None:
        print(f"  Structure: compartments F1={vs.get('compartments_f1')}  flows F1={vs.get('flows_f1')} (vs gold)")

    # 3b) Completeness score (0-100)
    completeness = compute_completeness_score(
        gaps_before=gaps,
        gaps_after=gaps_after,
        filled_result=filled,
        validation_summary=vs,
        threelayer=threelayer,
        structural_before=structural if structural.get("available") else None,
        structural_after=structural_after if structural_after.get("available") else None,
    )
    completeness_path = output_dir / "phase3_completeness.json"
    with open(completeness_path, "w", encoding="utf-8") as f:
        json.dump(completeness, f, indent=2)
    comp_c = completeness["components"]
    struct_str = (
        f"  struct={comp_c.get('structural_integrity_pct','—')}%"
        if "structural_integrity_pct" in comp_c else ""
    )
    print(f"  Complete: {completeness['completeness_score']}/100  "
          f"(gap_red={comp_c['gap_reduction_pct']}%  "
          f"ref_agree={comp_c['reference_agreement_pct']}%  "
          f"trace={comp_c['fill_traceability_pct']}%  "
          f"param_acc={comp_c['parameter_accuracy_pct']}%"
          f"{struct_str})")

    # 4) Report
    report_md = output_dir / "gap_report.md"
    generate_gap_report(
        gaps,
        filled,
        report_md,
        disease_hint=disease,
        validation=validation,
        improvement=improvement,
        threelayer=threelayer,
        completeness=completeness,
        structural=structural,
        structural_after=structural_after,
        repair_report=repair_report,
    )
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
