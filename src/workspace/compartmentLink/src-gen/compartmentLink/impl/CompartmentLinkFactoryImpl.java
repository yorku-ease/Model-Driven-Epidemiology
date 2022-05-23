/**
 */
package compartmentLink.impl;

import compartmentLink.*;

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
public class CompartmentLinkFactoryImpl extends EFactoryImpl implements CompartmentLinkFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CompartmentLinkFactory init() {
		try {
			CompartmentLinkFactory theCompartmentLinkFactory = (CompartmentLinkFactory) EPackage.Registry.INSTANCE
					.getEFactory(CompartmentLinkPackage.eNS_URI);
			if (theCompartmentLinkFactory != null) {
				return theCompartmentLinkFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CompartmentLinkFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompartmentLinkFactoryImpl() {
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
		case CompartmentLinkPackage.COMPARTMENT_LINK:
			return createCompartmentLink();
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
	public CompartmentLink createCompartmentLink() {
		CompartmentLinkImpl compartmentLink = new CompartmentLinkImpl();
		return compartmentLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentLinkPackage getCompartmentLinkPackage() {
		return (CompartmentLinkPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CompartmentLinkPackage getPackage() {
		return CompartmentLinkPackage.eINSTANCE;
	}

} //CompartmentLinkFactoryImpl
