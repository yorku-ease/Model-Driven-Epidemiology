package epi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import epi.NamedCompartments.Flow;
import epimodel.Epidemic;
import epimodel.MetaCompartment;
import epimodel.Dimension;

public class Epi {
	public static void main(String[] args) throws JSONException, IOException {
		String fn = "C:\\Users\\Bruno\\Desktop\\Model-Driven-Epidemiology\\src\\runtime-EclipseApplication\\"
				+ "epimodel0315\\MyEpimodel.epimodel";

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(epimodel.EpimodelPackage.eNAME, new EcoreResourceFactoryImpl());

        ResourceSet resSet = new ResourceSetImpl();
        resSet.getPackageRegistry().put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);
        URI uri = URI.createFileURI(fn);
        Resource resource = resSet.getResource(uri, true);
        
        Epidemic myEpi = (Epidemic) resource.getContents().get(0);
        EList<Dimension> dimensions = myEpi.getDimension();
        NamedCompartments namedCompartments = new NamedCompartments(dimensions);
        
        JSONObject metadata = new JSONObject();
    	JSONArray compartments = new JSONArray();
    	JSONObject flowParameters = new JSONObject();
    	JSONObject initialConditions = new JSONObject();
        JSONObject susceptibility = new JSONObject();
        JSONObject contagiousness = new JSONObject();
        
        JSONArray dims = new JSONArray();
        for (Dimension dim : dimensions) {
        	JSONObject dimjson = new JSONObject();
        	dimjson.put("id", dim.getId());
        	JSONArray metaCompartments = new JSONArray();
        	for (MetaCompartment c : dim.getCompartment()) {
        		JSONObject cjson = new JSONObject();
        		cjson.put("id", c.getId());
        		metaCompartments.put(cjson);
        	}
        	dimjson.put("MetaCompartments", metaCompartments);
        	dims.put(dimjson);
        }
        metadata.put("dimensions", dims);
        
        for (Compartment c : namedCompartments.compartments) {
        	compartments.put(c.getEquation());
        	susceptibility.put(c.getCompositeStateName(), 0.1);
        	contagiousness.put(c.getCompositeStateName(), 0.1);
    		initialConditions.put(c.id, 1);
        }
        
        for (Flow flow : namedCompartments.flows)
        	flowParameters.put(flow.id + "[" + flow.n + "]", 0.1);
        
    	JSONObject parameters = new JSONObject();
    	parameters.put("flows", flowParameters);
    	parameters.put("initial_conditions", initialConditions);
    	parameters.put("susceptibility", susceptibility);
    	parameters.put("contagiousness", contagiousness);
    	writeJsonFile(compartments, args[0]);
    	writeJsonFile(parameters, args[1]);
    	writeJsonFile(metadata, args[2]);
        
        Runtime.getRuntime().exec("python epi.py C:\\Users\\Bruno\\Desktop\\compartments.json C:\\Users\\Bruno\\Desktop\\parameters.json C:\\Users\\Bruno\\Desktop\\epiout.txt");
        String content = Files.readAllLines((Paths.get("C:\\Users\\Bruno\\Desktop\\epiout.txt"))).get(0);
        List<List<Float>> solution = new ArrayList<>();
        if (content.charAt(0) == '[') {
			String[] splitContent = content.substring(1).split(",");
			for (String s : splitContent) {
				if (s.charAt(0) != ']') {
					if (s.charAt(0) == '[') {
						solution.add(new ArrayList<>());
						s = s.substring(1);
					}
					solution.get(solution.size() - 1).add(Float.parseFloat(s));
				}
			}
        }
        System.out.println(solution);
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
}