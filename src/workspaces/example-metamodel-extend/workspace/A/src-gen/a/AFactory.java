/**
 */
package a;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see a.APackage
 * @generated
 */
public interface AFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AFactory eINSTANCE = a.impl.AFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>New EClass1</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>New EClass1</em>'.
	 * @generated
	 */
	NewEClass1 createNewEClass1();

	/**
	 * Returns a new object of class '<em>New EClass2</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>New EClass2</em>'.
	 * @generated
	 */
	NewEClass2 createNewEClass2();

	/**
	 * Returns a new object of class '<em>New EClass3</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>New EClass3</em>'.
	 * @generated
	 */
	NewEClass3 createNewEClass3();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	APackage getAPackage();

} //AFactory
