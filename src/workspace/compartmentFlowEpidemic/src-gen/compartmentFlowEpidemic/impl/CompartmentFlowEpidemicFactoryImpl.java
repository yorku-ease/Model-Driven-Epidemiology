/**
 */
package compartmentFlowEpidemic.impl;

import compartmentFlowEpidemic.*;

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
public class CompartmentFlowEpidemicFactoryImpl extends EFactoryImpl implements CompartmentFlowEpidemicFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CompartmentFlowEpidemicFactory init() {
		try {
			CompartmentFlowEpidemicFactory theCompartmentFlowEpidemicFactory = (CompartmentFlowEpidemicFactory) EPackage.Registry.INSTANCE
					.getEFactory(CompartmentFlowEpidemicPackage.eNS_URI);
			if (theCompartmentFlowEpidemicFactory != null) {
				return theCompartmentFlowEpidemicFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CompartmentFlowEpidemicFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompartmentFlowEpidemicFactoryImpl() {
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
		case CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC:
			return createCompartmentFlowEpidemic();
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
	public CompartmentFlowEpidemic createCompartmentFlowEpidemic() {
		CompartmentFlowEpidemicImpl compartmentFlowEpidemic = new CompartmentFlowEpidemicImpl();
		return compartmentFlowEpidemic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentFlowEpidemicPackage getCompartmentFlowEpidemicPackage() {
		return (CompartmentFlowEpidemicPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CompartmentFlowEpidemicPackage getPackage() {
		return CompartmentFlowEpidemicPackage.eINSTANCE;
	}

} //CompartmentFlowEpidemicFactoryImpl
