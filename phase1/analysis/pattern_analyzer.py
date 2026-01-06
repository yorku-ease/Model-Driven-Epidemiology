"""
Task 3.3: Analyze Papers for Data Flow Patterns

Read through papers and identify common patterns:
- Standard SEIR
- SEIR with Hospitalization
- Vector-Borne (Malaria)
"""
import json
from pathlib import Path
from typing import Dict, List, Any
import sys

sys.path.insert(0, str(Path(__file__).parent.parent / 'utils'))
from xml_parser import CompModelParser


class PatternAnalyzer:
    """Analyze models for common data flow patterns"""
    
    def __init__(self):
        self.patterns = []
    
    def analyze_model_patterns(self, model_path: str, model_name: str) -> Dict[str, Any]:
        """Analyze a model for patterns"""
        parser = CompModelParser(str(model_path))
        data = parser.extract_all()
        
        compartments = [c['primaryName'].lower() for c in data['compartments']]
        flows = []
        for comp in data['compartments']:
            for flow in comp['outgoingFlows']:
                flows.append({
                    'type': flow['type'],
                    'source': comp['primaryName'],
                    'target': flow.get('target', '')
                })
        
        # Detect patterns
        detected_patterns = []
        
        # Pattern 1: Standard SEIR
        has_s = any('susceptible' in c for c in compartments)
        has_e = any('exposed' in c for c in compartments)
        has_i = any('infectious' in c or 'infected' in c for c in compartments)
        has_r = any('recovered' in c for c in compartments)
        
        if has_s and has_e and has_i and has_r:
            detected_patterns.append({
                'pattern': 'Standard SEIR',
                'description': 'S → E (ContactFlow, β*S*I/N), E → I (RateFlow, σ*E), I → R (RateFlow, γ*I)',
                'confidence': 'high',
                'compartments': ['S', 'E', 'I', 'R']
            })
        
        # Pattern 2: SEIR with Hospitalization
        has_h = any('hospital' in c or 'icu' in c for c in compartments)
        if has_s and has_e and has_i and has_r and has_h:
            detected_patterns.append({
                'pattern': 'SEIR with Hospitalization',
                'description': 'I → H (RateFlow, hospitalization rate), H → R or H → D (competing flows)',
                'confidence': 'high',
                'compartments': ['S', 'E', 'I', 'R', 'H']
            })
        
        # Pattern 3: Vector-Borne
        has_vector = any('mosquito' in c or 'vector' in c for c in compartments)
        if has_vector:
            detected_patterns.append({
                'pattern': 'Vector-Borne (Malaria)',
                'description': 'SH → EH (ContactFlow with mosquito), SM → EM (ContactFlow with human), Dual population dynamics',
                'confidence': 'high',
                'compartments': ['Human compartments', 'Vector compartments']
            })
        
        # Pattern 4: With Treatment
        has_treatment = any('treated' in c or 'treatment' in c for c in compartments)
        if has_treatment:
            detected_patterns.append({
                'pattern': 'With Treatment Compartment',
                'description': 'I → T (RateFlow, treatment rate), T → R (RateFlow, recovery)',
                'confidence': 'medium',
                'compartments': ['I', 'T', 'R']
            })
        
        # Pattern 5: Stratified
        has_groups = len(data['groups']) > 0
        if has_groups:
            detected_patterns.append({
                'pattern': 'Stratified Model',
                'description': 'Model includes population stratification (age, gender, risk, etc.)',
                'confidence': 'high',
                'groups': [g['name'] for g in data['groups']]
            })
        
        return {
            'modelName': model_name,
            'detectedPatterns': detected_patterns,
            'compartments': [c['primaryName'] for c in data['compartments']],
            'numFlows': sum(len(c['outgoingFlows']) for c in data['compartments']),
            'hasStratification': has_groups,
            'hasVectors': has_vector
        }
    
    def build_pattern_library(self) -> Dict[str, Any]:
        """Build library of common patterns"""
        base_path = Path(__file__).parent.parent.parent / 'Compartmental' / 'CompartmentalModel'
        
        models = {
            'COVID-19': base_path / 'covid.compmodel',
            'Malaria': base_path / 'malaria.compmodel',
            'HIV': base_path / 'HIV.compmodel'
        }
        
        pattern_library = {
            'patterns': [
                {
                    'name': 'Standard SEIR',
                    'description': 'Basic SEIR model with four compartments',
                    'structure': {
                        'compartments': ['S (Susceptible)', 'E (Exposed)', 'I (Infectious)', 'R (Recovered)'],
                        'flows': [
                            'S → E (ContactFlow, β*S*I/N)',
                            'E → I (RateFlow, σ*E)',
                            'I → R (RateFlow, γ*I)'
                        ]
                    },
                    'examples': []
                },
                {
                    'name': 'SEIR with Hospitalization',
                    'description': 'SEIR model extended with healthcare system',
                    'structure': {
                        'compartments': ['S', 'E', 'I', 'H (Hospital)', 'R', 'D (Death)'],
                        'flows': [
                            'S → E (ContactFlow)',
                            'E → I (RateFlow)',
                            'I → H (RateFlow, hospitalization rate)',
                            'H → R (RateFlow, recovery)',
                            'H → D (RateFlow, mortality)'
                        ]
                    },
                    'examples': []
                },
                {
                    'name': 'Vector-Borne (Malaria)',
                    'description': 'Dual population model with human and vector compartments',
                    'structure': {
                        'compartments': [
                            'SH (Susceptible Human)', 'EH (Exposed Human)', 'IH (Infectious Human)', 'RH (Recovered Human)',
                            'SM (Susceptible Mosquito)', 'EM (Exposed Mosquito)', 'IM (Infectious Mosquito)'
                        ],
                        'flows': [
                            'SH → EH (ContactFlow with IM)',
                            'EH → IH (RateFlow)',
                            'IH → RH (RateFlow)',
                            'SM → EM (ContactFlow with IH)',
                            'EM → IM (RateFlow)'
                        ]
                    },
                    'examples': []
                },
                {
                    'name': 'With Treatment',
                    'description': 'Model includes treatment compartment',
                    'structure': {
                        'compartments': ['S', 'I (Untreated)', 'T (Treated)', 'R'],
                        'flows': [
                            'S → I (ContactFlow)',
                            'I → T (RateFlow, treatment rate)',
                            'T → R (RateFlow, recovery)'
                        ]
                    },
                    'examples': []
                },
                {
                    'name': 'Stratified Model',
                    'description': 'Model with population stratification',
                    'structure': {
                        'stratification': 'Age, Gender, Risk, Location, etc.',
                        'note': 'Same compartment structure repeated for each stratum'
                    },
                    'examples': []
                }
            ],
            'modelAnalyses': []
        }
        
        # Analyze actual models
        for model_name, model_path in models.items():
            if model_path.exists():
                analysis = self.analyze_model_patterns(str(model_path), model_name)
                pattern_library['modelAnalyses'].append(analysis)
                
                # Add to pattern examples
                for pattern in pattern_library['patterns']:
                    for detected in analysis['detectedPatterns']:
                        if pattern['name'] == detected['pattern']:
                            pattern['examples'].append(model_name)
        
        return pattern_library
    
    def export_pattern_library(self, output_path: str):
        """Export pattern library to JSON"""
        library = self.build_pattern_library()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            json.dump(library, f, indent=2, ensure_ascii=False)
    
    def export_markdown(self, output_path: str):
        """Export pattern library to Markdown"""
        library = self.build_pattern_library()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            f.write("# Pattern Library: Common Compartmental Model Structures\n\n")
            f.write("Common patterns found in epidemiological compartmental models.\n\n")
            
            for pattern in library['patterns']:
                f.write(f"## {pattern['name']}\n\n")
                f.write(f"{pattern['description']}\n\n")
                
                if 'structure' in pattern:
                    f.write("### Structure\n\n")
                    if 'compartments' in pattern['structure']:
                        f.write("**Compartments:**\n\n")
                        for comp in pattern['structure']['compartments']:
                            f.write(f"- {comp}\n")
                        f.write("\n")
                    
                    if 'flows' in pattern['structure']:
                        f.write("**Flows:**\n\n")
                        for flow in pattern['structure']['flows']:
                            f.write(f"- {flow}\n")
                        f.write("\n")
                
                if pattern['examples']:
                    f.write(f"**Examples:** {', '.join(pattern['examples'])}\n\n")
            
            f.write("## Model Analyses\n\n")
            for analysis in library['modelAnalyses']:
                f.write(f"### {analysis['modelName']}\n\n")
                f.write(f"**Detected Patterns:**\n\n")
                for pattern in analysis['detectedPatterns']:
                    f.write(f"- {pattern['pattern']} ({pattern['confidence']} confidence)\n")
                f.write("\n")


def main():
    """Main function to analyze patterns"""
    output_dir = Path(__file__).parent.parent / 'reports' / 'patterns'
    output_dir.mkdir(parents=True, exist_ok=True)
    
    print("=" * 80)
    print("TASK 3.3: ANALYZE PAPERS FOR DATA FLOW PATTERNS")
    print("=" * 80)
    
    analyzer = PatternAnalyzer()
    
    # Export JSON
    json_path = output_dir / 'pattern_library.json'
    analyzer.export_pattern_library(str(json_path))
    print(f"✓ Pattern library exported to: {json_path}")
    
    # Export Markdown
    md_path = output_dir / 'pattern_library.md'
    analyzer.export_markdown(str(md_path))
    print(f"✓ Pattern library exported to: {md_path}")
    
    library = analyzer.build_pattern_library()
    print(f"\nPatterns identified: {len(library['patterns'])}")
    print(f"Models analyzed: {len(library['modelAnalyses'])}")
    
    print("\n" + "=" * 80)
    print("PATTERN ANALYSIS COMPLETE")
    print("=" * 80)


if __name__ == '__main__':
    main()

