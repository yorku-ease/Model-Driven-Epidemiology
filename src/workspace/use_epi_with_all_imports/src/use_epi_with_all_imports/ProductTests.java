package use_epi_with_all_imports;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.impl.CompartmentImpl;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalCompartment;
import product.impl.ProductImpl;
import product.util.CartesianProduct;

class ProductTests {
	
	@BeforeAll
	static void init_metamodel() throws Exception {
		// load metamodel things in right order & make sure model creation is fine
		make_models.main(null);
	}
	
	@Test
	void testCartesianProductOrder() {
		
		List<List<String>> computed = CartesianProduct.cartesianProduct(
			Arrays.asList(
					Arrays.asList("x", "y"),
					Arrays.asList("1", "2", "3", "4"),
					Arrays.asList("a", "b", "c")
				)
			);
		
		List<List<String>> expected = Arrays.asList(
				Arrays.asList("x", "1", "a"),
				Arrays.asList("x", "1", "b"),
				Arrays.asList("x", "1", "c"),
				Arrays.asList("x", "2", "a"),
				Arrays.asList("x", "2", "b"),
				Arrays.asList("x", "2", "c"),
				Arrays.asList("x", "3", "a"),
				Arrays.asList("x", "3", "b"),
				Arrays.asList("x", "3", "c"),
				Arrays.asList("x", "4", "a"),
				Arrays.asList("x", "4", "b"),
				Arrays.asList("x", "4", "c"),
				Arrays.asList("y", "1", "a"),
				Arrays.asList("y", "1", "b"),
				Arrays.asList("y", "1", "c"),
				Arrays.asList("y", "2", "a"),
				Arrays.asList("y", "2", "b"),
				Arrays.asList("y", "2", "c"),
				Arrays.asList("y", "3", "a"),
				Arrays.asList("y", "3", "b"),
				Arrays.asList("y", "3", "c"),
				Arrays.asList("y", "4", "a"),
				Arrays.asList("y", "4", "b"),
				Arrays.asList("y", "4", "c")
			);
		
		assertEquals(expected, computed);
	}

	@Test
	void test_expand_pc_simple() {
		ProductImpl epi = (ProductImpl) make_models.product("product");
		
		assertEquals(
			Arrays.asList(
				new PhysicalCompartment(Arrays.asList("a", "1", "x")),
				new PhysicalCompartment(Arrays.asList("a", "1", "y")),
				new PhysicalCompartment(Arrays.asList("a", "2", "x")),
				new PhysicalCompartment(Arrays.asList("a", "2", "y"))
			),
			epi.expand(
				new PhysicalCompartment(Arrays.asList("a")),
				Arrays.asList(
					Arrays.asList(
						new PhysicalCompartment(Arrays.asList("1")),
						new PhysicalCompartment(Arrays.asList("2"))),
					Arrays.asList(
						new PhysicalCompartment(Arrays.asList("x")),
						new PhysicalCompartment(Arrays.asList("y")))))
		);
	}

	@Test
	void test_expand_pc_multiple() {
		ProductImpl epi = (ProductImpl) make_models.product("product");
		
		assertEquals(
			Arrays.asList(
				new PhysicalCompartment(Arrays.asList("a", "b", "1", "3", "x")),
				new PhysicalCompartment(Arrays.asList("a", "b", "1", "3", "y", "z")),
				new PhysicalCompartment(Arrays.asList("a", "b", "2", "x")),
				new PhysicalCompartment(Arrays.asList("a", "b", "2", "y", "z"))
			),
			epi.expand(
				new PhysicalCompartment(Arrays.asList("a", "b")),
				Arrays.asList(
					Arrays.asList(
						new PhysicalCompartment(Arrays.asList("1", "3")),
						new PhysicalCompartment(Arrays.asList("2"))),
					Arrays.asList(
						new PhysicalCompartment(Arrays.asList("x")),
						new PhysicalCompartment(Arrays.asList("y", "z")))))
		);
	}

	@Test
	void test_expand_equation_simple() {
		ProductImpl epi = (ProductImpl) make_models.product("product");
		
		PhysicalFlow rateFromAtoB = new PhysicalFlow(
			Arrays.asList(new PhysicalCompartment(Arrays.asList("A"))),
			new PhysicalCompartment(Arrays.asList("A")),
			new PhysicalCompartment(Arrays.asList("B")),
			"", // equation
			new ArrayList<>() // required ops
		);
		
		List<List<String>> prod = CartesianProduct.cartesianProduct(
			Arrays.asList(Arrays.asList("1", "2"), Arrays.asList("x", "y"))
		);
		
		for (int i = 0; i < 4; ++i)
			assertEquals(
				new PhysicalFlow(
					Arrays.asList(new PhysicalCompartment(Arrays.asList("A", prod.get(i).get(0), prod.get(i).get(1)))),
					new PhysicalCompartment(Arrays.asList("A", prod.get(i).get(0), prod.get(i).get(1))),
					new PhysicalCompartment(Arrays.asList("B", prod.get(i).get(0), prod.get(i).get(1))),
					"", // equation
					new ArrayList<>() // required ops
				),
				epi.expand(
					rateFromAtoB,
					Arrays.asList(
						Arrays.asList(
							new PhysicalCompartment(Arrays.asList("1")),
							new PhysicalCompartment(Arrays.asList("2"))),
						Arrays.asList(
							new PhysicalCompartment(Arrays.asList("x")),
							new PhysicalCompartment(Arrays.asList("y"))))).get(i)
			);
	}

	@Test
	void test_expand_equation_complex() {
		ProductImpl epi = (ProductImpl) make_models.product("product");
		
		PhysicalFlow rateFromAtoB1 = new PhysicalFlow(
			Arrays.asList(new PhysicalCompartment(Arrays.asList("A"))),
			new PhysicalCompartment(Arrays.asList("A")),
			new PhysicalCompartment(Arrays.asList("B", "1")),
			"", // equation
			new ArrayList<>() // required ops
		);
		
		List<List<String>> prod = CartesianProduct.cartesianProduct(
			Arrays.asList(Arrays.asList("j", "k"), Arrays.asList("x", "y", "z"))
		);
		
		List<PhysicalFlow> l = epi.expand(
				rateFromAtoB1,
				Arrays.asList(
					Arrays.asList(
						new PhysicalCompartment(Arrays.asList("j")),
						new PhysicalCompartment(Arrays.asList("k"))),
					Arrays.asList(
						new PhysicalCompartment(Arrays.asList("x")),
						new PhysicalCompartment(Arrays.asList("y")),
						new PhysicalCompartment(Arrays.asList("z")))));
		
		for (int i = 0; i < 6; ++i)
			assertEquals(
				new PhysicalFlow(
					Arrays.asList(new PhysicalCompartment(Arrays.asList("A", prod.get(i).get(0), prod.get(i).get(1)))),
					new PhysicalCompartment(Arrays.asList("A", prod.get(i).get(0), prod.get(i).get(1))),
					new PhysicalCompartment(Arrays.asList("B", "1", prod.get(i).get(0), prod.get(i).get(1))),
					"", // equation
					new ArrayList<>() // required ops
				),
				l.get(i)
			);
	}
	
	@Test
	void test_replicateEquationToMatchPCs_simple() {
		ProductImpl epi = (ProductImpl) make_models.product("product");
		
		PhysicalFlow rateFromAtoB = new PhysicalFlow(
			Arrays.asList(new PhysicalCompartment(Arrays.asList("A"))),
			new PhysicalCompartment(Arrays.asList("A")),
			new PhysicalCompartment(Arrays.asList("B")),
			"", // equation
			new ArrayList<>() // required ops
		);
		
		List<PhysicalFlow> l = epi.replicateEquationToMatchPCs(rateFromAtoB, new CompartmentImpl() {
			@Override
			public List<PhysicalCompartment> getSourcesFor(PhysicalCompartment c) {
				assertEquals("B", c.labels.get(0));
				return Arrays.asList(
					new PhysicalCompartment(Arrays.asList("source1")),
					new PhysicalCompartment(Arrays.asList("source2"))
				);
			}
			@Override
			public List<PhysicalCompartment> getSinksFor(PhysicalCompartment c) {
				assertEquals("A", c.labels.get(0));
				return Arrays.asList(
					new PhysicalCompartment(Arrays.asList("sink1")),
					new PhysicalCompartment(Arrays.asList("sink2")),
					new PhysicalCompartment(Arrays.asList("sink3"))
				);
			}
		});
		
		assertEquals(6, l.size());
		
		List<List<String>> prod = CartesianProduct.cartesianProduct(
			Arrays.asList(
				Arrays.asList("sink1", "sink2", "sink3"),
				Arrays.asList("source1", "source2")
			)
		);
		
		for (int i = 0; i < 6; ++i)
			assertEquals(
				new PhysicalFlow(
						Arrays.asList(new PhysicalCompartment(Arrays.asList("A", prod.get(i).get(0)))),
						new PhysicalCompartment(Arrays.asList("A", prod.get(i).get(0))),
						new PhysicalCompartment(Arrays.asList("B", prod.get(i).get(1))),
						"", // equation
						new ArrayList<>() // required ops
					),
				l.get(i)
			);
		
//		replicateEquationToMatchPCs
	}
	
	@Test
	void test_getPartOfWhichDimIndex() {
		ProductImpl epi = (ProductImpl)
			make_models.product("I",
				make_models.group("Variants",
						make_models.compartment("DELTA"),
						make_models.compartment("OMICRON")),
				make_models.group("Infectious",
						make_models.compartment("Asymptomatic"),
						make_models.compartment("Symptomatic")));
		
		PhysicalFlow symptoms = new PhysicalFlow(
			Arrays.asList(new PhysicalCompartment(Arrays.asList("Asymptomatic"))),
			new PhysicalCompartment(Arrays.asList("Asymptomatic")),
			new PhysicalCompartment(Arrays.asList("Symptomatic")),
			"", // equation
			new ArrayList<>() // required ops
		);
		List<List<PhysicalCompartment>> pcl = epi.getCompartment().stream().map(CompartmentWrapper::getCompartment).map(Compartment::getPhysicalCompartments).toList();
		assertEquals(1, epi.getPartOfWhichDimIndex(symptoms, pcl));
	}
}
