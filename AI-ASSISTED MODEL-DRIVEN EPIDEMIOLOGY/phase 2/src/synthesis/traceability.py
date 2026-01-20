"""Create traceability mapping between model elements and paper evidence

Maps every model element to its source in the paper (text span, page, method).
"""

import json
from pathlib import Path
from typing import Dict, List, Any, Optional


class TraceabilityMapper:
    """Map model elements to paper evidence"""
    
    def create_traceability(self, entities: Dict[str, Any], 
                           model_xml_path: Optional[str] = None) -> Dict[str, Any]:
        """
        Create traceability mapping.
        
        Args:
            entities: Extracted entities with evidence
            model_xml_path: Optional path to generated .compmodel file
        
        Returns:
            Traceability mapping dictionary
        """
        traceability = {
            "compartments": {},
            "flows": {},
            "parameters": {},
            "stratifications": {},
            "interventions": {},
            "coverage_metrics": {}
        }
        
        # Map compartments
        for comp in entities.get('compartments', []):
            comp_name = comp.get('normalized_name', '')
            traceability['compartments'][comp_name] = {
                "evidence": [{
                    "text_span": comp.get('text_span', ''),
                    "page_number": comp.get('page_number', 0),
                    "method": comp.get('extraction_method', 'unknown'),
                    "confidence": comp.get('confidence', 'unknown')
                }],
                "paper_backed": comp.get('paper_backed', True)
            }
        
        # Map flows
        for flow in entities.get('flows', []):
            flow_key = f"{flow.get('source', '')} -> {flow.get('target', '')}"
            traceability['flows'][flow_key] = {
                "evidence": [{
                    "text_span": flow.get('text_span', ''),
                    "page_number": flow.get('page_number', 0),
                    "method": flow.get('extraction_method', 'unknown'),
                    "confidence": flow.get('confidence', 'unknown')
                }],
                "paper_backed": flow.get('paper_backed', True)
            }
        
        # Map parameters
        for param in entities.get('parameters', []):
            param_name = param.get('normalized_name', '')
            traceability['parameters'][param_name] = {
                "evidence": [{
                    "text_span": param.get('text_span', ''),
                    "page_number": param.get('page_number', 0),
                    "method": param.get('extraction_method', 'unknown'),
                    "confidence": param.get('confidence', 'unknown'),
                    "value": param.get('value'),
                    "unit": param.get('unit')
                }],
                "paper_backed": param.get('paper_backed', True)
            }
        
        # Map stratifications
        for strat in entities.get('stratifications', []):
            strat_dim = strat.get('dimension', '')
            traceability['stratifications'][strat_dim] = {
                "evidence": [{
                    "text_span": strat.get('text_span', ''),
                    "page_number": strat.get('page_number', 0),
                    "method": strat.get('extraction_method', 'unknown'),
                    "confidence": strat.get('confidence', 'unknown')
                }],
                "paper_backed": strat.get('paper_backed', True)
            }
        
        # Map interventions
        for interv in entities.get('interventions', []):
            interv_type = interv.get('type', '')
            traceability['interventions'][interv_type] = {
                "evidence": [{
                    "text_span": interv.get('text_span', ''),
                    "page_number": interv.get('page_number', 0),
                    "method": interv.get('extraction_method', 'unknown'),
                    "confidence": interv.get('confidence', 'unknown')
                }],
                "paper_backed": interv.get('paper_backed', True)
            }
        
        # Calculate coverage metrics
        total_items = (
            len(entities.get('compartments', [])) +
            len(entities.get('flows', [])) +
            len(entities.get('parameters', [])) +
            len(entities.get('stratifications', [])) +
            len(entities.get('interventions', []))
        )
        
        items_with_evidence = sum([
            len([c for c in entities.get('compartments', []) if c.get('text_span')]),
            len([f for f in entities.get('flows', []) if f.get('text_span')]),
            len([p for p in entities.get('parameters', []) if p.get('text_span')]),
            len([s for s in entities.get('stratifications', []) if s.get('text_span')]),
            len([i for i in entities.get('interventions', []) if i.get('text_span')])
        ])
        
        paper_backed_items = sum([
            len([c for c in entities.get('compartments', []) if c.get('paper_backed', False)]),
            len([f for f in entities.get('flows', []) if f.get('paper_backed', False)]),
            len([p for p in entities.get('parameters', []) if p.get('paper_backed', False)]),
            len([s for s in entities.get('stratifications', []) if s.get('paper_backed', False)]),
            len([i for i in entities.get('interventions', []) if i.get('paper_backed', False)])
        ])
        
        traceability['coverage_metrics'] = {
            "total_items": total_items,
            "items_with_evidence": items_with_evidence,
            "coverage_percentage": (items_with_evidence / total_items * 100) if total_items > 0 else 0,
            "paper_backed_items": paper_backed_items,
            "faithfulness_percentage": (paper_backed_items / total_items * 100) if total_items > 0 else 0
        }
        
        return traceability
    
    def save_traceability(self, traceability: Dict[str, Any], output_path: str):
        """Save traceability mapping to JSON file"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        
        with open(output_path, 'w') as f:
            json.dump(traceability, f, indent=2)
