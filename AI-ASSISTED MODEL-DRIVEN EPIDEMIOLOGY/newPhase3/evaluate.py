"""
evaluate.py
===========
Entry point for evaluating LLM-extracted compartmental models.

Loads gold standard from .compmodel XML, loads extracted model from JSON,
runs semantic matching evaluation, returns JSON report.
"""

import json
from pathlib import Path
from typing import Dict, Any, Optional, Union

from evaluator import Evaluator, report_to_dict
from utils.xmlParsestojson import parse_compartmental_xml


def load_gold(source: str) -> Dict[str, Any]:
    """Load gold standard from .compmodel XML or .json file."""
    if source.endswith(".json"):
        with open(source, "r") as f:
            return json.load(f)
    else:
        with open(source, "r") as f:
            xml_content = f.read()
        return parse_compartmental_xml(xml_content)


def load_extracted(source: str) -> Dict[str, Any]:
    """Load extracted model from JSON file."""
    with open(source, "r") as f:
        return json.load(f)


def evaluate(
    gold_source: Union[str, Dict[str, Any]],
    extracted_source: Union[str, Dict[str, Any]],
    threshold: float = 0.72,
    model_name: str = "sentence-transformers/all-MiniLM-L6-v2",
) -> Dict[str, Any]:
    """
    Evaluate extracted model against gold standard.

    Args:
        gold_source: Path to .compmodel XML file OR pre-parsed JSON dict
        extracted_source: Path to extracted JSON file OR pre-parsed JSON dict
        threshold: Similarity threshold (default 0.72)
        model_name: Embedding model name

    Returns:
        Dictionary with evaluation metrics (TP, FP, FN, F1, composite score, etc.)
    """
    if isinstance(gold_source, (str, Path)):
        gold_data = load_gold(gold_source)
    else:
        gold_data = gold_source

    if isinstance(extracted_source, (str, Path)):
        extracted_data = load_extracted(extracted_source)
    else:
        extracted_data = extracted_source

    evaluator = Evaluator(threshold=threshold, model_name=model_name)
    report = evaluator.evaluate(gold_data, extracted_data)

    return report_to_dict(report)


def evaluate_from_files(
    gold_xml_path: str,
    extracted_json_path: str,
    output_path: Optional[str] = None,
    threshold: float = 0.72,
) -> Dict[str, Any]:
    """
    Evaluate extracted JSON against gold XML, optionally save report.

    Args:
        gold_xml_path: Path to gold standard .compmodel file
        extracted_json_path: Path to LLM-extracted JSON file
        output_path: Optional path to save JSON report
        threshold: Similarity threshold

    Returns:
        Dictionary with evaluation metrics
    """
    result = evaluate(gold_xml_path, extracted_json_path, threshold)

    if output_path:
        with open(output_path, "w") as f:
            json.dump(result, f, indent=2)

    return result


if __name__ == "__main__":
    import sys

    if len(sys.argv) < 3:
        print(
            "Usage: python evaluate.py <gold.compmodel> <extracted.json> [output.json] [threshold]"
        )
        sys.exit(1)

    gold_path = sys.argv[1]
    extracted_path = sys.argv[2]
    output_path = sys.argv[3] if len(sys.argv) > 3 else None
    threshold = float(sys.argv[4]) if len(sys.argv) > 4 else 0.72

    result = evaluate_from_files(gold_path, extracted_path, output_path, threshold)
    print(json.dumps(result, indent=2))
