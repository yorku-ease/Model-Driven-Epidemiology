"""Summarize recall evaluation results from pruned pipeline runs.

Scans output directories for recall_evaluation.json files and creates
a combined summary table.
"""

import json
from pathlib import Path
from datetime import datetime


def find_recall_results(output_dir: Path):
    """Find all recall_evaluation.json files in output directories."""
    results = []

    if not output_dir.exists():
        print(f"Directory not found: {output_dir}")
        return results

    for entry in sorted(output_dir.iterdir()):
        if not entry.is_dir():
            continue

        name = entry.name.lower()

        # Parse folder name to extract disease and LLM
        # Format: {disease}_pruned_{llm}_{timestamp}
        if "_pruned_" not in name:
            continue

        parts = name.split("_pruned_")
        if len(parts) != 2:
            continue

        disease = parts[0]
        rest = parts[1]
        rest_parts = rest.rsplit("_", 2)
        llm = rest_parts[0] if rest_parts else "unknown"
        timestamp = "_".join(rest_parts[-2:]) if len(rest_parts) >= 2 else ""

        recall_file = entry / "recall_evaluation.json"
        if not recall_file.exists():
            continue

        try:
            with open(recall_file, "r") as f:
                recall_data = json.load(f)

            results.append(
                {
                    "disease": disease,
                    "llm": llm,
                    "folder": entry.name,
                    "timestamp": timestamp,
                    "recall_data": recall_data,
                }
            )
        except Exception as e:
            print(f"Error reading {recall_file}: {e}")

    return results


def print_summary_table(results):
    """Print formatted summary table."""
    print()
    print("=" * 80)
    print("PRUNED PIPELINE RECALL SUMMARY")
    print("=" * 80)
    print()

    # Header
    print(
        f"{'Disease':<15} {'LLM':<10} {'Compartments':<15} {'Flows':<15} {'Parameters':<15}"
    )
    print(f"{'':15} {'':10} {'Recall':>10} {'Recall':>10} {'Recall':>10}")
    print("-" * 80)

    # Group by disease
    diseases = sorted(set(r["disease"] for r in results))

    for disease in diseases:
        disease_results = [r for r in results if r["disease"] == disease]

        for r in disease_results:
            rd = r["recall_data"]
            comp_recall = rd.get("compartments", {}).get("recall", 0)
            flow_recall = rd.get("flows", {}).get("recall", 0)
            param_recall = rd.get("parameters", {}).get("recall", 0)

            print(
                f"{r['disease']:<15} {r['llm']:<10} {comp_recall:>10.4f} {flow_recall:>10.4f} {param_recall:>10.4f}"
            )

        # Disease average row
        disease_recalls = [r["recall_data"] for r in disease_results]
        avg_comp = sum(
            r.get("compartments", {}).get("recall", 0) for r in disease_recalls
        ) / len(disease_recalls)
        avg_flow = sum(
            r.get("flows", {}).get("recall", 0) for r in disease_recalls
        ) / len(disease_recalls)
        avg_param = sum(
            r.get("parameters", {}).get("recall", 0) for r in disease_recalls
        ) / len(disease_recalls)

        print(
            f"{'--- AVERAGE ---':<15} {'':<10} {avg_comp:>10.4f} {avg_flow:>10.4f} {avg_param:>10.4f}"
        )
        print()

    # Overall averages
    total_recalls = [r["recall_data"] for r in results]
    overall_comp = sum(
        r.get("compartments", {}).get("recall", 0) for r in total_recalls
    ) / len(total_recalls)
    overall_flow = sum(
        r.get("flows", {}).get("recall", 0) for r in total_recalls
    ) / len(total_recalls)
    overall_param = sum(
        r.get("parameters", {}).get("recall", 0) for r in total_recalls
    ) / len(total_recalls)

    print("-" * 80)
    print(
        f"{'OVERALL':<15} {'':<10} {overall_comp:>10.4f} {overall_flow:>10.4f} {overall_param:>10.4f}"
    )
    print("=" * 80)


def print_detailed_table(results):
    """Print detailed table with TP/FN counts."""
    print()
    print("=" * 100)
    print("DETAILED RECALL METRICS")
    print("=" * 100)
    print()

    print(
        f"{'Disease':<12} {'LLM':<8} | {'Compartments':^22} | {'Flows':^22} | {'Parameters':^22}"
    )
    print(
        f"{'':12} {'':8} | {'Recall':>7} {'TP':>5} {'FN':>5} | {'Recall':>7} {'TP':>5} {'FN':>5} | {'Recall':>7} {'TP':>5} {'FN':>5}"
    )
    print("-" * 100)

    for r in results:
        rd = r["recall_data"]

        comp = rd.get("compartments", {})
        flow = rd.get("flows", {})
        param = rd.get("parameters", {})

        print(
            f"{r['disease']:<12} {r['llm']:<8} | "
            f"{comp.get('recall', 0):>7.4f} {comp.get('tp', 0):>5} {comp.get('fn', 0):>5} | "
            f"{flow.get('recall', 0):>7.4f} {flow.get('tp', 0):>5} {flow.get('fn', 0):>5} | "
            f"{param.get('recall', 0):>7.4f} {param.get('tp', 0):>5} {param.get('fn', 0):>5}"
        )


def save_summary_json(results, output_path: Path):
    """Save summary as JSON."""
    summary = {
        "generated_at": datetime.now().isoformat(),
        "total_runs": len(results),
        "comparisons": [],
        "overall_averages": {},
    }

    total_recalls = [r["recall_data"] for r in results]

    for r in results:
        rd = r["recall_data"]
        summary["comparisons"].append(
            {
                "disease": r["disease"],
                "llm": r["llm"],
                "folder": r["folder"],
                "compartments": rd.get("compartments", {}),
                "flows": rd.get("flows", {}),
                "parameters": rd.get("parameters", {}),
            }
        )

    summary["overall_averages"] = {
        "compartments_recall": sum(
            r.get("compartments", {}).get("recall", 0) for r in total_recalls
        )
        / len(total_recalls),
        "flows_recall": sum(r.get("flows", {}).get("recall", 0) for r in total_recalls)
        / len(total_recalls),
        "parameters_recall": sum(
            r.get("parameters", {}).get("recall", 0) for r in total_recalls
        )
        / len(total_recalls),
    }

    with open(output_path, "w") as f:
        json.dump(summary, f, indent=2)

    print(f"Summary JSON saved to: {output_path}")


def save_summary_csv(results, output_path: Path):
    """Save summary as CSV."""
    import csv

    with open(output_path, "w", newline="") as f:
        writer = csv.writer(f)

        # Header
        writer.writerow(
            [
                "Disease",
                "LLM",
                "Folder",
                "Compartments_Recall",
                "Compartments_TP",
                "Compartments_FN",
                "Flows_Recall",
                "Flows_TP",
                "Flows_FN",
                "Parameters_Recall",
                "Parameters_TP",
                "Parameters_FN",
            ]
        )

        # Data rows
        for r in results:
            rd = r["recall_data"]
            comp = rd.get("compartments", {})
            flow = rd.get("flows", {})
            param = rd.get("parameters", {})

            writer.writerow(
                [
                    r["disease"],
                    r["llm"],
                    r["folder"],
                    comp.get("recall", 0),
                    comp.get("tp", 0),
                    comp.get("fn", 0),
                    flow.get("recall", 0),
                    flow.get("tp", 0),
                    flow.get("fn", 0),
                    param.get("recall", 0),
                    param.get("tp", 0),
                    param.get("fn", 0),
                ]
            )

        # Average row
        total_recalls = [r["recall_data"] for r in results]
        avg_comp = sum(
            r.get("compartments", {}).get("recall", 0) for r in total_recalls
        ) / len(total_recalls)
        avg_flow = sum(
            r.get("flows", {}).get("recall", 0) for r in total_recalls
        ) / len(total_recalls)
        avg_param = sum(
            r.get("parameters", {}).get("recall", 0) for r in total_recalls
        ) / len(total_recalls)

        writer.writerow(
            [
                "OVERALL",
                "AVERAGE",
                "",
                avg_comp,
                "",
                "",
                avg_flow,
                "",
                "",
                avg_param,
                "",
                "",
            ]
        )

    print(f"Summary CSV saved to: {output_path}")


def main():
    import argparse

    parser = argparse.ArgumentParser(
        description="Summarize recall evaluation results from pruned pipeline runs"
    )
    parser.add_argument(
        "--output-dir",
        type=str,
        default="reports_pruned_output",
        help="Directory containing pruned pipeline output folders",
    )
    parser.add_argument(
        "--format",
        type=str,
        choices=["table", "csv", "json", "all"],
        default="all",
        help="Output format (default: all)",
    )
    parser.add_argument(
        "--detailed", action="store_true", help="Show detailed table with TP/FN counts"
    )

    args = parser.parse_args()

    output_dir = Path(args.output_dir)
    results = find_recall_results(output_dir)

    if not results:
        print(f"No recall_evaluation.json files found in {output_dir}")
        print("Run run_pruned_pipeline.py first to generate results.")
        return 1

    print(f"Found {len(results)} recall evaluation results")

    if args.format in ["table", "all"]:
        print_summary_table(results)

    if args.detailed and args.format in ["table", "all"]:
        print_detailed_table(results)

    if args.format in ["json", "all"]:
        save_summary_json(results, output_dir / "pruned_recall_summary.json")

    if args.format in ["csv", "all"]:
        save_summary_csv(results, output_dir / "pruned_recall_summary.csv")

    return 0


if __name__ == "__main__":
    exit(main())
