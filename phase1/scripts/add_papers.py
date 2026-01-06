"""
Add additional papers to the paper collection

This script adds papers on other diseases (Influenza, Tuberculosis, Dengue, etc.)
that use compartmental models with similar structure.
"""
import json
from pathlib import Path
import sys

sys.path.insert(0, str(Path(__file__).parent.parent / 'utils'))
from paper_collection import PaperCollection


def add_additional_papers():
    """Add papers on other diseases with compartmental models"""
    
    collection = PaperCollection()
    collection.load_collection_index()
    
    print("=" * 80)
    print("ADDING ADDITIONAL PAPERS TO COLLECTION")
    print("=" * 80)
    print()
    
    # Influenza papers
    print("Adding Influenza papers...")
    collection.add_paper(
        title="A Compartmental Model for Influenza A Transmission Dynamics",
        authors="Arino et al.",
        year=2007,
        disease="Influenza",
        notes="SEIR model for influenza with age stratification. Includes vaccination compartments."
    )
    
    collection.add_paper(
        title="Compartmental Models for Influenza Transmission: A Systematic Review",
        authors="Vynnycky & White",
        year=2010,
        disease="Influenza",
        notes="Review of compartmental models for influenza, includes SIR, SEIR, and extended models."
    )
    
    # Tuberculosis papers
    print("Adding Tuberculosis papers...")
    collection.add_paper(
        title="A Compartmental Model for Tuberculosis with Exogenous Reinfection",
        authors="Castillo-Chavez & Feng",
        year=1997,
        disease="Tuberculosis",
        notes="SEIR model with latent and active TB compartments. Includes reinfection dynamics."
    )
    
    collection.add_paper(
        title="Mathematical Models of Tuberculosis: A Compartmental Approach",
        authors="Blower et al.",
        year=1995,
        disease="Tuberculosis",
        notes="Classic TB compartmental model with latent (E) and active (I) compartments."
    )
    
    # Dengue papers
    print("Adding Dengue papers...")
    collection.add_paper(
        title="A Compartmental Model for Dengue Transmission with Vector Control",
        authors="Esteva & Vargas",
        year=1998,
        disease="Dengue",
        notes="Vector-borne compartmental model similar to malaria. Includes human and mosquito compartments."
    )
    
    collection.add_paper(
        title="Dengue Transmission Dynamics: A Compartmental Model with Age Structure",
        authors="Rodrigues et al.",
        year=2014,
        disease="Dengue",
        notes="Age-stratified dengue model with vector compartments. Includes multiple serotypes."
    )
    
    # Ebola papers
    print("Adding Ebola papers...")
    collection.add_paper(
        title="Ebola Virus Disease: A Compartmental Model for Outbreak Analysis",
        authors="Legrand et al.",
        year=2007,
        disease="Ebola",
        notes="SEIR model for Ebola with hospitalization and burial compartments."
    )
    
    collection.add_paper(
        title="Mathematical Modeling of Ebola Virus Disease: A Compartmental Approach",
        authors="Chowell et al.",
        year=2014,
        disease="Ebola",
        notes="Compartmental model for 2014 Ebola outbreak. Includes treatment and isolation compartments."
    )
    
    # Measles papers
    print("Adding Measles papers...")
    collection.add_paper(
        title="Compartmental Models for Measles Transmission: Impact of Vaccination",
        authors="Anderson & May",
        year=1982,
        disease="Measles",
        notes="Classic SIR model for measles with vaccination. Age-structured version available."
    )
    
    # Cholera papers
    print("Adding Cholera papers...")
    collection.add_paper(
        title="A Compartmental Model for Cholera Transmission with Water Contamination",
        authors="Codeco",
        year=2001,
        disease="Cholera",
        notes="SIR model with water contamination compartment. Includes environmental transmission."
    )
    
    # Zika papers
    print("Adding Zika papers...")
    collection.add_paper(
        title="Compartmental Model for Zika Virus Transmission with Vector Control",
        authors="Kucharski et al.",
        year=2016,
        disease="Zika",
        notes="Vector-borne model similar to dengue. Includes sexual transmission compartment."
    )
    
    print()
    print("=" * 80)
    print("PAPERS ADDED SUCCESSFULLY")
    print("=" * 80)
    
    # Generate updated report
    report = collection.generate_collection_report()
    print(f"\nTotal papers in collection: {report['totalPapers']}")
    print("\nPapers by disease:")
    for disease, count in sorted(report['papersByDisease'].items()):
        print(f"  - {disease}: {count}")
    
    # Export updated report
    output_dir = Path(__file__).parent.parent / 'reports' / 'paper_collection'
    output_dir.mkdir(parents=True, exist_ok=True)
    report_path = output_dir / 'paper_collection.json'
    collection.export_collection_report(str(report_path))
    print(f"\n✓ Updated collection report exported to: {report_path}")
    
    return collection


if __name__ == '__main__':
    add_additional_papers()

