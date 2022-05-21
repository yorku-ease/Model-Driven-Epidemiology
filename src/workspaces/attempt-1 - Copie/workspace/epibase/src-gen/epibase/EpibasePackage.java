/**
 */
package epibase;

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
 * @see epibase.EpibaseFactory
 * @model kind="package"
 * @generated
 */
public interface EpibasePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "epibase";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/epibase";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "epibase";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EpibasePackage eINSTANCE = epibase.impl.EpibasePackageImpl.init();

	/**
	 * The meta object id for the '{@link epibase.Flow <em>Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epibase.Flow
	 * @see epibase.impl.EpibasePackageImpl#getFlow()
	 * @generated
	 */
	int FLOW = 0;

	/**
	 * The number of structural features of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epibase.Compartment <em>Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epibase.Compartment
	 * @see epibase.impl.EpibasePackageImpl#getCompartment()
	 * @generated
	 */
	int COMPARTMENT = 1;

	/**
	 * The number of structural features of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epibase.impl.DimensionImpl <em>Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epibase.impl.DimensionImpl
	 * @see epibase.impl.EpibasePackageImpl#getDimension()
	 * @generated
	 */
	int DIMENSION = 2;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__COMPARTMENT = 0;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__FLOW = 1;

	/**
	 * The number of structural features of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epibase.impl.EpidemicImpl <em>Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epibase.impl.EpidemicImpl
	 * @see epibase.impl.EpibasePackageImpl#getEpidemic()
	 * @generated
	 */
	int EPIDEMIC = 3;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__DIMENSION = 0;

	/**
	 * The number of structural features of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link epibase.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow</em>'.
	 * @see epibase.Flow
	 * @generated
	 */
	EClass getFlow();

	/**
	 * Returns the meta object for class '{@link epibase.Compartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compartment</em>'.
	 * @see epibase.Compartment
	 * @generated
	 */
	EClass getCompartment();

	/**
	 * Returns the meta object for class '{@link epibase.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension</em>'.
	 * @see epibase.Dimension
	 * @generated
	 */
	EClass getDimension();

	/**
	 * Returns the meta object for the containment reference list '{@link epibase.Dimension#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartment</em>'.
	 * @see epibase.Dimension#getCompartment()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Compartment();

	/**
	 * Returns the meta object for the containment reference list '{@link epibase.Dimension#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see epibase.Dimension#getFlow()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Flow();

	/**
	 * Returns the meta object for class '{@link epibase.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Epidemic</em>'.
	 * @see epibase.Epidemic
	 * @generated
	 */
	EClass getEpidemic();

	/**
	 * Returns the meta object for the containment reference list '{@link epibase.Epidemic#getDimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dimension</em>'.
	 * @see epibase.Epidemic#getDimension()
	 * @see #getEpidemic()
	 * @generated
	 */
	EReference getEpidemic_Dimension();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EpibaseFactory getEpibaseFactory();

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
		 * The meta object literal for the '{@link epibase.Flow <em>Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epibase.Flow
		 * @see epibase.impl.EpibasePackageImpl#getFlow()
		 * @generated
		 */
		EClass FLOW = eINSTANCE.getFlow();
		/**
		 * The meta object literal for the '{@link epibase.Compartment <em>Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epibase.Compartment
		 * @see epibase.impl.EpibasePackageImpl#getCompartment()
		 * @generated
		 */
		EClass COMPARTMENT = eINSTANCE.getCompartment();
		/**
		 * The meta object literal for the '{@link epibase.impl.DimensionImpl <em>Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epibase.impl.DimensionImpl
		 * @see epibase.impl.EpibasePackageImpl#getDimension()
		 * @generated
		 */
		EClass DIMENSION = eINSTANCE.getDimension();
		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION__COMPARTMENT = eINSTANCE.getDimension_Compartment();
		/**
		 * The meta object literal for the '<em><b>Flow</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION__FLOW = eINSTANCE.getDimension_Flow();
		/**
		 * The meta object literal for the '{@link epibase.impl.EpidemicImpl <em>Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epibase.impl.EpidemicImpl
		 * @see epibase.impl.EpibasePackageImpl#getEpidemic()
		 * @generated
		 */
		EClass EPIDEMIC = eINSTANCE.getEpidemic();
		/**
		 * The meta object literal for the '<em><b>Dimension</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EPIDEMIC__DIMENSION = eINSTANCE.getEpidemic_Dimension();

	}

} //EpibasePackage
