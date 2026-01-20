"""
Task 2.3: Enhanced Sensitivity Analysis

Implements multiple sensitivity analysis methods:
- Grid search: systematic exploration of parameter space
- Random search: random sampling of parameter combinations
- Morris method: one-at-a-time sensitivity analysis
- Sobol sequences: quasi-random sampling for global sensitivity
- Multi-parameter sensitivity: varying multiple parameters simultaneously
"""
import json
from pathlib import Path
from typing import Dict, List, Any, Tuple, Optional
import sys
import random
import math

try:
    import numpy as np
    HAS_NUMPY = True
except ImportError:
    HAS_NUMPY = False
    print("Warning: numpy not installed. Some methods will be limited.")
    print("Install with: pip install numpy")

sys.path.insert(0, str(Path(__file__).parent.parent / 'utils'))
from xml_parser import CompModelParser


class SensitivityAnalyzer:
    """Enhanced sensitivity analysis with multiple methods"""
    
    def __init__(self, model_path: str, model_name: str):
        """Initialize sensitivity analyzer"""
        self.model_path = Path(model_path)
        self.model_name = model_name
        self.parser = CompModelParser(str(model_path))
        self.data = self.parser.extract_all()
    
    def identify_key_parameters(self) -> List[Dict[str, Any]]:
        """
        Identify key parameters for sensitivity analysis.
        Extracts from both explicit parameters and rates embedded in flows.
        """
        key_params = []
        seen_params = set()  # Track to avoid duplicates
        
        # Method 1: Extract from explicit parameters
        for param in self.data['parameters']:
            param_name = param['name'].lower()
            
            # Try to extract numeric value
            try:
                value = float(param.get('expression', 0))
            except (ValueError, TypeError):
                value = None
            
            if value is not None and value > 0:
                key_params.append({
                    'parameter': param['name'],
                    'type': self._classify_parameter_type(param_name),
                    'currentValue': value,
                    'description': param.get('description', ''),
                    'importance': 'high' if 'transmission' in param_name or 'recovery' in param_name else 'medium',
                    'source': 'explicit_parameter'
                })
                seen_params.add(param['name'])
        
        # Method 2: Extract from flows (for models with rates in flows like COVID-19, HIV)
        flow_params = self._extract_parameters_from_flows()
        for param in flow_params:
            if param['parameter'] not in seen_params:
                key_params.append(param)
                seen_params.add(param['parameter'])
        
        return key_params
    
    def _classify_parameter_type(self, param_name: str) -> str:
        """Classify parameter type based on name"""
        param_lower = param_name.lower()
        
        if any(kw in param_lower for kw in ['beta', 'transmission', 'contact']):
            return 'transmission'
        elif any(kw in param_lower for kw in ['gamma', 'recovery']):
            return 'recovery'
        elif any(kw in param_lower for kw in ['sigma', 'rho', 'progression', 'incubation']):
            return 'progression'
        elif any(kw in param_lower for kw in ['mortality', 'death', 'mu']):
            return 'mortality'
        else:
            return 'other'
    
    def _extract_parameters_from_flows(self) -> List[Dict[str, Any]]:
        """
        Extract parameters from flows (rate, contactRate, etc.)
        This handles models where rates are embedded in flows rather than explicit parameters.
        """
        flow_params = []
        param_values = {}  # Track unique parameter values
        
        for comp in self.data['compartments']:
            for flow in comp.get('outgoingFlows', []):
                # Extract rate values
                rate_fields = ['rate', 'contactRate', 'rateParameter', 'contactRateParameter']
                
                for field in rate_fields:
                    value_str = flow.get(field, '')
                    if value_str and value_str.strip():
                        try:
                            value = float(value_str)
                            if value > 0:
                                # Create parameter name based on flow context
                                source_comp = comp.get('primaryName', 'Unknown')
                                target_ref = flow.get('target', '')
                                flow_type = flow.get('type', 'Flow')
                                
                                # Try to get target compartment name
                                target_name = 'Unknown'
                                if target_ref.startswith('//@compartments.'):
                                    try:
                                        idx = int(target_ref.split('.')[-1])
                                        if idx < len(self.data['compartments']):
                                            target_comp = self.data['compartments'][idx]
                                            target_name = target_comp.get('primaryName', 'Unknown')
                                    except (ValueError, IndexError):
                                        pass
                                
                                # Create descriptive parameter name
                                if field == 'contactRate':
                                    param_name = f"contactRate_{source_comp}_to_{target_name}"
                                elif field == 'rate':
                                    param_name = f"rate_{source_comp}_to_{target_name}"
                                else:
                                    param_name = f"{field}_{source_comp}"
                                
                                # Use most common value if multiple flows have same name
                                if param_name not in param_values:
                                    param_values[param_name] = {
                                        'value': value,
                                        'count': 1,
                                        'source_comp': source_comp,
                                        'target_comp': target_name,
                                        'flow_type': flow_type,
                                        'description': flow.get('description', '')
                                    }
                                else:
                                    # Average if multiple values
                                    existing = param_values[param_name]
                                    existing['value'] = (existing['value'] * existing['count'] + value) / (existing['count'] + 1)
                                    existing['count'] += 1
                        except (ValueError, TypeError):
                            pass
                
                # Extract stratum-specific rates
                for ssr in flow.get('stratumSpecificRates', []):
                    rate_str = ssr.get('rate', '')
                    if rate_str:
                        try:
                            value = float(rate_str)
                            if value > 0:
                                stratum = ssr.get('stratum', '')
                                source_comp = comp.get('primaryName', 'Unknown')
                                param_name = f"rate_{source_comp}_stratum_{stratum}"
                                
                                if param_name not in param_values:
                                    param_values[param_name] = {
                                        'value': value,
                                        'count': 1,
                                        'source_comp': source_comp,
                                        'target_comp': 'Stratified',
                                        'flow_type': 'StratumSpecific',
                                        'description': f"Stratum-specific rate for {stratum}"
                                    }
                        except (ValueError, TypeError):
                            pass
        
        # Convert to parameter list
        for param_name, info in param_values.items():
            # Classify parameter type
            param_type = self._classify_parameter_type(param_name)
            
            # Determine importance
            if param_type in ['transmission', 'recovery']:
                importance = 'high'
            elif param_type == 'progression':
                importance = 'medium'
            else:
                importance = 'low'
            
            flow_params.append({
                'parameter': param_name,
                'type': param_type,
                'currentValue': info['value'],
                'description': info.get('description', 
                    f"{info['flow_type']} from {info['source_comp']} to {info['target_comp']}"),
                'importance': importance,
                'source': 'flow',
                'occurrences': info['count']
            })
        
        # Sort by importance and value
        flow_params.sort(key=lambda x: (
            {'high': 0, 'medium': 1, 'low': 2}.get(x['importance'], 3),
            -x['currentValue']  # Higher values first
        ))
        
        # Limit to top parameters to avoid too many
        return flow_params[:10]  # Top 10 flow-based parameters
    
    def simulate_simple_seir(self, beta: float, gamma: float, sigma: float = 0.2,
                            initial_s: float = 99000, initial_e: float = 500,
                            initial_i: float = 500, initial_r: float = 0,
                            days: int = 200, dt: float = 0.1) -> Dict[str, Any]:
        """
        Simple SEIR simulation for sensitivity analysis.
        Uses realistic population scale and longer simulation time.
        """
        n = initial_s + initial_e + initial_i + initial_r
        
        if HAS_NUMPY:
            times = np.arange(0, days, dt)
            s = np.zeros(len(times))
            e = np.zeros(len(times))
            i = np.zeros(len(times))
            r = np.zeros(len(times))
        else:
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
            'peakInfections': float(peak_infections),
            'peakTime': float(peak_time),
            'totalCases': float(total_cases),
            'finalRecovered': float(r[-1])
        }
    
    def grid_search(self, parameters: Dict[str, Tuple[float, float, int]], 
                   n_samples_per_param: int = 5) -> List[Dict[str, Any]]:
        """
        Grid search: systematic exploration of parameter space.
        
        Args:
            parameters: Dict of {param_name: (min, max, n_points)}
            n_samples_per_param: Number of grid points per parameter
        
        Returns:
            List of simulation results for each parameter combination
        """
        results = []
        
        # Generate grid points for each parameter
        param_grids = {}
        for param_name, (min_val, max_val, n_points) in parameters.items():
            if HAS_NUMPY:
                param_grids[param_name] = np.linspace(min_val, max_val, n_points).tolist()
            else:
                step = (max_val - min_val) / (n_points - 1) if n_points > 1 else 0
                param_grids[param_name] = [min_val + i * step for i in range(n_points)]
        
        # Generate all combinations
        param_names = list(parameters.keys())
        if len(param_names) == 1:
            # Single parameter
            for val in param_grids[param_names[0]]:
                param_values = {param_names[0]: val}
                result = self._run_simulation_with_params(param_values)
                result['parameters'] = param_values
                results.append(result)
        else:
            # Multiple parameters - Cartesian product
            from itertools import product
            for combo in product(*[param_grids[p] for p in param_names]):
                param_values = {param_names[i]: combo[i] for i in range(len(param_names))}
                result = self._run_simulation_with_params(param_values)
                result['parameters'] = param_values
                results.append(result)
        
        return results
    
    def random_search(self, parameters: Dict[str, Tuple[float, float]], 
                     n_samples: int = 100, seed: Optional[int] = None) -> List[Dict[str, Any]]:
        """
        Random search: random sampling of parameter combinations.
        
        Args:
            parameters: Dict of {param_name: (min, max)}
            n_samples: Number of random samples
            seed: Random seed for reproducibility
        
        Returns:
            List of simulation results
        """
        if seed is not None:
            random.seed(seed)
            if HAS_NUMPY:
                np.random.seed(seed)
        
        results = []
        param_names = list(parameters.keys())
        
        for _ in range(n_samples):
            # Sample random values for each parameter
            param_values = {}
            for param_name, (min_val, max_val) in parameters.items():
                if HAS_NUMPY:
                    param_values[param_name] = np.random.uniform(min_val, max_val)
                else:
                    param_values[param_name] = random.uniform(min_val, max_val)
            
            result = self._run_simulation_with_params(param_values)
            result['parameters'] = param_values
            results.append(result)
        
        return results
    
    def morris_method(self, parameters: Dict[str, Tuple[float, float]], 
                     n_trajectories: int = 10, levels: int = 4) -> Dict[str, Any]:
        """
        Morris method: one-at-a-time sensitivity analysis.
        Tests each parameter individually while others are fixed.
        
        Args:
            parameters: Dict of {param_name: (min, max)}
            n_trajectories: Number of trajectories (different starting points)
            levels: Number of levels for each parameter
        
        Returns:
            Dictionary with sensitivity indices for each parameter
        """
        param_names = list(parameters.keys())
        baseline_values = {name: (min_val + max_val) / 2 for name, (min_val, max_val) in parameters.items()}
        
        # Calculate step size
        step_sizes = {}
        for param_name, (min_val, max_val) in parameters.items():
            step_sizes[param_name] = (max_val - min_val) / (levels - 1)
        
        # Run trajectories
        elementary_effects = {name: [] for name in param_names}
        
        for traj in range(n_trajectories):
            # Random starting point
            start_values = {}
            for param_name, (min_val, max_val) in parameters.items():
                if HAS_NUMPY:
                    start_values[param_name] = np.random.uniform(min_val, max_val)
                else:
                    start_values[param_name] = random.uniform(min_val, max_val)
            
            # Test each parameter
            for param_name in param_names:
                # Baseline simulation
                baseline_result = self._run_simulation_with_params(start_values)
                # Use totalCases as the primary output (more stable than peakInfections for endemic models)
                baseline_output = baseline_result['totalCases']

                # Perturbed simulation
                perturbed_values = start_values.copy()
                # Move to next level
                current_val = start_values[param_name]
                step = step_sizes[param_name]
                if HAS_NUMPY:
                    perturbed_val = current_val + step if current_val + step <= parameters[param_name][1] else current_val - step
                else:
                    perturbed_val = current_val + step if current_val + step <= parameters[param_name][1] else current_val - step
                perturbed_values[param_name] = perturbed_val

                perturbed_result = self._run_simulation_with_params(perturbed_values)
                perturbed_output = perturbed_result['totalCases']

                # Calculate elementary effect
                delta = perturbed_val - current_val
                if abs(delta) > 1e-10:
                    ee = (perturbed_output - baseline_output) / delta
                    elementary_effects[param_name].append(ee)
        
        # Calculate sensitivity indices
        sensitivity_indices = {}
        for param_name in param_names:
            ees = elementary_effects[param_name]
            if ees:
                if HAS_NUMPY:
                    mu = np.mean(ees)
                    mu_star = np.mean(np.abs(ees))
                    sigma = np.std(ees)
                else:
                    mu = sum(ees) / len(ees)
                    mu_star = sum(abs(e) for e in ees) / len(ees)
                    variance = sum((e - mu)**2 for e in ees) / len(ees)
                    sigma = math.sqrt(variance)
                
                sensitivity_indices[param_name] = {
                    'mu': float(mu),  # Mean elementary effect
                    'mu_star': float(mu_star),  # Mean absolute elementary effect (importance)
                    'sigma': float(sigma),  # Standard deviation (non-linearity/interactions)
                    'n_samples': len(ees)
                }
        
        return {
            'method': 'Morris',
            'parameters': param_names,
            'n_trajectories': n_trajectories,
            'sensitivity_indices': sensitivity_indices,
            'interpretation': {
                'mu_star': 'Mean absolute effect (higher = more important)',
                'sigma': 'Standard deviation (higher = more non-linear/interactions)'
            }
        }
    
    def sobol_sequences(self, parameters: Dict[str, Tuple[float, float]], 
                       n_samples: int = 100) -> List[Dict[str, Any]]:
        """
        Sobol sequences: quasi-random sampling for better space coverage.
        Falls back to random if numpy not available.
        
        Args:
            parameters: Dict of {param_name: (min, max)}
            n_samples: Number of samples
        
        Returns:
            List of simulation results
        """
        if HAS_NUMPY:
            try:
                from scipy.stats import qmc
                # Generate Sobol sequence
                dim = len(parameters)
                sampler = qmc.Sobol(d=dim, scramble=True)
                samples = sampler.random(n_samples)
                
                # Scale to parameter ranges
                param_names = list(parameters.keys())
                results = []
                for sample in samples:
                    param_values = {}
                    for i, param_name in enumerate(param_names):
                        min_val, max_val = parameters[param_name]
                        param_values[param_name] = min_val + sample[i] * (max_val - min_val)
                    
                    result = self._run_simulation_with_params(param_values)
                    result['parameters'] = param_values
                    results.append(result)
                
                return results
            except ImportError:
                # Fall back to random if scipy not available
                print("Warning: scipy not available. Using random search instead of Sobol sequences.")
                return self.random_search(parameters, n_samples)
        else:
            # Fall back to random
            return self.random_search(parameters, n_samples)
    
    def multi_parameter_sensitivity(self, parameters: Dict[str, Tuple[float, float]], 
                                   method: str = 'random', n_samples: int = 100) -> Dict[str, Any]:
        """
        Multi-parameter sensitivity: varying multiple parameters simultaneously.
        
        Args:
            parameters: Dict of {param_name: (min, max)}
            method: 'random', 'grid', 'sobol', or 'morris'
            n_samples: Number of samples (for random/sobol) or grid points (for grid)
        
        Returns:
            Dictionary with sensitivity analysis results
        """
        if method == 'random':
            results = self.random_search(parameters, n_samples)
        elif method == 'grid':
            # Convert to grid format
            grid_params = {name: (min_val, max_val, int(n_samples ** (1/len(parameters)))) 
                          for name, (min_val, max_val) in parameters.items()}
            results = self.grid_search(grid_params)
        elif method == 'sobol':
            results = self.sobol_sequences(parameters, n_samples)
        elif method == 'morris':
            return self.morris_method(parameters, n_trajectories=n_samples // len(parameters))
        else:
            raise ValueError(f"Unknown method: {method}")
        
        # Analyze results
        return self._analyze_multi_parameter_results(results, parameters)
    
    def _run_simulation_with_params(self, param_values: Dict[str, float]) -> Dict[str, Any]:
        """
        Run simulation with given parameter values using actual model structure.
        Uses GenericModelSimulator to simulate the actual compartmental model.
        """
        sys.path.insert(0, str(Path(__file__).parent.parent / 'utils'))
        from generic_simulator import GenericModelSimulator

        # Create simulator with actual model structure
        simulator = GenericModelSimulator(str(self.model_path))

        # Run simulation with the provided parameters
        return simulator.simulate(param_values, days=200, dt=0.1)
    
    def _analyze_multi_parameter_results(self, results: List[Dict[str, Any]], 
                                        parameters: Dict[str, Tuple[float, float]]) -> Dict[str, Any]:
        """Analyze results from multi-parameter sensitivity"""
        if not results:
            return {'error': 'No results to analyze'}
        
        # Extract outputs
        peak_infections = [r['peakInfections'] for r in results]
        peak_times = [r['peakTime'] for r in results]
        total_cases = [r['totalCases'] for r in results]
        
        # Calculate statistics
        if HAS_NUMPY:
            stats = {
                'peakInfections': {
                    'mean': float(np.mean(peak_infections)),
                    'std': float(np.std(peak_infections)),
                    'min': float(np.min(peak_infections)),
                    'max': float(np.max(peak_infections)),
                    'range': float(np.max(peak_infections) - np.min(peak_infections))
                },
                'peakTime': {
                    'mean': float(np.mean(peak_times)),
                    'std': float(np.std(peak_times)),
                    'min': float(np.min(peak_times)),
                    'max': float(np.max(peak_times)),
                    'range': float(np.max(peak_times) - np.min(peak_times))
                },
                'totalCases': {
                    'mean': float(np.mean(total_cases)),
                    'std': float(np.std(total_cases)),
                    'min': float(np.min(total_cases)),
                    'max': float(np.max(total_cases)),
                    'range': float(np.max(total_cases) - np.min(total_cases))
                }
            }
        else:
            stats = {
                'peakInfections': {
                    'mean': sum(peak_infections) / len(peak_infections),
                    'min': min(peak_infections),
                    'max': max(peak_infections),
                    'range': max(peak_infections) - min(peak_infections)
                },
                'peakTime': {
                    'mean': sum(peak_times) / len(peak_times),
                    'min': min(peak_times),
                    'max': max(peak_times),
                    'range': max(peak_times) - min(peak_times)
                },
                'totalCases': {
                    'mean': sum(total_cases) / len(total_cases),
                    'min': min(total_cases),
                    'max': max(total_cases),
                    'range': max(total_cases) - min(total_cases)
                }
            }
        
        return {
            'n_samples': len(results),
            'parameters': list(parameters.keys()),
            'statistics': stats,
            'results': results[:10]  # Include first 10 results as examples
        }
    
    def generate_sensitivity_report(self, method: str = 'morris',
                                   variation_range: float = 0.5) -> Dict[str, Any]:
        """
        Generate comprehensive sensitivity analysis report using specified method.

        Args:
            method: Sensitivity method ('morris', 'random', 'grid', 'sobol')
            variation_range: Relative variation range (default 0.5 = ±50%)
        """
        key_params = self.identify_key_parameters()

        if not key_params:
            return {
                'modelName': self.model_name,
                'message': 'No key parameters identified for sensitivity analysis',
                'note': 'This model may have rates embedded in flows rather than explicit parameters'
            }

        # Prepare parameters for analysis
        parameters = {}
        for param in key_params[:5]:  # Analyze top 5 parameters (was 3)
            if param['currentValue'] is not None and param['currentValue'] > 0:
                baseline = param['currentValue']

                # For very small parameters (< 0.001), use larger variation range
                # This helps detect sensitivity in transmission rates which are often very small
                effective_range = variation_range
                if baseline < 0.001:
                    effective_range = 2.0  # ±200% for very small parameters
                elif baseline < 0.01:
                    effective_range = 1.0  # ±100% for small parameters

                min_val = baseline * (1 - effective_range)
                max_val = baseline * (1 + effective_range)

                # Ensure min is positive
                min_val = max(baseline * 0.1, min_val)

                parameters[param['parameter']] = (min_val, max_val)

        if not parameters:
            return {
                'modelName': self.model_name,
                'message': 'No numeric parameters found for sensitivity analysis'
            }
        
        # Run sensitivity analysis
        if method == 'morris':
            sensitivity_result = self.morris_method(parameters, n_trajectories=20)
        elif method == 'random':
            sensitivity_result = self.multi_parameter_sensitivity(parameters, method='random', n_samples=100)
        elif method == 'grid':
            sensitivity_result = self.multi_parameter_sensitivity(parameters, method='grid', n_samples=25)
        elif method == 'sobol':
            sensitivity_result = self.multi_parameter_sensitivity(parameters, method='sobol', n_samples=100)
        else:
            # Default to Morris
            sensitivity_result = self.morris_method(parameters, n_trajectories=20)
        
        return {
            'modelName': self.model_name,
            'method': method,
            'keyParameters': key_params,
            'parametersAnalyzed': list(parameters.keys()),
            'sensitivityAnalysis': sensitivity_result,
            'variationRange': f"±{variation_range*100}%"
        }
    
    def export_sensitivity_report(self, output_path: str, method: str = 'morris'):
        """Export sensitivity report to JSON"""
        report = self.generate_sensitivity_report(method=method)
        
        with open(output_path, 'w', encoding='utf-8') as f:
            json.dump(report, f, indent=2, ensure_ascii=False)


def main():
    """Main function to run sensitivity analysis for all models"""
    import argparse
    
    parser = argparse.ArgumentParser(
        description='Enhanced sensitivity analysis with multiple methods',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Methods:
  morris  - Morris method (one-at-a-time, recommended for screening)
  random  - Random search (good for exploration)
  grid    - Grid search (systematic, but expensive for many parameters)
  sobol   - Sobol sequences (quasi-random, good coverage)

Examples:
  # Use Morris method (default)
  python3 sensitivity_analysis.py

  # Use random search
  python3 sensitivity_analysis.py --method random

  # Use grid search
  python3 sensitivity_analysis.py --method grid
        """
    )
    
    parser.add_argument('--method', choices=['morris', 'random', 'grid', 'sobol'],
                       default='morris', help='Sensitivity analysis method (default: morris)')
    parser.add_argument('--variation', type=float, default=0.5,
                       help='Variation range as fraction (default: 0.5 = ±50%%, auto-scaled for small parameters)')

    args = parser.parse_args()

    base_path = Path(__file__).parent.parent.parent / 'Compartmental' / 'CompartmentalModel'
    output_dir = Path(__file__).parent.parent / 'reports' / 'sensitivity'
    output_dir.mkdir(parents=True, exist_ok=True)

    # Analyze all 3 models
    models = [
        ('covid.compmodel', 'COVID-19'),
        ('malaria.compmodel', 'Malaria'),
        ('HIV.compmodel', 'HIV')
    ]

    print("=" * 80)
    print("TASK 2.3: ENHANCED SENSITIVITY ANALYSIS")
    print("=" * 80)
    print(f"Method: {args.method.upper()}")
    print(f"Variation range: ±{args.variation*100}%")
    print("Note: Very small parameters (< 0.001) automatically use ±200% range")
    print()
    
    for model_file, model_name in models:
        model_path = base_path / model_file
        
        if not model_path.exists():
            print(f"\n⚠ Warning: {model_path} not found, skipping {model_name}")
            continue
        
        print(f"\n{'=' * 80}")
        print(f"Analyzing: {model_name}")
        print('=' * 80)
        
        try:
            analyzer = SensitivityAnalyzer(str(model_path), model_name)
            
            # Export JSON
            json_filename = f"{model_name.lower().replace('-', '_')}_sensitivity_{args.method}.json"
            json_path = output_dir / json_filename
            analyzer.export_sensitivity_report(str(json_path), method=args.method)
            print(f"✓ Sensitivity report exported to: {json_path}")
            
            report = analyzer.generate_sensitivity_report(method=args.method, variation_range=args.variation)
            
            if 'sensitivityAnalysis' in report:
                sens = report['sensitivityAnalysis']
                if args.method == 'morris' and 'sensitivity_indices' in sens:
                    print(f"\nSensitivity Indices (Morris Method):")
                    for param, indices in sens['sensitivity_indices'].items():
                        print(f"  {param}:")
                        print(f"    μ* (importance): {indices['mu_star']:.4f}")
                        print(f"    σ (non-linearity): {indices['sigma']:.4f}")
                elif 'statistics' in sens:
                    stats = sens['statistics']
                    print(f"\nOutput Statistics:")
                    print(f"  Peak Infections: {stats['peakInfections']['mean']:.2f} "
                          f"(range: {stats['peakInfections']['min']:.2f} - {stats['peakInfections']['max']:.2f})")
                    print(f"  Peak Time: {stats['peakTime']['mean']:.1f} days "
                          f"(range: {stats['peakTime']['min']:.1f} - {stats['peakTime']['max']:.1f})")
            else:
                print(f"⚠ {report.get('message', 'No sensitivity analysis performed')}")
                
        except Exception as e:
            print(f"✗ Error analyzing {model_name}: {e}")
            import traceback
            traceback.print_exc()
    
    print("\n" + "=" * 80)
    print("SENSITIVITY ANALYSIS COMPLETE")
    print("=" * 80)
    print(f"\nAll reports saved to: {output_dir}")


if __name__ == '__main__':
    main()
