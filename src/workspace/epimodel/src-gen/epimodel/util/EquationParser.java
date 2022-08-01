package epimodel.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EquationParser {
	public static Expression Parse(String eq) throws Exception {
		StringCursor sc = new StringCursor(eq);
		Expression res = _Parse(sc);
		if (sc.cursor != sc.s.length())
			throw new Exception("Bad Equation, couldn't fully parse, finished at index " + sc.cursor + " out of " + sc.s.length());
		return res;
	}
	
	static class StringCursor {
		public final String s;
		public int cursor;
		public StringCursor(String s) {
			this.s = s;
			this.cursor = 0;
		}
	}
	
	private static Expression _Parse(StringCursor sc) {
		while (sc.s.charAt(sc.cursor) == ' ')
			sc.cursor += 1;
		
		char beg = sc.s.charAt(sc.cursor);
		
		if (beg == ')')
			return null;
		
		if (beg == '(')
			return ParseFunction(sc);
		
		if (beg == '[')
			return ParseCompartment(sc);
		
		
		int anchor = sc.cursor;
		
		if (Character.isDigit(beg)) {
			while (Character.isDigit(sc.s.charAt(sc.cursor)))
				sc.cursor += 1;
			return new Number(sc.s.substring(anchor, sc.cursor));
		}
		else {
			while (Character.isDigit(sc.s.charAt(sc.cursor)) || Character.isLetter(sc.s.charAt(sc.cursor)))
				sc.cursor += 1;
			return new Variable(sc.s.substring(anchor, sc.cursor));
		}
	}
	
	public static Function ParseFunction(StringCursor sc) {
		sc.cursor += 1;
		Expression operator = _Parse(sc);
		List<Expression> operands = new ArrayList<>();
		{
			Expression operand;
			while (true) {
				operand = _Parse(sc);
				if (operand == null) { // hit closing ')' of function call
					sc.cursor += 1;
					break;
			}
				operands.add(operand);
			}
		}
		return new Function(operator, operands);
	}
	
	public static CompartmentSpec ParseCompartment(StringCursor sc) {
		sc.cursor += 1;
		int end = sc.s.substring(sc.cursor).indexOf(']') + sc.cursor;
		String[] specs = sc.s.substring(sc.cursor, end).replace(" ", "").split(",");
		sc.cursor = end + 1;
		return new CompartmentSpec(Arrays.asList(specs));
	}
	
	public static interface Expression {
//		public List<String> getRequiredOperators();
//		public List<String> getRequiredParameters();
		public String treeView();
	}
	
	static class Function implements Expression {
		public final Expression operator;
		public final List<Expression> operands;
		public Function(Expression operator, List<Expression> operands) {
			this.operator = operator;
			this.operands = operands;
		}
		
		@Override
		public String toString() {
			return "(" + operator + " " + operands.stream().map(Object::toString).collect(Collectors.joining(" ")) + ")";
		}
		
		@Override
		public String treeView() {
			return "(" + operator.treeView() + " " + operands.stream().map(Expression::treeView).collect(Collectors.joining(" ")) + ")";
		}
	}
	
	static class Variable implements Expression {
		public final String value;
		public Variable(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}
		
		@Override
		public String treeView() {
			return "{V: " + value + "}";
		}
	}
	
	static class Number implements Expression {
		public final String value;
		public Number(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}
		
		@Override
		public String treeView() {
			return "{N: " + value + "}";
		}
	}
	
	static class CompartmentSpec implements Expression {
		public final List<String> spec;
		
		public CompartmentSpec(List<String> spec) {
			this.spec = spec;
		}
		
		@Override
		public String toString() {
			return spec.toString();
		}
		
		@Override
		public String treeView() {
			return "{C: " + spec.toString() + "}";
		}
	}
}
