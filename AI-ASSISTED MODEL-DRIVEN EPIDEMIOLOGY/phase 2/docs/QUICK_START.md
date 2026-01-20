# Phase 2 Quick Start Guide

## 🚀 Where to Put Your API Key

**File:** `.api_key.txt` (in the `phase 2` directory)

**Instructions:**
1. Open the file `.api_key.txt` 
2. Add your OpenAI API key on a new line (without quotes)
3. Example:
   ```
   sk-abcdefghijklmnopqrstuvwxyz1234567890
   ```

The system will automatically read from this file.

## 📦 Installation

```bash
cd "AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/phase 2"
pip install -r requirements.txt
```

## 🏃 Run Phase 2

```bash
python run_phase2.py --paper EbolaSensitivity.pdf --output reports/ebola
```

## ✅ What's Implemented

**Steps 1-2 are complete:**
- ✅ PDF text extraction and cleaning
- ✅ Section detection
- ✅ Table extraction
- ✅ Paper promises extraction (pattern + LLM)

**Output files:**
- `reports/ebola/paper_text.json` - Cleaned paper text
- `reports/ebola/paper_sections.json` - Sections and tables
- `reports/ebola/paper_promises.json` - What paper promises to model

## 🚧 Next Steps

The following steps are planned but not yet implemented:
- Step 3: Entity extraction (compartments, flows, parameters)
- Step 4: Model synthesis (generate `.compmodel` files)
- Step 5: Traceability mapping
- Step 6: Gap analysis
- Step 7: Gap filler
- Step 8: Quality checks
- Step 9: Evaluation

See `IMPLEMENTATION_STATUS.md` for detailed status.
