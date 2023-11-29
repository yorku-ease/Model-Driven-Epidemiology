/**
 */
package EpidemicRoot.tests;

import EpidemicRoot.EpidemicRootFactory;
import EpidemicRoot.UnitCompartment;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Unit Compartment</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class UnitCompartmentTest extends AbstractCompartmentTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(UnitCompartmentTest.class);
	}

	/**
	 * Constructs a new Unit Compartment test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnitCompartmentTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Unit Compartment test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected UnitCompartment getFixture() {
		return (UnitCompartment)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(EpidemicRootFactory.eINSTANCE.createUnitCompartment());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //UnitCompartmentTest
