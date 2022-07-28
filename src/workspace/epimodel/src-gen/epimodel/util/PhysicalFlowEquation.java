package epimodel.util;

import java.util.List;

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

public class PhysicalFlowEquation {
	public final List<PhysicalCompartment> equationCompartments;
	public final List<PhysicalCompartment> affectedCompartments;
	public final List<Float> coefficients;
	public final String equation;
	public final List<String> requiredOperators;
	
	public PhysicalFlowEquation(
		List<PhysicalCompartment> equationCompartments,
		List<PhysicalCompartment> affectedCompartments,
		List<Float> coefficients,
		String equation,
		List<String> requiredOperators
	) {
		this.equationCompartments = equationCompartments;
		this.affectedCompartments = affectedCompartments;
		this.coefficients = coefficients;
		this.equation = equation;
		this.requiredOperators = requiredOperators;
	}
}
