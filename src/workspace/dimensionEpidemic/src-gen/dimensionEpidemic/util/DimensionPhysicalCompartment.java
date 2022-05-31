package dimensionEpidemic.util;

import java.util.List;

import epimodel.Compartment;
import epimodel.util.PhysicalCompartment;

public class DimensionPhysicalCompartment extends PhysicalCompartment {
	public final List<Compartment> originalLabels;

	public DimensionPhysicalCompartment(List<Compartment> originalLabels, String id) {
		super(id);
		this.originalLabels = originalLabels;
	}
	
	protected String getId(List<Compartment> originalLabels) {
		String res = "";
		for (Compartment label : originalLabels)
			res += label.getId() + ", ";
		if (res.length() != 0)
			res = res.substring(0, res.length() - 2);
		return "[" + res + "]";
	}
}
