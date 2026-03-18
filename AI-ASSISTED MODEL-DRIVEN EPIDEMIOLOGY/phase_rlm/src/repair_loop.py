"""
Repair Loop

Orchestrates the iterative repair process:
1. Validate model for structural errors
2. Classify paper sections
3. Dispatch errors to relevant sections
4. Attempt repairs via LLM
5. Re-validate until confidence threshold or max iterations
"""

import json
import tempfile
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any, Dict, List, Optional

from .repair_dispatcher import RepairDispatcher
from .repair_engine import RepairEngine
from .section_classifier import SectionClassifier
from .structural_validator import StructuralValidator


@dataclass
class RepairLogEntry:
    """Single repair attempt in the log."""

    iteration: int
    error_type: str
    error_element: str
    repaired: bool
    explanation: str


@dataclass
class RepairReport:
    """Final report of the repair process."""

    iterations: int
    errors_found_initially: int
    errors_remaining: int
    errors_repaired: int
    repair_log: List[Dict[str, Any]]
    validator_pass_rate: float
    final_model_valid: bool

    def to_dict(self) -> Dict[str, Any]:
        return {
            "iterations": self.iterations,
            "errors_found_initially": self.errors_found_initially,
            "errors_remaining": self.errors_remaining,
            "errors_repaired": self.errors_repaired,
            "repair_log": self.repair_log,
            "validator_pass_rate": self.validator_pass_rate,
            "final_model_valid": self.final_model_valid,
        }


class RepairLoop:
    """Main repair loop orchestrator."""

    SEVERITY_ORDER = ["critical", "high", "medium", "low"]

    def __init__(
        self,
        validator: StructuralValidator,
        classifier: SectionClassifier,
        dispatcher: RepairDispatcher,
        engine: RepairEngine,
        config: Dict[str, Any],
    ):
        self.validator = validator
        self.classifier = classifier
        self.dispatcher = dispatcher
        self.engine = engine
        self.config = config

        self.max_iterations = config.get("max_iterations", 5)
        self.confidence_threshold = config.get("confidence_threshold", 0.8)

    def run(
        self,
        compmodel_path: Path,
        paper_sections_path: Path,
    ) -> Dict[str, Any]:
        """
        Run the repair loop on a compmodel file.

        Args:
            compmodel_path: Path to the .compmodel file
            paper_sections_path: Path to paper_sections.json

        Returns:
            RepairReport dict with results
        """
        print(f"\n{'=' * 60}")
        print(f"Starting Repair Loop for: {compmodel_path.name}")
        print(f"{'=' * 60}")

        current_xml = compmodel_path.read_text(encoding="utf-8")

        print("\n[1/5] Loading and classifying paper sections...")
        classified = self.classifier.load_and_classify(paper_sections_path)
        print(f"  - Mechanistic: {len(classified.mechanistic)} sections")
        print(f"  - Inference: {len(classified.inference)} sections (excluded)")
        print(f"  - Parameters: {len(classified.parameters)} sections")

        initial_validation = self.validator.validate(compmodel_path)
        initial_errors = initial_validation.get("errors", [])
        print(f"\n[2/5] Initial validation found {len(initial_errors)} errors")

        for err in initial_errors[:5]:
            print(f"  - [{err['severity']}] {err['type']}: {err['element']}")

        repair_log = []
        current_errors = initial_errors
        iterations = 0

        while iterations < self.max_iterations and current_errors:
            iterations += 1
            print(f"\n{'=' * 60}")
            print(f"ITERATION {iterations}/{self.max_iterations}")
            print(f"{'=' * 60}")

            print(f"\n[Iteration {iterations}] Current errors: {len(current_errors)}")

            sorted_errors = self._sort_errors_by_priority(current_errors)

            sorted_errors = self._sort_errors_by_priority(current_errors)
            errors_remaining = list(sorted_errors)

            for error in errors_remaining:
                print(
                    f"\n  Repairing: [{error['severity']}] {error['type']} - {error['element']}"
                )

                context = self.dispatcher.dispatch(
                    error,
                    classified,
                    current_xml,
                )

                result = self.engine.repair(context, current_xml)

                if result.success and result.repaired_model_xml != current_xml:
                    print(f"    ✓ Repair successful")
                    current_xml = result.repaired_model_xml

                    repair_log.append(
                        {
                            "iteration": iterations,
                            "error_type": error["type"],
                            "error_element": error["element"],
                            "repaired": True,
                            "explanation": result.explanation[:200]
                            if result.explanation
                            else "",
                        }
                    )

                    with tempfile.NamedTemporaryFile(
                        mode="w",
                        suffix=".compmodel",
                        dir=compmodel_path.parent,
                        delete=True,
                        encoding="utf-8",
                    ) as tmp:
                        tmp.write(current_xml)
                        tmp.flush()
                        new_validation = self.validator.validate(Path(tmp.name))

                    current_errors = new_validation.get("errors", [])
                    print(f"    Re-validation: {len(current_errors)} errors remaining")

                    if len(current_errors) == 0:
                        print("    ✓ All errors resolved!")
                        break
                else:
                    msg = (
                        result.explanation[:100]
                        if result.explanation
                        else "No change made"
                    )
                    print(f"    ✗ Repair failed: {msg}")

                    repair_log.append(
                        {
                            "iteration": iterations,
                            "error_type": error["type"],
                            "error_element": error["element"],
                            "repaired": False,
                            "explanation": result.explanation[:200]
                            if result.explanation
                            else "",
                        }
                    )

            if len(current_errors) == 0:
                break

        with tempfile.NamedTemporaryFile(
            mode="w",
            suffix=".compmodel",
            dir=compmodel_path.parent,
            delete=True,
            encoding="utf-8",
        ) as tmp:
            tmp.write(current_xml)
            tmp.flush()
            final_validation = self.validator.validate(Path(tmp.name))

        final_errors = final_validation.get("errors", [])

        errors_repaired = len(initial_errors) - len(final_errors)
        pass_rate = 1.0 - (len(final_errors) / max(len(initial_errors), 1))

        report = RepairReport(
            iterations=iterations,
            errors_found_initially=len(initial_errors),
            errors_remaining=len(final_errors),
            errors_repaired=errors_repaired,
            repair_log=repair_log,
            validator_pass_rate=pass_rate,
            final_model_valid=len(final_errors) == 0,
        )

        print(f"\n{'=' * 60}")
        print(f"REPAIR COMPLETE")
        print(f"{'=' * 60}")
        print(f"  Iterations: {report.iterations}")
        print(f"  Initial errors: {report.errors_found_initially}")
        print(f"  Errors repaired: {report.errors_repaired}")
        print(f"  Errors remaining: {report.errors_remaining}")
        print(f"  Pass rate: {report.validator_pass_rate:.1%}")
        print(f"  Model valid: {report.final_model_valid}")

        return {
            "report": report.to_dict(),
            "repaired_model_xml": current_xml,
            "final_validation": final_validation,
        }

    def _sort_errors_by_priority(
        self, errors: List[Dict[str, Any]]
    ) -> List[Dict[str, Any]]:
        """Sort errors by severity priority."""

        def error_priority(e: Dict[str, Any]) -> int:
            severity = e.get("severity", "low")
            return (
                self.SEVERITY_ORDER.index(severity)
                if severity in self.SEVERITY_ORDER
                else 4
            )

        return sorted(errors, key=error_priority)


def create_repair_loop(
    validator: StructuralValidator,
    classifier: SectionClassifier,
    dispatcher: RepairDispatcher,
    engine: RepairEngine,
    config: Dict[str, Any],
) -> RepairLoop:
    """Factory function to create repair loop."""
    return RepairLoop(validator, classifier, dispatcher, engine, config)


def run_repair(
    compmodel_path: Path,
    paper_sections_path: Path,
    config: Dict[str, Any],
) -> Dict[str, Any]:
    """
    Convenience function to run repair loop with default components.

    Args:
        compmodel_path: Path to .compmodel file
        paper_sections_path: Path to paper_sections.json
        config: Configuration dict

    Returns:
        Repair report dict
    """
    from .utils.llm_client import create_llm_client

    validator = StructuralValidator(config)
    classifier = SectionClassifier(config.get("section_labels", {}))
    dispatcher = RepairDispatcher(config)
    llm_client = create_llm_client(config)
    engine = RepairEngine(llm_client, config)

    loop = create_repair_loop(validator, classifier, dispatcher, engine, config)

    return loop.run(compmodel_path, paper_sections_path)
