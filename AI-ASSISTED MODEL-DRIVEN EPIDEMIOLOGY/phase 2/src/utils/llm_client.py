"""LLM client wrapper for OpenAI and Gemini APIs"""

import os
from pathlib import Path
from typing import Optional, Dict, Any, Literal
import json


class LLMClient:
    """Wrapper for OpenAI and Gemini API clients with API key management"""
    
    def __init__(self, api_key: Optional[str] = None, api_key_file: Optional[str] = None,
                 provider: Literal["openai", "gemini"] = "openai"):
        """
        Initialize LLM client.
        
        Args:
            api_key: Direct API key (not recommended for security)
            api_key_file: Path to file containing API key (default: .api_key.txt in phase2 dir)
            provider: LLM provider to use - "openai" or "gemini" (default: "openai")
        """
        self.provider = provider.lower()
        self.api_key = self._load_api_key(api_key, api_key_file, provider)
        self.client = None
        self.available = False
        
        if self.api_key:
            if self.provider == "openai":
                try:
                    import openai
                    self.client = openai.OpenAI(api_key=self.api_key)
                    self.available = True
                except ImportError:
                    print("Warning: openai package not installed. Install with: pip install openai")
                except Exception as e:
                    print(f"Warning: Failed to initialize OpenAI client: {e}")
            elif self.provider == "gemini":
                try:
                    import google.generativeai as genai
                    genai.configure(api_key=self.api_key)
                    self.client = genai
                    self.available = True
                except ImportError:
                    print("Warning: google-generativeai package not installed. Install with: pip install google-generativeai")
                except Exception as e:
                    print(f"Warning: Failed to initialize Gemini client: {e}")
            else:
                print(f"Warning: Unknown provider '{self.provider}'. Supported: 'openai', 'gemini'")
    
    def _load_api_key(self, api_key: Optional[str], api_key_file: Optional[str], 
                     provider: str) -> Optional[str]:
        """Load API key from various sources"""
        # Priority 1: Direct API key
        if api_key:
            return api_key.strip()
        
        # Priority 2: Environment variable (provider-specific)
        if provider == "openai":
            env_key = os.getenv("OPENAI_API_KEY")
        elif provider == "gemini":
            env_key = os.getenv("GEMINI_API_KEY")
        else:
            env_key = None
            
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
                            # If file has provider prefix (e.g., "openai:sk-..." or "gemini:AI...")
                            if ':' in line and line.split(':')[0].lower() in ['openai', 'gemini']:
                                prefix, key = line.split(':', 1)
                                if prefix.lower() == provider:
                                    return key.strip()
                            # Otherwise, return first non-comment line (backward compatible)
                            return line
            except Exception as e:
                print(f"Warning: Failed to read API key file {api_key_path}: {e}")
        
        return None
    
    def extract_with_llm(self, prompt: str, model: Optional[str] = None, 
                        temperature: float = 0.3, max_tokens: int = 2000) -> Dict[str, Any]:
        """
        Extract structured data using LLM.
        
        Args:
            prompt: Prompt text
            model: Model name (default: provider-specific)
            temperature: Temperature (default: 0.3 for consistency)
            max_tokens: Maximum tokens (default: 2000)
        
        Returns:
            Dictionary with response data
        """
        if not self.available:
            provider_pkg = "openai" if self.provider == "openai" else "google-generativeai"
            raise RuntimeError(f"LLM not available. Check API key and {provider_pkg} package installation.")
        
        # Set default model if not provided
        if model is None:
            if self.provider == "openai":
                model = "gpt-4o-mini"
            elif self.provider == "gemini":
                model = "gemini-2.5-pro"
        
        try:
            if self.provider == "openai":
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
                
            elif self.provider == "gemini":
                # Gemini uses a different API structure
                full_prompt = f"You are a scientific paper analyzer. Return only valid JSON.\n\n{prompt}"
                gen_model = self.client.GenerativeModel(model)
                response = gen_model.generate_content(
                    full_prompt,
                    generation_config={
                        "temperature": temperature,
                        "max_output_tokens": max_tokens,
                    }
                )
                result_text = response.text.strip()
            else:
                raise ValueError(f"Unknown provider: {self.provider}")
            
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
            print(f"Error calling LLM ({self.provider}): {e}")
            raise
    
    def is_available(self) -> bool:
        """Check if LLM is available"""
        return self.available
