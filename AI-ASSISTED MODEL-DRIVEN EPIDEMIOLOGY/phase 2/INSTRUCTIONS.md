# Phase 2: Step-by-Step Instructions

## What You Need to Do

### 1. Set Up API Key (Required)

**File:** `.api_key.txt` (in the `phase 2` directory)

1. Open `.api_key.txt`
2. Add your OpenAI API key on a new line (without quotes)
3. Save the file

Example:
```
sk-your-actual-api-key-here
```

**Alternative:** Set environment variable:
```bash
export OPENAI_API_KEY="sk-your-api-key-here"
```

### 2. Install Dependencies

**Option A: Use Phase 1 Virtual Environment (Recommended)**
```bash
cd "../phase 1"
source venv/bin/activate
pip install pdfplumber openai
cd "../phase 2"
```

**Option B: Install Locally**
```bash
pip install -r requirements.txt
```

### 3. Prepare Your Paper

Put your PDF paper in the `data/papers/` directory:

```bash
cp your_paper.pdf data/papers/
```

### 4. Run Phase 2

**Basic Command:**
```bash
python run_phase2.py --paper data/papers/your_paper.pdf --output reports/your_paper_name
```

**Full Command (with all options):**
```bash
python run_phase2.py \
    --paper data/papers/your_paper.pdf \
    --output reports/your_paper_name \
    --phase1-dir "../phase 1" \
    --prior-models-dir "../phase 1/reports/model_analysis"
```

### 5. Check Results

Results will be in `reports/your_paper_name/`:

```bash
ls reports/your_paper_name/
```

**Key Files to Check:**
- `model_draft.compmodel` - Generated model
- `evaluation_report.json` - Quality metrics
- `phase2_gap_report.json` - Missing items
- `gap_fill_suggestions.json` - Suggestions for gaps

## Project Structure

```
phase 2/
├── .api_key.txt              ← PUT YOUR API KEY HERE
├── run_phase2.py             ← RUN THIS SCRIPT
├── requirements.txt
├── README.md                 ← READ THIS FIRST
│
├── src/                      ← Source code (don't modify)
│   ├── extraction/           ← PDF and entity extraction
│   ├── synthesis/            ← Model generation
│   ├── analysis/             ← Gap analysis
│   ├── evaluation/           ← Quality checks
│   └── utils/                ← Utilities
│
├── data/                     ← Your input data
│   ├── papers/               ← PUT YOUR PDFs HERE
│   └── baseline_models/      ← Baseline models for comparison
│
└── reports/                   ← Output (generated automatically)
    └── [paper_name]/         ← Results for each paper
```

## Quick Test

Test with the included Ebola paper:

```bash
python run_phase2.py --paper data/papers/EbolaSensitivity.pdf --output reports/ebola
```

## What Happens

The pipeline runs 9 steps automatically:

1. **PDF Pipeline** - Extracts text from PDF
2. **Paper Promises** - Identifies what paper promises to model
3. **Entity Extraction** - Extracts compartments, flows, parameters
4. **Model Synthesis** - Generates `.compmodel` file
5. **Traceability** - Maps elements to paper evidence
6. **Gap Analysis** - Finds missing items
7. **Gap Filler** - Suggests how to fill gaps
8. **Quality Checks** - Runs Phase 1 analyzers
9. **Evaluation** - Calculates quality metrics

## Output Files

After running, you'll get 10 JSON/XML files in the output directory:

1. `paper_text.json` - Cleaned paper text
2. `paper_sections.json` - Detected sections
3. `paper_promises.json` - What paper promises
4. `extracted_entities.json` - All extracted entities
5. `model_draft.compmodel` - Generated model (XML)
6. `traceability.json` - Evidence mapping
7. `phase2_gap_report.json` - Missing items
8. `gap_fill_suggestions.json` - Gap fill suggestions
9. `quality_checks.json` - Quality check results
10. `evaluation_report.json` - Quality metrics

## Troubleshooting

**"No PDF extraction library found"**
```bash
pip install pdfplumber
```

**"LLM not available"**
- Check `.api_key.txt` has your API key
- Or set `OPENAI_API_KEY` environment variable

**"Module not found" errors**
- Make sure you're in the `phase 2` directory
- Check that `src/` folder exists with all subdirectories

**"Phase 1 analyzer not found"**
- This is OK - quality checks will be skipped
- Make sure `--phase1-dir` points to Phase 1 directory if you want quality checks

## Next Steps After Running

1. **Review the model:** Open `model_draft.compmodel` in a text editor
2. **Check gaps:** Look at `phase2_gap_report.json` for missing items
3. **Use suggestions:** Review `gap_fill_suggestions.json` for gap fills
4. **Compare:** Compare with baseline model if available
5. **Evaluate:** Check `evaluation_report.json` for quality metrics

## Need Help?

1. Read `README.md` for detailed documentation
2. Check `docs/` folder for more information
3. Review error messages in terminal output
4. Check generated JSON files for details
