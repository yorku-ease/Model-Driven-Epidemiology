# START HERE: Phase 2 Quick Guide

## What is Phase 2?

Phase 2 automatically extracts compartmental epidemiological models from scientific papers. It reads a PDF, extracts the model structure, and generates a `.compmodel` file.

## What You Need to Do (3 Steps)

### 1. Add Your API Key

**File:** `.api_key.txt` (in this directory)

1. Open `.api_key.txt`
2. Add your OpenAI API key on a new line
3. Save the file

Example:
```
sk-your-actual-api-key-here
```

### 2. Install Dependencies

```bash
# Option A: Use Phase 1 virtual environment (recommended)
cd "../phase 1"
source venv/bin/activate
pip install pdfplumber openai
cd "../phase 2"

# Option B: Install locally
pip install -r requirements.txt
```

### 3. Run Phase 2

**Test with included paper:**
```bash
python run_phase2.py --paper data/papers/EbolaSensitivity.pdf --output reports/ebola
```

**For your own paper:**
```bash
# 1. Put your PDF in data/papers/
cp your_paper.pdf data/papers/

# 2. Run Phase 2
python run_phase2.py --paper data/papers/your_paper.pdf --output reports/your_paper_name
```

## What You Get

After running, check `reports/your_paper_name/`:

- **`model_draft.compmodel`** - The extracted model (main output)
- **`evaluation_report.json`** - Quality metrics
- **`phase2_gap_report.json`** - What's missing
- **`gap_fill_suggestions.json`** - How to fill gaps
- Plus 6 more analysis files

## Project Structure

```
phase 2/
├── .api_key.txt          ← ADD YOUR API KEY HERE
├── run_phase2.py         ← RUN THIS SCRIPT
├── data/
│   └── papers/           ← PUT YOUR PDFs HERE
└── reports/              ← RESULTS APPEAR HERE
```

## Need More Help?

- **`GET_STARTED.md`** - Quick 3-step guide
- **`INSTRUCTIONS.md`** - Detailed step-by-step instructions
- **`README.md`** - Full documentation
- **`docs/`** - Technical documentation

## Troubleshooting

**"No PDF extraction library found"**
→ Run: `pip install pdfplumber`

**"LLM not available"**
→ Check `.api_key.txt` has your API key

**"Module not found"**
→ Make sure you're in the `phase 2` directory

## That's It!

Just add your API key and run the script. Everything else is automatic.
