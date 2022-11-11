/**
 */
package compartmentLink.impl;

import compartmentLink.CompartmentLink;
import compartmentLink.CompartmentLinkFactory;
import compartmentLink.CompartmentLinkPackage;

import epimodel.EpimodelPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CompartmentLinkPackageImpl extends EPackageImpl implements CompartmentLinkPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compartmentLinkEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see compartmentLink.CompartmentLinkPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CompartmentLinkPackageImpl() {
		super(eNS_URI, CompartmentLinkFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link CompartmentLinkPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CompartmentLinkPackage init() {
		if (isInited)
			return (CompartmentLinkPackage) EPackage.Registry.INSTANCE.getEPackage(CompartmentLinkPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCompartmentLinkPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CompartmentLinkPackageImpl theCompartmentLinkPackage = registeredCompartmentLinkPackage instanceof CompartmentLinkPackageImpl
				? (CompartmentLinkPackageImpl) registeredCompartmentLinkPackage
				: new CompartmentLinkPackageImpl();

		isInited = true;

		// Initialize simple dependencies
//		Manually removed
//		EpimodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCompartmentLinkPackage.createPackageContents();

		// Initialize created meta-data
		theCompartmentLinkPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCompartmentLinkPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CompartmentLinkPackage.eNS_URI, theCompartmentLinkPackage);
		return theCompartmentLinkPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompartmentLink() {
		return compartmentLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompartmentLink_Compartment() {
		return (EReference) compartmentLinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentLinkFactory getCompartmentLinkFactory() {
		return (CompartmentLinkFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		compartmentLinkEClass = createEClass(COMPARTMENT_LINK);
		createEReference(compartmentLinkEClass, COMPARTMENT_LINK__COMPARTMENT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EpimodelPackage theEpimodelPackage = (EpimodelPackage) EPackage.Registry.INSTANCE
				.getEPackage(EpimodelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		compartmentLinkEClass.getESuperTypes().add(theEpimodelPackage.getCompartment());

		// Initialize classes, features, and operations; add parameters
		initEClass(compartmentLinkEClass, CompartmentLink.class, "CompartmentLink", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompartmentLink_Compartment(), theEpimodelPackage.getCompartmentWrapper(), null,
				"compartment", null, 0, 1, CompartmentLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //CompartmentLinkPackageImpl
