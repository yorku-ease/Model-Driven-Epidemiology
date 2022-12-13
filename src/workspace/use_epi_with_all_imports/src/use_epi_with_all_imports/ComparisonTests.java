package use_epi_with_all_imports;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import epimodel.Epidemic;
import epimodel.util.Comparison.ComparisonResult;

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
		
		assertTrue(res.context.modelctx1.composables.size() == number_of_composables_in_both_models);
		assertTrue(res.context.modelctx2.composables.size() == number_of_composables_in_both_models);
		assertTrue(res.matches.matches.size() == number_of_composables_that_have_the_same_names_in_both_models);
		assertTrue(res.diffs.size() == 1);
		assertTrue(res.diffs.get(0).accountsForMatches.size() == number_of_composables_that_have_the_same_names_in_both_models);
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
	
	@Test
	void test3() {
		
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
		assertTrue(res.diffs.size() == 1);
		
		// expect an ADD for Age and its 4 children
		assertTrue(res.diffs.get(0).accountsForAdditions.size() == 5);
		assertTrue(res.diffs.get(0).accountsForAdditions.get(0).getLabels().equals(Arrays.asList("Age")));
		assertTrue(res.diffs.get(0).accountsForAdditions.get(1).getLabels().equals(Arrays.asList("0_10")));
		assertTrue(res.diffs.get(0).accountsForAdditions.get(2).getLabels().equals(Arrays.asList("11_25")));
		assertTrue(res.diffs.get(0).accountsForAdditions.get(3).getLabels().equals(Arrays.asList("26_50")));
		assertTrue(res.diffs.get(0).accountsForAdditions.get(4).getLabels().equals(Arrays.asList("51_")));
	}
	
	@Test
	void test4() {
		
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
		assertTrue(res.diffs.size() == 1);
		
		// expect an ADD for Age and its 4 children
		assertTrue(res.diffs.get(0).accountsForSubstractions.size() == 5);
		assertTrue(res.diffs.get(0).accountsForSubstractions.get(0).getLabels().equals(Arrays.asList("Age")));
		assertTrue(res.diffs.get(0).accountsForSubstractions.get(1).getLabels().equals(Arrays.asList("0_10")));
		assertTrue(res.diffs.get(0).accountsForSubstractions.get(2).getLabels().equals(Arrays.asList("11_25")));
		assertTrue(res.diffs.get(0).accountsForSubstractions.get(3).getLabels().equals(Arrays.asList("26_50")));
		assertTrue(res.diffs.get(0).accountsForSubstractions.get(4).getLabels().equals(Arrays.asList("51_")));
	}
	
	@Test
	void test5() {
		
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
		
		// expect 2 diffs, one for SEIR and one for Symptoms
		assertTrue(res.diffs.size() == 2);
		assertTrue(res.diffs.get(0).accountsForMatches.get(0).match.first.getLabels().equals(Arrays.asList("SEIR")));
		assertTrue(res.diffs.get(0).accountsForMatches.get(0).match.second.getLabels().equals(Arrays.asList("SEIR")));
		assertTrue(res.diffs.get(1).accountsForMatches.get(0).match.first.getLabels().equals(Arrays.asList("Symptoms")));
		assertTrue(res.diffs.get(1).accountsForMatches.get(0).match.second.getLabels().equals(Arrays.asList("Symptoms")));
		
		// expect diff of epidemic to account for remove and diff of symptoms to account for match
		assertTrue(res.diffs.get(0).accountsForSubstractions.size() == 3);
		assertTrue(res.diffs.get(0).accountsForSubstractions.get(0).getLabels().equals(Arrays.asList("Symptoms")));
		assertTrue(res.diffs.get(1).accountsForMatches.size() == 3);
		assertTrue(res.diffs.get(1).accountsForMatches.get(0).match.first.getLabels().equals(Arrays.asList("Symptoms")));
	}
}
