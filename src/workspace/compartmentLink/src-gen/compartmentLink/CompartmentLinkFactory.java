/**
 */
package compartmentLink;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see compartmentLink.CompartmentLinkPackage
 * @generated
 */
public interface CompartmentLinkFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CompartmentLinkFactory eINSTANCE = compartmentLink.impl.CompartmentLinkFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Compartment Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compartment Link</em>'.
	 * @generated
	 */
	CompartmentLink createCompartmentLink();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CompartmentLinkPackage getCompartmentLinkPackage();

} //CompartmentLinkFactory
