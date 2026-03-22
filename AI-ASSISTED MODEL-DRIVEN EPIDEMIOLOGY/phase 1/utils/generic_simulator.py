"""
Generic Model Simulator

Simulates any compartmental model using its actual structure from .compmodel files.
Works for SEIR, vector-borne, and other compartmental epidemiological models.
"""
import sys
from pathlib import Path
from typing import Dict, List, Any, Tuple

sys.path.insert(0, str(Path(__file__).parent))
from xml_parser import CompModelParser

try:
    import numpy as np
    HAS_NUMPY = True
except ImportError:
    HAS_NUMPY = False


class GenericModelSimulator:
    """Simulates compartmental models using their actual structure"""

    def __init__(self, model_path: str):
        """
        Initialize simulator with model file.

        Args:
            model_path: Path to .compmodel file
        """
        self.parser = CompModelParser(model_path)
        self.data = self.parser.extract_all()
        self.compartments = self.data['compartments']
        self.parameters = {p['name']: p.get('expression', '0') for p in self.data['parameters']}

        # Build compartment index mapping
        self.comp_index = {c['primaryName']: idx for idx, c in enumerate(self.compartments)}
        self.comp_names = [c['primaryName'] for c in self.compartments]

        # Build parameter to flow mapping for sensitivity analysis
        self.param_to_flow = self._build_parameter_mapping()

    def _build_parameter_mapping(self) -> Dict[str, Tuple[int, int, str]]:
        """
        Build mapping from parameter names to flows.
        Returns dict: {param_name: (comp_idx, flow_idx, rate_field)}
        """
        mapping = {}

        for comp_idx, comp in enumerate(self.compartments):
            source_name = comp.get('primaryName', 'Unknown')

            for flow_idx, flow in enumerate(comp.get('outgoingFlows', [])):
                target_ref = flow.get('target', '')
                target_name = 'Unknown'

                # Get target compartment name
                if target_ref.startswith('//@compartments.'):
                    try:
                        target_idx = int(target_ref.split('.')[-1])
                        if target_idx < len(self.compartments):
                            target_name = self.compartments[target_idx].get('primaryName', 'Unknown')
                    except (ValueError, IndexError):
                        pass

                # Map contactRate parameters
                if 'contactRate' in flow:
                    param_name = f"contactRate_{source_name}_to_{target_name}"
                    mapping[param_name] = (comp_idx, flow_idx, 'contactRate')

                # Map rate parameters
                if 'rate' in flow:
                    param_name = f"rate_{source_name}_to_{target_name}"
                    mapping[param_name] = (comp_idx, flow_idx, 'rate')

                # Map stratum-specific rates
                for ssr_idx, ssr in enumerate(flow.get('stratumSpecificRates', [])):
                    stratum = ssr.get('stratum', '')
                    if stratum:
                        param_name = f"rate_{source_name}_stratum_{stratum}"
                        mapping[param_name] = (comp_idx, flow_idx, f'ssr_{ssr_idx}')

        # Also map explicit parameters
        for param in self.data['parameters']:
            param_name = param['name']
            # Direct parameter mapping
            mapping[param_name] = ('parameter', param_name, 'value')

        return mapping

    def _evaluate_rate(self, rate_str: str, params: Dict[str, float], populations: List[float],
                       comp_idx: int = -1, flow_idx: int = -1, rate_field: str = '') -> float:
        """
        Evaluate a rate expression with parameter override support.

        Args:
            rate_str: Rate expression (can be numeric or parameter name)
            params: Parameter values (can include overrides)
            populations: Current compartment populations
            comp_idx: Compartment index (for override lookup)
            flow_idx: Flow index (for override lookup)
            rate_field: Rate field name (for override lookup)

        Returns:
            Evaluated rate value
        """
        # First, check if there's a parameter override for this specific flow
        if comp_idx >= 0 and flow_idx >= 0 and rate_field:
            comp_name = self.compartments[comp_idx].get('primaryName', 'Unknown')
            flow = self.compartments[comp_idx].get('outgoingFlows', [])[flow_idx]
            target_ref = flow.get('target', '')

            # Get target name
            target_name = 'Unknown'
            if target_ref.startswith('//@compartments.'):
                try:
                    target_idx = int(target_ref.split('.')[-1])
                    if target_idx < len(self.compartments):
                        target_name = self.compartments[target_idx].get('primaryName', 'Unknown')
                except (ValueError, IndexError):
                    pass

            # Check for parameter override
            if rate_field == 'contactRate':
                override_key = f"contactRate_{comp_name}_to_{target_name}"
                if override_key in params:
                    return float(params[override_key])
            elif rate_field == 'rate':
                override_key = f"rate_{comp_name}_to_{target_name}"
                if override_key in params:
                    return float(params[override_key])
            elif rate_field.startswith('ssr_'):
                # Stratum-specific rate override
                ssr_idx = int(rate_field.split('_')[1])
                ssrs = flow.get('stratumSpecificRates', [])
                if ssr_idx < len(ssrs):
                    stratum = ssrs[ssr_idx].get('stratum', '')
                    override_key = f"rate_{comp_name}_stratum_{stratum}"
                    if override_key in params:
                        return float(params[override_key])

        if not rate_str or rate_str.strip() == '':
            return 0.0

        try:
            # Try direct numeric conversion
            return float(rate_str)
        except ValueError:
            pass

        # Try as parameter name (for explicit parameters)
        if rate_str in params:
            try:
                return float(params[rate_str])
            except (ValueError, TypeError):
                # Parameter might be an expression - try to evaluate
                return self._evaluate_expression(params[rate_str], params, populations)

        # Try to evaluate as expression
        return self._evaluate_expression(rate_str, params, populations)

    def _evaluate_expression(self, expr: str, params: Dict[str, float], populations: List[float]) -> float:
        """Safely evaluate a mathematical expression"""
        if not expr or expr.strip() == '':
            return 0.0

        try:
            # Replace parameter names with values
            eval_expr = str(expr)
            for param_name, param_value in params.items():
                if param_name in eval_expr:
                    try:
                        val = float(param_value)
                        eval_expr = eval_expr.replace(param_name, str(val))
                    except (ValueError, TypeError):
                        pass

            # Try to evaluate
            result = eval(eval_expr, {"__builtins__": {}}, {})
            return float(result)
        except Exception:
            # If evaluation fails, return 0
            return 0.0

    def _get_target_index(self, target_ref: str) -> int:
        """Get compartment index from target reference"""
        if target_ref.startswith('//@compartments.'):
            try:
                return int(target_ref.split('.')[-1])
            except (ValueError, IndexError):
                return -1
        return -1

    def _resolve_parameter_reference(self, param_ref: str) -> str:
        """
        Resolve a parameter reference like '//@parameters.2' to the parameter name.
        Returns the parameter name or empty string if not found.
        """
        if not param_ref or not param_ref.startswith('//@parameters.'):
            return ''

        try:
            param_idx = int(param_ref.split('.')[-1])
            if param_idx < len(self.data['parameters']):
                return self.data['parameters'][param_idx]['name']
        except (ValueError, IndexError):
            pass

        return ''

    def _compute_derivatives(self, populations: List[float], params: Dict[str, float]) -> List[float]:
        """
        Compute derivatives (rates of change) for all compartments.

        Args:
            populations: Current population in each compartment
            params: Parameter values

        Returns:
            List of derivatives for each compartment
        """
        n_comps = len(self.compartments)
        derivatives = [0.0] * n_comps

        # Total population (for contact rates)
        total_pop = sum(populations)
        if total_pop == 0:
            total_pop = 1.0  # Avoid division by zero

        # Process each compartment's outgoing flows
        for comp_idx, comp in enumerate(self.compartments):
            for flow_idx, flow in enumerate(comp.get('outgoingFlows', [])):
                flow_type = flow.get('type', 'RateFlow')
                target_idx = self._get_target_index(flow.get('target', ''))

                if target_idx < 0 or target_idx >= n_comps:
                    continue

                # Calculate flow rate based on available fields (some models mix types)
                # First, resolve parameter references if present
                contact_rate_str = flow.get('contactRate', '')
                rate_str = flow.get('rate', '')

                # If contactRate or rate is empty, check for parameter references
                if not contact_rate_str.strip() and flow.get('contactRateParameter'):
                    param_name = self._resolve_parameter_reference(flow.get('contactRateParameter', ''))
                    if param_name:
                        contact_rate_str = param_name

                if not rate_str.strip() and flow.get('rateParameter'):
                    param_name = self._resolve_parameter_reference(flow.get('rateParameter', ''))
                    if param_name:
                        rate_str = param_name

                # Check if contactRate exists (regardless of flow type)
                if contact_rate_str and contact_rate_str.strip():
                    # Contact-based flow: depends on interaction between compartments
                    contact_rate = self._evaluate_rate(contact_rate_str, params, populations,
                                                      comp_idx, flow_idx, 'contactRate')
                    contact_comp_ref = flow.get('contactCompartment', '')
                    contact_idx = self._get_target_index(contact_comp_ref)

                    if contact_idx >= 0 and contact_idx < n_comps:
                        # Flow = contact_rate * source_pop * contact_pop / total_pop
                        flow_value = contact_rate * populations[comp_idx] * populations[contact_idx] / total_pop
                    else:
                        # If no contact compartment specified, assume mass action with target
                        flow_value = contact_rate * populations[comp_idx] * populations[target_idx] / total_pop

                elif rate_str and rate_str.strip():
                    # Rate-based flow: proportional to source population
                    if flow_type == 'ExternalSource':
                        # External source: constant inflow (not proportional to source)
                        rate = self._evaluate_rate(rate_str, params, populations,
                                                  comp_idx, flow_idx, 'rate')
                        flow_value = rate
                    else:
                        # Regular rate flow: proportional to source population
                        rate = self._evaluate_rate(rate_str, params, populations,
                                                  comp_idx, flow_idx, 'rate')
                        flow_value = rate * populations[comp_idx]

                else:
                    # No rate specified, skip this flow
                    continue

                # Update derivatives
                derivatives[comp_idx] -= flow_value  # Outflow from source
                if flow_type != 'ExternalSink':  # External sinks don't add to other compartments
                    derivatives[target_idx] += flow_value  # Inflow to target

        # Process external sources (constant inflows to compartments)
        for ext_source in self.data.get('externalSources', []):
            target_ref = ext_source.get('targetCompartment', '')
            target_idx = self._get_target_index(target_ref)

            if target_idx >= 0 and target_idx < n_comps:
                # Get rate from parameter reference or direct value
                rate_str = ext_source.get('rate', '')
                if not rate_str.strip() and ext_source.get('rateParameter'):
                    param_name = self._resolve_parameter_reference(ext_source.get('rateParameter', ''))
                    if param_name:
                        rate_str = param_name

                if rate_str and rate_str.strip():
                    rate = self._evaluate_rate(rate_str, params, populations)
                    derivatives[target_idx] += rate

        # Process external sinks (outflows from compartments)
        for ext_sink in self.data.get('externalSinks', []):
            source_ref = ext_sink.get('sourceCompartment', '')
            source_idx = self._get_target_index(source_ref)

            if source_idx >= 0 and source_idx < n_comps:
                # Get rate from parameter reference or direct value
                rate_str = ext_sink.get('rate', '')
                if not rate_str.strip() and ext_sink.get('rateParameter'):
                    param_name = self._resolve_parameter_reference(ext_sink.get('rateParameter', ''))
                    if param_name:
                        rate_str = param_name

                if rate_str and rate_str.strip():
                    rate = self._evaluate_rate(rate_str, params, populations)
                    flow_value = rate * populations[source_idx]
                    derivatives[source_idx] -= flow_value

        return derivatives

    def simulate(self, params: Dict[str, float], days: int = 200, dt: float = 0.1) -> Dict[str, Any]:
        """
        Simulate the model with given parameters.

        Args:
            params: Parameter values to use (overrides for specific parameters)
            days: Number of days to simulate
            dt: Time step size

        Returns:
            Dictionary with simulation results and metrics
        """
        # Merge provided params with model's default parameters
        # This allows expressions like β₁a(T) to be evaluated using model defaults
        full_params = self.parameters.copy()

        # Add reasonable defaults for common environmental variables if not defined
        if 'T' not in full_params or not full_params['T'] or full_params['T'] == '':
            full_params['T'] = '28'  # Default temperature for malaria models (°C)
        if 'R' not in full_params or not full_params['R'] or full_params['R'] == '':
            full_params['R'] = '25'  # Default rainfall (mm/day)

        full_params.update(params)  # Override with provided params
        n_steps = int(days / dt)
        n_comps = len(self.compartments)

        # Initialize populations
        if HAS_NUMPY:
            populations = np.zeros((n_steps, n_comps))
        else:
            populations = [[0.0] * n_comps for _ in range(n_steps)]

        # Set initial populations
        total_init_pop = 0
        for idx, comp in enumerate(self.compartments):
            pop_str = comp.get('population', '0')
            try:
                init_pop = float(pop_str)
                if HAS_NUMPY:
                    populations[0][idx] = init_pop
                else:
                    populations[0][idx] = init_pop
                total_init_pop += init_pop
            except (ValueError, TypeError):
                pass

        # If no initial populations specified, use default
        if total_init_pop == 0:
            # Check if this looks like a vector-borne disease model (mosquito/human compartments)
            comp_names = [c.get('primaryName', '').lower() for c in self.compartments]
            has_mosquito = any('mosquito' in name for name in comp_names)

            if has_mosquito and n_comps >= 7:
                # Vector-borne model: initialize both human and mosquito populations
                # Find first mosquito compartment
                mosquito_start_idx = -1
                for idx, name in enumerate(comp_names):
                    if 'mosquito' in name and mosquito_start_idx < 0:
                        mosquito_start_idx = idx
                        break

                # Humans: 99k susceptible, 500 infected
                if HAS_NUMPY:
                    populations[0][0] = 99000
                    if 2 < mosquito_start_idx:  # If there's an infected humans compartment before mosquitoes
                        populations[0][2] = 500  # Infected humans to seed transmission
                else:
                    populations[0][0] = 99000
                    if 2 < mosquito_start_idx:
                        populations[0][2] = 500

                # Mosquitoes: start from identified index
                if mosquito_start_idx >= 0:
                    if HAS_NUMPY:
                        populations[0][mosquito_start_idx] = 75000  # Susceptible mosquitoes
                        if mosquito_start_idx + 2 < n_comps:
                            populations[0][mosquito_start_idx + 2] = 2000  # Infected mosquitoes
                    else:
                        populations[0][mosquito_start_idx] = 75000
                        if mosquito_start_idx + 2 < n_comps:
                            populations[0][mosquito_start_idx + 2] = 2000
            else:
                # Standard SEIR-like model: 99% susceptible, 1% exposed/infected
                if n_comps > 0:
                    if HAS_NUMPY:
                        populations[0][0] = 99000
                    else:
                        populations[0][0] = 99000
                if n_comps > 1:
                    if HAS_NUMPY:
                        populations[0][1] = 1000
                    else:
                        populations[0][1] = 1000

        # Run simulation using Euler integration
        for t in range(1, n_steps):
            if HAS_NUMPY:
                current_pop = populations[t-1].copy()
            else:
                current_pop = populations[t-1][:]

            # Compute derivatives with full parameter set
            derivatives = self._compute_derivatives(current_pop, full_params)

            # Update populations
            for idx in range(n_comps):
                new_pop = current_pop[idx] + derivatives[idx] * dt
                new_pop = max(0, new_pop)  # Ensure non-negative
                if HAS_NUMPY:
                    populations[t][idx] = new_pop
                else:
                    populations[t][idx] = new_pop

        # Calculate metrics
        # Find compartments that represent "infectious" states
        infectious_indices = []
        for idx, comp in enumerate(self.compartments):
            name_lower = comp['primaryName'].lower()
            if any(kw in name_lower for kw in ['infectious', 'infected', 'symptomatic', 'exposed']):
                infectious_indices.append(idx)

        # If no infectious compartments found, use second compartment as proxy
        if not infectious_indices and n_comps > 1:
            infectious_indices = [1]

        # Calculate total infectious over time
        if HAS_NUMPY:
            infectious_totals = np.sum([populations[:, idx] for idx in infectious_indices], axis=0)
            peak_infections = float(np.max(infectious_totals))
            peak_time = float(np.argmax(infectious_totals) * dt)

            # Total population over time
            total_pops = np.sum(populations, axis=1)
            final_total = float(total_pops[-1])
        else:
            infectious_totals = [sum(populations[t][idx] for idx in infectious_indices) for t in range(n_steps)]
            peak_infections = float(max(infectious_totals))
            peak_time = float(infectious_totals.index(max(infectious_totals)) * dt)

            # Total population over time
            total_pops = [sum(populations[t]) for t in range(n_steps)]
            final_total = float(total_pops[-1])

        # Total cases (assuming last compartments are recovered/removed states)
        # Find "recovered" or "removed" compartments
        recovered_indices = []
        for idx, comp in enumerate(self.compartments):
            name_lower = comp['primaryName'].lower()
            if any(kw in name_lower for kw in ['recovered', 'removed', 'treated', 'dead']):
                recovered_indices.append(idx)

        if recovered_indices:
            if HAS_NUMPY:
                final_recovered = float(np.sum([populations[-1][idx] for idx in recovered_indices]))
            else:
                final_recovered = float(sum(populations[-1][idx] for idx in recovered_indices))
        else:
            final_recovered = 0.0

        # Estimate total cases
        initial_total = sum(populations[0])
        total_cases = initial_total - populations[-1][0] if n_comps > 0 else 0  # Lost from first compartment
        if total_cases < 0:
            total_cases = final_recovered

        result = {
            'peakInfections': float(peak_infections),
            'peakTime': float(peak_time),
            'totalCases': float(total_cases),
            'finalRecovered': float(final_recovered)
        }
        if HAS_NUMPY:
            result['trajectory'] = populations
        else:
            result['trajectory'] = populations
        return result
