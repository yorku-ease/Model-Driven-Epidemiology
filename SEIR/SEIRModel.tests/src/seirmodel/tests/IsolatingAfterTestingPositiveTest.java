/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.IsolatingAfterTestingPositive;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Isolating After Testing Positive</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class IsolatingAfterTestingPositiveTest extends CompartmentTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(IsolatingAfterTestingPositiveTest.class);
	}

	/**
	 * Constructs a new Isolating After Testing Positive test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsolatingAfterTestingPositiveTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Isolating After Testing Positive test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IsolatingAfterTestingPositive getFixture() {
		return (IsolatingAfterTestingPositive)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createIsolatingAfterTestingPositive());
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

} //IsolatingAfterTestingPositiveTest
