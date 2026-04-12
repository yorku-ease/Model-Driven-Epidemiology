#!/usr/bin/env python3
"""
Phase 3: RAG and Gap Filling

Usage:
  # Step 1 — build the comprehensive database (run once, or when Phase 2 data changes)
  python build_database.py

  # Step 2 — showcase (benchmark): best Phase 2 per disease × retrieval_only / llm_only / both
  python run_phase3.py --showcase --llm-provider claude
  # (default --output reports — same folder name as --all; layouts differ)

  # Or: single Phase 2 report
  python run_phase3.py --phase2-report "../phase 2/reports/<disease>_llm_<provider>_<timestamp>" \\
      --output reports/<disease>_phase3

  # Or: ALL latest Phase 2 reports (one run per disease×provider)
  python run_phase3.py --all --output reports
  python run_phase3.py --all --llm-provider gemini --output reports
  python run_phase3.py --all --no-rag --no-inference --output reports
"""

import argparse
import importlib.util
import json
import os
import sys
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple

PHASE3_DIR = Path(__file__).resolve().parent
PHASE2_DIR = PHASE3_DIR.parent / "phase 2"
sys.path.insert(0, str(PHASE3_DIR))

_p2_paths_spec = importlib.util.spec_from_file_location(
    "phase2_paths",
    PHASE2_DIR / "src" / "utils" / "phase2_paths.py",
)
assert _p2_paths_spec and _p2_paths_spec.loader
_phase2_paths_mod = importlib.util.module_from_spec(_p2_paths_spec)
_p2_paths_spec.loader.exec_module(_phase2_paths_mod)
find_gold_compmodel_for_run_stem = _phase2_paths_mod.find_gold_compmodel_for_run_stem

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
from src.rag.paper_database import load_paper_database
from src.reporting.gap_report import generate_gap_report


DB_PATH = PHASE3_DIR / "data" / "paper_database"
PHASE1_DIR = PHASE3_DIR.parent / "phase 1"
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
    """Auto-discover gold-standard .compmodel for a run id (e.g. covid2) or disease name."""
    d = disease.lower()
    g = find_gold_compmodel_for_run_stem(d)
    if g:
        return g
    if BASELINES_DIR.is_dir():
        for cm in BASELINES_DIR.glob("*.compmodel"):
            if d in cm.stem.lower():
                return cm
    if PHASE1_DIR.is_dir():
        for cm in PHASE1_DIR.glob("papers/**/*.compmodel"):
            if d in cm.stem.lower():
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

    # 2) Gap filling — iterative loop (max 2 rounds)
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
        all_fill_records.extend(filled_iter.get("filled_gaps", []))
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
        filled["filled_gaps"] = all_fill_records  # used by compute_completeness_score
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

    # 3a-guard) Non-regression: if fills degraded structural F1 vs the Phase 2 draft,
    #           revert model_filled.compmodel to the original draft so Phase 3 never
    #           makes the model worse than what Phase 2 already produced.
    struct_align = validation.get("structural_alignment", {})
    draft_align  = struct_align.get("draft_vs_gold", {})
    filled_align = struct_align.get("filled_vs_gold", {})
    draft_comp_f1  = draft_align.get("compartments",  {}).get("f1", 0.0) or 0.0
    draft_flow_f1  = draft_align.get("flows",         {}).get("f1", 0.0) or 0.0
    filled_comp_f1 = filled_align.get("compartments", {}).get("f1", 0.0) or 0.0
    filled_flow_f1 = filled_align.get("flows",        {}).get("f1", 0.0) or 0.0
    draft_f1_avg   = (draft_comp_f1  + draft_flow_f1)  / 2.0
    filled_f1_avg  = (filled_comp_f1 + filled_flow_f1) / 2.0
    if filled_align and filled_f1_avg < draft_f1_avg - 0.005:
        import shutil as _shutil
        print(f"  [NON-REGRESSION] Filled F1={filled_f1_avg:.3f} < Draft F1={draft_f1_avg:.3f}"
              " — reverting to Phase 2 draft to prevent regression")
        _shutil.copy2(model_path, filled_model_path)
        # Re-run validation against the reverted model so scores reflect the draft
        validation = evaluate_phase3_full(
            filled,
            gold_standard=gold_rich,
            gold_compmodel_path=gold_path,
            draft_compmodel_path=model_path if model_path.exists() else None,
            filled_compmodel_path=filled_model_path if filled_model_path.exists() else None,
        )
        with open(val_path, "w", encoding="utf-8") as f:
            json.dump(validation, f, indent=2)
        vs = validation["summary"]

    # 3b) Completeness score (0-100)
    completeness = compute_completeness_score(
        gaps_before=gaps,
        gaps_after=gaps_after,
        filled_result=filled,
        validation_summary=vs,
        threelayer=threelayer,
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
    )
    print(f"  Report  : {report_md}")


# --- Showcase: best Phase 2 per disease × retrieval_only / llm_only / both -----------------


def _load_json(path: Path) -> Dict[str, Any]:
    try:
        with open(path, "r", encoding="utf-8") as f:
            return json.load(f)
    except Exception:
        return {}


def _phase2_eval_path(report_dir: Path) -> Path:
    return report_dir / "evaluation_report.json"


def phase2_composite_score(eval_data: Dict[str, Any]) -> float:
    """Composite Phase 2 score (0–100) from evaluation_report.json (traceability, faithfulness, recall)."""
    if not eval_data:
        return 0.0
    score = 0.0
    weight_sum = 0.0
    cov = (eval_data.get("traceability_coverage") or {}).get("coverage_percentage")
    if cov is not None:
        score += float(cov) * 0.3
        weight_sum += 0.3
    faith = (eval_data.get("faithfulness") or {}).get("faithfulness_percentage")
    if faith is not None:
        score += float(faith) * 0.3
        weight_sum += 0.3
    gap_analysis = eval_data.get("gap_analysis") or {}
    total_gaps = gap_analysis.get("total_gaps", 0) or 0
    gap_penalty = max(0, 100 - int(total_gaps) * 10)
    score += gap_penalty * 0.2
    weight_sum += 0.2
    gold = eval_data.get("gold_standard_comparison") or {}
    params = gold.get("parameters") or {}
    f1 = params.get("f1")
    if f1 is not None:
        score += float(f1) * 100 * 0.2
        weight_sum += 0.2
    else:
        score += 50 * 0.2
        weight_sum += 0.2
    if weight_sum == 0:
        return 50.0
    return score / weight_sum


def phase2_extractor_from_report_dir(report_dir: Path) -> str:
    name = report_dir.name
    if "_llm_" not in name:
        return "unknown"
    rest = name.split("_llm_", 1)[1]
    return rest.split("_")[0].lower()


def discover_all_phase2_reports_for_disease(reports_dir: Path, disease: str) -> List[Path]:
    prefix = f"{disease}_llm_"
    return sorted(
        [p for p in reports_dir.iterdir() if p.is_dir() and p.name.startswith(prefix)],
        key=lambda p: p.name,
    )


def best_phase2_report(
    reports_dir: Path,
    disease: str,
) -> Optional[Tuple[Path, float]]:
    candidates = discover_all_phase2_reports_for_disease(reports_dir, disease)
    if not candidates:
        return None
    best: Optional[Tuple[Path, float]] = None
    for p in candidates:
        ev = _load_json(_phase2_eval_path(p))
        s = phase2_composite_score(ev)
        if best is None or s > best[1]:
            best = (p, s)
    return best


def all_diseases_with_reports(reports_dir: Path) -> List[str]:
    diseases: set = set()
    for d in reports_dir.iterdir():
        if not d.is_dir() or "_llm_" not in d.name:
            continue
        diseases.add(infer_disease(d))
    return sorted(diseases)


def score_phase3_run(phase3_dir: Path) -> Tuple[float, Dict[str, Any]]:
    gaps = _load_json(phase3_dir / "phase3_gaps.json")
    val = _load_json(phase3_dir / "phase3_validation.json")
    g = int((gaps.get("summary") or {}).get("total_gaps", 0))
    vs = val.get("summary") or {}
    compared = vs.get("compared", 0) or 0
    acc = float(vs.get("accuracy_pct") or 0) if compared else 0.0
    cf1 = float(vs.get("compartments_f1") or 0)
    ff1 = float(vs.get("flows_f1") or 0)
    struct_f1 = (cf1 + ff1) / 2.0 if (cf1 or ff1) else 0.0
    sort_key = g * 1e6 - acc - struct_f1 * 500.0
    detail = {
        "total_gaps": g,
        "accuracy_pct": round(acc, 2),
        "compartments_f1": round(cf1, 4) if cf1 else None,
        "flows_f1": round(ff1, 4) if ff1 else None,
        "compared_params": compared,
    }
    return sort_key, detail


def write_showcase_report(
    base: Path,
    rows: List[Dict[str, Any]],
    *,
    phase3_llm_provider: str,
) -> None:
    prov = phase3_llm_provider
    lines = [
        f"# Phase 3 — Showcase (best Phase 2 LLM × Retrieval / LLM / both, Phase 3 inference: **{prov}**)",
        "",
        "For each disease: **best Phase 2** run among **gemini / openai / claude** (by evaluation score), then Phase 3 with **your chosen** `--llm-provider` (**"
        f"{prov}** here) for inference in three modes.",
        "",
        "| Disease | Winning Phase 2 LLM | Best Phase 2 report | Phase2 score | Winner | retrieval_only | llm_only | both |",
        "|---------|----------------------|---------------------|--------------|--------|----------|----------|------|",
    ]
    for r in rows:
        d = r["disease_display"]
        br = Path(r["best_phase2_report"]).name
        p2s = r["phase2_score"]
        w = r["winner_mode"]
        ex = r.get("best_phase2_extractor", "—")

        def _fmt(mode: str) -> str:
            return r["modes"][mode].get("summary_str", "—")

        lines.append(
            f"| {d} | **{ex}** | `{br}` | {p2s:.1f} | **{w}** | {_fmt('retrieval_only')} | {_fmt('llm_only')} | {_fmt('both')} |"
        )
    lines.extend([
        "",
        "## How to read",
        "",
        "- **Winner** = lowest gap count, then highest parameter accuracy vs gold, then highest mean compartment/flow F1.",
        f"- Subfolders: `retrieval_only/`, `llm_only/`, `both/` each contain `<disease>_{prov}_phase3/` with `phase3_gaps.json`, …, and `phase3_showcase_source.json` (which Phase 2 folder was used).",
        "",
    ])
    (base / "SHOWCASE_REPORT.md").write_text("\n".join(lines), encoding="utf-8")
    with open(base / "showcase_summary.json", "w", encoding="utf-8") as f:
        json.dump(rows, f, indent=2, ensure_ascii=False)


def run_showcase(
    *,
    phase2_reports: Path,
    out_base: Path,
    llm_pf: str,
    gemini_model: str,
    diseases_filter: Optional[List[str]],
) -> None:
    """Best Phase 2 per disease × retrieval_only / llm_only / both."""
    idx_file = DB_PATH / "index.json"
    if not idx_file.exists():
        print("Paper database not found. Run:  python build_database.py")
        sys.exit(1)
    db = load_paper_database(DB_PATH)
    print(
        f"Paper database: {db.get('num_entries', 0)} entries, "
        f"{db.get('num_parameters', 0)} parameters"
    )
    print(f"Phase 3 inference provider (your choice): {llm_pf}")
    if llm_pf == "gemini":
        os.environ["GEMINI_MODEL"] = gemini_model
        print(f"Gemini model: {os.environ['GEMINI_MODEL']}")
    print("Phase 2 per disease: best among all *_llm_* runs (gemini / openai / claude).")

    if diseases_filter:
        diseases = diseases_filter
    else:
        diseases = all_diseases_with_reports(phase2_reports)

    if not diseases:
        print(f"No diseases found with *_llm_* reports under {phase2_reports}")
        sys.exit(1)

    rows: List[Dict[str, Any]] = []

    for disease in diseases:
        picked = best_phase2_report(phase2_reports, disease)
        if not picked:
            print(f"\n[SKIP] {disease}: no Phase 2 report matching {disease}_llm_*")
            continue
        report_dir, p2_score = picked
        p2_extractor = phase2_extractor_from_report_dir(report_dir)
        print(f"\n{'='*60}")
        print(
            f"Disease: {disease}  |  Best Phase 2: {report_dir.name}  "
            f"(extractor: {p2_extractor}, score {p2_score:.2f})"
        )

        modes = {
            "retrieval_only": {"use_rag": True, "use_inference": False},
            "llm_only": {"use_rag": False, "use_inference": True},
            "both": {"use_rag": True, "use_inference": True},
        }
        mode_results: Dict[str, Any] = {}

        for mode_name, flags in modes.items():
            sub_out = out_base / mode_name / f"{disease}_{llm_pf}_phase3"
            run_for_report(
                report_dir,
                sub_out,
                use_rag=flags["use_rag"],
                use_inference=flags["use_inference"],
                llm_provider=llm_pf,
            )
            src_meta = {
                "phase2_report_dir": str(report_dir),
                "phase2_extractor": p2_extractor,
                "phase3_inference_provider": llm_pf,
                "showcase_mode": mode_name,
            }
            with open(sub_out / "phase3_showcase_source.json", "w", encoding="utf-8") as f:
                json.dump(src_meta, f, indent=2, ensure_ascii=False)
            sk, detail = score_phase3_run(sub_out)
            filled = _load_json(sub_out / "phase3_filled.json").get("summary") or {}
            detail["sort_key"] = sk
            detail["rag_count"] = filled.get("rag_count", 0)
            detail["inference_count"] = filled.get("inference_count", 0)
            detail["flagged_count"] = filled.get("flagged_count", 0)
            detail["summary_str"] = (
                f"g={detail['total_gaps']} acc={detail['accuracy_pct']}% "
                f"cF1={detail.get('compartments_f1')} fF1={detail.get('flows_f1')}"
            )
            mode_results[mode_name] = detail

        winner = min(mode_results.keys(), key=lambda m: mode_results[m]["sort_key"])
        rows.append({
            "disease": disease,
            "disease_display": disease.replace("_", " ").title(),
            "best_phase2_report": str(report_dir),
            "best_phase2_extractor": p2_extractor,
            "phase2_score": round(p2_score, 3),
            "phase3_llm_provider": llm_pf,
            "phase3_gemini_model": os.environ.get("GEMINI_MODEL") if llm_pf == "gemini" else None,
            "modes": mode_results,
            "winner_mode": winner,
        })

    out_base.mkdir(parents=True, exist_ok=True)
    write_showcase_report(out_base, rows, phase3_llm_provider=llm_pf)

    print(f"\n{'='*60}")
    print(f"Showcase complete: {out_base}")
    print(f"  Report: {out_base / 'SHOWCASE_REPORT.md'}")
    print(f"  JSON:   {out_base / 'showcase_summary.json'}")
    phase4_dir = (PHASE3_DIR.parent / "phase 4").resolve()
    print("\nOptional: for Phase 4, build selected_models/ from a showcase mode (e.g. both):")
    print(f'  cd "{phase4_dir}" && python3 run_phase4.py --create-selected-models \\')
    print(f'      --showcase-dir "{out_base.resolve()}" --mode both')


def main():
    ap = argparse.ArgumentParser(description="Phase 3: RAG and Gap Filling")
    ap.add_argument(
        "--showcase",
        action="store_true",
        help=(
            "Benchmark mode: per disease, pick best Phase 2 among gemini/openai/claude, "
            "then run retrieval_only / llm_only / both. Requires --llm-provider (or PHASE3_LLM_PROVIDER). "
            "Default output dir: reports (same folder name as --all batch runs; layouts differ)."
        ),
    )
    ap.add_argument("--phase2-report", type=str, help="Path to a single Phase 2 report directory")
    ap.add_argument("--all", action="store_true", help="Run for ALL latest Phase 2 reports")
    ap.add_argument(
        "--output",
        type=str,
        default=None,
        help="Output directory (default: reports for --all, --phase2-report, and --showcase)",
    )
    ap.add_argument("--llm-provider", type=str,
                    choices=["openai", "gemini", "claude"],
                    default=None,
                    help="LLM provider for inference (default: auto-detect from Phase 2 report name)")
    ap.add_argument("--no-rag", action="store_true", help="Skip RAG lookup")
    ap.add_argument("--no-inference", action="store_true", help="Skip LLM inference")
    ap.add_argument(
        "--phase2-reports",
        type=str,
        default=None,
        help="[--showcase] Phase 2 reports directory (default: ../phase 2/reports)",
    )
    ap.add_argument(
        "--gemini-model",
        type=str,
        default="gemini-2.5-flash",
        help="[--showcase] Gemini model id when --llm-provider gemini",
    )
    ap.add_argument(
        "--diseases",
        type=str,
        default=None,
        help="[--showcase] Comma-separated disease slugs (default: all with *_llm_* reports)",
    )
    args = ap.parse_args()

    idx_file = DB_PATH / "index.json"
    if not idx_file.exists():
        print("Paper database not found. Run  python build_database.py  first.")
        sys.exit(1)

    if args.showcase:
        llm_pf = args.llm_provider or os.environ.get("PHASE3_LLM_PROVIDER", "").strip().lower()
        if llm_pf not in ("gemini", "openai", "claude"):
            print(
                "Showcase requires Phase 3 inference provider:\n"
                "  --llm-provider {gemini,openai,claude}\n"
                "Or:  PHASE3_LLM_PROVIDER=gemini",
                file=sys.stderr,
            )
            sys.exit(2)
        phase2_reports = (
            Path(args.phase2_reports)
            if args.phase2_reports
            else (PHASE3_DIR.parent / "phase 2" / "reports")
        )
        out_base = Path(args.output or "reports")
        diseases_filter = None
        if args.diseases:
            diseases_filter = [x.strip().lower() for x in args.diseases.split(",") if x.strip()]
        run_showcase(
            phase2_reports=phase2_reports,
            out_base=out_base,
            llm_pf=llm_pf,
            gemini_model=args.gemini_model,
            diseases_filter=diseases_filter,
        )
        print(f"\n{'='*60}")
        print("Phase 3 complete.")
        return

    if not args.phase2_report and not args.all:
        ap.error("Specify --showcase, or --phase2-report <dir>, or --all")

    db = load_paper_database(DB_PATH)
    print(f"Paper database: {db.get('num_entries',0)} entries, {db.get('num_parameters',0)} parameters")

    out_default = Path(args.output or "reports")

    if args.all:
        dirs = _latest_report_dirs()
        if not dirs:
            print("No Phase 2 report directories found.")
            sys.exit(1)
        print(f"Running Phase 3 for {len(dirs)} reports...")
        out_base = out_default
        for rdir in sorted(dirs, key=lambda p: p.name):
            disease = infer_disease(rdir)
            provider = rdir.name.split("_llm_")[1].split("_")[0] if "_llm_" in rdir.name else "unknown"
            out = out_base / f"{disease}_{provider}_phase3"
            run_for_report(rdir, out, use_rag=not args.no_rag, use_inference=not args.no_inference,
                           llm_provider=args.llm_provider)

        print(f"\n  Per-report outputs in: {out_base}/<disease>_<provider>_phase3/")
        print("  For Phase 4 on many models: run --showcase, then phase 4/run_phase4.py --create-selected-models; or pass --model to run_phase4.py per model_filled.compmodel.")
    else:
        rdir = Path(args.phase2_report)
        if not rdir.is_dir():
            print(f"Error: not a directory: {rdir}")
            sys.exit(1)
        run_for_report(rdir, out_default, use_rag=not args.no_rag, use_inference=not args.no_inference,
                       llm_provider=args.llm_provider)

    print(f"\n{'='*60}")
    print("Phase 3 complete.")


if __name__ == "__main__":
    main()
