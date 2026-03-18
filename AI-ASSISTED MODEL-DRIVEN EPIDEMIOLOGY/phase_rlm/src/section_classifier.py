"""
Section Classifier for GROBID-parsed paper sections

Classifies paper sections by content type to filter out inference/Bayesian
sections that cause parameter contamination.
"""

import json
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any, Dict, List, Optional


@dataclass
class SectionLabel:
    """A labeled section from the paper."""

    title: str
    content: str
    label: str
    score: float = 0.0
    section_type: str = ""


@dataclass
class ClassifiedSections:
    """All classified sections from a paper."""

    mechanistic: List[SectionLabel] = field(default_factory=list)
    inference: List[SectionLabel] = field(default_factory=list)
    parameters: List[SectionLabel] = field(default_factory=list)
    other: List[SectionLabel] = field(default_factory=list)

    def get_all_mechanistic_text(self) -> str:
        """Get concatenated text from all mechanistic sections."""
        return "\n\n".join(f"=== {s.title} ===\n{s.content}" for s in self.mechanistic)

    def get_sections_by_label(self, label: str) -> List[SectionLabel]:
        """Get sections by label."""
        if label == "mechanistic":
            return self.mechanistic
        elif label == "inference":
            return self.inference
        elif label == "parameters":
            return self.parameters
        else:
            return self.other


class SectionClassifier:
    """Classifies GROBID sections by content type."""

    MECHANISTIC_KEYWORDS = [
        "compartment",
        "state variable",
        "ODE",
        "differential equation",
        "susceptible",
        "exposed",
        "infectious",
        "recovered",
        "removed",
        "transmission rate",
        "contact rate",
        "flow",
        "transition",
        "model description",
        "model formulation",
        "compartmental model",
        "system of equations",
        "dynamic model",
        "epidemic model",
        "SIR",
        "SEIR",
        "SEIRS",
        "SI",
        "SIRS",
        "compartments",
        "infection dynamics",
        "disease progression",
    ]

    INFERENCE_KEYWORDS = [
        "bayesian",
        "prior",
        "posterior",
        "likelihood",
        "MCMC",
        "markov chain",
        "negative binomial",
        "observation model",
        "calibration",
        "fitting",
        "EGR",
        "exponential growth rate",
        "dispersion",
        "credible interval",
        "inference",
        "estimation",
        "posterior",
        "likelihood",
        "chain",
        "sampler",
        "Gibbs",
        "Metropolis",
        "Hastings",
        "ABC",
        "approximate bayesian",
        "parameter estimation",
        "inverse problem",
        "optimization",
        "least squares",
        "maximum likelihood",
        "AIC",
        "BIC",
        "goodness of fit",
        "residual",
        "deviance",
    ]

    PARAMETER_KEYWORDS = [
        "table",
        "parameter values",
        "initial conditions",
        "baseline",
        "estimated",
        "fixed at",
        "value",
        "parameter table",
        "parameter list",
        "estimated values",
        "literature values",
        "default value",
        "rate constant",
        "coefficient",
    ]

    def __init__(self, config: Optional[Dict[str, Any]] = None):
        self.config = config or {}

        self.mechanistic_keywords = self.config.get(
            "mechanistic", self.MECHANISTIC_KEYWORDS
        )
        self.inference_keywords = self.config.get("inference", self.INFERENCE_KEYWORDS)
        self.parameter_keywords = self.config.get("parameters", self.PARAMETER_KEYWORDS)

    def _score_section(self, title: str, content: str) -> Dict[str, float]:
        """Score a section against each label category."""
        text = f"{title} {content}".lower()

        mechanistic_score = sum(
            1 for kw in self.mechanistic_keywords if kw.lower() in text
        )
        inference_score = sum(1 for kw in self.inference_keywords if kw.lower() in text)
        parameter_score = sum(1 for kw in self.parameter_keywords if kw.lower() in text)

        return {
            "mechanistic": mechanistic_score,
            "inference": inference_score,
            "parameters": parameter_score,
        }

    def _get_label_from_scores(self, scores: Dict[str, float]) -> str:
        """Determine label from scores."""
        max_score = max(scores.values())

        if max_score == 0:
            return "other"

        if scores["inference"] >= 4:
            return "inference"
        elif scores["mechanistic"] >= 2:
            return "mechanistic"
        elif scores["parameters"] >= 2:
            return "parameters"
        else:
            return "other"

    def classify_sections(self, paper_sections: Dict[str, Any]) -> ClassifiedSections:
        """
        Classify all sections from paper_sections.json.

        Expected format of paper_sections:
        {
            "sections": [
                {"title": "...", "content": "..."},
                ...
            ]
        }
        """
        classified = ClassifiedSections()

        sections = paper_sections.get("sections", [])

        if isinstance(sections, dict):
            sections = list(sections.values())

        if isinstance(paper_sections, dict) and "sections" not in paper_sections:
            sections = list(paper_sections.values())

        for section in sections:
            if isinstance(section, dict):
                title = section.get("title", "") or section.get("heading", "")
                content = section.get("content", "") or section.get("text", "")
            else:
                title = ""
                content = str(section)

            if not content:
                continue

            scores = self._score_section(title, content)
            label = self._get_label_from_scores(scores)
            max_score = max(scores.values()) if scores else 0.0

            section_label = SectionLabel(
                title=title,
                content=content,
                label=label,
                score=max_score,
            )

            if label == "mechanistic":
                classified.mechanistic.append(section_label)
            elif label == "inference":
                classified.inference.append(section_label)
            elif label == "parameters":
                classified.parameters.append(section_label)
            else:
                classified.other.append(section_label)

        return classified

    def load_and_classify(self, paper_sections_path: Path) -> ClassifiedSections:
        """Load paper_sections.json and classify sections."""
        with open(paper_sections_path, "r", encoding="utf-8") as f:
            paper_sections = json.load(f)

        return self.classify_sections(paper_sections)

    def get_relevant_sections(
        self,
        classified: ClassifiedSections,
        include_labels: List[str] = None,
        exclude_labels: List[str] = None,
    ) -> List[SectionLabel]:
        """
        Get sections filtered by labels.

        Args:
            classified: ClassifiedSections object
            include_labels: Labels to include (e.g., ["mechanistic", "parameters"])
            exclude_labels: Labels to exclude (e.g., ["inference"])
        """
        if include_labels is None:
            include_labels = ["mechanistic", "parameters"]

        if exclude_labels is None:
            exclude_labels = ["inference"]

        all_sections = []

        for label in include_labels:
            all_sections.extend(classified.get_sections_by_label(label))

        if exclude_labels:
            all_sections = [s for s in all_sections if s.label not in exclude_labels]

        all_sections.sort(key=lambda x: x.score, reverse=True)

        return all_sections

    def get_mechanistic_only(self, classified: ClassifiedSections) -> str:
        """Get all mechanistic section text (excludes inference contamination)."""
        return classified.get_all_mechanistic_text()


def load_classifier_config(config_path: Path) -> Dict[str, Any]:
    """Load section labels config from repair_config.json."""
    with open(config_path, "r") as f:
        config = json.load(f)
    return config.get("section_labels", {})


if __name__ == "__main__":
    import sys

    if len(sys.argv) < 2:
        print("Usage: python section_classifier.py <paper_sections.json>")
        sys.exit(1)

    classifier = SectionClassifier()
    result = classifier.load_and_classify(Path(sys.argv[1]))

    print("=== CLASSIFICATION RESULTS ===")
    print(f"Mechanistic: {len(result.mechanistic)} sections")
    print(f"Inference: {len(result.inference)} sections")
    print(f"Parameters: {len(result.parameters)} sections")
    print(f"Other: {len(result.other)} sections")

    print("\n=== MECHANISTIC SECTIONS ===")
    for s in result.mechanistic:
        print(f"- {s.title} (score: {s.score})")

    print("\n=== INFERENCE SECTIONS (EXCLUDED) ===")
    for s in result.inference:
        print(f"- {s.title} (score: {s.score})")
