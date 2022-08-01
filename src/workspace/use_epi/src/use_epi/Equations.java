package use_epi;

import epimodel.util.EquationParser;
import epimodel.util.EquationParser.Expression;

public class Equations {
	public static void main(String[] args) throws Exception {
		Expression e = EquationParser.Parse("(get [1,1,fd,23,rrr4,4g3,g45,g43,4g,3g4,g43,34, ] (get get 1 324 asda12fdtas6ft6s7dfg5sd67f5s7d6f3))");
		System.out.println("e = " + e.treeView());
	}
}
