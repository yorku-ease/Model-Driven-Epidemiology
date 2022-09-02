package org.epimodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import epimodel.Compartment;
import epimodel.impl.EpimodelPackageImpl;

public class ExternalJavaActionAddChildToCompartment implements IExternalJavaAction {
	
	static List<EClass> eclasses = null;
	
	public ExternalJavaActionAddChildToCompartment() {
		System.out.println("ExternalJavaAction1");
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		
		if (eclasses == null)
			collectEClasses();
		
		Compartment clicked = null;
		for (EObject e : selections)
			if (e instanceof Compartment) {
				clicked = (Compartment) e;
				break;
			}
		if (clicked == null)
			return;
		
		Display display = PlatformUI.getWorkbench().getDisplay();
		final Shell shell = new Shell(display, SWT.TITLE | SWT.MIN | SWT.CLOSE);
		setupOriginalSelectionWindow(shell, new ArrayList<>(), clicked);
        shell.open();
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		// we don't use this selection anyway
		return true;
	}
	
	void collectEClasses() {
		eclasses = new ArrayList<>();
	    List<EPackage> epimodelPackages = EpimodelPackageImpl.getEpimodelPackages();
	    
	    for (EPackage pkg : epimodelPackages) {
	    	System.out.println(pkg.getName());
	    	EList<EClassifier> eclassifiers = pkg.getEClassifiers();
	    	for (EClassifier classifier : eclassifiers) {
	    		if (!(classifier instanceof EClass)) {
		    		System.out.println("\tnon class " + classifier.getName() + "type = " + classifier.getClass());
		    		continue;
	    		}
    			EClass cl = (EClass) classifier;
    			eclasses.add(cl);
    			if (cl.isAbstract())
    				if (cl.isInterface())
    					System.out.println("\tinterface " + classifier.getName());
    				else
    					System.out.println("\tabstract class " + classifier.getName());
    			else
		    		System.out.println("\tclass " + classifier.getName());
    			
    			for (EAttribute a : cl.getEAllAttributes())
    				System.out.println("\t\tattribute " + a.getName());
    			for (EReference c : cl.getEAllContainments())
    				System.out.println("\t\tcontainement " + c.getName());
    			for (EReference r : cl.getEAllReferences())
    				System.out.println("\t\treference " + r.getName());
	    	}
	    }
	}
	
	void setupOriginalSelectionWindow(Shell shell, final List<Control> controls, Compartment clicked) {
		controls.forEach(c -> c.dispose());
		controls.clear();
		
		shell.setText("Add Children To " + clicked.getClass().getName() + " " + clicked.getLabel());
        shell.setLayout(new GridLayout(2, false));
        
        EClass cl = clicked.eClass();
        
        for (EReference containement : cl.getEAllContainments()) {
        	Text t = new Text(shell, SWT.SINGLE);
        	t.setText(containement.getName());
        	t.setLayoutData(new GridData(300, 50));
        	System.out.println("settext " + containement.getName());
        	Button b = new Button(shell, SWT.NONE);
        	b.setText("BTN " + containement.getName());
        	b.setLayoutData(new GridData(300, 50));
        	controls.add(t);
        	controls.add(b);
    		b.addSelectionListener(new SelectionAdapter() {
    			@Override
    			public void widgetSelected(SelectionEvent e) {
    				setupChildSelectionWindow(shell, controls, clicked, containement);
    			}
    		});
        }
        shell.pack(true);
	}
	
	void setupChildSelectionWindow(Shell shell, final List<Control> controls, Compartment clicked, EReference containement) {
		controls.forEach(c -> c.dispose());
		controls.clear();
		
		EClass refT = containement.getEReferenceType();
		boolean isWrapper = refT.getName().contains("Wrapper");
		EClass expected = isWrapper ?
				refT.getEAllContainments().get(0).getEReferenceType() :
				refT;
		List<EClass> possibleChildrenTs = getNonAbstractEClassesOfType(expected);
		
		shell.setText("Add " + containement.getName() + " To " + clicked.getClass().getName() + " " + clicked.getLabel());
        shell.setLayout(new GridLayout(2, false));
        
        for (EClass ec : possibleChildrenTs) {
        	Text t = new Text(shell, SWT.SINGLE);
        	t.setText(ec.getName());
        	t.setLayoutData(new GridData(300, 50));
        	System.out.println("settext " + ec.getName());
        	Button b = new Button(shell, SWT.NONE);
        	b.setText("BTN " + ec.getName());
        	b.setLayoutData(new GridData(300, 50));
        	controls.add(t);
        	controls.add(b);
    		b.addSelectionListener(new SelectionAdapter() {
    			@Override
    			public void widgetSelected(SelectionEvent e) {
    				TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(clicked);
    			    domain.getCommandStack().execute(new RecordingCommand(domain) {
    			        @Override
    			        protected void doExecute() {
    	    				EList<EObject> ctn = (EList<EObject>) clicked.eGet(containement);
    	    				System.out.println(ctn);
    						EObject first = EcoreUtil.create(refT);
    						ctn.add(first);
    	    				if (isWrapper) {
    	    					EObject second = EcoreUtil.create(expected);
    	    					EReference wrapperContainement = first.eClass().getEAllContainments().get(0);
	    						first.eSet(wrapperContainement, second);
    	    				}
    			        }
    			    });
    			}
    		});
        }
        shell.pack(true);
	}
	
	List<EClass> getNonAbstractEClassesOfType(EClass supertype) {
		return eclasses
				.stream()
				.filter(ec -> !ec.isAbstract() && !ec.isInterface() && supertype.isSuperTypeOf(ec))
				.collect(Collectors.toList());
	}
}
