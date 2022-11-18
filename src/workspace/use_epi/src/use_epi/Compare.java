package use_epi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import epimodel.Composable;
import epimodel.Epidemic;
import epimodel.util.Comparison.CompareContext;
import epimodel.util.Comparison.Difference;
import epimodel.util.Comparison.Pair;
import epimodel.util.Comparison.Match;
import epimodel.util.Comparison.MatchResult;

public class Compare {
	public static void main(String[] args) {
		String model1fn = "../../runtime-EclipseApplication/modeling/DEPGG_COVID_INF_VAR_SEIR_copy.epimodel";
		String model2fn = "../../runtime-EclipseApplication/modeling/DEPGG_COVID_INF_VAR_SEIR.epimodel";
		compare(model1fn, model2fn);
	}
	
	private static void compare(String model1fn, String model2fn) {
		System.out.println("Comparing " + model1fn + " and " + model2fn);
		Epidemic model1 = ((epimodel.EpidemicWrapper) epimodel.impl.EpimodelPackageImpl.loadModel(model1fn)).getEpidemic();
		Epidemic model2 = ((epimodel.EpidemicWrapper) epimodel.impl.EpimodelPackageImpl.loadModel(model2fn)).getEpidemic();
		compare(model1, model2);
	}
	
	private static void compare(Epidemic model1, Epidemic model2) {
		List<Pair<String, String>> renamings = new ArrayList<>();
		MatchResult matches = ExactOrContainsLabelMatch(new CompareContext(model1, model2, renamings));
		
		System.out.println();
		System.out.println(matches);
		
		Match topLevelMatch = matches.find(model1, model2);
		// if there is no top level match we might have a problem
		if (topLevelMatch == null) {
			Match left = matches.find(model1);
			Match right = matches.find(model2);
			// if either top level element left or right is matched, but not the other,
			// we remove the match because we don't want that match.
			// It is simpler to always assume a match for both top level elements
			if (left == null && right != null)
				matches.matches.remove(right);
			else if (left != null && right == null)
				matches.matches.remove(left);
			// else they are both unmatched and that is ok
		}
		
		// start diff with top level element always
		Difference diff = model1.compare(model2, matches);
		List<Difference> diffs = new ArrayList<>(Arrays.asList(diff));
		// iterate other matches in case the top level element did not account for all matches
		// this happens if at some level incompatible type of objects are compared and unable to compare their children
		for (Match match : matches.matches) {
			boolean accountedFor = false;
			for (Difference d : diffs)
				if (d.accountsForMatches.contains(match)) {
					accountedFor = true;
					break;
				}
			if (!accountedFor)
				diffs.add(match.match.first.compare(match.match.second, matches));
		}
		System.out.println();
		for (Difference d : diffs)
			System.out.println("diff: " + d.getSimpleDescription() + "\n");
	}
	
	public static MatchResult ExactOrContainsLabelMatch(CompareContext context) {
		MatchResult res = new MatchResult(context);
		List<Composable> model1compartments = new ArrayList<>();
		List<Composable> model2compartments = new ArrayList<>();
		context.model1.eAllContents().forEachRemaining(eobject -> {
			if (eobject instanceof Composable)
				model1compartments.add((Composable) eobject);
		});
		context.model2.eAllContents().forEachRemaining(eobject -> {
			if (eobject instanceof Composable)
				model2compartments.add((Composable) eobject);
		});
		System.out.println("Model1:");
		System.out.println(model1compartments.stream().map(Composable::getLabels).collect(Collectors.toList()));
		System.out.println("Model2:");
		System.out.println(model2compartments.stream().map(Composable::getLabels).collect(Collectors.toList()));
		
		List<Composable> model1NotExactMatchedCompartments = new ArrayList<>(model1compartments);
		List<Composable> model2NotExactMatchedCompartments = new ArrayList<>(model2compartments);
		
		// look for same arrays: ["S", "0"] matches only ["S", "0"]
		for (Composable c1 : model1compartments)
			for (Composable c2 : model2compartments)
				if (c1.getLabels().equals(c2.getLabels())) {
					res.matches.add(new Match(c1, c2));
					model1NotExactMatchedCompartments.remove(c1);
					model2NotExactMatchedCompartments.remove(c2);
				}
		
		List<Composable> model1Not2ContainsAll1MatchedCompartments = new ArrayList<>(model1NotExactMatchedCompartments);
		List<Composable> model2Not2ContainsAll1Compartments = new ArrayList<>(model2NotExactMatchedCompartments);

		// look for model1 array contained by model2 array: ["S"] matches [..., "S", ...]
		// Labels are unique so even though it could happen that there are multiple matches, it shouldn't if the model is correct
		for (Composable c1 : model1NotExactMatchedCompartments)
			for (Composable c2 : model2NotExactMatchedCompartments)
				if (c2.getLabels().containsAll(c1.getLabels())) {
					res.matches.add(new Match(c1, c2));
					model1Not2ContainsAll1MatchedCompartments.remove(c1);
					model2Not2ContainsAll1Compartments.remove(c2);
				}

		// look for model2 array contained by model1 array: [..., "S", ...] matches ["S"] 
		// Labels are unique so even though it could happen that there are multiple matches, it shouldn't if the model is correct
		for (Composable c1 : model1NotExactMatchedCompartments)
			for (Composable c2 : model2NotExactMatchedCompartments)
				if (c1.getLabels().containsAll(c2.getLabels()))
					res.matches.add(new Match(c1, c2));
		
		return res;
	}
}
