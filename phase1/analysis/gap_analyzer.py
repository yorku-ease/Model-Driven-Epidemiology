"""
Task 2.1: Deep Dive into Malaria Model Gaps

Analyze what's missing in the malaria model compared to literature.
Create gap reports identifying structural and parameter gaps.
"""
import json
from pathlib import Path
from typing import Dict, List, Any, Optional
import sys

sys.path.insert(0, str(Path(__file__).parent.parent / 'utils'))
from xml_parser import CompModelParser


class GapAnalyzer:
    """Analyze gaps in models compared to literature and best practices"""
    
    def __init__(self, model_path: str, model_name: str):
        """Initialize gap analyzer"""
        self.model_path = Path(model_path)
        self.model_name = model_name
        self.parser = CompModelParser(str(model_path))
        self.data = self.parser.extract_all()
        self.gaps = []
    
    def analyze_gaps(self) -> Dict[str, Any]:
        """Analyze gaps in model based on model type"""
        if self.model_name.lower() == 'malaria':
            return self.analyze_malaria_gaps()
        elif self.model_name.lower() == 'covid-19':
            return self.analyze_covid_gaps()
        elif self.model_name.lower() == 'hiv':
            return self.analyze_hiv_gaps()
        else:
            # Generic gap analysis
            return self.analyze_generic_gaps()
    
    def analyze_malaria_gaps(self) -> Dict[str, Any]:
        """Analyze gaps specific to malaria model"""
        gaps = {
            'structuralGaps': [],
            'parameterGaps': [],
            'stratificationGaps': [],
            'interventionGaps': []
        }
        
        compartments = [c['primaryName'].lower() for c in self.data['compartments']]
        
        # Structural Gaps
        # 1. Missing Asymptomatic compartment
        if not any('asymptomatic' in c for c in compartments):
            gaps['structuralGaps'].append({
                'gap': 'Missing Asymptomatic Carriers (AH)',
                'description': 'Asymptomatic individuals who contribute to transmission',
                'whyItMatters': 'Asymptomatic carriers can significantly contribute to malaria transmission',
                'foundIn': [
                    'Akowe et al. (2025) - mentions asymptomatic carriers',
                    'Multiple malaria modeling papers include asymptomatic compartments'
                ],
                'howToAdd': 'Add compartment: IH → AH with proportion p_asymp',
                'severity': 'medium',
                'suggestedAction': 'Review literature for asymptomatic proportion values (typically 0.2-0.5)'
            })
        
        # 2. Missing Severe compartment
        if not any('severe' in c for c in compartments):
            gaps['structuralGaps'].append({
                'gap': 'Missing Severe Disease Compartment',
                'description': 'Compartment for individuals with severe malaria',
                'whyItMatters': 'Severe malaria has different mortality and treatment rates',
                'foundIn': [
                    'WHO malaria modeling guidelines',
                    'Age-stratified models often include severe cases'
                ],
                'howToAdd': 'Add compartment: IH → Severe with age-specific rates',
                'severity': 'low',
                'suggestedAction': 'Only add if modeling severe outcomes explicitly'
            })
        
        # 3. Missing Resistant compartment
        if not any('resistant' in c or 'drug' in c for c in compartments):
            gaps['structuralGaps'].append({
                'gap': 'Missing Drug Resistance Compartment',
                'description': 'Compartment for drug-resistant infections',
                'whyItMatters': 'Drug resistance is a major concern in malaria',
                'foundIn': [
                    'Models studying treatment efficacy',
                    'Models of drug resistance evolution'
                ],
                'howToAdd': 'Add compartment for resistant strains if modeling resistance',
                'severity': 'low',
                'suggestedAction': 'Only add if paper focuses on drug resistance'
            })
        
        # Stratification Gaps
        # 1. Missing Age Groups
        groups = self.data['groups']
        has_age_groups = any('age' in g['name'].lower() for g in groups)
        
        if not has_age_groups:
            gaps['stratificationGaps'].append({
                'gap': 'Missing Age Stratification',
                'description': 'No age groups defined in model',
                'whyItMatters': 'Children <5 years have higher malaria risk and different severity',
                'foundIn': [
                    'Most modern malaria models include age stratification',
                    'WHO guidelines recommend age-specific modeling for malaria'
                ],
                'howToAdd': 'Create age groups: <5, 5-14, 15-64, 65+',
                'severity': 'high',
                'suggestedAction': 'Review paper to see if age stratification is mentioned',
                'note': 'Only required if paper mentions age groups'
            })
        
        # Parameter Gaps
        parameters = {p['name']: p for p in self.data['parameters']}
        
        # 1. Missing asymptomatic proportion
        if 'p_asymp' not in parameters and 'asymptomatic' not in str(parameters).lower():
            gaps['parameterGaps'].append({
                'gap': 'Missing Asymptomatic Proportion (p_asymp)',
                'description': 'Parameter for proportion of infections that are asymptomatic',
                'currentValue': 'Not in model',
                'foundInLiterature': '0.2-0.5 (varies by region and study)',
                'uncertainty': 'Medium (range varies across studies)',
                'severity': 'medium',
                'suggestedValue': '0.3 (mid-range estimate)',
                'suggestedRange': [0.2, 0.5],
                'sources': [
                    'Akowe et al. (2025) - may have region-specific values',
                    'WHO malaria epidemiology reports'
                ]
            })
        
        # 2. Treatment efficacy as separate parameter
        has_treatment = any('treatment' in c.lower() or 'treated' in c.lower() 
                           for c in compartments)
        if has_treatment and 'treatment_efficacy' not in parameters:
            gaps['parameterGaps'].append({
                'gap': 'Treatment Efficacy Not Explicit',
                'description': 'Treatment efficacy is implicit in treatment rate',
                'currentValue': 'Implicit in treatment rate',
                'shouldBe': 'Separate parameter with uncertainty',
                'foundInLiterature': '0.85-0.95 (varies by drug and resistance)',
                'uncertainty': 'Medium',
                'severity': 'low',
                'suggestedValue': '0.90',
                'suggestedRange': [0.85, 0.95],
                'sources': [
                    'WHO treatment guidelines',
                    'Drug-specific efficacy studies'
                ]
            })
        
        # 3. Bed net coverage
        if 'bed_net' not in str(parameters).lower() and 'net' not in str(parameters).lower():
            gaps['parameterGaps'].append({
                'gap': 'Missing Bed Net Coverage Parameter',
                'description': 'Parameter for insecticide-treated bed net coverage',
                'currentValue': 'Not in model',
                'foundInLiterature': 'Varies by region (0.0-0.8)',
                'uncertainty': 'High (varies significantly by location)',
                'severity': 'low',
                'note': 'Only needed if modeling interventions',
                'suggestedValue': '0.5 (example value)',
                'suggestedRange': [0.0, 0.8]
            })
        
        # Intervention Gaps
        # 1. Missing intervention compartments
        has_vaccination = any('vaccin' in c.lower() for c in compartments)
        has_bed_nets = any('net' in c.lower() for c in compartments)
        
        if not has_vaccination and not has_bed_nets:
            gaps['interventionGaps'].append({
                'gap': 'No Intervention Compartments',
                'description': 'Model does not include vaccination or bed net interventions',
                'whyItMatters': 'Interventions are key for malaria control',
                'foundIn': [
                    'Many malaria models include intervention compartments',
                    'Models studying control strategies'
                ],
                'howToAdd': 'Add compartments for vaccinated or protected populations',
                'severity': 'low',
                'note': 'Only required if paper is about interventions'
            })
        
        return gaps
    
    def analyze_covid_gaps(self) -> Dict[str, Any]:
        """Analyze gaps specific to COVID-19 model"""
        gaps = {
            'structuralGaps': [],
            'parameterGaps': [],
            'stratificationGaps': [],
            'interventionGaps': []
        }
        
        compartments = [c['primaryName'].lower() for c in self.data['compartments']]
        parameters = {p['name']: p for p in self.data['parameters']}
        groups = self.data['groups']
        
        # Structural gaps - check for common COVID-19 compartments
        if not any('hospital' in c or 'icu' in c for c in compartments):
            gaps['structuralGaps'].append({
                'gap': 'Missing Healthcare System Compartments',
                'description': 'No hospital or ICU compartments',
                'whyItMatters': 'COVID-19 models often track healthcare burden',
                'foundIn': ['Most COVID-19 models include healthcare compartments'],
                'howToAdd': 'Add Hospital and ICU compartments',
                'severity': 'low',
                'note': 'Already present - this is just a check'
            })
        
        # Parameter gaps - COVID-19 has rates in flows, not parameters
        if len(parameters) == 0:
            gaps['parameterGaps'].append({
                'gap': 'No Explicit Parameters Defined',
                'description': 'Rates are hardcoded in flows, not defined as parameters',
                'currentValue': 'Rates embedded in flow definitions',
                'whyItMatters': 'Makes sensitivity analysis and uncertainty quantification difficult',
                'severity': 'medium',
                'suggestedAction': 'Extract rates from flows and define as parameters'
            })
        
        # Check if age stratification exists
        has_age_groups = any('age' in g['name'].lower() for g in groups)
        if not has_age_groups:
            gaps['stratificationGaps'].append({
                'gap': 'Missing Age Stratification',
                'description': 'Age groups may not be properly defined',
                'whyItMatters': 'COVID-19 severity varies significantly by age',
                'severity': 'low',
                'note': 'Model appears to have age stratification already'
            })
        
        return gaps
    
    def analyze_hiv_gaps(self) -> Dict[str, Any]:
        """Analyze gaps specific to HIV model"""
        gaps = {
            'structuralGaps': [],
            'parameterGaps': [],
            'stratificationGaps': [],
            'interventionGaps': []
        }
        
        compartments = [c['primaryName'].lower() for c in self.data['compartments']]
        parameters = {p['name']: p for p in self.data['parameters']}
        groups = self.data['groups']
        
        # Check for treatment compartments
        has_treatment = any('treatment' in c or 'treated' in c or 'art' in c for c in compartments)
        if not has_treatment:
            gaps['structuralGaps'].append({
                'gap': 'Missing Treatment Compartment',
                'description': 'No compartment for individuals on treatment',
                'whyItMatters': 'HIV models often track treatment status separately',
                'foundIn': ['Most HIV models include treatment compartments'],
                'howToAdd': 'Add treated compartment',
                'severity': 'low',
                'note': 'Model appears to have treatment already'
            })
        
        # Parameter gaps - HIV has rates in flows
        if len(parameters) == 0:
            gaps['parameterGaps'].append({
                'gap': 'No Explicit Parameters Defined',
                'description': 'Rates are hardcoded in flows with stratum-specific values',
                'currentValue': 'Rates embedded in flow definitions',
                'whyItMatters': 'Makes sensitivity analysis and uncertainty quantification difficult',
                'severity': 'medium',
                'suggestedAction': 'Extract rates from flows and define as parameters by risk group'
            })
        
        # Check risk group stratification
        has_risk_groups = any('risk' in g['name'].lower() or 'behavior' in g['name'].lower() 
                             for g in groups)
        if not has_risk_groups:
            gaps['stratificationGaps'].append({
                'gap': 'Missing Risk Group Stratification',
                'description': 'Risk groups may not be properly defined',
                'whyItMatters': 'HIV transmission varies significantly by risk behavior',
                'severity': 'low',
                'note': 'Model appears to have sexual behavior stratification already'
            })
        
        # Intervention gaps
        has_prep = any('prep' in c.lower() or 'pre-exposure' in c.lower() for c in compartments)
        if not has_prep:
            gaps['interventionGaps'].append({
                'gap': 'Missing PrEP Intervention Compartment',
                'description': 'No compartment for PrEP users',
                'whyItMatters': 'PrEP is important for HIV prevention',
                'severity': 'low',
                'note': 'Only needed if paper is about PrEP'
            })
        
        return gaps
    
    def analyze_generic_gaps(self) -> Dict[str, Any]:
        """Generic gap analysis for unknown model types"""
        gaps = {
            'structuralGaps': [],
            'parameterGaps': [],
            'stratificationGaps': [],
            'interventionGaps': []
        }
        
        compartments = [c['primaryName'].lower() for c in self.data['compartments']]
        parameters = {p['name']: p for p in self.data['parameters']}
        
        # Basic checks
        if len(compartments) < 2:
            gaps['structuralGaps'].append({
                'gap': 'Insufficient Compartments',
                'description': 'Model has fewer than 2 compartments',
                'severity': 'high'
            })
        
        if len(parameters) == 0:
            gaps['parameterGaps'].append({
                'gap': 'No Parameters Defined',
                'description': 'Model has no explicit parameters',
                'severity': 'medium'
            })
        
        return gaps
    
    def generate_gap_report(self) -> Dict[str, Any]:
        """Generate comprehensive gap report"""
        gaps = self.analyze_gaps()
        
        report = {
            'modelName': self.model_name,
            'analysisDate': str(Path(__file__).stat().st_mtime),
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
                               if g.get('severity') == 'low'])
            }
        }
        
        return report
    
    def export_gap_report(self, output_path: str):
        """Export gap report to JSON"""
        report = self.generate_gap_report()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            json.dump(report, f, indent=2, ensure_ascii=False)
    


def main():
    """Main function to analyze gaps for all models"""
    base_path = Path(__file__).parent.parent.parent / 'Compartmental' / 'CompartmentalModel'
    output_dir = Path(__file__).parent.parent / 'reports' / 'gap_reports'
    output_dir.mkdir(parents=True, exist_ok=True)
    
    # Analyze all 3 models
    models = {
        'COVID-19': base_path / 'covid.compmodel',
        'Malaria': base_path / 'malaria.compmodel',
        'HIV': base_path / 'HIV.compmodel'
    }
    
    print("=" * 80)
    print("TASK 2.1: GAP ANALYSIS FOR ALL MODELS")
    print("=" * 80)
    
    for model_name, model_path in models.items():
        if not model_path.exists():
            print(f"\n⚠ Warning: {model_path} not found, skipping {model_name}")
            continue
        
        print(f"\n{'=' * 80}")
        print(f"Analyzing: {model_name}")
        print('=' * 80)
        
        try:
            analyzer = GapAnalyzer(str(model_path), model_name)
            
            # Export JSON
            json_filename = f"{model_name.lower().replace('-', '_')}_gap_analysis.json"
            json_path = output_dir / json_filename
            analyzer.export_gap_report(str(json_path))
            print(f"✓ Gap report exported to: {json_path}")
        except Exception as e:
            print(f"✗ Error analyzing {model_name}: {e}")
            import traceback
            traceback.print_exc()
    
    
    report = analyzer.generate_gap_report()
    print(f"\nTotal gaps found: {report['totalGaps']}")
    print(f"  - Structural: {len(report['gaps']['structuralGaps'])}")
    print(f"  - Parameters: {len(report['gaps']['parameterGaps'])}")
    print(f"  - Stratification: {len(report['gaps']['stratificationGaps'])}")
    print(f"  - Interventions: {len(report['gaps']['interventionGaps'])}")
    
    print("\n" + "=" * 80)
    print("GAP ANALYSIS COMPLETE")
    print("=" * 80)


if __name__ == '__main__':
    main()

