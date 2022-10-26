/**
 */
package test_metamodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see test_metamodel.Test_metamodelFactory
 * @model kind="package"
 * @generated
 */
public interface Test_metamodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "test_metamodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/test_metamodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "test_metamodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Test_metamodelPackage eINSTANCE = test_metamodel.impl.Test_metamodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link test_metamodel.impl.VetImpl <em>Vet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see test_metamodel.impl.VetImpl
	 * @see test_metamodel.impl.Test_metamodelPackageImpl#getVet()
	 * @generated
	 */
	int VET = 0;

	/**
	 * The feature id for the '<em><b>Cat</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VET__CAT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VET__NAME = 1;

	/**
	 * The number of structural features of the '<em>Vet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VET_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Vet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link test_metamodel.impl.CatImpl <em>Cat</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see test_metamodel.impl.CatImpl
	 * @see test_metamodel.impl.Test_metamodelPackageImpl#getCat()
	 * @generated
	 */
	int CAT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Cat</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Cat</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAT_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link test_metamodel.Vet <em>Vet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vet</em>'.
	 * @see test_metamodel.Vet
	 * @generated
	 */
	EClass getVet();

	/**
	 * Returns the meta object for the containment reference list '{@link test_metamodel.Vet#getCat <em>Cat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cat</em>'.
	 * @see test_metamodel.Vet#getCat()
	 * @see #getVet()
	 * @generated
	 */
	EReference getVet_Cat();

	/**
	 * Returns the meta object for the attribute '{@link test_metamodel.Vet#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see test_metamodel.Vet#getName()
	 * @see #getVet()
	 * @generated
	 */
	EAttribute getVet_Name();

	/**
	 * Returns the meta object for class '{@link test_metamodel.Cat <em>Cat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cat</em>'.
	 * @see test_metamodel.Cat
	 * @generated
	 */
	EClass getCat();

	/**
	 * Returns the meta object for the attribute '{@link test_metamodel.Cat#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see test_metamodel.Cat#getName()
	 * @see #getCat()
	 * @generated
	 */
	EAttribute getCat_Name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Test_metamodelFactory getTest_metamodelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link test_metamodel.impl.VetImpl <em>Vet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see test_metamodel.impl.VetImpl
		 * @see test_metamodel.impl.Test_metamodelPackageImpl#getVet()
		 * @generated
		 */
		EClass VET = eINSTANCE.getVet();

		/**
		 * The meta object literal for the '<em><b>Cat</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VET__CAT = eINSTANCE.getVet_Cat();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VET__NAME = eINSTANCE.getVet_Name();

		/**
		 * The meta object literal for the '{@link test_metamodel.impl.CatImpl <em>Cat</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see test_metamodel.impl.CatImpl
		 * @see test_metamodel.impl.Test_metamodelPackageImpl#getCat()
		 * @generated
		 */
		EClass CAT = eINSTANCE.getCat();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CAT__NAME = eINSTANCE.getCat_Name();

	}

} //Test_metamodelPackage
