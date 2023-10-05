package epimodel.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Epidemic;
import epimodel.Flow;

public class Comparison {
	
	public static class ModelContext {
		public final Epidemic model;
		public final List<Compartment> compartments;
		public final Set<String> uniqueLabels;
		public final Set<String> duplicateLabels;
		
		public ModelContext(Epidemic model) {
			this.model = model;
			
			uniqueLabels = new HashSet<>();
			duplicateLabels = new HashSet<>();
			
			// this.compartments will be in breadth first order not depth first
			List<List<Compartment>> compartments_by_level =
				new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(
					model.getCompartmentwrapper().getCompartment()
				))));
			
			while (true) {
				List<CompartmentWrapper> next_level = new ArrayList<>();
				
				for (Compartment c : compartments_by_level.get(compartments_by_level.size() - 1))
					for (EObject e : c.eContents())
						if (e instanceof CompartmentWrapper)
							next_level.add((CompartmentWrapper) e);
				
				if (next_level.isEmpty())
					break;
				else
					compartments_by_level.add(
						next_level
							.stream()
							.map(CompartmentWrapper::getCompartment)
							.toList()
					);
			}
			compartments = compartments_by_level.stream().flatMap(List::stream).toList();
			
			model.eAllContents().forEachRemaining(eobject -> {
				if (!(eobject instanceof Compartment))
					return;
				Compartment compartment = (Compartment) eobject;
				duplicateLabels.addAll(
					compartment
						.getLabels()
						.stream()
						.filter(label -> uniqueLabels.contains(label) && !duplicateLabels.contains(label))
						.toList());
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
			
			childrenDiffs = childrenMatches.stream().map(m -> m.compare(matches)).toList();
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
				.toList();
			
			this.isSame = notSameChildrenMatchAndDiffs.size() == 0 &&
						  myUnMatchedCompartments.size() == 0 &&
						  otherUnMatchedCompartments.size() == 0;
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
						}).toList().toString()
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
						}).toList().toString()
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
		if (matches.find(me, other).isPresent())
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
				}).flatMap(List::stream).toList(),
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
				}).flatMap(List::stream).toList(),
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
			return opt.isPresent() && opt.get().isMove;
		}
	}
	
	@SafeVarargs
	public static <T> HashSet<T> intersection(Collection<T>... sets) {
        if (sets.length == 0)
            return new HashSet<>();
        
        HashSet<T> resultSet = new HashSet<>(sets[0]);

        for (int i = 1; i < sets.length; i++)
            resultSet.retainAll(sets[i]);

        return resultSet;
    }

	public static MatchResult contains_same_unique_label_match(ComparisonContext context) {
		MatchResult res = new MatchResult(context);
		Set<String> uniqueLabels = intersection(context.modelctx1.uniqueLabels, context.modelctx2.uniqueLabels);
		
		for (Compartment c1 : context.modelctx1.compartments)
			for (Compartment c2 : context.modelctx2.compartments)
				if (res.find(c1).isEmpty() &&
					res.find(c2).isEmpty() &&
					!intersection(
						c1.getLabels(),
						c2.getLabels(),
						uniqueLabels
					).isEmpty())
						res.matches.add(new Match(c1, c2));
		
		
		// for each matched element pair, if parents are not matched but same type, match them
		// this isn't a for (Match m : res.matches) as we are modifying matches as we go
		// and looping through the added elements as well is required to apply the parent matching recursively
		for (int i = 0; i < res.matches.size(); ++i) {
			Match m = res.matches.get(i);
			EObject leftParent = m.matchedCompartmentPair.first.eContainer().eContainer();
			EObject rightParent = m.matchedCompartmentPair.second.eContainer().eContainer();
			if (leftParent instanceof Compartment &&
				leftParent.getClass().equals(rightParent.getClass())
			) {
				Compartment leftParentc = (Compartment) leftParent;
				Compartment rightParentc = (Compartment) rightParent;
				if (res.find(leftParentc).isEmpty() &&
					res.find(rightParentc).isEmpty()
				) {
					// prepend it
					res.matches.add(i, new Match(leftParentc, rightParentc));
					// then backtrack
					i -= 1;
				}
			}
		}
		
		return res;
	}
}
