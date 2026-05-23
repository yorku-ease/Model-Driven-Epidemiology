import sys
import os
from pathlib import Path

# Add phase 2 to path so we can import llm_client and pdf pipeline
repo_root = Path(__file__).parent.parent
phase2_dir = repo_root / "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY" / "phase 2"
sys.path.append(str(phase2_dir))

try:
    from src.utils.llm_client import LLMClient
except ImportError as e:
    print(f"Error importing LLMClient: {e}")
    sys.exit(1)

def main():
    if len(sys.argv) < 3:
        print("Usage: python run_prompt.py <provider> <prompt_file_path>")
        sys.exit(1)

    provider = sys.argv[1].lower()
    prompt_file = Path(sys.argv[2])

    if not prompt_file.exists():
        print(f"Error: Prompt file {prompt_file} does not exist.")
        sys.exit(1)

    # Find key from .keys directory
    key_file = repo_root / ".keys" / f"{provider}.key"
    api_key = None
    if key_file.exists():
        api_key = key_file.read_text().strip()

    client = LLMClient(api_key=api_key, provider=provider)
    if not client.is_available():
        print(f"Error: LLM client for {provider} could not be initialized.")
        sys.exit(1)

    print(f"Running LLM ({provider}) on prompt from {prompt_file.name}...")
    prompt_text = prompt_file.read_text(encoding="utf-8")
    
    # Process any unextracted PDFs found in the prompt text
    import re
    pdf_matches = list(re.finditer(r"path:\s*(.*?\.pdf),\s*content:\s*\n\[Binary or unsupported file - content not extracted\]", prompt_text, re.IGNORECASE))
    
    if pdf_matches:
        pdf_pipeline = None
        for match in pdf_matches:
            pdf_path = match.group(1).strip()
            if os.path.exists(pdf_path):
                if pdf_pipeline is None:
                    try:
                        from src.extraction.pdf_pipeline import PDFPipeline
                        pdf_pipeline = PDFPipeline()
                    except Exception as e:
                        print(f"Warning: Failed to load PDFPipeline: {e}")
                        continue
                        
                print(f"Extracting text from PDF: {pdf_path}")
                try:
                    pdf_result = pdf_pipeline.process_pdf(pdf_path)
                    full_text = pdf_result.get("full_text", "")
                    
                    # Replace placeholder in prompt_text
                    to_replace = f"path: {pdf_path}, content: \n[Binary or unsupported file - content not extracted]"
                    replacement = f"path: {pdf_path}, content: \n{full_text}"
                    prompt_text = prompt_text.replace(to_replace, replacement)
                except Exception as e:
                    print(f"Error extracting PDF {pdf_path}: {e}")
                    
        # Update the prompt file so the user can see the extracted text that was actually sent
        prompt_file.write_text(prompt_text, encoding="utf-8")

    # Generate text and validate XML with auto-repair loop
    import xml.etree.ElementTree as ET
    max_retries = 2
    
    for attempt in range(max_retries + 1):
        try:
            # Generate with the maximum possible token limit for very large generated models
            result = client.generate_text(prompt_text, max_tokens=65536, temperature=0.0)
            
            # Clean up result if the LLM output markdown code blocks accidentally
            clean_result = result.strip()
            if clean_result.startswith("```xml"):
                clean_result = clean_result[6:]
            elif clean_result.startswith("```"):
                clean_result = clean_result[3:]
                
            if clean_result.endswith("```"):
                clean_result = clean_result[:-3]
                
            clean_result = clean_result.strip()
            
            # Validate XML
            ET.fromstring(clean_result)
            
            # If successful, break out of the retry loop
            break
            
        except ET.ParseError as e:
            if attempt < max_retries:
                print(f"⚠️ XML Parse Error Detected: {e}. Requesting LLM auto-repair (Attempt {attempt+1}/{max_retries})...")
                error_msg = str(e)
                repair_prompt = f"\n\n==================================================\nXML PARSE ERROR DETECTED\n==================================================\nThe generated XML model had the following parsing error:\n{error_msg}\n\nPlease find the error in the compmodel and return with the correct compmodel and NO explanation at all. Follow the metamodel strictly.\n"
                
                # Append the generated bad text and the repair prompt to the file
                with open(prompt_file, "a", encoding="utf-8") as f:
                    f.write("\n\n" + "="*50 + "\n")
                    f.write(f"LLM Output ({provider}) [Attempt {attempt+1}]:\n")
                    f.write("="*50 + "\n\n")
                    f.write(result + "\n")
                    f.write(repair_prompt)
                
                # Also append to the in-memory prompt text for the next LLM call
                prompt_text += f"\n\n{result}\n{repair_prompt}"
            else:
                print(f"⚠️ Failed to generate valid XML after {max_retries} retries. Saving last attempt. Final error: {e}")
        except Exception as e:
            print(f"Error calling LLM: {e}")
            sys.exit(1)

    # Append the final successful (or failed-after-retries) output to file
    with open(prompt_file, "a", encoding="utf-8") as f:
        f.write("\n\n" + "="*50 + "\n")
        f.write(f"LLM Output ({provider}) [Final]:\n")
        f.write("="*50 + "\n\n")
        f.write(result + "\n")

    print(f"Successfully appended final LLM output to {prompt_file.name}")

    # Also save just the result as a .compmodel file in the Compartmental directory
    try:
        out_name = prompt_file.name.replace("_prompt.txt", "")
        compmodel_dir = repo_root / "Compartmental" / "CompartmentalModel"
        compmodel_dir.mkdir(parents=True, exist_ok=True)
        compmodel_file = compmodel_dir / f"{out_name}.compmodel"
        
        compmodel_file.write_text(clean_result, encoding="utf-8")
        print(f"Successfully saved generated model to {compmodel_file}")
    except Exception as e:
        print(f"Warning: Failed to save .compmodel file: {e}")

if __name__ == "__main__":
    main()
