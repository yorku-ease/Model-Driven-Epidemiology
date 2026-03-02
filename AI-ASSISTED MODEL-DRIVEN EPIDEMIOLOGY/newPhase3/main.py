import os
import json
from pathlib import Path
from dotenv import load_dotenv

load_dotenv()

from utils.fullTextExtractor import PDFPipeline
from utils.grobid_sectioner import extract_sections_from_pdf
from utils.camelotTableExtraction import CamelotTableExtractor
from utils.llm import LLMClient
from concurrent.futures import ThreadPoolExecutor

PAPERS_DIR = Path("papers")
FULLTEXT_DIR = Path("fullText")
GROBID_OUTPUT_DIR = Path("grobid_output")
CAMELOT_TABLES_DIR = Path("camelot_tables")
IMAGES_DIR = Path("images")
EQUATIONS_DIR = Path("equations")
LLM_OUTPUT_DIR = Path("llm_output")
METAMODEL_PATH = Path("metamodel.txt")
PROMPTS_PATH = Path("prompts.json")


def get_pdfs():
    return sorted([f for f in PAPERS_DIR.iterdir() if f.suffix.lower() == ".pdf"])


def task_1_extract_full_text():
    pdfs = get_pdfs()
    if not pdfs:
        print("No PDFs found in papers folder.")
        return

    print(f"\nFound {len(pdfs)} PDFs:\n")
    for i, pdf in enumerate(pdfs, 1):
        print(f"  {i}. {pdf.stem}")

    pipeline = PDFPipeline()

    print("\nExtracting full text from all PDFs...\n")
    for pdf in pdfs:
        print(f"  Processing: {pdf.stem}")
        try:
            text = pipeline.extract_full_text(str(pdf))
            output_name = f"{pdf.stem}fullText.txt"
            output_path = FULLTEXT_DIR / output_name
            with open(output_path, "w", encoding="utf-8") as f:
                f.write(text)
            print(f"    Saved: {output_name}")
        except Exception as e:
            print(f"    Error: {e}")

    print("\nDone!")


def task_2_grobid_sectioning():
    pdfs = get_pdfs()
    if not pdfs:
        print("No PDFs found in papers folder.")
        return

    print(f"\nFound {len(pdfs)} PDFs:\n")
    for i, pdf in enumerate(pdfs, 1):
        print(f"  {i}. {pdf.stem}")

    print("\nRunning GROBID sectioning on all PDFs...\n")
    for pdf in pdfs:
        print(f"  Processing: {pdf.stem}")
        try:
            result = extract_sections_from_pdf(str(pdf))

            sections = result.get("sections", [])
            metadata = result.get("metadata", {})

            sections_path = GROBID_OUTPUT_DIR / f"{pdf.stem}_grobid_sections.json"
            metadata_path = GROBID_OUTPUT_DIR / f"{pdf.stem}_grobid_metadata.json"

            with open(sections_path, "w", encoding="utf-8") as f:
                json.dump(sections, f, indent=2, ensure_ascii=False)

            with open(metadata_path, "w", encoding="utf-8") as f:
                json.dump(metadata, f, indent=2, ensure_ascii=False)

            print(f"    Saved: {pdf.stem}_grobid_sections.json")
            print(f"    Saved: {pdf.stem}_grobid_metadata.json")
        except Exception as e:
            print(f"    Error: {e}")

    print("\nDone!")


def task_3_camelot_tables():
    pdfs = get_pdfs()
    if not pdfs:
        print("No PDFs found in papers folder.")
        return

    print(f"\nFound {len(pdfs)} PDFs:\n")
    for i, pdf in enumerate(pdfs, 1):
        print(f"  {i}. {pdf.stem}")

    extractor = CamelotTableExtractor()

    print("\nExtracting tables with Camelot from all PDFs...\n")
    for pdf in pdfs:
        print(f"  Processing: {pdf.stem}")
        try:
            tables = extractor.extract_tables(str(pdf), apply_filter=False)

            output_path = CAMELOT_TABLES_DIR / f"{pdf.stem}_camelot_tables.json"

            tables_out = []
            for t in tables:
                rows, cols = t["shape"]
                tables_out.append(
                    {
                        "page": t["page"],
                        "source": t["source"],
                        "table_index": t["table_index"],
                        "rows": rows,
                        "cols": cols,
                        "data": t["data"],
                    }
                )

            output = {
                "pdf": str(pdf),
                "num_tables": len(tables_out),
                "tables": tables_out,
            }

            with open(output_path, "w", encoding="utf-8") as f:
                json.dump(output, f, indent=2, ensure_ascii=False)

            print(
                f"    Saved: {pdf.stem}_camelot_tables.json ({len(tables_out)} tables)"
            )
        except Exception as e:
            print(f"    Error: {e}")

    print("\nDone!")


def load_prompts():
    with open(PROMPTS_PATH, "r", encoding="utf-8") as f:
        return json.load(f)


def load_metamodel():
    with open(METAMODEL_PATH, "r", encoding="utf-8") as f:
        return f.read()


def get_image_path(disease):
    disease_lower = disease.lower()
    for ext in [".png", ".jpg"]:
        path = IMAGES_DIR / f"{disease_lower}{ext}"
        if path.exists():
            return path
        path = IMAGES_DIR / f"{disease.capitalize()}{ext}"
        if path.exists():
            return path
    return None


def get_equations_path(disease):
    disease_lower = disease.lower()
    path = EQUATIONS_DIR / f"{disease_lower}_equations.txt"
    if path.exists():
        return path
    return None


def load_input_file(path):
    if path.suffix == ".txt":
        with open(path, "r", encoding="utf-8") as f:
            return f.read()
    elif path.suffix == ".json":
        with open(path, "r", encoding="utf-8") as f:
            return json.load(f)
    return None


def assemble_inputs_block(disease, input_types, prompts_config, image_analysis=None):
    input_labels = prompts_config["_instructions"]["input_section_labels"]
    blocks = []

    if "full_text" in input_types:
        fulltext_path = FULLTEXT_DIR / f"{disease}fullText.txt"
        if fulltext_path.exists():
            content = load_input_file(fulltext_path)
            if content:
                blocks.append(f"{input_labels['full_text']}\n{content[:50000]}")

    if "grobid" in input_types:
        grobid_path = GROBID_OUTPUT_DIR / f"{disease}_grobid_sections.json"
        if grobid_path.exists():
            content = load_input_file(grobid_path)
            if content:
                blocks.append(
                    f"{input_labels['grobid']}\n{json.dumps(content, indent=2)[:50000]}"
                )

    if "tables" in input_types:
        tables_path = CAMELOT_TABLES_DIR / f"{disease}_camelot_tables.json"
        if tables_path.exists():
            content = load_input_file(tables_path)
            if content:
                blocks.append(
                    f"{input_labels['tables']}\n{json.dumps(content, indent=2)[:50000]}"
                )

    if "images" in input_types:
        if image_analysis:
            img_block = f"{input_labels['images']}\n"
            img_block += f"OpenAI analysis: {json.dumps(image_analysis.get('openai', {}), indent=2)[:10000]}\n\n"
            img_block += f"Gemini analysis: {json.dumps(image_analysis.get('gemini', {}), indent=2)[:10000]}"
            blocks.append(img_block)
        else:
            image_path = get_image_path(disease)
            if image_path:
                blocks.append(
                    f"{input_labels['images']}\n[Image file: {image_path.name}]"
                )

    if "equations" in input_types:
        eq_path = get_equations_path(disease)
        if eq_path:
            content = load_input_file(eq_path)
            if content:
                blocks.append(f"{input_labels['equations']}\n{content}")

    return "\n\n".join(blocks)


def build_full_prompt(
    disease, case_config, prompts_config, metamodel, image_analysis=None
):
    template = case_config["user_prompt_template"]
    input_types = case_config["inputs"]

    inputs_block = assemble_inputs_block(
        disease, input_types, prompts_config, image_analysis
    )

    prompt = template.format(
        disease=disease, inputs_block=inputs_block, metamodel=metamodel
    )
    return prompt


def analyze_image_with_llm(image_path, provider):
    client = LLMClient(provider=provider)
    image_prompt = """Analyze this figure from an epidemiological modeling paper. 
Describe what you see: compartments (boxes), flows (arrows), labels, equations, and any model structure visible.
Focus on: What compartments are shown? What are the transitions between them? Any parameter symbols visible?"""

    try:
        result = client.extract_with_llm(
            prompt=image_prompt,
            image_path=str(image_path),
            temperature=0.3,
            max_output_tokens=2000,
        )
        return result
    except Exception as e:
        return f"Error: {str(e)}"


def run_llm_extraction(prompt, provider):
    client = LLMClient(provider=provider)
    try:
        result = client.extract_with_llm(
            prompt=prompt,
            temperature=0.3,
            max_output_tokens=4000,
        )
        return result
    except Exception as e:
        return f"Error: {str(e)}"


def task_4_llm_extraction():
    LLM_OUTPUT_DIR.mkdir(exist_ok=True)

    prompts_config = load_prompts()
    metamodel = load_metamodel()

    diseases = [f.stem for f in get_pdfs()]
    cases = prompts_config["cases"]

    print(f"\nFound {len(diseases)} diseases: {diseases}")
    print(f"Found {len(cases)} cases: {list(cases.keys())}")

    for disease in diseases:
        print(f"\n{'=' * 50}")
        print(f"Processing disease: {disease}")
        print(f"{'=' * 50}")

        for case_id, case_config in cases.items():
            case_name = case_config["name"]
            input_types = case_config["inputs"]

            print(f"\n  Case {case_id}: {case_name} (inputs: {input_types})")

            has_images = "images" in input_types
            image_analysis = {}

            if has_images:
                image_path = get_image_path(disease)
                if image_path:
                    print(f"    Analyzing image: {image_path.name}")

                    with ThreadPoolExecutor(max_workers=2) as executor:
                        future_openai = executor.submit(
                            analyze_image_with_llm, image_path, "openai"
                        )
                        future_gemini = executor.submit(
                            analyze_image_with_llm, image_path, "gemini"
                        )

                        try:
                            image_analysis["openai"] = future_openai.result()
                        except Exception as e:
                            image_analysis["openai"] = {"error": str(e)}

                        try:
                            image_analysis["gemini"] = future_gemini.result()
                        except Exception as e:
                            image_analysis["gemini"] = {"error": str(e)}
                else:
                    print(f"    Warning: Image not found for {disease}")

            full_prompt = build_full_prompt(
                disease,
                case_config,
                prompts_config,
                metamodel,
                image_analysis if image_analysis else None,
            )

            print(f"    Running OpenAI and Gemini...")

            def run_providers():
                results = {}

                def run_openai():
                    return "openai", run_llm_extraction(full_prompt, "openai")

                def run_gemini():
                    return "gemini", run_llm_extraction(full_prompt, "gemini")

                with ThreadPoolExecutor(max_workers=2) as executor:
                    future_o = executor.submit(run_openai)
                    future_g = executor.submit(run_gemini)

                    try:
                        provider, result = future_o.result()
                        results[provider] = result
                    except Exception as e:
                        results["openai"] = {"error": str(e)}

                    try:
                        provider, result = future_g.result()
                        results[provider] = result
                    except Exception as e:
                        results["gemini"] = {"error": str(e)}

                return results

            results = run_providers()

            for provider, result in results.items():
                output_name = f"{disease}_{provider}_{case_name}.txt"
                output_path = LLM_OUTPUT_DIR / output_name

                with open(output_path, "w", encoding="utf-8") as f:
                    f.write(result)

                print(f"    Saved: {output_name}")

    print(f"\n{'=' * 50}")
    print("All LLM extractions complete!")
    print(f"Output directory: {LLM_OUTPUT_DIR}")
    print(f"{'=' * 50}")


def main():
    print("=" * 50)
    print("Main Menu")
    print("=" * 50)
    print("1. Extract full text from PDFs")
    print("2. Extract sections with GROBID")
    print("3. Extract tables with Camelot")
    print("4. Run LLM extraction")
    print("5. Exit")
    print("=" * 50)

    choice = input("\nEnter option: ").strip()

    if choice == "1":
        task_1_extract_full_text()
    elif choice == "2":
        task_2_grobid_sectioning()
    elif choice == "3":
        task_3_camelot_tables()
    elif choice == "4":
        task_4_llm_extraction()
    elif choice == "5" or choice.lower() == "exit":
        print("Goodbye!")
    else:
        print("Invalid option or task not yet implemented.")


if __name__ == "__main__":
    main()
