/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.SeirmodelFactory;
import seirmodel.Treated;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Treated</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class TreatedTest extends CompartmentTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(TreatedTest.class);
	}

	/**
	 * Constructs a new Treated test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TreatedTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Treated test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Treated getFixture() {
		return (Treated)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createTreated());
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

} //TreatedTest
