/**
 */
package compartmentLink;

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
 * @see compartmentLink.CompartmentLinkFactory
 * @model kind="package"
 * @generated
 */
public interface CompartmentLinkPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "compartmentLink";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/compartmentLink";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "compartmentLink";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CompartmentLinkPackage eINSTANCE = compartmentLink.impl.CompartmentLinkPackageImpl.init();

	/**
	 * The meta object id for the '{@link compartmentLink.impl.CompartmentLinkImpl <em>Compartment Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see compartmentLink.impl.CompartmentLinkImpl
	 * @see compartmentLink.impl.CompartmentLinkPackageImpl#getCompartmentLink()
	 * @generated
	 */
	int COMPARTMENT_LINK = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_LINK__LABEL = EpimodelPackage.COMPARTMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_LINK__COMPARTMENT = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Compartment Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_LINK_FEATURE_COUNT = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Compartment Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_LINK_OPERATION_COUNT = EpimodelPackage.COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link compartmentLink.CompartmentLink <em>Compartment Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compartment Link</em>'.
	 * @see compartmentLink.CompartmentLink
	 * @generated
	 */
	EClass getCompartmentLink();

	/**
	 * Returns the meta object for the reference '{@link compartmentLink.CompartmentLink#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Compartment</em>'.
	 * @see compartmentLink.CompartmentLink#getCompartment()
	 * @see #getCompartmentLink()
	 * @generated
	 */
	EReference getCompartmentLink_Compartment();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CompartmentLinkFactory getCompartmentLinkFactory();

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
		 * The meta object literal for the '{@link compartmentLink.impl.CompartmentLinkImpl <em>Compartment Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see compartmentLink.impl.CompartmentLinkImpl
		 * @see compartmentLink.impl.CompartmentLinkPackageImpl#getCompartmentLink()
		 * @generated
		 */
		EClass COMPARTMENT_LINK = eINSTANCE.getCompartmentLink();

		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPARTMENT_LINK__COMPARTMENT = eINSTANCE.getCompartmentLink_Compartment();

	}

} //CompartmentLinkPackage
