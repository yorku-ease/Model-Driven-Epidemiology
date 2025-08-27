package seir.utilities;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import seirmodel.Group;
import seirmodel.SEIRModel;
import seirmodel.SeirmodelFactory;

/**
 * Simple application to split SEIR models by groups.
 * 
 * Usage: java ModelSplitterMain <path-to-seirmodel-file>
 */
public class ModelSplitterMain {
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ModelSplitterMain <path-to-seirmodel-file>");
            System.out.println("Example: java ModelSplitterMain covid.seirmodel");
            return;
        }
        
        String modelPath = args[0];
        System.out.println("Processing: " + modelPath);
        
        splitModelByGroups(modelPath);
        
        System.out.println("Done! Check the generated files.");
    }
    
    /**
     * Creates separate model files for each group value in the original model
     */
    public static void splitModelByGroups(String inputFile) {
        try {
            // Load original model
            SEIRModel originalModel = loadModel(inputFile);
            
            // Get all group values
            Set<String> groupValues = getAllGroupValues(originalModel);
            
            if (groupValues.isEmpty()) {
                System.out.println("No groups found in the model.");
                return;
            }
            
            System.out.println("Found " + groupValues.size() + " group values:");
            for (String value : groupValues) {
                System.out.println("  - " + value);
                
                // Create a basic model for this group
                SEIRModel groupModel = createBasicGroupModel(originalModel, value);
                
                // Save the group model
                String outputFile = inputFile.replace(".seirmodel", "_" + 
                    value.replaceAll("[^a-zA-Z0-9]", "_") + ".seirmodel");
                saveModel(groupModel, outputFile);
                
                System.out.println("    Saved: " + outputFile);
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get all unique group values from the model
     */
    private static Set<String> getAllGroupValues(SEIRModel model) {
        Set<String> values = new HashSet<String>();
        
        for (int i = 0; i < model.getGroups().size(); i++) {
            Group group = model.getGroups().get(i);
            for (int j = 0; j < group.getValues().size(); j++) {
                values.add(group.getValues().get(j));
            }
        }
        
        return values;
    }
    
    /**
     * Create a basic group model with minimal structure
     */
    private static SEIRModel createBasicGroupModel(SEIRModel original, String groupValue) {
        SEIRModel groupModel = SeirmodelFactory.eINSTANCE.createSEIRModel();
        
        // Copy basic properties
        groupModel.setTotalPopulation(original.getTotalPopulation());
        groupModel.setGlobalBirthRate(original.getGlobalBirthRate());
        groupModel.setGlobalDeathRate(original.getGlobalDeathRate());
        
        // Note: This creates a basic template.
        // For a complete implementation, you would copy compartments,
        // flows, birth sources, and death sinks that are relevant to this group.
        
        return groupModel;
    }
    
    /**
     * Load a SEIR model from file
     */
    private static SEIRModel loadModel(String filePath) throws IOException {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
            .put("seirmodel", new XMIResourceFactoryImpl());
        
        URI uri = URI.createFileURI(filePath);
        Resource resource = resourceSet.getResource(uri, true);
        
        if (resource.getContents().isEmpty()) {
            throw new IOException("Model file is empty");
        }
        
        return (SEIRModel) resource.getContents().get(0);
    }
    
    /**
     * Save a model to file
     */
    private static void saveModel(SEIRModel model, String filePath) throws IOException {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
            .put("seirmodel", new XMIResourceFactoryImpl());
        
        URI uri = URI.createFileURI(filePath);
        Resource resource = resourceSet.createResource(uri);
        resource.getContents().add(model);
        
        try {
            resource.save(null);
        } catch (IOException e) {
            throw new IOException("Failed to save model: " + e.getMessage());
        }
    }
}