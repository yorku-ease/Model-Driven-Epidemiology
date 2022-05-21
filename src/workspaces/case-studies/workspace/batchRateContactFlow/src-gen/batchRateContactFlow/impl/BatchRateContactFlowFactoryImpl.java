/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BatchRateContactFlowFactoryImpl extends EFactoryImpl implements BatchRateContactFlowFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BatchRateContactFlowFactory init() {
		try {
			BatchRateContactFlowFactory theBatchRateContactFlowFactory = (BatchRateContactFlowFactory) EPackage.Registry.INSTANCE
					.getEFactory(BatchRateContactFlowPackage.eNS_URI);
			if (theBatchRateContactFlowFactory != null) {
				return theBatchRateContactFlowFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BatchRateContactFlowFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BatchRateContactFlowFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case BatchRateContactFlowPackage.CONTACT:
			return createContact();
		case BatchRateContactFlowPackage.BATCH:
			return createBatch();
		case BatchRateContactFlowPackage.RATE:
			return createRate();
		case BatchRateContactFlowPackage.FROM_TO_FLOW:
			return createFromToFlow();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Contact createContact() {
		ContactImpl contact = new ContactImpl();
		return contact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Batch createBatch() {
		BatchImpl batch = new BatchImpl();
		return batch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rate createRate() {
		RateImpl rate = new RateImpl();
		return rate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FromToFlow createFromToFlow() {
		FromToFlowImpl fromToFlow = new FromToFlowImpl();
		return fromToFlow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BatchRateContactFlowPackage getBatchRateContactFlowPackage() {
		return (BatchRateContactFlowPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BatchRateContactFlowPackage getPackage() {
		return BatchRateContactFlowPackage.eINSTANCE;
	}

} //BatchRateContactFlowFactoryImpl
