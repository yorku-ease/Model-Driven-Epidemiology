import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import EpidemicRoot.Contact;
import EpidemicRoot.Epidemic;
import EpidemicRoot.EpidemicRootFactory;
import EpidemicRoot.Group;
import EpidemicRoot.Product;
import EpidemicRoot.Rate;
import EpidemicRoot.UnitCompartment;

public class TB {
	public static void main(String[] args) {
		System.out.println("Start ............. !!!!!!!");
		EpidemicRootFactory factory = EpidemicRootFactory.eINSTANCE;
		
		Epidemic TB_epidemic = factory.createEpidemic();
		
		// SEIR unit compartments
		UnitCompartment S = factory.createUnitCompartment();
		S.setLabel("S");
		
		UnitCompartment E = factory.createUnitCompartment();
		E.setLabel("E");
		
		UnitCompartment I = factory.createUnitCompartment();
		I.setLabel("I");
		
		UnitCompartment R = factory.createUnitCompartment();
		R.setLabel("R");
		
		// A group to contain SEIR compartments
		Group seir_compartments_gp = factory.createGroup();
		seir_compartments_gp.setLabel("TB_SEIR");
		seir_compartments_gp.getCompartments().add(S);
		seir_compartments_gp.getCompartments().add(E);
		seir_compartments_gp.getCompartments().add(I);
		seir_compartments_gp.getCompartments().add(R);
		
		
		// Define a contact flow
		Contact contact = factory.createContact();
		contact.setFrom(S);
		contact.setTo(E);
		contact.setContact(I);
		contact.setSourceParameters("An example of what I can put here?");
		contact.setContactParameters("An example of what I can put here?");
		
		// Define a rate flow between Exposed and Infectious
		Rate rate_EI = factory.createRate();
		rate_EI.setFrom(E);
		rate_EI.setTo(I);
		rate_EI.setSourceParameters("An example of what I can put here?");
		
		// Define a rate flow between Infectious and Recovered
		Rate rate_IR = factory.createRate();
		rate_IR.setFrom(I);
		rate_IR.setTo(R);
		rate_IR.setSourceParameters("An example of what I can put here?");
		
		
		// Define a gender group and its compartments
		UnitCompartment female = factory.createUnitCompartment();
		female.setLabel("F");
		
		UnitCompartment male = factory.createUnitCompartment();
		male.setLabel("M");
		
		Group gender_group = factory.createGroup();
		gender_group.setLabel("gender");
		gender_group.getCompartments().add(female);
		gender_group.getCompartments().add(male);
		
		
		// Define a main product to mix everything
		Product main_product = factory.createProduct();
		main_product.setLabel("TB_p");
		main_product.getCompartments().add(seir_compartments_gp);
		main_product.getCompartments().add(gender_group);
		

		// Set the main product as the main compartment of this epidemiology
		TB_epidemic.setCompartment(main_product);
		
		
		/* create resource */
		// Create a resource set.
		ResourceSet resourceSet = new ResourceSetImpl();
		// Register the default resource factory -- only needed for stand-alone!
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
		Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

		// Get the URI of the model file.
		URI fileURI = URI.createFileURI(new File("tb_sample_model.xmi").getAbsolutePath());

		// Create a resource for this file.
		Resource resource = resourceSet.createResource(fileURI);

		resource.getContents().add(TB_epidemic);
		// Save the contents of the resource to the file system.
		try
		{
		    resource.save(Collections.EMPTY_MAP);
		}
		catch (IOException e) {}


		
		
	}
}
