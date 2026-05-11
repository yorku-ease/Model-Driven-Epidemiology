"""
Run the hanta virus prompt through an LLM using the phase 2 LLM client.
Usage:
    python run_prompt.py --provider openai
    python run_prompt.py --provider gemini
    python run_prompt.py --provider claude

Make sure .api_key.txt has your key first, e.g.:
    gemini:AIza...
    openai:sk-...
    claude:sk-ant-...
"""
import sys
import json
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent.parent / "phase 2"))
from src.utils.llm_client import LLMClient

HERE = Path(__file__).parent
PROMPT_FILE = HERE / "prompt.txt"
OUTPUT_DIR = HERE / "outputs"

def main():
    import argparse
    parser = argparse.ArgumentParser(description="Run hanta virus prompt through LLM")
    parser.add_argument("--provider", default="gemini", choices=["openai", "gemini", "claude"])
    parser.add_argument("--api-key-file", default=str(Path(__file__).parent.parent / "phase 2" / ".api_key.txt"))
    parser.add_argument("--output", default=str(OUTPUT_DIR / "llm_response.json"))
    args = parser.parse_args()

    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    print(f"Reading prompt from {PROMPT_FILE}...")
    prompt = PROMPT_FILE.read_text(encoding="utf-8")
    print(f"Prompt length: {len(prompt)} chars")

    print(f"Initializing LLM (provider={args.provider})...")
    llm = LLMClient(api_key_file=args.api_key_file, provider=args.provider)

    if not llm.available:
        print("ERROR: LLM not available. Check your API key.")
        sys.exit(1)

    print("Sending to LLM...")
    result = llm.extract_with_llm(
        prompt=prompt,
        temperature=0.3,
        max_tokens=8192,
    )
    print(f"Response type: {type(result).__name__}")

    output_path = Path(args.output)
    output_path.write_text(
        json.dumps(result, indent=2, ensure_ascii=False),
        encoding="utf-8",
    )
    print(f"Saved to {output_path}")

    # Also save raw text version for readability
    text_path = output_path.with_suffix(".txt")
    if isinstance(result, dict):
        text_path.write_text(json.dumps(result, indent=2, ensure_ascii=False), encoding="utf-8")
    else:
        text_path.write_text(str(result), encoding="utf-8")
    print(f"Also saved readable version to {text_path}")

if __name__ == "__main__":
    main()
