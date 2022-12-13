package use_epi;

import java.nio.file.Paths;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.ConfigurationAnalyzer;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.init.FMCoreLibrary;
import de.ovgu.featureide.fm.core.init.LibraryManager;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import epimodel.Compartment;

public class FeatureModelDemo {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		LibraryManager.registerLibrary(FMCoreLibrary.getInstance());
		IFeatureModel fm = FeatureModelManager.load(Paths.get("C:/Users/Bruno/Desktop/Model-Driven-Epidemiology/src/feature-model.xml"));
		FeatureModelFormula fmf = new FeatureModelFormula(fm);
		Configuration conf = new Configuration(fmf);
		ConfigurationAnalyzer ca = new ConfigurationAnalyzer(fmf, conf);
		
//		List<String> selected = conf.getSelectedFeatures().stream().map(IFeatureModelElement::getName).collect(Collectors.toList());
//		List<String> unselected = conf.getUnSelectedFeatures().stream().map(IFeatureModelElement::getName).collect(Collectors.toList());
//		List<String> undefined = conf.getUndefinedSelectedFeatures().stream().map(IFeatureModelElement::getName).collect(Collectors.toList());
//		System.out.println("Selected: " + selected);
//		System.out.println("UnSelected: " + unselected);
//		System.out.println("Undefined: " + undefined);
		
		conf.setManual("Vaccination", Selection.SELECTED);
		conf.setManual("ProductCompartment", Selection.UNSELECTED);

		System.out.println("Invalid configuration, here are the unrespected sets of constraints:");
		for (List<IConstraint> err : FeatureModelSolver.getErrors(fm, conf, 2)) {
			FeatureModelSolver.FeatureModelConfigurationError fmce = new FeatureModelSolver.FeatureModelConfigurationError(err);
			System.out.println("Unrespected Set of Constraints: " + fmce.getShort());
			System.out.println(fmce.getDetailed());
		}

		EObject m1 = epimodel.impl.EpimodelPackageImpl.loadModel("../../runtime-plugin/modeling/MyEpimodel.epimodel");
		
		m1.eAllContents().forEachRemaining(e -> {
			if (!(e instanceof Compartment))
				return;
			System.out.print(e);
			System.out.print(": ");
			System.out.println(((Compartment) e).getLabel());
		});
	}
}
