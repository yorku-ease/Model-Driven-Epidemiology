EpiMDE + LLM Pipeline Integration Report
1. Executive Summary
This report outlines integrating the AI-Assisted LLM Pipeline into the EpiMDE Eclipse/EMF/Sirius tool. The goal is to create a unified environment where users can:
1. Upload PDF papers → auto-generate .compmodel files via LLM (Phases 2+3)
2. Validate generated models by comparing equations against paper equations
3. Detect and auto-fix model errors using LLM
4. Interactively query LLM to modify specific compartments/flows from the diagram
5. Maintain per-disease LLM conversation memory
---
2. Current State Analysis
2.1 Compartmental (Eclipse EMF/Sirius)
Component	Path
Metamodel	Compartmental/CompartmentalModel/compartmental.ecore
Sirius Design	Compartmental/CompartmentalModel.design/description/CompartmentalModel.odesign
Java Services	Compartmental/CompartmentalModel.design/src/CompartmentalModel/design/Services.java
Equation Generator	Compartmental/CompartmentalModel/src/compartmental/equationgenerator/CompartmentalEquationGenerator.java
Key Extension Points:
- Services.java — Java methods callable from .odesign via service:methodName(args)
- .odesign — can add new diagram tools (buttons, context menu actions, popup bars)
- Eclipse Commands — can invoke external processes (Python LLM pipeline)
- EMF Transaction API — programmatic model modification
2.2 AI-Assisted LLM Pipeline
Phase	Entry Point	Purpose
Phase 2	run_phase2.py --paper <pdf>	PDF → LLM → model_draft.compmodel
Phase 2.5	phase 2.5/	Canonical feature profiles from gold models
Phase 3	phase 3/src/rag/ + inference/	RAG-based gap filling
Phase 4	phase 4/	Uncertainty quantification, Monte Carlo
---
3. Integration Architecture
3.1 Communication Strategy
Since Eclipse runs on Java and the LLM pipeline is Python:
Approach	Use Case
ProcessBuilder (spawn Python)	Batch operations (PDF→model)
HTTP REST API (FastAPI wrapper)	Interactive diagram queries
Java-Python bridge	Direct calls
3.2 Architecture Diagram
┌─────────────────────────────────────────────────┐
│              Eclipse IDE (EpiMDE)               │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  │
│  │ Sirius   │  │ EMF Tree │  │ LLM Menu │  │
│  │ Diagram  │  │ Editor   │  │/Toolbar  │  │
│  └────┬─────┘  └────┬─────┘  └────┬─────┘  │
│        └──────────────┴──────────────┘        │
│                     │                          │
│           ┌─────────▼─────────┐                │
│           │  Services.java    │ (extended)      │
│           │  LLMService.java │ (new)           │
│           └─────────┬─────────┘                │
└─────────────────────┼──────────────────────────┘
                      │
          ProcessBuilder │ HTTP
                      ▼
┌─────────────────────────────────────────────────┐
│        Python LLM Pipeline (subprocess)          │
│  Phase 2 (PDF→compmodel) │ Phase 3 (RAG)     │
│  FastAPI wrapper for interactive queries        │
└─────────────────────────────────────────────────┘
---
4. Feature Implementation Plans
Feature 1: PDF Upload → Generate .compmodel
User Flow: Menu "EpiMDE → Import Paper → Generate Model" → select PDF → choose LLM provider → progress bar → result opens in Sirius diagram.
Implementation Steps:
Step 1: Create Eclipse Command + Handler
// CompartmentalModel.editor/src/compartmental/editor/handlers/GenerateModelFromPaperHandler.java
public class GenerateModelFromPaperHandler extends AbstractHandler {
    public Object execute(ExecutionEvent event) {
        // 1. File dialog → PDF
        // 2. Provider selection dialog (Gemini/OpenAI/Claude)
        // 3. Job.runWithProgress() → ProcessBuilder calls run_phase2.py
        // 4. Refresh workspace, open .compmodel in Sirius
    }
}
Step 2: Register in CompartmentalModel.editor/plugin.xml
<extension point="org.eclipse.ui.commands">
    <command id="compartmental.commands.generateFromPaper" name="Generate Model from Paper"/>
</extension>
<extension point="org.eclipse.ui.menus">
    <menuContribution locationURI="menu:org.eclipse.ui.main.menu?after=additions">
        <menu id="epimdeMenu" label="EpiMDE">
            <command commandId="compartmental.commands.generateFromPaper"/>
        </menu>
    </menuContribution>
</extension>
Step 3: Python Process Wrapper
// CompartmentalModel.design/src/CompartmentalModel/design/LLMPipelineClient.java
public class LLMPipelineClient {
    public Path generateModel(Path pdfPath, String provider) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("python3", 
            phase2Root + "/run_phase2.py",
            "--paper", pdfPath.toString(),
            "--llm-provider", provider);
        pb.directory(phase2Root.toFile());
        Process p = pb.start();
        // Stream output to Eclipse Console
        return findGeneratedModel(pdfPath);
    }
}
Step 4: Combine Phase 2+3 in a single script
Create AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/run_full_pipeline.py that runs Phase 2 → Phase 3 sequentially and outputs the final model_filled.compmodel.
---
Feature 2: Equation Generation + Comparison with Paper
User Flow: Open .compmodel → "Generate & Compare Equations" → side-by-side view of generated vs paper equations → highlight mismatches → "Fix with LLM" button.
Implementation:
1. Extend CompartmentalEquationGenerator.java to output structured JSON (not just text):
public JSONObject generateEquationsJSON(String compmodelPath) {
    // Parse model, emit { "compartments": [...], "equations": [...] }
}
2. Create equation_extractor.py (Python side):
def extract_equations_from_paper(pdf_path: str) -> list[dict]:
    """LLM extracts ODE equations from paper PDF"""
    prompt = "Extract all compartmental ODE equations from this paper with compartment names and formulas."
    return llm_client.complete(prompt, pdf=pdf_path)
3. Build EquationComparisonView.java (Eclipse ViewPart):
   - Split pane: left = generated equations, right = paper equations
   - Fuzzy-matched compartment names
   - "Fix with LLM" button per mismatch
---
Feature 3: Model Validation + Auto-Fix with LLM
User Flow: "Validate Model" → Problems View shows errors → click "Fix with LLM" → LLM proposes fix → preview diff → approve/reject.
Implementation:
1. Create ModelValidator.java:
public IStatus validate(CompartmentalModel model) {
    MultiStatus status = new MultiStatus(...);
    // Check: flows have valid targets, population conservation,
    // non-negative rates, valid stratum references
    return status;
}
2. Integrate with Eclipse Problems View using IMarker:
IMarker marker = file.createMarker(IMarker.PROBLEM);
marker.setAttribute(IMarker.MESSAGE, "Flow has no target");
marker.setAttribute("llm_fixable", true);
marker.setAttribute("model_path", modelPath);
3. Create LLMQuickFixProcessor.java:
public void fix(IMarker[] markers) {
    for (IMarker m : markers) {
        String fix = callLLMForFix(m.getAttribute(IMarker.MESSAGE));
        if (showDiffDialog(fix)) applyFix(fix);
    }
}
4. Python side: model_fixer.py:
def fix_model_error(compmodel_path: str, error_msg: str) -> str:
    prompt = f"Fix this error in the compartmental model: {error_msg}\nModel: {compmodel_path}"
    return llm_client.complete(prompt)
---
Feature 4: Interactive Diagram with LLM Query
User Flow: Right-click compartment/flow in Sirius diagram → "Query LLM..." → type question → LLM receives element data + RAG context → proposes modification → user approves.
Implementation:
1. Extend .odesign with context menu action:
<ownedTools xsi:type="description_1:DiagramTool">
    <name>Query LLM</name>
    <mapping href="...@nodeMappings[name='CompartmentNode']"/>
    <body>
        <changeContext variable="element" expression="var:element">
            <setVariable variable="query" expression="service:promptUser(element)"/>
            <setVariable variable="response" expression="service:queryLLM(element, query)"/>
        </changeContext>
    </body>
</ownedTools>
2. Extend Services.java:
public String promptUser(EObject element) {
    InputDialog d = new InputDialog(shell, "Query LLM about " + element, "Your question:", "", null);
    d.open(); return d.getValue();
}
public String queryLLM(EObject element, String query) {
    String elementJSON = elementToJSON(element);
    String memory = MemoryManager.getMemoryFor(element).toContext();
    return LLMService.query(elementJSON, query, memory);
}
3. FastAPI wrapper (interactive_service.py):
@app.post("/query")
async def query_llm(req: QueryRequest):
    context = paper_db.search(req.query) if req.paper_path else ""
    prompt = f"Element: {req.element_data}\nQuery: {req.query}\nContext: {context}\nMemory: {req.memory}"
    return {"response": llm.complete(prompt)}
---
Feature 5: LLM Memory System (Per-Disease Conversation History)
User Flow: Each .compmodel gets associated .memory.json → all LLM interactions logged → memory included in context → user can view/edit memory from Eclipse.
Memory File Structure:
{
  "disease": "COVID-19",
  "model_file": "covid.compmodel",
  "conversations": [
    {
      "timestamp": "2026-05-04T10:30:00",
      "type": "validation_fix",
      "element": "Infectious (severe)",
      "user_query": "Rate seems too low",
      "llm_response": "Updated rate from 0.15 to 0.26",
      "applied": true
    }
  ],
  "learnings": ["COVID severe cases: 26% of mild progress to severe"]
}
Implementation:
1. MemoryManager.java — getOrCreate memory file per model
2. MemoryFile.java — load/save JSON, add interactions
3. Include in all LLM calls: LLMService.query() appends memory.toContext()
4. MemoryView.java (Eclipse ViewPart) — tree viewer of conversations
---
5. Additional Recommended Features
5.1 Model Diff Viewer
Compare two .compmodel files (draft vs filled). Side-by-side comparison with highlighted differences. Merge selected changes.
5.2 Batch Paper Processing
Select multiple PDFs → batch generate models. Progress view with per-paper status. Aggregate report.
5.3 Parameter Sensitivity Analysis (Phase 4 Integration)
From Eclipse, run Monte Carlo simulation on current model. View sensitivity indices. Parameter sweep UI.
5.4 Export to Other Formats
Export .compmodel to Python (simulation.py), R (deSolve), Julia (DifferentialEquations.jl), or LaTeX (for publications).
5.5 Model Templates
Save common structures (SIR, SEIR, vector-borne) as templates. "New Model from Template" wizard.
5.6 Real-Time RAG Enhancement
When LLM queries are made, automatically add successful fixes back to the RAG database, improving future extractions for the same disease type.
---
6. Implementation Roadmap
Phase	Weeks	Deliverables
A: Foundation	1-2	FastAPI wrapper, LLMService.java, PDF→Model command, basic integration test
B: Validation + Fix	3-4	ModelValidator.java, LLMQuickFixProcessor.java, model_fixer.py
C: Interactive Queries	5-6	.odesign context menu, Services.java extensions, query dialog
D: Memory System	7-8	MemoryManager.java, MemoryFile.java, MemoryView.java
E: Equation Comparison	9-10	EquationComparator.java, equation_extractor.py, comparison view
F: Polish	11-12	Model diff viewer, batch processing UI, documentation
---
7. File Structure (New/Modified)
Compartmental/
├── CompartmentalModel/
│   ├── src/compartmental/
│   │   ├── validation/ModelValidator.java          (NEW)
│   │   ├── memory/MemoryManager.java              (NEW)
│   │   └── equationgenerator/                    (EXTEND)
│   └── disease_memories/                          (NEW)
│
├── CompartmentalModel.design/
│   ├── src/CompartmentalModel/design/
│   │   ├── Services.java                          (EXTEND)
│   │   └── LLMService.java                       (NEW)
│   └── description/CompartmentalModel.odesign    (EXTEND)
│
├── CompartmentalModel.editor/
│   └── src/compartmental/editor/
│       ├── handlers/GenerateModelFromPaperHandler.java  (NEW)
│       ├── quickfix/LLMQuickFixProcessor.java           (NEW)
│       └── views/EquationComparisonView.java            (NEW)
│
└── CompartmentalModel.editor/plugin.xml                (EXTEND)
AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/
├── interactive_service.py                               (NEW - FastAPI wrapper)
├── run_full_pipeline.py                                 (NEW - Phase 2+3 combo)
├── phase 3/src/
│   ├── inference/model_fixer.py                         (NEW)
│   └── extraction/equation_extractor.py                (NEW)
└── (existing phase 2, 3, 4 files remain unchanged)
---
## 8. Technical Considerations
- **Eclipse**: Modeling Tools 2024.x+, EMF 2.40+, Sirius 7.4.x+, Java 17+
- **Python**: 3.10+, virtualenv recommended, API keys in `phase 2/.api_key.txt`
- **Security**: API keys never logged; LLM responses validated before applying; user must approve all changes
- **Error Handling**: LLM calls timeout after 30s; Python stderr streamed to Eclipse Console; `.compmodel` backed up before LLM modifications
---
Report generated: May 2026 | For implementation details, see referenced source files in the repository.
