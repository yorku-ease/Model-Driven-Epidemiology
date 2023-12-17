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
import EpidemicRoot.EpidemicRootFactory;
import EpidemicRoot.EpidemicRootPackage;
import EpidemicRoot.Flow;
import EpidemicRoot.Group;
import EpidemicRoot.Product;
import EpidemicRoot.UnitCompartment;
import EpidemicRoot.impl.EpidemicImpl;
import src_ph.PhysicalEpidemicRoot.Label;
import src_ph.PhysicalEpidemicRoot.PhysicalCompartment;
import src_ph.PhysicalEpidemicRoot.PhysicalEpidemicRootFactory;
import src_ph.PhysicalEpidemicRoot.PhysicalFlow;
import src_ph.PhysicalEpidemicRoot.PhysicalEpidemic;

public class EpidemicToPhysicalEpidemic {
	
	public static ArrayList<PhysicalFlow> physicalFlows = new ArrayList<>();
	
	public static void main(String[] args) {
		// Read the input file
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		EpidemicRootPackage epidemicPackage = EpidemicRootPackage.eINSTANCE;
		URI fileURI = URI.createFileURI(new File("hiv2.xmi").getAbsolutePath());
		Resource in_resource = resourceSet.getResource(fileURI, true);
		EpidemicImpl in_root = (EpidemicImpl)in_resource.getContents().get(0);
		AbstractCompartment in_abstract_compartment = in_root.getCompartment();


		System.out.println("input abs compartment:"+in_abstract_compartment+ "the type:"+in_abstract_compartment.getClass().getName());



		//Create physical compartment for the output
		PhysicalEpidemicRootFactory ph_factory = PhysicalEpidemicRootFactory.eINSTANCE;
		PhysicalEpidemic physicalEpidemic = ph_factory.createPhysicalEpidemic();

		// create an array list of physical compartments
		ArrayList<PhysicalCompartment> physicalCompartments = new ArrayList<PhysicalCompartment>();

		// Create the physical compartments
		createPhysicalCompartments(physicalCompartments,in_abstract_compartment, null);
		
		//print the physical flows
		for (PhysicalFlow f : physicalFlows) {
			System.out.println("______________________");
			System.out.println("flow:"+f);
			System.out.println("from :"+f.getFrom().getLabels());
			System.out.println("from :"+f.getTo().getLabels());
		}
			

		// Add the physical compartments to the physical epidemic root
		physicalEpidemic.getCompartments().addAll(physicalCompartments);
		
		//Add the constructed physical flows
		physicalEpidemic.getFlows().addAll(physicalFlows);



		//Write the physical model to a file
		fileURI = URI.createFileURI(new File("physical2.xmi").getAbsolutePath());
		Resource resource = resourceSet.createResource(fileURI);
		resource.getContents().add(physicalEpidemic);
		System.out.println("Successfull!!");

		try {
			resource.save(Collections.EMPTY_MAP);
		}
		catch (IOException e) {}
	}

	public static void createPhysicalCompartments(ArrayList<PhysicalCompartment> physicalCompartmentsList, AbstractCompartment compartment, ArrayList<Label> pre_labels) {
		PhysicalEpidemicRootFactory physicalFactory = PhysicalEpidemicRootFactory.eINSTANCE;

		if (pre_labels == null) {
			pre_labels = new ArrayList<Label>();
		}

		String label = compartment.getLabel();
		if (compartment instanceof UnitCompartment) {
			//			System.out.println("UNIT!!! label: "+label);
			//			System.out.println("physical compartments list so far:"+physicalCompartmentsList);
			//			System.out.println("labels so far:"+pre_labels);
			PhysicalCompartment ph_comp = physicalFactory.createPhysicalCompartment();
			// Add it's label to the list
			Label compartmentLabel = physicalFactory.createLabel();
			compartmentLabel.setName(label);
			pre_labels.add(compartmentLabel);
			ph_comp.getLabels().addAll(pre_labels);
			physicalCompartmentsList.add(ph_comp);
		}

		else if (compartment instanceof Group) {
			//			System.out.println("it is group, the stuff:"+ "physical compartments list so far:"+physicalCompartmentsList);
			//			System.out.println("labels so far:"+pre_labels);
			// Add it's label to the list
			Label compartmentLabel = physicalFactory.createLabel();
			compartmentLabel.setName(label);
			pre_labels.add(compartmentLabel);

			EList<AbstractCompartment> children = ((Group) compartment).getCompartments();
			for(AbstractCompartment childCompartment:children ) {
				createPhysicalCompartments(physicalCompartmentsList,childCompartment,deepCloneLabels(pre_labels));
			}
			
			if (((Group) compartment).getFlows().size() > 0) {
				
				for (Flow flow:((Group) compartment).getFlows() ) {
					ArrayList<PhysicalCompartment> fromCompartments = new ArrayList<>();
					ArrayList<PhysicalCompartment> toCompartments = new ArrayList<>();
					createPhysicalCompartments(fromCompartments,flow.getFrom(),deepCloneLabels(pre_labels));
					createPhysicalCompartments(toCompartments,flow.getTo(),deepCloneLabels(pre_labels));
					for (PhysicalCompartment from :fromCompartments ) {
						for (PhysicalCompartment to: toCompartments) {
							PhysicalFlow physicalFlow = physicalFactory.createPhysicalFlow();
							physicalFlow.setId(flow.getId());
							physicalFlow.setFrom(from);
							physicalFlow.setTo(to);
							physicalFlows.add(physicalFlow);
						}
					}
				}
			}
			

		}

		else if (compartment instanceof Product) {
			ArrayList<PhysicalCompartment> productedCompartments = handleProducts((Product)compartment);
			physicalCompartmentsList.addAll(productedCompartments);
		}

	}

	static ArrayList<PhysicalCompartment> handleProducts(Product product) {
		EpidemicRootFactory factory = EpidemicRootFactory.eINSTANCE;
		PhysicalEpidemicRootFactory ph_factory = PhysicalEpidemicRootFactory.eINSTANCE;
		int numberOfChildren = product.getCompartments().size();

		ArrayList<PhysicalCompartment> productedCompartments = new ArrayList<PhysicalCompartment>();

		if (numberOfChildren == 2) {

			ArrayList<PhysicalCompartment> physicalCompartments1 = new ArrayList<PhysicalCompartment>();
			ArrayList<PhysicalCompartment> physicalCompartments2 = new ArrayList<PhysicalCompartment>();
			createPhysicalCompartments( physicalCompartments1,product.getCompartments().get(0), null);
			createPhysicalCompartments(physicalCompartments2,product.getCompartments().get(1), null);

			for (PhysicalCompartment level1: physicalCompartments1) {
				for(PhysicalCompartment level2: physicalCompartments2) {

					PhysicalCompartment producted = ph_factory.createPhysicalCompartment();

					ArrayList<Label> combinedLabels = combineLabels(level1.getLabels(),level2.getLabels());

					//					System.out.println("The combined labels ----> "+combinedLabels );
					producted.getLabels().addAll(combinedLabels);
					productedCompartments.add(producted);
				}
			}


		}
		else if (numberOfChildren >2) {
			Product subProduct1 = factory.createProduct();
			// first do a product on children indexed 0..n-1
			subProduct1.getCompartments().addAll(product.getCompartments().subList(0, numberOfChildren-1));
			//Handle products for  0..n-1 using this same function (recursion)
			ArrayList<PhysicalCompartment> physicalCompartments1 = handleProducts(subProduct1);
			// Create physical compartments from the last child
			ArrayList<PhysicalCompartment> physicalCompartments2= new ArrayList<PhysicalCompartment>();
			createPhysicalCompartments( physicalCompartments2,product.getCompartments().get(product.getCompartments().size()-1), null);

			// We have 2 sets of physical compartments, between which we should do a product, so:

			for (PhysicalCompartment level1: physicalCompartments1) {
				for(PhysicalCompartment level2: physicalCompartments2) {

					PhysicalCompartment producted = ph_factory.createPhysicalCompartment();

					ArrayList<Label> combinedLabels = combineLabels(level1.getLabels(),level2.getLabels());

					//					System.out.println("The combined labels (if >2 ) ----> "+combinedLabels );
					producted.getLabels().addAll(combinedLabels);
					productedCompartments.add(producted);
				}
			}

		}
		return productedCompartments;
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

	public static ArrayList<Label> combineLabels(EList<Label> labels1, EList<Label> labels2) {
		PhysicalEpidemicRootFactory ph_factory = PhysicalEpidemicRootFactory.eINSTANCE;
		ArrayList<Label> result = new ArrayList<Label>();
		for (Label label : labels1) {
			Label newLabel = ph_factory.createLabel();
			newLabel.setName(label.getName());
			result.add(newLabel);
		}

		for (Label label : labels2) {
			Label newLabel = ph_factory.createLabel();
			newLabel.setName(label.getName());
			result.add(newLabel);
		}

		return result;

	}


	
}
