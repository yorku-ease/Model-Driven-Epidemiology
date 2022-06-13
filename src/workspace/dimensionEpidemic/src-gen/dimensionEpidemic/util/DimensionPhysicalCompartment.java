package dimensionEpidemic.util;

import java.util.List;

import epimodel.util.PhysicalCompartment;

public class DimensionPhysicalCompartment extends PhysicalCompartment {
	public final List<String> originalLabels;

	public DimensionPhysicalCompartment(List<String> originalLabels) {
		super(getId(originalLabels));
		this.originalLabels = originalLabels;
	}
	
	protected static String getId(List<String> originalLabels) {
		String res = "";
		for (String label : originalLabels)
			res += label + ", ";
		if (res.length() != 0)
			res = res.substring(0, res.length() - 2);
		return "[" + res + "]";
	}
}
