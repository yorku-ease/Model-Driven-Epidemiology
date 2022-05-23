package use_epi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {
	public static void main(String[] args) throws JSONException, IOException, ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		List<String> jars = Arrays.asList(
			"C:/users/bruno/desktop/dims.jar",
			"C:/users/bruno/desktop/flows.jar"
		);
		List<Class<?>> classes = jars.stream().map(jar -> {
			try {
				return Main.loadClassesFromJar(jar);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("ClassNotFoundException");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("IOException");
			}
		}).flatMap(List::stream).collect(Collectors.toList());
		
		String fn = "../../runtime-EclipseApplication/modeling/covid.epimodel";
		
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(epimodel.EpimodelPackage.eNAME, new EcoreResourceFactoryImpl());

        ResourceSet resSet = new ResourceSetImpl();
        resSet.getPackageRegistry().put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);
        
        for (Class<?> c : classes) {
        	if (c.getName().endsWith("Package")) {

        		Field f_eNS_URI = c.getField("eNS_URI");
        		f_eNS_URI.setAccessible(true);
        		String eNS_URI = (String) f_eNS_URI.get(new Object()); // eNS_URI is static so it works on new Object()

        		Field f_eINSTANCE = c.getField("eINSTANCE");
        		f_eINSTANCE.setAccessible(true);
        		Object eINSTANCE = f_eINSTANCE.get(new Object()); // eINSTANCE is static so it works on new Object()

                resSet.getPackageRegistry().put(eNS_URI, eINSTANCE);
        	}
        }
        
        URI uri = URI.createFileURI(fn);
        Resource resource = resSet.getResource(uri, true);
        
        epimodel.Epidemic myEpi = ((epimodel.EpidemicWrapper) resource.getContents().get(0)).getEpidemic();
        return;

//		URIConverter converter = new ExtensibleURIConverterImpl();
////		converter.getURIMap().put(URI.createPlatformResourceURI("platform:/MyEpimodel.epimodel", false), URI.createFileURI(fn2));
//		converter.getURIMap().put(URI.createURI("platform:/", false), URI.createURI("file:/C:/Users/Bruno/Desktop/ObeoDesigner-Community-11.5-win32.win32.x86_64/ObeoDesigner-Community/runtime-EclipseApplication/epimodel0315/"));
//		
//		URI uri2 = converter.normalize(URI.createURI("platform:/MyEpimodel.epimodel", false));
//		System.out.println(uri2);
//        Resource resource2 = resSet.getResource(uri2, true);
//        epimodel.Epidemic myEpi2 = (epimodel.Epidemic) resource2.getContents().get(0);
//        myEpi.getDimension().add(myEpi2.getDimension().get(0));
//        resource.save(null);
//        
//        myEpi.save(null);
//        
//        EcoreUtil.resolveAll(myEpi);
//        
//        boolean y = myEpi.eIsProxy();
//
//        MetaCompartment test1 = myEpi.getDimension().get(0).getFlow().get(0).getFrom();
//        boolean x1 = test1.eIsProxy();
//        Dimension test2 = myEpi.getDimension().get(1);
//        boolean x2 = test2.eIsProxy();
//
//        EcoreUtil.resolveAll(test1);
//        EcoreUtil.resolveAll(test2);
//        
//        JSONObject metadata = new JSONObject();
//    	JSONArray compartments = new JSONArray();
//    	JSONObject flowParameters = new JSONObject();
//    	JSONObject initialConditions = new JSONObject();
//        JSONObject susceptibility = new JSONObject();
//        JSONObject contagiousness = new JSONObject();
//        
//        JSONArray dims = new JSONArray();
//        for (Dimension dim : dimensions) {
//        	JSONObject dimjson = new JSONObject();
//        	dimjson.put("id", dim.getId());
//        	JSONArray metaCompartments = new JSONArray();
//        	for (MetaCompartment c : dim.getCompartment()) {
//        		JSONObject cjson = new JSONObject();
//        		cjson.put("id", c.getId());
//        		metaCompartments.put(cjson);
//        	}
//        	dimjson.put("MetaCompartments", metaCompartments);
//        	dims.put(dimjson);
//        }
//        metadata.put("dimensions", dims);
//        
//        for (Compartment c : namedCompartments.compartments) {
//        	compartments.put(c.getEquation());
//        	susceptibility.put(c.getCompositeStateName(), 0.1);
//        	contagiousness.put(c.getCompositeStateName(), 0.1);
//    		initialConditions.put(c.name, 1);
//        }
//        
//        for (Flow flow : namedCompartments.flows)
//        	flowParameters.put(flow.id + "[" + flow.n + "]", 0.1);
//        
//    	JSONObject parameters = new JSONObject();
//    	parameters.put("flows", flowParameters);
//    	parameters.put("initial_conditions", initialConditions);
//    	parameters.put("susceptibility", susceptibility);
//    	parameters.put("contagiousness", contagiousness);
//    	writeJsonFile(compartments, args[0]);
//    	writeJsonFile(parameters, args[1]);
//    	writeJsonFile(metadata, args[2]);
//        
//        Runtime.getRuntime().exec("python epi.py C:\\Users\\Bruno\\Desktop\\compartments.json C:\\Users\\Bruno\\Desktop\\parameters.json C:\\Users\\Bruno\\Desktop\\epiout.txt");
//        String content = Files.readAllLines((Paths.get("C:\\Users\\Bruno\\Desktop\\epiout.txt"))).get(0);
//        List<List<Float>> solution = new ArrayList<>();
//        if (content.charAt(0) == '[') {
//			String[] splitContent = content.substring(1).split(",");
//			for (String s : splitContent) {
//				if (s.charAt(0) != ']') {
//					if (s.charAt(0) == '[') {
//						solution.add(new ArrayList<>());
//						s = s.substring(1);
//					}
//					solution.get(solution.size() - 1).add(Float.parseFloat(s));
//				}
//			}
//        }
//        System.out.println(solution);
	}
	
	protected static void writeJsonFile(JSONObject o, String filename) throws FileNotFoundException, UnsupportedEncodingException, JSONException {
	    PrintWriter writer = new PrintWriter(filename, "UTF-8");
	    writer.print(o.toString(4));
	    writer.println();
	    writer.close();
	}
	
	protected static void writeJsonFile(JSONArray o, String filename) throws FileNotFoundException, UnsupportedEncodingException, JSONException {
	    PrintWriter writer = new PrintWriter(filename, "UTF-8");
	    writer.print(o.toString(4));
	    writer.println();
	    writer.close();
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
}
