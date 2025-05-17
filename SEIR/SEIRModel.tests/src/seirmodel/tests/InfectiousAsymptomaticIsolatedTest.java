/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.InfectiousAsymptomaticIsolated;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Infectious Asymptomatic Isolated</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfectiousAsymptomaticIsolatedTest extends InfectiousTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InfectiousAsymptomaticIsolatedTest.class);
	}

	/**
	 * Constructs a new Infectious Asymptomatic Isolated test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfectiousAsymptomaticIsolatedTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Infectious Asymptomatic Isolated test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected InfectiousAsymptomaticIsolated getFixture() {
		return (InfectiousAsymptomaticIsolated)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createInfectiousAsymptomaticIsolated());
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

} //InfectiousAsymptomaticIsolatedTest
