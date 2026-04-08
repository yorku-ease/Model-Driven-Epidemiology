"""
Task 1.1: Analyze Current Models

Go through each existing model and document:
- All compartments and their meanings
- All flows and their types (Contact, Rate, External)
- All parameters and where they came from
- Stratification (age groups, how they work)
- What information is complete? What's uncertain? What did I guess?
"""
import json
import sys
from pathlib import Path

try:
    import pandas as pd
    HAS_PANDAS = True
except ImportError:
    HAS_PANDAS = False
    print("Warning: pandas not installed. Excel/CSV export will be disabled.")
    print("Install with: pip install pandas openpyxl")

# Add parent directory to path for imports
sys.path.insert(0, str(Path(__file__).parent.parent))

from utils.xml_parser import CompModelParser
from utils.phase1_paths import resolve_default_model_dir, find_compmodel_files


class ModelAnalyzer:
    """Analyze compartmental models and extract structured information"""
    
    def __init__(self, model_path: str, model_name: str):
        """Initialize analyzer with model file"""
        self.model_path = Path(model_path)
        self.model_name = model_name
        self.parser = CompModelParser(str(model_path))
        self.data = self.parser.extract_all()
    
    def analyze_compartments(self):
        """Analyze compartments and create structured data"""
        rows = []
        for comp in self.data['compartments']:
            # Count flows by type
            flow_types = {}
            for flow in comp['outgoingFlows']:
                flow_type = flow['type']
                flow_types[flow_type] = flow_types.get(flow_type, 0) + 1
            
            rows.append({
                'Model': self.model_name,
                'Type': 'Compartment',
                'Name': comp['primaryName'],
                'SecondaryName': comp.get('secondaryName', ''),
                'Population': comp.get('population', '0'),
                'HasStratification': bool(comp.get('product', '')),
                'NumFlows': len(comp['outgoingFlows']),
                'FlowTypes': ', '.join(flow_types.keys()) if flow_types else 'None',
                'Source': 'Model file',
                'Confidence': 'Certain',  # Will be updated manually
                'Notes': ''
            })
        
        if HAS_PANDAS:
            return pd.DataFrame(rows)
        return rows
    
    def analyze_flows(self):
        """Analyze flows and create structured data"""
        rows = []
        for comp in self.data['compartments']:
            for flow in comp['outgoingFlows']:
                # Determine source and target compartments
                source = comp['primaryName']
                if comp.get('secondaryName'):
                    source += f" ({comp['secondaryName']})"
                
                # Get target compartment index
                target_ref = flow.get('target', '')
                target_name = 'Unknown'
                if target_ref.startswith('//@compartments.'):
                    try:
                        idx = int(target_ref.split('.')[-1])
                        if idx < len(self.data['compartments']):
                            target_comp = self.data['compartments'][idx]
                            target_name = target_comp['primaryName']
                            if target_comp.get('secondaryName'):
                                target_name += f" ({target_comp['secondaryName']})"
                    except (ValueError, IndexError):
                        pass
                
                flow_type = flow['type']
                rate_value = flow.get('rate') or flow.get('rateParameter') or flow.get('contactRate') or flow.get('contactRateParameter', '')
                
                rows.append({
                    'Model': self.model_name,
                    'Type': 'Flow',
                    'Source': source,
                    'Target': target_name,
                    'FlowType': flow_type,
                    'Rate': rate_value,
                    'HasStratumSpecificRates': len(flow.get('stratumSpecificRates', [])) > 0,
                    'Description': flow.get('description', ''),
                    'Source': 'Model file',
                    'Confidence': 'Certain',
                    'Notes': ''
                })
        
        if HAS_PANDAS:
            return pd.DataFrame(rows)
        return rows
    
    def analyze_parameters(self):
        """Analyze parameters and create structured data"""
        rows = []
        for param in self.data['parameters']:
            rows.append({
                'Model': self.model_name,
                'Type': 'Parameter',
                'Name': param['name'],
                'ParameterType': param.get('type', 'CONSTANT'),
                'Value': param.get('expression', ''),
                'Unit': param.get('unit', ''),
                'Description': param.get('description', ''),
                'Source': 'Model file',
                'Confidence': 'Certain',  # Will be updated manually
                'Notes': ''
            })

        if HAS_PANDAS:
            return pd.DataFrame(rows)
        return rows

    def analyze_stratification(self):
        """Analyze stratification (groups and products)"""
        rows = []
        
        # Groups
        for group in self.data['groups']:
            rows.append({
                'Model': self.model_name,
                'Type': 'Stratification',
                'Category': 'Group',
                'Name': group['name'],
                'Values': ', '.join(group['values']),
                'Description': group.get('description', ''),
                'Source': 'Model file',
                'Confidence': 'Certain',
                'Notes': ''
            })
        
        # Products
        for product in self.data['products']:
            rows.append({
                'Model': self.model_name,
                'Type': 'Stratification',
                'Category': 'Product',
                'Name': product['name'],
                'Values': product.get('groups', ''),
                'Description': product.get('description', ''),
                'Source': 'Model file',
                'Confidence': 'Certain',
                'Notes': ''
            })

        if HAS_PANDAS:
            return pd.DataFrame(rows)
        return rows

    def generate_summary(self) -> dict:
        """Generate summary statistics"""
        return {
            'modelName': self.model_name,
            'totalPopulation': self.data['modelInfo'].get('totalPopulation', 'Unknown'),
            'numCompartments': len(self.data['compartments']),
            'numParameters': len(self.data['parameters']),
            'numGroups': len(self.data['groups']),
            'numProducts': len(self.data['products']),
            'numExternalSources': len(self.data['externalSources']),
            'numExternalSinks': len(self.data['externalSinks']),
            'hasStratification': len(self.data['groups']) > 0,
            'hasParameters': len(self.data['parameters']) > 0,
            'hasVectorCompartments': any('mosquito' in comp['primaryName'].lower() 
                                       for comp in self.data['compartments']),
            'hasTemperatureDependent': any('T' in param.get('expression', '') or 
                                          param.get('type') == 'VARIABLE'
                                          for param in self.data['parameters'])
        }
    
    def export_to_json(self, output_path: str):
        """Export full analysis to JSON"""
        output = {
            'modelName': self.model_name,
            'summary': self.generate_summary(),
            'data': self.data
        }
        
        with open(output_path, 'w', encoding='utf-8') as f:
            json.dump(output, f, indent=2, ensure_ascii=False)
    
    def export_to_dataframe(self):
        """Export all analysis to a single DataFrame or list"""
        compartments_data = self.analyze_compartments()
        flows_data = self.analyze_flows()
        parameters_data = self.analyze_parameters()
        stratification_data = self.analyze_stratification()
        
        if HAS_PANDAS:
            # Combine all DataFrames
            all_data = pd.concat([
                compartments_data,
                flows_data,
                parameters_data,
                stratification_data
            ], ignore_index=True)
            return all_data
        else:
            # Return combined list
            return compartments_data + flows_data + parameters_data + stratification_data


def main():
    """
    Main function to analyze all models in a directory.
    Automatically discovers .compmodel files and processes them.
    """
    import argparse
    
    parser = argparse.ArgumentParser(
        description='Analyze compartmental models and extract structured information',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  # Analyze all models in default directories (epimde + phase 2/data/diseases)
  python3 model_analyzer.py

  # Analyze models in specific directory
  python3 model_analyzer.py --model-dir /path/to/models

  # Analyze specific model file
  python3 model_analyzer.py --model-file model.compmodel --model-name "Model Name"
        """
    )
    
    parser.add_argument('--model-dir', 
                       help='Directory containing .compmodel files (default: papers/epimde, else phase 2/data/diseases, else phase 2 baselines)')
    parser.add_argument('--model-file', 
                       help='Single model file to analyze (overrides --model-dir)')
    parser.add_argument('--model-name', 
                       help='Name for the model (required if --model-file specified)')
    parser.add_argument('--output-dir',
                       help='Output directory for reports (default: reports/model_analysis)')
    
    args = parser.parse_args()
    
    # Determine model directory
    if args.model_file:
        # Single file mode
        model_path = Path(args.model_file)
        if not model_path.exists():
            print(f"Error: Model file not found: {model_path}")
            return
        
        if not args.model_name:
            print("Error: --model-name is required when using --model-file")
            return
        
        models = [(args.model_name, model_path)]
        model_dir = model_path.parent
    else:
        # Directory mode - find all .compmodel files
        if args.model_dir:
            model_dir = Path(args.model_dir)
        else:
            model_dir = resolve_default_model_dir()
        
        models = find_compmodel_files(model_dir)
        
        if not models:
            print(f"No .compmodel files found in: {model_dir}")
            print("\nTo analyze a specific file, use:")
            print("  python3 model_analyzer.py --model-file <file.compmodel> --model-name '<Model Name>'")
            return
    
    # Determine output directory
    if args.output_dir:
        output_dir = Path(args.output_dir)
    else:
        output_dir = Path(__file__).parent.parent / 'reports' / 'model_analysis'
    output_dir.mkdir(parents=True, exist_ok=True)
    
    all_analyses = []
    all_dataframes = []
    
    print("=" * 80)
    print("TASK 1.1: ANALYZING MODELS")
    print("=" * 80)
    print(f"Model directory: {model_dir}")
    print(f"Found {len(models)} model(s) to analyze")
    print()
    
    for model_name, model_path in models:
        if not model_path.exists():
            print(f"Warning: {model_path} not found, skipping {model_name}")
            continue
        
        print(f"\nAnalyzing {model_name}...")
        print(f"  File: {model_path}")
        
        try:
            analyzer = ModelAnalyzer(str(model_path), model_name)
            
            # Generate summary
            summary = analyzer.generate_summary()
            print(f"  Compartments: {summary['numCompartments']}")
            print(f"  Parameters: {summary['numParameters']}")
            print(f"  Groups: {summary['numGroups']}")
            print(f"  Has Stratification: {summary['hasStratification']}")
            print(f"  Has Vector Compartments: {summary['hasVectorCompartments']}")
            print(f"  Has Temperature Dependent: {summary['hasTemperatureDependent']}")
            
            # Export JSON
            json_filename = f"{model_name.lower().replace(' ', '_').replace('-', '_')}_analysis.json"
            json_path = output_dir / json_filename
            analyzer.export_to_json(str(json_path))
            print(f"  ✓ Exported JSON to: {json_path}")
            
            # Export DataFrame
            df = analyzer.export_to_dataframe()
            all_dataframes.append(df)
            all_analyses.append({
                'model': model_name,
                'summary': summary,
                'dataframe': df
            })
            
        except Exception as e:
            print(f"  ✗ Error analyzing {model_name}: {e}")
            import traceback
            traceback.print_exc()
            continue
    
    if not all_analyses:
        print("\nNo models were successfully analyzed.")
        return
    
    # Combine all DataFrames
    if all_dataframes:
        if HAS_PANDAS:
            combined_df = pd.concat(all_dataframes, ignore_index=True)
            excel_path = output_dir / 'all_models_analysis.xlsx'
            try:
                combined_df.to_excel(excel_path, index=False, engine='openpyxl')
                print(f"\n✓ Combined analysis exported to: {excel_path}")
            except Exception as e:
                print(f"Warning: Could not export to Excel: {e}")
            
            # Also export as CSV
            csv_path = output_dir / 'all_models_analysis.csv'
            combined_df.to_csv(csv_path, index=False)
            print(f"✓ Combined analysis exported to: {csv_path}")
        else:
            # Export as JSON instead
            json_path = output_dir / 'all_models_analysis.json'
            with open(json_path, 'w') as f:
                json.dump(all_dataframes, f, indent=2)
            print(f"\n✓ Combined analysis exported to: {json_path}")
    
    # Generate summary report
    summary_path = output_dir / 'summary_report.txt'
    with open(summary_path, 'w') as f:
        f.write("=" * 80 + "\n")
        f.write("MODEL ANALYSIS SUMMARY\n")
        f.write("=" * 80 + "\n\n")
        
        for analysis in all_analyses:
            f.write(f"Model: {analysis['model']}\n")
            f.write(f"  Compartments: {analysis['summary']['numCompartments']}\n")
            f.write(f"  Parameters: {analysis['summary']['numParameters']}\n")
            f.write(f"  Groups: {analysis['summary']['numGroups']}\n")
            f.write(f"  External Sources: {analysis['summary']['numExternalSources']}\n")
            f.write(f"  External Sinks: {analysis['summary']['numExternalSinks']}\n")
            f.write(f"  Has Stratification: {analysis['summary']['hasStratification']}\n")
            f.write(f"  Has Vector Compartments: {analysis['summary']['hasVectorCompartments']}\n")
            f.write(f"  Has Temperature Dependent: {analysis['summary']['hasTemperatureDependent']}\n")
            f.write("\n")
    
    print(f"\n✓ Summary report exported to: {summary_path}")
    print("\n" + "=" * 80)
    print("ANALYSIS COMPLETE")
    print("=" * 80)
    print(f"\nAnalyzed {len(all_analyses)} model(s) successfully")


if __name__ == '__main__':
    main()

