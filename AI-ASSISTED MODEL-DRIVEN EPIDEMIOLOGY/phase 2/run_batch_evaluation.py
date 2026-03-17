#!/usr/bin/env python3
"""Run Phase 2 evaluation on all diseases with both OpenAI and Gemini."""

import os
import subprocess
import sys
from pathlib import Path

DISEASES = [
    "measles",
    "dengue",
    "Ebola",
    "hiv",
    "influenza",
    "malaria",
    "tuberculosis",
    "zika",
]

PAPERS_DIR = Path("data/papers")
BASELINE_DIR = Path("data/baseline_models")


def run_evaluation(disease, provider):
    """Run evaluation for a single disease with specified LLM provider."""
    paper_path = PAPERS_DIR / f"{disease}.pdf"

    if not paper_path.exists():
        print(f"  ⚠ Paper not found: {paper_path}")
        return None

    # Set API key based on provider
    if provider == "openai":
        env = os.environ.copy()
        api_key = env.get("OPENAI_API_KEY")
        if not api_key:
            print(f"  ⚠ OPENAI_API_KEY not set, skipping {disease}")
            return None
    else:  # gemini
        env = os.environ.copy()
        api_key = env.get("GEMINI_API_KEY")
        if not api_key:
            print(f"  ⚠ GEMINI_API_KEY not set, skipping {disease}")
            return None

    cmd = [
        "python",
        "run_phase2.py",
        "--paper",
        str(paper_path),
        "--llm-provider",
        provider,
        "--baseline-models-dir",
        str(BASELINE_DIR),
        "--eval-threshold",
        "0.70",
    ]

    print(f"  Running {provider} on {disease}...")
    result = subprocess.run(cmd, env=env, capture_output=True, text=True)

    if result.returncode != 0:
        print(f"  ❌ Error: {result.stderr[:200]}")
        return None

    # Extract results from output
    output = result.stdout + result.stderr
    lines = output.split("\n")
    results = {}

    # Look for the actual format: "      * Compartments: Precision=..., Recall=..., F1=..."
    for line in lines:
        if "Compartments:" in line and "Precision=" in line:
            parts = line.split("Compartments:")[1].strip()
            for p in parts.split(","):
                if "F1=" in p:
                    results["compartments_f1"] = float(p.split("F1=")[1].strip())
        if "Parameters:" in line and "Precision=" in line:
            parts = line.split("Parameters:")[1].strip()
            for p in parts.split(","):
                if "F1=" in p:
                    results["parameters_f1"] = float(p.split("F1=")[1].strip())
        if "Flows:" in line and "Precision=" in line:
            parts = line.split("Flows:")[1].strip()
            for p in parts.split(","):
                if "F1=" in p:
                    results["flows_f1"] = float(p.split("F1=")[1].strip())

    print(
        f"  ✓ Done: C={results.get('compartments_f1', '?')}, P={results.get('parameters_f1', '?')}, F={results.get('flows_f1', '?')}"
    )
    return results


def main():
    # Check API keys
    if not os.environ.get("OPENAI_API_KEY"):
        print("Error: OPENAI_API_KEY not set")
        sys.exit(1)
    if not os.environ.get("GEMINI_API_KEY"):
        print("Error: GEMINI_API_KEY not set")
        sys.exit(1)

    print("=" * 60)
    print("Running Phase 2 on all diseases with OpenAI and Gemini")
    print("=" * 60)

    results = {}

    for disease in DISEASES:
        print(f"\n[{disease.upper()}]")
        results[disease] = {}

        # Run OpenAI
        result = run_evaluation(disease, "openai")
        if result:
            results[disease]["openai"] = result

        # Run Gemini
        result = run_evaluation(disease, "gemini")
        if result:
            results[disease]["gemini"] = result

    # Print summary
    print("\n" + "=" * 60)
    print("SUMMARY")
    print("=" * 60)
    print(f"{'Disease':<15} {'OpenAI C/P/F':<20} {'Gemini C/P/F':<20}")
    print("-" * 60)

    for disease in DISEASES:
        o = results.get(disease, {}).get("openai", {})
        g = results.get(disease, {}).get("gemini", {})

        o_str = (
            f"{o.get('compartments_f1', 0):.2f}/{o.get('parameters_f1', 0):.2f}/{o.get('flows_f1', 0):.2f}"
            if o
            else "N/A"
        )
        g_str = (
            f"{g.get('compartments_f1', 0):.2f}/{g.get('parameters_f1', 0):.2f}/{g.get('flows_f1', 0):.2f}"
            if g
            else "N/A"
        )

        print(f"{disease:<15} {o_str:<20} {g_str:<20}")

    # Save results to file
    import json

    with open("reports/batch_results.json", "w") as f:
        json.dump(results, f, indent=2)
    print(f"\nResults saved to reports/batch_results.json")


if __name__ == "__main__":
    main()
