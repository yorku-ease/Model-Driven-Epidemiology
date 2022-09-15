package epimodel.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
		controls.forEach(c -> c.dispose());
		controls.clear();
        shell.setLayout(new GridLayout(1, false));
        for (EClass ec : getNonAbstractEClassesOfType(EpimodelPackage.Literals.COMPARTMENT)) {
        	addBtn(shell, controls, ec.getName(), () -> {
    			controls.forEach(c -> c.dispose());
    			controls.clear();
                CompartmentWrapper w = EpimodelFactory.eINSTANCE.createCompartmentWrapper();
                Compartment compartment = (Compartment) EcoreUtil.create(ec);
                w.setCompartment(compartment);
    			shell.addListener(SWT.Close, (e) -> {
                    callback.accept(w);
    			});
	        	compartment.create(dom, shell, controls);
    			shell.pack(true);
        	});
        }
		shell.pack(true);
	}
	
	public static void addFlowWindow(Shell shell, List<Control> controls, Consumer<FlowWrapper> callback) {
		controls.forEach(c -> c.dispose());
		controls.clear();
        shell.setLayout(new GridLayout(1, false));
        for (EClass ec : getNonAbstractEClassesOfType(EpimodelPackage.Literals.FLOW)) {
        	addBtn(shell, controls, ec.getName(), () -> {
                FlowWrapper w = EpimodelFactory.eINSTANCE.createFlowWrapper();
                Flow f = (Flow) EcoreUtil.create(ec);
                w.setFlow(f);
                callback.accept(w);
                {
                	final Shell shell2 = new Shell(PlatformUI.getWorkbench().getDisplay(), SWT.TITLE | SWT.MIN | SWT.CLOSE);
                    List<Control> controls2 = new ArrayList<>();
                    
                    Class<?> modelClass = f.getClass();
                    Method createMethod;
            		try {
            			createMethod = modelClass.getMethod("create", Shell.class, List.class);
            	        try {
            	        	createMethod.invoke(f, shell2, controls2);
            			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            				e.printStackTrace();
            			}
            		} catch (NoSuchMethodException | SecurityException e1) {
            			e1.printStackTrace();
            		}
                    
                    shell.pack(true);
                    shell.open();
                }
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
				.collectEClasses()
				.stream()
				.filter(ec -> !ec.isAbstract() && !ec.isInterface() && supertype.isSuperTypeOf(ec))
				.collect(Collectors.toList());
	}
}
