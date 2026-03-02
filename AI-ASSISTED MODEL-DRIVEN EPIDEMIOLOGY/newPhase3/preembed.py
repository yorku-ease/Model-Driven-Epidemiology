import argparse
import json
import numpy as np
from pathlib import Path

from evalutation.evaluator import Evaluator


def embed_disease(
    disease_name: str, parsed_dir: str = "parsed_models", output_dir: str = "embeddings"
):
    parsed_path = Path(parsed_dir) / disease_name
    out_path = Path(output_dir) / f"{disease_name}_embeddings"

    out_path.mkdir(parents=True, exist_ok=True)

    json_files = sorted(parsed_path.glob("*_parsed.json"))

    if not json_files:
        json_files = sorted(parsed_path.glob("*_gold.json"))

    if not json_files:
        print(f"No parsed JSON files found in {parsed_path}")
        return

    evaluator = Evaluator()
    evaluator.load_model()

    print(f"Found {len(json_files)} {disease_name} model files")

    for json_file in json_files:
        print(f"Processing: {json_file.name}")
        data = json.load(open(json_file))

        comp_strings = evaluator._prepare_compartment_strings(data)
        flow_strings = evaluator._prepare_flow_strings(data)
        param_strings = evaluator._prepare_parameter_strings(data)

        all_strings = comp_strings + flow_strings + param_strings
        embeddings = evaluator.model.encode(all_strings)

        stem = json_file.stem
        np.save(out_path / f"{stem}_embeddings.npy", embeddings)

        strings_data = {
            "comp": comp_strings,
            "flow": flow_strings,
            "param": param_strings,
        }
        with open(out_path / f"{stem}_strings.json", "w") as f:
            json.dump(strings_data, f, indent=2)

        print(
            f"  - compartments: {len(comp_strings)}, flows: {len(flow_strings)}, params: {len(param_strings)}"
        )

    print(f"\nDone! Embeddings saved to {out_path}")


def main():
    parser = argparse.ArgumentParser(description="Embed parsed disease models")
    parser.add_argument(
        "disease", help="Disease name (e.g., measles, zika, tuberculosis)"
    )
    parser.add_argument(
        "--parsed-dir", default="parsed_models", help="Base directory for parsed models"
    )
    parser.add_argument(
        "--output-dir", default="embeddings", help="Base directory for embeddings"
    )
    args = parser.parse_args()

    embed_disease(args.disease, args.parsed_dir, args.output_dir)


if __name__ == "__main__":
    main()
