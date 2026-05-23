"""LLM client wrapper for OpenAI and Gemini APIs"""

import os
import re
from pathlib import Path
from typing import Optional, Dict, Any, Literal
import json


class LLMClient:
    """Wrapper for OpenAI, Gemini, and Claude API clients with API key management"""

    def __init__(
        self,
        api_key: Optional[str] = None,
        api_key_file: Optional[str] = None,
        provider: Literal["openai", "gemini", "claude"] = "openai",
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
            if self.provider == "claude" and not key.startswith("sk-"):
                # Anthropic keys typically start with "sk-ant-"
                print(
                    "Warning: API key does not look like an Anthropic key for provider 'claude'. "
                    "Use 'claude:sk-...' or set ANTHROPIC_API_KEY. Falling back to pattern-based extraction."
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
                    import warnings
                    with warnings.catch_warnings():
                        warnings.simplefilter("ignore", category=FutureWarning)
                        import google.generativeai as genai

                    genai.configure(api_key=self.api_key)
                    self.client = genai
                    self.available = True
                except ImportError:
                    print(
                        "Warning: google-generativeai package not installed. Install with: pip install google-generativeai"
                    )
                except Exception as e:
                    print(f"Warning: Failed to initialize Gemini client: {e}")
            elif self.provider == "claude":
                try:
                    import anthropic

                    self.client = anthropic.Anthropic(api_key=self.api_key)
                    self.available = True
                except ImportError:
                    print(
                        "Warning: anthropic package not installed. Install with: pip install anthropic"
                    )
                except Exception as e:
                    print(f"Warning: Failed to initialize Claude client: {e}")
            else:
                print(
                    f"Warning: Unknown provider '{self.provider}'. Supported: 'openai', 'gemini', 'claude'"
                )

    def _load_api_key(
        self, api_key: Optional[str], api_key_file: Optional[str], provider: str
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
        elif provider == "claude":
            env_key = os.getenv("ANTHROPIC_API_KEY")
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
                claude_key = None
                with open(api_key_path, "r") as f:
                    for line in f:
                        line = line.strip()
                        if not line or line.startswith("#"):
                            continue
                        # Prefixed line: "openai:sk-..." or "gemini:AIza..."
                        if ":" in line and line.split(":")[0].lower() in [
                            "openai",
                            "gemini",
                            "claude",
                        ]:
                            prefix, key = line.split(":", 1)
                            key = key.strip().strip(
                                "\ufeff"
                            )  # strip BOM and whitespace
                            if prefix.lower() == "openai" and key:
                                openai_key = key
                            elif prefix.lower() == "gemini" and key:
                                gemini_key = key
                            elif prefix.lower() == "claude" and key:
                                claude_key = key
                            continue
                        # No prefix: infer from key shape
                        if line.startswith("sk-"):
                            # Heuristic: Anthropic keys usually start with "sk-ant-"; others are OpenAI.
                            if line.startswith("sk-ant-"):
                                claude_key = line
                            else:
                                openai_key = line
                        elif line.startswith("AIza"):
                            gemini_key = line
                if provider == "openai":
                    return openai_key
                if provider == "gemini":
                    return gemini_key
                if provider == "claude":
                    return claude_key
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
                provider_pkg = "google-generativeai"
            elif self.provider == "claude":
                provider_pkg = "anthropic"
            else:
                provider_pkg = "openai"
            raise RuntimeError(
                f"LLM not available. Check API key and {provider_pkg} package installation."
            )

        # Set default model if not provided; allow environment variable overrides
        if model is None:
            if self.provider == "openai":
                # Default to gpt-5.4-mini; override with OPENAI_MODEL env var if needed
                model = os.getenv("OPENAI_MODEL", "gpt-5.4-mini")
            elif self.provider == "gemini":
                # e.g. export GEMINI_MODEL=gemini-2.5-flash or gemini-2.5-pro
                model = os.getenv("GEMINI_MODEL", "gemini-2.5-pro")
            elif self.provider == "claude":
                # CLAUDE_MODEL can be set by user. If unset, we try fallback IDs (API model list varies by region/plan).
                _claude_env = os.getenv("CLAUDE_MODEL", "").strip()
                if _claude_env:
                    _claude_models_to_try = [_claude_env]
                else:
                    _claude_models_to_try = [
                        "claude-opus-4-6",
                        "claude-sonnet-4",
                        "claude-3-5-sonnet-20241022",
                        "claude-3-haiku-20240307",
                        "claude-3-opus-20240229",
                    ]
                model = _claude_models_to_try[0]  # for first attempt

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
                response = self.client.responses.create(
                    model=model,
                    input=[
                        {
                            "role": "user",
                            "content": [{"type": "input_text", "text": prompt}],
                        }
                    ],
                    temperature=temperature,
                    max_output_tokens=max_tokens,
                )
                result_text = response.output_text.strip()

            elif self.provider == "gemini":
                # Use the same system instruction prepended to prompt (Gemini doesn't have separate system messages)
                full_prompt = f"{system_instruction}\n\n{prompt}"
                # For extraction tasks with schema, use lower temperature (best practice for reliability)
                gemini_temp = 0.2 if response_schema else temperature
                gen_config = {
                    "temperature": gemini_temp,
                    "max_output_tokens": max_tokens,
                }
                if response_schema:
                    # Structured output: guarantees valid JSON matching schema (reduces parse failures and drift)
                    gen_config = self.client.types.GenerationConfig(
                        temperature=gemini_temp,
                        max_output_tokens=max_tokens,
                        response_mime_type="application/json",
                        response_schema=response_schema,
                    )
                gen_model = self.client.GenerativeModel(model)
                response = gen_model.generate_content(
                    full_prompt,
                    generation_config=gen_config,
                    safety_settings=[
                        {
                            "category": "HARM_CATEGORY_HARASSMENT",
                            "threshold": "BLOCK_NONE",
                        },
                        {
                            "category": "HARM_CATEGORY_HATE_SPEECH",
                            "threshold": "BLOCK_NONE",
                        },
                        {
                            "category": "HARM_CATEGORY_SEXUALLY_EXPLICIT",
                            "threshold": "BLOCK_NONE",
                        },
                        {
                            "category": "HARM_CATEGORY_DANGEROUS_CONTENT",
                            "threshold": "BLOCK_NONE",
                        },
                    ],
                    request_options={
                        "timeout": 120
                    },  # 2-minute timeout to avoid hanging
                )
                # Handle Gemini response - check for blocked content
                if hasattr(response, "candidates") and response.candidates:
                    candidate = response.candidates[0]
                    # Check finish reason (2 = SAFETY, 3 = RECITATION, etc.)
                    if hasattr(candidate, "finish_reason"):
                        if candidate.finish_reason == 2:  # SAFETY
                            # Try to get the text anyway, or use flash model as fallback
                            if hasattr(candidate, "content") and candidate.content:
                                if (
                                    hasattr(candidate.content, "parts")
                                    and candidate.content.parts
                                ):
                                    result_text = "".join(
                                        [
                                            part.text
                                            for part in candidate.content.parts
                                            if hasattr(part, "text")
                                        ]
                                    ).strip()
                                else:
                                    # Safety blocked - try flash model as fallback
                                    print(
                                        f"⚠️  Gemini Pro blocked by safety filters. Trying Flash model..."
                                    )
                                    if model != "gemini-2.5-flash":
                                        return self.extract_with_llm(
                                            prompt,
                                            model="gemini-2.5-flash",
                                            temperature=temperature,
                                            max_tokens=max_tokens,
                                            response_schema=response_schema,
                                            retry_count=retry_count,
                                        )
                                    raise ValueError(
                                        "Gemini content blocked by safety filters and Flash also unavailable"
                                    )
                            else:
                                raise ValueError(
                                    "Gemini content blocked by safety filters"
                                )
                        elif candidate.finish_reason == 3:  # RECITATION
                            raise ValueError(
                                "Gemini blocked content due to recitation policy"
                            )

                # Try to get text from response
                try:
                    result_text = response.text.strip()
                except AttributeError:
                    # Fallback: try to extract from candidates
                    if hasattr(response, "candidates") and response.candidates:
                        candidate = response.candidates[0]
                        if hasattr(candidate, "content") and candidate.content:
                            if (
                                hasattr(candidate.content, "parts")
                                and candidate.content.parts
                            ):
                                result_text = "".join(
                                    [
                                        part.text
                                        for part in candidate.content.parts
                                        if hasattr(part, "text")
                                    ]
                                ).strip()
                            else:
                                # Check for blocked content
                                if hasattr(response, "prompt_feedback"):
                                    raise ValueError(
                                        f"Gemini blocked content: {response.prompt_feedback}"
                                    )
                                raise ValueError("Gemini returned empty response")
                        else:
                            raise ValueError("Gemini returned empty response")
                    else:
                        if hasattr(response, "prompt_feedback"):
                            raise ValueError(
                                f"Gemini blocked content: {response.prompt_feedback}"
                            )
                        raise ValueError("Gemini returned empty response")
            elif self.provider == "claude":
                # Anthropic Claude: default max output is 4096 tokens per model limit.
                claude_max = min(max_tokens, 4096)
                messages_client = self.client
                response = None
                last_error = None
                for try_model in _claude_models_to_try:
                    try:
                        response = messages_client.messages.create(
                            model=try_model,
                            max_tokens=claude_max,
                            temperature=temperature,
                            system=system_instruction,
                            messages=[{"role": "user", "content": prompt}],
                        )
                        break
                    except Exception as e:
                        err_str = str(e).lower()
                        # 401 / authentication_error: key invalid or missing
                        if (
                            "401" in err_str
                            or "authentication_error" in err_str
                            or "invalid x-api-key" in err_str
                        ):
                            print("  ⚠ Claude authentication failed (invalid API key).")
                            print(
                                "  💡 Get a key from https://console.anthropic.com and either:"
                            )
                            print("     - Set env: export ANTHROPIC_API_KEY=sk-ant-...")
                            print(
                                "     - Or in .api_key.txt use a line: claude:sk-ant-..."
                            )
                            raise
                        # 404 / not_found: try next model in list
                        if (
                            "404" in err_str
                            or "not_found" in err_str
                            or "not found" in err_str
                        ):
                            last_error = e
                            if try_model != _claude_models_to_try[-1]:
                                print(
                                    f"  ⚠ Claude model '{try_model}' not available, trying next..."
                                )
                                continue
                        raise
                if response is None and last_error is not None:
                    print(
                        "  💡 Set CLAUDE_MODEL to a model ID from your Anthropic console (e.g. export CLAUDE_MODEL=claude-opus-4-6)"
                    )
                    raise last_error
                # Concatenate text from content blocks (skip thinking/tool_use; handle dict or object blocks)
                parts = []
                content = getattr(response, "content", []) or []
                for block in content:
                    if isinstance(block, dict):
                        btype, text = block.get("type"), block.get("text", "")
                    else:
                        btype = getattr(block, "type", None)
                        text = getattr(block, "text", "") or ""
                    if btype == "text" and text:
                        parts.append(text)
                result_text = "".join(parts).strip()
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

                # Try to salvage common truncated single-object outputs
                # (e.g., {"value": 0.2, "unit": "per day", "reasoning ...)
                salvaged_obj = self._salvage_partial_object(result_text)
                if salvaged_obj is not None:
                    return salvaged_obj

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

    def _salvage_partial_object(self, text: str) -> Optional[Dict[str, Any]]:
        """
        Best-effort salvage for truncated JSON object responses.
        Handles common inference payloads used in Phase 3:
          - {"value": <num>, "unit": "...", "reasoning": "..."}
          - {"primary_name": "...", ...}
          - {"flow_type": "RateFlow|ContactFlow", "description": "...", ...}
        """
        if not text:
            return None

        s = text.strip()
        if not s.startswith("{"):
            return None

        out: Dict[str, Any] = {}

        # Numeric value
        m_val = re.search(r'"value"\s*:\s*([-+]?[0-9]*\.?[0-9]+(?:[eE][-+]?\d+)?)', s)
        if m_val:
            try:
                out["value"] = float(m_val.group(1))
            except ValueError:
                pass

        # Unit (possibly truncated; capture safely until next quote if available)
        m_unit = re.search(r'"unit"\s*:\s*"([^"]*)', s)
        if m_unit:
            unit = m_unit.group(1).strip()
            if unit:
                out["unit"] = unit

        # Optional reasoning/description/name fields (allow truncated strings)
        m_reason = re.search(r'"reasoning"\s*:\s*"([^"]*)', s)
        if m_reason:
            out["reasoning"] = m_reason.group(1).strip()

        m_desc = re.search(r'"description"\s*:\s*"([^"]*)', s)
        if m_desc:
            out["description"] = m_desc.group(1).strip()

        m_pname = re.search(r'"primary_name"\s*:\s*"([^"]*)', s)
        if m_pname:
            pname = m_pname.group(1).strip()
            if pname:
                out["primary_name"] = pname

        m_ftype = re.search(r'"flow_type"\s*:\s*"([^"]*)', s)
        if m_ftype:
            ftype = m_ftype.group(1).strip()
            if ftype:
                out["flow_type"] = ftype

        if not out:
            return None
        return out

    def try_salvage_array(self, raw_text: str):
        """Try to salvage a JSON array from broken LLM output (e.g. flow/compartment lists). Returns list or None."""
        if not raw_text:
            return None
        return self._salvage_json_array(raw_text)

    def is_available(self) -> bool:
        """Check if LLM is available"""
        return self.available

    def generate_text(self, prompt: str, model: Optional[str] = None, temperature: float = 0.3, max_tokens: int = 2000) -> str:
        """Generate raw text from LLM."""
        if not self.available:
            raise RuntimeError("LLM not available.")
            
        if model is None:
            if self.provider == "openai":
                model = os.getenv("OPENAI_MODEL", "gpt-4o-mini")
            elif self.provider == "gemini":
                model = os.getenv("GEMINI_MODEL", "gemini-2.5-pro")
            elif self.provider == "claude":
                model = os.getenv("CLAUDE_MODEL", "claude-3-5-sonnet-20241022")

        if self.provider == "openai":
            response = self.client.responses.create(
                model=model,
                input=[{"role": "user", "content": [{"type": "input_text", "text": prompt}]}],
                temperature=temperature,
                max_output_tokens=max_tokens,
            )
            return response.output_text.strip()
        elif self.provider == "gemini":
            gen_model = self.client.GenerativeModel(model)
            safety_settings = [
                {"category": "HARM_CATEGORY_HARASSMENT", "threshold": "BLOCK_NONE"},
                {"category": "HARM_CATEGORY_HATE_SPEECH", "threshold": "BLOCK_NONE"},
                {"category": "HARM_CATEGORY_SEXUALLY_EXPLICIT", "threshold": "BLOCK_NONE"},
                {"category": "HARM_CATEGORY_DANGEROUS_CONTENT", "threshold": "BLOCK_NONE"}
            ]
            response = gen_model.generate_content(
                prompt,
                generation_config={"temperature": temperature, "max_output_tokens": max_tokens},
                safety_settings=safety_settings
            )
            try:
                return response.text.strip()
            except Exception as e:
                if model != "gemini-2.5-flash":
                    print(f"⚠️  Gemini blocked by safety filters or hit an error. Trying Flash model...")
                    return self.generate_text(prompt, model="gemini-2.5-flash", temperature=temperature, max_tokens=max_tokens)
                    
                # Fallback if flash also fails
                if getattr(response, "candidates", None) and response.candidates:
                    cand = response.candidates[0]
                    if getattr(cand, "content", None) and getattr(cand.content, "parts", None):
                        return "".join(getattr(part, "text", "") for part in cand.content.parts).strip()
                return ""
        elif self.provider == "claude":
            claude_max = min(max_tokens, 4096)
            response = self.client.messages.create(
                model=model,
                max_tokens=claude_max,
                temperature=temperature,
                messages=[{"role": "user", "content": prompt}]
            )
            parts = []
            for block in (getattr(response, "content", []) or []):
                text = getattr(block, "text", "") or ""
                if text: parts.append(text)
            return "".join(parts).strip()
        return ""

    def extract_with_llm_fast(
        self,
        prompt: str,
        temperature: float = 0.3,
        max_tokens: int = 2000,
        response_schema: Optional[Dict[str, Any]] = None,
        retry_count: int = 0,
    ) -> Dict[str, Any]:
        """
        Extract structured data using a faster, more lightweight LLM (e.g., flash models).
        """
        model = None
        if self.provider == "openai":
            model = os.getenv("OPENAI_FAST_MODEL", "gpt-4o-mini")
        elif self.provider == "gemini":
            model = os.getenv("GEMINI_FAST_MODEL", "gemini-2.5-flash")
        elif self.provider == "claude":
            model = os.getenv("CLAUDE_FAST_MODEL", "claude-3-haiku-20240307")
            
        return self.extract_with_llm(
            prompt=prompt,
            model=model,
            temperature=temperature,
            max_tokens=max_tokens,
            response_schema=response_schema,
            retry_count=retry_count,
        )

    def generate_text_fast(
        self, 
        prompt: str, 
        temperature: float = 0.3, 
        max_tokens: int = 2000
    ) -> str:
        """
        Generate raw text using a faster, more lightweight LLM (e.g., flash models).
        """
        model = None
        if self.provider == "openai":
            model = os.getenv("OPENAI_FAST_MODEL", "gpt-4o-mini")
        elif self.provider == "gemini":
            model = os.getenv("GEMINI_FAST_MODEL", "gemini-2.5-flash")
        elif self.provider == "claude":
            model = os.getenv("CLAUDE_FAST_MODEL", "claude-3-haiku-20240307")
            
        return self.generate_text(
            prompt=prompt,
            model=model,
            temperature=temperature,
            max_tokens=max_tokens,
        )
