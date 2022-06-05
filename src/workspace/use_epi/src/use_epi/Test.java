package use_epi;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
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
		
		String fn = "../../runtime-EclipseApplication/modeling/MyEpimodel.epimodel";
		String outfolder = "C:/Users/Bruno/Desktop/";
		
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("*", new EcoreResourceFactoryImpl());

        ResourceSet resSet = new ResourceSetImpl();
        resSet.getPackageRegistry().put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);
        resSet.getPackageRegistry().put(dimensionEpidemic.DimensionEpidemicPackage.eNS_URI, dimensionEpidemic.DimensionEpidemicPackage.eINSTANCE);
        resSet.getPackageRegistry().put(batchRateContactFlow.BatchRateContactFlowPackage.eNS_URI, batchRateContactFlow.BatchRateContactFlowPackage.eINSTANCE);

        URI uri = URI.createFileURI(fn);
        Resource resource = resSet.getResource(uri, true);
        
        DimensionEpidemic myEpi = (DimensionEpidemic) ((epimodel.EpidemicWrapper) resource.getContents().get(0)).getEpidemic();
        
        @SuppressWarnings("unused")
		List<JSONObject> res = myEpi.compile();
        
        for (JSONObject obj : res) {
        	Object content = obj.get(obj.getString("filename"));
        	if (content instanceof JSONObject)
        		writeJsonFile((JSONObject) content, outfolder + obj.getString("filename") + ".json");
        	else if (content instanceof JSONArray)
        		writeJsonFile((JSONArray) content, outfolder + obj.getString("filename") + ".json");
        	else
        		throw new Exception();
        }
        	

        return;
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
