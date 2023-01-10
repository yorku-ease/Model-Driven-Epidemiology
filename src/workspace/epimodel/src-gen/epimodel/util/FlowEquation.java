package epimodel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Example of HIV infecting [S]usceptible [M]en by [I]nfectious [W]omen (there could also be man/man,woman/man,woman/woman)
 * equationCompartments = [[S,M],[I,W]] the compartments that are used in the equation are [S]us.[M]en and [I]nf.[W]omen
 * affectedCompartments = [[S,M],[I,M]] the compartments that change are [S]us. and [I]nf. [M]en
 * coefficients = [-1, 1] ([S,M] to [I,M])
 * equation = (* $0 $1), lisp style for easy parse, otherwise we need to force a data format like json
 * 	 OR more complex: equation = (* (get susceptibilityHIV $0) $0 (get contagiousnessHIV $1) $1) query some more coefficients from a table
 * requiredOperators = [*] we might have other operators such as +,-,/,min,max,etc and might have user implementation on the simulation side
 * 	 OR more complex = [*, get]
 */

public class FlowEquation {
	public final List<PhysicalCompartment> equationCompartments;
	public final PhysicalCompartment source;
	public final PhysicalCompartment sink;
	public final String equation;
	public final List<String> requiredOperators;
	
	public FlowEquation(
		List<PhysicalCompartment> equationCompartments,
		PhysicalCompartment source,
		PhysicalCompartment sink,
		String equation,
		List<String> requiredOperators
	) {
		this.equationCompartments = equationCompartments;
		this.source = source;
		this.sink = sink;
		this.equation = equation;
		this.requiredOperators = requiredOperators;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof FlowEquation && equals((FlowEquation) other));
	}
	
	protected boolean equals(FlowEquation other) {
		return equationCompartments.equals(other.equationCompartments) && 
				source.equals(other.source) && 
				sink.equals(other.sink) && 
				equation.equals(other.equation) && 
				requiredOperators.equals(other.requiredOperators);
	}
	
	public FlowEquation deepCopy() {
		return new FlowEquation(
				equationCompartments.stream().map(pc -> new PhysicalCompartment(new ArrayList<>(pc.labels))).collect(Collectors.toList()),
				new PhysicalCompartment(new ArrayList<>(source.labels)),
				new PhysicalCompartment(new ArrayList<>(sink.labels)),
				equation,
				new ArrayList<>(requiredOperators));
	}
	
	@Override
	public String toString() {
		return "{Flow: source = " + source + ", sink = " + sink + ", equationCompartments = " + equationCompartments + "}";
	}
}
