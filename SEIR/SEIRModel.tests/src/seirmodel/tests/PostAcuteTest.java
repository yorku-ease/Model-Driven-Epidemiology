/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.PostAcute;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Post Acute</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PostAcuteTest extends CompartmentTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PostAcuteTest.class);
	}

	/**
	 * Constructs a new Post Acute test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PostAcuteTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Post Acute test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected PostAcute getFixture() {
		return (PostAcute)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createPostAcute());
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

} //PostAcuteTest
