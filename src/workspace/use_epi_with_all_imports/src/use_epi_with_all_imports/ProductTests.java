package use_epi_with_all_imports;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import epimodel.util.FlowEquation;
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
		
		FlowEquation rateFromAtoB = new FlowEquation(
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
				new FlowEquation(
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
}
