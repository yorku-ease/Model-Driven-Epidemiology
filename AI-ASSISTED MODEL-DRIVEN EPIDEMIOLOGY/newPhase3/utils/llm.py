"""LLM client wrapper for OpenAI and Gemini APIs"""

import os
import re
from pathlib import Path
from typing import Optional, Dict, Any, Literal, Union
import json
import base64
import io
from concurrent.futures import ThreadPoolExecutor, as_completed

try:
    from PIL import Image
except ImportError:
    Image = None

# New Google GenAI SDK (google-genai) — replaces deprecated google-generativeai
# Install: pip install google-genai
# Old SDK (google-generativeai) reached end-of-life November 30, 2025


class LLMClient:
    """Wrapper for OpenAI and Gemini API clients with API key management"""

    def __init__(
        self,
        api_key: Optional[str] = None,
        api_key_file: Optional[Union[str, Path]] = None,
        provider: Literal["openai", "gemini"] = "openai",
    ):
        """
        Initialize LLM client.

        Args:
            api_key: Direct API key (not recommended for security)
            api_key_file: Path to file containing API key (default: .api_key.txt in phase2 dir)
            provider: LLM provider to use - "openai" or "gemini" (default: "openai")
        """
        self.provider = provider.lower()
        self.api_key = self._load_api_key(api_key, api_key_file, provider)
        # Guard against accidentally using the wrong provider's key (common when storing multiple keys)
        if self.api_key:
            key = self.api_key.strip()
            if self.provider == "openai" and key.startswith("AIza"):
                print(
                    "Warning: .api_key.txt appears to contain a Gemini key (AIza...) but provider is openai. "
                    "Use 'openai:sk-...' or set OPENAI_API_KEY. Falling back to pattern-based extraction."
                )
                self.api_key = None
            if self.provider == "gemini" and key.startswith("sk-"):
                print(
                    "Warning: .api_key.txt appears to contain an OpenAI key (sk-...) but provider is gemini. "
                    "Use 'gemini:AIza...' or set GEMINI_API_KEY. Falling back to pattern-based extraction."
                )
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
                    print(
                        "Warning: openai package not installed. Install with: pip install openai"
                    )
                except Exception as e:
                    print(f"Warning: Failed to initialize OpenAI client: {e}")
            elif self.provider == "gemini":
                try:
                    from google import genai

                    self.client = genai.Client(api_key=self.api_key)
                    self.available = True
                except ImportError:
                    print(
                        "Warning: google-genai package not installed. Install with: pip install google-genai"
                    )
                except Exception as e:
                    print(f"Warning: Failed to initialize Gemini client: {e}")
            else:
                print(
                    f"Warning: Unknown provider '{self.provider}'. Supported: 'openai', 'gemini'"
                )

    def _load_api_key(
        self,
        api_key: Optional[str],
        api_key_file: Optional[Union[str, Path]],
        provider: str,
    ) -> Optional[str]:
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
            return env_key.strip().strip("\ufeff")

        # Priority 3: API key file
        if api_key_file is None:
            # Default: look for .api_key.txt in phase2 directory
            phase2_dir = Path(__file__).parent.parent
            api_key_file = phase2_dir / ".api_key.txt"

        api_key_path = Path(api_key_file)
        if api_key_path.exists():
            try:
                openai_key = None
                gemini_key = None
                with open(api_key_path, "r") as f:
                    for line in f:
                        line = line.strip()
                        if not line or line.startswith("#"):
                            continue
                        # Prefixed line: "openai:sk-..." or "gemini:AIza..."
                        if ":" in line and line.split(":")[0].lower() in [
                            "openai",
                            "gemini",
                        ]:
                            prefix, key = line.split(":", 1)
                            key = key.strip().strip(
                                "\ufeff"
                            )  # strip BOM and whitespace
                            if prefix.lower() == "openai" and key:
                                openai_key = key
                            elif prefix.lower() == "gemini" and key:
                                gemini_key = key
                            continue
                        # No prefix: infer from key shape
                        if line.startswith("sk-"):
                            openai_key = line
                        elif line.startswith("AIza"):
                            gemini_key = line
                if provider == "openai":
                    return openai_key
                if provider == "gemini":
                    return gemini_key
                return None
            except Exception as e:
                print(f"Warning: Failed to read API key file {api_key_path}: {e}")

        return None

    def extract_with_llm(
        self,
        prompt: str,
        model: Optional[str] = None,
        temperature: float = 0.3,
        max_tokens: int = 2000,
        response_schema: Optional[Dict[str, Any]] = None,
        retry_count: int = 0,
    ) -> Dict[str, Any]:
        """
        Extract structured data using LLM.

        Args:
            prompt: Prompt text
            model: Model name (default: provider-specific)
            temperature: Temperature (default: 0.3 for consistency; 0.2 used for Gemini when response_schema set)
            max_tokens: Maximum tokens (default: 2000)
            response_schema: Optional JSON Schema dict for Gemini structured output (guarantees valid JSON)

        Returns:
            Dictionary or list with response data (parsed JSON)
        """
        if not self.available:
            if self.provider == "openai":
                provider_pkg = "openai"
            elif self.provider == "gemini":
                provider_pkg = "google-genai"
            else:
                provider_pkg = "openai"
            raise RuntimeError(
                f"LLM not available. Check API key and {provider_pkg} package installation."
            )

        # Set default model if not provided; allow environment variable overrides
        if model is None:
            if self.provider == "openai":
                # gpt-5.2 is the current flagship (launched Dec 2025); set OPENAI_MODEL to override
                model = os.getenv("OPENAI_MODEL", "gpt-5.2")
            elif self.provider == "gemini":
                # gemini-3-flash-preview for general tasks; set GEMINI_MODEL to override
                # Use gemini-3-pro-preview for highest quality at higher cost
                model = os.getenv("GEMINI_MODEL", "gemini-3-flash-preview")

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
                # Use the same system instruction for fair comparison
                messages = [
                    {"role": "system", "content": system_instruction},
                    {"role": "user", "content": prompt},
                ]

                # IMPORTANT: OpenAI's json_schema structured outputs currently require a root object schema,
                # but our extraction schemas are arrays. Until we redesign prompts/schema around an object
                # wrapper, we keep using the previous behavior (json_object for objects, plain JSON mode for arrays).
                # This avoids 400 errors like:
                #   "schema must be a JSON Schema of 'type: \"object\"', got 'type: \"array\"'."

                # Check if prompt asks for array or object
                # If prompt mentions "array" or starts with "Return a JSON array", don't use json_object format
                # (json_object format only works for objects, not arrays)
                use_json_object = (
                    "array" not in prompt.lower() and "json array" not in prompt.lower()
                )

                if use_json_object:
                    response = self.client.chat.completions.create(
                        model=model,
                        messages=messages,
                        temperature=temperature,
                        max_tokens=max_tokens,
                        response_format={"type": "json_object"},
                    )
                else:
                    # For arrays, rely on prompt instructions only
                    response = self.client.chat.completions.create(
                        model=model,
                        messages=messages,
                        temperature=temperature,
                        max_tokens=max_tokens,
                    )

                result_text = response.choices[0].message.content.strip()

            elif self.provider == "gemini":
                from google import genai as _genai
                from google.genai import types as _gtypes

                # New SDK: config goes into GenerateContentConfig, system instruction included there
                # Safety settings use types.SafetySetting objects inside the config
                safety_settings = [
                    _gtypes.SafetySetting(category="HARM_CATEGORY_HARASSMENT",       threshold="BLOCK_NONE"),
                    _gtypes.SafetySetting(category="HARM_CATEGORY_HATE_SPEECH",       threshold="BLOCK_NONE"),
                    _gtypes.SafetySetting(category="HARM_CATEGORY_SEXUALLY_EXPLICIT", threshold="BLOCK_NONE"),
                    _gtypes.SafetySetting(category="HARM_CATEGORY_DANGEROUS_CONTENT", threshold="BLOCK_NONE"),
                ]

                gen_config = _gtypes.GenerateContentConfig(
                    system_instruction=system_instruction,
                    temperature=0.2 if response_schema else temperature,
                    max_output_tokens=max_tokens,
                    safety_settings=safety_settings,
                )

                if response_schema:
                    gen_config = _gtypes.GenerateContentConfig(
                        system_instruction=system_instruction,
                        temperature=0.2,
                        max_output_tokens=max_tokens,
                        safety_settings=safety_settings,
                        response_mime_type="application/json",
                        response_schema=response_schema,
                    )

                response = self.client.models.generate_content(
                    model=model,
                    contents=prompt,
                    config=gen_config,
                )

                # Handle blocked or empty response
                if hasattr(response, "candidates") and response.candidates:
                    candidate = response.candidates[0]
                    if hasattr(candidate, "finish_reason"):
                        finish = candidate.finish_reason
                        # finish_reason values: 1=STOP, 2=SAFETY, 3=RECITATION, etc.
                        if finish == 2:  # SAFETY
                            # Try flash fallback
                            fallback = "gemini-2.5-flash-lite"
                            if model != fallback:
                                print(f"⚠️  Gemini blocked by safety filters. Trying {fallback}...")
                                return self.extract_with_llm(
                                    prompt, model=fallback,
                                    temperature=temperature, max_tokens=max_tokens,
                                    response_schema=response_schema, retry_count=retry_count,
                                )
                            raise ValueError("Gemini content blocked by safety filters")
                        elif finish == 3:  # RECITATION
                            raise ValueError("Gemini blocked content due to recitation policy")

                try:
                    result_text = response.text.strip()
                except (AttributeError, ValueError):
                    # Fallback: assemble from parts
                    parts_text = []
                    if hasattr(response, "candidates") and response.candidates:
                        content = getattr(response.candidates[0], "content", None)
                        if content and hasattr(content, "parts"):
                            parts_text = [p.text for p in content.parts if hasattr(p, "text")]
                    if parts_text:
                        result_text = "".join(parts_text).strip()
                    else:
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
                    print(
                        f"Warning: Expected JSON array but got object. Response keys: {list(parsed.keys())[:5]}"
                    )
                elif (
                    isinstance(parsed, list)
                    and "object" in prompt.lower()
                    and "array" not in prompt.lower()
                ):
                    print(
                        f"Warning: Expected JSON object but got array with {len(parsed)} items"
                    )
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
                        json_part = (
                            result_text.split("```json")[1].split("```")[0].strip()
                        )
                        repaired = self._repair_json(json_part)
                        if repaired:
                            return json.loads(repaired)
                        return json.loads(json_part)
                    except Exception:
                        pass

                # As a last resort for array prompts, try to salvage objects one by one (both providers)
                expects_array = "array" in prompt.lower()
                if expects_array:
                    salvaged = self._salvage_json_array(result_text)
                    if salvaged:
                        print(
                            f"✓ Salvaged {len(salvaged)} items from partially broken JSON array"
                        )
                        return salvaged

                return {
                    "error": "Failed to parse JSON",
                    "raw_response": result_text[:1000],
                }

        except Exception as e:
            error_str = str(e).lower()
            error_type = type(e).__name__
            # Check if it's a quota error (429) and we haven't retried too much
            if "429" in str(e) or "quota" in error_str:
                if retry_count < 2:  # Retry up to 2 times
                    import time

                    wait_time = 5 * (retry_count + 1)  # Wait 5, 10 seconds
                    print(
                        f"⚠️  Quota error detected. Waiting {wait_time}s before retry {retry_count + 1}..."
                    )
                    time.sleep(wait_time)
                    return self.extract_with_llm(
                        prompt,
                        model,
                        temperature,
                        max_tokens,
                        response_schema=response_schema,
                        retry_count=retry_count + 1,
                    )
                else:
                    print(f"❌ Error: API quota exceeded after retries.")
                    print(f"   Please check your quota.")
                    print(
                        f"   The system will fall back to pattern-based extraction (limited accuracy)."
                    )
            # Check for timeout / deadline exceeded errors and retry once
            elif (
                "deadline" in error_str
                or "timeout" in error_str
                or "timed out" in error_str
                or error_type == "DeadlineExceeded"
            ):
                if retry_count < 1:
                    import time

                    print(
                        f"⚠️  API call timed out. Retrying (attempt {retry_count + 1})..."
                    )
                    time.sleep(3)
                    return self.extract_with_llm(
                        prompt,
                        model,
                        temperature,
                        max_tokens,
                        response_schema=response_schema,
                        retry_count=retry_count + 1,
                    )
                else:
                    print(f"❌ Error: API call timed out after retry.")
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

                if char == "\\":
                    # Escape character
                    result.append(char)
                    escape_next = True
                    i += 1
                    continue

                # Handle double-quote: check in_string first so we can escape inner quotes
                if char == '"' and not escape_next:
                    if in_string:
                        # Look ahead: if next non-space is : , } ] then this " closes the string
                        j = i + 1
                        while j < len(repaired) and repaired[j] in " \t\n\r":
                            j += 1
                        if j < len(repaired) and repaired[j] in ":,}]":
                            result.append(char)
                            in_string = False
                        else:
                            result.append(
                                '\\"'
                            )  # inner quote LLM forgot to escape (flow text_span)
                    else:
                        in_string = True
                        result.append(char)
                    i += 1
                    continue

                if in_string:
                    # We're inside a string - escape special characters
                    if char == "\n":
                        result.append("\\n")
                    elif char == "\r":
                        result.append("\\r")
                    elif char == "\t":
                        result.append("\\t")
                    elif char == "\\":
                        result.append(char)
                        escape_next = True
                    else:
                        result.append(char)
                else:
                    result.append(char)

                i += 1

            repaired = "".join(result)
        except Exception:
            # If state machine fails, fall back to simpler regex-based approach
            # This is a less reliable fallback
            pass

        # Fix missing commas between array elements: }{ -> },{
        repaired = re.sub(r"\}\s*\{", "},{", repaired)

        # Fix trailing commas before } or ]
        repaired = re.sub(r",\s*}", "}", repaired)
        repaired = re.sub(r",\s*]", "]", repaired)

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

    def try_salvage_array(self, raw_text: str):
        """Try to salvage a JSON array from broken LLM output (e.g. flow/compartment lists). Returns list or None."""
        if not raw_text:
            return None
        return self._salvage_json_array(raw_text)

    def is_available(self) -> bool:
        """Check if LLM is available"""
        return self.available

    def extract_with_multimodal(
        self,
        image: Union["Image.Image", bytes, str, Path],
        prompt: str,
        providers: Optional[Union[Literal["both"], list[str]]] = None,
        model: Optional[str] = None,
        temperature: float = 0.3,
        max_tokens: int = 2000,
    ) -> Dict[str, Any]:
        """
        Extract structured data using multimodal LLM with image + text input.

        Args:
            image: PIL Image, bytes, or file path (str/Path)
            prompt: Text prompt to send with the image
            providers: List of providers to use, or "both" for both simultaneously.
                      Options: ["openai"], ["gemini"], ["openai", "gemini"], "both"
            model: Model name (default: provider-specific)
            temperature: Temperature setting
            max_tokens: Max tokens in response

        Returns:
            Dict with results keyed by provider: {"openai": ..., "gemini": ...}
            Each value is either the parsed JSON response or {"error": ...} on failure.
        """
        if providers is None:
            providers = [self.provider]
        elif providers == "both":
            providers = ["openai", "gemini"]
        elif isinstance(providers, str):
            providers = [providers]

        providers = [p.lower() for p in providers]

        results: Dict[str, Any] = {}

        def run_openai() -> Dict[str, Any]:
            openai_key = self._load_api_key(None, None, "openai")
            if not openai_key:
                raise RuntimeError(
                    "OpenAI API key not provided. Set OPENAI_API_KEY or add 'openai:sk-...' to .api_key.txt"
                )

            try:
                import openai
            except ImportError:
                raise RuntimeError(
                    "openai package not installed. Install with: pip install openai"
                )

            client = openai.OpenAI(api_key=openai_key)

            img = self._prepare_image(image)
            if img is None:
                raise RuntimeError("Failed to load image")

            b64_image = base64.b64encode(img).decode("utf-8")
            # img bytes are always re-saved as PNG by _prepare_image (via PIL)
            data_url = f"data:image/png;base64,{b64_image}"

            model_name = model or os.getenv("OPENAI_MODEL", "gpt-5.2")

            messages = [
                {
                    "role": "user",
                    "content": [
                        {"type": "text", "text": prompt},
                        {"type": "image_url", "image_url": {"url": data_url}},
                    ],
                }
            ]

            response = client.chat.completions.create(
                model=model_name,
                messages=messages,
                temperature=temperature,
                max_tokens=max_tokens,
            )

            result_text = response.choices[0].message.content.strip()
            return self._parse_json_response(result_text, prompt)

        def run_gemini() -> Dict[str, Any]:
            gemini_key = self._load_api_key(None, None, "gemini")
            if not gemini_key:
                raise RuntimeError(
                    "Gemini API key not provided. Set GEMINI_API_KEY or add 'gemini:AIza...' to .api_key.txt"
                )

            try:
                from google import genai as _genai
                from google.genai import types as _gtypes
            except ImportError:
                raise RuntimeError(
                    "google-genai package not installed. Install with: pip install google-genai"
                )

            model_name = model or os.getenv("GEMINI_MODEL", "gemini-3-flash-preview")

            # Load image as raw bytes — new SDK uses Part.from_bytes
            raw_bytes = self._prepare_image_for_gemini(image)
            if raw_bytes is None:
                raise RuntimeError("Failed to load image for Gemini")

            # Detect mime type from bytes header
            if isinstance(raw_bytes, bytes):
                if raw_bytes[:3] == b'\xff\xd8\xff':
                    mime_type = "image/jpeg"
                elif raw_bytes[:8] == b'\x89PNG\r\n\x1a\n':
                    mime_type = "image/png"
                else:
                    mime_type = "image/png"  # safe default
                image_part = _gtypes.Part.from_bytes(data=raw_bytes, mime_type=mime_type)
            else:
                # PIL Image object — convert to PNG bytes first
                buf = io.BytesIO()
                raw_bytes.save(buf, format="PNG")
                image_part = _gtypes.Part.from_bytes(data=buf.getvalue(), mime_type="image/png")

            client = _genai.Client(api_key=gemini_key)
            response = client.models.generate_content(
                model=model_name,
                contents=[image_part, prompt],
                config=_gtypes.GenerateContentConfig(
                    temperature=temperature,
                    max_output_tokens=max_tokens,
                    safety_settings=[
                        _gtypes.SafetySetting(category="HARM_CATEGORY_HARASSMENT",       threshold="BLOCK_NONE"),
                        _gtypes.SafetySetting(category="HARM_CATEGORY_HATE_SPEECH",       threshold="BLOCK_NONE"),
                        _gtypes.SafetySetting(category="HARM_CATEGORY_SEXUALLY_EXPLICIT", threshold="BLOCK_NONE"),
                        _gtypes.SafetySetting(category="HARM_CATEGORY_DANGEROUS_CONTENT", threshold="BLOCK_NONE"),
                    ],
                ),
            )

            result_text = response.text.strip()
            return self._parse_json_response(result_text, prompt)

        def _parse_json_response(result_text: str, prompt: str) -> Dict[str, Any]:
            if "```json" in result_text:
                result_text = result_text.split("```json")[1].split("```")[0].strip()
            elif "```" in result_text:
                result_text = result_text.split("```")[1].split("```")[0].strip()

            try:
                return json.loads(result_text)
            except json.JSONDecodeError:
                repaired = self._repair_json(result_text)
                if repaired:
                    try:
                        return json.loads(repaired)
                    except json.JSONDecodeError:
                        pass
                return {
                    "error": "Failed to parse JSON",
                    "raw_response": result_text[:1000],
                }

        if len(providers) == 1:
            p = providers[0]
            if p == "openai":
                try:
                    results["openai"] = run_openai()
                except Exception as e:
                    results["openai"] = {"error": str(e)}
            elif p == "gemini":
                try:
                    results["gemini"] = run_gemini()
                except Exception as e:
                    results["gemini"] = {"error": str(e)}
            else:
                raise ValueError(f"Unknown provider: {p}")
        else:
            with ThreadPoolExecutor(max_workers=len(providers)) as executor:
                futures = {}
                if "openai" in providers:
                    futures[executor.submit(run_openai)] = "openai"
                if "gemini" in providers:
                    futures[executor.submit(run_gemini)] = "gemini"

                for future in as_completed(futures):
                    p = futures[future]
                    try:
                        results[p] = future.result()
                    except Exception as e:
                        results[p] = {"error": str(e)}

        return results

    def _prepare_image(
        self, image: Union["Image.Image", bytes, str, Path]
    ) -> Optional[bytes]:
        """Prepare image as PNG bytes for OpenAI"""
        try:
            if Image is None:
                raise RuntimeError(
                    "PIL not installed. Install with: pip install Pillow"
                )

            if isinstance(image, bytes):
                img = Image.open(io.BytesIO(image))
            elif isinstance(image, (str, Path)):
                img = Image.open(image)
            elif hasattr(image, "convert"):
                img = image
            else:
                return None

            if img.mode != "RGB":
                img = img.convert("RGB")

            buf = io.BytesIO()
            img.save(buf, format="PNG")
            return buf.getvalue()
        except Exception as e:
            print(f"Error preparing image: {e}")
            return None

    def _prepare_image_for_gemini(
        self, image: Union["Image.Image", bytes, str, Path]
    ) -> Optional[any]:
        """Prepare image for Gemini (PIL.Image or raw bytes)"""
        try:
            if Image is None:
                raise RuntimeError(
                    "PIL not installed. Install with: pip install Pillow"
                )

            if isinstance(image, bytes):
                return image
            elif isinstance(image, (str, Path)):
                with open(image, "rb") as f:
                    return f.read()
            elif hasattr(image, "convert"):
                return image
            else:
                return None
        except Exception as e:
            print(f"Error preparing image for Gemini: {e}")
            return None
