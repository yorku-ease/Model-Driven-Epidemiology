/**
 */
package epidemicDimension;

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
 * @see epidemicDimension.EpidemicDimensionFactory
 * @model kind="package"
 * @generated
 */
public interface EpidemicDimensionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "epidemicDimension";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/epidemicDimension";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "epidemicDimension";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EpidemicDimensionPackage eINSTANCE = epidemicDimension.impl.EpidemicDimensionPackageImpl.init();

	/**
	 * The meta object id for the '{@link epidemicDimension.impl.DimensionImpl <em>Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidemicDimension.impl.DimensionImpl
	 * @see epidemicDimension.impl.EpidemicDimensionPackageImpl#getDimension()
	 * @generated
	 */
	int DIMENSION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__ID = EpimodelPackage.COMPARTMENT__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__COMPARTMENT = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__FLOW = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_FEATURE_COUNT = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_OPERATION_COUNT = EpimodelPackage.COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link epidemicDimension.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension</em>'.
	 * @see epidemicDimension.Dimension
	 * @generated
	 */
	EClass getDimension();

	/**
	 * Returns the meta object for the containment reference list '{@link epidemicDimension.Dimension#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartment</em>'.
	 * @see epidemicDimension.Dimension#getCompartment()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Compartment();

	/**
	 * Returns the meta object for the containment reference list '{@link epidemicDimension.Dimension#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see epidemicDimension.Dimension#getFlow()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Flow();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EpidemicDimensionFactory getEpidemicDimensionFactory();

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
		 * The meta object literal for the '{@link epidemicDimension.impl.DimensionImpl <em>Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidemicDimension.impl.DimensionImpl
		 * @see epidemicDimension.impl.EpidemicDimensionPackageImpl#getDimension()
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

	}

} //EpidemicDimensionPackage
