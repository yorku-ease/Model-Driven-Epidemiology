package org.epimodel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        
        Class<?> modelClass = clicked.getClass();
        Method editMethod;
		try {
			editMethod = modelClass.getMethod("edit", Shell.class, List.class);
	        try {
				editMethod.invoke(clicked, shell, controls);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
        
        shell.pack(true);
        shell.open();
	}
}
