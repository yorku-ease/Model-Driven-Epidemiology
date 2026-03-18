#!/usr/bin/env python3
"""
Rerun HIV evaluations with updated gold standard
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


# HIV paths
gold_path = "/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3/parsed_models/benchmark_gold_standard/hiv_faithful_gold.json"
models_dir = Path(
    "/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3/embeddings/hiv_embeddings"
)
output_dir = Path(
    "/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3/evalutation/results"
)

# Check gold
with open(gold_path) as f:
    gold = json.load(f)
print(f"Gold: {len(gold['compartments'])} comps, {len(gold['flows'])} flows")

variants = sorted(
    set(
        [
            f.stem.replace("_parsed_strings", "")
            for f in models_dir.glob("*_parsed_strings.json")
        ]
    )
)
print(f"Found {len(variants)} variants\n")

results = []
for i, model_name in enumerate(variants):
    print(f"[{i + 1}/{len(variants)}] {model_name}...", end=" ")

    extracted = convert_extracted(models_dir / f"{model_name}_parsed_strings.json")
    print(
        f"(ext: {len(extracted['compartments'])}c/{len(extracted['flows'])}f)",
        end=" ",
        flush=True,
    )

    provider = "gemini" if "gemini" in model_name else "openai"

    result = evaluate(
        gold_path,
        extracted,
        threshold=0.72,
        meta={"disease": "hiv", "case_id": model_name, "provider": provider},
    )

    with open(output_dir / f"hiv_{model_name}_report.json", "w") as f:
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
    results.append(summary)
    print(f"-> {summary['composite']:.4f}")

# Save summary
with open(output_dir / "hiv_all_summary.json", "w") as f:
    json.dump(results, f, indent=2)

# Print results
print("\n" + "=" * 70)
print("HIV EVALUATION RESULTS (UPDATED GOLD)")
print("=" * 70)
for r in sorted(results, key=lambda x: -x["composite"]):
    print(f"{r['model']:<50} {r['provider']:<8} {r['composite']:.4f} {r['quality']}")

avg = sum(r["composite"] for r in results) / len(results)
print("-" * 70)
print(f"AVERAGE: {avg:.4f}")
