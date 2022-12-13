package epimodel.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import epimodel.Compartment;
import epimodel.Composable;
import epimodel.Epidemic;
import epimodel.Flow;

public class Comparison {
	
	public static class ModelContext {
		public final Epidemic model;
		public final List<Composable> composables;
		public final Set<String> uniqueLabels;
		public final Set<String> duplicateLabels;
		
		public ModelContext(Epidemic model) {
			this.model = model;
			composables = new ArrayList<>(Arrays.asList(model));
			
			uniqueLabels = new HashSet<>();
			duplicateLabels = new HashSet<>();
			
			model.eAllContents().forEachRemaining(eobject -> {
				if (!(eobject instanceof Composable))
					return;
				Composable composable = (Composable) eobject;
				composables.add(composable);
				duplicateLabels.addAll(
					composable
						.getLabels()
						.stream()
						.filter(label -> uniqueLabels.contains(label) && !duplicateLabels.contains(label))
						.collect(Collectors.toList()));
				uniqueLabels.addAll(composable.getLabels());
			});
			uniqueLabels.removeAll(duplicateLabels);
		}
	}

	public static class ComparisonContext {
		final List<Pair<String, String>> renamings;
		public final ModelContext modelctx1;
		public final ModelContext modelctx2;
		
		public ComparisonContext(
			Epidemic model1,
			Epidemic model2,
			List<Pair<String, String>> renamings
		) {
			this.modelctx1 = new ModelContext(model1);
			this.modelctx2 = new ModelContext(model2);
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
		public final ComparisonContext context;
		public final List<Match> matches = new ArrayList<>();
		
		public MatchResult(ComparisonContext context) {
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
		
		public Optional<Match> find(Composable c) {
			for (Match match : matches)
				if (match.match.first.equals(c) || match.match.second.equals(c))
					return Optional.of(match);
			return Optional.empty();
		}
	}
	
	public static class DiffResult {
		public final List<Difference> diffs = new ArrayList<>();
	}
	
	public static class ChildrenDiffResult {
		public final MatchResult matches;
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

			this.matches = matches;
			this.myCompartments = myCompartments;
			this.otherCompartments = otherCompartments;

			myMatchedCompartments = new ArrayList<>();
			myUnMatchedCompartments = new ArrayList<>(myCompartments);
			
			otherMatchedCompartments = new ArrayList<>();
			otherUnMatchedCompartments = new ArrayList<>(otherCompartments);

			childrenMatches = new ArrayList<>();
			
			for (Compartment c : myCompartments) {
				Optional<Match> o = matches.find(c);
				Match childMatch = o.isPresent() ? o.get() : null;
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
					otherUnMatchedCompartments
						.stream()
						.map(c -> {
							Optional<Match> o = matches.find(c);
							return o.isPresent() ? c.getLabels() + "(Move detected)" : c.getLabels();
						}).collect(Collectors.toList()).toString()
				);
				requiresComma = true;
			}
			if (!myUnMatchedCompartments.isEmpty()) {
				if (requiresComma)
					sb.append(",");
				sb.append(" Removed Children: ");
				sb.append(
						myUnMatchedCompartments
						.stream()
						.map(c -> {
							Optional<Match> o = matches.find(c);
							return o.isPresent() ? c.getLabels() + "(Move detected)" : c.getLabels();
						}).collect(Collectors.toList()).toString()
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
		
		List<Match> accountedForMatches = new ArrayList<>();
		if (match != null)
			accountedForMatches.add(match);
		accountedForMatches.addAll(childrenDiffs.accountsForMatches);
		
		boolean isSame = childrenDiffs.isSame && me.getLabels().equals(other.getLabels());
		
		String description = isSame ?
			me.compareWithDifferentClass(other, matches).description :
			me.compareWithDifferentClass(other, matches).description + childrenDiffs.getSimpleDescription();
		
		
		
		return new Difference(
				accountedForMatches,
				childrenDiffs.otherUnMatchedCompartments
					.stream().map(c -> {
						List<Composable> res = new ArrayList<Composable>();
						res.add(c);
						c.eAllContents().forEachRemaining(o -> {
							if (o instanceof Composable)
								res.add((Composable) o);
						});
						return res;
					}).flatMap(List::stream).collect(Collectors.toList()),
				childrenDiffs.myUnMatchedCompartments
					.stream().map(c -> {
						List<Composable> res = new ArrayList<Composable>();
						res.add(c);
						c.eAllContents().forEachRemaining(o -> {
							if (o instanceof Composable)
								res.add((Composable) o);
						});
						return res;
					}).flatMap(List::stream).collect(Collectors.toList()),
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
	
	public static class ComparisonResult {
		public final ComparisonContext context;
		public final MatchResult matches;
		public final List<Difference> diffs;
		
		public ComparisonResult(ComparisonContext context, MatchResult matches, List<Difference> diffs) {
			this.context = context;
			this.matches = matches;
			this.diffs = diffs;
		}
	}
	
	public static MatchResult ExactOrContainsLabelMatch(ComparisonContext context) {
		MatchResult res = new MatchResult(context);
		List<Composable> model1compartments = new ArrayList<>(context.modelctx1.composables);
		List<Composable> model2compartments = new ArrayList<>(context.modelctx2.composables);
		System.out.println("Model1:\n" + model1compartments.stream().map(Composable::getLabels).collect(Collectors.toList()));
		System.out.println("Model2:\n" + model2compartments.stream().map(Composable::getLabels).collect(Collectors.toList()));
		
		List<Composable> model1NotExactMatchedCompartments = new ArrayList<>(model1compartments);
		List<Composable> model2NotExactMatchedCompartments = new ArrayList<>(model2compartments);
		
		// look for same labels: ["S", "0"] matches only ["S", "0"]
		for (Composable c1 : model1compartments)
			for (Composable c2 : model2compartments)
				if (c1.getLabels().equals(c2.getLabels())) {
					res.matches.add(new Match(c1, c2));
					model1NotExactMatchedCompartments.remove(c1);
					model2NotExactMatchedCompartments.remove(c2);
				}
		
		List<Composable> model1Not2ContainsAll1MatchedCompartments = new ArrayList<>(model1NotExactMatchedCompartments);
		List<Composable> model2Not2ContainsAll1Compartments = new ArrayList<>(model2NotExactMatchedCompartments);

		// look for object from model1 who's labels are contained by a model2 object's labels: ["S"] (model1) matches [..., "S", ...] (model2)
		// Look only for unique labels so for example if [SI, S] and [SI, I] exist, SI would be uneligible but S and I are fine
		for (Composable c1 : model1NotExactMatchedCompartments)
			for (Composable c2 : model2NotExactMatchedCompartments)
				if (c2.getLabels().containsAll(c1.getLabels())) {
					boolean hasMatchedUnique = false;
					for (String label : c1.getLabels())
						if (context.modelctx1.uniqueLabels.contains(label) && context.modelctx2.uniqueLabels.contains(label)) {
							hasMatchedUnique = true;
							break;
						}
					if (!hasMatchedUnique)
						continue;
					res.matches.add(new Match(c1, c2));
					model1Not2ContainsAll1MatchedCompartments.remove(c1);
					model2Not2ContainsAll1Compartments.remove(c2);
				}

		// look for object from model2 who's labels are contained by a model1 object's labels: [..., "S", ...] (model1) matches ["S"] (model2)
		// Look only for unique labels so for example if [SI, S] and [SI, I] exist, SI would be uneligible but S and I are fine
		for (Composable c1 : model1NotExactMatchedCompartments)
			for (Composable c2 : model2NotExactMatchedCompartments)
				if (c1.getLabels().containsAll(c2.getLabels())) {
					boolean hasMatchedUnique = false;
					for (String label : c2.getLabels())
						if (context.modelctx1.uniqueLabels.contains(label) && context.modelctx2.uniqueLabels.contains(label)) {
							hasMatchedUnique = true;
							break;
						}
					if (!hasMatchedUnique)
						continue;
					res.matches.add(new Match(c1, c2));
				}
		
		return res;
	}
}
