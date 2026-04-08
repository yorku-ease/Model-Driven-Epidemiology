"""
Task 3.1: Build My Paper Collection

Collect and organize papers for training/testing.
Create structure for paper metadata and extracted models.
"""
import json
import re
import shutil
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
    
    def add_paper(
        self,
        title: str,
        authors: str,
        year: int,
        disease: str,
        pdf_path: str = None,
        notes: str = None,
        venue: Optional[str] = None,
        venue_type: Optional[str] = None,
        bibtex: Optional[str] = None,
    ) -> Dict[str, Any]:
        """Add a paper to the collection.

        Args:
            venue: Publication venue name (e.g. journal or conference title).
            venue_type: One of: journal, conference, book, preprint, other, unknown
            bibtex: Full BibTeX entry (paste from Google Scholar, PubMed, etc.).
        """
        paper_id = f"{disease.lower().replace(' ', '_')}_{year}_{len(self.papers_db)}"
        paper_dir = self.collection_path / paper_id
        paper_dir.mkdir(parents=True, exist_ok=True)

        vt = (venue_type or "unknown").strip().lower()
        if vt not in ("journal", "conference", "book", "preprint", "other", "unknown"):
            vt = "other"

        paper_data = {
            'id': paper_id,
            'title': title,
            'authors': authors,
            'year': year,
            'disease': disease,
            'venue': venue,
            'venueType': vt,
            'bibtex': bibtex,
            'pdfPath': str(pdf_path) if pdf_path else None,
            'notes': notes,
            'addedDate': datetime.now().isoformat(),
            'extractedModel': None,
            'gaps': [],
        }
        
        # Save paper metadata
        metadata_path = paper_dir / 'metadata.json'
        with open(metadata_path, 'w', encoding='utf-8') as f:
            json.dump(paper_data, f, indent=2, ensure_ascii=False)
        
        # Copy PDF if provided
        phase1_root = self.collection_path.parent.parent
        if pdf_path and Path(pdf_path).exists():
            import shutil
            pdf_dest = paper_dir / f"{paper_id}.pdf"
            shutil.copy(pdf_path, pdf_dest)
            try:
                paper_data["pdfPath"] = str(
                    pdf_dest.relative_to(phase1_root)
                ).replace("\\", "/")
            except ValueError:
                paper_data["pdfPath"] = str(pdf_dest)
        
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
            for p in self.papers_db:
                p.setdefault('venue', None)
                p.setdefault('venueType', 'unknown')
                p.setdefault('bibtex', None)
                p.setdefault('goldCompmodelPath', None)
        else:
            self.papers_db = []

    def _phase1_root(self) -> Path:
        return self.collection_path.parent.parent

    def _project_root(self) -> Path:
        return self._phase1_root().parent

    def _to_phase1_relative(self, p: Path) -> str:
        phase1_root = self._phase1_root()
        try:
            return str(p.resolve().relative_to(phase1_root)).replace("\\", "/")
        except ValueError:
            return str(p.resolve())

    def _resolve_from_phase1(self, p: Optional[str]) -> Optional[Path]:
        if not p:
            return None
        raw = Path(p)
        if raw.is_absolute():
            return raw
        return (self._phase1_root() / raw).resolve()

    @staticmethod
    def _stem_sort_key(stem: str) -> tuple:
        """Natural-ish key: covid2 < covid10, with stable fallback on full stem."""
        m = re.match(r"^(.*?)(\d+)$", stem.lower())
        if not m:
            return (stem.lower(), -1, stem.lower())
        return (m.group(1), int(m.group(2)), stem.lower())

    def _sort_collection(self) -> None:
        def key(p: Dict[str, Any]) -> tuple:
            disease = str(p.get("disease", "")).strip().lower()
            pdf_path = self._resolve_from_phase1(p.get("pdfPath"))
            stem = pdf_path.stem if pdf_path else p.get("id", "")
            return (disease, self._stem_sort_key(str(stem)), str(p.get("id", "")))
        self.papers_db.sort(key=key)

    @staticmethod
    def _find_compmodel_for_pdf(pdf_path: Path) -> Optional[Path]:
        exact = pdf_path.parent / f"{pdf_path.stem}.compmodel"
        if exact.is_file():
            return exact
        stem = pdf_path.stem.lower()
        for cm in sorted(pdf_path.parent.glob("*.compmodel")):
            if cm.stem.lower() == stem:
                return cm
        return None

    @staticmethod
    def _slug(s: str) -> str:
        cleaned = re.sub(r"[^a-zA-Z0-9]+", "_", s.strip().lower()).strip("_")
        return cleaned or "paper"

    def _next_available_id(self, base_id: str, used_ids: set[str]) -> str:
        if base_id not in used_ids:
            return base_id
        i = 2
        while f"{base_id}_{i}" in used_ids:
            i += 1
        return f"{base_id}_{i}"

    def remove_legacy_bootstrap_entries(self) -> int:
        """
        Remove old seeded rows that create confusing duplicates once per-paper
        benchmark rows (covid1/covid2/...) are present.
        """
        legacy_titles = {
            "COVID-19 Model (Tuite et al. 2020)",
            "Malaria Model (Akowe et al. 2025)",
            "HIV Model (Espitia et al. 2022)",
        }
        kept: List[Dict[str, Any]] = []
        removed = 0
        for p in self.papers_db:
            title = str(p.get("title", "")).strip()
            if title in legacy_titles:
                pid = p.get("id")
                if pid:
                    old_dir = self.collection_path / str(pid)
                    if old_dir.is_dir():
                        shutil.rmtree(old_dir, ignore_errors=True)
                removed += 1
                continue
            kept.append(p)
        if removed:
            self.papers_db = kept
            self._sort_collection()
            self.save_collection_index()
        return removed

    def sync_phase2_disease_papers(self) -> int:
        """
        Add one Phase 1 index entry per paired benchmark paper under:
          - phase 2/data/diseases/<disease>/*.pdf (canonical)
          - phase 2/data/<disease>/*.pdf (legacy fallback)
        """
        phase2_data = self._project_root() / "phase 2" / "data"
        canonical = phase2_data / "diseases"
        reserved = {"baseline_models", "papers", "examples", "cases", "diseases"}
        disease_dirs: List[Path] = []

        if canonical.is_dir():
            disease_dirs.extend([d for d in sorted(canonical.iterdir()) if d.is_dir()])
        if phase2_data.is_dir():
            for d in sorted(phase2_data.iterdir()):
                if d.is_dir() and d.name not in reserved:
                    disease_dirs.append(d)

        # Rebuild auto-synced Phase 2 entries each run so IDs stay aligned with paper names.
        rebuilt_records: List[Dict[str, Any]] = []
        for p in self.papers_db:
            notes = str(p.get("notes", ""))
            if "Auto-synced from phase 2 benchmark pair:" in notes:
                pid = p.get("id")
                if pid:
                    old_dir = self.collection_path / pid
                    if old_dir.is_dir():
                        shutil.rmtree(old_dir, ignore_errors=True)
                continue
            rebuilt_records.append(p)
        self.papers_db = rebuilt_records

        added = 0
        year_placeholder = datetime.now().year
        used_ids = {str(p.get("id", "")) for p in self.papers_db if p.get("id")}

        for disease_dir in disease_dirs:
            disease_name = disease_dir.name.strip().replace("_", " ")
            if not disease_name:
                continue
            # Title-case most diseases, but keep acronyms readable.
            if disease_name.lower() == "hiv":
                disease_display = "HIV"
            elif disease_name.lower() in {"covid", "covid-19"}:
                disease_display = "COVID-19"
            else:
                disease_display = disease_name.capitalize()

            pdfs = sorted(
                disease_dir.glob("*.pdf"),
                key=lambda p: self._stem_sort_key(p.stem),
            )
            for pdf in pdfs:
                compmodel = self._find_compmodel_for_pdf(pdf)
                if compmodel is None:
                    continue

                # Deterministic ID aligned with paper filename (e.g., covid2).
                paper_id_base = self._slug(pdf.stem)
                paper_id = self._next_available_id(paper_id_base, used_ids)
                used_ids.add(paper_id)

                paper_dir = self.collection_path / paper_id
                paper_dir.mkdir(parents=True, exist_ok=True)
                notes = (
                    f"Auto-synced from phase 2 benchmark pair: {pdf.name} + {compmodel.name}. "
                    "Fill title/authors/year/venue/bibtex when known."
                )
                paper_data = {
                    "id": paper_id,
                    "title": pdf.stem,
                    "authors": "(fill from publication)",
                    "year": year_placeholder,
                    "disease": disease_display,
                    "venue": None,
                    "venueType": "unknown",
                    "bibtex": None,
                    "pdfPath": self._to_phase1_relative(pdf),
                    "goldCompmodelPath": self._to_phase1_relative(compmodel),
                    "notes": notes,
                    "addedDate": datetime.now().isoformat(),
                    "extractedModel": None,
                    "gaps": [],
                }
                metadata_path = paper_dir / "metadata.json"
                with open(metadata_path, "w", encoding="utf-8") as f:
                    json.dump(paper_data, f, indent=2, ensure_ascii=False)

                self.papers_db.append(paper_data)
                added += 1

        self._sort_collection()
        self.save_collection_index()
        return added

    def sync_epimde_reference_papers(self) -> int:
        """
        Add one index entry per ``*.compmodel`` in ``papers/epimde/`` that does not
        already appear in ``papers_db`` (matched by **disease** display name).

        Links a paired PDF when ``find_pdf_for_compmodel`` finds one. Fill
        title/authors/venue/bibtex manually or via ``add_paper`` for curated metadata.
        """
        _phase1 = Path(__file__).parent.parent
        import sys

        utils_dir = str(_phase1 / "utils")
        if utils_dir not in sys.path:
            sys.path.insert(0, utils_dir)
        from phase1_paths import epimde_models_dir, find_compmodel_files, find_pdf_for_compmodel

        epimde = epimde_models_dir()
        papers_base = _phase1 / "papers"
        models = find_compmodel_files(epimde)
        existing = {p["disease"].strip().lower() for p in self.papers_db}
        added = 0
        year_placeholder = datetime.now().year
        for display_name, compmodel_path in models:
            key = display_name.strip().lower()
            if key in existing:
                continue
            pdf = find_pdf_for_compmodel(compmodel_path, papers_base)
            pdf_str = str(pdf.resolve()) if pdf else None
            self.add_paper(
                title=f"Reference model ({display_name})",
                authors="(fill from publication)",
                year=year_placeholder,
                disease=display_name,
                pdf_path=pdf_str,
                notes=(
                    f"Auto-synced from papers/epimde/{compmodel_path.name}. "
                    "Replace title/authors; add venue and bibtex when known."
                ),
                venue=None,
                venue_type="unknown",
                bibtex=None,
            )
            existing.add(key)
            added += 1
        return added

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
    collection.load_collection_index()
    if not collection.papers_db:
        print("\nNo existing index — starting empty collection.")
    else:
        print(f"\nLoaded {len(collection.papers_db)} paper(s) from data/papers/collection_index.json")
        collection.save_collection_index()  # persist migrated keys (venue, venueType, bibtex)

    # Remove old default seed rows that conflict with per-paper benchmark naming.
    n_removed = collection.remove_legacy_bootstrap_entries()
    if n_removed:
        print(f"✓ Removed {n_removed} legacy bootstrap row(s) (old COVID/HIV/Malaria defaults).")

    # Add any epimde reference models not yet in the index (one row per .compmodel)
    n_new = collection.sync_epimde_reference_papers()
    if n_new:
        print(f"\n✓ Synced {n_new} new reference(s) from papers/epimde/ (skipped diseases already in index).")
    else:
        print("\n✓ Epimde sync: no new diseases to add (already covered).")

    # Add paired benchmark papers from Phase 2 data/diseases for multi-paper ordering.
    n_phase2 = collection.sync_phase2_disease_papers()
    if n_phase2:
        print(f"✓ Synced {n_phase2} paper pair(s) from phase 2/data/diseases/")
    else:
        print("✓ Phase 2 benchmark sync: no new paired papers to add.")

    collection._sort_collection()
    collection.save_collection_index()

    print(f"\nPaper collection size: {len(collection.papers_db)} papers")
    
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
    print("  collection.add_paper(title, authors, year, disease,")
    print("    pdf_path=..., notes=..., venue=..., venue_type='journal', bibtex='...')")
    print("  venue_type: journal | conference | book | preprint | other | unknown")
    print("  bibtex: optional full BibTeX string (Scholar / PubMed export).")


if __name__ == '__main__':
    main()

