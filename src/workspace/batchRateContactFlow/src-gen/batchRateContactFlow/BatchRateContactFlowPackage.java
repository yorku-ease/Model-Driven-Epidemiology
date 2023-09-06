/**
 */
package batchRateContactFlow;

import epimodel.EpimodelPackage;

import org.eclipse.emf.ecore.EAttribute;
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
 * @see batchRateContactFlow.BatchRateContactFlowFactory
 * @model kind="package"
 * @generated
 */
public interface BatchRateContactFlowPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "batchRateContactFlow";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/batchRateContactFlow";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "batchRateContactFlow";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BatchRateContactFlowPackage eINSTANCE = batchRateContactFlow.impl.BatchRateContactFlowPackageImpl.init();

	/**
	 * The meta object id for the '{@link batchRateContactFlow.impl.FromToFlowImpl <em>From To Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see batchRateContactFlow.impl.FromToFlowImpl
	 * @see batchRateContactFlow.impl.BatchRateContactFlowPackageImpl#getFromToFlow()
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
	 * The feature id for the '<em><b>Source Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TO_FLOW__SOURCE_PARAMETERS = EpimodelPackage.FLOW_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Sink Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TO_FLOW__SINK_PARAMETERS = EpimodelPackage.FLOW_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TO_FLOW__PARAMETERS = EpimodelPackage.FLOW_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>From To Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TO_FLOW_FEATURE_COUNT = EpimodelPackage.FLOW_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>From To Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TO_FLOW_OPERATION_COUNT = EpimodelPackage.FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link batchRateContactFlow.impl.ContactImpl <em>Contact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see batchRateContactFlow.impl.ContactImpl
	 * @see batchRateContactFlow.impl.BatchRateContactFlowPackageImpl#getContact()
	 * @generated
	 */
	int CONTACT = 0;

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
	 * The feature id for the '<em><b>Source Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__SOURCE_PARAMETERS = FROM_TO_FLOW__SOURCE_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Sink Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__SINK_PARAMETERS = FROM_TO_FLOW__SINK_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__PARAMETERS = FROM_TO_FLOW__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Contact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__CONTACT = FROM_TO_FLOW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Contact Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__CONTACT_PARAMETERS = FROM_TO_FLOW_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Contact And Source Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__CONTACT_AND_SOURCE_PARAMETERS = FROM_TO_FLOW_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FEATURE_COUNT = FROM_TO_FLOW_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_OPERATION_COUNT = FROM_TO_FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link batchRateContactFlow.impl.BatchImpl <em>Batch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see batchRateContactFlow.impl.BatchImpl
	 * @see batchRateContactFlow.impl.BatchRateContactFlowPackageImpl#getBatch()
	 * @generated
	 */
	int BATCH = 1;

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
	 * The feature id for the '<em><b>Source Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH__SOURCE_PARAMETERS = FROM_TO_FLOW__SOURCE_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Sink Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH__SINK_PARAMETERS = FROM_TO_FLOW__SINK_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH__PARAMETERS = FROM_TO_FLOW__PARAMETERS;

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
	 * The meta object id for the '{@link batchRateContactFlow.impl.RateImpl <em>Rate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see batchRateContactFlow.impl.RateImpl
	 * @see batchRateContactFlow.impl.BatchRateContactFlowPackageImpl#getRate()
	 * @generated
	 */
	int RATE = 2;

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
	 * The feature id for the '<em><b>Source Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__SOURCE_PARAMETERS = FROM_TO_FLOW__SOURCE_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Sink Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__SINK_PARAMETERS = FROM_TO_FLOW__SINK_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__PARAMETERS = FROM_TO_FLOW__PARAMETERS;

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
	 * Returns the meta object for class '{@link batchRateContactFlow.Contact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contact</em>'.
	 * @see batchRateContactFlow.Contact
	 * @generated
	 */
	EClass getContact();

	/**
	 * Returns the meta object for the reference '{@link batchRateContactFlow.Contact#getContact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contact</em>'.
	 * @see batchRateContactFlow.Contact#getContact()
	 * @see #getContact()
	 * @generated
	 */
	EReference getContact_Contact();

	/**
	 * Returns the meta object for the attribute '{@link batchRateContactFlow.Contact#getContactParameters <em>Contact Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Contact Parameters</em>'.
	 * @see batchRateContactFlow.Contact#getContactParameters()
	 * @see #getContact()
	 * @generated
	 */
	EAttribute getContact_ContactParameters();

	/**
	 * Returns the meta object for the attribute '{@link batchRateContactFlow.Contact#getContactAndSourceParameters <em>Contact And Source Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Contact And Source Parameters</em>'.
	 * @see batchRateContactFlow.Contact#getContactAndSourceParameters()
	 * @see #getContact()
	 * @generated
	 */
	EAttribute getContact_ContactAndSourceParameters();

	/**
	 * Returns the meta object for class '{@link batchRateContactFlow.Batch <em>Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Batch</em>'.
	 * @see batchRateContactFlow.Batch
	 * @generated
	 */
	EClass getBatch();

	/**
	 * Returns the meta object for class '{@link batchRateContactFlow.Rate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rate</em>'.
	 * @see batchRateContactFlow.Rate
	 * @generated
	 */
	EClass getRate();

	/**
	 * Returns the meta object for class '{@link batchRateContactFlow.FromToFlow <em>From To Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>From To Flow</em>'.
	 * @see batchRateContactFlow.FromToFlow
	 * @generated
	 */
	EClass getFromToFlow();

	/**
	 * Returns the meta object for the reference '{@link batchRateContactFlow.FromToFlow#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see batchRateContactFlow.FromToFlow#getFrom()
	 * @see #getFromToFlow()
	 * @generated
	 */
	EReference getFromToFlow_From();

	/**
	 * Returns the meta object for the reference '{@link batchRateContactFlow.FromToFlow#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see batchRateContactFlow.FromToFlow#getTo()
	 * @see #getFromToFlow()
	 * @generated
	 */
	EReference getFromToFlow_To();

	/**
	 * Returns the meta object for the attribute '{@link batchRateContactFlow.FromToFlow#getSourceParameters <em>Source Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Parameters</em>'.
	 * @see batchRateContactFlow.FromToFlow#getSourceParameters()
	 * @see #getFromToFlow()
	 * @generated
	 */
	EAttribute getFromToFlow_SourceParameters();

	/**
	 * Returns the meta object for the attribute '{@link batchRateContactFlow.FromToFlow#getSinkParameters <em>Sink Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sink Parameters</em>'.
	 * @see batchRateContactFlow.FromToFlow#getSinkParameters()
	 * @see #getFromToFlow()
	 * @generated
	 */
	EAttribute getFromToFlow_SinkParameters();

	/**
	 * Returns the meta object for the attribute '{@link batchRateContactFlow.FromToFlow#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameters</em>'.
	 * @see batchRateContactFlow.FromToFlow#getParameters()
	 * @see #getFromToFlow()
	 * @generated
	 */
	EAttribute getFromToFlow_Parameters();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BatchRateContactFlowFactory getBatchRateContactFlowFactory();

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
		 * The meta object literal for the '{@link batchRateContactFlow.impl.ContactImpl <em>Contact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see batchRateContactFlow.impl.ContactImpl
		 * @see batchRateContactFlow.impl.BatchRateContactFlowPackageImpl#getContact()
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
		 * The meta object literal for the '<em><b>Contact Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT__CONTACT_PARAMETERS = eINSTANCE.getContact_ContactParameters();

		/**
		 * The meta object literal for the '<em><b>Contact And Source Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT__CONTACT_AND_SOURCE_PARAMETERS = eINSTANCE.getContact_ContactAndSourceParameters();

		/**
		 * The meta object literal for the '{@link batchRateContactFlow.impl.BatchImpl <em>Batch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see batchRateContactFlow.impl.BatchImpl
		 * @see batchRateContactFlow.impl.BatchRateContactFlowPackageImpl#getBatch()
		 * @generated
		 */
		EClass BATCH = eINSTANCE.getBatch();

		/**
		 * The meta object literal for the '{@link batchRateContactFlow.impl.RateImpl <em>Rate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see batchRateContactFlow.impl.RateImpl
		 * @see batchRateContactFlow.impl.BatchRateContactFlowPackageImpl#getRate()
		 * @generated
		 */
		EClass RATE = eINSTANCE.getRate();

		/**
		 * The meta object literal for the '{@link batchRateContactFlow.impl.FromToFlowImpl <em>From To Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see batchRateContactFlow.impl.FromToFlowImpl
		 * @see batchRateContactFlow.impl.BatchRateContactFlowPackageImpl#getFromToFlow()
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

		/**
		 * The meta object literal for the '<em><b>Source Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FROM_TO_FLOW__SOURCE_PARAMETERS = eINSTANCE.getFromToFlow_SourceParameters();

		/**
		 * The meta object literal for the '<em><b>Sink Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FROM_TO_FLOW__SINK_PARAMETERS = eINSTANCE.getFromToFlow_SinkParameters();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FROM_TO_FLOW__PARAMETERS = eINSTANCE.getFromToFlow_Parameters();

	}

} //BatchRateContactFlowPackage
