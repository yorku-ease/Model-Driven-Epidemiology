"""Extract detailed entities (compartments, flows, parameters) with evidence

Extracts compartments, flows, parameters, stratifications, and interventions
from paper text with evidence (text span, page number, confidence).
"""

import json
import os
import re
from pathlib import Path
from typing import Dict, List, Any, Optional
from difflib import SequenceMatcher
from src.utils.llm_client import LLMClient
from src.extraction.text_windows import build_text_window, format_tables_for_prompt

# JSON schemas for Gemini structured output (guarantees valid JSON; improves compartments/flows/params extraction)
GEMINI_COMPARTMENT_SCHEMA: Dict[str, Any] = {
    "type": "array",
    "items": {
        "type": "object",
        "properties": {
            "name": {"type": "string", "description": "Compartment name"},
            "description": {
                "type": "string",
                "description": "Brief description from paper",
            },
            "text_span": {
                "type": "string",
                "description": "Exact quote showing where compartment is mentioned",
            },
        },
        "required": ["name", "description", "text_span"],
    },
}
GEMINI_FLOW_SCHEMA: Dict[str, Any] = {
    "type": "array",
    "items": {
        "type": "object",
        "properties": {
            "source": {"type": "string", "description": "Source compartment name"},
            "target": {"type": "string", "description": "Target compartment name"},
            "description": {
                "type": "string",
                "description": "Brief description of the flow",
            },
            "text_span": {
                "type": "string",
                "description": "Exact quote showing where flow is described",
            },
            "flow_type": {
                "type": "string",
                "description": "RateFlow or ContactFlow",
                "enum": ["RateFlow", "ContactFlow"],
            },
        },
        "required": ["source", "target", "description", "text_span", "flow_type"],
    },
}
GEMINI_PARAMETER_SCHEMA: Dict[str, Any] = {
    "type": "array",
    "items": {
        "type": "object",
        "properties": {
            "name": {"type": "string", "description": "Parameter symbol or name"},
            "value": {"type": "string", "description": "Numerical value if specified"},
            "unit": {"type": "string", "description": "Unit if specified"},
            "description": {
                "type": "string",
                "description": "Brief description from paper",
            },
            "text_span": {
                "type": "string",
                "description": "Exact quote showing where parameter is defined",
            },
        },
        "required": ["name", "description", "text_span"],
    },
}


class EntityExtractor:
    """Extract model entities from paper text with evidence"""

    def __init__(
        self,
        llm_client: Optional[LLMClient] = None,
        metamodel_path: Optional[str] = None,
        example_models_path: Optional[str] = None,
        llm_compartments_chars: int = 50000,
        llm_flows_chars: int = 80000,
        llm_parameters_chars: int = 80000,
        flow_fuzzy_threshold: float = 0.78,
        paper_type: Optional[Dict[str, bool]] = None,
        experiment: Optional[str] = None,
    ):
        """
        Initialize entity extractor.

        Args:
            llm_client: LLM client instance
            metamodel_path: Path to epidemiology metamodel JSON
            example_models_path: Path to directory with Phase 1 example .compmodel files
            llm_compartments_chars: Max characters of paper text to provide to LLM for compartments
            llm_flows_chars: Max characters of paper text to provide to LLM for flows
            llm_parameters_chars: Max characters of paper text to provide to LLM for parameters
            flow_fuzzy_threshold: Similarity threshold used to snap LLM flow endpoints to known compartments
            paper_type: Optional {"vector_borne": bool, "climate": bool} to tailor prompts (vector/climate hints)
            experiment: Optional variant string (e.g. "B", "C", "B+C") for A/B testing; from PHASE2_EXPERIMENT
        """
        self.llm_client = llm_client or LLMClient()
        self.metamodel = None
        if metamodel_path:
            self._load_metamodel(metamodel_path)

        self.example_models = []
        if example_models_path:
            self._load_example_models(example_models_path)

        self.llm_compartments_chars = (
            int(llm_compartments_chars) if llm_compartments_chars else 50000
        )
        self.llm_flows_chars = int(llm_flows_chars) if llm_flows_chars else 80000
        self.llm_parameters_chars = (
            int(llm_parameters_chars) if llm_parameters_chars else 80000
        )
        self.flow_fuzzy_threshold = (
            float(flow_fuzzy_threshold) if flow_fuzzy_threshold else 0.78
        )
        self.paper_type = paper_type or {}
        # Best variant from experiments: B+C (short flow text_span + exact compartment names). Kept as default.
        self.experiment = (
            (experiment or os.environ.get("PHASE2_EXPERIMENT", "B+C") or "B+C")
            .strip()
            .upper()
        )

    def _normalize_flow_compartment_name(self, name: str) -> str:
        """Strip trailing parenthetical like (E1), (I2) so 'Exposed (E1)' matches 'Exposed'."""
        if not name:
            return name
        return (
            re.sub(r"\s*\([A-Za-z0-9_]+\)\s*$", "", name.strip()).strip()
            or name.strip()
        )

    def _best_fuzzy_match(
        self, needle: str, haystack: List[str], threshold: float
    ) -> Optional[str]:
        """Return best fuzzy match from haystack for needle (or None)."""
        if not needle:
            return None
        needle_norm = needle.strip().lower()
        best = None
        best_sim = 0.0
        for cand in haystack:
            cand_norm = (cand or "").strip().lower()
            if not cand_norm:
                continue
            if needle_norm == cand_norm:
                return cand
            sim = SequenceMatcher(None, needle_norm, cand_norm).ratio()
            # Prefer substring containment for abbreviations like "Sus" vs "Susceptible"
            if needle_norm in cand_norm or cand_norm in needle_norm:
                sim = max(sim, 0.90)
            if sim > best_sim:
                best_sim = sim
                best = cand
        return best if best is not None and best_sim >= threshold else None

    def _build_compartment_alias_map(
        self, compartments: List[Dict[str, Any]]
    ) -> Dict[str, str]:
        """Build alias map for common compartment abbreviations (S/E/I/R/D etc.)."""
        comp_names = [
            c.get("normalized_name", "")
            for c in compartments
            if c.get("normalized_name")
        ]
        alias: Dict[str, str] = {}

        # From explicit "(X)" patterns in evidence spans
        for comp in compartments:
            comp_name = comp.get("normalized_name", "")
            text_span = comp.get("text_span", "") or ""
            m = re.search(r"\(([A-Za-z])\)", text_span)
            if m and comp_name:
                alias[m.group(1).upper()] = comp_name

        # Common epidemiology conventions
        canonical = {n.lower(): n for n in comp_names}
        for letter, default_name in [
            ("S", "Susceptible"),
            ("E", "Exposed"),
            ("I", "Infectious"),
            ("R", "Recovered"),
            ("D", "Dead"),
        ]:
            if letter not in alias and default_name.lower() in canonical:
                alias[letter] = canonical[default_name.lower()]

        # Fallback: map first letter if it is unique among compartments
        letter_to_names: Dict[str, List[str]] = {}
        for n in comp_names:
            if n:
                letter_to_names.setdefault(n[0].upper(), []).append(n)
        for letter, names in letter_to_names.items():
            if letter not in alias and len(names) == 1:
                alias[letter] = names[0]

        return alias

    def _clean_and_validate_flows(
        self, flows: List[Dict[str, Any]], compartments: List[Dict[str, Any]]
    ) -> List[Dict[str, Any]]:
        """
        Post-process flows to:
        - Snap source/target to known compartment names (case-insensitive + alias + fuzzy)
        - Drop flows with unresolved endpoints
        - Deduplicate after normalization
        """
        comp_names = [
            c.get("normalized_name", "")
            for c in compartments
            if c.get("normalized_name")
        ]
        comp_set = set(comp_names)
        comp_lower_to_canonical = {n.lower(): n for n in comp_names}
        alias_map = self._build_compartment_alias_map(compartments)

        cleaned: List[Dict[str, Any]] = []
        seen = set()

        def resolve_endpoint(x: str) -> Optional[str]:
            if not x:
                return None
            x = x.strip()

            # Exact case-insensitive
            if x.lower() in comp_lower_to_canonical:
                return comp_lower_to_canonical[x.lower()]

            # Alias single-letter or known abbrev
            if len(x) == 1 and x.upper() in alias_map:
                return alias_map[x.upper()]

            # Try to extract single-letter from forms like "S(t)" or "I0"
            m = re.match(r"^([A-Za-z])(?:\W|$)", x)
            if m and m.group(1).upper() in alias_map:
                return alias_map[m.group(1).upper()]

            # Handle subscripted abbreviations: S_h, E_h, I_v, S_v etc.
            m_sub = re.match(r"^([A-Za-z])_[a-zA-Z0-9]+$", x)
            if m_sub and m_sub.group(1).upper() in alias_map:
                return alias_map[m_sub.group(1).upper()]

            # Handle numbered abbreviations: E1, E2, I1, I2 etc.
            m_num = re.match(r"^([A-Za-z])\d+$", x)
            if m_num and m_num.group(1).upper() in alias_map:
                return alias_map[m_num.group(1).upper()]

            # Fuzzy match
            return self._best_fuzzy_match(
                x, comp_names, threshold=self.flow_fuzzy_threshold
            )

        dropped = 0
        for flow in flows:
            src_raw = (flow.get("source") or "").strip()
            tgt_raw = (flow.get("target") or "").strip()
            src = resolve_endpoint(src_raw)
            tgt = resolve_endpoint(tgt_raw)

            if not src or not tgt:
                dropped += 1
                continue
            if src not in comp_set or tgt not in comp_set:
                dropped += 1
                continue
            # Keep self-loops if they appear; some baseline models include them (e.g., stage holding flows)

            key = f"{src}->{tgt}"
            if key in seen:
                continue
            seen.add(key)

            flow["source"] = src
            flow["target"] = tgt
            # Normalize flow_type if present
            ft = (flow.get("flow_type") or "").strip()
            if ft:
                if ft.lower() in [
                    "contactflow",
                    "contact_flow",
                    "infection",
                    "transmission",
                ]:
                    flow["flow_type"] = "ContactFlow"
                elif ft.lower() in [
                    "rateflow",
                    "rate_flow",
                    "progression",
                    "recovery",
                    "death",
                    "treatment",
                ]:
                    flow["flow_type"] = "RateFlow"

            cleaned.append(flow)

        if dropped:
            print(
                f"  ✓ Flow post-processing: kept {len(cleaned)} flows, dropped {dropped} inconsistent/unmatched flows"
            )
        return cleaned

    def _clean_and_filter_parameters(
        self, parameters: List[Dict[str, Any]]
    ) -> List[Dict[str, Any]]:
        """
        Minimal post-processing - only drop obvious junk, trust LLM for the rest.
        """
        if not parameters:
            return parameters

        # Only filter obvious metadata/junk
        junk_substrings = ["doi", "http", "www", "pmid", "copyright", "license"]

        cleaned = []
        seen = set()
        dropped = 0

        for p in parameters:
            name = (
                p.get("normalized_name") or p.get("raw_text") or p.get("name") or ""
            ).strip()
            if not name:
                dropped += 1
                continue

            name_low = name.lower()

            # Drop obvious junk
            if any(bad in name_low for bad in junk_substrings):
                dropped += 1
                continue

            # Deduplicate
            if name in seen:
                continue
            seen.add(name)

            p["normalized_name"] = name
            cleaned.append(p)

        if dropped:
            print(
                f"  ✓ Parameter post-processing: kept {len(cleaned)} parameters, dropped {dropped} junk parameters"
            )
        return cleaned

    def _load_metamodel(self, metamodel_path: str):
        """Load metamodel for normalization"""
        try:
            with open(metamodel_path, "r") as f:
                self.metamodel = json.load(f)
        except Exception as e:
            print(f"Warning: Failed to load metamodel: {e}")

    def _load_example_models(self, example_models_path: str):
        """Load example .compmodel files from Phase 1 for context"""
        import xml.etree.ElementTree as ET

        example_path = Path(example_models_path)
        if not example_path.exists():
            return

        for compmodel_file in example_path.glob("*.compmodel"):
            try:
                with open(compmodel_file, "r", encoding="utf-8") as f:
                    content = f.read()
                    # Parse XML to extract compartments, parameters, and flows as examples
                    tree = ET.fromstring(content)

                    # Extract compartments
                    compartments = []
                    for comp in tree.findall(
                        ".//{http://example.com/compartmentalmodel}compartments"
                    ):
                        compartments.append(
                            {
                                "name": comp.get("PrimaryName", ""),
                                "population": comp.get("population", "0"),
                            }
                        )

                    # Extract parameters
                    parameters = []
                    for param in tree.findall(
                        ".//{http://example.com/compartmentalmodel}parameters"
                    ):
                        parameters.append(
                            {
                                "name": param.get("name", ""),
                                "value": param.get("expression", ""),
                                "description": param.get("description", ""),
                            }
                        )

                    self.example_models.append(
                        {
                            "name": compmodel_file.stem,
                            "compartments": compartments,
                            "parameters": parameters,
                        }
                    )
            except Exception as e:
                print(f"Warning: Failed to load example {compmodel_file}: {e}")

    def _normalize_compartment_name(self, name: str) -> str:
        """Normalize compartment name to canonical form"""
        name = name.strip()

        # Common normalizations
        normalizations = {
            "infected": "Infectious",
            "exposed": "Exposed",
            "susceptible": "Susceptible",
            "recovered": "Recovered",
            "removed": "Recovered",
            "dead": "Dead",
            "deceased": "Dead",
            "hospitalized": "Hospitalized",
            "quarantined": "Quarantined",
            "vaccinated": "Vaccinated",
            "treated": "Treated",
        }

        name_lower = name.lower()
        if name_lower in normalizations:
            return normalizations[name_lower]

        # Capitalize first letter
        return name[0].upper() + name[1:] if name else name

    def extract_compartments(
        self,
        paper_text: str,
        pages_data: List[Dict],
        paper_promises: Optional[Dict[str, Any]] = None,
    ) -> List[Dict[str, Any]]:
        """
        Extract compartments with evidence.

        Args:
            paper_promises: Optional Step 2 promises; if provided, LLM is instructed to only include
                promised compartments when there is direct evidence (reduces hallucination).

        Returns:
            List of compartment entities with evidence
        """
        compartments = []
        seen = set()

        # Pattern-based extraction
        patterns = [
            r"(?:compartment|state|class)\s+([A-Z])\s*(?:\([^)]+\))?\s*(?:represents?|denotes?|is)\s+([^.]+)",
            r"([A-Z])\(t\)\s*(?:represents?|denotes?|is)\s+([^.]+)",
            r"(?:we\s+model|model\s+includes?)\s+([^.]+)",
            r"(?:Susceptible|Exposed|Infectious|Recovered|Dead|Hospitalized|Quarantined|Vaccinated|Treated)",
        ]

        for page_data in pages_data:
            page_num = page_data.get("page_number", 0)
            text = page_data.get("text", "")

            for pattern in patterns:
                matches = re.finditer(pattern, text, re.IGNORECASE)
                for match in matches:
                    if len(match.groups()) >= 1:
                        comp_name = match.group(1) if match.groups() else match.group(0)
                        comp_name = self._normalize_compartment_name(comp_name)

                        if comp_name and comp_name not in seen:
                            seen.add(comp_name)
                            compartments.append(
                                {
                                    "raw_text": match.group(0),
                                    "normalized_name": comp_name,
                                    "page_number": page_num,
                                    "text_span": match.group(0),
                                    "extraction_method": "pattern",
                                    "confidence": "medium",
                                    "paper_backed": True,
                                }
                            )

        # LLM-based extraction if available
        if self.llm_client.is_available():
            # Build a focused window for compartments (equations + "divided into compartments")
            try:
                window_text = build_text_window(
                    pages_data,
                    include_patterns=[
                        r"\bcompartment",
                        r"\bdivided into\b",
                        r"\bS\s*\(t\)|\bE\s*\(t\)|\bI\s*\(t\)|\bR\s*\(t\)",
                        r"d[a-z]\s*\/\s*dt|d[a-z]\s*/\s*dt",
                        r"\binitial condition|\bS\(0\)|\bE\(0\)|\bI\(0\)|\bR\(0\)",
                    ],
                    title="COMPARTMENTS WINDOW (model definition/equations)",
                    max_chars=self.llm_compartments_chars,
                    pad=1,
                    max_pages=8,
                    fallback_first_pages=4,
                )
            except Exception:
                window_text = paper_text

            llm_compartments = self._extract_compartments_llm(
                window_text, paper_promises=paper_promises
            )
            for comp in llm_compartments:
                if comp["normalized_name"] not in seen:
                    seen.add(comp["normalized_name"])
                    compartments.append(comp)

        return compartments

    def _extract_compartments_llm(
        self, paper_text: str, paper_promises: Optional[Dict[str, Any]] = None
    ) -> List[Dict[str, Any]]:
        """Extract compartments using LLM with metamodel and Phase 1 examples.
        If paper_promises is provided, the prompt instructs to only include promised
        compartments when there is direct evidence (reduces hallucination from Step 2)."""
        truncated_text = paper_text[: self.llm_compartments_chars]

        # Use detailed prompts for both providers so extraction quality is comparable
        use_detailed_prompt = True

        # Paper-type hints (vector-borne / climate) for tailored prompts
        paper_type_context = ""
        if self.paper_type.get("vector_borne"):
            paper_type_context = """
This paper appears to describe a VECTOR-BORNE model (e.g. mosquito, dengue, Zika, malaria). Look for BOTH human compartments (Susceptible humans, Exposed humans, Infectious humans, Recovered humans) AND vector/life-stage compartments (Susceptible mosquitoes, Infectious mosquitoes, Eggs, Larvae, Pupae, Susceptible female adults, Exposed female adults, Infectious female adults) if there is direct evidence in the text. Only include those for which you find a clear quote.
"""
        if self.paper_type.get("climate"):
            paper_type_context += """
Consider CLIMATE or ENVIRONMENTAL drivers if mentioned (e.g. temperature, rainfall, seasonality, humidity). Include compartments or parameters related to these only if explicitly evidenced.
"""

        # Optional: promised compartments from Step 2 — only include if evidence found
        promised_context = ""
        if paper_promises:
            promised_comps = paper_promises.get("compartments", [])
            if isinstance(promised_comps, list) and promised_comps:
                names = []
                for c in promised_comps:
                    if isinstance(c, str):
                        names.append(c)
                    elif isinstance(c, dict) and c.get("name"):
                        names.append(c["name"])
                if names:
                    promised_context = f"""
PROMISED COMPARTMENTS (from a prior pass): {", ".join(names[:20])}
Only include these if you find direct evidence in the paper text below. If you find no supporting quote for a promised compartment, do NOT include it. Omit any promised item that lacks evidence.
"""

        # Build metamodel context
        metamodel_context = ""
        if self.metamodel:
            compartment_types = self.metamodel.get(
                "epimde_compartmental_metamodel", {}
            ).get("compartment_types", [])
            if compartment_types:
                metamodel_context = f"""
Standard compartment types from metamodel:
{", ".join(compartment_types)}
"""

        # Build Phase 1 examples context
        examples_context = ""
        if self.example_models:
            examples_list = []
            for model in self.example_models[:3]:  # Use up to 3 examples
                comp_names = [
                    c["name"] for c in model.get("compartments", []) if c["name"]
                ]
                if comp_names:
                    examples_list.append(
                        f"  {model['name']}: {', '.join(comp_names[:6])}"
                    )

            if examples_list:
                examples_context = f"""
Examples from successful Phase 1 models:
{chr(10).join(examples_list)}
"""

        separator = "\n" + "*" * 80 + "\n"

        if use_detailed_prompt:
            # Detailed prompt for Gemini (benefits from extensive instructions)
            prompt = f"""{separator}
TASK: Extract ALL compartment names from this epidemiological modeling paper.
{separator}
CRITICAL OUTPUT FORMAT:
- Return ONLY a valid JSON array
- No explanations, no markdown, no code blocks
- Begin directly with '[' and end with ']'
- Do NOT use ```json or ``` markers
- Invalid JSON will cause errors
- Each object must be properly formatted JSON

{paper_type_context}{promised_context}{metamodel_context}{examples_context}
{separator}
YOUR TASK:
You are an expert in epidemiological compartmental modeling. Your job is to identify every compartment that is clearly mentioned in this paper (with evidence). Do not add compartments that lack supporting quotes. Missing even one compartment will cause the model to be incomplete and incorrect.

COMPARTMENTS TO LOOK FOR:
- Standard SEIR compartments: Susceptible (S), Exposed (E), Infectious (I), Recovered (R)
- Disease-specific compartments: Dead, Deceased, Hospitalized, Quarantined, Vaccinated, Treated
- Vector compartments: Susceptible Mosquitoes, Infected Mosquitoes (for vector-borne diseases)
- Any other population groups or states mentioned in the model
- Look for compartment definitions, state variables, population groups, or model states

HOW TO IDENTIFY COMPARTMENTS - BE SYSTEMATIC:
1. Look for explicit compartment definitions: "compartment X represents...", "state X", "group X", "class X"
2. Look for state variables in equations: "S(t)", "I(t)", "E(t)", "dS/dt", "dI/dt" (differential equations)
3. Look for population groups: "susceptible individuals", "infected people", "recovered population"
4. Look for model descriptions: "the model includes compartments: X, Y, Z" or "we model X groups"
5. Look for compartment lists in tables, especially "Table of Compartments" or similar
6. Check for compartment names with letters in parentheses: "Susceptible (S)", "Infectious (I)"
7. Look for compartment indices: "Compartment 0: S", "Compartment 1: E", etc.
8. Check model diagrams descriptions if mentioned in text
9. Look for initial population definitions: "S(0) = ...", "I(0) = ..." (these indicate compartments)
10. Check for compartment transitions in flow descriptions: "from X to Y" implies both X and Y are compartments

REQUIRED JSON STRUCTURE:
Each compartment object MUST have these exact fields:
{{
  "name": "CompartmentName",
  "description": "Brief description from paper",
  "text_span": "Exact quote showing where this compartment is mentioned"
}}

EVIDENCE RULE - CRITICAL:
- Only include a compartment if you find clear, direct evidence (exact quote) in the paper text below.
- If you cannot find a supporting quote for a compartment, do NOT include it. Prefer omitting over inventing.
- Do not infer or assume compartments that are not explicitly stated or clearly implied in the text.
- Reject any compartment for which there is not enough evidence; it is better to have false negatives than hallucinations.

CHAIN-OF-THOUGHT (reason step by step): First list the compartment names you find in the text; for each note the exact quote that defines it; then output the JSON array. This reduces omissions and spurious entries.

EXTRACTION RULES - BE THOROUGH BUT EVIDENCE-BASED:
- Extract compartments that are clearly mentioned or defined; do not add compartments that are only implied or guessed
- If a compartment is mentioned multiple times, use the most detailed description for the description field
- For text_span, use the FIRST or MOST DEFINITIVE mention (where it's clearly defined)
- Normalize names to standard epidemiological terms:
  * "Susceptible" or "S" or "Susceptibles" → "Susceptible"
  * "Exposed" or "E" or "Latent" or "Incubating" → "Exposed"
  * "Infectious" or "Infected" or "I" or "Symptomatic" → "Infectious"
  * "Recovered" or "Removed" or "R" or "Immune" → "Recovered"
  * "Dead" or "Deceased" or "D" → "Dead" or "Infectious Deceased" (choose based on context)
  * "Hospitalized" or "H" → "Hospitalized"
  * "Quarantined" or "Q" → "Quarantined"
- Include the EXACT text quote (text_span) showing where each compartment is defined or mentioned
- Be comprehensive - missing compartments will cause model errors
- Only include a compartment if you have a clear text_span quote from the paper; if no quote, omit it
- Count compartments: If paper says "5 compartments" or "SEIR model", ensure you extract exactly that many (only if each has evidence)
- Check for secondary names: Some compartments have primary and secondary names (e.g., "Infectious, Untreated")

COMMON COMPARTMENT PATTERNS TO SEARCH FOR:
- "Susceptible (S) individuals..." or "S(t) represents..."
- "The model consists of X compartments: ..." or "we model X groups"
- "We model the following states: ..." or "the model includes..."
- "dS/dt", "dI/dt", "dE/dt" (differential equations - each variable is a compartment)
- "S(t)", "I(t)", "E(t)", "R(t)" (state variables in equations)
- "Table of Compartments" or "Compartment definitions"
- "Initial conditions: S(0)=..., I(0)=..." (initial values indicate compartments)
- "Compartment 0: ...", "Compartment 1: ..." (indexed lists)

VALIDATION CHECKLIST:
Before finalizing your response, verify:
- Did you find all compartments mentioned in model descriptions?
- Did you check differential equations for state variables?
- Did you look for compartment lists or tables?
- Did you check for compartments mentioned in flow descriptions?
- If the paper says "X compartments" or "SEIR model", do you have that many?

{separator}
PAPER TEXT:
{truncated_text}
{separator}
Return ONLY valid JSON array starting with '[' and ending with ']'. Extract ALL compartments mentioned in the paper. Be thorough and systematic."""
        else:
            # Concise prompt for OpenAI (works better with focused, direct instructions)
            prompt = f"""{separator}
TASK: Extract all compartment names from this epidemiological modeling paper.
{separator}
OUTPUT FORMAT: Return ONLY a valid JSON array. No markdown, no explanations. Start with '[' and end with ']'.

{paper_type_context}{promised_context}{metamodel_context}{examples_context}
{separator}
EVIDENCE RULE: Only include a compartment if you find clear, direct evidence (exact quote) in the paper. If you cannot find a supporting quote, do NOT include it. Prefer false negatives over inventing entities.

REASON STEP BY STEP: First list compartment names you find; for each cite the exact quote; then output the JSON array.

EXTRACTION INSTRUCTIONS:
Identify compartments by looking for:
- Compartment definitions ("compartment X", "state X", "group X")
- State variables in equations ("S(t)", "I(t)", "dS/dt", "dI/dt")
- Model descriptions ("the model includes compartments: X, Y, Z")
- Compartment lists in tables
- Initial conditions ("S(0) = ...", "I(0) = ...")
Only output compartments for which you have a clear text_span quote. Reject if no evidence.

REQUIRED JSON STRUCTURE:
Each object must have: "name", "description", "text_span"
{{
  "name": "CompartmentName",
  "description": "Brief description",
  "text_span": "Exact quote from paper"
}}

NORMALIZATION:
- "Susceptible"/"S" → "Susceptible"
- "Exposed"/"E"/"Latent" → "Exposed"
- "Infectious"/"Infected"/"I" → "Infectious"
- "Recovered"/"Removed"/"R" → "Recovered"
- "Dead"/"Deceased"/"D" → "Dead" (or "Infectious Deceased" if context requires)

EXTRACT ALL compartments mentioned. Include exact text quotes. If paper mentions "X compartments", ensure you extract that many.

{separator}
PAPER TEXT:
{truncated_text}
{separator}
Return ONLY valid JSON array: [{{"name": "...", "description": "...", "text_span": "..."}}, ...]"""

        try:
            # Use higher max_tokens for compartments (text_span can be long)
            # When supported (Gemini), use structured output + lower temperature for more reliable extraction
            kwargs = {"max_tokens": 4000}
            if self.llm_client.provider == "gemini":
                kwargs["response_schema"] = GEMINI_COMPARTMENT_SCHEMA
                kwargs["temperature"] = 0.2
            result = self.llm_client.extract_with_llm(prompt, **kwargs)
            if isinstance(result, list):
                return [
                    {
                        "raw_text": item.get("text_span", ""),
                        "normalized_name": self._normalize_compartment_name(
                            item.get("name", "")
                        ),
                        "page_number": 0,  # LLM doesn't know page numbers
                        "text_span": item.get("text_span", ""),
                        "extraction_method": "llm",
                        "confidence": "high",
                        "paper_backed": True,
                        "description": item.get("description", ""),
                    }
                    for item in result
                ]
        except Exception as e:
            print(f"Warning: LLM compartment extraction failed: {e}")

        return []

    def extract_flows(
        self, paper_text: str, pages_data: List[Dict], compartments: List[Dict]
    ) -> List[Dict[str, Any]]:
        """
        Extract flows with evidence.

        Returns:
            List of flow entities with evidence
        """
        flows = []
        seen_flows = set()

        # Create mapping from single letters to compartment names
        comp_letter_map = {}
        for comp in compartments:
            comp_name = comp["normalized_name"]
            # Try to extract letter from text span (e.g., "Susceptible (S)")
            text_span = comp.get("text_span", "")
            letter_match = re.search(r"\(([A-Z])\)", text_span)
            if letter_match:
                letter = letter_match.group(1).upper()
                comp_letter_map[letter] = comp_name

        # Also map by first letter as fallback
        for comp in compartments:
            comp_name = comp["normalized_name"]
            first_letter = comp_name[0].upper()
            if first_letter not in comp_letter_map:
                comp_letter_map[first_letter] = comp_name

        # Enhanced pattern-based extraction
        flow_patterns = [
            # Arrow notation
            (r"([A-Z])\s*→\s*([A-Z])", "arrow"),
            (r"([A-Z])\(t\)\s*→\s*([A-Z])\(t\)", "arrow_function"),
            # Differential equations
            (r"d([A-Z])/dt\s*=\s*[^=]*[+\-]\s*[^=]*([A-Z])", "differential"),
            (r"d([A-Z])/dt\s*=\s*[^=]*β[^=]*([A-Z])", "contact_flow"),
            # Text descriptions
            (
                r"([A-Z])\s+(?:progresses?|transitions?|moves?|flows?)\s+to\s+([A-Z])",
                "text",
            ),
            (r"from\s+([A-Z])\s+to\s+([A-Z])", "text"),
            (r"([A-Z])\s+→\s+([A-Z])\s+(?:at|with|rate)", "text_rate"),
            # Compartment names directly
            (
                r"(Susceptible|Exposed|Infectious|Recovered|Dead|Deceased)\s+(?:→|to|progresses?)\s+(Susceptible|Exposed|Infectious|Recovered|Dead|Deceased)",
                "name",
            ),
        ]

        for page_data in pages_data:
            page_num = page_data.get("page_number", 0)
            text = page_data.get("text", "")

            for pattern, pattern_type in flow_patterns:
                matches = re.finditer(pattern, text, re.IGNORECASE)
                for match in matches:
                    if len(match.groups()) >= 2:
                        source_key = match.group(1)
                        target_key = match.group(2)

                        # Map to compartment names
                        source_comp = comp_letter_map.get(source_key.upper()) or next(
                            (
                                c["normalized_name"]
                                for c in compartments
                                if source_key.lower() in c["normalized_name"].lower()
                            ),
                            None,
                        )
                        target_comp = comp_letter_map.get(target_key.upper()) or next(
                            (
                                c["normalized_name"]
                                for c in compartments
                                if target_key.lower() in c["normalized_name"].lower()
                            ),
                            None,
                        )

                        if source_comp and target_comp:
                            flow_key = f"{source_comp}->{target_comp}"
                            if flow_key not in seen_flows:
                                seen_flows.add(flow_key)

                                # Determine flow type
                                flow_type = (
                                    "ContactFlow"
                                    if "contact" in match.group(0).lower()
                                    or "β" in match.group(0)
                                    else "RateFlow"
                                )

                                flows.append(
                                    {
                                        "source": source_comp,
                                        "target": target_comp,
                                        "raw_text": match.group(0),
                                        "page_number": page_num,
                                        "text_span": match.group(0),
                                        "extraction_method": f"pattern_{pattern_type}",
                                        "confidence": "medium",
                                        "paper_backed": True,
                                        "flow_type": flow_type,
                                    }
                                )

        # LLM-based extraction if available (always try to get more flows)
        if self.llm_client.is_available():
            # Focus on equations/diagram pages for flow extraction
            try:
                flow_window = build_text_window(
                    pages_data,
                    include_patterns=[
                        r"d[a-z]\s*\/\s*dt|d[a-z]\s*/\s*dt",
                        r"\bS\s*\(t\)|\bE\s*\(t\)|\bI\s*\(t\)|\bR\s*\(t\)",
                        r"\b→\b|->|from\s+\w+\s+to\s+\w+",
                        r"\bequation\b|\bflow\b|\btransition\b|\bprogress\b|\brecover\b|\bdeath\b|\binfect",
                        r"\bfigure\b|\bdiagram\b",
                    ],
                    title="FLOWS WINDOW (equations/diagram/transitions)",
                    max_chars=self.llm_flows_chars,
                    pad=1,
                    max_pages=8,
                    fallback_first_pages=4,
                )
            except Exception:
                flow_window = paper_text

            llm_flows = self._extract_flows_llm(flow_window, compartments)
            # Add LLM flows that aren't duplicates
            existing_flow_keys = {f"{f['source']}->{f['target']}" for f in flows}
            for llm_flow in llm_flows:
                flow_key = f"{llm_flow['source']}->{llm_flow['target']}"
                if flow_key not in existing_flow_keys:
                    flows.append(llm_flow)
                    existing_flow_keys.add(flow_key)

        return flows

    def _extract_flows_llm(
        self, paper_text: str, compartments: List[Dict]
    ) -> List[Dict[str, Any]]:
        """Extract flows using LLM with metamodel and Phase 1 examples"""
        truncated_text = paper_text[: self.llm_flows_chars]

        use_detailed_prompt = True  # same quality instructions for both providers

        comp_names = [c["normalized_name"] for c in compartments]
        comp_list = ", ".join(comp_names)

        # Paper-type hints for flows
        paper_type_context = ""
        if self.paper_type.get("vector_borne"):
            paper_type_context = """
This paper appears to describe a VECTOR-BORNE model. Look for flows between HUMAN compartments (e.g. Susceptible humans -> Exposed humans) AND vector/life-stage flows (e.g. Eggs -> Larvae -> Pupae -> Susceptible female adults, and human-vector transmission flows). Only include flows for which you find clear evidence.
"""

        # Build metamodel context
        metamodel_context = ""
        if self.metamodel:
            flow_types = self.metamodel.get("epimde_compartmental_metamodel", {}).get(
                "flow_types", []
            )
            if flow_types:
                metamodel_context = f"""
Flow types from metamodel:
{", ".join(flow_types)}
"""

        # Build Phase 1 examples context for flows
        examples_context = ""
        if self.example_models:
            examples_list = []
            for model in self.example_models[:2]:  # Use up to 2 examples
                comp_names_ex = [
                    c["name"] for c in model.get("compartments", []) if c["name"]
                ]
                if comp_names_ex:
                    # Show example flow patterns
                    flow_pattern = " → ".join(comp_names_ex[:4])
                    examples_list.append(f"  {model['name']}: {flow_pattern}")

            if examples_list:
                examples_context = f"""
Example flow patterns from Phase 1 models:
{chr(10).join(examples_list)}
"""

        # Experiment variants: B = short text_span + more tokens, C = exact compartment names only
        flow_format_extra = ""
        if "B" in self.experiment:
            flow_format_extra = "\n- Keep each text_span to at most 200 characters (one or two sentences) to avoid broken JSON.\n"
        if "C" in self.experiment:
            flow_format_extra += f"\nCRITICAL: Use ONLY these exact compartment names for source and target (no parentheticals like (E1) or (I2)): {comp_list}\n"
        flow_max_tokens = 6000 if "B" in self.experiment else 4000

        separator = "\n" + "*" * 80 + "\n"

        if use_detailed_prompt:
            # Detailed prompt for Gemini
            prompt = f"""{separator}
TASK: Extract flows (transitions) between compartments from this epidemiological modeling paper.
{separator}
CRITICAL OUTPUT FORMAT:
- Return ONLY a valid JSON array
- No explanations, no markdown, no code blocks
- Begin directly with '[' and end with ']'
- Do NOT use ```json or ``` markers
- Invalid JSON will cause errors
- Each object must be properly formatted JSON

AVAILABLE COMPARTMENTS: {comp_list}
{paper_type_context}{metamodel_context}{examples_context}
{separator}
YOUR TASK:
You are an expert in epidemiological compartmental modeling.
You MUST extract EVERY flow (transition) between compartments that is implied by:
- any model equation (dS/dt, S(t+Δt), etc.)
- any text description ("from X to Y", "X → Y", "X moves to Y")
- any diagram/table caption that describes transitions.
Missing a flow is considered an error.

HOW TO IDENTIFY FLOWS:
1. Look for explicit flow descriptions: "from X to Y", "X → Y", "X transitions to Y"
2. Look for differential equations: "dX/dt = ... + Y" indicates flow from Y to X
3. Look for transmission/infection flows: "Susceptible becomes Infectious", "S → I"
4. Look for progression flows: "Exposed progresses to Infectious", "E → I"
5. Look for recovery flows: "Infectious recovers", "I → R"
6. Look for death flows: "Infectious dies", "I → D"
7. Look for treatment flows: "Infectious receives treatment", "I → T"
8. Check for flow tables or lists in the paper
9. Look for compartment transitions in model descriptions

REQUIRED JSON STRUCTURE:
Each flow object MUST have these exact fields:
{{
  "source": "SourceCompartmentName",
  "target": "TargetCompartmentName",
  "description": "Brief description of the flow",
  "text_span": "Exact quote showing where this flow is described",
  "flow_type": "RateFlow" or "ContactFlow"
}}
{flow_format_extra}
FLOW TYPE RULES:
- RateFlow: progression (E→I), recovery (I→R), death (I→D), treatment (I→T)
- ContactFlow: transmission/infection (S→E, S→I) involving contact between compartments
- Match source/target to available compartments exactly (case-sensitive)

EVIDENCE RULE - CRITICAL:
- Only include a flow if you find clear, direct evidence (exact quote) in the paper text below.
- If you cannot find a supporting quote for a flow, do NOT include it.
- Do not infer or assume flows that are not explicitly stated or clearly implied (e.g. in equations or diagram descriptions).
- Reject any flow for which there is not enough evidence.

REASON STEP BY STEP: First list each flow you find (source -> target) with the quote that describes it; then output the JSON array. This reduces missed or invented flows.

EXTRACTION RULES - BE THOROUGH AND EVIDENCE-BASED:
- Extract ALL flows that are clearly mentioned or implied by equations/diagrams; be thorough
- Use ONLY these compartment names for source/target (map symbols like S(t), E1(t) to the closest compartment): {comp_list}
- Include exact text quotes as evidence for each flow; if no quote, omit the flow
- If unsure about flow type, use RateFlow for progression/recovery/death, ContactFlow for transmission

{separator}
PAPER TEXT:
{truncated_text}
{separator}
Return ONLY valid JSON array starting with '[' and ending with ']'. Extract flows for which you have clear evidence."""
        else:
            # Concise prompt for OpenAI
            prompt = f"""{separator}
TASK: Extract flows (transitions) between compartments from this epidemiological modeling paper.
{separator}
OUTPUT FORMAT: Return ONLY a valid JSON array. No markdown, no explanations. Start with '[' and end with ']'.

AVAILABLE COMPARTMENTS: {comp_list}
{paper_type_context}{metamodel_context}{examples_context}
{separator}
EVIDENCE RULE: Only include a flow if you find clear, direct evidence (exact quote) in the paper. If you cannot find a supporting quote, do NOT include it. Be thorough - extract all flows you can find evidence for.

REASON STEP BY STEP: First list each flow (source -> target) with the quote; then output the JSON array.

EXTRACTION INSTRUCTIONS:
Identify flows by looking for:
- Flow descriptions ("from X to Y", "X → Y", "X transitions to Y")
- Differential equations ("dX/dt = ... + Y" indicates flow from Y to X)
- Transmission flows ("Susceptible becomes Infectious")
- Progression/recovery/death flows
- ALL flows for which you have evidence - be thorough

REQUIRED JSON STRUCTURE:
Each object must have: "source", "target", "description", "text_span", "flow_type"
{{
  "source": "SourceCompartmentName",
  "target": "TargetCompartmentName",
  "description": "Brief description",
  "text_span": "Exact quote from paper",
  "flow_type": "RateFlow" or "ContactFlow"
}}

FLOW TYPES:
- RateFlow: progression, recovery, death, treatment
- ContactFlow: transmission, infection

Match source/target to available compartments exactly: {comp_list}
{flow_format_extra}
{separator}
PAPER TEXT:
{truncated_text}
{separator}
Return ONLY valid JSON array: [{{"source": "...", "target": "...", "description": "...", "text_span": "...", "flow_type": "..."}}, ...]"""

        try:
            kwargs = {"max_tokens": flow_max_tokens}
            if self.llm_client.provider == "gemini":
                kwargs["response_schema"] = GEMINI_FLOW_SCHEMA
                kwargs["temperature"] = 0.2
            result = self.llm_client.extract_with_llm(prompt, **kwargs)
            if isinstance(result, list):
                flows = []
                unmatched = []
                for item in result:
                    source = item.get("source", "").strip()
                    target = item.get("target", "").strip()
                    # Normalize so "Exposed (E1)" matches "Exposed"
                    source_norm = self._normalize_flow_compartment_name(source)
                    target_norm = self._normalize_flow_compartment_name(target)

                    if not source_norm or not target_norm:
                        unmatched.append(f"Missing source/target: {item}")
                        continue

                    source_comp = None
                    target_comp = None
                    for c in compartments:
                        comp_name = c["normalized_name"]
                        if source_norm.lower() == comp_name.lower():
                            source_comp = comp_name
                        if target_norm.lower() == comp_name.lower():
                            target_comp = comp_name
                    if not source_comp:
                        source_comp = next(
                            (
                                c["normalized_name"]
                                for c in compartments
                                if source_norm.lower() in c["normalized_name"].lower()
                                or c["normalized_name"].lower() in source_norm.lower()
                            ),
                            None,
                        )
                    if not target_comp:
                        target_comp = next(
                            (
                                c["normalized_name"]
                                for c in compartments
                                if target_norm.lower() in c["normalized_name"].lower()
                                or c["normalized_name"].lower() in target_norm.lower()
                            ),
                            None,
                        )

                    if source_comp and target_comp:
                        flows.append(
                            {
                                "source": source_comp,
                                "target": target_comp,
                                "raw_text": item.get("text_span", ""),
                                "page_number": 0,  # LLM doesn't know page numbers
                                "text_span": item.get("text_span", ""),
                                "extraction_method": "llm",
                                "confidence": "high",
                                "paper_backed": True,
                                "flow_type": item.get("flow_type", "RateFlow"),
                                "description": item.get("description", ""),
                            }
                        )
                    else:
                        unmatched.append(
                            f"{source}->{target} (source_match={source_comp is not None}, target_match={target_comp is not None})"
                        )

                if flows:
                    print(f"  ✓ LLM extracted {len(flows)} flows")
                else:
                    print(
                        f"  ⚠ LLM returned {len(result)} items but none matched compartments"
                    )
                    if unmatched:
                        print(f"  Unmatched flows: {unmatched[:3]}")  # Show first 3
                return flows
            elif isinstance(result, dict):
                # Check if it's an error response - try to salvage flow objects from raw JSON
                if "error" in result and "raw_response" in result:
                    raw = result.get("raw_response", "")
                    salvaged = self.llm_client.try_salvage_array(raw)
                    if salvaged:
                        flows = []
                        for item in salvaged:
                            if not isinstance(item, dict):
                                continue
                            source = self._normalize_flow_compartment_name(
                                item.get("source", "").strip()
                            )
                            target = self._normalize_flow_compartment_name(
                                item.get("target", "").strip()
                            )
                            if not source or not target:
                                continue
                            source_comp = next(
                                (
                                    c["normalized_name"]
                                    for c in compartments
                                    if source.lower() == c["normalized_name"].lower()
                                ),
                                None,
                            ) or next(
                                (
                                    c["normalized_name"]
                                    for c in compartments
                                    if source.lower() in c["normalized_name"].lower()
                                    or c["normalized_name"].lower() in source.lower()
                                ),
                                None,
                            )
                            target_comp = next(
                                (
                                    c["normalized_name"]
                                    for c in compartments
                                    if target.lower() == c["normalized_name"].lower()
                                ),
                                None,
                            ) or next(
                                (
                                    c["normalized_name"]
                                    for c in compartments
                                    if target.lower() in c["normalized_name"].lower()
                                    or c["normalized_name"].lower() in target.lower()
                                ),
                                None,
                            )
                            if source_comp and target_comp:
                                flows.append(
                                    {
                                        "source": source_comp,
                                        "target": target_comp,
                                        "raw_text": item.get("text_span", ""),
                                        "page_number": 0,
                                        "text_span": item.get("text_span", ""),
                                        "extraction_method": "llm",
                                        "confidence": "high",
                                        "paper_backed": True,
                                        "flow_type": item.get("flow_type", "RateFlow"),
                                        "description": item.get("description", ""),
                                    }
                                )
                        if flows:
                            print(f"  ✓ Salvaged {len(flows)} flows from broken JSON")
                            return flows
                    print(f"  ⚠ LLM flow extraction error: {result.get('error')}")
                    if raw:
                        print(f"  Raw response preview: {raw[:200]}...")
                elif "error" in result:
                    print(f"  ⚠ LLM flow extraction error: {result.get('error')}")
                else:
                    print(
                        f"  ⚠ LLM returned dict instead of array. Keys: {list(result.keys())}"
                    )
            else:
                print(f"  ⚠ LLM returned unexpected type: {type(result)}")
        except Exception as e:
            print(f"  ⚠ Warning: LLM flow extraction failed: {e}")
            import traceback

            traceback.print_exc()

        return []

    def extract_parameters(
        self, paper_text: str, pages_data: List[Dict], tables: List[Dict]
    ) -> List[Dict[str, Any]]:
        """
        Extract parameters with evidence.

        Returns:
            List of parameter entities with evidence
        """
        parameters = []
        seen = set()

        # Common Greek letters used in epidemiology
        valid_greek_params = {
            "α",
            "β",
            "γ",
            "δ",
            "μ",
            "ρ",
            "σ",
            "θ",
            "λ",
            "ω",
            "ν",
            "ε",
            "η",
        }

        # Extract from tables first (with strict filtering)
        for table in tables:
            table_data = table.get("data", [])
            if not table_data or len(table_data) < 2:
                continue

            # Check if this looks like a parameter table
            # Parameter tables typically have: symbol/name, value, unit, description
            header_row = [str(cell).strip().lower() for cell in table_data[0] if cell]
            header_text = " ".join(header_row)

            # Skip tables that clearly aren't parameter definitions
            skip_keywords = [
                "when",
                "r0",
                "threshold",
                "correlation",
                "degree",
                "values lie",
                "horizontal line",
                "underneath",
                "now found",
                "below",
            ]
            if any(keyword in header_text for keyword in skip_keywords):
                continue

            # Check if this looks like a parameter table (has "parameter", "value", "description" etc.)
            is_param_table = any(
                keyword in header_text
                for keyword in [
                    "parameter",
                    "symbol",
                    "value",
                    "description",
                    "definition",
                    "notation",
                ]
            )

            if not is_param_table:
                # Try to detect by structure: first column has short symbols, second has numbers
                first_col_sample = [
                    str(row[0]).strip() for row in table_data[1:3] if row and row[0]
                ]
                if not first_col_sample or all(
                    len(s) > 10 or " " in s for s in first_col_sample
                ):
                    continue  # First column doesn't look like parameter symbols

            # Extract parameters from table rows
            for row_idx, row in enumerate(table_data[1:], start=1):  # Skip header
                if not row or len(row) < 2:
                    continue

                param_name = str(row[0]).strip() if row[0] else None
                param_value = str(row[1]).strip() if len(row) > 1 and row[1] else None
                param_unit = str(row[2]).strip() if len(row) > 2 and row[2] else None
                param_desc = str(row[3]).strip() if len(row) > 3 and row[3] else None

                # Strict filtering for parameter names
                if not param_name or len(param_name) == 0:
                    continue

                # Skip if name contains skip keywords
                if any(keyword in param_name.lower() for keyword in skip_keywords):
                    continue

                # Valid parameter names are either:
                # 1. Single Greek letter
                # 2. Single Latin letter (a-z, A-Z)
                # 3. Short symbol (1-3 chars with Greek/Latin letters)
                # 4. Word-based parameter name (e.g., "birth_rate", "recovery rate")

                # Check for single Greek letter
                is_valid_param = False
                if len(param_name) == 1 and param_name in valid_greek_params:
                    is_valid_param = True
                # Check for single Latin letter
                elif len(param_name) == 1 and param_name.isalpha():
                    is_valid_param = True
                # Check for short symbols like "βc_I"
                elif 1 <= len(param_name) <= 10 and any(
                    c in valid_greek_params for c in param_name
                ):
                    is_valid_param = True
                # Check for word-based names (letters, underscores, spaces, max 30 chars)
                elif len(param_name) <= 30 and re.match(
                    r"^[a-zA-Z][a-zA-Z0-9_\s\-]*$", param_name
                ):
                    is_valid_param = True

                if not is_valid_param:
                    continue

                # Validate value (should be numeric or expression)
                if param_value and param_value.lower() not in ["none", "n/a", "na", ""]:
                    # Check if value is numeric or contains digits
                    if not any(c.isdigit() or c == "." for c in param_value):
                        continue
                else:
                    param_value = None

                if param_name and param_name not in seen:
                    seen.add(param_name)
                    # Support both PDF pipeline formats: page_number/page, table_number/table_index
                    page_no = table.get("page_number", table.get("page", 0))
                    table_no = table.get("table_number", table.get("table_index", 0))
                    parameters.append(
                        {
                            "raw_text": param_name,
                            "normalized_name": param_name,
                            "value": param_value,
                            "unit": param_unit
                            if param_unit
                            and param_unit.lower() not in ["none", "n/a", ""]
                            else None,
                            "description": param_desc
                            if param_desc
                            and param_desc.lower() not in ["none", "n/a", ""]
                            else None,
                            "page_number": page_no,
                            "text_span": f"Table {table_no}, Row {row_idx + 1}",
                            "extraction_method": "table",
                            "confidence": "high",
                            "paper_backed": True,
                        }
                    )

        # Extract from text (parameter definitions) - focusing on explicit definitions
        param_patterns = [
            # Greek letter with value: β = 0.75
            (r"([αβγδμρσθλωνεη])\s*[=:]\s*([0-9.]+(?:e[+-]?[0-9]+)?)", "greek_letter"),
            # Latin letter with value: R = 2.5
            (r"\b([A-Za-z])\s*[=:]\s*([0-9.]+(?:e[+-]?[0-9]+)?)", "latin_letter"),
            # Word parameter: rate = 0.5, death rate = 0.2
            (
                r"([a-zA-Z_]+(?:\s+[a-zA-Z_]+)?)\s*[=:]\s*([0-9.]+(?:e[+-]?[0-9]+)?)",
                "word",
            ),
        ]

        for page_data in pages_data:
            page_num = page_data.get("page_number", 0)
            text = page_data.get("text", "")

            for pattern, pattern_type in param_patterns:
                matches = re.finditer(pattern, text, re.IGNORECASE)
                for match in matches:
                    if len(match.groups()) >= 2:
                        param_name = match.group(1).strip()
                        param_value = match.group(2).strip()

                        # Additional validation
                        if pattern_type == "word":
                            # Skip common non-parameter words
                            if param_name.lower() in [
                                "table",
                                "figure",
                                "page",
                                "section",
                                "equation",
                                "year",
                                "day",
                                "week",
                                "month",
                                "time",
                                "at",
                                "is",
                                "the",
                                "and",
                                "or",
                                "in",
                                "of",
                                "to",
                                "for",
                                "with",
                            ]:
                                continue
                            # Skip if name is too long
                            if len(param_name) > 30:
                                continue

                        # Skip single letter parameters that are likely not parameters (except Greek letters)
                        if pattern_type == "latin_letter" and len(param_name) == 1:
                            # Skip common non-parameter single letters
                            if param_name.lower() in [
                                "a",
                                "b",
                                "c",
                                "d",
                                "e",
                                "f",
                                "g",
                                "h",
                                "j",
                                "k",
                                "l",
                                "m",
                                "n",
                                "o",
                                "p",
                                "q",
                                "t",
                                "u",
                                "v",
                                "w",
                                "x",
                                "y",
                                "z",
                            ]:
                                continue  # Only keep I, R, S which are common compartment abbreviations

                        # Skip obvious non-parameters by name
                        if param_name.lower() in ["cid", "fig", "table", "eq", "ref"]:
                            continue

                        if param_name and param_name not in seen:
                            seen.add(param_name)
                            parameters.append(
                                {
                                    "raw_text": param_name,
                                    "normalized_name": param_name,
                                    "value": param_value,
                                    "unit": None,
                                    "description": None,
                                    "page_number": page_num,
                                    "text_span": match.group(0),
                                    "extraction_method": "pattern_text",
                                    "confidence": "medium",
                                    "paper_backed": True,
                                }
                            )

        # Use LLM to extract parameters if available
        if self.llm_client.is_available():
            # Build a focused window for parameters and include extracted tables as structured text
            try:
                param_window = build_text_window(
                    pages_data,
                    include_patterns=[
                        r"\bparameter\b",
                        r"\bvalue\b",
                        r"\btable\b",
                        r"[αβγδμρσθλ]\s*[=:]",
                        r"\bR0\b|\bR_0\b|\breproduction number\b",
                        r"\bestimat|\bfit\b|\bcalibrat",
                    ],
                    title="PARAMETERS WINDOW (tables/definitions/equations)",
                    max_chars=min(self.llm_parameters_chars, 20000),
                    pad=1,
                    max_pages=8,
                    fallback_first_pages=4,
                )
            except Exception:
                param_window = paper_text[: min(self.llm_parameters_chars, 20000)]

            tables_text = ""
            try:
                tables_text = format_tables_for_prompt(
                    tables,
                    title="EXTRACTED TABLES (compact)",
                    max_tables=3,
                    max_rows=12,
                    max_chars=12000,
                )
            except Exception:
                tables_text = ""

            llm_input = param_window
            if tables_text:
                llm_input = f"{param_window}\n\n{tables_text}"

            llm_params = self._extract_parameters_llm(llm_input)
            for param in llm_params:
                param_name = param.get("normalized_name", "")
                # Filter out common non-parameters even from LLM
                if param_name and param_name not in seen:
                    # Skip single common letters that are likely not parameters
                    if len(param_name) == 1 and param_name.lower() in [
                        "a",
                        "b",
                        "c",
                        "d",
                        "e",
                        "f",
                        "g",
                        "h",
                        "j",
                        "k",
                        "l",
                        "m",
                        "n",
                        "o",
                        "p",
                        "q",
                        "t",
                        "u",
                        "v",
                        "w",
                        "x",
                        "y",
                        "z",
                    ]:
                        continue
                    # Skip obvious non-parameters
                    if param_name.lower() in ["cid", "fig", "table", "eq"]:
                        continue
                    seen.add(param_name)
                    parameters.append(param)

        return parameters

    def _extract_parameters_llm(self, paper_text: str) -> List[Dict[str, Any]]:
        """Extract parameters using LLM with metamodel and Phase 1 examples"""
        truncated_text = paper_text[: self.llm_parameters_chars]

        use_detailed_prompt = True  # same quality instructions for both providers

        # Paper-type hints for parameters
        paper_type_context = ""
        if self.paper_type.get("vector_borne"):
            paper_type_context = """
This paper appears to describe a VECTOR-BORNE model. Look for human parameters (transmission, recovery, mortality) AND vector/life-stage parameters (biting rate, egg/larval/pupal development rates, vector mortality, vector incubation). Only include parameters with clear evidence.
"""
        if self.paper_type.get("climate"):
            paper_type_context += """
Consider CLIMATE/ENVIRONMENTAL parameters if mentioned (e.g. temperature, rainfall, seasonality). Include only if explicitly defined in the text.
"""

        # Build metamodel context
        metamodel_context = ""
        if self.metamodel:
            parameter_types = self.metamodel.get(
                "epimde_compartmental_metamodel", {}
            ).get("parameter_types", [])
            if parameter_types:
                metamodel_context = f"""
Parameter types from metamodel:
{", ".join(parameter_types)}
"""

        # Build Phase 1 examples context for parameters
        examples_context = ""
        if self.example_models:
            examples_list = []
            for model in self.example_models[:3]:  # Use up to 3 examples
                params = model.get("parameters", [])
                if params:
                    param_examples = []
                    for p in params[:5]:  # Show up to 5 parameters per model
                        name = p.get("name", "")
                        value = p.get("value", "")
                        desc = p.get("description", "")
                        if name:
                            if value and desc:
                                param_examples.append(f"{name}={value} ({desc})")
                            elif value:
                                param_examples.append(f"{name}={value}")
                            else:
                                param_examples.append(name)
                    if param_examples:
                        examples_list.append(
                            f"  {model['name']}: {'; '.join(param_examples)}"
                        )

            if examples_list:
                examples_context = f"""
Example parameters from Phase 1 models:
{chr(10).join(examples_list)}
"""

        separator = "\n" + "*" * 80 + "\n"

        if use_detailed_prompt:
            # Detailed prompt for Gemini
            prompt = f"""{separator}
TASK: Extract model parameters from this epidemiological modeling paper.
{separator}
CRITICAL OUTPUT FORMAT:
- Return ONLY a valid JSON array
- No explanations, no markdown, no code blocks
- Begin directly with '[' and end with ']'
- Do NOT use ```json or ``` markers
- Invalid JSON will cause errors
- Each object must be properly formatted JSON

{paper_type_context}{metamodel_context}{examples_context}
{separator}
YOUR TASK:
You are an expert in epidemiological modeling. Extract EVERY parameter mentioned in this paper.

HOW TO IDENTIFY PARAMETERS:
1. Look for Greek letters with values: "β = 0.5", "α = 0.1", "γ = 0.2"
2. Look for parameter definitions: "transmission rate β", "recovery rate γ"
3. Look for parameter tables: "Table of Parameters" or parameter lists
4. Look for rate definitions: "rate = 0.3", "contact rate = 0.5"
5. Look for probability definitions: "probability p = 0.8"
6. Check equations for parameter symbols: "dS/dt = -βSI"
7. Look for parameter descriptions in text
8. Check for parameter values in model setup sections

REQUIRED JSON STRUCTURE:
Each parameter object MUST have these exact fields:
{{
  "name": "ParameterSymbol",
  "value": "numerical_value_if_specified",
  "unit": "unit_if_specified",
  "description": "Brief description from paper",
  "text_span": "Exact quote showing where parameter is defined (include full context)"
}}

EVIDENCE RULE - CRITICAL:
- Only include a parameter if you find clear, direct evidence (exact quote or table row) in the paper text below.
- If you cannot find a supporting quote or definition for a parameter, do NOT include it. Prefer omitting over inventing.
- Do not infer or assume parameters that are not explicitly stated or defined in the text or tables.
- Reject any parameter for which there is not enough evidence.

REASON STEP BY STEP: First list each parameter you find with its definition or table row; then output the JSON array. This reduces missed or invented parameters.

EXTRACTION RULES - BE THOROUGH BUT EVIDENCE-BASED:
- Extract parameters that are clearly defined or listed; do not add parameters that are only implied or guessed
- Focus on Greek letters (α, β, γ, δ, μ, ρ, σ, θ, λ, etc.) representing rates when they appear in the text/tables
- Include Latin letter parameters (R, N, etc.) only if the paper explicitly defines them as model constants
- Include exact text quotes or table references for text_span; if no quote, omit the parameter
- If value is not specified, use null or omit value field

COMMON PARAMETER TYPES:
- Transmission rate: β, beta, contact_rate
- Recovery rate: γ, gamma, recovery_rate
- Death rate: μ, mu, death_rate, mortality_rate
- Incubation rate: σ, sigma, incubation_rate
- Birth rate: λ, lambda, birth_rate
- Contact rate: c, contact_rate
- Probability: p, probability

{separator}
PAPER TEXT:
{truncated_text}
{separator}
Return ONLY valid JSON array starting with '[' and ending with ']'. Extract ALL parameters mentioned in the paper."""
        else:
            # Concise prompt for OpenAI
            prompt = f"""{separator}
TASK: Extract model parameters from this epidemiological modeling paper.
{separator}
OUTPUT FORMAT: Return ONLY a valid JSON array. No markdown, no explanations. Start with '[' and end with ']'.

{paper_type_context}{metamodel_context}{examples_context}
{separator}
EVIDENCE RULE: Only include a parameter if you find clear, direct evidence (exact quote or table) in the paper. If you cannot find a supporting quote, do NOT include it. Prefer false negatives over inventing.

REASON STEP BY STEP: First list each parameter with its quote or table reference; then output the JSON array.

EXTRACTION INSTRUCTIONS:
Identify parameters by looking for:
- Greek letters with values ("β = 0.5", "α = 0.1")
- Parameter definitions ("transmission rate β", "recovery rate γ")
- Parameter tables or lists
- Rate definitions ("rate = 0.3", "contact rate = 0.5")
- Parameter symbols in equations ("dS/dt = -βSI")
Only output parameters for which you have a clear text_span quote or table reference. Reject if no evidence.

REQUIRED JSON STRUCTURE:
Each object must have: "name", "value", "unit", "description", "text_span"
{{
  "name": "ParameterSymbol",
  "value": "numerical_value_if_specified",
  "unit": "unit_if_specified",
  "description": "Brief description",
  "text_span": "Exact quote from paper (include context)"
}}

EXTRACT parameters that have clear evidence. Include exact text quotes. Reject if no evidence.

{separator}
PAPER TEXT:
{truncated_text}
{separator}
Return ONLY valid JSON array: [{{"name": "...", "value": "...", "unit": "...", "description": "...", "text_span": "..."}}, ...]"""

        try:
            # Use higher max_tokens for parameters (text_span can be very long with full context)
            # When supported (Gemini), use structured output + lower temperature for more reliable extraction
            kwargs = {"max_tokens": 8000}
            if self.llm_client.provider == "gemini":
                kwargs["response_schema"] = GEMINI_PARAMETER_SCHEMA
                kwargs["temperature"] = 0.2
            result = self.llm_client.extract_with_llm(prompt, **kwargs)
            if isinstance(result, list):
                params = []
                for item in result:
                    param_name = item.get("name", "").strip()
                    if param_name:
                        params.append(
                            {
                                "raw_text": item.get("text_span", param_name),
                                "normalized_name": param_name,
                                "value": item.get("value"),
                                "unit": item.get("unit"),
                                "description": item.get("description"),
                                "page_number": 0,
                                "text_span": item.get("text_span", ""),
                                "extraction_method": "llm",
                                "confidence": "high",
                                "paper_backed": True,
                            }
                        )
                return params
        except Exception as e:
            print(f"Warning: LLM parameter extraction failed: {e}")

        return []

    def extract_stratifications(
        self, paper_text: str, pages_data: List[Dict]
    ) -> List[Dict[str, Any]]:
        """Extract stratifications with evidence"""
        stratifications = []

        # Common stratification patterns
        patterns = [
            r"age[-\s]?stratified|stratified\s+by\s+age",
            r"gender|sex[-\s]?stratified",
            r"risk[-\s]?stratified|stratified\s+by\s+risk",
            r"location[-\s]?stratified",
        ]

        for page_data in pages_data:
            page_num = page_data.get("page_number", 0)
            text = page_data.get("text", "").lower()

            if "age" in text and ("stratified" in text or "group" in text):
                stratifications.append(
                    {
                        "dimension": "age",
                        "page_number": page_num,
                        "text_span": "age stratification mentioned",
                        "extraction_method": "pattern",
                        "confidence": "medium",
                        "paper_backed": True,
                    }
                )

            if "gender" in text or "sex" in text:
                stratifications.append(
                    {
                        "dimension": "gender",
                        "page_number": page_num,
                        "text_span": "gender stratification mentioned",
                        "extraction_method": "pattern",
                        "confidence": "medium",
                        "paper_backed": True,
                    }
                )

        # Remove duplicates
        seen = set()
        unique_strats = []
        for strat in stratifications:
            key = strat["dimension"]
            if key not in seen:
                seen.add(key)
                unique_strats.append(strat)

        return unique_strats

    def extract_interventions(
        self, paper_text: str, pages_data: List[Dict]
    ) -> List[Dict[str, Any]]:
        """Extract interventions with evidence"""
        interventions = []
        seen = set()

        intervention_keywords = {
            "vaccination": ["vaccination", "vaccine", "vaccinated"],
            "treatment": ["treatment", "treated", "therapy"],
            "quarantine": ["quarantine", "quarantined", "isolation"],
            "contact_tracing": ["contact tracing", "contact-tracing"],
        }

        for page_data in pages_data:
            page_num = page_data.get("page_number", 0)
            text = page_data.get("text", "").lower()

            for intervention_type, keywords in intervention_keywords.items():
                if any(keyword in text for keyword in keywords):
                    if intervention_type not in seen:
                        seen.add(intervention_type)
                        interventions.append(
                            {
                                "type": intervention_type,
                                "page_number": page_num,
                                "text_span": f"{intervention_type} mentioned",
                                "extraction_method": "pattern",
                                "confidence": "medium",
                                "paper_backed": True,
                            }
                        )

        return interventions

    # ------------------------------------------------------------------ #
    #  Unified single-pass extraction (preferred when LLM is available)   #
    # ------------------------------------------------------------------ #

    def _extract_all_unified_llm(
        self, paper_text: str, pages_data: List[Dict], tables: List[Dict]
    ) -> Optional[tuple]:
        """
        Extract compartments, flows, and parameters in ONE LLM call.

        Returns (compartments, flows, parameters) lists on success, or None
        so the caller can fall back to separate extraction.
        """
        # ---- build context window ----
        max_chars = max(
            self.llm_compartments_chars, self.llm_flows_chars, self.llm_parameters_chars
        )
        try:
            window = build_text_window(
                pages_data,
                include_patterns=[
                    r"\bcompartment|\bstate\b|\bgroup\b|\bclass\b",
                    r"\bS\s*\(t\)|\bE\s*\(t\)|\bI\s*\(t\)|\bR\s*\(t\)",
                    r"d[a-z]\s*/\s*dt",
                    r"\bequation|\bflow|\btransition|\bmodel\b",
                    r"\bparameter|\brate\b|\bvalue\b|\btable\b",
                    r"\bfigure\b|\bdiagram\b",
                ],
                title="PAPER TEXT (model-relevant sections)",
                max_chars=max_chars,
                pad=2,
                max_pages=14,
                fallback_first_pages=6,
            )
        except Exception:
            window = paper_text[:max_chars]

        # ---- optional tables ----
        tables_text = ""
        if tables:
            try:
                tables_text = format_tables_for_prompt(
                    tables, title="TABLES FROM PAPER", max_tables=5, max_rows=20
                )
            except Exception:
                pass

        # ---- paper-type hint ----
        paper_type_hint = ""
        if self.paper_type.get("vector_borne"):
            paper_type_hint = (
                "\nNote: This paper describes a VECTOR-BORNE disease model. "
                "Look for both human AND vector/mosquito compartments and flows.\n"
            )

        # ---- prompt ----
        prompt = f"""You are an expert epidemiological modeler.
From the paper text below, extract the PRIMARY compartmental model as presented
in the paper's model diagram, flow chart, or system of differential equations.

Return a single JSON object:
{{
  "compartments": [{{"name": "...", "description": "..."}}],
  "flows": [{{"source": "...", "target": "...", "type": "RateFlow or ContactFlow", "description": "..."}}],
  "parameters": [{{"name": "...", "value": "...", "unit": "...", "description": "..."}}]
}}
{paper_type_hint}
Rules:
- Compartments: Extract the compartments at the same level of abstraction as the
  paper's model diagram or differential equations. Use the standard epidemiological names
  (e.g. "Susceptible", "Exposed", "Infectious", "Recovered").
  Use full descriptive names — NOT single-letter abbreviations (S, E, I, R),
  subscripted symbols (S_h, I_v), or numbered variants (E1, I2).
  If the model has multiple population groups (humans/vectors, children/adults),
  include compartments for EACH group (e.g. "Susceptible Humans", "Infectious Mosquitoes").
  If the paper groups sub-states into one compartment (e.g. both symptomatic and
  asymptomatic into "Infectious"), use the aggregated name from the model diagram.
- Flows: Every transition arrow in the model.
  source and target MUST exactly match compartment names from your list above.
  ContactFlow = transmission/infection (involves contact between groups).
  RateFlow = all other transitions (recovery, death, vaccination, progression, etc.).
- Parameters: Model parameters with their symbols, values, and units when available.
  Include parameters from tables, equations, and text.
- Extract ONLY what the paper explicitly presents in its model. Do not invent entities
  or split compartments beyond what the paper's model structure shows.

PAPER TEXT:
{window}

{tables_text}

Return ONLY valid JSON."""

        try:
            # Use temperature=0 for deterministic output and high max_tokens to avoid truncation
            kwargs = {"max_tokens": 16000, "temperature": 0}
            result = self.llm_client.extract_with_llm(prompt, **kwargs)

            if not isinstance(result, dict) or "compartments" not in result:
                if isinstance(result, dict) and "error" in result:
                    print(f"  ⚠ Unified extraction LLM error: {result.get('error')}")
                else:
                    print(
                        f"  ⚠ Unified extraction: unexpected response type ({type(result).__name__})"
                    )
                return None

            # ---- parse compartments ----
            compartments = []
            for item in result.get("compartments", []):
                name = (item.get("name") or "").strip()
                if name:
                    compartments.append(
                        {
                            "normalized_name": self._normalize_compartment_name(name),
                            "raw_text": name,
                            "page_number": 0,
                            "text_span": item.get("description", ""),
                            "extraction_method": "llm",
                            "confidence": "high",
                            "paper_backed": True,
                            "description": item.get("description", ""),
                        }
                    )

            # ---- parse flows ----
            flows = []
            for item in result.get("flows", []):
                source = (item.get("source") or "").strip()
                target = (item.get("target") or "").strip()
                if source and target:
                    ft = (item.get("type") or "RateFlow").strip()
                    if ft.lower() in ("contactflow", "contact"):
                        ft = "ContactFlow"
                    else:
                        ft = "RateFlow"
                    flows.append(
                        {
                            "source": source,
                            "target": target,
                            "flow_type": ft,
                            "raw_text": item.get("description", ""),
                            "page_number": 0,
                            "text_span": item.get("description", ""),
                            "extraction_method": "llm",
                            "confidence": "high",
                            "paper_backed": True,
                            "description": item.get("description", ""),
                        }
                    )

            # ---- parse parameters ----
            parameters = []
            for item in result.get("parameters", []):
                name = (item.get("name") or "").strip()
                if name:
                    parameters.append(
                        {
                            "normalized_name": name,
                            "raw_text": name,
                            "value": item.get("value"),
                            "unit": item.get("unit"),
                            "page_number": 0,
                            "text_span": item.get("description", ""),
                            "extraction_method": "llm",
                            "confidence": "high",
                            "paper_backed": True,
                            "description": item.get("description", ""),
                        }
                    )

            if not compartments:
                print("  ⚠ Unified extraction returned 0 compartments – falling back")
                return None

            print(
                f"  ✓ Unified extraction: {len(compartments)} compartments, "
                f"{len(flows)} flows, {len(parameters)} parameters"
            )

            # ---- Quality check: retry once if extraction looks incomplete ----
            # Truncation or early stopping can cause missing flows/parameters
            incomplete = (len(flows) < 2 and len(compartments) >= 3) or (
                len(parameters) < 2 and len(compartments) >= 3
            )
            if incomplete:
                print(
                    f"  ⚠ Extraction may be truncated ({len(flows)} flows, "
                    f"{len(parameters)} params). Retrying with extended output..."
                )
                try:
                    retry_kwargs = {"max_tokens": 32000, "temperature": 0}
                    result2 = self.llm_client.extract_with_llm(prompt, **retry_kwargs)
                    if isinstance(result2, dict) and "compartments" in result2:
                        c2 = [
                            i
                            for i in result2.get("compartments", [])
                            if (i.get("name") or "").strip()
                        ]
                        f2 = [
                            i
                            for i in result2.get("flows", [])
                            if (i.get("source") or "").strip()
                            and (i.get("target") or "").strip()
                        ]
                        p2 = [
                            i
                            for i in result2.get("parameters", [])
                            if (i.get("name") or "").strip()
                        ]
                        total2 = len(c2) + len(f2) + len(p2)
                        total1 = len(compartments) + len(flows) + len(parameters)
                        if total2 > total1:
                            print(
                                f"  ✓ Retry produced more entities ({total2} vs {total1}), using retry result"
                            )
                            # Re-parse from result2
                            compartments = [
                                {
                                    "normalized_name": self._normalize_compartment_name(
                                        (i.get("name") or "").strip()
                                    ),
                                    "raw_text": (i.get("name") or "").strip(),
                                    "page_number": 0,
                                    "text_span": i.get("description", ""),
                                    "extraction_method": "llm",
                                    "confidence": "high",
                                    "paper_backed": True,
                                    "description": i.get("description", ""),
                                }
                                for i in c2
                            ]
                            flows = [
                                {
                                    "source": (i.get("source") or "").strip(),
                                    "target": (i.get("target") or "").strip(),
                                    "flow_type": "ContactFlow"
                                    if (i.get("type") or "").lower()
                                    in ("contactflow", "contact")
                                    else "RateFlow",
                                    "raw_text": i.get("description", ""),
                                    "page_number": 0,
                                    "text_span": i.get("description", ""),
                                    "extraction_method": "llm",
                                    "confidence": "high",
                                    "paper_backed": True,
                                    "description": i.get("description", ""),
                                }
                                for i in f2
                            ]
                            parameters = [
                                {
                                    "normalized_name": (i.get("name") or "").strip(),
                                    "raw_text": (i.get("name") or "").strip(),
                                    "value": i.get("value"),
                                    "unit": i.get("unit"),
                                    "page_number": 0,
                                    "text_span": i.get("description", ""),
                                    "extraction_method": "llm",
                                    "confidence": "high",
                                    "paper_backed": True,
                                    "description": i.get("description", ""),
                                }
                                for i in p2
                            ]
                            print(
                                f"  ✓ After retry: {len(compartments)} compartments, "
                                f"{len(flows)} flows, {len(parameters)} parameters"
                            )
                        else:
                            print(
                                f"  ✓ Retry did not improve ({total2} vs {total1}), keeping original"
                            )
                except Exception as e2:
                    print(f"  ⚠ Retry failed: {e2}, keeping original result")

            return compartments, flows, parameters

        except Exception as e:
            print(f"  ⚠ Unified extraction failed: {e}")
            return None

    # ------------------------------------------------------------------ #
    #  Main entry point                                                   #
    # ------------------------------------------------------------------ #

    def extract_all(
        self, pdf_data: Dict[str, Any], paper_promises: Optional[Dict[str, Any]] = None
    ) -> Dict[str, Any]:
        """
        Extract all entities from PDF data.

        Uses a single unified LLM call when the LLM is available.
        Falls back to separate per-entity-type extraction (pattern + LLM)
        when the unified call fails or when no LLM is configured.

        Args:
            pdf_data: Output from PDFPipeline.process_pdf()
            paper_promises: Optional Step 2 promises (only used in fallback path).

        Returns:
            Dictionary with all extracted entities
        """
        paper_text = pdf_data.get("full_text", "")
        pages_data = pdf_data.get("raw_pages") or pdf_data.get("pages") or []
        tables = pdf_data.get("tables", [])

        compartments = flows = parameters = None

        # ---- Try unified extraction first (one LLM call) ----
        if self.llm_client.is_available():
            unified = self._extract_all_unified_llm(paper_text, pages_data, tables)
            if unified:
                compartments, flows, parameters = unified

        # ---- Fallback: separate extraction (3 calls or pattern-only) ----
        if compartments is None:
            print("  Using separate extraction (fallback)...")
            compartments = self.extract_compartments(
                paper_text, pages_data, paper_promises=paper_promises
            )
            flows = self.extract_flows(paper_text, pages_data, compartments)
            parameters = self.extract_parameters(paper_text, pages_data, tables)

        # ---- Post-processing (lightweight, always applied) ----
        flows = self._clean_and_validate_flows(flows, compartments)
        parameters = self._clean_and_filter_parameters(parameters)

        # Stratifications & interventions (pattern-only, no LLM)
        stratifications = self.extract_stratifications(paper_text, pages_data)
        interventions = self.extract_interventions(paper_text, pages_data)

        return {
            "compartments": compartments,
            "flows": flows,
            "parameters": parameters,
            "stratifications": stratifications,
            "interventions": interventions,
            "extraction_summary": {
                "num_compartments": len(compartments),
                "num_flows": len(flows),
                "num_parameters": len(parameters),
                "num_stratifications": len(stratifications),
                "num_interventions": len(interventions),
            },
        }

    def save_entities(self, entities: Dict[str, Any], output_path: str):
        """Save extracted entities to JSON file"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)

        with open(output_path, "w") as f:
            json.dump(entities, f, indent=2)
