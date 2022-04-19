/**
 */
package epidimensionsbase.impl;

import epibase.Compartment;
import epidimensionsbase.*;

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
public class EpidimensionsbaseFactoryImpl extends EFactoryImpl implements EpidimensionsbaseFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EpidimensionsbaseFactory init() {
		try {
			EpidimensionsbaseFactory theEpidimensionsbaseFactory = (EpidimensionsbaseFactory) EPackage.Registry.INSTANCE
					.getEFactory(EpidimensionsbasePackage.eNS_URI);
			if (theEpidimensionsbaseFactory != null) {
				return theEpidimensionsbaseFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EpidimensionsbaseFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpidimensionsbaseFactoryImpl() {
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
		case EpidimensionsbasePackage.COMPARTMENT:
			return (EObject) createCompartment();
		case EpidimensionsbasePackage.DIMENSION:
			return createDimension();
		case EpidimensionsbasePackage.BATCH:
			return createBatch();
		case EpidimensionsbasePackage.RATE:
			return createRate();
		case EpidimensionsbasePackage.CONTACT:
			return createContact();
		case EpidimensionsbasePackage.EPIDEMIC:
			return createEpidemic();
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
	public EpidimensionsbasePackage getEpidimensionsbasePackage() {
		return (EpidimensionsbasePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EpidimensionsbasePackage getPackage() {
		return EpidimensionsbasePackage.eINSTANCE;
	}

} //EpidimensionsbaseFactoryImpl
