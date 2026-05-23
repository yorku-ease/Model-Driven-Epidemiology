import sys
from llm_client import LLMClient

print("Initializing Gemini client...")
client = LLMClient(provider="gemini", api_key="test-api-key-so-we-dont-error-out")
print("Client initialized successfully.")
