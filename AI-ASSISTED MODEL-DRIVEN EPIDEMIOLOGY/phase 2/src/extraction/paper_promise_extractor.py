"""Extract paper promises (scope) from paper text

Identifies what the paper promises to model: compartments, parameters, 
stratifications, interventions, and model type.
"""

import json
import re
from pathlib import Path
from typing import Dict, List, Any, Optional
from src.utils.llm_client import LLMClient


class PaperPromiseExtractor:
    """Extract what a paper promises to model"""
    
    def __init__(self, llm_client: Optional[LLMClient] = None, metamodel_path: Optional[str] = None):
        """
        Initialize extractor.
        
        Args:
            llm_client: LLM client instance (optional)
            metamodel_path: Path to epidemiology metamodel JSON (for LLM prompts)
        """
        self.llm_client = llm_client or LLMClient()
        self.metamodel = None
        if metamodel_path:
            self._load_metamodel(metamodel_path)
    
    def _load_metamodel(self, metamodel_path: str):
        """Load epidemiology metamodel for LLM prompts"""
        try:
            with open(metamodel_path, 'r') as f:
                self.metamodel = json.load(f)
        except Exception as e:
            print(f"Warning: Failed to load metamodel {metamodel_path}: {e}")
    
    def extract_with_patterns(self, paper_text: str) -> Dict[str, Any]:
        """
        Extract promises using pattern matching.
        
        Returns:
            Dictionary with extracted promises
        """
        promises = {
            "compartments": [],
            "stratifications": [],
            "parameters": [],
            "interventions": [],
            "model_type": None,
            "description": "",
            "extraction_method": "pattern"
        }
        
        text_lower = paper_text.lower()
        
        # Extract compartments (common patterns)
        compartment_patterns = [
            r'(?:compartments?|states?|classes?)\s*(?:are|include|consist of|:)\s*([^.]+)',
            r'(?:model\s+includes?|we\s+model)\s+([^.]+)',
            r'(?:S|E|I|R|D|H|Q|V)\s*\([^)]+\)\s*(?:represents?|denotes?|is)\s+([^.]+)',
        ]
        
        for pattern in compartment_patterns:
            matches = re.finditer(pattern, text_lower, re.IGNORECASE)
            for match in matches:
                compartments_text = match.group(1)
                # Extract compartment names
                comps = re.findall(r'\b(S|E|I|R|D|Susceptible|Exposed|Infectious|Recovered|Dead|Hospitalized|Quarantined|Vaccinated)\b', 
                                  compartments_text, re.IGNORECASE)
                promises["compartments"].extend([c.title() for c in comps])
        
        # Extract model type
        if re.search(r'\bSEIR\b', paper_text, re.IGNORECASE):
            promises["model_type"] = "SEIR"
        elif re.search(r'\bSIR\b', paper_text, re.IGNORECASE):
            promises["model_type"] = "SIR"
        elif re.search(r'\bSIS\b', paper_text, re.IGNORECASE):
            promises["model_type"] = "SIS"
        elif re.search(r'vector[-\s]?borne', text_lower):
            promises["model_type"] = "Vector-Borne"
        
        # Extract stratifications
        if re.search(r'age[-\s]?stratified|stratified\s+by\s+age', text_lower):
            promises["stratifications"].append("age")
        if re.search(r'gender|sex[-\s]?stratified', text_lower):
            promises["stratifications"].append("gender")
        if re.search(r'risk[-\s]?stratified|stratified\s+by\s+risk', text_lower):
            promises["stratifications"].append("risk_group")
        
        # Extract interventions
        if re.search(r'\bvaccination\b', text_lower):
            promises["interventions"].append("vaccination")
        if re.search(r'\btreatment\b', text_lower):
            promises["interventions"].append("treatment")
        if re.search(r'\bquarantine\b', text_lower):
            promises["interventions"].append("quarantine")
        
        # Remove duplicates
        promises["compartments"] = list(set(promises["compartments"]))
        promises["stratifications"] = list(set(promises["stratifications"]))
        promises["interventions"] = list(set(promises["interventions"]))
        
        return promises
    
    def extract_with_llm(self, paper_text: str) -> Dict[str, Any]:
        """
        Extract promises using LLM.
        
        Returns:
            Dictionary with extracted promises
        """
        if not self.llm_client.is_available():
            print("LLM not available, falling back to pattern-based extraction")
            return self.extract_with_patterns(paper_text)
        
        # Handle long papers: split into chunks if > 30k tokens (~120k chars)
        # Quality degrades over 32k tokens, so we split for better results
        max_chars_per_chunk = 60000  # ~15k tokens per chunk (safe limit)
        paper_length = len(paper_text)
        
        # Build prompt template
        metamodel_schema = ""
        if self.metamodel:
            # Include relevant parts of metamodel
            metamodel_schema = f"""
Use this schema for compartmental models:
{json.dumps(self.metamodel.get('epimde_compartmental_metamodel', {}).get('compartment_types', {}), indent=2)}
"""
        
        use_detailed_prompt = True  # same quality for both OpenAI and Gemini
        
        # Build base prompt (will be filled with paper text)
        separator = "\n" + "*" * 80 + "\n"
        def build_prompt(paper_chunk_text: str) -> str:
            if use_detailed_prompt:
                # Detailed prompt for Gemini
                return f"""{separator}
TASK: Extract what the paper PROMISES to model from this epidemiological modeling paper.
{separator}
CRITICAL OUTPUT FORMAT:
- Return ONLY a valid JSON object
- No explanations, no markdown, no code blocks
- Begin directly with '{{' and end with '}}'
- Do NOT use ```json or ``` markers
- Invalid JSON will cause errors
- Each field must be properly formatted JSON

{metamodel_schema}
{separator}
YOUR TASK:
You are an expert in epidemiological modeling. Extract what the paper EXPLICITLY promises or describes to model.

HOW TO IDENTIFY PROMISES:
1. Look for model descriptions: "we model X compartments", "the model includes..."
2. Look for compartment lists: "compartments: S, E, I, R"
3. Look for model type statements: "SEIR model", "SIR model", "vector-borne model"
4. Look for stratification mentions: "age-stratified", "stratified by age/gender/risk"
5. Look for intervention descriptions: "vaccination", "treatment", "quarantine"
6. Look for parameter mentions: "parameters include β, γ, μ"
7. Check abstract and introduction sections
8. Look for model setup or methodology sections

REQUIRED JSON STRUCTURE:
{{
  "compartments": ["list", "of", "compartment", "names", "promised"],
  "stratifications": ["list", "of", "stratification", "dimensions", "e.g.", "age", "risk_group"],
  "parameters": ["list", "of", "parameter", "names", "mentioned"],
  "interventions": ["list", "of", "interventions", "e.g.", "vaccination", "treatment"],
  "model_type": "SEIR" or "SIR" or "Vector-Borne" etc.,
  "description": "brief description of what the paper promises to model"
}}

EXTRACTION RULES - BE PRECISE:
- Focus ONLY on what is EXPLICITLY stated in the paper
- Don't infer or add things not mentioned
- If something is not mentioned, use an empty list []
- Be precise and only include what is explicitly stated
- Extract model type from paper (SEIR, SIR, SEIRS, Vector-Borne, etc.)
- Identify stratification dimensions if mentioned (age, gender, risk_group, etc.)
- List interventions if discussed (vaccination, treatment, quarantine, etc.)
- Extract compartment names if explicitly listed
- Extract parameter names if mentioned

{separator}
PAPER TEXT:
{paper_chunk_text}
{separator}
Return ONLY valid JSON object starting with '{{' and ending with '}}'."""
            else:
                # Concise prompt for OpenAI
                return f"""{separator}
TASK: Extract what the paper PROMISES to model from this epidemiological modeling paper.
{separator}
OUTPUT FORMAT: Return ONLY a valid JSON object. No markdown, no explanations. Start with '{{' and end with '}}'.

{metamodel_schema}
{separator}
EXTRACTION INSTRUCTIONS:
Identify what the paper explicitly promises to model:
- Compartments: Look for "we model X compartments", compartment lists
- Model type: Look for "SEIR", "SIR", "vector-borne" mentions
- Stratifications: Look for "age-stratified", "stratified by..."
- Interventions: Look for "vaccination", "treatment", "quarantine"
- Parameters: Look for parameter mentions or lists

REQUIRED JSON STRUCTURE:
{{
  "compartments": ["list", "of", "compartment", "names"],
  "stratifications": ["list", "of", "stratification", "dimensions"],
  "parameters": ["list", "of", "parameter", "names"],
  "interventions": ["list", "of", "interventions"],
  "model_type": "SEIR" or "SIR" etc.,
  "description": "brief description"
}}

Focus ONLY on what is explicitly stated. Use empty lists [] if not mentioned.

{separator}
PAPER TEXT:
{paper_chunk_text}
{separator}
Return ONLY valid JSON object: {{"compartments": [...], "stratifications": [...], "parameters": [...], "interventions": [...], "model_type": "...", "description": "..."}}"""
        
        # If paper is very long, split into chunks and merge results
        if paper_length > max_chars_per_chunk * 2:  # Split if > 2 chunks worth
            print(f"  Paper is long ({paper_length} chars). Splitting into chunks for better extraction...")
            chunks = []
            for i in range(0, paper_length, max_chars_per_chunk):
                chunk = paper_text[i:i + max_chars_per_chunk]
                if i + max_chars_per_chunk < paper_length:
                    chunk += "\n[... continued in next chunk ...]"
                chunks.append(chunk)
            
            # Extract from each chunk and merge
            all_results = []
            for idx, chunk in enumerate(chunks):
                prompt = build_prompt(chunk)
                try:
                    chunk_result = self.llm_client.extract_with_llm(prompt, max_tokens=4000)
                    if isinstance(chunk_result, dict):
                        all_results.append(chunk_result)
                except Exception as e:
                    print(f"  Warning: Chunk {idx+1} extraction failed: {e}")
            
            # Merge results from all chunks
            if all_results:
                merged = {
                    "compartments": [],
                    "stratifications": [],
                    "parameters": [],
                    "interventions": [],
                    "model_type": "",
                    "description": ""
                }
                for result in all_results:
                    merged["compartments"].extend(result.get("compartments", []))
                    merged["stratifications"].extend(result.get("stratifications", []))
                    merged["parameters"].extend(result.get("parameters", []))
                    merged["interventions"].extend(result.get("interventions", []))
                    # Use first non-empty model_type and description
                    if not merged["model_type"] and result.get("model_type"):
                        merged["model_type"] = result.get("model_type", "")
                    if not merged["description"] and result.get("description"):
                        merged["description"] = result.get("description", "")
                
                # Deduplicate
                merged["compartments"] = list(set(merged["compartments"]))
                merged["stratifications"] = list(set(merged["stratifications"]))
                merged["parameters"] = list(set(merged["parameters"]))
                merged["interventions"] = list(set(merged["interventions"]))
                merged["extraction_method"] = "llm"
                return merged
            else:
                # Fallback if all chunks failed
                print("  All chunks failed, falling back to pattern extraction...")
                return self.extract_with_patterns(paper_text)
        else:
            # Single chunk - use full text or truncate if still too long
            truncated_text = paper_text[:max_chars_per_chunk]
            if paper_length > max_chars_per_chunk:
                truncated_text += "\n[... text truncated ...]"
            
            prompt = build_prompt(truncated_text)
        
        try:
            # Use higher max_tokens for paper promises (can have long descriptions)
            result = self.llm_client.extract_with_llm(prompt, max_tokens=4000)
            result["extraction_method"] = "llm"
            return result
        except Exception as e:
            print(f"Error with LLM extraction: {e}")
            print("Falling back to pattern-based extraction...")
            return self.extract_with_patterns(paper_text)
    
    def extract(self, paper_text: str, use_llm: bool = True) -> Dict[str, Any]:
        """
        Extract paper promises using hybrid approach.
        
        Args:
            paper_text: Full paper text
            use_llm: Whether to use LLM (default: True)
        
        Returns:
            Dictionary with extracted promises
        """
        if use_llm and self.llm_client.is_available():
            return self.extract_with_llm(paper_text)
        else:
            return self.extract_with_patterns(paper_text)
    
    def save_promises(self, promises: Dict[str, Any], output_path: str):
        """Save promises to JSON file"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        
        with open(output_path, 'w') as f:
            json.dump(promises, f, indent=2)
