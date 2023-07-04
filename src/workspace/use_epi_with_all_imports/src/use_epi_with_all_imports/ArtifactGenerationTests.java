package use_epi_with_all_imports;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import epimodel.Compartment;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalCompartment;
import group.Group;
import product.util.CartesianProduct;

class ArtifactGenerationTests {
	
	@BeforeAll
	static void init_metamodel() throws Exception {
		// load metamodel things in right order & make sure model creation is fine
		make_models.main(null);
	}
	
	@Test
	void test_contact_in_group() {
		Compartment epi = make_models.group("SEIR",
						make_models.contact("exposure",
								Arrays.asList("S"),
								Arrays.asList("E"),
								Arrays.asList("I")),
						make_models.compartment("S"),
						make_models.compartment("E"),
						make_models.compartment("I"),
						make_models.compartment("R"));

		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
		List<PhysicalFlow> pfs = epi.getEquations();
		
		assertEquals(4, pcs.size());
		assertEquals(1, pfs.size());
	}
	
	@Test
	void test_contact_in_group_in_group() {
		Compartment epi = make_models.group("above",
						make_models.group("SEIR",
							make_models.contact("exposure",
									Arrays.asList("S"),
									Arrays.asList("E"),
									Arrays.asList("I")),
							make_models.compartment("S"),
							make_models.compartment("E"),
							make_models.compartment("I"),
							make_models.compartment("R")));

		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
		List<PhysicalFlow> pfs = epi.getEquations();
		
		assertEquals(4, pcs.size());
		assertEquals(1, pfs.size());
	}
	
	@Test
	void test_contact_in_group_in_product() {
		Compartment epi = make_models.product("above",
						make_models.group("SEIR",
							make_models.contact("exposure",
									Arrays.asList("S"),
									Arrays.asList("E"),
									Arrays.asList("I")),
							make_models.compartment("S"),
							make_models.compartment("E"),
							make_models.compartment("I"),
							make_models.compartment("R"))
						);

		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
		List<PhysicalFlow> eqs = epi.getEquations();
		
		assertEquals(4, pcs.size());
		assertEquals(1, eqs.size());
		
		assertEquals(
			Arrays.asList(
					new PhysicalCompartment(Arrays.asList("above", "SEIR", "S")),
					new PhysicalCompartment(Arrays.asList("above", "SEIR", "I"))
			),
			eqs.get(0).equationCompartments
		);
		
		assertEquals(
			new PhysicalCompartment(Arrays.asList("S", "SEIR", "above")),
			eqs.get(0).source
		);
		
		assertEquals(
			new PhysicalCompartment(Arrays.asList("E", "SEIR", "above")),
			eqs.get(0).sink
		);
	}
	
	@Test
	void test_rate_in_group_in_product_with_2_dimensions() {
		Compartment epi =
				make_models.product("above",
						make_models.group("G1",
							make_models.rate("rate", Arrays.asList("A"), Arrays.asList("B")),
							make_models.compartment("A"),
							make_models.compartment("B")),
						make_models.group("G2",
								make_models.compartment("1"),
								make_models.compartment("2")));

		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
		List<PhysicalFlow> eqs = epi.getEquations();
		
		assertEquals(4, pcs.size());
		assertEquals(2, eqs.size());
		assertEquals(1, eqs.get(0).equationCompartments.size());
		assertEquals(1, eqs.get(1).equationCompartments.size());
		
		List<String> flowspecs = Arrays.asList("1", "2");
		for (int i = 0; i < 2; ++i) {
			
			PhysicalFlow eq = eqs.get(i);
			String spec = flowspecs.get(i);
			
			assertEquals(
				new PhysicalCompartment(Arrays.asList("G2", spec, "G1", "A", "above")),
				eq.source
			);
			
			assertEquals(
				new PhysicalCompartment(Arrays.asList("G2", spec, "G1", "B", "above")),
				eq.sink
			);
		}
	}
	
	@Test
	void test_rate_in_group_in_product_with_3_dimensions() {
		Compartment epi =
				make_models.product("above",
						make_models.group("G1",
							make_models.rate("rate", Arrays.asList("A"), Arrays.asList("B")),
							make_models.compartment("A"),
							make_models.compartment("B")),
						make_models.group("G2",
								make_models.compartment("1"),
								make_models.compartment("2")),
						make_models.group("G3",
								make_models.compartment("X"),
								make_models.compartment("Y")));

		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
		List<PhysicalFlow> eqs = epi.getEquations();
		
		assertEquals(8, pcs.size());
		assertEquals(4, eqs.size());
		
		List<List<String>> flowspecs = CartesianProduct.cartesianProduct(Arrays.asList(Arrays.asList("1", "2"), Arrays.asList("X", "Y")));
		for (int i = 0; i < 2; ++i) {
			
			PhysicalFlow eq = eqs.get(i);
			List<String> spec = flowspecs.get(i);
			
			assertEquals(
				Arrays.asList(
						new PhysicalCompartment(Arrays.asList("G1", "G2", "G3", spec.get(0), spec.get(1), "A", "above"))
				),
				eq.equationCompartments
			);
			
			assertEquals(
				new PhysicalCompartment(Arrays.asList("G1", "G2", "G3", spec.get(0), spec.get(1), "A", "above")),
				eq.source
			);
			
			assertEquals(
				new PhysicalCompartment(Arrays.asList("G1", "G2", "G3", spec.get(0), spec.get(1), "B", "above")),
				eq.sink
			);
		}
	}
	
	@Test
	void test_rate_in_product_with_2_dimensions() {
		Compartment epi = 
				make_models.product("product",
						make_models.rate("aging", Arrays.asList("Child"), Arrays.asList("Adult")),
						make_models.group("Age",
							make_models.compartment("Child"),
							make_models.compartment("Adult")),
						make_models.group("Sex",
							make_models.compartment("Male"),
							make_models.compartment("Female")));

		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
		List<PhysicalFlow> eqs = epi.getEquations();
		
		// there should be 2 rates
		// the rate only takes us along the Age axis
		assertEquals(4, pcs.size());
		assertEquals(2, eqs.size());
		assertEquals(
				new PhysicalCompartment(Arrays.asList("Sex", "Male", "Age", "Child", "product")),
				eqs.get(0).source
			);
		assertEquals(
				new PhysicalCompartment(Arrays.asList("Sex", "Male", "Age", "Adult", "product")),
				eqs.get(0).sink
			);
		assertEquals(
				new PhysicalCompartment(Arrays.asList("Sex", "Female", "Age", "Child", "product")),
				eqs.get(1).source
			);
		assertEquals(
				new PhysicalCompartment(Arrays.asList("Sex", "Female", "Age", "Adult", "product")),
				eqs.get(1).sink
			);
	}
	
	@Test
	void test_rate_in_product_with_3_dimensions() {
		Compartment epi = make_models.product("product",
				make_models.rate("aging", Arrays.asList("Child"), Arrays.asList("Adult")),
				make_models.group("Age",
					make_models.compartment("Child"),
					make_models.compartment("Adult")),
				make_models.group("Sex",
						make_models.compartment("Male"),
						make_models.compartment("Female")),
				make_models.group("Geo",
						make_models.compartment("Mtl"),
						make_models.compartment("Boucherville")));

		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
		List<PhysicalFlow> eqs = epi.getEquations();
		
		// there should be 4 rates
		// the rate only takes us along the Age axis
		assertEquals(8, pcs.size());
		assertEquals(4, eqs.size());
		assertEquals(
				new PhysicalCompartment(Arrays.asList("Sex", "Male", "Age", "Child", "Geo", "Mtl", "product")),
				eqs.get(0).source
			);
		assertEquals(
				new PhysicalCompartment(Arrays.asList("Sex", "Male", "Age", "Adult", "Geo", "Mtl", "product")),
				eqs.get(0).sink
			);
		assertEquals(
				new PhysicalCompartment(Arrays.asList("Sex", "Male", "Age", "Child", "Geo", "Boucherville", "product")),
				eqs.get(1).source
			);
		assertEquals(
				new PhysicalCompartment(Arrays.asList("Sex", "Male", "Age", "Adult", "Geo", "Boucherville", "product")),
				eqs.get(1).sink
			);
		assertEquals(
				new PhysicalCompartment(Arrays.asList("Sex", "Female", "Age", "Child", "Geo", "Mtl", "product")),
				eqs.get(2).source
			);
		assertEquals(
				new PhysicalCompartment(Arrays.asList("Sex", "Female", "Age", "Adult", "Geo", "Mtl", "product")),
				eqs.get(2).sink
			);
		assertEquals(
				new PhysicalCompartment(Arrays.asList("Sex", "Female", "Age", "Child", "Geo", "Boucherville", "product")),
				eqs.get(3).source
			);
		assertEquals(
				new PhysicalCompartment(Arrays.asList("Sex", "Female", "Age", "Adult", "Geo", "Boucherville", "product")),
				eqs.get(3).sink
			);
	}
	
	@Test
	void test_rate_in_group_to_group_source() {
		Group epi = make_models.group("group1",
				make_models.compartment("1"),
				make_models.group("group2",
						make_models.compartment("source"),
						make_models.compartment("sink"),
						make_models.source(Arrays.asList("source"))),
				make_models.rate("rate", Arrays.asList("1"), Arrays.asList("group2"))
			);
		
		List<PhysicalCompartment> pcs = epi.getPhysicalCompartments();
		List<PhysicalFlow> eqs = epi.getEquations();
		
		assertEquals(3, pcs.size());
		assertEquals(1, eqs.size());
		
		assertEquals(
			new PhysicalCompartment(Arrays.asList("group1", "1")),
			eqs.get(0).source
		);
		
		assertEquals(
			new PhysicalCompartment(Arrays.asList("group1", "group2", "source")),
			eqs.get(0).sink
		);
	}
}
