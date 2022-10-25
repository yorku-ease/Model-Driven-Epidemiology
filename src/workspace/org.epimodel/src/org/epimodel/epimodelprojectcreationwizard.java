package org.epimodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
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
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.SelectableFeature;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.init.FMCoreLibrary;
import de.ovgu.featureide.fm.core.init.LibraryManager;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelIO;

public class epimodelprojectcreationwizard extends Wizard implements INewWizard {
	
	protected WizardNewProjectCreationPage creationPage;
	final IFeatureModel fm;
	final FeatureModelFormula fmf;
	final Configuration conf;

	public epimodelprojectcreationwizard() throws IOException {
		LibraryManager.registerLibrary(FMCoreLibrary.getInstance());
		
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		URL url = FileLocator.find(bundle, new org.eclipse.core.runtime.Path("model.xml"));
		Path path = Paths.get(url.getPath());
		
		String modelXML = readFile(url);
		fm = FeatureModelIO.getInstance().loadFromSource(modelXML, path);
		fmf = new FeatureModelFormula(fm);
		conf = new Configuration(fmf);
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
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	@Override
	public boolean performFinish() {
		if (markPlugins()) {
			CustomProjectSupport.createProject(
					creationPage.getProjectName(),
					ResourcesPlugin.getWorkspace().getRoot().getLocationURI(),
					fm,
					conf);
			return true;
		} else {
			List<List<IConstraint>> errs = Solver.getErrors(fm,  conf, 3);
			if (errs.size() > 0)
				System.out.println("Invalid configuration, here are the unrespected sets of constraints:");
	//		for (int i = 0; i < availableExtensions.size(); ++i) {
	//			conf.setManual(availableExtensions.get(i), creationPage.getTruthValues().get(i) ? Selection.SELECTED : Selection.UNSELECTED);
	//		}
			for (List<IConstraint> err : errs) {
				Solver.FeatureModelConfigurationError fmce = new Solver.FeatureModelConfigurationError(err);
				System.out.println("Unrespected Set of Constraints: " + fmce.getShort());
				System.out.println(fmce.getDetailed());
			}
			return false;
		}
	}
	
	boolean markPlugins() {
		List<IFeatureStructure> pluginFeatures = fm.getFeature("Plugins").getStructure().getChildren();
		
		{			
			for (IFeatureStructure feature : pluginFeatures)
				conf.setManual(feature.getFeature().getName(), Selection.SELECTED);
			List<List<IConstraint>> errs = Solver.getErrors(fm,  conf, 1);
			if (errs.size() > 0)
				return false;	
		}
		
		for (IFeatureStructure feature : pluginFeatures) {
			conf.setManual(feature.getFeature().getName(), Selection.UNSELECTED);
			List<List<IConstraint>> errs = Solver.getErrors(fm,  conf, 1);
			if (errs.size() > 0)
				conf.setManual(feature.getFeature().getName(), Selection.SELECTED);
		}

		List<List<IConstraint>> errs = Solver.getErrors(fm,  conf, 1);
		return errs.size() == 0;
	}
	
	@Override
    public void addPages() {
		super.addPages();
		creationPage = new WizardNewProjectCreationPage(fm, conf);
		addPage(creationPage);
	}
	
	class WizardNewProjectCreationPage extends WizardPage {
	    private Composite container;
		private Text projectNameField;
		final IFeatureModel fm;
		final Configuration conf;
		List<Control> controls = new ArrayList<>();

		protected WizardNewProjectCreationPage(IFeatureModel fm, Configuration conf) {
			super("WizardNewProjectCreationPage");
			this.fm = fm;
			this.conf = conf;
	        setTitle("New Epidemiological Project");
	        setDescription("Wizard: First page");
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
	                if (!projectNameField.getText().isEmpty())
	                    setPageComplete(true);
	            }
	        });
//
//			this.checkboxes = new ArrayList<>(extensions.size());
//			for (int i = 0; i < extensions.size(); ++i) {
//			}
	        
	        redraw();
	        
	        setControl(container);
	        
	        setPageComplete(false);
		}
		
		void tree(Composite container, IFeatureStructure feature, int depth) {
			
			if (feature.getFeature().getName().equals("Plugins"))
				return;
			
			String prefix = "";
			for (int i = 0; i < depth; ++i)
				prefix += "      ";
			final String _prefix = prefix;
			
			
			Button b = new Button(container, SWT.CHECK);
			boolean enabled = enabled(feature.getFeature());
			b.setSelection(enabled);
	        Label label = new Label(container, SWT.NONE);
	        controls.add(b);
	        controls.add(label);
	        label.setText(_prefix + feature.getFeature().getName());
	        
	        b.addSelectionListener(new SelectionAdapter()  {
				@Override
				public void widgetSelected(SelectionEvent e) {
					boolean enabled = b.getSelection();
					if (enabled) {
						conf.setManual(feature.getFeature().getName(), Selection.SELECTED);
					} else {
						conf.setManual(feature.getFeature().getName(), Selection.UNSELECTED);
						unselectChildren(feature);
					}
					redraw();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
	        	
	        });
	        
			if (enabled)
				for (IFeatureStructure child : feature.getChildren())
					tree(container, child, depth + 1);
		}
		
		void unselectChildren(IFeatureStructure feature) {
				for (IFeatureStructure child : feature.getChildren())
					if (enabled(child.getFeature())) {
						conf.setManual((SelectableFeature) child, Selection.UNSELECTED);
						unselectChildren(child);
				}
		}
		
		void redraw() {
			for (Control  c : controls)
				c.dispose();
			controls.clear();
	        
			IFeatureStructure root = fm.getFeature("EpidemicMetamodelLine").getStructure();
        	tree(container, root, 0);
			
        	container.layout();
		}
		
		boolean enabled(IFeature feature) {
			return conf.getSelectedFeatures().contains(feature);
		}
		
		public String getProjectName() {
			return projectNameField.getText();
		}
	}
}
