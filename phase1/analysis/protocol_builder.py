"""
Task 1.2: Formalize "How to Fill In" for Models

Create a checklist/template for building models from papers with step-by-step protocol.
"""
import json
from pathlib import Path
from typing import Dict, List, Any


class ProtocolBuilder:
    """Build formal protocol for extracting models from papers"""
    
    def __init__(self):
        self.protocol = {
            'title': 'Protocol for Building Compartmental Models from Research Papers',
            'version': '1.0',
            'description': 'Step-by-step guide for extracting epidemiological models from academic papers',
            'steps': []
        }
    
    def build_protocol(self) -> Dict[str, Any]:
        """Build the complete protocol"""
        
        # Step 1: Initial Reading
        self.protocol['steps'].append({
            'step': 1,
            'title': 'Initial Paper Reading',
            'description': 'Read paper abstract and introduction to identify disease and model type',
            'actions': [
                'Read abstract to understand the research question',
                'Read introduction to identify the disease being modeled',
                'Identify model type (SEIR, SEIRS, SIR, etc.)',
                'Note if paper mentions stratification, interventions, or special features'
            ],
            'output': 'Disease name, model type, special features mentioned'
        })
        
        # Step 2: Find Methods Section
        self.protocol['steps'].append({
            'step': 2,
            'title': 'Locate Model Description',
            'description': 'Find Methods section and look for "Mathematical Model" or "Model Structure"',
            'actions': [
                'Navigate to Methods/Modeling section',
                'Look for headings: "Mathematical Model", "Model Structure", "Compartmental Model"',
                'Find model diagram (usually Figure 1 or Figure 2)',
                'Locate equation list or model equations'
            ],
            'output': 'Model diagram reference, equation section location'
        })
        
        # Step 3: Extract Compartments
        self.protocol['steps'].append({
            'step': 3,
            'title': 'Extract Compartments',
            'description': 'Identify all compartments from diagram, equations, or text',
            'sources': [
                {
                    'source': 'Model Diagram (Figure X)',
                    'method': 'Look for boxes/circles representing population states',
                    'examples': ['S (Susceptible)', 'E (Exposed)', 'I (Infectious)', 'R (Recovered)']
                },
                {
                    'source': 'Equation List',
                    'method': 'Each differential equation typically represents one compartment',
                    'examples': ['dS/dt = ... → S compartment', 'dI/dt = ... → I compartment']
                },
                {
                    'source': 'Text Description',
                    'method': 'Look for phrases like "The model includes compartments for..."',
                    'examples': ['"Susceptible individuals (S)"', '"Infected individuals (I)"']
                }
            ],
            'actions': [
                'List all compartment names',
                'Note primary names (e.g., "Susceptible")',
                'Note secondary names if present (e.g., "presymptomatic", "quarantined")',
                'Record initial population values if given'
            ],
            'output': 'List of compartments with names and initial values'
        })
        
        # Step 4: Extract Flows
        self.protocol['steps'].append({
            'step': 4,
            'title': 'Extract Flows',
            'description': 'Identify flows between compartments by reading equations',
            'flow_types': [
                {
                    'type': 'ContactFlow',
                    'pattern': 'Terms with S*I/N or β*S*I/N',
                    'example': 'dS/dt = -β*S*I/N → ContactFlow from S to E',
                    'characteristics': [
                        'Involves two compartments (source and contact)',
                        'Rate depends on product of two populations',
                        'Usually represents transmission/infection'
                    ]
                },
                {
                    'type': 'RateFlow',
                    'pattern': 'Terms with rate*Compartment',
                    'example': 'dE/dt = ... - σ*E → RateFlow from E to I',
                    'characteristics': [
                        'Single compartment with fixed rate',
                        'Represents progression, recovery, or transition',
                        'Rate is constant (not dependent on other compartments)'
                    ]
                },
                {
                    'type': 'ExternalSource',
                    'pattern': 'Terms with + (inflow from outside)',
                    'example': 'dS/dt = + π*N → ExternalSource to S',
                    'characteristics': [
                        'Positive term not from another compartment',
                        'Represents births, recruitment, or external input',
                        'May be optional for short-term models'
                    ]
                },
                {
                    'type': 'ExternalSink',
                    'pattern': 'Terms with - (outflow not to another compartment)',
                    'example': 'dS/dt = ... - μ*S → ExternalSink from S',
                    'characteristics': [
                        'Negative term not going to another compartment',
                        'Represents deaths or external output',
                        'May be optional for short-term models'
                    ]
                }
            ],
            'actions': [
                'Read each differential equation',
                'Identify positive terms (inflows)',
                'Identify negative terms (outflows)',
                'Classify each term as ContactFlow, RateFlow, ExternalSource, or ExternalSink',
                'Note the rate parameter or value for each flow'
            ],
            'output': 'List of flows with source, target, type, and rate'
        })
        
        # Step 5: Extract Parameters
        self.protocol['steps'].append({
            'step': 5,
            'title': 'Extract Parameters',
            'description': 'Find all parameter values from tables, text, or supplementary materials',
            'sources': [
                {
                    'source': 'Table 1: Model Parameters',
                    'method': 'Most common location - look for parameter table',
                    'columns': ['Parameter', 'Symbol', 'Value', 'Unit', 'Description', 'Source']
                },
                {
                    'source': 'Inline Text',
                    'method': 'Look for phrases like "transmission rate β = 0.3"',
                    'example': '"We used a transmission rate of β = 0.3 per day"'
                },
                {
                    'source': 'Supplementary Materials',
                    'method': 'Check supplementary files for additional parameters',
                    'note': 'Some papers put detailed parameter tables in supplements'
                },
                {
                    'source': 'References to Other Papers',
                    'method': 'Parameters may cite values from previous studies',
                    'action': 'Note the reference and try to locate original source'
                }
            ],
            'actions': [
                'Locate parameter table (usually Table 1)',
                'Extract parameter name, symbol, value, unit, description',
                'Check for uncertainty information (ranges, confidence intervals)',
                'Note if parameter is constant, variable, or expression',
                'Record source (table, text, supplementary, reference)'
            ],
            'output': 'List of parameters with values, units, and sources'
        })
        
        # Step 6: Extract Stratification
        self.protocol['steps'].append({
            'step': 6,
            'title': 'Extract Stratification',
            'description': 'Identify population stratification if mentioned',
            'indicators': [
                'Look for "age groups", "age-specific", "stratified"',
                'Check if parameters have subscripts (β_child, β_adult)',
                'Look for separate equations for different groups',
                'Check model diagram for multiple parallel compartments'
            ],
            'actions': [
                'Search text for stratification keywords',
                'Identify group categories (age, gender, risk, location)',
                'List group values (e.g., "0-17", "18-64", "65+")',
                'Note if parameters differ by stratum',
                'Check if flows have stratum-specific rates'
            ],
            'output': 'Stratification groups and values (if present)',
            'note': 'Stratification is OPTIONAL - only extract if paper mentions it'
        })
        
        # Step 7: Record Initial Conditions
        self.protocol['steps'].append({
            'step': 7,
            'title': 'Record Initial Conditions',
            'description': 'Extract initial population values for each compartment',
            'sources': [
                'Text: "We initialized with S(0) = X, I(0) = Y..."',
                'Table: Initial conditions table',
                'Figure caption: Initial values in simulation description'
            ],
            'actions': [
                'Find initial condition statement',
                'Extract population value for each compartment',
                'Calculate total population if not given',
                'Note if initial conditions are stratified'
            ],
            'output': 'Initial population values for each compartment'
        })
        
        # Step 8: Note Missing Information
        self.protocol['steps'].append({
            'step': 8,
            'title': 'Document Missing Information',
            'description': 'Identify what information is missing or uncertain',
            'categories': [
                {
                    'category': 'Missing Parameters',
                    'examples': [
                        'Parameter mentioned but no value given',
                        'Parameter referenced but not defined',
                        'Parameter value in unreferenced paper'
                    ]
                },
                {
                    'category': 'Vague Descriptions',
                    'examples': [
                        '"We used standard mortality rate" (no value)',
                        '"Typical recovery rate" (not specified)',
                        '"Based on literature" (no citation)'
                    ]
                },
                {
                    'category': 'Unavailable References',
                    'examples': [
                        'Referenced paper not accessible',
                        'Parameter from unpublished data',
                        'Value from private communication'
                    ]
                },
                {
                    'category': 'Uncertain Values',
                    'examples': [
                        'Parameter with wide range',
                        'Value estimated from other studies',
                        'Assumed value without justification'
                    ]
                }
            ],
            'actions': [
                'List all mentioned but undefined parameters',
                'Note vague parameter descriptions',
                'Record inaccessible references',
                'Mark uncertain values with confidence level',
                'Document assumptions made'
            ],
            'output': 'List of gaps and uncertainties'
        })
        
        return self.protocol
    
    def export_protocol(self, output_path: str):
        """Export protocol to JSON file"""
        protocol = self.build_protocol()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            json.dump(protocol, f, indent=2, ensure_ascii=False)
    
    def export_markdown(self, output_path: str):
        """Export protocol to Markdown file"""
        protocol = self.build_protocol()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            f.write(f"# {protocol['title']}\n\n")
            f.write(f"**Version:** {protocol['version']}\n\n")
            f.write(f"{protocol['description']}\n\n")
            
            for step in protocol['steps']:
                f.write(f"## Step {step['step']}: {step['title']}\n\n")
                f.write(f"{step['description']}\n\n")
                
                if 'actions' in step:
                    f.write("### Actions:\n\n")
                    for i, action in enumerate(step['actions'], 1):
                        f.write(f"{i}. {action}\n")
                    f.write("\n")
                
                if 'sources' in step:
                    f.write("### Sources:\n\n")
                    for source in step['sources']:
                        if isinstance(source, dict):
                            f.write(f"- **{source['source']}**: {source['method']}\n")
                            if 'examples' in source:
                                for ex in source['examples']:
                                    f.write(f"  - Example: {ex}\n")
                        else:
                            f.write(f"- {source}\n")
                    f.write("\n")
                
                if 'flow_types' in step:
                    f.write("### Flow Types:\n\n")
                    for flow_type in step['flow_types']:
                        f.write(f"- **{flow_type['type']}**:\n")
                        f.write(f"  - Pattern: {flow_type['pattern']}\n")
                        f.write(f"  - Example: {flow_type['example']}\n")
                        if 'characteristics' in flow_type:
                            f.write("  - Characteristics:\n")
                            for char in flow_type['characteristics']:
                                f.write(f"    - {char}\n")
                    f.write("\n")
                
                if 'output' in step:
                    f.write(f"### Output:\n\n{step['output']}\n\n")
                
                if 'note' in step:
                    f.write(f"**Note:** {step['note']}\n\n")


def main():
    """Main function to generate protocol"""
    output_dir = Path(__file__).parent.parent / 'reports' / 'protocols'
    output_dir.mkdir(parents=True, exist_ok=True)
    
    builder = ProtocolBuilder()
    
    # Export JSON
    json_path = output_dir / 'extraction_protocol.json'
    builder.export_protocol(str(json_path))
    print(f"✓ Protocol exported to: {json_path}")
    
    # Export Markdown
    md_path = output_dir / 'extraction_protocol.md'
    builder.export_markdown(str(md_path))
    print(f"✓ Protocol exported to: {md_path}")
    
    print("\nProtocol generation complete!")


if __name__ == '__main__':
    main()

