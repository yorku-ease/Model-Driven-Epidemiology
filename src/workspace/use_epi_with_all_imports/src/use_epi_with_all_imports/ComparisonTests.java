package use_epi_with_all_imports;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import epimodel.Epidemic;
import epimodel.util.Comparison.ComparisonResult;
import epimodel.util.Comparison.Difference;

class ComparisonTests {
	
	@BeforeAll
	static void init_metamodel() throws Exception {
		// load metamodel things in right order & make sure model creation is fine
		make_models.main(null);
	}
	
	@Test
	void test1() {
		// same models as test 8
		// please do not merge test 1 and 8 as test 1 is a way simpler regression test
		// in the event all tests fail its simpler to understand only test 1 than
		// test 8 and the tests are meant to be kind of
		// in increasing order of complexity
		
		// just a basic test, same composables, just 1 different flow (in product vs in a dimension of the product)
		// we are only looking at counts of matches in this test to make sure all objects are accounted for during matching
		Epidemic withFlowInGroup = make_models.create_model(
				make_models.product("withFlowInGroup",
					make_models.group("SEIR",
							make_models.compartment("S"),
							make_models.compartment("E"),
							make_models.product("I",
									make_models.group("Variants",
											make_models.compartment("DELTA"),
											make_models.compartment("OMICRON")),
									make_models.addRate(
											make_models.group("Infectious",
													make_models.compartment("Asymptomatic"),
													make_models.compartment("Symptomatic")),
											"Symptoms",
											Arrays.asList("Asymptomatic"),
											Arrays.asList("Symptomatic"))),
							make_models.compartment("R"))));
		
		Epidemic withFlowInProduct = make_models.create_model(
				make_models.product("withFlowInProduct",
					make_models.group("SEIR",
							make_models.compartment("S"),
							make_models.compartment("E"),
							make_models.product("I",
									make_models.rate("Symptoms",
											Arrays.asList("Asymptomatic"),
											Arrays.asList("Symptomatic")),
									make_models.group("Variants",
											make_models.compartment("DELTA"),
											make_models.compartment("OMICRON")),
									make_models.group("Infectious",
											make_models.compartment("Asymptomatic"),
											make_models.compartment("Symptomatic"))),
							make_models.compartment("R"))));
		
		ComparisonResult res = use_epi.Compare.compare(withFlowInGroup, withFlowInProduct);

		/* expect 12 matches:
		 *  1 for withFlowIn*
		 * 		1 for SEIR
		 * 			3 for compartments S,E,R
		 * 			1 for product I
		 * 				2 for groups Variants, Infectious
		 * 					4 for compartments Delta, Omicron, Asym, Sym
		 */
		int number_of_composables_in_both_models = 12;
		
		assertEquals(number_of_composables_in_both_models, res.context.modelctx1.compartments.size());
		assertEquals(number_of_composables_in_both_models, res.context.modelctx2.compartments.size());
		assertEquals(number_of_composables_in_both_models, res.matches.matches.size());
		assertEquals(1, res.diffs.size());
		// expect the first diff to be not same since the flow is in a different spot
		assertFalse(res.diffs.get(0).isSame);
		assertEquals(number_of_composables_in_both_models, res.diffs.get(0).accountsForMatches.size());
	}
	
	@Test
	void test2() {
		// test two models producing same physical compartments except the epidemic label
		// one uses a group SI containing S & I and the other just uses a group epidemic with 2 compartments SI.S, SI.I
		
		Epidemic model1 = make_models.create_model(make_models.group("group",
				make_models.compartment("SI", "S"),
				make_models.compartment("SI", "I")));
		
		Epidemic model2 = make_models.create_model(make_models.product("product",
				make_models.group("SI",
						make_models.compartment("S"),
						make_models.compartment("I"))));
		
		ComparisonResult res = use_epi.Compare.compare(model1, model2);
		
		// expect top level match, SI.S match S && SI.I match I
		assertEquals(3, res.matches.matches.size());
		assertEquals(Arrays.asList("SI", "S"), res.matches.matches.get(1).matchedCompartmentPair.first.getLabels());
		assertEquals(Arrays.asList("S"), res.matches.matches.get(1).matchedCompartmentPair.second.getLabels());
		assertEquals(Arrays.asList("SI", "I"), res.matches.matches.get(2).matchedCompartmentPair.first.getLabels());
		assertEquals(Arrays.asList("I"), res.matches.matches.get(2).matchedCompartmentPair.second.getLabels());

		// S, I and group are unique labels in the first model, SI is not as it is used twice
		assertEquals(new HashSet<String>(Arrays.asList("S", "I", "group")), res.context.modelctx1.uniqueLabels);
		assertEquals(new HashSet<String>(Arrays.asList("SI")), res.context.modelctx1.duplicateLabels);
		
		// in the second model the label SI is unique, along with product instead of group
		assertEquals(new HashSet<String>(Arrays.asList("S", "I", "SI", "product")), res.context.modelctx2.uniqueLabels);
		assertEquals(new HashSet<String>(), res.context.modelctx2.duplicateLabels);
	}
	
	@Test
	void test3() {
		// opposite of test 4
		// compare a product/dimension epidemic with another one that has one more dimension, being Age
		
		Epidemic withoutAge = make_models.create_model(make_models.product("withoutAge",
				make_models.group("SEIR",
						make_models.compartment("S"),
						make_models.compartment("E"),
						make_models.product("I",
								make_models.group("Variants",
										make_models.compartment("DELTA"),
										make_models.compartment("OMICRON")),
								make_models.group("Infectious",
										make_models.compartment("Asymptomatic"),
										make_models.compartment("Symptomatic"))),
						make_models.compartment("R"))));
		
		Epidemic withAge = make_models.create_model(make_models.product("withAge",
				make_models.group("SEIR",
						make_models.compartment("S"),
						make_models.compartment("E"),
						make_models.product("I",
								make_models.group("Variants",
										make_models.compartment("DELTA"),
										make_models.compartment("OMICRON")),
								make_models.group("Infectious",
										make_models.compartment("Asymptomatic"),
										make_models.compartment("Symptomatic"))),
						make_models.compartment("R")),
				make_models.group("Age",
						make_models.compartment("0_10"),
						make_models.compartment("11_25"),
						make_models.compartment("26_50"),
						make_models.compartment("51_"))));
		
		ComparisonResult res = use_epi.Compare.compare(withoutAge, withAge);
		
		// expect a singular top level diff
		assertEquals(1, res.diffs.size());
		
		// expect an ADD for Age and its 4 children
		assertEquals(5, res.diffs.get(0).accountsForAdditions.size());
		assertEquals(Arrays.asList("Age"), res.diffs.get(0).accountsForAdditions.get(0).getLabels());
		assertEquals(Arrays.asList("0_10"), res.diffs.get(0).accountsForAdditions.get(1).getLabels());
		assertEquals(Arrays.asList("11_25"), res.diffs.get(0).accountsForAdditions.get(2).getLabels());
		assertEquals(Arrays.asList("26_50"), res.diffs.get(0).accountsForAdditions.get(3).getLabels());
		assertEquals(Arrays.asList("51_"), res.diffs.get(0).accountsForAdditions.get(4).getLabels());
	}
	
	@Test
	void test4() {
		// opposite of test 3
		// compare a product/dimension epidemic with another one that has one less dimension, being Age
		
		Epidemic withoutAge = make_models.create_model(make_models.product("withoutAge",
				make_models.group("SEIR",
						make_models.compartment("S"),
						make_models.compartment("E"),
						make_models.product("I",
								make_models.group("Variants",
										make_models.compartment("DELTA"),
										make_models.compartment("OMICRON")),
								make_models.group("Infectious",
										make_models.compartment("Asymptomatic"),
										make_models.compartment("Symptomatic"))),
						make_models.compartment("R"))));
		
		Epidemic withAge = make_models.create_model(make_models.product("withAge",
				make_models.group("SEIR",
						make_models.compartment("S"),
						make_models.compartment("E"),
						make_models.product("I",
								make_models.group("Variants",
										make_models.compartment("DELTA"),
										make_models.compartment("OMICRON")),
								make_models.group("Infectious",
										make_models.compartment("Asymptomatic"),
										make_models.compartment("Symptomatic"))),
						make_models.compartment("R")),
				make_models.group("Age",
						make_models.compartment("0_10"),
						make_models.compartment("11_25"),
						make_models.compartment("26_50"),
						make_models.compartment("51_"))));
		
		ComparisonResult res = use_epi.Compare.compare(withAge, withoutAge);
		
		// expect a singular top level diff
		assertEquals(1, res.diffs.size());
		
		// expect an ADD for Age and its 4 children
		assertEquals(5, res.diffs.get(0).accountsForSubstractions.size());
		assertEquals(Arrays.asList("Age"), res.diffs.get(0).accountsForSubstractions.get(0).getLabels());
		assertEquals(Arrays.asList("0_10"), res.diffs.get(0).accountsForSubstractions.get(1).getLabels());
		assertEquals(Arrays.asList("11_25"), res.diffs.get(0).accountsForSubstractions.get(2).getLabels());
		assertEquals(Arrays.asList("26_50"), res.diffs.get(0).accountsForSubstractions.get(3).getLabels());
		assertEquals(Arrays.asList("51_"), res.diffs.get(0).accountsForSubstractions.get(4).getLabels());
	}
	
	@Test
	void test5() {
		// opposite of test 6
		// compare a product/dimension epidemic with another one that has one more dimension, being Symptoms
		// but its SEIR.I dimension has one less dimension, being Symptoms
		// we want to detect the "move" operation for Symptoms
		
		Epidemic SymptomsOutside = make_models.create_model(make_models.product("SymptomsOutside",
				make_models.group("SEIR",
						make_models.compartment("S"),
						make_models.compartment("E"),
						make_models.product("I",
								make_models.group("Variants",
										make_models.compartment("DELTA"),
										make_models.compartment("OMICRON"))),
						make_models.compartment("R")),
				make_models.group("Symptoms",
						make_models.compartment("Asymptomatic"),
						make_models.compartment("Symptomatic"))));
		
		Epidemic SymptomsInside = make_models.create_model(make_models.product("SymptomsInside",
				make_models.group("SEIR",
						make_models.compartment("S"),
						make_models.compartment("E"),
						make_models.product("I",
								make_models.group("Variants",
										make_models.compartment("DELTA"),
										make_models.compartment("OMICRON")),
								make_models.group("Symptoms",
										make_models.compartment("Asymptomatic"),
										make_models.compartment("Symptomatic"))),
						make_models.compartment("R"))));
		
		ComparisonResult res = use_epi.Compare.compare(SymptomsOutside, SymptomsInside);
		
		// expect 2 diffs, one for the epidemic and one for group Symptoms
		assertEquals(2, res.diffs.size());
		assertEquals(Arrays.asList("SymptomsOutside"), res.diffs.get(0).accountsForMatches.get(0).matchedCompartmentPair.first.getLabels());
		assertEquals(Arrays.asList("SymptomsInside"), res.diffs.get(0).accountsForMatches.get(0).matchedCompartmentPair.second.getLabels());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(1).accountsForMatches.get(0).matchedCompartmentPair.first.getLabels());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(1).accountsForMatches.get(0).matchedCompartmentPair.second.getLabels());
		
		// expect diff of epidemic to account for remove and diff of symptoms to account for match
		assertEquals(3, res.diffs.get(0).accountsForSubstractions.size());
		assertEquals(3, res.diffs.get(1).accountsForMatches.size());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(0).accountsForSubstractions.get(0).getLabels());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(1).accountsForMatches.get(0).matchedCompartmentPair.first.getLabels());
		
		// just in case check the other lists expected empty
		assertEquals(0, res.diffs.get(0).accountsForAdditions.size());
		assertEquals(0, res.diffs.get(1).accountsForAdditions.size());
		assertEquals(0, res.diffs.get(1).accountsForSubstractions.size());
	}
	
	@Test
	void test6() {
		// opposite of test 5
		// compare a product/dimension epidemic with another one that has one less dimension, being Symptoms
		// but its SEIR.I dimension has one more dimension, being Symptoms
		// we want to detect the "move" operation for Symptoms
		
		Epidemic SymptomsOutside = make_models.create_model(make_models.product("SymptomsOutside",
				make_models.group("SEIR",
						make_models.compartment("S"),
						make_models.compartment("E"),
						make_models.product("I",
								make_models.group("Variants",
										make_models.compartment("DELTA"),
										make_models.compartment("OMICRON"))),
						make_models.compartment("R")),
				make_models.group("Symptoms",
						make_models.compartment("Asymptomatic"),
						make_models.compartment("Symptomatic"))));
		
		Epidemic SymptomsInside = make_models.create_model(make_models.product("SymptomsInside",
				make_models.group("SEIR",
						make_models.compartment("S"),
						make_models.compartment("E"),
						make_models.product("I",
								make_models.group("Variants",
										make_models.compartment("DELTA"),
										make_models.compartment("OMICRON")),
								make_models.group("Symptoms",
										make_models.compartment("Asymptomatic"),
										make_models.compartment("Symptomatic"))),
						make_models.compartment("R"))));
		
		ComparisonResult res = use_epi.Compare.compare(SymptomsInside, SymptomsOutside);

		// expect 2 diffs, one for the epidemic and one for group Symptoms
		assertEquals(2, res.diffs.size());
		assertEquals(Arrays.asList("SymptomsInside"), res.diffs.get(0).accountsForMatches.get(0).matchedCompartmentPair.first.getLabels());
		assertEquals(Arrays.asList("SymptomsOutside"), res.diffs.get(0).accountsForMatches.get(0).matchedCompartmentPair.second.getLabels());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(1).accountsForMatches.get(0).matchedCompartmentPair.first.getLabels());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(1).accountsForMatches.get(0).matchedCompartmentPair.second.getLabels());
		
		// expect diff of epidemic to account for remove and diff of symptoms to account for match
		assertEquals(3, res.diffs.get(0).accountsForAdditions.size());
		assertEquals(3, res.diffs.get(1).accountsForMatches.size());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(0).accountsForAdditions.get(0).getLabels());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(1).accountsForMatches.get(0).matchedCompartmentPair.first.getLabels());
		
		// just in case check the other lists expected empty
		assertEquals(0, res.diffs.get(0).accountsForSubstractions.size());
		assertEquals(0, res.diffs.get(1).accountsForSubstractions.size());
		assertEquals(0, res.diffs.get(1).accountsForAdditions.size());
	}
	
	@Test
	void test7() {
		// test two models with a naming conflict
		
		Epidemic model1 = make_models.create_model(make_models.group("conflict",
				make_models.group("whatever",
						make_models.compartment("S"),
						make_models.compartment("I"))));
		
		Epidemic model2 = make_models.create_model(make_models.group("ok",
				make_models.group("conflict",
						make_models.compartment("S"),
						make_models.compartment("I"))));
		
		ComparisonResult res = use_epi.Compare.compare(model1, model2);
		
		// expect 3 diffs: one for conflict, 1 for S, 1 for I
		// the top level diff will have 1 add "conflict" and 1 remove "whatever"
		assertEquals(3, res.diffs.size());
		Difference epidiff = res.diffs.get(0);
		assertEquals(1, epidiff.accountsForMatches.size());
		assertEquals(Arrays.asList("conflict"), epidiff.accountsForMatches.get(0).matchedCompartmentPair.first.getLabels());
		assertEquals(Arrays.asList("conflict"), epidiff.accountsForMatches.get(0).matchedCompartmentPair.second.getLabels());
		// expect to find that the top level diff thinks there are 3 deleted and 2 added elements
		// model2 conflict sees S, I added and thinks whatever, S, I have been removed
		assertEquals(2, epidiff.accountsForAdditions.size());
		assertEquals(3, epidiff.accountsForSubstractions.size());
		assertEquals(Arrays.asList("whatever"), epidiff.accountsForSubstractions.get(0).getLabels());
		assertEquals(Arrays.asList("S"), epidiff.accountsForSubstractions.get(1).getLabels());
		assertEquals(Arrays.asList("I"), epidiff.accountsForSubstractions.get(2).getLabels());
		assertEquals(Arrays.asList("S"), epidiff.accountsForAdditions.get(0).getLabels());
		assertEquals(Arrays.asList("I"), epidiff.accountsForAdditions.get(1).getLabels());
		// but also expect to see that S and I are actually moves but not whatever and conflict
		assertFalse(res.isMove(epidiff.accountsForSubstractions.get(0)));  // whatever
		assertTrue(res.isMove(epidiff.accountsForSubstractions.get(1))); // S
		assertTrue(res.isMove(epidiff.accountsForSubstractions.get(2))); // I
		assertTrue(res.isMove(epidiff.accountsForAdditions.get(0))); // S
		assertTrue(res.isMove(epidiff.accountsForAdditions.get(1))); // I
	}
	
	@Test
	void test8() {
		// same model as test 1
		// please do not merge test 1 and 8 as test 1 is a way simpler regression test
		// in the event all tests fail its simpler to understand only test 1 than
		// test 8 and the tests are meant to be kind of
		// in increasing order of complexity
		
		Epidemic withFlowInGroup = make_models.create_model(make_models.product("withFlowInGroup",
				make_models.group("SEIR",
						make_models.compartment("S"),
						make_models.compartment("E"),
						make_models.product("I",
								make_models.group("Variants",
										make_models.compartment("DELTA"),
										make_models.compartment("OMICRON")),
								make_models.addRate(
										make_models.group("Infectious",
												make_models.compartment("Asymptomatic"),
												make_models.compartment("Symptomatic")),
										"Symptoms",
										Arrays.asList("Asymptomatic"),
										Arrays.asList("Symptomatic"))),
						make_models.compartment("R"))));
		
		Epidemic withFlowInProduct = make_models.create_model(make_models.product("withFlowInProduct",
				make_models.group("SEIR",
						make_models.compartment("S"),
						make_models.compartment("E"),
						make_models.addRate(
							make_models.product("I",
									make_models.group("Variants",
											make_models.compartment("DELTA"),
											make_models.compartment("OMICRON")),
									make_models.group("Infectious",
											make_models.compartment("Asymptomatic"),
											make_models.compartment("Symptomatic"))),
							"Symptoms",
							Arrays.asList("Asymptomatic"),
							Arrays.asList("Symptomatic")),
						make_models.compartment("R"))));
		
		ComparisonResult res = use_epi.Compare.compare(withFlowInGroup, withFlowInProduct);

		/* expect 12 matches:
		 * 	1 for epidemic
		 * 		1 for group
		 * 			3 for compartments
		 * 			1 for product
		 * 				2 for groups
		 * 					4 for compartments
		 */
		int number_of_composables_in_both_models = 12;
		
		assertEquals(number_of_composables_in_both_models, res.context.modelctx1.compartments.size());
		assertEquals(number_of_composables_in_both_models, res.context.modelctx2.compartments.size());
		assertEquals(number_of_composables_in_both_models, res.matches.matches.size());
		assertEquals(1, res.diffs.size());
		Difference topLevelDiff = res.diffs.get(0);
		// expect the first diff to be not same since the flow is in a different spot
		assertFalse(topLevelDiff.isSame);
		assertEquals(number_of_composables_in_both_models, topLevelDiff.accountsForMatches.size());
		
		// expect that we can find the match of product I and the match for group Infectious and both are considered different
		// childrenDiffs of epidemic should:
		// be there:
		assertTrue(topLevelDiff.childrenDiffResult.isPresent());
		// have a diff:
		assertEquals(1, topLevelDiff.childrenDiffResult.get().childrenDiffs.size());
		// and that diff should be for SEIR
		Difference seirDiff = topLevelDiff.childrenDiffResult.get().childrenDiffs.get(0);
		assertEquals(Arrays.asList("SEIR"), seirDiff.match.matchedCompartmentPair.first.getLabels());
		// and seir diff should have 4 diffs for S,E,I and R
		assertEquals(4, seirDiff.childrenDiffResult.get().childrenDiffs.size());
		// and the 3rd diff should be for I
		Difference IDiff = seirDiff.childrenDiffResult.get().childrenDiffs.get(2);
		assertEquals(Arrays.asList("I"), IDiff.match.matchedCompartmentPair.first.getLabels());
		// and that diff should not be same
//		assertFalse(IDiff.isSame);
	}
	
	@Test
	void test_rename_top_level() {
		Epidemic a = make_models.create_model(make_models.compartment("1"));
		
		Epidemic b = make_models.create_model(
				make_models.group("b",
						make_models.compartment("1")));
		
		ComparisonResult res = use_epi.Compare.compare(a, b);
		
		// expect match 1 -> 1
		assertEquals(1, res.matches.matches.size());
		assertEquals(1, res.diffs.size());
		Difference diff_of_1s = res.diffs.get(0);
		assertTrue(diff_of_1s.isSame);
		assertEquals(0, diff_of_1s.accountsForAdditions.size());
		assertEquals(0, diff_of_1s.accountsForSubstractions.size());
	}
	
	@Test
	void test_rename_non_top_level() {
		Epidemic a1k = make_models.create_model(
				make_models.group("a",
						make_models.group("1",
								make_models.compartment("k"))));
		
		Epidemic a2k = make_models.create_model(
				make_models.group("a",
						make_models.group("2",
								make_models.compartment("k"))));
		
		ComparisonResult res = use_epi.Compare.compare(a1k, a2k);
		
		// expect 1 to match 2 because type and children match
		assertEquals(3, res.matches.matches.size());
		assertEquals(1, res.diffs.size());
		Difference topLevelDiff = res.diffs.get(0);
		// expect the first diff to be not same since it is a rename
		assertFalse(topLevelDiff.isSame);
	}
}
