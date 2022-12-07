package use_epi_with_all_imports;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import compartmentGroup.Group;
import compartmentGroup.GroupEpidemic;
import compartmentGroup.impl.CompartmentGroupFactoryImpl;
import dimensionEpidemic.DimensionEpidemic;
import dimensionEpidemic.Product;
import dimensionEpidemic.impl.DimensionEpidemicFactoryImpl;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Epidemic;
import epimodel.EpidemicWrapper;
import epimodel.Flow;
import epimodel.FlowWrapper;
import epimodel.impl.EpimodelFactoryImpl;

public class make_models {
	public static ResourceSet resSet = new ResourceSetImpl();
	
	public static void make(Epidemic e) throws IOException {
        URI uri = URI.createFileURI("../../test-models/" + e.getId() + ".epimodel");
        Resource resource = resSet.createResource(uri);
        resource.getContents().add(wrap(e));
        resource.save(null);
	}
	
	public static void main(String[] args) throws Exception {
		Resource.Factory.Registry factoryRegistry = new ResourceFactoryRegistryImpl();
        factoryRegistry.getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		
        resSet = new ResourceSetImpl();
        EPackage.Registry pkgRegistry = new EPackageRegistryImpl();
        resSet.setPackageRegistry(pkgRegistry);
        resSet.setResourceFactoryRegistry(factoryRegistry);

		make(groupEpi("GECC_S_I",
				compartment("S"),
				compartment("I")));
		
		make(groupEpi("GECC_SI_S_I",
				compartment("SI", "S"),
				compartment("SI", "I")));
		
		make(productEpi("DEG_SI_S_I",
				group("SI",
						compartment("S"),
						compartment("I"))));
		
		make(productEpi("DEPGG_COVID_INF_VAR_SEIR",
				group("SEIR",
						compartment("S"),
						compartment("E"),
						product("I",
								group("Variants",
										compartment("DELTA"),
										compartment("OMICRON")),
								group("Infectious",
										compartment("Asymptomatic"),
										compartment("Symptomatic"))),
						compartment("R"))));
	}
	
	static DimensionEpidemic productEpi(String id, Compartment... compartments) {
		DimensionEpidemic de = DimensionEpidemicFactoryImpl.eINSTANCE.createDimensionEpidemic();
		de.setId(id);
		for (Compartment c : compartments)
			de.getDimension().add(wrap((Compartment) c));
		return de;
	}
	
	static GroupEpidemic groupEpi(String id, Compartment... compartments) {
		GroupEpidemic ge = CompartmentGroupFactoryImpl.eINSTANCE.createGroupEpidemic();
		ge.setId(id);
		for (Compartment c : compartments)
			ge.getCompartment().add(wrap((Compartment) c));
		return ge;
	}
	
	static Product product(Object... labelsAndCompartments) {
		Product p = DimensionEpidemicFactoryImpl.eINSTANCE.createProduct();
		for (Object label : labelsAndCompartments)
			if (label instanceof String)
				p.getLabel().add((String) label);
		for (Object c : labelsAndCompartments)
			if (c instanceof Compartment)
				p.getDimensions().add(wrap((Compartment) c));
		return p;
	}
	
	static Group group(Object... labelsAndCompartments) {
		Group g = CompartmentGroupFactoryImpl.eINSTANCE.createGroup();
		for (Object label : labelsAndCompartments)
			if (label instanceof String)
				g.getLabel().add((String) label);
		for (Object c : labelsAndCompartments)
			if (c instanceof Compartment)
				g.getCompartment().add(wrap((Compartment) c));
		return g;
	}
	
	static Compartment compartment(String... labels) {
		Compartment c = EpimodelFactoryImpl.eINSTANCE.createCompartment();
		for (String label : labels)
			c.getLabel().add(label);
		return c;
	}
	
	static CompartmentWrapper wrap(Compartment c) {
		epimodel.CompartmentWrapper w = EpimodelFactoryImpl.eINSTANCE.createCompartmentWrapper();
		w.setCompartment(c);
		return w;
	}
	
	static FlowWrapper wrap(Flow f) {
		epimodel.FlowWrapper w = EpimodelFactoryImpl.eINSTANCE.createFlowWrapper();
		w.setFlow(f);
		return w;
	}
	
	static EpidemicWrapper wrap(Epidemic e) {
		epimodel.EpidemicWrapper w = EpimodelFactoryImpl.eINSTANCE.createEpidemicWrapper();
		w.setEpidemic(e);
		return w;
	}
}
