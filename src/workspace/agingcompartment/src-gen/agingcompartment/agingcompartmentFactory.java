/**
 */
package agingcompartment;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see agingcompartment.agingcompartmentPackage
 * @generated
 */
public interface agingcompartmentFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	agingcompartmentFactory eINSTANCE = agingcompartment.impl.agingcompartmentFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Aging</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Aging</em>'.
	 * @generated
	 */
	Aging createAging();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	agingcompartmentPackage getagingcompartmentPackage();

} //agingcompartmentFactory
