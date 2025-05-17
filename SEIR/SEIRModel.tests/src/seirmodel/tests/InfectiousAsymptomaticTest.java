/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.InfectiousAsymptomatic;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Infectious Asymptomatic</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfectiousAsymptomaticTest extends InfectiousTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InfectiousAsymptomaticTest.class);
	}

	/**
	 * Constructs a new Infectious Asymptomatic test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfectiousAsymptomaticTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Infectious Asymptomatic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected InfectiousAsymptomatic getFixture() {
		return (InfectiousAsymptomatic)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createInfectiousAsymptomatic());
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

} //InfectiousAsymptomaticTest
