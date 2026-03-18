"""
evaluator.py
============
Semantic matching engine for EpiMDE model evaluation.

Uses sentence embeddings to compare gold-standard compartmental models
against LLM-extracted models. Converts both to flat strings, embeds them,
and uses Hungarian algorithm for optimal matching.
"""

import json
import logging
import os
from typing import Dict, Any, List, Tuple, Optional
from dataclasses import dataclass, asdict

os.environ["TOKENIZERS_PARALLELISM"] = "false"

logging.getLogger("sentence_transformers").setLevel(logging.ERROR)
logging.getLogger("transformers").setLevel(logging.ERROR)

import numpy as np
from sentence_transformers import SentenceTransformer, util
from scipy.optimize import linear_sum_assignment


MODEL_NAME = "sentence-transformers/all-MiniLM-L6-v2"
DEFAULT_THRESHOLD = 0.72

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
class EvaluationMetrics:
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


@dataclass
class EvaluationReport:
    threshold: float
    compartments: EvaluationMetrics
    flows: EvaluationMetrics
    parameters: EvaluationMetrics
    compartment_matches: List[MatchResult]
    flow_matches: List[MatchResult]
    parameter_matches: List[MatchResult]
    composite_score: float
    direction_errors: List[Dict[str, str]]
    value_errors: List[Dict[str, Any]]


class Evaluator:
    def __init__(
        self, threshold: float = DEFAULT_THRESHOLD, model_name: str = MODEL_NAME
    ):
        self.threshold = threshold
        self.model_name = model_name
        self._model = None

    @property
    def model(self) -> SentenceTransformer:
        if self._model is None:
            self._model = SentenceTransformer(self.model_name)
        return self._model

    def load_model(self):
        """Eagerly load the embedding model."""
        _ = self.model

    def _prepare_compartment_strings(self, data: Dict[str, Any]) -> List[str]:
        """Extract compartment names for embedding."""
        return [comp.get("name", "") for comp in data.get("compartments", [])]

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
            symbol = param.get("symbol", "")
            description = param.get("description", "")
            if symbol:
                if description:
                    param_strings.append(f"{symbol} {description}")
                else:
                    param_strings.append(symbol)
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
    ) -> Tuple[List[MatchResult], List[int], List[int], Any]:
        """
        Match gold strings to extracted strings using Hungarian algorithm.

        Returns:
            matches: List of MatchResult for pairs above threshold
            unmatched_gold_indices: Indices of gold strings with no match
            unmatched_extracted_indices: Indices of extracted strings with no match
            similarity_matrix: The computed similarity matrix (None if no computation needed)
        """
        if not gold_strings and not extracted_strings:
            return [], [], [], None

        if not gold_strings:
            return [], [], list(range(len(extracted_strings))), None

        if not extracted_strings:
            return [], list(range(len(gold_strings))), [], None

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
            else:
                unmatched_gold.add(g_idx)

        unmatched_gold = sorted(unmatched_gold)
        unmatched_extracted = sorted(unmatched_extracted)

        return matches, unmatched_gold, unmatched_extracted, similarity_matrix

    def _check_flow_direction_errors(
        self,
        gold_data: Dict[str, Any],
        extracted_data: Dict[str, Any],
        matches: List[MatchResult],
        threshold: float,
        similarity_matrix: Any = None,
    ) -> List[Dict[str, str]]:
        """Check for flows that matched when reversed but not in original direction."""
        errors = []

        gold_flows = gold_data.get("flows", [])
        extracted_flows = extracted_data.get("flows", [])

        gold_strings = self._prepare_flow_strings(gold_data)
        extracted_strings = self._prepare_flow_strings(extracted_data)

        if not gold_strings or not extracted_strings:
            return errors

        if similarity_matrix is None:
            similarity_matrix = self._compute_similarity_matrix(
                gold_strings, extracted_strings
            )

        matched_pairs = {(m.gold, m.extracted) for m in matches}

        for g_idx, g_flow in enumerate(gold_flows):
            for l_idx, l_flow in enumerate(extracted_flows):
                g_str = gold_strings[g_idx]
                l_str = extracted_strings[l_idx]

                if (g_str, l_str) in matched_pairs:
                    continue

                forward_score = float(similarity_matrix[g_idx][l_idx])

                g_parts = g_str.split("->")
                l_parts = l_str.split("->")

                if len(g_parts) == 2 and len(l_parts) == 2:
                    reversed_l = f"{l_parts[1]}->{l_parts[0]}"
                    reversed_g = f"{g_parts[1]}->{g_parts[0]}"

                    if reversed_g == l_str or g_str == reversed_l:
                        if forward_score < threshold:
                            if reversed_g == l_str and forward_score < threshold:
                                errors.append(
                                    {
                                        "gold": g_str,
                                        "extracted": l_str,
                                        "error_type": "reversed_direction",
                                        "reversed_score": "N/A",
                                    }
                                )

        return errors

    def _check_value_errors(
        self, gold_data: Dict[str, Any], extracted_data: Dict[str, Any]
    ) -> List[Dict[str, Any]]:
        """Check parameter values when both have numeric values."""
        errors = []

        gold_params = {p["symbol"]: p for p in gold_data.get("parameters", [])}
        extracted_params = {
            p["symbol"]: p for p in extracted_data.get("parameters", [])
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
        gold_data: Dict[str, Any],
        extracted_data: Dict[str, Any],
        threshold: Optional[float] = None,
    ) -> EvaluationReport:
        """
        Evaluate extracted model against gold standard.

        Args:
            gold_data: Gold standard model as canonical JSON
            extracted_data: LLM-extracted model as canonical JSON
            threshold: Similarity threshold (uses default if not provided)

        Returns:
            EvaluationReport with metrics and matches
        """
        threshold = threshold or self.threshold

        gold_compartments = self._prepare_compartment_strings(gold_data)
        extracted_compartments = self._prepare_compartment_strings(extracted_data)

        gold_flows = self._prepare_flow_strings(gold_data)
        extracted_flows = self._prepare_flow_strings(extracted_data)

        gold_params = self._prepare_parameter_strings(gold_data)
        extracted_params = self._prepare_parameter_strings(extracted_data)

        comp_matches, comp_unmatched_gold, comp_unmatched_extracted, _ = (
            self._match_strings(gold_compartments, extracted_compartments, threshold)
        )

        (
            flow_matches,
            flow_unmatched_gold,
            flow_unmatched_extracted,
            flow_similarity_matrix,
        ) = self._match_strings(gold_flows, extracted_flows, threshold)

        param_matches, param_unmatched_gold, param_unmatched_extracted, _ = (
            self._match_strings(gold_params, extracted_params, threshold)
        )

        comp_metrics = EvaluationMetrics(
            tp=len(comp_matches),
            fp=len(comp_unmatched_extracted),
            fn=len(comp_unmatched_gold),
        )

        flow_metrics = EvaluationMetrics(
            tp=len(flow_matches),
            fp=len(flow_unmatched_extracted),
            fn=len(flow_unmatched_gold),
        )

        param_metrics = EvaluationMetrics(
            tp=len(param_matches),
            fp=len(param_unmatched_extracted),
            fn=len(param_unmatched_gold),
        )

        direction_errors = self._check_flow_direction_errors(
            gold_data, extracted_data, flow_matches, threshold, flow_similarity_matrix
        )

        value_errors = self._check_value_errors(gold_data, extracted_data)

        composite_score = (
            COMPOSITE_WEIGHTS["compartments"] * comp_metrics.f1
            + COMPOSITE_WEIGHTS["flows"] * flow_metrics.f1
            + COMPOSITE_WEIGHTS["parameters"] * param_metrics.f1
        )

        return EvaluationReport(
            threshold=threshold,
            compartments=comp_metrics,
            flows=flow_metrics,
            parameters=param_metrics,
            compartment_matches=comp_matches,
            flow_matches=flow_matches,
            parameter_matches=param_matches,
            composite_score=composite_score,
            direction_errors=direction_errors,
            value_errors=value_errors,
        )


def evaluate(
    gold_json: Dict[str, Any],
    extracted_json: Dict[str, Any],
    threshold: float = DEFAULT_THRESHOLD,
) -> EvaluationReport:
    """
    Convenience function to evaluate two models.

    Args:
        gold_json: Gold standard model as canonical JSON
        extracted_json: LLM-extracted model as canonical JSON
        threshold: Similarity threshold (default 0.72)

    Returns:
        EvaluationReport with metrics and matches
    """
    evaluator = Evaluator(threshold=threshold)
    return evaluator.evaluate(gold_json, extracted_json, threshold)


def report_to_dict(report: EvaluationReport) -> Dict[str, Any]:
    """Convert EvaluationReport to dictionary for JSON serialization."""
    return {
        "threshold": report.threshold,
        "compartments": {
            "tp": report.compartments.tp,
            "fp": report.compartments.fp,
            "fn": report.compartments.fn,
            "precision": report.compartments.precision,
            "recall": report.compartments.recall,
            "f1": report.compartments.f1,
        },
        "flows": {
            "tp": report.flows.tp,
            "fp": report.flows.fp,
            "fn": report.flows.fn,
            "precision": report.flows.precision,
            "recall": report.flows.recall,
            "f1": report.flows.f1,
        },
        "parameters": {
            "tp": report.parameters.tp,
            "fp": report.parameters.fp,
            "fn": report.parameters.fn,
            "precision": report.parameters.precision,
            "recall": report.parameters.recall,
            "f1": report.parameters.f1,
        },
        "composite_score": report.composite_score,
        "compartment_matches": [asdict(m) for m in report.compartment_matches],
        "flow_matches": [asdict(m) for m in report.flow_matches],
        "parameter_matches": [asdict(m) for m in report.parameter_matches],
        "direction_errors": report.direction_errors,
        "value_errors": report.value_errors,
    }
