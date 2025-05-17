/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.AdmittedToHospitalPreICU;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Admitted To Hospital Pre ICU</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class AdmittedToHospitalPreICUTest extends HospitalizedTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(AdmittedToHospitalPreICUTest.class);
	}

	/**
	 * Constructs a new Admitted To Hospital Pre ICU test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdmittedToHospitalPreICUTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Admitted To Hospital Pre ICU test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected AdmittedToHospitalPreICU getFixture() {
		return (AdmittedToHospitalPreICU)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createAdmittedToHospitalPreICU());
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

} //AdmittedToHospitalPreICUTest
