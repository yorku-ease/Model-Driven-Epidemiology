package use_epi;

import java.nio.file.Paths;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

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

public class FM_MM {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		
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
		for (List<IConstraint> err : Solver.getErrors(fm, conf, 2)) {
			Solver.FeatureModelConfigurationError fmce = new Solver.FeatureModelConfigurationError(err);
			System.out.println("Unrespected Set of Constraints: " + fmce.getShort());
			System.out.println(fmce.getDetailed());
		}

		EObject m1 = loadModel("../../runtime-plugin/modeling/MyEpimodel.epimodel");
		
		m1.eAllContents().forEachRemaining(e -> {
			if (!(e instanceof Compartment))
				return;
			System.out.print(e);
			System.out.print(": ");
			System.out.println(((Compartment) e).getLabel());
		});
	}
	
	static EObject loadModel(String model_fn) throws Exception {
		Resource.Factory.Registry factoryRegistry = new ResourceFactoryRegistryImpl();
        factoryRegistry.getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		
        ResourceSet resSet = new ResourceSetImpl();
        resSet.setPackageRegistry(EPackage.Registry.INSTANCE);
        resSet.setResourceFactoryRegistry(factoryRegistry);
        
        EPackage.Registry.INSTANCE.put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);
        
        URI uri = URI.createFileURI(model_fn);
        Resource resource = resSet.getResource(uri, true);
        
        return resource.getContents().get(0);
	}
}
