package use_epi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalCompartment;

public class CompileModel {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
//		compile("../../test-models/contact_g.epimodel");
//		compile("../../test-models/gcontact_p_g.epimodel");
//		compile("../../test-models/gcontact_p1_g.epimodel");
//		compile("../../test-models/grate_p1_g.epimodel");
//		compile("../../test-models/gcontact_p2_g.epimodel");
//		compile("../../test-models/pcontact_p_g.epimodel");
//		compile("../../test-models/pcontact_p1_g.epimodel");
//		compile("../../test-models/pcontact_p2_g.epimodel");
		compile("../../runtime/model1/model1.epimodel");
	}
	
	static void compile(String model_fn) throws FileNotFoundException, UnsupportedEncodingException {
        
		String outfolder = "C:/Users/Bruno/Desktop/compiled_models/";

		epimodel.Epidemic root = (epimodel.Epidemic)
				epimodel.impl.EpimodelPackageImpl.loadModel(model_fn);
		epimodel.CompartmentWrapper myEpi = root.getCompartmentwrapper();
		
		String innerFolderName = myEpi
				.getCompartment()
				.getLabels()
				.toString()
				.replace("[", "")
				.replace("]", "");
		
		String innerFolder = outfolder + innerFolderName + "/";
		
		String baseFileName = innerFolder + innerFolderName;
		
		new File(innerFolder).mkdirs();
		
		{
			PrintWriter writer = new PrintWriter(baseFileName + ".compartments.txt", "UTF-8");
			for (PhysicalCompartment pc : myEpi.getCompartment().getPhysicalCompartments())
				writer.println(sorted(pc));
		    writer.close();
		}
		{
			PrintWriter writer = new PrintWriter(baseFileName + ".equations.txt", "UTF-8");
			for (PhysicalFlow eq : myEpi.getCompartment().getEquations()) {
				writer.println(eq.equation);
				writer.println(sorted(eq.source));
				writer.println(sorted(eq.sink));
				writer.println(eq.requiredOperators);
			    writer.println();
			}
		    writer.close();
		}
	}
	
	static String sorted(PhysicalCompartment pc) {
		PhysicalCompartment pc2 = new PhysicalCompartment(pc.labels);
		pc2.labels.sort(java.util.Comparator.naturalOrder());
		return pc2.labels.toString();
	}
}