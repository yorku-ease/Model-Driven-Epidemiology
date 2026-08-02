import sys
import argparse
import json
import os
import datetime
from pathlib import Path

# Add phase 2 to path so we can import llm_client
repo_root = Path(__file__).parent.parent
phase2_dir = repo_root / "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY" / "phase 2"
sys.path.append(str(phase2_dir))

from src.utils.llm_client import LLMClient

def extract_text(file_path):
    ext = os.path.splitext(file_path)[1].lower()
    if ext == '.pdf':
        try:
            import fitz
            doc = fitz.open(file_path)
            text = ""
            for page in doc:
                text += page.get_text()
            return text
        except Exception as e:
            return f"<Error extracting PDF: {str(e)}>"
    else:
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                return f.read()
        except Exception as e:
            return f"<Error reading file as text: {str(e)}>"

def main():
    parser = argparse.ArgumentParser(description="EpiMDE LLM JSON Runner")
    parser.add_argument("--prompt", required=True, help="User request")
    parser.add_argument("--context", required=True, help="Current serialized model")
    parser.add_argument("--provider", default="gemini", help="LLM Provider (gemini or openai)")
    parser.add_argument("--attach", action="append", default=[], help="Attached files format alias::path")
    parser.add_argument("--log-name", default="", help="Prefix for the prompt sample log file")
    args = parser.parse_args()

    provider = args.provider.lower()
    
    # Read context file if it's a file path, otherwise treat as string
    context_str = args.context
    if os.path.exists(args.context):
        with open(args.context, 'r', encoding='utf-8') as f:
            context_str = f.read()

    # Read prompt file if it's a file path
    prompt_str = args.prompt
    if os.path.exists(args.prompt):
        with open(args.prompt, 'r', encoding='utf-8') as f:
            prompt_str = f.read()
            
    # Process attachments
    attachments_context = ""
    for attachment in args.attach:
        if "::" in attachment:
            alias, path = attachment.split("::", 1)
        else:
            alias = "Attachment"
            path = attachment
            
        if os.path.exists(path):
            text = extract_text(path)
            attachments_context += f"=== ATTACHED FILE: {alias} ===\n{text}\n=====================\n\n"

    key_file = repo_root / ".keys" / f"{provider}.key"
    api_key = None
    if key_file.exists():
        api_key = key_file.read_text().strip()

    client = LLMClient(api_key=api_key, provider=provider)
    if not client.is_available():
        print(json.dumps({"error": f"LLM client for {provider} could not be initialized."}))
        sys.exit(1)
        
    # Read metamodel
    metamodel_path = repo_root / "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY" / "phase 1" / "metamodel_epidemiology.json"
    metamodel_str = ""
    if metamodel_path.exists():
        with open(metamodel_path, 'r', encoding='utf-8') as f:
            metamodel_str = f.read()

    # EpiMDE System Prompt Structure
    system_prompt = f"""You are an expert Epidemiological Modeler working within the EpiMDE Eclipse framework.
Your task is to modify or generate a compartmental model based on the user's request.

Current Model State (XML):
{context_str}

{attachments_context}

User Request:
{prompt_str}

EpiMDE Metamodel Context:
{metamodel_str}

You MUST return your response as raw, valid JSON conforming to this exact schema:
{{
  "model_xml": "<the full updated XML string here>"
}}
ABSOLUTELY NO COMMENTS, NO EXPLANATIONS, AND NO MARKDOWN BLOCK BACKTICKS. JUST THE RAW JSON OBJECT.
Follow the metamodel strictly and ensure all features are valid according to the metamodel."""

    try:
        has_context_history = any(att.startswith("ContextHistory::") for att in args.attach)
        if has_context_history:
            result = client.generate_text(system_prompt, max_tokens=65536, temperature=0.0)
        else:
            result = client.generate_text_fast(system_prompt, max_tokens=65536, temperature=0.0)
        
        # Create a sample prompt that excludes the metamodel text for cleaner evaluation
        system_prompt_for_sample = system_prompt.replace(f"EpiMDE Metamodel Context:\n{metamodel_str}\n\n", "")
        
        # Save prompt and response sample for evaluation
        samples_dir = repo_root / "LLM_Samples"
        samples_dir.mkdir(exist_ok=True)
        timestamp = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
        log_prefix = f"{args.log_name}_" if args.log_name else ""
        sample_path = samples_dir / f"prompt_sample_{log_prefix}{timestamp}.txt"
        with open(sample_path, "w", encoding="utf-8") as sf:
            sf.write("=== SYSTEM PROMPT ===\n")
            sf.write(system_prompt_for_sample)
            sf.write("\n\n=== LLM RAW RESPONSE ===\n")
            sf.write(result)

        clean_result = result.strip()
        if clean_result.startswith("```json"):
            clean_result = clean_result[7:]
        elif clean_result.startswith("```"):
            clean_result = clean_result[3:]
        if clean_result.endswith("```"):
            clean_result = clean_result[:-3]
            
        clean_result = clean_result.strip()
        
        # Verify it parses as JSON
        parsed = json.loads(clean_result)
        
        # Sanitize XML to remove description attributes from elements that don't support them in the current EMF metamodel
        if "model_xml" in parsed:
            xml_str = parsed["model_xml"]
            import re
            
            # Remove description="something" from compartments, externalSources, and externalSinks tags
            # We use a regex that looks for these tags and removes the description attribute
            
            def remove_desc(match):
                tag_content = match.group(0)
                # Remove description="..." or description='...'
                cleaned = re.sub(r'\s+description\s*=\s*(?:"[^"]*"|\'[^\']*\')', '', tag_content)
                return cleaned

            # Match <compartments ...> or <externalSources ...> or <externalSinks ...>
            # This matches the start tag and its attributes up to the closing > or />
            xml_str = re.sub(r'<(?:compartments|externalSources|externalSinks)\b[^>]*>', remove_desc, xml_str)
            
            parsed["model_xml"] = xml_str
        
        # Print only the JSON to stdout as requested by architecture
        print(json.dumps(parsed))
        
    except Exception as e:
        print(json.dumps({"error": str(e)}))
        sys.exit(1)

if __name__ == "__main__":
    main()
