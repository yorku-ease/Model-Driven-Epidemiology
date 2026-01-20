"""Gap analysis: Compare paper promises vs extracted model

Identifies missing elements that the paper promises but weren't extracted.
"""

import json
from pathlib import Path
from typing import Dict, List, Any, Optional
import xml.etree.ElementTree as ET


class GapAnalyzer:
    """Analyze gaps between paper promises and extracted model"""
    
    def __init__(self):
        pass
    
    def load_paper_promises(self, promises_path: str) -> Dict[str, Any]:
        """Load paper promises from JSON"""
        with open(promises_path, 'r') as f:
            return json.load(f)
    
    def load_extracted_entities(self, entities_path: str) -> Dict[str, Any]:
        """Load extracted entities from JSON"""
        with open(entities_path, 'r') as f:
            return json.load(f)
    
    def load_model_structure(self, compmodel_path: str) -> Dict[str, Any]:
        """Load model structure from .compmodel XML"""
        try:
            tree = ET.parse(compmodel_path)
            root = tree.getroot()
            
            # Extract compartments
            compartments = []
            for comp in root.findall('.//compartments'):
                comp_name = comp.get('PrimaryName', '')
                if comp_name:
                    compartments.append(comp_name)
            
            # Extract parameters
            parameters = []
            for param in root.findall('.//parameters'):
                param_name = param.get('name', '')
                if param_name and param_name.lower() not in ['none', 'n/a', '']:
                    parameters.append(param_name)
            
            # Extract stratifications (groups)
            stratifications = []
            for group in root.findall('.//groups'):
                group_name = group.get('name', '')
                if group_name:
                    stratifications.append(group_name)
            
            return {
                "compartments": compartments,
                "parameters": parameters,
                "stratifications": stratifications
            }
        except Exception as e:
            print(f"Warning: Failed to parse .compmodel: {e}")
            return {"compartments": [], "parameters": [], "stratifications": []}
    
    def analyze_gaps(self, promises: Dict[str, Any], 
                    entities: Dict[str, Any],
                    model_structure: Dict[str, Any]) -> Dict[str, Any]:
        """
        Analyze gaps between promises and extracted model.
        
        Returns:
            Dictionary with gap analysis results
        """
        gaps = {
            "missing_compartments": [],
            "missing_parameters": [],
            "missing_stratifications": [],
            "missing_interventions": [],
            "summary": {}
        }
        
        # Get promised items
        promised_compartments = [c.lower() for c in promises.get('compartments', [])]
        promised_parameters = [p.lower() for p in promises.get('parameters', [])]
        promised_stratifications = [s.lower() for s in promises.get('stratifications', [])]
        promised_interventions = [i.lower() for i in promises.get('interventions', [])]
        
        # Get extracted items
        extracted_compartments = [c['normalized_name'].lower() for c in entities.get('compartments', [])]
        extracted_parameters = [p['normalized_name'].lower() for p in entities.get('parameters', [])]
        extracted_stratifications = [s['dimension'].lower() for s in entities.get('stratifications', [])]
        extracted_interventions = [i['type'].lower() for i in entities.get('interventions', [])]
        
        # Find missing compartments
        for promised_comp in promised_compartments:
            # Check if any extracted compartment matches (fuzzy matching)
            found = False
            for extracted_comp in extracted_compartments:
                if promised_comp in extracted_comp or extracted_comp in promised_comp:
                    found = True
                    break
                # Check for common synonyms
                synonyms = {
                    'exposed': ['non-symptomatic', 'latent', 'incubating'],
                    'infectious': ['infected', 'symptomatic'],
                    'recovered': ['removed', 'immune'],
                    'dead': ['deceased', 'death']
                }
                for key, values in synonyms.items():
                    if (promised_comp in key or key in promised_comp) and \
                       any(v in extracted_comp for v in values):
                        found = True
                        break
                if found:
                    break
            
            if not found:
                gaps["missing_compartments"].append({
                    "promised": promised_comp,
                    "severity": "critical",
                    "reason": "Promised in paper but not found in extracted model"
                })
        
        # Find missing parameters
        for promised_param in promised_parameters:
            found = False
            for extracted_param in extracted_parameters:
                if promised_param in extracted_param or extracted_param in promised_param:
                    found = True
                    break
            
            if not found:
                gaps["missing_parameters"].append({
                    "promised": promised_param,
                    "severity": "high",
                    "reason": "Promised in paper but not found in extracted model"
                })
        
        # Find missing stratifications
        for promised_strat in promised_stratifications:
            found = False
            for extracted_strat in extracted_stratifications:
                if promised_strat in extracted_strat or extracted_strat in promised_strat:
                    found = True
                    break
            
            if not found:
                gaps["missing_stratifications"].append({
                    "promised": promised_strat,
                    "severity": "critical",
                    "reason": "Promised in paper but not found in extracted model"
                })
        
        # Find missing interventions
        for promised_interv in promised_interventions:
            found = False
            for extracted_interv in extracted_interventions:
                if promised_interv in extracted_interv or extracted_interv in promised_interv:
                    found = True
                    break
            
            if not found:
                gaps["missing_interventions"].append({
                    "promised": promised_interv,
                    "severity": "medium",
                    "reason": "Promised in paper but not found in extracted model"
                })
        
        # Calculate summary
        total_gaps = (
            len(gaps["missing_compartments"]) +
            len(gaps["missing_parameters"]) +
            len(gaps["missing_stratifications"]) +
            len(gaps["missing_interventions"])
        )
        
        gaps["summary"] = {
            "total_gaps": total_gaps,
            "critical_gaps": len(gaps["missing_compartments"]) + len(gaps["missing_stratifications"]),
            "high_gaps": len(gaps["missing_parameters"]),
            "medium_gaps": len(gaps["missing_interventions"]),
            "promised_items": {
                "compartments": len(promised_compartments),
                "parameters": len(promised_parameters),
                "stratifications": len(promised_stratifications),
                "interventions": len(promised_interventions)
            },
            "extracted_items": {
                "compartments": len(extracted_compartments),
                "parameters": len(extracted_parameters),
                "stratifications": len(extracted_stratifications),
                "interventions": len(extracted_interventions)
            }
        }
        
        return gaps
    
    def save_gap_report(self, gaps: Dict[str, Any], output_path: str):
        """Save gap analysis report to JSON"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        
        with open(output_path, 'w') as f:
            json.dump(gaps, f, indent=2)
