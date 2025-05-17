/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.AdmittedToHospital;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Admitted To Hospital</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class AdmittedToHospitalTest extends HospitalizedTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(AdmittedToHospitalTest.class);
	}

	/**
	 * Constructs a new Admitted To Hospital test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdmittedToHospitalTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Admitted To Hospital test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected AdmittedToHospital getFixture() {
		return (AdmittedToHospital)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createAdmittedToHospital());
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

} //AdmittedToHospitalTest
