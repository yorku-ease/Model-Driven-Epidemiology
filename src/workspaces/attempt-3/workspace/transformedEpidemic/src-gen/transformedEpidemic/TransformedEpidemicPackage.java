/**
 */
package transformedEpidemic;

import epidemicDimension.EpidemicDimensionPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

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
 * @see transformedEpidemic.TransformedEpidemicFactory
 * @model kind="package"
 * @generated
 */
public interface TransformedEpidemicPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "transformedEpidemic";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/transformedEpidemic";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "transformedEpidemic";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TransformedEpidemicPackage eINSTANCE = transformedEpidemic.impl.TransformedEpidemicPackageImpl.init();

	/**
	 * The meta object id for the '{@link transformedEpidemic.impl.TransformedEpidemicImpl <em>Transformed Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see transformedEpidemic.impl.TransformedEpidemicImpl
	 * @see transformedEpidemic.impl.TransformedEpidemicPackageImpl#getTransformedEpidemic()
	 * @generated
	 */
	int TRANSFORMED_EPIDEMIC = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMED_EPIDEMIC__ID = EpidemicDimensionPackage.DIMENSION__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMED_EPIDEMIC__COMPARTMENT = EpidemicDimensionPackage.DIMENSION__COMPARTMENT;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMED_EPIDEMIC__FLOW = EpidemicDimensionPackage.DIMENSION__FLOW;

	/**
	 * The number of structural features of the '<em>Transformed Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMED_EPIDEMIC_FEATURE_COUNT = EpidemicDimensionPackage.DIMENSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Transformed Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMED_EPIDEMIC_OPERATION_COUNT = EpidemicDimensionPackage.DIMENSION_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link transformedEpidemic.TransformedEpidemic <em>Transformed Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transformed Epidemic</em>'.
	 * @see transformedEpidemic.TransformedEpidemic
	 * @generated
	 */
	EClass getTransformedEpidemic();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TransformedEpidemicFactory getTransformedEpidemicFactory();

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
		 * The meta object literal for the '{@link transformedEpidemic.impl.TransformedEpidemicImpl <em>Transformed Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see transformedEpidemic.impl.TransformedEpidemicImpl
		 * @see transformedEpidemic.impl.TransformedEpidemicPackageImpl#getTransformedEpidemic()
		 * @generated
		 */
		EClass TRANSFORMED_EPIDEMIC = eINSTANCE.getTransformedEpidemic();

	}

} //TransformedEpidemicPackage
