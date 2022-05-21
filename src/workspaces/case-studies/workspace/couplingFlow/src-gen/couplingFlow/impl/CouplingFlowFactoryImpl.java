/**
 */
package couplingFlow.impl;

import couplingFlow.*;

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
public class CouplingFlowFactoryImpl extends EFactoryImpl implements CouplingFlowFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CouplingFlowFactory init() {
		try {
			CouplingFlowFactory theCouplingFlowFactory = (CouplingFlowFactory) EPackage.Registry.INSTANCE
					.getEFactory(CouplingFlowPackage.eNS_URI);
			if (theCouplingFlowFactory != null) {
				return theCouplingFlowFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CouplingFlowFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CouplingFlowFactoryImpl() {
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
		case CouplingFlowPackage.COUPLING_FLOW:
			return createCouplingFlow();
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
	public CouplingFlow createCouplingFlow() {
		CouplingFlowImpl couplingFlow = new CouplingFlowImpl();
		return couplingFlow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CouplingFlowPackage getCouplingFlowPackage() {
		return (CouplingFlowPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CouplingFlowPackage getPackage() {
		return CouplingFlowPackage.eINSTANCE;
	}

} //CouplingFlowFactoryImpl
