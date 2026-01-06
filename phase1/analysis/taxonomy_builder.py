"""
Task 1.4: Define Taxonomies

Based on existing models, create structured lists of:
- Compartment types
- Flow types
- Parameter types
- Stratification types
"""
import json
from pathlib import Path
from typing import Dict, List, Any


class TaxonomyBuilder:
    """Build taxonomies for compartmental models"""
    
    def __init__(self):
        self.taxonomies = {}
    
    def build_compartment_taxonomy(self) -> Dict[str, Any]:
        """Build taxonomy of compartment types"""
        return {
            'name': 'Compartment Types',
            'description': 'Taxonomy of compartment types found in epidemiological models',
            'categories': [
                {
                    'category': 'Disease States',
                    'description': 'Core disease progression compartments',
                    'examples': [
                        {'name': 'Susceptible', 'abbreviation': 'S', 'description': 'Individuals not yet exposed to disease'},
                        {'name': 'Exposed', 'abbreviation': 'E', 'description': 'Individuals exposed but not yet infectious'},
                        {'name': 'Infectious', 'abbreviation': 'I', 'description': 'Individuals capable of transmitting disease'},
                        {'name': 'Recovered', 'abbreviation': 'R', 'description': 'Individuals who have recovered from disease'},
                        {'name': 'Vaccinated', 'abbreviation': 'V', 'description': 'Individuals who have been vaccinated'}
                    ],
                    'required': True,
                    'conditional': False
                },
                {
                    'category': 'Healthcare',
                    'description': 'Compartments related to healthcare system',
                    'examples': [
                        {'name': 'Hospital', 'abbreviation': 'H', 'description': 'Individuals hospitalized'},
                        {'name': 'ICU', 'abbreviation': 'ICU', 'description': 'Individuals in intensive care'},
                        {'name': 'Quarantine', 'abbreviation': 'Q', 'description': 'Individuals in quarantine'},
                        {'name': 'Isolated', 'abbreviation': 'ISO', 'description': 'Individuals in isolation'}
                    ],
                    'required': False,
                    'conditional': True,
                    'condition': 'Only if paper models healthcare system or interventions'
                },
                {
                    'category': 'Outcomes',
                    'description': 'Final state compartments',
                    'examples': [
                        {'name': 'Death', 'abbreviation': 'D', 'description': 'Disease-induced deaths'},
                        {'name': 'Recovered', 'abbreviation': 'R', 'description': 'Recovered individuals'},
                        {'name': 'HIV Deaths', 'abbreviation': 'HD', 'description': 'HIV/AIDS-related deaths'}
                    ],
                    'required': False,
                    'conditional': True,
                    'condition': 'Only if paper tracks deaths or specific outcomes'
                },
                {
                    'category': 'Vectors',
                    'description': 'Vector compartments for vector-borne diseases',
                    'examples': [
                        {'name': 'Susceptible_Mosquito', 'abbreviation': 'SM', 'description': 'Susceptible mosquito population'},
                        {'name': 'Exposed_Mosquito', 'abbreviation': 'EM', 'description': 'Exposed mosquito population'},
                        {'name': 'Infectious_Mosquito', 'abbreviation': 'IM', 'description': 'Infectious mosquito population'}
                    ],
                    'required': False,
                    'conditional': True,
                    'condition': 'ONLY if vector-borne disease (malaria, dengue, zika, yellow fever)'
                },
                {
                    'category': 'Stages',
                    'description': 'Disease stage compartments',
                    'examples': [
                        {'name': 'Presymptomatic', 'description': 'Infectious but not yet showing symptoms'},
                        {'name': 'Mild', 'description': 'Mild disease symptoms'},
                        {'name': 'Severe', 'description': 'Severe disease symptoms'},
                        {'name': 'Asymptomatic', 'description': 'Infected but asymptomatic'}
                    ],
                    'required': False,
                    'conditional': True,
                    'condition': 'ONLY if paper describes disease stages or substages'
                },
                {
                    'category': 'Treatment',
                    'description': 'Treatment-related compartments',
                    'examples': [
                        {'name': 'Treated', 'description': 'Individuals receiving treatment'},
                        {'name': 'Untreated', 'description': 'Untreated infected individuals'},
                        {'name': 'On ART', 'description': 'Individuals on antiretroviral therapy (HIV)'}
                    ],
                    'required': False,
                    'conditional': True,
                    'condition': 'ONLY if paper is about treatment or interventions'
                }
            ]
        }
    
    def build_flow_taxonomy(self) -> Dict[str, Any]:
        """Build taxonomy of flow types"""
        return {
            'name': 'Flow Types',
            'description': 'Taxonomy of flow types in compartmental models',
            'categories': [
                {
                    'category': 'ContactFlow',
                    'description': 'Transmission flows that depend on contact between compartments',
                    'pattern': 'β*S*I/N or contactRate*Source*ContactCompartment/TotalPopulation',
                    'examples': [
                        'S → E: Susceptible to Exposed via contact with Infectious',
                        'SH → EH: Susceptible Human to Exposed Human via contact with Infectious Mosquito'
                    ],
                    'characteristics': [
                        'Involves two compartments (source and contact)',
                        'Rate depends on product of two populations',
                        'Represents transmission/infection',
                        'Usually normalized by total population'
                    ],
                    'required': False,
                    'conditional': True,
                    'condition': 'Required if model includes transmission'
                },
                {
                    'category': 'RateFlow',
                    'description': 'Fixed rate transitions between compartments',
                    'pattern': 'rate*Compartment',
                    'examples': [
                        'E → I: Exposed to Infectious (incubation/progression)',
                        'I → R: Infectious to Recovered (recovery)',
                        'I → H: Infectious to Hospital (hospitalization)'
                    ],
                    'characteristics': [
                        'Single compartment with fixed rate',
                        'Represents progression, recovery, or transition',
                        'Rate is constant (not dependent on other compartments)',
                        'Can have stratum-specific rates'
                    ],
                    'required': True,
                    'conditional': False
                },
                {
                    'category': 'ExternalSource',
                    'description': 'External inflows (births, recruitment, inputs)',
                    'pattern': '+ rate*TotalPopulation or + constant',
                    'examples': [
                        'Births: ExternalSource to Susceptible',
                        'Recruitment: ExternalSource to specific compartment',
                        'Onramp: ExternalSource to traffic network node'
                    ],
                    'characteristics': [
                        'Positive term not from another compartment',
                        'Represents births, recruitment, or external input',
                        'May target specific stratum',
                        'OPTIONAL for short-term models'
                    ],
                    'required': False,
                    'conditional': True,
                    'condition': 'ONLY if long-term (>1 year) simulation or paper mentions demography'
                },
                {
                    'category': 'ExternalSink',
                    'description': 'External outflows (deaths, outputs)',
                    'pattern': '- rate*Compartment',
                    'examples': [
                        'Natural Death: ExternalSink from all compartments',
                        'Disease Death: ExternalSink from Infectious/Hospital',
                        'Offramp: ExternalSink from traffic network node'
                    ],
                    'characteristics': [
                        'Negative term not going to another compartment',
                        'Represents deaths or external output',
                        'May be from specific stratum',
                        'OPTIONAL for short-term models'
                    ],
                    'required': False,
                    'conditional': True,
                    'condition': 'ONLY if long-term (>1 year) simulation or paper mentions demography'
                }
            ]
        }
    
    def build_parameter_taxonomy(self) -> Dict[str, Any]:
        """Build taxonomy of parameter types"""
        return {
            'name': 'Parameter Types',
            'description': 'Taxonomy of parameter types in compartmental models',
            'categories': [
                {
                    'category': 'CONSTANT',
                    'description': 'Fixed values with names',
                    'examples': [
                        {'name': 'β', 'value': '0.9969', 'description': 'Transmission rate', 'unit': 'per day'},
                        {'name': 'γ', 'value': '0.1', 'description': 'Recovery rate', 'unit': 'per day'},
                        {'name': 'μ', 'value': '0.0002', 'description': 'Natural death rate', 'unit': 'per day'}
                    ],
                    'required': True,
                    'conditional': False
                },
                {
                    'category': 'VARIABLE',
                    'description': 'Runtime inputs (temperature, intervention level, etc.)',
                    'examples': [
                        {'name': 'T', 'description': 'Temperature', 'unit': '°C'},
                        {'name': 'intervention_level', 'description': 'Level of intervention', 'unit': 'dimensionless'}
                    ],
                    'required': False,
                    'conditional': True,
                    'condition': 'ONLY if model has environmental dependencies or time-varying inputs'
                },
                {
                    'category': 'EXPRESSION',
                    'description': 'Computed from other parameters/compartments',
                    'examples': [
                        {'name': 'β₁a(T)', 'expression': 'β₁ * a(T)', 'description': 'Temperature-dependent contact rate'},
                        {'name': 'β2', 'expression': 'eta_S * IM', 'description': 'Force of infection'}
                    ],
                    'required': False,
                    'conditional': True,
                    'condition': 'ONLY if model has derived parameters or complex relationships'
                }
            ]
        }
    
    def build_stratification_taxonomy(self) -> Dict[str, Any]:
        """Build taxonomy of stratification types"""
        return {
            'name': 'Stratification Types',
            'description': 'Taxonomy of population stratification types',
            'note': 'Stratification is CONDITIONAL - only if paper mentions it',
            'categories': [
                {
                    'category': 'Age',
                    'description': 'Age-based stratification',
                    'examples': [
                        {'values': ['0-17', '18-64', '65+'], 'description': 'COVID-19 age groups'},
                        {'values': ['<5', '5-14', '15-64', '65+'], 'description': 'Malaria age groups'},
                        {'values': ['Children', 'Adults', 'Elderly'], 'description': 'General age categories'}
                    ],
                    'common_use': 'Age-specific transmission, severity, or mortality rates',
                    'required': False,
                    'conditional': True,
                    'condition': 'ONLY if paper says "age-stratified" or mentions age groups'
                },
                {
                    'category': 'Gender',
                    'description': 'Gender-based stratification',
                    'examples': [
                        {'values': ['Male', 'Female'], 'description': 'Binary gender classification'}
                    ],
                    'common_use': 'Gender-specific transmission or treatment rates',
                    'required': False,
                    'conditional': True,
                    'condition': 'ONLY if paper mentions gender-specific patterns'
                },
                {
                    'category': 'Risk',
                    'description': 'Risk-based stratification',
                    'examples': [
                        {'values': ['High', 'Medium', 'Low'], 'description': 'Risk level categories'},
                        {'values': ['Homosexual Men', 'Women', 'Heterosexual Men'], 'description': 'HIV risk groups'}
                    ],
                    'common_use': 'Risk-specific transmission or behavior patterns',
                    'required': False,
                    'conditional': True,
                    'condition': 'ONLY if paper mentions risk groups'
                },
                {
                    'category': 'Location',
                    'description': 'Geographic or spatial stratification',
                    'examples': [
                        {'values': ['Urban', 'Rural'], 'description': 'Urban-rural classification'},
                        {'values': ['Region1', 'Region2', 'Region3'], 'description': 'Geographic regions'}
                    ],
                    'common_use': 'Location-specific transmission or access patterns',
                    'required': False,
                    'conditional': True,
                    'condition': 'ONLY if paper mentions geographic stratification'
                },
                {
                    'category': 'Vaccination Status',
                    'description': 'Vaccination-based stratification',
                    'examples': [
                        {'values': ['Unvaccinated', 'Vaccinated'], 'description': 'Vaccination status'},
                        {'values': ['Unvaccinated', 'Partially Vaccinated', 'Fully Vaccinated'], 'description': 'Vaccination levels'}
                    ],
                    'common_use': 'Vaccination-specific transmission or protection',
                    'required': False,
                    'conditional': True,
                    'condition': 'ONLY if paper is about vaccination'
                }
            ]
        }
    
    def build_all_taxonomies(self) -> Dict[str, Any]:
        """Build all taxonomies"""
        return {
            'compartmentTypes': self.build_compartment_taxonomy(),
            'flowTypes': self.build_flow_taxonomy(),
            'parameterTypes': self.build_parameter_taxonomy(),
            'stratificationTypes': self.build_stratification_taxonomy()
        }
    
    def export_taxonomies(self, output_path: str):
        """Export all taxonomies to JSON"""
        taxonomies = self.build_all_taxonomies()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            json.dump(taxonomies, f, indent=2, ensure_ascii=False)
    
    def export_markdown(self, output_path: str):
        """Export taxonomies to Markdown"""
        taxonomies = self.build_all_taxonomies()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            f.write("# Compartmental Model Taxonomies\n\n")
            f.write("Structured taxonomies for compartmental epidemiological models.\n\n")
            
            for key, taxonomy in taxonomies.items():
                f.write(f"## {taxonomy['name']}\n\n")
                f.write(f"{taxonomy['description']}\n\n")
                
                if 'note' in taxonomy:
                    f.write(f"**Note:** {taxonomy['note']}\n\n")
                
                for category in taxonomy['categories']:
                    f.write(f"### {category['category']}\n\n")
                    f.write(f"{category['description']}\n\n")
                    
                    if 'examples' in category:
                        f.write("**Examples:**\n\n")
                        for ex in category['examples']:
                            if isinstance(ex, dict):
                                if 'name' in ex:
                                    f.write(f"- **{ex['name']}**: {ex.get('description', '')}\n")
                                elif 'values' in ex:
                                    f.write(f"- Values: {', '.join(ex['values'])} - {ex.get('description', '')}\n")
                            else:
                                f.write(f"- {ex}\n")
                        f.write("\n")
                    
                    if 'pattern' in category:
                        f.write(f"**Pattern:** {category['pattern']}\n\n")
                    
                    if 'characteristics' in category:
                        f.write("**Characteristics:**\n\n")
                        for char in category['characteristics']:
                            f.write(f"- {char}\n")
                        f.write("\n")
                    
                    f.write(f"**Required:** {category['required']}\n")
                    if category.get('conditional'):
                        f.write(f"**Conditional:** {category.get('condition', 'Yes')}\n")
                    f.write("\n")


def main():
    """Main function to generate taxonomies"""
    output_dir = Path(__file__).parent.parent / 'reports' / 'taxonomies'
    output_dir.mkdir(parents=True, exist_ok=True)
    
    builder = TaxonomyBuilder()
    
    # Export JSON
    json_path = output_dir / 'taxonomies.json'
    builder.export_taxonomies(str(json_path))
    print(f"✓ Taxonomies exported to: {json_path}")
    
    # Export Markdown
    md_path = output_dir / 'taxonomies.md'
    builder.export_markdown(str(md_path))
    print(f"✓ Taxonomies exported to: {md_path}")
    
    print("\nTaxonomy generation complete!")


if __name__ == '__main__':
    main()

