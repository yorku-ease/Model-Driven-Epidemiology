package use_epi;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

import epimodel.util.FlowEquation;
import epimodel.util.PhysicalCompartment;

public class CompileModel {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

//		compile(resSet, "../../runtime-EclipseApplication/modeling/GECC_S_I.epimodel");
//		compile(resSet, "../../runtime-EclipseApplication/modeling/GECC_SI_S_I.epimodel");
//		compile(resSet, "../../runtime-EclipseApplication/modeling/DEG_SI_S_I.epimodel");
//		compile(resSet, "../../runtime-EclipseApplication/modeling/DEPGG_COVID_INF_VAR_SEIR.epimodel");
		compile("../../runtime-plugin/modeling/MyEpimodel.epimodel");
//		compile(resSet, "../../runtime-plugin/modeling/model2.epimodel");
//		compile(resSet, "../../runtime-plugin/modeling/model3.epimodel");
	}
	
	static void compile(String model_fn) throws FileNotFoundException, UnsupportedEncodingException {
        
        epimodel.CompartmentWrapper myEpi = (epimodel.CompartmentWrapper) epimodel.impl.EpimodelPackageImpl.loadModel(model_fn);

		String outfolder = "C:/Users/Bruno/Desktop/";

		{
			PrintWriter writer = new PrintWriter(outfolder + myEpi.getCompartment().getLabels() + ".compartments.txt", "UTF-8");
			for (PhysicalCompartment pc : myEpi.getCompartment().getPhysicalCompartments())
				writer.println(pc.labels);
		    writer.close();
		}
		{
			PrintWriter writer = new PrintWriter(outfolder + myEpi.getCompartment().getLabels() + ".equations.txt", "UTF-8");
			for (FlowEquation eq : myEpi.getCompartment().getPhysicalFlows()) {
				writer.println(eq.equation);
				writer.println(eq.equationCompartments.stream().map(p->p.labels).collect(Collectors.toList()));
				writer.println(eq.source);
				writer.println(eq.sink);
				writer.println(eq.requiredOperators);
			    writer.println();
			}
		    writer.close();
		}
	}
}