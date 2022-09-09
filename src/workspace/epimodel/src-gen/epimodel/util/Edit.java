package epimodel.util;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import epimodel.impl.EpimodelPackageImpl;

public class Edit {
	
	public static void addText(Shell shell, List<Control> controls, String text) {
		Text t = new Text(shell, SWT.SINGLE);
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
