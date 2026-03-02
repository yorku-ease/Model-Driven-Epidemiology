#!/usr/bin/env python3
"""
Quick evaluation runner for measles models
"""

import json
import sys
import os

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
gold_path = "/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3/parsed_models/benchmark_gold_standard/measles_faithful_gold.json"

# Model to evaluate
model_name = "measles_gemini_baseline"
extracted_path = f"/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3/embeddings/measles_embeddings/{model_name}_parsed_strings.json"

# Convert extracted
extracted = convert_extracted(extracted_path)

# Run evaluation
result = evaluate(
    gold_path,
    extracted,
    threshold=0.72,
    meta={"disease": "measles", "case_id": model_name, "provider": "gemini"},
)

# Save result
output_path = f"/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3/evalutation/results/measles_{model_name}_report.json"
os.makedirs(os.path.dirname(output_path), exist_ok=True)

with open(output_path, "w") as f:
    json.dump(result, f, indent=2)

print(f"Results saved to: {output_path}")
print(f"\nComposite Score: {result['scores']['composite']}")
print(f"Quality: {result['llm_oversight']['overall_quality']}")
