/**
 */
package compartmentalmodel.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import compartmentalmodel.CompartmentalModel;
import compartmentalmodel.CompartmentalmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>SEIR Model</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class CompartmentalModelTest extends TestCase {

	/**
	 * The fixture for this SEIR Model test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompartmentalModel fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(CompartmentalModelTest.class);
	}

	/**
	 * Constructs a new SEIR Model test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompartmentalModelTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this SEIR Model test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(CompartmentalModel fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this SEIR Model test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompartmentalModel getFixture() {
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
		setFixture(CompartmentalmodelFactory.eINSTANCE.createCompartmentalModel());
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

} //CompartmentalModelTest
