package compartmentalmodel.editor.actions;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

public class GenerateFromPaperHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        Shell shell = HandlerUtil.getActiveShell(event);
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
        if (shell == null || window == null) return null;

        // Step 1: Select PDF file
        FileDialog pdfDialog = new FileDialog(shell, SWT.OPEN);
        pdfDialog.setText("Select Epidemiological Paper (PDF)");
        pdfDialog.setFilterExtensions(new String[]{"*.pdf", "*.*"});
        pdfDialog.setFilterNames(new String[]{"PDF Files (*.pdf)", "All Files (*.*)"});
        String pdfPathStr = pdfDialog.open();
        Path pdfFile;
        if (pdfPathStr != null) {
            pdfFile = Paths.get(pdfPathStr);
        } else {
            // Cancel defaults to EbolaSensitivity.pdf for convenience
            pdfFile = findRepoRoot().resolve("Compartmental/EbolaSensitivity.pdf");
        }

        // Step 2: Select LLM provider (gemini or openai only)
        InputDialog providerDialog = new InputDialog(shell,
                "LLM Provider",
                "Enter LLM provider: gemini or openai",
                "gemini",
                newText -> {
                    String t = newText.trim().toLowerCase();
                    if ("gemini".equals(t) || "openai".equals(t)) return null;
                    return "Must be 'gemini' or 'openai'";
                });
        if (providerDialog.open() != Window.OK) return null;
        String llmProvider = providerDialog.getValue().trim().toLowerCase();

        // Step 3: Enter API key for the selected provider
        InputDialog keyDialog = new InputDialog(shell,
                "API Key",
                "Enter API key for " + llmProvider + ":",
                "",
                newText -> {
                    if (newText == null || newText.trim().isEmpty()) {
                        return "API key cannot be empty";
                    }
                    return null;
                });
        if (keyDialog.open() != Window.OK) return null;
        String apiKey = keyDialog.getValue().trim();

        // Step 4: Resolve repo root and pipeline paths
        Path repoRoot = findRepoRoot();
        if (repoRoot == null) {
            MessageDialog.openError(shell, "Error", "Could not locate repository root.");
            return null;
        }
        Path pipelineRoot = repoRoot.resolve("AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY");
        if (!pdfFile.toFile().exists()) {
            MessageDialog.openError(shell, "Error", "PDF not found at " + pdfFile);
            return null;
        }

        // Step 5: Create or find the CompartmentalModel project
        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject("CompartmentalModel");
        try {
            if (project == null || !project.exists()) {
                project.create(null);
                project.open(null);
            } else if (!project.isOpen()) {
                project.open(null);
            }
        } catch (CoreException e) {
            MessageDialog.openError(shell, "Error",
                    "Failed to create project: " + e.getMessage());
            return null;
        }
        Path workspaceOutputDir = project.getLocation().toFile().toPath();

        // Step 6: Write API key to the key file that the Python pipeline reads
        Path apiKeyFile = pipelineRoot.resolve("phase 2/.api_key.txt");
        try {
            Files.writeString(apiKeyFile, llmProvider + ":" + apiKey + "\n",
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            MessageDialog.openError(shell, "Error",
                    "Failed to write API key: " + e.getMessage());
            return null;
        }

        // Step 7: Detect Python runtime
        Path pythonBin = PipelineRunner.detectPython(pipelineRoot);
        Path pipelineScript = pipelineRoot.resolve("run_full_pipeline.py");
        if (!pipelineScript.toFile().exists()) {
            MessageDialog.openError(shell, "Error",
                    "Pipeline script not found at " + pipelineScript);
            return null;
        }
        PipelineRunner runner = new PipelineRunner(pythonBin, pipelineScript, pipelineRoot);

        String modelName = "ebola_" + llmProvider + "_"
                + java.time.LocalDateTime.now().toString()
                    .replace(":", "-").replace("T", "_");

        // Step 8: Run pipeline in a background Job
        final Path finalPdf = pdfFile;
        final Path finalOutput = workspaceOutputDir;
        final String finalName = modelName;

        Job job = new Job("Generating model from paper") {
            @Override
            protected IStatus run(IProgressMonitor monitor) {
                System.out.println("[EpiMDE] Pipeline started — see log output below");
                System.out.println("[EpiMDE] Provider: " + llmProvider
                        + " | PDF: " + finalPdf.getFileName()
                        + " | Output: " + finalName + ".compmodel");
                System.out.println("--- begin pipeline log ---");
                monitor.beginTask("Running LLM pipeline (Phase 2 + Phase 3)...",
                        IProgressMonitor.UNKNOWN);
                try {
                    if (monitor.isCanceled()) return Status.CANCEL_STATUS;

                    Path result = runner.run(finalPdf, llmProvider,
                            finalOutput, finalName, monitor);

                    System.out.println("--- end pipeline log ---");
                    System.out.println("[EpiMDE] Model generated: " + result);

                    project.refreshLocal(IResource.DEPTH_INFINITE, monitor);

                    Display.getDefault().asyncExec(() -> {
                        IFile modelFile = project.getFile(finalName + ".compmodel");
                        if (modelFile.exists()) {
                            try {
                                IWorkbenchPage page = window.getActivePage();
                                if (page != null) {
                                    page.openEditor(new FileEditorInput(modelFile),
                                            "compartmentalmodel.presentation.CompartmentalmodelEditorID");
                                }
                            } catch (PartInitException e) {
                                // silent
                            }
                        }
                    });

                    monitor.done();
                    return Status.OK_STATUS;
                } catch (Exception e) {
                    Display.getDefault().asyncExec(() -> {
                        Shell activeShell = Display.getDefault().getActiveShell();
                        MessageDialog.openError(activeShell, "Pipeline Error",
                                "Model generation failed:\n" + e.getMessage());
                    });
                    monitor.done();
                    return new Status(IStatus.ERROR, "CompartmentalModel.editor",
                            "Pipeline failed: " + e.getMessage(), e);
                }
            }
        };
        job.setUser(true);
        job.schedule();

        return null;
    }

    private static Path findRepoRoot() {
        try {
            URL bundleUrl = Platform.getBundle("CompartmentalModel.editor").getEntry("/");
            URL fileUrl = FileLocator.toFileURL(bundleUrl);
            Path path = Paths.get(fileUrl.toURI());
            while (path != null && !path.resolve("Compartmental").toFile().exists()) {
                path = path.getParent();
            }
            return path;
        } catch (Exception e) {
            return null;
        }
    }
}
