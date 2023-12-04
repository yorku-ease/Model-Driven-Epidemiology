/**
 */
package PhysicalEpidemicRoot;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage
 * @generated
 */
public interface PhysicalEpidemicRootFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PhysicalEpidemicRootFactory eINSTANCE = PhysicalEpidemicRoot.impl.PhysicalEpidemicRootFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Physical Epidemic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Physical Epidemic</em>'.
	 * @generated
	 */
	PhysicalEpidemic createPhysicalEpidemic();

	/**
	 * Returns a new object of class '<em>Label</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Label</em>'.
	 * @generated
	 */
	Label createLabel();

	/**
	 * Returns a new object of class '<em>Equation Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Equation Template</em>'.
	 * @generated
	 */
	EquationTemplate createEquationTemplate();

	/**
	 * Returns a new object of class '<em>Physical Compartment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Physical Compartment</em>'.
	 * @generated
	 */
	PhysicalCompartment createPhysicalCompartment();

	/**
	 * Returns a new object of class '<em>Physical Flow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Physical Flow</em>'.
	 * @generated
	 */
	PhysicalFlow createPhysicalFlow();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PhysicalEpidemicRootPackage getPhysicalEpidemicRootPackage();

} //PhysicalEpidemicRootFactory
