"""
LLM Prompt Templates for Iterative Repair

Design philosophy:
1. LLM designs semantic search queries (not hardcoded)
2. LLM repairs with conversation history
3. Clear decision framework: CAN_FIX / NEED_MORE_CONTEXT / CANNOT_FIX
4. Early termination if evidence shows element is inherently wrong
"""

from dataclasses import dataclass
from typing import List, Optional

QUERY_DESIGN_SYSTEM = """You are a biomedical researcher specializing in epidemiological modeling.
Given an error type and description, design an effective semantic search query to find 
relevant paper sections that can help fix the error.

Guidelines:
- Focus on key terms from the error description
- Include relevant epidemiological concepts
- Keep queries 1-3 sentences maximum
- Be specific but not overly narrow
"""

QUERY_DESIGN_USER = """Design a search query to find relevant paper sections for this error:

Error Type: {error_type}
Element: {element}
Detail: {detail}

MEMORY (past attempts for similar errors):
{memory_context}

Return ONLY the search query text. No explanations or formatting."""


QUERY_DESIGN_WITH_MEMORY_SYSTEM = """You are a biomedical researcher specializing in epidemiological modeling.
Given an error type and description and past repair memory, design an effective semantic search query.

If memory shows similar errors failed, still design a query but note what to avoid.
If memory shows similar errors succeeded, consider using a similar approach.
"""

REPAIR_SYSTEM = """You are repairing epidemiological model XML files using evidence from research papers.

CRITICAL DECISION FRAMEWORK - You MUST choose ONE of these:

1. CAN_FIX
   - You have sufficient evidence from the paper chunks AND the model XML
   - You can confidently determine the correct fix
   - Return: <CAN_FIX>FULL_MODEL_XML_HERE</CAN_FIX>
   - IMPORTANT: Return the ENTIRE corrected model XML (not just the fixed portion)

2. NEED_MORE_CONTEXT
   - You need specific information not in current chunks
   - You know EXACTLY what to search for
   - Return: <NEED_MORE_CONTEXT>What specific information you need</NEED_MORE_CONTEXT>

3. CANNOT_FIX
   - Evidence shows element is inherently wrong or data is missing from paper
   - Even with remaining iterations, fix is impossible
   - Return: <CANNOT_FIX>Why this cannot be fixed</CANNOT_FIX>

IMPORTANT RULES:
- You may choose CANNOT_FIX even if iterations remain
- If you choose NEED_MORE_CONTEXT, be specific about what to search for
- Never guess or hallucinate values not in the paper chunks
- Return COMPLETE model XML when using CAN_FIX - do not return partial fragments
- Past memory can inform your decision - use it wisely"""


REPAIR_USER = """PHASE 2 BASELINE EVALUATION (extraction vs gold; use as soft guidance, not ground truth for XML structure):
{evaluation_context}

REPAIR MEMORY (past attempts for this error type):
{memory_context}

ERROR TO FIX:
- Type: {error_type}
- Element: {element}
- Detail: {detail}

PAPER CHUNKS (relevant evidence):
{chunks}

CURRENT MODEL XML:
{model_xml}

ITERATION: {iteration}/{max_iterations}
Remaining context requests: {remaining}

Based on memory and paper chunks, make your decision:
1. CAN_FIX → Provide the COMPLETE corrected model XML (include ALL elements, not just the fix)
2. NEED_MORE_CONTEXT → Request specific search terms
3. CANNOT_FIX → Explain why this cannot be fixed"""


REPAIR_WITH_HISTORY_USER = """PHASE 2 BASELINE EVALUATION (extraction vs gold; use as soft guidance, not ground truth for XML structure):
{evaluation_context}

REPAIR MEMORY (past attempts for this error type):
{memory_context}

ERROR TO FIX:
- Type: {error_type}
- Element: {element}
- Detail: {detail}

PAPER CHUNKS (newly retrieved):
{new_chunks}

CONVERSATION HISTORY:
{history}

CURRENT MODEL XML:
{model_xml}

ITERATION: {iteration}/{max_iterations}
Remaining context requests: {remaining}

Review memory, new chunks, AND conversation history, then decide:
1. CAN_FIX → Provide the COMPLETE corrected model XML (include ALL elements, not just the fix)
2. NEED_MORE_CONTEXT → Request specific search terms
3. CANNOT_FIX → Explain why this cannot be fixed"""


@dataclass
class LLMRepairResponse:
    """Structured response from LLM repair."""

    decision: str  # "CAN_FIX" | "NEED_MORE_CONTEXT" | "CANNOT_FIX"
    content: str  # The XML, context request, or reason
    full_response: str  # Raw LLM response for debugging


def format_chunks_for_prompt(chunks: List, include_metadata: bool = True) -> str:
    """Format chunks for LLM prompt."""
    if not chunks:
        return "No relevant chunks found."

    parts = []
    for i, chunk in enumerate(chunks, 1):
        source = (
            f"[{chunk.source_type.upper()}] {chunk.title}"
            if include_metadata and chunk.title
            else f"[{chunk.source_type.upper()}]"
        )
        parts.append(
            f"--- Chunk {i} (score: {chunk.score:.3f}) ---\n{source}\n{chunk.content}"
        )

    return "\n\n".join(parts)


def format_conversation_history(history: List[LLMRepairResponse]) -> str:
    """Format conversation history for prompt."""
    if not history:
        return "No previous attempts."

    parts = []
    for i, msg in enumerate(history, 1):
        if msg.decision == "CAN_FIX":
            parts.append(f"Attempt {i}: CAN_FIX\n{msg.content}")
        elif msg.decision == "NEED_MORE_CONTEXT":
            parts.append(f"Attempt {i}: NEED_MORE_CONTEXT\nRequested: {msg.content}")
        else:
            parts.append(f"Attempt {i}: CANNOT_FIX\nReason: {msg.content}")

    return "\n\n".join(parts)
