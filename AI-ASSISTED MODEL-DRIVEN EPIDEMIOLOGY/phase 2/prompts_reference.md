# LLM Prompts Reference — Phase 2 Pipeline

This file documents **all** prompts used in the Phase 2 LLM pipeline.  
It is a **reference only** — it is not loaded by any code.

Sources:
- `src/utils/llm_client.py` — generic system instruction
- `src/extraction/entity_extractor.py` — compartment / flow / parameter / unified prompts
- `src/extraction/paper_promise_extractor.py` — paper promise prompts

---

## 1. Generic System Instruction

**File:** `src/utils/llm_client.py` (line 232)

Used as the `system` parameter for OpenAI and Claude; prepended to the user prompt for Gemini.

```
You are a scientific paper analyzer. You must return ONLY valid JSON.
CRITICAL RULES:
- No explanations, no markdown code blocks, no extra text before or after
- Begin directly with '{' or '[' and end with '}' or ']'
- Do NOT use ```json or ``` markers
- Invalid JSON will cause errors
- Return pure JSON only
```

---

## 2. Compartment Extraction Prompts

**File:** `src/extraction/entity_extractor.py`

### Context variables built before the prompt (lines 564–654):

- `paper_type_context` — vector-borne / climate hints
- `promised_context` — promised compartments from Step 2 (pattern-based)
- `metamodel_context` — standard compartment types from metamodel
- `examples_context` — generic extraction examples or Phase 1 model examples

### Detailed variant (lines 659–750, used for Gemini):

```
********************************************************************************
TASK: Extract ALL compartment names from this epidemiological modeling paper.
********************************************************************************
CRITICAL OUTPUT FORMAT:
- Return ONLY a valid JSON array
- No explanations, no markdown, no code blocks
- Begin directly with '[' and end with ']'
- Do NOT use ```json or ``` markers
- Invalid JSON will cause errors
- Each object must be properly formatted JSON

{paper_type_context}{promised_context}{metamodel_context}{examples_context}
********************************************************************************
YOUR TASK:
You are an expert in epidemiological compartmental modeling. Your job is to identify every compartment that is clearly mentioned in this paper (with evidence). Do not add compartments that lack supporting quotes. Missing even one compartment will cause the model to be incomplete and incorrect.

COMPARTMENTS TO LOOK FOR:
- Standard SEIR compartments: Susceptible (S), Exposed (E), Infectious (I), Recovered (R)
- Disease-specific compartments: Dead, Deceased, Hospitalized, Quarantined, Vaccinated, Treated
- Vector compartments: Susceptible Mosquitoes, Infected Mosquitoes (for vector-borne diseases)
- Any other population groups or states mentioned in the model
- Look for compartment definitions, state variables, population groups, or model states

HOW TO IDENTIFY COMPARTMENTS - BE SYSTEMATIC:
1. Look for explicit compartment definitions: "compartment X represents...", "state X", "group X", "class X"
2. Look for state variables in equations: "S(t)", "I(t)", "E(t)", "dS/dt", "dI/dt" (differential equations)
3. Look for population groups: "susceptible individuals", "infected people", "recovered population"
4. Look for model descriptions: "the model includes compartments: X, Y, Z" or "we model X groups"
5. Look for compartment lists in tables, especially "Table of Compartments" or similar
6. Check for compartment names with letters in parentheses: "Susceptible (S)", "Infectious (I)"
7. Look for compartment indices: "Compartment 0: S", "Compartment 1: E", etc.
8. Check model diagrams descriptions if mentioned in text
9. Look for initial population definitions: "S(0) = ...", "I(0) = ..." (these indicate compartments)
10. Check for compartment transitions in flow descriptions: "from X to Y" implies both X and Y are compartments

REQUIRED JSON STRUCTURE:
Each compartment object MUST have these exact fields:
{
  "name": "CompartmentName",
  "description": "Brief description from paper",
  "text_span": "Exact quote showing where this compartment is mentioned"
}

EVIDENCE RULE - CRITICAL:
- Only include a compartment if you find clear, direct evidence (exact quote) in the paper text below.
- If you cannot find a supporting quote for a compartment, do NOT include it. Prefer omitting over inventing.
- Do not infer or assume compartments that are not explicitly stated or clearly implied in the text.
- Reject any compartment for which there is not enough evidence; it is better to have false negatives than hallucinations.

CHAIN-OF-THOUGHT (reason step by step): First list the compartment names you find in the text; for each note the exact quote that defines it; then output the JSON array. This reduces omissions and spurious entries.

EXTRACTION RULES - BE THOROUGH BUT EVIDENCE-BASED:
- Extract compartments that are clearly mentioned or defined; do not add compartments that are only implied or guessed
- If a compartment is mentioned multiple times, use the most detailed description for the description field
- For text_span, use the FIRST or MOST DEFINITIVE mention (where it's clearly defined)
- Normalize names to standard epidemiological terms:
  * "Susceptible" or "S" or "Susceptibles" → "Susceptible"
  * "Exposed" or "E" or "Latent" or "Incubating" → "Exposed"
  * "Infectious" or "Infected" or "I" or "Symptomatic" → "Infectious"
  * "Recovered" or "Removed" or "R" or "Immune" → "Recovered"
  * "Dead" or "Deceased" or "D" → "Dead" or "Infectious Deceased" (choose based on context)
  * "Hospitalized" or "H" → "Hospitalized"
  * "Quarantined" or "Q" → "Quarantined"
- Include the EXACT text quote (text_span) showing where each compartment is defined or mentioned
- Be comprehensive — missing compartments will cause model errors
- Only include a compartment if you have a clear text_span quote from the paper; if no quote, omit it
- Count compartments: If paper says "5 compartments" or "SEIR model", ensure you extract exactly that many (only if each has evidence)
- Check for secondary names: Some compartments have primary and secondary names (e.g., "Infectious, Untreated")

COMMON COMPARTMENT PATTERNS TO SEARCH FOR:
- "Susceptible (S) individuals..." or "S(t) represents..."
- "The model consists of X compartments: ..." or "we model X groups"
- "We model the following states: ..." or "the model includes..."
- "dS/dt", "dI/dt", "dE/dt" (differential equations — each variable is a compartment)
- "S(t)", "I(t)", "E(t)", "R(t)" (state variables in equations)
- "Table of Compartments" or "Compartment definitions"
- "Initial conditions: S(0)=..., I(0)=..." (initial values indicate compartments)
- "Compartment 0: ...", "Compartment 1: ..." (indexed lists)

VALIDATION CHECKLIST:
Before finalizing your response, verify:
- Did you find all compartments mentioned in model descriptions?
- Did you check differential equations for state variables?
- Did you look for compartment lists or tables?
- Did you check for compartments mentioned in flow descriptions?
- If the paper says "X compartments" or "SEIR model", do you have that many?

********************************************************************************
PAPER TEXT:
{truncated_text}
********************************************************************************
Return ONLY valid JSON array starting with '[' and ending with ']'. Extract ALL compartments mentioned in the paper. Be thorough and systematic.
```

### Concise variant (lines 753–794, used for OpenAI):

```
********************************************************************************
TASK: Extract all compartment names from this epidemiological modeling paper.
********************************************************************************
OUTPUT FORMAT: Return ONLY a valid JSON array. No markdown, no explanations. Start with '[' and end with ']'.

{paper_type_context}{promised_context}{metamodel_context}{examples_context}
********************************************************************************
EVIDENCE RULE: Only include a compartment if you find clear, direct evidence (exact quote) in the paper. If you cannot find a supporting quote, do NOT include it. Prefer false negatives over inventing entities.

REASON STEP BY STEP: First list compartment names you find; for each cite the exact quote; then output the JSON array.

EXTRACTION INSTRUCTIONS:
Identify compartments by looking for:
- Compartment definitions ("compartment X", "state X", "group X")
- State variables in equations ("S(t)", "I(t)", "dS/dt", "dI/dt")
- Model descriptions ("the model includes compartments: X, Y, Z")
- Compartment lists in tables
- Initial conditions ("S(0) = ...", "I(0) = ...")
Only output compartments for which you have a clear text_span quote. Reject if no evidence.

REQUIRED JSON STRUCTURE:
Each object must have: "name", "description", "text_span"
{
  "name": "CompartmentName",
  "description": "Brief description",
  "text_span": "Exact quote from paper"
}

NORMALIZATION:
- "Susceptible"/"S" → "Susceptible"
- "Exposed"/"E"/"Latent" → "Exposed"
- "Infectious"/"Infected"/"I" → "Infectious"
- "Recovered"/"Removed"/"R" → "Recovered"
- "Dead"/"Deceased"/"D" → "Dead" (or "Infectious Deceased" if context requires)

EXTRACT ALL compartments mentioned. Include exact text quotes. If paper mentions "X compartments", ensure you extract that many.

********************************************************************************
PAPER TEXT:
{truncated_text}
********************************************************************************
Return ONLY valid JSON array: [{"name": "...", "description": "...", "text_span": "..."}, ...]
```

---

## 3. Flow Extraction Prompts

**File:** `src/extraction/entity_extractor.py`

### Context variables built before the prompt (lines 970–1048):

- `comp_list` — comma-joined list of known compartment names
- `paper_type_context` — vector-borne flow hints
- `metamodel_context` — flow types from metamodel
- `examples_context` — generic flow extraction examples or Phase 1 fallback
- `flow_format_extra` — experiment variant flags

### Detailed variant (lines 1054–1120, used for Gemini):

```
********************************************************************************
TASK: Extract flows (transitions) between compartments from this epidemiological modeling paper.
********************************************************************************
CRITICAL OUTPUT FORMAT:
- Return ONLY a valid JSON array
- No explanations, no markdown, no code blocks
- Begin directly with '[' and end with ']'
- Do NOT use ```json or ``` markers
- Invalid JSON will cause errors
- Each object must be properly formatted JSON

AVAILABLE COMPARTMENTS: {comp_list}
{paper_type_context}{metamodel_context}{examples_context}
********************************************************************************
YOUR TASK:
You are an expert in epidemiological compartmental modeling.
You MUST extract EVERY flow (transition) between compartments that is implied by:
- any model equation (dS/dt, S(t+Δt), etc.)
- any text description ("from X to Y", "X → Y", "X moves to Y")
- any diagram/table caption that describes transitions.
Missing a flow is considered an error.

HOW TO IDENTIFY FLOWS:
1. Look for explicit flow descriptions: "from X to Y", "X → Y", "X transitions to Y"
2. Look for differential equations: "dX/dt = ... + Y" indicates flow from Y to X
3. Look for transmission/infection flows: "Susceptible becomes Infectious", "S → I"
4. Look for progression flows: "Exposed progresses to Infectious", "E → I"
5. Look for recovery flows: "Infectious recovers", "I → R"
6. Look for death flows: "Infectious dies", "I → D"
7. Look for treatment flows: "Infectious receives treatment", "I → T"
8. Check for flow tables or lists in the paper
9. Look for compartment transitions in model descriptions

REQUIRED JSON STRUCTURE:
Each flow object MUST have these exact fields:
{
  "source": "SourceCompartmentName",
  "target": "TargetCompartmentName",
  "description": "Brief description of the flow",
  "text_span": "Exact quote showing where this flow is described",
  "flow_type": "RateFlow" or "ContactFlow"
}
{flow_format_extra}
FLOW TYPE RULES:
- RateFlow: progression (E→I), recovery (I→R), death (I→D), treatment (I→T)
- ContactFlow: transmission/infection (S→E, S→I) involving contact between compartments
- Match source/target to available compartments exactly (case-sensitive)

EVIDENCE RULE - CRITICAL:
- Only include a flow if you find clear, direct evidence (exact quote) in the paper text below.
- If you cannot find a supporting quote for a flow, do NOT include it.
- Do not infer or assume flows that are not explicitly stated or clearly implied (e.g. in equations or diagram descriptions).
- Reject any flow for which there is not enough evidence.

REASON STEP BY STEP: First list each flow you find (source -> target) with the quote that describes it; then output the JSON array. This reduces missed or invented flows.

EXTRACTION RULES - BE THOROUGH AND EVIDENCE-BASED:
- Extract ALL flows that are clearly mentioned or implied by equations/diagrams; be thorough
- Use ONLY these compartment names for source/target (map symbols like S(t), E1(t) to the closest compartment): {comp_list}
- Include exact text quotes as evidence for each flow; if no quote, omit the flow
- If unsure about flow type, use RateFlow for progression/recovery/death, ContactFlow for transmission

********************************************************************************
PAPER TEXT:
{truncated_text}
********************************************************************************
Return ONLY valid JSON array starting with '[' and ending with ']'. Extract flows for which you have clear evidence.
```

### Concise variant (lines 1123–1163, used for OpenAI):

```
********************************************************************************
TASK: Extract flows (transitions) between compartments from this epidemiological modeling paper.
********************************************************************************
OUTPUT FORMAT: Return ONLY a valid JSON array. No markdown, no explanations. Start with '[' and end with ']'.

AVAILABLE COMPARTMENTS: {comp_list}
{paper_type_context}{metamodel_context}{examples_context}
********************************************************************************
EVIDENCE RULE: Only include a flow if you find clear, direct evidence (exact quote) in the paper. If you cannot find a supporting quote, do NOT include it. Be thorough — extract all flows you can find evidence for.

REASON STEP BY STEP: First list each flow (source -> target) with the quote; then output the JSON array.

EXTRACTION INSTRUCTIONS:
Identify flows by looking for:
- Flow descriptions ("from X to Y", "X → Y", "X transitions to Y")
- Differential equations ("dX/dt = ... + Y" indicates flow from Y to X)
- Transmission flows ("Susceptible becomes Infectious")
- Progression/recovery/death flows
- ALL flows for which you have evidence — be thorough

REQUIRED JSON STRUCTURE:
Each object must have: "source", "target", "description", "text_span", "flow_type"
{
  "source": "SourceCompartmentName",
  "target": "TargetCompartmentName",
  "description": "Brief description",
  "text_span": "Exact quote from paper",
  "flow_type": "RateFlow" or "ContactFlow"
}

FLOW TYPES:
- RateFlow: progression, recovery, death, treatment
- ContactFlow: transmission, infection

Match source/target to available compartments exactly: {comp_list}
{flow_format_extra}
********************************************************************************
PAPER TEXT:
{truncated_text}
********************************************************************************
Return ONLY valid JSON array: [{"source": "...", "target": "...", "description": "...", "text_span": "...", "flow_type": "..."}, ...]
```

---

## 4. Parameter Extraction Prompts

**File:** `src/extraction/entity_extractor.py`

### Context variables built before the prompt (lines 1678–1757):

- `paper_type_context` — vector-borne / climate parameter hints
- `metamodel_context` — parameter types from metamodel
- `examples_context` — generic parameter extraction examples or Phase 1 fallback

### Detailed variant (lines 1763–1827, used for Gemini):

```
********************************************************************************
TASK: Extract model parameters from this epidemiological modeling paper.
********************************************************************************
CRITICAL OUTPUT FORMAT:
- Return ONLY a valid JSON array
- No explanations, no markdown, no code blocks
- Begin directly with '[' and end with ']'
- Do NOT use ```json or ``` markers
- Invalid JSON will cause errors
- Each object must be properly formatted JSON

{paper_type_context}{metamodel_context}{examples_context}
********************************************************************************
YOUR TASK:
You are an expert in epidemiological modeling. Extract EVERY parameter mentioned in this paper.

HOW TO IDENTIFY PARAMETERS:
1. Look for Greek letters with values: "β = 0.5", "α = 0.1", "γ = 0.2"
2. Look for parameter definitions: "transmission rate β", "recovery rate γ"
3. Look for parameter tables: "Table of Parameters" or parameter lists
4. Look for rate definitions: "rate = 0.3", "contact rate = 0.5"
5. Look for probability definitions: "probability p = 0.8"
6. Check equations for parameter symbols: "dS/dt = -βSI"
7. Look for parameter descriptions in text
8. Check for parameter values in model setup sections

REQUIRED JSON STRUCTURE:
Each parameter object MUST have these exact fields:
{
  "name": "ParameterSymbol",
  "value": "numerical_value_if_specified",
  "unit": "unit_if_specified",
  "description": "Brief description from paper",
  "text_span": "Exact quote showing where parameter is defined (include full context)"
}

EVIDENCE RULE - CRITICAL:
- Only include a parameter if you find clear, direct evidence (exact quote or table row) in the paper text below.
- If you cannot find a supporting quote or definition for a parameter, do NOT include it. Prefer omitting over inventing.
- Do not infer or assume parameters that are not explicitly stated or defined in the text or tables.
- Reject any parameter for which there is not enough evidence.

REASON STEP BY STEP: First list each parameter you find with its definition or table row; then output the JSON array. This reduces missed or invented parameters.

EXTRACTION RULES - BE THOROUGH BUT EVIDENCE-BASED:
- Extract parameters that are clearly defined or listed; do not add parameters that are only implied or guessed
- Focus on Greek letters (α, β, γ, δ, μ, ρ, σ, θ, λ, etc.) representing rates when they appear in the text/tables
- Include Latin letter parameters (R, N, etc.) only if the paper explicitly defines them as model constants
- Include exact text quotes or table references for text_span; if no quote, omit the parameter
- If value is not specified, use null or omit value field

COMMON PARAMETER TYPES:
- Transmission rate: β, beta, contact_rate
- Recovery rate: γ, gamma, recovery_rate
- Death rate: μ, mu, death_rate, mortality_rate
- Incubation rate: σ, sigma, incubation_rate
- Birth rate: λ, lambda, birth_rate
- Contact rate: c, contact_rate
- Probability: p, probability

********************************************************************************
PAPER TEXT:
{truncated_text}
********************************************************************************
Return ONLY valid JSON array starting with '[' and ending with ']'. Extract ALL parameters mentioned in the paper.
```

### Concise variant (lines 1830–1866, used for OpenAI):

```
********************************************************************************
TASK: Extract model parameters from this epidemiological modeling paper.
********************************************************************************
OUTPUT FORMAT: Return ONLY a valid JSON array. No markdown, no explanations. Start with '[' and end with ']'.

{paper_type_context}{metamodel_context}{examples_context}
********************************************************************************
EVIDENCE RULE: Only include a parameter if you find clear, direct evidence (exact quote or table) in the paper. If you cannot find a supporting quote, do NOT include it. Prefer false negatives over inventing.

REASON STEP BY STEP: First list each parameter with its quote or table reference; then output the JSON array.

EXTRACTION INSTRUCTIONS:
Identify parameters by looking for:
- Greek letters with values ("β = 0.5", "α = 0.1")
- Parameter definitions ("transmission rate β", "recovery rate γ")
- Parameter tables or lists
- Rate definitions ("rate = 0.3", "contact rate = 0.5")
- Parameter symbols in equations ("dS/dt = -βSI")
Only output parameters for which you have a clear text_span quote or table reference. Reject if no evidence.

REQUIRED JSON STRUCTURE:
Each object must have: "name", "value", "unit", "description", "text_span"
{
  "name": "ParameterSymbol",
  "value": "numerical_value_if_specified",
  "unit": "unit_if_specified",
  "description": "Brief description",
  "text_span": "Exact quote from paper (include context)"
}

EXTRACT parameters that have clear evidence. Include exact text quotes. Reject if no evidence.

********************************************************************************
PAPER TEXT:
{truncated_text}
********************************************************************************
Return ONLY valid JSON array: [{"name": "...", "value": "...", "unit": "...", "description": "...", "text_span": "..."}, ...]
```

---

## 5. Unified Extraction Prompt (single-pass)

**File:** `src/extraction/entity_extractor.py` (lines 2045–2080)

Used as an optimization when the caller requests a single LLM call for compartments + flows + parameters.

```
You are an expert epidemiological modeler.
From the paper text below, extract the PRIMARY compartmental model as presented
in the paper's model diagram, flow chart, or system of differential equations.

Return a single JSON object:
{
  "compartments": [{"name": "...", "description": "..."}],
  "flows": [{"source": "...", "target": "...", "type": "RateFlow or ContactFlow", "description": "..."}],
  "parameters": [{"name": "...", "value": "...", "unit": "...", "description": "..."}]
}
{paper_type_hint}
Rules:
- Compartments: Extract the compartments at the same level of abstraction as the
  paper's model diagram or differential equations. Use the standard epidemiological names
  (e.g. "Susceptible", "Exposed", "Infectious", "Recovered").
  Use full descriptive names — NOT single-letter abbreviations (S, E, I, R),
  subscripted symbols (S_h, I_v), or numbered variants (E1, I2).
  If the model has multiple population groups (humans/vectors, children/adults),
  include compartments for EACH group (e.g. "Susceptible Humans", "Infectious Mosquitoes").
  If the paper groups sub-states into one compartment (e.g. both symptomatic and
  asymptomatic into "Infectious"), use the aggregated name from the model diagram.
- Flows: Every transition arrow in the model.
  source and target MUST exactly match compartment names from your list above.
  ContactFlow = transmission/infection (involves contact between groups).
  RateFlow = all other transitions (recovery, death, vaccination, progression, etc.).
- Parameters: Model parameters with their symbols, values, and units when available.
  Include parameters from tables, equations, and text.
- Extract ONLY what the paper explicitly presents in its model. Do not invent entities
  or split compartments beyond what the paper's model structure shows.

PAPER TEXT:
{window}

{tables_text}

Return ONLY valid JSON.
```

---

## 6. Paper Promise Extraction Prompts

**File:** `src/extraction/paper_promise_extractor.py`

### Context variables (lines 123–129):

- `metamodel_schema` — JSON-serialized `epimde_compartmental_metamodel.compartment_types` from metamodel (if available)

### Detailed variant (lines 138–189, inside `build_prompt()` closure):

```
********************************************************************************
TASK: Extract what the paper PROMISES to model from this epidemiological modeling paper.
********************************************************************************
CRITICAL OUTPUT FORMAT:
- Return ONLY a valid JSON object
- No explanations, no markdown, no code blocks
- Begin directly with '{' and end with '}'
- Do NOT use ```json or ``` markers
- Invalid JSON will cause errors
- Each field must be properly formatted JSON

{metamodel_schema}
********************************************************************************
YOUR TASK:
You are an expert in epidemiological modeling. Extract what the paper EXPLICITLY promises or describes to model.

HOW TO IDENTIFY PROMISES:
1. Look for model descriptions: "we model X compartments", "the model includes..."
2. Look for compartment lists: "compartments: S, E, I, R"
3. Look for model type statements: "SEIR model", "SIR model", "vector-borne model"
4. Look for stratification mentions: "age-stratified", "stratified by age/gender/risk"
5. Look for intervention descriptions: "vaccination", "treatment", "quarantine"
6. Look for parameter mentions: "parameters include β, γ, μ"
7. Check abstract and introduction sections
8. Look for model setup or methodology sections

REQUIRED JSON STRUCTURE:
{
  "compartments": ["list", "of", "compartment", "names", "promised"],
  "stratifications": ["list", "of", "stratification", "dimensions", "e.g.", "age", "risk_group"],
  "parameters": ["list", "of", "parameter", "names", "mentioned"],
  "interventions": ["list", "of", "interventions", "e.g.", "vaccination", "treatment"],
  "model_type": "SEIR" or "SIR" or "Vector-Borne" etc.,
  "description": "brief description of what the paper promises to model"
}

EXTRACTION RULES - BE PRECISE:
- Focus ONLY on what is EXPLICITLY stated in the paper
- Don't infer or add things not mentioned
- If something is not mentioned, use an empty list []
- Be precise and only include what is explicitly stated
- Extract model type from paper (SEIR, SIR, SEIRS, Vector-Borne, etc.)
- Identify stratification dimensions if mentioned (age, gender, risk_group, etc.)
- List interventions if discussed (vaccination, treatment, quarantine, etc.)
- Extract compartment names if explicitly listed
- Extract parameter names if mentioned

********************************************************************************
PAPER TEXT:
{paper_chunk_text}
********************************************************************************
Return ONLY valid JSON object starting with '{' and ending with '}'.
```

### Concise variant (lines 192–223, inside `build_prompt()` closure):

```
********************************************************************************
TASK: Extract what the paper PROMISES to model from this epidemiological modeling paper.
********************************************************************************
OUTPUT FORMAT: Return ONLY a valid JSON object. No markdown, no explanations. Start with '{' and end with '}'.

{metamodel_schema}
********************************************************************************
EXTRACTION INSTRUCTIONS:
Identify what the paper explicitly promises to model:
- Compartments: Look for "we model X compartments", compartment lists
- Model type: Look for "SEIR", "SIR", "vector-borne" mentions
- Stratifications: Look for "age-stratified", "stratified by..."
- Interventions: Look for "vaccination", "treatment", "quarantine"
- Parameters: Look for parameter mentions or lists

REQUIRED JSON STRUCTURE:
{
  "compartments": ["list", "of", "compartment", "names"],
  "stratifications": ["list", "of", "stratification", "dimensions"],
  "parameters": ["list", "of", "parameter", "names"],
  "interventions": ["list", "of", "interventions"],
  "model_type": "SEIR" or "SIR" etc.,
  "description": "brief description"
}

Focus ONLY on what is explicitly stated. Use empty lists [] if not mentioned.

********************************************************************************
PAPER TEXT:
{paper_chunk_text}
********************************************************************************
Return ONLY valid JSON object: {"compartments": [...], "stratifications": [...], "parameters": [...], "interventions": [...], "model_type": "...", "description": "..."}
```
