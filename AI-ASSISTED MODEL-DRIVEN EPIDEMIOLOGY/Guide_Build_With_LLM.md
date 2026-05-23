# Elaborated Guide: Building the "Build with LLM" Feature

This guide provides a deep-dive elaboration on exactly how we constructed the "Build with LLM" button (`GenerateFromPaperHandler.java`) within the EpiMDE Eclipse plugin. If you want to recreate this or build a similar AI-assisted Eclipse pipeline, these are the exact steps, UI components, data flows, and critical mistakes to avoid.

## 1. The User Interface (SWT/JFace)
When the user clicks "Build with LLM" from the Eclipse menu, the execution starts in the `execute(ExecutionEvent event)` method of `GenerateFromPaperHandler.java`.

Instead of building one massive custom UI window from scratch, we chain together native Eclipse JFace dialogs. This is much faster to build and feels native to the IDE.

**A. Add Files (PDF Selection)**
We use the standard OS-level file browser via Eclipse's `FileDialog`.
```java
FileDialog pdfDialog = new FileDialog(shell, SWT.OPEN);
pdfDialog.setText("Select Epidemiological Paper (PDF)");
pdfDialog.setFilterExtensions(new String[]{"*.pdf", "*.*"});
String pdfPathStr = pdfDialog.open(); // Pauses execution until user picks a file
```

**B. Choose LLM Provider**
We use an `InputDialog` equipped with a custom `IInputValidator`. This prevents the user from typing nonsense and crashing the Python script later.
```java
InputDialog providerDialog = new InputDialog(shell,
    "LLM Provider", "Enter LLM provider: gemini or openai", "gemini",
    newText -> {
        String t = newText.trim().toLowerCase();
        if ("gemini".equals(t) || "openai".equals(t)) return null; // null means valid
        return "Must be 'gemini' or 'openai'"; // Returns error message dynamically
    });
```

**C. Save API Key**
We ask for the API key using another `InputDialog`. Once obtained, we securely write it to a hidden folder (`.keys/<provider>.key`) in the root of the repository.
```java
Path keysDir = repoRoot.resolve(".keys");
keysDir.toFile().mkdirs();
Path apiKeyFile = keysDir.resolve(llmProvider + ".key");
Files.writeString(apiKeyFile, apiKey, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
```
**Why do this?** Passing an API key as a command-line argument to a Python script is a security risk (it shows up in the OS process list). Writing it to a file allows the Python backend to read it securely via environment variables or file-reads.

## 2. The Python Backend & System Prompt
Once the UI gathers the data, Java executes the Python pipeline via `java.lang.ProcessBuilder`.
```java
ProcessBuilder pb = new ProcessBuilder(
    pythonBin.toString(),
    pipelineScript.toString(),
    "--pdf", finalPdf.toString(),
    "--llm-provider", llmProvider,
    "--output", workspaceOutputDir.toString(),
    "--json" // Critical flag: Tells Python to ONLY output raw JSON
);
```

### The System Prompt & Rules
Inside the Python backend (`run_full_pipeline.py` / `EntityExtractor.py`), the system prompt is strictly constrained using the MDE (Model-Driven Engineering) paradigm:
1. **Metamodel Injection:** The script reads `metamodel_epidemiology.json` and injects the exact schema into the prompt. The LLM is instructed: "You must output valid JSON conforming exactly to this structure."
2. **Entity Extraction (Phase 2):** The PDF is parsed via PyMuPDF. Because papers are huge, the prompt splits the context: it sends up to 80,000 characters of text to the LLM and asks it to extract compartments, flows, and parameters.
3. **Synthesis:** The Python script converts the JSON arrays returned by the LLM directly into `.compmodel` XML format.

## 3. Retrieving and Updating the Eclipse Workspace
The Python script prints the final model XML wrapped inside a JSON object to Standard Output (`stdout`). Java captures this output stream line-by-line.
```java
// Java parses the last line of Python's output
String jsonStr = output.toString().trim();
// Extract the XML string and unescape newlines/quotes
String xmlContent = lastLine.substring(firstQuote + 1, lastQuote);
xmlContent = xmlContent.replace("\\n", "\n").replace("\\\"", "\"").replace("\\\\", "\\");
```

**Injecting into Eclipse EMF**
We take that raw XML string and load it into Eclipse's EMF (Eclipse Modeling Framework) engine.
```java
IFile newFile = project.getFile(finalName + ".compmodel");
URI uri = URI.createPlatformResourceURI(newFile.getFullPath().toString(), true);
Resource res = rset.createResource(uri);
// Load XML bytes into EMF Resource and save to disk
```

## 4. Critical Errors to Avoid (Lessons Learned)
If you attempt to recreate this, you will run into these specific Eclipse pitfalls if you aren't careful:

**Mistake 1: Blocking the UI Thread (The "Freezing Eclipse" Bug)**
* **The Error:** If you run `ProcessBuilder.start().waitFor()` directly inside the Handler, the entire Eclipse IDE will completely freeze and turn white until the LLM finishes 2 minutes later.
* **The Fix:** You must wrap the Python execution inside an Eclipse `Job` object.
```java
Job job = new Job("Building Model with LLM") {
    protected IStatus run(IProgressMonitor monitor) {
        // Run Python ProcessBuilder here!
        return Status.OK_STATUS;
    }
};
job.schedule();
```

**Mistake 2: Updating the UI from a Background Thread (The "Invalid Thread Access" Bug)**
* **The Error:** Once your background `Job` finishes and you try to show a "Success" popup or open the file editor, Eclipse will crash with an `SWTException: Invalid thread access`.
* **The Fix:** You cannot touch the UI from a background `Job`. You must use `Display.asyncExec()`.
```java
Display.getDefault().asyncExec(() -> {
    MessageDialog.openInformation(shell, "Success", "Model built!");
});
```

**Mistake 3: The Workspace "Ghost File" Bug**
* **The Error:** You successfully save the `.compmodel` file to disk using Java's standard `FileWriter`, but it doesn't appear in the Eclipse Package Explorer.
* **The Fix:** Eclipse caches its file system. If you bypass Eclipse to write a file, you must force it to refresh, OR better yet, use Eclipse's internal `IFile` API to write the file.
```java
project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
```

**Mistake 4: Escaping JSON from Python to Java**
* **The Error:** Python's `json.dumps()` escapes XML quotes (`"`) and newlines (`\n`). When Java receives it via `stdout`, if you just dump it straight into an XML parser, EMF will crash claiming the XML is malformed.
* **The Fix:** You must manually unescape `\\n` to `\n` and `\\"` to `"` in Java before feeding it to the EMF `ResourceSet`.
