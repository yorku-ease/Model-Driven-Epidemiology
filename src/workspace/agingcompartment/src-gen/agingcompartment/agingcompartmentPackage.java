/**
 */
package agingcompartment;

import epimodel.EpimodelPackage;

import org.eclipse.emf.ecore.EAttribute;
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
 * @see agingcompartment.agingcompartmentFactory
 * @model kind="package"
 * @generated
 */
public interface agingcompartmentPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "agingcompartment";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/agingcompartment";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "agingcompartment";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	agingcompartmentPackage eINSTANCE = agingcompartment.impl.agingcompartmentPackageImpl.init();

	/**
	 * The meta object id for the '{@link agingcompartment.impl.AgingImpl <em>Aging</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see agingcompartment.impl.AgingImpl
	 * @see agingcompartment.impl.agingcompartmentPackageImpl#getAging()
	 * @generated
	 */
	int AGING = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGING__LABEL = EpimodelPackage.COMPARTMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Age Groups</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGING__AGE_GROUPS = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Aging</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGING_FEATURE_COUNT = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Aging</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGING_OPERATION_COUNT = EpimodelPackage.COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link agingcompartment.Aging <em>Aging</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Aging</em>'.
	 * @see agingcompartment.Aging
	 * @generated
	 */
	EClass getAging();

	/**
	 * Returns the meta object for the attribute '{@link agingcompartment.Aging#getAgeGroups <em>Age Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Age Groups</em>'.
	 * @see agingcompartment.Aging#getAgeGroups()
	 * @see #getAging()
	 * @generated
	 */
	EAttribute getAging_AgeGroups();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	agingcompartmentFactory getagingcompartmentFactory();

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
		 * The meta object literal for the '{@link agingcompartment.impl.AgingImpl <em>Aging</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see agingcompartment.impl.AgingImpl
		 * @see agingcompartment.impl.agingcompartmentPackageImpl#getAging()
		 * @generated
		 */
		EClass AGING = eINSTANCE.getAging();

		/**
		 * The meta object literal for the '<em><b>Age Groups</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AGING__AGE_GROUPS = eINSTANCE.getAging_AgeGroups();

	}

} //agingcompartmentPackage
