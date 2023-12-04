/**
 */
package src_ph.PhysicalEpidemicRoot.impl;

import src_ph.PhysicalEpidemicRoot.*;

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
public class PhysicalEpidemicRootFactoryImpl extends EFactoryImpl implements PhysicalEpidemicRootFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PhysicalEpidemicRootFactory init() {
		try {
			PhysicalEpidemicRootFactory thePhysicalEpidemicRootFactory = (PhysicalEpidemicRootFactory)EPackage.Registry.INSTANCE.getEFactory(PhysicalEpidemicRootPackage.eNS_URI);
			if (thePhysicalEpidemicRootFactory != null) {
				return thePhysicalEpidemicRootFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PhysicalEpidemicRootFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhysicalEpidemicRootFactoryImpl() {
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
			case PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC: return createPhysicalEpidemic();
			case PhysicalEpidemicRootPackage.LABEL: return createLabel();
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE: return createEquationTemplate();
			case PhysicalEpidemicRootPackage.PHYSICAL_COMPARTMENT: return createPhysicalCompartment();
			case PhysicalEpidemicRootPackage.PHYSICAL_FLOW: return createPhysicalFlow();
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
	public PhysicalEpidemic createPhysicalEpidemic() {
		PhysicalEpidemicImpl physicalEpidemic = new PhysicalEpidemicImpl();
		return physicalEpidemic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Label createLabel() {
		LabelImpl label = new LabelImpl();
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EquationTemplate createEquationTemplate() {
		EquationTemplateImpl equationTemplate = new EquationTemplateImpl();
		return equationTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PhysicalCompartment createPhysicalCompartment() {
		PhysicalCompartmentImpl physicalCompartment = new PhysicalCompartmentImpl();
		return physicalCompartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PhysicalFlow createPhysicalFlow() {
		PhysicalFlowImpl physicalFlow = new PhysicalFlowImpl();
		return physicalFlow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PhysicalEpidemicRootPackage getPhysicalEpidemicRootPackage() {
		return (PhysicalEpidemicRootPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PhysicalEpidemicRootPackage getPackage() {
		return PhysicalEpidemicRootPackage.eINSTANCE;
	}

} //PhysicalEpidemicRootFactoryImpl
