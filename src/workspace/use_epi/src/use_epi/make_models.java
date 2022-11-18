package use_epi;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

//import compartmentGroup.Group;
//import compartmentGroup.GroupEpidemic;
//import compartmentGroup.impl.CompartmentGroupFactoryImpl;
//import dimensionEpidemic.DimensionEpidemic;
//import dimensionEpidemic.Product;
//import dimensionEpidemic.impl.DimensionEpidemicFactoryImpl;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Epidemic;
import epimodel.EpidemicWrapper;
import epimodel.Flow;
import epimodel.FlowWrapper;
import epimodel.impl.EpimodelFactoryImpl;

public class make_models {
	public static void main(String[] args) throws Exception {
		Resource.Factory.Registry factoryRegistry = new ResourceFactoryRegistryImpl();
        factoryRegistry.getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		
        ResourceSet resSet = new ResourceSetImpl();
        EPackage.Registry pkgRegistry = new EPackageRegistryImpl();
        resSet.setPackageRegistry(pkgRegistry);
        resSet.setResourceFactoryRegistry(factoryRegistry);
        
		{
	        pkgRegistry.put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);
		}
		
//		{
//			String model_fn = "../../runtime-EclipseApplication/modeling/GECC_S_I.epimodel";
//	        URI uri = URI.createFileURI(model_fn);
//	        Resource resource = resSet.createResource(uri);
//	        
//	        epimodel.EpidemicWrapper w = EpimodelFactoryImpl.eINSTANCE.createEpidemicWrapper();
//	        {
//	        	GroupEpidemic e = createGE("GECC_S_I");
//	        	e.getCompartment().add(wrapc(createCompartment("S")));
//	        	e.getCompartment().add(wrapc(createCompartment("I")));
//	        	w.setEpidemic(e);
//	        }
//	        
//	        resource.getContents().add(w);
//	        resource.save(null);
//		}
//		{
//			String model_fn = "../../runtime-EclipseApplication/modeling/GECC_SI_S_I.epimodel";
//	        URI uri = URI.createFileURI(model_fn);
//	        Resource resource = resSet.createResource(uri);
//	        
//	        epimodel.EpidemicWrapper w = EpimodelFactoryImpl.eINSTANCE.createEpidemicWrapper();
//	        {
//	        	GroupEpidemic e = createGE("GECC_SI_S_I");
//	        	e.getCompartment().add(wrapc(createCompartment("SI", "S")));
//	        	e.getCompartment().add(wrapc(createCompartment("SI", "I")));
//	        	w.setEpidemic(e);
//	        }
//	        
//	        resource.getContents().add(w);
//	        resource.save(null);
//		}
//		{
//			String model_fn = "../../runtime-EclipseApplication/modeling/DEG_SI_S_I.epimodel";
//	        URI uri = URI.createFileURI(model_fn);
//	        Resource resource = resSet.createResource(uri);
//	        
//	        epimodel.EpidemicWrapper w = EpimodelFactoryImpl.eINSTANCE.createEpidemicWrapper();
//	        {
//	        	DimensionEpidemic d = createDE("DEG_SI_S_I");
//	        	Group g = createGroup("SI");
//	        	g.getCompartment().add(wrapc(createCompartment("S")));
//	        	g.getCompartment().add(wrapc(createCompartment("I")));
//	        	d.getDimension().add(wrapc(g));
//	        	w.setEpidemic(d);
//	        }
//	        
//	        resource.getContents().add(w);
//	        resource.save(null);
//		}
//
//		{
//			String model_fn = "../../runtime-EclipseApplication/modeling/DEPGG_COVID_INF_VAR_SEIR.epimodel";
//	        URI uri = URI.createFileURI(model_fn);
//	        Resource resource = resSet.createResource(uri);
//	        
//	        epimodel.EpidemicWrapper w = EpimodelFactoryImpl.eINSTANCE.createEpidemicWrapper();
//	        {
//	        	DimensionEpidemic d = createDE("DEPGG_COVID_INF_VAR_SEIR");
//	        	
//	        	Group e = createGroup("SEIR");
//	        
//	        	e.getCompartment().add(wrapc(createCompartment("S")));
//	        	e.getCompartment().add(wrapc(createCompartment("E")));
//	        	{
//
//		        	Product p =  createProduct("I");
//		        	Group gv = createGroup("Variants");
//		        	gv.getCompartment().add(wrapc(createCompartment("DELTA")));
//		        	gv.getCompartment().add(wrapc(createCompartment("OMICRON")));
//		        	p.getDimensions().add(wrapc(gv)); 
//
//		        	Group gi = createGroup("Infectious");
//		        	gi.getCompartment().add(wrapc(createCompartment("Symptomatic")));
//		        	gi.getCompartment().add(wrapc(createCompartment("Asymptomatic")));
//		        	p.getDimensions().add(wrapc(gi)); 
//		        	e.getCompartment().add(wrapc(p));
//	        	}
//	        	e.getCompartment().add(wrapc(createCompartment("R")));
//	        	d.getDimension().add(wrapc(e));
//	        	
//	        	w.setEpidemic(d);
//	        }
//	        
//	        resource.getContents().add(w);
//	        resource.save(null);
//		}
	}
	
//	static DimensionEpidemic createDE(String id) {
//		DimensionEpidemic e = DimensionEpidemicFactoryImpl.eINSTANCE.createDimensionEpidemic();
//		e.setId(id);
//		return e;
//	}
//	
//	static GroupEpidemic createGE(String id) {
//		GroupEpidemic e = CompartmentGroupFactoryImpl.eINSTANCE.createGroupEpidemic();
//		e.setId(id);
//		return e;
//	}
//	
//	static Product createProduct(String... labels) {
//		Product c = DimensionEpidemicFactoryImpl.eINSTANCE.createProduct();
//		for (String label : labels)
//			c.getLabel().add(label);
//		return c;
//	}
//	
//	static Group createGroup(String... labels) {
//		Group c = CompartmentGroupFactoryImpl.eINSTANCE.createGroup();
//		for (String label : labels)
//			c.getLabel().add(label);
//		return c;
//	}
	
	static Compartment createCompartment(String... labels) {
		Compartment c = EpimodelFactoryImpl.eINSTANCE.createCompartment();
		for (String label : labels)
			c.getLabel().add(label);
		return c;
	}
	
	static CompartmentWrapper wrapc(Compartment c) {
		epimodel.CompartmentWrapper w = EpimodelFactoryImpl.eINSTANCE.createCompartmentWrapper();
		w.setCompartment(c);
		return w;
	}
	
	static FlowWrapper wrapf(Flow f) {
		epimodel.FlowWrapper w = EpimodelFactoryImpl.eINSTANCE.createFlowWrapper();
		w.setFlow(f);
		return w;
	}
	
	static EpidemicWrapper wrape(Epidemic e) {
		epimodel.EpidemicWrapper w = EpimodelFactoryImpl.eINSTANCE.createEpidemicWrapper();
		w.setEpidemic(e);
		return w;
	}
}
