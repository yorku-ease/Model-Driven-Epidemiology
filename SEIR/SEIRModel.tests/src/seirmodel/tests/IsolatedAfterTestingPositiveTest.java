/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.IsolatedAfterTestingPositive;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Isolated After Testing Positive</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class IsolatedAfterTestingPositiveTest extends CompartmentTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(IsolatedAfterTestingPositiveTest.class);
	}

	/**
	 * Constructs a new Isolated After Testing Positive test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsolatedAfterTestingPositiveTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Isolated After Testing Positive test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IsolatedAfterTestingPositive getFixture() {
		return (IsolatedAfterTestingPositive)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createIsolatedAfterTestingPositive());
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

} //IsolatedAfterTestingPositiveTest
