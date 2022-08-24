package use_epi;

import java.io.PrintWriter;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
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
		
		String model3 = "../../runtime-EclipseApplication/modeling/model3.epimodel";
				
		String model4 = "../../runtime-EclipseApplication/modeling/DECC_SI_S_I.epimodel";  
		
		String model5 = "../../runtime-EclipseApplication/modeling/DECC_S_I.epimodel"; 
		
		String model6 = "../../runtime-EclipseApplication/modeling/DEG_SI_S_I.epimodel"; 
		
		String model7 = "../../runtime-EclipseApplication/modeling/GECC_SI_S_I.epimodel"; 
		
		String model8 = "../../runtime-EclipseApplication/modeling/GECC_S_I.epimodel"; 
	
		String model9 = "../../runtime-EclipseApplication/modeling/GECC_S_1.epimodel";
		
		String model10 = "../../runtime-EclipseApplication/modeling/GECC_S_2.epimodel";
		
		epimodel.EpidemicWrapper myEpi1 = Match.loadEpimodel(model1);
		epimodel.EpidemicWrapper myEpi2 = Match.loadEpimodel(model10);
		

		System.out.println(myEpi1.getEpidemic().getPhysicalCompartments().get(0).labels);
		
	    List<PhysicalCompartment> pc1 = myEpi1.getEpidemic().getPhysicalCompartments();
        List<PhysicalCompartment> pc2 = myEpi2.getEpidemic().getPhysicalCompartments();
       
        Map<String, List<Compartment>> branches1 = myEpi1.getEpidemic().getAllCompartmentBranches();
        Map<String, List<Compartment>> branches2 = myEpi2.getEpidemic().getAllCompartmentBranches();
        
        Map<String, Compartment> branchesbis1 = myEpi1.getEpidemic().getModelTree();
      //  Map<String, Compartment> branchesbis2 = myEpi2.getEpidemic().getModelTree();
       
        List<String> Test = new ArrayList<>(); 
        List<String> Test2 = new ArrayList<>(); 
        

        
        Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch = Match.matchTwoEpimodels(pc1, pc2);
     //   myEpi1.getEpidemic().printModelTree();
          
      //  myEpi2.getEpidemic().printModelTree();
        
       // displayDiff(branchesbis1, branchesbis2, resultmatch);
        System.out.println(" FIN ");
		
		
	}
	
	
	public static void displayDiff( Map<String, Compartment> branchesbis1,  Map<String, Compartment> branchesbis2, Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch) {
		
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
		 //ORDERED KEYSET
		 
		 for (String key1 : branchesbis1.keySet()) {
			 System.out.println(key1);
			 if( branchesbis2.containsKey(key1) ) {  //OBJECT WITH THE SAME ID

				 Compartment right = branchesbis1.get(key1);
				 String key2 = branchesbis2.get(key1).getSimpleCompartmentLabel();
				 Compartment left =  branchesbis2.get(key2);
				 
				//SAME TYPE
				 //TODO boolean havesame..
				 if(isSameType(right, left)) { 
					 
					 if(haveSameParent(right, left) && haveSameChildren(right, left) & isMap(right.getPhysicalCompartments(), left.getPhysicalCompartments(), resultmatch)) //  SAME PARENT & SAME CHILDREN
							 System.out.println("EXACT MATCH " + key1 + " --> " + key2 );
					//TODO CHECK LABEL
					 else if(haveSameParent(right, left) && !haveSameChildren(right, left)) //SAME PARENT & DIFFERENT CHILDREN 
						 	displaysPCAnalysis(right.getPhysicalCompartments(), left.getPhysicalCompartments(), resultmatch);
						 
					 else if(!haveSameParent(right, left) && haveSameChildren(right, left)) //DIFFERENT PARENT & SAME CHILDREN
						 	System.out.println("MOVE MATCH " + key1 + " --> " + key2 );
					 else if (right.eContainer().eContainer() instanceof Epidemic &&  left.eContainer().eContainer() instanceof Epidemic) // CASE THE PARENT ARE THE EPIDEMIC OBJECT
						 	System.out.println(" MATCH " + key1 + " --> " + key2 );
					 else {															//		DIFFERENT PARENT & DIFFERENT CHILDREN
						 	System.out.println("ADD/DELETE MATCH " + key1 + " --> " + key2 );
					 }
				 }
				 
				//DIFFERENT TYPE
				 
				 else{							
					 if(haveSameParent(right, left) && haveSameChildren(right, left)) //  SAME PARENT & SAME CHILDREN
						 System.out.println("RETYPE MATCH " + key1 +" Of type  : " + right.getClass() + " --> " + key2 + " Of type : " + left.getClass());
					 
					 else if(haveSameParent(right, left) && !haveSameChildren(right, left)) //SAME PARENT & DIFFERENT CHILDREN 
						 displaysPCAnalysis(right.getPhysicalCompartments(), left.getPhysicalCompartments(), resultmatch);
					 
					 else if(!haveSameParent(right, left) && haveSameChildren(right, left)) //DIFFERENT PARENT match& SAME CHILDREN
						 System.out.println("RETYPE/MOVE MATCH " + key1 +" Of type  : " + right.getClass() + " --> " + key2 + " Of type : " + left.getClass());
					 
					 																		
					 else if(!haveSameParent(right, left) && !haveSameChildren(right, left)) {//DIFFERENT PARENT & DIFFERENT CHILDREN
						 //System.out.println("RIGHT " + right.getParent() + "LEFT " + left.getParent() + "RIGHT " + right.getChildren() + "LEFT " + left.getChildren());
						 System.out.println("ADD/DELETE MATCH " + key1 + " --> " + key2 );
					 }
				 } 
			 }
			 
			// NO OBJECT WITH THE SAME ID
			 else if(!branchesbis2.containsKey(key1)){
				 System.out.println("DELETE  " + key1 );
			 }
 		}	
	 }	

		
		
	private static List<PhysicalCompartment> getInterList(List<PhysicalCompartment> a, List<PhysicalCompartment> b){
		
		
		List<PhysicalCompartment> c = a;
		c.retainAll(b);
		return c;
		
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
	
	
	private static boolean isSpecification (List <PhysicalCompartment> right, List<PhysicalCompartment> left, Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch) {
		
		List<PhysicalCompartment> intersection = getInterList(right, left);
		List<PhysicalCompartment> match = resultmatch.get(intersection);
		
		return right.size()<match.size();
	
	}
	
	private static boolean isGeneralization(List <PhysicalCompartment> right, List<PhysicalCompartment> left,  Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch ) {
		// checker l'intersection entre les deux compt -> checker le nb  de pc qui contiennent l'intersection des deux pc 
		
		List<PhysicalCompartment> intersection = getInterList(right, left);
		List<PhysicalCompartment> match = resultmatch.get(intersection);
		
		return right.size()>match.size();
	
	}
	private static boolean isMap (List <PhysicalCompartment> right, List<PhysicalCompartment> left,  Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch ) {
		// checker l'intersection entre les deux compt -> checker le nb  de pc qui contiennent l'intersection des deux pc 
		
		List<PhysicalCompartment> intersection = getInterList(right, left);
		List<PhysicalCompartment> match = resultmatch.get(intersection);
		
		return right.size()==match.size();
	
	}
	private static void displaysPCAnalysis(List <PhysicalCompartment> right, List<PhysicalCompartment> left,  Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch ) {
		
		if(isSpecification(right, left, resultmatch)) {
			System.out.print("SPECIFICATION MATCH  : ");
			displayListPC(right); 
			System.out.print( " ---> ");
			displayListPC(left);
		}
		else if(isGeneralization(right, left, resultmatch))
			System.out.println("GENERALIZATION MATCH  : " + right + " ---> " + left);
	
		else if(isMap(right, left, resultmatch))
			System.out.println("MAP MATCH  : " + right + " ---> " + left);
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
