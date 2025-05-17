/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.InfectiousSevereSymptomatic;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Infectious Severe Symptomatic</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfectiousSevereSymptomaticTest extends InfectiousTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InfectiousSevereSymptomaticTest.class);
	}

	/**
	 * Constructs a new Infectious Severe Symptomatic test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfectiousSevereSymptomaticTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Infectious Severe Symptomatic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected InfectiousSevereSymptomatic getFixture() {
		return (InfectiousSevereSymptomatic)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createInfectiousSevereSymptomatic());
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

} //InfectiousSevereSymptomaticTest
