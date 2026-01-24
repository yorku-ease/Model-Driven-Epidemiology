"""Extract detailed entities (compartments, flows, parameters) with evidence

Extracts compartments, flows, parameters, stratifications, and interventions
from paper text with evidence (text span, page number, confidence).
"""

import json
import re
from pathlib import Path
from typing import Dict, List, Any, Optional
from src.utils.llm_client import LLMClient


class EntityExtractor:
    """Extract model entities from paper text with evidence"""
    
    def __init__(self, llm_client: Optional[LLMClient] = None, metamodel_path: Optional[str] = None,
                 example_models_path: Optional[str] = None):
        """
        Initialize entity extractor.

        Args:
            llm_client: LLM client instance
            metamodel_path: Path to epidemiology metamodel JSON
            example_models_path: Path to directory with Phase 1 example .compmodel files
        """
        self.llm_client = llm_client or LLMClient()
        self.metamodel = None
        if metamodel_path:
            self._load_metamodel(metamodel_path)

        self.example_models = []
        if example_models_path:
            self._load_example_models(example_models_path)
    
    def _load_metamodel(self, metamodel_path: str):
        """Load metamodel for normalization"""
        try:
            with open(metamodel_path, 'r') as f:
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
                with open(compmodel_file, 'r', encoding='utf-8') as f:
                    content = f.read()
                    # Parse XML to extract compartments, parameters, and flows as examples
                    tree = ET.fromstring(content)

                    # Extract compartments
                    compartments = []
                    for comp in tree.findall('.//{http://example.com/compartmentalmodel}compartments'):
                        compartments.append({
                            'name': comp.get('PrimaryName', ''),
                            'population': comp.get('population', '0')
                        })

                    # Extract parameters
                    parameters = []
                    for param in tree.findall('.//{http://example.com/compartmentalmodel}parameters'):
                        parameters.append({
                            'name': param.get('name', ''),
                            'value': param.get('expression', ''),
                            'description': param.get('description', '')
                        })

                    self.example_models.append({
                        'name': compmodel_file.stem,
                        'compartments': compartments,
                        'parameters': parameters
                    })
            except Exception as e:
                print(f"Warning: Failed to load example {compmodel_file}: {e}")

    def _normalize_compartment_name(self, name: str) -> str:
        """Normalize compartment name to canonical form"""
        name = name.strip()
        
        # Common normalizations
        normalizations = {
            'infected': 'Infectious',
            'exposed': 'Exposed',
            'susceptible': 'Susceptible',
            'recovered': 'Recovered',
            'removed': 'Recovered',
            'dead': 'Dead',
            'deceased': 'Dead',
            'hospitalized': 'Hospitalized',
            'quarantined': 'Quarantined',
            'vaccinated': 'Vaccinated',
            'treated': 'Treated'
        }
        
        name_lower = name.lower()
        if name_lower in normalizations:
            return normalizations[name_lower]
        
        # Capitalize first letter
        return name[0].upper() + name[1:] if name else name
    
    def extract_compartments(self, paper_text: str, pages_data: List[Dict]) -> List[Dict[str, Any]]:
        """
        Extract compartments with evidence.
        
        Returns:
            List of compartment entities with evidence
        """
        compartments = []
        seen = set()
        
        # Pattern-based extraction
        patterns = [
            r'(?:compartment|state|class)\s+([A-Z])\s*(?:\([^)]+\))?\s*(?:represents?|denotes?|is)\s+([^.]+)',
            r'([A-Z])\(t\)\s*(?:represents?|denotes?|is)\s+([^.]+)',
            r'(?:we\s+model|model\s+includes?)\s+([^.]+)',
            r'(?:Susceptible|Exposed|Infectious|Recovered|Dead|Hospitalized|Quarantined|Vaccinated|Treated)',
        ]
        
        for page_data in pages_data:
            page_num = page_data.get('page_number', 0)
            text = page_data.get('text', '')
            
            for pattern in patterns:
                matches = re.finditer(pattern, text, re.IGNORECASE)
                for match in matches:
                    if len(match.groups()) >= 1:
                        comp_name = match.group(1) if match.groups() else match.group(0)
                        comp_name = self._normalize_compartment_name(comp_name)
                        
                        if comp_name and comp_name not in seen:
                            seen.add(comp_name)
                            compartments.append({
                                "raw_text": match.group(0),
                                "normalized_name": comp_name,
                                "page_number": page_num,
                                "text_span": match.group(0),
                                "extraction_method": "pattern",
                                "confidence": "medium",
                                "paper_backed": True
                            })
        
        # LLM-based extraction if available
        if self.llm_client.is_available():
            llm_compartments = self._extract_compartments_llm(paper_text)
            for comp in llm_compartments:
                if comp['normalized_name'] not in seen:
                    seen.add(comp['normalized_name'])
                    compartments.append(comp)
        
        return compartments
    
    def _extract_compartments_llm(self, paper_text: str) -> List[Dict[str, Any]]:
        """Extract compartments using LLM with metamodel and Phase 1 examples"""
        # Use more text for compartments - they're usually defined early in the paper
        truncated_text = paper_text[:50000]  # Increased limit for better extraction
        
        # Provider-specific prompt optimization: OpenAI works better with concise prompts,
        # while Gemini benefits from detailed instructions
        use_detailed_prompt = self.llm_client.provider == "gemini"

        # Build metamodel context
        metamodel_context = ""
        if self.metamodel:
            compartment_types = self.metamodel.get('epimde_compartmental_metamodel', {}).get('compartment_types', [])
            if compartment_types:
                metamodel_context = f"""
Standard compartment types from metamodel:
{', '.join(compartment_types)}
"""

        # Build Phase 1 examples context
        examples_context = ""
        if self.example_models:
            examples_list = []
            for model in self.example_models[:3]:  # Use up to 3 examples
                comp_names = [c['name'] for c in model.get('compartments', []) if c['name']]
                if comp_names:
                    examples_list.append(f"  {model['name']}: {', '.join(comp_names[:6])}")

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

{metamodel_context}{examples_context}
{separator}
YOUR TASK:
You are an expert in epidemiological compartmental modeling. Your job is to identify EVERY compartment mentioned in this paper. Missing even one compartment will cause the model to be incomplete and incorrect.

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

EXTRACTION RULES - BE THOROUGH AND SYSTEMATIC:
- Extract ALL compartments mentioned, even if briefly or only mentioned once
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
- If unsure whether something is a compartment, include it (better to over-extract than miss)
- Count compartments: If paper says "5 compartments" or "SEIR model", ensure you extract exactly that many
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

{metamodel_context}{examples_context}
{separator}
EXTRACTION INSTRUCTIONS:
Identify all compartments by looking for:
- Compartment definitions ("compartment X", "state X", "group X")
- State variables in equations ("S(t)", "I(t)", "dS/dt", "dI/dt")
- Model descriptions ("the model includes compartments: X, Y, Z")
- Compartment lists in tables
- Initial conditions ("S(0) = ...", "I(0) = ...")

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
            result = self.llm_client.extract_with_llm(prompt, max_tokens=4000)
            if isinstance(result, list):
                return [
                    {
                        "raw_text": item.get('text_span', ''),
                        "normalized_name": self._normalize_compartment_name(item.get('name', '')),
                        "page_number": 0,  # LLM doesn't know page numbers
                        "text_span": item.get('text_span', ''),
                        "extraction_method": "llm",
                        "confidence": "high",
                        "paper_backed": True,
                        "description": item.get('description', '')
                    }
                    for item in result
                ]
        except Exception as e:
            print(f"Warning: LLM compartment extraction failed: {e}")
        
        return []
    
    def extract_flows(self, paper_text: str, pages_data: List[Dict], 
                     compartments: List[Dict]) -> List[Dict[str, Any]]:
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
            comp_name = comp['normalized_name']
            # Try to extract letter from text span (e.g., "Susceptible (S)")
            text_span = comp.get('text_span', '')
            letter_match = re.search(r'\(([A-Z])\)', text_span)
            if letter_match:
                letter = letter_match.group(1).upper()
                comp_letter_map[letter] = comp_name
        
        # Also map by first letter as fallback
        for comp in compartments:
            comp_name = comp['normalized_name']
            first_letter = comp_name[0].upper()
            if first_letter not in comp_letter_map:
                comp_letter_map[first_letter] = comp_name
        
        # Enhanced pattern-based extraction
        flow_patterns = [
            # Arrow notation
            (r'([A-Z])\s*→\s*([A-Z])', 'arrow'),
            (r'([A-Z])\(t\)\s*→\s*([A-Z])\(t\)', 'arrow_function'),
            # Differential equations
            (r'd([A-Z])/dt\s*=\s*[^=]*[+\-]\s*[^=]*([A-Z])', 'differential'),
            (r'd([A-Z])/dt\s*=\s*[^=]*β[^=]*([A-Z])', 'contact_flow'),
            # Text descriptions
            (r'([A-Z])\s+(?:progresses?|transitions?|moves?|flows?)\s+to\s+([A-Z])', 'text'),
            (r'from\s+([A-Z])\s+to\s+([A-Z])', 'text'),
            (r'([A-Z])\s+→\s+([A-Z])\s+(?:at|with|rate)', 'text_rate'),
            # Compartment names directly
            (r'(Susceptible|Exposed|Infectious|Recovered|Dead|Deceased)\s+(?:→|to|progresses?)\s+(Susceptible|Exposed|Infectious|Recovered|Dead|Deceased)', 'name'),
        ]
        
        for page_data in pages_data:
            page_num = page_data.get('page_number', 0)
            text = page_data.get('text', '')
            
            for pattern, pattern_type in flow_patterns:
                matches = re.finditer(pattern, text, re.IGNORECASE)
                for match in matches:
                    if len(match.groups()) >= 2:
                        source_key = match.group(1)
                        target_key = match.group(2)
                        
                        # Map to compartment names
                        source_comp = comp_letter_map.get(source_key.upper()) or \
                                     next((c['normalized_name'] for c in compartments 
                                          if source_key.lower() in c['normalized_name'].lower()), None)
                        target_comp = comp_letter_map.get(target_key.upper()) or \
                                     next((c['normalized_name'] for c in compartments 
                                          if target_key.lower() in c['normalized_name'].lower()), None)
                        
                        if source_comp and target_comp:
                            flow_key = f"{source_comp}->{target_comp}"
                            if flow_key not in seen_flows:
                                seen_flows.add(flow_key)
                                
                                # Determine flow type
                                flow_type = "ContactFlow" if 'contact' in match.group(0).lower() or 'β' in match.group(0) else "RateFlow"
                                
                                flows.append({
                                    "source": source_comp,
                                    "target": target_comp,
                                    "raw_text": match.group(0),
                                    "page_number": page_num,
                                    "text_span": match.group(0),
                                    "extraction_method": f"pattern_{pattern_type}",
                                    "confidence": "medium",
                                    "paper_backed": True,
                                    "flow_type": flow_type
                                })
        
        # LLM-based extraction if available (always try to get more flows)
        if self.llm_client.is_available():
            llm_flows = self._extract_flows_llm(paper_text, compartments)
            # Add LLM flows that aren't duplicates
            existing_flow_keys = {f"{f['source']}->{f['target']}" for f in flows}
            for llm_flow in llm_flows:
                flow_key = f"{llm_flow['source']}->{llm_flow['target']}"
                if flow_key not in existing_flow_keys:
                    flows.append(llm_flow)
                    existing_flow_keys.add(flow_key)
        
        return flows
    
    def _extract_flows_llm(self, paper_text: str, compartments: List[Dict]) -> List[Dict[str, Any]]:
        """Extract flows using LLM with metamodel and Phase 1 examples"""
        truncated_text = paper_text[:40000]  # Limit for prompt
        
        # Provider-specific prompt optimization
        use_detailed_prompt = self.llm_client.provider == "gemini"

        comp_names = [c['normalized_name'] for c in compartments]
        comp_list = ", ".join(comp_names)

        # Build metamodel context
        metamodel_context = ""
        if self.metamodel:
            flow_types = self.metamodel.get('epimde_compartmental_metamodel', {}).get('flow_types', [])
            if flow_types:
                metamodel_context = f"""
Flow types from metamodel:
{', '.join(flow_types)}
"""

        # Build Phase 1 examples context for flows
        examples_context = ""
        if self.example_models:
            examples_list = []
            for model in self.example_models[:2]:  # Use up to 2 examples
                comp_names_ex = [c['name'] for c in model.get('compartments', []) if c['name']]
                if comp_names_ex:
                    # Show example flow patterns
                    flow_pattern = " → ".join(comp_names_ex[:4])
                    examples_list.append(f"  {model['name']}: {flow_pattern}")

            if examples_list:
                examples_context = f"""
Example flow patterns from Phase 1 models:
{chr(10).join(examples_list)}
"""

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
{metamodel_context}{examples_context}
{separator}
YOUR TASK:
You are an expert in epidemiological compartmental modeling. Extract EVERY flow (transition) between compartments mentioned in this paper.

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

FLOW TYPE RULES:
- RateFlow: progression (E→I), recovery (I→R), death (I→D), treatment (I→T)
- ContactFlow: transmission/infection (S→E, S→I) involving contact between compartments
- Match source/target to available compartments exactly (case-sensitive)

EXTRACTION RULES - BE THOROUGH:
- Extract ALL flows mentioned, even if briefly
- Match compartment names exactly to available compartments: {comp_list}
- Include exact text quotes as evidence
- Be comprehensive - missing flows will cause model errors
- If unsure about flow type, use RateFlow for progression/recovery/death, ContactFlow for transmission

{separator}
PAPER TEXT:
{truncated_text}
{separator}
Return ONLY valid JSON array starting with '[' and ending with ']'. Extract ALL flows mentioned in the paper."""
        else:
            # Concise prompt for OpenAI
            prompt = f"""{separator}
TASK: Extract flows (transitions) between compartments from this epidemiological modeling paper.
{separator}
OUTPUT FORMAT: Return ONLY a valid JSON array. No markdown, no explanations. Start with '[' and end with ']'.

AVAILABLE COMPARTMENTS: {comp_list}
{metamodel_context}{examples_context}
{separator}
EXTRACTION INSTRUCTIONS:
Identify all flows by looking for:
- Flow descriptions ("from X to Y", "X → Y", "X transitions to Y")
- Differential equations ("dX/dt = ... + Y" indicates flow from Y to X)
- Transmission flows ("Susceptible becomes Infectious")
- Progression/recovery/death flows

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

{separator}
PAPER TEXT:
{truncated_text}
{separator}
Return ONLY valid JSON array: [{{"source": "...", "target": "...", "description": "...", "text_span": "...", "flow_type": "..."}}, ...]"""
        
        try:
            # Use higher max_tokens for flows (text_span can be long)
            result = self.llm_client.extract_with_llm(prompt, max_tokens=4000)
            if isinstance(result, list):
                flows = []
                unmatched = []
                for item in result:
                    source = item.get('source', '').strip()
                    target = item.get('target', '').strip()
                    
                    if not source or not target:
                        unmatched.append(f"Missing source/target: {item}")
                        continue
                    
                    # Try exact match first (case-insensitive)
                    source_comp = None
                    target_comp = None
                    
                    for c in compartments:
                        comp_name = c['normalized_name']
                        # Exact match (case-insensitive)
                        if source.lower() == comp_name.lower():
                            source_comp = comp_name
                        if target.lower() == comp_name.lower():
                            target_comp = comp_name
                    
                    # If no exact match, try substring match (more lenient)
                    if not source_comp:
                        source_comp = next((c['normalized_name'] for c in compartments 
                                          if source.lower() in c['normalized_name'].lower() or 
                                             c['normalized_name'].lower() in source.lower()), None)
                    if not target_comp:
                        target_comp = next((c['normalized_name'] for c in compartments 
                                          if target.lower() in c['normalized_name'].lower() or 
                                             c['normalized_name'].lower() in target.lower()), None)
                    
                    if source_comp and target_comp:
                        flows.append({
                            "source": source_comp,
                            "target": target_comp,
                            "raw_text": item.get('text_span', ''),
                            "page_number": 0,  # LLM doesn't know page numbers
                            "text_span": item.get('text_span', ''),
                            "extraction_method": "llm",
                            "confidence": "high",
                            "paper_backed": True,
                            "flow_type": item.get('flow_type', 'RateFlow'),
                            "description": item.get('description', '')
                        })
                    else:
                        unmatched.append(f"{source}->{target} (source_match={source_comp is not None}, target_match={target_comp is not None})")
                
                if flows:
                    print(f"  ✓ LLM extracted {len(flows)} flows")
                else:
                    print(f"  ⚠ LLM returned {len(result)} items but none matched compartments")
                    if unmatched:
                        print(f"  Unmatched flows: {unmatched[:3]}")  # Show first 3
                return flows
            elif isinstance(result, dict):
                # Check if it's an error response
                if 'error' in result:
                    print(f"  ⚠ LLM flow extraction error: {result.get('error')}")
                    if 'raw_response' in result:
                        print(f"  Raw response preview: {result['raw_response'][:200]}...")
                else:
                    print(f"  ⚠ LLM returned dict instead of array. Keys: {list(result.keys())}")
            else:
                print(f"  ⚠ LLM returned unexpected type: {type(result)}")
        except Exception as e:
            print(f"  ⚠ Warning: LLM flow extraction failed: {e}")
            import traceback
            traceback.print_exc()
        
        return []
    
    def extract_parameters(self, paper_text: str, pages_data: List[Dict],
                          tables: List[Dict]) -> List[Dict[str, Any]]:
        """
        Extract parameters with evidence.

        Returns:
            List of parameter entities with evidence
        """
        parameters = []
        seen = set()

        # Common Greek letters used in epidemiology
        valid_greek_params = {'α', 'β', 'γ', 'δ', 'μ', 'ρ', 'σ', 'θ', 'λ', 'ω', 'ν', 'ε', 'η'}

        # Extract from tables first (with strict filtering)
        for table in tables:
            table_data = table.get('data', [])
            if not table_data or len(table_data) < 2:
                continue

            # Check if this looks like a parameter table
            # Parameter tables typically have: symbol/name, value, unit, description
            header_row = [str(cell).strip().lower() for cell in table_data[0] if cell]
            header_text = ' '.join(header_row)

            # Skip tables that clearly aren't parameter definitions
            skip_keywords = ['when', 'r0', 'threshold', 'correlation', 'degree', 'values lie',
                           'horizontal line', 'underneath', 'now found', 'below']
            if any(keyword in header_text for keyword in skip_keywords):
                continue

            # Check if this looks like a parameter table (has "parameter", "value", "description" etc.)
            is_param_table = any(keyword in header_text for keyword in
                                ['parameter', 'symbol', 'value', 'description', 'definition', 'notation'])

            if not is_param_table:
                # Try to detect by structure: first column has short symbols, second has numbers
                first_col_sample = [str(row[0]).strip() for row in table_data[1:3] if row and row[0]]
                if not first_col_sample or all(len(s) > 10 or ' ' in s for s in first_col_sample):
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
                elif 1 <= len(param_name) <= 10 and any(c in valid_greek_params for c in param_name):
                    is_valid_param = True
                # Check for word-based names (letters, underscores, spaces, max 30 chars)
                elif len(param_name) <= 30 and re.match(r'^[a-zA-Z][a-zA-Z0-9_\s\-]*$', param_name):
                    is_valid_param = True

                if not is_valid_param:
                    continue

                # Validate value (should be numeric or expression)
                if param_value and param_value.lower() not in ['none', 'n/a', 'na', '']:
                    # Check if value is numeric or contains digits
                    if not any(c.isdigit() or c == '.' for c in param_value):
                        continue
                else:
                    param_value = None

                if param_name and param_name not in seen:
                    seen.add(param_name)
                    parameters.append({
                        "raw_text": param_name,
                        "normalized_name": param_name,
                        "value": param_value,
                        "unit": param_unit if param_unit and param_unit.lower() not in ['none', 'n/a', ''] else None,
                        "description": param_desc if param_desc and param_desc.lower() not in ['none', 'n/a', ''] else None,
                        "page_number": table.get('page_number', 0),
                        "text_span": f"Table {table.get('table_number', 0)}, Row {row_idx + 1}",
                        "extraction_method": "table",
                        "confidence": "high",
                        "paper_backed": True
                    })

        # Extract from text (parameter definitions) - focusing on explicit definitions
        param_patterns = [
            # Greek letter with value: β = 0.75
            (r'([αβγδμρσθλωνεη])\s*[=:]\s*([0-9.]+(?:e[+-]?[0-9]+)?)', 'greek_letter'),
            # Latin letter with value: R = 2.5
            (r'\b([A-Za-z])\s*[=:]\s*([0-9.]+(?:e[+-]?[0-9]+)?)', 'latin_letter'),
            # Word parameter: rate = 0.5, death rate = 0.2
            (r'([a-zA-Z_]+(?:\s+[a-zA-Z_]+)?)\s*[=:]\s*([0-9.]+(?:e[+-]?[0-9]+)?)', 'word'),
        ]

        for page_data in pages_data:
            page_num = page_data.get('page_number', 0)
            text = page_data.get('text', '')

            for pattern, pattern_type in param_patterns:
                matches = re.finditer(pattern, text, re.IGNORECASE)
                for match in matches:
                    if len(match.groups()) >= 2:
                        param_name = match.group(1).strip()
                        param_value = match.group(2).strip()

                        # Additional validation
                        if pattern_type == 'word':
                            # Skip common non-parameter words
                            if param_name.lower() in ['table', 'figure', 'page', 'section', 'equation',
                                                     'year', 'day', 'week', 'month', 'time', 'at', 'is',
                                                     'the', 'and', 'or', 'in', 'of', 'to', 'for', 'with']:
                                continue
                            # Skip if name is too long
                            if len(param_name) > 30:
                                continue

                        # Skip single letter parameters that are likely not parameters (except Greek letters)
                        if pattern_type == 'latin_letter' and len(param_name) == 1:
                            # Skip common non-parameter single letters
                            if param_name.lower() in ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
                                                     'n', 'o', 'p', 'q', 't', 'u', 'v', 'w', 'x', 'y', 'z']:
                                continue  # Only keep I, R, S which are common compartment abbreviations

                        # Skip obvious non-parameters by name
                        if param_name.lower() in ['cid', 'fig', 'table', 'eq', 'ref']:
                            continue

                        if param_name and param_name not in seen:
                            seen.add(param_name)
                            parameters.append({
                                "raw_text": param_name,
                                "normalized_name": param_name,
                                "value": param_value,
                                "unit": None,
                                "description": None,
                                "page_number": page_num,
                                "text_span": match.group(0),
                                "extraction_method": "pattern_text",
                                "confidence": "medium",
                                "paper_backed": True
                            })

        # Use LLM to extract parameters if available
        if self.llm_client.is_available():
            llm_params = self._extract_parameters_llm(paper_text)
            for param in llm_params:
                param_name = param.get('normalized_name', '')
                # Filter out common non-parameters even from LLM
                if param_name and param_name not in seen:
                    # Skip single common letters that are likely not parameters
                    if len(param_name) == 1 and param_name.lower() in ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 't', 'u', 'v', 'w', 'x', 'y', 'z']:
                        continue
                    # Skip obvious non-parameters
                    if param_name.lower() in ['cid', 'fig', 'table', 'eq']:
                        continue
                    seen.add(param_name)
                    parameters.append(param)

        return parameters

    def _extract_parameters_llm(self, paper_text: str) -> List[Dict[str, Any]]:
        """Extract parameters using LLM with metamodel and Phase 1 examples"""
        truncated_text = paper_text[:30000]  # Limit for prompt
        
        # Provider-specific prompt optimization
        use_detailed_prompt = self.llm_client.provider == "gemini"

        # Build metamodel context
        metamodel_context = ""
        if self.metamodel:
            parameter_types = self.metamodel.get('epimde_compartmental_metamodel', {}).get('parameter_types', [])
            if parameter_types:
                metamodel_context = f"""
Parameter types from metamodel:
{', '.join(parameter_types)}
"""

        # Build Phase 1 examples context for parameters
        examples_context = ""
        if self.example_models:
            examples_list = []
            for model in self.example_models[:3]:  # Use up to 3 examples
                params = model.get('parameters', [])
                if params:
                    param_examples = []
                    for p in params[:5]:  # Show up to 5 parameters per model
                        name = p.get('name', '')
                        value = p.get('value', '')
                        desc = p.get('description', '')
                        if name:
                            if value and desc:
                                param_examples.append(f"{name}={value} ({desc})")
                            elif value:
                                param_examples.append(f"{name}={value}")
                            else:
                                param_examples.append(name)
                    if param_examples:
                        examples_list.append(f"  {model['name']}: {'; '.join(param_examples)}")

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

{metamodel_context}{examples_context}
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

EXTRACTION RULES - BE THOROUGH:
- Extract ALL parameters mentioned, even if value is not given
- Focus on Greek letters (α, β, γ, δ, μ, ρ, σ, θ, λ, etc.) representing rates
- Include Latin letter parameters (R, N, etc.) if they represent model constants
- Extract rate parameters: transmission rate, recovery rate, death rate, birth rate
- Include contact rates, probabilities, and other model constants
- Include exact text quotes with full context for text_span
- Be comprehensive - missing parameters will cause model errors
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

{metamodel_context}{examples_context}
{separator}
EXTRACTION INSTRUCTIONS:
Identify all parameters by looking for:
- Greek letters with values ("β = 0.5", "α = 0.1")
- Parameter definitions ("transmission rate β", "recovery rate γ")
- Parameter tables or lists
- Rate definitions ("rate = 0.3", "contact rate = 0.5")
- Parameter symbols in equations ("dS/dt = -βSI")

REQUIRED JSON STRUCTURE:
Each object must have: "name", "value", "unit", "description", "text_span"
{{
  "name": "ParameterSymbol",
  "value": "numerical_value_if_specified",
  "unit": "unit_if_specified",
  "description": "Brief description",
  "text_span": "Exact quote from paper (include context)"
}}

EXTRACT ALL parameters mentioned. Focus on Greek letters (α, β, γ, μ, etc.) and rate parameters. Include exact text quotes.

{separator}
PAPER TEXT:
{truncated_text}
{separator}
Return ONLY valid JSON array: [{{"name": "...", "value": "...", "unit": "...", "description": "...", "text_span": "..."}}, ...]"""

        try:
            # Use higher max_tokens for parameters (text_span can be very long with full context)
            result = self.llm_client.extract_with_llm(prompt, max_tokens=8000)
            if isinstance(result, list):
                params = []
                for item in result:
                    param_name = item.get('name', '').strip()
                    if param_name:
                        params.append({
                            "raw_text": item.get('text_span', param_name),
                            "normalized_name": param_name,
                            "value": item.get('value'),
                            "unit": item.get('unit'),
                            "description": item.get('description'),
                            "page_number": 0,
                            "text_span": item.get('text_span', ''),
                            "extraction_method": "llm",
                            "confidence": "high",
                            "paper_backed": True
                        })
                return params
        except Exception as e:
            print(f"Warning: LLM parameter extraction failed: {e}")

        return []
    
    def extract_stratifications(self, paper_text: str, pages_data: List[Dict]) -> List[Dict[str, Any]]:
        """Extract stratifications with evidence"""
        stratifications = []
        
        # Common stratification patterns
        patterns = [
            r'age[-\s]?stratified|stratified\s+by\s+age',
            r'gender|sex[-\s]?stratified',
            r'risk[-\s]?stratified|stratified\s+by\s+risk',
            r'location[-\s]?stratified',
        ]
        
        for page_data in pages_data:
            page_num = page_data.get('page_number', 0)
            text = page_data.get('text', '').lower()
            
            if 'age' in text and ('stratified' in text or 'group' in text):
                stratifications.append({
                    "dimension": "age",
                    "page_number": page_num,
                    "text_span": "age stratification mentioned",
                    "extraction_method": "pattern",
                    "confidence": "medium",
                    "paper_backed": True
                })
            
            if 'gender' in text or 'sex' in text:
                stratifications.append({
                    "dimension": "gender",
                    "page_number": page_num,
                    "text_span": "gender stratification mentioned",
                    "extraction_method": "pattern",
                    "confidence": "medium",
                    "paper_backed": True
                })
        
        # Remove duplicates
        seen = set()
        unique_strats = []
        for strat in stratifications:
            key = strat['dimension']
            if key not in seen:
                seen.add(key)
                unique_strats.append(strat)
        
        return unique_strats
    
    def extract_interventions(self, paper_text: str, pages_data: List[Dict]) -> List[Dict[str, Any]]:
        """Extract interventions with evidence"""
        interventions = []
        seen = set()
        
        intervention_keywords = {
            'vaccination': ['vaccination', 'vaccine', 'vaccinated'],
            'treatment': ['treatment', 'treated', 'therapy'],
            'quarantine': ['quarantine', 'quarantined', 'isolation'],
            'contact_tracing': ['contact tracing', 'contact-tracing'],
        }
        
        for page_data in pages_data:
            page_num = page_data.get('page_number', 0)
            text = page_data.get('text', '').lower()
            
            for intervention_type, keywords in intervention_keywords.items():
                if any(keyword in text for keyword in keywords):
                    if intervention_type not in seen:
                        seen.add(intervention_type)
                        interventions.append({
                            "type": intervention_type,
                            "page_number": page_num,
                            "text_span": f"{intervention_type} mentioned",
                            "extraction_method": "pattern",
                            "confidence": "medium",
                            "paper_backed": True
                        })
        
        return interventions
    
    def extract_all(self, pdf_data: Dict[str, Any]) -> Dict[str, Any]:
        """
        Extract all entities from PDF data.
        
        Args:
            pdf_data: Output from PDFPipeline.process_pdf()
        
        Returns:
            Dictionary with all extracted entities
        """
        paper_text = pdf_data['full_text']
        pages_data = pdf_data['raw_pages']
        tables = pdf_data['tables']
        
        # Extract in order (compartments needed for flows)
        compartments = self.extract_compartments(paper_text, pages_data)
        flows = self.extract_flows(paper_text, pages_data, compartments)
        parameters = self.extract_parameters(paper_text, pages_data, tables)
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
                "num_interventions": len(interventions)
            }
        }
    
    def save_entities(self, entities: Dict[str, Any], output_path: str):
        """Save extracted entities to JSON file"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        
        with open(output_path, 'w') as f:
            json.dump(entities, f, indent=2)
