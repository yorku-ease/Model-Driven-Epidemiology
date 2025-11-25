"""
Unified Equation Generator for Compartmental Models
Supports both epidemiological models and traffic network models

Reads .compmodel files (XML) and generates differential equations based on:
- Disease models: Standard rate-based and contact-based flows
- Traffic models: Supply-demand dynamics with PP/FIFO junction rules

Author: EpiMDE Framework
Date: 2025
"""

import xml.etree.ElementTree as ET
from typing import Dict, List, Optional, Tuple
from dataclasses import dataclass
from enum import Enum


class JunctionRuleType(Enum):
    """Junction rule types for traffic models"""
    NONE = "NONE"
    PPFIFO = "PPFIFO"
    CUSTOM = "CUSTOM"


class SupplyFunctionType(Enum):
    """Supply/demand function types for traffic models"""
    NONE = "NONE"
    TRIANGULAR = "TRIANGULAR"
    LINEAR = "LINEAR"
    CUSTOM = "CUSTOM"


@dataclass
class SupplyFunction:
    """Flow network supply/demand function parameters"""
    function_type: SupplyFunctionType
    is_source_node: bool
    max_density: float = 0.0
    critical_density: float = 0.0
    max_throughput: float = 0.0
    max_demand: float = 0.0


@dataclass
class Compartment:
    """Represents a compartment in the model"""
    name: str
    primary_name: str
    secondary_name: str
    population: float
    supply_function: Optional[SupplyFunction] = None
    junction_rule: JunctionRuleType = JunctionRuleType.NONE
    outgoing_flows: List = None

    def __post_init__(self):
        if self.outgoing_flows is None:
            self.outgoing_flows = []

    def is_traffic_model(self) -> bool:
        """Check if this is a traffic compartment"""
        return self.supply_function is not None

    def is_disease_model(self) -> bool:
        """Check if this is a disease compartment"""
        return self.supply_function is None


@dataclass
class Flow:
    """Base flow between compartments"""
    target_compartment: str
    description: str


@dataclass
class RateFlow(Flow):
    """Rate-based flow (disease models) or split ratio (traffic models)"""
    rate: float = 0.0
    rate_parameter: Optional[str] = None


@dataclass
class ContactFlow(Flow):
    """Contact-based flow for disease transmission"""
    contact_compartment: str
    contact_rate: float = 0.0
    contact_rate_parameter: Optional[str] = None


@dataclass
class BirthSource:
    """Birth/input source"""
    name: str
    target_compartment: str
    rate: float = 0.0
    rate_parameter: Optional[str] = None
    fixed_rate: bool = False


class EquationGenerator:
    """Generate differential equations from compartmental models"""

    def __init__(self, model_file: str):
        self.model_file = model_file
        self.compartments: Dict[str, Compartment] = {}
        self.parameters: Dict[str, str] = {}
        self.birth_sources: List[BirthSource] = []
        self.is_traffic_network = False

        self.parse_model()

    def parse_model(self):
        """Parse .compmodel XML file"""
        tree = ET.parse(self.model_file)
        root = tree.getroot()

        # Namespace handling - try with and without namespace
        ns = {'compartmental': 'http://example.com/compartmentalmodel'}

        # Parse parameters - try both with and without namespace
        params = root.findall('.//compartmental:parameters', ns)
        if not params:
            params = root.findall('.//parameters')

        for param in params:
            name = param.get('name')
            expr = param.get('expression', '0')
            self.parameters[name] = expr

        # Parse compartments - try both with and without namespace
        comps = root.findall('.//compartmental:compartments', ns)
        if not comps:
            comps = root.findall('.//compartments')

        for idx, comp_elem in enumerate(comps):
            primary_name = comp_elem.get('PrimaryName', f'Compartment_{idx}')
            secondary_name = comp_elem.get('SecondaryName', '')
            population = float(comp_elem.get('population', '0'))
            junction_rule_str = comp_elem.get('junctionRule', 'NONE')

            try:
                junction_rule = JunctionRuleType(junction_rule_str)
            except ValueError:
                junction_rule = JunctionRuleType.NONE

            # Parse supply function if present (traffic model)
            supply_function = None
            supply_elem = comp_elem.find('compartmental:supplyFunction', ns)
            if supply_elem is None:
                supply_elem = comp_elem.find('supplyFunction')

            if supply_elem is not None:
                self.is_traffic_network = True
                func_type_str = supply_elem.get('type', 'NONE')
                try:
                    func_type = SupplyFunctionType(func_type_str)
                except ValueError:
                    func_type = SupplyFunctionType.NONE

                is_source_node = supply_elem.get('isSourceNode', 'false').lower() == 'true'
                max_density = float(supply_elem.get('maxDensity', '0'))
                critical_density = float(supply_elem.get('criticalDensity', '0'))
                max_throughput = float(supply_elem.get('maxThroughput', '0'))
                max_demand = float(supply_elem.get('maxDemand', '0'))

                supply_function = SupplyFunction(
                    function_type=func_type,
                    is_source_node=is_source_node,
                    max_density=max_density,
                    critical_density=critical_density,
                    max_throughput=max_throughput,
                    max_demand=max_demand
                )

            compartment = Compartment(
                name=primary_name,
                primary_name=primary_name,
                secondary_name=secondary_name,
                population=population,
                supply_function=supply_function,
                junction_rule=junction_rule
            )

            # Parse outgoing flows
            flows = comp_elem.findall('compartmental:outgoingFlows', ns)
            if not flows:
                flows = comp_elem.findall('outgoingFlows')

            for flow_elem in flows:
                flow_type = flow_elem.get('{http://www.w3.org/2001/XMLSchema-instance}type')
                target_path = flow_elem.get('target', '')
                description = flow_elem.get('description', '')

                # Extract target compartment index from path
                # Format: "//@compartments.1" -> index 1
                target_idx = int(target_path.split('.')[-1]) if target_path else 0

                if 'RateFlow' in flow_type:
                    rate = float(flow_elem.get('rate', '0'))
                    rate_param_path = flow_elem.get('rateParameter', '')
                    rate_param = self._extract_parameter_name(rate_param_path)

                    flow = RateFlow(
                        target_compartment=f"compartment_{target_idx}",
                        description=description,
                        rate=rate,
                        rate_parameter=rate_param
                    )
                    compartment.outgoing_flows.append(flow)

                elif 'ContactFlow' in flow_type:
                    contact_rate = float(flow_elem.get('contactRate', '0'))
                    contact_rate_param_path = flow_elem.get('contactRateParameter', '')
                    contact_rate_param = self._extract_parameter_name(contact_rate_param_path)
                    contact_comp_path = flow_elem.get('contactCompartment', '')
                    contact_idx = int(contact_comp_path.split('.')[-1]) if contact_comp_path else 0

                    flow = ContactFlow(
                        target_compartment=f"compartment_{target_idx}",
                        description=description,
                        contact_compartment=f"compartment_{contact_idx}",
                        contact_rate=contact_rate,
                        contact_rate_parameter=contact_rate_param
                    )
                    compartment.outgoing_flows.append(flow)

            self.compartments[f"compartment_{idx}"] = compartment

        # Parse birth sources
        births = root.findall('.//compartmental:birthSources', ns)
        if not births:
            births = root.findall('.//birthSources')

        for birth_elem in births:
            name = birth_elem.get('name', '')
            target_path = birth_elem.get('targetCompartment', '')
            target_idx = int(target_path.split('.')[-1]) if target_path else 0
            rate = float(birth_elem.get('rate', '0'))
            rate_param_path = birth_elem.get('rateParameter', '')
            rate_param = self._extract_parameter_name(rate_param_path)
            fixed_rate = birth_elem.get('fixedRate', 'false').lower() == 'true'

            birth = BirthSource(
                name=name,
                target_compartment=f"compartment_{target_idx}",
                rate=rate,
                rate_parameter=rate_param,
                fixed_rate=fixed_rate
            )
            self.birth_sources.append(birth)

    def _extract_parameter_name(self, param_path: str) -> Optional[str]:
        """Extract parameter name from reference path"""
        if not param_path:
            return None
        # Format: "//@parameters.10" -> get parameter at index 10
        try:
            idx = int(param_path.split('.')[-1])
            param_list = list(self.parameters.keys())
            if 0 <= idx < len(param_list):
                return param_list[idx]
        except (ValueError, IndexError):
            pass
        return None

    def generate_equations(self, output_file: str):
        """Generate differential equations and write to file"""
        with open(output_file, 'w') as f:
            f.write("=" * 70 + "\n")
            f.write(f"Differential Equations Generated from: {self.model_file}\n")
            f.write("=" * 70 + "\n\n")

            if self.is_traffic_network:
                f.write("MODEL TYPE: Traffic Network (Supply-Demand Dynamics)\n\n")
                self._generate_traffic_equations(f)
            else:
                f.write("MODEL TYPE: Epidemiological (Rate-Based Dynamics)\n\n")
                self._generate_disease_equations(f)

    def _generate_disease_equations(self, f):
        """Generate equations for disease models"""
        f.write("Parameters:\n")
        for name, value in self.parameters.items():
            f.write(f"  {name} = {value}\n")
        f.write("\n")

        f.write("Differential Equations:\n\n")

        for comp_id, comp in self.compartments.items():
            # Start with birth sources
            equation_terms = []

            for birth in self.birth_sources:
                if birth.target_compartment == comp_id:
                    if birth.rate_parameter:
                        equation_terms.append(f"+ {birth.rate_parameter}")
                    elif birth.rate > 0:
                        equation_terms.append(f"+ {birth.rate}")

            # Add incoming flows (from other compartments)
            for other_id, other_comp in self.compartments.items():
                if other_id == comp_id:
                    continue
                for flow in other_comp.outgoing_flows:
                    if flow.target_compartment == comp_id:
                        if isinstance(flow, RateFlow):
                            if flow.rate_parameter:
                                equation_terms.append(f"+ {flow.rate_parameter} * {other_comp.primary_name}")
                            elif flow.rate > 0:
                                equation_terms.append(f"+ {flow.rate} * {other_comp.primary_name}")
                        elif isinstance(flow, ContactFlow):
                            contact_comp = self.compartments[flow.contact_compartment]
                            if flow.contact_rate_parameter:
                                equation_terms.append(
                                    f"+ {flow.contact_rate_parameter} * {other_comp.primary_name} * {contact_comp.primary_name}"
                                )
                            elif flow.contact_rate > 0:
                                equation_terms.append(
                                    f"+ {flow.contact_rate} * {other_comp.primary_name} * {contact_comp.primary_name}"
                                )

            # Subtract outgoing flows
            for flow in comp.outgoing_flows:
                if isinstance(flow, RateFlow):
                    if flow.rate_parameter:
                        equation_terms.append(f"- {flow.rate_parameter} * {comp.primary_name}")
                    elif flow.rate > 0:
                        equation_terms.append(f"- {flow.rate} * {comp.primary_name}")
                elif isinstance(flow, ContactFlow):
                    contact_comp = self.compartments[flow.contact_compartment]
                    if flow.contact_rate_parameter:
                        equation_terms.append(
                            f"- {flow.contact_rate_parameter} * {comp.primary_name} * {contact_comp.primary_name}"
                        )
                    elif flow.contact_rate > 0:
                        equation_terms.append(
                            f"- {flow.contact_rate} * {comp.primary_name} * {contact_comp.primary_name}"
                        )

            equation = " ".join(equation_terms) if equation_terms else "0"
            f.write(f"d({comp.primary_name})/dt = {equation}\n\n")

    def _generate_traffic_equations(self, f):
        """Generate equations for traffic models"""
        f.write("Traffic Network Parameters:\n")
        for name, value in self.parameters.items():
            f.write(f"  {name} = {value}\n")
        f.write("\n")

        f.write("Supply and Demand Functions:\n\n")

        for comp_id, comp in self.compartments.items():
            if comp.supply_function:
                sf = comp.supply_function
                f.write(f"{comp.primary_name}:\n")
                f.write(f"  Type: {'Source Node' if sf.is_source_node else 'Constrained Node'}\n")

                if sf.is_source_node:
                    f.write(f"  Demand: Φ^out(ρ) = min(max(ρ, {sf.max_demand}), {sf.max_demand})\n")
                    f.write(f"  Supply: Φ^in(ρ) = ∞\n")
                else:
                    f.write(f"  ρ^max = {sf.max_density}\n")
                    f.write(f"  ρ^crit = {sf.critical_density}\n")
                    f.write(f"  Φ^max = {sf.max_throughput}\n")
                    f.write(f"  Demand: Φ^out(ρ) = min((Φ^max/ρ^crit)*ρ, Φ^max) for ρ ≤ ρ^crit\n")
                    f.write(f"  Supply: Φ^in(ρ) = min((Φ^max/(ρ^max-ρ^crit))*(ρ^max-ρ), Φ^max) for ρ > ρ^crit\n")
                f.write("\n")

        f.write("\nJunction Rules:\n")
        for comp_id, comp in self.compartments.items():
            if comp.junction_rule != JunctionRuleType.NONE:
                f.write(f"  {comp.primary_name}: {comp.junction_rule.value}\n")
        f.write("\n")

        f.write("Differential Equations:\n\n")
        f.write("NOTE: Traffic models require PP/FIFO computation at each time step.\n")
        f.write("See traffic_network.py for full implementation.\n\n")

        for comp_id, comp in self.compartments.items():
            if comp.supply_function and comp.supply_function.is_source_node:
                # Source node dynamics (e.g., onramps in traffic)
                birth_term = None
                for birth in self.birth_sources:
                    if birth.target_compartment == comp_id and birth.rate_parameter:
                        birth_term = birth.rate_parameter
                        break

                if birth_term:
                    f.write(f"dρ_{comp.primary_name}/dt = {birth_term} - f^out_{comp.primary_name}(ρ)\n")
                else:
                    f.write(f"dρ_{comp.primary_name}/dt = d_{comp.primary_name} - f^out_{comp.primary_name}(ρ)\n")
            else:
                # Constrained node dynamics (e.g., ordinary links in traffic)
                f.write(f"dρ_{comp.primary_name}/dt = f^in_{comp.primary_name}(ρ) - f^out_{comp.primary_name}(ρ)\n")
            f.write("\n")

        f.write("\nwhere f^out and f^in are computed using PP/FIFO rule with split ratios.\n")


def main():
    """Example usage"""
    import sys
    import os

    if len(sys.argv) < 2:
        print("Usage: python equation_generator.py <model_file.compmodel> [output_file.txt]")
        print("\nExample:")
        print("  python equation_generator.py traffic.compmodel traffic_equations.txt")
        print("  python equation_generator.py covid.compmodel covid_equations.txt")
        return

    model_file = sys.argv[1]
    if not os.path.exists(model_file):
        print(f"Error: File '{model_file}' not found")
        return

    output_file = sys.argv[2] if len(sys.argv) > 2 else model_file.replace('.compmodel', '_equations.txt')

    try:
        generator = EquationGenerator(model_file)
        generator.generate_equations(output_file)
        print(f"✓ Equations generated successfully!")
        print(f"  Input:  {model_file}")
        print(f"  Output: {output_file}")
        print(f"  Type:   {'Traffic Network' if generator.is_traffic_network else 'Epidemiological Model'}")
    except Exception as e:
        print(f"Error generating equations: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main()
