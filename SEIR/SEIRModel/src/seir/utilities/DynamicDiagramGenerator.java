package seir.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
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
import seirmodel.RateFlow;
import seirmodel.SEIRModel;
import seirmodel.StratumSpecificRate;

/**
 * Completely dynamic model splitter that works with any groups.
 * Automatically detects all group values and creates separate models.
 */
public class DynamicDiagramGenerator {
    
    public static void main(String[] args) {
        String modelFile;
        
        if (args.length == 0) {
            // No arguments provided - look for covid.seirmodel in current directory
        	Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name of the .seirmodel file (e.g. covid.seirmodel): ");
            String fileName = scanner.nextLine().trim();
            scanner.close();

            modelFile = System.getProperty("user.dir") + "/" + fileName;
            System.out.println("No arguments provided. Looking for covid.seirmodel in current directory...");
        } else if (args.length == 1) {
            // Use provided argument
            modelFile = args[0];
        } else {
            System.out.println("Usage: java DynamicDiagramGenerator [model-file.seirmodel]");
            System.out.println("Examples:");
            System.out.println("  java DynamicDiagramGenerator                    (looks for covid.seirmodel)");
            System.out.println("  java DynamicDiagramGenerator covid.seirmodel    (uses specified file)");
            System.out.println("");
            System.out.println("This utility automatically:");
            System.out.println("  1. Detects all groups in your model");
            System.out.println("  2. Finds all group values dynamically");
            System.out.println("  3. Creates separate model files for each group value");
            System.out.println("  4. Preserves group-specific rates and flows");
            return;
        }
        
        generateDynamicModels(modelFile);
    }
    
    public static void generateDynamicModels(String inputFile) {
        try {
            System.out.println("=== Dynamic SEIR Model Splitter ===");
            System.out.println("Loading: " + inputFile);
            
            SEIRModel originalModel = loadModel(inputFile);
            
            // Dynamic group detection
            List<GroupInfo> allGroups = analyzeGroups(originalModel);
            
            if (allGroups.isEmpty()) {
                System.out.println("❌ No groups found in model. Nothing to split.");
                System.out.println("Make sure your model has <groups> with <values> defined.");
                return;
            }
            
            System.out.println("✓ Found " + allGroups.size() + " group(s):");
            for (GroupInfo groupInfo : allGroups) {
                System.out.println("  📁 " + groupInfo.groupName + ": " + groupInfo.values.size() + " values");
                for (String value : groupInfo.values) {
                    System.out.println("    - " + value);
                }
            }
            
            // Get all unique values across all groups
            Set<String> allUniqueValues = new HashSet<String>();
            for (GroupInfo groupInfo : allGroups) {
                allUniqueValues.addAll(groupInfo.values);
            }
            
            System.out.println("\n🔄 Creating " + allUniqueValues.size() + " group-specific models...");
            
            int count = 0;
            for (String groupValue : allUniqueValues) {
                count++;
                System.out.println("\n[" + count + "/" + allUniqueValues.size() + "] Processing: " + groupValue);
                
                // Create complete copy
                SEIRModel groupModel = (SEIRModel) EcoreUtil.copy(originalModel);
                
                // Customize for this group
                customizeModelForGroup(groupModel, groupValue);
                
                // Generate filename
                String outputFile = generateFileName(inputFile, groupValue);
                
                // Save
                saveModel(groupModel, outputFile);
                
                // Report
                int compartments = groupModel.getCompartments().size();
                int stratifiedCompartments = countStratifiedCompartments(groupModel);
                
                System.out.println("  ✓ Saved: " + outputFile);
                System.out.println("    📊 " + compartments + " compartments (" + stratifiedCompartments + " stratified)");
                System.out.println("    🔄 " + countFlows(groupModel) + " flows");
                System.out.println("    📈 " + groupModel.getBirthSources().size() + " birth sources");
                System.out.println("    📉 " + groupModel.getDeathSinks().size() + " death sinks");
            }
            
            System.out.println("\n🎉 SUCCESS! Generated " + allUniqueValues.size() + " group-specific models.");
            System.out.println("💡 You can now create diagrams from each file in Eclipse:");
            for (String groupValue : allUniqueValues) {
                System.out.println("  📋 " + generateFileName(inputFile, groupValue));
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static class GroupInfo {
        String groupName;
        String description;
        List<String> values;
        
        GroupInfo(String name, String desc) {
            this.groupName = name;
            this.description = desc;
            this.values = new ArrayList<String>();
        }
    }
    
    private static List<GroupInfo> analyzeGroups(SEIRModel model) {
        List<GroupInfo> groups = new ArrayList<GroupInfo>();
        
        for (int i = 0; i < model.getGroups().size(); i++) {
            Group group = model.getGroups().get(i);
            GroupInfo info = new GroupInfo(group.getName(), group.getDescription());
            
            // Collect all values
            for (int j = 0; j < group.getValues().size(); j++) {
                info.values.add(group.getValues().get(j));
            }
            
            if (!info.values.isEmpty()) {
                groups.add(info);
            }
        }
        
        return groups;
    }
    
    private static void customizeModelForGroup(SEIRModel model, String targetGroupValue) {
        System.out.println("    🔧 Customizing flows for group: " + targetGroupValue);
        
        // Process all flows to use group-specific rates
        int flowsProcessed = 0;
        int ratesReplaced = 0;
        
        for (int i = 0; i < model.getCompartments().size(); i++) {
            Compartment comp = model.getCompartments().get(i);
            
            for (int j = 0; j < comp.getOutgoingFlows().size(); j++) {
                Flow flow = comp.getOutgoingFlows().get(j);
                flowsProcessed++;
                
                if (processFlow(flow, targetGroupValue)) {
                    ratesReplaced++;
                }
            }
        }
        
        System.out.println("    📊 Processed " + flowsProcessed + " flows, replaced " + ratesReplaced + " rates");
        
        // Update labels
        updateLabels(model, targetGroupValue);
    }
    
    private static boolean processFlow(Flow flow, String targetGroup) {
        boolean rateReplaced = false;
        
        if (flow instanceof RateFlow) {
            RateFlow rateFlow = (RateFlow) flow;
            
            // Look for group-specific rate
            for (int k = 0; k < rateFlow.getStratumSpecificRates().size(); k++) {
                StratumSpecificRate stratumRate = rateFlow.getStratumSpecificRates().get(k);
                if (targetGroup.equals(stratumRate.getStratum())) {
                    rateFlow.setRate(stratumRate.getRate());
                    rateReplaced = true;
                    break;
                }
            }
            
            // Clear stratum-specific rates (no longer needed)
            rateFlow.getStratumSpecificRates().clear();
            
        } else if (flow instanceof ContactFlow) {
            ContactFlow contactFlow = (ContactFlow) flow;
            
            // Look for group-specific contact rate
            for (int k = 0; k < contactFlow.getStratumSpecificRates().size(); k++) {
                StratumSpecificRate stratumRate = contactFlow.getStratumSpecificRates().get(k);
                if (targetGroup.equals(stratumRate.getStratum())) {
                    contactFlow.setContactRate(stratumRate.getRate());
                    rateReplaced = true;
                    break;
                }
            }
            
            // Clear stratum-specific rates
            contactFlow.getStratumSpecificRates().clear();
        }
        
        return rateReplaced;
    }
    
    private static void updateLabels(SEIRModel model, String groupValue) {
        // Filter birth sources to only include those relevant to this group
        List<BirthSource> relevantBirthSources = new ArrayList<BirthSource>();
        for (int i = 0; i < model.getBirthSources().size(); i++) {
            BirthSource source = model.getBirthSources().get(i);
            // Keep birth source if it belongs to this group or has no stratum specified
            if (source.getTargetStratum() == null || groupValue.equals(source.getTargetStratum())) {
                relevantBirthSources.add(source);
            }
        }
        model.getBirthSources().clear();
        model.getBirthSources().addAll(relevantBirthSources);
        
        // Filter death sinks to only include those relevant to this group
        List<DeathSink> relevantDeathSinks = new ArrayList<DeathSink>();
        for (int i = 0; i < model.getDeathSinks().size(); i++) {
            DeathSink sink = model.getDeathSinks().get(i);
            // Keep death sink if it belongs to this group or has no stratum specified
            if (sink.getSourceStratum() == null || groupValue.equals(sink.getSourceStratum())) {
                relevantDeathSinks.add(sink);
            }
        }
        model.getDeathSinks().clear();
        model.getDeathSinks().addAll(relevantDeathSinks);
    }
    
    private static String generateFileName(String inputFile, String groupValue) {
        // Create safe filename
        String safeGroupValue = groupValue.replaceAll("[^a-zA-Z0-9]", "_");
        return inputFile.replace(".seirmodel", "_" + safeGroupValue + ".seirmodel");
    }
    
    private static int countStratifiedCompartments(SEIRModel model) {
        int count = 0;
        for (int i = 0; i < model.getCompartments().size(); i++) {
            if (model.getCompartments().get(i).getProduct() != null) {
                count++;
            }
        }
        return count;
    }
    
    private static int countFlows(SEIRModel model) {
        int count = 0;
        for (int i = 0; i < model.getCompartments().size(); i++) {
            count += model.getCompartments().get(i).getOutgoingFlows().size();
        }
        return count;
    }
    
    private static SEIRModel loadModel(String filePath) throws IOException {
        // Register the SEIR model package
        seirmodel.SeirmodelPackage.eINSTANCE.eClass();
        
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
            .put("seirmodel", new XMIResourceFactoryImpl());
        
        // Register the package URI
        resourceSet.getPackageRegistry().put(
            seirmodel.SeirmodelPackage.eNS_URI, 
            seirmodel.SeirmodelPackage.eINSTANCE
        );
        
        URI uri = URI.createFileURI(filePath);
        Resource resource = resourceSet.getResource(uri, true);
        
        if (resource.getContents().isEmpty()) {
            throw new IOException("Model file is empty or invalid");
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