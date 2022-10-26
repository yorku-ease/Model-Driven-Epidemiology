/**
 */
package test_metamodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see test_metamodel.Test_metamodelPackage
 * @generated
 */
public interface Test_metamodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Test_metamodelFactory eINSTANCE = test_metamodel.impl.Test_metamodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Vet</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Vet</em>'.
	 * @generated
	 */
	Vet createVet();

	/**
	 * Returns a new object of class '<em>Cat</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Cat</em>'.
	 * @generated
	 */
	Cat createCat();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	Test_metamodelPackage getTest_metamodelPackage();

} //Test_metamodelFactory
