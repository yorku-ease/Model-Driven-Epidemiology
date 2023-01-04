package use_epi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import epimodel.Compartment;
import epimodel.Epidemic;
import epimodel.util.Comparison;
import epimodel.util.Comparison.ComparisonContext;
import epimodel.util.Comparison.ComparisonResult;
import epimodel.util.Comparison.Difference;
import epimodel.util.Comparison.Pair;
import epimodel.util.Comparison.Match;
import epimodel.util.Comparison.MatchResult;

public class Compare {
	public static void main(String[] args) {
		String model1fn = "../../runtime-extensions/Modeling1/Modeling1.epimodel";
		String model2fn = "../../runtime-extensions/Modeling2/Modeling2.epimodel";
		compare(model1fn, model2fn);
	}
	
	public static ComparisonResult compare(String model1fn, String model2fn) {
		System.out.println("Comparing " + model1fn + " and " + model2fn);
		Epidemic model1 = (Epidemic) epimodel.impl.EpimodelPackageImpl.loadModel(model1fn);
		Epidemic model2 = (Epidemic) epimodel.impl.EpimodelPackageImpl.loadModel(model2fn);
		return compare(model1, model2, true);
	}
	
	public static ComparisonResult compare(Epidemic model1, Epidemic model2) {
		return compare(model1, model2, false);
	}
	
	public static ComparisonResult compare(Epidemic model1, Epidemic model2, boolean doPrint) {
		List<Pair<String, String>> renamings = new ArrayList<>();
		ComparisonContext context = new ComparisonContext(model1, model2, renamings);
		MatchResult matches = Comparison.exactOrContainsLabelMatch(context, doPrint);
		
		if (doPrint) {
			System.out.println();
			System.out.println(matches);
		}
		
		Compartment topLevel1 = model1.getCompartmentwrapper().getCompartment();
		Compartment topLevel2 = model2.getCompartmentwrapper().getCompartment();
		
		Match topLevelMatch = matches.find(topLevel1, topLevel2).orElse(null);
		// if there is no top level match we might have a problem
		if (topLevelMatch == null) {
			Match left = matches.find(topLevel1).orElse(null);
			Match right = matches.find(topLevel2).orElse(null);
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
		List<Difference> diffs = new ArrayList<>(Arrays.asList(topLevel1.compare(topLevel2, matches)));
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
				diffs.add(match.matchedCompartmentPair.first.compare(match.matchedCompartmentPair.second, matches));
		}
		
		for (Difference d : diffs) {
			for (Compartment c : d.accountsForAdditions)
				matches.find(c).ifPresent(m -> m.isMove = true);
			for (Compartment c : d.accountsForSubstractions)
				matches.find(c).ifPresent(m -> m.isMove = true);
		}
		
		if (doPrint)
			for (Difference d : diffs) {
				// description isnt formatted so just find `{` and `}`, add newlines and indent them, and if there are newlines, tab them too
				String s = d.description;
				StringBuilder sb = new StringBuilder();
				int depth = 0;
				for (int i = 0; i < s.length(); ++i) {
					char c = s.charAt(i);
					if (c == '{') {
						depth += 1;
						sb.append("{\n");
						for (int j = 0; j < depth; ++j)
							sb.append("\t");
					}
					else if (c == '}') {
						depth -= 1;
						sb.append("\n");
						for (int j = 0; j < depth; ++j)
							sb.append("\t");
						sb.append("}");
					}
					else if (c == '\n') {
						sb.append("\n");
						for (int j = 0; j < depth; ++j)
							sb.append("\t");
					}
					else
						sb.append(c);
				}
				System.out.println();
				System.out.println("diff: " + sb.toString());
			}
		
		return new ComparisonResult(context, matches, diffs);
	}
}
