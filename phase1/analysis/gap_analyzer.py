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
    
    def generate_gap_report(self) -> Dict[str, Any]:
        """Generate comprehensive gap report"""
        if self.model_name.lower() == 'malaria':
            gaps = self.analyze_malaria_gaps()
        else:
            gaps = {
                'structuralGaps': [],
                'parameterGaps': [],
                'stratificationGaps': [],
                'interventionGaps': []
            }
        
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
    
    def export_markdown(self, output_path: str):
        """Export gap report to Markdown"""
        report = self.generate_gap_report()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            f.write(f"# Gap Analysis Report: {report['modelName']}\n\n")
            f.write(f"**Analysis Date:** {report['analysisDate']}\n\n")
            f.write(f"**Total Gaps Found:** {report['totalGaps']}\n\n")
            
            f.write("## Summary\n\n")
            f.write(f"- **Critical Gaps (High Severity):** {report['summary']['criticalGaps']}\n")
            f.write(f"- **Medium Gaps:** {report['summary']['mediumGaps']}\n")
            f.write(f"- **Low Gaps:** {report['summary']['lowGaps']}\n\n")
            
            # Structural Gaps
            if report['gaps']['structuralGaps']:
                f.write("## Structural Gaps\n\n")
                for gap in report['gaps']['structuralGaps']:
                    f.write(f"### {gap['gap']}\n\n")
                    f.write(f"**Description:** {gap['description']}\n\n")
                    f.write(f"**Why It Matters:** {gap['whyItMatters']}\n\n")
                    f.write(f"**Severity:** {gap['severity'].upper()}\n\n")
                    f.write("**Found In:**\n")
                    for source in gap['foundIn']:
                        f.write(f"- {source}\n")
                    f.write("\n")
                    f.write(f"**How to Add:** {gap['howToAdd']}\n\n")
                    if 'suggestedAction' in gap:
                        f.write(f"**Suggested Action:** {gap['suggestedAction']}\n\n")
            
            # Parameter Gaps
            if report['gaps']['parameterGaps']:
                f.write("## Parameter Gaps\n\n")
                for gap in report['gaps']['parameterGaps']:
                    f.write(f"### {gap['gap']}\n\n")
                    f.write(f"**Description:** {gap['description']}\n\n")
                    f.write(f"**Current Value:** {gap['currentValue']}\n\n")
                    if 'foundInLiterature' in gap:
                        f.write(f"**Found in Literature:** {gap['foundInLiterature']}\n\n")
                    f.write(f"**Uncertainty:** {gap['uncertainty']}\n\n")
                    f.write(f"**Severity:** {gap['severity'].upper()}\n\n")
                    if 'suggestedValue' in gap:
                        f.write(f"**Suggested Value:** {gap['suggestedValue']}\n\n")
                    if 'suggestedRange' in gap:
                        f.write(f"**Suggested Range:** {gap['suggestedRange']}\n\n")
                    if 'sources' in gap:
                        f.write("**Sources:**\n")
                        for source in gap['sources']:
                            f.write(f"- {source}\n")
                        f.write("\n")
            
            # Stratification Gaps
            if report['gaps']['stratificationGaps']:
                f.write("## Stratification Gaps\n\n")
                for gap in report['gaps']['stratificationGaps']:
                    f.write(f"### {gap['gap']}\n\n")
                    f.write(f"**Description:** {gap['description']}\n\n")
                    f.write(f"**Why It Matters:** {gap['whyItMatters']}\n\n")
                    f.write(f"**Severity:** {gap['severity'].upper()}\n\n")
                    if 'note' in gap:
                        f.write(f"**Note:** {gap['note']}\n\n")


def main():
    """Main function to analyze gaps"""
    base_path = Path(__file__).parent.parent.parent / 'Compartmental' / 'CompartmentalModel'
    output_dir = Path(__file__).parent.parent / 'reports' / 'gap_reports'
    output_dir.mkdir(parents=True, exist_ok=True)
    
    # Focus on malaria model for Task 2.1
    malaria_path = base_path / 'malaria.compmodel'
    
    if not malaria_path.exists():
        print(f"Warning: {malaria_path} not found")
        return
    
    print("=" * 80)
    print("TASK 2.1: DEEP DIVE INTO MALARIA MODEL GAPS")
    print("=" * 80)
    
    analyzer = GapAnalyzer(str(malaria_path), 'Malaria')
    
    # Export JSON
    json_path = output_dir / 'malaria_gap_analysis.json'
    analyzer.export_gap_report(str(json_path))
    print(f"✓ Gap report exported to: {json_path}")
    
    # Export Markdown
    md_path = output_dir / 'malaria_gap_analysis.md'
    analyzer.export_markdown(str(md_path))
    print(f"✓ Gap report exported to: {md_path}")
    
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

