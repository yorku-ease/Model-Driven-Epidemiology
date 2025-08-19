package seir.equationgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import seirmodel.StratumSpecificRate;
import seirmodel.BirthSource;
import seirmodel.Compartment;
import seirmodel.ContactFlow;
import seirmodel.DeathSink;
import seirmodel.Flow;
import seirmodel.Group;
import seirmodel.Product;
import seirmodel.RateFlow;
import seirmodel.SEIRModel;
import seirmodel.SeirmodelPackage;

public class SEIREquationGenerator {
    public static void main(String[] args) {
        initializeEMF();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the .seirmodel file (e.g. covid.seirmodel): ");
        String fileName = scanner.nextLine().trim();
        scanner.close();

        String modelPath = System.getProperty("user.dir") + "/" + fileName;

        SEIRModel seirModel = loadSEIRModel(modelPath);
        if (seirModel == null) {
            System.out.println("❌ Failed to load SEIR model.");
            return;
        }

        Map<String, String> equations = generateEquations(seirModel);
        
        // Also generate stratified equations if products exist
        if (!seirModel.getProducts().isEmpty()) {
            System.out.println("\n🔍 Stratification detected! Generating expanded equations...");
            Map<String, String> stratifiedEquations = generateStratifiedEquations(seirModel);
            System.out.println("✅ Generated Stratified SEIR Model Equations:");
            stratifiedEquations.forEach((compartment, equation) -> System.out.println(equation));
            
            String stratifiedOutputFileName = fileName.replace(".seirmodel", "_stratified.txt");
            saveEquationsToFile(stratifiedEquations, stratifiedOutputFileName);
        }

        System.out.println("✅ Generated SEIR Model Equations:");
        equations.forEach((compartment, equation) -> System.out.println(equation));

        String outputFileName = fileName.replace(".seirmodel", ".txt");
        saveEquationsToFile(equations, outputFileName);
    }

    private static void initializeEMF() {
        try {
            Class.forName("org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl");
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("seirmodel", new XMIResourceFactoryImpl());
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: Unable to load EMF XMI Factory.");
            e.printStackTrace();
        }
    }

    private static SEIRModel loadSEIRModel(String filePath) {
        File modelFile = new File(filePath);
        if (!modelFile.exists()) {
            System.err.println("❌ Error: Model file not found at " + filePath);
            return null;
        }

        try {
            EPackage.Registry.INSTANCE.put(SeirmodelPackage.eNS_URI, SeirmodelPackage.eINSTANCE);
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

    private static Map<String, String> generateEquations(SEIRModel model) {
        Map<String, String> equations = new HashMap<>();

        for (Compartment compartment : model.getCompartments()) {
            String primaryName = compartment.getPrimaryName();
            String secondaryName = compartment.getSecondaryName() != null ? compartment.getSecondaryName() : "";
            String displayName = primaryName + (secondaryName.isEmpty() ? "" : " (" + secondaryName + ")");
            StringBuilder equation = new StringBuilder("d" + displayName + "/dt = ");

            // Incoming flows to this compartment
            for (Compartment source : model.getCompartments()) {
                for (Flow flow : source.getOutgoingFlows()) {
                    if (flow.getTarget() == compartment) {
                        String sourceName = source.getPrimaryName();
                        String sourceSecondary = source.getSecondaryName();
                        String sourceDisplay = sourceName + (sourceSecondary != null && !sourceSecondary.isEmpty() ? " (" + sourceSecondary + ")" : "");
                        
                        if (flow instanceof ContactFlow) {
                            ContactFlow contactFlow = (ContactFlow) flow;
                            Compartment contactCompartment = contactFlow.getContactCompartment();
                            String contactName = contactCompartment.getPrimaryName();
                            String contactSecondary = contactCompartment.getSecondaryName();
                            String contactDisplay = contactName + (contactSecondary != null && !contactSecondary.isEmpty() ? " (" + contactSecondary + ")" : "");
                            
                            equation.append("+ (").append(contactFlow.getContactRate())
                                    .append(" * ").append(sourceDisplay)
                                    .append(" * ").append(contactDisplay)
                                    .append(" / ").append(model.getTotalPopulation()).append(") ");
                        } else if (flow instanceof RateFlow) {
                            RateFlow rateFlow = (RateFlow) flow;
                            equation.append("+ ").append(rateFlow.getRate()).append(" * ").append(sourceDisplay).append(" ");
                        }
                    }
                }
            }

            // Birth sources flowing into this compartment
            for (BirthSource birthSource : model.getBirthSources()) {
                if (birthSource.getTargetCompartment() == compartment) {
                    equation.append("+ ").append(birthSource.getRate()).append(" * ").append(model.getTotalPopulation()).append(" ");
                }
            }

            // Outgoing flows from this compartment
            for (Flow flow : compartment.getOutgoingFlows()) {
                if (flow instanceof ContactFlow) {
                    ContactFlow contactFlow = (ContactFlow) flow;
                    Compartment contactCompartment = contactFlow.getContactCompartment();
                    String contactName = contactCompartment.getPrimaryName();
                    String contactSecondary = contactCompartment.getSecondaryName();
                    String contactDisplay = contactName + (contactSecondary != null && !contactSecondary.isEmpty() ? " (" + contactSecondary + ")" : "");
                    
                    equation.append("- (").append(contactFlow.getContactRate())
                            .append(" * ").append(displayName)
                            .append(" * ").append(contactDisplay)
                            .append(" / ").append(model.getTotalPopulation()).append(") ");
                } else if (flow instanceof RateFlow) {
                    RateFlow rateFlow = (RateFlow) flow;
                    equation.append("- ").append(rateFlow.getRate()).append(" * ").append(displayName).append(" ");
                }
            }

            // Death sinks flowing out of this compartment
            for (DeathSink deathSink : model.getDeathSinks()) {
                if (deathSink.getSourceCompartment() == compartment) {
                    equation.append("- ").append(deathSink.getRate()).append(" * ").append(displayName).append(" ");
                }
            }

            equations.put(displayName, equation.toString());
        }

        return equations;
    }

    private static Map<String, String> generateStratifiedEquations(SEIRModel model) {
        Map<String, String> stratifiedEquations = new HashMap<>();
        
        for (Compartment compartment : model.getCompartments()) {
            Product product = compartment.getProduct();
            if (product == null) {
                // Non-stratified compartment - generate single equation
                String equation = generateCompartmentEquation(compartment, model, "");
                stratifiedEquations.put(getCompartmentDisplayName(compartment, ""), equation);
            } else {
                // Stratified compartment - expand into all product combinations
                List<String> strataCombinations = generateStrataCombinations(product);
                for (String stratum : strataCombinations) {
                    String equation = generateCompartmentEquation(compartment, model, stratum);
                    stratifiedEquations.put(getCompartmentDisplayName(compartment, stratum), equation);
                }
            }
        }
        
        return stratifiedEquations;
    }

    private static List<String> generateStrataCombinations(Product product) {
        List<String> combinations = new ArrayList<>();
        combinations.add(""); // Start with empty combination
        
        for (Group group : product.getGroups()) {
            List<String> newCombinations = new ArrayList<>();
            for (String combination : combinations) {
                for (String value : group.getValues()) {
                    String newCombination = combination.isEmpty() ? value : combination + "_" + value;
                    newCombinations.add(newCombination);
                }
            }
            combinations = newCombinations;
        }
        
        return combinations;
    }

    private static String getCompartmentDisplayName(Compartment compartment, String stratum) {
        String primaryName = compartment.getPrimaryName();
        String secondaryName = compartment.getSecondaryName() != null ? compartment.getSecondaryName() : "";
        String baseName = primaryName + (secondaryName.isEmpty() ? "" : " (" + secondaryName + ")");
        return stratum.isEmpty() ? baseName : baseName + "_" + stratum;
    }

    private static String generateCompartmentEquation(Compartment compartment, SEIRModel model, String stratum) {
        String displayName = getCompartmentDisplayName(compartment, stratum);
        StringBuilder equation = new StringBuilder("d" + displayName + "/dt = ");

        // Incoming flows to this compartment (only from matching strata)
        for (Compartment source : model.getCompartments()) {
            for (Flow flow : source.getOutgoingFlows()) {
                if (flow.getTarget() == compartment) {
                    // Only include flows if source and target have compatible stratification
                    if (areCompatibleStrata(source, compartment, stratum)) {
                        String sourceDisplay = getCompartmentDisplayName(source, stratum);
                        
                        if (flow instanceof ContactFlow) {
                            ContactFlow contactFlow = (ContactFlow) flow;
                            Compartment contactCompartment = contactFlow.getContactCompartment();
                            
                            // Only include if contact compartment also has compatible stratification
                            if (areCompatibleStrata(contactCompartment, compartment, stratum)) {
                                String contactDisplay = getCompartmentDisplayName(contactCompartment, stratum);
                                
                                // Get stratum-specific rate or default rate
                                double contactRate = getStratumSpecificRate(contactFlow, stratum, contactFlow.getContactRate());
                                double susceptibilityMultiplier = getStratumSpecificMultiplier(contactFlow, stratum);
                                
                                equation.append("+ (").append(contactRate * susceptibilityMultiplier)
                                        .append(" * ").append(sourceDisplay)
                                        .append(" * ").append(contactDisplay)
                                        .append(" / ").append(model.getTotalPopulation()).append(") ");
                            }
                        } else if (flow instanceof RateFlow) {
                            RateFlow rateFlow = (RateFlow) flow;
                            // Get stratum-specific rate or default rate
                            double rate = getStratumSpecificRate(rateFlow, stratum, rateFlow.getRate());
                            equation.append("+ ").append(rate).append(" * ").append(sourceDisplay).append(" ");
                        }
                    }
                }
            }
        }

        // Birth sources flowing into this compartment (only for matching strata)
        for (BirthSource birthSource : model.getBirthSources()) {
            if (birthSource.getTargetCompartment() == compartment) {
                // Only add birth flow if stratum matches or compartment is not stratified
                Product targetProduct = compartment.getProduct();
                if (targetProduct == null || stratum.isEmpty()) {
                    // Non-stratified compartment - add full birth rate
                    equation.append("+ ").append(birthSource.getRate()).append(" ");
                } else {
                    // Stratified compartment - divide birth rate among strata (could be enhanced with stratum-specific birth rates)
                    List<String> allStrata = generateStrataCombinations(targetProduct);
                    double stratumBirthRate = birthSource.getRate() / allStrata.size();
                    equation.append("+ ").append(stratumBirthRate).append(" ");
                }
            }
        }

        // Outgoing flows from this compartment
        for (Flow flow : compartment.getOutgoingFlows()) {
            if (flow instanceof ContactFlow) {
                ContactFlow contactFlow = (ContactFlow) flow;
                Compartment contactCompartment = contactFlow.getContactCompartment();
                String contactDisplay = getCompartmentDisplayName(contactCompartment, stratum);
                
                // Get stratum-specific rate or default rate
                double contactRate = getStratumSpecificRate(contactFlow, stratum, contactFlow.getContactRate());
                double susceptibilityMultiplier = getStratumSpecificMultiplier(contactFlow, stratum);
                
                equation.append("- (").append(contactRate * susceptibilityMultiplier)
                        .append(" * ").append(displayName)
                        .append(" * ").append(contactDisplay)
                        .append(" / ").append(model.getTotalPopulation()).append(") ");
            } else if (flow instanceof RateFlow) {
                RateFlow rateFlow = (RateFlow) flow;
                // Get stratum-specific rate or default rate
                double rate = getStratumSpecificRate(rateFlow, stratum, rateFlow.getRate());
                equation.append("- ").append(rate).append(" * ").append(displayName).append(" ");
            }
        }

        // Death sinks flowing out of this compartment
        for (DeathSink deathSink : model.getDeathSinks()) {
            if (deathSink.getSourceCompartment() == compartment) {
                equation.append("- ").append(deathSink.getRate()).append(" * ").append(displayName).append(" ");
            }
        }

        return equation.toString();
    }

    private static double getStratumSpecificRate(ContactFlow contactFlow, String stratum, double defaultRate) {
        if (stratum.isEmpty() || contactFlow.getStratumSpecificRates().isEmpty()) {
            return defaultRate;
        }
        
        // Extract first stratum value from combined stratum (first part before underscore)
        String stratumValue = stratum.contains("_") ? stratum.split("_")[0] : stratum;
        
        for (StratumSpecificRate stratumRate : contactFlow.getStratumSpecificRates()) {
            if (stratumValue.equals(stratumRate.getStratum())) {
                return stratumRate.getRate();
            }
        }
        
        return defaultRate;
    }

    private static double getStratumSpecificRate(RateFlow rateFlow, String stratum, double defaultRate) {
        if (stratum.isEmpty() || rateFlow.getStratumSpecificRates().isEmpty()) {
            return defaultRate;
        }
        
        // Extract first stratum value from combined stratum (first part before underscore)
        String stratumValue = stratum.contains("_") ? stratum.split("_")[0] : stratum;
        
        for (StratumSpecificRate stratumRate : rateFlow.getStratumSpecificRates()) {
            if (stratumValue.equals(stratumRate.getStratum())) {
                return stratumRate.getRate();
            }
        }
        
        return defaultRate;
    }

    private static double getStratumSpecificMultiplier(ContactFlow contactFlow, String stratum) {
        if (stratum.isEmpty() || contactFlow.getStratumSpecificRates().isEmpty()) {
            return 1.0; // Default multiplier (no effect)
        }
        
        // Extract first stratum value from combined stratum (first part before underscore)
        String stratumValue = stratum.contains("_") ? stratum.split("_")[0] : stratum;
        
        for (StratumSpecificRate stratumRate : contactFlow.getStratumSpecificRates()) {
            if (stratumValue.equals(stratumRate.getStratum())) {
                return stratumRate.getMultiplier();
            }
        }
        
        return 1.0; // Default multiplier (no effect)
    }

    /**
     * Check if two compartments have compatible stratification for the given stratum.
     * Compatible means:
     * 1. Both are non-stratified (product == null)
     * 2. Both have the same product (same stratification scheme)
     * 3. One is stratified and the other is not (mixed interaction)
     */
    private static boolean areCompatibleStrata(Compartment source, Compartment target, String stratum) {
        Product sourceProduct = source.getProduct();
        Product targetProduct = target.getProduct();
        
        // If stratum is empty, we're generating non-stratified equations
        if (stratum.isEmpty()) {
            return sourceProduct == null && targetProduct == null;
        }
        
        // For stratified equations, only include if both compartments have the same stratification
        // or if one is stratified and the other is not (for mixed interactions)
        if (sourceProduct != null && targetProduct != null) {
            return sourceProduct == targetProduct; // Same stratification scheme
        } else if (sourceProduct == null && targetProduct != null) {
            return false; // Non-stratified source to stratified target (skip to avoid duplication)
        } else if (sourceProduct != null && targetProduct == null) {
            return false; // Stratified source to non-stratified target (skip to avoid duplication)
        } else {
            return true; // Both non-stratified
        }
    }

    private static void saveEquationsToFile(Map<String, String> equations, String outputFileName) {
        try (FileWriter writer = new FileWriter(outputFileName)) {
            for (Map.Entry<String, String> entry : equations.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            System.out.println("✅ Equations saved to " + outputFileName);
        } catch (IOException e) {
            System.err.println("❌ Error: Failed to save equations.");
            e.printStackTrace();
        }
    }
}
