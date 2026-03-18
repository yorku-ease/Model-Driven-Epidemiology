"""
Iterative Repair Engine

LLM-driven repair with:
1. LLM designs semantic search queries
2. Iterative context gathering (max 3 iterations)
3. Conversation history maintained
4. Error memory for intelligent reuse
5. Clear decision framework: CAN_FIX / NEED_MORE_CONTEXT / CANNOT_FIX
"""

import re
import xml.etree.ElementTree as ET
from dataclasses import dataclass
from pathlib import Path
from typing import Any, Dict, List, Optional

from .error_memory import ErrorMemory
from .utils.llm_client import RLMClient
from .utils.vector_store import VectorStore
from .llm_prompts import (
    QUERY_DESIGN_USER,
    REPAIR_SYSTEM,
    REPAIR_USER,
    REPAIR_WITH_HISTORY_USER,
    LLMRepairResponse,
    format_chunks_for_prompt,
    format_conversation_history,
)


@dataclass
class RepairResult:
    """Result of a repair attempt."""

    success: bool
    original_error: str
    repaired_model_xml: str
    explanation: str
    error_type: str
    failure_reason: Optional[str] = None
    iterations_used: int = 0


class IterativeRepairEngine:
    """LLM-driven iterative repair engine with semantic search."""

    CAN_FIX = "CAN_FIX"
    NEED_MORE_CONTEXT = "NEED_MORE_CONTEXT"
    CANNOT_FIX = "CANNOT_FIX"

    def __init__(
        self,
        llm_client: RLMClient,
        vector_store: VectorStore,
        config: Optional[Dict[str, Any]] = None,
        error_memory: Optional[ErrorMemory] = None,
    ):
        self.llm = llm_client
        self.vector_store = vector_store
        self.config = config or {}
        self.error_memory = error_memory

        self.max_iterations = self.config.get("max_context_iterations", 3)
        self.initial_search_k = self.config.get("initial_search_k", 10)
        self.additional_search_k = self.config.get("additional_search_k", 5)
        self.min_chunk_score = self.config.get("min_chunk_score", 0.25)

        memory_config = self.config.get("error_memory", {})
        self.semantic_threshold = memory_config.get("semantic_threshold", 0.82)
        self.max_memory_results = memory_config.get("max_results", 3)
        self.min_entries_for_semantic = memory_config.get(
            "min_entries_for_semantic", 10
        )

    def _log_success(
        self,
        error_type: str,
        element: str,
        search_query: Optional[str],
        fix_summary: str,
        evidence_chunk: Optional[str],
    ) -> None:
        """Log successful repair to error memory."""
        if self.error_memory:
            self.error_memory.add_success(
                error_type=error_type,
                element=element,
                search_query_used=search_query,
                fix_summary=fix_summary,
                paper_evidence_chunk=evidence_chunk,
            )

    def _log_failure(
        self,
        error_type: str,
        element: str,
        failure_reason: str,
        explanation: str,
    ) -> None:
        """Log failed repair to error memory."""
        if self.error_memory:
            self.error_memory.add_failure(
                error_type=error_type,
                element=element,
                failure_reason=failure_reason,
                llm_explanation=explanation,
            )

    def repair(
        self,
        error: Dict[str, Any],
        current_model_xml: str,
    ) -> RepairResult:
        """
        Attempt to repair an error using iterative LLM with semantic search and memory.

        Args:
            error: Error dict with type, element, detail, severity
            current_model_xml: Current model XML

        Returns:
            RepairResult with success status and repaired model
        """
        error_type = error.get("type", "")
        error_element = error.get("element", "")
        error_detail = error.get("detail", "")

        used_search_query: Optional[str] = None
        best_chunk_used = None

        try:
            memory_result = None
            memory_context = "No previous repair attempts for this error type."

            if self.error_memory and self.error_memory.has_memory:
                memory_result = self.error_memory.search(
                    error_type=error_type,
                    element=error_element,
                    semantic_threshold=self.semantic_threshold,
                    max_results=self.max_memory_results,
                    min_entries_for_semantic=self.min_entries_for_semantic,
                )

                if memory_result.entries:
                    memory_context = self.error_memory.format_for_prompt(memory_result)
                    print(
                        f"    [Memory] Found {len(memory_result.entries)} past attempt(s) ({memory_result.match_type})"
                    )

            search_query = self._design_search_query(
                error_type, error_element, error_detail, memory_context
            )
            used_search_query = search_query
            print(f"    [Query] {search_query[:80]}...")

            initial_chunks = self.vector_store.semantic_search(
                search_query,
                top_k=self.initial_search_k,
                min_score=self.min_chunk_score,
            )

            if not initial_chunks:
                return RepairResult(
                    success=False,
                    original_error=error_element,
                    repaired_model_xml=current_model_xml,
                    explanation="No relevant chunks found for initial search",
                    error_type=error_type,
                    failure_reason="no_chunks_found",
                    iterations_used=1,
                )

            conversation_history: List[LLMRepairResponse] = []
            all_chunks = list(initial_chunks)

            for iteration in range(1, self.max_iterations + 1):
                remaining = self.max_iterations - iteration

                if iteration == 1:
                    llm_response = self._llm_repair(
                        error_type=error_type,
                        error_element=error_element,
                        error_detail=error_detail,
                        model_xml=current_model_xml,
                        chunks=all_chunks,
                        conversation=[],
                        iteration=iteration,
                        max_iterations=self.max_iterations,
                        remaining=remaining,
                        memory_context=memory_context,
                    )
                else:
                    llm_response = self._llm_repair_with_history(
                        error_type=error_type,
                        error_element=error_element,
                        error_detail=error_detail,
                        model_xml=current_model_xml,
                        new_chunks=initial_chunks,
                        conversation=conversation_history,
                        iteration=iteration,
                        max_iterations=self.max_iterations,
                        remaining=remaining,
                        memory_context=memory_context,
                    )

                if llm_response.decision == self.CAN_FIX:
                    patched_xml = self._apply_fix(
                        current_model_xml,
                        llm_response.content,
                        error_element,
                    )
                    if patched_xml:
                        best_chunk = all_chunks[0] if all_chunks else None
                        self._log_success(
                            error_type=error_type,
                            element=error_element,
                            search_query=used_search_query,
                            fix_summary=llm_response.content[:200],
                            evidence_chunk=best_chunk.content[:500]
                            if best_chunk
                            else None,
                        )
                        return RepairResult(
                            success=True,
                            original_error=error_element,
                            repaired_model_xml=patched_xml,
                            explanation=f"Fixed using {iteration} iteration(s)",
                            error_type=error_type,
                            iterations_used=iteration,
                        )
                    else:
                        self._log_failure(
                            error_type=error_type,
                            element=error_element,
                            failure_reason="xml_parse_failed",
                            explanation=llm_response.content[:500],
                        )
                        return RepairResult(
                            success=False,
                            original_error=error_element,
                            repaired_model_xml=current_model_xml,
                            explanation=f"LLM suggested fix but XML parsing failed: {llm_response.content[:200]}",
                            error_type=error_type,
                            failure_reason="xml_parse_failed",
                            iterations_used=iteration,
                        )

                elif llm_response.decision == self.CANNOT_FIX:
                    self._log_failure(
                        error_type=error_type,
                        element=error_element,
                        failure_reason="no_evidence",
                        explanation=llm_response.content,
                    )
                    return RepairResult(
                        success=False,
                        original_error=error_element,
                        repaired_model_xml=current_model_xml,
                        explanation=llm_response.content,
                        error_type=error_type,
                        failure_reason="no_evidence",
                        iterations_used=iteration,
                    )

                elif llm_response.decision == self.NEED_MORE_CONTEXT:
                    context_request = llm_response.content
                    more_chunks = self.vector_store.semantic_search(
                        context_request,
                        top_k=self.additional_search_k,
                        min_score=self.min_chunk_score,
                    )

                    if more_chunks:
                        existing_ids = {c.chunk_id for c in all_chunks}
                        for chunk in more_chunks:
                            if chunk.chunk_id not in existing_ids:
                                all_chunks.append(chunk)
                                existing_ids.add(chunk.chunk_id)

                    conversation_history.append(llm_response)

                    if remaining == 0:
                        self._log_failure(
                            error_type=error_type,
                            element=error_element,
                            failure_reason="max_iterations",
                            explanation=f"Max iterations reached. Last request: {context_request[:200]}",
                        )
                        return RepairResult(
                            success=False,
                            original_error=error_element,
                            repaired_model_xml=current_model_xml,
                            explanation=f"Max iterations reached. Last request: {context_request[:200]}",
                            error_type=error_type,
                            failure_reason="max_iterations",
                            iterations_used=self.max_iterations,
                        )

            self._log_failure(
                error_type=error_type,
                element=error_element,
                failure_reason="max_iterations",
                explanation="Repair loop completed without resolution",
            )
            return RepairResult(
                success=False,
                original_error=error_element,
                repaired_model_xml=current_model_xml,
                explanation="Repair loop completed without resolution",
                error_type=error_type,
                failure_reason="max_iterations",
                iterations_used=self.max_iterations,
            )

        except Exception as e:
            self._log_failure(
                error_type=error_type,
                element=error_element,
                failure_reason="engine_error",
                explanation=str(e),
            )
            return RepairResult(
                success=False,
                original_error=error_element,
                repaired_model_xml=current_model_xml,
                explanation=f"Repair engine error: {str(e)}",
                error_type=error_type,
                failure_reason="engine_error",
            )

    def _design_search_query(
        self,
        error_type: str,
        error_element: str,
        error_detail: str,
        memory_context: str = "No previous repair attempts for this error type.",
    ) -> str:
        """Use LLM to design a semantic search query."""
        prompt = QUERY_DESIGN_USER.format(
            error_type=error_type,
            element=error_element,
            detail=error_detail,
            memory_context=memory_context,
        )

        response = self.llm.generate_flash(
            prompt=prompt,
            system_instruction="Design a search query for relevant paper sections.",
            temperature=0.3,
            max_tokens=200,
        )

        return response.strip()

    def _llm_repair(
        self,
        error_type: str,
        error_element: str,
        error_detail: str,
        model_xml: str,
        chunks: List,
        conversation: List,
        iteration: int,
        max_iterations: int,
        remaining: int,
        memory_context: str = "No previous repair attempts for this error type.",
    ) -> LLMRepairResponse:
        """First LLM call for repair."""
        chunks_text = format_chunks_for_prompt(chunks)

        prompt = REPAIR_USER.format(
            error_type=error_type,
            element=error_element,
            detail=error_detail,
            chunks=chunks_text,
            model_xml=model_xml,
            iteration=iteration,
            max_iterations=max_iterations,
            remaining=remaining,
            memory_context=memory_context,
        )

        response = self.llm.generate_flash(
            prompt=prompt,
            system_instruction=REPAIR_SYSTEM,
            temperature=0.3,
            max_tokens=4000,
        )

        return self._parse_llm_response(response)

    def _llm_repair_with_history(
        self,
        error_type: str,
        error_element: str,
        error_detail: str,
        model_xml: str,
        new_chunks: List,
        conversation: List,
        iteration: int,
        max_iterations: int,
        remaining: int,
        memory_context: str = "No previous repair attempts for this error type.",
    ) -> LLMRepairResponse:
        """Subsequent LLM calls with conversation history."""
        chunks_text = format_chunks_for_prompt(new_chunks)
        history_text = format_conversation_history(conversation)

        prompt = REPAIR_WITH_HISTORY_USER.format(
            error_type=error_type,
            element=error_element,
            detail=error_detail,
            new_chunks=chunks_text,
            history=history_text,
            model_xml=model_xml,
            iteration=iteration,
            max_iterations=max_iterations,
            remaining=remaining,
            memory_context=memory_context,
        )

        response = self.llm.generate_flash(
            prompt=prompt,
            system_instruction=REPAIR_SYSTEM,
            temperature=0.3,
            max_tokens=2000,
        )

        return self._parse_llm_response(response)

    def _parse_llm_response(self, response: str) -> LLMRepairResponse:
        """Parse LLM response into structured format."""
        can_fix_match = re.search(r"<CAN_FIX>(.*?)</CAN_FIX>", response, re.DOTALL)
        if can_fix_match:
            return LLMRepairResponse(
                decision=self.CAN_FIX,
                content=can_fix_match.group(1).strip(),
                full_response=response,
            )

        need_context_match = re.search(
            r"<NEED_MORE_CONTEXT>(.*?)</NEED_MORE_CONTEXT>", response, re.DOTALL
        )
        if need_context_match:
            return LLMRepairResponse(
                decision=self.NEED_MORE_CONTEXT,
                content=need_context_match.group(1).strip(),
                full_response=response,
            )

        cannot_fix_match = re.search(
            r"<CANNOT_FIX>(.*?)</CANNOT_FIX>", response, re.DOTALL
        )
        if cannot_fix_match:
            return LLMRepairResponse(
                decision=self.CANNOT_FIX,
                content=cannot_fix_match.group(1).strip(),
                full_response=response,
            )

        return LLMRepairResponse(
            decision=self.CANNOT_FIX,
            content=f"Could not parse LLM response. Response: {response[:500]}",
            full_response=response,
        )

    def _apply_fix(
        self,
        original_xml: str,
        fix_xml: str,
        error_element: str,
    ) -> Optional[str]:
        """Apply the fixed XML from LLM (full model or partial)."""
        try:
            cleaned = self._extract_xml(fix_xml)
            if not cleaned:
                return None

            ET.fromstring(cleaned)
            return cleaned

        except ET.ParseError as e:
            print(f"[RepairEngine] XML parse error: {e}")
            return None
        except Exception as e:
            print(f"[RepairEngine] Apply fix error: {e}")
            return None

    def _extract_xml(self, text: str) -> Optional[str]:
        """Extract XML from text."""
        text = text.strip()

        if text.startswith("<"):
            if text.endswith(">"):
                return text

        code_block_match = re.search(r"```xml\s*(.*?)\s*```", text, re.DOTALL)
        if code_block_match:
            return code_block_match.group(1).strip()

        code_match = re.search(r"```\s*(.*?)\s*```", text, re.DOTALL)
        if code_match:
            content = code_match.group(1).strip()
            if content.startswith("<"):
                return content

        return None

    def _patch_model(
        self,
        original_xml: str,
        patch_xml: str,
        error_element: str,
    ) -> Optional[str]:
        """Patch model with new elements."""
        try:
            root = ET.fromstring(original_xml)
            patch_root = ET.fromstring(patch_xml)

            for elem in patch_root:
                elem_tag = elem.tag.split("}")[-1] if "}" in elem.tag else elem.tag

                if elem_tag == "outgoingFlows":
                    self._add_outgoing_flows(root, elem)
                elif elem_tag == "externalSources":
                    self._add_external_sources(root, elem)
                elif elem_tag == "externalSinks":
                    self._add_external_sinks(root, elem)
                else:
                    root.append(elem)

            return ET.tostring(root, encoding="unicode")

        except Exception as e:
            print(f"[RepairEngine] Patch model error: {e}")
            return None

    def _add_outgoing_flows(self, root: ET.Element, flows_elem: ET.Element):
        """Add outgoing flows to compartments."""
        compartments = root.findall(".//compartments")
        if not compartments:
            compartments = root.findall(
                ".//{http://example.com/compartmentalmodel}compartments"
            )

        if compartments:
            comp = compartments[0]
            for flow in flows_elem:
                comp.append(flow)

    def _add_external_sources(self, root: ET.Element, sources_elem: ET.Element):
        """Add external sources to model."""
        model = root.find(".//model") or root
        for source in sources_elem:
            model.append(source)

    def _add_external_sinks(self, root: ET.Element, sinks_elem: ET.Element):
        """Add external sinks to model."""
        model = root.find(".//model") or root
        for sink in sinks_elem:
            model.append(sink)

    def _generic_replace(
        self,
        original_xml: str,
        replacement_xml: str,
        error_element: str,
    ) -> Optional[str]:
        """Generic replacement - append if no specific handler."""
        try:
            root = ET.fromstring(original_xml)

            tag_pattern = re.search(r"<(\w+)", replacement_xml)
            if not tag_pattern:
                return None

            tag_name = tag_pattern.group(1)
            tag_match = re.search(rf"<(\w+:)?{tag_name}[^>]*>", replacement_xml)

            if tag_match and "population=" in replacement_xml:
                compartments = root.findall(".//compartments")
                if not compartments:
                    compartments = root.findall(
                        ".//{http://example.com/compartmentalmodel}compartments"
                    )

                pop_match = re.search(r'population="(\d+)"', replacement_xml)
                if pop_match and compartments:
                    for comp in compartments:
                        current_pop = comp.get("population", "0")
                        if current_pop == "0":
                            comp.set("population", pop_match.group(1))
                    return ET.tostring(root, encoding="unicode")

            return original_xml + "\n" + replacement_xml

        except Exception as e:
            print(f"[RepairEngine] Generic replace error: {e}")
            return None


def create_repair_engine(
    llm_client: RLMClient,
    vector_store: VectorStore,
    config: Optional[Dict[str, Any]] = None,
) -> IterativeRepairEngine:
    """Factory function to create iterative repair engine."""
    return IterativeRepairEngine(llm_client, vector_store, config)
