package use_epi;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

public class Test {
	public static void main(String[] args) throws Exception {
		
		String model_fn = "../../runtime-EclipseApplication/modeling/model4.epimodel";
		
		Resource.Factory.Registry factoryRegistry = new ResourceFactoryRegistryImpl();
        Map<String, Object> m = factoryRegistry.getExtensionToFactoryMap();
        m.put("*", new EcoreResourceFactoryImpl());
		
        ResourceSet resSet = new ResourceSetImpl();
        EPackage.Registry pkgRegistry = new EPackageRegistryImpl();
        resSet.setPackageRegistry(pkgRegistry);
        resSet.setResourceFactoryRegistry(factoryRegistry);
        
		{
	        pkgRegistry.put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);
	        pkgRegistry.put(dimensionEpidemic.DimensionEpidemicPackage.eNS_URI, dimensionEpidemic.DimensionEpidemicPackage.eINSTANCE);
	        pkgRegistry.put(batchRateContactFlow.BatchRateContactFlowPackage.eNS_URI, batchRateContactFlow.BatchRateContactFlowPackage.eINSTANCE);
	        pkgRegistry.put(compartmentGroup.CompartmentGroupPackage.eNS_URI, compartmentGroup.CompartmentGroupPackage.eINSTANCE);
	        
//	        pkgRegistry.remove(epimodel.EpimodelPackage.eNS_URI);
//	        pkgRegistry.remove(dimensionEpidemic.DimensionEpidemicPackage.eNS_URI);
//	        pkgRegistry.remove(batchRateContactFlow.BatchRateContactFlowPackage.eNS_URI);
//	        pkgRegistry.remove(compartmentGroup.CompartmentGroupPackage.eNS_URI);
		}
        
        URI uri = URI.createFileURI(model_fn);
        Resource resource = resSet.getResource(uri, true);
        
        epimodel.EpidemicWrapper myEpi = (epimodel.EpidemicWrapper) resource.getContents().get(0);

        List<PhysicalCompartment> cs = myEpi.getEpidemic().getPhysicalCompartments();
        List<PhysicalFlow> fs = myEpi.getEpidemic().getPhysicalFlows();

		String outfolder = "C:/Users/Bruno/Desktop/";

		{
			PrintWriter writer = new PrintWriter(outfolder + myEpi.getEpidemic().getId() + ".compartments.txt", "UTF-8");
			for (PhysicalCompartment pc : cs)
				writer.println(pc.labels);
		    writer.close();
		}
		{
			PrintWriter writer = new PrintWriter(outfolder + myEpi.getEpidemic().getId() + ".equations.txt", "UTF-8");
			for (PhysicalFlow pf : fs) {
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

        return;
	}
}
