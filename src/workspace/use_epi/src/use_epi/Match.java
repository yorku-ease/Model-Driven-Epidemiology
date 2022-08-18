package use_epi;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.lang.*;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import epimodel.Compartment;
import epimodel.Epidemic;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalFlowEquation;

public class Match {
	public static void main(String[] args) throws Exception {
		
		String model1 = "../../runtime-EclipseApplication/modeling/model1.epimodel";

		String model2 = "../../runtime-EclipseApplication/modeling/model2.epimodel";
		
		epimodel.EpidemicWrapper myEpi1 = loadEpimodel(model1);
		epimodel.EpidemicWrapper myEpi2 = loadEpimodel(model2);
		


        List<PhysicalCompartment> cs1 = myEpi1.getEpidemic().getPhysicalCompartments();
        List<PhysicalCompartment> cs2 = myEpi2.getEpidemic().getPhysicalCompartments();
        
        Map<String, List<Compartment>> branches1 = myEpi1.getEpidemic().getAllCompartmentBranches();
        Map<String, List<Compartment>> branches2 = myEpi2.getEpidemic().getAllCompartmentBranches();
        
        //System.out.println(branches);
        
        Map<String, Compartment> branchesbis1 = myEpi2.getEpidemic().getModelTree();
        Map<String, Compartment> branchesbis2 = myEpi2.getEpidemic().getModelTree();
        
        
        System.out.println("BRANCHES MODELE 1 ");
        for (String key : branches1.keySet()) {
        	System.out.println("\nKEY  :" + key + "---->" + branches1.get(key));
	     }
        
        

        System.out.println("BRANCHES MODELE 2 ");
        for (String key : branches2.keySet()) {
        	System.out.println("\nKEY  :" + key + "---->" + branches2.get(key));
	     }
        
        
        System.out.println("BRANCHES BIS MODELE 2 ");
        for (String key : branchesbis2.keySet()) {
        	System.out.println("\nKEY  :" + key + "---->" + branchesbis2.get(key));
	     }
        
        
        Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch = matchTwoEpimodels(cs1, cs2);
       
        System.out.println("PHYSICAL ");
        printResultMatch(resultmatch);        
        
        System.out.println(" FIN ");
       
	} 
	
	protected static epimodel.EpidemicWrapper loadEpimodel(String path) {
		

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
		

        URI uri = URI.createFileURI(path);
        Resource resource1 = resSet.getResource(uri, true);
        epimodel.EpidemicWrapper myEpi = (epimodel.EpidemicWrapper) resource1.getContents().get(0);
        
        return myEpi;
	}
	
	private static Map<PhysicalCompartment, List<PhysicalCompartment>> matchTwoEpimodels(List<PhysicalCompartment> cs1 , List<PhysicalCompartment> cs2) {
		
		 Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch = new HashMap<>();
		 
		        for (PhysicalCompartment pc1 : cs1 ){
	        	ArrayList<PhysicalCompartment> tmp = new ArrayList<>();
		        	for (PhysicalCompartment pc2 : cs2) {
		        		if (pc1.labels.containsAll(pc2.labels) || pc2.labels.containsAll(pc1.labels) ) {
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
	        
	        printResultMatch(resultmatch);
	        return resultmatch;
	}
		
	
	
	private static void printResultMatch (Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch) {
		
		for (PhysicalCompartment key : resultmatch.keySet()) {
			System.out.print("MATCH  : " + key.labels + "   ---->      " +  "[ ");
			for (int i = 0; i < resultmatch.get(key).size(); i++ )  {
				System.out.print(resultmatch.get(key).get(i).labels);
				try {
					if(resultmatch.get(key).get(i+1) != null)
						System.out.print(" , ");
				} catch (Exception e) {

				}
				
			}
			System.out.print(" ] \n");
		}
	}
}
