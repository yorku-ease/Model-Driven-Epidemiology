"""
Utility module for parsing .compmodel XML files
"""
import xml.etree.ElementTree as ET
from typing import Dict, List, Any, Optional
from pathlib import Path


class CompModelParser:
    """Parser for compartmental model XML files"""
    
    def __init__(self, xml_file: str):
        """Initialize parser with XML file path"""
        self.xml_file = Path(xml_file)
        self.tree = self._parse_with_namespace_repair(self.xml_file)
        self.root = self.tree.getroot()
        
        # Extract namespace
        self.ns = {}
        for prefix, uri in self.root.attrib.items():
            if prefix.startswith('xmlns'):
                ns_name = prefix.split(':')[-1] if ':' in prefix else 'default'
                self.ns[ns_name] = uri
        
        # Default namespace (most common)
        if 'compartmental' in self.ns:
            self.ns['default'] = self.ns['compartmental']

    @staticmethod
    def _parse_with_namespace_repair(xml_path: Path) -> ET.ElementTree:
        """
        Parse XML and auto-repair a common malformed-prefix issue:
        files using ``xsi:`` attributes without declaring ``xmlns:xsi``.
        """
        try:
            return ET.parse(str(xml_path))
        except ET.ParseError as e:
            raw = xml_path.read_text(encoding="utf-8", errors="replace")
            if "unbound prefix" not in str(e):
                raise
            if "xsi:" not in raw or "xmlns:xsi" in raw:
                raise

            fixed = raw
            if "xmlns:xmi=" in fixed:
                fixed = fixed.replace(
                    "xmlns:xmi=",
                    'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xmi=',
                    1,
                )
            else:
                fixed = fixed.replace(
                    "<metamodel:CompartmentalModel",
                    '<metamodel:CompartmentalModel xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"',
                    1,
                )
            root = ET.fromstring(fixed)
            return ET.ElementTree(root)
    
    def _find_all(self, tag: str) -> List[ET.Element]:
        """Find all elements with tag, handling namespaces"""
        # Try with namespace
        if 'default' in self.ns:
            full_tag = f"{{{self.ns['default']}}}{tag}"
            elements = self.root.findall(f".//{full_tag}")
            if elements:
                return elements
        
        # Try without namespace
        elements = self.root.findall(f".//{tag}")
        return elements
    
    def _get_attr(self, element: ET.Element, attr: str, default: Any = None) -> Any:
        """Get attribute value, handling xsi:type"""
        if attr == 'type' and 'xsi:type' in element.attrib:
            return element.attrib['xsi:type'].split(':')[-1]
        return element.attrib.get(attr, default)
    
    def get_model_info(self) -> Dict[str, Any]:
        """Extract basic model information"""
        model_elem = self.root
        return {
            'totalPopulation': model_elem.get('totalPopulation'),
            'globalBirthRate': model_elem.get('globalDeathRate', '0'),
            'globalDeathRate': model_elem.get('globalDeathRate', '0'),
        }
    
    def extract_compartments(self) -> List[Dict[str, Any]]:
        """Extract all compartments"""
        compartments = []
        comp_elements = self._find_all('compartments')
        
        for idx, comp in enumerate(comp_elements):
            comp_data = {
                'index': idx,
                'primaryName': comp.get('PrimaryName', ''),
                'secondaryName': comp.get('SecondaryName', ''),
                'population': comp.get('population', '0'),
                'product': comp.get('product', ''),
                'outgoingFlows': []
            }
            
            # Extract flows from this compartment
            flows = comp.findall('.//outgoingFlows')
            for flow in flows:
                flow_data = {
                    'type': self._get_attr(flow, 'type', 'RateFlow'),
                    'target': flow.get('target', ''),
                    'rate': flow.get('rate', ''),
                    'rateParameter': flow.get('rateParameter', ''),
                    'contactRate': flow.get('contactRate', ''),
                    'contactRateParameter': flow.get('contactRateParameter', ''),
                    'contactCompartment': flow.get('contactCompartment', ''),
                    'description': flow.get('description', ''),
                    'stratumSpecificRates': []
                }
                
                # Extract stratum-specific rates
                for ssr in flow.findall('.//stratumSpecificRates'):
                    flow_data['stratumSpecificRates'].append({
                        'stratum': ssr.get('stratum', ''),
                        'rate': ssr.get('rate', ''),
                        'multiplier': ssr.get('multiplier', '')
                    })
                
                comp_data['outgoingFlows'].append(flow_data)
            
            compartments.append(comp_data)
        
        return compartments
    
    def extract_parameters(self) -> List[Dict[str, Any]]:
        """Extract all parameters"""
        parameters = []
        param_elements = self._find_all('parameters')
        
        for idx, param in enumerate(param_elements):
            param_data = {
                'index': idx,
                'name': param.get('name', ''),
                'type': param.get('type', 'CONSTANT'),
                'expression': param.get('expression', ''),
                'description': param.get('description', ''),
                'unit': param.get('unit', '')
            }
            parameters.append(param_data)
        
        return parameters
    
    def extract_groups(self) -> List[Dict[str, Any]]:
        """Extract all groups (for stratification)"""
        groups = []
        group_elements = self._find_all('groups')
        
        for idx, group in enumerate(group_elements):
            group_data = {
                'index': idx,
                'name': group.get('name', ''),
                'description': group.get('description', ''),
                'values': [val.text for val in group.findall('.//values') if val.text]
            }
            groups.append(group_data)
        
        return groups
    
    def extract_products(self) -> List[Dict[str, Any]]:
        """Extract all products (for stratification)"""
        products = []
        product_elements = self._find_all('products')
        
        for idx, product in enumerate(product_elements):
            product_data = {
                'index': idx,
                'name': product.get('name', ''),
                'description': product.get('description', ''),
                'groups': product.get('groups', '')
            }
            products.append(product_data)
        
        return products
    
    def extract_external_sources(self) -> List[Dict[str, Any]]:
        """Extract external sources (births, inputs)"""
        sources = []
        source_elements = self._find_all('externalSources')
        
        for source in source_elements:
            source_data = {
                'name': source.get('name', ''),
                'rate': source.get('rate', ''),
                'rateParameter': source.get('rateParameter', ''),
                'targetCompartment': source.get('targetCompartment', ''),
                'targetStratum': source.get('targetStratum', '')
            }
            sources.append(source_data)
        
        return sources
    
    def extract_external_sinks(self) -> List[Dict[str, Any]]:
        """Extract external sinks (deaths, outputs)"""
        sinks = []
        sink_elements = self._find_all('externalSinks')
        
        for sink in sink_elements:
            sink_data = {
                'name': sink.get('name', ''),
                'rate': sink.get('rate', ''),
                'rateParameter': sink.get('rateParameter', ''),
                'sourceCompartment': sink.get('sourceCompartment', ''),
                'sourceStratum': sink.get('sourceStratum', '')
            }
            sinks.append(sink_data)
        
        return sinks
    
    def extract_all(self) -> Dict[str, Any]:
        """Extract all model components"""
        return {
            'modelInfo': self.get_model_info(),
            'compartments': self.extract_compartments(),
            'parameters': self.extract_parameters(),
            'groups': self.extract_groups(),
            'products': self.extract_products(),
            'externalSources': self.extract_external_sources(),
            'externalSinks': self.extract_external_sinks()
        }

