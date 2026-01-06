"""
Task 3.2: Manual Model Extraction Practice

Create structured JSON format for manually extracted models.
This becomes "ground truth" for training AI later.
"""
import json
from pathlib import Path
from typing import Dict, List, Any, Optional


class ManualExtractionTemplate:
    """Template for manual model extraction"""
    
    def __init__(self):
        self.template = {
            "paper": {
                "title": "",
                "authors": "",
                "year": 0,
                "disease": "",
                "doi": "",
                "url": ""
            },
            "model": {
                "compartments": [],
                "flows": [],
                "parameters": [],
                "stratification": {
                    "groups": []
                },
                "initialConditions": {},
                "totalPopulation": 0
            },
            "gaps": []
        }
    
    def create_extraction_template(self) -> Dict[str, Any]:
        """Create template for manual extraction"""
        return {
            "instructions": {
                "description": "Manual extraction template for epidemiological models",
                "usage": "Fill in this template when manually extracting a model from a paper",
                "fields": {
                    "paper": "Paper metadata (title, authors, year, disease)",
                    "model": "Extracted model structure (compartments, flows, parameters)",
                    "gaps": "Missing or uncertain information"
                }
            },
            "template": self.template,
            "examples": {
                "compartment": {
                    "name": "Susceptible",
                    "description": "Individuals not yet exposed to disease",
                    "initial_pop": 14700000,
                    "secondary_name": ""  # Optional
                },
                "flow": {
                    "source": "Susceptible",
                    "target": "Exposed",
                    "type": "ContactFlow",
                    "contact_compartment": "Infectious_presymptomatic",
                    "rate": "beta_base",  # Parameter name or value
                    "description": "Infection transmission"
                },
                "parameter": {
                    "name": "beta_base",
                    "value": 0.00001,
                    "unit": "per day",
                    "source": "Table 1, page 4",
                    "uncertainty": "±20%",
                    "type": "CONSTANT"  # CONSTANT, VARIABLE, or EXPRESSION
                },
                "gap": {
                    "type": "missing_parameter",
                    "description": "Natural death rate not specified",
                    "severity": "low"
                }
            }
        }
    
    def create_example_extraction(self) -> Dict[str, Any]:
        """Create example extraction based on COVID model"""
        return {
            "paper": {
                "title": "COVID-19 Age-Stratified Model",
                "authors": "Tuite et al.",
                "year": 2020,
                "disease": "COVID-19",
                "doi": "",
                "url": ""
            },
            "model": {
                "compartments": [
                    {
                        "name": "Susceptible",
                        "description": "Individuals not yet exposed",
                        "initial_pop": 14700000,
                        "secondary_name": ""
                    },
                    {
                        "name": "Exposed",
                        "description": "Individuals exposed but not yet infectious",
                        "initial_pop": 5000,
                        "secondary_name": ""
                    },
                    {
                        "name": "Infectious",
                        "description": "Infectious individuals",
                        "initial_pop": 3000,
                        "secondary_name": "presymptomatic"
                    }
                ],
                "flows": [
                    {
                        "source": "Susceptible",
                        "target": "Exposed",
                        "type": "ContactFlow",
                        "contact_compartment": "Infectious_presymptomatic",
                        "rate": "beta_base",
                        "description": "Infection transmission"
                    },
                    {
                        "source": "Exposed",
                        "target": "Infectious",
                        "type": "RateFlow",
                        "rate": "sigma",
                        "description": "Progression to infectious"
                    }
                ],
                "parameters": [
                    {
                        "name": "beta_base",
                        "value": 0.00001,
                        "unit": "per day",
                        "source": "Table 1",
                        "uncertainty": "±20%",
                        "type": "CONSTANT"
                    },
                    {
                        "name": "sigma",
                        "value": 0.4,
                        "unit": "per day",
                        "source": "Table 1",
                        "uncertainty": "±10%",
                        "type": "CONSTANT"
                    }
                ],
                "stratification": {
                    "groups": [
                        {
                            "name": "AgeGroup",
                            "values": ["0-17", "18-64", "65+"]
                        }
                    ]
                },
                "initialConditions": {
                    "Susceptible": 14700000,
                    "Exposed": 5000,
                    "Infectious": 3000
                },
                "totalPopulation": 14800000
            },
            "gaps": [
                {
                    "type": "missing_parameter",
                    "description": "Natural death rate not specified",
                    "severity": "low"
                }
            ]
        }
    
    def export_templates(self, output_dir: str):
        """Export templates and examples"""
        output_path = Path(output_dir)
        output_path.mkdir(parents=True, exist_ok=True)
        
        # Export template
        template = self.create_extraction_template()
        template_path = output_path / 'extraction_template.json'
        with open(template_path, 'w', encoding='utf-8') as f:
            json.dump(template, f, indent=2, ensure_ascii=False)
        
        # Export example
        example = self.create_example_extraction()
        example_path = output_path / 'extraction_example.json'
        with open(example_path, 'w', encoding='utf-8') as f:
            json.dump(example, f, indent=2, ensure_ascii=False)
        
        return template_path, example_path


def main():
    """Main function to create extraction templates"""
    output_dir = Path(__file__).parent.parent / 'reports' / 'manual_extraction'
    output_dir.mkdir(parents=True, exist_ok=True)
    
    print("=" * 80)
    print("TASK 3.2: MANUAL MODEL EXTRACTION PRACTICE")
    print("=" * 80)
    
    template_builder = ManualExtractionTemplate()
    
    template_path, example_path = template_builder.export_templates(str(output_dir))
    
    print(f"✓ Extraction template exported to: {template_path}")
    print(f"✓ Example extraction exported to: {example_path}")
    
    print("\nUse these templates to manually extract models from papers.")
    print("The extracted models will serve as 'ground truth' for training AI later.")
    
    print("\n" + "=" * 80)
    print("MANUAL EXTRACTION TEMPLATES CREATED")
    print("=" * 80)


if __name__ == '__main__':
    main()

