"""
LLM Client for Phase RLM

Wrapper for the new LLM client in llm.py.
"""

from typing import Optional, Literal, Any

from .llm import LLMClient as Phase2LLMClient


class RLMClient:
    """Wrapper for Phase 2 LLM client."""

    def __init__(
        self,
        provider: Literal["openai", "gemini"] = "gemini",
        model: str = "gemini-2.5-flash",
    ):
        self.provider: str = provider.lower()
        self.model = model
        self._client = None
        self.available = False
        self._init_client()

    def _init_client(self):
        """Initialize the client."""
        self._client = Phase2LLMClient(provider=self.provider)
        self.available = self._client.available if self._client else False

        if self.available:
            print(f"[RLM] Phase 2 LLM client initialized with provider {self.provider}")
        else:
            print(f"[RLM] Warning: LLM client not available")

    def generate(
        self,
        prompt: str,
        system_instruction: str = "",
        temperature: float = 0.3,
        max_tokens: int = 2000,
    ):
        """Generate text from LLM using pro model."""
        if not self.available:
            raise RuntimeError("LLM client not available")

        full_prompt = (
            f"{system_instruction}\n\n{prompt}" if system_instruction else prompt
        )

        return self._client.extract_with_llm(
            prompt=full_prompt,
            temperature=temperature,
            max_output_tokens=max_tokens,
        )

    def generate_flash(
        self,
        prompt: str,
        system_instruction: str = "",
        temperature: float = 0.3,
        max_tokens: int = 2000,
    ):
        """Generate text from LLM using flash model (faster, cheaper)."""
        if not self.available:
            raise RuntimeError("LLM client not available")

        full_prompt = (
            f"{system_instruction}\n\n{prompt}" if system_instruction else prompt
        )

        return self._client.extract_with_llm_flash(
            prompt=full_prompt,
            temperature=temperature,
            max_output_tokens=max_tokens,
        )


def create_llm_client(config):
    """Factory function to create LLM client."""
    return RLMClient(
        provider=config.get("llm_provider", "gemini"),
        model=config.get("llm_model", "gemini-2.5-flash"),
    )
