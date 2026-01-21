"""Evaluation: Calculate metrics and scores

Calculates traceability coverage, faithfulness, gap metrics, and optionally
compares to gold standard for precision/recall.
"""

import json
import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Dict, List, Any, Optional


class Evaluator:
    """Evaluate extraction quality with metrics"""
    
    def __init__(self, gold_standard_path: Optional[str] = None):
        """
        Initialize evaluator.
        
        Args:
            gold_standard_path: Path to gold standard JSON or .compmodel file (optional)
        """
        self.gold_standard = None
        if gold_standard_path:
            self._load_gold_standard(gold_standard_path)
    
    def _load_gold_standard(self, gold_path: str):
        """Load gold standard for comparison (supports both JSON and .compmodel files)"""
        gold_path_obj = Path(gold_path)
        if not gold_path_obj.exists():
            print(f"Warning: Gold standard file not found: {gold_path}")
            return
        
        try:
            # Check if it's a .compmodel file
            if gold_path_obj.suffix == '.compmodel':
                self.gold_standard = self._convert_compmodel_to_gold_standard(gold_path)
            else:
                # Assume it's JSON
                with open(gold_path, 'r') as f:
                    self.gold_standard = json.load(f)
        except Exception as e:
            print(f"Warning: Failed to load gold standard: {e}")
    
    def _convert_compmodel_to_gold_standard(self, compmodel_path: str) -> Dict[str, Any]:
        """
        Convert .compmodel XML file to gold standard JSON format.
        
        Args:
            compmodel_path: Path to .compmodel file
            
        Returns:
            Dictionary in gold standard format
        """
        try:
            tree = ET.parse(compmodel_path)
            root = tree.getroot()
            
            # Extract compartments
            compartments = []
            for comp in root.findall('.//{http://example.com/compartmentalmodel}compartments'):
                comp_name = comp.get('PrimaryName', '')
                if comp_name:
                    compartments.append(comp_name)
            
            # Also try without namespace (for compatibility)
            if not compartments:
                for comp in root.findall('.//compartments'):
                    comp_name = comp.get('PrimaryName', '')
                    if comp_name:
                        compartments.append(comp_name)
            
            # Extract parameters
            parameters = []
            for param in root.findall('.//{http://example.com/compartmentalmodel}parameters'):
                param_name = param.get('name', '')
                if param_name and param_name.lower() not in ['none', 'n/a', '']:
                    parameters.append(param_name)
            
            # Also try without namespace (for compatibility)
            if not parameters:
                for param in root.findall('.//parameters'):
                    param_name = param.get('name', '')
                    if param_name and param_name.lower() not in ['none', 'n/a', '']:
                        parameters.append(param_name)
            
            return {
                "gold_entities": {
                    "compartments": compartments,
                    "parameters": parameters
                },
                "source": str(compmodel_path),
                "source_type": "compmodel"
            }
        except Exception as e:
            print(f"Warning: Failed to parse .compmodel file {compmodel_path}: {e}")
            return {"gold_entities": {"compartments": [], "parameters": []}}
    
    def evaluate(self, extracted_entities: Dict[str, Any],
                 traceability: Dict[str, Any],
                 gaps: Dict[str, Any]) -> Dict[str, Any]:
        """
        Evaluate extraction quality.
        
        Returns:
            Dictionary with evaluation metrics
        """
        metrics = {
            "traceability_coverage": self._calculate_traceability_coverage(traceability),
            "faithfulness": self._calculate_faithfulness(traceability),
            "gap_analysis": self._analyze_gaps(gaps),
            "gold_standard_comparison": None
        }
        
        # Gold standard comparison if available
        if self.gold_standard:
            metrics["gold_standard_comparison"] = self._compare_to_gold_standard(
                extracted_entities, self.gold_standard
            )
        
        return metrics
    
    def _calculate_traceability_coverage(self, traceability: Dict[str, Any]) -> Dict[str, Any]:
        """Calculate traceability coverage metrics"""
        metrics = traceability.get("coverage_metrics", {})
        return {
            "total_items": metrics.get("total_items", 0),
            "items_with_evidence": metrics.get("items_with_evidence", 0),
            "coverage_percentage": metrics.get("coverage_percentage", 0.0)
        }
    
    def _calculate_faithfulness(self, traceability: Dict[str, Any]) -> Dict[str, Any]:
        """Calculate faithfulness metrics"""
        metrics = traceability.get("coverage_metrics", {})
        return {
            "paper_backed_items": metrics.get("paper_backed_items", 0),
            "faithfulness_percentage": metrics.get("faithfulness_percentage", 0.0)
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
                "missing_interventions": len(gaps.get("missing_interventions", []))
            }
        }
    
    def _compare_to_gold_standard(self, extracted: Dict[str, Any], 
                                  gold: Dict[str, Any]) -> Dict[str, Any]:
        """Compare extracted entities to gold standard"""
        if not self.gold_standard:
            return None
        
        # Extract entity lists
        extracted_comps = {c['normalized_name'].lower() for c in extracted.get('compartments', [])}
        extracted_params = {p['normalized_name'].lower() for p in extracted.get('parameters', [])}
        
        gold_comps = {c.lower() for c in gold.get('gold_entities', {}).get('compartments', [])}
        gold_params = {p.lower() for p in gold.get('gold_entities', {}).get('parameters', [])}
        
        # Calculate precision and recall
        comp_tp = len(extracted_comps & gold_comps)
        comp_fp = len(extracted_comps - gold_comps)
        comp_fn = len(gold_comps - extracted_comps)
        
        param_tp = len(extracted_params & gold_params)
        param_fp = len(extracted_params - gold_params)
        param_fn = len(gold_params - extracted_params)
        
        comp_precision = comp_tp / (comp_tp + comp_fp) if (comp_tp + comp_fp) > 0 else 0
        comp_recall = comp_tp / (comp_tp + comp_fn) if (comp_tp + comp_fn) > 0 else 0
        
        param_precision = param_tp / (param_tp + param_fp) if (param_tp + param_fp) > 0 else 0
        param_recall = param_tp / (param_tp + param_fn) if (param_tp + param_fn) > 0 else 0
        
        return {
            "compartments": {
                "precision": comp_precision,
                "recall": comp_recall,
                "f1": 2 * comp_precision * comp_recall / (comp_precision + comp_recall) if (comp_precision + comp_recall) > 0 else 0,
                "tp": comp_tp,
                "fp": comp_fp,
                "fn": comp_fn
            },
            "parameters": {
                "precision": param_precision,
                "recall": param_recall,
                "f1": 2 * param_precision * param_recall / (param_precision + param_recall) if (param_precision + param_recall) > 0 else 0,
                "tp": param_tp,
                "fp": param_fp,
                "fn": param_fn
            }
        }
    
    def save_evaluation(self, evaluation: Dict[str, Any], output_path: str):
        """Save evaluation results to JSON"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        
        with open(output_path, 'w') as f:
            json.dump(evaluation, f, indent=2)
