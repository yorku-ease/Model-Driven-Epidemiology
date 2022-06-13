package use_epi;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
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

public class Main {
	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		
		List<String> jars = Arrays.asList(
			"C:/users/bruno/desktop/dims.jar",
			"C:/users/bruno/desktop/flows.jar"
		);
		
		String fn = "../../runtime-EclipseApplication/modeling/covid.epimodel";
        List<String> methodNames = Arrays.asList("getEpidemic", "compile");
		
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
        
        // epimodel.Epidemic myEpi = ((epimodel.EpidemicWrapper) resource.getContents().get(0)).getEpidemic();
        
        for (String methodName : methodNames) {
            Class<?> objclass = obj.getClass();
            Method getEpidemic = getMethodByName(objclass, methodName);
            obj = getEpidemic.invoke(obj);
        }

        return;
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
	
	protected static Method getMethodByName(Class<?> c, String methodName) throws NoSuchMethodException {
		return c.getMethod(methodName);
	}
}
