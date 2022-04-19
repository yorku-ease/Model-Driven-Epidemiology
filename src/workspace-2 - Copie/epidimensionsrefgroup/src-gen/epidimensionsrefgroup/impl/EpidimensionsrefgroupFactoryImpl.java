/**
 */
package epidimensionsrefgroup.impl;

import epidimensionsrefgroup.*;

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
public class EpidimensionsrefgroupFactoryImpl extends EFactoryImpl implements EpidimensionsrefgroupFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EpidimensionsrefgroupFactory init() {
		try {
			EpidimensionsrefgroupFactory theEpidimensionsrefgroupFactory = (EpidimensionsrefgroupFactory) EPackage.Registry.INSTANCE
					.getEFactory(EpidimensionsrefgroupPackage.eNS_URI);
			if (theEpidimensionsrefgroupFactory != null) {
				return theEpidimensionsrefgroupFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EpidimensionsrefgroupFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpidimensionsrefgroupFactoryImpl() {
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
		case EpidimensionsrefgroupPackage.EPIDEMIC:
			return createEpidemic();
		case EpidimensionsrefgroupPackage.DIMENSION:
			return createDimension();
		case EpidimensionsrefgroupPackage.DIMENSION_REF:
			return createDimensionRef();
		case EpidimensionsrefgroupPackage.COMPARTMENT:
			return createCompartment();
		case EpidimensionsrefgroupPackage.RATE:
			return createRate();
		case EpidimensionsrefgroupPackage.BATCH:
			return createBatch();
		case EpidimensionsrefgroupPackage.CONTACT:
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
	public DimensionRef createDimensionRef() {
		DimensionRefImpl dimensionRef = new DimensionRefImpl();
		return dimensionRef;
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
	public EpidimensionsrefgroupPackage getEpidimensionsrefgroupPackage() {
		return (EpidimensionsrefgroupPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EpidimensionsrefgroupPackage getPackage() {
		return EpidimensionsrefgroupPackage.eINSTANCE;
	}

} //EpidimensionsrefgroupFactoryImpl
