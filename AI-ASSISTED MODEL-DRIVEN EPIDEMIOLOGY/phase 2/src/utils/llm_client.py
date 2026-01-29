"""LLM client wrapper for OpenAI and Gemini APIs"""

import os
import re
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
        # Guard against accidentally using the wrong provider's key (common when storing both)
        if self.api_key:
            if self.provider == "openai" and self.api_key.strip().startswith("AIza"):
                print("Warning: .api_key.txt appears to contain a Gemini key (AIza...) but provider is openai. "
                      "Use 'openai:sk-...' or set OPENAI_API_KEY. Falling back to pattern-based extraction.")
                self.api_key = None
            if self.provider == "gemini" and self.api_key.strip().startswith("sk-"):
                print("Warning: .api_key.txt appears to contain an OpenAI key (sk-...) but provider is gemini. "
                      "Use 'gemini:AIza...' or set GEMINI_API_KEY. Falling back to pattern-based extraction.")
                self.api_key = None
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
                        temperature: float = 0.3, max_tokens: int = 2000, 
                        retry_count: int = 0) -> Dict[str, Any]:
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
        
        # Set default model if not provided; allow environment variable overrides
        if model is None:
            if self.provider == "openai":
                # e.g. export OPENAI_MODEL=gpt-4.1-mini
                model = os.getenv("OPENAI_MODEL", "gpt-4o-mini")
            elif self.provider == "gemini":
                # e.g. export GEMINI_MODEL=gemini-2.5-flash or gemini-2.5-pro
                model = os.getenv("GEMINI_MODEL", "gemini-2.5-pro")
        
        # Use identical system instruction for both providers to ensure fair comparison
        system_instruction = """You are a scientific paper analyzer. You must return ONLY valid JSON.
CRITICAL RULES:
- No explanations, no markdown code blocks, no extra text before or after
- Begin directly with '{' or '[' and end with '}' or ']'
- Do NOT use ```json or ``` markers
- Invalid JSON will cause errors
- Return pure JSON only"""
        
        try:
            if self.provider == "openai":
                # Check if prompt asks for array or object
                # If prompt mentions "array" or starts with "Return a JSON array", don't use json_object format
                # (json_object format only works for objects, not arrays)
                use_json_object = "array" not in prompt.lower() and "json array" not in prompt.lower()
                
                # Use the same system instruction for fair comparison
                messages = [
                    {"role": "system", "content": system_instruction},
                    {"role": "user", "content": prompt}
                ]
                
                # Only use response_format for JSON objects (not arrays)
                if use_json_object:
                    response = self.client.chat.completions.create(
                        model=model,
                        messages=messages,
                        temperature=temperature,
                        max_tokens=max_tokens,
                        response_format={"type": "json_object"}
                    )
                else:
                    # For arrays, rely on prompt instructions only
                    response = self.client.chat.completions.create(
                        model=model,
                        messages=messages,
                        temperature=temperature,
                        max_tokens=max_tokens
                    )
                result_text = response.choices[0].message.content.strip()
                
            elif self.provider == "gemini":
                # Use the same system instruction prepended to prompt (Gemini doesn't have separate system messages)
                full_prompt = f"{system_instruction}\n\n{prompt}"
                
                gen_model = self.client.GenerativeModel(model)
                response = gen_model.generate_content(
                    full_prompt,
                    generation_config={
                        "temperature": temperature,
                        "max_output_tokens": max_tokens,
                    },
                    safety_settings=[
                        {"category": "HARM_CATEGORY_HARASSMENT", "threshold": "BLOCK_NONE"},
                        {"category": "HARM_CATEGORY_HATE_SPEECH", "threshold": "BLOCK_NONE"},
                        {"category": "HARM_CATEGORY_SEXUALLY_EXPLICIT", "threshold": "BLOCK_NONE"},
                        {"category": "HARM_CATEGORY_DANGEROUS_CONTENT", "threshold": "BLOCK_NONE"},
                    ]
                )
                # Handle Gemini response - check for blocked content
                if hasattr(response, 'candidates') and response.candidates:
                    candidate = response.candidates[0]
                    # Check finish reason (2 = SAFETY, 3 = RECITATION, etc.)
                    if hasattr(candidate, 'finish_reason'):
                        if candidate.finish_reason == 2:  # SAFETY
                            # Try to get the text anyway, or use flash model as fallback
                            if hasattr(candidate, 'content') and candidate.content:
                                if hasattr(candidate.content, 'parts') and candidate.content.parts:
                                    result_text = ''.join([part.text for part in candidate.content.parts if hasattr(part, 'text')]).strip()
                                else:
                                    # Safety blocked - try flash model as fallback
                                    print(f"⚠️  Gemini Pro blocked by safety filters. Trying Flash model...")
                                    if model != "gemini-2.5-flash":
                                        return self.extract_with_llm(prompt, model="gemini-2.5-flash", temperature=temperature, max_tokens=max_tokens, retry_count=retry_count)
                                    raise ValueError("Gemini content blocked by safety filters and Flash also unavailable")
                            else:
                                raise ValueError("Gemini content blocked by safety filters")
                        elif candidate.finish_reason == 3:  # RECITATION
                            raise ValueError("Gemini blocked content due to recitation policy")
                
                # Try to get text from response
                try:
                    result_text = response.text.strip()
                except AttributeError:
                    # Fallback: try to extract from candidates
                    if hasattr(response, 'candidates') and response.candidates:
                        candidate = response.candidates[0]
                        if hasattr(candidate, 'content') and candidate.content:
                            if hasattr(candidate.content, 'parts') and candidate.content.parts:
                                result_text = ''.join([part.text for part in candidate.content.parts if hasattr(part, 'text')]).strip()
                            else:
                                # Check for blocked content
                                if hasattr(response, 'prompt_feedback'):
                                    raise ValueError(f"Gemini blocked content: {response.prompt_feedback}")
                                raise ValueError("Gemini returned empty response")
                        else:
                            raise ValueError("Gemini returned empty response")
                    else:
                        if hasattr(response, 'prompt_feedback'):
                            raise ValueError(f"Gemini blocked content: {response.prompt_feedback}")
                        raise ValueError("Gemini returned empty response")
            else:
                raise ValueError(f"Unknown provider: {self.provider}")
            
            # Extract JSON from response (handle markdown code blocks)
            if "```json" in result_text:
                result_text = result_text.split("```json")[1].split("```")[0].strip()
            elif "```" in result_text:
                result_text = result_text.split("```")[1].split("```")[0].strip()
            
            # Parse JSON with automatic repair for common issues
            try:
                parsed = json.loads(result_text)
                # If we expected an array but got an object (or vice versa), log a warning
                if isinstance(parsed, dict) and "array" in prompt.lower():
                    print(f"Warning: Expected JSON array but got object. Response keys: {list(parsed.keys())[:5]}")
                elif isinstance(parsed, list) and "object" in prompt.lower() and "array" not in prompt.lower():
                    print(f"Warning: Expected JSON object but got array with {len(parsed)} items")
                return parsed
            except json.JSONDecodeError as e:
                # Try to repair common JSON issues
                repaired = self._repair_json(result_text)
                if repaired:
                    try:
                        parsed = json.loads(repaired)
                        print(f"✓ Repaired JSON (fixed missing commas or other issues)")
                        return parsed
                    except json.JSONDecodeError:
                        pass
                
                print(f"Warning: Failed to parse LLM response as JSON: {e}")
                print(f"Response text (first 500 chars): {result_text[:500]}")
                # Try to extract JSON if it's wrapped in text
                if "```json" in result_text:
                    try:
                        json_part = result_text.split("```json")[1].split("```")[0].strip()
                        repaired = self._repair_json(json_part)
                        if repaired:
                            return json.loads(repaired)
                        return json.loads(json_part)
                    except Exception:
                        pass

                # As a last resort for Gemini array prompts, try to salvage objects one by one
                expects_array = "array" in prompt.lower()
                if self.provider == "gemini" and expects_array:
                    salvaged = self._salvage_json_array(result_text)
                    if salvaged:
                        print(f"✓ Salvaged {len(salvaged)} items from partially broken JSON array")
                        return salvaged

                return {"error": "Failed to parse JSON", "raw_response": result_text[:1000]}
        
        except Exception as e:
            error_str = str(e)
            # Check if it's a quota error (429) and we haven't retried too much
            if "429" in error_str or "quota" in error_str.lower():
                if retry_count < 2:  # Retry up to 2 times
                    import time
                    wait_time = 5 * (retry_count + 1)  # Wait 5, 10 seconds
                    print(f"⚠️  Quota error detected. Waiting {wait_time}s before retry {retry_count + 1}...")
                    time.sleep(wait_time)
                    return self.extract_with_llm(prompt, model, temperature, max_tokens, retry_count + 1)
                else:
                    print(f"❌ Error: API quota exceeded after retries.")
                    print(f"   Please check your quota.")
                    print(f"   The system will fall back to pattern-based extraction (limited accuracy).")
            print(f"Error calling LLM ({self.provider}): {e}")
            raise
    
    def _repair_json(self, text: str) -> Optional[str]:
        """
        Attempt to repair common JSON issues:
        - Missing commas between array elements: }{ -> },{
        - Missing commas between object properties
        - Trailing commas
        - Unescaped quotes in strings (especially in text_span fields)
        - Unescaped newlines in strings
        """
        if not text or not text.strip():
            return None
        
        repaired = text.strip()
        
        # First, fix unescaped quotes and newlines in string values using a state machine
        # This is more reliable than regex for handling nested quotes
        try:
            result = []
            i = 0
            in_string = False
            escape_next = False
            
            while i < len(repaired):
                char = repaired[i]
                
                if escape_next:
                    # Previous char was backslash, this char is escaped
                    result.append(char)
                    escape_next = False
                    i += 1
                    continue
                
                if char == '\\':
                    # Escape character
                    result.append(char)
                    escape_next = True
                    i += 1
                    continue
                
                if char == '"' and not escape_next:
                    # Toggle string state
                    in_string = not in_string
                    result.append(char)
                    i += 1
                    continue
                
                if in_string:
                    # We're inside a string - escape special characters
                    if char == '\n':
                        # Unescaped newline - escape it
                        result.append('\\n')
                    elif char == '\r':
                        # Unescaped carriage return - escape it
                        result.append('\\r')
                    elif char == '\t':
                        # Unescaped tab - escape it
                        result.append('\\t')
                    elif char == '"':
                        # Unescaped quote inside string - escape it
                        result.append('\\"')
                    elif char == '\\':
                        # Backslash - will be handled by escape_next logic
                        result.append(char)
                        escape_next = True
                    else:
                        result.append(char)
                else:
                    # Outside string - keep as is
                    result.append(char)
                
                i += 1
            
            repaired = ''.join(result)
        except Exception:
            # If state machine fails, fall back to simpler regex-based approach
            # This is a less reliable fallback
            pass
        
        # Fix missing commas between array elements: }{ -> },{
        repaired = re.sub(r'\}\s*\{', '},{', repaired)
        
        # Fix trailing commas before } or ]
        repaired = re.sub(r',\s*}', '}', repaired)
        repaired = re.sub(r',\s*]', ']', repaired)

        # Fix missing commas after closing braces/quotes before opening braces
        repaired = re.sub(r'"\s*\{', '",{', repaired)
        repaired = re.sub(r"'\s*\{", "',{", repaired)
        
        return repaired if repaired != text else None

    def _salvage_json_array(self, text: str) -> Optional[list]:
        """
        Salvage best-effort JSON array from text by parsing objects one by one.

        This is mainly used for Gemini when it returns a mostly-correct JSON array
        where only the last object (or a few) are malformed. We try to extract
        each top-level {...} block and parse it independently, ignoring failures.
        """
        if not text:
            return None

        # Remove any surrounding code fences that might still be present
        stripped = text.strip()
        if stripped.startswith("```"):
            # Drop first fence and anything before the next fence
            parts = stripped.split("```")
            if len(parts) >= 3:
                stripped = parts[2].strip()

        objects = []
        buf = []
        depth = 0
        in_string = False
        escape_next = False

        for ch in stripped:
            if escape_next:
                buf.append(ch)
                escape_next = False
                continue
            if ch == "\\":
                buf.append(ch)
                escape_next = True
                continue
            if ch == '"' and not escape_next:
                in_string = not in_string
                buf.append(ch)
                continue
            if not in_string:
                if ch == "{":
                    depth += 1
                elif ch == "}":
                    depth -= 1
            if depth > 0 or (depth == 0 and ch == "}"):
                buf.append(ch)
            # When depth returns to zero and we have a buffer, try to parse it
            if depth == 0 and buf:
                candidate = "".join(buf).strip()
                buf = []
                if not candidate:
                    continue
                try:
                    repaired_obj = self._repair_json(candidate) or candidate
                    parsed = json.loads(repaired_obj)
                    if isinstance(parsed, dict):
                        objects.append(parsed)
                except Exception:
                    # Ignore this candidate and continue
                    continue

        return objects or None
    
    def is_available(self) -> bool:
        """Check if LLM is available"""
        return self.available
