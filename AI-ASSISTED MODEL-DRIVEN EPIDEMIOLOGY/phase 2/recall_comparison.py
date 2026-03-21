"""Compare extracted models to baseline models using recall-only metrics.

Calculates recall (TP / (TP + FN)) for compartments, flows, and parameters
using semantic similarity matching. No composite score - just raw recall per entity type.
"""

import json
import logging
import os
import re
import xml.etree.ElementTree as ET
from dataclasses import dataclass
from pathlib import Path
from typing import Dict, List, Any, Optional

os.environ["TOKENIZERS_PARALLELISM"] = "false"
logging.getLogger("sentence_transformers").setLevel(logging.ERROR)
logging.getLogger("transformers").setLevel(logging.ERROR)

import numpy as np
from sentence_transformers import SentenceTransformer, util
from scipy.optimize import linear_sum_assignment


MODEL_NAME = "sentence-transformers/all-MiniLM-L6-v2"
DEFAULT_THRESHOLD = 0.70


@dataclass
class RecallResult:
    tp: int
    fn: int

    @property
    def recall(self) -> float:
        if (self.tp + self.fn) == 0:
            return 0.0
        return self.tp / (self.tp + self.fn)


class RecallComparator:
    """Compare extracted models to baselines using recall metrics only"""

    def __init__(self, threshold: float = DEFAULT_THRESHOLD):
        self.threshold = threshold
        self._model = None

    @property
    def model(self) -> SentenceTransformer:
        if self._model is None:
            self._model = SentenceTransformer(MODEL_NAME)
        return self._model

    def load_model(self):
        _ = self.model

    def parse_compmodel(self, compmodel_path: str) -> Dict[str, Any]:
        """Parse .compmodel XML file to extract compartments, flows, parameters."""
        try:
            parser = ET.XMLParser()
            try:
                tree = ET.parse(compmodel_path, parser=parser)
                root = tree.getroot()
            except ET.ParseError as e:
                if "unbound prefix" in str(e) or "prefix" in str(e).lower():
                    with open(compmodel_path, "r", encoding="utf-8") as f:
                        content = f.read()
                    if "xsi:type" in content and "xmlns:xsi" not in content:
                        content = content.replace(
                            "xmlns:compartmental=",
                            'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:compartmental=',
                            1,
                        )
                    root = ET.fromstring(content)
                else:
                    raise

            compartments = []
            for comp in root.findall(
                ".//{http://example.com/compartmentalmodel}compartments"
            ):
                comp_name = comp.get("PrimaryName", "")
                if comp_name:
                    compartments.append({"name": comp_name})

            if not compartments:
                for comp in root.findall(".//compartments"):
                    comp_name = comp.get("PrimaryName", "")
                    if comp_name:
                        compartments.append({"name": comp_name})

            parameters = []
            for param in root.findall(
                ".//{http://example.com/compartmentalmodel}parameters"
            ):
                param_name = param.get("name", "")
                if param_name and param_name.lower() not in ["none", "n/a", ""]:
                    parameters.append(
                        {
                            "symbol": param_name,
                            "value": param.get("expression", ""),
                            "unit": param.get("unit", ""),
                            "description": param.get("description", ""),
                        }
                    )

            if not parameters:
                for param in root.findall(".//parameters"):
                    param_name = param.get("name", "")
                    if param_name and param_name.lower() not in ["none", "n/a", ""]:
                        parameters.append(
                            {
                                "symbol": param_name,
                                "value": param.get("expression", ""),
                                "unit": param.get("unit", ""),
                                "description": param.get("description", ""),
                            }
                        )

            flows = []
            namespace = "{http://example.com/compartmentalmodel}"
            comp_elements = root.findall(f".//{namespace}compartments")
            if not comp_elements:
                comp_elements = root.findall(".//compartments")

            for comp in comp_elements:
                source_comp = comp.get("PrimaryName", "")
                if not source_comp:
                    continue

                outgoing_flows = comp.findall(f"{namespace}outgoingFlows")
                if not outgoing_flows:
                    outgoing_flows = comp.findall("outgoingFlows")

                for flow in outgoing_flows:
                    target_comp = None
                    target_ref = flow.get("target", "")
                    if target_ref and "compartments." in target_ref:
                        try:
                            match = re.search(r"compartments\.(\d+)", target_ref)
                            if match:
                                comp_index = int(match.group(1))
                                if comp_index < len(comp_elements):
                                    target_comp = comp_elements[comp_index].get(
                                        "PrimaryName", ""
                                    )
                        except (ValueError, IndexError, AttributeError):
                            pass

                    if target_comp:
                        flows.append({"source": source_comp, "target": target_comp})

            return {
                "compartments": compartments,
                "parameters": parameters,
                "flows": flows,
            }
        except Exception as e:
            print(f"Warning: Failed to parse {compmodel_path}: {e}")
            return {"compartments": [], "parameters": [], "flows": []}

    def _prepare_compartment_strings(self, data: Dict[str, Any]) -> List[str]:
        return [
            comp.get("normalized_name") or comp.get("name", "")
            for comp in data.get("compartments", [])
        ]

    def _prepare_flow_strings(self, data: Dict[str, Any]) -> List[str]:
        flows = data.get("flows", [])
        flow_strings = []
        for flow in flows:
            source = flow.get("source", "") or ""
            target = flow.get("target", "") or ""
            if source and target:
                flow_strings.append(f"{source}->{target}")
        return flow_strings

    def _prepare_parameter_strings(self, data: Dict[str, Any]) -> List[str]:
        params = data.get("parameters", [])
        param_strings = []
        for param in params:
            symbol = (
                param.get("normalized_name")
                or param.get("symbol", "")
                or param.get("name", "")
            )
            value = param.get("value", "")
            description = param.get("description", "")
            if symbol:
                parts = [symbol]
                if value:
                    parts.append(str(value))
                if description:
                    parts.append(description)
                param_strings.append(" ".join(parts))
        return param_strings

    def _compute_similarity_matrix(
        self, gold_strings: List[str], extracted_strings: List[str]
    ) -> np.ndarray:
        if not gold_strings or not extracted_strings:
            return np.array([])

        gold_embs = self.model.encode(gold_strings, convert_to_tensor=True)
        extracted_embs = self.model.encode(extracted_strings, convert_to_tensor=True)
        similarity_matrix = util.cos_sim(gold_embs, extracted_embs)
        return similarity_matrix.numpy()

    def _match_and_calculate_recall(
        self, gold_strings: List[str], extracted_strings: List[str]
    ) -> RecallResult:
        """Match strings and calculate recall (TP / (TP + FN))."""
        if not gold_strings and not extracted_strings:
            return RecallResult(tp=0, fn=0)

        if not gold_strings:
            return RecallResult(tp=0, fn=0)

        if not extracted_strings:
            return RecallResult(tp=0, fn=len(gold_strings))

        similarity_matrix = self._compute_similarity_matrix(
            gold_strings, extracted_strings
        )
        cost_matrix = 1 - similarity_matrix
        gold_indices, extracted_indices = linear_sum_assignment(cost_matrix)

        tp = 0
        for g_idx, l_idx in zip(gold_indices, extracted_indices):
            score = float(similarity_matrix[g_idx][l_idx])
            if score >= self.threshold:
                tp += 1

        fn = len(gold_strings) - tp
        return RecallResult(tp=tp, fn=fn)

    def compare(self, extracted_path: str, baseline_path: str) -> Dict[str, Any]:
        """Compare extracted model to baseline, return recall metrics."""
        extracted_data = self.parse_compmodel(extracted_path)
        baseline_data = self.parse_compmodel(baseline_path)

        gold_compartments = self._prepare_compartment_strings(baseline_data)
        extracted_compartments = self._prepare_compartment_strings(extracted_data)

        gold_flows = self._prepare_flow_strings(baseline_data)
        extracted_flows = self._prepare_flow_strings(extracted_data)

        gold_params = self._prepare_parameter_strings(baseline_data)
        extracted_params = self._prepare_parameter_strings(extracted_data)

        comp_recall = self._match_and_calculate_recall(
            gold_compartments, extracted_compartments
        )
        flow_recall = self._match_and_calculate_recall(gold_flows, extracted_flows)
        param_recall = self._match_and_calculate_recall(gold_params, extracted_params)

        return {
            "threshold": self.threshold,
            "compartments": {
                "tp": comp_recall.tp,
                "fn": comp_recall.fn,
                "recall": round(comp_recall.recall, 4),
                "gold_count": len(gold_compartments),
                "extracted_count": len(extracted_compartments),
            },
            "flows": {
                "tp": flow_recall.tp,
                "fn": flow_recall.fn,
                "recall": round(flow_recall.recall, 4),
                "gold_count": len(gold_flows),
                "extracted_count": len(extracted_flows),
            },
            "parameters": {
                "tp": param_recall.tp,
                "fn": param_recall.fn,
                "recall": round(param_recall.recall, 4),
                "gold_count": len(gold_params),
                "extracted_count": len(extracted_params),
            },
        }


def find_report_folders(reports_dir: Path) -> List[Dict[str, str]]:
    """Find all disease model report folders."""
    folders = []
    if not reports_dir.exists():
        return folders

    for entry in sorted(reports_dir.iterdir()):
        if not entry.is_dir():
            continue

        name = entry.name.lower()

        # Handle both regular (_llm_) and pruned (_pruned_llm_) naming patterns
        if "_llm_" in name:
            parts = name.split("_llm_")
            if len(parts) != 2:
                continue
            disease = parts[0]
            llm_provider = parts[1].rsplit("_", 2)[0]
            timestamp_parts = parts[1].rsplit("_", 2)
            timestamp = (
                "_".join(timestamp_parts[-2:]) if len(timestamp_parts) >= 2 else ""
            )
        elif "_pruned_" in name:
            # Format: {disease}_pruned_{llm}_{timestamp}
            parts = name.split("_pruned_")
            if len(parts) != 2:
                continue
            disease = parts[0]
            rest_parts = parts[1].rsplit("_", 2)
            llm_provider = rest_parts[0]
            timestamp = "_".join(rest_parts[-2:]) if len(rest_parts) >= 2 else ""
        else:
            continue

        model_file = entry / "model_draft.compmodel"
        if model_file.exists():
            folders.append(
                {
                    "disease": disease,
                    "llm": llm_provider,
                    "report_folder": str(entry),
                    "model_path": str(model_file),
                    "timestamp": timestamp,
                }
            )

    return folders


def find_baseline_model(baseline_dir: Path, disease: str) -> Optional[str]:
    """Find baseline model matching disease name."""
    baseline_path = Path(baseline_dir)
    if not baseline_path.exists():
        return None

    disease_lower = disease.lower()

    for model_file in baseline_path.glob("*.compmodel"):
        if model_file.stem.lower() == disease_lower:
            return str(model_file)

    for model_file in baseline_path.glob("*.compmodel"):
        if disease_lower in model_file.stem.lower():
            return str(model_file)

    return None


def main():
    import argparse

    parser = argparse.ArgumentParser(
        description="Compare extracted models to baselines using recall metrics"
    )
    parser.add_argument(
        "--reports-dir",
        type=str,
        default="reports",
        help="Directory with extracted model reports",
    )
    parser.add_argument(
        "--baseline-dir",
        type=str,
        default="data/baseline_models",
        help="Directory with baseline models",
    )
    parser.add_argument(
        "--threshold", type=float, default=0.70, help="Cosine similarity threshold"
    )
    parser.add_argument(
        "--output",
        type=str,
        default="recall_comparison_report.json",
        help="Output file path",
    )
    args = parser.parse_args()

    reports_dir = Path(args.reports_dir)
    baseline_dir = Path(args.baseline_dir)

    print("=" * 70)
    print("RECALL COMPARISON: Extracted Models vs Baseline Models")
    print("=" * 70)
    print(f"Reports dir: {reports_dir}")
    print(f"Baseline dir: {baseline_dir}")
    print(f"Threshold: {args.threshold}")
    print()

    comparator = RecallComparator(threshold=args.threshold)
    print("Loading embedding model...")
    comparator.load_model()
    print()

    report_folders = find_report_folders(reports_dir)
    print(f"Found {len(report_folders)} extracted model reports")
    print()

    comparisons = []
    errors = []

    for folder in report_folders:
        disease = folder["disease"]
        llm = folder["llm"]
        extracted_path = folder["model_path"]

        baseline_path = find_baseline_model(baseline_dir, disease)
        if not baseline_path:
            print(f"  [SKIP] {disease}/{llm}: No baseline model found")
            continue

        print(f"Processing: {disease} / {llm}...")
        print(f"  Extracted: {extracted_path}")
        print(f"  Baseline: {baseline_path}")

        try:
            result = comparator.compare(extracted_path, baseline_path)

            comp_recall = result["compartments"]["recall"]
            flow_recall = result["flows"]["recall"]
            param_recall = result["parameters"]["recall"]

            comparison = {
                "disease": disease,
                "llm": llm,
                "report_folder": folder["report_folder"],
                "extracted_model": extracted_path,
                "baseline_model": baseline_path,
                "compartments": result["compartments"],
                "flows": result["flows"],
                "parameters": result["parameters"],
            }
            comparisons.append(comparison)

            print(
                f"  Compartments Recall: {comp_recall:.4f} (TP={result['compartments']['tp']}, FN={result['compartments']['fn']})"
            )
            print(
                f"  Flows Recall:        {flow_recall:.4f} (TP={result['flows']['tp']}, FN={result['flows']['fn']})"
            )
            print(
                f"  Parameters Recall:   {param_recall:.4f} (TP={result['parameters']['tp']}, FN={result['parameters']['fn']})"
            )
            print()

        except Exception as e:
            print(f"  [ERROR] {e}")
            errors.append({"disease": disease, "llm": llm, "error": str(e)})
            print()

    print("=" * 70)
    print("SUMMARY TABLE")
    print("=" * 70)
    print(
        f"{'Disease':<15} {'LLM':<10} {'Compartments':<15} {'Flows':<15} {'Parameters':<15}"
    )
    print("-" * 70)
    for c in comparisons:
        print(
            f"{c['disease']:<15} {c['llm']:<10} {c['compartments']['recall']:<15.4f} {c['flows']['recall']:<15.4f} {c['parameters']['recall']:<15.4f}"
        )
    print("-" * 70)

    output_data = {
        "threshold": args.threshold,
        "comparisons": comparisons,
        "errors": errors,
        "summary": {
            "total_comparisons": len(comparisons),
            "total_errors": len(errors),
        },
    }

    output_path = Path(args.output)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    with open(output_path, "w") as f:
        json.dump(output_data, f, indent=2)
    print(f"\nResults saved to: {output_path}")

    if errors:
        print(f"\n{len(errors)} comparisons failed (see 'errors' in output JSON)")

    return 0


if __name__ == "__main__":
    exit(main())
