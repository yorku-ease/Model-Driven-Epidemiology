#!/usr/bin/env python3
"""
Build comprehensive paper database from Phase 1 AND Phase 2.

Sources collected:
  Phase 1:
    - papers/epimde/*.compmodel + co-located PDFs (primary; see phase 1/utils/phase1_paths.py)
    - papers/**/*.compmodel / **/*.pdf (additional)
    - data/papers/*/metadata.json (paper collection registry)
    - reports/model_analysis/*_analysis.json
    - reports/uncertainty/*_uncertainty.json
    - reports/sensitivity/*_sensitivity_*.json
    - reports/gap_reports/*_gap_analysis.json
    - reports/patterns/pattern_library.json
    - reports/taxonomies/taxonomies.json
    - reports/protocols/required_optional.json, extraction_protocol.json
    - reports/manual_extraction/extraction_example.json
    - reports/paper_collection/paper_collection.json

  Phase 2:
    - data/papers/*.pdf   (not raw bytes — we read full_text from reports)
    - data/baseline_models/*.compmodel
    - reports/<disease>_llm_<provider>_<ts>/
        paper_text.json  (full_text only, NO word bounding boxes)
        extracted_entities.json
        paper_promises.json
        model_draft.compmodel
        evaluation_report.json
        <disease>_analysis.json
        <disease>_uncertainty.json
        <disease>_sensitivity_morris.json
        phase2_gap_report.json
        gap_fill_suggestions.json

Output: data/paper_database/index.json
"""

import argparse
import json
import re
import sys
import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Any, Dict, List, Optional, Set, Tuple

PHASE3_DIR = Path(__file__).resolve().parent
sys.path.insert(0, str(PHASE3_DIR))
PHASE1_DIR = PHASE3_DIR.parent / "phase 1"
PHASE2_DIR = PHASE3_DIR.parent / "phase 2"
DB_DIR = PHASE3_DIR / "data" / "paper_database"

from src.gap_analysis.gap_detector import load_model_structure  # noqa: E402

DISEASES_CANONICAL = {
    "covid": "covid", "covid_19": "covid", "covid-19": "covid",
    "malaria": "malaria", "hiv": "hiv",
    "cholera": "cholera", "dengue": "dengue", "ebola": "ebola",
    "flu": "influenza", "influenza": "influenza",
    "measles": "measles", "tuberculosis": "tuberculosis", "zika": "zika",
    "monitoring_and_predicting_influenza": "influenza",
    "ebolasen": "ebola", "ebolasensitivity": "ebola",
}

# Papers whose filenames are journal IDs rather than disease names
FILENAME_OVERRIDES = {
    "1_s2.0_s0025556413001880_main": "cholera",
    "1_s2.0_s240584402411849x_main": "ebola",
    "main": "ebola",
    "2500267": "zika",
}


def _canonical(name: str) -> str:
    n = re.sub(r"[\s_-]+", "_", name.lower().strip())
    if n in FILENAME_OVERRIDES:
        return FILENAME_OVERRIDES[n]
    for k, v in DISEASES_CANONICAL.items():
        if k in n:
            return v
    return n


def _chunk_text(text: str, size: int = 2000, overlap: int = 300) -> List[str]:
    if not text or len(text) <= size:
        return [text] if text else []
    chunks, start = [], 0
    while start < len(text):
        chunks.append(text[start:start + size])
        start += size - overlap
    return chunks


def _load_json(path: Path) -> Optional[Any]:
    try:
        with open(path, "r", encoding="utf-8", errors="replace") as f:
            return json.load(f)
    except Exception:
        return None


def _parse_compmodel(path: Path) -> Dict[str, Any]:
    """Extract compartments, parameters, flows from .compmodel XML (namespace-agnostic)."""
    try:
        tree = ET.parse(path)
        root = tree.getroot()
    except Exception as e:
        return {"error": str(e)}

    def ltag(el):
        return el.tag.split("}")[-1] if "}" in el.tag else el.tag

    compartments, parameters, flows = [], [], []
    for el in root.iter():
        tag = ltag(el)
        if tag == "compartments":
            name = el.get("PrimaryName", "")
            if name:
                compartments.append({
                    "name": name,
                    "population": el.get("population", ""),
                    "secondary": el.get("SecondaryName", ""),
                })
        elif tag == "parameters":
            p = {
                "name": el.get("name", ""),
                "expression": el.get("expression", ""),
                "type": el.get("type", ""),
                "unit": el.get("unit", ""),
                "description": el.get("description", ""),
            }
            if p["name"]:
                parameters.append(p)
        elif "Flow" in tag or "flow" in tag:
            flows.append({
                "type": tag,
                "target": el.get("target", ""),
                "rate": el.get("rate", ""),
                "rateParameter": el.get("rateParameter", ""),
                "description": el.get("description", ""),
            })
    return {
        "compartments": compartments,
        "parameters": parameters,
        "flows": flows,
        "source_path": str(path),
    }


# ─── Phase 1 ───────────────────────────────────────────────────────────────

def _phase1_entry_from_compmodel(cm: Path) -> Dict[str, Any]:
    """Single Phase 1 entry for a .compmodel under ``papers/`` (epimde or legacy)."""
    disease = _canonical(cm.stem)
    pdf = cm.with_suffix(".pdf")
    entry: Dict[str, Any] = {
        "paper_id": f"p1_model_{disease}",
        "source_phase": "phase1",
        "disease": disease,
        "compmodel_path": str(cm),
        "model_structure": _parse_compmodel(cm),
    }
    if pdf.exists():
        entry["pdf_path"] = str(pdf)

    for pattern in [f"{cm.stem}_analysis.json", f"{disease}_analysis.json"]:
        a = PHASE1_DIR / "reports" / "model_analysis" / pattern
        if a.exists():
            entry["analysis"] = _load_json(a)
            break

    for pattern in [f"{cm.stem}_uncertainty.json", f"{disease}_uncertainty.json"]:
        u = PHASE1_DIR / "reports" / "uncertainty" / pattern
        if u.exists():
            entry["uncertainty"] = _load_json(u)
            break

    for s in sorted(PHASE1_DIR.glob(f"reports/sensitivity/*{disease}*")):
        entry.setdefault("sensitivity", []).append(_load_json(s))

    for pattern in [f"{cm.stem}_gap_analysis.json", f"{disease}_gap_analysis.json"]:
        g = PHASE1_DIR / "reports" / "gap_reports" / pattern
        if g.exists():
            entry["gap_analysis"] = _load_json(g)
            break

    return entry


def _collect_phase1_paper_metadata_entries() -> List[Dict[str, Any]]:
    """``phase 1/data/papers/<id>/metadata.json`` — curated paper collection (Phase 1 layout)."""
    out: List[Dict[str, Any]] = []
    base = PHASE1_DIR / "data" / "papers"
    if not base.is_dir():
        return out
    for meta_path in sorted(base.glob("*/metadata.json")):
        data = _load_json(meta_path)
        if not data:
            continue
        disease = _canonical(str(data.get("disease") or meta_path.parent.name))
        pdf_path = None
        for pdf in meta_path.parent.glob("*.pdf"):
            pdf_path = str(pdf)
            break
        if not pdf_path and data.get("pdfPath"):
            pp = Path(str(data["pdfPath"]))
            if pp.is_file():
                pdf_path = str(pp)
        out.append({
            "paper_id": f"p1_meta_{meta_path.parent.name}",
            "source_phase": "phase1_paper_metadata",
            "disease": disease,
            "metadata": data,
            "metadata_path": str(meta_path),
            "pdf_path": pdf_path,
        })
    return out


def _collect_phase1_entries() -> List[Dict[str, Any]]:
    """
    Phase 1 models: prefer ``papers/epimde/*.compmodel`` (canonical per ``phase 1/utils/phase1_paths.py``),
    then any other ``papers/**/*.compmodel`` not already indexed. Unlinked PDFs under ``papers/`` are
    indexed as text-only entries. Paper metadata folders under ``data/papers/`` are included.
    """
    entries: List[Dict[str, Any]] = []
    if not PHASE1_DIR.exists():
        print("  [WARN] Phase 1 dir not found")
        return entries

    linked_pdfs: Set[str] = set()
    seen_cm: Set[str] = set()

    # 1a) Primary: epimde reference models
    epimde = PHASE1_DIR / "papers" / "epimde"
    if epimde.is_dir():
        for cm in sorted(epimde.glob("*.compmodel")):
            seen_cm.add(str(cm.resolve()))
            e = _phase1_entry_from_compmodel(cm)
            if e.get("pdf_path"):
                linked_pdfs.add(str(Path(e["pdf_path"]).resolve()))
            entries.append(e)

    # 1b) Other compmodels under papers/ (legacy / extra)
    for cm in sorted(PHASE1_DIR.glob("papers/**/*.compmodel")):
        if str(cm.resolve()) in seen_cm:
            continue
        seen_cm.add(str(cm.resolve()))
        e = _phase1_entry_from_compmodel(cm)
        if e.get("pdf_path"):
            linked_pdfs.add(str(Path(e["pdf_path"]).resolve()))
        entries.append(e)

    # 2) PDFs not yet linked
    for pdf in sorted(PHASE1_DIR.glob("papers/**/*.pdf")):
        if str(pdf.resolve()) in linked_pdfs:
            continue
        disease = _canonical(pdf.stem)
        entries.append({
            "paper_id": f"p1_paper_{disease}_{pdf.stem[:20]}",
            "source_phase": "phase1",
            "disease": disease,
            "pdf_path": str(pdf),
        })

    entries.extend(_collect_phase1_paper_metadata_entries())
    return entries


def _build_flow_index(entries: List[Dict[str, Any]]) -> List[Dict[str, Any]]:
    """Flat index of ``Source->Target`` flow signatures for RAG (structure_lookup)."""
    rows: List[Dict[str, Any]] = []
    seen: Set[str] = set()
    for e in entries:
        p = e.get("compmodel_path")
        if not p:
            continue
        path = Path(p)
        if not path.exists():
            continue
        try:
            ms = load_model_structure(path)
        except Exception:
            continue
        for sig in ms.get("flows") or []:
            if not sig or sig in seen:
                continue
            seen.add(sig)
            rows.append({
                "signature": sig,
                "disease": e.get("disease", ""),
                "paper_id": e.get("paper_id", ""),
                "source_phase": e.get("source_phase", ""),
            })
    return rows


def _collect_phase1_knowledge() -> Dict[str, Any]:
    """Load Phase 1 knowledge assets: patterns, taxonomies, protocols, etc."""
    kb: Dict[str, Any] = {}
    for name, rel in [
        ("pattern_library", "reports/patterns/pattern_library.json"),
        ("taxonomies", "reports/taxonomies/taxonomies.json"),
        ("required_optional", "reports/protocols/required_optional.json"),
        ("extraction_protocol", "reports/protocols/extraction_protocol.json"),
        ("manual_extraction_example", "reports/manual_extraction/extraction_example.json"),
        ("paper_collection", "reports/paper_collection/paper_collection.json"),
        ("all_models_analysis", "reports/model_analysis/all_models_analysis.json"),
        ("all_models_uncertainty", "reports/uncertainty/all_models_uncertainty.json"),
        ("all_models_gap_summary", "reports/gap_reports/all_models_gap_summary.json"),
    ]:
        p = PHASE1_DIR / rel
        if p.exists():
            kb[name] = _load_json(p)
    return kb


# ─── Phase 2 ───────────────────────────────────────────────────────────────

def _latest_reports(reports_dir: Path) -> Dict[str, Path]:
    """Return dict of (disease_provider) -> latest report dir."""
    grouped: Dict[str, List[Path]] = {}
    if not reports_dir.is_dir():
        return {}
    for d in sorted(reports_dir.iterdir()):
        if not d.is_dir() or "_llm_" not in d.name:
            continue
        parts = d.name.split("_llm_")
        if len(parts) != 2:
            continue
        disease = _canonical(parts[0])
        rest = parts[1]                     # e.g. "gemini_20260211_201419"
        provider = rest.split("_")[0]
        key = f"{disease}_{provider}"
        grouped.setdefault(key, []).append(d)
    return {k: max(dirs, key=lambda p: p.name) for k, dirs in grouped.items()}


def _read_full_text(paper_text_path: Path) -> str:
    """Read full_text from paper_text.json WITHOUT loading giant word arrays."""
    try:
        with open(paper_text_path, "r", encoding="utf-8", errors="replace") as f:
            data = json.load(f)
        ft = data.get("full_text", "") or ""
        if isinstance(ft, list):
            ft = " ".join(str(x) for x in ft)
        return ft
    except Exception:
        return ""


def _collect_phase2_entries(exclude_baselines: bool = False) -> List[Dict[str, Any]]:
    entries: List[Dict[str, Any]] = []
    reports_dir = PHASE2_DIR / "reports"
    latest = _latest_reports(reports_dir)
    print(f"  Phase 2 latest report dirs: {len(latest)}")

    for key, rdir in sorted(latest.items()):
        disease = key.rsplit("_", 1)[0]
        provider = key.rsplit("_", 1)[1]

        entry: Dict[str, Any] = {
            "paper_id": f"p2_{rdir.name}",
            "source_phase": "phase2",
            "disease": disease,
            "provider": provider,
            "report_dir": str(rdir),
        }

        # Paper text -> chunks (skip word bounding boxes)
        pt_path = rdir / "paper_text.json"
        if pt_path.exists():
            full_text = _read_full_text(pt_path)
            if full_text:
                entry["text_length"] = len(full_text)
                entry["chunks"] = _chunk_text(full_text)
                entry["num_chunks"] = len(entry["chunks"])

        # Extracted entities
        ep = rdir / "extracted_entities.json"
        if ep.exists():
            ent = _load_json(ep)
            if ent:
                entry["extracted_entities"] = {
                    "compartments": ent.get("compartments", []),
                    "parameters": ent.get("parameters", []),
                    "flows": ent.get("flows", []),
                    "stratifications": ent.get("stratifications", []),
                    "interventions": ent.get("interventions", []),
                }

        # Paper promises
        pp = rdir / "paper_promises.json"
        if pp.exists():
            entry["promises"] = _load_json(pp)

        # Model structure (.compmodel)
        cm = rdir / "model_draft.compmodel"
        if cm.exists():
            entry["model_structure"] = _parse_compmodel(cm)

        # Evaluation
        ev = rdir / "evaluation_report.json"
        if ev.exists():
            evd = _load_json(ev)
            if evd:
                entry["evaluation"] = {
                    "gold_standard": evd.get("gold_standard_comparison"),
                    "traceability": evd.get("traceability_coverage"),
                    "faithfulness": evd.get("faithfulness"),
                    "gap_analysis": evd.get("gap_analysis"),
                }

        # In-report analysis (disease_analysis.json, uncertainty, sensitivity)
        for pattern in sorted(rdir.glob("*_analysis.json")):
            entry["analysis"] = _load_json(pattern)
            break
        for pattern in sorted(rdir.glob("*_uncertainty.json")):
            entry["uncertainty"] = _load_json(pattern)
            break
        for pattern in sorted(rdir.glob("*_sensitivity*.json")):
            entry.setdefault("sensitivity", []).append(_load_json(pattern))

        # Phase 2 gap reports
        for name in ["phase2_gap_report.json", "gap_fill_suggestions.json"]:
            gp = rdir / name
            if gp.exists():
                entry[name.replace(".json", "")] = _load_json(gp)

        entries.append(entry)

    # Baseline .compmodel files (gold standards — exclude during benchmark eval to avoid data leakage)
    baselines = PHASE2_DIR / "data" / "baseline_models"
    if baselines.is_dir():
        if exclude_baselines:
            print("  [INFO] --exclude-baselines: skipping gold-standard baseline_models/ "
                  "(safe for benchmark evaluation runs)")
        else:
            for cm in sorted(baselines.glob("*.compmodel")):
                disease = _canonical(cm.stem)
                entries.append({
                    "paper_id": f"p2_baseline_{disease}",
                    "source_phase": "phase2_baseline",
                    "disease": disease,
                    "compmodel_path": str(cm),
                    "model_structure": _parse_compmodel(cm),
                })

    return entries


# ─── Parameter index ───────────────────────────────────────────────────────

def _build_parameter_index(entries: List[Dict[str, Any]]) -> List[Dict[str, Any]]:
    """
    Flat parameter index for fast RAG lookup.
    Each record: { name, value, unit, description, disease, source_phase, paper_id }
    """
    params: List[Dict[str, Any]] = []
    seen: Set[Tuple[str, str, str]] = set()

    for entry in entries:
        disease = entry.get("disease", "")
        pid = entry.get("paper_id", "")
        src = entry.get("source_phase", "")

        # From model_structure.parameters
        for p in (entry.get("model_structure") or {}).get("parameters", []):
            name = p.get("name", "")
            key = (disease, name, pid)
            if not name or key in seen:
                continue
            seen.add(key)
            params.append({
                "name": name,
                "value": p.get("expression", ""),
                "unit": p.get("unit", ""),
                "description": p.get("description", ""),
                "disease": disease,
                "source_phase": src,
                "paper_id": pid,
            })

        # From extracted_entities.parameters (Phase 2)
        for p in (entry.get("extracted_entities") or {}).get("parameters", []):
            name = p.get("normalized_name") or p.get("name") or ""
            key = (disease, name, pid)
            if not name or key in seen:
                continue
            seen.add(key)
            params.append({
                "name": name,
                "value": p.get("value", ""),
                "unit": p.get("unit", ""),
                "description": p.get("description", ""),
                "disease": disease,
                "source_phase": src,
                "paper_id": pid,
                "text_span": p.get("text_span", ""),
                "confidence": p.get("confidence", ""),
            })

        # From analysis.data.parameters (richer format with compartment details)
        analysis_data = (entry.get("analysis") or {}).get("data", {})
        for comp in analysis_data.get("compartments", []):
            for flow in comp.get("outgoingFlows", []):
                desc = flow.get("description", "")
                if desc and desc not in seen:
                    seen.add((disease, desc, pid))

        # From uncertainty.parameters
        for p in (entry.get("uncertainty") or {}).get("parameters", []):
            name = p.get("parameter", "")
            key = (disease, name, f"{pid}_unc")
            if not name or key in seen:
                continue
            seen.add(key)
            lit = p.get("rangeInLiterature") or {}
            params.append({
                "name": name,
                "value": p.get("yourValue", ""),
                "unit": p.get("unit", ""),
                "description": p.get("description", ""),
                "disease": disease,
                "source_phase": f"{src}_uncertainty",
                "paper_id": pid,
                "range_min": lit.get("min"),
                "range_max": lit.get("max"),
                "confidence": p.get("confidence", ""),
            })

    return params


# ─── Main ──────────────────────────────────────────────────────────────────

def main():
    parser = argparse.ArgumentParser(
        description="Build the Phase 3 retrieval database from Phase 1 + Phase 2 outputs."
    )
    parser.add_argument(
        "--exclude-baselines",
        action="store_true",
        default=False,
        help=(
            "Exclude gold-standard baseline_models/ from the retrieval index. "
            "Use this flag for all benchmark / evaluation runs to prevent data leakage "
            "where the retrieval index would otherwise contain the same models being evaluated."
        ),
    )
    args = parser.parse_args()

    print("=" * 65)
    print("Building comprehensive paper database  (Phase 1 + Phase 2)")
    print("=" * 65)
    print(f"  Phase 1 : {PHASE1_DIR}")
    print(f"  Phase 2 : {PHASE2_DIR}")
    print(f"  Output  : {DB_DIR}")
    if args.exclude_baselines:
        print("  Mode    : BENCHMARK (gold-standard baselines excluded)\n")
    else:
        print("  Mode    : FULL (gold-standard baselines included — NOT for benchmarking)\n")

    # Collect entries
    p1_entries = _collect_phase1_entries()
    print(f"  Phase 1 entries : {len(p1_entries)}")

    p2_entries = _collect_phase2_entries(exclude_baselines=args.exclude_baselines)
    print(f"  Phase 2 entries : {len(p2_entries)}")

    all_entries = p1_entries + p2_entries

    # Knowledge base from Phase 1
    knowledge_base = _collect_phase1_knowledge()
    print(f"  Knowledge assets: {', '.join(knowledge_base.keys())}")

    # Parameter index
    param_index = _build_parameter_index(all_entries)
    print(f"  Parameter index : {len(param_index)} records")

    flow_index = _build_flow_index(all_entries)
    print(f"  Flow index      : {len(flow_index)} signatures")

    # Summary
    diseases = sorted({e.get("disease", "") for e in all_entries if e.get("disease")})
    total_chunks = sum(e.get("num_chunks", 0) for e in all_entries)
    print(f"  Diseases        : {', '.join(diseases)}")
    print(f"  Total text chunks: {total_chunks}")

    index = {
        "version": 3,
        "baselines_excluded": args.exclude_baselines,
        "num_entries": len(all_entries),
        "num_parameters": len(param_index),
        "num_flow_signatures": len(flow_index),
        "total_chunks": total_chunks,
        "diseases": diseases,
        "knowledge_base": knowledge_base,
        "entries": all_entries,
        "parameter_index": param_index,
        "flow_index": flow_index,
    }

    DB_DIR.mkdir(parents=True, exist_ok=True)
    index_path = DB_DIR / "index.json"
    with open(index_path, "w", encoding="utf-8") as f:
        json.dump(index, f, indent=2, ensure_ascii=False)

    size_mb = index_path.stat().st_size / (1024 * 1024)
    print(f"\n  Written to {index_path}")
    print(f"  Size: {size_mb:.2f} MB")
    print("=" * 65)
    print("Done.\n")

    return index


if __name__ == "__main__":
    main()
