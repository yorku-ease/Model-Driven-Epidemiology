"""
Task 2.1: Deep Dive into Model Gaps

Analyze what's missing in models compared to paper promises and literature.
Phase 2-ready: accepts paper promises and performs generic gap analysis.
Supports both Phase 1 (disease-specific rules) and Phase 2 (paper-driven) modes.
"""
import json
import os
from pathlib import Path
from typing import Dict, List, Any, Optional, Set
import sys
from datetime import datetime

sys.path.insert(0, str(Path(__file__).parent.parent / 'utils'))
from xml_parser import CompModelParser

# Import paper promise extractor (optional - only needed for Phase 2)
try:
    from paper_promise_extractor import PaperPromiseExtractor
    HAS_PAPER_EXTRACTOR = True
except ImportError:
    HAS_PAPER_EXTRACTOR = False


class PaperPromises:
    """
    Represents what a paper promises to model.
    This will be extracted from papers in Phase 2.
    """
    
    def __init__(
        self,
        compartments: Optional[List[str]] = None,
        stratifications: Optional[List[str]] = None,
        parameters: Optional[List[str]] = None,
        interventions: Optional[List[str]] = None,
        model_type: Optional[str] = None,
        description: Optional[str] = None
    ):
        """
        Initialize paper promises.
        
        Args:
            compartments: List of compartment names/types promised (e.g., ['Asymptomatic', 'Severe'])
            stratifications: List of stratification dimensions (e.g., ['age', 'risk_group'])
            parameters: List of parameter names promised (e.g., ['transmission_rate', 'recovery_rate'])
            interventions: List of interventions mentioned (e.g., ['vaccination', 'treatment'])
            model_type: Type of model (e.g., 'SEIR', 'SIR', 'Vector-Borne')
            description: Free-text description of what the paper promises
        """
        self.compartments = set(compartments or [])
        self.stratifications = set(stratifications or [])
        self.parameters = set(parameters or [])
        self.interventions = set(interventions or [])
        self.model_type = model_type
        self.description = description
    
    def to_dict(self) -> Dict[str, Any]:
        """Convert to dictionary for JSON serialization"""
        return {
            'compartments': list(self.compartments),
            'stratifications': list(self.stratifications),
            'parameters': list(self.parameters),
            'interventions': list(self.interventions),
            'model_type': self.model_type,
            'description': self.description
        }
    
    @classmethod
    def from_dict(cls, data: Dict[str, Any]) -> 'PaperPromises':
        """Create from dictionary"""
        return cls(
            compartments=data.get('compartments', []),
            stratifications=data.get('stratifications', []),
            parameters=data.get('parameters', []),
            interventions=data.get('interventions', []),
            model_type=data.get('model_type'),
            description=data.get('description')
        )


class GapRule:
    """
    A rule for detecting gaps. Can be required or optional.
    """
    
    def __init__(
        self,
        gap_type: str,  # 'compartment', 'stratification', 'parameter', 'intervention'
        name: str,
        description: str,
        severity: str = 'medium',  # 'critical', 'medium', 'low'
        required: bool = False,  # True if paper must have this
        keywords: Optional[List[str]] = None,  # Keywords to check for in model
        condition: Optional[callable] = None  # Custom function to check
    ):
        self.gap_type = gap_type
        self.name = name
        self.description = description
        self.severity = severity
        self.required = required
        self.keywords = keywords or [name.lower()]
        self.condition = condition


class GapAnalyzer:
    """Analyze gaps in models compared to paper promises and best practices"""
    
    def __init__(self, model_path: str, model_name: str, paper_promises: Optional[PaperPromises] = None, 
                 use_model_reports: bool = True):
        """
        Initialize gap analyzer.
        
        Args:
            model_path: Path to .compmodel file
            model_name: Name of the model
            paper_promises: What the paper promises (None for Phase 1, will be extracted in Phase 2)
            use_model_reports: If True, try to use Phase 1 model analysis reports if available
        """
        self.model_path = Path(model_path)
        self.model_name = model_name
        self.paper_promises = paper_promises
        self.use_model_reports = use_model_reports
        self.data = self._load_model_data()
        self.gaps = []
    
    def _load_model_data(self) -> Dict[str, Any]:
        """
        Load model data from Phase 1 reports if available, otherwise parse XML.
        Ensures consistency with Phase 1 analysis and avoids re-parsing.
        """
        if self.use_model_reports:
            # Try to load from Phase 1 model analysis report
            reports_dir = Path(__file__).parent.parent / 'reports' / 'model_analysis'
            report_filename = f"{self.model_name.lower().replace('-', '_')}_analysis.json"
            report_path = reports_dir / report_filename
            
            if report_path.exists():
                try:
                    with open(report_path, 'r', encoding='utf-8') as f:
                        report = json.load(f)
                    # Use the data from the report (same structure as parsed data)
                    if 'data' in report:
                        return report['data']
                    elif 'summary' in report and 'data' not in report:
                        # Old format - just return what we can extract
                        pass
                except Exception as e:
                    print(f"Warning: Could not load model report from {report_path}: {e}")
                    print("Falling back to XML parsing...")
        
        # Fall back to parsing XML directly
        parser = CompModelParser(str(self.model_path))
        return parser.extract_all()
    
    def _get_compartment_names(self) -> Set[str]:
        """Get all compartment names (normalized, lowercase)"""
        return {c['primaryName'].lower() for c in self.data['compartments']}
    
    def _get_stratification_names(self) -> Set[str]:
        """Get all stratification group names (normalized, lowercase)"""
        return {g['name'].lower() for g in self.data['groups']}
    
    def _get_parameter_names(self) -> Set[str]:
        """Get all parameter names (normalized, lowercase)"""
        return {p['name'].lower() for p in self.data['parameters']}
    
    def _check_compartment_exists(self, keywords: List[str]) -> bool:
        """Check if any compartment matches keywords"""
        compartments = self._get_compartment_names()
        for keyword in keywords:
            if any(keyword in comp for comp in compartments):
                return True
        return False
    
    def _check_stratification_exists(self, keywords: List[str]) -> bool:
        """Check if any stratification matches keywords"""
        stratifications = self._get_stratification_names()
        for keyword in keywords:
            if any(keyword in strat for strat in stratifications):
                return True
        return False
    
    def _check_parameter_exists(self, keywords: List[str]) -> bool:
        """Check if any parameter matches keywords"""
        parameters = self._get_parameter_names()
        for keyword in keywords:
            if any(keyword in param for param in parameters):
                return True
        return False
    
    def analyze_gaps_generic(self, promises: PaperPromises) -> Dict[str, Any]:
        """
        Generic gap analysis comparing model to paper promises.
        This is the Phase 2-ready method.
        """
        gaps = {
            'structuralGaps': [],
            'parameterGaps': [],
            'stratificationGaps': [],
            'interventionGaps': []
        }
        
        compartments = self._get_compartment_names()
        stratifications = self._get_stratification_names()
        parameters = self._get_parameter_names()
        
        # Check for promised compartments
        for promised_comp in promises.compartments:
            if not any(promised_comp.lower() in comp or comp in promised_comp.lower() 
                      for comp in compartments):
                gaps['structuralGaps'].append({
                    'gap': f'Missing Promised Compartment: {promised_comp}',
                    'description': f'Paper promises {promised_comp} compartment but model does not include it',
                    'promisedByPaper': True,
                    'severity': 'high',
                    'howToAdd': f'Add compartment: {promised_comp}'
                })
        
        # Check for promised stratifications
        for promised_strat in promises.stratifications:
            if not any(promised_strat.lower() in strat or strat in promised_strat.lower() 
                      for strat in stratifications):
                gaps['stratificationGaps'].append({
                    'gap': f'Missing Promised Stratification: {promised_strat}',
                    'description': f'Paper promises {promised_strat} stratification but model does not include it',
                    'promisedByPaper': True,
                    'severity': 'high',
                    'howToAdd': f'Add stratification dimension: {promised_strat}'
                })
        
        # Check for promised parameters
        for promised_param in promises.parameters:
            if not any(promised_param.lower() in param or param in promised_param.lower() 
                      for param in parameters):
                gaps['parameterGaps'].append({
                    'gap': f'Missing Promised Parameter: {promised_param}',
                    'description': f'Paper promises {promised_param} parameter but model does not include it',
                    'promisedByPaper': True,
                    'severity': 'high',
                    'suggestedAction': f'Extract {promised_param} from flows or add as explicit parameter'
                })
        
        # Check for promised interventions
        for promised_intervention in promises.interventions:
            # Check both compartments and parameters for interventions
            comp_match = any(promised_intervention.lower() in comp for comp in compartments)
            param_match = any(promised_intervention.lower() in param for param in parameters)
            if not comp_match and not param_match:
                gaps['interventionGaps'].append({
                    'gap': f'Missing Promised Intervention: {promised_intervention}',
                    'description': f'Paper promises {promised_intervention} intervention but model does not include it',
                    'promisedByPaper': True,
                    'severity': 'medium',
                    'howToAdd': f'Add intervention: {promised_intervention}'
                })
        
        return gaps
    
    def analyze_gaps_with_rules(self, rules: List[GapRule]) -> Dict[str, Any]:
        """
        Analyze gaps using configurable rules.
        Rules can be loaded from JSON or defined programmatically.
        """
        gaps = {
            'structuralGaps': [],
            'parameterGaps': [],
            'stratificationGaps': [],
            'interventionGaps': []
        }
        
        for rule in rules:
            # Check if rule condition is met
            found = False
            
            # If condition provided, use it directly (condition returns True if gap exists)
            if rule.condition:
                found = not rule.condition(self.data)  # Invert: condition True means gap exists
            else:
                # Otherwise use keyword matching
                if rule.gap_type == 'compartment':
                    found = self._check_compartment_exists(rule.keywords)
                elif rule.gap_type == 'stratification':
                    found = self._check_stratification_exists(rule.keywords)
                elif rule.gap_type == 'parameter':
                    found = self._check_parameter_exists(rule.keywords)
                elif rule.gap_type == 'intervention':
                    found = (self._check_compartment_exists(rule.keywords) or 
                            self._check_parameter_exists(rule.keywords))
            
            # If not found (gap exists), add it
            if not found:
                gap_entry = {
                    'gap': rule.name,
                    'description': rule.description,
                    'severity': rule.severity,
                    'promisedByPaper': False  # Will be True if from paper promises
                }
                
                if rule.gap_type == 'compartment':
                    gaps['structuralGaps'].append(gap_entry)
                elif rule.gap_type == 'stratification':
                    gaps['stratificationGaps'].append(gap_entry)
                elif rule.gap_type == 'parameter':
                    gaps['parameterGaps'].append(gap_entry)
                elif rule.gap_type == 'intervention':
                    gaps['interventionGaps'].append(gap_entry)
        
        return gaps
    
    def _load_disease_specific_rules(self) -> List[GapRule]:
        """
        Load disease-specific rules for Phase 1 compatibility.
        In Phase 2, rules should come from paper analysis or config files.
        """
        model_lower = self.model_name.lower()
        rules = []
        
        if 'malaria' in model_lower:
            rules.extend([
                GapRule('compartment', 'Asymptomatic Carriers', 
                       'Asymptomatic individuals who contribute to transmission',
                       severity='medium', keywords=['asymptomatic', 'ah']),
                GapRule('stratification', 'Age Groups',
                       'Age stratification for children <5 years',
                       severity='high', keywords=['age']),
                GapRule('parameter', 'Asymptomatic Proportion',
                       'Parameter for proportion of infections that are asymptomatic',
                       severity='medium', keywords=['asymptomatic', 'p_asymp']),
            ])
        elif 'covid' in model_lower or 'covid-19' in model_lower:
            rules.extend([
                GapRule('parameter', 'Explicit Parameters',
                       'Rates should be defined as explicit parameters, not hardcoded in flows',
                       severity='medium', 
                       condition=lambda d: len(d['parameters']) == 0),
            ])
        elif 'hiv' in model_lower:
            rules.extend([
                GapRule('parameter', 'Explicit Parameters',
                       'Rates should be defined as explicit parameters, not hardcoded in flows',
                       severity='medium',
                       condition=lambda d: len(d['parameters']) == 0),
                GapRule('intervention', 'PrEP Compartment',
                       'PrEP intervention compartment',
                       severity='low', keywords=['prep', 'pre-exposure']),
            ])
        
        return rules
    
    def analyze_gaps(self) -> Dict[str, Any]:
        """
        Main gap analysis method.
        - If paper_promises provided: use generic comparison (Phase 2) - ONLY paper promises, no general rules
        - Otherwise: use disease-specific rules (Phase 1)
        
        Phase 2 is faithful to paper: only flags gaps that paper promises, doesn't add literature-based components.
        """
        if self.paper_promises:
            # Phase 2: Compare to paper promises ONLY (faithful to paper)
            # Don't add general rules - only flag what paper actually promises
            gaps = self.analyze_gaps_generic(self.paper_promises)
        else:
            # Phase 1: Use disease-specific rules (literature-based expectations)
            rules = self._load_disease_specific_rules()
            gaps = self.analyze_gaps_with_rules(rules)
        
        return gaps
    
    def generate_gap_report(self) -> Dict[str, Any]:
        """Generate comprehensive gap report"""
        gaps = self.analyze_gaps()
        
        report = {
            'modelName': self.model_name,
            'analysisDate': datetime.now().isoformat(),
            'paperPromises': self.paper_promises.to_dict() if self.paper_promises else None,
            'totalGaps': (
                len(gaps['structuralGaps']) +
                len(gaps['parameterGaps']) +
                len(gaps['stratificationGaps']) +
                len(gaps['interventionGaps'])
            ),
            'gaps': gaps,
            'summary': {
                'criticalGaps': len([g for g in gaps['structuralGaps'] + gaps['parameterGaps'] 
                                    if g.get('severity') == 'high']),
                'mediumGaps': len([g for g in gaps['structuralGaps'] + gaps['parameterGaps'] 
                                  if g.get('severity') == 'medium']),
                'lowGaps': len([g for g in gaps['structuralGaps'] + gaps['parameterGaps'] 
                               if g.get('severity') == 'low']),
                'promisedByPaper': len([g for g in gaps['structuralGaps'] + gaps['parameterGaps'] + 
                                       gaps['stratificationGaps'] + gaps['interventionGaps']
                                       if g.get('promisedByPaper', False)])
            }
        }
        
        return report
    
    def export_gap_report(self, output_path: str):
        """Export gap report to JSON"""
        report = self.generate_gap_report()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            json.dump(report, f, indent=2, ensure_ascii=False)


def analyze_gaps_with_paper(
    model_path: str,
    model_name: str,
    paper_path: Optional[str] = None,
    paper_text: Optional[str] = None,
    use_llm: bool = False,
    llm_api_key: Optional[str] = None,
    output_path: Optional[str] = None
) -> Dict[str, Any]:
    """
    Complete Phase 2 gap analysis workflow.
    Extracts promises from paper and compares model to those promises.
    
    Args:
        model_path: Path to .compmodel file
        model_name: Name of the model
        paper_path: Path to PDF file (optional)
        paper_text: Paper text as string (optional, if paper_path not provided)
        use_llm: Whether to use LLM for extraction
        llm_api_key: LLM API key if using LLM
        output_path: Output JSON path for gap report
    
    Returns:
        Gap analysis report dictionary
    """
    if not HAS_PAPER_EXTRACTOR:
        raise ImportError(
            "Paper promise extractor not available. "
            "This function requires paper_promise_extractor.py to be in the same directory."
        )
    
    # Step 1: Extract promises from paper
    promises = None
    if paper_path:
        print("=" * 80)
        print("STEP 1: EXTRACTING PROMISES FROM PAPER")
        print("=" * 80)
        extractor = PaperPromiseExtractor(use_llm=use_llm, llm_api_key=llm_api_key)
        promises = extractor.extract_from_pdf(paper_path)
        
        print(f"\n✓ Extracted promises:")
        print(f"  - Compartments: {list(promises.compartments)}")
        print(f"  - Stratifications: {list(promises.stratifications)}")
        print(f"  - Parameters: {list(promises.parameters)}")
        print(f"  - Interventions: {list(promises.interventions)}")
        print(f"  - Model Type: {promises.model_type}")
        
    elif paper_text:
        print("=" * 80)
        print("STEP 1: EXTRACTING PROMISES FROM TEXT")
        print("=" * 80)
        extractor = PaperPromiseExtractor(use_llm=use_llm, llm_api_key=llm_api_key)
        promises = extractor.extract_from_text(paper_text)
    else:
        print("Warning: No paper provided. Using disease-specific rules (Phase 1 mode).")
    
    # Step 2: Analyze gaps
    print("\n" + "=" * 80)
    print("STEP 2: ANALYZING GAPS")
    print("=" * 80)
    
    analyzer = GapAnalyzer(model_path, model_name, paper_promises=promises)
    report = analyzer.generate_gap_report()
    
    # Step 3: Print summary
    print(f"\n✓ Gap Analysis Complete")
    print(f"\nTotal gaps found: {report['totalGaps']}")
    print(f"  - Structural: {len(report['gaps']['structuralGaps'])}")
    print(f"  - Parameters: {len(report['gaps']['parameterGaps'])}")
    print(f"  - Stratification: {len(report['gaps']['stratificationGaps'])}")
    print(f"  - Interventions: {len(report['gaps']['interventionGaps'])}")
    print(f"\nBy severity:")
    print(f"  - Critical: {report['summary']['criticalGaps']}")
    print(f"  - Medium: {report['summary']['mediumGaps']}")
    print(f"  - Low: {report['summary']['lowGaps']}")
    
    if promises:
        print(f"  - Promised by paper: {report['summary']['promisedByPaper']}")
    
    # Step 4: Export report
    if output_path:
        analyzer.export_gap_report(output_path)
        print(f"\n✓ Report saved to: {output_path}")
    else:
        # Default output path
        output_dir = Path(__file__).parent.parent / 'reports' / 'gap_reports'
        output_dir.mkdir(parents=True, exist_ok=True)
        suffix = "_with_paper" if promises else ""
        output_file = output_dir / f"{model_name.lower().replace('-', '_')}{suffix}_gap_analysis.json"
        analyzer.export_gap_report(str(output_file))
        print(f"\n✓ Report saved to: {output_file}")
    
    return report


def main():
    """
    Main function supporting both Phase 1 and Phase 2 modes.
    
    Phase 1: Analyze all models with disease-specific rules (default)
    Phase 2: Analyze single model with paper promises (use --paper-pdf or --paper-text)
    """
    import argparse
    
    parser = argparse.ArgumentParser(
        description='Gap Analysis: Phase 1 (rules-based) or Phase 2 (paper-driven)',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  # Phase 1: Analyze all models with disease-specific rules
  python3 gap_analyzer.py

  # Phase 2: Analyze single model with paper PDF
  python3 gap_analyzer.py --model Compartmental/CompartmentalModel/covid.compmodel \\
                          --name "COVID-19" \\
                          --paper-pdf papers/epimde/covid.pdf

  # Phase 2: With LLM extraction
  python3 gap_analyzer.py --model model.compmodel --name "Model" \\
                          --paper-pdf paper.pdf --use-llm --api-key sk-...
        """
    )
    
    parser.add_argument('--model', help='Path to single .compmodel file (Phase 2 mode)')
    parser.add_argument('--name', help='Name of the model (required if --model specified)')
    parser.add_argument('--paper-pdf', help='Path to paper PDF file (Phase 2)')
    parser.add_argument('--paper-text', help='Paper text as string (Phase 2, for testing)')
    parser.add_argument('--use-llm', action='store_true', help='Use LLM for extraction (Phase 2)')
    parser.add_argument('--api-key', help='OpenAI API key (or set OPENAI_API_KEY env var)')
    parser.add_argument('--output', help='Output JSON file path')
    
    args = parser.parse_args()
    
    # Phase 2 mode: single model with paper
    if args.model or args.paper_pdf or args.paper_text:
        if not args.model or not args.name:
            parser.error("--model and --name are required for Phase 2 mode")
        
        if not HAS_PAPER_EXTRACTOR:
            print("Error: Paper promise extractor not available.")
            print("Ensure paper_promise_extractor.py is in the same directory.")
            sys.exit(1)
        
        # Get API key
        api_key = args.api_key or os.getenv('OPENAI_API_KEY')
        
        if args.use_llm and not api_key:
            print("Warning: --use-llm specified but no API key provided.")
            print("Set OPENAI_API_KEY environment variable or use --api-key")
            print("Falling back to pattern-based extraction...")
            args.use_llm = False
        
        # Run Phase 2 analysis
        analyze_gaps_with_paper(
            model_path=args.model,
            model_name=args.name,
            paper_path=args.paper_pdf,
            paper_text=args.paper_text,
            use_llm=args.use_llm,
            llm_api_key=api_key,
            output_path=args.output
        )
        
        return
    
    # Phase 1 mode: analyze all models with disease-specific rules
    base_path = Path(__file__).parent.parent.parent / 'Compartmental' / 'CompartmentalModel'
    output_dir = Path(__file__).parent.parent / 'reports' / 'gap_reports'
    output_dir.mkdir(parents=True, exist_ok=True)
    
    models = {
        'COVID-19': base_path / 'covid.compmodel',
        'Malaria': base_path / 'malaria.compmodel',
        'HIV': base_path / 'HIV.compmodel'
    }
    
    print("=" * 80)
    print("TASK 2.1: GAP ANALYSIS FOR ALL MODELS")
    print("Phase 1 Mode: Using disease-specific rules (no paper promises)")
    print("=" * 80)
    print("\nTo use Phase 2 mode (with paper), use:")
    print("  python3 gap_analyzer.py --model <model.compmodel> --name '<Model Name>' --paper-pdf <paper.pdf>")
    
    all_reports = {}
    
    for model_name, model_path in models.items():
        if not model_path.exists():
            print(f"\n⚠ Warning: {model_path} not found, skipping {model_name}")
            continue
        
        print(f"\n{'=' * 80}")
        print(f"Analyzing: {model_name}")
        print('=' * 80)
        
        try:
            # Phase 1: No paper promises, uses disease-specific rules
            analyzer = GapAnalyzer(str(model_path), model_name, paper_promises=None)
            
            # Export JSON
            json_filename = f"{model_name.lower().replace('-', '_')}_gap_analysis.json"
            json_path = output_dir / json_filename
            analyzer.export_gap_report(str(json_path))
            print(f"✓ Gap report exported to: {json_path}")
            
            report = analyzer.generate_gap_report()
            all_reports[model_name] = report
            
            print(f"\nTotal gaps found: {report['totalGaps']}")
            print(f"  - Structural: {len(report['gaps']['structuralGaps'])}")
            print(f"  - Parameters: {len(report['gaps']['parameterGaps'])}")
            print(f"  - Stratification: {len(report['gaps']['stratificationGaps'])}")
            print(f"  - Interventions: {len(report['gaps']['interventionGaps'])}")
            print(f"  - Critical: {report['summary']['criticalGaps']}")
            print(f"  - Medium: {report['summary']['mediumGaps']}")
            print(f"  - Low: {report['summary']['lowGaps']}")
            
        except Exception as e:
            print(f"✗ Error analyzing {model_name}: {e}")
            import traceback
            traceback.print_exc()
    
    # Generate summary
    summary = {
        'analysisDate': datetime.now().isoformat(),
        'modelsAnalyzed': list(all_reports.keys()),
        'summary': {
            'totalGaps': sum(r['totalGaps'] for r in all_reports.values()),
            'byModel': {name: r['totalGaps'] for name, r in all_reports.items()},
            'bySeverity': {
                'critical': sum(r['summary']['criticalGaps'] for r in all_reports.values()),
                'medium': sum(r['summary']['mediumGaps'] for r in all_reports.values()),
                'low': sum(r['summary']['lowGaps'] for r in all_reports.values())
            },
            'byType': {
                'structural': sum(len(r['gaps']['structuralGaps']) for r in all_reports.values()),
                'parameter': sum(len(r['gaps']['parameterGaps']) for r in all_reports.values()),
                'stratification': sum(len(r['gaps']['stratificationGaps']) for r in all_reports.values()),
                'intervention': sum(len(r['gaps']['interventionGaps']) for r in all_reports.values())
            }
        }
    }
    
    summary_path = output_dir / 'all_models_gap_summary.json'
    with open(summary_path, 'w', encoding='utf-8') as f:
        json.dump(summary, f, indent=2, ensure_ascii=False)
    
    print("\n" + "=" * 80)
    print("GAP ANALYSIS COMPLETE")
    print("=" * 80)
    print(f"\nSummary saved to: {summary_path}")
    print(f"\nTotal gaps across all models: {summary['summary']['totalGaps']}")
    print(f"  - Critical: {summary['summary']['bySeverity']['critical']}")
    print(f"  - Medium: {summary['summary']['bySeverity']['medium']}")
    print(f"  - Low: {summary['summary']['bySeverity']['low']}")


if __name__ == '__main__':
    main()
