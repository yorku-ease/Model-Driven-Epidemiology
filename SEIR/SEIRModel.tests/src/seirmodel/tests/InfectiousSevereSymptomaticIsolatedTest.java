/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.InfectiousSevereSymptomaticIsolated;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Infectious Severe Symptomatic Isolated</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfectiousSevereSymptomaticIsolatedTest extends InfectiousTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InfectiousSevereSymptomaticIsolatedTest.class);
	}

	/**
	 * Constructs a new Infectious Severe Symptomatic Isolated test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfectiousSevereSymptomaticIsolatedTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Infectious Severe Symptomatic Isolated test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected InfectiousSevereSymptomaticIsolated getFixture() {
		return (InfectiousSevereSymptomaticIsolated)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createInfectiousSevereSymptomaticIsolated());
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

} //InfectiousSevereSymptomaticIsolatedTest
