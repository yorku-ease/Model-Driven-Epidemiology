import sys
import argparse
import os
from pathlib import Path

# Add phase 2 to path so we can import llm_client
repo_root = Path(__file__).parent.parent
phase2_dir = repo_root / "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY" / "phase 2"
sys.path.append(str(phase2_dir))

from src.utils.llm_client import LLMClient

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--error", required=True)
    parser.add_argument("--provider", default="gemini")
    parser.add_argument("--fast", action="store_true", help="Use faster LLM model")
    args = parser.parse_args()

    provider = args.provider.lower()
    error_str = args.error
    if os.path.exists(args.error):
        with open(args.error, 'r', encoding='utf-8') as f:
            error_str = f.read()

    key_file = repo_root / ".keys" / f"{provider}.key"
    api_key = None
    if key_file.exists():
        api_key = key_file.read_text().strip()

    client = LLMClient(api_key=api_key, provider=provider)
    if not client.is_available():
        print(f"Error: LLM client for {provider} could not be initialized.")
        sys.exit(1)

    system_prompt = f"""You are an expert Eclipse plugin developer and Sirius Modeling expert. 
A user has encountered an error in their Eclipse environment. Please explain what this error means in plain English, and provide a likely solution to fix it. Keep it concise.

IMPORTANT: You MUST format your response as a valid HTML snippet suitable for an embedded browser. 
Use tags like <p>, <ul>, <li>, <b>, <code>. 
For code blocks, use <pre style="background:#f4f4f5; padding:10px; border-radius:5px; overflow-x:auto;"><code>...</code></pre>.
DO NOT wrap your response in ```html markdown blocks. Output raw HTML only.

Error Log & Context:
{error_str}"""

    try:
        if args.fast and hasattr(client, 'generate_text_fast'):
            result = client.generate_text_fast(system_prompt, max_tokens=2048, temperature=0.2)
        else:
            result = client.generate_text(system_prompt, max_tokens=2048, temperature=0.2)
            
        clean_result = result.strip()
        if clean_result.startswith("```html"):
            clean_result = clean_result[7:]
        elif clean_result.startswith("```"):
            clean_result = clean_result[3:]
        if clean_result.endswith("```"):
            clean_result = clean_result[:-3]
            
        print(clean_result.strip())
    except Exception as e:
        print(f"Error communicating with LLM: {str(e)}")
        sys.exit(1)

if __name__ == "__main__":
    main()
