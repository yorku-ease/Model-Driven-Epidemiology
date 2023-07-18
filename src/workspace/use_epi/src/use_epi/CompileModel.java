package use_epi;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalCompartment;

public class CompileModel {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		compile("../../test-models/GECC_SI_S_I.epimodel");
		compile("../../test-models/withFlowInProduct.epimodel");
	}
	
	static void compile(String model_fn) throws FileNotFoundException, UnsupportedEncodingException {
        
		String outfolder = "C:/Users/Bruno/Desktop/";

		epimodel.Epidemic root = (epimodel.Epidemic)
				epimodel.impl.EpimodelPackageImpl.loadModel(model_fn);
		epimodel.CompartmentWrapper myEpi = root.getCompartmentwrapper();
		
		String baseFileName = outfolder + myEpi.getCompartment().getLabels();
		
		{
			PrintWriter writer = new PrintWriter(baseFileName + ".compartments.txt", "UTF-8");
			for (PhysicalCompartment pc : myEpi.getCompartment().getPhysicalCompartments())
				writer.println(pc.labels);
		    writer.close();
		}
		{
			PrintWriter writer = new PrintWriter(baseFileName + ".equations.txt", "UTF-8");
			for (PhysicalFlow eq : myEpi.getCompartment().getEquations()) {
				writer.println(eq.equation);
				writer.println(eq.source);
				writer.println(eq.sink);
				writer.println(eq.requiredOperators);
			    writer.println();
			}
		    writer.close();
		}
	}
}