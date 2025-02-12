/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.ExposedIsolated;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Exposed Isolated</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExposedIsolatedTest extends ExposedTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ExposedIsolatedTest.class);
	}

	/**
	 * Constructs a new Exposed Isolated test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExposedIsolatedTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Exposed Isolated test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ExposedIsolated getFixture() {
		return (ExposedIsolated)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createExposedIsolated());
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

} //ExposedIsolatedTest
