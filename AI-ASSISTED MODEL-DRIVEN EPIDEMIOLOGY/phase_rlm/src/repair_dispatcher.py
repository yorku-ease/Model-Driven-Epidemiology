"""
Repair Dispatcher

Maps error types to relevant paper sections for targeted repair.
This is error-driven RAG - only retrieve evidence for things that failed validation.
Supports both keyword-based and semantic search retrieval.
"""

from dataclasses import dataclass
from typing import Any, Dict, List, Optional

from .section_classifier import ClassifiedSections, SectionLabel
from .utils.vector_store import VectorStore


ERROR_TYPE_TO_QUERY = {
    "self_referential_flow": [
        "contact flow transmission infection rate",
        "force of infection transmission dynamics",
        "compartmental model flow between compartments",
    ],
    "missing_birth_sources": [
        "birth recruitment rate population entry",
        "susceptible population initial conditions",
        "force of infection recruitment dynamics",
    ],
    "zero_population_all": [
        "initial population values compartment sizes",
        "initial conditions starting values I0 IC",
        "population size initial compartment values",
    ],
    "orphaned_parameters": [
        "parameter values rate constants coefficients",
        "transmission rate recovery rate values",
        "biological parameters estimates",
    ],
    "missing_death_sinks": [
        "death rate mortality removal disease death",
        "disease induced death rate sink",
        "removal rate exit from compartment",
    ],
    "flow_chain_incomplete": [
        "flow transition compartment progression ODE",
        "differential equations disease dynamics",
        "compartment transitions infection dynamics",
    ],
    "parameter_layer_contamination": [
        "mechanistic parameters transmission rate biological",
        "contact rate transmission coefficient",
        "recovery rate progression rate biological",
    ],
    "composite_parameter_decomposition": [
        "composite parameter decomposition beta gamma",
        "transmission contact rate expression",
        "parameter breakdown decomposition values",
    ],
}


ERROR_TYPE_SECTION_MAPPING = {
    "self_referential_flow": {
        "include": ["mechanistic", "parameters"],
        "exclude": ["inference"],
        "search_terms": [
            "contact flow",
            "infection",
            "transmission",
            "compartmental",
            "contact rate",
            "force of infection",
            "transmission rate",
        ],
        "priority": "high",
    },
    "missing_birth_sources": {
        "include": ["mechanistic", "parameters"],
        "exclude": ["inference"],
        "search_terms": [
            "recruitment",
            "birth",
            "susceptible",
            "initial conditions",
            "force of infection",
            "population",
            "entry",
        ],
        "priority": "high",
    },
    "zero_population_all": {
        "include": ["mechanistic", "parameters"],
        "exclude": [],
        "search_terms": [
            "initial population",
            "initial conditions",
            "population",
            "initial compartment",
            "starting values",
            "IC",
            "I0",
        ],
        "priority": "critical",
    },
    "orphaned_parameters": {
        "include": ["parameters", "mechanistic"],
        "exclude": ["inference"],
        "search_terms": ["parameter", "rate", "coefficient", "constant", "value"],
        "priority": "medium",
    },
    "missing_death_sinks": {
        "include": ["mechanistic", "parameters"],
        "exclude": ["inference"],
        "search_terms": [
            "death",
            "mortality",
            "removal",
            "sink",
            "exit",
            "disease death",
            "natural death",
            "removal rate",
        ],
        "priority": "high",
    },
    "flow_chain_incomplete": {
        "include": ["mechanistic"],
        "exclude": ["inference"],
        "search_terms": [
            "flow",
            "transition",
            "compartment",
            "ODE",
            "differential",
            "progression",
            "recovery",
            "infection dynamics",
        ],
        "priority": "high",
    },
    "parameter_layer_contamination": {
        "include": ["mechanistic", "parameters"],
        "exclude": ["inference"],
        "search_terms": [
            "mechanistic",
            "transmission rate",
            "contact rate",
            "recovery rate",
            "progression rate",
            "biological",
        ],
        "priority": "medium",
    },
    "composite_parameter_decomposition": {
        "include": ["parameters", "mechanistic"],
        "exclude": ["inference"],
        "search_terms": [
            "parameter",
            "transmission",
            "contact",
            "composite",
            "beta",
            "gamma",
            "rate",
            "expression",
        ],
        "priority": "low",
    },
}


@dataclass
class RepairContext:
    """Context for a specific repair task."""

    error_type: str
    error_element: str
    error_detail: str
    severity: str
    relevant_sections: List[SectionLabel]
    current_model_xml: str


class RepairDispatcher:
    """Dispatches repairs to relevant paper sections based on error type."""

    def __init__(self, config: Optional[Dict[str, Any]] = None):
        self.config = config or {}
        self.error_mapping = self.config.get(
            "error_section_mapping", ERROR_TYPE_SECTION_MAPPING
        )
        self.vector_store: Optional[VectorStore] = None
        self.semantic_top_k = self.config.get("semantic_top_k", 8)

    def set_vector_store(self, vector_store: VectorStore) -> None:
        """Set the vector store for semantic search."""
        self.vector_store = vector_store

    def has_vector_store(self) -> bool:
        """Check if vector store is available."""
        return self.vector_store is not None and self.vector_store.is_built

    def get_section_config(self, error_type: str) -> Dict[str, Any]:
        """Get section configuration for an error type."""
        return self.error_mapping.get(
            error_type,
            {
                "include": ["mechanistic", "parameters"],
                "exclude": ["inference"],
                "search_terms": [],
                "priority": "medium",
            },
        )

    def dispatch(
        self,
        error: Dict[str, Any],
        classified_sections: ClassifiedSections,
        model_xml: str = "",
    ) -> RepairContext:
        """
        Create repair context for an error.

        Args:
            error: Error dict from structural_validator
            classified_sections: Classified sections from section_classifier
            model_xml: Current model XML (for context)

        Returns:
            RepairContext with relevant sections
        """
        error_type = error.get("type", "")
        error_element = error.get("element", "")
        error_detail = error.get("detail", "")
        severity = error.get("severity", "medium")

        section_config = self.get_section_config(error_type)

        include_labels = section_config.get("include", ["mechanistic"])
        exclude_labels = section_config.get("exclude", ["inference"])

        relevant_sections = self._filter_sections(
            classified_sections,
            include_labels,
            exclude_labels,
            section_config.get("search_terms", []),
        )

        return RepairContext(
            error_type=error_type,
            error_element=error_element,
            error_detail=error_detail,
            severity=severity,
            relevant_sections=relevant_sections,
            current_model_xml=model_xml,
        )

    def _filter_sections(
        self,
        classified: ClassifiedSections,
        include_labels: List[str],
        exclude_labels: List[str],
        search_terms: List[str],
    ) -> List[SectionLabel]:
        """Filter sections by labels and search terms."""
        all_sections = []

        for label in include_labels:
            all_sections.extend(classified.get_sections_by_label(label))

        if exclude_labels:
            all_sections = [s for s in all_sections if s.label not in exclude_labels]

        if search_terms:

            def section_matches(s: SectionLabel) -> bool:
                text = f"{s.title} {s.content}".lower()
                return any(term.lower() in text for term in search_terms)

            matched = [s for s in all_sections if section_matches(s)]

            if matched:
                all_sections = matched

        all_sections.sort(key=lambda x: x.score, reverse=True)

        return all_sections[:5]

    def format_context_for_llm(self, context: RepairContext) -> str:
        """Format repair context as prompt for LLM."""
        sections_text = ""

        if context.relevant_sections:
            sections_parts = []
            for i, section in enumerate(context.relevant_sections[:5], 1):
                sections_parts.append(
                    f"SECTION {i}: {section.title}\n{section.content}"
                )
            sections_text = "\n\n".join(sections_parts)
        else:
            sections_text = "No relevant sections found in paper."

        prompt = f"""ERROR TYPE: {context.error_type}
ELEMENT: {context.error_element}
SEVERITY: {context.severity}

PROBLEM DESCRIPTION:
{context.error_detail}

RELEVANT PAPER SECTIONS:
{sections_text}

TASK: Identify the correct value/element based on the paper sections above and 
return the corrected XML element. Only modify the specific element described.
If the paper does not contain enough information, indicate this clearly."""

        return prompt

    def semantic_dispatch(
        self,
        error: Dict[str, Any],
        model_xml: str = "",
    ) -> RepairContext:
        """Create repair context using semantic search instead of keyword matching.

        Requires vector_store to be set via set_vector_store().

        Args:
            error: Error dict from structural_validator
            model_xml: Current model XML (for context)

        Returns:
            RepairContext with semantically retrieved sections
        """
        if not self.has_vector_store():
            raise RuntimeError(
                "Vector store not set. Call set_vector_store() first, "
                "or use dispatch() for keyword-based retrieval."
            )

        error_type = error.get("type", "")
        error_element = error.get("element", "")
        error_detail = error.get("detail", "")
        severity = error.get("severity", "medium")

        query = self._build_semantic_query(error_type, error_element, error_detail)

        section_config = self.get_section_config(error_type)
        exclude_types = section_config.get("exclude", ["inference"])

        results = self.vector_store.semantic_search(
            query=query,
            top_k=self.semantic_top_k,
            exclude_types=exclude_types if exclude_types else None,
        )

        relevant_sections = [chunk.to_section_label() for chunk in results]

        return RepairContext(
            error_type=error_type,
            error_element=error_element,
            error_detail=error_detail,
            severity=severity,
            relevant_sections=relevant_sections,
            current_model_xml=model_xml,
        )

    def _build_semantic_query(
        self,
        error_type: str,
        error_element: str,
        error_detail: str,
    ) -> str:
        """Build a rich query for semantic search.

        Combines error type context with error details to create
        a more specific search query.
        """
        query_parts = []

        if error_type in ERROR_TYPE_TO_QUERY:
            query_parts.extend(ERROR_TYPE_TO_QUERY[error_type][:1])

        query_parts.append(error_detail)

        if error_element:
            query_parts.append(f"element: {error_element}")

        return " ".join(query_parts)

    def batch_dispatch(
        self,
        errors: List[Dict[str, Any]],
        classified_sections: ClassifiedSections,
        model_xml: str = "",
    ) -> List[RepairContext]:
        """Dispatch multiple errors to their relevant sections."""
        contexts = []

        for error in errors:
            context = self.dispatch(error, classified_sections, model_xml)
            contexts.append(context)

        return contexts


def create_dispatcher(config: Optional[Dict[str, Any]] = None) -> RepairDispatcher:
    """Factory function to create a RepairDispatcher."""
    return RepairDispatcher(config)


if __name__ == "__main__":
    from pathlib import Path
    import json

    classifier_path = Path(
        "phase 2/reports/measles_llm_gemini_20260312_165901/paper_sections.json"
    )

    if classifier_path.exists():
        with open(classifier_path) as f:
            sections = json.load(f)

        from section_classifier import SectionClassifier

        classifier = SectionClassifier()
        classified = classifier.classify_sections(sections)

        dispatcher = RepairDispatcher()

        test_error = {
            "type": "self_referential_flow",
            "element": "Susceptible -> ContactFlow",
            "detail": "ContactFlow has same target and contactCompartment",
            "severity": "critical",
        }

        context = dispatcher.dispatch(test_error, classified)
        print("=== REPAIR CONTEXT ===")
        print(f"Error: {context.error_type}")
        print(f"Element: {context.error_element}")
        print(f"Relevant sections: {len(context.relevant_sections)}")
        for s in context.relevant_sections:
            print(f"  - {s.title} ({s.label}, score: {s.score})")
    else:
        print(f"Test file not found: {classifier_path}")
