# Get Started with Phase 2

## Quick Start (3 Steps)

### Step 1: Add Your API Key

Open `.api_key.txt` and add your OpenAI API key:
```
sk-your-api-key-here
```

### Step 2: Install Dependencies

```bash
# Use Phase 1 virtual environment (recommended)
cd "../phase 1"
source venv/bin/activate
pip install pdfplumber openai
cd "../phase 2"
```

### Step 3: Run Phase 2

```bash
python run_phase2.py --paper data/papers/EbolaSensitivity.pdf --output reports/ebola
```

That's it! Results will be in `reports/ebola/`

## What You Get

After running, you'll have:

- **`model_draft.compmodel`** - The extracted model (XML file)
- **`evaluation_report.json`** - Quality metrics
- **`phase2_gap_report.json`** - What's missing
- **`gap_fill_suggestions.json`** - How to fill gaps
- Plus 6 more analysis files

## For Your Own Papers

1. Put your PDF in `data/papers/`
2. Run: `python run_phase2.py --paper data/papers/your_paper.pdf --output reports/your_paper`
3. Check results in `reports/your_paper/`

## Need More Details?

- **`README.md`** - Full documentation
- **`INSTRUCTIONS.md`** - Step-by-step guide
- **`docs/`** - Detailed technical docs
