package use_epi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		Epidemic model1 = ((epimodel.EpidemicWrapper) epimodel.impl.EpimodelPackageImpl.loadModel(model1fn)).getEpidemic();
		Epidemic model2 = ((epimodel.EpidemicWrapper) epimodel.impl.EpimodelPackageImpl.loadModel(model2fn)).getEpidemic();
		return compare(model1, model2);
	}
	
	public static ComparisonResult compare(Epidemic model1, Epidemic model2) {
		List<Pair<String, String>> renamings = new ArrayList<>();
		ComparisonContext context = new ComparisonContext(model1, model2, renamings);
		MatchResult matches = Comparison.ExactOrContainsLabelMatch(context);
		
		System.out.println();
		System.out.println(matches);
		
		Match topLevelMatch = null;
		try {
			topLevelMatch = matches.find(model1, model2);
		} catch (Exception e) {}
		// if there is no top level match we might have a problem
		if (topLevelMatch == null) {
			Match left = null;
			Match right = null;
			try {
				left = matches.find(model1);
			} catch (Exception e) {
				// left = null
			}
			try {
				right = matches.find(model2);
			} catch (Exception e) {
				// right = null
			}
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
		
		for (Difference d : diffs) {
			// description isnt formatted so just find {...} and add newlines and tab them, and if there are newlines, tab them too
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
			System.out.println("diff: " + sb.toString() + "\n");
			
		}
		
		return new ComparisonResult(context, matches, diffs);
	}
}
