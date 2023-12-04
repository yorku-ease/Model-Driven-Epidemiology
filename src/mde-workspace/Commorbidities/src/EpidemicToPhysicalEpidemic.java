import org.eclipse.emf.ecore.resource.ResourceSet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import EpidemicRoot.AbstractCompartment;
import EpidemicRoot.EpidemicRootPackage;
import EpidemicRoot.Group;
import EpidemicRoot.UnitCompartment;
import EpidemicRoot.impl.EpidemicImpl;
import src_ph.PhysicalEpidemicRoot.Label;
import src_ph.PhysicalEpidemicRoot.PhysicalCompartment;
import src_ph.PhysicalEpidemicRoot.PhysicalEpidemicRootFactory;
import src_ph.PhysicalEpidemicRoot.PhysicalEpidemic;

public class EpidemicToPhysicalEpidemic {
	public static void main(String[] args) {
		// Read the input file
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		EpidemicRootPackage epidemicPackage = EpidemicRootPackage.eINSTANCE;
		URI fileURI = URI.createFileURI(new File("hiv_sample_model.xmi").getAbsolutePath());
		Resource in_resource = resourceSet.getResource(fileURI, true);
		EpidemicImpl in_root = (EpidemicImpl)in_resource.getContents().get(0);
		AbstractCompartment in_abstract_compartment = in_root.getCompartment();

		//		in_abstract_compartment.c

		System.out.println("input abs compartment:"+in_abstract_compartment+ "the type:"+in_abstract_compartment.getClass().getName());

		

		//Create physical compartment for the output
		PhysicalEpidemicRootFactory ph_factory = PhysicalEpidemicRootFactory.eINSTANCE;
		PhysicalEpidemic physicalEpidemic = ph_factory.createPhysicalEpidemic();

		// create an array list of physical compartments
		ArrayList<PhysicalCompartment> physicalCompartments = new ArrayList<PhysicalCompartment>();
		

		createPhysicalCompartments(ph_factory, physicalCompartments,in_abstract_compartment, null);


		physicalEpidemic.getCompartments().addAll(physicalCompartments);



		//Write the physical model to a file
		fileURI = URI.createFileURI(new File("physical244.xmi").getAbsolutePath());
		Resource resource = resourceSet.createResource(fileURI);
		resource.getContents().add(physicalEpidemic);
		System.out.println("Successfull!!");

		try {
			resource.save(Collections.EMPTY_MAP);
		}
		catch (IOException e) {}
	}

	public static void createPhysicalCompartments(PhysicalEpidemicRootFactory physicalFactory, ArrayList<PhysicalCompartment> list, AbstractCompartment compartment, ArrayList<Label> pre_labels) {
		if (pre_labels == null) {
			pre_labels = new ArrayList<Label>();
		}
		
		String label = compartment.getLabel();
		if (compartment instanceof UnitCompartment) {
			System.out.println("UNIT!!! label: "+label);
			System.out.println("physical compartments list so far:"+list);
			System.out.println("labels so far:"+pre_labels);
			PhysicalCompartment ph_comp = physicalFactory.createPhysicalCompartment();
			// Add it's label to the list
			Label compartmentLabel = physicalFactory.createLabel();
			compartmentLabel.setName(label);
			pre_labels.add(compartmentLabel);
			ph_comp.getLabels().addAll(pre_labels);
			list.add(ph_comp);
		}
		
		else if (compartment instanceof Group) {
			System.out.println("it is group, the stuff:"+ "physical compartments list so far:"+list);
			System.out.println("labels so far:"+pre_labels);
			// Add it's label to the list
			Label compartmentLabel = physicalFactory.createLabel();
			compartmentLabel.setName(label);
			pre_labels.add(compartmentLabel);
			
			EList<AbstractCompartment> children = ((Group) compartment).getCompartments();
			for(AbstractCompartment childCompartment:children ) {
				createPhysicalCompartments(physicalFactory,list,childCompartment,deepCloneLabels(pre_labels));
			}

		}

	}
	
	public static ArrayList<Label> deepCloneLabels(ArrayList<Label> labels) {
		PhysicalEpidemicRootFactory ph_factory = PhysicalEpidemicRootFactory.eINSTANCE;
		
		ArrayList<Label> copyLabels = new ArrayList<Label>();
		for (Label label : labels) {
			Label newLabel = ph_factory.createLabel();
			newLabel.setName(label.getName());
            copyLabels.add(newLabel);
        }
		
		return copyLabels;
	}
}
