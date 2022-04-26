/**
 */
package transformedEpidemic.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import transformedEpidemic.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TransformedEpidemicFactoryImpl extends EFactoryImpl implements TransformedEpidemicFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TransformedEpidemicFactory init() {
		try {
			TransformedEpidemicFactory theTransformedEpidemicFactory = (TransformedEpidemicFactory) EPackage.Registry.INSTANCE
					.getEFactory(TransformedEpidemicPackage.eNS_URI);
			if (theTransformedEpidemicFactory != null) {
				return theTransformedEpidemicFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TransformedEpidemicFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformedEpidemicFactoryImpl() {
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
		case TransformedEpidemicPackage.TRANSFORMED_EPIDEMIC:
			return createTransformedEpidemic();
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
	public TransformedEpidemic createTransformedEpidemic() {
		TransformedEpidemicImpl transformedEpidemic = new TransformedEpidemicImpl();
		return transformedEpidemic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TransformedEpidemicPackage getTransformedEpidemicPackage() {
		return (TransformedEpidemicPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TransformedEpidemicPackage getPackage() {
		return TransformedEpidemicPackage.eINSTANCE;
	}

} //TransformedEpidemicFactoryImpl
