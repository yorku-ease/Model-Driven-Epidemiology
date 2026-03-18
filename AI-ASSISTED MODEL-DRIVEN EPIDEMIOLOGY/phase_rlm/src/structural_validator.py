"""
Structural Validator for Disease Models

Pure Python validation of .compmodel XML files.
Checks for common structural errors that indicate failed extraction.
"""

import json
import re
import xml.etree.ElementTree as ET
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any, Dict, List, Optional, Set


@dataclass
class TypedError:
    """A typed structural error in the disease model."""

    type: str
    element: str
    detail: str
    severity: str
    compartment_idx: Optional[int] = None
    parameter_idx: Optional[int] = None
    flow_idx: Optional[int] = None

    def to_dict(self) -> Dict[str, Any]:
        return {
            "type": self.type,
            "element": self.element,
            "detail": self.detail,
            "severity": self.severity,
            "compartment_idx": self.compartment_idx,
            "parameter_idx": self.parameter_idx,
            "flow_idx": self.flow_idx,
        }


@dataclass
class ModelStructure:
    """Parsed model structure from .compmodel XML."""

    compartments: List[Dict[str, Any]] = field(default_factory=list)
    parameters: List[Dict[str, Any]] = field(default_factory=list)
    flows: List[Dict[str, Any]] = field(default_factory=list)
    external_sources: List[Dict[str, Any]] = field(default_factory=list)
    external_sinks: List[Dict[str, Any]] = field(default_factory=list)
    raw_xml: str = ""

    def to_dict(self) -> Dict[str, Any]:
        return {
            "compartments": self.compartments,
            "parameters": self.parameters,
            "flows": self.flows,
            "external_sources": self.external_sources,
            "external_sinks": self.external_sinks,
        }


class StructuralValidator:
    """Validates disease models for structural errors."""

    def __init__(self, config: Optional[Dict[str, Any]] = None):
        self.config = config or {}

    def parse_compmodel(self, compmodel_path: Path) -> ModelStructure:
        """Parse a .compmodel XML file into a ModelStructure."""
        raw = compmodel_path.read_text(encoding="utf-8", errors="replace")

        if "xmlns:xsi" not in raw and "xsi:" in raw:
            raw = raw.replace(
                "xmlns:xmi=",
                'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xmi=',
            )

        root = ET.fromstring(raw)

        compartments = []
        parameters = []
        flows = []
        external_sources = []
        external_sinks = []

        for el in root.iter():
            tag = el.tag.split("}")[-1] if "}" in el.tag else el.tag

            if tag == "compartments":
                name = el.get("PrimaryName", "")
                population = el.get("population", "0")
                compartment_idx = len(compartments)

                compartment_flows = []
                for flow_el in el.findall(".//outgoingFlows"):
                    flow_type = flow_el.get(
                        "{http://www.w3.org/2001/XMLSchema-instance}type", ""
                    )
                    if "ContactFlow" in flow_type:
                        flow_type = "ContactFlow"
                    else:
                        flow_type = "RateFlow"

                    target = flow_el.get("target", "")
                    contact_compartment = flow_el.get("contactCompartment", "")
                    rate_param = flow_el.get("rateParameter", "")
                    contact_rate_param = flow_el.get("contactRateParameter", "")
                    rate = flow_el.get("rate", "0.0")
                    contact_rate = flow_el.get("contactRate", "0.0")

                    flow = {
                        "type": flow_type,
                        "source": name,
                        "source_idx": compartment_idx,
                        "target": target,
                        "contactCompartment": contact_compartment,
                        "rateParameter": rate_param,
                        "contactRateParameter": contact_rate_param,
                        "rate": rate,
                        "contactRate": contact_rate,
                        "description": flow_el.get("description", ""),
                    }
                    flows.append(flow)
                    compartment_flows.append(flow)

                compartments.append(
                    {
                        "name": name,
                        "population": population,
                        "idx": compartment_idx,
                        "outgoing_flows": compartment_flows,
                    }
                )

            elif tag == "parameters":
                name = el.get("name", "")
                expression = el.get("expression", "")
                param_type = el.get("type", "CONSTANT")
                description = el.get("description", "")

                parameters.append(
                    {
                        "name": name,
                        "expression": expression,
                        "type": param_type,
                        "description": description,
                        "idx": len(parameters),
                    }
                )

            elif tag == "externalSources":
                source_name = el.get("name", "")
                rate_param = el.get("rateParameter", "")
                target = el.get("targetCompartment", "")

                external_sources.append(
                    {
                        "name": source_name,
                        "rateParameter": rate_param,
                        "target": target,
                    }
                )

            elif tag == "externalSinks":
                sink_name = el.get("name", "")
                rate_param = el.get("rateParameter", "")
                source = el.get("sourceCompartment", "")

                external_sinks.append(
                    {
                        "name": sink_name,
                        "rateParameter": rate_param,
                        "source": source,
                    }
                )

        return ModelStructure(
            compartments=compartments,
            parameters=parameters,
            flows=flows,
            external_sources=external_sources,
            external_sinks=external_sinks,
            raw_xml=raw,
        )

    def _get_compartment_name_by_idx(self, idx_str: str, model: ModelStructure) -> str:
        """Extract compartment name from index reference like //@compartments.2"""
        if not idx_str:
            return ""
        match = re.search(r"\.(\d+)", idx_str)
        if match:
            idx = int(match.group(1))
            if 0 <= idx < len(model.compartments):
                return model.compartments[idx]["name"]
        return idx_str

    def _get_parameter_name_by_idx(self, idx_str: str, model: ModelStructure) -> str:
        """Extract parameter name from index reference like //@parameters.2"""
        if not idx_str:
            return ""
        match = re.search(r"\.(\d+)", idx_str)
        if match:
            idx = int(match.group(1))
            if 0 <= idx < len(model.parameters):
                return model.parameters[idx]["name"]
        return idx_str

    def _get_referenced_parameters(self, model: ModelStructure) -> Set[str]:
        """Get set of all parameter names referenced in flows."""
        referenced = set()

        for flow in model.flows:
            rate_param = flow.get("rateParameter", "")
            contact_rate_param = flow.get("contactRateParameter", "")

            if rate_param:
                param_name = self._get_parameter_name_by_idx(rate_param, model)
                if param_name:
                    referenced.add(param_name)

            if contact_rate_param:
                param_name = self._get_parameter_name_by_idx(contact_rate_param, model)
                if param_name:
                    referenced.add(param_name)

        for sink in model.external_sinks:
            rate_param = sink.get("rateParameter", "")
            if rate_param:
                param_name = self._get_parameter_name_by_idx(rate_param, model)
                if param_name:
                    referenced.add(param_name)

        for source in model.external_sources:
            rate_param = source.get("rateParameter", "")
            if rate_param:
                param_name = self._get_parameter_name_by_idx(rate_param, model)
                if param_name:
                    referenced.add(param_name)

        return referenced

    def check_self_referential_flows(self, model: ModelStructure) -> List[TypedError]:
        """
        Check for ContactFlow where contactCompartment == target.
        This was the #1 failure across HIV, Influenza, Malaria.
        """
        errors = []

        for flow in model.flows:
            if flow["type"] == "ContactFlow":
                target = flow.get("target", "")
                contact_comp = flow.get("contactCompartment", "")

                target_name = self._get_compartment_name_by_idx(target, model)
                contact_name = self._get_compartment_name_by_idx(contact_comp, model)

                if target == contact_comp or target_name == contact_name:
                    errors.append(
                        TypedError(
                            type="self_referential_flow",
                            element=f"{flow['source']} -> ContactFlow",
                            detail=f"ContactFlow has contactCompartment={contact_comp} and target={target}. "
                            f"These should be different compartments. ContactFlow moves source "
                            f"to target upon contact with contactCompartment.",
                            severity="critical",
                            flow_idx=model.flows.index(flow),
                        )
                    )

        return errors

    def check_missing_birth_sources(self, model: ModelStructure) -> List[TypedError]:
        """
        Check for compartments with no incoming flow AND no birthSource AND population=0.
        Indicates missing recruitment into susceptible compartments.
        """
        errors = []

        incoming_targets = set()
        for flow in model.flows:
            target = flow.get("target", "")
            if target:
                incoming_targets.add(target)

        for source in model.external_sources:
            target = source.get("targetCompartment", "")
            if target:
                incoming_targets.add(target)

        for comp in model.compartments:
            comp_ref = f"//@compartments.{comp['idx']}"
            population = comp.get("population", "0")

            if population == "0" and comp_ref not in incoming_targets:
                is_susceptible = "susceptible" in comp["name"].lower()

                if is_susceptible:
                    errors.append(
                        TypedError(
                            type="missing_birth_sources",
                            element=comp["name"],
                            detail=f"Compartment '{comp['name']}' has population=0 and no incoming flow or "
                            f"externalSource. Susceptible compartments typically need recruitment.",
                            severity="high",
                            compartment_idx=comp["idx"],
                        )
                    )

        return errors

    def check_zero_population_all(self, model: ModelStructure) -> List[TypedError]:
        """
        Check if ALL compartments have population=0.
        This appeared in every hard disease (HIV, Malaria, etc).
        """
        errors = []

        all_zero = all(
            comp.get("population", "0") == "0" for comp in model.compartments
        )

        if all_zero and model.compartments:
            errors.append(
                TypedError(
                    type="zero_population_all",
                    element="all_compartments",
                    detail=f"All {len(model.compartments)} compartments have population=0. "
                    f"This indicates a critical extraction failure.",
                    severity="critical",
                )
            )

        return errors

    def check_orphaned_parameters(self, model: ModelStructure) -> List[TypedError]:
        """
        Check for parameters declared but not referenced in any flow.
        HIV had b_s, b_h, c_s, c_h declared but never composed.
        """
        errors = []

        referenced = self._get_referenced_parameters(model)

        for param in model.parameters:
            param_name = param["name"]
            if param_name and param_name not in referenced:
                errors.append(
                    TypedError(
                        type="orphaned_parameters",
                        element=param_name,
                        detail=f"Parameter '{param_name}' is declared but never referenced in any flow. "
                        f"It may have been extracted but not properly composed into the model.",
                        severity="medium",
                        parameter_idx=param["idx"],
                    )
                )

        return errors

    def check_missing_death_sinks(self, model: ModelStructure) -> List[TypedError]:
        """
        Check for long-running models (endemic diseases) without death flows.
        Absence is almost always an error for endemic diseases like HIV, Malaria.
        """
        errors = []

        has_death_sinks = len(model.external_sinks) > 0

        if not has_death_sinks and len(model.compartments) > 4:
            death_keywords = ["death", "mortality", "sink", "removal"]

            has_death_param = any(
                "death" in p.get("description", "").lower()
                or "mortality" in p.get("description", "").lower()
                for p in model.parameters
            )

            if not has_death_param:
                errors.append(
                    TypedError(
                        type="missing_death_sinks",
                        element="model",
                        detail=f"Model has {len(model.compartments)} compartments but no externalSinks "
                        f"(death flows). Endemic disease models typically require death sinks.",
                        severity="high",
                    )
                )

        return errors

    def check_flow_chain_completeness(self, model: ModelStructure) -> List[TypedError]:
        """
        Check that every non-sink compartment has at least one outgoing flow.
        Influenza had I2 with no flow to R.
        """
        errors = []

        compartments_with_death_sinks = set()
        for sink in model.external_sinks:
            source = sink.get("sourceCompartment", "")
            if source:
                compartments_with_death_sinks.add(source)

        for comp in model.compartments:
            has_outgoing = len(comp.get("outgoing_flows", [])) > 0
            has_death_sink = (
                f"//@compartments.{comp['idx']}" in compartments_with_death_sinks
            )

            if not has_outgoing and not has_death_sink:
                is_susceptible = "susceptible" in comp["name"].lower()
                is_immune = (
                    "immune" in comp["name"].lower()
                    or "recovered" in comp["name"].lower()
                )

                if not (is_susceptible or is_immune):
                    errors.append(
                        TypedError(
                            type="flow_chain_incomplete",
                            element=comp["name"],
                            detail=f"Compartment '{comp['name']}' has no outgoing flow or sink. "
                            f"This breaks the flow chain.",
                            severity="high",
                            compartment_idx=comp["idx"],
                        )
                    )

        return errors

    def check_parameter_layer_contamination(
        self, model: ModelStructure
    ) -> List[TypedError]:
        """
        Check for parameters with expressions like 'Inferred' or type=VARIABLE with no formula.
        These likely represent inference layer bleed from Bayesian fitting sections.
        """
        errors = []

        inference_indicators = [
            "inferred",
            "estimated",
            "fitted",
            "posterior",
            "prior",
            "MCMC",
            "chain",
            "likelihood",
            "Bayesian",
        ]

        for param in model.parameters:
            expression = param.get("expression", "")
            param_type = param.get("type", "CONSTANT")
            description = param.get("description", "")

            is_inference_layer = any(
                indicator in expression.lower() or indicator in description.lower()
                for indicator in inference_indicators
            )

            if is_inference_layer:
                errors.append(
                    TypedError(
                        type="parameter_layer_contamination",
                        element=param["name"],
                        detail=f"Parameter '{param['name']}' has expression='{expression}' or description "
                        f"containing inference keywords. This may be contamination from "
                        f"Bayesian fitting sections rather than mechanistic parameters.",
                        severity="medium",
                        parameter_idx=param["idx"],
                    )
                )

        return errors

    def check_composite_parameter_decomposition(
        self, model: ModelStructure
    ) -> List[TypedError]:
        """
        Check if primitive params exist but composite not properly composed.
        If primitive params like b_h, c_h exist but composite ch_bh is not used.
        """
        errors = []

        param_names = {p["name"].lower() for p in model.parameters if p["name"]}

        primitive_patterns = [
            (r"^b[sh]?$", "transmission"),
            (r"^c[sh]?$", "contact"),
            (r"^beta", "transmission"),
            (r"^gamma", "recovery"),
        ]

        for param in model.parameters:
            pname = param["name"].lower()

            for pattern, meaning in primitive_patterns:
                if re.match(pattern, pname):
                    if len(model.compartments) > 6:
                        if not any(
                            pname in ref.lower()
                            for ref in self._get_referenced_parameters(model)
                        ):
                            errors.append(
                                TypedError(
                                    type="composite_parameter_decomposition",
                                    element=param["name"],
                                    detail=f"Parameter '{param['name']}' appears to be a primitive "
                                    f"({meaning}) but may need to be composed into a composite "
                                    f"parameter for proper model formulation.",
                                    severity="low",
                                    parameter_idx=param["idx"],
                                )
                            )

        return errors

    def check_uniform_parameter_collapse(
        self, model: ModelStructure
    ) -> List[TypedError]:
        """
        Check if most flows reference the same parameter index.
        This indicates systematic index collapse — LLM defaulted to //@parameters.0
        for most flows regardless of what parameter each flow actually needs.
        """
        errors = []

        rate_params = []
        for f in model.flows:
            rp = f.get("rateParameter", "")
            crp = f.get("contactRateParameter", "")
            if rp:
                rate_params.append(rp)
            if crp:
                rate_params.append(crp)

        if len(rate_params) < 3:
            return []

        from collections import Counter

        param_counts = Counter(rate_params)

        most_common_param, most_common_count = param_counts.most_common(1)[0]
        collapse_ratio = most_common_count / len(rate_params)

        if collapse_ratio >= 0.8:
            errors.append(
                TypedError(
                    type="uniform_parameter_collapse",
                    element="all_flows",
                    detail=f"{int(collapse_ratio * 100)}% of flows ({most_common_count}/{len(rate_params)}) "
                    f"reference the same parameter ({most_common_param}). This indicates systematic "
                    f"index collapse — each flow should reference its own rate parameter based on "
                    f"the flow description.",
                    severity="critical",
                )
            )

        return errors

    def validate(self, compmodel_path: Path) -> Dict[str, Any]:
        """
        Run all validation checks on a .compmodel file.
        Returns a dict with all errors found.
        """
        model = self.parse_compmodel(compmodel_path)

        all_errors = []

        all_errors.extend(self.check_self_referential_flows(model))
        all_errors.extend(self.check_missing_birth_sources(model))
        all_errors.extend(self.check_zero_population_all(model))
        all_errors.extend(self.check_orphaned_parameters(model))
        all_errors.extend(self.check_uniform_parameter_collapse(model))
        all_errors.extend(self.check_missing_death_sinks(model))
        all_errors.extend(self.check_flow_chain_completeness(model))
        all_errors.extend(self.check_parameter_layer_contamination(model))
        all_errors.extend(self.check_composite_parameter_decomposition(model))

        critical = [e for e in all_errors if e.severity == "critical"]
        high = [e for e in all_errors if e.severity == "high"]
        medium = [e for e in all_errors if e.severity == "medium"]
        low = [e for e in all_errors if e.severity == "low"]

        return {
            "valid": len(all_errors) == 0,
            "total_errors": len(all_errors),
            "errors_by_severity": {
                "critical": len(critical),
                "high": len(high),
                "medium": len(medium),
                "low": len(low),
            },
            "errors": [e.to_dict() for e in all_errors],
            "model_structure": model.to_dict(),
        }


def validate_compmodel(
    compmodel_path: Path, config: Optional[Dict[str, Any]] = None
) -> Dict[str, Any]:
    """Convenience function to validate a compmodel file."""
    validator = StructuralValidator(config)
    return validator.validate(compmodel_path)


if __name__ == "__main__":
    import sys

    if len(sys.argv) < 2:
        print("Usage: python structural_validator.py <path_to.compmodel>")
        sys.exit(1)

    result = validate_compmodel(Path(sys.argv[1]))
    print(json.dumps(result, indent=2))
