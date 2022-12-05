package epimodel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import epimodel.Compartment;
import epimodel.Composable;
import epimodel.Epidemic;
import epimodel.Flow;

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
		
		public Difference compare(MatchResult matches) {
			return match.first.compare(match.second, matches);
		}
		
		@Override
		public String toString() {
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
			throw new RuntimeException("no match found for " + c1.getLabels() + " <-> " + c2.getLabels());
		}
		
		public Match find(Composable c) {
			for (Match match : matches)
				if (match.match.first.equals(c) || match.match.second.equals(c))
					return match;
			throw new RuntimeException("no match found for " + c.getLabels());
		}
	}
	
	public static class DiffResult {
		public final List<Difference> diffs = new ArrayList<>();
	}
	
	public static class ChildrenDiffResult {
		public final List<Match> childrenMatches;
		public final List<Match> accountsForMatches;

		public final List<Compartment> myCompartments;
		public final List<Compartment> myMatchedCompartments;
		public final List<Compartment> myUnMatchedCompartments;
		
		public final List<Compartment> otherCompartments;
		public final List<Compartment> otherMatchedCompartments;
		public final List<Compartment> otherUnMatchedCompartments;
		
		public final List<Difference> childrenDiffs;
		public final List<Pair<Match, Difference>> sameChildrenMatchAndDiffs;
		public final List<Pair<Match, Difference>> notSameChildrenMatchAndDiffs;
		
		public final boolean isSame;
		
		public ChildrenDiffResult(List<Compartment> myCompartments, List<Compartment> otherCompartments, MatchResult matches) {

			this.myCompartments = myCompartments;
			this.otherCompartments = otherCompartments;

			myMatchedCompartments = new ArrayList<>();
			myUnMatchedCompartments = new ArrayList<>(myCompartments);
			
			otherMatchedCompartments = new ArrayList<>();
			otherUnMatchedCompartments = new ArrayList<>(otherCompartments);

			childrenMatches = new ArrayList<>();
			
			for (Compartment c : myCompartments) {
				Match childMatch = matches.find(c);
				if (childMatch != null && otherCompartments.contains(childMatch.match.second)) {
					childrenMatches.add(childMatch);
					myMatchedCompartments.add(c);
					myUnMatchedCompartments.remove(c);
					otherMatchedCompartments.add((Compartment) childMatch.match.second);
					otherUnMatchedCompartments.remove(childMatch.match.second);
				}
			}
			
			childrenDiffs = childrenMatches.stream().map(m -> m.compare(matches)).collect(Collectors.toList());
			sameChildrenMatchAndDiffs = new ArrayList<>();
			notSameChildrenMatchAndDiffs = new ArrayList<>();
			
			for (int i = 0; i < childrenDiffs.size(); ++i) {
				Match m = childrenMatches.get(i);
				Difference d = childrenDiffs.get(i);
				Pair<Match, Difference> p = new Pair<>(m, d);
				
				if (d.isSame)
					sameChildrenMatchAndDiffs.add(p);
				else
					notSameChildrenMatchAndDiffs.add(p);
			}
			
			accountsForMatches = childrenDiffs
					.stream()
					.map(diffResult -> diffResult.accountsForMatches)
					.flatMap(List::stream)
					.collect(Collectors.toList());
			
			this.isSame = notSameChildrenMatchAndDiffs.size() == 0 && myUnMatchedCompartments.size() == 0 && otherUnMatchedCompartments.size() == 0;
		}
		
		public String getSimpleDescription() {
			StringBuilder sb = new StringBuilder();
			boolean requiresComma = false;
			if (!sameChildrenMatchAndDiffs.isEmpty()) {
				if (requiresComma)
					sb.append(",");
				sb.append(" Same Matched Children (no differences): ");
				for (Pair<Match, Difference> matchDiff : sameChildrenMatchAndDiffs)
					sb.append(matchDiff.first.match.first.getLabels()).append(", ");
				requiresComma = false;
			}
			if (!notSameChildrenMatchAndDiffs.isEmpty()) {
				if (requiresComma)
					sb.append(",");
				sb.append(" Not Same Matched Children (found differences): ");
				for (Pair<Match, Difference> matchDiff : notSameChildrenMatchAndDiffs)
					sb.append("{").append(matchDiff.second.description).append("}, ");
				requiresComma = false;
			}
			if (!otherUnMatchedCompartments.isEmpty()) {
				if (requiresComma)
					sb.append(",");
				sb.append(" Added Children: ");
				sb.append(
					otherUnMatchedCompartments.stream().map(Composable::getLabels).collect(Collectors.toList()).toString()
				);
				requiresComma = true;
			}
			if (!myUnMatchedCompartments.isEmpty()) {
				if (requiresComma)
					sb.append(",");
				sb.append(" Removed Children: ");
				sb.append(
						myUnMatchedCompartments.stream().map(Composable::getLabels).collect(Collectors.toList()).toString()
				);
				requiresComma = true;
			}
			// TODO FLOWS
			if (requiresComma)
				sb.append(",");
			sb.append(" potentially unmatched flows (TODO)");
			return sb.toString();
		}
	}
	
	public static Difference createDifference(
		Composable me,
		Composable other,
		MatchResult matches,
		List<Compartment> myCompartments,
		List<Compartment> otherCompartments,
		List<Flow> myFlows,
		List<Flow> otherFlows) {
		
		// TODO FLOWS
	
		Match match = null;
		try {
			match = matches.find(me, other);
		} catch (Exception e) {
			// match is null
		}
		
		ChildrenDiffResult childrenDiffs = new ChildrenDiffResult(myCompartments, otherCompartments, matches);
		
		List<Match> accountedForMatches = new ArrayList<>(childrenDiffs.accountsForMatches);
		if (match != null)
			accountedForMatches.add(match);
		
		boolean isSame = childrenDiffs.isSame && me.getLabels().equals(other.getLabels());
		
		String description = isSame ?
				me.compareWithDifferentClass(other, matches).description :
				me.compareWithDifferentClass(other, matches).description + childrenDiffs.getSimpleDescription();
		
		return new Difference(
				accountedForMatches,
				new ArrayList<>(childrenDiffs.myUnMatchedCompartments),
				new ArrayList<>(childrenDiffs.otherUnMatchedCompartments),
				isSame,
				description);
	}

	public static class Difference {
		public final List<Match> accountsForMatches;
		public final List<Composable> accountsForAdditions;
		public final List<Composable> accountsForSubstractions;
		public final boolean isSame;
		public final String description;
		
		public Difference(
				List<Match> accountsForMatches,
				List<Composable> accountsForAdditions,
				List<Composable> accountsForSubstractions,
				boolean isSame,
				String description) {
			this.accountsForMatches = new ArrayList<>(accountsForMatches);
			this.accountsForAdditions = new ArrayList<>(accountsForAdditions);
			this.accountsForSubstractions = new ArrayList<>(accountsForSubstractions);
			this.isSame = isSame;
			this.description = description;
		}
	}
	
	public static final class Pair <T, U> {
		public final T first;
		public final U second;
		
		public Pair(T first, U second) {
			this.first = first;
			this.second = second;
		}
	}
}
