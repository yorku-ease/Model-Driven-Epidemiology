/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.InfectiousPreSymptomatic;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Infectious Pre Symptomatic</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfectiousPreSymptomaticTest extends InfectiousTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InfectiousPreSymptomaticTest.class);
	}

	/**
	 * Constructs a new Infectious Pre Symptomatic test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfectiousPreSymptomaticTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Infectious Pre Symptomatic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected InfectiousPreSymptomatic getFixture() {
		return (InfectiousPreSymptomatic)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createInfectiousPreSymptomatic());
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

} //InfectiousPreSymptomaticTest
