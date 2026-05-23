import sys
import argparse
import os
from pathlib import Path

# Add phase 2 to path so we can import llm_client
repo_root = Path(__file__).parent.parent
phase2_dir = repo_root / "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY" / "phase 2"
sys.path.append(str(phase2_dir))

from src.utils.llm_client import LLMClient

def extract_text(file_path):
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            return f.read()
    except Exception as e:
        return f"<Error reading file as text: {str(e)}>"

def main():
    parser = argparse.ArgumentParser(description="EpiMDE LLM Explain Element")
    parser.add_argument("--prompt", required=True)
    parser.add_argument("--provider", default="gemini")
    parser.add_argument("--attach", action="append", default=[])
    args = parser.parse_args()

    provider = args.provider.lower()
    
    prompt_str = args.prompt
    if os.path.exists(args.prompt):
        with open(args.prompt, 'r', encoding='utf-8') as f:
            prompt_str = f.read()

    attachments_context = ""
    has_context_history = False
    for attachment in args.attach:
        if "::" in attachment:
            alias, path = attachment.split("::", 1)
        else:
            alias = "Attachment"
            path = attachment
            
        if os.path.exists(path):
            if alias.startswith("ContextHistory"):
                has_context_history = True
            text = extract_text(path)
            attachments_context += f"=== ATTACHED FILE: {alias} ===\n{text}\n=====================\n\n"

    key_file = repo_root / ".keys" / f"{provider}.key"
    api_key = None
    if key_file.exists():
        api_key = key_file.read_text().strip()

    client = LLMClient(api_key=api_key, provider=provider)
    if not client.is_available():
        print("Error: LLM client could not be initialized.")
        sys.exit(1)

    system_prompt = f"""You are an expert Epidemiological Modeler working within the EpiMDE Eclipse framework.
Your task is to explain the selected epidemiological model element to the user.

IMPORTANT: You MUST format your response as a valid HTML snippet suitable for an embedded browser. 
Use tags like <p>, <ul>, <li>, <b>, <code>. 
For code blocks, use <pre style="background:#f4f4f5; padding:10px; border-radius:5px; overflow-x:auto;"><code>...</code></pre>.
DO NOT wrap your response in ```html markdown blocks. Output raw HTML only.

{attachments_context}

User Request / Element Info:
{prompt_str}

Please provide a clear, concise plain-English explanation of what this element represents in the context of the model. Do not return JSON. Just HTML."""

    try:
        if has_context_history:
            result = client.generate_text(system_prompt, max_tokens=4000, temperature=0.3)
        else:
            result = client.generate_text_fast(system_prompt, max_tokens=4000, temperature=0.3)
            
        clean_result = result.strip()
        if clean_result.startswith("```html"):
            clean_result = clean_result[7:]
        elif clean_result.startswith("```"):
            clean_result = clean_result[3:]
        if clean_result.endswith("```"):
            clean_result = clean_result[:-3]
            
        print(clean_result.strip())
        
    except Exception as e:
        print(f"Error: {str(e)}")
        sys.exit(1)

if __name__ == "__main__":
    main()
