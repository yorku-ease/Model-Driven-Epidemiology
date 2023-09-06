/**
 */
package mortality;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see mortality.MortalityPackage
 * @generated
 */
public interface MortalityFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MortalityFactory eINSTANCE = mortality.impl.MortalityFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Mortality</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mortality</em>'.
	 * @generated
	 */
	Mortality createMortality();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MortalityPackage getMortalityPackage();

} //MortalityFactory
