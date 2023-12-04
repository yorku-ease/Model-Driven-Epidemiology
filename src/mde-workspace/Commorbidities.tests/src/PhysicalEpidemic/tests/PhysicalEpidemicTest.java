/**
 */
package PhysicalEpidemic.tests;

import PhysicalEpidemic.PhysicalEpidemic;
import PhysicalEpidemic.PhysicalEpidemicFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Physical Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PhysicalEpidemicTest extends TestCase {

	/**
	 * The fixture for this Physical Epidemic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PhysicalEpidemic fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PhysicalEpidemicTest.class);
	}

	/**
	 * Constructs a new Physical Epidemic test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhysicalEpidemicTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Physical Epidemic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(PhysicalEpidemic fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Physical Epidemic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PhysicalEpidemic getFixture() {
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
		setFixture(PhysicalEpidemicFactory.eINSTANCE.createPhysicalEpidemic());
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

} //PhysicalEpidemicTest
