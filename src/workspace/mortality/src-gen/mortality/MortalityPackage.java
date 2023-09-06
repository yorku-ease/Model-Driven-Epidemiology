/**
 */
package mortality;

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
 * @see mortality.MortalityFactory
 * @model kind="package"
 * @generated
 */
public interface MortalityPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "mortality";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/mortality";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "mortality";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MortalityPackage eINSTANCE = mortality.impl.MortalityPackageImpl.init();

	/**
	 * The meta object id for the '{@link mortality.impl.MortalityImpl <em>Mortality</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mortality.impl.MortalityImpl
	 * @see mortality.impl.MortalityPackageImpl#getMortality()
	 * @generated
	 */
	int MORTALITY = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MORTALITY__LABEL = EpimodelPackage.COMPARTMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Mortality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MORTALITY__MORTALITY = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Mortality</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MORTALITY_FEATURE_COUNT = EpimodelPackage.COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Mortality</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MORTALITY_OPERATION_COUNT = EpimodelPackage.COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link mortality.Mortality <em>Mortality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mortality</em>'.
	 * @see mortality.Mortality
	 * @generated
	 */
	EClass getMortality();

	/**
	 * Returns the meta object for the containment reference '{@link mortality.Mortality#getMortality <em>Mortality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mortality</em>'.
	 * @see mortality.Mortality#getMortality()
	 * @see #getMortality()
	 * @generated
	 */
	EReference getMortality_Mortality();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MortalityFactory getMortalityFactory();

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
		 * The meta object literal for the '{@link mortality.impl.MortalityImpl <em>Mortality</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mortality.impl.MortalityImpl
		 * @see mortality.impl.MortalityPackageImpl#getMortality()
		 * @generated
		 */
		EClass MORTALITY = eINSTANCE.getMortality();

		/**
		 * The meta object literal for the '<em><b>Mortality</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MORTALITY__MORTALITY = eINSTANCE.getMortality_Mortality();

	}

} //MortalityPackage
