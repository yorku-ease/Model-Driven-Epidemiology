"""
Task 1.3: Define REQUIRED vs OPTIONAL Elements

CRITICAL: Not every model has every feature! Define what's universal vs conditional.
Create detection rules for AI to determine what's required vs optional.
"""
import json
from pathlib import Path
from typing import Dict, List, Any


class RequiredOptionalBuilder:
    """Build definitions of required vs optional model elements"""
    
    def __init__(self):
        self.definitions = {}
    
    def build_definitions(self) -> Dict[str, Any]:
        """Build complete definitions"""
        return {
            'title': 'Required vs Optional Model Elements',
            'version': '1.0',
            'description': 'Definitions of what is always required vs conditional in compartmental models',
            'principle': 'Only flag as missing if paper promises it but model does not have it',
            'alwaysRequired': {
                'description': 'Elements that every model MUST have',
                'elements': [
                    {
                        'element': 'Compartments',
                        'minimum': 2,
                        'description': 'At least 2 compartments are required',
                        'examples': ['S and I (minimal SIR)', 'S, E, I, R (SEIR)'],
                        'validation': 'Count compartments - must be >= 2'
                    },
                    {
                        'element': 'Flows',
                        'minimum': 1,
                        'description': 'At least 1 flow is required to connect compartments',
                        'examples': ['S → I (transmission)', 'I → R (recovery)'],
                        'validation': 'Count flows - must be >= 1'
                    },
                    {
                        'element': 'Flow Types',
                        'description': 'Each flow must have a defined type',
                        'types': ['ContactFlow', 'RateFlow', 'ExternalSource', 'ExternalSink'],
                        'validation': 'Every flow must have a type'
                    },
                    {
                        'element': 'Rate Values or Parameters',
                        'description': 'Each flow must have a rate value or parameter reference',
                        'examples': ['rate="0.1"', 'rateParameter="//@parameters.0"'],
                        'validation': 'Every flow must have rate or rateParameter'
                    },
                    {
                        'element': 'Total Population OR Initial Conditions',
                        'description': 'Model must specify total population or initial conditions for each compartment',
                        'examples': ['totalPopulation="10000"', 'population="9900" for each compartment'],
                        'validation': 'Check for totalPopulation attribute or population values in compartments'
                    }
                ]
            },
            'conditionalOptional': {
                'description': 'Elements that are only required if paper mentions them or context requires them',
                'elements': [
                    {
                        'element': 'Stratification',
                        'requiredIf': [
                            'Paper says "stratified"',
                            'Paper mentions "age groups"',
                            'Paper mentions "risk groups"',
                            'Parameters have subscripts (β_child, β_adult)',
                            'Separate equations for different groups'
                        ],
                        'notRequiredIf': [
                            'Paper says "simple SEIR model"',
                            'No mention of stratification',
                            'Single set of equations for entire population'
                        ],
                        'examples': [
                            'Age groups: 0-17, 18-64, 65+',
                            'Risk groups: High, Medium, Low',
                            'Gender: Male, Female'
                        ],
                        'detectionRules': [
                            'Search for keywords: "stratified", "age group", "age-specific", "risk group", "gender"',
                            'Check if parameters have subscripts',
                            'Look for multiple parallel compartments with same name'
                        ]
                    },
                    {
                        'element': 'Groups/Products',
                        'requiredIf': 'Stratification is present',
                        'notRequiredIf': 'No stratification mentioned',
                        'description': 'Groups and Products are only needed if model is stratified',
                        'validation': 'If stratification keywords found, check for groups and products'
                    },
                    {
                        'element': 'Births/Deaths (Demography)',
                        'requiredIf': [
                            'Simulation duration > 1 year',
                            'Paper mentions "long-term"',
                            'Paper mentions "demography" or "demographic"',
                            'Paper models population growth'
                        ],
                        'notRequiredIf': [
                            'Short-term simulation (< 1 year)',
                            'Paper says "short-term" or "outbreak"',
                            'No mention of births/deaths'
                        ],
                        'examples': [
                            'ExternalSource for births',
                            'ExternalSink for natural deaths'
                        ],
                        'detectionRules': [
                            'Extract simulation duration from paper',
                            'Search for keywords: "birth", "death", "demography", "recruitment"',
                            'Check if model includes population growth'
                        ]
                    },
                    {
                        'element': 'Vector Compartments',
                        'requiredIf': [
                            'Disease is vector-borne (malaria, dengue, zika, yellow fever)',
                            'Paper mentions "mosquito" or "vector"',
                            'Model includes vector transmission'
                        ],
                        'notRequiredIf': [
                            'Disease is not vector-borne',
                            'Direct transmission only'
                        ],
                        'examples': [
                            'Susceptible_Mosquito, Exposed_Mosquito, Infectious_Mosquito'
                        ],
                        'detectionRules': [
                            'Identify disease type',
                            'Check if vector-borne disease list includes disease',
                            'Search for "mosquito", "vector", "Anopheles", "Aedes" keywords'
                        ],
                        'vectorBorneDiseases': ['malaria', 'dengue', 'zika', 'yellow fever', 'chikungunya', 'west nile']
                    },
                    {
                        'element': 'Temperature Parameters',
                        'requiredIf': [
                            'Vector-borne disease',
                            'Paper mentions "temperature-dependent"',
                            'Parameters include temperature (T)'
                        ],
                        'notRequiredIf': [
                            'Not vector-borne',
                            'No temperature dependence mentioned'
                        ],
                        'examples': [
                            'Parameter T (VARIABLE type)',
                            'Expression parameters with T: a(T), μᵥ(T)'
                        ],
                        'detectionRules': [
                            'Check if vector compartments present',
                            'Search for "temperature" keyword',
                            'Look for parameters with T in expression'
                        ]
                    },
                    {
                        'element': 'Interventions',
                        'requiredIf': [
                            'Paper is about vaccination',
                            'Paper is about treatment',
                            'Paper mentions "intervention"',
                            'Paper studies control measures'
                        ],
                        'notRequiredIf': [
                            'Paper is about natural disease dynamics only',
                            'No intervention mentioned'
                        ],
                        'examples': [
                            'Vaccinated compartment',
                            'Treated compartment',
                            'Quarantine compartment'
                        ],
                        'detectionRules': [
                            'Search for keywords: "vaccination", "vaccine", "treatment", "intervention"',
                            'Check if paper title/abstract mentions interventions',
                            'Look for intervention compartments'
                        ]
                    },
                    {
                        'element': 'Secondary Names',
                        'requiredIf': [
                            'Paper describes substages',
                            'Paper mentions "presymptomatic", "asymptomatic"',
                            'Paper has multiple stages with same primary name'
                        ],
                        'notRequiredIf': [
                            'Simple compartment structure',
                            'No substages mentioned'
                        ],
                        'examples': [
                            'Infectious (presymptomatic)',
                            'Infectious (isolated)',
                            'Exposed (quarantined)'
                        ],
                        'detectionRules': [
                            'Look for parenthetical descriptions in compartment names',
                            'Search for substage keywords',
                            'Check if multiple compartments share primary name'
                        ]
                    }
                ]
            },
            'detectionRules': {
                'description': 'Rules for AI to detect what is required vs optional',
                'rules': [
                    {
                        'rule': 'IF paper says "simple SEIR model" → DON\'T expect stratification',
                        'action': 'Do not flag absence of age groups as gap',
                        'example': 'Simple SEIR with no stratification → ✓ Valid, complete model!'
                    },
                    {
                        'rule': 'IF paper says "age-stratified" → REQUIRE age groups',
                        'action': 'Flag absence of age groups as gap',
                        'example': 'Paper mentions "age-stratified" but no groups → ✗ Gap'
                    },
                    {
                        'rule': 'IF malaria/dengue → REQUIRE mosquito compartments',
                        'action': 'Flag absence of vector compartments as gap',
                        'example': 'Malaria without mosquitoes → ✗ Gap (vector-borne needs vectors!)'
                    },
                    {
                        'rule': 'IF COVID short-term → births/deaths OPTIONAL',
                        'action': 'Do not flag absence of births/deaths for short-term models',
                        'example': 'COVID short-term without births → ✓ Valid (not needed for short-term)'
                    },
                    {
                        'rule': 'IF simulation > 1 year → births/deaths REQUIRED',
                        'action': 'Flag absence of demography as gap for long-term models',
                        'example': '10-year simulation without births → ✗ Gap'
                    },
                    {
                        'rule': 'IF intervention study → intervention compartments REQUIRED',
                        'action': 'Flag absence of intervention compartments if paper is about interventions',
                        'example': 'Vaccination study without vaccinated compartment → ✗ Gap'
                    }
                ]
            },
            'validationChecklist': {
                'description': 'Checklist for validating model completeness',
                'alwaysCheck': [
                    'At least 2 compartments exist',
                    'At least 1 flow exists',
                    'All flows have types',
                    'All flows have rates or rateParameters',
                    'Total population or initial conditions specified'
                ],
                'conditionalCheck': [
                    'If stratification mentioned → groups and products exist',
                    'If vector-borne disease → vector compartments exist',
                    'If long-term simulation → births/deaths exist',
                    'If intervention study → intervention compartments exist',
                    'If temperature-dependent → temperature parameters exist'
                ]
            }
        }
    
    def export_definitions(self, output_path: str):
        """Export definitions to JSON"""
        definitions = self.build_definitions()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            json.dump(definitions, f, indent=2, ensure_ascii=False)
    
    def export_markdown(self, output_path: str):
        """Export definitions to Markdown"""
        definitions = self.build_definitions()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            f.write(f"# {definitions['title']}\n\n")
            f.write(f"**Version:** {definitions['version']}\n\n")
            f.write(f"{definitions['description']}\n\n")
            f.write(f"**Key Principle:** {definitions['principle']}\n\n")
            
            # Always Required
            f.write("## Always Required Elements\n\n")
            f.write(f"{definitions['alwaysRequired']['description']}\n\n")
            for elem in definitions['alwaysRequired']['elements']:
                f.write(f"### {elem['element']}\n\n")
                f.write(f"{elem['description']}\n\n")
                if 'minimum' in elem:
                    f.write(f"**Minimum:** {elem['minimum']}\n\n")
                if 'examples' in elem:
                    f.write("**Examples:**\n\n")
                    for ex in elem['examples']:
                        f.write(f"- {ex}\n")
                    f.write("\n")
                if 'validation' in elem:
                    f.write(f"**Validation:** {elem['validation']}\n\n")
            
            # Conditional/Optional
            f.write("## Conditional/Optional Elements\n\n")
            f.write(f"{definitions['conditionalOptional']['description']}\n\n")
            for elem in definitions['conditionalOptional']['elements']:
                f.write(f"### {elem['element']}\n\n")
                
                if 'requiredIf' in elem:
                    f.write("**Required If:**\n\n")
                    for condition in elem['requiredIf']:
                        f.write(f"- {condition}\n")
                    f.write("\n")
                
                if 'notRequiredIf' in elem:
                    f.write("**Not Required If:**\n\n")
                    for condition in elem['notRequiredIf']:
                        f.write(f"- {condition}\n")
                    f.write("\n")
                
                if 'examples' in elem:
                    f.write("**Examples:**\n\n")
                    for ex in elem['examples']:
                        f.write(f"- {ex}\n")
                    f.write("\n")
                
                if 'detectionRules' in elem:
                    f.write("**Detection Rules:**\n\n")
                    for rule in elem['detectionRules']:
                        f.write(f"- {rule}\n")
                    f.write("\n")
            
            # Detection Rules
            f.write("## Detection Rules for AI\n\n")
            for rule in definitions['detectionRules']['rules']:
                f.write(f"### {rule['rule']}\n\n")
                f.write(f"**Action:** {rule['action']}\n\n")
                f.write(f"**Example:** {rule['example']}\n\n")
            
            # Validation Checklist
            f.write("## Validation Checklist\n\n")
            f.write("### Always Check:\n\n")
            for check in definitions['validationChecklist']['alwaysCheck']:
                f.write(f"- [ ] {check}\n")
            f.write("\n")
            f.write("### Conditional Check:\n\n")
            for check in definitions['validationChecklist']['conditionalCheck']:
                f.write(f"- [ ] {check}\n")


def main():
    """Main function to generate definitions"""
    output_dir = Path(__file__).parent.parent / 'reports' / 'protocols'
    output_dir.mkdir(parents=True, exist_ok=True)
    
    builder = RequiredOptionalBuilder()
    
    # Export JSON
    json_path = output_dir / 'required_optional.json'
    builder.export_definitions(str(json_path))
    print(f"✓ Definitions exported to: {json_path}")
    
    # Export Markdown
    md_path = output_dir / 'required_optional.md'
    builder.export_markdown(str(md_path))
    print(f"✓ Definitions exported to: {md_path}")
    
    print("\nRequired/Optional definitions generation complete!")


if __name__ == '__main__':
    main()

