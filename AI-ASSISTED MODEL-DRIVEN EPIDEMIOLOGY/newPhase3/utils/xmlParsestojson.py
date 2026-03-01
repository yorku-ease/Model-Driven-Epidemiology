"""XML to JSON converter for EpiMDE Compartmental Models"""

import re
import json
from typing import Dict, Any, Optional, List
from xml.etree import ElementTree as ET


def parse_compartmental_xml(xml_string: str) -> Dict[str, Any]:
    """
    Parse a compartmental model XML string and convert to canonical JSON format.

    Args:
        xml_string: XML content as string

    Returns:
        Dictionary conforming to EpiMDE Canonical JSON schema
    """
    root = ET.fromstring(xml_string)

    params = _extract_parameters(root)
    compartments = _extract_compartments(root)
    flows = _extract_flows(root, compartments, params)

    return {"compartments": compartments, "flows": flows, "parameters": params}


def _strip_ns(tag: str) -> str:
    """Strip namespace from tag."""
    if "}" in tag:
        return tag.split("}")[1]
    return tag


def _find_all(root: ET.Element, local_name: str) -> List[ET.Element]:
    """Find all elements with given local name regardless of namespace."""
    return [elem for elem in root.iter() if _strip_ns(elem.tag) == local_name]


def _extract_parameters(root: ET.Element) -> List[Dict[str, Any]]:
    """Extract parameters from XML."""
    params = []

    for param in _find_all(root, "parameters"):
        name = param.get("name", "")
        param_type = param.get("type", "CONSTANT")
        expression = param.get("expression", "")
        description = param.get("description", "")
        unit = param.get("unit", "")

        value = expression if expression else None
        if param_type == "CONSTANT":
            try:
                value = float(expression) if expression else None
            except (ValueError, TypeError):
                pass

        param_entry = {
            "symbol": name,
            "type": param_type,
            "value": value,
            "unit": unit,
            "description": description,
        }
        params.append(param_entry)

    return params


def _extract_compartments(root: ET.Element) -> List[Dict[str, Any]]:
    """Extract compartments from XML."""
    compartments = []

    for comp in _find_all(root, "compartments"):
        primary_name = comp.get("PrimaryName", "")
        secondary_name = comp.get("SecondaryName", "")

        if secondary_name:
            name = f"{primary_name} {secondary_name}"
        else:
            name = primary_name

        population_str = comp.get("population", "")
        try:
            initial_population = float(population_str) if population_str else None
        except (ValueError, TypeError):
            initial_population = None

        compartments.append(
            {
                "name": name,
                "secondary_name": secondary_name if secondary_name else "",
                "initial_population": initial_population,
            }
        )

    return compartments


def _extract_flows(
    root: ET.Element,
    compartments: List[Dict[str, Any]],
    parameters: List[Dict[str, Any]],
) -> List[Dict[str, Any]]:
    """Extract flows from XML including outgoingFlows, birthSources, and deathSinks."""
    flows = []

    param_lookup = {p["symbol"]: p["symbol"] for p in parameters}
    for i, p in enumerate(parameters):
        param_lookup[f"//@parameters.{i}"] = p["symbol"]
        param_lookup[f"parameters.{i}"] = p["symbol"]

    comp_lookup = {c["name"]: c["name"] for c in compartments}
    for i, c in enumerate(compartments):
        comp_lookup[f"//@compartments.{i}"] = c["name"]
        comp_lookup[f"compartments.{i}"] = c["name"]

    all_compartments = _find_all(root, "compartments")

    for comp in all_compartments:
        comp_idx = all_compartments.index(comp)
        source_name = (
            compartments[comp_idx]["name"] if comp_idx < len(compartments) else None
        )

        for flow in comp.findall("outgoingFlows"):
            flow_type = flow.get("{http://www.w3.org/2001/XMLSchema-instance}type", "")

            if "RateFlow" in flow_type:
                target = flow.get("target", "")
                rate_value = flow.get("rate", "")
                rate_param = flow.get("rateParameter", "")
                description = flow.get("description", "")

                target_name = _resolve_reference(target, comp_lookup)

                flow_entry = {
                    "source": source_name,
                    "target": target_name,
                    "type": "RateFlow",
                    "rate_parameter": _resolve_reference(rate_param, param_lookup)
                    if rate_param
                    else None,
                    "rate_value": float(rate_value)
                    if rate_value and rate_value != "0.0"
                    else None,
                    "rate_unit": None,
                    "description": description,
                }
                flows.append(flow_entry)

            elif "ContactFlow" in flow_type:
                target = flow.get("target", "")
                contact_rate = flow.get("contactRate", "")
                contact_rate_param = flow.get("contactRateParameter", "")
                contact_compartment = flow.get("contactCompartment", "")
                description = flow.get("description", "")

                target_name = _resolve_reference(target, comp_lookup)
                contact_comp_name = _resolve_reference(contact_compartment, comp_lookup)

                flow_entry = {
                    "source": source_name,
                    "target": target_name,
                    "type": "ContactFlow",
                    "contact_compartment": contact_comp_name,
                    "rate_parameter": _resolve_reference(
                        contact_rate_param, param_lookup
                    )
                    if contact_rate_param
                    else None,
                    "rate_value": float(contact_rate)
                    if contact_rate and contact_rate != "0.0"
                    else None,
                    "rate_unit": None,
                    "description": description,
                }
                flows.append(flow_entry)

    for birth in _find_all(root, "birthSources"):
        name = birth.get("name", "")
        target_comp = birth.get("targetCompartment", "")
        rate_value = birth.get("rate", "")
        rate_param = birth.get("rateParameter", "")

        target_name = _resolve_reference(target_comp, comp_lookup)

        flow_entry = {
            "source": name,
            "target": target_name,
            "type": "BirthSource",
            "rate_parameter": _resolve_reference(rate_param, param_lookup)
            if rate_param
            else None,
            "rate_value": float(rate_value) if rate_value else None,
            "rate_unit": None,
            "description": birth.get("description", ""),
        }
        flows.append(flow_entry)

    for death in _find_all(root, "deathSinks"):
        name = death.get("name", "")
        source_comp = death.get("sourceCompartment", "")
        rate_value = death.get("rate", "")
        rate_param = death.get("rateParameter", "")

        source_name = _resolve_reference(source_comp, comp_lookup)

        flow_entry = {
            "source": source_name,
            "target": name,
            "type": "DeathSink",
            "rate_parameter": _resolve_reference(rate_param, param_lookup)
            if rate_param
            else None,
            "rate_value": float(rate_value) if rate_value else None,
            "rate_unit": None,
            "description": death.get("description", ""),
        }
        flows.append(flow_entry)

    for ext_sink in _find_all(root, "externalSinks"):
        name = ext_sink.get("name", "")
        source_comp = ext_sink.get("sourceCompartment", "")
        rate_param = ext_sink.get("rateParameter", "")

        source_name = _resolve_reference(source_comp, comp_lookup)

        flow_entry = {
            "source": source_name,
            "target": name,
            "type": "DeathSink",
            "rate_parameter": _resolve_reference(rate_param, param_lookup)
            if rate_param
            else None,
            "rate_value": None,
            "rate_unit": None,
            "description": name,
        }
        flows.append(flow_entry)

    return flows


def _resolve_reference(ref: str, lookup: Dict[str, str]) -> Optional[str]:
    """Resolve a reference like //@compartments.X or //@parameters.X to actual name."""
    if not ref:
        return None

    if ref in lookup:
        return lookup[ref]

    match = re.match(r"//@(compartments|parameters)\.(\d+)", ref)
    if match:
        ref_type, idx = match.groups()
        key = f"//@{ref_type}.{idx}"
        return lookup.get(key)

    return None


def validate_compartmental_json(data: Dict[str, Any]) -> tuple[bool, List[str]]:
    """
    Validate JSON output against EpiMDE schema.

    Returns:
        (is_valid, error_messages)
    """
    errors = []

    if "compartments" not in data:
        errors.append("Missing required field: compartments")
    elif not isinstance(data["compartments"], list):
        errors.append("compartments must be an array")
    else:
        for i, comp in enumerate(data["compartments"]):
            if "name" not in comp:
                errors.append(f"compartments[{i}]: missing required field 'name'")

    if "flows" not in data:
        errors.append("Missing required field: flows")
    elif not isinstance(data["flows"], list):
        errors.append("flows must be an array")
    else:
        for i, flow in enumerate(data["flows"]):
            if "source" not in flow:
                errors.append(f"flows[{i}]: missing required field 'source'")
            if "target" not in flow:
                errors.append(f"flows[{i}]: missing required field 'target'")
            if "type" not in flow:
                errors.append(f"flows[{i}]: missing required field 'type'")
            else:
                valid_types = ["ContactFlow", "RateFlow", "BirthSource", "DeathSink"]
                if flow["type"] not in valid_types:
                    errors.append(f"flows[{i}]: type must be one of {valid_types}")

    if "parameters" not in data:
        errors.append("Missing required field: parameters")
    elif not isinstance(data["parameters"], list):
        errors.append("parameters must be an array")
    else:
        for i, param in enumerate(data["parameters"]):
            if "symbol" not in param:
                errors.append(f"parameters[{i}]: missing required field 'symbol'")
            if "type" in param:
                valid_types = ["CONSTANT", "VARIABLE", "EXPRESSION"]
                if param["type"] not in valid_types:
                    errors.append(f"parameters[{i}]: type must be one of {valid_types}")

    return len(errors) == 0, errors


if __name__ == "__main__":
    sample_xml = """<?xml version="1.0" encoding="UTF-8"?>
<compartmental:CompartmentalModel xmi:version="2.0"
  xmlns:xmi="http://www.omg.org/XMI"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:compartmental="http://example.com/compartmentalmodel">

  <compartments PrimaryName="Susceptible (S)">
    <outgoingFlows xsi:type="compartmental:ContactFlow"
      contactRate="0.0"
      contactRateParameter="//@parameters.0"
      contactCompartment="//@compartments.3"
      target="//@compartments.1"
      description="Infection from exposure to contaminated water reservoir"/>
  </compartments>

  <compartments PrimaryName="Infectious (I)">
    <outgoingFlows xsi:type="compartmental:RateFlow"
      rate="0.0"
      rateParameter="//@parameters.1"
      target="//@compartments.2"
      description="Recovery and immunity"/>
    <outgoingFlows xsi:type="compartmental:RateFlow"
      rate="0.0"
      rateParameter="//@parameters.2"
      target="//@compartments.3"
      description="Shedding/contamination of water reservoir by infectious humans"/>
  </compartments>

  <compartments PrimaryName="Recovered (R)"/>

  <compartments PrimaryName="Bacterial concentration in water reservoir (B)"/>

  <externalSinks name="Bacteria decay/removal"
    rateParameter="//@parameters.3"
    sourceCompartment="//@compartments.3"/>

  <parameters name="λ"
    type="EXPRESSION"
    expression="(contact_with_reservoir * B * IC50_term)"
    description="Force of infection term"
    unit="1/time"/>

  <parameters name="γ"
    expression="(placeholder)"
    description="Per-capita recovery rate"
    unit="1/time"/>

  <parameters name="α"
    expression="(placeholder)"
    description="Rate of water contamination"
    unit="cells mL^-1 person^-1 day^-1"/>

  <parameters name="μ_B"
    expression="(placeholder)"
    description="Removal/decay rate of V. cholerae"
    unit="1/time"/>

  <parameters name="K"
    expression="(placeholder)"
    description="IC50-like parameter"
    unit="cells/mL"/>

</compartmental:CompartmentalModel>"""

    result = parse_compartmental_xml(sample_xml)
    print(json.dumps(result, indent=2))

    is_valid, errors = validate_compartmental_json(result)
    print(f"\nValidation: {'PASSED' if is_valid else 'FAILED'}")
    if errors:
        for e in errors:
            print(f"  - {e}")
