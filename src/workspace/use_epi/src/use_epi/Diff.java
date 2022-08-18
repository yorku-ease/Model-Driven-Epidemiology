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
        
        displayDiff(branchesbis1, branchesbis2);
        System.out.println(" FIN ");
		
		
	}
	
	
	public static void displayDiff( Map<String, Compartment> branchesbis1,  Map<String, Compartment> branchesbis2) {
		
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
			
			 if( branchesbis2.containsKey(key1) ) {  //OBJECT WITH THE SAME ID

				 Compartment right = branchesbis1.get(key1);
				 String key2 = branchesbis2.get(key1).getSimpleCompartmentLabel();
				 Compartment left =  branchesbis2.get(key2);
				 
				//SAME TYPE
				 
				 if(isSameType(right, left)) { 
					 
					 if(haveSameParent(right, left) & haveSameChildren(right, left)) //  SAME PARENT & SAME CHILDREN
							 System.out.println("EXACT MATCH " + key1 + " --> " + key2 );
					
					 else if(haveSameParent(right, left) & !haveSameChildren(right, left)) //SAME PARENT & DIFFERENT CHILDREN 
						 	displaysPCAnalysis(right.getPhysicalCompartments(), left.getPhysicalCompartments());
						 
					 else if(!haveSameParent(right, left) & haveSameChildren(right, left)) //DIFFERENT PARENT & SAME CHILDREN
						 	System.out.println("MOVE MATCH " + key1 + " --> " + key2 );
					 else if (right.eContainer().eContainer() instanceof Epidemic &  left.eContainer().eContainer() instanceof Epidemic) // CASE THE PARENT ARE THE EPIDEMIC OBJECT
						 	System.out.println(" MATCH " + key1 + " --> " + key2 );
					 else {															//		DIFFERENT PARENT & DIFFERENT CHILDREN
						 	System.out.println("ADD/DELETE MATCH " + key1 + " --> " + key2 );
					 }
				 }
				 
				//DIFFERENT TYPE
				 
				 else{							
					 if(haveSameParent(right, left) & haveSameChildren(right, left)) //  SAME PARENT & SAME CHILDREN
						 System.out.println("RETYPE MATCH " + key1 +" Of type  : " + right.getClass() + " --> " + key2 + " Of type : " + left.getClass());
					 
					 else if(haveSameParent(right, left) & !haveSameChildren(right, left)) //SAME PARENT & DIFFERENT CHILDREN 
						 displaysPCAnalysis(right.getPhysicalCompartments(), left.getPhysicalCompartments());
					 
					 else if(!haveSameParent(right, left) & haveSameChildren(right, left)) //DIFFERENT PARENT & SAME CHILDREN
						 System.out.println("RETYPE/MOVE MATCH " + key1 +" Of type  : " + right.getClass() + " --> " + key2 + " Of type : " + left.getClass());
					 
					 																		
					 else if(!haveSameParent(right, left) & !haveSameChildren(right, left)) {//DIFFERENT PARENT & DIFFERENT CHILDREN
						 System.out.println("RIGHT " + right.getParent() + "LEFT " + left.getParent() + "RIGHT " + right.getChildren() + "LEFT " + left.getChildren());
						 System.out.println("ADD/DELETE MATCH " + key1 + " --> " + key2 );
					 }
				 } 
			 }
			 
			// NO OBJECT WITH THE SAME ID
			 else if(!branchesbis2.containsKey(key1)){
				 System.out.println("DELETE" + key1 );
			 }
 		}	
	 }	

		
		
	

	private static boolean haveSameParent (Compartment right, Compartment left) {
		try {
			return right.getParent().getLabel().equals(left.getParent().getLabel());
		}catch (Exception e) {
			return false;// TODO: handle exception
		}
	}
	
	private static boolean haveSameChildren (Compartment right, Compartment left) {
		try {
			return right.getChildren().equals(left.getChildren());
		}catch (Exception e) {
			return false;
		}
	}
	
	
	private static boolean isSpecification (List <PhysicalCompartment> key, List<PhysicalCompartment> match) {
		

		return key.size()<match.size();
	
	}
	
	private static boolean isGeneralization(List <PhysicalCompartment> key, List<PhysicalCompartment> match) {
		
		return key.size()>match.size();
	
	}
	private static boolean isMap(List <PhysicalCompartment> key, List<PhysicalCompartment> match) {
		
		return key.size()==match.size();
	
	}
	
	private static void displaysPCAnalysis(List<PhysicalCompartment> key, List<PhysicalCompartment> match) {
		
		if(isSpecification(key, match)) {
			System.out.print("SPECIFICATION MATCH  : ");
			displayListPC(key); 
			System.out.print( " ---> ");
			displayListPC(match);
		}
		else if(isGeneralization(key, match))
			System.out.println("GENERALIZATION MATCH  : " + key + " ---> " + match);
	
		else if(isMap(key, match))
			System.out.println("MAP MATCH  : " + key + " ---> " + match);
	}
	
	private static void displayListPC(List <PhysicalCompartment> pc) {
		
		for (PhysicalCompartment pci : pc) {
			
			System.out.print(pci.labels);
			
		}
		System.out.println("\n");
		
	}
	
	/*
	private static boolean isGeneralizationBis (Compartment right, Compartment left) {
		
		return left.getChildren().isEmpty() && right.isDivided();
	}
	
	private static boolean isSpecificationBis (Compartment right, Compartment left) {
		
		return right.getChildren().isEmpty() && left.isDivided();
	}
	
	
	*/
	private static boolean isSameType (Compartment right, Compartment left) {
	
		
		return right.getClass().equals(left.getClass());
	
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
