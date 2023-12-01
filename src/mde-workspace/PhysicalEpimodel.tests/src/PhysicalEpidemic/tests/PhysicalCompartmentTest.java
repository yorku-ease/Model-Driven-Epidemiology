/**
 */
package PhysicalEpidemic.tests;

import PhysicalEpidemic.PhysicalCompartment;
import PhysicalEpidemic.PhysicalEpidemicFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Physical Compartment</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PhysicalCompartmentTest extends TestCase {

	/**
	 * The fixture for this Physical Compartment test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PhysicalCompartment fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PhysicalCompartmentTest.class);
	}

	/**
	 * Constructs a new Physical Compartment test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhysicalCompartmentTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Physical Compartment test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(PhysicalCompartment fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Physical Compartment test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PhysicalCompartment getFixture() {
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
		setFixture(PhysicalEpidemicFactory.eINSTANCE.createPhysicalCompartment());
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

} //PhysicalCompartmentTest
