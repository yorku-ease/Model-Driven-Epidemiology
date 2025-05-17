/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.RecoveryLongTermHealthComplications;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Recovery Long Term Health Complications</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class RecoveryLongTermHealthComplicationsTest extends CompartmentTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RecoveryLongTermHealthComplicationsTest.class);
	}

	/**
	 * Constructs a new Recovery Long Term Health Complications test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RecoveryLongTermHealthComplicationsTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Recovery Long Term Health Complications test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected RecoveryLongTermHealthComplications getFixture() {
		return (RecoveryLongTermHealthComplications)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createRecoveryLongTermHealthComplications());
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

} //RecoveryLongTermHealthComplicationsTest
