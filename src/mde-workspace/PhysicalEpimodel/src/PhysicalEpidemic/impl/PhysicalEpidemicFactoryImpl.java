/**
 */
package PhysicalEpidemic.impl;

import PhysicalEpidemic.*;

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
public class PhysicalEpidemicFactoryImpl extends EFactoryImpl implements PhysicalEpidemicFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PhysicalEpidemicFactory init() {
		try {
			PhysicalEpidemicFactory thePhysicalEpidemicFactory = (PhysicalEpidemicFactory)EPackage.Registry.INSTANCE.getEFactory(PhysicalEpidemicPackage.eNS_URI);
			if (thePhysicalEpidemicFactory != null) {
				return thePhysicalEpidemicFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PhysicalEpidemicFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhysicalEpidemicFactoryImpl() {
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
			case PhysicalEpidemicPackage.PHYSICAL_EPIDEMIC: return createPhysicalEpidemic();
			case PhysicalEpidemicPackage.LABEL: return createLabel();
			case PhysicalEpidemicPackage.EQUATION_TEMPLATE: return createEquationTemplate();
			case PhysicalEpidemicPackage.PHYSICAL_COMPARTMENT: return createPhysicalCompartment();
			case PhysicalEpidemicPackage.PHYSICAL_FLOW: return createPhysicalFlow();
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
	public PhysicalEpidemicPackage getPhysicalEpidemicPackage() {
		return (PhysicalEpidemicPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PhysicalEpidemicPackage getPackage() {
		return PhysicalEpidemicPackage.eINSTANCE;
	}

} //PhysicalEpidemicFactoryImpl
