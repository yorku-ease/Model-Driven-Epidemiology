package epi;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.json.JSONException;
import epimodel.Epidemic;
import epimodel.EpimodelFactory;
import epimodel.MetaCompartment;
import epimodel.Dimension;

public class EpiImport {
	public static void main(String[] args) throws JSONException, IOException {
		
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(epimodel.EpimodelPackage.eNAME, new EcoreResourceFactoryImpl());
        
        ResourceSet resSet = new ResourceSetImpl();
        resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);

        {
	        Epidemic e1 = EpimodelFactory.eINSTANCE.createEpidemic();
	        e1.getDimension().add(EpimodelFactory.eINSTANCE.createDimension());
	        ((Dimension) e1.getDimension().get(0)).setId("test");
	        ((Dimension) e1.getDimension().get(0)).getCompartment().add(EpimodelFactory.eINSTANCE.createMetaCompartment());
	        ((Dimension) e1.getDimension().get(0)).getCompartment().add(EpimodelFactory.eINSTANCE.createMetaCompartment());
	        ((MetaCompartment) ((Dimension) e1.getDimension().get(0)).getCompartment().get(0)).setId("name0");
	        ((MetaCompartment) ((Dimension) e1.getDimension().get(0)).getCompartment().get(1)).setId("name1");
	        Resource r1 = resSet.createResource(URI.createFileURI("1.epimodel"));
	        r1.getContents().add(e1);
	        
	        Epidemic e2 = EpimodelFactory.eINSTANCE.createEpidemic();
	        e2.getDimension().add(EpimodelFactory.eINSTANCE.createDimension());
	        ((Dimension) e2.getDimension().get(0)).getFlow().add(EpimodelFactory.eINSTANCE.createMetaRate());
	        ((Dimension) e1.getDimension().get(0)).getCompartment().get(0);
	        ((Dimension) e2.getDimension().get(0)).getFlow().get(0);
	        ((Dimension) e2.getDimension().get(0)).getFlow().get(0).setFrom(((Dimension) e1.getDimension().get(0)).getCompartment().get(0));
	        r1.save(null);
	        Resource r2 = resSet.createResource(URI.createFileURI("2.epimodel"));
	        r2.getContents().add(e2);
	        r2.save(null);
		}
        {
            Resource referencer = load("3.epimodel");
            Epidemic referencerEpi = (Epidemic) referencer.getContents().get(0);
            Dimension d = (Dimension) referencerEpi.getDimension().get(0);
            MetaCompartment mc = (MetaCompartment) d.getFlow().get(0).getFrom();
            String s = mc.getId();
            boolean b2 = mc.eIsProxy();
            s = mc.getId();
        }
	}
	
	static Resource load(String filename) {
        ResourceSet resSet = new ResourceSetImpl();
        resSet.getPackageRegistry().put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);
        URI uri = URI.createFileURI(filename);
        Resource resource = resSet.getResource(uri, true);
        return resource;
	}
}