package use_epi;

import java.io.PrintWriter;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalFlowEquation;

public class Test {
	public static void main(String[] args) throws Exception {

//		compile(resSet, "../../runtime-EclipseApplication/modeling/GECC_S_I.epimodel");
//		compile(resSet, "../../runtime-EclipseApplication/modeling/GECC_SI_S_I.epimodel");
//		compile(resSet, "../../runtime-EclipseApplication/modeling/DEG_SI_S_I.epimodel");
//		compile(resSet, "../../runtime-EclipseApplication/modeling/DEPGG_COVID_INF_VAR_SEIR.epimodel");
		compile("../../runtime-plugin/modeling/MyEpimodel.epimodel");
//		compile(resSet, "../../runtime-plugin/modeling/model2.epimodel");
//		compile(resSet, "../../runtime-plugin/modeling/model3.epimodel");
	}
	
	static void compile(String model_fn) throws Exception {
        
        epimodel.EpidemicWrapper myEpi = (epimodel.EpidemicWrapper) loadModel(model_fn);

		String outfolder = "C:/Users/Bruno/Desktop/";

		{
			PrintWriter writer = new PrintWriter(outfolder + myEpi.getEpidemic().getId() + ".compartments.txt", "UTF-8");
			for (PhysicalCompartment pc : myEpi.getEpidemic().getPhysicalCompartments())
				writer.println(pc.labels);
		    writer.close();
		}
		{
			PrintWriter writer = new PrintWriter(outfolder + myEpi.getEpidemic().getId() + ".equations.txt", "UTF-8");
			for (PhysicalFlow pf : myEpi.getEpidemic().getPhysicalFlows()) {
				for (PhysicalFlowEquation  pfe : pf.equations) {
					writer.println(pfe.equation);
					writer.println(pfe.equationCompartments.stream().map(p->p.labels).collect(Collectors.toList()));
					writer.println(pfe.coefficients);
					writer.println(pfe.affectedCompartments.stream().map(p->p.labels).collect(Collectors.toList()));
					writer.println(pfe.requiredOperators);
				    writer.println();
				}
			}
		    writer.close();
		}
	}
	
	static EObject loadModel(String model_fn) throws Exception {
		Resource.Factory.Registry factoryRegistry = new ResourceFactoryRegistryImpl();
        factoryRegistry.getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		
        ResourceSet resSet = new ResourceSetImpl();
        resSet.setPackageRegistry(EPackage.Registry.INSTANCE);
        resSet.setResourceFactoryRegistry(factoryRegistry);
        
        EPackage.Registry.INSTANCE.put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);
        
        URI uri = URI.createFileURI(model_fn);
        Resource resource = resSet.getResource(uri, true);
        
        return resource.getContents().get(0);
	}
}
