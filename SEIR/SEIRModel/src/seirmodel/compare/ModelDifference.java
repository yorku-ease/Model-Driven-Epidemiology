package seirmodel.compare;

public record ModelDifference(
        DifferenceType type,
        String element,
        String location,
        Object oldValue,
        Object newValue
) {
    private static String v(Object o) {
        return o == null ? "null" : o.toString();
    }

    /**
     * One-line display string meant to be printed UNDER a section header.
     * - For existence diffs (added/removed): shows "only in ..."
     * - For value diffs: shows "(old → new)"
     */
    public String itemString() {

        // Existence-style diff (one side is null)
        if (oldValue == null && newValue != null) {
            return "[" + element + "] " + location + " (only in " + v(newValue) + ")";
        }
        if (oldValue != null && newValue == null) {
            return "[" + element + "] " + location + " (only in " + v(oldValue) + ")";
        }

        // Value-style diff
        return "[" + element + "] " + location + " (" + v(oldValue) + " \u2192 " + v(newValue) + ")";
    }
}
