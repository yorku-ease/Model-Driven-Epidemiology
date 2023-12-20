/**
 */
package EpidemicRoot.impl;

import EpidemicRoot.*;

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
public class EpidemicRootFactoryImpl extends EFactoryImpl implements EpidemicRootFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EpidemicRootFactory init() {
		try {
			EpidemicRootFactory theEpidemicRootFactory = (EpidemicRootFactory)EPackage.Registry.INSTANCE.getEFactory(EpidemicRootPackage.eNS_URI);
			if (theEpidemicRootFactory != null) {
				return theEpidemicRootFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EpidemicRootFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpidemicRootFactoryImpl() {
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
			case EpidemicRootPackage.EPIDEMIC: return createEpidemic();
			case EpidemicRootPackage.GROUP: return createGroup();
			case EpidemicRootPackage.UNIT_COMPARTMENT: return createUnitCompartment();
			case EpidemicRootPackage.FLOW: return createFlow();
			case EpidemicRootPackage.CONTACT: return createContact();
			case EpidemicRootPackage.RATE: return createRate();
			case EpidemicRootPackage.PRODUCT: return createProduct();
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
	public Epidemic createEpidemic() {
		EpidemicImpl epidemic = new EpidemicImpl();
		return epidemic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Group createGroup() {
		GroupImpl group = new GroupImpl();
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UnitCompartment createUnitCompartment() {
		UnitCompartmentImpl unitCompartment = new UnitCompartmentImpl();
		return unitCompartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Flow createFlow() {
		FlowImpl flow = new FlowImpl();
		return flow;
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
	public Product createProduct() {
		ProductImpl product = new ProductImpl();
		return product;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EpidemicRootPackage getEpidemicRootPackage() {
		return (EpidemicRootPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EpidemicRootPackage getPackage() {
		return EpidemicRootPackage.eINSTANCE;
	}

} //EpidemicRootFactoryImpl
