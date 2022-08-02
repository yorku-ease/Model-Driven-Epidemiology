package use_epi;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
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
//				System.out.println("Unrespected Set of Constraints: " + fmce.getShort());
			System.out.println(fmce.getDetailed());
		}
		
		List<String> jars = Arrays.asList(
			"C:/users/bruno/desktop/dims.jar",
			"C:/users/bruno/desktop/flows.jar"
		);
		
		String fn = "../../runtime-EclipseApplication/modeling/model4.epimodel";
		
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(epimodel.EpimodelPackage.eNAME, new EcoreResourceFactoryImpl());

        ResourceSet resSet = new ResourceSetImpl();
        resSet.getPackageRegistry().put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);

        for (String jar : jars)
            for (Class<?> c : loadClassesFromJar(jar))
            	if (c.getName().endsWith("Package"))
                    resSet.getPackageRegistry().put(
                		(String) getStaticFieldByName(c, "eNS_URI"),
                		getStaticFieldByName(c, "eINSTANCE")
                	);
        
        URI uri = URI.createFileURI(fn);
        Resource resource = resSet.getResource(uri, true);
        
        Object obj = resource.getContents().get(0);
        
        resource.getContents().get(0);
	}
	
	protected static List<Class<?>> loadClassesFromJar(String pathToJar) throws IOException, ClassNotFoundException {
		JarFile jarFile = new JarFile(pathToJar);
		Enumeration<JarEntry> e = jarFile.entries();
		List<Class<?>> classes = new ArrayList<>();

		URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);

		while (e.hasMoreElements()) {
		    JarEntry je = e.nextElement();
		    if (je.isDirectory() || !je.getName().endsWith(".class"))
		        continue;
		    String className = je.getName().substring(0, je.getName().indexOf(".class")).replace('/', '.');
		    Class<?> c = cl.loadClass(className);
		    classes.add(c);
		}
		jarFile.close();
		return classes;
	}
	
	protected static Object getStaticFieldByName(Class<?> c, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field field = c.getField(fieldName);
		field.setAccessible(true);
		return field.get(new Object());
	}
}
