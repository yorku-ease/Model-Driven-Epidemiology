/**
 */
package dimensions.impl;

import dimensions.*;

import epibase.Compartment;
import epibase.Epidemic;
import epibase.Flow;
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
public class DimensionsFactoryImpl extends EFactoryImpl implements DimensionsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DimensionsFactory init() {
		try {
			DimensionsFactory theDimensionsFactory = (DimensionsFactory) EPackage.Registry.INSTANCE
					.getEFactory(DimensionsPackage.eNS_URI);
			if (theDimensionsFactory != null) {
				return theDimensionsFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DimensionsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DimensionsFactoryImpl() {
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
		case DimensionsPackage.DIMENSION:
			return createDimension();
		case DimensionsPackage.COMPARTMENT:
			return (EObject) createCompartment();
		case DimensionsPackage.EPIDEMIC:
			return (EObject) createEpidemic();
		case DimensionsPackage.FLOW:
			return (EObject) createFlow();
		case DimensionsPackage.DIMENSION_LINK:
			return createDimensionLink();
		case DimensionsPackage.FROM_TO_FLOW:
			return createFromToFlow();
		case DimensionsPackage.BATCH:
			return createBatch();
		case DimensionsPackage.RATE:
			return createRate();
		case DimensionsPackage.CONTACT:
			return createContact();
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
	public Dimension createDimension() {
		DimensionImpl dimension = new DimensionImpl();
		return dimension;
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
	public DimensionLink createDimensionLink() {
		DimensionLinkImpl dimensionLink = new DimensionLinkImpl();
		return dimensionLink;
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
	public DimensionsPackage getDimensionsPackage() {
		return (DimensionsPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DimensionsPackage getPackage() {
		return DimensionsPackage.eINSTANCE;
	}

} //DimensionsFactoryImpl
