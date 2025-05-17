/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.InfectiousPreSymptomaticIsolated;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Infectious Pre Symptomatic Isolated</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfectiousPreSymptomaticIsolatedTest extends InfectiousTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InfectiousPreSymptomaticIsolatedTest.class);
	}

	/**
	 * Constructs a new Infectious Pre Symptomatic Isolated test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfectiousPreSymptomaticIsolatedTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Infectious Pre Symptomatic Isolated test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected InfectiousPreSymptomaticIsolated getFixture() {
		return (InfectiousPreSymptomaticIsolated)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createInfectiousPreSymptomaticIsolated());
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

} //InfectiousPreSymptomaticIsolatedTest
