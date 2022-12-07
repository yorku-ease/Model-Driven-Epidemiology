package use_epi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import epimodel.util.Comparison.ComparisonResult;

class CompareTest {
	@Test
	void test1() {
		String model1fn = "../../runtime-extensions/Modeling1/Modeling1.epimodel";
		String model2fn = "../../runtime-extensions/Modeling2/Modeling2.epimodel";
		ComparisonResult res = Compare.compare(model1fn, model2fn);

		assertTrue(res.context.model1.getLabels().get(0).equals("withFlowInGroup"));
		assertTrue(res.context.model2.getLabels().get(0).equals("withFlowInProduct"));

		int number_of_compartments_with_identical_names_in_both_models = 6;
		int number_of_composable_objects_in_total_per_model = 1 + number_of_compartments_with_identical_names_in_both_models;
		
		assertTrue(res.context.model1compartments.size() == number_of_composable_objects_in_total_per_model);
		assertTrue(res.context.model2compartments.size() == number_of_composable_objects_in_total_per_model);
		assertTrue(res.matches.matches.size() == number_of_compartments_with_identical_names_in_both_models);
		assertTrue(res.diffs.size() == 1);
		assertTrue(res.diffs.get(0).accountsForMatches.size() == number_of_compartments_with_identical_names_in_both_models);
	}
	@Test
	void test2() {
		String model1fn = "../../test-models/GECC_SI_S_I.epimodel";
		String model2fn = "../../test-models/DEG_SI_S_I.epimodel";
		ComparisonResult res = Compare.compare(model1fn, model2fn);
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
