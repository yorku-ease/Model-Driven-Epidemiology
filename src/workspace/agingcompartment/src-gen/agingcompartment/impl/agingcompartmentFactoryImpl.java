/**
 */
package agingcompartment.impl;

import agingcompartment.*;

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
public class agingcompartmentFactoryImpl extends EFactoryImpl implements agingcompartmentFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static agingcompartmentFactory init() {
		try {
			agingcompartmentFactory theagingcompartmentFactory = (agingcompartmentFactory) EPackage.Registry.INSTANCE
					.getEFactory(agingcompartmentPackage.eNS_URI);
			if (theagingcompartmentFactory != null) {
				return theagingcompartmentFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new agingcompartmentFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public agingcompartmentFactoryImpl() {
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
		case agingcompartmentPackage.AGING:
			return createAging();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Aging createAging() {
		AgingImpl aging = new AgingImpl();
		return aging;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public agingcompartmentPackage getagingcompartmentPackage() {
		return (agingcompartmentPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static agingcompartmentPackage getPackage() {
		return agingcompartmentPackage.eINSTANCE;
	}

} //agingcompartmentFactoryImpl
