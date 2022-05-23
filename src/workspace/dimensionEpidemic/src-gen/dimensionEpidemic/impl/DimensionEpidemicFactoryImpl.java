/**
 */
package dimensionEpidemic.impl;

import dimensionEpidemic.*;

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
public class DimensionEpidemicFactoryImpl extends EFactoryImpl implements DimensionEpidemicFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DimensionEpidemicFactory init() {
		try {
			DimensionEpidemicFactory theDimensionEpidemicFactory = (DimensionEpidemicFactory) EPackage.Registry.INSTANCE
					.getEFactory(DimensionEpidemicPackage.eNS_URI);
			if (theDimensionEpidemicFactory != null) {
				return theDimensionEpidemicFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DimensionEpidemicFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DimensionEpidemicFactoryImpl() {
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC:
			return createDimensionEpidemic();
		case DimensionEpidemicPackage.DIMENSION:
			return createDimension();
		case DimensionEpidemicPackage.DIMENSION_WRAPPER:
			return createDimensionWrapper();
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
	public DimensionEpidemic createDimensionEpidemic() {
		DimensionEpidemicImpl dimensionEpidemic = new DimensionEpidemicImpl();
		return dimensionEpidemic;
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
	public DimensionWrapper createDimensionWrapper() {
		DimensionWrapperImpl dimensionWrapper = new DimensionWrapperImpl();
		return dimensionWrapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DimensionEpidemicPackage getDimensionEpidemicPackage() {
		return (DimensionEpidemicPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DimensionEpidemicPackage getPackage() {
		return DimensionEpidemicPackage.eINSTANCE;
	}

} //DimensionEpidemicFactoryImpl
