/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.AdmittedToHospitalICU;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Admitted To Hospital ICU</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class AdmittedToHospitalICUTest extends HospitalizedTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(AdmittedToHospitalICUTest.class);
	}

	/**
	 * Constructs a new Admitted To Hospital ICU test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdmittedToHospitalICUTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Admitted To Hospital ICU test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected AdmittedToHospitalICU getFixture() {
		return (AdmittedToHospitalICU)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createAdmittedToHospitalICU());
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

} //AdmittedToHospitalICUTest
