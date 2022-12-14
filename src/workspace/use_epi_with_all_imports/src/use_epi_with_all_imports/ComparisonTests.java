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
		// load things in right order & make sure model creation is fine
		make_models.main(null);
	}
	
	@Test
	void test1() {
		Epidemic withFlowInGroup = make_models.productEpi("withFlowInGroup",
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
						make_models.compartment("R")));
		
		Epidemic withFlowInProduct = make_models.productEpi("withFlowInProduct",
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
						make_models.compartment("R")));
		
		ComparisonResult res = use_epi.Compare.compare(withFlowInGroup, withFlowInProduct);

		int number_of_composables_that_have_the_same_names_in_both_models = 11;
		int number_of_composables_in_both_models = 1 + number_of_composables_that_have_the_same_names_in_both_models;
		
		assertEquals(number_of_composables_in_both_models, res.context.modelctx1.composables.size());
		assertEquals(number_of_composables_in_both_models, res.context.modelctx2.composables.size());
		assertEquals(number_of_composables_that_have_the_same_names_in_both_models, res.matches.matches.size());
		assertEquals(1, res.diffs.size());
		assertEquals(number_of_composables_in_both_models, res.diffs.get(0).accountsForMatches.size());
	}
	
	@Test
	void test2() {
		// test two models producing same physical compartments except the epidemic label
		// one uses a group SI containing S & I and the other just uses a group epidemic with 2 compartments SI.S, SI.I
		
		Epidemic model1 = make_models.groupEpi("group",
				make_models.compartment("SI", "S"),
				make_models.compartment("SI", "I"));
		
		Epidemic model2 = make_models.productEpi("product",
				make_models.group("SI",
						make_models.compartment("S"),
						make_models.compartment("I")));
		
		ComparisonResult res = use_epi.Compare.compare(model1, model2);
		
		// expect SI.S match S && SI.I match I
		// expect no match for top level as no common unique labels even though the diff output says they "match"
		assertEquals(2, res.matches.matches.size());
		assertEquals(Arrays.asList("SI", "S"), res.matches.matches.get(0).match.first.getLabels());
		assertEquals(Arrays.asList("S"), res.matches.matches.get(0).match.second.getLabels());
		assertEquals(Arrays.asList("SI", "I"), res.matches.matches.get(1).match.first.getLabels());
		assertEquals(Arrays.asList("I"), res.matches.matches.get(1).match.second.getLabels());

		// assert same unique labels and only S and I not SI which is duplicate or the label of the top level object which is ignored
		assertEquals(new HashSet<String>(Arrays.asList("S", "I")), res.context.modelctx1.uniqueLabels);
		// in the second model the label SI appears only once, so we expect it too
		assertEquals(new HashSet<String>(Arrays.asList("S", "I", "SI")), res.context.modelctx2.uniqueLabels);
		
		assertEquals(new HashSet<String>(Arrays.asList("SI")), res.context.modelctx1.duplicateLabels);
		assertEquals(new HashSet<String>(), res.context.modelctx2.duplicateLabels);
	}
	
	@Test
	void test3() {
		// opposite of test 4
		// compare a product/dimension epidemic with another one that has one more dimension, being Age
		
		Epidemic withoutAge = make_models.productEpi("withoutAge",
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
						make_models.compartment("R")));
		
		Epidemic withAge = make_models.productEpi("withAge",
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
						make_models.compartment("51_")));
		
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
		
		Epidemic withoutAge = make_models.productEpi("withoutAge",
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
						make_models.compartment("R")));
		
		Epidemic withAge = make_models.productEpi("withAge",
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
						make_models.compartment("51_")));
		
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
		
		Epidemic SymptomsOutside = make_models.productEpi("SymptomsOutside",
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
						make_models.compartment("Symptomatic")));
		
		Epidemic SymptomsInside = make_models.productEpi("SymptomsInside",
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
						make_models.compartment("R")));
		
		ComparisonResult res = use_epi.Compare.compare(SymptomsOutside, SymptomsInside);
		
		// expect 2 diffs, one for the epidemic and one for group Symptoms
		assertEquals(2, res.diffs.size());
		assertEquals(Arrays.asList("SymptomsOutside"), res.diffs.get(0).accountsForMatches.get(0).match.first.getLabels());
		assertEquals(Arrays.asList("SymptomsInside"), res.diffs.get(0).accountsForMatches.get(0).match.second.getLabels());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(1).accountsForMatches.get(0).match.first.getLabels());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(1).accountsForMatches.get(0).match.second.getLabels());
		
		// expect diff of epidemic to account for remove and diff of symptoms to account for match
		assertEquals(3, res.diffs.get(0).accountsForSubstractions.size());
		assertEquals(3, res.diffs.get(1).accountsForMatches.size());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(0).accountsForSubstractions.get(0).getLabels());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(1).accountsForMatches.get(0).match.first.getLabels());
		
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
		
		Epidemic SymptomsOutside = make_models.productEpi("SymptomsOutside",
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
						make_models.compartment("Symptomatic")));
		
		Epidemic SymptomsInside = make_models.productEpi("SymptomsInside",
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
						make_models.compartment("R")));
		
		ComparisonResult res = use_epi.Compare.compare(SymptomsInside, SymptomsOutside);

		// expect 2 diffs, one for the epidemic and one for group Symptoms
		assertEquals(2, res.diffs.size());
		assertEquals(Arrays.asList("SymptomsInside"), res.diffs.get(0).accountsForMatches.get(0).match.first.getLabels());
		assertEquals(Arrays.asList("SymptomsOutside"), res.diffs.get(0).accountsForMatches.get(0).match.second.getLabels());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(1).accountsForMatches.get(0).match.first.getLabels());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(1).accountsForMatches.get(0).match.second.getLabels());
		
		// expect diff of epidemic to account for remove and diff of symptoms to account for match
		assertEquals(3, res.diffs.get(0).accountsForAdditions.size());
		assertEquals(3, res.diffs.get(1).accountsForMatches.size());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(0).accountsForAdditions.get(0).getLabels());
		assertEquals(Arrays.asList("Symptoms"), res.diffs.get(1).accountsForMatches.get(0).match.first.getLabels());
		
		// just in case check the other lists expected empty
		assertEquals(0, res.diffs.get(0).accountsForSubstractions.size());
		assertEquals(0, res.diffs.get(1).accountsForSubstractions.size());
		assertEquals(0, res.diffs.get(1).accountsForAdditions.size());
	}
	
	@Test
	void test7() {
		// test two models with a naming conflict
		
		Epidemic model1 = make_models.groupEpi("conflict",
				make_models.group("whatever",
						make_models.compartment("S"),
						make_models.compartment("I")));
		
		Epidemic model2 = make_models.groupEpi("ok",
				make_models.group("conflict",
						make_models.compartment("S"),
						make_models.compartment("I")));
		
		ComparisonResult res = use_epi.Compare.compare(model1, model2);
		
		// expect 3 diffs: one for top level, 1 for S, 1 for I
		// the top level diff will have 1 add "conflict" and 1 remove "whatever"
		assertEquals(3, res.diffs.size());
		Difference epidiff = res.diffs.get(0);
		assertEquals(1, epidiff.accountsForMatches.size());
		assertEquals(Arrays.asList("conflict"), epidiff.accountsForMatches.get(0).match.first.getLabels());
		assertEquals(Arrays.asList("ok"), epidiff.accountsForMatches.get(0).match.second.getLabels());
		// expect to find that the top level diff thinks there are 3 deleted and 3 added elements, being whatever:conflict, S:S, I:I
		assertEquals(3, epidiff.accountsForAdditions.size());
		assertEquals(3, epidiff.accountsForSubstractions.size());
		assertEquals(Arrays.asList("whatever"), epidiff.accountsForSubstractions.get(0).getLabels());
		assertEquals(Arrays.asList("S"), epidiff.accountsForSubstractions.get(1).getLabels());
		assertEquals(Arrays.asList("I"), epidiff.accountsForSubstractions.get(2).getLabels());
		assertEquals(Arrays.asList("conflict"), epidiff.accountsForAdditions.get(0).getLabels());
		assertEquals(Arrays.asList("S"), epidiff.accountsForAdditions.get(1).getLabels());
		assertEquals(Arrays.asList("I"), epidiff.accountsForAdditions.get(2).getLabels());
		// but also expect to see that S and I are actually moves but not whatever and conflict
		assertFalse(res.isMove(epidiff.accountsForSubstractions.get(0)));
		assertTrue(res.isMove(epidiff.accountsForSubstractions.get(1)));
		assertTrue(res.isMove(epidiff.accountsForSubstractions.get(2)));
		assertFalse(res.isMove(epidiff.accountsForAdditions.get(0)));
		assertTrue(res.isMove(epidiff.accountsForAdditions.get(1)));
		assertTrue(res.isMove(epidiff.accountsForAdditions.get(2)));
	}
}
