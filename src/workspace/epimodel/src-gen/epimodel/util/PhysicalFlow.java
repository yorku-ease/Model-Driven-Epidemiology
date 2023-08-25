package epimodel.util;

import java.util.ArrayList;

public class PhysicalFlow {
	public final PhysicalCompartment source;
	public final PhysicalCompartment sink;
	public final String equation;
	public final String name;
	
	public PhysicalFlow(
		PhysicalCompartment source,
		PhysicalCompartment sink,
		String equation,
		String name
	) {
		this.source = source;
		this.sink = sink;
		this.equation = equation;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof PhysicalFlow && equals((PhysicalFlow) other));
	}
	
	protected boolean equals(PhysicalFlow other) {
		return  source.equals(other.source) && 
				sink.equals(other.sink) && 
				equation.equals(other.equation);
	}
	
	public PhysicalFlow deepCopy() {
		return new PhysicalFlow(
				new PhysicalCompartment(new ArrayList<>(source.labels)),
				new PhysicalCompartment(new ArrayList<>(sink.labels)),
				new String(equation),
				new String(name));
	}
}
