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
		
		epimodel.EpidemicWrapper myEpi1 = loadEpimodel(model1);
		epimodel.EpidemicWrapper myEpi2 = loadEpimodel(model2);
		
        Map<List<String>, Compartment> branchesbis1 = myEpi1.getEpidemic().getModelTree();
        Map<List<String>, Compartment> branchesbis2 = myEpi2.getEpidemic().getModelTree();
       
        
        myEpi1.getEpidemic().printModelTree();		
        myEpi2.getEpidemic().printModelTree();
        
        System.out.println("BEGINING OF DIFF \n");	
        displayDiff(branchesbis1, branchesbis2);
        System.out.println(" \nEND");
		
		
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
		 Map<List<String>, Compartment> toBeAdd1 = new LinkedHashMap<>();
		 Map<List<String>, Compartment> toBeAdd = new LinkedHashMap<>();
		 List<String> group = new ArrayList<>();
		 group.add("group");
		 toBeAdd1.putAll(branchesbis1);
		 toBeAdd.putAll(branchesbis2);
		 for (List<String> key1 : branchesbis1.keySet()) {
			/* RENAMING GROUP AND SEIR 
			 if(key1.contains("SEIR") && branchesbis2.containsKey(group)) {
				 branchesbis2.replace(group, branchesbis1.get(key1));
				 branchesbis2.put(key1, branchesbis2.remove(group));
				 toBeAdd.remove(group);
			 }
			 */
			 if( branchesbis2.containsKey(key1)){  //OBJECT WITH THE SAME ID
				 toBeAdd.remove(key1);
				 toBeAdd1.remove(key1);
				 Compartment right = branchesbis1.get(key1);
				 List<String> key2 = branchesbis2.get(key1).getLabel();
				 Compartment left =  branchesbis2.get(key2);
				
				 boolean haveSameParent = haveSameParent(right, left);
				 boolean haveSameChildren = haveSameChildren(right, left);

				//SAME TYPE
					 
				 if(isSameType(right, left)) { 
				
					 if (right.eContainer().eContainer().eClass() instanceof Epidemic &&  left.eContainer().eContainer().eClass() instanceof Epidemic) // CASE THE PARENT ARE THE EPIDEMIC OBJECT
						 	System.out.println("EXACT MATCH " + key1 + " --> " + key2 ); 
					 else if(haveSameParent && haveSameChildren && isMap(right.getPhysicalCompartments(), left.getPhysicalCompartments(), toBeAdd, toBeAdd1)) //  SAME PARENT & SAME CHILDREN
							 System.out.println("EXACT MATCH " + key1 + " --> " + key2 );
					
					 else if(haveSameParent && !haveSameChildren) //SAME PARENT & DIFFERENT CHILDREN 
						 	displaysPCAnalysis(right.getPhysicalCompartments(), left.getPhysicalCompartments(), toBeAdd,toBeAdd1);
						 
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
					 
					 else if(haveSameParent && !haveSameChildren) { //SAME PARENT & DIFFERENT CHILDREN 
						 System.out.println("RETYPE MATCH " + key1 +" Of type  : " + right.getClass() + " --> " + key2 + " Of type : " + left.getClass());
						 displaysPCAnalysis(right.getPhysicalCompartments(), left.getPhysicalCompartments(), toBeAdd, toBeAdd1);
					 }
					 else if(!haveSameParent && haveSameChildren) //DIFFERENT PARENT match& SAME CHILDREN
						 System.out.println("RETYPE/MOVE MATCH " + key1 +" Of type  : " + right.getClass() + " --> " + key2 + " Of type : " + left.getClass());
					 
					 																		
					 else if(!haveSameParent && !haveSameChildren) {//DIFFERENT PARENT & DIFFERENT CHILDREN
						 //System.out.println("RIGHT " + right.getParent() + "LEFT " + left.getParent() + "RIGHT " + right.getChildren() + "LEFT " + left.getChildren());
						 System.out.println("ADD/DELETE MATCH " + key1 + " --> " + key2 );
					 }
				 } 
			 }
			 //CHECKING IF A MAPPING EXISTS
			 else if(!containsMap(branchesbis2,key1).isEmpty()) {
				 	List<List<String>> resultMap = containsMap(branchesbis2,key1);
				 	Compartment right = branchesbis1.get(key1);
					System.out.println("MAP MATCH  : " + resultMap.get(0) + " ---> " + resultMap.get(1));
					toBeAdd.remove(resultMap.get(0));
					toBeAdd.remove(resultMap.get(1));
			 }
			// NO OBJECT WITH THE SAME ID AND NO MAPPING
			 
			 else if(!branchesbis2.containsKey(key1)){
				 System.out.println("DELETE  " + key1 );
			 }
 		}
		 // DELETING OBJECTS WHICH HAVE NO MATCH
		 for(List<String> key : toBeAdd1.keySet()) {
				System.out.println("DELETE  " + toBeAdd1.get(key).getLabel());
			}
		 // ADDING OBJECTS WHICH HAVE NO MATCH
		for(List<String> key : toBeAdd.keySet()) {
			System.out.println("ADD  " + toBeAdd.get(key).getLabel());
		}
	 }	

	
	
	
	
	/*
	 Checks if a map exists between two compartments
	 */
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
	
	/*
	 Returns a list of strings representing shared elements between two lists of PhysicalCompartment
	 */
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
	
	/*
	 Returns à list of string representing shared elements between two PhysicalCompartments
	 */
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

	/*
	 Indicates if two compartments have the same parent 
	 */
	private static boolean haveSameParent (Compartment right, Compartment left) {
		try {
			return right.getParent().getLabel().equals(left.getParent().getLabel());
		}catch (Exception e) {
			return false;// TODO: handle exception
		}
	}
	
	
	 /*
	 Indicates if two compartments have the same children 
	 */
	 
	private static boolean haveSameChildren (Compartment right, Compartment left) {
		try {
			return right.getChildren().equals(left.getChildren());
		}catch (Exception e) {
			return false;
		}
	}
	
	/*
	 Indicates if there is a specification between two Physical Compartments 
	 */
	private static boolean isSpecification (List <PhysicalCompartment> right, List<PhysicalCompartment> left, Map<List<String>, Compartment> toBeAdd, Map<List<String>, Compartment> toBeAdd1) {
		
		 
		 
		List<String> intersection = getInterList(right, left);
		if(intersection.isEmpty())
			return false;
		List<PhysicalCompartment> match = contains(left, intersection.get(0));
	
		return right.size()<match.size();
	
	}

	/*
	 Indicates if there is a Generalization between two Physical Compartments 
	 */
	private static boolean isGeneralization(List <PhysicalCompartment> right, List<PhysicalCompartment> left,  Map<List<String>, Compartment> toBeAdd, Map<List<String>, Compartment> toBeAdd1 ) {
		
		List<String> intersection = getInterList(right, left);
		if(intersection.isEmpty())
			return false;
		List<PhysicalCompartment> match = contains(left, intersection.get(0));
		
		return right.size()>match.size();
	
	}

	/*
	 Indicates if there is a Mapping between two Physical Compartments 
	 */
	private static boolean isMap (List <PhysicalCompartment> right, List<PhysicalCompartment> left,  Map<List<String>, Compartment> toBeAdd, Map<List<String>, Compartment> toBeAdd1 ) {
		
		List<String> intersection = getInterList(right, left);
		if(intersection.isEmpty())
			return false;
		List<PhysicalCompartment> match = contains(left, intersection.get(0));
		
		return right.size()==match.size();
	
	}

	/*
	 Displays the PhysicalCompartment diff . 
	 */
	private static void displaysPCAnalysis(List <PhysicalCompartment> right, List<PhysicalCompartment> left,  Map<List<String>, Compartment> toBeAdd, Map<List<String>, Compartment> toBeAdd1 ) {
		
		if(isSpecification(right, left, toBeAdd,toBeAdd1)) {
			System.out.print("SPECIFICATION MATCH  : ");
			displayListPC(right,toBeAdd,toBeAdd1); 
			System.out.print( " ---> ");
			displayListPC(left, toBeAdd,toBeAdd1);
		}
		else if(isGeneralization(right, left, toBeAdd,toBeAdd1))
			System.out.println("GENERALIZATION MATCH  : " + right + " ---> " + left);
	
		else if(isMap(right, left, toBeAdd,toBeAdd1))
			System.out.println("MAP MATCH  : " + right + " ---> " + left);
	}


	/*
	Returns a List PhysicalComaprtments that contains objects with a specific label 
	 */
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

	/*
	 Indicates if a PhysicalCompartment with a specific label is already contained in a given List
	 */
	private static boolean containsDuplicatedPc(List<PhysicalCompartment> pcs, PhysicalCompartment p) {
		for(PhysicalCompartment pc : pcs) {
				if(pc.labels.equals(p.labels))
					return true;
		}
		
		return false;
	}
	/*
	 Displays the labels of all objects of all PhysicalCompartmens of a list.
	 And remove each object from a Map of elements representing elements to be treated in diff
	 */
	private static void displayListPC(List <PhysicalCompartment> pc, Map<List<String>, Compartment> toBeAdd, Map<List<String>, Compartment> toBeAdd1 ) {
		List<String> tmp = new ArrayList<>();
		for (PhysicalCompartment pci : pc) {
			
			System.out.print( pci.labels);
			for(String label_pc : pci.labels) {
				tmp.clear();
				tmp.add(label_pc);
				toBeAdd.remove(tmp);
				toBeAdd1.remove(tmp);
			}
		
		
			
			
		}
		System.out.print("\n");
		
	}
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
	
	static boolean containsAll(PhysicalCompartment pc1, PhysicalCompartment pc2) {
		
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
	
	protected static Map<PhysicalCompartment, List<PhysicalCompartment>> matchTwoEpimodels(List<PhysicalCompartment> cs1 , List<PhysicalCompartment> cs2) {
		
		 Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch = new LinkedHashMap<>();
		 
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
	        
	      //  printResultMatch(resultmatch);
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



