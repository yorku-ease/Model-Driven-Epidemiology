/**
 */
package a.impl;

import a.*;

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
public class AFactoryImpl extends EFactoryImpl implements AFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AFactory init() {
		try {
			AFactory theAFactory = (AFactory) EPackage.Registry.INSTANCE.getEFactory(APackage.eNS_URI);
			if (theAFactory != null) {
				return theAFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AFactoryImpl() {
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
		case APackage.NEW_ECLASS1:
			return createNewEClass1();
		case APackage.NEW_ECLASS2:
			return createNewEClass2();
		case APackage.NEW_ECLASS3:
			return createNewEClass3();
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
	public NewEClass1 createNewEClass1() {
		NewEClass1Impl newEClass1 = new NewEClass1Impl();
		return newEClass1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NewEClass2 createNewEClass2() {
		NewEClass2Impl newEClass2 = new NewEClass2Impl();
		return newEClass2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NewEClass3 createNewEClass3() {
		NewEClass3Impl newEClass3 = new NewEClass3Impl();
		return newEClass3;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public APackage getAPackage() {
		return (APackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static APackage getPackage() {
		return APackage.eINSTANCE;
	}

} //AFactoryImpl
