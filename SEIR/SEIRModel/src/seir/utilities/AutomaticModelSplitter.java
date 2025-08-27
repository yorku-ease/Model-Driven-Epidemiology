package seir.utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import seirmodel.BirthSource;
import seirmodel.Compartment;
import seirmodel.ContactFlow;
import seirmodel.DeathSink;
import seirmodel.Flow;
import seirmodel.Group;
import seirmodel.Product;
import seirmodel.RateFlow;
import seirmodel.SEIRModel;
import seirmodel.SeirmodelFactory;
import seirmodel.StratumSpecificRate;

/**
 * Automatically splits SEIR models by group values.
 * This creates complete models for each group with all relevant compartments and flows.
 */
public class AutomaticModelSplitter {
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java AutomaticModelSplitter <model-file.seirmodel>");
            System.out.println("Example: java AutomaticModelSplitter covid.seirmodel");
            return;
        }
        
        splitModel(args[0]);
    }
    
    public static void splitModel(String inputFile) {
        try {
            System.out.println("Loading model: " + inputFile);
            SEIRModel originalModel = loadModel(inputFile);
            
            Set<String> groupValues = extractGroupValues(originalModel);
            
            if (groupValues.isEmpty()) {
                System.out.println("No group values found. Model may not have groups defined.");
                return;
            }
            
            System.out.println("Found " + groupValues.size() + " group values: " + groupValues);
            
            for (String groupValue : groupValues) {
                System.out.println("\nCreating model for group: " + groupValue);
                
                // Create a complete copy of the original model
                SEIRModel groupModel = (SEIRModel) EcoreUtil.copy(originalModel);
                
                // Filter and modify for this specific group
                filterModelForGroup(groupModel, groupValue);
                
                // Save the group-specific model
                String outputFile = inputFile.replace(".seirmodel", 
                    "_" + sanitizeFileName(groupValue) + ".seirmodel");
                saveModel(groupModel, outputFile);
                
                System.out.println("Saved: " + outputFile);
                System.out.println("  Compartments: " + groupModel.getCompartments().size());
                System.out.println("  Birth Sources: " + groupModel.getBirthSources().size());
                System.out.println("  Death Sinks: " + groupModel.getDeathSinks().size());
            }
            
            System.out.println("\n✓ Model splitting completed successfully!");
            System.out.println("You can now create diagrams from each group-specific model file.");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static Set<String> extractGroupValues(SEIRModel model) {
        Set<String> values = new HashSet<String>();
        
        for (int i = 0; i < model.getGroups().size(); i++) {
            Group group = model.getGroups().get(i);
            for (int j = 0; j < group.getValues().size(); j++) {
                values.add(group.getValues().get(j));
            }
        }
        
        return values;
    }
    
    private static void filterModelForGroup(SEIRModel model, String targetGroupValue) {
        // Update model name/description to indicate the specific group
        updateModelForGroup(model, targetGroupValue);
        
        // Simplify flows - replace stratum-specific rates with target group rates
        simplifyFlowsForGroup(model, targetGroupValue);
        
        // Update labels to indicate group
        updateLabelsForGroup(model, targetGroupValue);
    }
    
    private static void updateModelForGroup(SEIRModel model, String groupValue) {
        // This could add group information to model metadata if such fields exist
        // For now, we'll rely on filename and content to indicate the group
    }
    
    private static void simplifyFlowsForGroup(SEIRModel model, String targetGroup) {
        // Process all compartments
        for (int i = 0; i < model.getCompartments().size(); i++) {
            Compartment comp = model.getCompartments().get(i);
            
            // Process all outgoing flows
            for (int j = 0; j < comp.getOutgoingFlows().size(); j++) {
                Flow flow = comp.getOutgoingFlows().get(j);
                simplifyFlowForGroup(flow, targetGroup);
            }
        }
    }
    
    private static void simplifyFlowForGroup(Flow flow, String targetGroup) {
        if (flow instanceof RateFlow) {
            RateFlow rateFlow = (RateFlow) flow;
            
            // Find the rate for our target group
            for (int k = 0; k < rateFlow.getStratumSpecificRates().size(); k++) {
                StratumSpecificRate stratumRate = rateFlow.getStratumSpecificRates().get(k);
                if (targetGroup.equals(stratumRate.getStratum())) {
                    // Replace the default rate with the group-specific rate
                    rateFlow.setRate(stratumRate.getRate());
                    break;
                }
            }
            
            // Clear all stratum-specific rates since we now have a single rate
            rateFlow.getStratumSpecificRates().clear();
            
        } else if (flow instanceof ContactFlow) {
            ContactFlow contactFlow = (ContactFlow) flow;
            
            // Find the contact rate for our target group  
            for (int k = 0; k < contactFlow.getStratumSpecificRates().size(); k++) {
                StratumSpecificRate stratumRate = contactFlow.getStratumSpecificRates().get(k);
                if (targetGroup.equals(stratumRate.getStratum())) {
                    // Replace the default contact rate with the group-specific rate
                    contactFlow.setContactRate(stratumRate.getRate());
                    break;
                }
            }
            
            // Clear all stratum-specific rates
            contactFlow.getStratumSpecificRates().clear();
        }
    }
    
    private static void updateLabelsForGroup(SEIRModel model, String groupValue) {
        // Update birth source names
        for (int i = 0; i < model.getBirthSources().size(); i++) {
            BirthSource source = model.getBirthSources().get(i);
            if (source.getName() != null && !source.getName().contains(groupValue)) {
                source.setName(source.getName() + " (" + groupValue + ")");
            }
        }
        
        // Update death sink names
        for (int i = 0; i < model.getDeathSinks().size(); i++) {
            DeathSink sink = model.getDeathSinks().get(i);
            if (sink.getName() != null && !sink.getName().contains(groupValue)) {
                sink.setName(sink.getName() + " (" + groupValue + ")");
            }
        }
    }
    
    private static String sanitizeFileName(String groupValue) {
        return groupValue.replaceAll("[^a-zA-Z0-9]", "_");
    }
    
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
    
    private static void saveModel(SEIRModel model, String filePath) throws IOException {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
            .put("seirmodel", new XMIResourceFactoryImpl());
        
        URI uri = URI.createFileURI(filePath);
        Resource resource = resourceSet.createResource(uri);
        resource.getContents().add(model);
        
        resource.save(null);
    }
}