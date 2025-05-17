/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.NoAccessToHealthCare;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>No Access To Health Care</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class NoAccessToHealthCareTest extends CompartmentTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(NoAccessToHealthCareTest.class);
	}

	/**
	 * Constructs a new No Access To Health Care test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NoAccessToHealthCareTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this No Access To Health Care test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected NoAccessToHealthCare getFixture() {
		return (NoAccessToHealthCare)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createNoAccessToHealthCare());
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

} //NoAccessToHealthCareTest
