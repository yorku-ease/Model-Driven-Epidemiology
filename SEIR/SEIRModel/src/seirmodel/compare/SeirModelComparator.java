package seirmodel.compare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seirmodel.BirthSource;
import seirmodel.Compartment;
import seirmodel.ContactFlow;
import seirmodel.DeathSink;
import seirmodel.Flow;
import seirmodel.Group;
import seirmodel.Product;
import seirmodel.RateFlow;
import seirmodel.SEIRModel;

public class SeirModelComparator {

    private final String leftModelName;   // Model 1
    private final String rightModelName;  // Model 2

    public SeirModelComparator(String leftModelName, String rightModelName) {
        this.leftModelName = leftModelName;
        this.rightModelName = rightModelName;
    }

    public List<ModelDifference> compare(SEIRModel left, SEIRModel right) {
        List<ModelDifference> diffs = new ArrayList<>();

        // --------------------------------------------------------------------
        // 1) Compartments (Domain-based hierarchical matching)
        //    - Match by PrimaryName
        //    - Then compare Secondary variants as "subcompartments"
        // --------------------------------------------------------------------
        Map<String, Map<String, Compartment>> leftByPrimary = indexCompartmentsByPrimary(left);
        Map<String, Map<String, Compartment>> rightByPrimary = indexCompartmentsByPrimary(right);

        // Primary-level added/removed
        for (String primary : leftByPrimary.keySet()) {
            if (!rightByPrimary.containsKey(primary)) {
                diffs.add(new ModelDifference(
                        DifferenceType.COMPARTMENT_REMOVED,
                        "Compartment",
                        primary,
                        leftModelName,
                        null
                ));
            }
        }
        for (String primary : rightByPrimary.keySet()) {
            if (!leftByPrimary.containsKey(primary)) {
                diffs.add(new ModelDifference(
                        DifferenceType.COMPARTMENT_ADDED,
                        "Compartment",
                        primary,
                        null,
                        rightModelName
                ));
            }
        }

        // Secondary-level added/removed (only where primary exists in both)
        for (String primary : leftByPrimary.keySet()) {
            if (!rightByPrimary.containsKey(primary)) continue;

            Map<String, Compartment> leftSecondaries = leftByPrimary.get(primary);
            Map<String, Compartment> rightSecondaries = rightByPrimary.get(primary);

            for (String secKey : leftSecondaries.keySet()) {
                if (!rightSecondaries.containsKey(secKey)) {
                    diffs.add(new ModelDifference(
                            DifferenceType.COMPARTMENT_REMOVED,
                            "Compartment",
                            formatCompName(primary, secKey),
                            leftModelName,
                            null
                    ));
                }
            }
            for (String secKey : rightSecondaries.keySet()) {
                if (!leftSecondaries.containsKey(secKey)) {
                    diffs.add(new ModelDifference(
                            DifferenceType.COMPARTMENT_ADDED,
                            "Compartment",
                            formatCompName(primary, secKey),
                            null,
                            rightModelName
                    ));
                }
            }
        }

        // --------------------------------------------------------------------
        // 2) Flows (compare flows inside ALL compartments that exist in both models)
        //    We collect flows from all matched compartments (primary+secondary)
        // --------------------------------------------------------------------
        Map<String, FlowInfo> leftFlows = collectFlowsFromHierarchicalIndex(leftByPrimary);
        Map<String, FlowInfo> rightFlows = collectFlowsFromHierarchicalIndex(rightByPrimary);

        for (String flowKey : leftFlows.keySet()) {
            if (!rightFlows.containsKey(flowKey)) {
                diffs.add(new ModelDifference(
                        DifferenceType.FLOW_REMOVED,
                        "Flow",
                        flowKey,
                        leftModelName,
                        null
                ));
            }
        }
        for (String flowKey : rightFlows.keySet()) {
            if (!leftFlows.containsKey(flowKey)) {
                diffs.add(new ModelDifference(
                        DifferenceType.FLOW_ADDED,
                        "Flow",
                        flowKey,
                        null,
                        rightModelName
                ));
            }
        }

        // Compare properties where "same" flow exists in both
        for (String flowKey : leftFlows.keySet()) {
            if (!rightFlows.containsKey(flowKey)) continue;

            FlowInfo lf = leftFlows.get(flowKey);
            FlowInfo rf = rightFlows.get(flowKey);

            // RateFlow: compare rate
            if (lf.flow instanceof RateFlow && rf.flow instanceof RateFlow) {
                double lRate = safeRate((RateFlow) lf.flow);
                double rRate = safeRate((RateFlow) rf.flow);
                if (!sameDouble(lRate, rRate)) {
                    diffs.add(new ModelDifference(
                            DifferenceType.RATE_CHANGED,
                            "Flow",
                            flowKey,
                            lRate,
                            rRate
                    ));
                }
            }

            // ContactFlow: compare contactRate + contactCompartment
            if (lf.flow instanceof ContactFlow && rf.flow instanceof ContactFlow) {
                ContactFlow lcf = (ContactFlow) lf.flow;
                ContactFlow rcf = (ContactFlow) rf.flow;

                double lCR = safeContactRate(lcf);
                double rCR = safeContactRate(rcf);
                if (!sameDouble(lCR, rCR)) {
                    diffs.add(new ModelDifference(
                            DifferenceType.CONTACT_RATE_CHANGED,
                            "Flow",
                            flowKey,
                            lCR,
                            rCR
                    ));
                }

                String lContactComp = compartmentKey(lcf.getContactCompartment());
                String rContactComp = compartmentKey(rcf.getContactCompartment());
                if (!eq(lContactComp, rContactComp)) {
                    diffs.add(new ModelDifference(
                            DifferenceType.CONTACT_COMPARTMENT_CHANGED,
                            "Flow",
                            flowKey,
                            lContactComp,
                            rContactComp
                    ));
                }
            }

            // Type mismatch: removed + added
            if ((lf.flow instanceof ContactFlow && rf.flow instanceof RateFlow)
                    || (lf.flow instanceof RateFlow && rf.flow instanceof ContactFlow)) {
                diffs.add(new ModelDifference(
                        DifferenceType.FLOW_REMOVED,
                        "Flow",
                        flowKey + " (type mismatch)",
                        leftModelName,
                        null
                ));
                diffs.add(new ModelDifference(
                        DifferenceType.FLOW_ADDED,
                        "Flow",
                        flowKey + " (type mismatch)",
                        null,
                        rightModelName
                ));
            }
        }

        // --------------------------------------------------------------------
        // 3) BirthSources
        // --------------------------------------------------------------------
        Map<String, BirthSource> leftBirths = indexBirthSources(left);
        Map<String, BirthSource> rightBirths = indexBirthSources(right);

        for (String k : leftBirths.keySet()) {
            if (!rightBirths.containsKey(k)) {
                diffs.add(new ModelDifference(
                        DifferenceType.BIRTHSOURCE_REMOVED,
                        "BirthSource",
                        k,
                        leftModelName,
                        null
                ));
            }
        }
        for (String k : rightBirths.keySet()) {
            if (!leftBirths.containsKey(k)) {
                diffs.add(new ModelDifference(
                        DifferenceType.BIRTHSOURCE_ADDED,
                        "BirthSource",
                        k,
                        null,
                        rightModelName
                ));
            }
        }

        // --------------------------------------------------------------------
        // 4) DeathSinks
        // --------------------------------------------------------------------
        Map<String, DeathSink> leftDeaths = indexDeathSinks(left);
        Map<String, DeathSink> rightDeaths = indexDeathSinks(right);

        for (String k : leftDeaths.keySet()) {
            if (!rightDeaths.containsKey(k)) {
                diffs.add(new ModelDifference(
                        DifferenceType.DEATHSINK_REMOVED,
                        "DeathSink",
                        k,
                        leftModelName,
                        null
                ));
            }
        }
        for (String k : rightDeaths.keySet()) {
            if (!leftDeaths.containsKey(k)) {
                diffs.add(new ModelDifference(
                        DifferenceType.DEATHSINK_ADDED,
                        "DeathSink",
                        k,
                        null,
                        rightModelName
                ));
            }
        }

        // --------------------------------------------------------------------
        // 5) Groups
        // --------------------------------------------------------------------
        Map<String, Group> leftGroups = indexGroups(left);
        Map<String, Group> rightGroups = indexGroups(right);

        for (String k : leftGroups.keySet()) {
            if (!rightGroups.containsKey(k)) {
                diffs.add(new ModelDifference(
                        DifferenceType.GROUP_REMOVED,
                        "Group",
                        k,
                        leftModelName,
                        null
                ));
            }
        }
        for (String k : rightGroups.keySet()) {
            if (!leftGroups.containsKey(k)) {
                diffs.add(new ModelDifference(
                        DifferenceType.GROUP_ADDED,
                        "Group",
                        k,
                        null,
                        rightModelName
                ));
            }
        }

        // --------------------------------------------------------------------
        // 6) Products
        // --------------------------------------------------------------------
        Map<String, Product> leftProducts = indexProducts(left);
        Map<String, Product> rightProducts = indexProducts(right);

        for (String k : leftProducts.keySet()) {
            if (!rightProducts.containsKey(k)) {
                diffs.add(new ModelDifference(
                        DifferenceType.PRODUCT_REMOVED,
                        "Product",
                        k,
                        leftModelName,
                        null
                ));
            }
        }
        for (String k : rightProducts.keySet()) {
            if (!leftProducts.containsKey(k)) {
                diffs.add(new ModelDifference(
                        DifferenceType.PRODUCT_ADDED,
                        "Product",
                        k,
                        null,
                        rightModelName
                ));
            }
        }

        return diffs;
    }

    // ======================================================================
    // Helpers (Compartments: hierarchical indexing)
    // ======================================================================

    /**
     * Returns:
     *  primaryName -> (secondaryKey -> Compartment)
     * where secondaryKey is "" when there is no secondary name.
     */
    private Map<String, Map<String, Compartment>> indexCompartmentsByPrimary(SEIRModel model) {
        Map<String, Map<String, Compartment>> out = new HashMap<>();
        if (model == null || model.getCompartments() == null) return out;

        for (Compartment c : model.getCompartments()) {
            String primary = safeStr(c.getPrimaryName());
            String secondary = safeStr(c.getSecondaryName()); // "" if none

            out.putIfAbsent(primary, new HashMap<>());
            out.get(primary).put(secondary, c);
        }
        return out;
    }

    private static String formatCompName(String primary, String secondaryKey) {
        if (secondaryKey == null || secondaryKey.isBlank()) return primary;
        return primary + " (" + secondaryKey + ")";
    }

    // Keep your original "full identity" key for places that need it (flows/contact compartment printing)
    private String compartmentKey(Compartment c) {
        if (c == null) return "null";
        String p = safeStr(c.getPrimaryName());
        String s = safeStr(c.getSecondaryName());
        if (s.isBlank()) return p;
        return p + " (" + s + ")";
    }

    // ======================================================================
    // Helpers (Flows)
    // ======================================================================

    private Map<String, FlowInfo> collectFlowsFromHierarchicalIndex(Map<String, Map<String, Compartment>> byPrimary) {
        Map<String, FlowInfo> map = new HashMap<>();

        for (Map<String, Compartment> secMap : byPrimary.values()) {
            for (Compartment c : secMap.values()) {
                if (c.getOutgoingFlows() == null) continue;

                for (Flow f : c.getOutgoingFlows()) {
                    String src = compartmentKey(c);
                    String tgt = compartmentKey(f.getTarget());
                    String base = src + " \u2192 " + tgt;

                    String typeSuffix = (f instanceof ContactFlow) ? " [ContactFlow]"
                            : (f instanceof RateFlow) ? " [RateFlow]"
                            : " [Flow]";
                    String key = base + typeSuffix;

                    if (map.containsKey(key)) {
                        String d = safeStr(f.getDescription());
                        key = key + " {" + d + "}";
                    }

                    map.put(key, new FlowInfo(c, f));
                }
            }
        }

        return map;
    }

    // ======================================================================
    // Helpers (Birth/Death/Groups/Products)
    // ======================================================================

    private Map<String, BirthSource> indexBirthSources(SEIRModel model) {
        Map<String, BirthSource> map = new HashMap<>();
        if (model == null || model.getBirthSources() == null) return map;

        for (BirthSource b : model.getBirthSources()) {
            map.put(birthSourceKey(b), b);
        }
        return map;
    }

    private Map<String, DeathSink> indexDeathSinks(SEIRModel model) {
        Map<String, DeathSink> map = new HashMap<>();
        if (model == null || model.getDeathSinks() == null) return map;

        for (DeathSink d : model.getDeathSinks()) {
            map.put(deathSinkKey(d), d);
        }
        return map;
    }

    private Map<String, Group> indexGroups(SEIRModel model) {
        Map<String, Group> map = new HashMap<>();
        if (model == null || model.getGroups() == null) return map;

        for (Group g : model.getGroups()) {
            map.put(safeStr(g.getName()), g);
        }
        return map;
    }

    private Map<String, Product> indexProducts(SEIRModel model) {
        Map<String, Product> map = new HashMap<>();
        if (model == null || model.getProducts() == null) return map;

        for (Product p : model.getProducts()) {
            map.put(safeStr(p.getName()), p);
        }
        return map;
    }

    private String birthSourceKey(BirthSource b) {
        String name = safeStr(b.getName());
        String tgt = compartmentKey(b.getTargetCompartment());
        String stratum = safeStr(b.getTargetStratum());
        if (!stratum.isBlank()) return name + " -> " + tgt + " @" + stratum;
        return name + " -> " + tgt;
    }

    private String deathSinkKey(DeathSink d) {
        String name = safeStr(d.getName());
        String src = compartmentKey(d.getSourceCompartment());
        String stratum = safeStr(d.getSourceStratum());
        if (!stratum.isBlank()) return name + " <- " + src + " @" + stratum;
        return name + " <- " + src;
    }

    // ======================================================================
    // Utility
    // ======================================================================

    private static String safeStr(String s) {
        return s == null ? "" : s.trim();
    }

    private static boolean eq(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equals(b);
    }

    private static boolean sameDouble(double a, double b) {
        double eps = 1e-12;
        return Math.abs(a - b) <= eps;
    }

    private static double safeRate(RateFlow rf) {
        try {
            return rf.getRate();
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    private static double safeContactRate(ContactFlow cf) {
        try {
            return cf.getContactRate();
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    private static class FlowInfo {
        @SuppressWarnings("unused")
        final Compartment source;
        final Flow flow;

        FlowInfo(Compartment source, Flow flow) {
            this.source = source;
            this.flow = flow;
        }
    }
}
