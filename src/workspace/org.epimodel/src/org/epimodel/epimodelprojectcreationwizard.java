package org.epimodel;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureModelElement;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.init.FMCoreLibrary;
import de.ovgu.featureide.fm.core.init.LibraryManager;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

public class epimodelprojectcreationwizard extends Wizard implements INewWizard {
	
	protected WizardNewProjectCreationPage creationPage;
	
	final IFeatureModel fm;
	final FeatureModelFormula fmf;
	final Configuration conf;
	final List<String> availableExtensions;

	public epimodelprojectcreationwizard() {
		LibraryManager.registerLibrary(FMCoreLibrary.getInstance());
		
		fm = FeatureModelManager.load(Paths.get("C:/Users/Bruno/Desktop/Model-Driven-Epidemiology/src/feature-model.xml"));
		fmf = new FeatureModelFormula(fm);
		conf = new Configuration(fmf);
		availableExtensions = conf.getUndefinedSelectedFeatures().stream().map(IFeatureModelElement::getName).collect(Collectors.toList());
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		System.out.println("init");
	}

	@Override
	public boolean performFinish() {
		System.out.println("performFinish");
		CustomProjectSupport.createProject(
			creationPage.getProjectName(),
			ResourcesPlugin.getWorkspace().getRoot().getLocationURI(),
			availableExtensions,
			creationPage.getTruthValues());
		
		System.out.println("Invalid configuration, here are the unrespected sets of constraints:");
		for (int i = 0; i < availableExtensions.size(); ++i) {
			conf.setManual(availableExtensions.get(i), creationPage.getTruthValues().get(i) ? Selection.SELECTED : Selection.UNSELECTED);
		}
		for (List<IConstraint> err : Solver.getErrors(fm, conf, 2)) {
			Solver.FeatureModelConfigurationError fmce = new Solver.FeatureModelConfigurationError(err);
			System.out.println("Unrespected Set of Constraints: " + fmce.getShort());
			System.out.println(fmce.getDetailed());
		}
		
		return true;
	}
	
	@Override
    public void addPages() {
		System.out.println("addPages");
		super.addPages();
		creationPage = new WizardNewProjectCreationPage("WizardNewProjectCreationPage", availableExtensions);
		addPage(creationPage);
	}
	
	class WizardNewProjectCreationPage extends WizardPage {
	    private Composite container;
		private Text projectNameField;
	    private List<Button> checkboxes;
	    private List<String> extensions;

		protected WizardNewProjectCreationPage(String pageName, List<String> extensions) {
			super(pageName);
			this.extensions = extensions;
	        setTitle(pageName);
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

			this.checkboxes = new ArrayList<>(extensions.size());
			for (int i = 0; i < extensions.size(); ++i) {
				this.checkboxes.add(new Button(container, SWT.CHECK));
		        Label checkboxlabel = new Label(container, SWT.NONE);
		        checkboxlabel.setText(extensions.get(i));
			}
	        
	        setControl(container);
	        
	        setPageComplete(false);
		}
		
		public List<Boolean> getTruthValues() {
			return checkboxes.stream().map(c -> c.getSelection()).collect(Collectors.toList());
		}
		
		public String getProjectName() {
			return projectNameField.getText();
		}
	}
}
