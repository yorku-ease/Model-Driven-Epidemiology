"""LLM client wrapper for OpenAI API"""

import os
from pathlib import Path
from typing import Optional, Dict, Any
import json


class LLMClient:
    """Wrapper for OpenAI API client with API key management"""
    
    def __init__(self, api_key: Optional[str] = None, api_key_file: Optional[str] = None):
        """
        Initialize LLM client.
        
        Args:
            api_key: Direct API key (not recommended for security)
            api_key_file: Path to file containing API key (default: .api_key.txt in phase2 dir)
        """
        self.api_key = self._load_api_key(api_key, api_key_file)
        self.client = None
        self.available = False
        
        if self.api_key:
            try:
                import openai
                self.client = openai.OpenAI(api_key=self.api_key)
                self.available = True
            except ImportError:
                print("Warning: openai package not installed. Install with: pip install openai")
            except Exception as e:
                print(f"Warning: Failed to initialize OpenAI client: {e}")
    
    def _load_api_key(self, api_key: Optional[str], api_key_file: Optional[str]) -> Optional[str]:
        """Load API key from various sources"""
        # Priority 1: Direct API key
        if api_key:
            return api_key.strip()
        
        # Priority 2: Environment variable
        env_key = os.getenv("OPENAI_API_KEY")
        if env_key:
            return env_key.strip()
        
        # Priority 3: API key file
        if api_key_file is None:
            # Default: look for .api_key.txt in phase2 directory
            phase2_dir = Path(__file__).parent.parent
            api_key_file = phase2_dir / ".api_key.txt"
        
        api_key_path = Path(api_key_file)
        if api_key_path.exists():
            try:
                with open(api_key_path, 'r') as f:
                    for line in f:
                        line = line.strip()
                        # Skip comments and empty lines
                        if line and not line.startswith('#'):
                            return line
            except Exception as e:
                print(f"Warning: Failed to read API key file {api_key_path}: {e}")
        
        return None
    
    def extract_with_llm(self, prompt: str, model: str = "gpt-4o-mini", 
                        temperature: float = 0.3, max_tokens: int = 2000) -> Dict[str, Any]:
        """
        Extract structured data using LLM.
        
        Args:
            prompt: Prompt text
            model: Model name (default: gpt-4o-mini)
            temperature: Temperature (default: 0.3 for consistency)
            max_tokens: Maximum tokens (default: 2000)
        
        Returns:
            Dictionary with response data
        """
        if not self.available:
            raise RuntimeError("LLM not available. Check API key and openai package installation.")
        
        try:
            response = self.client.chat.completions.create(
                model=model,
                messages=[
                    {"role": "system", "content": "You are a scientific paper analyzer. Return only valid JSON."},
                    {"role": "user", "content": prompt}
                ],
                temperature=temperature,
                max_tokens=max_tokens
            )
            
            result_text = response.choices[0].message.content.strip()
            
            # Extract JSON from response (handle markdown code blocks)
            if "```json" in result_text:
                result_text = result_text.split("```json")[1].split("```")[0].strip()
            elif "```" in result_text:
                result_text = result_text.split("```")[1].split("```")[0].strip()
            
            # Parse JSON
            try:
                return json.loads(result_text)
            except json.JSONDecodeError as e:
                print(f"Warning: Failed to parse LLM response as JSON: {e}")
                print(f"Response text: {result_text[:500]}")
                return {"error": "Failed to parse JSON", "raw_response": result_text}
        
        except Exception as e:
            print(f"Error calling LLM: {e}")
            raise
    
    def is_available(self) -> bool:
        """Check if LLM is available"""
        return self.available
