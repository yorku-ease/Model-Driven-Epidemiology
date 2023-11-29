/**
 */
package Epidemic.tests;

import Epidemic.Epidemic;
import Epidemic.EpidemicFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class EpidemicTest extends TestCase {

	/**
	 * The fixture for this Epidemic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Epidemic fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(EpidemicTest.class);
	}

	/**
	 * Constructs a new Epidemic test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpidemicTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Epidemic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Epidemic fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Epidemic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Epidemic getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(EpidemicFactory.eINSTANCE.createEpidemic());
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

} //EpidemicTest
