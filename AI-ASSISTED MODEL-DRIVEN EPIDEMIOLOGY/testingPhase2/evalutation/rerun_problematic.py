#!/usr/bin/env python3
"""
Rerun problematic diseases: HIV, Zika, Ebola, Influenza
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

# Diseases to rerun (correct names!)
diseases = [
    ("hiv", "hiv"),  # folder: hiv_embeddings, gold: hiv_faithful_gold.json
    ("zika", "zika"),  # folder: zika_embeddings, gold: zika_faithful_gold.json
    ("ebola", "ebola"),  # folder: ebola_embeddings, gold: ebola_faithful_gold.json
    (
        "influenza",
        "influenza",
    ),  # folder: influenza_embeddings, gold: influenza_faithful_gold.json
    ("tuberculosis", "tuberculosis"),  # folder: tuberculosis_embeddings
]

all_summaries = {}

for folder_name, gold_name in diseases:
    gold_path = GOLD_DIR / f"{gold_name}_faithful_gold.json"
    models_dir = EMBED_DIR / f"{folder_name}_embeddings"

    print(f"\n{'=' * 60}")
    print(f"Processing: {folder_name.upper()}")
    print(f"Gold path: {gold_path}")
    print(f"Models dir: {models_dir}")
    print("=" * 60)

    if not gold_path.exists():
        print(f"ERROR: Gold file not found at {gold_path}")
        continue

    if not models_dir.exists():
        print(f"ERROR: Models dir not found at {models_dir}")
        continue

    # Check gold content
    with open(gold_path) as f:
        gold_data = json.load(f)
    print(
        f"Gold: {len(gold_data.get('compartments', []))} comps, {len(gold_data.get('flows', []))} flows, {len(gold_data.get('parameters', []))} params"
    )

    variants = sorted(
        set(
            [
                f.stem.replace("_parsed_strings", "")
                for f in models_dir.glob("*_parsed_strings.json")
            ]
        )
    )
    print(f"Found {len(variants)} model variants")

    results_summary = []

    for i, model_name in enumerate(variants):
        print(f"[{i + 1}/{len(variants)}] {model_name}...", end=" ", flush=True)

        extracted_path = models_dir / f"{model_name}_parsed_strings.json"

        if not extracted_path.exists():
            print("SKIP")
            continue

        try:
            extracted = convert_extracted(extracted_path)
            print(
                f"(ext: {len(extracted['compartments'])}c/{len(extracted['flows'])}f/{len(extracted['parameters'])}p)",
                end=" ",
                flush=True,
            )

            provider = "gemini" if "gemini" in model_name else "openai"

            result = evaluate(
                gold_path,
                extracted,
                threshold=0.72,
                meta={
                    "disease": folder_name,
                    "case_id": model_name,
                    "provider": provider,
                },
            )

            # Save individual result
            output_path = OUTPUT_DIR / f"{folder_name}_{model_name}_report.json"
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
            print(f"-> {summary['composite']:.4f}")

        except Exception as e:
            print(f"ERROR: {e}")
            import traceback

            traceback.print_exc()

    # Save disease summary
    summary_path = OUTPUT_DIR / f"{folder_name}_all_summary.json"
    with open(summary_path, "w") as f:
        json.dump(results_summary, f, indent=2)

    all_summaries[folder_name] = results_summary

    if results_summary:
        avg = sum(r["composite"] for r in results_summary) / len(results_summary)
        print(f"\n{folder_name.upper()} Average: {avg:.3f}")

# Print final summary
print("\n" + "=" * 80)
print("RERUN SUMMARY")
print("=" * 80)
for disease, results in all_summaries.items():
    if results:
        avg = sum(r["composite"] for r in results) / len(results)
        best = max(r["composite"] for r in results)
        print(f"{disease}: avg={avg:.3f}, best={best:.3f}")
