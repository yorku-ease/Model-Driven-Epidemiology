#!/usr/bin/env python3
"""
Test script for semantic search functionality.

Tests:
1. Building vector store from paper_sections.json
2. Semantic search queries
3. Integration with RepairDispatcher

Usage:
    python test_semantic_search.py [--disease measles]
"""

import argparse
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent.parent))

from src.utils.vector_store import VectorStore
from src.repair_dispatcher import RepairDispatcher


def find_paper_sections(disease: str) -> Path:
    """Find paper_sections.json for a disease."""
    reports_dir = Path(__file__).parent.parent / "phase 2" / "reports"
    pattern = f"{disease.lower()}_llm_*"

    candidates = list(reports_dir.glob(pattern))
    if not candidates:
        raise FileNotFoundError(f"No report found for {disease}")

    candidates.sort(key=lambda x: x.stat().st_mtime, reverse=True)
    return candidates[0] / "paper_sections.json"


def test_vector_store(paper_sections_path: Path) -> VectorStore:
    """Test building and searching the vector store."""
    print(f"\n{'=' * 60}")
    print("TEST 1: Building Vector Store")
    print(f"{'=' * 60}")

    vector_store = VectorStore()
    num_chunks = vector_store.build_from_paper_sections(paper_sections_path)

    print(f"\n✓ Built index with {num_chunks} chunks")

    return vector_store


def test_semantic_search(vector_store: VectorStore) -> None:
    """Test various semantic search queries."""
    print(f"\n{'=' * 60}")
    print("TEST 2: Semantic Search Queries")
    print(f"{'=' * 60}")

    test_queries = [
        {
            "name": "Contact Flow / Transmission",
            "query": "contact flow transmission infection rate force of infection",
            "expected": "sections about transmission dynamics",
        },
        {
            "name": "Initial Population Values",
            "query": "initial population values compartment sizes starting values I0",
            "expected": "sections with initial conditions",
        },
        {
            "name": "Parameter Values",
            "query": "parameter values rate constants transmission rate recovery rate",
            "expected": "tables with parameter values",
        },
        {
            "name": "Death Rate / Mortality",
            "query": "death rate mortality disease induced death sink",
            "expected": "sections about mortality",
        },
        {
            "name": "Birth / Recruitment",
            "query": "birth recruitment rate population entry susceptible",
            "expected": "sections about population dynamics",
        },
    ]

    for i, test in enumerate(test_queries, 1):
        print(f"\n--- Query {i}: {test['name']} ---")
        print(f"Query: {test['query']}")
        print(f"Expected: {test['expected']}")

        results = vector_store.semantic_search(test["query"], top_k=3)

        print(f"\nTop {len(results)} results:")
        for j, chunk in enumerate(results, 1):
            title = chunk.title[:60] + "..." if len(chunk.title) > 60 else chunk.title
            content_preview = chunk.content[:100].replace("\n", " ") + "..."
            print(f"  {j}. [{chunk.source_type}] {title}")
            print(f"     Score: {chunk.score:.4f}")
            print(f"     Content: {content_preview}")


def test_dispatcher_integration(vector_store: VectorStore) -> None:
    """Test integration with RepairDispatcher."""
    print(f"\n{'=' * 60}")
    print("TEST 3: Dispatcher Integration")
    print(f"{'=' * 60}")

    dispatcher = RepairDispatcher()
    dispatcher.set_vector_store(vector_store)

    print(f"\n✓ Vector store set on dispatcher")
    print(f"✓ has_vector_store(): {dispatcher.has_vector_store()}")

    test_errors = [
        {
            "type": "self_referential_flow",
            "element": "ContactFlow_S_to_S",
            "detail": "ContactFlow has target and contactCompartment pointing to same compartment",
            "severity": "critical",
        },
        {
            "type": "zero_population_all",
            "element": "Compartment_Susceptible",
            "detail": "Compartment has population=0 which is invalid",
            "severity": "critical",
        },
    ]

    for i, error in enumerate(test_errors, 1):
        print(f"\n--- Dispatch Test {i}: {error['type']} ---")

        context = dispatcher.semantic_dispatch(error)

        print(f"  Error type: {context.error_type}")
        print(f"  Error element: {context.error_element}")
        print(f"  Relevant sections found: {len(context.relevant_sections)}")

        for j, section in enumerate(context.relevant_sections[:2], 1):
            title = (
                section.title[:50] + "..." if len(section.title) > 50 else section.title
            )
            print(f"    {j}. {title} (score: {section.score:.4f})")


def main():
    parser = argparse.ArgumentParser(description="Test semantic search functionality")
    parser.add_argument(
        "--disease",
        "-d",
        default="measles",
        help="Disease name (default: measles)",
    )
    parser.add_argument(
        "--save-index",
        action="store_true",
        help="Save the built index to disk",
    )
    args = parser.parse_args()

    try:
        paper_sections_path = find_paper_sections(args.disease)
        print(f"Found paper sections: {paper_sections_path}")
    except FileNotFoundError as e:
        print(f"Error: {e}")
        sys.exit(1)

    vector_store = test_vector_store(paper_sections_path)

    if args.save_index:
        save_path = paper_sections_path.parent / "sections_vector_store"
        vector_store.save(save_path)
        print(f"\n✓ Index saved to {save_path}")

    test_semantic_search(vector_store)

    test_dispatcher_integration(vector_store)

    print(f"\n{'=' * 60}")
    print("ALL TESTS PASSED!")
    print(f"{'=' * 60}")


if __name__ == "__main__":
    main()
