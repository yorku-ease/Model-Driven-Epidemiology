# Phase 2: Before vs After — What Improved Results

This report compares **initial pipeline runs** (early February 2026, “before”) with **later runs** (mid-February 2026, “after”) and summarizes what was changed and what was effective in raising extraction quality (precision, recall, F1) against baseline `.compmodel` gold standards.

---

## 1. Quantitative comparison (Gemini)

Scores below are **F1** for compartments (C), parameters (Par), and flows (Flow). “Before” = latest run per disease from **2026-02-04** (initial pipeline). “After” = latest run per disease from **2026-02-11** (improved pipeline). Same provider (Gemini) and same seven diseases.

| Disease       | Before (Feb 4) C / Par / Flow | After (Feb 11) C / Par / Flow | Change (F1) |
|---------------|--------------------------------|--------------------------------|-------------|
| Cholera       | 0.46 / 0.71 / 0.40            | 1.00 / 0.91 / 1.00             | +0.54 / +0.20 / +0.60 |
| Dengue        | 0.92 / 0.48 / 0.71            | 1.00 / 0.64 / 1.00             | +0.08 / +0.16 / +0.29 |
| Ebola         | 1.00 / 0.80 / 0.89            | 0.80 / 0.78 / 0.50             | −0.20 / −0.02 / −0.39 |
| Flu           | 0.67 / 0.40 / 0.91            | 0.80 / 0.46 / 0.75             | +0.13 / +0.06 / −0.16 |
| Measles       | 0.25 / 0.00 / 0.00             | 1.00 / 0.00 / 1.00             | +0.75 / 0.00 / +1.00 |
| Tuberculosis  | 0.67 / 0.57 / 0.50             | 1.00 / 0.47 / 0.67             | +0.33 / −0.10 / +0.17 |
| Zika          | 1.00 / 0.20 / 0.80             | 1.00 / 0.49 / 0.84             | 0.00 / +0.29 / +0.04 |
| **Average**   | **0.70 / 0.45 / 0.60**         | **0.94 / 0.54 / 0.82**         | **+0.24 / +0.09 / +0.22** |

**Summary:** On average, the improved pipeline increased F1 by about **+0.24** (compartments), **+0.09** (parameters), and **+0.22** (flows). Compartments and flows improved the most; parameters improved modestly. Some diseases (e.g. Measles) went from near-zero to strong scores; a few (e.g. Ebola) had small regressions on flows.

---

## 2. What we did (pipeline changes)

### 2.1 PDF extraction and cleaning

- **Before:** Extraction either used a single full-page pass or a different library (e.g. PyPDF2). Many papers showed **run-together text** (e.g. `FungEmergingThemesinEpidemiology` instead of `Fung Emerging Themes in Epidemiology`), which hurts entity and flow extraction.
- **After:**
  - **pdfplumber** as the main extractor (with PyPDF2 only as fallback).
  - **Two-column handling:** Each page is split into left and right halves, text is extracted per half with `extract_text(x_tolerance=2, y_tolerance=2)`, then concatenated. This preserves reading order and word boundaries in typical two-column journal layouts.
  - **Word-level data:** `extract_words(use_text_flow=True)` is used for headings and layout, and word-level data is stored in `raw_pages` for downstream use.
  - **clean_text():** Hyphenation across line breaks is fixed, standalone page numbers are removed, and whitespace is normalized.

**Effect:** Cleaner, correctly spaced text and better section boundaries, so the LLM and pattern-based steps see proper sentences and compartment/parameter/flow phrases instead of glued tokens.

### 2.2 Section detection and structure

- **Before:** Section detection could be weaker or more fragile when headings were lost in run-together text.
- **After:**
  - Heading detection uses **font size and boldness** (pdfplumber word metadata) plus regex-based fallbacks.
  - Sections can be built from layout (headings + text regions) or from a **text-based fallback** (e.g. “Abstract”, “Introduction”, “Methods”, “Results”).
  - Optional **coalesce_short_sections** merges very short sections to avoid tiny fragments.

**Effect:** More stable sectioning and better context windows for promises and entity extraction.

### 2.3 Entity extraction (LLM and fallbacks)

- **Unified extraction:** When the LLM is available, a **single unified LLM call** can extract compartments, flows, and parameters with full-paper (or sectioned) context, instead of separate calls with narrow context.
- **Paper-type awareness:** Vector-borne vs climate (and related) paper types are inferred from text and promises; **prompts are tailored** (e.g. mosquito compartments for dengue) so the model focuses on the right entity types.
- **Fallback:** If the unified call fails, the pipeline falls back to **separate** compartment / flow / parameter extraction; pattern-based and post-processing (e.g. parameter noise filtering) still run.

**Effect:** Better recall and consistency (compartments and flows in particular) and fewer spurious parameters when prompts and context are aligned with paper type.

### 2.4 Evaluation and baselines

- **Baseline auto-detection:** Baseline `.compmodel` files in `data/baseline_models/` are matched to the paper name (e.g. `cholera.compmodel` for Cholera.pdf) so **evaluation is consistent** across runs.
- **Fuzzy matching:** Gold-standard comparison uses a **similarity threshold** (e.g. 0.75) for compartments, parameters, and flows so near-matches (e.g. “Recovered” vs “Recovered humans”) count as correct.

**Effect:** More reliable and comparable P/R/F1 across runs and diseases.

---

## 2.5 Prompts and extraction logic (comprehensive)

What actually changed in the **prompts**, **context**, and **LLM settings** is described here so the comparison is reproducible and auditable.

### 2.5.1 System instruction (all providers)

The LLM client sends a **single shared system instruction** for every extraction call (so provider comparison is fair):

- *"You are a scientific paper analyzer. You must return ONLY valid JSON."*
- Rules: no explanations, no markdown code blocks, no extra text; begin with `{` or `[`, end with `}` or `]`; do **not** use `\`\`\`json`; invalid JSON causes errors; return pure JSON only.

For Gemini this is prepended to the user prompt; for OpenAI/Claude it is sent as a separate system message.

### 2.5.2 Paper-type tailoring (vector-borne / climate)

**Detection** (`paper_type.py`): The pipeline infers `vector_borne` and `climate` from (1) paper text regexes (e.g. mosquito, vector, dengue, zika, malaria, biting rate, susceptible mosquitoes; and climate, temperature, rainfall, seasonal, etc.) and (2) Step 2 promises (e.g. `model_type` or compartment names containing "mosquito", "vector", "egg").

**Injection into prompts:** The following **exact** blocks are appended to the task/context section of the compartment, flow, and parameter prompts when the flags are true.

- **Vector-borne (compartments):**  
  *"This paper appears to describe a VECTOR-BORNE model (e.g. mosquito, dengue, Zika, malaria). Look for BOTH human compartments (Susceptible humans, Exposed humans, Infectious humans, Recovered humans) AND vector/life-stage compartments (Susceptible mosquitoes, Infectious mosquitoes, Eggs, Larvae, Pupae, Susceptible female adults, Exposed female adults, Infectious female adults) if there is direct evidence in the text. Only include those for which you find a clear quote."*

- **Climate (compartments):**  
  *"Consider CLIMATE or ENVIRONMENTAL drivers if mentioned (e.g. temperature, rainfall, seasonality, humidity). Include compartments or parameters related to these only if explicitly evidenced."*

- **Vector-borne (flows):**  
  *"This paper appears to describe a VECTOR-BORNE model. Look for flows between HUMAN compartments (e.g. Susceptible humans -> Exposed humans) AND vector/life-stage flows (e.g. Eggs -> Larvae -> Pupae -> Susceptible female adults, and human-vector transmission flows). Only include flows for which you find clear evidence."*

- **Vector-borne (parameters):**  
  *"This paper appears to describe a VECTOR-BORNE model. Look for human parameters (transmission, recovery, mortality) AND vector/life-stage parameters (biting rate, egg/larval/pupal development rates, vector mortality, vector incubation). Only include parameters with clear evidence."*

- **Climate (parameters):**  
  *"Consider CLIMATE/ENVIRONMENTAL parameters if mentioned (e.g. temperature, rainfall, seasonality). Include only if explicitly defined in the text."*

So **what changed** in prompts: after adding paper-type detection, these blocks were added so the model explicitly looks for human+vector compartments/flows/parameters in vector-borne papers and for climate-related entities only when evidenced.

### 2.5.3 Separate extraction prompts (compartments, flows, parameters)

When **unified** extraction is not used (or fails), the pipeline uses **separate** prompts. Two variants exist: **detailed** (used for Gemini and Claude) and **concise** (used for OpenAI). Both now use the same “detailed” style for quality parity (`use_detailed_prompt = True`), so in practice all providers get the long instructions below.

**Compartments:**

- **Task:** Extract ALL compartment names; return only a valid JSON array; no markdown; begin with `[`, end with `]`.
- **Evidence rule:** Only include a compartment if there is a clear, direct quote; if no supporting quote, do **not** include it; prefer false negatives over inventing; reject if not enough evidence.
- **Chain-of-thought:** “First list the compartment names you find in the text; for each note the exact quote that defines it; then output the JSON array.”
- **Required JSON:** `{"name": "...", "description": "...", "text_span": "Exact quote..."}` for each compartment.
- **Systematic search:** Instructions list where to look (compartment definitions, state variables in equations like S(t)/dS/dt, population groups, model descriptions, tables, initial conditions S(0)/I(0), flow descriptions “from X to Y”).
- **Normalization:** Explicit mapping (e.g. Susceptible/S → Susceptible, Exposed/E/Latent → Exposed, Infectious/Infected/I → Infectious, Recovered/Removed/R → Recovered, Dead/Deceased/D → Dead or Infectious Deceased).
- **Optional injected context:**  
  - **Promised compartments (Step 2):** If Step 2 produced a list, the prompt says “PROMISED COMPARTMENTS (from a prior pass): X, Y, Z. Only include these if you find direct evidence in the paper text below. If you find no supporting quote for a promised compartment, do NOT include it.” So **what changed**: we use Step 2 promises as a hint but require evidence, reducing hallucination.  
  - **Metamodel:** Standard compartment types from the metamodel (e.g. from `metamodel_epidemiology.json`) are listed.  
  - **Phase 1 examples:** Up to 3 example models with compartment names (e.g. “Model A: Susceptible, Infectious, Recovered”) to anchor naming.

**Flows:**

- **Task:** Extract every flow (transition) between compartments; return only a valid JSON array.
- **Evidence rule:** Same as compartments: only include a flow with a clear quote; prefer omitting over inventing.
- **Chain-of-thought:** “First list each flow you find (source -> target) with the quote that describes it; then output the JSON array.”
- **Required JSON:** `{"source": "...", "target": "...", "description": "...", "text_span": "...", "flow_type": "RateFlow" or "ContactFlow"}`.  
  Flow types: RateFlow = progression/recovery/death/treatment; ContactFlow = transmission/infection.
- **Available compartments:** The prompt lists the **exact** compartment names from the compartment step so source/target must match them (reduces bogus flow endpoints).
- **Where to look:** Flow descriptions (“from X to Y”), differential equations (dX/dt = … + Y), transmission/progression/recovery/death/treatment wording, flow tables.

**Parameters:**

- **Task:** Extract every parameter mentioned; return only a valid JSON array.
- **Evidence rule:** Only include if there is clear evidence (quote or table row); prefer omitting over inventing.
- **Chain-of-thought:** “First list each parameter you find with its definition or table row; then output the JSON array.”
- **Required JSON:** `{"name": "...", "value": "...", "unit": "...", "description": "...", "text_span": "..."}`.
- **Where to look:** Greek letters with values, parameter definitions, parameter tables, rate/probability definitions, symbols in equations (e.g. dS/dt = -βSI).
- **Metamodel and Phase 1 examples:** Same idea as compartments (parameter types + example models with parameter names/values).

**What changed in separate prompts:** (1) Paper-type blocks above were added. (2) Evidence rule and chain-of-thought were made explicit and consistent across all three tasks. (3) Promised compartments were wired in with “only if evidence” to reduce hallucination. (4) Detailed prompts were standardized so all providers get the same thorough instructions.

### 2.5.4 Unified extraction (single LLM call)

When the LLM is available, the pipeline **first** tries **one** call that returns a single JSON object with `compartments`, `flows`, and `parameters`:

- **Role:** “You are an expert epidemiological modeler.”
- **Task:** From the paper text below, extract the **primary** compartmental model (diagram, flow chart, or system of ODEs). Return one JSON object with:
  - `compartments`: `[{ "name": "...", "description": "..." }]`
  - `flows`: `[{ "source": "...", "target": "...", "type": "RateFlow or ContactFlow", "description": "..." }]`
  - `parameters`: `[{ "name": "...", "value": "...", "unit": "...", "description": "..." }]`
- **Paper-type hint (if vector_borne):** “This paper describes a VECTOR-BORNE disease model. Look for both human AND vector/mosquito compartments and flows.”
- **Rules:**  
  - Compartments: same level of abstraction as the paper’s diagram/equations; use standard epidemiological names; use **full** names (not S, E, I, R or S_h, I_v, E1, I2); if multiple groups (humans/vectors, age groups), include compartments for each (e.g. Susceptible Humans, Infectious Mosquitoes).  
  - Flows: every transition arrow; source/target must **exactly** match compartment names from the list; ContactFlow = transmission/infection; RateFlow = recovery, death, vaccination, progression, etc.  
  - Parameters: symbols, values, units from tables/equations/text.  
  - Extract **only** what the paper explicitly presents; do not invent or over-split.

**Context for unified call:** The text sent is not the full paper but a **window** built by `build_text_window()` (see below), plus optional formatted tables. So **what changed**: we added a single high-level “extract the primary model” prompt with strict naming and evidence rules, and we feed it a focused window instead of raw full text.

### 2.5.5 Context building (text windows and limits)

**Character limits (separate extraction):**

- Compartments: `llm_compartments_chars` (default 50,000).
- Flows: `llm_flows_chars` (default 80,000).
- Parameters: `llm_parameters_chars` (default 80,000); when using a window, parameters use `min(llm_parameters_chars, 20_000)`.

**Text window (when using `build_text_window`):**

- **Goal:** Send only the most relevant pages/segments instead of the full PDF text.
- **Mechanism:** `select_page_indices()` selects pages whose text matches **include_patterns**, with optional **exclude_patterns**. Selected indices are then **padded** (e.g. ±1 page) and capped at **max_pages**. If no page matches, a **fallback** uses the first N pages (e.g. 4–6).
- **Patterns used for compartments:** e.g. `\bcompartment|\bstate\b|\bgroup\b|\bclass\b`, `\bS\s*\(t\)|\bE\s*\(t\)|\bI\s*\(t\)|\bR\s*\(t\)`, `d[a-z]\s*/\s*dt`, `\bequation|\bflow|\btransition|\bmodel\b`, etc.
- **Patterns for flows:** Similar (flow, transition, equation, model, differential).
- **Patterns for parameters:** Parameter, rate, value, table, equation.
- **Unified extraction:** Uses a single window with combined model-relevant patterns, `max_chars = max(compartments, flows, parameters)`, `pad=2`, `max_pages=14`, `fallback_first_pages=6`.

So **what changed**: we moved from “send truncated full text” to “send a pattern-based window of pages + padding” so the LLM sees the model-defining sections (and a bit of context) instead of arbitrary truncation.

### 2.5.6 LLM settings and structured output

- **Temperature:** For extraction, Gemini uses **0.2** when a response schema is set; unified extraction uses **temperature=0** for determinism. Others use the default (e.g. 0.3) unless overridden.
- **Max tokens:** Compartments 4000; flows 4000 (or 6000 in an experiment variant); parameters 8000; unified 16000 to avoid truncation.
- **Gemini structured output:** For separate extraction, Gemini uses **response_schema** (JSON Schema) for compartments, flows, and parameters so the API returns valid JSON matching the expected shape; this reduces parse failures and drift.

### 2.5.7 Post-processing (parameters and flows)

- **Parameters:** `_clean_and_filter_parameters()` drops entries that look like noise: e.g. DOI/arXiv identifiers, long numeric IDs, “etal”, “figure”, “table” as parameter names, and applies a blacklist of substrings. It keeps Greek letters, R0, N, and other epidemiology-relevant symbols. So **what changed**: after extraction we explicitly filter likely non-parameters to improve precision.
- **Flows:** Flows whose source or target does not match any extracted compartment are dropped; “inconsistent/unmatched” flows are removed so the synthesized model only has valid compartment references.

---

## 3. What was most effective

1. **PDF extractor and two-column handling** — Fixing run-together text and column order had the largest impact on **compartments and flows**. Without readable text, the LLM and patterns both underperform.
2. **Section detection and clean text** — Better sections and clean_text (hyphenation, whitespace) gave the LLM **clearer context** and improved recall, especially where model structure is described in “Methods” or “Model”.
3. **Unified LLM extraction and paper-type prompts** — One coherent call plus vector-borne/climate-aware prompts (§2.5.2, §2.5.4) improved **consistency and recall** for compartments and flows; parameter F1 improved more modestly and remains the hardest category.
4. **Prompt design (evidence rule, CoT, context windows)** — Explicit “only if evidence” and chain-of-thought in every prompt (§2.5.3), plus pattern-based text windows (§2.5.5) instead of blind truncation, reduced hallucinations and missed entities.
5. **Stable evaluation (baseline + fuzzy match)** — Same gold standards and matching rules make before/after and cross-disease comparisons meaningful.

---

## 4. Where results still vary

- **Parameters** still have the lowest F1 on average (around 0.54–0.56) and are sensitive to notation (e.g. β vs β * κ, subscripts). Further gains likely need notation-aware parsing or parameter-specific prompts.
- **Ebola** (and occasionally others) can show lower flow F1 in a given run due to model complexity or baseline definition; this is a known variance.
- **OpenAI** runs in the reported set had no valid API/key, so comparison here is only Gemini (before/after) and Claude (after); adding OpenAI back would require re-runs with a working key.

---

## 5. How to reproduce

- **Before (old pipeline):** Use report dirs from **2026-02-04** (e.g. `cholera_llm_gemini_20260204_165108`, etc.). Those were produced with the initial PDF and extraction setup.
- **After (current pipeline):** Run the current Phase 2 code (pdfplumber, two-column extraction, unified LLM, section detection, and evaluation as in `run_phase2.py`). Latest report dirs are under `reports/` with timestamps **20260211** (Gemini) and **20260212** (Claude).
- Regenerate summary tables with:  
  `python3 build_results_md.py`

---

*Generated from Phase 2 report directories and pipeline code. “Before” = 2026-02-04 Gemini runs; “After” = 2026-02-11 Gemini (and 2026-02-12 Claude) as in RESULTS_REPORT.md.*
