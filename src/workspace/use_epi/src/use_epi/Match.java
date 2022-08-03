package use_epi;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.lang.*;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalFlowEquation;

public class Match {
	public static void main(String[] args) throws Exception {
		
		String model1 = "../../runtime-EclipseApplication/modeling/model1.epimodel";

		String model2 = "../../runtime-EclipseApplication/modeling/model2.epimodel";

		
		Resource.Factory.Registry factoryRegistry = new ResourceFactoryRegistryImpl();
        factoryRegistry.getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		
        ResourceSet resSet = new ResourceSetImpl();
        EPackage.Registry pkgRegistry = new EPackageRegistryImpl();
        resSet.setPackageRegistry(pkgRegistry);
        resSet.setResourceFactoryRegistry(factoryRegistry);
        
		{
	        pkgRegistry.put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);
	        pkgRegistry.put(dimensionEpidemic.DimensionEpidemicPackage.eNS_URI, dimensionEpidemic.DimensionEpidemicPackage.eINSTANCE);
	        pkgRegistry.put(batchRateContactFlow.BatchRateContactFlowPackage.eNS_URI, batchRateContactFlow.BatchRateContactFlowPackage.eINSTANCE);
	        pkgRegistry.put(compartmentGroup.CompartmentGroupPackage.eNS_URI, compartmentGroup.CompartmentGroupPackage.eINSTANCE);
		}
		

        URI uri1 = URI.createFileURI(model1);
        Resource resource1 = resSet.getResource(uri1, true);
        epimodel.EpidemicWrapper myEpi1 = (epimodel.EpidemicWrapper) resource1.getContents().get(0);


        URI uri2 = URI.createFileURI(model2);
        
        Resource resource2 = resSet.getResource(uri2, true);
        epimodel.EpidemicWrapper myEpi2 = (epimodel.EpidemicWrapper) resource2.getContents().get(0);
        
        List<PhysicalCompartment> cs1 = myEpi1.getEpidemic().getPhysicalCompartments();
        List<PhysicalCompartment> cs2 = myEpi2.getEpidemic().getPhysicalCompartments();
      
        
        Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch = new HashMap<>();
        
        for (PhysicalCompartment pc1 : cs1 ){
        	ArrayList<PhysicalCompartment> tmp = new ArrayList<>();
        	for (PhysicalCompartment pc2 : cs2) {
        		if(pc1.equals(pc2)) {
        			PhysicalCompartment key = pc1;
        			tmp.add(pc2);
        			resultmatch.put(key,tmp);
        		}
        		
        		else if (contains(pc1, pc2)) {
        			PhysicalCompartment key = pc1;
        			tmp.add(pc2);
        			resultmatch.put(key,tmp);
        			
        		}
        	}
        }
        System.out.println("LISTE PhysicalCompartment1  : \n");
        for (int i = 0; i < cs1.size(); i++ )  
        	 System.out.println(cs1.get(i).labels);

        System.out.println(" \n LISTE PhysicalCompartment2  : \n");
        for (int i = 0; i < cs2.size(); i++ )  
        	 System.out.println(cs2.get(i).labels);

        System.out.println("\n");
        for (PhysicalCompartment key : resultmatch.keySet()) {
        	 for (int i = 0; i < resultmatch.get(key).size(); i++ )  {
	        	System.out.println("MATCH  :" + key.labels +"   ---->      "+ resultmatch.get(key).get(i).labels);
        	 }
        }
        System.out.println("     ");
       
	} 
	private static int size(PhysicalCompartment pc) {
		
		int size = 0;
		
		for (String labels : pc.labels) {
			
			size ++;
		}
		
		return size;
	}
	
	private static boolean contains(PhysicalCompartment pc1, PhysicalCompartment pc2) {
		
		int check = 0;
		
		for (String labels1 : pc1.labels) {
			for (String labels2 : pc2.labels) {
				
				if (labels1.equals(labels2)) {
					check ++;
				}
			}
		}
		if (check == size(pc1))
			return true;
		
		return false;
	}
	
	
}
