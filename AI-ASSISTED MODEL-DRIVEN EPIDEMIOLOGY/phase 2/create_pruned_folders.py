"""Create pruned report folders with paper_sections.json for manual editing.

Copies paper_sections.json from existing reports to new pruned folders.
User then manually removes sections before running the pipeline.
"""

import shutil
import argparse
from datetime import datetime
from pathlib import Path


def main():
    parser = argparse.ArgumentParser(description="Create pruned report folders")
    parser.add_argument(
        "--reports-dir", type=str, default="reports", help="Source reports directory"
    )
    parser.add_argument(
        "--output-dir",
        type=str,
        default="reports_pruned",
        help="Output directory for pruned folders",
    )
    parser.add_argument(
        "--llm",
        type=str,
        default="openai",
        choices=["openai", "gemini"],
        help="Which LLM reports to use",
    )
    args = parser.parse_args()

    reports_dir = Path(args.reports_dir)
    output_dir = Path(args.output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)

    diseases = [
        "dengue",
        "ebola",
        "flu",
        "hiv",
        "malaria",
        "measles",
        "tuberculosis",
        "zika",
    ]

    print("=" * 60)
    print("CREATING PRUNED REPORT FOLDERS")
    print("=" * 60)

    created = []

    for disease in diseases:
        folder_pattern = f"{disease}_llm_{args.llm}_"

        matching_folders = [
            f
            for f in sorted(reports_dir.iterdir())
            if f.is_dir() and f.name.startswith(folder_pattern)
        ]

        if not matching_folders:
            print(f"[SKIP] {disease}: No {args.llm} folder found")
            continue

        source_folder = matching_folders[0]
        source_sections = source_folder / "paper_sections.json"

        if not source_sections.exists():
            print(
                f"[SKIP] {disease}: paper_sections.json not found in {source_folder.name}"
            )
            continue

        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        new_folder_name = f"{disease}_pruned_{timestamp}"
        new_folder = output_dir / new_folder_name
        new_folder.mkdir(parents=True, exist_ok=True)

        dest_sections = new_folder / "paper_sections.json"
        shutil.copy2(source_sections, dest_sections)

        created.append(
            {
                "disease": disease,
                "folder": str(new_folder),
                "file": str(dest_sections),
                "source": str(source_folder),
            }
        )

        print(f"[OK] {disease}")
        print(f"      Source: {source_folder.name}")
        print(f"      Dest:   {new_folder.name}/paper_sections.json")
        print()

    print("=" * 60)
    print(f"Created {len(created)} folders in: {output_dir}")
    print()
    print("NEXT STEPS:")
    print("1. Edit each paper_sections.json to remove unwanted sections")
    print("2. When done, tell me to run the pipeline on these pruned folders")
    print("=" * 60)

    return 0


if __name__ == "__main__":
    exit(main())
