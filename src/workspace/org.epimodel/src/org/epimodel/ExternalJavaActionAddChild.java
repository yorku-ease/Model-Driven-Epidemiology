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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class ExternalJavaActionAddChild implements IExternalJavaAction {

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
//		originalLogic(clicked);
		newLogic(clicked);
	}
	
	void newLogic(EObject clicked) {
		final Shell shell = new Shell(PlatformUI.getWorkbench().getDisplay(), SWT.TITLE | SWT.MIN | SWT.CLOSE);
		shell.setText("Add Children To " + clicked.getClass().getName() + " " + clicked);
        shell.setLayout(new GridLayout(2, false));
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
	
//	void addText(Shell shell, List<Control> controls, String text) {
//		Text t = new Text(shell, SWT.SINGLE);
//		t.setText(text);
//		t.setLayoutData(new GridData(300, 50));
//    	controls.add(t);
//	}
//	
//	void addBtn(Shell shell, List<Control> controls, String text, Runnable onClick) {
//    	Button b = new Button(shell, SWT.NONE);
//    	b.setText(text);
//    	b.setLayoutData(new GridData(300, 50));
//    	b.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				onClick.run();
//			}
//		});
//    	controls.add(b);
//	}
//	
//	void transact(EObject target, Runnable f) {
//		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(target);
//	    domain.getCommandStack().execute(new RecordingCommand(domain) {
//	        @Override
//	        protected void doExecute() {
//    			f.run();
//	        }});
//	}
//	
//	List<EClass> getNonAbstractEClassesOfType(EClass supertype) {
//		return EpimodelPackageImpl
//				.collectEClasses()
//				.stream()
//				.filter(ec -> !ec.isAbstract() && !ec.isInterface() && supertype.isSuperTypeOf(ec))
//				.collect(Collectors.toList());
//	}
}
