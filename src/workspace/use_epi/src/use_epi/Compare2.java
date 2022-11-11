package use_epi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import epimodel.Composable;
import epimodel.Epidemic;

public class Compare2 {
	public static void main(String[] args) throws Exception {
		String model1 = "../../runtime-EclipseApplication/modeling/DEPGG_COVID_INF_VAR_SEIR_copy.epimodel";
		String model2 = "../../runtime-EclipseApplication/modeling/DEPGG_COVID_INF_VAR_SEIR.epimodel";
		compare(model1, model2);
	}
	
	private static void compare(String model1str, String model2str) throws Exception {
		System.out.println("Comparing " + model1str + " and " + model2str);
//		compare((epimodel.EpidemicWrapper) loadModel(model1), (epimodel.EpidemicWrapper) loadModel(model2));
		Epidemic model1 = ((epimodel.EpidemicWrapper) epimodel.impl.EpimodelPackageImpl.loadModel(model1str)).getEpidemic();
		Epidemic model2 = ((epimodel.EpidemicWrapper) epimodel.impl.EpimodelPackageImpl.loadModel(model2str)).getEpidemic();
		List<Pair<String, String>> renamings = new ArrayList<>();
		MatchResult matches = ExactLabelMatch(new CompareContext(model1, model2, renamings));
	}

	public static class CompareContext {
		final List<Pair<String, String>> renamings;
		final Epidemic model1;
		final Epidemic model2;
		
		CompareContext(Epidemic model1, Epidemic model2, List<Pair<String, String>> renamings) {
			this.model1 = model1;
			this.model2 = model2;
			this.renamings = renamings;
//			Map.entry("", "");
		}
	}
	
	public static MatchResult ExactLabelMatch(CompareContext context) {
		MatchResult res = new MatchResult();
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
		System.out.println("Matches:");
		for (Composable c1 : model1compartments)
			for (Composable c2 : model2compartments)
				if (c1.getLabels().equals(c2.getLabels())) {
					System.out.println("\t" + c1.getLabels() + " = " + c2.getLabels());
					res.matches.add(new Pair<>(c1, c2));
				}
		return res;
	}
	
	public static class MatchResult {
		public final List<Pair<Composable, Composable>> matches = new ArrayList<>();
	}
	
	public static class DiffResult {
		public final List<Diff> diffs = new ArrayList<>();
		public final class Diff {
			
		}
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
