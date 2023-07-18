package epimodel.util;

import java.util.ArrayList;
import java.util.List;

public class PhysicalFlow {
	public final PhysicalCompartment source;
	public final PhysicalCompartment sink;
	public final String equation;
	public final List<String> requiredOperators;
	
	public PhysicalFlow(
		PhysicalCompartment source,
		PhysicalCompartment sink,
		String equation,
		List<String> requiredOperators
	) {
		this.source = source;
		this.sink = sink;
		this.equation = equation;
		this.requiredOperators = requiredOperators;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof PhysicalFlow && equals((PhysicalFlow) other));
	}
	
	protected boolean equals(PhysicalFlow other) {
		return  source.equals(other.source) && 
				sink.equals(other.sink) && 
				equation.equals(other.equation) && 
				requiredOperators.equals(other.requiredOperators);
	}
	
	public PhysicalFlow deepCopy() {
		return new PhysicalFlow(
				new PhysicalCompartment(new ArrayList<>(source.labels)),
				new PhysicalCompartment(new ArrayList<>(sink.labels)),
				new String(equation),
				new ArrayList<>(requiredOperators));
	}
}
