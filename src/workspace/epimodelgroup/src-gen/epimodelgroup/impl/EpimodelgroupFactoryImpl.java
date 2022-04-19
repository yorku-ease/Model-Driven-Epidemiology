/**
 */
package epimodelgroup.impl;

import epimodelgroup.*;

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
public class EpimodelgroupFactoryImpl extends EFactoryImpl implements EpimodelgroupFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EpimodelgroupFactory init() {
		try {
			EpimodelgroupFactory theEpimodelgroupFactory = (EpimodelgroupFactory) EPackage.Registry.INSTANCE
					.getEFactory(EpimodelgroupPackage.eNS_URI);
			if (theEpimodelgroupFactory != null) {
				return theEpimodelgroupFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EpimodelgroupFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpimodelgroupFactoryImpl() {
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
		case EpimodelgroupPackage.EPIDEMIC:
			return createEpidemic();
		case EpimodelgroupPackage.DIMENSION:
			return createDimension();
		case EpimodelgroupPackage.META_COMPARTMENT:
			return createMetaCompartment();
		case EpimodelgroupPackage.DIMENSION_REF:
			return createDimensionRef();
		case EpimodelgroupPackage.META_CONTACT:
			return createMetaContact();
		case EpimodelgroupPackage.META_RATE:
			return createMetaRate();
		case EpimodelgroupPackage.META_BATCH:
			return createMetaBatch();
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
	public EpimodelgroupPackage getEpimodelgroupPackage() {
		return (EpimodelgroupPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EpimodelgroupPackage getPackage() {
		return EpimodelgroupPackage.eINSTANCE;
	}

} //EpimodelgroupFactoryImpl
