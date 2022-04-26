/**
 */
package batchRateContactFlows.impl;

import batchRateContactFlows.*;

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
public class BatchRateContactFlowsFactoryImpl extends EFactoryImpl implements BatchRateContactFlowsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BatchRateContactFlowsFactory init() {
		try {
			BatchRateContactFlowsFactory theBatchRateContactFlowsFactory = (BatchRateContactFlowsFactory) EPackage.Registry.INSTANCE
					.getEFactory(BatchRateContactFlowsPackage.eNS_URI);
			if (theBatchRateContactFlowsFactory != null) {
				return theBatchRateContactFlowsFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BatchRateContactFlowsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BatchRateContactFlowsFactoryImpl() {
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
		case BatchRateContactFlowsPackage.BATCH:
			return createBatch();
		case BatchRateContactFlowsPackage.RATE:
			return createRate();
		case BatchRateContactFlowsPackage.CONTACT:
			return createContact();
		case BatchRateContactFlowsPackage.FROM_TO_FLOW:
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
	public BatchRateContactFlowsPackage getBatchRateContactFlowsPackage() {
		return (BatchRateContactFlowsPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BatchRateContactFlowsPackage getPackage() {
		return BatchRateContactFlowsPackage.eINSTANCE;
	}

} //BatchRateContactFlowsFactoryImpl
