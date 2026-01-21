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
        
        # Truncate text if too long
        max_chars = 50000  # ~12k tokens
        truncated_text = paper_text[:max_chars]
        if len(paper_text) > max_chars:
            truncated_text += "\n[... text truncated ...]"
        
        # Build prompt
        metamodel_schema = ""
        if self.metamodel:
            # Include relevant parts of metamodel
            metamodel_schema = f"""
Use this schema for compartmental models:
{json.dumps(self.metamodel.get('epimde_compartmental_metamodel', {}).get('compartment_types', {}), indent=2)}
"""
        
        prompt = f"""You are analyzing a scientific paper about epidemiological modeling.
Extract what the paper PROMISES to model. Return ONLY a JSON object with this structure:

{{
  "compartments": ["list", "of", "compartment", "names", "promised"],
  "stratifications": ["list", "of", "stratification", "dimensions", "e.g.", "age", "risk_group"],
  "parameters": ["list", "of", "parameter", "names", "mentioned"],
  "interventions": ["list", "of", "interventions", "e.g.", "vaccination", "treatment"],
  "model_type": "SEIR" or "SIR" or "Vector-Borne" etc.,
  "description": "brief description of what the paper promises to model"
}}

{metamodel_schema}

Focus on what the paper EXPLICITLY promises or describes. Don't infer or add things not mentioned.
If something is not mentioned, use an empty list.

Paper text:
{truncated_text[:50000]}"""
        
        try:
            result = self.llm_client.extract_with_llm(prompt)
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
