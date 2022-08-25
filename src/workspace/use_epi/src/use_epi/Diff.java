package use_epi;

import java.io.PrintWriter;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
		epimodel.EpidemicWrapper myEpi2 = Match.loadEpimodel(model9);
		

		//System.out.println(myEpi1.getEpidemic().getPhysicalCompartments().get(0).labels);
		
	    
       // Map<String, List<Compartment>> branches1 = myEpi1.getEpidemic().getAllCompartmentBranches();
       // Map<String, List<Compartment>> branches2 = myEpi2.getEpidemic().getAllCompartmentBranches();
        
        Map<List<String>, Compartment> branchesbis1 = myEpi1.getEpidemic().getModelTree();
        Map<List<String>, Compartment> branchesbis2 = myEpi2.getEpidemic().getModelTree();
       
        List<String> Test = new ArrayList<>(); 
        List<String> Test2 = new ArrayList<>(); 
        

        List<PhysicalCompartment> pc1 = myEpi1.getEpidemic().getPhysicalCompartments();
        List<PhysicalCompartment> pc2 = myEpi2.getEpidemic().getPhysicalCompartments();
       
        Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch = Match.matchTwoEpimodels(pc1, pc2);
        myEpi1.getEpidemic().printModelTree();
          
        myEpi2.getEpidemic().printModelTree();
        
        System.out.println("BEGINING OF DIFF");	
        displayDiff(branchesbis1, branchesbis2);
        System.out.println(" END");
		
		
	}
	
	
	public static void displayDiff( Map<List<String>, Compartment> branchesbis1,  Map<List<String>, Compartment> branchesbis2) {
		
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

		 Map<List<String>, Compartment> toBeAdd = new LinkedHashMap<>();
		 List<String> group = new ArrayList<>();
		 group.add("group");
		 toBeAdd.putAll(branchesbis2);
		 for (List<String> key1 : branchesbis1.keySet()) {
			
			 if(key1.contains("SEIR") && branchesbis2.containsKey(group)) {
				 branchesbis2.replace(group, branchesbis1.get(key1));
				 branchesbis2.put(key1, branchesbis2.remove(group));
			 }
			 if( branchesbis2.containsKey(key1)){  //OBJECT WITH THE SAME ID
				 toBeAdd.remove(key1);
				 Compartment right = branchesbis1.get(key1);
				 List<String> key2 = branchesbis2.get(key1).getLabel();
				 Compartment left =  branchesbis2.get(key2);
				
				//SAME TYPE
				 
				 boolean haveSameParent = haveSameParent(right, left);
				 boolean haveSameChildren = haveSameChildren(right, left);
				 
				 if(isSameType(right, left)) { 
					 
					 if (right.eContainer().eContainer() instanceof Epidemic &&  left.eContainer().eContainer() instanceof Epidemic) // CASE THE PARENT ARE THE EPIDEMIC OBJECT
						 	System.out.println(" MATCH " + key1 + " --> " + key2 );
					 else if(haveSameParent && haveSameChildren && isMap(right.getPhysicalCompartments(), left.getPhysicalCompartments(), toBeAdd)) //  SAME PARENT & SAME CHILDREN
							 System.out.println("EXACT MATCH " + key1 + " --> " + key2 );
					//TODO CHECK LABEL
					 else if(haveSameParent && !haveSameChildren) //SAME PARENT & DIFFERENT CHILDREN 
						 	displaysPCAnalysis(right.getPhysicalCompartments(), left.getPhysicalCompartments(), toBeAdd);
						 
					 else if(!haveSameParent && haveSameChildren) //DIFFERENT PARENT & SAME CHILDREN
						 	System.out.println("MOVE MATCH " + key1 + " --> " + key2 );
					 else {															//		DIFFERENT PARENT & DIFFERENT CHILDREN
						 	System.out.println("ADD/DELETE MATCH " + key1 + " --> " + key2 );
					 }
				 }
				 
				//DIFFERENT TYPE
				 
				 else{							
					 if(haveSameParent && haveSameChildren) //  SAME PARENT & SAME CHILDREN
						 System.out.println("RETYPE MATCH " + key1 +" Of type  : " + right.getClass() + " --> " + key2 + " Of type : " + left.getClass());
					 
					 else if(haveSameParent && !haveSameChildren) //SAME PARENT & DIFFERENT CHILDREN 
						 displaysPCAnalysis(right.getPhysicalCompartments(), left.getPhysicalCompartments(), toBeAdd);
					 
					 else if(!haveSameParent && haveSameChildren) //DIFFERENT PARENT match& SAME CHILDREN
						 System.out.println("RETYPE/MOVE MATCH " + key1 +" Of type  : " + right.getClass() + " --> " + key2 + " Of type : " + left.getClass());
					 
					 																		
					 else if(!haveSameParent && !haveSameChildren) {//DIFFERENT PARENT & DIFFERENT CHILDREN
						 //System.out.println("RIGHT " + right.getParent() + "LEFT " + left.getParent() + "RIGHT " + right.getChildren() + "LEFT " + left.getChildren());
						 System.out.println("ADD/DELETE MATCH " + key1 + " --> " + key2 );
					 }
				 } 
			 }
			 
			 else if(!containsMap(branchesbis2,key1).isEmpty()) {
				 	List<List<String>> resultMap = containsMap(branchesbis2,key1);
				 	Compartment right = branchesbis1.get(key1);
					System.out.println("MAP MATCH  : " + resultMap.get(0) + " ---> " + resultMap.get(1));
					toBeAdd.remove(resultMap.get(0));
					toBeAdd.remove(resultMap.get(1));
			 }
			// NO OBJECT WITH THE SAME ID
			 
			 else if(!branchesbis2.containsKey(key1)){
				 System.out.println("DELETE  " + key1 );
			 }
 		}
		for(List<String> key : toBeAdd.keySet()) {
			System.out.println("ADD  " + toBeAdd.get(key).getLabel());
		}
	 }	

	
	private static List<List<String>> containsMap (Map<List<String>, Compartment> branch2, List<String> key1) {
		List<List<String>> result = new ArrayList<>();
		for(List<String> key2 : branch2.keySet()) {
			
			for(String label2 : key2) {
				for(String label1 : key1) {
					if(label2.equals(label1)) {
						result.add(key1);
						result.add(key2);
					}
				}
			}
		}
		return result;
		
	}
		
	private static List<String> getInterList(List<PhysicalCompartment> a, List<PhysicalCompartment> b){
		
		List<String> e = new ArrayList<>();
		for (int i = 0; i < a.size(); ++i ){  
			 for (int j = 0; j < b.size(); j++ ){
				 for(String s : getInterPC(a.get(i), b.get(j))) {
					 if(!e.contains(s))
						 e.add(s);
				 }
					
			 }	
			 
		
	 	}

		return e;
		
	}
	private static List<String> getInterPC(PhysicalCompartment a, PhysicalCompartment b){
		
		List<String> c = new ArrayList<>();

		for(String label_a : a.labels) {
			for (String label_b : b.labels) {
				if (label_a.equals(label_b) && !c.contains(label_a))
					c.add(label_a);
			}
		}
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
	
	
	private static boolean isSpecification (List <PhysicalCompartment> right, List<PhysicalCompartment> left, Map<List<String>, Compartment> toBeAdd) {
		
		 
		 
		List<String> intersection = getInterList(right, left);
		//System.out.println(intersection);
		if(intersection.isEmpty())
			return false;
		List<PhysicalCompartment> match = contains(left, intersection.get(0));
	
		return right.size()<match.size();
	
	}
	
	private static boolean isGeneralization(List <PhysicalCompartment> right, List<PhysicalCompartment> left,  Map<List<String>, Compartment> toBeAdd ) {
		// checker l'intersection entre les deux compt -> checker le nb  de pc qui contiennent l'intersection des deux pc 
		
		List<String> intersection = getInterList(right, left);
		if(intersection.isEmpty())
			return false;
		List<PhysicalCompartment> match = contains(left, intersection.get(0));
		
		return right.size()>match.size();
	
	}
	private static boolean isMap (List <PhysicalCompartment> right, List<PhysicalCompartment> left,  Map<List<String>, Compartment> toBeAdd ) {
		// checker l'intersection entre les deux compt -> checker le nb  de pc qui contiennent l'intersection des deux pc 
		
		List<String> intersection = getInterList(right, left);
		if(intersection.isEmpty())
			return false;
		List<PhysicalCompartment> match = contains(left, intersection.get(0));
		
		return right.size()==match.size();
	
	}
	private static void displaysPCAnalysis(List <PhysicalCompartment> right, List<PhysicalCompartment> left,  Map<List<String>, Compartment> toBeAdd ) {
		
		if(isSpecification(right, left, toBeAdd)) {
			System.out.print("SPECIFICATION MATCH  : ");
			displayListPC(right,toBeAdd); 
			System.out.print( " ---> ");
			displayListPC(left, toBeAdd);
		}
		else if(isGeneralization(right, left, toBeAdd))
			System.out.println("GENERALIZATION MATCH  : " + right + " ---> " + left);
	
		else if(isMap(right, left, toBeAdd))
			System.out.println("MAP MATCH  : " + right + " ---> " + left);
	}

	private static List<PhysicalCompartment> contains (List<PhysicalCompartment> pcs, String label){
		
		List<PhysicalCompartment> result = new ArrayList<>();
		for(PhysicalCompartment pc : pcs) {
			for(String label_pc : pc.labels) {
				
				if(label_pc.equals(label) && !containsDuplicatedPc(result, pc)) {
					result.add(pc);
				}
			}

		}
		return result;
	}
	private static boolean containsDuplicatedPc(List<PhysicalCompartment> pcs, PhysicalCompartment p) {
		for(PhysicalCompartment pc : pcs) {
				if(pc.labels.equals(p.labels))
					return true;
		}
		
		return false;
	}
	private static void displayListPC(List <PhysicalCompartment> pc, Map<List<String>, Compartment> toBeAdd) {
		List<String> tmp = new ArrayList<>();
		for (PhysicalCompartment pci : pc) {
			
			System.out.print( pci.labels);
			for(String label_pc : pci.labels) {
				tmp.clear();
				tmp.add(label_pc);
				toBeAdd.remove(tmp);
			}
		
		
			
			
		}
		System.out.print("\n");
		
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
