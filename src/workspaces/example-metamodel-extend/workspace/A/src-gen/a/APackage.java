/**
 */
package a;

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
 * @see a.AFactory
 * @model kind="package"
 * @generated
 */
public interface APackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "a";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/a";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "a";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	APackage eINSTANCE = a.impl.APackageImpl.init();

	/**
	 * The meta object id for the '{@link a.impl.NewEClass1Impl <em>New EClass1</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see a.impl.NewEClass1Impl
	 * @see a.impl.APackageImpl#getNewEClass1()
	 * @generated
	 */
	int NEW_ECLASS1 = 0;

	/**
	 * The feature id for the '<em><b>Neweclass2</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ECLASS1__NEWECLASS2 = 0;

	/**
	 * The feature id for the '<em><b>Neweclass3</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ECLASS1__NEWECLASS3 = 1;

	/**
	 * The number of structural features of the '<em>New EClass1</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ECLASS1_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>New EClass1</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ECLASS1_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link a.impl.NewEClass2Impl <em>New EClass2</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see a.impl.NewEClass2Impl
	 * @see a.impl.APackageImpl#getNewEClass2()
	 * @generated
	 */
	int NEW_ECLASS2 = 1;

	/**
	 * The number of structural features of the '<em>New EClass2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ECLASS2_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>New EClass2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ECLASS2_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link a.impl.NewEClass3Impl <em>New EClass3</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see a.impl.NewEClass3Impl
	 * @see a.impl.APackageImpl#getNewEClass3()
	 * @generated
	 */
	int NEW_ECLASS3 = 2;

	/**
	 * The number of structural features of the '<em>New EClass3</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ECLASS3_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>New EClass3</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ECLASS3_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link a.NewEClass1 <em>New EClass1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>New EClass1</em>'.
	 * @see a.NewEClass1
	 * @generated
	 */
	EClass getNewEClass1();

	/**
	 * Returns the meta object for the containment reference list '{@link a.NewEClass1#getNeweclass2 <em>Neweclass2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Neweclass2</em>'.
	 * @see a.NewEClass1#getNeweclass2()
	 * @see #getNewEClass1()
	 * @generated
	 */
	EReference getNewEClass1_Neweclass2();

	/**
	 * Returns the meta object for the containment reference list '{@link a.NewEClass1#getNeweclass3 <em>Neweclass3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Neweclass3</em>'.
	 * @see a.NewEClass1#getNeweclass3()
	 * @see #getNewEClass1()
	 * @generated
	 */
	EReference getNewEClass1_Neweclass3();

	/**
	 * Returns the meta object for class '{@link a.NewEClass2 <em>New EClass2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>New EClass2</em>'.
	 * @see a.NewEClass2
	 * @generated
	 */
	EClass getNewEClass2();

	/**
	 * Returns the meta object for class '{@link a.NewEClass3 <em>New EClass3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>New EClass3</em>'.
	 * @see a.NewEClass3
	 * @generated
	 */
	EClass getNewEClass3();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AFactory getAFactory();

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
		 * The meta object literal for the '{@link a.impl.NewEClass1Impl <em>New EClass1</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see a.impl.NewEClass1Impl
		 * @see a.impl.APackageImpl#getNewEClass1()
		 * @generated
		 */
		EClass NEW_ECLASS1 = eINSTANCE.getNewEClass1();

		/**
		 * The meta object literal for the '<em><b>Neweclass2</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NEW_ECLASS1__NEWECLASS2 = eINSTANCE.getNewEClass1_Neweclass2();

		/**
		 * The meta object literal for the '<em><b>Neweclass3</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NEW_ECLASS1__NEWECLASS3 = eINSTANCE.getNewEClass1_Neweclass3();

		/**
		 * The meta object literal for the '{@link a.impl.NewEClass2Impl <em>New EClass2</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see a.impl.NewEClass2Impl
		 * @see a.impl.APackageImpl#getNewEClass2()
		 * @generated
		 */
		EClass NEW_ECLASS2 = eINSTANCE.getNewEClass2();

		/**
		 * The meta object literal for the '{@link a.impl.NewEClass3Impl <em>New EClass3</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see a.impl.NewEClass3Impl
		 * @see a.impl.APackageImpl#getNewEClass3()
		 * @generated
		 */
		EClass NEW_ECLASS3 = eINSTANCE.getNewEClass3();

	}

} //APackage
