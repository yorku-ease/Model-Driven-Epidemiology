/**
 */
package PhysicalEpidemic.tests;

import PhysicalEpidemic.PhysicalEpidemicFactory;
import PhysicalEpidemic.PhysicalFlow;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Physical Flow</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PhysicalFlowTest extends TestCase {

	/**
	 * The fixture for this Physical Flow test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PhysicalFlow fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PhysicalFlowTest.class);
	}

	/**
	 * Constructs a new Physical Flow test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhysicalFlowTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Physical Flow test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(PhysicalFlow fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Physical Flow test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PhysicalFlow getFixture() {
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
		setFixture(PhysicalEpidemicFactory.eINSTANCE.createPhysicalFlow());
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

} //PhysicalFlowTest
