# Phase 2: Automated Model Extraction from Papers - Comprehensive Plan

**Version:** 1.0  
**Status:** Planning Phase

---

## Table of Contents

1. [Overview](#overview)
2. [Goals and Objectives](#goals-and-objectives)
3. [Inputs and Outputs](#inputs-and-outputs)
4. [Step-by-Step Pipeline](#step-by-step-pipeline)
5. [LLM Integration Points](#llm-integration-points)
6. [Gap Filler System](#gap-filler-system)
7. [Scoring and Evaluation](#scoring-and-evaluation)
8. [Technical Implementation](#technical-implementation)
9. [File Structure](#file-structure)
10. [Dependencies](#dependencies)
11. [Testing Strategy](#testing-strategy)
12. [Success Criteria](#success-criteria)

---

## 1. Overview

Phase 2 automates the extraction of compartmental epidemiological models from scientific papers. Given a paper PDF, the system:

1. Extracts what the paper **promises** to model (scope)
2. Extracts detailed model entities (compartments, flows, parameters, stratifications, interventions)
3. Synthesizes a draft `.compmodel` file
4. Performs gap analysis (paper promises vs. extracted model)
5. Suggests gap fills using prior knowledge and domain expertise
6. Validates and evaluates the extracted model

**Key Principle:** Faithfulness to the paper - only extract what the paper explicitly describes or promises. Gap filler suggestions are clearly marked as "suggested, not in paper."

---

## 2. Goals and Objectives

### Primary Goals

1. **Automated Extraction**: Convert paper PDFs to structured model representations automatically
2. **Evidence-Based**: Every extracted element must have traceable evidence (paper text, page, span)
3. **Faithfulness**: Extract only what papers promise/describe, not best-practice additions
4. **Gap Identification**: Identify missing elements that papers promise but aren't extracted
5. **Gap Filling**: Suggest how to fill gaps using paper text, prior models, and domain knowledge
6. **Validation**: Reuse Phase 1 analyzers to validate extracted models

### Secondary Goals

1. **Scalability**: Process multiple papers automatically
2. **Reproducibility**: All extractions are reproducible with evidence trails
3. **Evaluation**: Quantitative metrics for extraction quality
4. **Extensibility**: Easy to add new extraction patterns and methods

---

## 3. Inputs and Outputs

### Inputs

- **Paper PDFs**: Scientific papers describing epidemiological models
- **Metamodel**: `phase1/metamodel_epidemiology.json` (epidemiology-only schema for LLM prompts)
- **Prior Models** (optional): Phase 1 model analysis JSONs for gap filling
- **Gold Standards** (optional): Manually annotated papers for evaluation

### Outputs (Per Paper)

1. **`paper_text.json`**: Cleaned paper text, sections, tables
2. **`paper_sections.json`**: Structured sections (Abstract, Methods, Model, Parameters, etc.)
3. **`paper_promises.json`**: What the paper promises to model (scope)
4. **`extracted_entities.json`**: Detailed entities with evidence (compartments, flows, parameters, etc.)
5. **`model_draft.compmodel`**: Auto-generated draft model file
6. **`traceability.json`**: Every model element → paper evidence mapping
7. **`phase2_gap_report.json`**: Paper promises vs. extracted model gaps
8. **`gap_fill_suggestions.json`**: Suggested gap fills with sources
9. **`model_analysis.json`**: Phase 1 model analyzer results on draft
10. **`uncertainty_analysis.json`**: Parameter uncertainty analysis
11. **`sensitivity_analysis.json`**: Sensitivity analysis (if runnable)

**All outputs are JSON** (except `.compmodel` which is XML) for programmatic access and reproducibility.

---

## 4. Step-by-Step Pipeline

### Step 1: PDF Ingestion and Cleaning

**Module:** `phase2/pdf_pipeline.py`

**Tasks:**
1. Extract text from PDF using `pdfplumber` (preferred) or `PyPDF2` (fallback)
2. Clean text:
   - Remove headers/footers
   - Fix hyphenation (join words split across lines)
   - Remove page numbers
   - Strip references section (optional, configurable)
3. Detect sections:
   - Abstract
   - Introduction
   - Methods
   - Model Description
   - Parameters
   - Results
   - Discussion
   - References
4. Extract tables:
   - Parameter tables
   - Model structure tables
   - Initial condition tables
5. Extract figures/captions (optional, for future use)

**Output:**
- `paper_text.json`: Full cleaned text, page numbers, section boundaries
- `paper_sections.json`: Structured sections with metadata

**Implementation Notes:**
- Use `pdfplumber` for better table extraction
- Store page numbers for evidence tracking
- Preserve section boundaries for targeted extraction

---

### Step 2: Paper Promises Extraction (Scope)

**Module:** `phase2/paper_promise_extractor.py` (extends Phase 1 version)

**Tasks:**
1. Extract paper promises using hybrid approach:
   - **Pattern-based first**: Regex patterns for common promises
   - **LLM fallback**: If patterns insufficient, use LLM with metamodel schema
2. Extract promises for:
   - **Compartments**: What compartments does the paper promise?
   - **Stratifications**: Age, gender, risk groups, location?
   - **Parameters**: What parameters are mentioned?
   - **Interventions**: Vaccination, treatment, NPIs?
   - **Model Type**: SEIR, SIR, Vector-borne, etc.?
   - **Description**: Brief summary of model scope

**LLM Prompt Structure:**
```
You are analyzing a scientific paper about epidemiological modeling.
Extract what the paper PROMISES to model. Return ONLY a JSON object.

Use this schema: [include metamodel_epidemiology.json structure]

Focus on what the paper EXPLICITLY promises or describes.
Don't infer or add things not mentioned.

Paper text: [truncated to ~50k chars]
```

**Output:**
- `paper_promises.json`: Structured promises with confidence levels

**Validation:**
- JSON schema validation against expected structure
- Check for required fields (compartments, model_type)

---

### Step 3: Entity Extraction with Evidence

**Module:** `phase2/entity_extractor.py`

**Tasks:**
1. Extract compartments:
   - Pattern-based: Look for compartment lists, state diagrams, model equations
   - LLM-based: Ask LLM to identify compartments from model description
   - Evidence: Store paper text span, page number, confidence

2. Extract flows:
   - Pattern-based: Look for transition verbs ("progress to", "infect", "recover")
   - Equation-based: Parse differential equations (dS/dt, dI/dt, etc.)
   - LLM-based: Extract flows from model description
   - Evidence: Store source text, page, confidence

3. Extract parameters:
   - Table extraction: Extract from parameter tables
   - Text extraction: Look for parameter definitions with values
   - Equation extraction: Extract from model equations
   - LLM-based: Identify parameters with values, units, descriptions
   - Evidence: Store value, unit, source text, page, confidence

4. Extract stratifications:
   - Pattern-based: Look for "age-stratified", "by age group", etc.
   - Table extraction: Extract from stratification tables
   - LLM-based: Identify stratification dimensions
   - Evidence: Store dimension names, levels, source text

5. Extract interventions:
   - Pattern-based: Look for "vaccination", "treatment", "quarantine"
   - LLM-based: Identify intervention types
   - Evidence: Store intervention type, description, source text

**Evidence Structure (for each entity):**
```json
{
  "raw_text": "original text from paper",
  "normalized_name": "canonical name",
  "page_number": 5,
  "text_span": "exact quote",
  "extraction_method": "pattern|llm|table",
  "confidence": "high|medium|low",
  "paper_backed": true
}
```

**Output:**
- `extracted_entities.json`: All entities with evidence

**Normalization:**
- Normalize compartment names (e.g., "Infected" → "Infectious")
- Normalize parameter names (e.g., "beta" → "β", "transmission_rate" → "β")
- Use metamodel compartment types as reference

---

### Step 4: Model Synthesis

**Module:** `phase2/model_synthesizer.py`

**Tasks:**
1. Map extracted entities to `.compmodel` structure:
   - Compartments → `<compartments>` elements
   - Flows → `<outgoingFlows>` elements (RateFlow or ContactFlow)
   - Parameters → `<parameters>` elements
   - Stratifications → `<groups>` and `<products>` elements

2. Create compartments:
   - Use extracted compartment names
   - Set initial populations (if extracted)
   - Add SecondaryName for stratifications

3. Create flows:
   - Determine flow type (RateFlow vs. ContactFlow) from context
   - Set rates (numeric or parameter reference)
   - Add descriptions from evidence
   - Handle stratum-specific rates if stratification present

4. Create parameters:
   - Use extracted parameter names (preserve Unicode)
   - Set values, units, descriptions
   - Use CONSTANT type by default
   - Mark as VARIABLE if environmental dependency

5. Create stratifications:
   - Create groups for each stratification dimension
   - Create products for combined stratifications
   - Link compartments to products

6. Validation:
   - Validate against metamodel schema
   - Check reference consistency (compartments, parameters)
   - Ensure valid XML structure

**Output:**
- `model_draft.compmodel`: Valid XML model file

**Handling Missing Information:**
- Use placeholder values (0.0 for rates) if not extracted
- Add TODO comments in descriptions
- Mark as incomplete in metadata

---

### Step 5: Traceability Mapping

**Module:** `phase2/traceability.py`

**Tasks:**
1. Create bidirectional mapping:
   - Model element → Paper evidence
   - Paper evidence → Model elements

2. For each model element:
   - Link to source entity in `extracted_entities.json`
   - Include paper text span, page, extraction method
   - Include confidence level
   - Mark as "paper_backed" or "suggested"

3. Generate traceability report:
   - Coverage: % of model elements with evidence
   - Confidence distribution
   - Extraction method distribution

**Output:**
- `traceability.json`: Complete traceability mapping

**Structure:**
```json
{
  "compartments": {
    "Susceptible": {
      "evidence": [
        {
          "text_span": "S(t) represents susceptible individuals",
          "page": 3,
          "method": "llm",
          "confidence": "high"
        }
      ],
      "paper_backed": true
    }
  },
  "flows": { ... },
  "parameters": { ... }
}
```

---

### Step 6: Gap Analysis (Paper-Driven)

**Module:** `phase2/gap_analyzer.py` (extends Phase 1 `gap_analyzer.py`)

**Tasks:**
1. Load paper promises from `paper_promises.json`
2. Load extracted model structure from `model_draft.compmodel`
3. Compare promises vs. extracted model:
   - Missing promised compartments
   - Missing promised stratifications
   - Missing promised parameters
   - Missing promised interventions

4. Generate gap report:
   - List all gaps with severity (critical/medium/low)
   - Link gaps to paper promises
   - Explain why each gap is identified

**Output:**
- `phase2_gap_report.json`: Structured gap report

**Key Difference from Phase 1:**
- **Phase 1**: Used hardcoded disease-specific rules (fallback to generic)
- **Phase 2**: Uses **only** paper promises (faithful to paper, no best-practice additions)

**Gap Severity:**
- **Critical**: Missing promised compartment, stratification, or required parameter
- **Medium**: Missing promised intervention or optional parameter
- **Low**: Missing best-practice element (not in paper promises)

---

### Step 7: Gap Filler System

**Module:** `phase2/gap_filler.py`

**Tasks:**
1. For each gap identified in `phase2_gap_report.json`:

   **Strategy 1: Paper Text Re-examination**
   - Re-search paper text for weak signals
   - Look for synonyms, related terms
   - Check if gap is mentioned but not explicitly promised
   - If found: Mark as "paper_backed" with lower confidence

   **Strategy 2: Prior Model Knowledge**
   - Search Phase 1 model analysis JSONs
   - Find similar models (same disease, similar structure)
   - Extract how they handle the gap
   - Suggest based on prior models
   - Mark as "suggested, from_prior_model"

   **Strategy 3: Domain Knowledge**
   - Use metamodel compartment types
   - Use common epidemiological patterns
   - Suggest standard compartment/flow structures
   - Mark as "suggested, domain_knowledge"

2. Generate gap fill suggestions:
   - Proposed element (compartment, flow, parameter, etc.)
   - Suggested value/range (if parameter)
   - Source: paper_span | prior_model | domain_knowledge
   - Confidence level
   - Rationale/justification

**Output:**
- `gap_fill_suggestions.json`: All suggestions with sources

**Structure:**
```json
{
  "gaps": [
    {
      "gap_id": "missing_compartment_asymptomatic",
      "gap_type": "compartment",
      "severity": "critical",
      "promised_in_paper": true,
      "suggestions": [
        {
          "proposed_element": {
            "type": "compartment",
            "PrimaryName": "Asymptomatic",
            "population": 0
          },
          "source": "paper_span",
          "source_text": "Asymptomatic individuals are not explicitly modeled but mentioned in text",
          "page": 5,
          "confidence": "medium",
          "rationale": "Paper mentions asymptomatic transmission but doesn't include compartment"
        },
        {
          "proposed_element": {
            "type": "compartment",
            "PrimaryName": "Asymptomatic",
            "population": 0
          },
          "source": "prior_model",
          "source_model": "covid_19_analysis.json",
          "confidence": "high",
          "rationale": "COVID-19 model includes Asymptomatic compartment with similar structure"
        }
      ]
    }
  ]
}
```

**Important:**
- **Never** mark domain knowledge suggestions as "paper_backed"
- Always distinguish between paper evidence and suggestions
- Provide clear rationale for each suggestion

---

### Step 8: Quality Checks (Reuse Phase 1 Analyzers)

**Module:** `phase2/quality_checks.py`

**Tasks:**
1. **Model Analysis**:
   - Run Phase 1 `model_analyzer.py` on `model_draft.compmodel`
   - Generate structural analysis
   - Export to JSON

2. **Uncertainty Analysis**:
   - Run Phase 1 `uncertainty_analyzer.py` on extracted parameters
   - Check for missing literature ranges
   - Check for missing sources
   - Mark parameters needing literature review

3. **Sensitivity Analysis** (if model is runnable):
   - Run Phase 1 `sensitivity_analysis.py`
   - Use Morris method (or other methods)
   - Identify key parameters
   - If not runnable: Mark as "not_runnable_yet" with reason

**Output:**
- `model_analysis.json`: Structural analysis
- `uncertainty_analysis.json`: Parameter uncertainty
- `sensitivity_analysis.json`: Sensitivity results (or "not_runnable" status)

**Integration:**
- Reuse existing Phase 1 analyzers (no code duplication)
- Call as subprocess or import directly
- Ensure JSON-only outputs

---

### Step 9: Evaluation and Scoring

**Module:** `phase2/evaluator.py`

**Tasks:**
1. **Precision/Recall** (if gold standard available):
   - Compare extracted entities to gold standard
   - Calculate precision = TP / (TP + FP)
   - Calculate recall = TP / (TP + FN)
   - Report micro and macro averages

2. **Traceability Coverage**:
   - % of extracted items with evidence
   - Average evidence spans per item
   - Confidence distribution

3. **Faithfulness**:
   - % of extracted items supported by paper text
   - % of model elements with paper evidence
   - Distinguish paper-backed vs. suggested

4. **Gap False-Positive Rate**:
   - Compare flagged gaps to gold standard promises
   - Calculate FPR = false_positives / total_gaps
   - Identify gaps that are not truly missing

5. **Parameter Accuracy** (if gold standard available):
   - Value accuracy: % exact matches, % within tolerance
   - Unit accuracy: % correct units
   - Description accuracy: semantic similarity

**Output:**
- `evaluation_report.json`: All metrics and scores

**Gold Standard Format:**
```json
{
  "paper_id": "covid_19",
  "gold_promises": { ... },
  "gold_entities": {
    "compartments": [ ... ],
    "flows": [ ... ],
    "parameters": [ ... ]
  },
  "gold_gaps": [ ... ]
}
```

---

## 5. LLM Integration Points

### Where LLM is Used

1. **Paper Promises Extraction** (Step 2):
   - Fallback when patterns insufficient
   - Use `metamodel_epidemiology.json` as schema
   - Model: `gpt-4o-mini` (configurable)

2. **Entity Normalization** (Step 3):
   - Normalize raw text to canonical names
   - Resolve synonyms and aliases
   - Link related entities

3. **Entity Extraction** (Step 3):
   - Fallback when patterns fail
   - Extract from unstructured text
   - Disambiguate ambiguous mentions

4. **Gap Filler Suggestions** (Step 7):
   - Generate domain knowledge suggestions
   - Propose parameter values/ranges
   - Suggest model structures

### LLM Configuration

**API Key:**
- Environment variable: `OPENAI_API_KEY`
- Fallback: Config file (not recommended for security)

**Model Selection:**
- Default: `gpt-4o-mini` (cost-effective)
- Configurable: `gpt-4`, `gpt-3.5-turbo`
- Temperature: 0.3 (lower for consistency)

**Prompt Engineering:**
- Include metamodel schema in prompts
- Use few-shot examples
- Request JSON-only responses
- Validate outputs against schema

**Error Handling:**
- Graceful degradation if API unavailable
- Fallback to pattern-based extraction
- Log all LLM calls for debugging
- Rate limiting and retry logic

---

## 6. Gap Filler System

### Architecture

The gap filler uses a **three-tier strategy**:

1. **Tier 1: Paper Re-examination**
   - Re-search paper for weak signals
   - Use semantic search (embeddings) if available
   - Look for related terms, synonyms
   - **Output**: Paper-backed suggestions (lower confidence)

2. **Tier 2: Prior Model Knowledge**
   - Load Phase 1 model analysis JSONs
   - Find similar models (disease, structure, parameters)
   - Extract how prior models handle gaps
   - **Output**: Suggestions from prior models

3. **Tier 3: Domain Knowledge**
   - Use metamodel compartment types
   - Use common epidemiological patterns
   - Use LLM for domain knowledge
   - **Output**: Domain knowledge suggestions

### Implementation

**Module Structure:**
```python
class GapFiller:
    def __init__(self, paper_text, prior_models, metamodel):
        self.paper_text = paper_text
        self.prior_models = prior_models  # List of Phase 1 JSONs
        self.metamodel = metamodel
    
    def fill_gap(self, gap):
        suggestions = []
        
        # Tier 1: Paper re-examination
        paper_suggestions = self._search_paper(gap)
        suggestions.extend(paper_suggestions)
        
        # Tier 2: Prior models
        prior_suggestions = self._search_prior_models(gap)
        suggestions.extend(prior_suggestions)
        
        # Tier 3: Domain knowledge
        domain_suggestions = self._use_domain_knowledge(gap)
        suggestions.extend(domain_suggestions)
        
        return suggestions
```

**Prior Model Matching:**
- Match by disease type
- Match by model structure (SEIR, SIR, etc.)
- Match by parameter names
- Use similarity scores

**Domain Knowledge Sources:**
- Metamodel compartment types
- Common flow patterns
- Standard parameter ranges (from literature)
- LLM-generated suggestions

---

## 7. Scoring and Evaluation

### Metrics

1. **Extraction Precision/Recall**:
   ```
   Precision = TP / (TP + FP)
   Recall = TP / (TP + FN)
   ```
   - TP: Correctly extracted entities
   - FP: Incorrectly extracted entities
   - FN: Missed entities (in gold standard but not extracted)

2. **Traceability Coverage**:
   ```
   Coverage = (Items with evidence) / (Total items)
   ```

3. **Faithfulness**:
   ```
   Faithfulness = (Paper-backed items) / (Total items)
   ```

4. **Gap False-Positive Rate**:
   ```
   FPR = (False positive gaps) / (Total gaps flagged)
   ```

5. **Parameter Accuracy**:
   - Value accuracy: Exact match or within tolerance
   - Unit accuracy: Correct unit
   - Description similarity: Semantic similarity score

### Evaluation Workflow

1. **Manual Gold Standard Creation**:
   - Annotate 1-2 papers manually
   - Create gold standard JSON files
   - Include: promises, entities, gaps

2. **Automated Evaluation**:
   - Run Phase 2 on papers with gold standards
   - Compare outputs to gold standards
   - Calculate all metrics

3. **Reporting**:
   - Generate evaluation report
   - Identify common failure modes
   - Suggest improvements

---

## 8. Technical Implementation

### Module Structure

```
phase2/
├── __init__.py
├── pdf_pipeline.py          # Step 1: PDF ingestion
├── paper_promise_extractor.py  # Step 2: Promise extraction
├── entity_extractor.py      # Step 3: Entity extraction
├── model_synthesizer.py     # Step 4: Model synthesis
├── traceability.py          # Step 5: Traceability mapping
├── gap_analyzer.py          # Step 6: Gap analysis (extends Phase 1)
├── gap_filler.py            # Step 7: Gap filling
├── quality_checks.py        # Step 8: Quality checks
├── evaluator.py             # Step 9: Evaluation
├── run_phase2.py            # Main orchestrator
└── utils/
    ├── llm_client.py        # LLM API wrapper
    ├── pattern_matcher.py   # Pattern-based extraction
    ├── normalizer.py        # Name normalization
    └── validator.py         # Schema validation
```

### Key Classes

**PDFPipeline:**
```python
class PDFPipeline:
    def extract_text(self, pdf_path) -> Dict
    def clean_text(self, text) -> str
    def detect_sections(self, text) -> Dict
    def extract_tables(self, pdf_path) -> List[Dict]
```

**EntityExtractor:**
```python
class EntityExtractor:
    def extract_compartments(self, paper_text) -> List[Entity]
    def extract_flows(self, paper_text, compartments) -> List[Entity]
    def extract_parameters(self, paper_text, tables) -> List[Entity]
    def extract_stratifications(self, paper_text) -> List[Entity]
    def extract_interventions(self, paper_text) -> List[Entity]
```

**ModelSynthesizer:**
```python
class ModelSynthesizer:
    def __init__(self, metamodel_path):
        self.metamodel = self._load_metamodel(metamodel_path)
    
    def synthesize(self, entities) -> str  # Returns XML
    def validate(self, compmodel_xml) -> bool
```

**GapFiller:**
```python
class GapFiller:
    def fill_gaps(self, gaps, paper_text, prior_models) -> List[Suggestion]
    def _search_paper(self, gap) -> List[Suggestion]
    def _search_prior_models(self, gap) -> List[Suggestion]
    def _use_domain_knowledge(self, gap) -> List[Suggestion]
```

---

## 9. File Structure

### Input Directory Structure

```
phase1/papers/
├── epimde/
│   ├── covid.pdf
│   ├── covid.compmodel
│   ├── hiv.pdf
│   ├── hiv.compmodel
│   ├── malaria.pdf
│   └── malaria.compmodel
└── new_papers/
    ├── paper1.pdf
    ├── paper2.pdf
    └── ...
```

### Output Directory Structure

```
phase2/reports/
├── covid_19/
│   ├── paper_text.json
│   ├── paper_sections.json
│   ├── paper_promises.json
│   ├── extracted_entities.json
│   ├── model_draft.compmodel
│   ├── traceability.json
│   ├── phase2_gap_report.json
│   ├── gap_fill_suggestions.json
│   ├── model_analysis.json
│   ├── uncertainty_analysis.json
│   └── sensitivity_analysis.json
├── hiv/
│   └── ...
├── malaria/
│   └── ...
└── evaluation/
    ├── evaluation_report.json
    └── metrics_summary.json
```

---

## 10. Dependencies

### Required Dependencies

```python
# PDF Processing
pdfplumber>=0.10.0  # Preferred PDF extraction
PyPDF2>=3.0.0       # Fallback PDF extraction

# LLM Integration
openai>=1.0.0       # OpenAI API client

# XML/JSON Processing
lxml>=4.9.0
xmltodict>=0.13.0
jsonschema>=4.17.0

# Data Processing
pandas>=2.0.0
numpy>=1.24.0

# Phase 1 Reuse
# (Import from phase1/analysis/)
```

### Optional Dependencies

```python
# Advanced PDF Processing
pymupdf>=1.23.0     # Alternative PDF library

# Semantic Search (for gap filler)
sentence-transformers>=2.2.0  # For embeddings

# Evaluation
scikit-learn>=1.3.0  # For similarity metrics
```

### Environment Variables

```bash
export OPENAI_API_KEY="your-api-key-here"
```

---

## 11. Testing Strategy

### Unit Tests

1. **PDF Pipeline Tests**:
   - Test text extraction from sample PDFs
   - Test section detection
   - Test table extraction

2. **Entity Extractor Tests**:
   - Test pattern matching
   - Test normalization
   - Test evidence capture

3. **Model Synthesizer Tests**:
   - Test XML generation
   - Test schema validation
   - Test reference consistency

4. **Gap Filler Tests**:
   - Test paper re-search
   - Test prior model matching
   - Test domain knowledge suggestions

### Integration Tests

1. **End-to-End Pipeline**:
   - Run full pipeline on test papers
   - Verify all outputs generated
   - Check output validity

2. **Phase 1 Integration**:
   - Test Phase 1 analyzer reuse
   - Verify JSON outputs

### Evaluation Tests

1. **Gold Standard Comparison**:
   - Run on papers with gold standards
   - Calculate metrics
   - Verify metric calculations

---

## 12. Success Criteria

### Phase 2 is Complete When:

1. ✅ **Pipeline Runs End-to-End**:
   - Processes PDF → generates all outputs
   - No critical errors in pipeline

2. ✅ **Extraction Quality**:
   - Precision ≥ 0.7 (70% of extracted items correct)
   - Recall ≥ 0.6 (60% of gold standard items extracted)
   - Traceability coverage ≥ 0.8 (80% of items have evidence)

3. ✅ **Faithfulness**:
   - ≥ 0.9 (90% of extracted items are paper-backed)
   - Gap false-positive rate ≤ 0.2 (≤20% false positives)

4. ✅ **Model Validity**:
   - Generated `.compmodel` files are valid XML
   - Pass Phase 1 model analyzer validation
   - Reference consistency (no broken references)

5. ✅ **Gap Filler**:
   - Generates suggestions for all critical gaps
   - Clearly distinguishes paper-backed vs. suggested
   - Provides rationale for each suggestion

6. ✅ **Documentation**:
   - All modules documented
   - Usage examples provided
   - Evaluation results documented

---

## 13. Implementation Phases

### Phase 2.1: Core Pipeline (Weeks 1-2)
- PDF ingestion and cleaning
- Paper promises extraction
- Basic entity extraction (pattern-based)
- Model synthesis (basic)

### Phase 2.2: LLM Integration (Week 3)
- LLM-based extraction
- Entity normalization
- Improved promises extraction

### Phase 2.3: Gap Analysis and Filling (Week 4)
- Gap analysis (paper-driven)
- Gap filler implementation
- Prior model integration

### Phase 2.4: Quality and Evaluation (Week 5)
- Phase 1 analyzer integration
- Evaluation metrics
- Gold standard creation

### Phase 2.5: Testing and Refinement (Week 6)
- Comprehensive testing
- Bug fixes
- Performance optimization
- Documentation

---

## 14. Usage Example

### Command Line

```bash
# Process single paper
python phase2/run_phase2.py \
    --paper phase1/papers/epimde/covid.pdf \
    --output phase2/reports/covid_19 \
    --metamodel phase1/metamodel_epidemiology.json \
    --prior-models phase1/reports/model_analysis

# Process all papers in directory
python phase2/run_phase2.py \
    --papers-dir phase1/papers/epimde \
    --output phase2/reports \
    --metamodel phase1/metamodel_epidemiology.json

# With evaluation (gold standard)
python phase2/run_phase2.py \
    --paper phase1/papers/epimde/covid.pdf \
    --output phase2/reports/covid_19 \
    --gold-standard phase2/gold_standards/covid_19_gold.json \
    --evaluate
```

### Python API

```python
from phase2.run_phase2 import Phase2Pipeline

pipeline = Phase2Pipeline(
    metamodel_path="phase1/metamodel_epidemiology.json",
    prior_models_dir="phase1/reports/model_analysis"
)

results = pipeline.process_paper(
    pdf_path="phase1/papers/epimde/covid.pdf",
    output_dir="phase2/reports/covid_19"
)

print(f"Extracted {len(results['entities']['compartments'])} compartments")
print(f"Found {len(results['gaps'])} gaps")
print(f"Generated {len(results['gap_suggestions'])} suggestions")
```

---

## 15. Future Enhancements

1. **Multi-Paper Comparison**:
   - Compare models across papers
   - Identify common patterns
   - Build model library

2. **Interactive Refinement**:
   - GUI for reviewing extractions
   - Manual correction interface
   - Feedback loop for improvement

3. **Advanced LLM Features**:
   - Fine-tuned models for epidemiology
   - Few-shot learning
   - Chain-of-thought reasoning

4. **Simulation Integration**:
   - Auto-generate simulation code
   - Parameter estimation
   - Model calibration

5. **Literature Review Automation**:
   - Auto-search for parameter values
   - Extract from multiple papers
   - Build parameter database

---

## 16. Notes and Considerations

### Key Principles

1. **Faithfulness First**: Only extract what papers promise/describe
2. **Evidence-Based**: Every extraction must have traceable evidence
3. **Transparency**: Clearly mark paper-backed vs. suggested
4. **Reproducibility**: All outputs are JSON, all steps are logged
5. **Reusability**: Leverage Phase 1 analyzers, don't duplicate

### Challenges

1. **PDF Quality**: Poor OCR, scanned PDFs, complex layouts
2. **Ambiguity**: Papers may be ambiguous about model structure
3. **Incompleteness**: Papers may not fully describe models
4. **LLM Costs**: API costs for large-scale processing
5. **Evaluation**: Need manual gold standards for evaluation

### Mitigation Strategies

1. **Multiple PDF Libraries**: Fallback options for different PDF types
2. **Confidence Scores**: Mark uncertain extractions
3. **Gap Filler**: Fill missing information with suggestions
4. **Caching**: Cache LLM responses for reproducibility
5. **Crowdsourcing**: Use multiple annotators for gold standards

---

## 17. References

- Phase 1 Analysis Framework: `phase1/README.md`
- Metamodel: `phase1/metamodel_epidemiology.json`
- Phase 1 Results: `phase1/PHASE1_RESULTS_SUMMARY.md`
- Paper Promise Extractor: `phase1/analysis/paper_promise_extractor.py`
- Gap Analyzer: `phase1/analysis/gap_analyzer.py`

---

**End of Phase 2 Plan**
