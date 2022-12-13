package use_epi_with_all_imports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import batchRateContactFlow.Contact;
import batchRateContactFlow.Rate;
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
	public static ResourceSet resSet = null;
	
	public static void main(String[] args) throws Exception {
		// first model requires NO FLOWS (dependency loading hell)
		create_model_file(groupEpi("GECC_S_I",
				compartment("S"),
				compartment("I")));
		
		create_model_file(groupEpi("GECC_SI_S_I",
				compartment("SI", "S"),
				compartment("SI", "I")));
		
		create_model_file(productEpi("DEG_SI_S_I",
				group("SI",
						compartment("S"),
						compartment("I"))));
		
		create_model_file(productEpi("DEPGG_COVID_INF_VAR_SEIR",
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
		
		create_model_file(productEpi("withFlowInGroup",
				group("SEIR",
						compartment("S"),
						compartment("E"),
						product("I",
								group("Variants",
										compartment("DELTA"),
										compartment("OMICRON")),
								addRate(
										group("Infectious",
												compartment("Asymptomatic"),
												compartment("Symptomatic")),
										"Symptoms",
										Arrays.asList("Asymptomatic"),
										Arrays.asList("Symptomatic"))),
						compartment("R"))));
		
		create_model_file(productEpi("withFlowInProduct",
				group("SEIR",
						compartment("S"),
						compartment("E"),
						addRate(
							product("I",
									group("Variants",
											compartment("DELTA"),
											compartment("OMICRON")),
									group("Infectious",
											compartment("Asymptomatic"),
											compartment("Symptomatic"))),
							"Symptoms",
							Arrays.asList("Asymptomatic"),
							Arrays.asList("Symptomatic")),
						compartment("R"))));
	}
	
	public static void create_model_file(Epidemic e) throws IOException {
		if (resSet == null) {
			resSet = new ResourceSetImpl();
			Resource.Factory.Registry factoryRegistry = new ResourceFactoryRegistryImpl();
	        factoryRegistry.getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
			
	        resSet = new ResourceSetImpl();
	        EPackage.Registry pkgRegistry = new EPackageRegistryImpl();
	        resSet.setPackageRegistry(pkgRegistry);
	        resSet.setResourceFactoryRegistry(factoryRegistry);
		}
        URI uri = URI.createFileURI("../../test-models/" + e.getId() + ".epimodel");
        Resource resource = resSet.createResource(uri);
        resource.getContents().add(wrap(e));
        resource.save(null);
	}
	
	static Compartment getCompartmentBasedOnLabels(EObject root, List<String> labels) {
		List<Compartment> res = new ArrayList<>();
		root.eAllContents().forEachRemaining(obj -> {
			if (obj instanceof Compartment && ((Compartment) obj).getLabels().equals(labels)) 
				res.add((Compartment) obj);
		});
		
		// throw here because this is prototyping code for making models for tests for
		// example so it doesn't make sense to not find the compartment
		if (res.size() == 0)
			throw new RuntimeException("Could not find compartment '" + labels + "'");
		else if (res.size() > 1)
			throw new RuntimeException("Found multiple compartments '" + labels + "'");
		else
			return res.get(0);
	}
	
	static Product addContact(Product p, String id, List<String> from, List<String> to, List<String> contact) {
		Contact f = batchRateContactFlow.impl.BatchRateContactFlowFactoryImpl.eINSTANCE.createContact();
		f.setId(id);
		f.setTo(getCompartmentBasedOnLabels(p, to));
		f.setFrom(getCompartmentBasedOnLabels(p, from));
		f.setContact(getCompartmentBasedOnLabels(p, contact));
		p.getFlow().add(wrap(f));
		return p;
	}
	
	static Group addContact(Group g, String id, List<String> from, List<String> to, List<String> contact) {
		Contact f = batchRateContactFlow.impl.BatchRateContactFlowFactoryImpl.eINSTANCE.createContact();
		f.setId(id);
		f.setTo(getCompartmentBasedOnLabels(g, to));
		f.setFrom(getCompartmentBasedOnLabels(g, from));
		f.setContact(getCompartmentBasedOnLabels(g, contact));
		g.getFlow().add(wrap(f));
		return g;
	}
	
	static Product addRate(Product p, String id, List<String> from, List<String> to) {
		Rate f = batchRateContactFlow.impl.BatchRateContactFlowFactoryImpl.eINSTANCE.createRate();
		f.setId(id);
		f.setTo(getCompartmentBasedOnLabels(p, to));
		f.setFrom(getCompartmentBasedOnLabels(p, from));
		p.getFlow().add(wrap(f));
		return p;
	}
	
	static Group addRate(Group g, String id, List<String> from, List<String> to) {
		Rate f = batchRateContactFlow.impl.BatchRateContactFlowFactoryImpl.eINSTANCE.createRate();
		f.setId(id);
		f.setTo(getCompartmentBasedOnLabels(g, to));
		f.setFrom(getCompartmentBasedOnLabels(g, from));
		g.getFlow().add(wrap(f));
		return g;
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
