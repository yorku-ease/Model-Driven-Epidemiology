/**
 */
package dimensionReference.impl;

import dimensionReference.*;

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
public class DimensionReferenceFactoryImpl extends EFactoryImpl implements DimensionReferenceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DimensionReferenceFactory init() {
		try {
			DimensionReferenceFactory theDimensionReferenceFactory = (DimensionReferenceFactory) EPackage.Registry.INSTANCE
					.getEFactory(DimensionReferencePackage.eNS_URI);
			if (theDimensionReferenceFactory != null) {
				return theDimensionReferenceFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DimensionReferenceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DimensionReferenceFactoryImpl() {
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
		case DimensionReferencePackage.DIMENSION_LINK:
			return createDimensionLink();
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
	public DimensionReferencePackage getDimensionReferencePackage() {
		return (DimensionReferencePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DimensionReferencePackage getPackage() {
		return DimensionReferencePackage.eINSTANCE;
	}

} //DimensionReferenceFactoryImpl
