/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.SeirmodelFactory;
import seirmodel.Symptomatic;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Symptomatic</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SymptomaticTest extends InfectiousTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SymptomaticTest.class);
	}

	/**
	 * Constructs a new Symptomatic test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymptomaticTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Symptomatic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Symptomatic getFixture() {
		return (Symptomatic)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createSymptomatic());
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

} //SymptomaticTest
