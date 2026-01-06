"""
Helper script to update paper collection with PDF links once you find them
"""
import json
from pathlib import Path
import sys

sys.path.insert(0, str(Path(__file__).parent.parent / 'utils'))
from paper_collection import PaperCollection


def update_paper_link(paper_id: str, pdf_url: str, doi: str = None):
    """Update a paper with PDF URL and/or DOI"""
    collection = PaperCollection()
    collection.load_collection_index()
    
    # Find the paper
    paper = None
    for p in collection.papers_db:
        if p['id'] == paper_id:
            paper = p
            break
    
    if not paper:
        print(f"Paper {paper_id} not found!")
        return
    
    # Update paper metadata
    paper_dir = Path(collection.collection_path) / paper_id
    metadata_path = paper_dir / 'metadata.json'
    
    if metadata_path.exists():
        with open(metadata_path, 'r') as f:
            metadata = json.load(f)
    else:
        metadata = paper
    
    # Update with new information
    if pdf_url:
        metadata['pdfUrl'] = pdf_url
        print(f"✓ Added PDF URL for {paper['title']}")
    
    if doi:
        metadata['doi'] = doi
        print(f"✓ Added DOI for {paper['title']}")
    
    # Save updated metadata
    with open(metadata_path, 'w', encoding='utf-8') as f:
        json.dump(metadata, f, indent=2, ensure_ascii=False)
    
    # Update collection index
    collection.save_collection_index()
    
    print(f"✓ Updated {paper['title']}")


def list_papers():
    """List all papers with their IDs"""
    collection = PaperCollection()
    collection.load_collection_index()
    
    print("=" * 80)
    print("PAPER COLLECTION - All Papers")
    print("=" * 80)
    print()
    
    for paper in collection.papers_db:
        print(f"ID: {paper['id']}")
        print(f"  Title: {paper['title']}")
        print(f"  Authors: {paper['authors']}")
        print(f"  Year: {paper['year']} | Disease: {paper['disease']}")
        if 'pdfUrl' in paper:
            print(f"  PDF URL: {paper['pdfUrl']}")
        if 'doi' in paper:
            print(f"  DOI: {paper['doi']}")
        print()


def main():
    """Main function"""
    import sys
    
    if len(sys.argv) < 2:
        print("Usage:")
        print("  python3 update_paper_links.py list                    # List all papers")
        print("  python3 update_paper_links.py update <id> <url> [doi] # Update paper with URL")
        print()
        list_papers()
        return
    
    command = sys.argv[1]
    
    if command == 'list':
        list_papers()
    elif command == 'update':
        if len(sys.argv) < 4:
            print("Error: Need paper ID and URL")
            print("Usage: python3 update_paper_links.py update <paper_id> <pdf_url> [doi]")
            return
        
        paper_id = sys.argv[2]
        pdf_url = sys.argv[3]
        doi = sys.argv[4] if len(sys.argv) > 4 else None
        
        update_paper_link(paper_id, pdf_url, doi)
    else:
        print(f"Unknown command: {command}")
        print("Use 'list' or 'update'")


if __name__ == '__main__':
    main()

