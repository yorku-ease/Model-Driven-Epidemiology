/**
 */
package seirmodel.tests;

import junit.textui.TestRunner;

import seirmodel.SeirmodelFactory;
import seirmodel.UntreatedInfectious;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Untreated Infectious</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class UntreatedInfectiousTest extends CompartmentTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(UntreatedInfectiousTest.class);
	}

	/**
	 * Constructs a new Untreated Infectious test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UntreatedInfectiousTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Untreated Infectious test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected UntreatedInfectious getFixture() {
		return (UntreatedInfectious)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SeirmodelFactory.eINSTANCE.createUntreatedInfectious());
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

} //UntreatedInfectiousTest
