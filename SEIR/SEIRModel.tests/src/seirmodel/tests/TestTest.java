/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.SeirmodelFactory;
import seirmodel.Test;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class TestTest extends CompartmentTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(TestTest.class);
	}

	/**
	 * Constructs a new Test test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Test test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Test getFixture() {
		return (Test)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createTest());
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

} //TestTest
