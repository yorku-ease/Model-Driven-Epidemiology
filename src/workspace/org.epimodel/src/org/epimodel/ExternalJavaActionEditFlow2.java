package org.epimodel;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import epimodel.FlowWrapper;

public class ExternalJavaActionEditFlow2 implements IExternalJavaAction {

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

        ((FlowWrapper) clicked).getFlow().edit(shell, controls);
        
        shell.pack(true);
        shell.open();
	}
}
