package use_epi_with_all_imports;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import epimodel.Compartment;
import epimodel.util.FlowEquation;
import epimodel.util.PhysicalCompartment;

class ArtifactGenerationTests {
	
	@BeforeAll
	static void init_metamodel() throws Exception {
		// load metamodel things in right order & make sure model creation is fine
		make_models.main(null);
	}
	
//	@Test
//	void test_contact_in_group() {
//		Compartment epi = make_models.addContact(make_models.group("SEIR",
//						make_models.compartment("S"),
//						make_models.compartment("E"),
//						make_models.compartment("I"),
//						make_models.compartment("R")),
//					"exposure",
//					Arrays.asList("S"),
//					Arrays.asList("E"),
//					Arrays.asList("I"));
//
//		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
//		List<FlowEquation> pfs = epi.getPhysicalFlows();
//		
//		assertEquals(4, pcs.size());
//		assertEquals(1, pfs.size());
//	}
//	
//	@Test
//	void test_contact_in_group_in_group() {
//		Compartment epi = make_models.group("above",
//				make_models.addContact(
//						make_models.group("SEIR",
//							make_models.compartment("S"),
//							make_models.compartment("E"),
//							make_models.compartment("I"),
//							make_models.compartment("R")),
//						"exposure",
//						Arrays.asList("S"),
//						Arrays.asList("E"),
//						Arrays.asList("I")));
//
//		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
//		List<FlowEquation> pfs = epi.getPhysicalFlows();
//		
//		assertEquals(4, pcs.size());
//		assertEquals(1, pfs.size());
//	}
//	
//	@Test
//	void test_contact_in_group_in_product() {
//		Compartment epi = make_models.product("above",
//				make_models.addContact(
//						make_models.group("SEIR",
//							make_models.compartment("S"),
//							make_models.compartment("E"),
//							make_models.compartment("I"),
//							make_models.compartment("R")),
//						"exposure",
//						Arrays.asList("S"),
//						Arrays.asList("E"),
//						Arrays.asList("I")));
//
//		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
//		List<FlowEquation> eqs = epi.getPhysicalFlows();
//		
//		assertEquals(4, pcs.size());
//		assertEquals(1, eqs.size());
//		
//		assertEquals(
//			Arrays.asList(
//					Arrays.asList("S", "SEIR", "above"),
//					Arrays.asList("I", "SEIR", "above")
//			),
//			eqs.get(0).equationCompartments.stream().map(pc->pc.labels).collect(Collectors.toList())
//		);
//		
//		assertEquals(
//			Arrays.asList(
//					new PhysicalCompartment(Arrays.asList("above", "SEIR", "S")),
//					new PhysicalCompartment(Arrays.asList("above", "SEIR", "I"))
//			),
//			eqs.get(0).equationCompartments
//		);
//		
//		assertEquals(
//			Arrays.asList(
//					Arrays.asList("S", "SEIR", "above"),
//					Arrays.asList("E", "SEIR", "above")
//			),
//			eqs.get(0).affectedCompartments.stream().map(pc->pc.labels).collect(Collectors.toList())
//		);
//		
//		assertEquals(
//			Arrays.asList(
//					new PhysicalCompartment(Arrays.asList("above", "SEIR", "S")),
//					new PhysicalCompartment(Arrays.asList("above", "SEIR", "E"))
//			),
//			eqs.get(0).affectedCompartments
//		);
//	}
//	
//	@Test
//	void test_rate_in_group_in_product_with_2_dimensions() {
//		Compartment epi = make_models.product("above",
//				make_models.addRate(
//						make_models.group("G1",
//							make_models.compartment("A"),
//							make_models.compartment("B")),
//						"rate",
//						Arrays.asList("A"),
//						Arrays.asList("B")),
//				make_models.group("G2",
//						make_models.compartment("1"),
//						make_models.compartment("2")));
//
//		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
//		List<FlowEquation> eqs = epi.getPhysicalFlows();
//		
//		assertEquals(4, pcs.size());
//		assertEquals(2, eqs.size());
//		assertEquals(1, eqs.get(0).equationCompartments.size());
//		assertEquals(1, eqs.get(1).equationCompartments.size());
//		assertEquals(2, eqs.get(0).affectedCompartments.size());
//		assertEquals(2, eqs.get(1).affectedCompartments.size());
//		
//		List<String> flowspecs = Arrays.asList("1", "2");
//		for (int i = 0; i < 2; ++i) {
//			
//			FlowEquation eq = eqs.get(i);
//			String spec = flowspecs.get(i);
//			
//			assertEquals(
//				Arrays.asList(
//						Arrays.asList("G2", spec, "A", "G1", "above")
//				),
//				eq.equationCompartments.stream().map(pc->pc.labels).collect(Collectors.toList())
//			);
//			
//			assertEquals(
//				Arrays.asList(
//						Arrays.asList("G2", spec, "A", "G1", "above"),
//						Arrays.asList("G2", spec, "B", "G1", "above")
//				),
//				eq.affectedCompartments.stream().map(pc->pc.labels).collect(Collectors.toList())
//			);
//		}
//	}
	
	@Test
	void test_rate_in_product_with_2_dimensions() {
		Compartment epi = make_models.addRate(
				make_models.product("above",
						make_models.group("Age",
							make_models.compartment("A"),
							make_models.compartment("B")),
						make_models.group("Sex",
							make_models.compartment("1"),
							make_models.compartment("2"))),
				"rate",
				Arrays.asList("A"),
				Arrays.asList("B"));

		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
		List<FlowEquation> eqs = epi.getPhysicalFlows();
		
		// there should be 2 rates
		// one from G1,A,G2,1 to G1,B,G2,1 and one from G1,A,G2,2 to G1,B,G2,2
		// the rate only takes us along the Age axis
		assertEquals(4, pcs.size());
		assertEquals(2, eqs.size());
	}
}
