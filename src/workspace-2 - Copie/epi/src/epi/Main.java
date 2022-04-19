package epi;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {
	public static void main(String[] args) throws Exception {
		String fn = "C:\\Users\\Bruno\\Desktop\\Model-Driven-Epidemiology\\src\\runtime-EclipseApplication\\"
				+ "epidemiological-modeling-1\\" +
				// "MyEpidimensionsbase.epidimensionsbase";
				"MyEpidimensionsrefgroup.epidimensionsrefgroup";

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        ResourceSet resSet = new ResourceSetImpl();
        
		{
	        m.put(epibase.EpibasePackage.eNAME, new EcoreResourceFactoryImpl());
	        resSet.getPackageRegistry().put(epibase.EpibasePackage.eNS_URI, epibase.EpibasePackage.eINSTANCE);
		}
		{
	        m.put(epidimensionsbase.EpidimensionsbasePackage.eNAME, new EcoreResourceFactoryImpl());
	        resSet.getPackageRegistry().put(epidimensionsbase.EpidimensionsbasePackage.eNS_URI, epidimensionsbase.EpidimensionsbasePackage.eINSTANCE);
		}
		{
	        m.put(epidimensionsrefgroup.EpidimensionsrefgroupPackage.eNAME, new EcoreResourceFactoryImpl());
	        resSet.getPackageRegistry().put(epidimensionsrefgroup.EpidimensionsrefgroupPackage.eNS_URI, epidimensionsrefgroup.EpidimensionsrefgroupPackage.eINSTANCE);
		}
        
        URI uri = URI.createFileURI(fn);
        Resource resource = resSet.getResource(uri, true);
        
        epibase.Epidemic myEpi = (epibase.Epidemic) resource.getContents().get(0);
        
        JSONObject json = myEpi.compile();
        
        writeJsonFile(json, json.getString("filename"));
	}
	
	protected static void writeJsonFile(JSONObject o, String filename) throws FileNotFoundException, UnsupportedEncodingException, JSONException {
	    PrintWriter writer = new PrintWriter(filename, "UTF-8");
	    writer.print(o.toString(4));
	    writer.println();
	    writer.close();
	}
}
