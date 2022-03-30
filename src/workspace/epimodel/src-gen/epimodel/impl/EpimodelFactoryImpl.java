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
		case EpimodelPackage.EPIDEMIC:
			return createEpidemic();
		case EpimodelPackage.DIMENSION:
			return createDimension();
		case EpimodelPackage.META_COMPARTMENT:
			return createMetaCompartment();
		case EpimodelPackage.META_CONTACT:
			return createMetaContact();
		case EpimodelPackage.META_RATE:
			return createMetaRate();
		case EpimodelPackage.META_BATCH:
			return createMetaBatch();
		case EpimodelPackage.DIMENSION_REF:
			return createDimensionRef();
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
	public MetaCompartment createMetaCompartment() {
		MetaCompartmentImpl metaCompartment = new MetaCompartmentImpl();
		return metaCompartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MetaContact createMetaContact() {
		MetaContactImpl metaContact = new MetaContactImpl();
		return metaContact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MetaRate createMetaRate() {
		MetaRateImpl metaRate = new MetaRateImpl();
		return metaRate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MetaBatch createMetaBatch() {
		MetaBatchImpl metaBatch = new MetaBatchImpl();
		return metaBatch;
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
