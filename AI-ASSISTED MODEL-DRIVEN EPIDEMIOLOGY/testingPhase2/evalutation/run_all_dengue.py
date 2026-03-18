#!/usr/bin/env python3
"""
Batch evaluation runner for dengue models
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


# Gold standard path
gold_path = "/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3/parsed_models/benchmark_gold_standard/dengue_faithful_gold.json"

# Model directory
models_dir = Path(
    "/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3/embeddings/dengue_embeddings"
)
output_dir = Path(
    "/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3/evalutation/results"
)
output_dir.mkdir(parents=True, exist_ok=True)

# Find all model variants
variants = sorted(
    set(
        [
            f.stem.replace("_parsed_strings", "")
            for f in models_dir.glob("*_parsed_strings.json")
        ]
    )
)

print(f"Found {len(variants)} dengue model variants")
print("-" * 50)

results_summary = []

for i, model_name in enumerate(variants):
    print(f"[{i + 1}/{len(variants)}] Running {model_name}...", end=" ")

    extracted_path = models_dir / f"{model_name}_parsed_strings.json"

    if not extracted_path.exists():
        print("SKIP (no file)")
        continue

    try:
        extracted = convert_extracted(extracted_path)

        provider = "gemini" if "gemini" in model_name else "openai"

        result = evaluate(
            gold_path,
            extracted,
            threshold=0.72,
            meta={"disease": "dengue", "case_id": model_name, "provider": provider},
        )

        # Save individual result
        output_path = output_dir / f"dengue_{model_name}_report.json"
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
            "tp_comp": result["counts"]["compartments"]["tp"],
            "fp_comp": result["counts"]["compartments"]["fp"],
            "fn_comp": result["counts"]["compartments"]["fn"],
            "tp_flow": result["counts"]["flows"]["tp"],
            "fp_flow": result["counts"]["flows"]["fp"],
            "fn_flow": result["counts"]["flows"]["fn"],
            "tp_param": result["counts"]["parameters"]["tp"],
            "fp_param": result["counts"]["parameters"]["fp"],
            "fn_param": result["counts"]["parameters"]["fn"],
        }
        results_summary.append(summary)

        print(f"Done! Composite: {summary['composite']:.4f}")

    except Exception as e:
        print(f"ERROR: {e}")

# Save summary
summary_path = output_dir / "dengue_all_summary.json"
with open(summary_path, "w") as f:
    json.dump(results_summary, f, indent=2)

print("-" * 50)
print(f"All done! Summary saved to {summary_path}")

# Print summary table
print("\n" + "=" * 95)
print("DENGUE MODEL EVALUATION SUMMARY")
print("=" * 95)
print(
    f"{'Model':<50} {'Prov':<8} {'Comp':<6} {'Flow':<6} {'Param':<6} {'Composite':<10} {'Quality'}"
)
print("-" * 95)

for r in sorted(results_summary, key=lambda x: -x["composite"]):
    print(
        f"{r['model']:<50} {r['provider']:<8} {r['comp_f1']:<6.3f} {r['flow_f1']:<6.3f} {r['param_f1']:<6.3f} {r['composite']:<10.4f} {r['quality']}"
    )

avg_comp = sum(r["comp_f1"] for r in results_summary) / len(results_summary)
avg_flow = sum(r["flow_f1"] for r in results_summary) / len(results_summary)
avg_param = sum(r["param_f1"] for r in results_summary) / len(results_summary)
avg_total = sum(r["composite"] for r in results_summary) / len(results_summary)

print("-" * 95)
print(
    f"{'AVERAGE':<50} {'':<8} {avg_comp:<6.3f} {avg_flow:<6.3f} {avg_param:<6.3f} {avg_total:<10.4f}"
)
