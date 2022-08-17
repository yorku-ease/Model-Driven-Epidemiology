package org.epimodel;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.ConfigurationAnalyzer;

public class Solver {
	public static boolean canBeValid(IFeatureModel fm, Configuration conf, List<IConstraint> constraints) {
		List<IConstraint> saved = new ArrayList<>(fm.getConstraints());
		
		fm.setConstraints(constraints);
		FeatureModelFormula fmf = new FeatureModelFormula(fm);
		
		ConfigurationAnalyzer ca = new ConfigurationAnalyzer(fmf, conf);
		boolean res = ca.canBeValid();
		
		fm.setConstraints(saved);
		
//		System.out.println(res + constraints.toString());
		
		return res;
	}
	
	public static List<List<IConstraint>> getErrors(IFeatureModel fm, Configuration conf, int maxSearchComplexity) {
		// just in case someone calls this with a valid model
		FeatureModelFormula fmf = new FeatureModelFormula(fm);
		ConfigurationAnalyzer ca = new ConfigurationAnalyzer(fmf, conf);
		if (ca.canBeValid())
			return new ArrayList<>();
		// potentially expensive, depends on the error complexity, max depth, number of features
		for (int i = 1; i <= maxSearchComplexity; ++i) {
			List<List<IConstraint>> potential = getConstraintArrangementsOfComplexity(fm, i);
			
			List<List<IConstraint>> errorsAtThisLevel = potential
				.stream()
				.filter(l -> !canBeValid(fm, conf, l))
				.collect(Collectors.toList());

			if (!errorsAtThisLevel.isEmpty() || maxSearchComplexity == 0)
				return errorsAtThisLevel;
		}
		throw new RuntimeException();
	}
	
	private static List<List<Integer>> getArrangementsOfComplexity(int complexity, int range) {
		List<Integer> base = new ArrayList<>();
		for (int i = 0; i < range; ++i)
			base.add(i);
		List<List<Integer>> res = base.stream().map(c -> new ArrayList<>(Arrays.asList(c))).collect(Collectors.toList());

		for (int i = 1; i < complexity; ++i) {
			List<List<Integer>> temp = new ArrayList<>();
			for (List<Integer> l : res)
				for (Integer c : base)
					if (l.get(l.size() - 1) < c)
						temp.add(newListFromListAndNewElement(l, c));
			res = temp;
		}
		
		return res;
	}
	
	public static List<List<IConstraint>> getConstraintArrangementsOfComplexity(IFeatureModel fm, int complexity) {
		
		if (complexity > fm.getConstraintCount())
			throw new InvalidParameterException();
		
		List<List<Integer>> indices = getArrangementsOfComplexity(complexity, fm.getConstraintCount());
		return indices
				.stream()
				.map(li -> li
						.stream()
						.map(i -> fm.getConstraints().get(i))
						.collect(Collectors.toList()))
				.collect(Collectors.toList());
	}
	
	public static <T> List<T> newListFromListAndNewElement(List<T> l, T elem) {
		List<T> l2 = new ArrayList<>(l);
		l2.add(elem);
		return l2;
	}
	
	public static class FeatureModelConfigurationError {
		final List<IConstraint> errors;
		
		public FeatureModelConfigurationError(List<IConstraint> errors) {
			this.errors = errors;
		}
		
		public String getShort() {
			return errors
					.stream()
					.map(e -> e.getDisplayName())
					.collect(Collectors.toList())
					.toString();
		}
		
		public String getDetailed() {
			return errors
					.stream()
					.map(e -> e.getDisplayName() + ":\n\t" + e.getDescription() + "\n")
					.collect(Collectors.joining());
		}
		
		@Override
		public String toString() {
			return getShort();
		}
	}
}
