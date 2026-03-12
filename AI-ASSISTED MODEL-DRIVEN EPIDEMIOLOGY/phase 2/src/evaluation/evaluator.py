"""Evaluation: Calculate metrics and scores using cosine similarity

Calculates traceability coverage, faithfulness, gap metrics, and optionally
compares to gold standard for precision/recall using semantic embeddings.
"""

import json
import logging
import os
import xml.etree.ElementTree as ET
from dataclasses import dataclass, asdict
from pathlib import Path
from typing import Dict, List, Any, Optional, Tuple

os.environ["TOKENIZERS_PARALLELISM"] = "false"
logging.getLogger("sentence_transformers").setLevel(logging.ERROR)
logging.getLogger("transformers").setLevel(logging.ERROR)

import numpy as np
from sentence_transformers import SentenceTransformer, util
from scipy.optimize import linear_sum_assignment


MODEL_NAME = "sentence-transformers/all-MiniLM-L6-v2"
DEFAULT_THRESHOLD = 0.70

COMPOSITE_WEIGHTS = {
    "compartments": 0.425,
    "flows": 0.425,
    "parameters": 0.150,
}


@dataclass
class MatchResult:
    gold: str
    extracted: str
    score: float


@dataclass
class EntityMetrics:
    tp: int
    fp: int
    fn: int

    @property
    def precision(self) -> float:
        if (self.tp + self.fp) == 0:
            return 0.0
        return self.tp / (self.tp + self.fp)

    @property
    def recall(self) -> float:
        if (self.tp + self.fn) == 0:
            return 0.0
        return self.tp / (self.tp + self.fn)

    @property
    def f1(self) -> float:
        if self.precision + self.recall == 0:
            return 0.0
        return 2 * self.precision * self.recall / (self.precision + self.recall)


class Evaluator:
    """Evaluate extraction quality with semantic similarity metrics"""

    def __init__(
        self,
        gold_standard_path: Optional[str] = None,
        threshold: float = DEFAULT_THRESHOLD,
    ):
        """
        Initialize evaluator.

        Args:
            gold_standard_path: Path to gold standard JSON or .compmodel file (optional)
            threshold: Cosine similarity threshold for matching (default 0.70)
        """
        self.gold_standard = None
        self.threshold = threshold
        self._model = None

        if gold_standard_path:
            self._load_gold_standard(gold_standard_path)

    @property
    def model(self) -> SentenceTransformer:
        """Lazy load the embedding model"""
        if self._model is None:
            self._model = SentenceTransformer(MODEL_NAME)
        return self._model

    def load_model(self):
        """Eagerly load the embedding model."""
        _ = self.model

    def _load_gold_standard(self, gold_path: str):
        """Load gold standard for comparison (supports both JSON and .compmodel files)"""
        gold_path_obj = Path(gold_path)
        if not gold_path_obj.exists():
            print(f"Warning: Gold standard file not found: {gold_path}")
            return

        try:
            if gold_path_obj.suffix == ".compmodel":
                self.gold_standard = self._convert_compmodel_to_gold_standard(gold_path)
            else:
                with open(gold_path, "r") as f:
                    self.gold_standard = json.load(f)
        except Exception as e:
            print(f"Warning: Failed to load gold standard: {e}")

    def _convert_compmodel_to_gold_standard(
        self, compmodel_path: str
    ) -> Dict[str, Any]:
        """Convert .compmodel XML file to gold standard JSON format."""
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
                            import re

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
                        flows.append(
                            {
                                "source": source_comp,
                                "target": target_comp,
                                "type": "RateFlow",
                            }
                        )

            return {
                "compartments": compartments,
                "parameters": parameters,
                "flows": flows,
            }
        except Exception as e:
            print(f"Warning: Failed to parse .compmodel file {compmodel_path}: {e}")
            return {"compartments": [], "parameters": [], "flows": []}

    def _prepare_compartment_strings(self, data: Dict[str, Any]) -> List[str]:
        """Extract compartment names for embedding."""
        return [
            comp.get("normalized_name") or comp.get("name", "")
            for comp in data.get("compartments", [])
        ]

    def _prepare_flow_strings(self, data: Dict[str, Any]) -> List[str]:
        """Extract flow strings as 'source->target' for embedding."""
        flows = data.get("flows", [])
        flow_strings = []
        for flow in flows:
            source = flow.get("source", "") or ""
            target = flow.get("target", "") or ""
            if source and target:
                flow_strings.append(f"{source}->{target}")
        return flow_strings

    def _prepare_parameter_strings(self, data: Dict[str, Any]) -> List[str]:
        """Extract parameter strings as 'symbol description' for embedding."""
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
        """Compute cosine similarity matrix between gold and extracted strings."""
        if not gold_strings or not extracted_strings:
            return np.array([])

        gold_embs = self.model.encode(gold_strings, convert_to_tensor=True)
        extracted_embs = self.model.encode(extracted_strings, convert_to_tensor=True)

        similarity_matrix = util.cos_sim(gold_embs, extracted_embs)
        return similarity_matrix.numpy()

    def _match_strings(
        self, gold_strings: List[str], extracted_strings: List[str], threshold: float
    ) -> Tuple[List[MatchResult], List[int], List[int]]:
        """Match gold strings to extracted strings using Hungarian algorithm."""
        if not gold_strings and not extracted_strings:
            return [], [], []

        if not gold_strings:
            return [], [], list(range(len(extracted_strings)))

        if not extracted_strings:
            return [], list(range(len(gold_strings))), []

        similarity_matrix = self._compute_similarity_matrix(
            gold_strings, extracted_strings
        )

        cost_matrix = 1 - similarity_matrix
        gold_indices, extracted_indices = linear_sum_assignment(cost_matrix)

        matches = []
        unmatched_gold = set(range(len(gold_strings)))
        unmatched_extracted = set(range(len(extracted_strings)))

        for g_idx, l_idx in zip(gold_indices, extracted_indices):
            score = float(similarity_matrix[g_idx][l_idx])
            if score >= threshold:
                matches.append(
                    MatchResult(
                        gold=gold_strings[g_idx],
                        extracted=extracted_strings[l_idx],
                        score=score,
                    )
                )
                unmatched_gold.discard(g_idx)
                unmatched_extracted.discard(l_idx)

        return matches, sorted(unmatched_gold), sorted(unmatched_extracted)

    def _check_flow_direction_errors(
        self,
        gold_strings: List[str],
        extracted_strings: List[str],
        matches: List[MatchResult],
        similarity_matrix: np.ndarray,
    ) -> List[Dict[str, str]]:
        """Check for flows that matched when reversed but not in original direction."""
        errors = []

        if not gold_strings or not extracted_strings:
            return errors

        matched_pairs = {(m.gold, m.extracted) for m in matches}

        for g_idx, g_str in enumerate(gold_strings):
            for l_idx, l_str in enumerate(extracted_strings):
                if (g_str, l_str) in matched_pairs:
                    continue

                g_parts = g_str.split("->")
                l_parts = l_str.split("->")

                if len(g_parts) == 2 and len(l_parts) == 2:
                    reversed_l = f"{l_parts[1]}->{l_parts[0]}"
                    reversed_g = f"{g_parts[1]}->{g_parts[0]}"

                    if reversed_g == l_str or g_str == reversed_l:
                        forward_score = float(similarity_matrix[g_idx][l_idx])
                        if forward_score < self.threshold:
                            errors.append(
                                {
                                    "gold": g_str,
                                    "extracted": l_str,
                                    "error_type": "reversed_direction",
                                }
                            )

        return errors

    def _check_value_errors(
        self, gold_data: Dict[str, Any], extracted_data: Dict[str, Any]
    ) -> List[Dict[str, Any]]:
        """Check parameter values when both have numeric values."""
        errors = []

        gold_params = {
            p.get("symbol", ""): p
            for p in gold_data.get("parameters", [])
            if p.get("symbol")
        }
        extracted_params = {
            p.get("symbol", ""): p
            for p in extracted_data.get("parameters", [])
            if p.get("symbol")
        }

        UNIT_TO_DAY = {
            "per week": 1 / 7,
            "per year": 1 / 365,
            "per day": 1.0,
            "1/time": 1.0,
        }

        for symbol, gold_p in gold_params.items():
            if symbol not in extracted_params:
                continue

            extracted_p = extracted_params[symbol]

            gold_val = gold_p.get("value")
            llm_val = extracted_p.get("value")

            if isinstance(gold_val, (int, float)) and isinstance(llm_val, (int, float)):
                gold_unit = gold_p.get("unit", "").lower()
                llm_unit = extracted_p.get("unit", "").lower()

                gold_per_day = gold_val * UNIT_TO_DAY.get(gold_unit, 1.0)
                llm_per_day = llm_val * UNIT_TO_DAY.get(llm_unit, 1.0)

                if (
                    gold_per_day != 0
                    and abs(gold_per_day - llm_per_day) / gold_per_day >= 0.05
                ):
                    errors.append(
                        {
                            "symbol": symbol,
                            "gold_value": gold_val,
                            "extracted_value": llm_val,
                            "gold_unit": gold_p.get("unit"),
                            "extracted_unit": extracted_p.get("unit"),
                        }
                    )

        return errors

    def evaluate(
        self,
        extracted_entities: Dict[str, Any],
        traceability: Dict[str, Any],
        gaps: Dict[str, Any],
    ) -> Dict[str, Any]:
        """
        Evaluate extraction quality.

        Returns:
            Dictionary with evaluation metrics
        """
        metrics = {
            "traceability_coverage": self._calculate_traceability_coverage(
                traceability
            ),
            "faithfulness": self._calculate_faithfulness(traceability),
            "gap_analysis": self._analyze_gaps(gaps),
            "gold_standard_comparison": None,
        }

        if self.gold_standard:
            metrics["gold_standard_comparison"] = self._compare_to_gold_standard(
                extracted_entities, self.gold_standard
            )

        return metrics

    def _calculate_traceability_coverage(
        self, traceability: Dict[str, Any]
    ) -> Dict[str, Any]:
        """Calculate traceability coverage metrics"""
        metrics = traceability.get("coverage_metrics", {})
        return {
            "total_items": metrics.get("total_items", 0),
            "items_with_evidence": metrics.get("items_with_evidence", 0),
            "coverage_percentage": metrics.get("coverage_percentage", 0.0),
        }

    def _calculate_faithfulness(self, traceability: Dict[str, Any]) -> Dict[str, Any]:
        """Calculate faithfulness metrics"""
        metrics = traceability.get("coverage_metrics", {})
        return {
            "paper_backed_items": metrics.get("paper_backed_items", 0),
            "faithfulness_percentage": metrics.get("faithfulness_percentage", 0.0),
        }

    def _analyze_gaps(self, gaps: Dict[str, Any]) -> Dict[str, Any]:
        """Analyze gap metrics"""
        summary = gaps.get("summary", {})
        return {
            "total_gaps": summary.get("total_gaps", 0),
            "critical_gaps": summary.get("critical_gaps", 0),
            "high_gaps": summary.get("high_gaps", 0),
            "medium_gaps": summary.get("medium_gaps", 0),
            "gap_breakdown": {
                "missing_compartments": len(gaps.get("missing_compartments", [])),
                "missing_parameters": len(gaps.get("missing_parameters", [])),
                "missing_stratifications": len(gaps.get("missing_stratifications", [])),
                "missing_interventions": len(gaps.get("missing_interventions", [])),
            },
        }

    def _compare_to_gold_standard(
        self, extracted_entities: Dict[str, Any], gold_standard: Dict[str, Any]
    ) -> Dict[str, Any]:
        """Compare extracted entities to gold standard using cosine similarity."""
        gold_compartments = self._prepare_compartment_strings(gold_standard)
        extracted_compartments = self._prepare_compartment_strings(extracted_entities)

        gold_flows = self._prepare_flow_strings(gold_standard)
        extracted_flows = self._prepare_flow_strings(extracted_entities)

        gold_params = self._prepare_parameter_strings(gold_standard)
        extracted_params = self._prepare_parameter_strings(extracted_entities)

        comp_matches, comp_unmatched_gold, comp_unmatched_extracted = (
            self._match_strings(
                gold_compartments, extracted_compartments, self.threshold
            )
        )

        flow_matches, flow_unmatched_gold, flow_unmatched_extracted = (
            self._match_strings(gold_flows, extracted_flows, self.threshold)
        )

        param_matches, param_unmatched_gold, param_unmatched_extracted = (
            self._match_strings(gold_params, extracted_params, self.threshold)
        )

        comp_metrics = EntityMetrics(
            tp=len(comp_matches),
            fp=len(comp_unmatched_extracted),
            fn=len(comp_unmatched_gold),
        )

        flow_metrics = EntityMetrics(
            tp=len(flow_matches),
            fp=len(flow_unmatched_extracted),
            fn=len(flow_unmatched_gold),
        )

        param_metrics = EntityMetrics(
            tp=len(param_matches),
            fp=len(param_unmatched_extracted),
            fn=len(param_unmatched_gold),
        )

        flow_similarity_matrix = np.array([])
        if gold_flows and extracted_flows:
            flow_similarity_matrix = self._compute_similarity_matrix(
                gold_flows, extracted_flows
            )

        direction_errors = self._check_flow_direction_errors(
            gold_flows, extracted_flows, flow_matches, flow_similarity_matrix
        )

        value_errors = self._check_value_errors(gold_standard, extracted_entities)

        composite_score = (
            COMPOSITE_WEIGHTS["compartments"] * comp_metrics.f1
            + COMPOSITE_WEIGHTS["flows"] * flow_metrics.f1
            + COMPOSITE_WEIGHTS["parameters"] * param_metrics.f1
        )

        return {
            "threshold": self.threshold,
            "compartments": {
                "tp": comp_metrics.tp,
                "fp": comp_metrics.fp,
                "fn": comp_metrics.fn,
                "precision": round(comp_metrics.precision, 4),
                "recall": round(comp_metrics.recall, 4),
                "f1": round(comp_metrics.f1, 4),
            },
            "flows": {
                "tp": flow_metrics.tp,
                "fp": flow_metrics.fp,
                "fn": flow_metrics.fn,
                "precision": round(flow_metrics.precision, 4),
                "recall": round(flow_metrics.recall, 4),
                "f1": round(flow_metrics.f1, 4),
            },
            "parameters": {
                "tp": param_metrics.tp,
                "fp": param_metrics.fp,
                "fn": param_metrics.fn,
                "precision": round(param_metrics.precision, 4),
                "recall": round(param_metrics.recall, 4),
                "f1": round(param_metrics.f1, 4),
            },
            "composite_score": round(composite_score, 4),
            "compartment_matches": [asdict(m) for m in comp_matches],
            "flow_matches": [asdict(m) for m in flow_matches],
            "parameter_matches": [asdict(m) for m in param_matches],
            "direction_errors": direction_errors,
            "value_errors": value_errors,
            "matching_method": "cosine_similarity",
        }

    def save_evaluation(self, evaluation: Dict[str, Any], output_path: str):
        """Save evaluation results to JSON"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)

        with open(output_path, "w") as f:
            json.dump(evaluation, f, indent=2)
