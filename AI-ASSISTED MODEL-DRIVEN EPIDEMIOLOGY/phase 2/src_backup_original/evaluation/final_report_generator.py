"""Generate final comprehensive report combining all Phase 2 results"""

import json
from pathlib import Path
from typing import Dict, Any


class FinalReportGenerator:
    """Generate a single comprehensive final report"""
    
    def generate_final_report(self, output_dir: Path, paper_name: str) -> Dict[str, Any]:
        """
        Generate final comprehensive report from all Phase 2 outputs.
        
        Args:
            output_dir: Directory containing all Phase 2 output files
            paper_name: Name of the paper/model
        
        Returns:
            Dictionary with complete report
        """
        output_dir = Path(output_dir)
        
        # Load all available reports
        reports = {}
        
        # Load paper promises
        if (output_dir / "paper_promises.json").exists():
            with open(output_dir / "paper_promises.json", 'r') as f:
                reports['paper_promises'] = json.load(f)
        
        # Load extracted entities
        if (output_dir / "extracted_entities.json").exists():
            with open(output_dir / "extracted_entities.json", 'r') as f:
                reports['extracted_entities'] = json.load(f)
        
        # Load traceability
        if (output_dir / "traceability.json").exists():
            with open(output_dir / "traceability.json", 'r') as f:
                reports['traceability'] = json.load(f)
        
        # Load gap analysis
        if (output_dir / "phase2_gap_report.json").exists():
            with open(output_dir / "phase2_gap_report.json", 'r') as f:
                reports['gap_analysis'] = json.load(f)
        
        # Load gap fill suggestions
        if (output_dir / "gap_fill_suggestions.json").exists():
            with open(output_dir / "gap_fill_suggestions.json", 'r') as f:
                reports['gap_suggestions'] = json.load(f)
        
        # Load evaluation
        if (output_dir / "evaluation_report.json").exists():
            with open(output_dir / "evaluation_report.json", 'r') as f:
                reports['evaluation'] = json.load(f)
        
        # Load quality checks
        if (output_dir / "quality_checks.json").exists():
            with open(output_dir / "quality_checks.json", 'r') as f:
                reports['quality_checks'] = json.load(f)
        
        # Compile final report
        final_report = {
            "paper_name": paper_name,
            "summary": self._generate_summary(reports),
            "paper_promises": reports.get('paper_promises', {}),
            "extracted_entities": self._summarize_entities(reports.get('extracted_entities', {})),
            "model_structure": {
                "compartments": len(reports.get('extracted_entities', {}).get('compartments', [])),
                "flows": len(reports.get('extracted_entities', {}).get('flows', [])),
                "parameters": len(reports.get('extracted_entities', {}).get('parameters', [])),
                "stratifications": len(reports.get('extracted_entities', {}).get('stratifications', [])),
                "interventions": len(reports.get('extracted_entities', {}).get('interventions', []))
            },
            "traceability": reports.get('traceability', {}).get('coverage_metrics', {}),
            "gap_analysis": self._summarize_gaps(reports.get('gap_analysis', {})),
            "gap_suggestions": reports.get('gap_suggestions', {}).get('summary', {}),
            "evaluation": reports.get('evaluation', {}),
            "quality_checks": reports.get('quality_checks', {}).get('status', {}),
            "files_generated": [
                "paper_text.json",
                "paper_sections.json",
                "paper_promises.json",
                "extracted_entities.json",
                "model_draft.compmodel",
                "traceability.json",
                "phase2_gap_report.json",
                "gap_fill_suggestions.json",
                "quality_checks.json",
                "evaluation_report.json"
            ]
        }
        
        return final_report
    
    def _generate_summary(self, reports: Dict[str, Any]) -> Dict[str, Any]:
        """Generate executive summary"""
        entities = reports.get('extracted_entities', {})
        extraction_summary = entities.get('extraction_summary', {})
        traceability = reports.get('traceability', {})
        gap_analysis = reports.get('gap_analysis', {})
        evaluation = reports.get('evaluation', {})
        
        coverage_metrics = traceability.get('coverage_metrics', {})
        gap_summary = gap_analysis.get('summary', {})
        eval_gap = evaluation.get('gap_analysis', {})
        
        return {
            "extraction": {
                "compartments": extraction_summary.get('num_compartments', 0),
                "flows": extraction_summary.get('num_flows', 0),
                "parameters": extraction_summary.get('num_parameters', 0),
                "stratifications": extraction_summary.get('num_stratifications', 0),
                "interventions": extraction_summary.get('num_interventions', 0)
            },
            "quality_metrics": {
                "traceability_coverage": coverage_metrics.get('coverage_percentage', 0),
                "faithfulness": coverage_metrics.get('faithfulness_percentage', 0),
                "total_gaps": gap_summary.get('total_gaps', 0),
                "critical_gaps": gap_summary.get('critical_gaps', 0),
                "high_gaps": gap_summary.get('high_gaps', 0),
                "medium_gaps": gap_summary.get('medium_gaps', 0)
            },
            "model_type": reports.get('paper_promises', {}).get('model_type', 'Unknown')
        }
    
    def _summarize_entities(self, entities: Dict[str, Any]) -> Dict[str, Any]:
        """Summarize extracted entities"""
        return {
            "compartments": [
                {
                    "name": c.get('normalized_name'),
                    "confidence": c.get('confidence'),
                    "method": c.get('extraction_method')
                }
                for c in entities.get('compartments', [])
            ],
            "flows": [
                {
                    "source": f.get('source'),
                    "target": f.get('target'),
                    "type": f.get('flow_type'),
                    "confidence": f.get('confidence'),
                    "method": f.get('extraction_method')
                }
                for f in entities.get('flows', [])
            ],
            "parameters": [
                {
                    "name": p.get('normalized_name'),
                    "value": p.get('value'),
                    "unit": p.get('unit'),
                    "confidence": p.get('confidence'),
                    "method": p.get('extraction_method')
                }
                for p in entities.get('parameters', [])
            ]
        }
    
    def _summarize_gaps(self, gap_analysis: Dict[str, Any]) -> Dict[str, Any]:
        """Summarize gap analysis"""
        return {
            "summary": gap_analysis.get('summary', {}),
            "missing_compartments": gap_analysis.get('missing_compartments', []),
            "missing_parameters": gap_analysis.get('missing_parameters', []),
            "missing_stratifications": gap_analysis.get('missing_stratifications', []),
            "missing_interventions": gap_analysis.get('missing_interventions', [])
        }
    
    def save_final_report(self, report: Dict[str, Any], output_path: Path):
        """Save final report to JSON"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        
        with open(output_path, 'w') as f:
            json.dump(report, f, indent=2)
