package epimodel.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import epimodel.Compartment;
import epimodel.Flow;

public class Comparison {
	
	public static class ModelContext {
		public final Compartment model;
		public final List<Compartment> compartments;
		public final Set<String> uniqueLabels;
		public final Set<String> duplicateLabels;
		
		public ModelContext(Compartment model) {
			this.model = model;
			compartments = new ArrayList<>(Arrays.asList(model));
			
			uniqueLabels = new HashSet<>();
			duplicateLabels = new HashSet<>();
			
			model.eAllContents().forEachRemaining(eobject -> {
				if (!(eobject instanceof Compartment))
					return;
				Compartment compartment = (Compartment) eobject;
				compartments.add(compartment);
				duplicateLabels.addAll(
						compartment
						.getLabels()
						.stream()
						.filter(label -> uniqueLabels.contains(label) && !duplicateLabels.contains(label))
						.collect(Collectors.toList()));
				uniqueLabels.addAll(compartment.getLabels());
			});
			uniqueLabels.removeAll(duplicateLabels);
		}
	}

	public static class ComparisonContext {
		final List<Pair<String, String>> renamings;
		public final ModelContext modelctx1;
		public final ModelContext modelctx2;
		
		public ComparisonContext(
			Compartment model1,
			Compartment model2,
			List<Pair<String, String>> renamings
		) {
			this.modelctx1 = new ModelContext(model1);
			this.modelctx2 = new ModelContext(model2);
			this.renamings = renamings;
		}
	}
	
	public static class Match {
		public final Pair<Compartment, Compartment> matchedCompartmentPair;
		public boolean isMove = false;
		
		public Match(Compartment first, Compartment second) {
			this.matchedCompartmentPair = new Pair<>(first, second);
		}
		
		public Difference compare(MatchResult matches) {
			return matchedCompartmentPair.first.compare(matchedCompartmentPair.second, matches);
		}
		
		@Override
		public String toString() {
			boolean sameId = matchedCompartmentPair.first.getLabels().equals(matchedCompartmentPair.second.getLabels());
			boolean containedId1inId2 = !sameId && matchedCompartmentPair.second.getLabels().containsAll(matchedCompartmentPair.first.getLabels());
			boolean containedId2inId1 = !sameId && matchedCompartmentPair.first.getLabels().containsAll(matchedCompartmentPair.second.getLabels());
			boolean sameClass = matchedCompartmentPair.first.getClass().equals(matchedCompartmentPair.second.getClass());
			
			String retypePrefix = sameClass ? "" : "Retype of ";
			String labelMatchType = sameId ? "Exact " : containedId1inId2 ? "Specialized " : containedId2inId1 ? "Generalized " : "";
			final String matchPresentation;
			
			if (sameClass && sameId)
				matchPresentation = matchedCompartmentPair.first.getClass().getSimpleName() + ": " + matchedCompartmentPair.first.getLabels();
			else if (sameClass)
				matchPresentation = matchedCompartmentPair.first.getClass().getSimpleName() + ": " + matchedCompartmentPair.first.getLabels() + " -> " + matchedCompartmentPair.second.getLabels();
			else
				matchPresentation = matchedCompartmentPair.first .getClass().getSimpleName() + matchedCompartmentPair.first .getLabels() + " -> " + 
						matchedCompartmentPair.second.getClass().getSimpleName() + matchedCompartmentPair.second.getLabels();
			
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
		
		public Optional<Match> find(Compartment c1, Compartment c2) {
			for (Match match : matches)
				if (match.matchedCompartmentPair.first.equals(c1) && match.matchedCompartmentPair.second.equals(c2))
					return Optional.of(match);
			return Optional.empty();
		}
		
		public Optional<Match> find(Compartment c) {
			for (Match match : matches)
				if (match.matchedCompartmentPair.first.equals(c) || match.matchedCompartmentPair.second.equals(c))
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
		
		// todo take flows??
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
				Match childMatch = matches.find(c).orElse(null);
				if (childMatch != null && otherCompartments.contains(childMatch.matchedCompartmentPair.second)) {
					childrenMatches.add(childMatch);
					myMatchedCompartments.add(c);
					myUnMatchedCompartments.remove(c);
					otherMatchedCompartments.add((Compartment) childMatch.matchedCompartmentPair.second);
					otherUnMatchedCompartments.remove(childMatch.matchedCompartmentPair.second);
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
					sb.append(matchDiff.first.matchedCompartmentPair.first.getLabels()).append(", ");
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
		Compartment me,
		Compartment other,
		MatchResult matches,
		List<Compartment> myCompartments,
		List<Compartment> otherCompartments,
		List<Flow> myFlows,
		List<Flow> otherFlows) {
		// TODO FLOWS
		
		ChildrenDiffResult childrenDiffs = new ChildrenDiffResult(myCompartments, otherCompartments, matches);
		
		List<Match> accountedForMatches = new ArrayList<>();
		accountedForMatches.add(matches.find(me, other).get());
		accountedForMatches.addAll(childrenDiffs.accountsForMatches);
		
		boolean isSame = childrenDiffs.isSame && me.getLabels().equals(other.getLabels());
		
		String description = isSame ?
			me.compareWithDifferentClass(other, matches).description :
			me.compareWithDifferentClass(other, matches).description + childrenDiffs.getSimpleDescription();
		
		return new Difference(
			accountedForMatches.get(0),
			accountedForMatches,
			childrenDiffs.otherUnMatchedCompartments
				.stream()
				.map(c -> {
					List<Compartment> res = new ArrayList<>();
					res.add(c);
					c.eAllContents().forEachRemaining(o -> {
						if (o instanceof Compartment)
							res.add((Compartment) o);
					});
					return res;
				}).flatMap(List::stream).collect(Collectors.toList()),
			childrenDiffs.myUnMatchedCompartments
				.stream()
				.map(c -> {
					List<Compartment> res = new ArrayList<>();
					res.add(c);
					c.eAllContents().forEachRemaining(o -> {
						if (o instanceof Compartment)
							res.add((Compartment) o);
					});
					return res;
				}).flatMap(List::stream).collect(Collectors.toList()),
			Optional.of(childrenDiffs),
			isSame,
			description);
	}

	public static class Difference {
		public final Match match;
		public final List<Match> accountsForMatches;
		public final Optional<ChildrenDiffResult> childrenDiffResult;
		public final List<Compartment> accountsForAdditions;
		public final List<Compartment> accountsForSubstractions;
		public final boolean isSame;
		public final String description;
		
		public Difference(
				Match match,
				List<Match> accountsForMatches,
				List<Compartment> accountsForAdditions,
				List<Compartment> accountsForSubstractions,
				Optional<ChildrenDiffResult> childrenDiffResult,
				boolean isSame,
				String description) {
			this.match = match;
			this.accountsForMatches = new ArrayList<>(accountsForMatches);
			this.accountsForAdditions = new ArrayList<>(accountsForAdditions);
			this.accountsForSubstractions = new ArrayList<>(accountsForSubstractions);
			this.childrenDiffResult = childrenDiffResult;
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
		
		public boolean isMove(Compartment c) {
			Optional<Match> opt = matches.find(c);
			return opt.isPresent() ? opt.get().isMove : false;
		}
	}

	public static MatchResult exactOrContainsLabelMatch(ComparisonContext context) {
		return exactOrContainsLabelMatch(context, false);
	}
	
	public static MatchResult exactOrContainsLabelMatch(ComparisonContext context, boolean doPrint) {
		MatchResult res = new MatchResult(context);
		List<Compartment> model1compartments = new ArrayList<>(context.modelctx1.compartments);
		List<Compartment> model2compartments = new ArrayList<>(context.modelctx2.compartments);
		if (doPrint) {
			System.out.println("Model1:\n" + model1compartments.stream().map(Compartment::getLabels).collect(Collectors.toList()));
			System.out.println("Model2:\n" + model2compartments.stream().map(Compartment::getLabels).collect(Collectors.toList()));
		}
		
		List<Compartment> model1NotExactMatchedCompartments = new ArrayList<>(model1compartments);
		List<Compartment> model2NotExactMatchedCompartments = new ArrayList<>(model2compartments);
		
		// look for same labels: ["S", "0"] matches only ["S", "0"]
		for (Compartment c1 : model1compartments)
			for (Compartment c2 : model2compartments)
				if (c1.getLabels().equals(c2.getLabels())) {
					res.matches.add(new Match(c1, c2));
					model1NotExactMatchedCompartments.remove(c1);
					model2NotExactMatchedCompartments.remove(c2);
				}
		
		List<Compartment> model1Not2ContainsAll1MatchedCompartments = new ArrayList<>(model1NotExactMatchedCompartments);
		List<Compartment> model2Not2ContainsAll1Compartments = new ArrayList<>(model2NotExactMatchedCompartments);

		// look for object from model1 who's labels are contained by a model2 object's labels: ["S"] (model1) matches [..., "S", ...] (model2)
		// Look only for unique labels so for example if [SI, S] and [SI, I] exist, SI would be uneligible but S and I are fine
		for (Compartment c1 : model1NotExactMatchedCompartments)
			for (Compartment c2 : model2NotExactMatchedCompartments)
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
		for (Compartment c1 : model1NotExactMatchedCompartments)
			for (Compartment c2 : model2NotExactMatchedCompartments)
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
		
		// at this point there are most likely unmatched epidemics as it doesn't
		// necessarily make sense for 2 models to be named the same for their top 
		// level element, so we just look for the first epidemic of each model and
		// if they are not matched we match and push them in front (logical order)
		Compartment epi1 = (Compartment) model1compartments.stream().filter(c -> c instanceof Compartment).findFirst().get();
		Compartment epi2 = (Compartment) model2compartments.stream().filter(c -> c instanceof Compartment).findFirst().get();
		
		// happy path
		if (res.find(epi1, epi2).isPresent())
			return res;

		Optional<Match> m1 = res.find(epi1);
		Optional<Match> m2 = res.find(epi2);
		
		// unhappy paths, either c1 or c2 is matched but not both, which means a second
		// epidemic or compartment in either model matched with the other model's first epidemic.
		// this is quite bad as it wouldn't make the comparison logic as straight forward
		// so we will just remove the conflicting match and add our top level match
		if (m1.isPresent() && !m2.isPresent())
			res.matches.remove(m1.get());
		else if (!m1.isPresent() && m2.isPresent())
			res.matches.remove(m2.get());
		
		res.matches.add(0, new Match(epi1, epi2));
		
		return res;
	}
}
