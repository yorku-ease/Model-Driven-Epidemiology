package use_epi_with_all_imports;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import epimodel.Epidemic;
import epimodel.util.Comparison.ComparisonResult;

class ComparisonTests {
	@Test
	void test1() {
		String model1fn = "../../runtime-extensions/Modeling1/Modeling1.epimodel";
		String model2fn = "../../runtime-extensions/Modeling2/Modeling2.epimodel";
		ComparisonResult res = use_epi.Compare.compare(model1fn, model2fn);

		assertTrue(res.context.modelctx1.model.getLabels().get(0).equals("withFlowInGroup"));
		assertTrue(res.context.modelctx2.model.getLabels().get(0).equals("withFlowInProduct"));

		int number_of_compartments_with_identical_names_in_both_models = 6;
		int number_of_composable_objects_in_total_per_model = 1 + number_of_compartments_with_identical_names_in_both_models;
		
		assertTrue(res.context.modelctx1.composables.size() == number_of_composable_objects_in_total_per_model);
		assertTrue(res.context.modelctx2.composables.size() == number_of_composable_objects_in_total_per_model);
		assertTrue(res.matches.matches.size() == number_of_compartments_with_identical_names_in_both_models);
		assertTrue(res.diffs.size() == 1);
		assertTrue(res.diffs.get(0).accountsForMatches.size() == number_of_compartments_with_identical_names_in_both_models);
	}
	@Test
	void test2() {
		// test two models producing same physical compartments except the epidemic label
		// one uses a group SI containing S & I and the other just uses a group epidemic with 2 compartments SI.S, SI.I
		
		Epidemic model1 = make_models.groupEpi("GECC_SI_S_I",
				make_models.compartment("SI", "S"),
				make_models.compartment("SI", "I"));
		
		Epidemic model2 = make_models.productEpi("DEG_SI_S_I",
				make_models.group("SI",
						make_models.compartment("S"),
						make_models.compartment("I")));
		
		ComparisonResult res = use_epi.Compare.compare(model1, model2);
		
		// expect SI.S match S && SI.I match I
		// expect no match for top level as no common unique labels even though the diff output says they "match"
		assertTrue(res.matches.matches.size() == 2);
		assertTrue(res.matches.matches.get(0).match.first.getLabels().equals(Arrays.asList("SI", "S")));
		assertTrue(res.matches.matches.get(0).match.second.getLabels().equals(Arrays.asList("S")));
		assertTrue(res.matches.matches.get(1).match.first.getLabels().equals(Arrays.asList("SI", "I")));
		assertTrue(res.matches.matches.get(1).match.second.getLabels().equals(Arrays.asList("I")));

		// assert same unique labels and only S and I not SI which is duplicate or the label of the top level object which is ignored
		assertTrue(res.context.modelctx1.uniqueLabels.equals(new HashSet<String>(Arrays.asList("S", "I"))));
		// in the second model the label SI appears only once, so we expect it too
		assertTrue(res.context.modelctx2.uniqueLabels.equals(new HashSet<String>(Arrays.asList("S", "I", "SI"))));
		
		assertTrue(res.context.modelctx1.duplicateLabels.equals(new HashSet<String>(Arrays.asList("SI"))));
		assertTrue(res.context.modelctx2.duplicateLabels.equals(new HashSet<String>()));
	}
	
//	@Test
//	void test2() {
//		List<List<String>> leftPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S")
//		);
//		List<List<String>> rightPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S", "1")
//		);
//		Compare.PhysicalMatchResult res = Compare.physicalMatchLists(leftPhysicalCompartments, rightPhysicalCompartments);
//		assertTrue(res.isValidResult());
//		
//		assertTrue(res.generalizations.size() == 0);
//		assertTrue(res.specializations.size() == 0);
//		assertTrue(res.mappingsLeft.size() == 1);
//		assertTrue(res.mappingsRight.size() == 1);
//		assertFalse(res.mappingsLeft.equals(res.mappingsRight));
//	}
//	
//	@Test
//	void test3() {
//		List<List<String>> leftPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S", "1")
//		);
//		List<List<String>> rightPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S")
//		);
//		Compare.PhysicalMatchResult res = Compare.physicalMatchLists(leftPhysicalCompartments, rightPhysicalCompartments);
//		assertTrue(res.isValidResult());
//		
//		assertTrue(res.generalizations.size() == 0);
//		assertTrue(res.specializations.size() == 0);
//		assertTrue(res.mappingsLeft.size() == 1);
//		assertTrue(res.mappingsRight.size() == 1);
//		assertFalse(res.mappingsLeft.equals(res.mappingsRight));
//	}
//	
//	@Test
//	void test4() {
//		List<List<String>> leftPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S", "0")
//		);
//		List<List<String>> rightPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S", "1")
//		);
//		Compare.PhysicalMatchResult res = Compare.physicalMatchLists(leftPhysicalCompartments, rightPhysicalCompartments);
//		assertTrue(res.isValidResult());
//		
//		assertTrue(res.generalizations.size() == 0);
//		assertTrue(res.specializations.size() == 0);
//		assertTrue(res.mappingsLeft.size() == 0);
//		assertTrue(res.mappingsRight.size() == 0);
//		assertTrue(res.additions.size() == 1);
//		assertTrue(res.additions.get(0).equals(Arrays.asList("S", "1")));
//		assertTrue(res.substractions.size() == 1);
//		assertTrue(res.substractions.get(0).equals(Arrays.asList("S", "0")));
//	}
//	
//	@Test
//	void test5() {
//		List<List<String>> leftPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S")
//		);
//		List<List<String>> rightPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S"),
//			Arrays.asList("S", "1")
//		);
//		Compare.PhysicalMatchResult res = Compare.physicalMatchLists(leftPhysicalCompartments, rightPhysicalCompartments);
//
//		System.out.println(res);
//		assertTrue(res.isValidResult());
//		
//		assertTrue(res.generalizations.size() == 0);
//		assertTrue(res.specializations.size() == 1);
//		assertTrue(res.specializations.get(Arrays.asList("S")).size() == 2);
//		assertTrue(res.mappingsLeft.size() == 0);
//		assertTrue(res.mappingsRight.size() == 0);
//		assertTrue(res.additions.size() == 0);
//		assertTrue(res.substractions.size() == 0);
//	}
//	
//	@Test
//	void test6() {
//		List<List<String>> leftPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S", "1")
//		);
//		List<List<String>> rightPhysicalCompartments = Arrays.asList(
//			Arrays.asList("S"),
//			Arrays.asList("S", "1")
//		);
//		Compare.PhysicalMatchResult res = Compare.physicalMatchLists(leftPhysicalCompartments, rightPhysicalCompartments);
//
//		System.out.println(res);
//		assertTrue(res.isValidResult());
//		
//		assertTrue(res.generalizations.size() == 0);
//		assertTrue(res.generalizations.get(Arrays.asList("S")).size() == 1);
//		assertTrue(res.specializations.size() == 1);
//		assertTrue(res.mappingsLeft.size() == 0);
//		assertTrue(res.mappingsRight.size() == 0);
//		assertTrue(res.additions.size() == 0);
//		assertTrue(res.substractions.size() == 0);
//	}
}
