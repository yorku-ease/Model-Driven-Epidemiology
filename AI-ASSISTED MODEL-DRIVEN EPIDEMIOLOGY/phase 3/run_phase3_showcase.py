#!/usr/bin/env python3
"""
Phase 3 showcase: best Phase 2 report per disease × three fill strategies.

For each disease:
  1. **Phase 2 — pick the best extractor:** among all Phase 2 folders
     ``<disease>_llm_<gemini|openai|claude>_`` (every run you have for that disease),
     choose the one with the **highest composite evaluation score** (same weighting as
     ``select_best_model.py``). So you compare the three LLMs from Phase 2 and work
     from the winner.
  2. **Phase 3 — you choose the inference API:** run Phase 3 **three times** on that
     winning report using ``--llm-provider`` or ``PHASE3_LLM_PROVIDER`` (independent of
     which LLM won Phase 2):
     - **rag_only** — RAG + flag (no LLM inference)
     - **llm_only** — LLM + flag (no RAG)
     - **both** — RAG + LLM + flag

Outputs under ``--output`` (default ``showcase_phase3``)::

  <output>/
    rag_only/<disease>_<llm_provider>_phase3/
    llm_only/<disease>_<llm_provider>_phase3/
    both/<disease>_<llm_provider>_phase3/
    SHOWCASE_REPORT.md
    showcase_summary.json
    <mode>/<disease>_<phase3_llm>_phase3/phase3_showcase_source.json  (which Phase 2 dir was used)

Requires: ``python build_database.py`` once; ``phase 2/.api_key.txt`` for llm_only and both.
If ``--llm-provider gemini`` is used, default model is set to ``gemini-2.5-flash`` unless
``--gemini-model`` is provided.

Usage::

  cd phase 3
  python3 build_database.py
  python3 run_phase3_showcase.py --llm-provider claude --output showcase_claude
  python3 run_phase3_showcase.py --llm-provider gemini --gemini-model gemini-2.5-flash --output showcase_gemini
"""

from __future__ import annotations

import argparse
import json
import os
import sys
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple

PHASE3_DIR = Path(__file__).resolve().parent
sys.path.insert(0, str(PHASE3_DIR))

from run_phase3 import (  # noqa: E402
    DB_PATH,
    infer_disease,
    run_for_report,
)
from src.rag.paper_database import load_paper_database  # noqa: E402


def _load_json(path: Path) -> Dict[str, Any]:
    try:
        with open(path, "r", encoding="utf-8") as f:
            return json.load(f)
    except Exception:
        return {}


def _phase2_eval_path(report_dir: Path) -> Path:
    """Prefer fuzzy eval if present (Phase 2 standard for gold comparison)."""
    fuzzy = report_dir / "evaluation_report_fuzzy_temp.json"
    if fuzzy.exists():
        return fuzzy
    return report_dir / "evaluation_report.json"


def phase2_composite_score(eval_data: Dict[str, Any]) -> float:
    """Same weighting idea as select_best_model._phase2_score (0–100)."""
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
    """``dengue_llm_openai_20260321_...`` → ``openai``."""
    name = report_dir.name
    if "_llm_" not in name:
        return "unknown"
    rest = name.split("_llm_", 1)[1]
    return rest.split("_")[0].lower()


def discover_all_phase2_reports_for_disease(reports_dir: Path, disease: str) -> List[Path]:
    """All ``<disease>_llm_*`` Phase 2 runs (gemini, openai, claude, …)."""
    prefix = f"{disease}_llm_"
    return sorted(
        [p for p in reports_dir.iterdir() if p.is_dir() and p.name.startswith(prefix)],
        key=lambda p: p.name,
    )


def best_phase2_report(
    reports_dir: Path,
    disease: str,
) -> Optional[Tuple[Path, float]]:
    """Return (report_dir, score) with highest composite Phase 2 score among all LLM runs."""
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


def all_diseases(reports_dir: Path) -> List[str]:
    """Diseases that have at least one ``*_llm_*`` Phase 2 report."""
    diseases: set = set()
    for d in reports_dir.iterdir():
        if not d.is_dir() or "_llm_" not in d.name:
            continue
        diseases.add(infer_disease(d))
    return sorted(diseases)


def score_phase3_run(phase3_dir: Path) -> Tuple[float, Dict[str, Any]]:
    """Lower sort_key = better. Matches select_best_model spirit."""
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
        f"# Phase 3 — Showcase (best Phase 2 LLM × RAG / LLM / both, Phase 3 inference: **{prov}**)",
        "",
        "For each disease: **best Phase 2** run among **gemini / openai / claude** (by evaluation score), then Phase 3 with **your chosen** `--llm-provider` (**"
        f"{prov}** here) for inference in three modes.",
        "",
        "| Disease | Winning Phase 2 LLM | Best Phase 2 report | Phase2 score | Winner | rag_only | llm_only | both |",
        "|---------|----------------------|---------------------|--------------|--------|----------|----------|------|",
    ]
    for r in rows:
        d = r["disease_display"]
        br = Path(r["best_phase2_report"]).name
        p2s = r["phase2_score"]
        w = r["winner_mode"]
        ex = r.get("best_phase2_extractor", "—")
        fmt = lambda mode: r["modes"][mode].get("summary_str", "—")
        lines.append(
            f"| {d} | **{ex}** | `{br}` | {p2s:.1f} | **{w}** | {fmt('rag_only')} | {fmt('llm_only')} | {fmt('both')} |"
        )
    lines.extend([
        "",
        "## How to read",
        "",
        "- **Winner** = lowest gap count, then highest parameter accuracy vs gold, then highest mean compartment/flow F1 (same ordering as `select_best_model.py`).",
        f"- Subfolders: `rag_only/`, `llm_only/`, `both/` each contain `<disease>_{prov}_phase3/` with `phase3_gaps.json`, …, and `phase3_showcase_source.json` (which Phase 2 folder was used).",
        "",
    ])
    (base / "SHOWCASE_REPORT.md").write_text("\n".join(lines), encoding="utf-8")
    with open(base / "showcase_summary.json", "w", encoding="utf-8") as f:
        json.dump(rows, f, indent=2, ensure_ascii=False)


def main() -> None:
    ap = argparse.ArgumentParser(
        description=(
            "Phase 3: for each disease, pick best Phase 2 among gemini/openai/claude, "
            "then run rag_only / llm_only / both (Phase 3 inference: --llm-provider)."
        ),
    )
    ap.add_argument(
        "--phase2-reports",
        type=str,
        default=None,
        help="Phase 2 reports directory (default: ../phase 2/reports)",
    )
    ap.add_argument(
        "--output",
        type=str,
        default="showcase_phase3",
        help="Output base directory (default: showcase_phase3)",
    )
    ap.add_argument(
        "--llm-provider",
        type=str,
        choices=["gemini", "openai", "claude"],
        default=None,
        help="LLM for Phase 3 inference. If omitted, uses env PHASE3_LLM_PROVIDER. Output dirs: <disease>_<provider>_phase3/.",
    )
    ap.add_argument(
        "--gemini-model",
        type=str,
        default="gemini-2.5-flash",
        help="Gemini model id when --llm-provider gemini (default: gemini-2.5-flash).",
    )
    ap.add_argument(
        "--diseases",
        type=str,
        default=None,
        help="Comma-separated disease slugs (default: all diseases with at least one *_llm_* report)",
    )
    args = ap.parse_args()

    llm_pf = args.llm_provider or os.environ.get("PHASE3_LLM_PROVIDER", "").strip().lower()
    if llm_pf not in ("gemini", "openai", "claude"):
        print(
            "Specify Phase 3 inference provider:  --llm-provider {gemini,openai,claude}\n"
            "Or set environment variable:  PHASE3_LLM_PROVIDER=gemini",
            file=sys.stderr,
        )
        sys.exit(2)

    phase2_reports = Path(args.phase2_reports) if args.phase2_reports else (PHASE3_DIR.parent / "phase 2" / "reports")
    out_base = Path(args.output)

    idx_file = DB_PATH / "index.json"
    if not idx_file.exists():
        print("Paper database not found. Run:  python build_database.py")
        sys.exit(1)
    db = load_paper_database(DB_PATH)
    print(f"Paper database: {db.get('num_entries', 0)} entries")
    print(f"Phase 3 inference provider (your choice): {llm_pf}")
    if llm_pf == "gemini":
        os.environ["GEMINI_MODEL"] = args.gemini_model
        print(f"Gemini model: {os.environ['GEMINI_MODEL']}")
    print("Phase 2 per disease: best among all *_llm_* runs (gemini / openai / claude).")

    if args.diseases:
        diseases = [x.strip().lower() for x in args.diseases.split(",") if x.strip()]
    else:
        diseases = all_diseases(phase2_reports)

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
        print(f"Disease: {disease}  |  Best Phase 2: {report_dir.name}  (extractor: {p2_extractor}, score {p2_score:.2f})")

        modes = {
            "rag_only": {"use_rag": True, "use_inference": False},
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

        # Winner: lowest sort_key
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
    print("\nOptional: compare three roots with select_best_model.py:")
    roots = f"{out_base}/rag_only {out_base}/llm_only {out_base}/both"
    print(f"  python3 select_best_model.py --phase2-reports \"{phase2_reports}\" \\")
    print(f"      --phase3-roots {roots} \\")
    print("      --output selected_from_showcase")


if __name__ == "__main__":
    main()
