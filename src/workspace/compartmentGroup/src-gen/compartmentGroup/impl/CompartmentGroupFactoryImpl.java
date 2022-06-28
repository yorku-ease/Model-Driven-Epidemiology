/**
 */
package compartmentGroup.impl;

import compartmentGroup.*;

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
public class CompartmentGroupFactoryImpl extends EFactoryImpl implements CompartmentGroupFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CompartmentGroupFactory init() {
		try {
			CompartmentGroupFactory theCompartmentGroupFactory = (CompartmentGroupFactory) EPackage.Registry.INSTANCE
					.getEFactory(CompartmentGroupPackage.eNS_URI);
			if (theCompartmentGroupFactory != null) {
				return theCompartmentGroupFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CompartmentGroupFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompartmentGroupFactoryImpl() {
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
		case CompartmentGroupPackage.GROUP:
			return createGroup();
		case CompartmentGroupPackage.GROUP_SOURCES:
			return createGroupSources();
		case CompartmentGroupPackage.GROUP_SINKS:
			return createGroupSinks();
		case CompartmentGroupPackage.END:
			return createEnd();
		case CompartmentGroupPackage.LINK:
			return createLink();
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
	public Group createGroup() {
		GroupImpl group = new GroupImpl();
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GroupSources createGroupSources() {
		GroupSourcesImpl groupSources = new GroupSourcesImpl();
		return groupSources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GroupSinks createGroupSinks() {
		GroupSinksImpl groupSinks = new GroupSinksImpl();
		return groupSinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public End createEnd() {
		EndImpl end = new EndImpl();
		return end;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Link createLink() {
		LinkImpl link = new LinkImpl();
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentGroupPackage getCompartmentGroupPackage() {
		return (CompartmentGroupPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CompartmentGroupPackage getPackage() {
		return CompartmentGroupPackage.eINSTANCE;
	}

} //CompartmentGroupFactoryImpl
