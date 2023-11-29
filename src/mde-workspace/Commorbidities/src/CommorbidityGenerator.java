import org.eclipse.emf.ecore.resource.ResourceSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import EpidemicRoot.AbstractCompartment;
import EpidemicRoot.Epidemic;
import EpidemicRoot.EpidemicRootFactory;
import EpidemicRoot.EpidemicRootPackage;
import EpidemicRoot.Product;
import EpidemicRoot.impl.EpidemicImpl;

public class CommorbidityGenerator {

	public static void main(String[] args) {

		//// new epidemic:
		EpidemicRootFactory factory = EpidemicRootFactory.eINSTANCE;

		Epidemic commorbidity_epidemic = factory.createEpidemic();
		Product main_commorbidity_product = factory.createProduct();

		// Create a resource set.
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register the default resource factory -- only needed for stand-alone!
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

		EpidemicRootPackage epidemicPackage = EpidemicRootPackage.eINSTANCE;

		// Get the URI of the model file.
		URI fileURI = URI.createFileURI(new File("hiv_sample_model.xmi").getAbsolutePath());
		// Demand load the resource for this file.
		Resource hiv_resource = resourceSet.getResource(fileURI, true);
		
		fileURI = URI.createFileURI(new File("tb_sample_model.xmi").getAbsolutePath());
		Resource tb_resource = resourceSet.getResource(fileURI, true);

		// Print the contents of the resource to System.out.
		try
		{
			hiv_resource.save(System.out, Collections.EMPTY_MAP);
			tb_resource.save(System.out, Collections.EMPTY_MAP);

			//		     System.out.print("resource --> "+  resource.getContents().get(0).getClass());
			EpidemicImpl HIV_root = (EpidemicImpl)hiv_resource.getContents().get(0);
			AbstractCompartment HIV_compartment = HIV_root.getCompartment();
			System.out.print(HIV_compartment);
			
			EpidemicImpl TB_root = (EpidemicImpl)tb_resource.getContents().get(0);
			AbstractCompartment TB_compartment = TB_root.getCompartment();
			System.out.print(TB_compartment);
			


			// Add the products of each epidemy to the new product
			main_commorbidity_product.getCompartments().add(HIV_compartment);
			main_commorbidity_product.getCompartments().add(TB_compartment);
			commorbidity_epidemic.setCompartment(main_commorbidity_product);


			// Get the URI of the model file.
			fileURI = URI.createFileURI(new File("commorbidities.xmi").getAbsolutePath());
			Resource resource = resourceSet.createResource(fileURI);
			resource.getContents().add(commorbidity_epidemic);
			resource.save(Collections.EMPTY_MAP);

		}
		catch (IOException e) {}


	}
}
