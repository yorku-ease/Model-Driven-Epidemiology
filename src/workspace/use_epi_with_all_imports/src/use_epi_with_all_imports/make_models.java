package use_epi_with_all_imports;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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

import batchRateContactFlow.Batch;
import batchRateContactFlow.Contact;
import batchRateContactFlow.Rate;
import product.Product;
import product.impl.ProductFactoryImpl;
import group.Group;
import group.impl.GroupFactoryImpl;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Epidemic;
import epimodel.Flow;
import epimodel.FlowWrapper;
import epimodel.impl.EpimodelFactoryImpl;

public class make_models {
	public static ResourceSet resSet = null;
	
	public static void main(String[] args) throws Exception {
		// first model must require NO FLOWS (dependency loading hell)
		create_model_file(create_model(group("GECC_S_I",
				compartment("S"),
				compartment("I"))));
		
		create_model_file(create_model(
				addContact(
					group(
						"contact_g",
						compartment("S"),
						compartment("I")
					),
					"Exposure",
					Arrays.asList("S"),
					Arrays.asList("I"),
					Arrays.asList("I"))
				));
		
		create_model_file(create_model(
				addContact(
					product(
						"pcontact_p_g",
						group(
							"contact_g",
							compartment("S"),
							compartment("I")
						)
					),
					"Exposure",
					Arrays.asList("S"),
					Arrays.asList("I"),
					Arrays.asList("I"))
				));
		
		create_model_file(create_model(
				addContact(
					product(
						"pcontact_p1_g",
						group(
							"si",
							compartment("S"),
							compartment("I")
						),
						compartment("0")
					),
					"Exposure",
					Arrays.asList("S"),
					Arrays.asList("I"),
					Arrays.asList("I"))
				));
		
		create_model_file(create_model(
				addContact(
					product(
						"pcontact_p2_g",
						group(
							"si",
							compartment("S"),
							compartment("I")
						),
						group(
							"01",
							compartment("0"),
							compartment("1")
						)
					),
					"Exposure",
					Arrays.asList("S"),
					Arrays.asList("I"),
					Arrays.asList("I"))
				));
		
		create_model_file(create_model(
					product(
						"gcontact_p_g",
						addContact(
						group(
							"contact_g",
							compartment("S"),
							compartment("I")
						),
						"Exposure",
						Arrays.asList("S"),
						Arrays.asList("I"),
						Arrays.asList("I")
					))
				));
		
		create_model_file(create_model(
					product(
						"gcontact_p1_g",
						addContact(
							group(
								"contact_g",
								compartment("S"),
								compartment("I")
							),
							"Exposure",
							Arrays.asList("S"),
							Arrays.asList("I"),
							Arrays.asList("I")
						),
						compartment("0")
					)
				));
		
		create_model_file(create_model(
					product(
						"grate_p1_g",
						addRate(
							group(
								"contact_g",
								compartment("S"),
								compartment("I")
							),
							"Exposure",
							Arrays.asList("S"),
							Arrays.asList("I")
						),
						compartment("0")
					)
				));
		
		create_model_file(create_model(
				product(
					"gcontact_p2_g",
					addContact(
						group(
							"contact_g",
							compartment("S"),
							compartment("I")
						),
						"Exposure",
						Arrays.asList("S"),
						Arrays.asList("I"),
						Arrays.asList("I")
					),
					group(
						"01",
						compartment("0"),
						compartment("1")
					)
				)
			));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		create_model_file(create_model(
				addRate(
						group("GECC_SI_S_I",
						compartment("SI", "S"),
						compartment("SI", "I")),
					"a",
					Arrays.asList("SI", "S"),
					Arrays.asList("SI", "I")
			)));
		
		create_model_file(create_model(product("DEG_SI_S_I",
				group("SI",
						compartment("S"),
						compartment("I")))));
		
		create_model_file(create_model(product("DEPGG_COVID_INF_VAR_SEIR",
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
						compartment("R")))));
		
		create_model_file(create_model(product("withFlowInGroup",
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
						compartment("R")))));
		
		create_model_file(create_model(product("withFlowInProduct",
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
						compartment("R")))));
	}
	
	public static Epidemic create_model(Compartment c) {
		Epidemic e = EpimodelFactoryImpl.eINSTANCE.createEpidemic();
		e.setCompartmentwrapper(EpimodelFactoryImpl.eINSTANCE.createCompartmentWrapper());
		e.getCompartmentwrapper().setCompartment(c);
		return e;
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
		String fn = "../../test-models/" + e.getCompartmentwrapper().getCompartment().getLabels().toString().replace("[",  "").replace("]",  "") + ".epimodel";
        URI uri = URI.createFileURI(fn);
        Resource resource = resSet.createResource(uri);
        resource.getContents().add(e);
        resource.save(null);
        
        // need to remove this because it is used by EMF to refer to other files but
        // we are in the current file and it just silently fails to retrieve the reference which is great
        // (or maybe i forgot a parameter somewhere to
        // resolve proxies but anyway just remove this file reference)
        fileSubStringRemove(fn, fn + "#");
	}
	
	public static void fileSubStringRemove(String filePath, String substringToRemove) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            fileContent.append(line).append(System.lineSeparator());
        }
        reader.close();

        String modifiedContent = fileContent.toString().replaceAll(substringToRemove, "");

        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(modifiedContent);
        writer.close();
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
		f.setParameters("some-parameter-" + id);
		return p;
	}
	
	static Group addContact(Group g, String id, List<String> from, List<String> to, List<String> contact) {
		Contact f = batchRateContactFlow.impl.BatchRateContactFlowFactoryImpl.eINSTANCE.createContact();
		f.setId(id);
		f.setTo(getCompartmentBasedOnLabels(g, to));
		f.setFrom(getCompartmentBasedOnLabels(g, from));
		f.setContact(getCompartmentBasedOnLabels(g, contact));
		g.getFlow().add(wrap(f));
		f.setParameters("some-parameter-" + id);
		return g;
	}
	
	static Product addRate(Product p, String id, List<String> from, List<String> to) {
		Rate f = batchRateContactFlow.impl.BatchRateContactFlowFactoryImpl.eINSTANCE.createRate();
		f.setId(id);
		f.setTo(getCompartmentBasedOnLabels(p, to));
		f.setFrom(getCompartmentBasedOnLabels(p, from));
		p.getFlow().add(wrap(f));
		f.setParameters("some-parameter-" + id);
		return p;
	}
	
	static Group addRate(Group g, String id, List<String> from, List<String> to) {
		Rate f = batchRateContactFlow.impl.BatchRateContactFlowFactoryImpl.eINSTANCE.createRate();
		f.setId(id);
		f.setTo(getCompartmentBasedOnLabels(g, to));
		f.setFrom(getCompartmentBasedOnLabels(g, from));
		g.getFlow().add(wrap(f));
		f.setParameters("some-parameter-" + id);
		return g;
	}
	
	static Product addBatch(Product p, String id, List<String> from, List<String> to) {
		Batch f = batchRateContactFlow.impl.BatchRateContactFlowFactoryImpl.eINSTANCE.createBatch();
		f.setId(id);
		f.setTo(getCompartmentBasedOnLabels(p, to));
		f.setFrom(getCompartmentBasedOnLabels(p, from));
		p.getFlow().add(wrap(f));
		f.setParameters("some-parameter-" + id);
		return p;
	}
	
	static Group addBatch(Group g, String id, List<String> from, List<String> to) {
		Batch f = batchRateContactFlow.impl.BatchRateContactFlowFactoryImpl.eINSTANCE.createBatch();
		f.setId(id);
		f.setTo(getCompartmentBasedOnLabels(g, to));
		f.setFrom(getCompartmentBasedOnLabels(g, from));
		g.getFlow().add(wrap(f));
		f.setParameters("some-parameter-" + id);
		return g;
	}
	
	static Group addSource(Group g, List<String> source) {
		for (CompartmentWrapper w : g.getCompartment())
			if (w.getCompartment().getLabels().equals(source)) {
				g.getSource().add(w);
				return g;
			}
		throw new RuntimeException("Missing compartment " + source);
	}
	
	static Group addSink(Group g, List<String> sink) {
		for (CompartmentWrapper w : g.getCompartment())
			if (w.getCompartment().getLabels().equals(sink)) {
				g.getSink().add(w);
				return g;
			}
		throw new RuntimeException("Missing compartment " + sink);
	}
	
	static Product product(Object... params) {
		Product p = ProductFactoryImpl.eINSTANCE.createProduct();
		for (Object label : params)
			if (label instanceof String)
				p.getLabel().add((String) label);
		for (Object c : params)
			if (c instanceof Compartment)
				p.getCompartment().add(wrap((Compartment) c));
		for (Object rate : params)
			if (rate instanceof RateParam)
				p = addRate(p, ((RateParam) rate).id, ((RateParam) rate).from, ((RateParam) rate).to);
		for (Object flow : params)
			if (flow instanceof BatchParam)
				p = addBatch(p, ((BatchParam) flow).id, ((BatchParam) flow).from, ((BatchParam) flow).to);
		for (Object flow : params)
			if (flow instanceof ContactParam)
				p = addContact(p, ((ContactParam) flow).id, ((ContactParam) flow).from, ((ContactParam) flow).to, ((ContactParam) flow).contact);
		return p;
	}
	
	static Group group(Object... params) {
		Group g = GroupFactoryImpl.eINSTANCE.createGroup();
		for (Object label : params)
			if (label instanceof String)
				g.getLabel().add((String) label);
		for (Object c : params)
			if (c instanceof Compartment)
				g.getCompartment().add(wrap((Compartment) c));
		for (Object flow : params)
			if (flow instanceof RateParam)
				g = addRate(g, ((RateParam) flow).id, ((RateParam) flow).from, ((RateParam) flow).to);
		for (Object flow : params)
			if (flow instanceof BatchParam)
				g = addBatch(g, ((BatchParam) flow).id, ((BatchParam) flow).from, ((BatchParam) flow).to);
		for (Object flow : params)
			if (flow instanceof ContactParam)
				g = addContact(g, ((ContactParam) flow).id, ((ContactParam) flow).from, ((ContactParam) flow).to, ((ContactParam) flow).contact);
		for (Object source : params)
			if (source instanceof SourceParam)
				g = addSource(g, ((SourceParam) source).source);
		for (Object sink : params)
			if (sink instanceof SinkParam)
				g = addSink(g, ((SinkParam) sink).sink);
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
	
	static RateParam rate(String id, List<String> from, List<String> to) {
		return new RateParam(id, from, to);
	}
	
	static BatchParam batch(String id, List<String> from, List<String> to) {
		return new BatchParam(id, from, to);
	}
	
	static ContactParam contact(String id, List<String> from, List<String> to, List<String> contact) {
		return new ContactParam(id, from, to, contact);
	}
	
	static SourceParam source(List<String> source) {
		return new SourceParam(source);
	}
	
	static SinkParam sink(List<String> sink) {
		return new SinkParam(sink);
	}
	
	static class RateParam {
		public final String id;
		public final List<String> from;
		public final List<String> to;
		
		public RateParam(String id, List<String> from, List<String> to) {
			this.id = id;
			this.from = from;
			this.to = to;
		}
	}
	
	static class BatchParam {
		public final String id;
		public final List<String> from;
		public final List<String> to;
		
		public BatchParam(String id, List<String> from, List<String> to) {
			this.id = id;
			this.from = from;
			this.to = to;
		}
	}
	
	static class ContactParam {
		public final String id;
		public final List<String> from;
		public final List<String> to;
		public final List<String> contact;
		
		public ContactParam(String id, List<String> from, List<String> to, List<String> contact) {
			this.id = id;
			this.from = from;
			this.to = to;
			this.contact = contact;
		}
	}
	
	static class SourceParam {
		public final List<String> source;
		
		public SourceParam(List<String> source) {
			this.source = source;
		}
	}
	
	static class SinkParam {
		public final List<String> sink;
		
		public SinkParam(List<String> sink) {
			this.sink = sink;
		}
	}
}
