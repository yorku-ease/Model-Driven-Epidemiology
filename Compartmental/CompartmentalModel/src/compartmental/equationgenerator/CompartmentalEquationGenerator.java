package compartmental.equationgenerator;

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
import compartmentalmodel.StratumSpecificRate;
import compartmentalmodel.BirthSource;
import compartmentalmodel.Compartment;
import compartmentalmodel.ContactFlow;
import compartmentalmodel.DeathSink;
import compartmentalmodel.Flow;
import compartmentalmodel.Group;
import compartmentalmodel.Parameter;
import compartmentalmodel.Product;
import compartmentalmodel.RateFlow;
import compartmentalmodel.CompartmentalModel;
import compartmentalmodel.CompartmentalmodelPackage;

public class CompartmentalEquationGenerator {
    public static void main(String[] args) {
        initializeEMF();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the .compmodel file (e.g. covid.compmodel): ");
        String fileName = scanner.nextLine().trim();
        scanner.close();

        String modelPath = System.getProperty("user.dir") + "/" + fileName;

        CompartmentalModel seirModel = loadCompartmentalModel(modelPath);
        if (seirModel == null) {
            System.out.println("❌ Failed to load compartmental model.");
            return;
        }

        Map<String, String> equations = generateEquations(seirModel);
        
        // Also generate stratified equations if products exist
        if (!seirModel.getProducts().isEmpty()) {
            System.out.println("\n🔍 Stratification detected! Generating expanded equations...");
            Map<String, String> stratifiedEquations = generateStratifiedEquations(seirModel);
            System.out.println("✅ Generated Stratified Compartmental Model Equations:");
            stratifiedEquations.forEach((compartment, equation) -> System.out.println(equation));
            
            String stratifiedOutputFileName = fileName.replace(".compmodel", "_stratified.txt");
            saveEquationsToFile(stratifiedEquations, stratifiedOutputFileName);
        }

        System.out.println("✅ Generated Compartmental Model Equations:");
        equations.forEach((compartment, equation) -> System.out.println(equation));

        String outputFileName = fileName.replace(".compmodel", ".txt");
        saveEquationsToFile(equations, outputFileName);
    }

    private static void initializeEMF() {
        try {
            Class.forName("org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl");
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("compmodel", new XMIResourceFactoryImpl());
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: Unable to load EMF XMI Factory.");
            e.printStackTrace();
        }
    }

    private static CompartmentalModel loadCompartmentalModel(String filePath) {
        File modelFile = new File(filePath);
        if (!modelFile.exists()) {
            System.err.println("❌ Error: Model file not found at " + filePath);
            return null;
        }

        try {
            EPackage.Registry.INSTANCE.put(CompartmentalmodelPackage.eNS_URI, CompartmentalmodelPackage.eINSTANCE);
            ResourceSet resourceSet = new ResourceSetImpl();
            Resource resource = resourceSet.getResource(URI.createFileURI(modelFile.getAbsolutePath()), true);
            resource.load(null);
            return (CompartmentalModel) resource.getContents().get(0);
        } catch (IOException | IndexOutOfBoundsException e) {
            System.err.println("❌ Error: Failed to load compartmental model.");
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, String> generateEquations(CompartmentalModel model) {
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

                            String contactRateExpr = getContactRateExpression(contactFlow, "");
                            equation.append("+ (").append(contactRateExpr)
                                    .append(" * ").append(sourceDisplay)
                                    .append(" * ").append(contactDisplay)
                                    .append(" / ").append(model.getTotalPopulation()).append(") ");
                        } else if (flow instanceof RateFlow) {
                            RateFlow rateFlow = (RateFlow) flow;
                            String rateExpr = getRateExpression(rateFlow, "");
                            equation.append("+ ").append(rateExpr).append(" * ").append(sourceDisplay).append(" ");
                        }
                    }
                }
            }

            // Birth sources flowing into this compartment
            for (BirthSource birthSource : model.getBirthSources()) {
                if (birthSource.getTargetCompartment() == compartment) {
                    String birthRateExpr = getBirthRateExpression(birthSource);
                    if (isFixedRateBirthSource(birthSource)) {
                        equation.append("+ ").append(birthRateExpr).append(" ");
                    } else {
                        equation.append("+ ").append(birthRateExpr).append(" * ").append(model.getTotalPopulation()).append(" ");
                    }
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

                    String contactRateExpr = getContactRateExpression(contactFlow, "");
                    equation.append("- (").append(contactRateExpr)
                            .append(" * ").append(displayName)
                            .append(" * ").append(contactDisplay)
                            .append(" / ").append(model.getTotalPopulation()).append(") ");
                } else if (flow instanceof RateFlow) {
                    RateFlow rateFlow = (RateFlow) flow;
                    String rateExpr = getRateExpression(rateFlow, "");
                    equation.append("- ").append(rateExpr).append(" * ").append(displayName).append(" ");
                }
            }

            // Death sinks flowing out of this compartment
            for (DeathSink deathSink : model.getDeathSinks()) {
                if (deathSink.getSourceCompartment() == compartment) {
                    String deathRateExpr = getDeathRateExpression(deathSink);
                    equation.append("- ").append(deathRateExpr).append(" * ").append(displayName).append(" ");
                }
            }

            equations.put(displayName, equation.toString());
        }

        return equations;
    }

    private static Map<String, String> generateStratifiedEquations(CompartmentalModel model) {
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
                    // Use comma as separator to match model format (e.g., "0-17,Male")
                    String newCombination = combination.isEmpty() ? value : combination + "," + value;
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
        if (stratum.isEmpty()) {
            return baseName;
        }
        // Replace commas with underscores for display/file naming
        String displayStratum = stratum.replace(",", "_");
        return baseName + "_" + displayStratum;
    }

    private static String generateCompartmentEquation(Compartment compartment, CompartmentalModel model, String stratum) {
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
                            
                            // CRITICAL: Only include if this flow applies to the current stratum
                            if (isFlowApplicableToStratum(contactFlow, stratum) && areCompatibleStrata(contactCompartment, compartment, stratum)) {
                                String contactDisplay = getCompartmentDisplayName(contactCompartment, stratum);

                                // Get stratum-specific rate expression or default rate expression
                                String contactRateExpr = getContactRateExpression(contactFlow, stratum);
                                String multiplierExpr = getMultiplierExpression(contactFlow, stratum);

                                if (!multiplierExpr.equals("1.0") && !multiplierExpr.equals("1")) {
                                    equation.append("+ (").append(contactRateExpr).append(" * ").append(multiplierExpr)
                                            .append(" * ").append(sourceDisplay)
                                            .append(" * ").append(contactDisplay)
                                            .append(" / ").append(model.getTotalPopulation()).append(") ");
                                } else {
                                    equation.append("+ (").append(contactRateExpr)
                                            .append(" * ").append(sourceDisplay)
                                            .append(" * ").append(contactDisplay)
                                            .append(" / ").append(model.getTotalPopulation()).append(") ");
                                }
                            }
                        } else if (flow instanceof RateFlow) {
                            RateFlow rateFlow = (RateFlow) flow;
                            // Get stratum-specific rate expression or default rate expression
                            String rateExpr = getRateExpression(rateFlow, stratum);
                            equation.append("+ ").append(rateExpr).append(" * ").append(sourceDisplay).append(" ");
                        }
                    }
                }
            }
        }

        // Birth sources flowing into this compartment (only for matching strata)
        for (BirthSource birthSource : model.getBirthSources()) {
            if (birthSource.getTargetCompartment() == compartment) {
                // Check if this birth source applies to the current stratum
                if (isBirthSourceApplicableToStratum(birthSource, stratum)) {
                    String birthRateExpr = getBirthRateExpression(birthSource);

                    // Check if this is a fixed rate or population-based rate
                    if (isFixedRateBirthSource(birthSource)) {
                        // Fixed rate - don't multiply by population
                        equation.append("+ ").append(birthRateExpr).append(" ");
                    } else {
                        // Population-based rate - multiply by total population (legacy behavior)
                        equation.append("+ ").append(birthRateExpr).append(" * ").append(model.getTotalPopulation()).append(" ");
                    }
                }
            }
        }

        // Outgoing flows from this compartment
        for (Flow flow : compartment.getOutgoingFlows()) {
            if (flow instanceof ContactFlow) {
                ContactFlow contactFlow = (ContactFlow) flow;

                // CRITICAL: Only include if this flow applies to the current stratum
                if (isFlowApplicableToStratum(contactFlow, stratum)) {
                    Compartment contactCompartment = contactFlow.getContactCompartment();
                    String contactDisplay = getCompartmentDisplayName(contactCompartment, stratum);

                    // Get stratum-specific rate expression or default rate expression
                    String contactRateExpr = getContactRateExpression(contactFlow, stratum);
                    String multiplierExpr = getMultiplierExpression(contactFlow, stratum);

                    if (!multiplierExpr.equals("1.0") && !multiplierExpr.equals("1")) {
                        equation.append("- (").append(contactRateExpr).append(" * ").append(multiplierExpr)
                                .append(" * ").append(displayName)
                                .append(" * ").append(contactDisplay)
                                .append(" / ").append(model.getTotalPopulation()).append(") ");
                    } else {
                        equation.append("- (").append(contactRateExpr)
                                .append(" * ").append(displayName)
                                .append(" * ").append(contactDisplay)
                                .append(" / ").append(model.getTotalPopulation()).append(") ");
                    }
                }
            } else if (flow instanceof RateFlow) {
                RateFlow rateFlow = (RateFlow) flow;

                // For RateFlow, check if it has stratum-specific rates and if they apply
                if (isFlowApplicableToStratum(rateFlow, stratum)) {
                    // Get stratum-specific rate expression or default rate expression
                    String rateExpr = getRateExpression(rateFlow, stratum);
                    equation.append("- ").append(rateExpr).append(" * ").append(displayName).append(" ");
                }
            }
        }

        // Death sinks flowing out of this compartment (with stratum matching)
        for (DeathSink deathSink : model.getDeathSinks()) {
            if (deathSink.getSourceCompartment() == compartment) {
                // Check if death sink applies to this stratum
                if (isDeathSinkApplicableToStratum(deathSink, stratum)) {
                    String deathRateExpr = getDeathRateExpression(deathSink);
                    equation.append("- ").append(deathRateExpr).append(" * ").append(displayName).append(" ");
                }
            }
        }

        return equation.toString();
    }

    private static double getStratumSpecificRate(ContactFlow contactFlow, String stratum, double defaultRate) {
        if (stratum.isEmpty() || contactFlow.getStratumSpecificRates().isEmpty()) {
            return defaultRate;
        }

        // Try exact match first (for Cartesian products like "0-17,Male")
        for (StratumSpecificRate stratumRate : contactFlow.getStratumSpecificRates()) {
            if (stratum.equals(stratumRate.getStratum())) {
                // If rate is 0, use multiplier instead
                if (stratumRate.getRate() == 0.0 && stratumRate.getMultiplier() != 0.0) {
                    return defaultRate * stratumRate.getMultiplier();
                }
                return stratumRate.getRate();
            }
        }

        return defaultRate;
    }

    private static double getStratumSpecificRate(RateFlow rateFlow, String stratum, double defaultRate) {
        if (stratum.isEmpty() || rateFlow.getStratumSpecificRates().isEmpty()) {
            return defaultRate;
        }

        // Try exact match first (for Cartesian products like "0-17,Male")
        for (StratumSpecificRate stratumRate : rateFlow.getStratumSpecificRates()) {
            if (stratum.equals(stratumRate.getStratum())) {
                // If rate is 0, use multiplier instead
                if (stratumRate.getRate() == 0.0 && stratumRate.getMultiplier() != 0.0) {
                    return defaultRate * stratumRate.getMultiplier();
                }
                return stratumRate.getRate();
            }
        }

        return defaultRate;
    }

    private static double getStratumSpecificMultiplier(ContactFlow contactFlow, String stratum) {
        if (stratum.isEmpty() || contactFlow.getStratumSpecificRates().isEmpty()) {
            return 1.0; // Default multiplier (no effect)
        }

        // Try exact match (for Cartesian products like "0-17,Male")
        for (StratumSpecificRate stratumRate : contactFlow.getStratumSpecificRates()) {
            if (stratum.equals(stratumRate.getStratum())) {
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

    /**
     * Check if a flow applies to the given stratum.
     * A flow applies if:
     * 1. It has no stratum-specific rates (applies to all)
     * 2. It has stratum-specific rates and one matches the current stratum
     */
    private static boolean isFlowApplicableToStratum(ContactFlow contactFlow, String stratum) {
        // If no stratum specified, apply to all non-stratified
        if (stratum.isEmpty()) {
            return true;
        }

        // If flow has no stratum-specific rates, it applies to all
        if (contactFlow.getStratumSpecificRates().isEmpty()) {
            return true;
        }

        // Check if any stratum-specific rate exactly matches the current stratum (e.g., "0-17,Male")
        for (StratumSpecificRate stratumRate : contactFlow.getStratumSpecificRates()) {
            if (stratum.equals(stratumRate.getStratum())) {
                return true;
            }
        }

        // Flow has stratum-specific rates but none match - doesn't apply
        return false;
    }

    /**
     * Check if a RateFlow applies to the given stratum.
     */
    private static boolean isFlowApplicableToStratum(RateFlow rateFlow, String stratum) {
        // If no stratum specified, apply to all non-stratified
        if (stratum.isEmpty()) {
            return true;
        }

        // If flow has no stratum-specific rates, it applies to all strata
        if (rateFlow.getStratumSpecificRates().isEmpty()) {
            return true;
        }

        // Check if any stratum-specific rate exactly matches the current stratum (e.g., "0-17,Male")
        for (StratumSpecificRate stratumRate : rateFlow.getStratumSpecificRates()) {
            if (stratum.equals(stratumRate.getStratum())) {
                return true;
            }
        }

        // Flow has stratum-specific rates but none match - doesn't apply
        return false;
    }

    /**
     * Check if a birth source applies to the given stratum.
     * Uses the targetStratum attribute if available, otherwise applies to all.
     */
    private static boolean isBirthSourceApplicableToStratum(BirthSource birthSource, String stratum) {
        // If no stratum specified, birth source applies to non-stratified compartments
        if (stratum.isEmpty()) {
            return true;
        }

        // Extract targetStratum from EMF object using reflection
        String targetStratum = birthSource.getTargetStratum();

        if (targetStratum != null && !targetStratum.isEmpty()) {
            // Exact match for Cartesian products (e.g., "0-17,Male")
            return targetStratum.equals(stratum);
        }

        // If no targetStratum specified, don't apply to stratified compartments (avoid duplication)
        return false;
    }

    /**
     * Check if a death sink applies to the given stratum.
     * Uses the sourceStratum attribute if available, otherwise applies to all.
     */
    private static boolean isDeathSinkApplicableToStratum(DeathSink deathSink, String stratum) {
        // If no stratum specified, death sink applies to non-stratified compartments
        if (stratum.isEmpty()) {
            return true;
        }

        // Extract sourceStratum from death sink
        String sourceStratum = deathSink.getSourceStratum();

        if (sourceStratum != null && !sourceStratum.isEmpty()) {
            // Exact match for Cartesian products (e.g., "0-17,Male")
            return sourceStratum.equals(stratum);
        }

        // If no sourceStratum specified, don't apply to stratified compartments (avoid duplication)
        return false;
    }


    /**
     * Check if a birth source uses fixed rate (not population-based)
     * Tries to extract fixedRate attribute from EMF object
     */
    private static boolean isFixedRateBirthSource(BirthSource birthSource) {
        try {
            // Try reflection to get the fixedRate attribute
            Object eObject = birthSource;
            if (eObject instanceof org.eclipse.emf.ecore.EObject) {
                org.eclipse.emf.ecore.EObject eo = (org.eclipse.emf.ecore.EObject) eObject;
                org.eclipse.emf.ecore.EClass eClass = eo.eClass();
                
                // Look for fixedRate attribute
                for (org.eclipse.emf.ecore.EAttribute attr : eClass.getEAllAttributes()) {
                    if ("fixedRate".equals(attr.getName())) {
                        Object value = eo.eGet(attr);
                        if (value instanceof Boolean) {
                            return (Boolean) value;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not extract fixedRate from birth source '" + 
                             birthSource.getName() + "'. Consider regenerating EMF classes.");
        }
        
        // Default to population-based (legacy behavior) if no fixedRate attribute found
        return false;
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

    /**
     * Get contact rate as either a symbolic parameter name or numeric value.
     * Prioritizes parameter reference over numeric attribute.
     *
     * For CONSTANT parameters: Returns the parameter NAME (e.g., "mu1")
     * For EXPRESSION parameters: Returns the expression (e.g., "eta_S * IM")
     * For numeric attributes: Returns the numeric value (e.g., "0.5")
     */
    private static String getContactRateExpression(ContactFlow flow, String stratum) {
        // Check for stratum-specific parameter first
        if (!stratum.isEmpty() && !flow.getStratumSpecificRates().isEmpty()) {
            for (StratumSpecificRate ssr : flow.getStratumSpecificRates()) {
                if (stratum.equals(ssr.getStratum())) {
                    Parameter ssrParam = ssr.getRateParameter();
                    if (ssrParam != null) {
                        // Return parameter name for CONSTANT/VARIABLE, expression for EXPRESSION
                        return getParameterRepresentation(ssrParam);
                    } else if (ssr.getRate() != 0.0) {
                        return String.valueOf(ssr.getRate());
                    }
                }
            }
        }

        // Fall back to flow-level parameter or numeric value
        Parameter param = flow.getContactRateParameter();
        if (param != null) {
            return getParameterRepresentation(param);
        } else if (flow.getContactRate() != 0.0) {
            return String.valueOf(flow.getContactRate());
        } else {
            return "UNKNOWN_CONTACT_RATE";
        }
    }

    /**
     * Get rate for RateFlow as symbolic parameter name or numeric value.
     */
    private static String getRateExpression(RateFlow flow, String stratum) {
        // Check for stratum-specific parameter first
        if (!stratum.isEmpty() && !flow.getStratumSpecificRates().isEmpty()) {
            for (StratumSpecificRate ssr : flow.getStratumSpecificRates()) {
                if (stratum.equals(ssr.getStratum())) {
                    Parameter ssrParam = ssr.getRateParameter();
                    if (ssrParam != null) {
                        return getParameterRepresentation(ssrParam);
                    } else if (ssr.getRate() != 0.0) {
                        return String.valueOf(ssr.getRate());
                    }
                }
            }
        }

        // Fall back to flow-level parameter or numeric value
        Parameter param = flow.getRateParameter();
        if (param != null) {
            return getParameterRepresentation(param);
        } else if (flow.getRate() != 0.0) {
            return String.valueOf(flow.getRate());
        } else {
            return "UNKNOWN_RATE";
        }
    }

    /**
     * Get birth rate as symbolic parameter name or numeric value.
     */
    private static String getBirthRateExpression(BirthSource birthSource) {
        Parameter param = birthSource.getRateParameter();
        if (param != null) {
            return getParameterRepresentation(param);
        } else if (birthSource.getRate() != 0.0) {
            return String.valueOf(birthSource.getRate());
        } else {
            return "UNKNOWN_BIRTH_RATE";
        }
    }

    /**
     * Get death rate as symbolic parameter name or numeric value.
     */
    private static String getDeathRateExpression(DeathSink deathSink) {
        Parameter param = deathSink.getRateParameter();
        if (param != null) {
            return getParameterRepresentation(param);
        } else if (deathSink.getRate() != 0.0) {
            return String.valueOf(deathSink.getRate());
        } else {
            return "UNKNOWN_DEATH_RATE";
        }
    }

    /**
     * Get multiplier as symbolic parameter name or numeric value.
     */
    private static String getMultiplierExpression(ContactFlow flow, String stratum) {
        if (stratum.isEmpty() || flow.getStratumSpecificRates().isEmpty()) {
            return "1.0";
        }

        for (StratumSpecificRate ssr : flow.getStratumSpecificRates()) {
            if (stratum.equals(ssr.getStratum())) {
                Parameter multiplierParam = ssr.getMultiplierParameter();
                if (multiplierParam != null) {
                    return getParameterRepresentation(multiplierParam);
                } else if (ssr.getMultiplier() != 0.0 && ssr.getMultiplier() != 1.0) {
                    return String.valueOf(ssr.getMultiplier());
                }
            }
        }

        return "1.0";
    }

    /**
     * Get the appropriate representation for a parameter:
     * - For CONSTANT or VARIABLE: Return the parameter NAME (e.g., "pi", "mu1")
     * - For EXPRESSION: Return the expression itself (e.g., "eta_S * IM")
     */
    private static String getParameterRepresentation(Parameter param) {
        if (param == null) {
            return "UNKNOWN_PARAM";
        }

        // Check parameter type
        String typeName = param.getType() != null ? param.getType().toString() : "";

        if ("EXPRESSION".equals(typeName)) {
            // For EXPRESSION type, return the full expression
            String expr = param.getExpression();
            return (expr != null && !expr.isEmpty()) ? expr : param.getName();
        } else {
            // For CONSTANT or VARIABLE, return just the parameter name
            String name = param.getName();
            return (name != null && !name.isEmpty()) ? name : param.getExpression();
        }
    }
}
