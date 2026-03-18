"""
Multimodal LLM client:
- OpenAI (gpt-5.2)
- Gemini (gemini-2.5-pro)

Supports:
- Text input
- Local image input
- Image URL input (OpenAI)
- Raw text/XML output
"""

import os
import base64
from typing import Optional, Literal

import mimetypes


class LLMClient:
    def __init__(
        self,
        provider: Literal["openai", "gemini"] = "openai",
        api_key: Optional[str] = None,
    ):
        self.provider = provider.lower()
        self.api_key = api_key or self._load_api_key()
        self.client = None
        self.available = False

        if not self.api_key:
            return

        if self.provider == "openai":
            from openai import OpenAI

            self.client = OpenAI(api_key=self.api_key)
            self.available = True

        elif self.provider == "gemini":
            from google import genai

            self.client = genai.Client(api_key=self.api_key)
            self.available = True

    def _load_api_key(self) -> Optional[str]:
        if self.provider == "openai":
            return os.getenv("OPENAI_API_KEY")
        return os.getenv("GEMINI_API_KEY")

    def extract_with_llm(
        self,
        prompt: str,
        image_path: Optional[str] = None,
        image_url: Optional[str] = None,
        temperature: float = 0.2,
        max_output_tokens: int = 2048,
    ) -> str:
        if not self.available:
            raise RuntimeError("LLM client not initialized")

        if self.provider == "openai":
            result_text = self._openai_call(
                prompt,
                image_path,
                image_url,
                temperature,
                max_output_tokens,
            )
        else:
            result_text = self._gemini_call(
                prompt,
                image_path,
                temperature,
                max_output_tokens,
            )

        return result_text

    def _openai_call(
        self,
        prompt: str,
        image_path: Optional[str],
        image_url: Optional[str],
        temperature: float,
        max_output_tokens: int,
    ) -> str:
        input_content = [{"type": "input_text", "text": prompt}]

        if image_path:
            with open(image_path, "rb") as f:
                base64_image = base64.b64encode(f.read()).decode("utf-8")

            input_content.append(
                {
                    "type": "input_image",
                    "image_url": f"data:image/png;base64,{base64_image}",
                }
            )

        if image_url:
            input_content.append(
                {
                    "type": "input_image",
                    "image_url": image_url,
                }
            )

        response = self.client.responses.create(
            model="gpt-5.2",
            input=[
                {
                    "role": "user",
                    "content": input_content,
                }
            ],
            temperature=temperature,
            max_output_tokens=max_output_tokens,
        )

        return response.output_text.strip()

    def _gemini_call(
        self,
        prompt: str,
        image_path: Optional[str],
        temperature: float,
        max_output_tokens: int,
    ) -> str:
        parts = [{"text": prompt}]

        if image_path:
            with open(image_path, "rb") as f:
                image_bytes = f.read()
            mime_type, _ = mimetypes.guess_type(image_path)
            mime_type = mime_type or "image/png"

            parts.append(
                {
                    "inline_data": {
                        "mime_type": mime_type,
                        "data": base64.b64encode(image_bytes).decode("utf-8"),
                    }
                }
            )

        response = self.client.models.generate_content(
            model="gemini-2.5-pro",
            contents=[{"role": "user", "parts": parts}],
        )

        if not response.candidates:
            return ""

        candidate = response.candidates[0]

        if not getattr(candidate, "content", None):
            return ""

        if not getattr(candidate.content, "parts", None):
            return ""

        text_parts = [
            p.text for p in candidate.content.parts if hasattr(p, "text") and p.text
        ]

        return "".join(text_parts)

    def is_available(self) -> bool:
        return self.available
