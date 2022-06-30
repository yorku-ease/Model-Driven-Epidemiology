package use_epi;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dimensionEpidemic.DimensionEpidemic;

public class Test {
	public static void main(String[] args) throws Exception {
		
		String model_fn = "../../runtime-EclipseApplication/modeling/model4.epimodel";
		String outfolder = "C:/Users/Bruno/Desktop/";
		
		{
			Resource.Factory.Registry factoryRegistry = Resource.Factory.Registry.INSTANCE;
	        Map<String, Object> m = factoryRegistry.getExtensionToFactoryMap();
	        m.put("*", new EcoreResourceFactoryImpl());
		}
		
        ResourceSet resSet = new ResourceSetImpl();
        EPackage.Registry pkgRegistry = new EPackageRegistryImpl();
        resSet.setPackageRegistry(pkgRegistry);
        
		{
	        pkgRegistry.put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);
	        pkgRegistry.put(dimensionEpidemic.DimensionEpidemicPackage.eNS_URI, dimensionEpidemic.DimensionEpidemicPackage.eINSTANCE);
	        pkgRegistry.put(batchRateContactFlow.BatchRateContactFlowPackage.eNS_URI, batchRateContactFlow.BatchRateContactFlowPackage.eINSTANCE);
	        pkgRegistry.put(compartmentGroup.CompartmentGroupPackage.eNS_URI, compartmentGroup.CompartmentGroupPackage.eINSTANCE);
	        
//	        pkgRegistry.remove(epimodel.EpimodelPackage.eNS_URI);
//	        pkgRegistry.remove(dimensionEpidemic.DimensionEpidemicPackage.eNS_URI);
//	        pkgRegistry.remove(batchRateContactFlow.BatchRateContactFlowPackage.eNS_URI);
//	        pkgRegistry.remove(compartmentGroup.CompartmentGroupPackage.eNS_URI);
		}
        
        URI uri = URI.createFileURI(model_fn);
        Resource resource = resSet.getResource(uri, true);
        
        DimensionEpidemic myEpi = (DimensionEpidemic) ((epimodel.EpidemicWrapper) resource.getContents().get(0)).getEpidemic();
        
		List<JSONObject> res = myEpi.compile();
        
        for (JSONObject obj : res) {
        	String fn = obj.getString("filename");
        	Object content = obj.get(obj.getString("key"));
        	// java things...
        	if (content instanceof JSONObject)
        		writeJsonFile((JSONObject) content, outfolder + fn + ".json");
        	else if (content instanceof JSONArray)
        		writeJsonFile((JSONArray) content, outfolder + fn + ".json");
        	else
        		throw new Exception();
        }
        
        return;
	}

	// java things...
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
