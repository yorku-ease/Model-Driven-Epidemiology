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
public class Diff {

	public static void main(String[] args) throws Exception {
	
	
		
		
	}
	
	
	public static void Diff(Map<PhysicalCompartment, List<PhysicalCompartment>> resultmatch) {
		
		 
        	 
        		
        		 
 
		
		
	}
	

	private static boolean isExactMatch (PhysicalCompartment key,PhysicalCompartment match) {
		
		return key.equals(match);
	}
	
	private static boolean isMatchSpecification (PhysicalCompartment key, PhysicalCompartment match) {
		
		return key.labels.containsAll(match.labels);
	
	}
	
	private static boolean isMatchRegroupement (PhysicalCompartment key, PhysicalCompartment match) {
		
		return match.labels.containsAll(key.labels);
	
	}
	
	private static boolean isMatchRetype (PhysicalCompartment key, PhysicalCompartment match) {
	
		//TODO
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
