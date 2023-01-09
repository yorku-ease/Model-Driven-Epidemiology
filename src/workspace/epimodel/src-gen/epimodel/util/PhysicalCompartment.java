package epimodel.util;

import java.util.List;

public class PhysicalCompartment {
	public final List<String> labels;

	public PhysicalCompartment(List<String> labels) {
		this.labels = labels;
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof PhysicalCompartment &&
				((PhysicalCompartment) other).labels.size() == labels.size()
				&& labels.containsAll(((PhysicalCompartment) other).labels);
	}
}
