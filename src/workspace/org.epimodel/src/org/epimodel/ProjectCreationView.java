package org.epimodel;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ProjectCreationView extends ViewPart {

	public ProjectCreationView() {
		System.out.println("ProjectCreationView");
	}

	@Override
	public void createPartControl(Composite parent) {
		System.out.println("createPartControl");
		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		button.setText("BTN");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialog.openInformation(parent.getShell(), "TITLE", "CONTENT");
			}
		});
	}

	@Override
	public void setFocus() {
		System.out.println("View Set Focus");
	}
}
