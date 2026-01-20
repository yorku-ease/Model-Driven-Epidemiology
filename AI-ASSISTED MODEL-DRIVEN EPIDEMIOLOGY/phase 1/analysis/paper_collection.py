"""
Task 3.1: Build My Paper Collection

Collect and organize papers for training/testing.
Create structure for paper metadata and extracted models.
"""
import json
from pathlib import Path
from typing import Dict, List, Any, Optional
from datetime import datetime


class PaperCollection:
    """Manage collection of papers for model extraction"""
    
    def __init__(self, collection_path: str = None):
        """Initialize paper collection"""
        if collection_path is None:
            collection_path = Path(__file__).parent.parent / 'data' / 'papers'
        self.collection_path = Path(collection_path)
        self.collection_path.mkdir(parents=True, exist_ok=True)
        self.papers_db = []
    
    def add_paper(self, title: str, authors: str, year: int, disease: str,
                  pdf_path: str = None, notes: str = None) -> Dict[str, Any]:
        """Add a paper to the collection"""
        paper_id = f"{disease.lower().replace(' ', '_')}_{year}_{len(self.papers_db)}"
        paper_dir = self.collection_path / paper_id
        paper_dir.mkdir(parents=True, exist_ok=True)
        
        paper_data = {
            'id': paper_id,
            'title': title,
            'authors': authors,
            'year': year,
            'disease': disease,
            'pdfPath': str(pdf_path) if pdf_path else None,
            'notes': notes,
            'addedDate': datetime.now().isoformat(),
            'extractedModel': None,
            'gaps': []
        }
        
        # Save paper metadata
        metadata_path = paper_dir / 'metadata.json'
        with open(metadata_path, 'w', encoding='utf-8') as f:
            json.dump(paper_data, f, indent=2, ensure_ascii=False)
        
        # Copy PDF if provided
        if pdf_path and Path(pdf_path).exists():
            import shutil
            pdf_dest = paper_dir / f"{paper_id}.pdf"
            shutil.copy(pdf_path, pdf_dest)
            paper_data['pdfPath'] = str(pdf_dest)
        
        self.papers_db.append(paper_data)
        self.save_collection_index()
        
        return paper_data
    
    def save_collection_index(self):
        """Save collection index"""
        index_path = self.collection_path / 'collection_index.json'
        with open(index_path, 'w', encoding='utf-8') as f:
            json.dump({
                'totalPapers': len(self.papers_db),
                'papers': self.papers_db
            }, f, indent=2, ensure_ascii=False)
    
    def load_collection_index(self):
        """Load collection index"""
        index_path = self.collection_path / 'collection_index.json'
        if index_path.exists():
            with open(index_path, 'r', encoding='utf-8') as f:
                data = json.load(f)
                self.papers_db = data.get('papers', [])
        else:
            self.papers_db = []
    
    def initialize_collection(self):
        """Initialize with example papers from existing models"""
        # COVID-19 papers
        self.add_paper(
            title="COVID-19 Model (Tuite et al. 2020)",
            authors="Tuite et al.",
            year=2020,
            disease="COVID-19",
            notes="Age-stratified COVID-19 model - source for covid.compmodel"
        )
        
        # Malaria papers
        self.add_paper(
            title="Malaria Model (Akowe et al. 2025)",
            authors="Akowe et al.",
            year=2025,
            disease="Malaria",
            notes="Malaria transmission model with dual pathways - source for malaria.compmodel"
        )
        
        # HIV papers
        self.add_paper(
            title="HIV Model (Espitia et al. 2022)",
            authors="Espitia et al.",
            year=2022,
            disease="HIV",
            notes="HIV transmission model with sexual behavior stratification - source for HIV.compmodel"
        )
    
    def get_papers_by_disease(self, disease: str) -> List[Dict[str, Any]]:
        """Get all papers for a specific disease"""
        return [p for p in self.papers_db if p['disease'].lower() == disease.lower()]
    
    def generate_collection_report(self) -> Dict[str, Any]:
        """Generate collection statistics"""
        diseases = {}
        for paper in self.papers_db:
            disease = paper['disease']
            if disease not in diseases:
                diseases[disease] = 0
            diseases[disease] += 1
        
        return {
            'totalPapers': len(self.papers_db),
            'papersByDisease': diseases,
            'papers': self.papers_db
        }
    
    def export_collection_report(self, output_path: str):
        """Export collection report"""
        report = self.generate_collection_report()
        
        with open(output_path, 'w', encoding='utf-8') as f:
            json.dump(report, f, indent=2, ensure_ascii=False)


def main():
    """Main function to build paper collection"""
    output_dir = Path(__file__).parent.parent / 'reports' / 'paper_collection'
    output_dir.mkdir(parents=True, exist_ok=True)
    
    print("=" * 80)
    print("TASK 3.1: BUILD MY PAPER COLLECTION")
    print("=" * 80)
    
    collection = PaperCollection()
    collection.initialize_collection()
    
    print(f"\nInitialized paper collection with {len(collection.papers_db)} papers")
    
    # Generate report
    report_path = output_dir / 'paper_collection.json'
    collection.export_collection_report(str(report_path))
    print(f"✓ Collection report exported to: {report_path}")
    
    # Print summary
    report = collection.generate_collection_report()
    print(f"\nTotal papers: {report['totalPapers']}")
    print("Papers by disease:")
    for disease, count in report['papersByDisease'].items():
        print(f"  - {disease}: {count}")
    
    print(f"\nCollection directory: {collection.collection_path}")
    print("\n" + "=" * 80)
    print("PAPER COLLECTION INITIALIZED")
    print("=" * 80)
    print("\nTo add more papers, use:")
    print("  collection.add_paper(title, authors, year, disease, pdf_path, notes)")


if __name__ == '__main__':
    main()

