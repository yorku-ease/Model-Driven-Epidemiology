package seirmodel.compare;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DiffPrinter {

    public static void print(List<ModelDifference> diffs) {
        if (diffs == null) diffs = new ArrayList<>();

        Map<DifferenceType, List<ModelDifference>> grouped =
                new EnumMap<>(DifferenceType.class);

        for (DifferenceType t : DifferenceType.values()) {
            grouped.put(t, new ArrayList<>());
        }

        for (ModelDifference d : diffs) {
            grouped.get(d.type()).add(d);
        }

        for (DifferenceType t : DifferenceType.values()) {
            List<ModelDifference> items = grouped.get(t);
            if (items.isEmpty()) continue;

            System.out.println(t + ":");
            for (ModelDifference d : items) {
                System.out.println("  - " + d.itemString());
            }
            System.out.println();
        }

        if (diffs.isEmpty()) {
            System.out.println("No differences found.");
        }
    }
}
