package epimodel.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.EpimodelFactory;
import epimodel.EpimodelPackage;
import epimodel.Flow;
import epimodel.FlowWrapper;
import epimodel.impl.EpimodelPackageImpl;

public class Edit {
	
	public static void addText(Shell shell, List<Control> controls, String text) {
		Text t = new Text(shell, SWT.READ_ONLY);
		t.setText(text);
		t.setLayoutData(new GridData(300, 50));
		controls.add(t);
	}
	
	public static void addBtn(Shell shell, List<Control> controls, String text, Runnable onClick) {
		Button b = new Button(shell, SWT.NONE);
		b.setText(text);
		b.setLayoutData(new GridData(300, 50));
		b.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onClick.run();
			}
		});
		controls.add(b);
	}
	
	public static void addCompartmentWindow(EObject dom, Shell shell, List<Control> controls, Consumer<CompartmentWrapper> callback) {
		controls.forEach(Control::dispose);
		controls.clear();
        shell.setLayout(new GridLayout(1, false));
        List<EClass> types = getNonAbstractEClassesOfType(EpimodelPackage.Literals.COMPARTMENT);
        for (EClass ec : types) {
        	addBtn(shell, controls, ec.getName(), () -> {
    			controls.forEach(Control::dispose);
    			controls.clear();
                CompartmentWrapper w = EpimodelFactory.eINSTANCE.createCompartmentWrapper();
                Compartment compartment = (Compartment) EcoreUtil.create(ec);
                w.setCompartment(compartment);
    			shell.addListener(SWT.Close, e -> epimodel.util.Edit.transact(dom, () -> callback.accept(w)));
	        	compartment.edit(dom, shell, controls);
    			shell.pack(true);
        	});
        }
		shell.pack(true);
	}
	
	public static void addFlowWindow(Shell shell, List<Control> controls, Consumer<FlowWrapper> callback) {
		controls.forEach(Control::dispose);
		controls.clear();
        shell.setLayout(new GridLayout(1, false));
        List<EClass> types = getNonAbstractEClassesOfType(EpimodelPackage.Literals.FLOW);
        for (EClass ec : types) {
        	addBtn(shell, controls, ec.getName(), () -> {
                FlowWrapper w = EpimodelFactory.eINSTANCE.createFlowWrapper();
                Flow flow = (Flow) EcoreUtil.create(ec);
                w.setFlow(flow);
                callback.accept(w);
                shell.close();
        	});
        }
		shell.pack(true);
	}
	
	public static void transact(EObject target, Runnable f) {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(target);
	    domain.getCommandStack().execute(new RecordingCommand(domain) {
	        @Override
	        protected void doExecute() {
				f.run();
	        }}
	    );
	}
	
	public static List<EClass> getNonAbstractEClassesOfType(EClass supertype) {
		return EpimodelPackageImpl
				.collectEClasses(getCurrentPlugins())
				.stream()
				.filter(ec -> !ec.isAbstract() && !ec.isInterface() && supertype.isSuperTypeOf(ec))
				.collect(Collectors.toList());
	}
	
	static List<String> getCurrentPlugins() {
		IProject project = getCurrentProject();
		IFile extensions = project.getFile("extensions.txt");
		try {
			StringBuilder textBuilder = new StringBuilder();
		    Reader reader = new BufferedReader(new InputStreamReader(
	    		extensions.getContents(),
	    		StandardCharsets.UTF_8)
	    	);
	        int c = 0;
	        while ((c = reader.read()) != -1)
	            textBuilder.append((char) c);
	        reader.close();
			String filecontent = textBuilder.toString();
			String[] lines = filecontent.split("\n");
			return Arrays.asList(lines)
					.stream()
					.map(String::trim)
					.collect(Collectors.toList());
		} catch (IOException | CoreException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	// https://stackoverflow.com/questions/44149420/universal-way-to-get-the-current-project-in-eclipse-plugin
	static IProject getCurrentProject() {
	    IWorkbenchWindow window = PlatformUI.getWorkbench()
	            .getActiveWorkbenchWindow();
		
		IWorkbenchPage activePage = window.getActivePage();

		IEditorPart activeEditor = activePage.getActiveEditor();
		IProject project = null;
		
		if (activeEditor != null) {
		   IEditorInput input = activeEditor.getEditorInput();

		   project = input.getAdapter(IProject.class);
		   if (project == null) {
		      IResource resource = input.getAdapter(IResource.class);
		      if (resource != null) {
		         project = resource.getProject();
		      }
		   }
		}
		return project;
	}
}
