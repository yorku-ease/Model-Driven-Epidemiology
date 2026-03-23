"""
Repair Loop

Orchestrates the iterative repair process:
1. Validate model for structural errors
2. Attempt repairs via LLM with semantic search and error memory
3. Re-validate until confidence threshold or max iterations
"""

import tempfile
from dataclasses import dataclass
from pathlib import Path
from typing import Any, Dict, List, Optional

from .error_memory import ErrorMemory
from .repair_engine import IterativeRepairEngine, RepairResult
from .structural_validator import StructuralValidator
from .utils.vector_store import VectorStore


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
        engine: IterativeRepairEngine,
        config: Dict[str, Any],
    ):
        self.validator = validator
        self.engine = engine
        self.config = config

        self.max_iterations = config.get("max_iterations", 5)

    def _get_error_signature(self, error: Dict[str, Any]) -> str:
        """Create unique signature for an error."""
        return f"{error.get('type', '')}|{error.get('element', '')}"

    def run(
        self,
        compmodel_path: Path,
    ) -> Dict[str, Any]:
        """
        Run the repair loop on a compmodel file.

        Args:
            compmodel_path: Path to the .compmodel file

        Returns:
            RepairReport dict with results
        """
        print(f"\n{'=' * 60}")
        print(f"Starting Repair Loop for: {compmodel_path.name}")
        print(f"{'=' * 60}")

        current_xml = compmodel_path.read_text(encoding="utf-8")

        initial_validation = self.validator.validate(compmodel_path)
        initial_errors = initial_validation.get("errors", [])
        print(f"\n[1/5] Initial validation found {len(initial_errors)} errors")

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

            for error in sorted_errors:
                print(
                    f"\n  Repairing: [{error['severity']}] {error['type']} - {error['element']}"
                )

                result = self.engine.repair(error, current_xml)

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
                            "iterations_used": result.iterations_used,
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
                    print(f"    ✗ Repair failed: {result.failure_reason or 'unknown'}")
                    if result.explanation:
                        print(f"       {result.explanation[:150]}")

                    repair_log.append(
                        {
                            "iteration": iterations,
                            "error_type": error["type"],
                            "error_element": error["element"],
                            "repaired": False,
                            "failure_reason": result.failure_reason,
                            "explanation": result.explanation[:200]
                            if result.explanation
                            else "",
                            "iterations_used": result.iterations_used,
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


def run_repair(
    compmodel_path: Path,
    paper_sections_path: Path,
    config: Dict[str, Any],
    vector_store: VectorStore,
    error_memory: Optional[ErrorMemory] = None,
    evaluation_context: str = "",
) -> Dict[str, Any]:
    """
    Convenience function to run repair loop with default components.

    Args:
        compmodel_path: Path to .compmodel file
        paper_sections_path: Path to paper_sections.json (unused, kept for compatibility)
        config: Configuration dict
        vector_store: VectorStore for semantic search (required)
        error_memory: Optional ErrorMemory for remembering past repairs
        evaluation_context: Short text from Phase 2 evaluation JSON for LLM prompts

    Returns:
        Repair report dict
    """
    from .utils.llm_client import create_llm_client

    validator = StructuralValidator(config)
    llm_client = create_llm_client(config)
    engine = IterativeRepairEngine(
        llm_client,
        vector_store,
        config,
        error_memory,
        evaluation_context=evaluation_context,
    )

    loop = RepairLoop(validator, engine, config)

    return loop.run(compmodel_path)
