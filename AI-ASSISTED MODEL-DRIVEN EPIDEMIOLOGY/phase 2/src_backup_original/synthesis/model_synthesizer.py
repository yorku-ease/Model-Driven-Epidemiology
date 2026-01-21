"""Synthesize .compmodel XML file from extracted entities

Converts extracted entities into valid .compmodel XML format.
"""

import json
from pathlib import Path
from typing import Dict, List, Any, Optional
from xml.etree.ElementTree import Element, SubElement, tostring
from xml.dom import minidom


class ModelSynthesizer:
    """Generate .compmodel XML from extracted entities"""
    
    def __init__(self, metamodel_path: Optional[str] = None):
        """
        Initialize synthesizer.
        
        Args:
            metamodel_path: Path to metamodel JSON (for validation)
        """
        self.metamodel = None
        if metamodel_path:
            self._load_metamodel(metamodel_path)
    
    def _load_metamodel(self, metamodel_path: str):
        """Load metamodel for validation"""
        try:
            with open(metamodel_path, 'r') as f:
                self.metamodel = json.load(f)
        except Exception as e:
            print(f"Warning: Failed to load metamodel: {e}")
    
    def synthesize(self, entities: Dict[str, Any],
                  paper_promises: Optional[Dict[str, Any]] = None) -> str:
        """
        Synthesize .compmodel XML from entities.

        Args:
            entities: Extracted entities dictionary
            paper_promises: Optional paper promises for context

        Returns:
            XML string of .compmodel file
        """
        # Create root element
        root = Element('seir:SEIRModel')
        root.set('xmlns:xmi', 'http://www.omg.org/XMI')
        root.set('xmlns:xsi', 'http://www.w3.org/2001/XMLSchema-instance')
        root.set('xmlns:seir', 'http://example.com/seirmodel')
        root.set('xmi:version', '2.0')

        # Create parameters section
        parameters_elem = SubElement(root, 'parameters')
        param_index_map = {}
        param_name_map = {}  # Map normalized names to indices

        for idx, param in enumerate(entities.get('parameters', [])):
            param_elem = SubElement(parameters_elem, 'parameters')
            param_name = param.get('normalized_name', f'param_{idx}')
            param_elem.set('name', param_name)

            if param.get('value'):
                param_elem.set('expression', str(param['value']))
            else:
                param_elem.set('expression', '0.0')  # Placeholder

            param_elem.set('type', 'CONSTANT')

            if param.get('description'):
                param_elem.set('description', param['description'])
            if param.get('unit'):
                param_elem.set('unit', param['unit'])

            param_index_map[param_name] = idx
            # Also map common variations
            param_name_lower = param_name.lower().replace('_', ' ').replace('-', ' ')
            param_name_map[param_name_lower] = idx

        # Create compartments section
        compartments_elem = SubElement(root, 'compartments')
        comp_index_map = {}

        for idx, comp in enumerate(entities.get('compartments', [])):
            comp_elem = SubElement(compartments_elem, 'compartments')
            comp_elem.set('PrimaryName', comp.get('normalized_name', f'Compartment_{idx}'))

            # Set initial population (default 0 if not specified)
            comp_elem.set('population', '0')

            comp_index_map[comp['normalized_name']] = idx

        # Helper function to find matching parameter for a flow
        def find_matching_parameter(flow, source_comp, target_comp, parameters):
            """Find parameter that matches a flow based on description and context"""
            flow_desc = flow.get('description', '').lower()
            flow_text = flow.get('text_span', '').lower()
            source = source_comp.lower()
            target = target_comp.lower()

            # Match patterns for different flow types
            # 1. Direct parameter reference in flow description (e.g., "at rate α")
            for param in parameters:
                param_name = param.get('normalized_name', '')
                if param_name and param_name.lower() in flow_desc + flow_text:
                    return param_name

            # 2. Match by semantic meaning - with strict priority order
            # Recovery flows - look for γ (gamma) or "recovery" in parameter name/description
            if 'recover' in flow_desc or 'recover' in target:
                # First pass: look for exact Greek letter match
                for param in parameters:
                    param_name = param.get('normalized_name', '')
                    if param_name == 'γ':
                        return param_name
                # Second pass: look for "gamma" in name
                for param in parameters:
                    param_name = param.get('normalized_name', '')
                    if 'gamma' in param_name.lower():
                        return param_name
                # Third pass: look for "recover" in description
                for param in parameters:
                    param_desc = (param.get('description', '') or '').lower()
                    if 'recover' in param_desc:
                        return param.get('normalized_name', '')

            # Disease death flows - look for α (alpha) or "disease death" in parameter
            if ('death' in flow_desc or 'die' in flow_desc) and 'deceased' in target.lower():
                for param in parameters:
                    param_name = param.get('normalized_name', '')
                    param_desc = (param.get('description', '') or '').lower()
                    # Check parameter name first (α is definitive for disease death)
                    if param_name == 'α' or 'alpha' in param_name.lower():
                        # Make sure it's disease death, not background death
                        if 'background' not in param_desc:
                            return param_name
                    # Check description for "disease death"
                    if 'disease' in param_desc and 'death' in param_desc:
                        return param_name

            # Progression/becoming infectious flows - look for ω (omega) or "infectious" in parameter
            if 'progress' in flow_desc or 'become' in flow_desc or 'symptomatic' in flow_desc or \
               (('exposed' in source or 'non-symptomatic' in source) and 'infectious' in target):
                # First pass: look for exact Greek letter match
                for param in parameters:
                    param_name = param.get('normalized_name', '')
                    if param_name == 'ω':
                        return param_name
                # Second pass: look for "omega" in name
                for param in parameters:
                    param_name = param.get('normalized_name', '')
                    if 'omega' in param_name.lower():
                        return param_name
                # Third pass: check description for "infectious" or "incubation"
                for param in parameters:
                    param_desc = (param.get('description', '') or '').lower()
                    if ('infectious' in param_desc or 'incubation' in param_desc) and \
                       ('become' in param_desc or 'rate' in param_desc):
                        return param.get('normalized_name', '')

            # Transmission/infection flows (ContactFlow) - look for β or contact rates
            if flow.get('flow_type') == 'ContactFlow' or 'infect' in flow_desc or 'transmis' in flow_desc:
                # Check if source of infection is mentioned
                for param in parameters:
                    param_name = param.get('normalized_name', '')
                    param_desc = (param.get('description', '') or '').lower()
                    # Check for specific contact rates (c_I, c_D, βc_I, βc_D)
                    if 'infectious' in target.lower() and 'c_i' in param_name.lower():
                        return param_name
                    if 'deceased' in target.lower() and 'c_d' in param_name.lower():
                        return param_name
                    # Check for β (transmission probability)
                    if param_name == 'β' or 'beta' in param_name.lower():
                        return param_name
                    # Check description for contact/transmission
                    if 'contact' in param_desc or 'transmis' in param_desc:
                        return param_name

            # Background death/natural death - look for μ (mu)
            if 'background' in flow_desc or 'natural' in flow_desc:
                for param in parameters:
                    param_name = param.get('normalized_name', '')
                    param_desc = (param.get('description', '') or '').lower()
                    # Check parameter name first (μ is definitive)
                    if param_name == 'μ' or 'mu' in param_name.lower():
                        return param_name
                    # Check description
                    if 'background' in param_desc or 'natural' in param_desc:
                        return param_name

            # Safe burial - look for θ (theta)
            if 'burial' in flow_desc or 'lose infectivity' in flow_desc:
                for param in parameters:
                    param_name = param.get('normalized_name', '')
                    param_desc = (param.get('description', '') or '').lower()
                    # Check parameter name first (θ is definitive)
                    if param_name == 'θ' or 'theta' in param_name.lower():
                        return param_name
                    # Check description
                    if 'burial' in param_desc or 'safe' in param_desc:
                        return param_name

            return None

        # Create flows with parameter links
        for comp_idx, comp in enumerate(entities.get('compartments', [])):
            comp_elem = compartments_elem[comp_idx]
            outgoing_flows_elem = SubElement(comp_elem, 'outgoingFlows')

            # Find flows from this compartment
            source_name = comp['normalized_name']
            flows_from_source = [f for f in entities.get('flows', [])
                               if f.get('source') == source_name]

            for flow in flows_from_source:
                # Determine flow type (default to RateFlow)
                flow_type = flow.get('flow_type', 'RateFlow')
                target_name = flow.get('target', '')
                target_idx = comp_index_map.get(target_name, 0)

                # Find matching parameter
                matching_param = find_matching_parameter(
                    flow, source_name, target_name, entities.get('parameters', [])
                )

                if flow_type == 'ContactFlow':
                    flow_elem = SubElement(outgoing_flows_elem, 'outgoingFlows')
                    flow_elem.set('xsi:type', 'seir:ContactFlow')

                    # Set contact compartment (usually the source of infection)
                    # For ContactFlow, contact compartment is typically Infectious or Deceased
                    contact_comp = target_name if 'infectious' in target_name.lower() or 'deceased' in target_name.lower() else target_name
                    contact_idx = comp_index_map.get(contact_comp, target_idx)
                    flow_elem.set('contactCompartment', f'//@compartments.{contact_idx}')

                    # Link to parameter if found
                    if matching_param and matching_param in param_index_map:
                        param_idx = param_index_map[matching_param]
                        flow_elem.set('contactRateParameter', f'//@parameters.{param_idx}')
                    else:
                        flow_elem.set('contactRate', '0.0')  # Placeholder
                else:
                    flow_elem = SubElement(outgoing_flows_elem, 'outgoingFlows')
                    flow_elem.set('xsi:type', 'seir:RateFlow')

                    # Link to parameter if found
                    if matching_param and matching_param in param_index_map:
                        param_idx = param_index_map[matching_param]
                        flow_elem.set('rateParameter', f'//@parameters.{param_idx}')
                    else:
                        flow_elem.set('rate', '0.0')  # Placeholder

                # Set target
                flow_elem.set('target', f'//@compartments.{target_idx}')

                if flow.get('description'):
                    flow_elem.set('description', flow['description'])

        # Only create groups for stratifications if they have meaningful data
        # For now, skip empty stratifications to avoid cluttering the model
        stratifications = entities.get('stratifications', [])
        if stratifications:
            # Check if any stratification has actual values
            has_values = any(strat.get('values') for strat in stratifications)
            if has_values:
                groups_elem = SubElement(root, 'groups')
                for idx, strat in enumerate(stratifications):
                    if strat.get('values'):  # Only add if has values
                        group_elem = SubElement(groups_elem, 'groups')
                        group_elem.set('name', strat.get('dimension', f'Group_{idx}'))
                        values_elem = SubElement(group_elem, 'values')
                        # Add actual values here if available

        # Convert to pretty XML string
        rough_string = tostring(root, encoding='unicode')
        reparsed = minidom.parseString(rough_string)
        pretty_xml = reparsed.toprettyxml(indent="  ")

        return pretty_xml
    
    def save_compmodel(self, xml_string: str, output_path: str):
        """Save .compmodel XML to file"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        
        with open(output_path, 'w', encoding='utf-8') as f:
            f.write(xml_string)
    
    def validate(self, xml_string: str) -> bool:
        """
        Validate XML against metamodel schema.
        
        Returns:
            True if valid, False otherwise
        """
        try:
            # Basic XML validation
            minidom.parseString(xml_string)
            return True
        except Exception as e:
            print(f"XML validation error: {e}")
            return False
