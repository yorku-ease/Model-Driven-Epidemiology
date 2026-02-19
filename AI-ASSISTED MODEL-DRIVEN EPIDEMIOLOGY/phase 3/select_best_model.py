#!/usr/bin/env python3
"""
Select the best filled model per disease from multiple Phase 3 runs.

Use after running Phase 3 with each LLM provider (e.g. once with --llm-provider gemini
--output reports/gemini, once with openai, once with claude). This script discovers
all candidate Phase 3 output dirs (from one or more roots), scores each candidate using
Phase 2 evaluation and Phase 3 validation, and writes one model per disease to --output.

Scoring (no hardcoded diseases or providers):
  - Prefer more complete: fewer Phase 3 gaps first.
  - Then prefer better Phase 2 extraction: traceability, faithfulness, Phase 2 gaps, gold F1.
  - Then prefer better Phase 3 fill accuracy (vs gold standard).

Usage:
  python select_best_model.py --phase2-reports "../phase 2/reports" \\
      --phase3-roots reports/gemini reports/openai reports/claude \\
      --output selected_models
"""

import argparse
import json
import shutil
import sys
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple

PHASE3_DIR = Path(__file__).resolve().parent
sys.path.insert(0, str(PHASE3_DIR))


def _discover_phase3_candidates(roots: List[Path]) -> List[Tuple[Path, Path]]:
    """Return [(root, subdir), ...] for every dir named *_phase3 under each root."""
    out: List[Tuple[Path, Path]] = []
    for root in roots:
        r = Path(root)
        if not r.is_dir():
            continue
        for sub in r.iterdir():
            if sub.is_dir() and sub.name.endswith("_phase3"):
                out.append((r, sub))
    return out


def _disease_provider_from_dirname(dirname: str) -> Tuple[str, str]:
    """From 'disease_provider_phase3' return (disease, provider)."""
    base = dirname.replace("_phase3", "")
    parts = base.rsplit("_", 1)
    if len(parts) == 2:
        return parts[0], parts[1]
    return base, "unknown"


def _find_phase2_report(phase2_reports: Path, disease: str, provider: str) -> Optional[Path]:
    """Return the latest Phase 2 report dir for disease_llm_{provider}_*."""
    if not phase2_reports.is_dir():
        return None
    prefix = f"{disease}_llm_{provider}_"
    candidates = [d for d in phase2_reports.iterdir() if d.is_dir() and d.name.startswith(prefix)]
    if not candidates:
        return None
    return max(candidates, key=lambda p: p.name)


def _phase2_score(eval_data: Dict[str, Any]) -> float:
    """
    Composite 0–100 score from Phase 2 evaluation_report.json.
    Phase 2 is weighted as more important; uses traceability, faithfulness, gaps, gold F1.
    """
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


def _load_phase2_evaluation(phase2_report: Path) -> Dict[str, Any]:
    path = phase2_report / "evaluation_report.json"
    if not path.exists():
        return {}
    try:
        with open(path, "r", encoding="utf-8") as f:
            return json.load(f)
    except Exception:
        return {}


def _load_phase3_gaps(phase3_dir: Path) -> Dict[str, Any]:
    path = phase3_dir / "phase3_gaps.json"
    if not path.exists():
        return {}
    try:
        with open(path, "r", encoding="utf-8") as f:
            return json.load(f)
    except Exception:
        return {}


def _load_phase3_validation(phase3_dir: Path) -> Dict[str, Any]:
    path = phase3_dir / "phase3_validation.json"
    if not path.exists():
        return {}
    try:
        with open(path, "r", encoding="utf-8") as f:
            return json.load(f)
    except Exception:
        return {}


def score_candidate(
    phase2_reports: Path,
    phase3_dir: Path,
    disease: str,
    provider: str,
) -> Tuple[float, int, float, float, Optional[Path]]:
    """
    Return (sort_key_primary, phase3_gaps, phase2_score, phase3_accuracy, phase2_report_path).
    Sort key: lower gaps better, then higher phase2 better, then higher phase3 accuracy better.
    We return a single sort_key_primary = phase3_gaps * 1e6 - phase2_score * 1e3 - phase3_accuracy
    so that sorting ascending gives: fewer gaps first, then higher phase2, then higher phase3.
    """
    phase2_report = _find_phase2_report(phase2_reports, disease, provider)
    phase2_data = _load_phase2_evaluation(phase2_report) if phase2_report else {}
    gaps_data = _load_phase3_gaps(phase3_dir)
    val_data = _load_phase3_validation(phase3_dir)

    phase3_gaps = int((gaps_data.get("summary") or {}).get("total_gaps", 0))
    phase2_score = _phase2_score(phase2_data)
    val_summary = val_data.get("summary") or {}
    compared = val_summary.get("compared", 0) or 0
    phase3_accuracy = float(val_summary.get("accuracy_pct") or 0) if compared else 0.0

    # Primary: fewer gaps better; secondary: higher phase2 better; tertiary: higher phase3 accuracy
    sort_key = phase3_gaps * 1e6 - phase2_score * 1e3 - phase3_accuracy
    return (sort_key, phase3_gaps, phase2_score, phase3_accuracy, phase2_report)


def main():
    ap = argparse.ArgumentParser(
        description="Select best filled model per disease from Phase 3 runs (e.g. gemini/openai/claude)."
    )
    ap.add_argument(
        "--phase2-reports",
        type=str,
        default=None,
        help="Path to Phase 2 reports directory (default: ../phase 2/reports)",
    )
    ap.add_argument(
        "--phase3-roots",
        type=str,
        nargs="+",
        required=True,
        help="One or more Phase 3 output roots (e.g. reports/gemini reports/openai reports/claude)",
    )
    ap.add_argument(
        "--output",
        type=str,
        default="selected_models",
        help="Output directory: one subdir per disease with model_filled.compmodel and selection_report.json",
    )
    args = ap.parse_args()

    phase2_reports = Path(args.phase2_reports) if args.phase2_reports else (PHASE3_DIR.parent / "phase 2" / "reports")
    phase3_roots = [Path(p) for p in args.phase3_roots]
    out_base = Path(args.output)

    candidates = _discover_phase3_candidates(phase3_roots)
    if not candidates:
        print("No Phase 3 candidate directories found (expect *_phase3 under each root).")
        print("If you ran Phase 3 once with --output reports, use a single root:")
        print("  --phase3-roots reports")
        print("If you ran Phase 3 three times (gemini/openai/claude), use:")
        print("  --phase3-roots reports/gemini reports/openai reports/claude")
        sys.exit(1)

    by_disease: Dict[str, List[Tuple[Path, Path, str, str]]] = {}
    for root, subdir in candidates:
        disease, provider = _disease_provider_from_dirname(subdir.name)
        by_disease.setdefault(disease, []).append((root, subdir, disease, provider))

    out_base.mkdir(parents=True, exist_ok=True)
    report_lines: List[str] = ["# Phase 3 — Best model per disease", ""]
    selected_summary: List[Dict[str, Any]] = []

    for disease in sorted(by_disease.keys()):
        list_candidates = by_disease[disease]
        scored: List[Tuple[float, int, float, float, Optional[Path], Path, str]] = []
        for root, subdir, d, prov in list_candidates:
            sk, p3_gaps, p2_score, p3_acc, p2_path = score_candidate(phase2_reports, subdir, d, prov)
            scored.append((sk, p3_gaps, p2_score, p3_acc, p2_path, subdir, prov))
        scored.sort(key=lambda x: x[0])

        best = scored[0]
        _sort_key, best_gaps, best_p2, best_p3_acc, best_p2_report, best_subdir, best_provider = best

        disease_dir = out_base / disease
        disease_dir.mkdir(parents=True, exist_ok=True)
        filled_src = best_subdir / "model_filled.compmodel"
        if filled_src.exists():
            shutil.copy2(filled_src, disease_dir / "model_filled.compmodel")
        else:
            # Fallback: copy draft from Phase 2 if no filled model
            if best_p2_report and (best_p2_report / "model_draft.compmodel").exists():
                shutil.copy2(best_p2_report / "model_draft.compmodel", disease_dir / "model_filled.compmodel")

        selection = {
            "disease": disease,
            "chosen_phase3_dir": str(best_subdir),
            "chosen_phase2_provider": best_provider,
            "phase2_report": str(best_p2_report) if best_p2_report else None,
            "phase3_gaps": best_gaps,
            "phase2_score": round(best_p2, 2),
            "phase3_accuracy_pct": round(best_p3_acc, 1),
            "candidates_considered": len(scored),
        }
        with open(disease_dir / "selection_report.json", "w", encoding="utf-8") as f:
            json.dump(selection, f, indent=2)

        report_lines.append(f"## {disease.replace('_', ' ').title()}")
        report_lines.append(f"- **Chosen:** `{best_subdir.name}` (Phase 2 extractor: {best_provider})")
        report_lines.append(f"- Phase 3 gaps: {best_gaps} | Phase 2 score: {best_p2:.1f} | Phase 3 accuracy: {best_p3_acc:.1f}%")
        report_lines.append(f"- Candidates: {len(scored)}")
        report_lines.append("")
        selected_summary.append({
            "disease": disease,
            "display": disease.replace("_", " ").title(),
            "chosen_dir": best_subdir.name,
            "phase2_provider": best_provider,
            "phase3_gaps": best_gaps,
            "phase2_score": round(best_p2, 1),
            "phase3_accuracy_pct": round(best_p3_acc, 1),
            "candidates_considered": len(scored),
        })

    report_path = out_base / "SELECTION_REPORT.md"
    report_path.write_text("\n".join(report_lines), encoding="utf-8")

    # PHASE3_OVERALL_REPORT.md — overall summary over all selected models (created when we have everything)
    overall_lines: List[str] = [
        "# Phase 3 — Overall Report (Selected Models)",
        "",
        "This report summarizes the **selected** models (one per disease) after running Phase 3 and the best-model selector. It is generated by `select_best_model.py` when you have all candidates and run the selection.",
        "",
        "## Summary",
        "",
        f"- **Diseases:** {len(selected_summary)}",
        f"- **Models selected:** one per disease (best of Phase 2 extraction + Phase 3 fill by completeness, Phase 2 evaluation, Phase 3 accuracy).",
        "",
        "## Selected models (all)",
        "",
        "| Disease | Chosen candidate | Phase 2 extractor | Phase 3 gaps | Phase 2 score | Phase 3 accuracy |",
        "|---------|------------------|-------------------|--------------|---------------|-------------------|",
    ]
    for s in selected_summary:
        overall_lines.append(
            f"| {s['display']} | {s['chosen_dir']} | {s['phase2_provider'].capitalize()} | "
            f"{s['phase3_gaps']} | {s['phase2_score']} | {s['phase3_accuracy_pct']}% |"
        )
    overall_lines.extend([
        "",
        "## Output layout",
        "",
        f"- `{out_base}/<disease>/model_filled.compmodel` — selected filled model",
        f"- `{out_base}/<disease>/selection_report.json` — why this candidate was chosen",
        "- `SELECTION_REPORT.md` — per-disease selection details",
        "- `PHASE3_OVERALL_REPORT.md` — this file (overview over all selected models).",
        "",
    ])
    overall_path = out_base / "PHASE3_OVERALL_REPORT.md"
    overall_path.write_text("\n".join(overall_lines), encoding="utf-8")

    print(f"Selected one model per disease: {out_base}")
    print(f"Selection report: {report_path}")
    print(f"Overall report (all models): {overall_path}")


if __name__ == "__main__":
    main()
