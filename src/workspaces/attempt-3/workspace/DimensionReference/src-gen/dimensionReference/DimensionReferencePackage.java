/**
 */
package dimensionReference;

import epidemicDimension.EpidemicDimensionPackage;

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
 * @see dimensionReference.DimensionReferenceFactory
 * @model kind="package"
 * @generated
 */
public interface DimensionReferencePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "dimensionReference";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/dimensionReference";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "dimensionReference";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DimensionReferencePackage eINSTANCE = dimensionReference.impl.DimensionReferencePackageImpl.init();

	/**
	 * The meta object id for the '{@link dimensionReference.impl.DimensionLinkImpl <em>Dimension Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dimensionReference.impl.DimensionLinkImpl
	 * @see dimensionReference.impl.DimensionReferencePackageImpl#getDimensionLink()
	 * @generated
	 */
	int DIMENSION_LINK = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_LINK__ID = EpidemicDimensionPackage.DIMENSION__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_LINK__COMPARTMENT = EpidemicDimensionPackage.DIMENSION__COMPARTMENT;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_LINK__FLOW = EpidemicDimensionPackage.DIMENSION__FLOW;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_LINK__DIMENSION = EpidemicDimensionPackage.DIMENSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Dimension Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_LINK_FEATURE_COUNT = EpidemicDimensionPackage.DIMENSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Dimension Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_LINK_OPERATION_COUNT = EpidemicDimensionPackage.DIMENSION_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link dimensionReference.DimensionLink <em>Dimension Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension Link</em>'.
	 * @see dimensionReference.DimensionLink
	 * @generated
	 */
	EClass getDimensionLink();

	/**
	 * Returns the meta object for the reference '{@link dimensionReference.DimensionLink#getDimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Dimension</em>'.
	 * @see dimensionReference.DimensionLink#getDimension()
	 * @see #getDimensionLink()
	 * @generated
	 */
	EReference getDimensionLink_Dimension();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DimensionReferenceFactory getDimensionReferenceFactory();

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
		 * The meta object literal for the '{@link dimensionReference.impl.DimensionLinkImpl <em>Dimension Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see dimensionReference.impl.DimensionLinkImpl
		 * @see dimensionReference.impl.DimensionReferencePackageImpl#getDimensionLink()
		 * @generated
		 */
		EClass DIMENSION_LINK = eINSTANCE.getDimensionLink();

		/**
		 * The meta object literal for the '<em><b>Dimension</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION_LINK__DIMENSION = eINSTANCE.getDimensionLink_Dimension();

	}

} //DimensionReferencePackage
