#!/usr/bin/env python3
"""
Batch evaluation runner for all remaining diseases
"""

import json
import sys
import os
from pathlib import Path

sys.path.insert(
    0,
    "/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3",
)

from evaluate import evaluate


def convert_extracted(filepath):
    """Convert extracted format to gold format"""
    with open(filepath, "r") as f:
        raw = json.load(f)

    converted = {
        "compartments": [{"name": c} for c in raw.get("comp", [])],
        "flows": [],
        "parameters": [],
    }

    for f in raw.get("flow", []):
        parts = f.split("->")
        if len(parts) == 2:
            converted["flows"].append({"source": parts[0], "target": parts[1]})

    for p in raw.get("param", []):
        if isinstance(p, str):
            parts = p.split(" ", 1)
            converted["parameters"].append(
                {"symbol": parts[0], "description": parts[1] if len(parts) > 1 else ""}
            )

    return converted


BASE_DIR = Path(
    "/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3"
)
GOLD_DIR = BASE_DIR / "parsed_models/benchmark_gold_standard"
EMBED_DIR = BASE_DIR / "embeddings"
OUTPUT_DIR = BASE_DIR / "evalutation/results"
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

# Diseases to process
diseases = ["malaria", "tuberculosis", "ebola", "zika", "hiv", "influenza"]

all_summaries = {}

for disease in diseases:
    gold_path = GOLD_DIR / f"{disease}_faithful_gold.json"
    models_dir = EMBED_DIR / f"{disease}_embeddings"

    if not gold_path.exists():
        print(f"\n=== {disease.upper()}: No gold standard found, skipping ===")
        continue

    if not models_dir.exists():
        print(f"\n=== {disease.upper()}: No models found, skipping ===")
        continue

    variants = sorted(
        set(
            [
                f.stem.replace("_parsed_strings", "")
                for f in models_dir.glob("*_parsed_strings.json")
            ]
        )
    )

    print(f"\n{'=' * 60}")
    print(f"Running {disease.upper()} - {len(variants)} models")
    print("=" * 60)

    results_summary = []

    for i, model_name in enumerate(variants):
        print(f"[{i + 1}/{len(variants)}] {model_name}...", end=" ", flush=True)

        extracted_path = models_dir / f"{model_name}_parsed_strings.json"

        if not extracted_path.exists():
            print("SKIP")
            continue

        try:
            extracted = convert_extracted(extracted_path)
            provider = "gemini" if "gemini" in model_name else "openai"

            result = evaluate(
                gold_path,
                extracted,
                threshold=0.72,
                meta={"disease": disease, "case_id": model_name, "provider": provider},
            )

            # Save individual result
            output_path = OUTPUT_DIR / f"{disease}_{model_name}_report.json"
            with open(output_path, "w") as f:
                json.dump(result, f, indent=2)

            summary = {
                "model": model_name,
                "provider": provider,
                "composite": result["scores"]["composite"],
                "quality": result["llm_oversight"]["overall_quality"],
                "comp_f1": result["scores"]["compartments"]["f1"],
                "flow_f1": result["scores"]["flows"]["f1"],
                "param_f1": result["scores"]["parameters"]["f1"],
            }
            results_summary.append(summary)
            print(f"{summary['composite']:.4f}")

        except Exception as e:
            print(f"ERROR: {e}")

    # Save disease summary
    summary_path = OUTPUT_DIR / f"{disease}_all_summary.json"
    with open(summary_path, "w") as f:
        json.dump(results_summary, f, indent=2)

    all_summaries[disease] = results_summary

    # Print disease summary
    avg_comp = sum(r["comp_f1"] for r in results_summary) / len(results_summary)
    avg_flow = sum(r["flow_f1"] for r in results_summary) / len(results_summary)
    avg_param = sum(r["param_f1"] for r in results_summary) / len(results_summary)
    avg_total = sum(r["composite"] for r in results_summary) / len(results_summary)

    print(
        f"\n{disease.upper()} Summary: Comp={avg_comp:.3f}, Flow={avg_flow:.3f}, Param={avg_param:.3f}, Total={avg_total:.3f}"
    )

# Print final combined summary
print("\n" + "=" * 80)
print("ALL DISEASES SUMMARY")
print("=" * 80)

for disease, results in all_summaries.items():
    avg = sum(r["composite"] for r in results) / len(results)
    best = max(r["composite"] for r in results)
    worst = min(r["composite"] for r in results)
    print(
        f"{disease.upper():<15} Avg: {avg:.3f}  Best: {best:.3f}  Worst: {worst:.3f}  (n={len(results)})"
    )

# Save combined summary
with open(OUTPUT_DIR / "all_diseases_summary.json", "w") as f:
    json.dump(all_summaries, f, indent=2)

print(f"\nAll done! Results in {OUTPUT_DIR}")
