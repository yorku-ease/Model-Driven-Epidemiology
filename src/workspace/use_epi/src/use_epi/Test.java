package use_epi;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.json.JSONException;

import dimensionEpidemic.DimensionEpidemic;

public class Test {
	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, JSONException {
		
		String fn = "../../runtime-EclipseApplication/modeling/covid.epimodel";
		
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
		Object res = myEpi.compile();

        return;
	}
}
