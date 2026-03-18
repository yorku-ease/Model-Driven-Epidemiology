# Parsing Summary Report

## Overview

This document tracks the parsing results for LLM-generated compartmental model XML files across all disease benchmarks.

- **Total files processed**: 154 (22 files × 7 diseases)
- **Successfully parsed**: 150
- **Failed at parsing stage**: 4 (need LLM regeneration)
- **Manually parsed**: 4 (XML had invalid unicode characters)

## Parsing Statistics by Disease

| Disease       | Total | Parsed | Failed | Manually Parsed |
|---------------|-------|--------|--------|-----------------|
| Measles       | 22    | 21     | 0      | 1               |
| Tuberculosis  | 22    | 22     | 0      | 0               |
| HIV           | 22    | 22     | 0      | 0               |
| Ebola         | 22    | 22     | 0      | 0               |
| Malaria       | 22    | 21     | 0      | 1               |
| Dengue        | 22    | 20     | 0      | 2               |
| Influenza     | 22    | 22     | 0      | 0               |
| **Total**     | **154** | **150** | **0**  | **4**           |

---

## Files Requiring LLM Regeneration

These files failed at the **parsing stage** due to LLM API errors (no valid XML content was generated).

### Zika (4 files)

| File | Error | Details |
|------|-------|---------|
| `zika_gemini_grobid_only.txt` | 503 UNAVAILABLE | API overload - no content generated |
| `zika_openai_everything.txt` | 400 | Safety filter blocked - no content generated |
| `zika_openai_fulltext_tables.txt` | 400 | Likely safety filter blocked - no content generated |
| `zika_openai_grobid_tables.txt` | 400 | Likely safety filter blocked - no content generated |

---

## Files Manually Parsed

These files were **manually parsed** because the LLM-generated XML contained invalid unicode characters that broke the XML parser.

### Measles (1 file)

| File | Issue |
|------|-------|
| `measles_openai_grobid_equations.txt` | Invalid unicode symbols (≈, ψ) not escaped in descriptions |

### Malaria (1 file)

| File | Issue |
|------|-------|
| `malaria_openai_grobid_tables.txt` | Invalid unicode subscripts (₁, ₂, ₄, β₄₁) not escaped |

### Dengue (2 files)

| File | Issue |
|------|-------|
| `dengue_openai_baseline.txt` | Invalid unicode subscripts (ₕ, ᵥ, ₐ) not escaped |
| `dengue_openai_grobid_images_equations.txt` | Invalid unicode subscripts (ₕ, ᵥ, ₐ) not escaped |

---

## Output Locations

- **Parsed models**: `parsed_models/<disease>/`
- **Benchmark gold standards**: `parsed_models/benchmark_gold_standard/`
- **Runner script**: `parse_llm_output.py`

## Notes

- All other diseases (Tuberculosis, HIV, Ebola, Influenza) parsed successfully without issues
- The 4 failed Zika files need to be regenerated via the LLM pipeline
- The 4 manually parsed files could be auto-parsed if the LLM is configured to escape unicode characters in XML output
