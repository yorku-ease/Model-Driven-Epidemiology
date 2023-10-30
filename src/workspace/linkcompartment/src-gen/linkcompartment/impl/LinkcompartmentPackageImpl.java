/**
 */
package linkcompartment.impl;

import epimodel.EpimodelPackage;

import linkcompartment.Link;
import linkcompartment.LinkcompartmentFactory;
import linkcompartment.LinkcompartmentPackage;

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
public class LinkcompartmentPackageImpl extends EPackageImpl implements LinkcompartmentPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkEClass = null;

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
	 * @see linkcompartment.LinkcompartmentPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LinkcompartmentPackageImpl() {
		super(eNS_URI, LinkcompartmentFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link LinkcompartmentPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LinkcompartmentPackage init() {
		if (isInited)
			return (LinkcompartmentPackage) EPackage.Registry.INSTANCE.getEPackage(LinkcompartmentPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredLinkcompartmentPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		LinkcompartmentPackageImpl theLinkcompartmentPackage = registeredLinkcompartmentPackage instanceof LinkcompartmentPackageImpl
				? (LinkcompartmentPackageImpl) registeredLinkcompartmentPackage
				: new LinkcompartmentPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		// manually removed
//		EpimodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theLinkcompartmentPackage.createPackageContents();

		// Initialize created meta-data
		theLinkcompartmentPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLinkcompartmentPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LinkcompartmentPackage.eNS_URI, theLinkcompartmentPackage);
		return theLinkcompartmentPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLink() {
		return linkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLink_Link() {
		return (EReference) linkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkcompartmentFactory getLinkcompartmentFactory() {
		return (LinkcompartmentFactory) getEFactoryInstance();
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
		linkEClass = createEClass(LINK);
		createEReference(linkEClass, LINK__LINK);
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
		linkEClass.getESuperTypes().add(theEpimodelPackage.getCompartment());

		// Initialize classes, features, and operations; add parameters
		initEClass(linkEClass, Link.class, "Link", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLink_Link(), theEpimodelPackage.getCompartment(), null, "link", null, 0, 1, Link.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //LinkcompartmentPackageImpl
