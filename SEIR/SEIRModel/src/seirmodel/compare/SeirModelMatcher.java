package seirmodel.compare;

import java.util.HashMap;
import java.util.Map;

import seirmodel.BirthSource;
import seirmodel.Compartment;
import seirmodel.DeathSink;
import seirmodel.Flow;
import seirmodel.Group;
import seirmodel.Product;
import seirmodel.SEIRModel;

public final class SeirModelMatcher {

    public Map<String, Compartment> indexCompartments(SEIRModel model) {
        Map<String, Compartment> map = new HashMap<>();
        for (Compartment c : model.getCompartments()) {
            map.put(compartmentKey(c), c);
        }
        return map;
    }

    public Map<String, BirthSource> indexBirthSources(SEIRModel model) {
        Map<String, BirthSource> map = new HashMap<>();
        for (BirthSource b : model.getBirthSources()) {
            map.put(birthKey(b), b);
        }
        return map;
    }

    public Map<String, DeathSink> indexDeathSinks(SEIRModel model) {
        Map<String, DeathSink> map = new HashMap<>();
        for (DeathSink d : model.getDeathSinks()) {
            map.put(deathKey(d), d);
        }
        return map;
    }

    public Map<String, Group> indexGroups(SEIRModel model) {
        Map<String, Group> map = new HashMap<>();
        for (Group g : model.getGroups()) {
            map.put(groupKey(g), g);
        }
        return map;
    }

    public Map<String, Product> indexProducts(SEIRModel model) {
        Map<String, Product> map = new HashMap<>();
        for (Product p : model.getProducts()) {
            map.put(productKey(p), p);
        }
        return map;
    }

    /** Identity rule (domain-based): PrimaryName + SecondaryName (if any). */
    public String compartmentKey(Compartment c) {
        String primary = safe(c.getPrimaryName());
        String secondary = safe(c.getSecondaryName());
        if (secondary.isEmpty()) return primary;
        return primary + " (" + secondary + ")";
    }

    /** Identity rule: sourceCompartmentKey + targetCompartmentKey + flow type. */
    public String flowKey(Compartment source, Flow f) {
        String src = compartmentKey(source);
        String tgt = (f.getTarget() == null) ? "null" : compartmentKey(f.getTarget());

        // Use concrete EMF class name as type discriminator (RateFlow vs ContactFlow)
        String type = f.eClass().getName();
        return src + " → " + tgt + " :: " + type;
    }

    public String birthKey(BirthSource b) {
        // Stable enough: name + target compartment key
        String name = safe(b.getName());
        String tgt = (b.getTargetCompartment() == null) ? "null" : compartmentKey(b.getTargetCompartment());
        String stratum = safe(b.getTargetStratum());
        if (!stratum.isEmpty()) {
            return name + " -> " + tgt + " @" + stratum;
        }
        return name + " -> " + tgt;
    }

    public String deathKey(DeathSink d) {
        String name = safe(d.getName());
        String src = (d.getSourceCompartment() == null) ? "null" : compartmentKey(d.getSourceCompartment());
        String stratum = safe(d.getSourceStratum());
        if (!stratum.isEmpty()) {
            return name + " <- " + src + " @" + stratum;
        }
        return name + " <- " + src;
    }

    public String groupKey(Group g) {
        return safe(g.getName());
    }

    public String productKey(Product p) {
        return safe(p.getName());
    }

    private static String safe(String s) {
        return s == null ? "" : s.trim();
    }
}
