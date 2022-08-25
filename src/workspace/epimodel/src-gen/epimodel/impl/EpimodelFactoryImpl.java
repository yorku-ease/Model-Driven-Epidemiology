/**
 */
package epimodel.impl;

import epimodel.*;

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
public class EpimodelFactoryImpl extends EFactoryImpl implements EpimodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EpimodelFactory init() {
		try {
			EpimodelFactory theEpimodelFactory = (EpimodelFactory) EPackage.Registry.INSTANCE
					.getEFactory(EpimodelPackage.eNS_URI);
			if (theEpimodelFactory != null) {
				return theEpimodelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EpimodelFactoryImpl();
	}
	
	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpimodelFactoryImpl() {
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
		case EpimodelPackage.EPIDEMIC_WRAPPER:
			return createEpidemicWrapper();
		case EpimodelPackage.COMPARTMENT_WRAPPER:
			return createCompartmentWrapper();
		case EpimodelPackage.COMPARTMENT:
			return createCompartment();
		case EpimodelPackage.FLOW_WRAPPER:
			return createFlowWrapper();
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
	public EpidemicWrapper createEpidemicWrapper() {
		EpidemicWrapperImpl epidemicWrapper = new EpidemicWrapperImpl();
		return epidemicWrapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentWrapper createCompartmentWrapper() {
		CompartmentWrapperImpl compartmentWrapper = new CompartmentWrapperImpl();
		return compartmentWrapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Compartment createCompartment() {
		CompartmentImpl compartment = new CompartmentImpl();
		return compartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FlowWrapper createFlowWrapper() {
		FlowWrapperImpl flowWrapper = new FlowWrapperImpl();
		return flowWrapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EpimodelPackage getEpimodelPackage() {
		return (EpimodelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EpimodelPackage getPackage() {
		return EpimodelPackage.eINSTANCE;
	}

} //EpimodelFactoryImpl
