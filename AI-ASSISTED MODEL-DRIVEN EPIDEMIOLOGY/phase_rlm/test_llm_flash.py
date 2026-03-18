import sys

sys.path.insert(0, "src")

from utils.llm import LLMClient

with open(".api_key.txt") as f:
    keys = dict(line.strip().split(":", 1) for line in f)

print("=" * 60)
print("Testing LLM Flash Models")
print("=" * 60)

print("\n--- OpenAI Flash (gpt-5.4-mini) ---")
openai_flash = LLMClient(provider="openai", api_key=keys["openai"])
print(f"Available: {openai_flash.is_available()}")
if openai_flash.is_available():
    result = openai_flash.extract_with_llm_flash("What is 2+2? Answer in one word.")
    print(f"Response: {result}")

print("\n--- OpenAI Pro (gpt-5.4) ---")
openai_pro = LLMClient(provider="openai", api_key=keys["openai"])
print(f"Available: {openai_pro.is_available()}")
if openai_pro.is_available():
    result = openai_pro.extract_with_llm("What is 2+2? Answer in one word.")
    print(f"Response: {result}")

print("\n--- Gemini Flash (gemini-2.5-flash) ---")
gemini_flash = LLMClient(provider="gemini", api_key=keys["gemini"])
print(f"Available: {gemini_flash.is_available()}")
if gemini_flash.is_available():
    result = gemini_flash.extract_with_llm_flash("What is 2+2? Answer in one word.")
    print(f"Response: {result}")

print("\n--- Gemini Pro (gemini-2.5-pro) ---")
gemini_pro = LLMClient(provider="gemini", api_key=keys["gemini"])
print(f"Available: {gemini_pro.is_available()}")
if gemini_pro.is_available():
    result = gemini_pro.extract_with_llm("What is 2+2? Answer in one word.")
    print(f"Response: {result}")

print("\n" + "=" * 60)
print("All tests completed!")
print("=" * 60)
