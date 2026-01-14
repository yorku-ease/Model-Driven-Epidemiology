"""
Paper Promise Extractor for Phase 2

Extracts what a paper promises to model (compartments, parameters, stratifications, interventions)
from paper text/PDF. Supports both LLM-based extraction and pattern-based fallback.
"""
import json
import re
from pathlib import Path
from typing import Dict, List, Any, Optional, Set
import sys

# Add parent directory to path for imports
sys.path.insert(0, str(Path(__file__).parent))

# Import from same directory
from gap_analyzer import PaperPromises


class PaperPromiseExtractor:
    """
    Extract promises from papers about what they model.
    Supports LLM-based extraction (Phase 2) and pattern-based fallback.
    """
    
    def __init__(self, use_llm: bool = False, llm_api_key: Optional[str] = None):
        """
        Initialize extractor.
        
        Args:
            use_llm: Whether to use LLM for extraction (requires API key)
            llm_api_key: API key for LLM service (OpenAI, Anthropic, etc.)
        """
        self.use_llm = use_llm
        self.llm_api_key = llm_api_key
        self._check_llm_available()
    
    def _check_llm_available(self):
        """Check if LLM dependencies are available"""
        if self.use_llm:
            try:
                import openai
                self.llm_available = True
            except ImportError:
                print("Warning: openai package not installed. Install with: pip install openai")
                print("Falling back to pattern-based extraction.")
                self.llm_available = False
                self.use_llm = False
        else:
            self.llm_available = False
    
    def extract_text_from_pdf(self, pdf_path: str) -> str:
        """
        Extract text from PDF file.
        Requires: pip install PyPDF2 or pdfplumber
        """
        pdf_path = Path(pdf_path)
        if not pdf_path.exists():
            raise FileNotFoundError(f"PDF not found: {pdf_path}")
        
        # Try pdfplumber first (better for complex PDFs)
        try:
            import pdfplumber
            text = ""
            with pdfplumber.open(pdf_path) as pdf:
                for page in pdf.pages:
                    text += page.extract_text() + "\n"
            return text
        except ImportError:
            pass
        
        # Fallback to PyPDF2
        try:
            import PyPDF2
            text = ""
            with open(pdf_path, 'rb') as file:
                pdf_reader = PyPDF2.PdfReader(file)
                for page in pdf_reader.pages:
                    text += page.extract_text() + "\n"
            return text
        except ImportError:
            raise ImportError(
                "No PDF extraction library found. Install one of:\n"
                "  pip install pdfplumber  (recommended)\n"
                "  pip install PyPDF2"
            )
    
    def extract_with_llm(self, paper_text: str) -> PaperPromises:
        """
        Extract promises using LLM (OpenAI GPT).
        This is the Phase 2 preferred method.
        """
        if not self.llm_available:
            raise RuntimeError("LLM not available. Install openai package and set API key.")
        
        import openai
        
        # Truncate text if too long (LLM context limits)
        max_chars = 100000  # ~25k tokens
        if len(paper_text) > max_chars:
            paper_text = paper_text[:max_chars] + "\n[... text truncated ...]"
        
        prompt = """You are analyzing a scientific paper about epidemiological modeling. 
Extract what the paper PROMISES to model. Return ONLY a JSON object with this structure:

{
  "compartments": ["list", "of", "compartment", "names", "promised"],
  "stratifications": ["list", "of", "stratification", "dimensions", "e.g.", "age", "risk_group"],
  "parameters": ["list", "of", "parameter", "names", "mentioned"],
  "interventions": ["list", "of", "interventions", "e.g.", "vaccination", "treatment"],
  "model_type": "SEIR" or "SIR" or "Vector-Borne" etc.,
  "description": "brief description of what the paper promises to model"
}

Focus on what the paper EXPLICITLY promises or describes. Don't infer or add things not mentioned.
If something is not mentioned, use an empty list.

Paper text:
""" + paper_text[:50000]  # First 50k chars for prompt
        
        try:
            client = openai.OpenAI(api_key=self.llm_api_key)
            response = client.chat.completions.create(
                model="gpt-4o-mini",  # Can be changed to gpt-4, gpt-3.5-turbo, etc.
                messages=[
                    {"role": "system", "content": "You are a scientific paper analyzer. Return only valid JSON."},
                    {"role": "user", "content": prompt}
                ],
                temperature=0.3,  # Lower temperature for more consistent extraction
                max_tokens=2000
            )
            
            result_text = response.choices[0].message.content.strip()
            
            # Extract JSON from response (handle markdown code blocks)
            if "```json" in result_text:
                result_text = result_text.split("```json")[1].split("```")[0].strip()
            elif "```" in result_text:
                result_text = result_text.split("```")[1].split("```")[0].strip()
            
            data = json.loads(result_text)
            return PaperPromises(
                compartments=data.get('compartments', []),
                stratifications=data.get('stratifications', []),
                parameters=data.get('parameters', []),
                interventions=data.get('interventions', []),
                model_type=data.get('model_type'),
                description=data.get('description')
            )
        except Exception as e:
            print(f"Error with LLM extraction: {e}")
            print("Falling back to pattern-based extraction...")
            return self.extract_with_patterns(paper_text)
    
    def extract_with_patterns(self, paper_text: str) -> PaperPromises:
        """
        Extract promises using pattern matching (fallback method).
        Less accurate but doesn't require LLM.
        """
        text_lower = paper_text.lower()
        
        # Common compartment patterns
        compartment_patterns = [
            r'\b(susceptible|s)\b',
            r'\b(exposed|e)\b',
            r'\b(infectious|infected|i)\b',
            r'\b(recovered|r)\b',
            r'\b(vaccinated|v)\b',
            r'\b(asymptomatic|ah?)\b',
            r'\b(symptomatic|sh?)\b',
            r'\b(severe|hospitalized|h)\b',
            r'\b(icu|intensive care)\b',
            r'\b(treated|t)\b',
            r'\b(carrier|c)\b',
            r'\b(quarantined|q)\b',
            r'\b(immune|m)\b',
            r'\b(vector|mosquito|v)\b',  # Vector-borne
        ]
        
        compartments = set()
        for pattern in compartment_patterns:
            if re.search(pattern, text_lower):
                # Extract full compartment name from context
                matches = re.finditer(pattern, text_lower)
                for match in matches:
                    start = max(0, match.start() - 20)
                    end = min(len(text_lower), match.end() + 20)
                    context = text_lower[start:end]
                    # Try to find full name
                    if 'susceptible' in context:
                        compartments.add('Susceptible')
                    elif 'exposed' in context:
                        compartments.add('Exposed')
                    elif 'infectious' in context or 'infected' in context:
                        compartments.add('Infectious')
                    elif 'recovered' in context:
                        compartments.add('Recovered')
                    elif 'vaccinated' in context:
                        compartments.add('Vaccinated')
                    elif 'asymptomatic' in context:
                        compartments.add('Asymptomatic')
                    elif 'severe' in context or 'hospitalized' in context:
                        compartments.add('Severe')
                    elif 'icu' in context or 'intensive care' in context:
                        compartments.add('ICU')
        
        # Stratification patterns
        stratification_patterns = [
            r'\b(age[\s-]?stratified|age[\s-]?group|age[\s-]?specific)\b',
            r'\b(risk[\s-]?group|risk[\s-]?stratified|behavioral[\s-]?group)\b',
            r'\b(gender|sex[\s-]?stratified)\b',
            r'\b(geographic|location|spatial|region)\b',
            r'\b(vaccination[\s-]?status)\b',
        ]
        
        stratifications = set()
        for pattern in stratification_patterns:
            if re.search(pattern, text_lower):
                if 'age' in pattern:
                    stratifications.add('age')
                elif 'risk' in pattern or 'behavior' in pattern:
                    stratifications.add('risk_group')
                elif 'gender' in pattern or 'sex' in pattern:
                    stratifications.add('gender')
                elif 'geographic' in pattern or 'location' in pattern or 'spatial' in pattern:
                    stratifications.add('location')
                elif 'vaccination' in pattern:
                    stratifications.add('vaccination_status')
        
        # Parameter patterns (look for parameter definitions)
        parameter_patterns = [
            r'\b(transmission[\s-]?rate|beta|β)\b',
            r'\b(recovery[\s-]?rate|gamma|γ)\b',
            r'\b(incubation[\s-]?rate|sigma|σ)\b',
            r'\b(mortality[\s-]?rate|mortality|death[\s-]?rate)\b',
            r'\b(contact[\s-]?rate)\b',
            r'\b(birth[\s-]?rate)\b',
        ]
        
        parameters = set()
        for pattern in parameter_patterns:
            if re.search(pattern, text_lower):
                matches = re.findall(pattern, text_lower)
                for match in matches:
                    if isinstance(match, tuple):
                        match = match[0]
                    match = match.strip()
                    if 'transmission' in match or 'beta' in match:
                        parameters.add('transmission_rate')
                    elif 'recovery' in match or 'gamma' in match:
                        parameters.add('recovery_rate')
                    elif 'incubation' in match or 'sigma' in match:
                        parameters.add('incubation_rate')
                    elif 'mortality' in match or 'death' in match:
                        parameters.add('mortality_rate')
                    elif 'contact' in match:
                        parameters.add('contact_rate')
                    elif 'birth' in match:
                        parameters.add('birth_rate')
        
        # Intervention patterns
        intervention_patterns = [
            r'\b(vaccination|vaccine)\b',
            r'\b(treatment|antiviral|antiretroviral)\b',
            r'\b(quarantine|isolation)\b',
            r'\b(contact[\s-]?tracing)\b',
            r'\b(bed[\s-]?net|insecticide[\s-]?treated[\s-]?net|itn)\b',
            r'\b(prep|pre[\s-]?exposure[\s-]?prophylaxis)\b',
        ]
        
        interventions = set()
        for pattern in intervention_patterns:
            if re.search(pattern, text_lower):
                if 'vaccin' in pattern:
                    interventions.add('vaccination')
                elif 'treatment' in pattern or 'antiviral' in pattern or 'antiretroviral' in pattern:
                    interventions.add('treatment')
                elif 'quarantine' in pattern or 'isolation' in pattern:
                    interventions.add('quarantine')
                elif 'contact' in pattern and 'tracing' in pattern:
                    interventions.add('contact_tracing')
                elif 'bed' in pattern or 'net' in pattern or 'itn' in pattern:
                    interventions.add('bed_nets')
                elif 'prep' in pattern:
                    interventions.add('prep')
        
        # Model type detection
        model_type = None
        if re.search(r'\b(seir|s-e-i-r)\b', text_lower):
            model_type = 'SEIR'
        elif re.search(r'\b(sir|s-i-r)\b', text_lower) and not re.search(r'\b(seir|s-e-i-r)\b', text_lower):
            model_type = 'SIR'
        elif re.search(r'\b(vector[\s-]?borne|mosquito|malaria)\b', text_lower):
            model_type = 'Vector-Borne'
        elif re.search(r'\b(sid|s-i-d)\b', text_lower):
            model_type = 'SID'
        
        # Description
        description = f"Pattern-based extraction from paper text. Found {len(compartments)} compartments, {len(stratifications)} stratifications, {len(parameters)} parameters, {len(interventions)} interventions."
        
        return PaperPromises(
            compartments=list(compartments),
            stratifications=list(stratifications),
            parameters=list(parameters),
            interventions=list(interventions),
            model_type=model_type,
            description=description
        )
    
    def extract_from_pdf(self, pdf_path: str) -> PaperPromises:
        """
        Extract promises from PDF file.
        Uses LLM if available, otherwise falls back to patterns.
        """
        print(f"Extracting text from PDF: {pdf_path}")
        text = self.extract_text_from_pdf(pdf_path)
        print(f"Extracted {len(text)} characters of text")
        
        if self.use_llm and self.llm_available:
            print("Using LLM-based extraction...")
            return self.extract_with_llm(text)
        else:
            print("Using pattern-based extraction...")
            return self.extract_with_patterns(text)
    
    def extract_from_text(self, text: str) -> PaperPromises:
        """
        Extract promises from text string.
        Uses LLM if available, otherwise falls back to patterns.
        """
        if self.use_llm and self.llm_available:
            return self.extract_with_llm(text)
        else:
            return self.extract_with_patterns(text)
    
    def save_promises(self, promises: PaperPromises, output_path: str):
        """Save extracted promises to JSON file"""
        with open(output_path, 'w', encoding='utf-8') as f:
            json.dump(promises.to_dict(), f, indent=2, ensure_ascii=False)
    
    @staticmethod
    def load_promises(json_path: str) -> PaperPromises:
        """Load promises from JSON file"""
        with open(json_path, 'r', encoding='utf-8') as f:
            data = json.load(f)
        return PaperPromises.from_dict(data)


def main():
    """Example usage"""
    import argparse
    
    parser = argparse.ArgumentParser(description='Extract promises from a paper')
    parser.add_argument('pdf_path', help='Path to PDF file')
    parser.add_argument('--use-llm', action='store_true', help='Use LLM for extraction (requires API key)')
    parser.add_argument('--api-key', help='OpenAI API key (or set OPENAI_API_KEY env var)')
    parser.add_argument('--output', help='Output JSON file path')
    
    args = parser.parse_args()
    
    # Get API key
    api_key = args.api_key or os.getenv('OPENAI_API_KEY')
    if args.use_llm and not api_key:
        print("Warning: --use-llm specified but no API key provided.")
        print("Set OPENAI_API_KEY environment variable or use --api-key")
        args.use_llm = False
    
    # Extract promises
    extractor = PaperPromiseExtractor(use_llm=args.use_llm, llm_api_key=api_key)
    promises = extractor.extract_from_pdf(args.pdf_path)
    
    # Print results
    print("\n" + "=" * 80)
    print("EXTRACTED PROMISES")
    print("=" * 80)
    print(f"\nCompartments: {promises.compartments}")
    print(f"Stratifications: {promises.stratifications}")
    print(f"Parameters: {promises.parameters}")
    print(f"Interventions: {promises.interventions}")
    print(f"Model Type: {promises.model_type}")
    print(f"\nDescription: {promises.description}")
    
    # Save if output specified
    if args.output:
        extractor.save_promises(promises, args.output)
        print(f"\n✓ Saved to: {args.output}")
    else:
        # Save to default location
        pdf_name = Path(args.pdf_path).stem
        output_path = Path(__file__).parent.parent / 'reports' / 'paper_promises' / f"{pdf_name}_promises.json"
        output_path.parent.mkdir(parents=True, exist_ok=True)
        extractor.save_promises(promises, str(output_path))
        print(f"\n✓ Saved to: {output_path}")


if __name__ == '__main__':
    import os
    main()
