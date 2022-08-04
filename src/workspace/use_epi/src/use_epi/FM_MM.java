package use_epi;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
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
		
		List<String> jars = listFilesOfDir("../../../metamodel-jars/");
		
		String fn = "../../runtime-EclipseApplication/modeling/DECC_S_I.epimodel";
		
		EObject m1 = loadModelFromMetamodels(jars, fn);
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
	
	public static List<String> listFilesOfDir(String dirname) {
	    File dir = new File(dirname);
	    return Arrays.stream(dir.list()).map(s->dirname+s).collect(Collectors.toList());
	}
	
	static EObject loadModelFromMetamodels(List<String> metamodel_jars, String model_fn) throws Exception {
		Resource.Factory.Registry factoryRegistry = new ResourceFactoryRegistryImpl();
        factoryRegistry.getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		
        ResourceSet resSet = new ResourceSetImpl();
        EPackage.Registry pkgRegistry = new EPackageRegistryImpl();
        resSet.setPackageRegistry(pkgRegistry);
        resSet.setResourceFactoryRegistry(factoryRegistry);
        
        for (String jar : metamodel_jars)
            for (Class<?> c : loadClassesFromJar(jar))
            	if (c.getName().endsWith("Package"))
                    resSet.getPackageRegistry().put(
                		(String) getStaticFieldByName(c, "eNS_URI"),
                		getStaticFieldByName(c, "eINSTANCE")
                	);
        
        URI uri = URI.createFileURI(model_fn);
        Resource resource = resSet.getResource(uri, true);
        
        return resource.getContents().get(0);
	}
}
