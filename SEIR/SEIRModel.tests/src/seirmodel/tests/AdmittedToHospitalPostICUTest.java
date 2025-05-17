/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.AdmittedToHospitalPostICU;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Admitted To Hospital Post ICU</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class AdmittedToHospitalPostICUTest extends HospitalizedTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(AdmittedToHospitalPostICUTest.class);
	}

	/**
	 * Constructs a new Admitted To Hospital Post ICU test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdmittedToHospitalPostICUTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Admitted To Hospital Post ICU test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected AdmittedToHospitalPostICU getFixture() {
		return (AdmittedToHospitalPostICU)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createAdmittedToHospitalPostICU());
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

} //AdmittedToHospitalPostICUTest
