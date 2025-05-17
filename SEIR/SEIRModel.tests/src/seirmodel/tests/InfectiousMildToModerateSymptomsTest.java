/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.InfectiousMildToModerateSymptoms;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Infectious Mild To Moderate Symptoms</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfectiousMildToModerateSymptomsTest extends InfectiousTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InfectiousMildToModerateSymptomsTest.class);
	}

	/**
	 * Constructs a new Infectious Mild To Moderate Symptoms test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfectiousMildToModerateSymptomsTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Infectious Mild To Moderate Symptoms test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected InfectiousMildToModerateSymptoms getFixture() {
		return (InfectiousMildToModerateSymptoms)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createInfectiousMildToModerateSymptoms());
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

} //InfectiousMildToModerateSymptomsTest
