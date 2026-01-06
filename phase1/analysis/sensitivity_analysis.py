"""
Task 2.3: Example Uncertainty Analysis

Pick one model (COVID-19) and run sensitivity analysis:
1. Identify key parameter: β (transmission rate)
2. Run simulation with different parameter values
3. Compare: Peak infections, time to peak, total cases
4. Create plots showing the range
"""
import json
from pathlib import Path
from typing import Dict, List, Any, Tuple
import sys

try:
    import numpy as np
    HAS_NUMPY = True
except ImportError:
    HAS_NUMPY = False
    print("Warning: numpy not installed. Sensitivity analysis will use simplified calculations.")

sys.path.insert(0, str(Path(__file__).parent.parent / 'utils'))
from xml_parser import CompModelParser


class SensitivityAnalyzer:
    """Perform sensitivity analysis on model parameters"""
    
    def __init__(self, model_path: str, model_name: str):
        """Initialize sensitivity analyzer"""
        self.model_path = Path(model_path)
        self.model_name = model_name
        self.parser = CompModelParser(str(model_path))
        self.data = self.parser.extract_all()
    
    def identify_key_parameters(self) -> List[Dict[str, Any]]:
        """Identify key parameters for sensitivity analysis"""
        key_params = []
        
        for param in self.data['parameters']:
            param_name = param['name'].lower()
            
            # Identify transmission-related parameters
            if any(kw in param_name for kw in ['beta', 'transmission', 'contact']):
                key_params.append({
                    'parameter': param['name'],
                    'type': 'transmission',
                    'currentValue': float(param.get('expression', 0)) if param.get('expression', '').replace('.', '').replace('-', '').isdigit() else param.get('expression', ''),
                    'description': param.get('description', ''),
                    'importance': 'high'
                })
            
            # Identify recovery-related parameters
            elif any(kw in param_name for kw in ['gamma', 'recovery']):
                key_params.append({
                    'parameter': param['name'],
                    'type': 'recovery',
                    'currentValue': float(param.get('expression', 0)) if param.get('expression', '').replace('.', '').replace('-', '').isdigit() else param.get('expression', ''),
                    'description': param.get('description', ''),
                    'importance': 'high'
                })
            
            # Identify progression-related parameters
            elif any(kw in param_name for kw in ['sigma', 'rho', 'progression']):
                key_params.append({
                    'parameter': param['name'],
                    'type': 'progression',
                    'currentValue': float(param.get('expression', 0)) if param.get('expression', '').replace('.', '').replace('-', '').isdigit() else param.get('expression', ''),
                    'description': param.get('description', ''),
                    'importance': 'medium'
                })
        
        return key_params
    
    def simulate_simple_seir(self, beta: float, gamma: float, sigma: float = 0.2,
                            initial_s: float = 9900, initial_e: float = 50,
                            initial_i: float = 50, initial_r: float = 0,
                            days: int = 100, dt: float = 0.1) -> Dict[str, Any]:
        """Simple SEIR simulation for sensitivity analysis"""
        n = initial_s + initial_e + initial_i + initial_r
        
        if HAS_NUMPY:
            times = np.arange(0, days, dt)
            s = np.zeros(len(times))
            e = np.zeros(len(times))
            i = np.zeros(len(times))
            r = np.zeros(len(times))
        else:
            # Fallback without numpy
            num_steps = int(days / dt)
            times = [i * dt for i in range(num_steps)]
            s = [0.0] * num_steps
            e = [0.0] * num_steps
            i = [0.0] * num_steps
            r = [0.0] * num_steps
        
        s[0] = initial_s
        e[0] = initial_e
        i[0] = initial_i
        r[0] = initial_r
        
        for t in range(1, len(times)):
            # SEIR equations
            ds_dt = -beta * s[t-1] * i[t-1] / n
            de_dt = beta * s[t-1] * i[t-1] / n - sigma * e[t-1]
            di_dt = sigma * e[t-1] - gamma * i[t-1]
            dr_dt = gamma * i[t-1]
            
            s[t] = s[t-1] + ds_dt * dt
            e[t] = e[t-1] + de_dt * dt
            i[t] = i[t-1] + di_dt * dt
            r[t] = r[t-1] + dr_dt * dt
            
            # Ensure non-negative
            s[t] = max(0, s[t])
            e[t] = max(0, e[t])
            i[t] = max(0, i[t])
            r[t] = max(0, r[t])
        
        # Calculate metrics
        if HAS_NUMPY:
            peak_infections = np.max(i)
            peak_time = times[np.argmax(i)]
        else:
            peak_infections = max(i)
            peak_time = times[i.index(max(i))]
        total_cases = initial_i + initial_e + (n - s[-1])
        
        return {
            'times': times.tolist(),
            'susceptible': s.tolist(),
            'exposed': e.tolist(),
            'infectious': i.tolist(),
            'recovered': r.tolist(),
            'peakInfections': float(peak_infections),
            'peakTime': float(peak_time),
            'totalCases': float(total_cases),
            'parameters': {
                'beta': beta,
                'gamma': gamma,
                'sigma': sigma
            }
        }
    
    def run_sensitivity_analysis(self, parameter_name: str, 
                                 baseline_value: float,
                                 variations: List[float] = None) -> Dict[str, Any]:
        """Run sensitivity analysis on a parameter"""
        if variations is None:
            # Default: ±20% variation
            variations = [
                baseline_value * 0.8,  # Low
                baseline_value,        # Baseline
                baseline_value * 1.2   # High
            ]
        
        results = []
        
        # For COVID-19 example, use simple SEIR
        if self.model_name.lower() == 'covid-19':
            # Extract baseline parameters (simplified)
            beta_baseline = baseline_value
            gamma_baseline = 0.1  # Default recovery rate
            sigma_baseline = 0.2  # Default progression rate
            
            for var_value in variations:
                if parameter_name.lower() in ['beta', 'transmission']:
                    sim_result = self.simulate_simple_seir(
                        beta=var_value,
                        gamma=gamma_baseline,
                        sigma=sigma_baseline
                    )
                elif parameter_name.lower() in ['gamma', 'recovery']:
                    sim_result = self.simulate_simple_seir(
                        beta=beta_baseline,
                        gamma=var_value,
                        sigma=sigma_baseline
                    )
                else:
                    sim_result = self.simulate_simple_seir(
                        beta=beta_baseline,
                        gamma=gamma_baseline,
                        sigma=sigma_baseline
                    )
                
                results.append({
                    'parameterValue': float(var_value),
                    'variationPercent': ((var_value - baseline_value) / baseline_value * 100) if baseline_value != 0 else 0,
                    'peakInfections': sim_result['peakInfections'],
                    'peakTime': sim_result['peakTime'],
                    'totalCases': sim_result['totalCases']
                })
        
        # Calculate sensitivity metrics
        baseline_result = next((r for r in results if abs(r['variationPercent']) < 0.01), results[1])
        
        sensitivity_metrics = []
        for result in results:
            if result != baseline_result:
                peak_sensitivity = ((result['peakInfections'] - baseline_result['peakInfections']) / 
                                  baseline_result['peakInfections']) / (result['variationPercent'] / 100)
                time_sensitivity = ((result['peakTime'] - baseline_result['peakTime']) / 
                                   baseline_result['peakTime']) / (result['variationPercent'] / 100)
                
                sensitivity_metrics.append({
                    'parameterValue': result['parameterValue'],
                    'peakSensitivity': float(peak_sensitivity),
                    'timeSensitivity': float(time_sensitivity),
                    'totalCasesChange': ((result['totalCases'] - baseline_result['totalCases']) / 
                                        baseline_result['totalCases'] * 100)
                })
        
        return {
            'parameterName': parameter_name,
            'baselineValue': baseline_value,
            'variations': variations,
            'results': results,
            'baselineResult': baseline_result,
            'sensitivityMetrics': sensitivity_metrics,
            'summary': {
                'peakRange': [min(r['peakInfections'] for r in results),
                             max(r['peakInfections'] for r in results)],
                'timeRange': [min(r['peakTime'] for r in results),
                             max(r['peakTime'] for r in results)],
                'casesRange': [min(r['totalCases'] for r in results),
                              max(r['totalCases'] for r in results)]
            }
        }
    
    def generate_sensitivity_report(self) -> Dict[str, Any]:
        """Generate comprehensive sensitivity analysis report"""
        key_params = self.identify_key_parameters()
        
        if not key_params:
            return {
                'modelName': self.model_name,
                'message': 'No key parameters identified for sensitivity analysis',
                'note': 'This is a template - actual simulation requires model-specific implementation'
            }
        
        # Focus on first key parameter (usually transmission rate)
        main_param = key_params[0]
        
        # Run sensitivity analysis
        if isinstance(main_param['currentValue'], (int, float)):
            baseline = main_param['currentValue']
            sensitivity = self.run_sensitivity_analysis(
                main_param['parameter'],
                baseline
            )
        else:
            # Use default value for demonstration
            baseline = 0.00001 if 'beta' in main_param['parameter'].lower() else 0.1
            sensitivity = self.run_sensitivity_analysis(
                main_param['parameter'],
                baseline
            )
        
        return {
            'modelName': self.model_name,
            'keyParameters': key_params,
            'sensitivityAnalysis': sensitivity,
            'interpretation': {
                'parameter': main_param['parameter'],
                'impact': 'High' if abs(sensitivity['sensitivityMetrics'][0]['peakSensitivity']) > 1 else 'Medium',
                'recommendation': 'This parameter significantly affects model predictions. Ensure accurate estimation.'
            }
        }
    
    def export_sensitivity_report(self, output_path: str):
        """Export sensitivity report to JSON"""
        report = self.generate_sensitivity_report()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            json.dump(report, f, indent=2, ensure_ascii=False)
    
    def export_markdown(self, output_path: str):
        """Export sensitivity report to Markdown"""
        report = self.generate_sensitivity_report()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            f.write(f"# Sensitivity Analysis Report: {report['modelName']}\n\n")
            
            if 'message' in report:
                f.write(f"{report['message']}\n\n")
                f.write(f"**Note:** {report['note']}\n")
                return
            
            f.write("## Key Parameters Identified\n\n")
            for param in report['keyParameters']:
                f.write(f"- **{param['parameter']}** ({param['type']}): {param['description']}\n")
            f.write("\n")
            
            sens = report['sensitivityAnalysis']
            f.write(f"## Sensitivity Analysis: {sens['parameterName']}\n\n")
            f.write(f"**Baseline Value:** {sens['baselineValue']}\n\n")
            
            f.write("### Results\n\n")
            f.write("| Parameter Value | Variation % | Peak Infections | Peak Time (days) | Total Cases |\n")
            f.write("|-----------------|-------------|----------------|------------------|-------------|\n")
            for result in sens['results']:
                f.write(f"| {result['parameterValue']:.6f} | {result['variationPercent']:.1f}% | "
                       f"{result['peakInfections']:.2f} | {result['peakTime']:.1f} | "
                       f"{result['totalCases']:.2f} |\n")
            f.write("\n")
            
            f.write("### Summary\n\n")
            f.write(f"- **Peak Infections Range:** {sens['summary']['peakRange'][0]:.2f} - {sens['summary']['peakRange'][1]:.2f}\n")
            f.write(f"- **Peak Time Range:** {sens['summary']['timeRange'][0]:.1f} - {sens['summary']['timeRange'][1]:.1f} days\n")
            f.write(f"- **Total Cases Range:** {sens['summary']['casesRange'][0]:.2f} - {sens['summary']['casesRange'][1]:.2f}\n\n")
            
            f.write("### Interpretation\n\n")
            f.write(f"**Parameter:** {report['interpretation']['parameter']}\n\n")
            f.write(f"**Impact:** {report['interpretation']['impact']}\n\n")
            f.write(f"**Recommendation:** {report['interpretation']['recommendation']}\n")


def main():
    """Main function to run sensitivity analysis"""
    base_path = Path(__file__).parent.parent.parent / 'Compartmental' / 'CompartmentalModel'
    output_dir = Path(__file__).parent.parent / 'reports' / 'sensitivity'
    output_dir.mkdir(parents=True, exist_ok=True)
    
    # Focus on COVID-19 for Task 2.3
    covid_path = base_path / 'covid.compmodel'
    
    if not covid_path.exists():
        print(f"Warning: {covid_path} not found")
        return
    
    print("=" * 80)
    print("TASK 2.3: EXAMPLE UNCERTAINTY ANALYSIS (SENSITIVITY ANALYSIS)")
    print("=" * 80)
    
    analyzer = SensitivityAnalyzer(str(covid_path), 'COVID-19')
    
    # Export JSON
    json_path = output_dir / 'covid_sensitivity_analysis.json'
    analyzer.export_sensitivity_report(str(json_path))
    print(f"✓ Sensitivity report exported to: {json_path}")
    
    # Export Markdown
    md_path = output_dir / 'covid_sensitivity_analysis.md'
    analyzer.export_markdown(str(md_path))
    print(f"✓ Sensitivity report exported to: {md_path}")
    
    report = analyzer.generate_sensitivity_report()
    if 'sensitivityAnalysis' in report:
        sens = report['sensitivityAnalysis']
        print(f"\nParameter analyzed: {sens['parameterName']}")
        print(f"Baseline value: {sens['baselineValue']}")
        print(f"Peak infections range: {sens['summary']['peakRange'][0]:.2f} - {sens['summary']['peakRange'][1]:.2f}")
        print(f"Peak time range: {sens['summary']['timeRange'][0]:.1f} - {sens['summary']['timeRange'][1]:.1f} days")
    
    print("\n" + "=" * 80)
    print("SENSITIVITY ANALYSIS COMPLETE")
    print("=" * 80)


if __name__ == '__main__':
    main()

