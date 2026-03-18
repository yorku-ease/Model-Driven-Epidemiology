"""
Repair Engine

Performs targeted LLM-based repairs for specific model errors.
Each repair is scoped to ONE error, ONE element, relevant context only.
"""

import json
import re
import xml.etree.ElementTree as ET
from dataclasses import dataclass
from pathlib import Path
from typing import Any, Dict, List, Optional

from .repair_dispatcher import RepairContext
from .utils.llm_client import RLMClient


@dataclass
class RepairResult:
    """Result of a repair attempt."""

    success: bool
    original_error: str
    repaired_model_xml: str
    explanation: str
    retry_count: int
    error_type: str


class RepairEngine:
    """Engine for performing targeted repairs on disease models."""

    SYSTEM_INSTRUCTION = """You are repairing a specific error in an epidemiological model XML.
Only modify the element described. Do not change anything else.
Return ONLY valid XML for the corrected element, or indicate if the paper doesn't contain enough information."""

    def __init__(self, llm_client: RLMClient, config: Optional[Dict[str, Any]] = None):
        self.llm = llm_client
        self.config = config or {}
        self.max_retries = self.config.get("retry_per_error", 2)

    def repair(
        self,
        context: RepairContext,
        current_model_xml: str,
    ) -> RepairResult:
        """
        Attempt to repair a single error.

        Args:
            context: RepairContext with error details and relevant sections
            current_model_xml: Current model XML string

        Returns:
            RepairResult with success status and repaired model
        """
        if context.error_type == "self_referential_flow":
            return self._auto_fix_self_referential(current_model_xml, context)

        prompt = self._build_repair_prompt(context, current_model_xml)

        for retry in range(self.max_retries + 1):
            try:
                response = self.llm.generate(
                    prompt=prompt,
                    system_instruction=self.SYSTEM_INSTRUCTION,
                    temperature=0.3,
                    max_tokens=2000,
                )

                repaired_xml = self._apply_repair(
                    current_model_xml,
                    response,
                    context,
                )

                if repaired_xml:
                    if repaired_xml == current_model_xml:
                        return RepairResult(
                            success=False,
                            original_error=context.error_element,
                            repaired_model_xml=current_model_xml,
                            explanation="Repair attempted but model unchanged",
                            retry_count=retry,
                            error_type=context.error_type,
                        )
                    return RepairResult(
                        success=True,
                        original_error=context.error_element,
                        repaired_model_xml=repaired_xml,
                        explanation=response,
                        retry_count=retry,
                        error_type=context.error_type,
                    )
                else:
                    if retry < self.max_retries:
                        continue
                    else:
                        return RepairResult(
                            success=False,
                            original_error=context.error_element,
                            repaired_model_xml=current_model_xml,
                            explanation=f"Failed to parse repair response: {response[:200]}",
                            retry_count=retry,
                            error_type=context.error_type,
                        )

            except Exception as e:
                if retry < self.max_retries:
                    continue
                return RepairResult(
                    success=False,
                    original_error=context.error_element,
                    repaired_model_xml=current_model_xml,
                    explanation=f"LLM error: {str(e)}",
                    retry_count=retry,
                    error_type=context.error_type,
                )

        return RepairResult(
            success=False,
            original_error=context.error_element,
            repaired_model_xml=current_model_xml,
            explanation="Max retries exceeded",
            retry_count=self.max_retries,
            error_type=context.error_type,
        )

    def _build_repair_prompt(self, context: RepairContext, model_xml: str) -> str:
        """Build the repair prompt for the LLM."""
        sections_text = ""

        if context.relevant_sections:
            sections_parts = []
            for i, section in enumerate(context.relevant_sections[:3], 1):
                content_preview = section.content[:1500]
                sections_parts.append(
                    f"SECTION {i}: {section.title}\n{content_preview}"
                )
            sections_text = "\n\n".join(sections_parts)
        else:
            sections_text = "No relevant mechanistic sections found in paper."

        prompt = f"""ERROR TYPE: {context.error_type}
ELEMENT: {context.error_element}
SEVERITY: {context.severity}

PROBLEM DESCRIPTION:
{context.error_detail}

RELEVANT PAPER SECTIONS:
{sections_text}

CURRENT MODEL XML (relevant portion):
{model_xml[:3000]}

TASK: Based ONLY on the paper sections above, identify the correct fix.
Return the corrected XML element only. If unsure, return: <REPAIR_FAILED reason="insufficient_information"/>
"""

        return prompt

    def _auto_fix_self_referential(
        self,
        xml: str,
        context: RepairContext,
    ) -> RepairResult:
        """
        Auto-fix self-referential ContactFlows without LLM.

        Logic: For ContactFlow from S, the target should be E (Exposed),
        and the contactCompartment should be I (Infectious).
        """
        try:
            root = ET.fromstring(xml)
            compartments = root.findall(
                ".//{http://example.com/compartmentalmodel}compartments"
            )
            if not compartments:
                compartments = root.findall(".//compartments")

            compartment_names = [c.get("PrimaryName", "") for c in compartments]

            infectious_idx = None
            exposed_idx = None

            for i, name in enumerate(compartment_names):
                name_lower = name.lower()
                if "infectious" in name_lower or "infected" in name_lower:
                    if infectious_idx is None:
                        infectious_idx = i
                if "exposed" in name_lower or "latent" in name_lower:
                    if exposed_idx is None:
                        exposed_idx = i

            fixed_count = 0
            for comp in root.iter():
                tag = comp.tag.split("}")[-1] if "}" in comp.tag else comp.tag
                if tag != "compartments":
                    continue

                for flow in comp.findall(".//outgoingFlows"):
                    flow_type = flow.get(
                        "{http://www.w3.org/2001/XMLSchema-instance}type", ""
                    )

                    if "ContactFlow" not in flow_type:
                        continue

                    target = flow.get("target", "")
                    contact = flow.get("contactCompartment", "")

                    if target == contact:
                        if exposed_idx is not None:
                            flow.set("target", f"//@compartments.{exposed_idx}")
                        if infectious_idx is not None:
                            flow.set(
                                "contactCompartment",
                                f"//@compartments.{infectious_idx}",
                            )
                        fixed_count += 1

            if fixed_count > 0:
                return RepairResult(
                    success=True,
                    original_error=context.error_element,
                    repaired_model_xml=ET.tostring(root, encoding="unicode"),
                    explanation=f"Auto-fixed {fixed_count} self-referential ContactFlow(s). "
                    f"Set target=Exposed(idx={exposed_idx}), contact=Infectious(idx={infectious_idx})",
                    retry_count=0,
                    error_type=context.error_type,
                )
            else:
                return RepairResult(
                    success=False,
                    original_error=context.error_element,
                    repaired_model_xml=xml,
                    explanation="No self-referential ContactFlow found to fix",
                    retry_count=0,
                    error_type=context.error_type,
                )

        except Exception as e:
            return RepairResult(
                success=False,
                original_error=context.error_element,
                repaired_model_xml=xml,
                explanation=f"Auto-fix error: {str(e)}",
                retry_count=0,
                error_type=context.error_type,
            )

    def _apply_repair(
        self,
        original_xml: str,
        repair_response: str,
        context: RepairContext,
    ) -> Optional[str]:
        """Apply the repair response to the model XML."""
        if "<REPAIR_FAILED" in repair_response:
            return None

        cleaned = self._extract_xml_from_response(repair_response)
        if not cleaned:
            return None

        try:
            ET.fromstring(cleaned)
        except ET.ParseError:
            return None

        return self._apply_xml_patch(original_xml, cleaned, context)

    def _extract_xml_from_response(self, response: str) -> Optional[str]:
        """Extract clean XML from LLM response."""
        response = response.strip()

        if response.startswith("<"):
            return response

        if "```xml" in response:
            response = response.split("```xml")[1].split("```")[0].strip()
        elif "```" in response:
            response = response.split("```")[1].split("```")[0].strip()

        if response.startswith("<"):
            return response

        return None

    def _apply_xml_patch(
        self,
        original_xml: str,
        patch_xml: str,
        context: RepairContext,
    ) -> Optional[str]:
        """Apply XML patch to original model."""
        try:
            root = ET.fromstring(original_xml)

            patch_root = ET.fromstring(patch_xml)

            if context.error_type == "self_referential_flow":
                return self._fix_self_referential_flow(
                    original_xml, patch_xml, context.error_element
                )
            elif context.error_type == "missing_birth_sources":
                return self._add_birth_source(original_xml, context.error_element)
            elif context.error_type == "orphaned_parameters":
                return self._link_parameter(
                    original_xml, patch_xml, context.error_element
                )
            elif context.error_type == "missing_death_sinks":
                return self._add_death_sink(original_xml, context.error_element)
            elif context.error_type == "zero_population_all":
                return self._fix_population_values(original_xml, patch_xml)
            else:
                return self._generic_patch(original_xml, patch_xml)

        except ET.ParseError as e:
            print(f"[RepairEngine] XML parse error: {e}")
            return None

    def _fix_self_referential_flow(
        self,
        xml: str,
        patch: str,
        element_name: str,
    ) -> Optional[str]:
        """Fix self-referential ContactFlow by parsing and targeting specific element."""
        target_match = re.search(r'target="([^"]+)"', patch)
        contact_match = re.search(r'contactCompartment="([^"]+)"', patch)

        if not target_match or not contact_match:
            return None

        new_target = target_match.group(1)
        new_contact = contact_match.group(1)

        if not new_target or not new_contact:
            return None

        try:
            root = ET.fromstring(xml)

            fixed_count = 0
            for comp in root.iter():
                tag = comp.tag.split("}")[-1] if "}" in comp.tag else comp.tag
                if tag != "compartments":
                    continue

                for flow in comp.findall(".//outgoingFlows"):
                    flow_type = flow.get(
                        "{http://www.w3.org/2001/XMLSchema-instance}type", ""
                    )

                    if "ContactFlow" not in flow_type:
                        continue

                    target = flow.get("target", "")
                    contact = flow.get("contactCompartment", "")

                    if target == contact:
                        flow.set("target", new_target)
                        flow.set("contactCompartment", new_contact)
                        fixed_count += 1
                        print(
                            f"[RepairEngine] Fixed ContactFlow: target={new_target}, contact={new_contact}"
                        )

            if fixed_count > 0:
                return ET.tostring(root, encoding="unicode")
            else:
                print("[RepairEngine] No self-referential ContactFlow found to fix")
                return None

        except ET.ParseError as e:
            print(f"[RepairEngine] XML parse error in _fix_self_referential_flow: {e}")
            return None

    def _add_birth_source(self, xml: str, compartment_name: str) -> Optional[str]:
        """Add birth source for compartment."""
        try:
            root = ET.fromstring(xml)

            compartments = root.findall(
                ".//{http://example.com/compartmentalmodel}compartments"
            )
            if not compartments:
                compartments = root.findall(".//compartments")

            target_comp = None
            for comp in compartments:
                if comp.get("PrimaryName", "").lower() == compartment_name.lower():
                    target_comp = comp
                    break

            if target_comp is None:
                return None

            comp_idx = 0
            for i, c in enumerate(compartments):
                if c == target_comp:
                    comp_idx = i
                    break

            external_source = ET.Element("externalSources")
            external_source.set("name", f"Birth_{compartment_name.replace(' ', '_')}")
            external_source.set("rateParameter", "//@parameters.0")
            external_source.set("targetCompartment", f"//@compartments.{comp_idx}")

            root.append(external_source)

            return ET.tostring(root, encoding="unicode")

        except Exception as e:
            print(f"[RepairEngine] Error adding birth source: {e}")
            return None

    def _link_parameter(self, xml: str, patch: str, param_name: str) -> Optional[str]:
        """Link orphaned parameter to a flow. Returns None if cannot implement."""
        return None

    def _add_death_sink(self, xml: str, compartment_name: str) -> Optional[str]:
        """Add death sink for compartment. Returns None if cannot implement."""
        return None

    def _fix_population_values(self, xml: str, patch: str) -> Optional[str]:
        """Fix zero population values."""
        pop_match = re.search(r'population="(\d+)"', patch)
        if not pop_match:
            return None

        new_pop = pop_match.group(1)

        root = ET.fromstring(xml)
        compartments = root.findall(
            ".//{http://example.com/compartmentalmodel}compartments"
        )
        if not compartments:
            compartments = root.findall(".//compartments")

        for comp in compartments:
            if comp.get("population", "0") == "0":
                comp.set("population", new_pop)

        return ET.tostring(root, encoding="unicode")

    def _generic_patch(self, xml: str, patch: str) -> Optional[str]:
        """Generic patch application."""
        if "<outgoingFlows" in patch:
            return xml + "\n" + patch
        elif "<externalSinks" in patch:
            return xml + "\n" + patch
        elif "<externalSources" in patch:
            return xml + "\n" + patch

        return None


def create_repair_engine(
    llm_client: RLMClient,
    config: Optional[Dict[str, Any]] = None,
) -> RepairEngine:
    """Factory function to create repair engine."""
    return RepairEngine(llm_client, config)
