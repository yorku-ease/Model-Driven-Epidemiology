/**
 */
package batchRateContactFlows;

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
 * @see batchRateContactFlows.BatchRateContactFlowsFactory
 * @model kind="package"
 * @generated
 */
public interface BatchRateContactFlowsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "batchRateContactFlows";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/batchRateContactFlows";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "batchRateContactFlows";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BatchRateContactFlowsPackage eINSTANCE = batchRateContactFlows.impl.BatchRateContactFlowsPackageImpl.init();

	/**
	 * The meta object id for the '{@link batchRateContactFlows.impl.FromToFlowImpl <em>From To Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see batchRateContactFlows.impl.FromToFlowImpl
	 * @see batchRateContactFlows.impl.BatchRateContactFlowsPackageImpl#getFromToFlow()
	 * @generated
	 */
	int FROM_TO_FLOW = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TO_FLOW__ID = EpimodelPackage.FLOW__ID;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TO_FLOW__FROM = EpimodelPackage.FLOW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TO_FLOW__TO = EpimodelPackage.FLOW_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>From To Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TO_FLOW_FEATURE_COUNT = EpimodelPackage.FLOW_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>From To Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TO_FLOW_OPERATION_COUNT = EpimodelPackage.FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link batchRateContactFlows.impl.BatchImpl <em>Batch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see batchRateContactFlows.impl.BatchImpl
	 * @see batchRateContactFlows.impl.BatchRateContactFlowsPackageImpl#getBatch()
	 * @generated
	 */
	int BATCH = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH__ID = FROM_TO_FLOW__ID;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH__FROM = FROM_TO_FLOW__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH__TO = FROM_TO_FLOW__TO;

	/**
	 * The number of structural features of the '<em>Batch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_FEATURE_COUNT = FROM_TO_FLOW_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Batch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_OPERATION_COUNT = FROM_TO_FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link batchRateContactFlows.impl.RateImpl <em>Rate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see batchRateContactFlows.impl.RateImpl
	 * @see batchRateContactFlows.impl.BatchRateContactFlowsPackageImpl#getRate()
	 * @generated
	 */
	int RATE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__ID = FROM_TO_FLOW__ID;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__FROM = FROM_TO_FLOW__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__TO = FROM_TO_FLOW__TO;

	/**
	 * The number of structural features of the '<em>Rate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_FEATURE_COUNT = FROM_TO_FLOW_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Rate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_OPERATION_COUNT = FROM_TO_FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link batchRateContactFlows.impl.ContactImpl <em>Contact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see batchRateContactFlows.impl.ContactImpl
	 * @see batchRateContactFlows.impl.BatchRateContactFlowsPackageImpl#getContact()
	 * @generated
	 */
	int CONTACT = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__ID = FROM_TO_FLOW__ID;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__FROM = FROM_TO_FLOW__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__TO = FROM_TO_FLOW__TO;

	/**
	 * The feature id for the '<em><b>Contact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__CONTACT = FROM_TO_FLOW_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FEATURE_COUNT = FROM_TO_FLOW_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_OPERATION_COUNT = FROM_TO_FLOW_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link batchRateContactFlows.Batch <em>Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Batch</em>'.
	 * @see batchRateContactFlows.Batch
	 * @generated
	 */
	EClass getBatch();

	/**
	 * Returns the meta object for class '{@link batchRateContactFlows.Rate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rate</em>'.
	 * @see batchRateContactFlows.Rate
	 * @generated
	 */
	EClass getRate();

	/**
	 * Returns the meta object for class '{@link batchRateContactFlows.Contact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contact</em>'.
	 * @see batchRateContactFlows.Contact
	 * @generated
	 */
	EClass getContact();

	/**
	 * Returns the meta object for the reference '{@link batchRateContactFlows.Contact#getContact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contact</em>'.
	 * @see batchRateContactFlows.Contact#getContact()
	 * @see #getContact()
	 * @generated
	 */
	EReference getContact_Contact();

	/**
	 * Returns the meta object for class '{@link batchRateContactFlows.FromToFlow <em>From To Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>From To Flow</em>'.
	 * @see batchRateContactFlows.FromToFlow
	 * @generated
	 */
	EClass getFromToFlow();

	/**
	 * Returns the meta object for the reference '{@link batchRateContactFlows.FromToFlow#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see batchRateContactFlows.FromToFlow#getFrom()
	 * @see #getFromToFlow()
	 * @generated
	 */
	EReference getFromToFlow_From();

	/**
	 * Returns the meta object for the reference '{@link batchRateContactFlows.FromToFlow#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see batchRateContactFlows.FromToFlow#getTo()
	 * @see #getFromToFlow()
	 * @generated
	 */
	EReference getFromToFlow_To();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BatchRateContactFlowsFactory getBatchRateContactFlowsFactory();

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
		 * The meta object literal for the '{@link batchRateContactFlows.impl.BatchImpl <em>Batch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see batchRateContactFlows.impl.BatchImpl
		 * @see batchRateContactFlows.impl.BatchRateContactFlowsPackageImpl#getBatch()
		 * @generated
		 */
		EClass BATCH = eINSTANCE.getBatch();

		/**
		 * The meta object literal for the '{@link batchRateContactFlows.impl.RateImpl <em>Rate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see batchRateContactFlows.impl.RateImpl
		 * @see batchRateContactFlows.impl.BatchRateContactFlowsPackageImpl#getRate()
		 * @generated
		 */
		EClass RATE = eINSTANCE.getRate();

		/**
		 * The meta object literal for the '{@link batchRateContactFlows.impl.ContactImpl <em>Contact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see batchRateContactFlows.impl.ContactImpl
		 * @see batchRateContactFlows.impl.BatchRateContactFlowsPackageImpl#getContact()
		 * @generated
		 */
		EClass CONTACT = eINSTANCE.getContact();

		/**
		 * The meta object literal for the '<em><b>Contact</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTACT__CONTACT = eINSTANCE.getContact_Contact();

		/**
		 * The meta object literal for the '{@link batchRateContactFlows.impl.FromToFlowImpl <em>From To Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see batchRateContactFlows.impl.FromToFlowImpl
		 * @see batchRateContactFlows.impl.BatchRateContactFlowsPackageImpl#getFromToFlow()
		 * @generated
		 */
		EClass FROM_TO_FLOW = eINSTANCE.getFromToFlow();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FROM_TO_FLOW__FROM = eINSTANCE.getFromToFlow_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FROM_TO_FLOW__TO = eINSTANCE.getFromToFlow_To();

	}

} //BatchRateContactFlowsPackage
