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
import epimodel.Flow;

public class ExternalJavaActionEditFlow implements IExternalJavaAction {

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		// we don't use this selection anyway
		return true;
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		Flow source = (Flow) parameters.get("source");
		Compartment target = (Compartment) parameters.get("target");
		if (source == null || target == null)
			return;
		
		Shell shell = new Shell(PlatformUI.getWorkbench().getDisplay(), SWT.TITLE | SWT.MIN | SWT.CLOSE);
        List<Control> controls = new ArrayList<>();

		source.edit(shell, controls, target);
        
        shell.pack(true);
        shell.open();
	}
}
