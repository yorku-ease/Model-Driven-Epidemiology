package org.epimodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import epimodel.impl.EpimodelPackageImpl;

public class epimodelprojectcreationwizard extends Wizard implements INewWizard {
	
	protected PluginPage pluginPage;

	public epimodelprojectcreationwizard() {
	}
	
	String readFile(URL url) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
        String i;
		while ((i = read.readLine()) != null)
			sb.append(i);
		read.close();
        return sb.toString();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {}
	
	@Override
	public boolean canFinish() {
		return true;
	}

	@Override
	public boolean performFinish() {
		CustomProjectSupport.createProject(
				pluginPage.getProjectName(),
				ResourcesPlugin.getWorkspace().getRoot().getLocationURI(),
				pluginPage.pluginsEnabledManually);
		return true;
	}
	
	@Override
    public void addPages() {
		super.addPages();
		pluginPage = new PluginPage();
		addPage(pluginPage);
	}
	
	class PluginPage extends WizardPage {
		protected Composite container;
		protected List<Control> controls = new ArrayList<>();
		protected Text projectNameField;
		
		List<String> availablePlugins = loadAvailablePlugins();
		List<String> pluginsEnabledManually = new ArrayList<>();
		
		protected PluginPage() {
			super("PluginPage");
	        setTitle("New Epidemiological Modeling Project");
		}

		@Override
		public void createControl(Composite parent) {
			container = new Composite(parent, SWT.NONE);
	        container.setLayout(new GridLayout(2, false));
	        Label label = new Label(container, SWT.NONE);
	        label.setText("Project Name");
	        projectNameField = new Text(container, SWT.BORDER | SWT.SINGLE);
	        projectNameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	        projectNameField.setText("");
	        projectNameField.addKeyListener(new KeyListener() {
	            @Override
	            public void keyPressed(KeyEvent e) { }

	            @Override
	            public void keyReleased(KeyEvent e) {
	            	setPageComplete(isPageComplete());
	            }
	        });
	        
	        redraw();
	        
	        setControl(container);
	        
	        setPageComplete(true);
		}

		void redraw() {
			for (Control  c : controls)
				c.dispose();
			controls.clear();
			
			List<String> selectedFeatures = new ArrayList<>();
			
			for (String plugin : availablePlugins) {
				Button b = new Button(container, SWT.CHECK);
		        Label label = new Label(container, SWT.NONE);
		        controls.add(b);
		        controls.add(label);
		        label.setText(plugin);
				
				boolean selectedByFM = selectedFeatures.contains(plugin);
				if (selectedByFM) {
					b.setSelection(true);
					b.setEnabled(false);
				} else {
		        	b.setSelection(pluginsEnabledManually.contains(plugin));
			        b.addSelectionListener(new SelectionAdapter()  {
						@Override
						public void widgetSelected(SelectionEvent e) {
							boolean enabled = b.getSelection();
							if (enabled && !pluginsEnabledManually.contains(plugin))
								pluginsEnabledManually.add(plugin);
							else if (!enabled && pluginsEnabledManually.contains(plugin))
								pluginsEnabledManually.remove(plugin);
							redraw();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {}
			        });
				}
					
			}
			
        	container.layout();
		}
		
		List<String> loadAvailablePlugins() {
			try {
				EpimodelPackageImpl.loadExtensions(epimodelprojectcreationwizard.class.getClassLoader());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			EpimodelPackageImpl.collectAllEClasses();
			return EpimodelPackageImpl
					.getEpimodelPackages()
					.stream()
					.map(EPackage::getName)
					.filter(p -> p != "epimodel")
					.toList();
		}
		
		public String getProjectName() {
			return projectNameField.getText();
		}
	}
}
