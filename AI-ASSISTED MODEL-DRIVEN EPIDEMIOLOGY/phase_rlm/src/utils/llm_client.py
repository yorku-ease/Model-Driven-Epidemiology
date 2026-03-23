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
        api_key_file: Optional[str] = None,
    ):
        self.provider: str = provider.lower()
        self.model = model
        self.api_key_file = api_key_file
        self._client = None
        self.available = False
        self._init_client()

    def _init_client(self):
        """Initialize the client."""
        self._client = Phase2LLMClient(
            provider=self.provider,
            api_key_file=self.api_key_file,
            flash_model=self.model,
        )
        self.available = self._client.available if self._client else False

        if self.available:
            print(
                f"[RLM] LLM client: provider={self.provider}, "
                f"flash_model={self.model}"
            )
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
        api_key_file=config.get("api_key_file"),
    )
