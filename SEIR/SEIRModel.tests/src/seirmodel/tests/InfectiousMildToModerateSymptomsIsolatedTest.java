/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.InfectiousMildToModerateSymptomsIsolated;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Infectious Mild To Moderate Symptoms Isolated</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfectiousMildToModerateSymptomsIsolatedTest extends InfectiousTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InfectiousMildToModerateSymptomsIsolatedTest.class);
	}

	/**
	 * Constructs a new Infectious Mild To Moderate Symptoms Isolated test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfectiousMildToModerateSymptomsIsolatedTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Infectious Mild To Moderate Symptoms Isolated test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected InfectiousMildToModerateSymptomsIsolated getFixture() {
		return (InfectiousMildToModerateSymptomsIsolated)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createInfectiousMildToModerateSymptomsIsolated());
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

} //InfectiousMildToModerateSymptomsIsolatedTest
