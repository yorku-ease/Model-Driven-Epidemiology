#!/bin/bash
# Paper Organization Script
# Organizes papers into disease-specific directories

echo "===================================================================="
echo "PAPER ORGANIZATION SCRIPT"
echo "===================================================================="
echo

cd "$(dirname "$0")"

# Create organized directory structure
echo "Creating directory structure..."
mkdir -p papers/{covid,malaria,hiv,influenza,ebola,measles,tuberculosis,dengue,cholera,zika,other}

# Move original papers
echo
echo "Moving original papers..."
if [ -d "papers/epimde" ]; then
    if [ -f "papers/epimde/covid.pdf" ]; then
        mv papers/epimde/covid.pdf papers/covid/tuite2020_covid19.pdf
        echo "  ✓ Moved COVID-19 paper"
    fi

    if [ -f "papers/epimde/malaria.pdf" ]; then
        mv papers/epimde/malaria.pdf papers/malaria/akowe2025_malaria.pdf
        echo "  ✓ Moved Malaria paper"
    fi

    if [ -f "papers/epimde/hiv.pdf" ]; then
        mv papers/epimde/hiv.pdf papers/hiv/espitia2022_hiv.pdf
        echo "  ✓ Moved HIV paper"
    fi

    rmdir papers/epimde 2>/dev/null
fi

# Move known new papers
echo
echo "Moving identified new papers..."
if [ -d "papers/new papers" ]; then
    if [ -f "papers/new papers/measles.pdf" ]; then
        mv "papers/new papers/measles.pdf" papers/measles/
        echo "  ✓ Moved Measles paper"
    fi

    if [ -f "papers/new papers/EbolaSensitivity.pdf" ]; then
        mv "papers/new papers/EbolaSensitivity.pdf" papers/ebola/
        echo "  ✓ Moved Ebola paper"
    fi

    if [ -f "papers/new papers/Monitoring_and_predicting_influenza_epidemics_from.pdf" ]; then
        mv "papers/new papers/Monitoring_and_predicting_influenza_epidemics_from.pdf" papers/influenza/
        echo "  ✓ Moved Influenza paper"
    fi
fi

# Move remaining papers to 'other' for manual classification
echo
echo "Moving unidentified papers to 'other' directory..."
if [ -d "papers/new papers" ]; then
    for file in "papers/new papers"/*.pdf; do
        if [ -f "$file" ]; then
            mv "$file" papers/other/
            echo "  ✓ Moved $(basename "$file")"
        fi
    done
    rmdir "papers/new papers" 2>/dev/null
fi

echo
echo "===================================================================="
echo "PAPER ORGANIZATION COMPLETE"
echo "===================================================================="
echo
echo "Paper structure:"
tree -L 2 papers/ 2>/dev/null || find papers/ -type d -print | sed 's|[^/]*/| |g'

echo
echo "Next steps:"
echo "1. Check papers/other/ for unidentified papers"
echo "2. Use pdfinfo or open each PDF to identify disease/topic"
echo "3. Move papers to appropriate disease directories"
echo "4. Rename papers with descriptive names: author_year_disease.pdf"
echo
