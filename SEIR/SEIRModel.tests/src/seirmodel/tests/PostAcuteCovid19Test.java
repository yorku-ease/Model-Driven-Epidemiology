/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.PostAcuteCovid19;
import seirmodel.SeirmodelFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Post Acute Covid19</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PostAcuteCovid19Test extends CompartmentTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PostAcuteCovid19Test.class);
	}

	/**
	 * Constructs a new Post Acute Covid19 test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PostAcuteCovid19Test(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Post Acute Covid19 test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected PostAcuteCovid19 getFixture() {
		return (PostAcuteCovid19)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createPostAcuteCovid19());
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

} //PostAcuteCovid19Test
