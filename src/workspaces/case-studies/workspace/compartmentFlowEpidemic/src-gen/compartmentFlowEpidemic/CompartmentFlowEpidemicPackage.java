/**
 */
package compartmentFlowEpidemic;

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
 * @see compartmentFlowEpidemic.CompartmentFlowEpidemicFactory
 * @model kind="package"
 * @generated
 */
public interface CompartmentFlowEpidemicPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "compartmentFlowEpidemic";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/compartmentFlowEpidemic";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "compartmentFlowEpidemic";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CompartmentFlowEpidemicPackage eINSTANCE = compartmentFlowEpidemic.impl.CompartmentFlowEpidemicPackageImpl.init();

	/**
	 * The meta object id for the '{@link compartmentFlowEpidemic.impl.CompartmentFlowEpidemicImpl <em>Compartment Flow Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see compartmentFlowEpidemic.impl.CompartmentFlowEpidemicImpl
	 * @see compartmentFlowEpidemic.impl.CompartmentFlowEpidemicPackageImpl#getCompartmentFlowEpidemic()
	 * @generated
	 */
	int COMPARTMENT_FLOW_EPIDEMIC = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_FLOW_EPIDEMIC__ID = EpimodelPackage.EPIDEMIC__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_FLOW_EPIDEMIC__COMPARTMENT = EpimodelPackage.EPIDEMIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_FLOW_EPIDEMIC__FLOW = EpimodelPackage.EPIDEMIC_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Compartment Flow Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_FLOW_EPIDEMIC_FEATURE_COUNT = EpimodelPackage.EPIDEMIC_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Compartment Flow Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_FLOW_EPIDEMIC_OPERATION_COUNT = EpimodelPackage.EPIDEMIC_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link compartmentFlowEpidemic.CompartmentFlowEpidemic <em>Compartment Flow Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compartment Flow Epidemic</em>'.
	 * @see compartmentFlowEpidemic.CompartmentFlowEpidemic
	 * @generated
	 */
	EClass getCompartmentFlowEpidemic();

	/**
	 * Returns the meta object for the containment reference list '{@link compartmentFlowEpidemic.CompartmentFlowEpidemic#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartment</em>'.
	 * @see compartmentFlowEpidemic.CompartmentFlowEpidemic#getCompartment()
	 * @see #getCompartmentFlowEpidemic()
	 * @generated
	 */
	EReference getCompartmentFlowEpidemic_Compartment();

	/**
	 * Returns the meta object for the containment reference list '{@link compartmentFlowEpidemic.CompartmentFlowEpidemic#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see compartmentFlowEpidemic.CompartmentFlowEpidemic#getFlow()
	 * @see #getCompartmentFlowEpidemic()
	 * @generated
	 */
	EReference getCompartmentFlowEpidemic_Flow();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CompartmentFlowEpidemicFactory getCompartmentFlowEpidemicFactory();

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
		 * The meta object literal for the '{@link compartmentFlowEpidemic.impl.CompartmentFlowEpidemicImpl <em>Compartment Flow Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see compartmentFlowEpidemic.impl.CompartmentFlowEpidemicImpl
		 * @see compartmentFlowEpidemic.impl.CompartmentFlowEpidemicPackageImpl#getCompartmentFlowEpidemic()
		 * @generated
		 */
		EClass COMPARTMENT_FLOW_EPIDEMIC = eINSTANCE.getCompartmentFlowEpidemic();

		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPARTMENT_FLOW_EPIDEMIC__COMPARTMENT = eINSTANCE.getCompartmentFlowEpidemic_Compartment();

		/**
		 * The meta object literal for the '<em><b>Flow</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPARTMENT_FLOW_EPIDEMIC__FLOW = eINSTANCE.getCompartmentFlowEpidemic_Flow();

	}

} //CompartmentFlowEpidemicPackage
