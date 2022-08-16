package use_epi;

import java.io.PrintWriter;
import java.io.ObjectInputStream.GetField;
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

import epimodel.Compartment;
import epimodel.Epidemic;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalFlowEquation;
public class Diff {

	public static void main(String[] args) throws Exception {
	
		String model1 = "../../runtime-EclipseApplication/modeling/model1.epimodel";

		String model2 = "../../runtime-EclipseApplication/modeling/model2.epimodel";
		
		epimodel.EpidemicWrapper myEpi1 = Match.loadEpimodel(model1);
		epimodel.EpidemicWrapper myEpi2 = Match.loadEpimodel(model2);
		

		
		
	    List<PhysicalCompartment> pc1 = myEpi1.getEpidemic().getPhysicalCompartments();
        List<PhysicalCompartment> pc2 = myEpi2.getEpidemic().getPhysicalCompartments();
       
        Map<String, List<Compartment>> branches1 = myEpi1.getEpidemic().getAllCompartmentBranches();
        Map<String, List<Compartment>> branches2 = myEpi2.getEpidemic().getAllCompartmentBranches();
        
        Map<String, Compartment> branchesbis1 = myEpi1.getEpidemic().getModelTree();
        Map<String, Compartment> branchesbis2 = myEpi2.getEpidemic().getModelTree();
       
      //  myEpi2.getEpidemic().printModelTree();
        
        Diff(branchesbis1, branchesbis2);
        System.out.println(" FIN ");
		
		
	}
	
	
	public static void Diff( Map<String, Compartment> branchesbis1,  Map<String, Compartment> branchesbis2) {
		
	/*	 

		 System.out.println("\n MODEL 1 :");
		 for (String key : branchesbis1.keySet()) {
        	System.out.println("\nKEY  :" + key + "---->" + branchesbis1.get(key));
	     }
		 
		 System.out.println("\n MODEL 2 :");
		 for (String key : branchesbis2.keySet()) {
	        	System.out.println("\nKEY  :" + key + "---->" + branchesbis2.get(key));
		 }
		*/ 
		 
		 
		 for (String key1 : branchesbis1.keySet()) {
			 for (String key2 : branchesbis2.keySet()) {
				 Compartment right = branchesbis1.get(key1);
				 Compartment left =  branchesbis2.get(key2);
				// System.out.println("RIGHT : " + right + "  LEFT  " + left);
				 if(key1.equals(key2)) {
					// System.out.println("RIGHT   " + right.getClass() + "LEFT   " + left.getClass());
					 
					 if (right.getParent() instanceof Compartment && left.getParent() instanceof Compartment ) {
						 if (!right.getClass().equals(left.getClass())) {
							 System.out.println("\n RETYPE MATCH  :" + right.getSimpleCompartmentLabel() + "---->" + left.getSimpleCompartmentLabel());
							// System.out.println ("\nDEBUG " + left.eContents().get(0).eContents());
							// System.out.println("\n " +  left + " RIGHT" + right.getChildrens() + "LEFT" + left.isDivided());
							 if(right.getChildrens().isEmpty() && left.isDivided()) {
								 System.out.println("\n REGROUP MATCH  :" + right.getSimpleCompartmentLabel() + "---->" + left.getChildrens());
							 }
						 }
						  
						 else if(right.getParent().getSimpleCompartmentLabel().equals(left.getParent().getSimpleCompartmentLabel())) {
							 System.out.println("\n EXACT MATCH  :" + right.getSimpleCompartmentLabel() + "---->" + left.getSimpleCompartmentLabel());
						 }
					 }
				
			 }
		  }
		 }
		
		
	}
	
	
	
/*
	private static boolean isExactMatch (PhysicalCompartment key,PhysicalCompartment match) {
	
		
	}
	*/
	private static boolean isMatchSpecification (PhysicalCompartment key, PhysicalCompartment match) {
		
		return key.labels.containsAll(match.labels);
	
	}
	
	private static boolean isMatchRegroupement (PhysicalCompartment key, PhysicalCompartment match) {
		
		return match.labels.containsAll(key.labels);
	
	}
	
	private static boolean isMatchRetype (PhysicalCompartment key, PhysicalCompartment match) {
	
		
		return true;
	
	}
	private static int sizePhysicalCompartment(PhysicalCompartment pc) {
		
		int size = 0;
		for (String labels : pc.labels) {
			
			size ++;
		}
		
		return size;
	}
	
	static boolean containsAll(PhysicalCompartment pc2, PhysicalCompartment pc1) {
		
		int check = 0;
		
		for (String labels1 : pc1.labels) {
			for (String labels2 : pc2.labels) {
				
				if (labels1.equals(labels2)) {
					check ++;
				}
			}
		}
		
		return check == sizePhysicalCompartment(pc1);
	}
}
