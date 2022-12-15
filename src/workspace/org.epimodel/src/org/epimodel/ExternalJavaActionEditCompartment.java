package org.epimodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import epimodel.Compartment;

public class ExternalJavaActionEditCompartment implements IExternalJavaAction {

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		// we don't use this selection anyway
		return true;
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		EObject clicked = null;
		for (EObject e : selections) {
			clicked = e;
				break;
			}
		if (clicked == null)
			return;
		
		Shell shell = new Shell(PlatformUI.getWorkbench().getDisplay(), SWT.TITLE | SWT.MIN | SWT.CLOSE);
        List<Control> controls = new ArrayList<>();
        
        if (clicked instanceof Compartment)
        	((Compartment) clicked).edit(clicked, shell, controls);
//        else if (clicked instanceof CompartmentWrapper)
//        	((CompartmentWrapper) clicked).edit(clicked, shell, controls);
        
        shell.pack(true);
        shell.open();
	}
}
