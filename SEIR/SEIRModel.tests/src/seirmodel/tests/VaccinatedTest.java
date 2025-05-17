/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.SeirmodelFactory;
import seirmodel.Vaccinated;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Vaccinated</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class VaccinatedTest extends CompartmentTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(VaccinatedTest.class);
	}

	/**
	 * Constructs a new Vaccinated test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VaccinatedTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Vaccinated test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Vaccinated getFixture() {
		return (Vaccinated)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createVaccinated());
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

} //VaccinatedTest
