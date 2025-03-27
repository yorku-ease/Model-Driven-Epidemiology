package seir.equationgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import seirmodel.Compartment;
import seirmodel.Flow;
import seirmodel.SEIRModel;
import seirmodel.SeirmodelPackage; // Register the Ecore model

public class SEIREquationGenerator {
    public static void main(String[] args) {
        // Ensure EMF XMI Factory is registered correctly
        initializeEMF();

        // Get the correct model path
        String modelPath = System.getProperty("user.dir") + "/Sample.seirmodel"; 

        // Load the SEIR model
        SEIRModel seirModel = loadSEIRModel(modelPath);
        if (seirModel == null) {
            System.out.println("❌ Failed to load SEIR model.");
            return;
        }

        // Generate equations from the model
        Map<String, String> equations = generateEquations(seirModel);

        // Print equations to console
        System.out.println("✅ Generated SEIR Model Equations:");
        equations.forEach((compartment, equation) -> System.out.println(equation));

        // Save equations to a file
        saveEquationsToFile(equations);
    }

    /**
     * Ensures EMF XMI Factory is registered to work properly in Java 11+
     */
    private static void initializeEMF() {
        try {
            // Explicitly load XMI factory dynamically
            Class.forName("org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl");
            // Register it
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("seirmodel", new XMIResourceFactoryImpl());
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: Unable to load EMF XMI Factory.");
            e.printStackTrace();
        }
    }

    /**
     * Loads the SEIR model from the .seirmodel file.
     */
    private static SEIRModel loadSEIRModel(String filePath) {
        File modelFile = new File(filePath);
        if (!modelFile.exists()) {
            System.err.println("❌ Error: Model file not found at " + filePath);
            return null;
        }

        try {
            // Register the Ecore package for `seirmodel`
            EPackage.Registry.INSTANCE.put(SeirmodelPackage.eNS_URI, SeirmodelPackage.eINSTANCE);

            // Create a resource set and load the model
            ResourceSet resourceSet = new ResourceSetImpl();
            Resource resource = resourceSet.getResource(URI.createFileURI(modelFile.getAbsolutePath()), true);
            resource.load(null);

            return (SEIRModel) resource.getContents().get(0);
        } catch (IOException | IndexOutOfBoundsException e) {
            System.err.println("❌ Error: Failed to load SEIR model.");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates the SEIR differential equations dynamically from the model.
     */
    private static Map<String, String> generateEquations(SEIRModel model) {
        Map<String, String> equations = new HashMap<>();

        for (Compartment compartment : model.getCompartments()) {
            StringBuilder equation = new StringBuilder("d" + compartment.getName() + "/dt = ");

            // Incoming flows (population entering this compartment)
            for (Compartment source : model.getCompartments()) {
                for (Flow flow : source.getOutgoingFlows()) {
                    if (flow.getTarget() == compartment) {
                        equation.append("+ ").append(flow.getRate()).append(" * ")
                                .append(source.getName()).append(" ");
                    }
                }
            }

            // Outgoing flows (population leaving this compartment)
            for (Flow flow : compartment.getOutgoingFlows()) {
                equation.append("- ").append(flow.getRate()).append(" * ")
                        .append(compartment.getName()).append(" ");
            }

            equations.put(compartment.getName(), equation.toString());
        }

        return equations;
    }

    /**
     * Saves the generated SEIR equations to a text file.
     */
    private static void saveEquationsToFile(Map<String, String> equations) {
        try (FileWriter writer = new FileWriter("SEIR_Equations.txt")) {
            for (Map.Entry<String, String> entry : equations.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            System.out.println("✅ Equations saved to SEIR_Equations.txt");
        } catch (IOException e) {
            System.err.println("❌ Error: Failed to save equations.");
            e.printStackTrace();
        }
    }
}