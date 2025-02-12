/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.ExposedNonIsolated;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Exposed Non Isolated</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExposedNonIsolatedTest extends ExposedTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ExposedNonIsolatedTest.class);
	}

	/**
	 * Constructs a new Exposed Non Isolated test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExposedNonIsolatedTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Exposed Non Isolated test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ExposedNonIsolated getFixture() {
		return (ExposedNonIsolated)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createExposedNonIsolated());
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

} //ExposedNonIsolatedTest
