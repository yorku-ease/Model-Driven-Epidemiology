"""Gap filler: Suggest how to fill gaps using paper, prior models, and domain knowledge

Generates suggestions for filling gaps from:
1. Paper text re-examination
2. Prior models (Phase 1 analysis)
3. Domain knowledge (LLM)
"""

import json
from pathlib import Path
from typing import Dict, List, Any, Optional
from src.utils.llm_client import LLMClient


class GapFiller:
    """Suggest gap fills from paper, prior models, and domain knowledge"""
    
    def __init__(self, llm_client: Optional[LLMClient] = None, 
                 prior_models_dir: Optional[str] = None):
        """
        Initialize gap filler.
        
        Args:
            llm_client: LLM client for domain knowledge suggestions
            prior_models_dir: Directory containing Phase 1 model analysis JSONs
        """
        self.llm_client = llm_client or LLMClient()
        self.prior_models = []
        if prior_models_dir:
            self._load_prior_models(prior_models_dir)
    
    def _load_prior_models(self, prior_models_dir: str):
        """Load prior model analysis JSONs from Phase 1"""
        prior_dir = Path(prior_models_dir)
        if not prior_dir.exists():
            return
        
        # Look for model analysis JSONs
        for json_file in prior_dir.glob("*_analysis.json"):
            try:
                with open(json_file, 'r') as f:
                    model_data = json.load(f)
                    self.prior_models.append({
                        "name": json_file.stem.replace("_analysis", ""),
                        "data": model_data
                    })
            except Exception as e:
                print(f"Warning: Failed to load prior model {json_file}: {e}")
    
    def fill_gaps(self, gaps: Dict[str, Any], paper_text: str,
                  extracted_entities: Dict[str, Any]) -> Dict[str, Any]:
        """
        Generate gap fill suggestions.
        
        Args:
            gaps: Gap analysis results
            paper_text: Full paper text for re-examination
            extracted_entities: Extracted entities for context
        
        Returns:
            Dictionary with gap fill suggestions
        """
        suggestions = {
            "gaps": [],
            "summary": {}
        }
        
        # Process each gap type
        for gap_type in ["missing_compartments", "missing_parameters", 
                        "missing_stratifications", "missing_interventions"]:
            for gap in gaps.get(gap_type, []):
                gap_suggestions = self._suggest_for_gap(
                    gap, gap_type, paper_text, extracted_entities
                )
                suggestions["gaps"].append({
                    "gap": gap,
                    "gap_type": gap_type,
                    "suggestions": gap_suggestions
                })
        
        # Calculate summary
        total_suggestions = sum(len(g["suggestions"]) for g in suggestions["gaps"])
        suggestions["summary"] = {
            "total_gaps": len(suggestions["gaps"]),
            "total_suggestions": total_suggestions,
            "suggestions_by_source": self._count_by_source(suggestions["gaps"])
        }
        
        return suggestions
    
    def _suggest_for_gap(self, gap: Dict[str, Any], gap_type: str,
                         paper_text: str, extracted_entities: Dict[str, Any]) -> List[Dict[str, Any]]:
        """Generate suggestions for a single gap"""
        suggestions = []
        promised_item = gap.get("promised", "")
        
        # Strategy 1: Re-search paper for weak signals
        paper_suggestions = self._search_paper(paper_text, promised_item, gap_type)
        suggestions.extend(paper_suggestions)
        
        # Strategy 2: Search prior models
        prior_suggestions = self._search_prior_models(promised_item, gap_type)
        suggestions.extend(prior_suggestions)
        
        # Strategy 3: Domain knowledge (LLM)
        if self.llm_client.is_available():
            domain_suggestions = self._use_domain_knowledge(promised_item, gap_type)
            suggestions.extend(domain_suggestions)
        
        return suggestions
    
    def _search_paper(self, paper_text: str, item: str, gap_type: str) -> List[Dict[str, Any]]:
        """Re-search paper for weak signals about the missing item"""
        suggestions = []
        
        # Simple text search for the item
        item_lower = item.lower()
        text_lower = paper_text.lower()
        
        # Look for mentions of the item
        if item_lower in text_lower:
            # Find context around mentions
            import re
            matches = list(re.finditer(re.escape(item_lower), text_lower))
            if matches:
                # Get context from first match
                match = matches[0]
                start = max(0, match.start() - 200)
                end = min(len(paper_text), match.end() + 200)
                context = paper_text[start:end]
                
                suggestions.append({
                    "proposed_element": self._create_element_suggestion(item, gap_type),
                    "source": "paper_span",
                    "source_text": context,
                    "page_number": 0,  # Would need page mapping
                    "confidence": "medium",
                    "rationale": f"Item '{item}' mentioned in paper text but not explicitly extracted"
                })
        
        return suggestions
    
    def _search_prior_models(self, item: str, gap_type: str) -> List[Dict[str, Any]]:
        """Search prior models for how they handle this gap"""
        suggestions = []
        
        for prior_model in self.prior_models:
            model_data = prior_model["data"]
            model_name = prior_model["name"]
            
            # Handle case where model_data might be a list
            if isinstance(model_data, list):
                # If it's a list, it might be a list of compartments/parameters
                # Try to extract from first item structure
                if model_data and isinstance(model_data[0], dict):
                    # Check structure of first item
                    if "Name" in model_data[0] or "PrimaryName" in model_data[0]:
                        # It's a list of compartments
                        if gap_type == "missing_compartments":
                            for comp in model_data:
                                comp_name = (comp.get("Name") or comp.get("PrimaryName") or "").lower()
                                if item.lower() in comp_name or comp_name in item.lower():
                                    suggestions.append({
                                        "proposed_element": self._create_element_suggestion(item, gap_type),
                                        "source": "prior_model",
                                        "source_model": model_name,
                                        "confidence": "high",
                                        "rationale": f"Prior model '{model_name}' includes similar compartment"
                                    })
                                    break
                    elif "name" in model_data[0]:
                        # It's a list of parameters
                        if gap_type == "missing_parameters":
                            for param in model_data:
                                param_name = param.get("name", "").lower()
                                if item.lower() in param_name or param_name in item.lower():
                                    param_value = param.get("expression", param.get("value", "N/A"))
                                    suggestions.append({
                                        "proposed_element": self._create_element_suggestion(item, gap_type, param_value),
                                        "source": "prior_model",
                                        "source_model": model_name,
                                        "confidence": "high",
                                        "rationale": f"Prior model '{model_name}' includes parameter '{param_name}' with value {param_value}"
                                    })
                                    break
                continue
            
            # Handle case where model_data is a dict
            if not isinstance(model_data, dict):
                continue
            
            # Search in model data based on gap type
            if gap_type == "missing_compartments":
                compartments = model_data.get("compartments", [])
                if isinstance(compartments, list):
                    for comp in compartments:
                        if isinstance(comp, dict):
                            comp_name = (comp.get("Name") or comp.get("PrimaryName") or "").lower()
                            if item.lower() in comp_name or comp_name in item.lower():
                                suggestions.append({
                                    "proposed_element": self._create_element_suggestion(item, gap_type),
                                    "source": "prior_model",
                                    "source_model": model_name,
                                    "confidence": "high",
                                    "rationale": f"Prior model '{model_name}' includes similar compartment"
                                })
                                break
            
            elif gap_type == "missing_parameters":
                parameters = model_data.get("parameters", [])
                if isinstance(parameters, list):
                    for param in parameters:
                        if isinstance(param, dict):
                            param_name = param.get("name", "").lower()
                            if item.lower() in param_name or param_name in item.lower():
                                param_value = param.get("expression", param.get("value", "N/A"))
                                suggestions.append({
                                    "proposed_element": self._create_element_suggestion(item, gap_type, param_value),
                                    "source": "prior_model",
                                    "source_model": model_name,
                                    "confidence": "high",
                                    "rationale": f"Prior model '{model_name}' includes parameter '{param_name}' with value {param_value}"
                                })
                                break
        
        return suggestions
    
    def _use_domain_knowledge(self, item: str, gap_type: str) -> List[Dict[str, Any]]:
        """Use LLM for domain knowledge suggestions"""
        suggestions = []
        
        if gap_type == "missing_compartments":
            prompt = f"""Suggest a compartmental model compartment for: {item}

Return a JSON object with:
- "name": suggested compartment name
- "description": brief description
- "population": suggested initial population (default 0)

Return ONLY valid JSON."""
        
        elif gap_type == "missing_parameters":
            prompt = f"""Suggest a parameter for epidemiological modeling: {item}

Return a JSON object with:
- "name": suggested parameter name
- "value": suggested default value or range
- "unit": suggested unit
- "description": brief description

Return ONLY valid JSON."""
        
        else:
            return suggestions  # Not implemented for other types
        
        try:
            result = self.llm_client.extract_with_llm(prompt)
            if isinstance(result, dict):
                suggestions.append({
                    "proposed_element": result,
                    "source": "domain_knowledge",
                    "confidence": "low",
                    "rationale": f"Domain knowledge suggestion for '{item}'"
                })
        except Exception as e:
            print(f"Warning: Domain knowledge suggestion failed: {e}")
        
        return suggestions
    
    def _create_element_suggestion(self, item: str, gap_type: str, value: Any = None) -> Dict[str, Any]:
        """Create a suggested element structure"""
        if gap_type == "missing_compartments":
            return {
                "type": "compartment",
                "PrimaryName": item.title(),
                "population": 0
            }
        elif gap_type == "missing_parameters":
            return {
                "type": "parameter",
                "name": item,
                "value": value or "0.0",
                "unit": None,
                "description": f"Parameter: {item}"
            }
        elif gap_type == "missing_stratifications":
            return {
                "type": "stratification",
                "dimension": item
            }
        elif gap_type == "missing_interventions":
            return {
                "type": "intervention",
                "name": item
            }
        return {}
    
    def _count_by_source(self, gaps_with_suggestions: List[Dict]) -> Dict[str, int]:
        """Count suggestions by source"""
        counts = {"paper_span": 0, "prior_model": 0, "domain_knowledge": 0}
        for gap_data in gaps_with_suggestions:
            for suggestion in gap_data.get("suggestions", []):
                source = suggestion.get("source", "")
                if source in counts:
                    counts[source] += 1
        return counts
    
    def save_suggestions(self, suggestions: Dict[str, Any], output_path: str):
        """Save gap fill suggestions to JSON"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        
        with open(output_path, 'w') as f:
            json.dump(suggestions, f, indent=2)
