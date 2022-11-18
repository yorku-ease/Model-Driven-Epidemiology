package epimodel.util;

import java.util.ArrayList;
import java.util.List;

import epimodel.Composable;
import epimodel.Epidemic;

public class Comparison {

	public static class CompareContext {
		final List<Pair<String, String>> renamings;
		public final Epidemic model1;
		public final Epidemic model2;
		
		public CompareContext(Epidemic model1, Epidemic model2, List<Pair<String, String>> renamings) {
			this.model1 = model1;
			this.model2 = model2;
			this.renamings = renamings;
		}
	}
	
	public static class Match {
		public final Pair<Composable, Composable> match;
		public Match(Composable first, Composable second) {
			this.match = new Pair<>(first, second);
		}
		
		@Override public String toString() {
			boolean sameId = match.first.getLabels().equals(match.second.getLabels());
			boolean containedId1inId2 = !sameId && match.second.getLabels().containsAll(match.first.getLabels());
			boolean containedId2inId1 = !sameId && match.first.getLabels().containsAll(match.second.getLabels());
			boolean sameClass = match.first.getClass().equals(match.second.getClass());
			
			String retypePrefix = sameClass ? "" : "Retype of ";
			String labelMatchType = sameId ? "Exact " : containedId1inId2 ? "Specialized " : containedId2inId1 ? "Generalized " : "";
			final String matchPresentation;
			
			if (sameClass && sameId)
				matchPresentation = match.first.getClass().getSimpleName() + ": " + match.first.getLabels();
			else if (sameClass)
				matchPresentation = match.first.getClass().getSimpleName() + ": " + match.first.getLabels() + " -> " + match.second.getLabels();
			else
				matchPresentation = match.first .getClass().getSimpleName() + match.first .getLabels() + " -> " + 
									match.second.getClass().getSimpleName() + match.second.getLabels();
			
			return retypePrefix + labelMatchType + "Label Match " + matchPresentation;
		}
	}
	
	public static class MatchResult {
		public final CompareContext context;
		public final List<Match> matches = new ArrayList<>();
		
		public MatchResult(CompareContext context) {
			this.context = context;
		}
		
		@Override public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Matches:\n");
			for (Match match : matches)
				sb.append("\t").append(match).append("\n");
			return sb.toString();
		}
		
		public Match find(Composable c1, Composable c2) {
			for (Match match : matches)
				if (match.match.first.equals(c1) && match.match.second.equals(c2))
					return match;
			return null;
		}
		
		public Match find(Composable c) {
			for (Match match : matches)
				if (match.match.first.equals(c) || match.match.second.equals(c))
					return match;
			return null;
		}
	}
	
	public static class DiffResult {
		public final List<Difference> diffs = new ArrayList<>();
	}

	public static abstract class Difference {
		public final List<Match> accountsForMatches;
		public final List<Composable> accountsForAdditions;
		public final List<Composable> accountsForSubstractions;
		public Difference(List<Match> accountsForMatches, List<Composable> accountsForAdditions, List<Composable> accountsForSubstractions) {
			this.accountsForMatches = accountsForMatches;
			this.accountsForAdditions = accountsForAdditions;
			this.accountsForSubstractions = accountsForSubstractions;
		}
		
		public abstract String getSimpleDescription();
	}
	
	public static final class Pair <T, U> {
		public final T first;
		public final U second;
		
		Pair(T first, U second) {
			this.first = first;
			this.second = second;
		}
	}
}
