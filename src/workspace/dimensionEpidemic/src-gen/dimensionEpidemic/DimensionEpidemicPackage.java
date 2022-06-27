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
	 * The number of structural features of the '<em>Dimension Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_EPIDEMIC_FEATURE_COUNT = EpimodelPackage.EPIDEMIC_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Dimension Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_EPIDEMIC_OPERATION_COUNT = EpimodelPackage.EPIDEMIC_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link dimensionEpidemic.impl.ProductImpl <em>Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dimensionEpidemic.impl.ProductImpl
	 * @see dimensionEpidemic.impl.DimensionEpidemicPackageImpl#getProduct()
	 * @generated
	 */
	int PRODUCT = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__ID = EpimodelPackage.COMPARTMENT__ID;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__FLOW = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Dimensions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__DIMENSIONS = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_FEATURE_COUNT = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_OPERATION_COUNT = EpimodelPackage.COMPARTMENT_OPERATION_COUNT + 0;

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
	 * Returns the meta object for class '{@link dimensionEpidemic.Product <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product</em>'.
	 * @see dimensionEpidemic.Product
	 * @generated
	 */
	EClass getProduct();

	/**
	 * Returns the meta object for the containment reference list '{@link dimensionEpidemic.Product#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see dimensionEpidemic.Product#getFlow()
	 * @see #getProduct()
	 * @generated
	 */
	EReference getProduct_Flow();

	/**
	 * Returns the meta object for the containment reference list '{@link dimensionEpidemic.Product#getDimensions <em>Dimensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dimensions</em>'.
	 * @see dimensionEpidemic.Product#getDimensions()
	 * @see #getProduct()
	 * @generated
	 */
	EReference getProduct_Dimensions();

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
		 * The meta object literal for the '{@link dimensionEpidemic.impl.ProductImpl <em>Product</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see dimensionEpidemic.impl.ProductImpl
		 * @see dimensionEpidemic.impl.DimensionEpidemicPackageImpl#getProduct()
		 * @generated
		 */
		EClass PRODUCT = eINSTANCE.getProduct();

		/**
		 * The meta object literal for the '<em><b>Flow</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT__FLOW = eINSTANCE.getProduct_Flow();

		/**
		 * The meta object literal for the '<em><b>Dimensions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT__DIMENSIONS = eINSTANCE.getProduct_Dimensions();

	}

} //DimensionEpidemicPackage
