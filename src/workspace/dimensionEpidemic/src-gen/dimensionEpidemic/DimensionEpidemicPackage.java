/**
 */
package dimensionEpidemic;

import epimodel.EpimodelPackage;

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
 * @see dimensionEpidemic.DimensionEpidemicFactory
 * @model kind="package"
 * @generated
 */
public interface DimensionEpidemicPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "dimensionEpidemic";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/dimensionEpidemic";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "dimensionEpidemic";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DimensionEpidemicPackage eINSTANCE = dimensionEpidemic.impl.DimensionEpidemicPackageImpl.init();

	/**
	 * The meta object id for the '{@link dimensionEpidemic.impl.DimensionEpidemicImpl <em>Dimension Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dimensionEpidemic.impl.DimensionEpidemicImpl
	 * @see dimensionEpidemic.impl.DimensionEpidemicPackageImpl#getDimensionEpidemic()
	 * @generated
	 */
	int DIMENSION_EPIDEMIC = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_EPIDEMIC__ID = EpimodelPackage.EPIDEMIC__ID;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_EPIDEMIC__DIMENSION = EpimodelPackage.EPIDEMIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Core Compartment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_EPIDEMIC__CORE_COMPARTMENT = EpimodelPackage.EPIDEMIC_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Dimension Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_EPIDEMIC_FEATURE_COUNT = EpimodelPackage.EPIDEMIC_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Dimension Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_EPIDEMIC_OPERATION_COUNT = EpimodelPackage.EPIDEMIC_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link dimensionEpidemic.impl.DimensionImpl <em>Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dimensionEpidemic.impl.DimensionImpl
	 * @see dimensionEpidemic.impl.DimensionEpidemicPackageImpl#getDimension()
	 * @generated
	 */
	int DIMENSION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__ID = EpimodelPackage.COMPARTMENT__ID;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__FLOW = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Core Compartment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__CORE_COMPARTMENT = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__DIMENSION = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_FEATURE_COUNT = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_OPERATION_COUNT = EpimodelPackage.COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link dimensionEpidemic.impl.DimensionWrapperImpl <em>Dimension Wrapper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dimensionEpidemic.impl.DimensionWrapperImpl
	 * @see dimensionEpidemic.impl.DimensionEpidemicPackageImpl#getDimensionWrapper()
	 * @generated
	 */
	int DIMENSION_WRAPPER = 2;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_WRAPPER__DIMENSION = 0;

	/**
	 * The number of structural features of the '<em>Dimension Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_WRAPPER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Dimension Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_WRAPPER_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link dimensionEpidemic.DimensionEpidemic <em>Dimension Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension Epidemic</em>'.
	 * @see dimensionEpidemic.DimensionEpidemic
	 * @generated
	 */
	EClass getDimensionEpidemic();

	/**
	 * Returns the meta object for the containment reference list '{@link dimensionEpidemic.DimensionEpidemic#getDimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dimension</em>'.
	 * @see dimensionEpidemic.DimensionEpidemic#getDimension()
	 * @see #getDimensionEpidemic()
	 * @generated
	 */
	EReference getDimensionEpidemic_Dimension();

	/**
	 * Returns the meta object for the containment reference '{@link dimensionEpidemic.DimensionEpidemic#getCoreCompartment <em>Core Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Core Compartment</em>'.
	 * @see dimensionEpidemic.DimensionEpidemic#getCoreCompartment()
	 * @see #getDimensionEpidemic()
	 * @generated
	 */
	EReference getDimensionEpidemic_CoreCompartment();

	/**
	 * Returns the meta object for class '{@link dimensionEpidemic.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension</em>'.
	 * @see dimensionEpidemic.Dimension
	 * @generated
	 */
	EClass getDimension();

	/**
	 * Returns the meta object for the containment reference list '{@link dimensionEpidemic.Dimension#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see dimensionEpidemic.Dimension#getFlow()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Flow();

	/**
	 * Returns the meta object for the containment reference '{@link dimensionEpidemic.Dimension#getCoreCompartment <em>Core Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Core Compartment</em>'.
	 * @see dimensionEpidemic.Dimension#getCoreCompartment()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_CoreCompartment();

	/**
	 * Returns the meta object for the containment reference list '{@link dimensionEpidemic.Dimension#getDimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dimension</em>'.
	 * @see dimensionEpidemic.Dimension#getDimension()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Dimension();

	/**
	 * Returns the meta object for class '{@link dimensionEpidemic.DimensionWrapper <em>Dimension Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension Wrapper</em>'.
	 * @see dimensionEpidemic.DimensionWrapper
	 * @generated
	 */
	EClass getDimensionWrapper();

	/**
	 * Returns the meta object for the containment reference '{@link dimensionEpidemic.DimensionWrapper#getDimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Dimension</em>'.
	 * @see dimensionEpidemic.DimensionWrapper#getDimension()
	 * @see #getDimensionWrapper()
	 * @generated
	 */
	EReference getDimensionWrapper_Dimension();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DimensionEpidemicFactory getDimensionEpidemicFactory();

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
		 * The meta object literal for the '{@link dimensionEpidemic.impl.DimensionEpidemicImpl <em>Dimension Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see dimensionEpidemic.impl.DimensionEpidemicImpl
		 * @see dimensionEpidemic.impl.DimensionEpidemicPackageImpl#getDimensionEpidemic()
		 * @generated
		 */
		EClass DIMENSION_EPIDEMIC = eINSTANCE.getDimensionEpidemic();

		/**
		 * The meta object literal for the '<em><b>Dimension</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION_EPIDEMIC__DIMENSION = eINSTANCE.getDimensionEpidemic_Dimension();

		/**
		 * The meta object literal for the '<em><b>Core Compartment</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION_EPIDEMIC__CORE_COMPARTMENT = eINSTANCE.getDimensionEpidemic_CoreCompartment();

		/**
		 * The meta object literal for the '{@link dimensionEpidemic.impl.DimensionImpl <em>Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see dimensionEpidemic.impl.DimensionImpl
		 * @see dimensionEpidemic.impl.DimensionEpidemicPackageImpl#getDimension()
		 * @generated
		 */
		EClass DIMENSION = eINSTANCE.getDimension();

		/**
		 * The meta object literal for the '<em><b>Flow</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION__FLOW = eINSTANCE.getDimension_Flow();

		/**
		 * The meta object literal for the '<em><b>Core Compartment</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION__CORE_COMPARTMENT = eINSTANCE.getDimension_CoreCompartment();

		/**
		 * The meta object literal for the '<em><b>Dimension</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION__DIMENSION = eINSTANCE.getDimension_Dimension();

		/**
		 * The meta object literal for the '{@link dimensionEpidemic.impl.DimensionWrapperImpl <em>Dimension Wrapper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see dimensionEpidemic.impl.DimensionWrapperImpl
		 * @see dimensionEpidemic.impl.DimensionEpidemicPackageImpl#getDimensionWrapper()
		 * @generated
		 */
		EClass DIMENSION_WRAPPER = eINSTANCE.getDimensionWrapper();

		/**
		 * The meta object literal for the '<em><b>Dimension</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION_WRAPPER__DIMENSION = eINSTANCE.getDimensionWrapper_Dimension();

	}

} //DimensionEpidemicPackage
